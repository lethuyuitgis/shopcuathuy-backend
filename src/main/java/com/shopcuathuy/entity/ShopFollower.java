package com.shopcuathuy.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * ShopFollower entity representing shop followers
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "shop_followers", indexes = {
    @Index(name = "idx_shop_follower_user_id", columnList = "user_id"),
    @Index(name = "idx_shop_follower_seller_id", columnList = "seller_id"),
    @Index(name = "idx_shop_follower_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ShopFollower {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "seller_id", nullable = false)
    @NotBlank(message = "Seller ID is required")
    private String sellerId;

    @Column(name = "is_notification_enabled", nullable = false)
    @Builder.Default
    private Boolean isNotificationEnabled = true;

    @Column(name = "notification_types", length = 500)
    @Size(max = 500, message = "Notification types must not exceed 500 characters")
    private String notificationTypes;

    @Column(name = "follow_reason", length = 500)
    @Size(max = 500, message = "Follow reason must not exceed 500 characters")
    private String followReason;

    @Column(name = "is_anonymous", nullable = false)
    @Builder.Default
    private Boolean isAnonymous = false;

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
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private Seller seller;

    /**
     * Check if notification is enabled
     */
    public boolean isNotificationEnabled() {
        return isNotificationEnabled != null && isNotificationEnabled;
    }

    /**
     * Check if follow is anonymous
     */
    public boolean isAnonymous() {
        return isAnonymous != null && isAnonymous;
    }

    /**
     * Check if follow has reason
     */
    public boolean hasFollowReason() {
        return followReason != null && !followReason.trim().isEmpty();
    }

    /**
     * Check if follow has tags
     */
    public boolean hasTags() {
        return tags != null && !tags.trim().isEmpty();
    }

    /**
     * Check if follow has notification types
     */
    public boolean hasNotificationTypes() {
        return notificationTypes != null && !notificationTypes.trim().isEmpty();
    }

    /**
     * Get notification types as array
     */
    public String[] getNotificationTypesArray() {
        if (hasNotificationTypes()) {
            return notificationTypes.split(",");
        }
        return new String[0];
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
     * Add notification type
     */
    public void addNotificationType(String type) {
        if (type != null && !type.trim().isEmpty()) {
            if (hasNotificationTypes()) {
                this.notificationTypes += "," + type.trim();
            } else {
                this.notificationTypes = type.trim();
            }
        }
    }

    /**
     * Remove notification type
     */
    public void removeNotificationType(String type) {
        if (hasNotificationTypes() && type != null) {
            String[] typeArray = getNotificationTypesArray();
            StringBuilder newTypes = new StringBuilder();
            
            for (String t : typeArray) {
                if (!t.trim().equals(type.trim())) {
                    if (newTypes.length() > 0) {
                        newTypes.append(",");
                    }
                    newTypes.append(t.trim());
                }
            }
            
            this.notificationTypes = newTypes.toString();
        }
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
     * Check if follow is recent (within last 30 days)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(30));
    }

    /**
     * Check if follow is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Check if follow is old (older than 1 year)
     */
    public boolean isOld() {
        return createdAt != null && createdAt.isBefore(LocalDateTime.now().minusYears(1));
    }

    /**
     * Get follow summary
     */
    public String getFollowSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Notifications: ").append(isNotificationEnabled() ? "Enabled" : "Disabled");
        
        if (hasNotificationTypes()) {
            summary.append(", Types: ").append(notificationTypes);
        }
        
        if (hasTags()) {
            summary.append(", Tags: ").append(tags);
        }
        
        return summary.toString();
    }

    /**
     * Get days since followed
     */
    public long getDaysSinceFollowed() {
        if (createdAt == null) {
            return 0;
        }
        return java.time.Duration.between(createdAt, LocalDateTime.now()).toDays();
    }

    /**
     * Check if notification type is enabled
     */
    public boolean isNotificationTypeEnabled(String type) {
        if (!hasNotificationTypes()) {
            return false;
        }
        
        String[] types = getNotificationTypesArray();
        for (String t : types) {
            if (t.trim().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get follow duration
     */
    public String getFollowDuration() {
        long days = getDaysSinceFollowed();
        
        if (days < 1) {
            return "Less than a day";
        } else if (days < 7) {
            return days + " day(s)";
        } else if (days < 30) {
            return (days / 7) + " week(s)";
        } else if (days < 365) {
            return (days / 30) + " month(s)";
        } else {
            return (days / 365) + " year(s)";
        }
    }
}
