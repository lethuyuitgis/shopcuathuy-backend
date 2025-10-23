package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * Message entity representing messages between users
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "messages", indexes = {
    @Index(name = "idx_message_sender_id", columnList = "sender_id"),
    @Index(name = "idx_message_receiver_id", columnList = "receiver_id"),
    @Index(name = "idx_message_type", columnList = "type"),
    @Index(name = "idx_message_status", columnList = "status"),
    @Index(name = "idx_message_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "sender_id", nullable = false)
    @NotBlank(message = "Sender ID is required")
    private String senderId;

    @Column(name = "receiver_id", nullable = false)
    @NotBlank(message = "Receiver ID is required")
    private String receiverId;

    @Column(name = "subject", length = 255)
    @Size(max = 255, message = "Subject must not exceed 255 characters")
    private String subject;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Content is required")
    @Size(max = 5000, message = "Content must not exceed 5000 characters")
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
    private MessageStatus status = MessageStatus.SENT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MessagePriority priority = MessagePriority.NORMAL;

    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private Boolean isRead = false;

    @Column(name = "is_important", nullable = false)
    @Builder.Default
    private Boolean isImportant = false;

    @Column(name = "is_encrypted", nullable = false)
    @Builder.Default
    private Boolean isEncrypted = false;

    @Column(name = "attachment_url", length = 500)
    @Size(max = 500, message = "Attachment URL must not exceed 500 characters")
    private String attachmentUrl;

    @Column(name = "attachment_name", length = 255)
    @Size(max = 255, message = "Attachment name must not exceed 255 characters")
    private String attachmentName;

    @Column(name = "attachment_size")
    @Min(value = 0, message = "Attachment size cannot be negative")
    private Long attachmentSize;

    @Column(name = "attachment_type", length = 100)
    @Size(max = 100, message = "Attachment type must not exceed 100 characters")
    private String attachmentType;

    @Column(name = "reply_to_id")
    private String replyToId;

    @Column(name = "thread_id")
    private String threadId;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

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
    @JoinColumn(name = "sender_id", insertable = false, updatable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", insertable = false, updatable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to_id", insertable = false, updatable = false)
    private Message replyTo;

    @OneToMany(mappedBy = "replyTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> replies = new ArrayList<>();

    /**
     * Message status enum
     */
    public enum MessageStatus {
        DRAFT, SENT, DELIVERED, READ, FAILED, CANCELLED
    }

    /**
     * Message priority enum
     */
    public enum MessagePriority {
        LOW, NORMAL, HIGH, URGENT
    }

    /**
     * Check if message is draft
     */
    public boolean isDraft() {
        return status == MessageStatus.DRAFT;
    }

    /**
     * Check if message is sent
     */
    public boolean isSent() {
        return status == MessageStatus.SENT;
    }

    /**
     * Check if message is delivered
     */
    public boolean isDelivered() {
        return status == MessageStatus.DELIVERED;
    }

    /**
     * Check if message is read
     */
    public boolean isRead() {
        return isRead != null && isRead;
    }

    /**
     * Check if message is failed
     */
    public boolean isFailed() {
        return status == MessageStatus.FAILED;
    }

    /**
     * Check if message is cancelled
     */
    public boolean isCancelled() {
        return status == MessageStatus.CANCELLED;
    }

    /**
     * Check if message is important
     */
    public boolean isImportant() {
        return isImportant != null && isImportant;
    }

    /**
     * Check if message is encrypted
     */
    public boolean isEncrypted() {
        return isEncrypted != null && isEncrypted;
    }

    /**
     * Check if message has attachment
     */
    public boolean hasAttachment() {
        return attachmentUrl != null && !attachmentUrl.trim().isEmpty();
    }

    /**
     * Check if message has subject
     */
    public boolean hasSubject() {
        return subject != null && !subject.trim().isEmpty();
    }

    /**
     * Check if message has metadata
     */
    public boolean hasMetadata() {
        return metadata != null && !metadata.trim().isEmpty();
    }

    /**
     * Check if message is a reply
     */
    public boolean isReply() {
        return replyToId != null && !replyToId.trim().isEmpty();
    }

    /**
     * Check if message is part of a thread
     */
    public boolean isPartOfThread() {
        return threadId != null && !threadId.trim().isEmpty();
    }

    /**
     * Check if message is expired
     */
    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
    }

    /**
     * Check if message is scheduled
     */
    public boolean isScheduled() {
        return scheduledAt != null && scheduledAt.isAfter(LocalDateTime.now());
    }

    /**
     * Check if message can be retried
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
            return "Message";
        }
        
        switch (type.toLowerCase()) {
            case "text":
                return "Text Message";
            case "email":
                return "Email";
            case "system":
                return "System Message";
            case "notification":
                return "Notification";
            case "support":
                return "Support";
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
            case "support":
                return "Support";
            case "marketing":
                return "Marketing";
            case "system":
                return "System";
            default:
                return category;
        }
    }

    /**
     * Get message summary
     */
    public String getMessageSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(getTypeDisplayName());
        
        if (isImportant()) {
            summary.append(" (Important)");
        }
        
        if (hasSubject()) {
            summary.append(": ").append(subject);
        } else {
            String content = this.content;
            if (content.length() > 50) {
                content = content.substring(0, 50) + "...";
            }
            summary.append(": ").append(content);
        }
        
        return summary.toString();
    }

    /**
     * Get attachment size formatted
     */
    public String getAttachmentSizeFormatted() {
        if (attachmentSize == null) {
            return null;
        }
        
        if (attachmentSize < 1024) {
            return attachmentSize + " B";
        } else if (attachmentSize < 1024 * 1024) {
            return String.format("%.1f KB", attachmentSize / 1024.0);
        } else if (attachmentSize < 1024 * 1024 * 1024) {
            return String.format("%.1f MB", attachmentSize / (1024.0 * 1024.0));
        } else {
            return String.format("%.1f GB", attachmentSize / (1024.0 * 1024.0 * 1024.0));
        }
    }

    /**
     * Check if message is recent (within last 24 hours)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusHours(24));
    }

    /**
     * Check if message is today
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
        this.isRead = true;
        this.readAt = LocalDateTime.now();
        this.status = MessageStatus.READ;
    }

    /**
     * Mark as unread
     */
    public void markAsUnread() {
        this.isRead = false;
        this.readAt = null;
        this.status = MessageStatus.DELIVERED;
    }

    /**
     * Mark as delivered
     */
    public void markAsDelivered() {
        this.status = MessageStatus.DELIVERED;
        this.deliveredAt = LocalDateTime.now();
    }

    /**
     * Mark as failed
     */
    public void markAsFailed() {
        this.status = MessageStatus.FAILED;
    }

    /**
     * Cancel message
     */
    public void cancel() {
        this.status = MessageStatus.CANCELLED;
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
        this.status = MessageStatus.SENT;
        this.deliveryStatus = "sent";
    }
}
