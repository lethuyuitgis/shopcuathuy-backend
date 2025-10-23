package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopcuathuy.dto.CreateNotificationDTO;
import com.shopcuathuy.dto.NotificationDTO;
import com.shopcuathuy.entity.*;
import com.shopcuathuy.mapper.NotificationMapper;
import com.shopcuathuy.repository.NotificationRepository;
import com.shopcuathuy.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Notification Service
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
public class NotificationService {
    
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;
    private final MessageProducerService messageProducerService;
    private final FileStorageService fileStorageService;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository,
                             NotificationMapper notificationMapper, MessageProducerService messageProducerService,
                             FileStorageService fileStorageService) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.notificationMapper = notificationMapper;
        this.messageProducerService = messageProducerService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Create notification
     */
    @Transactional
    public NotificationDTO createNotification(CreateNotificationDTO createNotificationDTO) {
        // Validate user
        User user = userRepository.findById(String.valueOf(createNotificationDTO.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create notification
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(createNotificationDTO.getTitle());
        notification.setMessage(createNotificationDTO.getMessage());
        notification.setActionUrl(createNotificationDTO.getActionUrl());
        notification.setType(createNotificationDTO.getType());
        notification.setPriority(createNotificationDTO.getPriority());
        notification.setMetadata(createNotificationDTO.getMetadata());
        notification.setIsRead(false);
        notification.setIsSent(false);

        notification = notificationRepository.save(notification);

        // Send notification created message
        messageProducerService.sendNotificationCreated(notification);

        // Store notification data to MinIO
        storeNotificationToMinIO(notification);

        return notificationMapper.toDTO(notification);
    }

    /**
     * Get user's notifications
     */
    public List<NotificationDTO> getUserNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return notificationMapper.toDTOList(notifications);
    }

    /**
     * Get user's notifications with pagination
     */
    public Page<NotificationDTO> getUserNotifications(Long userId, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return notifications.map(notificationMapper::toDTO);
    }

    /**
     * Get unread notifications
     */
    public List<NotificationDTO> getUnreadNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        return notificationMapper.toDTOList(notifications);
    }

    /**
     * Get unread notifications with pagination
     */
    public Page<NotificationDTO> getUnreadNotifications(Long userId, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId, pageable);
        return notifications.map(notificationMapper::toDTO);
    }

    /**
     * Get notifications by type
     */
    public List<NotificationDTO> getNotificationsByType(Long userId, Notification.NotificationType type) {
        List<Notification> notifications = notificationRepository.findByUserIdAndTypeOrderByCreatedAtDesc(userId, type);
        return notificationMapper.toDTOList(notifications);
    }

    /**
     * Get notifications by priority
     */
    public List<NotificationDTO> getNotificationsByPriority(Long userId, Notification.NotificationPriority priority) {
        List<Notification> notifications = notificationRepository.findByUserIdAndPriorityOrderByCreatedAtDesc(userId, priority);
        return notificationMapper.toDTOList(notifications);
    }

    /**
     * Mark notification as read
     */
    @Transactional
    public NotificationDTO markAsRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to notification");
        }

        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());
        notification = notificationRepository.save(notification);

        return notificationMapper.toDTO(notification);
    }

    /**
     * Mark all notifications as read
     */
    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        LocalDateTime now = LocalDateTime.now();
        
        for (Notification notification : unreadNotifications) {
            notification.setIsRead(true);
            notification.setReadAt(now);
        }
        
        notificationRepository.saveAll(unreadNotifications);
    }

    /**
     * Delete notification
     */
    @Transactional
    public void deleteNotification(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to notification");
        }

        notificationRepository.delete(notification);
    }

    /**
     * Delete all notifications
     */
    @Transactional
    public void deleteAllNotifications(Long userId) {
        notificationRepository.deleteByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Get notification count
     */
    public long getNotificationCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    /**
     * Get notification count by type
     */
    public long getNotificationCountByType(Long userId, Notification.NotificationType type) {
        return notificationRepository.countByUserIdAndType(userId, type);
    }

    /**
     * Get notification count by priority
     */
    public long getNotificationCountByPriority(Long userId, Notification.NotificationPriority priority) {
        return notificationRepository.countByUserIdAndPriority(userId, priority);
    }

    /**
     * Search notifications
     */
    public List<NotificationDTO> searchNotifications(Long userId, String searchTerm) {
        List<Notification> notifications = notificationRepository.findByUserIdAndSearchTerm(userId, searchTerm);
        return notificationMapper.toDTOList(notifications);
    }

    /**
     * Get notification by ID
     */
    public NotificationDTO getNotificationById(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return notificationMapper.toDTO(notification);
    }

    /**
     * Clean up old notifications
     */
    @Transactional
    public void cleanupOldNotifications(int daysToKeep) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
        notificationRepository.deleteOldNotifications(cutoffDate);
    }

    /**
     * Export notification data
     */
    public String exportNotificationData(Long userId, LocalDateTime startDate, LocalDateTime endDate, String format) {
        List<Notification> notifications = notificationRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate);
        
        // Generate export data
        String exportData = generateNotificationExportData(notifications, format);
        
        // Store to MinIO
        String fileName = "notification-export-" + userId + "-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadNotificationExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Store notification data to MinIO
     */
    private void storeNotificationToMinIO(Notification notification) {
        try {
            String notificationData = createNotificationDataJson(notification);
            String fileName = "notifications/" + notification.getType() + "/" +
                            notification.getCreatedAt().toLocalDate() + "/" +
                            notification.getId() + ".json";
            
            fileStorageService.uploadNotificationData(fileName, notificationData);
        } catch (Exception e) {
            System.err.println("Failed to store notification data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create notification data JSON
     */
    private String createNotificationDataJson(Notification notification) {
        return String.format(
            "{\"id\":%d,\"userId\":%d,\"title\":\"%s\",\"message\":\"%s\",\"type\":\"%s\",\"createdAt\":\"%s\"}",
            notification.getId(),
            notification.getUser().getId(),
            notification.getTitle(),
            notification.getMessage(),
            notification.getType(),
            notification.getCreatedAt()
        );
    }

    /**
     * Generate notification export data
     */
    private String generateNotificationExportData(List<Notification> notifications, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateNotificationJsonExport(notifications);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateNotificationCsvExport(notifications);
        }
        return generateNotificationJsonExport(notifications);
    }

    /**
     * Generate JSON export
     */
    private String generateNotificationJsonExport(List<Notification> notifications) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < notifications.size(); i++) {
            Notification notification = notifications.get(i);
            json.append(createNotificationDataJson(notification));
            if (i < notifications.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateNotificationCsvExport(List<Notification> notifications) {
        StringBuilder csv = new StringBuilder("id,userId,title,message,type,createdAt\n");
        for (Notification notification : notifications) {
            csv.append(String.format("%d,%d,%s,%s,%s,%s\n",
                notification.getId(),
                notification.getUser().getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getCreatedAt()
            ));
        }
        return csv.toString();
    }

    /**
     * Send order created notification
     */
    public void sendOrderCreatedNotification(String userId, String orderId) {
        // Implementation for sending order created notification
        log.info("Sending order created notification to user: {} for order: {}", userId, orderId);
    }

    /**
     * Send order status update notification
     */
    public void sendOrderStatusUpdateNotification(String userId, String orderId, String status) {
        // Implementation for sending order status update notification
        log.info("Sending order status update notification to user: {} for order: {} with status: {}", userId, orderId, status);
    }

    /**
     * Send order cancelled notification
     */
    public void sendOrderCancelledNotification(String userId, String orderId) {
        // Implementation for sending order cancelled notification
        log.info("Sending order cancelled notification to user: {} for order: {}", userId, orderId);
    }

    /**
     * Send order shipped notification
     */
    public void sendOrderShippedNotification(String userId, String orderId, String trackingNumber) {
        // Implementation for sending order shipped notification
        log.info("Sending order shipped notification to user: {} for order: {} with tracking: {}", userId, orderId, trackingNumber);
    }

    /**
     * Send order delivered notification
     */
    public void sendOrderDeliveredNotification(String userId, String orderId) {
        // Implementation for sending order delivered notification
        log.info("Sending order delivered notification to user: {} for order: {}", userId, orderId);
    }

    /**
     * Send low stock notification
     */
    public void sendLowStockNotification(String sellerId, String productId, Integer stockLevel) {
        // Implementation for sending low stock notification
        log.info("Sending low stock notification to seller: {} for product: {} with stock: {}", sellerId, productId, stockLevel);
    }

    /**
     * Send notification
     */
    public void sendNotification(String userId, String title, String message, String type) {
        // Implementation for sending general notification
        log.info("Sending notification to user: {} with title: {} and message: {} of type: {}", userId, title, message, type);
    }
}