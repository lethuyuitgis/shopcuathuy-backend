package com.shopcuathuy.repository;

import com.shopcuathuy.entity.ProductVariant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for ProductVariant entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    /**
     * Find variants by product ID
     */
    List<ProductVariant> findByProductId(String productId);

    /**
     * Find variants by product ID with pagination
     */
    Page<ProductVariant> findByProductId(String productId, Pageable pageable);

    /**
     * Find variant by product ID and variant name
     */
    Optional<ProductVariant> findByProductIdAndVariantName(String productId, String variantName);

    /**
     * Find variants by SKU
     */
    Optional<ProductVariant> findBySku(String sku);

    /**
     * Find variants by price range
     */
    List<ProductVariant> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * Find variants by price range with pagination
     */
    Page<ProductVariant> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    /**
     * Find variants by stock quantity
     */
    List<ProductVariant> findByStockQuantity(Integer stockQuantity);

    /**
     * Find variants by stock quantity with pagination
     */
    Page<ProductVariant> findByStockQuantity(Integer stockQuantity, Pageable pageable);

    /**
     * Find variants with low stock
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.stockQuantity <= :threshold")
    List<ProductVariant> findLowStockVariants(@Param("threshold") Integer threshold);

    /**
     * Find variants with low stock with pagination
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.stockQuantity <= :threshold")
    Page<ProductVariant> findLowStockVariants(@Param("threshold") Integer threshold, Pageable pageable);

    /**
     * Find variants by product ID and price range
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.productId = :productId AND pv.price BETWEEN :minPrice AND :maxPrice")
    List<ProductVariant> findByProductIdAndPriceBetween(@Param("productId") String productId, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    /**
     * Find variants by product ID and price range with pagination
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.productId = :productId AND pv.price BETWEEN :minPrice AND :maxPrice")
    Page<ProductVariant> findByProductIdAndPriceBetween(@Param("productId") String productId, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, Pageable pageable);

    /**
     * Find variants by product ID and stock quantity
     */
    List<ProductVariant> findByProductIdAndStockQuantity(String productId, Integer stockQuantity);

    /**
     * Find variants by product ID and stock quantity with pagination
     */
    Page<ProductVariant> findByProductIdAndStockQuantity(String productId, Integer stockQuantity, Pageable pageable);

    /**
     * Find variants by SKU containing
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.sku LIKE %:sku%")
    List<ProductVariant> findBySkuContaining(@Param("sku") String sku);

    /**
     * Find variants by SKU containing with pagination
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.sku LIKE %:sku%")
    Page<ProductVariant> findBySkuContaining(@Param("sku") String sku, Pageable pageable);

    /**
     * Find variants by variant name containing
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.variantName LIKE %:variantName%")
    List<ProductVariant> findByVariantNameContaining(@Param("variantName") String variantName);

    /**
     * Find variants by variant name containing with pagination
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.variantName LIKE %:variantName%")
    Page<ProductVariant> findByVariantNameContaining(@Param("variantName") String variantName, Pageable pageable);

    /**
     * Count variants by product ID
     */
    long countByProductId(String productId);

    /**
     * Count variants by stock quantity
     */
    long countByStockQuantity(Integer stockQuantity);

    /**
     * Count low stock variants
     */
    @Query("SELECT COUNT(pv) FROM ProductVariant pv WHERE pv.stockQuantity <= :threshold")
    long countLowStockVariants(@Param("threshold") Integer threshold);

    /**
     * Find variants by multiple product IDs
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.productId IN :productIds")
    List<ProductVariant> findByProductIdIn(@Param("productIds") List<String> productIds);

    /**
     * Find variants by multiple product IDs with pagination
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.productId IN :productIds")
    Page<ProductVariant> findByProductIdIn(@Param("productIds") List<String> productIds, Pageable pageable);

    /**
     * Find variants by product ID and active status
     */
    List<ProductVariant> findByProductIdAndIsActive(String productId, Boolean isActive);

    /**
     * Find variants by product ID and active status with pagination
     */
    Page<ProductVariant> findByProductIdAndIsActive(String productId, Boolean isActive, Pageable pageable);

    /**
     * Find active variants by product ID
     */
    List<ProductVariant> findByProductIdAndIsActiveTrue(String productId);

    /**
     * Find active variants by product ID with pagination
     */
    Page<ProductVariant> findByProductIdAndIsActiveTrue(String productId, Pageable pageable);

    /**
     * Find variants by product ID and stock quantity greater than
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.productId = :productId AND pv.stockQuantity > :stockQuantity")
    List<ProductVariant> findByProductIdAndStockQuantityGreaterThan(@Param("productId") String productId, @Param("stockQuantity") Integer stockQuantity);

    /**
     * Find variants by product ID and stock quantity greater than with pagination
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.productId = :productId AND pv.stockQuantity > :stockQuantity")
    Page<ProductVariant> findByProductIdAndStockQuantityGreaterThan(@Param("productId") String productId, @Param("stockQuantity") Integer stockQuantity, Pageable pageable);

    /**
     * Find variants by product ID and stock quantity less than
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.productId = :productId AND pv.stockQuantity < :stockQuantity")
    List<ProductVariant> findByProductIdAndStockQuantityLessThan(@Param("productId") String productId, @Param("stockQuantity") Integer stockQuantity);

    /**
     * Find variants by product ID and stock quantity less than with pagination
     */
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.productId = :productId AND pv.stockQuantity < :stockQuantity")
    Page<ProductVariant> findByProductIdAndStockQuantityLessThan(@Param("productId") String productId, @Param("stockQuantity") Integer stockQuantity, Pageable pageable);
}
