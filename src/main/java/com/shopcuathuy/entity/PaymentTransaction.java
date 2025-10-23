package com.shopcuathuy.entity;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * Payment Transaction Entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "payment_transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @NotNull
    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Size(max = 100)
    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;

    @Size(max = 100)
    @Column(name = "gateway_reference")
    private String gatewayReference;

    @Size(max = 50)
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Size(max = 1000)
    @Column(name = "gateway_response")
    private String gatewayResponse;

    @Size(max = 1000)
    @Column(name = "gateway_request")
    private String gatewayRequest;

    @Size(max = 1000)
    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Size(max = 1000)
    @Column(name = "notes")
    private String notes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Transaction type enum
     */
    public enum TransactionType {
        PAYMENT,
        REFUND,
        PARTIAL_REFUND,
        CHARGEBACK,
        DISPUTE
    }

    /**
     * Transaction status enum
     */
    public enum TransactionStatus {
        PENDING,
        PROCESSING,
        SUCCESS,
        FAILED,
        CANCELLED,
        EXPIRED
    }
}
