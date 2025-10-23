package com.shopcuathuy.repository;

import com.shopcuathuy.entity.ProductReview;
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
 * Product Review Repository
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    /**
     * Find reviews by product ID
     */
    List<ProductReview> findByProductIdAndStatus(Long productId, ProductReview.ReviewStatus status);

    /**
     * Find reviews by product ID with pagination
     */
    Page<ProductReview> findByProductIdAndStatus(Long productId, ProductReview.ReviewStatus status, Pageable pageable);

    /**
     * Find reviews by user ID
     */
    List<ProductReview> findByUserId(Long userId);

    /**
     * Find reviews by user ID with pagination
     */
    Page<ProductReview> findByUserId(Long userId, Pageable pageable);

    /**
     * Find review by user ID and product ID
     */
    Optional<ProductReview> findByUserIdAndProductId(Long userId, Long productId);

    /**
     * Find reviews by rating
     */
    List<ProductReview> findByProductIdAndRatingAndStatus(Long productId, Integer rating, ProductReview.ReviewStatus status);

    /**
     * Find reviews by status
     */
    Page<ProductReview> findByStatus(ProductReview.ReviewStatus status, Pageable pageable);

    /**
     * Count reviews by product ID and status
     */
    long countByProductIdAndStatus(Long productId, ProductReview.ReviewStatus status);

    /**
     * Count reviews by product ID and rating
     */
    long countByProductIdAndRatingAndStatus(Long productId, Integer rating, ProductReview.ReviewStatus status);

    /**
     * Calculate average rating by product ID
     */
    @Query("SELECT AVG(r.rating) FROM ProductReview r WHERE r.product.id = :productId AND r.status = :status")
    Double calculateAverageRating(@Param("productId") Long productId, @Param("status") ProductReview.ReviewStatus status);

    /**
     * Find verified purchase reviews
     */
    List<ProductReview> findByProductIdAndIsVerifiedPurchaseTrueAndStatus(Long productId, ProductReview.ReviewStatus status);

    /**
     * Find helpful reviews
     */
    @Query("SELECT r FROM ProductReview r WHERE r.product.id = :productId AND r.status = :status ORDER BY r.helpfulCount DESC")
    List<ProductReview> findMostHelpfulReviews(@Param("productId") Long productId, @Param("status") ProductReview.ReviewStatus status);

    /**
     * Find recent reviews
     */
    @Query("SELECT r FROM ProductReview r WHERE r.product.id = :productId AND r.status = :status ORDER BY r.createdAt DESC")
    List<ProductReview> findRecentReviews(@Param("productId") Long productId, @Param("status") ProductReview.ReviewStatus status);

    /**
     * Find reviews by rating range
     */
    @Query("SELECT r FROM ProductReview r WHERE r.product.id = :productId AND r.rating BETWEEN :minRating AND :maxRating AND r.status = :status")
    List<ProductReview> findByProductIdAndRatingBetweenAndStatus(@Param("productId") Long productId, 
                                                                 @Param("minRating") Integer minRating, 
                                                                 @Param("maxRating") Integer maxRating, 
                                                                 @Param("status") ProductReview.ReviewStatus status);
}
