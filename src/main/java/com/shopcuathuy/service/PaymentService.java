package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.shopcuathuy.dto.CreatePaymentDTO;
import com.shopcuathuy.dto.PaymentDTO;
import com.shopcuathuy.dto.VNPayPaymentDTO;
import com.shopcuathuy.entity.*;
import com.shopcuathuy.mapper.PaymentMapper;
import com.shopcuathuy.repository.OrderRepository;
import com.shopcuathuy.repository.PaymentMethodRepository;
import com.shopcuathuy.repository.PaymentRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


/**
 * Payment Service
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;
    private final VNPayService vnPayService;
    private final MessageProducerService messageProducerService;
    private final FileStorageService fileStorageService;

    /**
     * Create payment
     */
    @Transactional
    public PaymentDTO createPayment(CreatePaymentDTO createPaymentDTO) {
        // Validate order
        Order order = orderRepository.findById(String.valueOf(createPaymentDTO.getOrderId()))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Validate payment method
        PaymentMethod paymentMethod = paymentMethodRepository.findById(createPaymentDTO.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        if (!paymentMethod.getIsActive()) {
            throw new RuntimeException("Payment method is not active");
        }

        // Check if payment already exists for this order
        List<Payment> existingPayments = paymentRepository.findByOrderId(createPaymentDTO.getOrderId());
        if (!existingPayments.isEmpty()) {
            throw new RuntimeException("Payment already exists for this order");
        }

        // Calculate processing fee
        BigDecimal processingFee = calculateProcessingFee(createPaymentDTO.getAmount(), paymentMethod);

        // Create payment
        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(paymentMethod)
                .amount(createPaymentDTO.getAmount())
                .processingFee(processingFee)
                .currency(createPaymentDTO.getCurrency())
                .transactionId(UUID.randomUUID().toString())
                .status(Payment.PaymentStatus.PENDING)
                .returnUrl(createPaymentDTO.getReturnUrl())
                .cancelUrl(createPaymentDTO.getCancelUrl())
                .expiredAt(LocalDateTime.now().plusMinutes(15)) // 15 minutes timeout
                .notes(createPaymentDTO.getNotes())
                .build();

        payment = paymentRepository.save(payment);

        // Send payment created message
        messageProducerService.sendPaymentCreated(payment);

        // Store payment data to MinIO
        storePaymentToMinIO(payment);

        return paymentMapper.toDTO(payment);
    }

    /**
     * Process payment
     */
    @Transactional
    public PaymentDTO processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() != Payment.PaymentStatus.PENDING) {
            throw new RuntimeException("Payment is not in pending status");
        }

        // Check if payment is expired
        if (payment.getExpiredAt() != null && payment.getExpiredAt().isBefore(LocalDateTime.now())) {
            payment.setStatus(Payment.PaymentStatus.EXPIRED);
            paymentRepository.save(payment);
            throw new RuntimeException("Payment has expired");
        }

        // Process based on payment method
        if ("VNPAY".equals(payment.getPaymentMethod().getCode())) {
            return processVNPayPayment(payment);
        } else if ("COD".equals(payment.getPaymentMethod().getCode())) {
            return processCODPayment(payment);
        } else {
            throw new RuntimeException("Unsupported payment method");
        }
    }

    /**
     * Process VNPay payment
     */
    private PaymentDTO processVNPayPayment(Payment payment) {
        try {
            VNPayPaymentDTO vnPayPayment = vnPayService.createPaymentUrl(payment);
            
            payment.setStatus(Payment.PaymentStatus.PROCESSING);
            payment.setGatewayUrl(vnPayPayment.getPaymentUrl());
            payment.setGatewayResponse(vnPayPayment.toString());
            
            payment = paymentRepository.save(payment);

            // Send payment processing message
            messageProducerService.sendPaymentProcessing(payment);

            return paymentMapper.toDTO(payment);
        } catch (Exception e) {
            log.error("Error processing VNPay payment", e);
            payment.setStatus(Payment.PaymentStatus.FAILED);
            payment.setFailureReason(e.getMessage());
            paymentRepository.save(payment);
            throw new RuntimeException("Failed to process VNPay payment", e);
        }
    }

    /**
     * Process COD payment
     */
    private PaymentDTO processCODPayment(Payment payment) {
        payment.setStatus(Payment.PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());
        payment = paymentRepository.save(payment);

        // Send payment success message
        messageProducerService.sendPaymentSuccess(payment);

        return paymentMapper.toDTO(payment);
    }

    /**
     * Handle payment callback
     */
    @Transactional
    public PaymentDTO handlePaymentCallback(Long paymentId, Map<String, String> callbackParams) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if ("VNPAY".equals(payment.getPaymentMethod().getCode())) {
            return handleVNPayCallback(payment, callbackParams);
        } else {
            throw new RuntimeException("Unsupported payment method for callback");
        }
    }

    /**
     * Handle VNPay callback
     */
    private PaymentDTO handleVNPayCallback(Payment payment, Map<String, String> callbackParams) {
        try {
            // Verify payment response
            if (!vnPayService.verifyPaymentResponse(callbackParams)) {
                payment.setStatus(Payment.PaymentStatus.FAILED);
                payment.setFailureReason("Invalid payment response");
                paymentRepository.save(payment);
                throw new RuntimeException("Invalid payment response");
            }

            String responseCode = callbackParams.get("vnp_ResponseCode");
            String transactionId = callbackParams.get("vnp_TransactionNo");

            if ("00".equals(responseCode)) {
                // Payment successful
                payment.setStatus(Payment.PaymentStatus.SUCCESS);
                payment.setGatewayTransactionId(transactionId);
                payment.setPaidAt(LocalDateTime.now());
                payment.setGatewayResponse(callbackParams.toString());
                
                payment = paymentRepository.save(payment);

                // Send payment success message
                messageProducerService.sendPaymentSuccess(payment);

            } else {
                // Payment failed
                payment.setStatus(Payment.PaymentStatus.FAILED);
                payment.setFailureReason("Payment failed with code: " + responseCode);
                payment.setGatewayResponse(callbackParams.toString());
                paymentRepository.save(payment);

                // Send payment failed message
                messageProducerService.sendPaymentFailed(payment);
            }

            return paymentMapper.toDTO(payment);

        } catch (Exception e) {
            log.error("Error handling VNPay callback", e);
            payment.setStatus(Payment.PaymentStatus.FAILED);
            payment.setFailureReason(e.getMessage());
            paymentRepository.save(payment);
            throw new RuntimeException("Failed to handle payment callback", e);
        }
    }

    /**
     * Get payment by ID
     */
    public PaymentDTO getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return paymentMapper.toDTO(payment);
    }

    /**
     * Get payments by order ID
     */
    public List<PaymentDTO> getPaymentsByOrderId(Long orderId) {
        List<Payment> payments = paymentRepository.findByOrderId(orderId);
        return paymentMapper.toDTOList(payments);
    }

    /**
     * Get payments by status
     */
    public Page<PaymentDTO> getPaymentsByStatus(Payment.PaymentStatus status, Pageable pageable) {
        Page<Payment> payments = paymentRepository.findByStatus(status, pageable);
        return payments.map(paymentMapper::toDTO);
    }

    /**
     * Get all payments with pagination
     */
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAll(pageable);
        return payments.map(paymentMapper::toDTO);
    }

    /**
     * Cancel payment
     */
    @Transactional
    public PaymentDTO cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() == Payment.PaymentStatus.SUCCESS) {
            throw new RuntimeException("Cannot cancel successful payment");
        }

        payment.setStatus(Payment.PaymentStatus.CANCELLED);
        payment = paymentRepository.save(payment);

        // Send payment cancelled message
        messageProducerService.sendPaymentCancelled(payment);

        return paymentMapper.toDTO(payment);
    }

    /**
     * Calculate processing fee
     */
    private BigDecimal calculateProcessingFee(BigDecimal amount, PaymentMethod paymentMethod) {
        BigDecimal fee = BigDecimal.ZERO;

        if (paymentMethod.getProcessingFeePercentage() != null) {
            fee = fee.add(amount.multiply(BigDecimal.valueOf(paymentMethod.getProcessingFeePercentage())).divide(new BigDecimal("100")));
        }

        if (paymentMethod.getProcessingFeeFixed() != null) {
            fee = fee.add(BigDecimal.valueOf(paymentMethod.getProcessingFeeFixed()));
        }

        return fee;
    }

    /**
     * Export payment data
     */
    public String exportPaymentData(Long userId, LocalDateTime startDate, LocalDateTime endDate, String format) {
        List<Payment> payments = paymentRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate);
        
        // Generate export data
        String exportData = generatePaymentExportData(payments, format);
        
        // Store to MinIO
        String fileName = "payment-export-" + userId + "-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadPaymentExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Store payment data to MinIO
     */
    private void storePaymentToMinIO(Payment payment) {
        try {
            String paymentData = createPaymentDataJson(payment);
            String fileName = "payments/" + payment.getStatus() + "/" +
                            payment.getCreatedAt().toLocalDate() + "/" +
                            payment.getId() + ".json";
            
            fileStorageService.uploadPaymentData(fileName, paymentData);
        } catch (Exception e) {
            System.err.println("Failed to store payment data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create payment data JSON
     */
    private String createPaymentDataJson(Payment payment) {
        return String.format(
            "{\"id\":%d,\"orderId\":%d,\"amount\":%s,\"status\":\"%s\",\"method\":\"%s\",\"createdAt\":\"%s\"}",
            payment.getId(),
            payment.getOrder().getId(),
            payment.getAmount(),
            payment.getStatus(),
            payment.getPaymentMethod().getName(),
            payment.getCreatedAt()
        );
    }

    /**
     * Generate payment export data
     */
    private String generatePaymentExportData(List<Payment> payments, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generatePaymentJsonExport(payments);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generatePaymentCsvExport(payments);
        }
        return generatePaymentJsonExport(payments);
    }

    /**
     * Generate JSON export
     */
    private String generatePaymentJsonExport(List<Payment> payments) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < payments.size(); i++) {
            Payment payment = payments.get(i);
            json.append(createPaymentDataJson(payment));
            if (i < payments.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generatePaymentCsvExport(List<Payment> payments) {
        StringBuilder csv = new StringBuilder("id,orderId,amount,status,method,createdAt\n");
        for (Payment payment : payments) {
            csv.append(String.format("%d,%d,%s,%s,%s,%s\n",
                payment.getId(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getPaymentMethod().getName(),
                payment.getCreatedAt()
            ));
        }
        return csv.toString();
    }
}
