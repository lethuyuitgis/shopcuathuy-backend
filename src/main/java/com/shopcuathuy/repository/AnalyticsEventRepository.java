package com.shopcuathuy.repository;

import com.shopcuathuy.entity.AnalyticsEvent;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Analytics Event Repository
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface AnalyticsEventRepository extends JpaRepository<AnalyticsEvent, Long> {

    /**
     * Find events by user ID
     */
    List<AnalyticsEvent> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Find events by user ID with pagination
     */
    Page<AnalyticsEvent> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * Find events by event type
     */
    List<AnalyticsEvent> findByEventTypeOrderByCreatedAtDesc(String eventType);

    /**
     * Find events by event type with pagination
     */
    Page<AnalyticsEvent> findByEventTypeOrderByCreatedAtDesc(String eventType, Pageable pageable);

    /**
     * Find events by product ID
     */
    List<AnalyticsEvent> findByProductIdOrderByCreatedAtDesc(Long productId);

    /**
     * Find events by product ID with pagination
     */
    Page<AnalyticsEvent> findByProductIdOrderByCreatedAtDesc(Long productId, Pageable pageable);

    /**
     * Find events by user ID and event type
     */
    List<AnalyticsEvent> findByUserIdAndEventTypeOrderByCreatedAtDesc(Long userId, String eventType);

    /**
     * Find events by date range
     */
    @Query("SELECT e FROM AnalyticsEvent e WHERE e.createdAt BETWEEN :startDate AND :endDate ORDER BY e.createdAt DESC")
    List<AnalyticsEvent> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                              @Param("endDate") LocalDateTime endDate);

    /**
     * Find events by date range with pagination
     */
    @Query("SELECT e FROM AnalyticsEvent e WHERE e.createdAt BETWEEN :startDate AND :endDate ORDER BY e.createdAt DESC")
    Page<AnalyticsEvent> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                              @Param("endDate") LocalDateTime endDate, 
                                              Pageable pageable);

    /**
     * Count events by event type
     */
    long countByEventType(String eventType);

    /**
     * Count events by user ID
     */
    long countByUserId(Long userId);

    /**
     * Count events by product ID
     */
    long countByProductId(Long productId);

    /**
     * Count events by event type and date range
     */
    @Query("SELECT COUNT(e) FROM AnalyticsEvent e WHERE e.eventType = :eventType AND e.createdAt BETWEEN :startDate AND :endDate")
    long countByEventTypeAndCreatedAtBetween(@Param("eventType") String eventType,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    /**
     * Find most popular products
     */
    @Query("SELECT e.product.id, COUNT(e) as eventCount FROM AnalyticsEvent e WHERE e.product.id IS NOT NULL AND e.eventType = 'PRODUCT_VIEW' GROUP BY e.product.id ORDER BY eventCount DESC")
    List<Object[]> findMostPopularProducts(Pageable pageable);

    /**
     * Find most active users
     */
    @Query("SELECT e.user.id, COUNT(e) as eventCount FROM AnalyticsEvent e WHERE e.user.id IS NOT NULL GROUP BY e.user.id ORDER BY eventCount DESC")
    List<Object[]> findMostActiveUsers(Pageable pageable);

    /**
     * Find events by session ID
     */
    List<AnalyticsEvent> findBySessionIdOrderByCreatedAtDesc(String sessionId);

    /**
     * Find events by IP address
     */
    List<AnalyticsEvent> findByIpAddressOrderByCreatedAtDesc(String ipAddress);

    /**
     * Get event statistics by type
     */
    @Query("SELECT e.eventType, COUNT(e) as count FROM AnalyticsEvent e GROUP BY e.eventType ORDER BY count DESC")
    List<Object[]> getEventStatisticsByType();

    /**
     * Get daily event counts
     */
    @Query("SELECT DATE(e.createdAt) as date, COUNT(e) as count FROM AnalyticsEvent e WHERE e.createdAt BETWEEN :startDate AND :endDate GROUP BY DATE(e.createdAt) ORDER BY date")
    List<Object[]> getDailyEventCounts(@Param("startDate") LocalDateTime startDate, 
                                     @Param("endDate") LocalDateTime endDate);

    /**
     * Get hourly event counts
     */
    @Query("SELECT HOUR(e.createdAt) as hour, COUNT(e) as count FROM AnalyticsEvent e WHERE e.createdAt BETWEEN :startDate AND :endDate GROUP BY HOUR(e.createdAt) ORDER BY hour")
    List<Object[]> getHourlyEventCounts(@Param("startDate") LocalDateTime startDate, 
                                       @Param("endDate") LocalDateTime endDate);
}
