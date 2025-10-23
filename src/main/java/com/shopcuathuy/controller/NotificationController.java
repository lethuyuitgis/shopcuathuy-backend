package com.shopcuathuy.controller;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopcuathuy.dto.CreateNotificationDTO;
import com.shopcuathuy.dto.NotificationDTO;
import com.shopcuathuy.entity.Notification;
import com.shopcuathuy.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Notification Controller
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification", description = "Notification management APIs")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Create notification
     */
    @PostMapping
    @Operation(summary = "Create notification", description = "Create a new notification")
    public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody CreateNotificationDTO createNotificationDTO) {
        NotificationDTO notification = notificationService.createNotification(createNotificationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }

    /**
     * Get user's notifications
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's notifications", description = "Get all notifications for a user")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get user's notifications with pagination
     */
    @GetMapping("/user/{userId}/paged")
    @Operation(summary = "Get user's notifications with pagination", description = "Get paginated notifications for a user")
    public ResponseEntity<Page<NotificationDTO>> getUserNotifications(
            @Parameter(description = "User ID") @PathVariable Long userId,
            Pageable pageable) {
        Page<NotificationDTO> notifications = notificationService.getUserNotifications(userId, pageable);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get unread notifications
     */
    @GetMapping("/user/{userId}/unread")
    @Operation(summary = "Get unread notifications", description = "Get all unread notifications for a user")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get unread notifications with pagination
     */
    @GetMapping("/user/{userId}/unread/paged")
    @Operation(summary = "Get unread notifications with pagination", description = "Get paginated unread notifications for a user")
    public ResponseEntity<Page<NotificationDTO>> getUnreadNotifications(
            @Parameter(description = "User ID") @PathVariable Long userId,
            Pageable pageable) {
        Page<NotificationDTO> notifications = notificationService.getUnreadNotifications(userId, pageable);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get notifications by type
     */
    @GetMapping("/user/{userId}/type/{type}")
    @Operation(summary = "Get notifications by type", description = "Get notifications by type for a user")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByType(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Notification type") @PathVariable Notification.NotificationType type) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByType(userId, type);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get notifications by priority
     */
    @GetMapping("/user/{userId}/priority/{priority}")
    @Operation(summary = "Get notifications by priority", description = "Get notifications by priority for a user")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByPriority(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Notification priority") @PathVariable Notification.NotificationPriority priority) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByPriority(userId, priority);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Mark notification as read
     */
    @PutMapping("/{notificationId}/read")
    @Operation(summary = "Mark notification as read", description = "Mark a notification as read")
    public ResponseEntity<NotificationDTO> markAsRead(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Notification ID") @PathVariable Long notificationId) {
        NotificationDTO notification = notificationService.markAsRead(userId, notificationId);
        return ResponseEntity.ok(notification);
    }

    /**
     * Mark all notifications as read
     */
    @PutMapping("/user/{userId}/read-all")
    @Operation(summary = "Mark all notifications as read", description = "Mark all notifications as read for a user")
    public ResponseEntity<Void> markAllAsRead(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete notification
     */
    @DeleteMapping("/{notificationId}")
    @Operation(summary = "Delete notification", description = "Delete a notification")
    public ResponseEntity<Void> deleteNotification(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Notification ID") @PathVariable Long notificationId) {
        notificationService.deleteNotification(userId, notificationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete all notifications
     */
    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Delete all notifications", description = "Delete all notifications for a user")
    public ResponseEntity<Void> deleteAllNotifications(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        notificationService.deleteAllNotifications(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get notification count
     */
    @GetMapping("/user/{userId}/count")
    @Operation(summary = "Get notification count", description = "Get total number of unread notifications for a user")
    public ResponseEntity<Long> getNotificationCount(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        long count = notificationService.getNotificationCount(userId);
        return ResponseEntity.ok(count);
    }

    /**
     * Get notification count by type
     */
    @GetMapping("/user/{userId}/count/type/{type}")
    @Operation(summary = "Get notification count by type", description = "Get number of notifications by type for a user")
    public ResponseEntity<Long> getNotificationCountByType(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Notification type") @PathVariable Notification.NotificationType type) {
        long count = notificationService.getNotificationCountByType(userId, type);
        return ResponseEntity.ok(count);
    }

    /**
     * Get notification count by priority
     */
    @GetMapping("/user/{userId}/count/priority/{priority}")
    @Operation(summary = "Get notification count by priority", description = "Get number of notifications by priority for a user")
    public ResponseEntity<Long> getNotificationCountByPriority(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Notification priority") @PathVariable Notification.NotificationPriority priority) {
        long count = notificationService.getNotificationCountByPriority(userId, priority);
        return ResponseEntity.ok(count);
    }

    /**
     * Search notifications
     */
    @GetMapping("/user/{userId}/search")
    @Operation(summary = "Search notifications", description = "Search notifications for a user")
    public ResponseEntity<List<NotificationDTO>> searchNotifications(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Search term") @RequestParam String searchTerm) {
        List<NotificationDTO> notifications = notificationService.searchNotifications(userId, searchTerm);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get notification by ID
     */
    @GetMapping("/{notificationId}")
    @Operation(summary = "Get notification by ID", description = "Get notification details by ID")
    public ResponseEntity<NotificationDTO> getNotificationById(
            @Parameter(description = "Notification ID") @PathVariable Long notificationId) {
        NotificationDTO notification = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(notification);
    }
}
