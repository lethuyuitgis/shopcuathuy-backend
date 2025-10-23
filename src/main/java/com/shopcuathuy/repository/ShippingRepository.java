package com.shopcuathuy.repository;

import com.shopcuathuy.entity.Shipping;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Shipping Repository
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {

    /**
     * Find shipping by order ID
     */
    Optional<Shipping> findByOrderId(Long orderId);

    /**
     * Find shipping by tracking number
     */
    Optional<Shipping> findByTrackingNumber(String trackingNumber);

    /**
     * Find shipping by status
     */
    List<Shipping> findByStatus(Shipping.ShippingStatus status);

    /**
     * Find shipping by status with pagination
     */
    Page<Shipping> findByStatus(Shipping.ShippingStatus status, Pageable pageable);

    /**
     * Find shipping by carrier
     */
    List<Shipping> findByCarrier(String carrier);

    /**
     * Find shipping by carrier with pagination
     */
    Page<Shipping> findByCarrier(String carrier, Pageable pageable);

    /**
     * Find shipping by date range
     */
    @Query("SELECT s FROM Shipping s WHERE s.createdAt BETWEEN :startDate AND :endDate ORDER BY s.createdAt DESC")
    List<Shipping> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);

    /**
     * Find shipping by date range with pagination
     */
    @Query("SELECT s FROM Shipping s WHERE s.createdAt BETWEEN :startDate AND :endDate ORDER BY s.createdAt DESC")
    Page<Shipping> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate, 
                                        Pageable pageable);

    /**
     * Find shipping by estimated delivery date
     */
    @Query("SELECT s FROM Shipping s WHERE s.estimatedDeliveryDate BETWEEN :startDate AND :endDate ORDER BY s.estimatedDeliveryDate ASC")
    List<Shipping> findByEstimatedDeliveryDateBetween(@Param("startDate") LocalDateTime startDate, 
                                                    @Param("endDate") LocalDateTime endDate);

    /**
     * Find overdue shipping
     */
    @Query("SELECT s FROM Shipping s WHERE s.estimatedDeliveryDate < :currentDate AND s.status NOT IN ('DELIVERED', 'CANCELLED', 'RETURNED') ORDER BY s.estimatedDeliveryDate ASC")
    List<Shipping> findOverdueShipping(@Param("currentDate") LocalDateTime currentDate);

    /**
     * Find shipping by status and date range
     */
    @Query("SELECT s FROM Shipping s WHERE s.status = :status AND s.createdAt BETWEEN :startDate AND :endDate ORDER BY s.createdAt DESC")
    List<Shipping> findByStatusAndCreatedAtBetween(@Param("status") Shipping.ShippingStatus status,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    /**
     * Count shipping by status
     */
    long countByStatus(Shipping.ShippingStatus status);

    /**
     * Count shipping by carrier
     */
    long countByCarrier(String carrier);

    /**
     * Count shipping by date range
     */
    @Query("SELECT COUNT(s) FROM Shipping s WHERE s.createdAt BETWEEN :startDate AND :endDate")
    long countByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, 
                               @Param("endDate") LocalDateTime endDate);

    /**
     * Find shipping by recipient name
     */
    @Query("SELECT s FROM Shipping s WHERE LOWER(s.recipientName) LIKE LOWER(CONCAT('%', :recipientName, '%')) ORDER BY s.createdAt DESC")
    List<Shipping> findByRecipientNameContainingIgnoreCase(@Param("recipientName") String recipientName);

    /**
     * Find shipping by recipient phone
     */
    List<Shipping> findByRecipientPhone(String recipientPhone);

    /**
     * Find shipping by tracking number containing
     */
    @Query("SELECT s FROM Shipping s WHERE s.trackingNumber LIKE CONCAT('%', :trackingNumber, '%') ORDER BY s.createdAt DESC")
    List<Shipping> findByTrackingNumberContaining(@Param("trackingNumber") String trackingNumber);

    /**
     * Get shipping statistics by status
     */
    @Query("SELECT s.status, COUNT(s) as count FROM Shipping s GROUP BY s.status ORDER BY count DESC")
    List<Object[]> getShippingStatisticsByStatus();

    /**
     * Get shipping statistics by carrier
     */
    @Query("SELECT s.carrier, COUNT(s) as count FROM Shipping s GROUP BY s.carrier ORDER BY count DESC")
    List<Object[]> getShippingStatisticsByCarrier();

    /**
     * Get daily shipping counts
     */
    @Query("SELECT DATE(s.createdAt) as date, COUNT(s) as count FROM Shipping s WHERE s.createdAt BETWEEN :startDate AND :endDate GROUP BY DATE(s.createdAt) ORDER BY date")
    List<Object[]> getDailyShippingCounts(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);
}
