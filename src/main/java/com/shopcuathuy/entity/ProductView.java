package com.shopcuathuy.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * ProductView entity representing product views
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "product_views", indexes = {
    @Index(name = "idx_product_view_product_id", columnList = "product_id"),
    @Index(name = "idx_product_view_user_id", columnList = "user_id"),
    @Index(name = "idx_product_view_ip_address", columnList = "ip_address"),
    @Index(name = "idx_product_view_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductView {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_id", nullable = false)
    @NotBlank(message = "Product ID is required")
    private String productId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "ip_address", nullable = false, length = 45)
    @NotBlank(message = "IP address is required")
    @Size(max = 45, message = "IP address must not exceed 45 characters")
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    @Size(max = 500, message = "User agent must not exceed 500 characters")
    private String userAgent;

    @Column(name = "referrer", length = 500)
    @Size(max = 500, message = "Referrer must not exceed 500 characters")
    private String referrer;

    @Column(name = "session_id", length = 100)
    @Size(max = 100, message = "Session ID must not exceed 100 characters")
    private String sessionId;

    @Column(name = "view_duration")
    @Min(value = 0, message = "View duration cannot be negative")
    private Integer viewDuration;

    @Column(name = "page_views")
    @Builder.Default
    @Min(value = 1, message = "Page views must be at least 1")
    private Integer pageViews = 1;

    @Column(name = "is_bounce", nullable = false)
    @Builder.Default
    private Boolean isBounce = false;

    @Column(name = "is_conversion", nullable = false)
    @Builder.Default
    private Boolean isConversion = false;

    @Column(name = "conversion_type", length = 50)
    @Size(max = 50, message = "Conversion type must not exceed 50 characters")
    private String conversionType;

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

    @Column(name = "utm_source", length = 100)
    @Size(max = 100, message = "UTM source must not exceed 100 characters")
    private String utmSource;

    @Column(name = "utm_medium", length = 100)
    @Size(max = 100, message = "UTM medium must not exceed 100 characters")
    private String utmMedium;

    @Column(name = "utm_campaign", length = 100)
    @Size(max = 100, message = "UTM campaign must not exceed 100 characters")
    private String utmCampaign;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    /**
     * Check if view is from a user
     */
    public boolean isUserView() {
        return userId != null && !userId.trim().isEmpty();
    }

    /**
     * Check if view is from a guest
     */
    public boolean isGuestView() {
        return !isUserView();
    }

    /**
     * Check if view is a bounce
     */
    public boolean isBounce() {
        return isBounce != null && isBounce;
    }

    /**
     * Check if view is a conversion
     */
    public boolean isConversion() {
        return isConversion != null && isConversion;
    }

    /**
     * Get view duration in seconds
     */
    public Integer getViewDurationInSeconds() {
        return viewDuration != null ? viewDuration : 0;
    }

    /**
     * Get view duration in minutes
     */
    public Double getViewDurationInMinutes() {
        if (viewDuration != null) {
            return viewDuration / 60.0;
        }
        return 0.0;
    }

    /**
     * Get view duration formatted
     */
    public String getViewDurationFormatted() {
        if (viewDuration == null || viewDuration == 0) {
            return "0s";
        }
        
        if (viewDuration < 60) {
            return viewDuration + "s";
        } else if (viewDuration < 3600) {
            return String.format("%.1fm", viewDuration / 60.0);
        } else {
            return String.format("%.1fh", viewDuration / 3600.0);
        }
    }

    /**
     * Get UTM parameters as string
     */
    public String getUtmParameters() {
        StringBuilder utm = new StringBuilder();
        
        if (utmSource != null && !utmSource.trim().isEmpty()) {
            utm.append("utm_source=").append(utmSource);
        }
        
        if (utmMedium != null && !utmMedium.trim().isEmpty()) {
            if (utm.length() > 0) utm.append("&");
            utm.append("utm_medium=").append(utmMedium);
        }
        
        if (utmCampaign != null && !utmCampaign.trim().isEmpty()) {
            if (utm.length() > 0) utm.append("&");
            utm.append("utm_campaign=").append(utmCampaign);
        }
        
        return utm.toString();
    }

    /**
     * Get location as string
     */
    public String getLocation() {
        StringBuilder location = new StringBuilder();
        
        if (city != null && !city.trim().isEmpty()) {
            location.append(city);
        }
        
        if (country != null && !country.trim().isEmpty()) {
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
        
        if (deviceType != null && !deviceType.trim().isEmpty()) {
            device.append(deviceType);
        }
        
        if (operatingSystem != null && !operatingSystem.trim().isEmpty()) {
            if (device.length() > 0) device.append(" - ");
            device.append(operatingSystem);
        }
        
        if (browser != null && !browser.trim().isEmpty()) {
            if (device.length() > 0) device.append(" - ");
            device.append(browser);
        }
        
        return device.toString();
    }

    /**
     * Check if view is recent (within last 24 hours)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusHours(24));
    }

    /**
     * Check if view is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }
}
