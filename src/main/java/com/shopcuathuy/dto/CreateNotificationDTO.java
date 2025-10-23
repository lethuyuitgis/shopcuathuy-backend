package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.shopcuathuy.entity.Notification;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Create Notification DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class CreateNotificationDTO {

    @NotNull
    private Long userId;

    @NotNull
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String message;

    @Size(max = 1000)
    private String actionUrl;

    @NotNull
    private Notification.NotificationType type;

    private Notification.NotificationPriority priority = Notification.NotificationPriority.NORMAL;

    @Size(max = 1000)
    private String metadata;

    // Constructors
    public CreateNotificationDTO() {}

    public CreateNotificationDTO(Long userId, String title, String message, String actionUrl,
                               Notification.NotificationType type, Notification.NotificationPriority priority,
                               String metadata) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.actionUrl = actionUrl;
        this.type = type;
        this.priority = priority;
        this.metadata = metadata;
    }

    // Getters and Setters
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

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
}
