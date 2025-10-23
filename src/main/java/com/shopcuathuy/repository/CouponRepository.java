package com.shopcuathuy.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shopcuathuy.entity.Coupon;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Coupon Repository
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /**
     * Find coupon by code
     */
    Optional<Coupon> findByCode(String code);

    /**
     * Find active coupons
     */
    List<Coupon> findByIsActiveTrueAndIsPublicTrue();

    /**
     * Find active coupons with pagination
     */
    Page<Coupon> findByIsActiveTrueAndIsPublicTrue(Pageable pageable);

    /**
     * Find coupons by type
     */
    List<Coupon> findByType(Coupon.CouponType type);

    /**
     * Find coupons by type with pagination
     */
    Page<Coupon> findByType(Coupon.CouponType type, Pageable pageable);

    /**
     * Find coupons by created by
     */
    List<Coupon> findByCreatedById(Long createdById);

    /**
     * Find coupons by created by with pagination
     */
    Page<Coupon> findByCreatedById(Long createdById, Pageable pageable);

    /**
     * Find valid coupons for current date
     */
    @Query("SELECT c FROM Coupon c WHERE c.isActive = true AND c.isPublic = true AND c.startDate <= :currentDate AND c.endDate >= :currentDate AND c.usedCount < c.usageLimit")
    List<Coupon> findValidCoupons(@Param("currentDate") LocalDateTime currentDate);

    /**
     * Find valid coupons for current date with pagination
     */
    @Query("SELECT c FROM Coupon c WHERE c.isActive = true AND c.isPublic = true AND c.startDate <= :currentDate AND c.endDate >= :currentDate AND c.usedCount < c.usageLimit")
    Page<Coupon> findValidCoupons(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    /**
     * Find coupons by date range
     */
    @Query("SELECT c FROM Coupon c WHERE c.startDate BETWEEN :startDate AND :endDate OR c.endDate BETWEEN :startDate AND :endDate ORDER BY c.startDate ASC")
    List<Coupon> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Find expired coupons
     */
    @Query("SELECT c FROM Coupon c WHERE c.endDate < :currentDate ORDER BY c.endDate DESC")
    List<Coupon> findExpiredCoupons(@Param("currentDate") LocalDateTime currentDate);

    /**
     * Find upcoming coupons
     */
    @Query("SELECT c FROM Coupon c WHERE c.startDate > :currentDate ORDER BY c.startDate ASC")
    List<Coupon> findUpcomingCoupons(@Param("currentDate") LocalDateTime currentDate);

    /**
     * Find coupons by minimum order amount
     */
    @Query("SELECT c FROM Coupon c WHERE c.minimumOrderAmount <= :orderAmount AND c.isActive = true ORDER BY c.minimumOrderAmount DESC")
    List<Coupon> findApplicableCoupons(@Param("orderAmount") java.math.BigDecimal orderAmount);

    /**
     * Find coupons by product ID
     */
    @Query("SELECT c FROM Coupon c WHERE c.applicableProducts LIKE CONCAT('%', :productId, '%') AND c.isActive = true")
    List<Coupon> findByApplicableProduct(@Param("productId") String productId);

    /**
     * Find coupons by category ID
     */
    @Query("SELECT c FROM Coupon c WHERE c.applicableCategories LIKE CONCAT('%', :categoryId, '%') AND c.isActive = true")
    List<Coupon> findByApplicableCategory(@Param("categoryId") String categoryId);

    /**
     * Count coupons by type
     */
    long countByType(Coupon.CouponType type);

    /**
     * Count active coupons
     */
    long countByIsActiveTrue();

    /**
     * Count expired coupons
     */
    @Query("SELECT COUNT(c) FROM Coupon c WHERE c.endDate < :currentDate")
    long countExpiredCoupons(@Param("currentDate") LocalDateTime currentDate);

    /**
     * Get coupon usage statistics
     */
    @Query("SELECT c.type, COUNT(c) as count, SUM(c.usedCount) as totalUsed FROM Coupon c GROUP BY c.type")
    List<Object[]> getCouponUsageStatistics();

    /**
     * Find most used coupons
     */
    @Query("SELECT c FROM Coupon c ORDER BY c.usedCount DESC")
    List<Coupon> findMostUsedCoupons(Pageable pageable);

    /**
     * Find least used coupons
     */
    @Query("SELECT c FROM Coupon c WHERE c.usedCount = 0 ORDER BY c.createdAt DESC")
    List<Coupon> findUnusedCoupons(Pageable pageable);

    /**
     * Search coupons by name or code
     */
    @Query("SELECT c FROM Coupon c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(c.code) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY c.createdAt DESC")
    List<Coupon> searchCoupons(@Param("searchTerm") String searchTerm);

    /**
     * Find coupons expiring soon
     */
    @Query("SELECT c FROM Coupon c WHERE c.endDate BETWEEN :currentDate AND :futureDate ORDER BY c.endDate ASC")
    List<Coupon> findCouponsExpiringSoon(@Param("currentDate") LocalDateTime currentDate, @Param("futureDate") LocalDateTime futureDate);
}
