package com.shopcuathuy.entity;

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
 * Shipping Entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "shippings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_method_id", nullable = false)
    private ShippingMethod shippingMethod;

    @NotNull
    @Size(max = 100)
    @Column(name = "tracking_number", nullable = false)
    private String trackingNumber;

    @NotNull
    @Size(max = 200)
    @Column(name = "carrier", nullable = false)
    private String carrier;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    @Column(name = "shipping_cost", precision = 19, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "estimated_delivery_date")
    private LocalDateTime estimatedDeliveryDate;

    @Column(name = "actual_delivery_date")
    private LocalDateTime actualDeliveryDate;

    @Size(max = 500)
    @Column(name = "shipping_address")
    private String shippingAddress;

    @Size(max = 100)
    @Column(name = "recipient_name")
    private String recipientName;

    @Size(max = 20)
    @Column(name = "recipient_phone")
    private String recipientPhone;

    @Size(max = 1000)
    @Column(name = "notes")
    private String notes;

    @Size(max = 1000)
    @Column(name = "tracking_url")
    private String trackingUrl;

    @Size(max = 1000)
    @Column(name = "delivery_proof")
    private String deliveryProof;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Shipping status enum
     */
    public enum ShippingStatus {
        PENDING,
        PICKED_UP,
        IN_TRANSIT,
        OUT_FOR_DELIVERY,
        DELIVERED,
        FAILED_DELIVERY,
        RETURNED,
        CANCELLED
    }
}
