package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * DiscountCodeUsage entity representing discount code usage records
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "discount_code_usage", indexes = {
    @Index(name = "idx_discount_code_usage_user_id", columnList = "user_id"),
    @Index(name = "idx_discount_code_usage_discount_code_id", columnList = "discount_code_id"),
    @Index(name = "idx_discount_code_usage_order_id", columnList = "order_id"),
    @Index(name = "idx_discount_code_usage_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DiscountCodeUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "discount_code_id", nullable = false)
    @NotBlank(message = "Discount code ID is required")
    private String discountCodeId;

    @Column(name = "order_id", nullable = false)
    @NotBlank(message = "Order ID is required")
    private String orderId;

    @Column(name = "discount_amount", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Discount amount is required")
    @DecimalMin(value = "0.0", message = "Discount amount cannot be negative")
    private BigDecimal discountAmount;

    @Column(name = "order_amount", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Order amount is required")
    @DecimalMin(value = "0.0", message = "Order amount cannot be negative")
    private BigDecimal orderAmount;

    @Column(name = "discount_percentage", precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "Discount percentage cannot be negative")
    @DecimalMax(value = "100.0", message = "Discount percentage cannot exceed 100")
    private BigDecimal discountPercentage;

    @Column(name = "savings_amount", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Savings amount cannot be negative")
    private BigDecimal savingsAmount;

    @Column(name = "is_successful", nullable = false)
    @Builder.Default
    private Boolean isSuccessful = true;

    @Column(name = "failure_reason", length = 500)
    @Size(max = 500, message = "Failure reason must not exceed 500 characters")
    private String failureReason;

    @Column(name = "used_at", nullable = false)
    @NotNull(message = "Used at date is required")
    private LocalDateTime usedAt;

    @Column(name = "ip_address", length = 45)
    @Size(max = 45, message = "IP address must not exceed 45 characters")
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    @Size(max = 500, message = "User agent must not exceed 500 characters")
    private String userAgent;

    @Column(name = "device_type", length = 50)
    @Size(max = 50, message = "Device type must not exceed 50 characters")
    private String deviceType;

    @Column(name = "browser", length = 100)
    @Size(max = 100, message = "Browser must not exceed 100 characters")
    private String browser;

    @Column(name = "operating_system", length = 100)
    @Size(max = 100, message = "Operating system must not exceed 100 characters")
    private String operatingSystem;

    @Column(name = "country", length = 100)
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @Column(name = "city", length = 100)
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Column(name = "referrer", length = 500)
    @Size(max = 500, message = "Referrer must not exceed 500 characters")
    private String referrer;

    @Column(name = "utm_source", length = 100)
    @Size(max = 100, message = "UTM source must not exceed 100 characters")
    private String utmSource;

    @Column(name = "utm_medium", length = 100)
    @Size(max = 100, message = "UTM medium must not exceed 100 characters")
    private String utmMedium;

    @Column(name = "utm_campaign", length = 100)
    @Size(max = 100, message = "UTM campaign must not exceed 100 characters")
    private String utmCampaign;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

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
    @JoinColumn(name = "discount_code_id", insertable = false, updatable = false)
    private DiscountCode discountCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    /**
     * Check if usage is successful
     */
    public boolean isSuccessful() {
        return isSuccessful != null && isSuccessful;
    }

    /**
     * Check if usage is failed
     */
    public boolean isFailed() {
        return !isSuccessful();
    }

    /**
     * Check if usage has failure reason
     */
    public boolean hasFailureReason() {
        return failureReason != null && !failureReason.trim().isEmpty();
    }

    /**
     * Check if usage has IP address
     */
    public boolean hasIpAddress() {
        return ipAddress != null && !ipAddress.trim().isEmpty();
    }

    /**
     * Check if usage has user agent
     */
    public boolean hasUserAgent() {
        return userAgent != null && !userAgent.trim().isEmpty();
    }

    /**
     * Check if usage has device type
     */
    public boolean hasDeviceType() {
        return deviceType != null && !deviceType.trim().isEmpty();
    }

    /**
     * Check if usage has browser
     */
    public boolean hasBrowser() {
        return browser != null && !browser.trim().isEmpty();
    }

    /**
     * Check if usage has operating system
     */
    public boolean hasOperatingSystem() {
        return operatingSystem != null && !operatingSystem.trim().isEmpty();
    }

    /**
     * Check if usage has country
     */
    public boolean hasCountry() {
        return country != null && !country.trim().isEmpty();
    }

    /**
     * Check if usage has city
     */
    public boolean hasCity() {
        return city != null && !city.trim().isEmpty();
    }

    /**
     * Check if usage has referrer
     */
    public boolean hasReferrer() {
        return referrer != null && !referrer.trim().isEmpty();
    }

    /**
     * Check if usage has UTM source
     */
    public boolean hasUtmSource() {
        return utmSource != null && !utmSource.trim().isEmpty();
    }

    /**
     * Check if usage has UTM medium
     */
    public boolean hasUtmMedium() {
        return utmMedium != null && !utmMedium.trim().isEmpty();
    }

    /**
     * Check if usage has UTM campaign
     */
    public boolean hasUtmCampaign() {
        return utmCampaign != null && !utmCampaign.trim().isEmpty();
    }

    /**
     * Check if usage has metadata
     */
    public boolean hasMetadata() {
        return metadata != null && !metadata.trim().isEmpty();
    }

    /**
     * Get location as string
     */
    public String getLocation() {
        StringBuilder location = new StringBuilder();
        
        if (hasCity()) {
            location.append(city);
        }
        
        if (hasCountry()) {
            if (location.length() > 0) location.append(", ");
            location.append(country);
        }
        
        return location.toString();
    }

    /**
     * Get device info as string
     */
    public String getDeviceInfo() {
        StringBuilder device = new StringBuilder();
        
        if (hasDeviceType()) {
            device.append(deviceType);
        }
        
        if (hasOperatingSystem()) {
            if (device.length() > 0) device.append(" - ");
            device.append(operatingSystem);
        }
        
        if (hasBrowser()) {
            if (device.length() > 0) device.append(" - ");
            device.append(browser);
        }
        
        return device.toString();
    }

    /**
     * Get UTM parameters as string
     */
    public String getUtmParameters() {
        StringBuilder utm = new StringBuilder();
        
        if (hasUtmSource()) {
            utm.append("utm_source=").append(utmSource);
        }
        
        if (hasUtmMedium()) {
            if (utm.length() > 0) utm.append("&");
            utm.append("utm_medium=").append(utmMedium);
        }
        
        if (hasUtmCampaign()) {
            if (utm.length() > 0) utm.append("&");
            utm.append("utm_campaign=").append(utmCampaign);
        }
        
        return utm.toString();
    }

    /**
     * Get savings percentage
     */
    public BigDecimal getSavingsPercentage() {
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return discountAmount.divide(orderAmount, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Get effective discount percentage
     */
    public BigDecimal getEffectiveDiscountPercentage() {
        if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return discountAmount.divide(orderAmount, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Get usage summary
     */
    public String getUsageSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Discount: $").append(discountAmount);
        summary.append(" on Order: $").append(orderAmount);
        
        if (isSuccessful()) {
            summary.append(" (Success)");
        } else {
            summary.append(" (Failed)");
        }
        
        return summary.toString();
    }

    /**
     * Check if usage is recent (within last 24 hours)
     */
    public boolean isRecent() {
        return usedAt != null && usedAt.isAfter(LocalDateTime.now().minusHours(24));
    }

    /**
     * Check if usage is today
     */
    public boolean isToday() {
        return usedAt != null && usedAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Get time since used
     */
    public String getTimeSinceUsed() {
        if (usedAt == null) {
            return "Unknown";
        }
        
        long minutes = java.time.Duration.between(usedAt, LocalDateTime.now()).toMinutes();
        
        if (minutes < 1) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + " minute(s) ago";
        } else if (minutes < 1440) {
            return (minutes / 60) + " hour(s) ago";
        } else {
            return (minutes / 1440) + " day(s) ago";
        }
    }

    /**
     * Get days since used
     */
    public long getDaysSinceUsed() {
        if (usedAt == null) {
            return 0;
        }
        return java.time.Duration.between(usedAt, LocalDateTime.now()).toDays();
    }

    /**
     * Check if usage is from mobile device
     */
    public boolean isFromMobile() {
        return hasDeviceType() && 
               (deviceType.toLowerCase().contains("mobile") || 
                deviceType.toLowerCase().contains("phone") || 
                deviceType.toLowerCase().contains("tablet"));
    }

    /**
     * Check if usage is from desktop device
     */
    public boolean isFromDesktop() {
        return hasDeviceType() && 
               deviceType.toLowerCase().contains("desktop");
    }

    /**
     * Check if usage is from tablet device
     */
    public boolean isFromTablet() {
        return hasDeviceType() && 
               deviceType.toLowerCase().contains("tablet");
    }

    /**
     * Check if usage is from phone device
     */
    public boolean isFromPhone() {
        return hasDeviceType() && 
               deviceType.toLowerCase().contains("phone");
    }
}
