package com.shopcuathuy.dto;

import com.shopcuathuy.entity.Payment;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;


/**
 * Payment DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long id;
    private Long orderId;
    private Long paymentMethodId;
    private String paymentMethodName;
    private BigDecimal amount;
    private BigDecimal processingFee;
    private String currency;
    private String transactionId;
    private String gatewayTransactionId;
    private Payment.PaymentStatus status;
    private String gatewayUrl;
    private String returnUrl;
    private String cancelUrl;
    private LocalDateTime paidAt;
    private LocalDateTime expiredAt;
    private String failureReason;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
