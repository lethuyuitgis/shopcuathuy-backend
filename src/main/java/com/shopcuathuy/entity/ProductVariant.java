package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * ProductVariant entity representing product variants
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "product_variants", indexes = {
    @Index(name = "idx_product_variant_product_id", columnList = "product_id"),
    @Index(name = "idx_product_variant_sku", columnList = "sku"),
    @Index(name = "idx_product_variant_status", columnList = "status"),
    @Index(name = "idx_product_variant_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_id", nullable = false)
    @NotBlank(message = "Product ID is required")
    private String productId;

    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "SKU is required")
    @Size(max = 100, message = "SKU must not exceed 100 characters")
    private String sku;

    @Column(name = "variant_name", nullable = false, length = 255)
    @NotBlank(message = "Variant name is required")
    @Size(max = 255, message = "Variant name must not exceed 255 characters")
    private String variantName;

    @Column(name = "variant_description", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Variant description must not exceed 1000 characters")
    private String variantDescription;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private BigDecimal price;

    @Column(name = "original_price", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Original price cannot be negative")
    private BigDecimal originalPrice;

    @Column(name = "cost_price", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Cost price cannot be negative")
    private BigDecimal costPrice;

    @Column(name = "stock_quantity", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity = 0;

    @Column(name = "min_stock_alert", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Minimum stock alert cannot be negative")
    private Integer minStockAlert = 5;

    @Column(name = "weight", precision = 8, scale = 2)
    @DecimalMin(value = "0.0", message = "Weight cannot be negative")
    private BigDecimal weight;

    @Column(name = "dimensions", length = 100)
    @Size(max = 100, message = "Dimensions must not exceed 100 characters")
    private String dimensions;

    @Column(name = "barcode", length = 100)
    @Size(max = 100, message = "Barcode must not exceed 100 characters")
    private String barcode;

    @Column(name = "image_url", length = 500)
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;

    @Column(name = "attributes", columnDefinition = "TEXT")
    private String attributes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private VariantStatus status = VariantStatus.ACTIVE;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Sort order cannot be negative")
    private Integer sortOrder = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    /**
     * Variant status enum
     */
    public enum VariantStatus {
        ACTIVE, INACTIVE, OUT_OF_STOCK
    }

    /**
     * Check if variant is active
     */
    public boolean isActive() {
        return status == VariantStatus.ACTIVE;
    }

    /**
     * Check if variant is inactive
     */
    public boolean isInactive() {
        return status == VariantStatus.INACTIVE;
    }

    /**
     * Check if variant is out of stock
     */
    public boolean isOutOfStock() {
        return status == VariantStatus.OUT_OF_STOCK;
    }

    /**
     * Check if variant is default
     */
    public boolean isDefault() {
        return isDefault != null && isDefault;
    }

    /**
     * Check if variant is in stock
     */
    public boolean isInStock() {
        return stockQuantity > 0 && isActive();
    }

    /**
     * Check if variant is on sale
     */
    public boolean isOnSale() {
        return originalPrice != null && originalPrice.compareTo(price) > 0;
    }

    /**
     * Get discount amount
     */
    public BigDecimal getDiscountAmount() {
        if (isOnSale()) {
            return originalPrice.subtract(price);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calculate discount percentage
     */
    public BigDecimal calculateDiscountPercent() {
        if (originalPrice != null && originalPrice.compareTo(price) > 0) {
            return originalPrice.subtract(price)
                    .divide(originalPrice, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Get profit margin
     */
    public BigDecimal getProfitMargin() {
        if (costPrice != null && costPrice.compareTo(BigDecimal.ZERO) > 0) {
            return price.subtract(costPrice)
                    .divide(costPrice, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Get profit amount
     */
    public BigDecimal getProfitAmount() {
        if (costPrice != null) {
            return price.subtract(costPrice);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Check if stock is low
     */
    public boolean isStockLow() {
        return stockQuantity <= minStockAlert;
    }

    /**
     * Decrement stock quantity
     */
    public boolean decrementStock(int quantity) {
        if (stockQuantity >= quantity) {
            stockQuantity -= quantity;
            if (stockQuantity == 0) {
                status = VariantStatus.OUT_OF_STOCK;
            }
            return true;
        }
        return false;
    }

    /**
     * Increment stock quantity
     */
    public void incrementStock(int quantity) {
        stockQuantity += quantity;
        if (stockQuantity > 0 && status == VariantStatus.OUT_OF_STOCK) {
            status = VariantStatus.ACTIVE;
        }
    }

    /**
     * Get variant name with attributes
     */
    public String getFullVariantName() {
        if (attributes != null && !attributes.trim().isEmpty()) {
            return variantName + " (" + attributes + ")";
        }
        return variantName;
    }
}
