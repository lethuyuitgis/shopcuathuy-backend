package com.shopcuathuy.repository;

import com.shopcuathuy.entity.Category;
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
 * Repository interface for Category entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    /**
     * Find category by slug
     */
    Optional<Category> findBySlug(String slug);

    /**
     * Find categories by parent category
     */
    List<Category> findByParentId(String parentId);

    /**
     * Find categories by parent category with pagination
     */
    Page<Category> findByParentId(String parentId, Pageable pageable);

    /**
     * Find root categories (no parent)
     */
    List<Category> findByParentIdIsNull();

    /**
     * Find root categories with pagination
     */
    Page<Category> findByParentIdIsNull(Pageable pageable);

    /**
     * Find root categories ordered by sort order
     */
    List<Category> findByParentIdIsNullOrderBySortOrderAsc();

    /**
     * Find categories by status
     */
    List<Category> findByStatus(Category.CategoryStatus status);

    /**
     * Find categories by status with pagination
     */
    Page<Category> findByStatus(Category.CategoryStatus status, Pageable pageable);

    /**
     * Find categories by parent and status
     */
    List<Category> findByParentIdAndStatus(String parentId, Category.CategoryStatus status);

    /**
     * Find categories by parent and status with pagination
     */
    Page<Category> findByParentIdAndStatus(String parentId, Category.CategoryStatus status, Pageable pageable);

    /**
     * Find categories by name containing
     */
    List<Category> findByNameContainingIgnoreCase(String name);

    /**
     * Find categories by name containing with pagination
     */
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Find categories by description containing
     */
    List<Category> findByDescriptionContainingIgnoreCase(String description);

    /**
     * Find categories by description containing with pagination
     */
    Page<Category> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    /**
     * Find categories by level
     */
    List<Category> findByLevel(Integer level);

    /**
     * Find categories by level with pagination
     */
    Page<Category> findByLevel(Integer level, Pageable pageable);

    /**
     * Find categories by level and status
     */
    List<Category> findByLevelAndStatus(Integer level, Category.CategoryStatus status);

    /**
     * Find categories by level and status with pagination
     */
    Page<Category> findByLevelAndStatus(Integer level, Category.CategoryStatus status, Pageable pageable);

    /**
     * Find categories by sort order
     */
    List<Category> findBySortOrder(Integer sortOrder);

    /**
     * Find categories by sort order with pagination
     */
    Page<Category> findBySortOrder(Integer sortOrder, Pageable pageable);

    /**
     * Find categories by sort order and status
     */
    List<Category> findBySortOrderAndStatus(Integer sortOrder, Category.CategoryStatus status);

    /**
     * Find categories by sort order and status with pagination
     */
    Page<Category> findBySortOrderAndStatus(Integer sortOrder, Category.CategoryStatus status, Pageable pageable);

    /**
     * Find categories by featured status
     */
    List<Category> findByFeaturedTrue();

    /**
     * Find categories by featured status with pagination
     */
    Page<Category> findByFeaturedTrue(Pageable pageable);

    /**
     * Find categories by featured and status
     */
    List<Category> findByFeaturedTrueAndStatus(Category.CategoryStatus status);

    /**
     * Find categories by featured and status with pagination
     */
    Page<Category> findByFeaturedTrueAndStatus(Category.CategoryStatus status, Pageable pageable);

    /**
     * Find categories created after date
     */
    List<Category> findByCreatedAtAfter(LocalDateTime date);

    /**
     * Find categories created after date with pagination
     */
    Page<Category> findByCreatedAtAfter(LocalDateTime date, Pageable pageable);

    /**
     * Find categories created before date
     */
    List<Category> findByCreatedAtBefore(LocalDateTime date);

    /**
     * Find categories created before date with pagination
     */
    Page<Category> findByCreatedAtBefore(LocalDateTime date, Pageable pageable);

    /**
     * Find categories created between dates
     */
    List<Category> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find categories created between dates with pagination
     */
    Page<Category> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find categories by multiple criteria
     */
    @Query("SELECT c FROM Category c WHERE " +
           "(:parentId IS NULL OR c.parentId = :parentId) AND " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:level IS NULL OR c.level = :level) AND " +
           "(:featured IS NULL OR c.featured = :featured) AND " +
           "(:search IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Category> findByMultipleCriteria(@Param("parentId") String parentId,
                                        @Param("status") Category.CategoryStatus status,
                                        @Param("level") Integer level,
                                        @Param("featured") Boolean featured,
                                        @Param("search") String search,
                                        Pageable pageable);

    /**
     * Find categories by text search
     */
    @Query("SELECT c FROM Category c WHERE " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Category> findByTextSearch(@Param("search") String search);

    /**
     * Find categories by text search with pagination
     */
    @Query("SELECT c FROM Category c WHERE " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Category> findByTextSearch(@Param("search") String search, Pageable pageable);

    /**
     * Find categories by multiple statuses
     */
    @Query("SELECT c FROM Category c WHERE " +
           "c.status IN :statuses")
    List<Category> findByStatusIn(@Param("statuses") List<Category.CategoryStatus> statuses);

    /**
     * Find categories by multiple statuses with pagination
     */
    @Query("SELECT c FROM Category c WHERE " +
           "c.status IN :statuses")
    Page<Category> findByStatusIn(@Param("statuses") List<Category.CategoryStatus> statuses, Pageable pageable);

    /**
     * Find categories by multiple levels
     */
    @Query("SELECT c FROM Category c WHERE " +
           "c.level IN :levels")
    List<Category> findByLevelIn(@Param("levels") List<Integer> levels);

    /**
     * Find categories by multiple levels with pagination
     */
    @Query("SELECT c FROM Category c WHERE " +
           "c.level IN :levels")
    Page<Category> findByLevelIn(@Param("levels") List<Integer> levels, Pageable pageable);

    /**
     * Find categories by multiple parent IDs
     */
    @Query("SELECT c FROM Category c WHERE " +
           "c.parentId IN :parentIds")
    List<Category> findByParentIdIn(@Param("parentIds") List<String> parentIds);

    /**
     * Find categories by multiple parent IDs with pagination
     */
    @Query("SELECT c FROM Category c WHERE " +
           "c.parentId IN :parentIds")
    Page<Category> findByParentIdIn(@Param("parentIds") List<String> parentIds, Pageable pageable);

    /**
     * Find recent categories
     */
    @Query("SELECT c FROM Category c WHERE " +
           "c.createdAt >= :date")
    List<Category> findRecentCategories(@Param("date") LocalDateTime date);

    /**
     * Find recent categories with pagination
     */
    @Query("SELECT c FROM Category c WHERE " +
           "c.createdAt >= :date")
    Page<Category> findRecentCategories(@Param("date") LocalDateTime date, Pageable pageable);

    /**
     * Find categories by date range
     */
    @Query("SELECT c FROM Category c WHERE " +
           "c.createdAt BETWEEN :startDate AND :endDate")
    List<Category> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);

    /**
     * Find categories by date range with pagination
     */
    @Query("SELECT c FROM Category c WHERE " +
           "c.createdAt BETWEEN :startDate AND :endDate")
    Page<Category> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate,
                                  Pageable pageable);

    /**
     * Find categories by name starting with
     */
    List<Category> findByNameStartingWithIgnoreCase(String name);

    /**
     * Find categories by name starting with with pagination
     */
    Page<Category> findByNameStartingWithIgnoreCase(String name, Pageable pageable);

    /**
     * Find categories by name ending with
     */
    List<Category> findByNameEndingWithIgnoreCase(String name);

    /**
     * Find categories by name ending with with pagination
     */
    Page<Category> findByNameEndingWithIgnoreCase(String name, Pageable pageable);

    /**
     * Find categories by description starting with
     */
    List<Category> findByDescriptionStartingWithIgnoreCase(String description);

    /**
     * Find categories by description starting with with pagination
     */
    Page<Category> findByDescriptionStartingWithIgnoreCase(String description, Pageable pageable);

    /**
     * Find categories by description ending with
     */
    List<Category> findByDescriptionEndingWithIgnoreCase(String description);

    /**
     * Find categories by description ending with with pagination
     */
    Page<Category> findByDescriptionEndingWithIgnoreCase(String description, Pageable pageable);

    /**
     * Find categories by slug starting with
     */
    List<Category> findBySlugStartingWith(String slug);

    /**
     * Find categories by slug starting with with pagination
     */
    Page<Category> findBySlugStartingWith(String slug, Pageable pageable);

    /**
     * Find categories by slug ending with
     */
    List<Category> findBySlugEndingWith(String slug);

    /**
     * Find categories by slug ending with with pagination
     */
    Page<Category> findBySlugEndingWith(String slug, Pageable pageable);

    /**
     * Find categories by icon containing
     */
    List<Category> findByIconContaining(String icon);

    /**
     * Find categories by icon containing with pagination
     */
    Page<Category> findByIconContaining(String icon, Pageable pageable);

    /**
     * Find categories by image containing
     */
    List<Category> findByImageContaining(String image);

    /**
     * Find categories by image containing with pagination
     */
    Page<Category> findByImageContaining(String image, Pageable pageable);

    /**
     * Find categories by meta title containing
     */
    List<Category> findByMetaTitleContaining(String metaTitle);

    /**
     * Find categories by meta title containing with pagination
     */
    Page<Category> findByMetaTitleContaining(String metaTitle, Pageable pageable);

    /**
     * Find categories by meta description containing
     */
    List<Category> findByMetaDescriptionContaining(String metaDescription);

    /**
     * Find categories by meta description containing with pagination
     */
    Page<Category> findByMetaDescriptionContaining(String metaDescription, Pageable pageable);

    /**
     * Find categories by meta keywords containing
     */
    List<Category> findByMetaKeywordsContaining(String metaKeywords);

    /**
     * Find categories by meta keywords containing with pagination
     */
    Page<Category> findByMetaKeywordsContaining(String metaKeywords, Pageable pageable);

    /**
     * Count categories by parent
     */
    long countByParentId(String parentId);

    /**
     * Count root categories
     */
    long countByParentIdIsNull();

    /**
     * Count categories by status
     */
    long countByStatus(Category.CategoryStatus status);

    /**
     * Count categories by parent and status
     */
    long countByParentIdAndStatus(String parentId, Category.CategoryStatus status);

    /**
     * Count categories by level
     */
    long countByLevel(Integer level);

    /**
     * Count categories by level and status
     */
    long countByLevelAndStatus(Integer level, Category.CategoryStatus status);

    /**
     * Count categories by sort order
     */
    long countBySortOrder(Integer sortOrder);

    /**
     * Count categories by sort order and status
     */
    long countBySortOrderAndStatus(Integer sortOrder, Category.CategoryStatus status);

    /**
     * Count featured categories
     */
    long countByFeaturedTrue();

    /**
     * Count featured categories by status
     */
    long countByFeaturedTrueAndStatus(Category.CategoryStatus status);

    /**
     * Count categories created after date
     */
    long countByCreatedAtAfter(LocalDateTime date);

    /**
     * Count categories created before date
     */
    long countByCreatedAtBefore(LocalDateTime date);

    /**
     * Count categories created between dates
     */
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Check if slug exists
     */
    boolean existsBySlug(String slug);

    /**
     * Check if slug exists excluding category
     */
    boolean existsBySlugAndIdNot(String slug, String id);

    /**
     * Find categories ordered by sort order
     */
    List<Category> findAllByOrderBySortOrderAsc();

    /**
     * Find categories by parent ordered by sort order
     */
    List<Category> findByParentIdOrderBySortOrderAsc(String parentId);

    /**
     * Find categories by status ordered by sort order
     */
    List<Category> findByStatusOrderBySortOrderAsc(Category.CategoryStatus status);

    /**
     * Find categories by parent and status ordered by sort order
     */
    List<Category> findByParentIdAndStatusOrderBySortOrderAsc(String parentId, Category.CategoryStatus status);

    /**
     * Find categories by level ordered by sort order
     */
    List<Category> findByLevelOrderBySortOrderAsc(Integer level);

    /**
     * Find categories by level and status ordered by sort order
     */
    List<Category> findByLevelAndStatusOrderBySortOrderAsc(Integer level, Category.CategoryStatus status);

    /**
     * Find categories by featured ordered by sort order
     */
    List<Category> findByFeaturedTrueOrderBySortOrderAsc();

    /**
     * Find categories by featured and status ordered by sort order
     */
    List<Category> findByFeaturedTrueAndStatusOrderBySortOrderAsc(Category.CategoryStatus status);
}
