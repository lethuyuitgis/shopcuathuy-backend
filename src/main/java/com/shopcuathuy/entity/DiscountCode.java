package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * DiscountCode entity representing discount codes
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "discount_codes", indexes = {
    @Index(name = "idx_discount_code_code", columnList = "code"),
    @Index(name = "idx_discount_code_seller_id", columnList = "seller_id"),
    @Index(name = "idx_discount_code_type", columnList = "type"),
    @Index(name = "idx_discount_code_status", columnList = "status"),
    @Index(name = "idx_discount_code_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DiscountCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "seller_id", nullable = false)
    @NotBlank(message = "Seller ID is required")
    private String sellerId;

    @Column(nullable = false, length = 50, unique = true)
    @NotBlank(message = "Discount code is required")
    @Size(max = 50, message = "Discount code must not exceed 50 characters")
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private DiscountType type = DiscountType.PERCENTAGE;

    @Column(name = "discount_value", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.0", message = "Discount value cannot be negative")
    private BigDecimal discountValue;

    @Column(name = "minimum_order_amount", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Minimum order amount cannot be negative")
    private BigDecimal minimumOrderAmount;

    @Column(name = "maximum_discount_amount", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Maximum discount amount cannot be negative")
    private BigDecimal maximumDiscountAmount;

    @Column(name = "usage_limit")
    @Min(value = 1, message = "Usage limit must be at least 1")
    private Integer usageLimit;

    @Column(name = "usage_count", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Usage count cannot be negative")
    private Integer usageCount = 0;

    @Column(name = "usage_limit_per_user")
    @Min(value = 1, message = "Usage limit per user must be at least 1")
    private Integer usageLimitPerUser;

    @Column(name = "valid_from", nullable = false)
    @NotNull(message = "Valid from date is required")
    private LocalDateTime validFrom;

    @Column(name = "valid_until", nullable = false)
    @NotNull(message = "Valid until date is required")
    private LocalDateTime validUntil;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private DiscountStatus status = DiscountStatus.ACTIVE;

    @Column(name = "is_public", nullable = false)
    @Builder.Default
    private Boolean isPublic = true;

    @Column(name = "is_first_time_only", nullable = false)
    @Builder.Default
    private Boolean isFirstTimeOnly = false;

    @Column(name = "is_new_user_only", nullable = false)
    @Builder.Default
    private Boolean isNewUserOnly = false;

    @Column(name = "is_single_use", nullable = false)
    @Builder.Default
    private Boolean isSingleUse = false;

    @Column(name = "applicable_products", columnDefinition = "TEXT")
    private String applicableProducts;

    @Column(name = "applicable_categories", columnDefinition = "TEXT")
    private String applicableCategories;

    @Column(name = "excluded_products", columnDefinition = "TEXT")
    private String excludedProducts;

    @Column(name = "excluded_categories", columnDefinition = "TEXT")
    private String excludedCategories;

    @Column(name = "terms_and_conditions", columnDefinition = "TEXT")
    @Size(max = 5000, message = "Terms and conditions must not exceed 5000 characters")
    private String termsAndConditions;

    @Column(name = "image_url", length = 500)
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;

    @Column(name = "banner_text", length = 255)
    @Size(max = 255, message = "Banner text must not exceed 255 characters")
    private String bannerText;

    @Column(name = "banner_color", length = 20)
    @Size(max = 20, message = "Banner color must not exceed 20 characters")
    private String bannerColor;

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

    @OneToMany(mappedBy = "discountCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiscountCodeUsage> usages = new ArrayList<>();

    /**
     * Discount type enum
     */
    public enum DiscountType {
        PERCENTAGE, FIXED_AMOUNT, FREE_SHIPPING
    }

    /**
     * Discount status enum
     */
    public enum DiscountStatus {
        ACTIVE, INACTIVE, EXPIRED, CANCELLED
    }

    /**
     * Check if discount is active
     */
    public boolean isActive() {
        return status == DiscountStatus.ACTIVE;
    }

    /**
     * Check if discount is inactive
     */
    public boolean isInactive() {
        return status == DiscountStatus.INACTIVE;
    }

    /**
     * Check if discount is expired
     */
    public boolean isExpired() {
        return status == DiscountStatus.EXPIRED || 
               (validUntil != null && validUntil.isBefore(LocalDateTime.now()));
    }

    /**
     * Check if discount is cancelled
     */
    public boolean isCancelled() {
        return status == DiscountStatus.CANCELLED;
    }

    /**
     * Check if discount is public
     */
    public boolean isPublic() {
        return isPublic != null && isPublic;
    }

    /**
     * Check if discount is first time only
     */
    public boolean isFirstTimeOnly() {
        return isFirstTimeOnly != null && isFirstTimeOnly;
    }

    /**
     * Check if discount is new user only
     */
    public boolean isNewUserOnly() {
        return isNewUserOnly != null && isNewUserOnly;
    }

    /**
     * Check if discount is single use
     */
    public boolean isSingleUse() {
        return isSingleUse != null && isSingleUse;
    }

    /**
     * Check if discount is valid
     */
    public boolean isValid() {
        return isActive() && !isExpired() && 
               (usageLimit == null || usageCount < usageLimit);
    }

    /**
     * Check if discount is percentage type
     */
    public boolean isPercentageType() {
        return type == DiscountType.PERCENTAGE;
    }

    /**
     * Check if discount is fixed amount type
     */
    public boolean isFixedAmountType() {
        return type == DiscountType.FIXED_AMOUNT;
    }

    /**
     * Check if discount is free shipping type
     */
    public boolean isFreeShippingType() {
        return type == DiscountType.FREE_SHIPPING;
    }

    /**
     * Check if discount has minimum order amount
     */
    public boolean hasMinimumOrderAmount() {
        return minimumOrderAmount != null && 
               minimumOrderAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Check if discount has maximum discount amount
     */
    public boolean hasMaximumDiscountAmount() {
        return maximumDiscountAmount != null && 
               maximumDiscountAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Check if discount has usage limit
     */
    public boolean hasUsageLimit() {
        return usageLimit != null && usageLimit > 0;
    }

    /**
     * Check if discount has usage limit per user
     */
    public boolean hasUsageLimitPerUser() {
        return usageLimitPerUser != null && usageLimitPerUser > 0;
    }

    /**
     * Check if discount has applicable products
     */
    public boolean hasApplicableProducts() {
        return applicableProducts != null && !applicableProducts.trim().isEmpty();
    }

    /**
     * Check if discount has applicable categories
     */
    public boolean hasApplicableCategories() {
        return applicableCategories != null && !applicableCategories.trim().isEmpty();
    }

    /**
     * Check if discount has excluded products
     */
    public boolean hasExcludedProducts() {
        return excludedProducts != null && !excludedProducts.trim().isEmpty();
    }

    /**
     * Check if discount has excluded categories
     */
    public boolean hasExcludedCategories() {
        return excludedCategories != null && !excludedCategories.trim().isEmpty();
    }

    /**
     * Check if discount has terms and conditions
     */
    public boolean hasTermsAndConditions() {
        return termsAndConditions != null && !termsAndConditions.trim().isEmpty();
    }

    /**
     * Check if discount has image
     */
    public boolean hasImage() {
        return imageUrl != null && !imageUrl.trim().isEmpty();
    }

    /**
     * Check if discount has banner text
     */
    public boolean hasBannerText() {
        return bannerText != null && !bannerText.trim().isEmpty();
    }

    /**
     * Check if discount has banner color
     */
    public boolean hasBannerColor() {
        return bannerColor != null && !bannerColor.trim().isEmpty();
    }

    /**
     * Get type display name
     */
    public String getTypeDisplayName() {
        if (type == null) {
            return "Discount";
        }
        
        switch (type) {
            case PERCENTAGE:
                return "Percentage Discount";
            case FIXED_AMOUNT:
                return "Fixed Amount Discount";
            case FREE_SHIPPING:
                return "Free Shipping";
            default:
                return "Discount";
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
            case EXPIRED:
                return "Expired";
            case CANCELLED:
                return "Cancelled";
            default:
                return "Unknown";
        }
    }

    /**
     * Get discount summary
     */
    public String getDiscountSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(getTypeDisplayName());
        
        if (isPercentageType()) {
            summary.append(": ").append(discountValue).append("%");
        } else if (isFixedAmountType()) {
            summary.append(": $").append(discountValue);
        } else if (isFreeShippingType()) {
            summary.append(": Free Shipping");
        }
        
        if (hasMinimumOrderAmount()) {
            summary.append(" (Min: $").append(minimumOrderAmount).append(")");
        }
        
        return summary.toString();
    }

    /**
     * Get usage percentage
     */
    public Double getUsagePercentage() {
        if (usageLimit == null || usageLimit == 0) {
            return 0.0;
        }
        return (double) usageCount / usageLimit * 100;
    }

    /**
     * Get remaining usage count
     */
    public Integer getRemainingUsageCount() {
        if (usageLimit == null) {
            return null;
        }
        return Math.max(0, usageLimit - usageCount);
    }

    /**
     * Check if discount is recent (within last 30 days)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(30));
    }

    /**
     * Check if discount is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Check if discount is expiring soon (within next 7 days)
     */
    public boolean isExpiringSoon() {
        return validUntil != null && 
               validUntil.isAfter(LocalDateTime.now()) && 
               validUntil.isBefore(LocalDateTime.now().plusDays(7));
    }

    /**
     * Get days until expiration
     */
    public long getDaysUntilExpiration() {
        if (validUntil == null) {
            return 0;
        }
        return java.time.Duration.between(LocalDateTime.now(), validUntil).toDays();
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

    /**
     * Increment usage count
     */
    public void incrementUsageCount() {
        this.usageCount++;
    }

    /**
     * Decrement usage count
     */
    public void decrementUsageCount() {
        if (this.usageCount > 0) {
            this.usageCount--;
        }
    }

    /**
     * Check if discount can be used
     */
    public boolean canBeUsed() {
        return isValid() && 
               (usageLimit == null || usageCount < usageLimit);
    }

    /**
     * Check if discount can be used by user
     */
    public boolean canBeUsedByUser(String userId) {
        if (!canBeUsed()) {
            return false;
        }
        
        // Check if user has reached usage limit per user
        if (hasUsageLimitPerUser()) {
            long userUsageCount = usages.stream()
                    .filter(usage -> usage.getUserId().equals(userId))
                    .count();
            return userUsageCount < usageLimitPerUser;
        }
        
        return true;
    }
}
