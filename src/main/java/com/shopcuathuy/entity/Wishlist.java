package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * Wishlist entity representing user wishlist items
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "wishlist", indexes = {
    @Index(name = "idx_wishlist_user_id", columnList = "user_id"),
    @Index(name = "idx_wishlist_product_id", columnList = "product_id"),
    @Index(name = "idx_wishlist_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "product_id", nullable = false)
    @NotBlank(message = "Product ID is required")
    private String productId;

    @Column(name = "notes", length = 500)
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    @Column(name = "priority", nullable = false)
    @Builder.Default
    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 5, message = "Priority must be at most 5")
    private Integer priority = 3;

    @Column(name = "is_notification_enabled", nullable = false)
    @Builder.Default
    private Boolean isNotificationEnabled = true;

    @Column(name = "notification_price_threshold", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Price threshold cannot be negative")
    private java.math.BigDecimal notificationPriceThreshold;

    @Column(name = "is_public", nullable = false)
    @Builder.Default
    private Boolean isPublic = false;

    @Column(name = "tags", length = 500)
    @Size(max = 500, message = "Tags must not exceed 500 characters")
    private String tags;

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

    /**
     * Check if notification is enabled
     */
    public boolean isNotificationEnabled() {
        return isNotificationEnabled != null && isNotificationEnabled;
    }

    /**
     * Check if wishlist is public
     */
    public boolean isPublic() {
        return isPublic != null && isPublic;
    }

    /**
     * Check if item has notes
     */
    public boolean hasNotes() {
        return notes != null && !notes.trim().isEmpty();
    }

    /**
     * Check if item has tags
     */
    public boolean hasTags() {
        return tags != null && !tags.trim().isEmpty();
    }

    /**
     * Check if item has price threshold
     */
    public boolean hasPriceThreshold() {
        return notificationPriceThreshold != null && 
               notificationPriceThreshold.compareTo(java.math.BigDecimal.ZERO) > 0;
    }

    /**
     * Get priority level as string
     */
    public String getPriorityLevel() {
        if (priority == null) {
            return "Medium";
        }
        
        switch (priority) {
            case 1:
                return "Very Low";
            case 2:
                return "Low";
            case 3:
                return "Medium";
            case 4:
                return "High";
            case 5:
                return "Very High";
            default:
                return "Medium";
        }
    }

    /**
     * Get priority stars
     */
    public String getPriorityStars() {
        if (priority == null) {
            return "★★★☆☆";
        }
        
        StringBuilder stars = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            if (i <= priority) {
                stars.append("★");
            } else {
                stars.append("☆");
            }
        }
        return stars.toString();
    }

    /**
     * Get tags as array
     */
    public String[] getTagsArray() {
        if (hasTags()) {
            return tags.split(",");
        }
        return new String[0];
    }

    /**
     * Add tag
     */
    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            if (hasTags()) {
                this.tags += "," + tag.trim();
            } else {
                this.tags = tag.trim();
            }
        }
    }

    /**
     * Remove tag
     */
    public void removeTag(String tag) {
        if (hasTags() && tag != null) {
            String[] tagArray = getTagsArray();
            StringBuilder newTags = new StringBuilder();
            
            for (String t : tagArray) {
                if (!t.trim().equals(tag.trim())) {
                    if (newTags.length() > 0) {
                        newTags.append(",");
                    }
                    newTags.append(t.trim());
                }
            }
            
            this.tags = newTags.toString();
        }
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
     * Check if item is old (older than 1 year)
     */
    public boolean isOld() {
        return createdAt != null && createdAt.isBefore(LocalDateTime.now().minusYears(1));
    }

    /**
     * Get item summary
     */
    public String getItemSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Priority: ").append(getPriorityLevel());
        
        if (hasPriceThreshold()) {
            summary.append(", Price Alert: $").append(notificationPriceThreshold);
        }
        
        if (hasTags()) {
            summary.append(", Tags: ").append(tags);
        }
        
        return summary.toString();
    }

    /**
     * Check if price threshold is met
     */
    public boolean isPriceThresholdMet(java.math.BigDecimal currentPrice) {
        return hasPriceThreshold() && 
               currentPrice.compareTo(notificationPriceThreshold) <= 0;
    }

    /**
     * Get days since added
     */
    public long getDaysSinceAdded() {
        if (createdAt == null) {
            return 0;
        }
        return java.time.Duration.between(createdAt, LocalDateTime.now()).toDays();
    }
}
