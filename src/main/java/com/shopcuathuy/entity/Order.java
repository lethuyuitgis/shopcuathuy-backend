package com.shopcuathuy.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Order entity representing customer orders
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "orders", indexes = {
    @Index(name = "idx_order_user_id", columnList = "user_id"),
    @Index(name = "idx_order_seller_id", columnList = "seller_id"),
    @Index(name = "idx_order_status", columnList = "status"),
    @Index(name = "idx_order_payment_status", columnList = "payment_status"),
    @Index(name = "idx_order_created_at", columnList = "created_at"),
    @Index(name = "idx_order_order_number", columnList = "order_number")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "order_number", nullable = false, length = 50, unique = true)
    @NotBlank(message = "Order number is required")
    @Size(max = 50, message = "Order number must not exceed 50 characters")
    private String orderNumber;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "seller_id", nullable = false)
    @NotBlank(message = "Seller ID is required")
    private String sellerId;

    @Column(name = "shipping_address_id")
    private String shippingAddressId;

    @Column(name = "billing_address_id")
    private String billingAddressId;

    @Column(name = "subtotal", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Subtotal is required")
    @DecimalMin(value = "0.0", message = "Subtotal cannot be negative")
    private BigDecimal subtotal;

    @Column(name = "tax_amount", precision = 15, scale = 2)
    @Builder.Default
    @DecimalMin(value = "0.0", message = "Tax amount cannot be negative")
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "shipping_cost", precision = 15, scale = 2)
    @Builder.Default
    @DecimalMin(value = "0.0", message = "Shipping cost cannot be negative")
    private BigDecimal shippingCost = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 15, scale = 2)
    @Builder.Default
    @DecimalMin(value = "0.0", message = "Discount amount cannot be negative")
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", message = "Total amount cannot be negative")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "payment_reference", length = 255)
    @Size(max = 255, message = "Payment reference must not exceed 255 characters")
    private String paymentReference;

    @Column(name = "shipping_method", length = 100)
    @Size(max = 100, message = "Shipping method must not exceed 100 characters")
    private String shippingMethod;

    @Column(name = "tracking_number", length = 100)
    @Size(max = 100, message = "Tracking number must not exceed 100 characters")
    private String trackingNumber;

    @Column(name = "notes", columnDefinition = "TEXT")
    @Size(max = 2000, message = "Notes must not exceed 2000 characters")
    private String notes;

    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason", length = 500)
    @Size(max = 500, message = "Cancellation reason must not exceed 500 characters")
    private String cancellationReason;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id", insertable = false, updatable = false)
    private UserAddress shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id", insertable = false, updatable = false)
    private UserAddress billingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * Order status enum
     */
    public enum OrderStatus {
        PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, REFUNDED
    }

    /**
     * Payment status enum
     */
    public enum PaymentStatus {
        PENDING, PAID, FAILED, REFUNDED, PARTIALLY_REFUNDED
    }

    /**
     * Payment method enum
     */
    public enum PaymentMethod {
        CASH_ON_DELIVERY, BANK_TRANSFER, CREDIT_CARD, DEBIT_CARD, PAYPAL, STRIPE, WALLET
    }

    /**
     * Check if order is pending
     */
    public boolean isPending() {
        return status == OrderStatus.PENDING;
    }

    /**
     * Check if order is confirmed
     */
    public boolean isConfirmed() {
        return status == OrderStatus.CONFIRMED;
    }

    /**
     * Check if order is processing
     */
    public boolean isProcessing() {
        return status == OrderStatus.PROCESSING;
    }

    /**
     * Check if order is shipped
     */
    public boolean isShipped() {
        return status == OrderStatus.SHIPPED;
    }

    /**
     * Check if order is delivered
     */
    public boolean isDelivered() {
        return status == OrderStatus.DELIVERED;
    }

    /**
     * Check if order is cancelled
     */
    public boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }

    /**
     * Check if order is refunded
     */
    public boolean isRefunded() {
        return status == OrderStatus.REFUNDED;
    }

    /**
     * Check if payment is pending
     */
    public boolean isPaymentPending() {
        return paymentStatus == PaymentStatus.PENDING;
    }

    /**
     * Check if payment is paid
     */
    public boolean isPaymentPaid() {
        return paymentStatus == PaymentStatus.PAID;
    }

    /**
     * Check if payment is failed
     */
    public boolean isPaymentFailed() {
        return paymentStatus == PaymentStatus.FAILED;
    }

    /**
     * Check if payment is refunded
     */
    public boolean isPaymentRefunded() {
        return paymentStatus == PaymentStatus.REFUNDED;
    }

    /**
     * Calculate total amount
     */
    public BigDecimal calculateTotalAmount() {
        return subtotal
                .add(taxAmount != null ? taxAmount : BigDecimal.ZERO)
                .add(shippingCost != null ? shippingCost : BigDecimal.ZERO)
                .subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);
    }

    /**
     * Get total items count
     */
    public int getTotalItemsCount() {
        return orderItems != null ? orderItems.size() : 0;
    }

    /**
     * Get total quantity
     */
    public int getTotalQuantity() {
        return orderItems != null ? 
                orderItems.stream().mapToInt(OrderItem::getQuantity).sum() : 0;
    }

    /**
     * Check if order can be cancelled
     */
    public boolean canBeCancelled() {
        return status == OrderStatus.PENDING || 
               status == OrderStatus.CONFIRMED || 
               status == OrderStatus.PROCESSING;
    }

    /**
     * Check if order can be shipped
     */
    public boolean canBeShipped() {
        return status == OrderStatus.CONFIRMED || 
               status == OrderStatus.PROCESSING;
    }

    /**
     * Check if order can be delivered
     */
    public boolean canBeDelivered() {
        return status == OrderStatus.SHIPPED;
    }

    /**
     * Check if order can be refunded
     */
    public boolean canBeRefunded() {
        return status == OrderStatus.DELIVERED && 
               paymentStatus == PaymentStatus.PAID;
    }
}
