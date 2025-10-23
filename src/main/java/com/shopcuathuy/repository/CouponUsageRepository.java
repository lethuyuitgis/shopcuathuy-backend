package com.shopcuathuy.repository;

import com.shopcuathuy.entity.CouponUsage;
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
 * Coupon Usage Repository
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {

    /**
     * Find coupon usage by coupon ID
     */
    List<CouponUsage> findByCouponId(Long couponId);

    /**
     * Find coupon usage by coupon ID with pagination
     */
    Page<CouponUsage> findByCouponId(Long couponId, Pageable pageable);

    /**
     * Find coupon usage by user ID
     */
    List<CouponUsage> findByUserId(Long userId);

    /**
     * Find coupon usage by user ID with pagination
     */
    Page<CouponUsage> findByUserId(Long userId, Pageable pageable);

    /**
     * Find coupon usage by order ID
     */
    List<CouponUsage> findByOrderId(Long orderId);

    /**
     * Find coupon usage by user ID and coupon ID
     */
    List<CouponUsage> findByUserIdAndCouponId(Long userId, Long couponId);

    /**
     * Count coupon usage by coupon ID
     */
    long countByCouponId(Long couponId);

    /**
     * Count coupon usage by user ID
     */
    long countByUserId(Long userId);

    /**
     * Count coupon usage by user ID and coupon ID
     */
    long countByUserIdAndCouponId(Long userId, Long couponId);

    /**
     * Find coupon usage by date range
     */
    @Query("SELECT cu FROM CouponUsage cu WHERE cu.usedAt BETWEEN :startDate AND :endDate ORDER BY cu.usedAt DESC")
    List<CouponUsage> findByUsedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Find coupon usage by date range with pagination
     */
    @Query("SELECT cu FROM CouponUsage cu WHERE cu.usedAt BETWEEN :startDate AND :endDate ORDER BY cu.usedAt DESC")
    Page<CouponUsage> findByUsedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    /**
     * Get total discount amount by coupon ID
     */
    @Query("SELECT SUM(cu.discountAmount) FROM CouponUsage cu WHERE cu.coupon.id = :couponId")
    java.math.BigDecimal getTotalDiscountByCouponId(@Param("couponId") Long couponId);

    /**
     * Get total discount amount by user ID
     */
    @Query("SELECT SUM(cu.discountAmount) FROM CouponUsage cu WHERE cu.user.id = :userId")
    java.math.BigDecimal getTotalDiscountByUserId(@Param("userId") Long userId);

    /**
     * Get coupon usage statistics by date
     */
    @Query("SELECT DATE(cu.usedAt) as date, COUNT(cu) as count, SUM(cu.discountAmount) as totalDiscount FROM CouponUsage cu WHERE cu.usedAt BETWEEN :startDate AND :endDate GROUP BY DATE(cu.usedAt) ORDER BY date")
    List<Object[]> getCouponUsageStatisticsByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Get most used coupons
     */
    @Query("SELECT cu.coupon.id, cu.coupon.code, cu.coupon.name, COUNT(cu) as usageCount, SUM(cu.discountAmount) as totalDiscount FROM CouponUsage cu GROUP BY cu.coupon.id, cu.coupon.code, cu.coupon.name ORDER BY usageCount DESC")
    List<Object[]> getMostUsedCoupons(Pageable pageable);

    /**
     * Get user coupon usage statistics
     */
    @Query("SELECT cu.user.id, COUNT(cu) as usageCount, SUM(cu.discountAmount) as totalDiscount FROM CouponUsage cu GROUP BY cu.user.id ORDER BY usageCount DESC")
    List<Object[]> getUserCouponUsageStatistics(Pageable pageable);

    /**
     * Check if user has used coupon
     */
    boolean existsByUserIdAndCouponId(Long userId, Long couponId);

    /**
     * Find recent coupon usage
     */
    @Query("SELECT cu FROM CouponUsage cu ORDER BY cu.usedAt DESC")
    List<CouponUsage> findRecentCouponUsage(Pageable pageable);
}
