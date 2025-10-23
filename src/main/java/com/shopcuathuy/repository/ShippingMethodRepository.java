package com.shopcuathuy.repository;

import com.shopcuathuy.entity.ShippingMethod;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for ShippingMethod entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Long> {

    /**
     * Find shipping methods by seller ID
     */
    List<ShippingMethod> findBySellerId(String sellerId);

    /**
     * Find shipping methods by seller ID with pagination
     */
    Page<ShippingMethod> findBySellerId(String sellerId, Pageable pageable);

    /**
     * Find shipping method by name
     */
    Optional<ShippingMethod> findByName(String name);

    /**
     * Find shipping methods by name containing
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.name LIKE %:name%")
    List<ShippingMethod> findByNameContaining(@Param("name") String name);

    /**
     * Find shipping methods by name containing with pagination
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.name LIKE %:name%")
    Page<ShippingMethod> findByNameContaining(@Param("name") String name, Pageable pageable);

    /**
     * Find shipping methods by cost range
     */
    List<ShippingMethod> findByCostBetween(BigDecimal minCost, BigDecimal maxCost);

    /**
     * Find shipping methods by cost range with pagination
     */
    Page<ShippingMethod> findByCostBetween(BigDecimal minCost, BigDecimal maxCost, Pageable pageable);

    /**
     * Find shipping methods by seller ID and cost range
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.cost BETWEEN :minCost AND :maxCost")
    List<ShippingMethod> findBySellerIdAndCostBetween(@Param("sellerId") String sellerId, @Param("minCost") BigDecimal minCost, @Param("maxCost") BigDecimal maxCost);

    /**
     * Find shipping methods by seller ID and cost range with pagination
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.cost BETWEEN :minCost AND :maxCost")
    Page<ShippingMethod> findBySellerIdAndCostBetween(@Param("sellerId") String sellerId, @Param("minCost") BigDecimal minCost, @Param("maxCost") BigDecimal maxCost, Pageable pageable);

    /**
     * Find shipping methods by seller ID and active status
     */
    List<ShippingMethod> findBySellerIdAndIsActive(String sellerId, Boolean isActive);

    /**
     * Find shipping methods by seller ID and active status with pagination
     */
    Page<ShippingMethod> findBySellerIdAndIsActive(String sellerId, Boolean isActive, Pageable pageable);

    /**
     * Find active shipping methods by seller ID
     */
    List<ShippingMethod> findBySellerIdAndIsActiveTrue(String sellerId);

    /**
     * Find active shipping methods by seller ID with pagination
     */
    Page<ShippingMethod> findBySellerIdAndIsActiveTrue(String sellerId, Pageable pageable);

    /**
     * Find shipping methods by delivery time range
     */
    List<ShippingMethod> findByDeliveryTimeBetween(Integer minDays, Integer maxDays);

    /**
     * Find shipping methods by delivery time range with pagination
     */
    Page<ShippingMethod> findByDeliveryTimeBetween(Integer minDays, Integer maxDays, Pageable pageable);

    /**
     * Find shipping methods by seller ID and delivery time range
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.deliveryTime BETWEEN :minDays AND :maxDays")
    List<ShippingMethod> findBySellerIdAndDeliveryTimeBetween(@Param("sellerId") String sellerId, @Param("minDays") Integer minDays, @Param("maxDays") Integer maxDays);

    /**
     * Find shipping methods by seller ID and delivery time range with pagination
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.deliveryTime BETWEEN :minDays AND :maxDays")
    Page<ShippingMethod> findBySellerIdAndDeliveryTimeBetween(@Param("sellerId") String sellerId, @Param("minDays") Integer minDays, @Param("maxDays") Integer maxDays, Pageable pageable);

    /**
     * Find shipping methods by cost less than or equal to
     */
    List<ShippingMethod> findByCostLessThanEqual(BigDecimal maxCost);

    /**
     * Find shipping methods by cost less than or equal to with pagination
     */
    Page<ShippingMethod> findByCostLessThanEqual(BigDecimal maxCost, Pageable pageable);

    /**
     * Find shipping methods by cost greater than or equal to
     */
    List<ShippingMethod> findByCostGreaterThanEqual(BigDecimal minCost);

    /**
     * Find shipping methods by cost greater than or equal to with pagination
     */
    Page<ShippingMethod> findByCostGreaterThanEqual(BigDecimal minCost, Pageable pageable);

    /**
     * Find shipping methods by delivery time less than or equal to
     */
    List<ShippingMethod> findByDeliveryTimeLessThanEqual(Integer maxDays);

    /**
     * Find shipping methods by delivery time less than or equal to with pagination
     */
    Page<ShippingMethod> findByDeliveryTimeLessThanEqual(Integer maxDays, Pageable pageable);

    /**
     * Find shipping methods by delivery time greater than or equal to
     */
    List<ShippingMethod> findByDeliveryTimeGreaterThanEqual(Integer minDays);

    /**
     * Find shipping methods by delivery time greater than or equal to with pagination
     */
    Page<ShippingMethod> findByDeliveryTimeGreaterThanEqual(Integer minDays, Pageable pageable);

    /**
     * Count shipping methods by seller ID
     */
    long countBySellerId(String sellerId);

    /**
     * Count active shipping methods by seller ID
     */
    long countBySellerIdAndIsActiveTrue(String sellerId);

    /**
     * Count shipping methods by cost range
     */
    long countByCostBetween(BigDecimal minCost, BigDecimal maxCost);

    /**
     * Count shipping methods by delivery time range
     */
    long countByDeliveryTimeBetween(Integer minDays, Integer maxDays);

    /**
     * Find shipping methods by multiple seller IDs
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId IN :sellerIds")
    List<ShippingMethod> findBySellerIdIn(@Param("sellerIds") List<String> sellerIds);

    /**
     * Find shipping methods by multiple seller IDs with pagination
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId IN :sellerIds")
    Page<ShippingMethod> findBySellerIdIn(@Param("sellerIds") List<String> sellerIds, Pageable pageable);

    /**
     * Find shipping methods by seller ID and name containing
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.name LIKE %:name%")
    List<ShippingMethod> findBySellerIdAndNameContaining(@Param("sellerId") String sellerId, @Param("name") String name);

    /**
     * Find shipping methods by seller ID and name containing with pagination
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.name LIKE %:name%")
    Page<ShippingMethod> findBySellerIdAndNameContaining(@Param("sellerId") String sellerId, @Param("name") String name, Pageable pageable);

    /**
     * Find cheapest shipping method by seller ID
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.isActive = true ORDER BY sm.cost ASC")
    List<ShippingMethod> findCheapestBySellerId(@Param("sellerId") String sellerId, Pageable pageable);

    /**
     * Find fastest shipping method by seller ID
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.isActive = true ORDER BY sm.deliveryTime ASC")
    List<ShippingMethod> findFastestBySellerId(@Param("sellerId") String sellerId, Pageable pageable);

    /**
     * Find shipping methods by seller ID and cost less than or equal to
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.cost <= :maxCost")
    List<ShippingMethod> findBySellerIdAndCostLessThanEqual(@Param("sellerId") String sellerId, @Param("maxCost") BigDecimal maxCost);

    /**
     * Find shipping methods by seller ID and cost less than or equal to with pagination
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.cost <= :maxCost")
    Page<ShippingMethod> findBySellerIdAndCostLessThanEqual(@Param("sellerId") String sellerId, @Param("maxCost") BigDecimal maxCost, Pageable pageable);

    /**
     * Find shipping methods by seller ID and delivery time less than or equal to
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.deliveryTime <= :maxDays")
    List<ShippingMethod> findBySellerIdAndDeliveryTimeLessThanEqual(@Param("sellerId") String sellerId, @Param("maxDays") Integer maxDays);

    /**
     * Find shipping methods by seller ID and delivery time less than or equal to with pagination
     */
    @Query("SELECT sm FROM ShippingMethod sm WHERE sm.sellerId = :sellerId AND sm.deliveryTime <= :maxDays")
    Page<ShippingMethod> findBySellerIdAndDeliveryTimeLessThanEqual(@Param("sellerId") String sellerId, @Param("maxDays") Integer maxDays, Pageable pageable);
}
