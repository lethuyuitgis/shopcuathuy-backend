package com.shopcuathuy.repository;

import com.shopcuathuy.entity.Wishlist;
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
 * Wishlist Repository
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    /**
     * Find wishlist items by user ID
     */
    List<Wishlist> findByUserIdAndIsActiveTrueOrderByCreatedAtDesc(Long userId);

    /**
     * Find wishlist items by user ID (active only)
     */
    List<Wishlist> findByUserIdAndIsActiveTrue(Long userId);

    /**
     * Find wishlist items by user ID with pagination
     */
    Page<Wishlist> findByUserIdAndIsActiveTrueOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * Find wishlist item by user ID and product ID
     */
    Optional<Wishlist> findByUserIdAndProductIdAndIsActiveTrue(Long userId, Long productId);

    /**
     * Check if product is in user's wishlist
     */
    boolean existsByUserIdAndProductIdAndIsActiveTrue(Long userId, Long productId);

    /**
     * Count wishlist items by user ID
     */
    long countByUserIdAndIsActiveTrue(Long userId);

    /**
     * Find wishlist items by user ID and product IDs
     */
    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND w.product.id IN :productIds AND w.isActive = true")
    List<Wishlist> findByUserIdAndProductIdIn(@Param("userId") Long userId, @Param("productIds") List<Long> productIds);

    /**
     * Delete wishlist items by user ID
     */
    void deleteByUserIdAndIsActiveTrue(Long userId);

    /**
     * Delete wishlist item by user ID and product ID
     */
    void deleteByUserIdAndProductIdAndIsActiveTrue(Long userId, Long productId);

    /**
     * Find most wishlisted products
     */
    @Query("SELECT w.product.id, COUNT(w) as wishlistCount FROM Wishlist w WHERE w.isActive = true GROUP BY w.product.id ORDER BY wishlistCount DESC")
    List<Object[]> findMostWishlistedProducts(Pageable pageable);

    /**
     * Find wishlist items by user ID and product name containing
     */
    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId AND LOWER(w.product.name) LIKE LOWER(CONCAT('%', :productName, '%')) AND w.isActive = true ORDER BY w.createdAt DESC")
    List<Wishlist> findByUserIdAndProductNameContainingIgnoreCase(@Param("userId") Long userId, @Param("productName") String productName);
}
