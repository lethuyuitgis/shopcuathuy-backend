package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * Notification entity representing user notifications
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "notifications", indexes = {
    @Index(name = "idx_notification_user_id", columnList = "user_id"),
    @Index(name = "idx_notification_type", columnList = "type"),
    @Index(name = "idx_notification_status", columnList = "status"),
    @Index(name = "idx_notification_priority", columnList = "priority"),
    @Index(name = "idx_notification_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "title", nullable = false, length = 255)
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Content is required")
    @Size(max = 2000, message = "Content must not exceed 2000 characters")
    private String content;

    @Column(name = "type", nullable = false, length = 50)
    @NotBlank(message = "Type is required")
    @Size(max = 50, message = "Type must not exceed 50 characters")
    private String type;

    @Column(name = "category", length = 50)
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private NotificationStatus status = NotificationStatus.UNREAD;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private NotificationPriority priority = NotificationPriority.NORMAL;

    @Column(name = "action_url", length = 500)
    @Size(max = 500, message = "Action URL must not exceed 500 characters")
    private String actionUrl;

    @Column(name = "action_text", length = 100)
    @Size(max = 100, message = "Action text must not exceed 100 characters")
    private String actionText;

    @Column(name = "image_url", length = 500)
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;

    @Column(name = "icon", length = 100)
    @Size(max = 100, message = "Icon must not exceed 100 characters")
    private String icon;

    @Column(name = "color", length = 20)
    @Size(max = 20, message = "Color must not exceed 20 characters")
    private String color;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "delivery_status", length = 50)
    @Size(max = 50, message = "Delivery status must not exceed 50 characters")
    private String deliveryStatus;

    @Column(name = "delivery_method", length = 50)
    @Size(max = 50, message = "Delivery method must not exceed 50 characters")
    private String deliveryMethod;

    @Column(name = "retry_count", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Retry count cannot be negative")
    private Integer retryCount = 0;

    @Column(name = "max_retries", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Max retries cannot be negative")
    private Integer maxRetries = 3;

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

    /**
     * Notification status enum
     */
    public enum NotificationStatus {
        UNREAD, READ, ARCHIVED, DELETED
    }

    /**
     * Notification priority enum
     */
    public enum NotificationPriority {
        LOW, NORMAL, HIGH, URGENT
    }

    /**
     * Check if notification is unread
     */
    public boolean isUnread() {
        return status == NotificationStatus.UNREAD;
    }

    /**
     * Check if notification is read
     */
    public boolean isRead() {
        return status == NotificationStatus.READ;
    }

    /**
     * Check if notification is archived
     */
    public boolean isArchived() {
        return status == NotificationStatus.ARCHIVED;
    }

    /**
     * Check if notification is deleted
     */
    public boolean isDeleted() {
        return status == NotificationStatus.DELETED;
    }

    /**
     * Check if notification is high priority
     */
    public boolean isHighPriority() {
        return priority == NotificationPriority.HIGH || priority == NotificationPriority.URGENT;
    }

    /**
     * Check if notification is urgent
     */
    public boolean isUrgent() {
        return priority == NotificationPriority.URGENT;
    }

    /**
     * Check if notification has action
     */
    public boolean hasAction() {
        return actionUrl != null && !actionUrl.trim().isEmpty();
    }

    /**
     * Check if notification has image
     */
    public boolean hasImage() {
        return imageUrl != null && !imageUrl.trim().isEmpty();
    }

    /**
     * Check if notification has icon
     */
    public boolean hasIcon() {
        return icon != null && !icon.trim().isEmpty();
    }

    /**
     * Check if notification has color
     */
    public boolean hasColor() {
        return color != null && !color.trim().isEmpty();
    }

    /**
     * Check if notification has metadata
     */
    public boolean hasMetadata() {
        return metadata != null && !metadata.trim().isEmpty();
    }

    /**
     * Check if notification is expired
     */
    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
    }

    /**
     * Check if notification is scheduled
     */
    public boolean isScheduled() {
        return scheduledAt != null && scheduledAt.isAfter(LocalDateTime.now());
    }

    /**
     * Check if notification is sent
     */
    public boolean isSent() {
        return sentAt != null;
    }

    /**
     * Check if notification can be retried
     */
    public boolean canRetry() {
        return retryCount < maxRetries;
    }

    /**
     * Get priority level as string
     */
    public String getPriorityLevel() {
        if (priority == null) {
            return "Normal";
        }
        
        switch (priority) {
            case LOW:
                return "Low";
            case NORMAL:
                return "Normal";
            case HIGH:
                return "High";
            case URGENT:
                return "Urgent";
            default:
                return "Normal";
        }
    }

    /**
     * Get priority color
     */
    public String getPriorityColor() {
        if (priority == null) {
            return "blue";
        }
        
        switch (priority) {
            case LOW:
                return "green";
            case NORMAL:
                return "blue";
            case HIGH:
                return "orange";
            case URGENT:
                return "red";
            default:
                return "blue";
        }
    }

    /**
     * Get type display name
     */
    public String getTypeDisplayName() {
        if (type == null) {
            return "Notification";
        }
        
        switch (type.toLowerCase()) {
            case "order":
                return "Order Update";
            case "payment":
                return "Payment";
            case "promotion":
                return "Promotion";
            case "system":
                return "System";
            case "security":
                return "Security";
            case "marketing":
                return "Marketing";
            default:
                return type;
        }
    }

    /**
     * Get category display name
     */
    public String getCategoryDisplayName() {
        if (category == null) {
            return "General";
        }
        
        switch (category.toLowerCase()) {
            case "general":
                return "General";
            case "order":
                return "Order";
            case "payment":
                return "Payment";
            case "promotion":
                return "Promotion";
            case "system":
                return "System";
            case "security":
                return "Security";
            case "marketing":
                return "Marketing";
            default:
                return category;
        }
    }

    /**
     * Get notification summary
     */
    public String getNotificationSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(getTypeDisplayName());
        
        if (isHighPriority()) {
            summary.append(" (").append(getPriorityLevel()).append(")");
        }
        
        summary.append(": ").append(title);
        
        return summary.toString();
    }

    /**
     * Check if notification is recent (within last 24 hours)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusHours(24));
    }

    /**
     * Check if notification is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Get time since created
     */
    public String getTimeSinceCreated() {
        if (createdAt == null) {
            return "Unknown";
        }
        
        long minutes = java.time.Duration.between(createdAt, LocalDateTime.now()).toMinutes();
        
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
     * Mark as read
     */
    public void markAsRead() {
        this.status = NotificationStatus.READ;
        this.readAt = LocalDateTime.now();
    }

    /**
     * Mark as unread
     */
    public void markAsUnread() {
        this.status = NotificationStatus.UNREAD;
        this.readAt = null;
    }

    /**
     * Archive notification
     */
    public void archive() {
        this.status = NotificationStatus.ARCHIVED;
    }

    /**
     * Delete notification
     */
    public void delete() {
        this.status = NotificationStatus.DELETED;
    }

    /**
     * Increment retry count
     */
    public void incrementRetryCount() {
        this.retryCount++;
    }

    /**
     * Mark as sent
     */
    public void markAsSent() {
        this.sentAt = LocalDateTime.now();
        this.deliveryStatus = "sent";
    }

    /**
     * Mark as failed
     */
    public void markAsFailed() {
        this.deliveryStatus = "failed";
    }
}
