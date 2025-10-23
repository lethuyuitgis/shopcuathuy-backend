package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;


/**
 * ShippingMethod entity representing shipping methods
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "shipping_methods", indexes = {
    @Index(name = "idx_shipping_method_seller_id", columnList = "seller_id"),
    @Index(name = "idx_shipping_method_name", columnList = "name"),
    @Index(name = "idx_shipping_method_type", columnList = "type"),
    @Index(name = "idx_shipping_method_status", columnList = "status"),
    @Index(name = "idx_shipping_method_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ShippingMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "seller_id", nullable = false)
    @NotBlank(message = "Seller ID is required")
    private String sellerId;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Column(name = "type", nullable = false, length = 50)
    @NotBlank(message = "Type is required")
    @Size(max = 50, message = "Type must not exceed 50 characters")
    private String type;

    @Column(name = "carrier", length = 100)
    @Size(max = 100, message = "Carrier must not exceed 100 characters")
    private String carrier;

    @Column(name = "service_code", length = 100)
    @Size(max = 100, message = "Service code must not exceed 100 characters")
    private String serviceCode;

    @Column(name = "base_cost", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Base cost is required")
    @DecimalMin(value = "0.0", message = "Base cost cannot be negative")
    private BigDecimal baseCost;

    @Column(name = "cost_per_kg", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Cost per kg cannot be negative")
    private BigDecimal costPerKg;

    @Column(name = "cost_per_item", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Cost per item cannot be negative")
    private BigDecimal costPerItem;

    @Column(name = "free_shipping_threshold", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Free shipping threshold cannot be negative")
    private BigDecimal freeShippingThreshold;

    @Column(name = "minimum_weight", precision = 8, scale = 2)
    @DecimalMin(value = "0.0", message = "Minimum weight cannot be negative")
    private BigDecimal minimumWeight;

    @Column(name = "maximum_weight", precision = 8, scale = 2)
    @DecimalMin(value = "0.0", message = "Maximum weight cannot be negative")
    private BigDecimal maximumWeight;

    @Column(name = "minimum_order_amount", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Minimum order amount cannot be negative")
    private BigDecimal minimumOrderAmount;

    @Column(name = "maximum_order_amount", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Maximum order amount cannot be negative")
    private BigDecimal maximumOrderAmount;

    @Column(name = "estimated_delivery_days_min", nullable = false)
    @NotNull(message = "Minimum estimated delivery days is required")
    @Min(value = 1, message = "Minimum estimated delivery days must be at least 1")
    private Integer estimatedDeliveryDaysMin;

    @Column(name = "estimated_delivery_days_max", nullable = false)
    @NotNull(message = "Maximum estimated delivery days is required")
    @Min(value = 1, message = "Maximum estimated delivery days must be at least 1")
    private Integer estimatedDeliveryDaysMax;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ShippingStatus status = ShippingStatus.ACTIVE;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "is_free_shipping", nullable = false)
    @Builder.Default
    private Boolean isFreeShipping = false;

    @Column(name = "is_express", nullable = false)
    @Builder.Default
    private Boolean isExpress = false;

    @Column(name = "is_insured", nullable = false)
    @Builder.Default
    private Boolean isInsured = false;

    @Column(name = "is_cod", nullable = false)
    @Builder.Default
    private Boolean isCod = false;

    @Column(name = "is_pickup", nullable = false)
    @Builder.Default
    private Boolean isPickup = false;

    @Column(name = "is_dropoff", nullable = false)
    @Builder.Default
    private Boolean isDropoff = false;

    @Column(name = "tracking_available", nullable = false)
    @Builder.Default
    private Boolean trackingAvailable = true;

    @Column(name = "signature_required", nullable = false)
    @Builder.Default
    private Boolean signatureRequired = false;

    @Column(name = "age_verification_required", nullable = false)
    @Builder.Default
    private Boolean ageVerificationRequired = false;

    @Column(name = "applicable_regions", columnDefinition = "TEXT")
    private String applicableRegions;

    @Column(name = "excluded_regions", columnDefinition = "TEXT")
    private String excludedRegions;

    @Column(name = "applicable_products", columnDefinition = "TEXT")
    private String applicableProducts;

    @Column(name = "excluded_products", columnDefinition = "TEXT")
    private String excludedProducts;

    @Column(name = "terms_and_conditions", columnDefinition = "TEXT")
    @Size(max = 5000, message = "Terms and conditions must not exceed 5000 characters")
    private String termsAndConditions;

    @Column(name = "icon", length = 100)
    @Size(max = 100, message = "Icon must not exceed 100 characters")
    private String icon;

    @Column(name = "image_url", length = 500)
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;

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
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private Seller seller;

    /**
     * Shipping status enum
     */
    public enum ShippingStatus {
        ACTIVE, INACTIVE, SUSPENDED, DISCONTINUED
    }

    /**
     * Check if shipping method is active
     */
    public boolean isActive() {
        return status == ShippingStatus.ACTIVE;
    }

    /**
     * Check if shipping method is inactive
     */
    public boolean isInactive() {
        return status == ShippingStatus.INACTIVE;
    }

    /**
     * Check if shipping method is suspended
     */
    public boolean isSuspended() {
        return status == ShippingStatus.SUSPENDED;
    }

    /**
     * Check if shipping method is discontinued
     */
    public boolean isDiscontinued() {
        return status == ShippingStatus.DISCONTINUED;
    }

    /**
     * Check if shipping method is default
     */
    public boolean isDefault() {
        return isDefault != null && isDefault;
    }

    /**
     * Check if shipping method is free shipping
     */
    public boolean isFreeShipping() {
        return isFreeShipping != null && isFreeShipping;
    }

    /**
     * Check if shipping method is express
     */
    public boolean isExpress() {
        return isExpress != null && isExpress;
    }

    /**
     * Check if shipping method is insured
     */
    public boolean isInsured() {
        return isInsured != null && isInsured;
    }

    /**
     * Check if shipping method is COD
     */
    public boolean isCod() {
        return isCod != null && isCod;
    }

    /**
     * Check if shipping method is pickup
     */
    public boolean isPickup() {
        return isPickup != null && isPickup;
    }

    /**
     * Check if shipping method is dropoff
     */
    public boolean isDropoff() {
        return isDropoff != null && isDropoff;
    }

    /**
     * Check if tracking is available
     */
    public boolean isTrackingAvailable() {
        return trackingAvailable != null && trackingAvailable;
    }

    /**
     * Check if signature is required
     */
    public boolean isSignatureRequired() {
        return signatureRequired != null && signatureRequired;
    }

    /**
     * Check if age verification is required
     */
    public boolean isAgeVerificationRequired() {
        return ageVerificationRequired != null && ageVerificationRequired;
    }

    /**
     * Check if shipping method has cost per kg
     */
    public boolean hasCostPerKg() {
        return costPerKg != null && costPerKg.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Check if shipping method has cost per item
     */
    public boolean hasCostPerItem() {
        return costPerItem != null && costPerItem.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Check if shipping method has free shipping threshold
     */
    public boolean hasFreeShippingThreshold() {
        return freeShippingThreshold != null && 
               freeShippingThreshold.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Check if shipping method has weight limits
     */
    public boolean hasWeightLimits() {
        return minimumWeight != null || maximumWeight != null;
    }

    /**
     * Check if shipping method has order amount limits
     */
    public boolean hasOrderAmountLimits() {
        return minimumOrderAmount != null || maximumOrderAmount != null;
    }

    /**
     * Check if shipping method has applicable regions
     */
    public boolean hasApplicableRegions() {
        return applicableRegions != null && !applicableRegions.trim().isEmpty();
    }

    /**
     * Check if shipping method has excluded regions
     */
    public boolean hasExcludedRegions() {
        return excludedRegions != null && !excludedRegions.trim().isEmpty();
    }

    /**
     * Check if shipping method has applicable products
     */
    public boolean hasApplicableProducts() {
        return applicableProducts != null && !applicableProducts.trim().isEmpty();
    }

    /**
     * Check if shipping method has excluded products
     */
    public boolean hasExcludedProducts() {
        return excludedProducts != null && !excludedProducts.trim().isEmpty();
    }

    /**
     * Check if shipping method has terms and conditions
     */
    public boolean hasTermsAndConditions() {
        return termsAndConditions != null && !termsAndConditions.trim().isEmpty();
    }

    /**
     * Check if shipping method has icon
     */
    public boolean hasIcon() {
        return icon != null && !icon.trim().isEmpty();
    }

    /**
     * Check if shipping method has image
     */
    public boolean hasImage() {
        return imageUrl != null && !imageUrl.trim().isEmpty();
    }

    /**
     * Get type display name
     */
    public String getTypeDisplayName() {
        if (type == null) {
            return "Shipping";
        }
        
        switch (type.toLowerCase()) {
            case "standard":
                return "Standard Shipping";
            case "express":
                return "Express Shipping";
            case "overnight":
                return "Overnight Shipping";
            case "same_day":
                return "Same Day Delivery";
            case "pickup":
                return "Store Pickup";
            case "dropoff":
                return "Drop-off Point";
            default:
                return type;
        }
    }

    /**
     * Get status display name
     */
    public String getStatusDisplayName() {
        if (status == null) {
            return "Unknown";
        }
        
        switch (status) {
            case ACTIVE:
                return "Active";
            case INACTIVE:
                return "Inactive";
            case SUSPENDED:
                return "Suspended";
            case DISCONTINUED:
                return "Discontinued";
            default:
                return "Unknown";
        }
    }

    /**
     * Get estimated delivery range
     */
    public String getEstimatedDeliveryRange() {
        if (estimatedDeliveryDaysMin == null || estimatedDeliveryDaysMax == null) {
            return "Unknown";
        }
        
        if (estimatedDeliveryDaysMin.equals(estimatedDeliveryDaysMax)) {
            return estimatedDeliveryDaysMin + " day(s)";
        } else {
            return estimatedDeliveryDaysMin + "-" + estimatedDeliveryDaysMax + " days";
        }
    }

    /**
     * Get shipping method summary
     */
    public String getShippingMethodSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(getTypeDisplayName());
        
        if (isFreeShipping()) {
            summary.append(" (Free)");
        } else {
            summary.append(": $").append(baseCost);
        }
        
        if (isExpress()) {
            summary.append(" (Express)");
        }
        
        return summary.toString();
    }

    /**
     * Calculate shipping cost for weight
     */
    public BigDecimal calculateShippingCost(BigDecimal weight) {
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            return baseCost;
        }
        
        BigDecimal cost = baseCost;
        
        if (hasCostPerKg()) {
            cost = cost.add(costPerKg.multiply(weight));
        }
        
        return cost;
    }

    /**
     * Calculate shipping cost for item count
     */
    public BigDecimal calculateShippingCostForItems(Integer itemCount) {
        if (itemCount == null || itemCount <= 0) {
            return baseCost;
        }
        
        BigDecimal cost = baseCost;
        
        if (hasCostPerItem()) {
            cost = cost.add(costPerItem.multiply(BigDecimal.valueOf(itemCount)));
        }
        
        return cost;
    }

    /**
     * Check if shipping method is applicable for weight
     */
    public boolean isApplicableForWeight(BigDecimal weight) {
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            return true;
        }
        
        if (minimumWeight != null && weight.compareTo(minimumWeight) < 0) {
            return false;
        }
        
        if (maximumWeight != null && weight.compareTo(maximumWeight) > 0) {
            return false;
        }
        
        return true;
    }

    /**
     * Check if shipping method is applicable for order amount
     */
    public boolean isApplicableForOrderAmount(BigDecimal orderAmount) {
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return true;
        }
        
        if (minimumOrderAmount != null && orderAmount.compareTo(minimumOrderAmount) < 0) {
            return false;
        }
        
        if (maximumOrderAmount != null && orderAmount.compareTo(maximumOrderAmount) > 0) {
            return false;
        }
        
        return true;
    }

    /**
     * Check if shipping method is recent (within last 30 days)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(30));
    }

    /**
     * Check if shipping method is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Get days since creation
     */
    public long getDaysSinceCreation() {
        if (createdAt == null) {
            return 0;
        }
        return java.time.Duration.between(createdAt, LocalDateTime.now()).toDays();
    }
}
