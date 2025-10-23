package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.shopcuathuy.entity.Notification;
import java.time.LocalDateTime;

/**
 * Notification DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class NotificationDTO {

    private Long id;
    private Long userId;
    private String title;
    private String message;
    private String actionUrl;
    private Notification.NotificationType type;
    private Notification.NotificationPriority priority;
    private Boolean isRead;
    private Boolean isSent;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private String metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public NotificationDTO() {}

    public NotificationDTO(Long id, Long userId, String title, String message, String actionUrl,
                          Notification.NotificationType type, Notification.NotificationPriority priority,
                          Boolean isRead, Boolean isSent, LocalDateTime sentAt, LocalDateTime readAt,
                          String metadata, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.actionUrl = actionUrl;
        this.type = type;
        this.priority = priority;
        this.isRead = isRead;
        this.isSent = isSent;
        this.sentAt = sentAt;
        this.readAt = readAt;
        this.metadata = metadata;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getActionUrl() { return actionUrl; }
    public void setActionUrl(String actionUrl) { this.actionUrl = actionUrl; }

    public Notification.NotificationType getType() { return type; }
    public void setType(Notification.NotificationType type) { this.type = type; }

    public Notification.NotificationPriority getPriority() { return priority; }
    public void setPriority(Notification.NotificationPriority priority) { this.priority = priority; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public Boolean getIsSent() { return isSent; }
    public void setIsSent(Boolean isSent) { this.isSent = isSent; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public LocalDateTime getReadAt() { return readAt; }
    public void setReadAt(LocalDateTime readAt) { this.readAt = readAt; }

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
