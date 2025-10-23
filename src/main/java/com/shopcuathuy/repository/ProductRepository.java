package com.shopcuathuy.repository;

import com.shopcuathuy.entity.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for Product entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    /**
     * Find product by slug
     */
    Optional<Product> findBySlug(String slug);

    /**
     * Find products by seller
     */
    List<Product> findBySellerId(String sellerId);

    /**
     * Find products by seller with pagination
     */
    Page<Product> findBySellerId(String sellerId, Pageable pageable);

    /**
     * Find products by seller ID and created date range
     */
    List<Product> findBySellerIdAndCreatedAtBetween(String sellerId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find products by category
     */
    List<Product> findByCategoryId(String categoryId);

    /**
     * Find products by category with pagination
     */
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);

    /**
     * Find products by status
     */
    List<Product> findByStatus(Product.ProductStatus status);

    /**
     * Find products by status with pagination
     */
    Page<Product> findByStatus(Product.ProductStatus status, Pageable pageable);

    /**
     * Find products by seller and status
     */
    List<Product> findBySellerIdAndStatus(String sellerId, Product.ProductStatus status);

    /**
     * Find products by seller and status with pagination
     */
    Page<Product> findBySellerIdAndStatus(String sellerId, Product.ProductStatus status, Pageable pageable);

    /**
     * Find products by category and status
     */
    List<Product> findByCategoryIdAndStatus(String categoryId, Product.ProductStatus status);

    /**
     * Find products by category and status with pagination
     */
    Page<Product> findByCategoryIdAndStatus(String categoryId, Product.ProductStatus status, Pageable pageable);

    /**
     * Find featured products
     */
    List<Product> findByFeaturedTrue();

    /**
     * Find featured products with pagination
     */
    Page<Product> findByFeaturedTrue(Pageable pageable);

    /**
     * Find featured products by status
     */
    List<Product> findByFeaturedTrueAndStatus(Product.ProductStatus status);

    /**
     * Find featured products by status with pagination
     */
    Page<Product> findByFeaturedTrueAndStatus(Product.ProductStatus status, Pageable pageable);

    /**
     * Find products by name containing
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Find products by name containing with pagination
     */
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Find products by description containing
     */
    List<Product> findByDescriptionContainingIgnoreCase(String description);

    /**
     * Find products by description containing with pagination
     */
    Page<Product> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    /**
     * Find products by brand
     */
    List<Product> findByBrand(String brand);

    /**
     * Find products by brand with pagination
     */
    Page<Product> findByBrand(String brand, Pageable pageable);

    /**
     * Find products by model
     */
    List<Product> findByModel(String model);

    /**
     * Find products by model with pagination
     */
    Page<Product> findByModel(String model, Pageable pageable);

    /**
     * Find products by SKU
     */
    Optional<Product> findBySku(String sku);

    /**
     * Find products by price range
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Find products by price range with pagination
     */
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    /**
     * Find products by price greater than
     */
    List<Product> findByPriceGreaterThan(BigDecimal price);

    /**
     * Find products by price greater than with pagination
     */
    Page<Product> findByPriceGreaterThan(BigDecimal price, Pageable pageable);

    /**
     * Find products by price less than
     */
    List<Product> findByPriceLessThan(BigDecimal price);

    /**
     * Find products by price less than with pagination
     */
    Page<Product> findByPriceLessThan(BigDecimal price, Pageable pageable);

    /**
     * Find products by rating range
     */
    List<Product> findByRatingBetween(BigDecimal minRating, BigDecimal maxRating);

    /**
     * Find products by rating range with pagination
     */
    Page<Product> findByRatingBetween(BigDecimal minRating, BigDecimal maxRating, Pageable pageable);

    /**
     * Find products by rating greater than
     */
    List<Product> findByRatingGreaterThan(BigDecimal rating);

    /**
     * Find products by rating greater than with pagination
     */
    Page<Product> findByRatingGreaterThan(BigDecimal rating, Pageable pageable);

    /**
     * Find products by stock quantity greater than
     */
    List<Product> findByStockQuantityGreaterThan(Integer quantity);

    /**
     * Find products by stock quantity greater than with pagination
     */
    Page<Product> findByStockQuantityGreaterThan(Integer quantity, Pageable pageable);

    /**
     * Find products by stock quantity less than
     */
    List<Product> findByStockQuantityLessThan(Integer quantity);

    /**
     * Find products by stock quantity less than with pagination
     */
    Page<Product> findByStockQuantityLessThan(Integer quantity, Pageable pageable);

    /**
     * Find products by stock quantity between
     */
    List<Product> findByStockQuantityBetween(Integer minQuantity, Integer maxQuantity);

    /**
     * Find products by stock quantity between with pagination
     */
    Page<Product> findByStockQuantityBetween(Integer minQuantity, Integer maxQuantity, Pageable pageable);

    /**
     * Find products by sold count greater than
     */
    List<Product> findBySoldCountGreaterThan(Integer count);

    /**
     * Find products by sold count greater than with pagination
     */
    Page<Product> findBySoldCountGreaterThan(Integer count, Pageable pageable);

    /**
     * Find products by view count greater than
     */
    List<Product> findByViewCountGreaterThan(Integer count);

    /**
     * Find products by view count greater than with pagination
     */
    Page<Product> findByViewCountGreaterThan(Integer count, Pageable pageable);

    /**
     * Find products by review count greater than
     */
    List<Product> findByReviewCountGreaterThan(Integer count);

    /**
     * Find products by review count greater than with pagination
     */
    Page<Product> findByReviewCountGreaterThan(Integer count, Pageable pageable);

    /**
     * Find products by free shipping
     */
    List<Product> findByFreeShippingTrue();

    /**
     * Find products by free shipping with pagination
     */
    Page<Product> findByFreeShippingTrue(Pageable pageable);

    /**
     * Find products by free shipping and status
     */
    List<Product> findByFreeShippingTrueAndStatus(Product.ProductStatus status);

    /**
     * Find products by free shipping and status with pagination
     */
    Page<Product> findByFreeShippingTrueAndStatus(Product.ProductStatus status, Pageable pageable);

    /**
     * Find products by weight range
     */
    List<Product> findByWeightBetween(BigDecimal minWeight, BigDecimal maxWeight);

    /**
     * Find products by weight range with pagination
     */
    Page<Product> findByWeightBetween(BigDecimal minWeight, BigDecimal maxWeight, Pageable pageable);

    /**
     * Find products by warranty period
     */
    List<Product> findByWarrantyPeriod(Integer warrantyPeriod);

    /**
     * Find products by warranty period with pagination
     */
    Page<Product> findByWarrantyPeriod(Integer warrantyPeriod, Pageable pageable);

    /**
     * Find products by warranty type
     */
    List<Product> findByWarrantyType(Product.WarrantyType warrantyType);

    /**
     * Find products by warranty type with pagination
     */
    Page<Product> findByWarrantyType(Product.WarrantyType warrantyType, Pageable pageable);

    /**
     * Find products created after date
     */
    List<Product> findByCreatedAtAfter(LocalDateTime date);

    /**
     * Find products created after date with pagination
     */
    Page<Product> findByCreatedAtAfter(LocalDateTime date, Pageable pageable);

    /**
     * Find products created before date
     */
    List<Product> findByCreatedAtBefore(LocalDateTime date);

    /**
     * Find products created before date with pagination
     */
    Page<Product> findByCreatedAtBefore(LocalDateTime date, Pageable pageable);

    /**
     * Find products created between dates
     */
    List<Product> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find products created between dates with pagination
     */
    Page<Product> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find products by multiple criteria
     */
    @Query("SELECT p FROM Product p WHERE " +
           "(:sellerId IS NULL OR p.sellerId = :sellerId) AND " +
           "(:categoryId IS NULL OR p.categoryId = :categoryId) AND " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:featured IS NULL OR p.featured = :featured) AND " +
           "(:freeShipping IS NULL OR p.freeShipping = :freeShipping) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:minRating IS NULL OR p.rating >= :minRating) AND " +
           "(:minStock IS NULL OR p.stockQuantity >= :minStock) AND " +
           "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Product> findByMultipleCriteria(@Param("sellerId") String sellerId,
                                       @Param("categoryId") String categoryId,
                                       @Param("status") Product.ProductStatus status,
                                       @Param("featured") Boolean featured,
                                       @Param("freeShipping") Boolean freeShipping,
                                       @Param("minPrice") BigDecimal minPrice,
                                       @Param("maxPrice") BigDecimal maxPrice,
                                       @Param("minRating") BigDecimal minRating,
                                       @Param("minStock") Integer minStock,
                                       @Param("search") String search,
                                       Pageable pageable);

    /**
     * Find products by text search
     */
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.model) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Product> findByTextSearch(@Param("search") String search);

    /**
     * Find products by text search with pagination
     */
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.model) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> findByTextSearch(@Param("search") String search, Pageable pageable);

    /**
     * Find products by category and subcategories
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.categoryId IN :categoryIds")
    List<Product> findByCategoryIdIn(@Param("categoryIds") List<String> categoryIds);

    /**
     * Find products by category and subcategories with pagination
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.categoryId IN :categoryIds")
    Page<Product> findByCategoryIdIn(@Param("categoryIds") List<String> categoryIds, Pageable pageable);

    /**
     * Find products by multiple sellers
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.sellerId IN :sellerIds")
    List<Product> findBySellerIdIn(@Param("sellerIds") List<String> sellerIds);

    /**
     * Find products by multiple sellers with pagination
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.sellerId IN :sellerIds")
    Page<Product> findBySellerIdIn(@Param("sellerIds") List<String> sellerIds, Pageable pageable);

    /**
     * Find products by multiple statuses
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.status IN :statuses")
    List<Product> findByStatusIn(@Param("statuses") List<Product.ProductStatus> statuses);

    /**
     * Find products by multiple statuses with pagination
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.status IN :statuses")
    Page<Product> findByStatusIn(@Param("statuses") List<Product.ProductStatus> statuses, Pageable pageable);

    /**
     * Find top selling products
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.status = 'ACTIVE' " +
           "ORDER BY p.soldCount DESC")
    List<Product> findTopSellingProducts(Pageable pageable);

    /**
     * Find most viewed products
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.status = 'ACTIVE' " +
           "ORDER BY p.viewCount DESC")
    List<Product> findMostViewedProducts(Pageable pageable);

    /**
     * Find highest rated products
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.status = 'ACTIVE' AND p.rating > 0 " +
           "ORDER BY p.rating DESC")
    List<Product> findHighestRatedProducts(Pageable pageable);

    /**
     * Find newest products
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.status = 'ACTIVE' " +
           "ORDER BY p.createdAt DESC")
    List<Product> findNewestProducts(Pageable pageable);

    /**
     * Find products on sale
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.status = 'ACTIVE' AND p.originalPrice IS NOT NULL AND p.originalPrice > p.price")
    List<Product> findProductsOnSale();

    /**
     * Find products on sale with pagination
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.status = 'ACTIVE' AND p.originalPrice IS NOT NULL AND p.originalPrice > p.price")
    Page<Product> findProductsOnSale(Pageable pageable);

    /**
     * Find low stock products
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.stockQuantity <= p.minStockAlert")
    List<Product> findLowStockProducts();

    /**
     * Find low stock products with pagination
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.stockQuantity <= p.minStockAlert")
    Page<Product> findLowStockProducts(Pageable pageable);

    /**
     * Find out of stock products
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.stockQuantity = 0")
    List<Product> findOutOfStockProducts();

    /**
     * Find out of stock products with pagination
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.stockQuantity = 0")
    Page<Product> findOutOfStockProducts(Pageable pageable);

    /**
     * Find products by discount percentage
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.originalPrice IS NOT NULL AND p.originalPrice > p.price AND " +
           "((p.originalPrice - p.price) / p.originalPrice * 100) >= :discountPercent")
    List<Product> findByDiscountPercentage(@Param("discountPercent") BigDecimal discountPercent);

    /**
     * Find products by discount percentage with pagination
     */
    @Query("SELECT p FROM Product p WHERE " +
           "p.originalPrice IS NOT NULL AND p.originalPrice > p.price AND " +
           "((p.originalPrice - p.price) / p.originalPrice * 100) >= :discountPercent")
    Page<Product> findByDiscountPercentage(@Param("discountPercent") BigDecimal discountPercent, Pageable pageable);

    /**
     * Count products by seller
     */
    long countBySellerId(String sellerId);

    /**
     * Count products by category
     */
    long countByCategoryId(String categoryId);

    /**
     * Count products by status
     */
    long countByStatus(Product.ProductStatus status);

    /**
     * Count products by seller and status
     */
    long countBySellerIdAndStatus(String sellerId, Product.ProductStatus status);

    /**
     * Count products by category and status
     */
    long countByCategoryIdAndStatus(String categoryId, Product.ProductStatus status);

    /**
     * Count featured products
     */
    long countByFeaturedTrue();

    /**
     * Count featured products by status
     */
    long countByFeaturedTrueAndStatus(Product.ProductStatus status);

    /**
     * Count products by price range
     */
    long countByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Count products by rating range
     */
    long countByRatingBetween(BigDecimal minRating, BigDecimal maxRating);

    /**
     * Count products by stock quantity range
     */
    long countByStockQuantityBetween(Integer minQuantity, Integer maxQuantity);

    /**
     * Count products by free shipping
     */
    long countByFreeShippingTrue();

    /**
     * Count products by free shipping and status
     */
    long countByFreeShippingTrueAndStatus(Product.ProductStatus status);

    /**
     * Count products created after date
     */
    long countByCreatedAtAfter(LocalDateTime date);

    /**
     * Count products created before date
     */
    long countByCreatedAtBefore(LocalDateTime date);

    /**
     * Count products created between dates
     */
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Check if slug exists
     */
    boolean existsBySlug(String slug);

    /**
     * Check if SKU exists
     */
    boolean existsBySku(String sku);

    /**
     * Check if slug exists excluding product
     */
    boolean existsBySlugAndIdNot(String slug, String id);

    /**
     * Check if SKU exists excluding product
     */
    boolean existsBySkuAndIdNot(String sku, String id);

    /**
     * Find products by name starting with
     */
    List<Product> findByNameStartingWithIgnoreCase(String name);

    /**
     * Find products by name starting with with pagination
     */
    Page<Product> findByNameStartingWithIgnoreCase(String name, Pageable pageable);

    /**
     * Find products by name ending with
     */
    List<Product> findByNameEndingWithIgnoreCase(String name);

    /**
     * Find products by name ending with with pagination
     */
    Page<Product> findByNameEndingWithIgnoreCase(String name, Pageable pageable);

    /**
     * Find products by brand starting with
     */
    List<Product> findByBrandStartingWithIgnoreCase(String brand);

    /**
     * Find products by brand starting with with pagination
     */
    Page<Product> findByBrandStartingWithIgnoreCase(String brand, Pageable pageable);

    /**
     * Find products by model starting with
     */
    List<Product> findByModelStartingWithIgnoreCase(String model);

    /**
     * Find products by model starting with with pagination
     */
    Page<Product> findByModelStartingWithIgnoreCase(String model, Pageable pageable);

    /**
     * Find products by weight greater than
     */
    List<Product> findByWeightGreaterThan(BigDecimal weight);

    /**
     * Find products by weight greater than with pagination
     */
    Page<Product> findByWeightGreaterThan(BigDecimal weight, Pageable pageable);

    /**
     * Find products by weight less than
     */
    List<Product> findByWeightLessThan(BigDecimal weight);

    /**
     * Find products by weight less than with pagination
     */
    Page<Product> findByWeightLessThan(BigDecimal weight, Pageable pageable);

    /**
     * Find products by dimensions containing
     */
    List<Product> findByDimensionsContaining(String dimensions);

    /**
     * Find products by dimensions containing with pagination
     */
    Page<Product> findByDimensionsContaining(String dimensions, Pageable pageable);

    /**
     * Find products by warranty period greater than
     */
    List<Product> findByWarrantyPeriodGreaterThan(Integer warrantyPeriod);

    /**
     * Find products by warranty period greater than with pagination
     */
    Page<Product> findByWarrantyPeriodGreaterThan(Integer warrantyPeriod, Pageable pageable);

    /**
     * Find products by warranty period less than
     */
    List<Product> findByWarrantyPeriodLessThan(Integer warrantyPeriod);

    /**
     * Find products by warranty period less than with pagination
     */
    Page<Product> findByWarrantyPeriodLessThan(Integer warrantyPeriod, Pageable pageable);

    /**
     * Find products by warranty period between
     */
    List<Product> findByWarrantyPeriodBetween(Integer minWarranty, Integer maxWarranty);

    /**
     * Find products by warranty period between with pagination
     */
    Page<Product> findByWarrantyPeriodBetween(Integer minWarranty, Integer maxWarranty, Pageable pageable);
}
