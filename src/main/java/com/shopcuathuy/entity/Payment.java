package com.shopcuathuy.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Payment Entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "processing_fee", precision = 19, scale = 2)
    private BigDecimal processingFee;

    @NotNull
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Size(max = 100)
    @Column(name = "transaction_id")
    private String transactionId;

    @Size(max = 100)
    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;

    @Size(max = 50)
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Size(max = 1000)
    @Column(name = "gateway_response")
    private String gatewayResponse;

    @Size(max = 1000)
    @Column(name = "gateway_url")
    private String gatewayUrl;

    @Size(max = 1000)
    @Column(name = "return_url")
    private String returnUrl;

    @Size(max = 1000)
    @Column(name = "cancel_url")
    private String cancelUrl;

    @Size(max = 1000)
    @Column(name = "webhook_url")
    private String webhookUrl;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Size(max = 1000)
    @Column(name = "failure_reason")
    private String failureReason;

    @Size(max = 1000)
    @Column(name = "notes")
    private String notes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Payment status enum
     */
    public enum PaymentStatus {
        PENDING,
        PROCESSING,
        SUCCESS,
        FAILED,
        CANCELLED,
        EXPIRED,
        REFUNDED,
        PARTIALLY_REFUNDED
    }
}
