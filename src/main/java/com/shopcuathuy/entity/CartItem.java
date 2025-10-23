package com.shopcuathuy.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;
import lombok.NoArgsConstructor;



/**
 * CartItem entity representing items in shopping cart
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "cart_items", indexes = {
    @Index(name = "idx_cart_item_user_id", columnList = "user_id"),
    @Index(name = "idx_cart_item_product_id", columnList = "product_id"),
    @Index(name = "idx_cart_item_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "product_id", nullable = false)
    @NotBlank(message = "Product ID is required")
    private String productId;

    @Column(name = "product_variant_id")
    private String productVariantId;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", message = "Unit price cannot be negative")
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Total price is required")
    @DecimalMin(value = "0.0", message = "Total price cannot be negative")
    private BigDecimal totalPrice;

    @Column(name = "discount_amount", precision = 15, scale = 2)
    @Builder.Default
    @DecimalMin(value = "0.0", message = "Discount amount cannot be negative")
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "tax_amount", precision = 15, scale = 2)
    @Builder.Default
    @DecimalMin(value = "0.0", message = "Tax amount cannot be negative")
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "notes", length = 500)
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    @Column(name = "is_gift", nullable = false)
    @Builder.Default
    private Boolean isGift = false;

    @Column(name = "gift_message", length = 500)
    @Size(max = 500, message = "Gift message must not exceed 500 characters")
    private String giftMessage;

    @Column(name = "is_saved_for_later", nullable = false)
    @Builder.Default
    private Boolean isSavedForLater = false;

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
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", insertable = false, updatable = false)
    private ProductVariant productVariant;

    /**
     * Check if item is a gift
     */
    public boolean isGift() {
        return isGift != null && isGift;
    }

    /**
     * Check if item is saved for later
     */
    public boolean isSavedForLater() {
        return isSavedForLater != null && isSavedForLater;
    }

    /**
     * Calculate total price
     */
    public BigDecimal calculateTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Calculate final price after discount and tax
     */
    public BigDecimal calculateFinalPrice() {
        return totalPrice
                .subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO)
                .add(taxAmount != null ? taxAmount : BigDecimal.ZERO);
    }

    /**
     * Get discount percentage
     */
    public BigDecimal getDiscountPercentage() {
        if (discountAmount != null && discountAmount.compareTo(BigDecimal.ZERO) > 0) {
            return discountAmount.divide(totalPrice, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Get tax percentage
     */
    public BigDecimal getTaxPercentage() {
        if (taxAmount != null && taxAmount.compareTo(BigDecimal.ZERO) > 0) {
            return taxAmount.divide(totalPrice, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Check if item has discount
     */
    public boolean hasDiscount() {
        return discountAmount != null && discountAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Check if item has tax
     */
    public boolean hasTax() {
        return taxAmount != null && taxAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Check if item has notes
     */
    public boolean hasNotes() {
        return notes != null && !notes.trim().isEmpty();
    }

    /**
     * Check if item has gift message
     */
    public boolean hasGiftMessage() {
        return giftMessage != null && !giftMessage.trim().isEmpty();
    }

    /**
     * Check if item is recent (within last 30 days)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(30));
    }

    /**
     * Check if item is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Get item summary
     */
    public String getItemSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Quantity: ").append(quantity);
        summary.append(", Price: $").append(unitPrice);
        
        if (hasDiscount()) {
            summary.append(", Discount: $").append(discountAmount);
        }
        
        if (hasTax()) {
            summary.append(", Tax: $").append(taxAmount);
        }
        
        return summary.toString();
    }

    /**
     * Update quantity
     */
    public void updateQuantity(int newQuantity) {
        if (newQuantity > 0) {
            this.quantity = newQuantity;
            this.totalPrice = calculateTotalPrice();
        }
    }

    /**
     * Increment quantity
     */
    public void incrementQuantity(int amount) {
        this.quantity += amount;
        this.totalPrice = calculateTotalPrice();
    }

    /**
     * Decrement quantity
     */
    public void decrementQuantity(int amount) {
        if (this.quantity > amount) {
            this.quantity -= amount;
            this.totalPrice = calculateTotalPrice();
        }
    }
}
