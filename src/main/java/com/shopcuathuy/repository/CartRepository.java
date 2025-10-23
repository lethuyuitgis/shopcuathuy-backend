package com.shopcuathuy.repository;

import com.shopcuathuy.entity.Cart;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Cart Repository
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    /**
     * Find cart items by user ID
     */
    List<Cart> findByUserIdAndIsActiveTrue(String userId);

    /**
     * Find cart item by user ID and product ID
     */
    Optional<Cart> findByUserIdAndProductIdAndIsActiveTrue(String userId, String productId);

    /**
     * Find cart item by user ID, product ID and variant ID
     */
    Optional<Cart> findByUserIdAndProductIdAndProductVariantIdAndIsActiveTrue(String userId, String productId, String productVariantId);

    /**
     * Count cart items by user ID
     */
    long countByUserIdAndIsActiveTrue(String userId);

    /**
     * Find cart items by user ID with pagination
     */
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.isActive = true ORDER BY c.createdAt DESC")
    List<Cart> findActiveCartItemsByUserId(@Param("userId") String userId);

    /**
     * Calculate total cart value by user ID
     */
    @Query("SELECT COALESCE(SUM(c.totalPrice), 0) FROM Cart c WHERE c.user.id = :userId AND c.isActive = true")
    Double calculateTotalCartValue(@Param("userId") Long userId);

    /**
     * Find cart items by user ID and product IDs
     */
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.product.id IN :productIds AND c.isActive = true")
    List<Cart> findByUserIdAndProductIdIn(@Param("userId") Long userId, @Param("productIds") List<Long> productIds);

    /**
     * Delete cart items by user ID
     */
    void deleteByUserIdAndIsActiveTrue(String userId);

    /**
     * Delete cart item by user ID and product ID
     */
    void deleteByUserIdAndProductIdAndIsActiveTrue(String userId, String productId);

    /**
     * Delete cart item by user ID, product ID and variant ID
     */
    void deleteByUserIdAndProductIdAndProductVariantIdAndIsActiveTrue(String userId, String productId, String productVariantId);
}
