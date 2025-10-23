package com.shopcuathuy.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shopcuathuy.entity.Seller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for Seller entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {

    /**
     * Find seller by user ID
     */
    Optional<Seller> findByUserId(String userId);

    /**
     * Find seller by shop slug
     */
    Optional<Seller> findByShopSlug(String shopSlug);

    /**
     * Find sellers by status
     */
    List<Seller> findByStatus(Seller.SellerStatus status);

    /**
     * Find verified sellers
     */
    List<Seller> findByVerifiedTrue();

    /**
     * Find featured sellers
     */
    List<Seller> findByFeaturedTrue();

    /**
     * Find sellers by rating range
     */
    List<Seller> findByRatingBetween(Double minRating, Double maxRating);

    /**
     * Find sellers created after date
     */
    List<Seller> findByCreatedAtAfter(LocalDateTime date);

    /**
     * Find sellers by status with pagination
     */
    Page<Seller> findByStatus(Seller.SellerStatus status, Pageable pageable);

    /**
     * Find verified sellers with pagination
     */
    Page<Seller> findByVerifiedTrue(Pageable pageable);

    /**
     * Find featured sellers with pagination
     */
    Page<Seller> findByFeaturedTrue(Pageable pageable);

    /**
     * Find sellers by rating range with pagination
     */
    Page<Seller> findByRatingBetween(Double minRating, Double maxRating, Pageable pageable);

    /**
     * Find sellers by shop name containing
     */
    @Query("SELECT s FROM Seller s WHERE s.shopName LIKE %:shopName%")
    List<Seller> findByShopNameContaining(@Param("shopName") String shopName);

    /**
     * Find sellers by shop name containing with pagination
     */
    @Query("SELECT s FROM Seller s WHERE s.shopName LIKE %:shopName%")
    Page<Seller> findByShopNameContaining(@Param("shopName") String shopName, Pageable pageable);

    /**
     * Find sellers by status and verified
     */
    List<Seller> findByStatusAndVerified(Seller.SellerStatus status, Boolean verified);

    /**
     * Find sellers by status and verified with pagination
     */
    Page<Seller> findByStatusAndVerified(Seller.SellerStatus status, Boolean verified, Pageable pageable);

    /**
     * Find sellers by status and featured
     */
    List<Seller> findByStatusAndFeatured(Seller.SellerStatus status, Boolean featured);

    /**
     * Find sellers by status and featured with pagination
     */
    Page<Seller> findByStatusAndFeatured(Seller.SellerStatus status, Boolean featured, Pageable pageable);

    /**
     * Count sellers by status
     */
    long countByStatus(Seller.SellerStatus status);

    /**
     * Count verified sellers
     */
    long countByVerifiedTrue();

    /**
     * Count featured sellers
     */
    long countByFeaturedTrue();

    /**
     * Find top rated sellers
     */
    @Query("SELECT s FROM Seller s ORDER BY s.rating DESC")
    List<Seller> findTopRatedSellers(Pageable pageable);

    /**
     * Find recently created sellers
     */
    @Query("SELECT s FROM Seller s ORDER BY s.createdAt DESC")
    List<Seller> findRecentlyCreatedSellers(Pageable pageable);

    /**
     * Find sellers with products count
     */
    @Query("SELECT s FROM Seller s LEFT JOIN s.products p GROUP BY s ORDER BY COUNT(p) DESC")
    List<Seller> findSellersWithProductCount(Pageable pageable);

    /**
     * Find sellers by multiple statuses
     */
    @Query("SELECT s FROM Seller s WHERE s.status IN :statuses")
    List<Seller> findByStatusIn(@Param("statuses") List<Seller.SellerStatus> statuses);

    /**
     * Find sellers by multiple statuses with pagination
     */
    @Query("SELECT s FROM Seller s WHERE s.status IN :statuses")
    Page<Seller> findByStatusIn(@Param("statuses") List<Seller.SellerStatus> statuses, Pageable pageable);

    /**
     * Find sellers created between dates
     */
    @Query("SELECT s FROM Seller s WHERE s.createdAt BETWEEN :startDate AND :endDate")
    List<Seller> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Find sellers created between dates with pagination
     */
    @Query("SELECT s FROM Seller s WHERE s.createdAt BETWEEN :startDate AND :endDate")
    Page<Seller> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    /**
     * Find sellers by rating greater than
     */
    List<Seller> findByRatingGreaterThan(Double rating);

    /**
     * Find sellers by rating greater than with pagination
     */
    Page<Seller> findByRatingGreaterThan(Double rating, Pageable pageable);

    /**
     * Find sellers by rating less than
     */
    List<Seller> findByRatingLessThan(Double rating);

    /**
     * Find sellers by rating less than with pagination
     */
    Page<Seller> findByRatingLessThan(Double rating, Pageable pageable);

    /**
     * Find sellers by status and rating range
     */
    @Query("SELECT s FROM Seller s WHERE s.status = :status AND s.rating BETWEEN :minRating AND :maxRating")
    List<Seller> findByStatusAndRatingBetween(@Param("status") Seller.SellerStatus status, @Param("minRating") Double minRating, @Param("maxRating") Double maxRating);

    /**
     * Find sellers by status and rating range with pagination
     */
    @Query("SELECT s FROM Seller s WHERE s.status = :status AND s.rating BETWEEN :minRating AND :maxRating")
    Page<Seller> findByStatusAndRatingBetween(@Param("status") Seller.SellerStatus status, @Param("minRating") Double minRating, @Param("maxRating") Double maxRating, Pageable pageable);

    /**
     * Find sellers by multiple criteria
     */
    @Query("SELECT s FROM Seller s WHERE s.status = :status AND s.verified = :verified AND s.featured = :featured")
    List<Seller> findByStatusAndVerifiedAndFeatured(@Param("status") Seller.SellerStatus status, @Param("verified") Boolean verified, @Param("featured") Boolean featured);

    /**
     * Find sellers by multiple criteria with pagination
     */
    @Query("SELECT s FROM Seller s WHERE s.status = :status AND s.verified = :verified AND s.featured = :featured")
    Page<Seller> findByStatusAndVerifiedAndFeatured(@Param("status") Seller.SellerStatus status, @Param("verified") Boolean verified, @Param("featured") Boolean featured, Pageable pageable);

    /**
     * Search sellers by shop name
     */
    @Query("SELECT s FROM Seller s WHERE s.shopName LIKE %:query% OR s.description LIKE %:query%")
    List<Seller> searchSellers(@Param("query") String query);

    /**
     * Find top sellers by rating
     */
    @Query("SELECT s FROM Seller s ORDER BY s.rating DESC, s.productsCount DESC")
    List<Seller> findTopSellers(Pageable pageable);

    /**
     * Get sales by date range
     */
    @Query("SELECT DATE(o.createdAt) as date, SUM(oi.price * oi.quantity) as total FROM Order o JOIN OrderItem oi ON o.id = oi.order.id WHERE o.seller.id = :sellerId AND o.createdAt BETWEEN :startDate AND :endDate GROUP BY DATE(o.createdAt)")
    List<Object[]> getSalesByDateRange(@Param("sellerId") String sellerId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Get top products by seller
     */
    @Query("SELECT p FROM Product p WHERE p.seller.id = :sellerId ORDER BY p.soldCount DESC, p.rating DESC")
    List<Object[]> getTopProducts(@Param("sellerId") String sellerId, Pageable pageable);
}
