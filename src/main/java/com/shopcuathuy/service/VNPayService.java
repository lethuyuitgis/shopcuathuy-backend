package com.shopcuathuy.service;

import com.shopcuathuy.config.VNPayConfig;
import com.shopcuathuy.dto.VNPayPaymentDTO;
import com.shopcuathuy.entity.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * VNPay Service
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VNPayService {

    private final VNPayConfig vnPayConfig;

    /**
     * Create VNPay payment URL
     */
    public VNPayPaymentDTO createPaymentUrl(Payment payment) {
        try {
            String vnp_TxnRef = payment.getTransactionId();
            String vnp_Amount = String.valueOf(payment.getAmount().multiply(new java.math.BigDecimal("100")).longValue());
            String vnp_CreateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String vnp_IpAddr = vnPayConfig.getIpAddress();

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnPayConfig.getVersion());
            vnp_Params.put("vnp_Command", vnPayConfig.getCommand());
            vnp_Params.put("vnp_TmnCode", vnPayConfig.getTmnCode());
            vnp_Params.put("vnp_Amount", vnp_Amount);
            vnp_Params.put("vnp_CurrCode", vnPayConfig.getCurrency());
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang " + payment.getOrder().getId());
            vnp_Params.put("vnp_OrderType", vnPayConfig.getOrderType());
            vnp_Params.put("vnp_Locale", vnPayConfig.getLocale());
            vnp_Params.put("vnp_ReturnUrl", payment.getReturnUrl());
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            // Add timeout
            LocalDateTime expireDate = LocalDateTime.now().plusMinutes(vnPayConfig.getTimeout());
            vnp_Params.put("vnp_ExpireDate", expireDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

            // Sort parameters
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            for (String fieldName : fieldNames) {
                String fieldValue = vnp_Params.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    hashData.append(fieldName).append("=").append(fieldValue);
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()))
                         .append("=")
                         .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                        hashData.append("&");
                        query.append("&");
                    }
                }
            }

            String vnp_SecureHash = hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
            query.append("&vnp_SecureHash=").append(vnp_SecureHash);

            String paymentUrl = vnPayConfig.getUrl() + "?" + query.toString();

            return VNPayPaymentDTO.builder()
                    .vnp_TmnCode(vnPayConfig.getTmnCode())
                    .vnp_Amount(vnp_Amount)
                    .vnp_Command(vnPayConfig.getCommand())
                    .vnp_CreateDate(vnp_CreateDate)
                    .vnp_CurrCode(vnPayConfig.getCurrency())
                    .vnp_IpAddr(vnp_IpAddr)
                    .vnp_Locale(vnPayConfig.getLocale())
                    .vnp_OrderInfo("Thanh toan don hang " + payment.getOrder().getId())
                    .vnp_OrderType(vnPayConfig.getOrderType())
                    .vnp_ReturnUrl(payment.getReturnUrl())
                    .vnp_TxnRef(vnp_TxnRef)
                    .vnp_Version(vnPayConfig.getVersion())
                    .vnp_SecureHash(vnp_SecureHash)
                    .paymentUrl(paymentUrl)
                    .build();

        } catch (Exception e) {
            log.error("Error creating VNPay payment URL", e);
            throw new RuntimeException("Failed to create VNPay payment URL", e);
        }
    }

    /**
     * Verify VNPay payment response
     */
    public boolean verifyPaymentResponse(Map<String, String> params) {
        try {
            String vnp_SecureHash = params.get("vnp_SecureHash");
            params.remove("vnp_SecureHash");
            params.remove("vnp_SecureHashType");

            List<String> fieldNames = new ArrayList<>(params.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            for (String fieldName : fieldNames) {
                String fieldValue = params.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    hashData.append(fieldName).append("=").append(fieldValue);
                    if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                        hashData.append("&");
                    }
                }
            }

            String secureHash = hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
            return secureHash.equals(vnp_SecureHash);

        } catch (Exception e) {
            log.error("Error verifying VNPay payment response", e);
            return false;
        }
    }

    /**
     * Generate HMAC SHA512 hash
     */
    private String hmacSHA512(String key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    /**
     * Convert bytes to hex string
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
