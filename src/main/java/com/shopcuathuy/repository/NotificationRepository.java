package com.shopcuathuy.repository;

import com.shopcuathuy.entity.Notification;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Notification Repository
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Find notifications by user ID
     */
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Delete notifications by user ID
     */
    void deleteByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Find notifications by user ID with pagination
     */
    Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * Find unread notifications by user ID
     */
    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);

    /**
     * Find unread notifications by user ID with pagination
     */
    Page<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * Find notifications by user ID and type
     */
    List<Notification> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, Notification.NotificationType type);

    /**
     * Find notifications by user ID and priority
     */
    List<Notification> findByUserIdAndPriorityOrderByCreatedAtDesc(Long userId, Notification.NotificationPriority priority);

    /**
     * Find notifications by user ID and read status
     */
    List<Notification> findByUserIdAndIsReadOrderByCreatedAtDesc(Long userId, Boolean isRead);

    /**
     * Find notifications by user ID and read status with pagination
     */
    Page<Notification> findByUserIdAndIsReadOrderByCreatedAtDesc(Long userId, Boolean isRead, Pageable pageable);

    /**
     * Count unread notifications by user ID
     */
    long countByUserIdAndIsReadFalse(Long userId);

    /**
     * Count notifications by user ID and type
     */
    long countByUserIdAndType(Long userId, Notification.NotificationType type);

    /**
     * Count notifications by user ID and priority
     */
    long countByUserIdAndPriority(Long userId, Notification.NotificationPriority priority);

    /**
     * Find notifications by user ID and date range
     */
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.createdAt BETWEEN :startDate AND :endDate ORDER BY n.createdAt DESC")
    List<Notification> findByUserIdAndCreatedAtBetween(@Param("userId") Long userId, 
                                                      @Param("startDate") LocalDateTime startDate, 
                                                      @Param("endDate") LocalDateTime endDate);

    /**
     * Find unsent notifications
     */
    List<Notification> findByIsSentFalseOrderByCreatedAtAsc();

    /**
     * Find notifications by user ID and type with pagination
     */
    Page<Notification> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, Notification.NotificationType type, Pageable pageable);

    /**
     * Find notifications by user ID and priority with pagination
     */
    Page<Notification> findByUserIdAndPriorityOrderByCreatedAtDesc(Long userId, Notification.NotificationPriority priority, Pageable pageable);

    /**
     * Delete old notifications
     */
    @Query("DELETE FROM Notification n WHERE n.createdAt < :cutoffDate")
    void deleteOldNotifications(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Find notifications by user ID and search term
     */
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND (LOWER(n.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(n.message) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) ORDER BY n.createdAt DESC")
    List<Notification> findByUserIdAndSearchTerm(@Param("userId") Long userId, @Param("searchTerm") String searchTerm);
}
