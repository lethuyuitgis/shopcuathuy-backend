package com.shopcuathuy.controller;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopcuathuy.dto.CreatePaymentDTO;
import com.shopcuathuy.dto.PaymentDTO;
import com.shopcuathuy.entity.Payment;
import com.shopcuathuy.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Payment Controller
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment management APIs")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Create payment
     */
    @PostMapping
    @Operation(summary = "Create payment", description = "Create a new payment for an order")
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody CreatePaymentDTO createPaymentDTO) {
        PaymentDTO payment = paymentService.createPayment(createPaymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    /**
     * Process payment
     */
    @PostMapping("/{paymentId}/process")
    @Operation(summary = "Process payment", description = "Process a pending payment")
    public ResponseEntity<PaymentDTO> processPayment(
            @Parameter(description = "Payment ID") @PathVariable Long paymentId) {
        PaymentDTO payment = paymentService.processPayment(paymentId);
        return ResponseEntity.ok(payment);
    }

    /**
     * Handle payment callback
     */
    @PostMapping("/{paymentId}/callback")
    @Operation(summary = "Handle payment callback", description = "Handle payment callback from payment gateway")
    public ResponseEntity<PaymentDTO> handlePaymentCallback(
            @Parameter(description = "Payment ID") @PathVariable Long paymentId,
            @RequestBody Map<String, String> callbackParams) {
        PaymentDTO payment = paymentService.handlePaymentCallback(paymentId, callbackParams);
        return ResponseEntity.ok(payment);
    }

    /**
     * Get payment by ID
     */
    @GetMapping("/{paymentId}")
    @Operation(summary = "Get payment by ID", description = "Get payment details by ID")
    public ResponseEntity<PaymentDTO> getPaymentById(
            @Parameter(description = "Payment ID") @PathVariable Long paymentId) {
        PaymentDTO payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    /**
     * Get payments by order ID
     */
    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get payments by order ID", description = "Get all payments for an order")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByOrderId(
            @Parameter(description = "Order ID") @PathVariable Long orderId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByOrderId(orderId);
        return ResponseEntity.ok(payments);
    }

    /**
     * Get payments by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get payments by status", description = "Get payments by status with pagination")
    public ResponseEntity<Page<PaymentDTO>> getPaymentsByStatus(
            @Parameter(description = "Payment status") @PathVariable Payment.PaymentStatus status,
            Pageable pageable) {
        Page<PaymentDTO> payments = paymentService.getPaymentsByStatus(status, pageable);
        return ResponseEntity.ok(payments);
    }

    /**
     * Get all payments
     */
    @GetMapping
    @Operation(summary = "Get all payments", description = "Get all payments with pagination")
    public ResponseEntity<Page<PaymentDTO>> getAllPayments(Pageable pageable) {
        Page<PaymentDTO> payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(payments);
    }

    /**
     * Cancel payment
     */
    @PostMapping("/{paymentId}/cancel")
    @Operation(summary = "Cancel payment", description = "Cancel a pending payment")
    public ResponseEntity<PaymentDTO> cancelPayment(
            @Parameter(description = "Payment ID") @PathVariable Long paymentId) {
        PaymentDTO payment = paymentService.cancelPayment(paymentId);
        return ResponseEntity.ok(payment);
    }
}
