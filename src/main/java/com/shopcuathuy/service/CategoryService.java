package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.shopcuathuy.dto.CategoryDTO;
import com.shopcuathuy.dto.CreateCategoryDTO;
import com.shopcuathuy.dto.UpdateCategoryDTO;
import com.shopcuathuy.entity.Category;
import com.shopcuathuy.exception.DuplicateResourceException;
import com.shopcuathuy.exception.ResourceNotFoundException;
import com.shopcuathuy.exception.ValidationException;
import com.shopcuathuy.mapper.CategoryMapper;
import com.shopcuathuy.repository.CategoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



/**
 * Service class for Category operations
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Create a new category
     */
    @CacheEvict(value = "categories", allEntries = true)
    public CategoryDTO createCategory(CreateCategoryDTO createCategoryDTO) {
        // Check if slug already exists
        if (categoryRepository.existsBySlug(createCategoryDTO.getSlug())) {
            throw new DuplicateResourceException("Slug already exists: " + createCategoryDTO.getSlug());
        }

        // Create category entity
        Category category = categoryMapper.toEntity(createCategoryDTO);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        // Set level based on parent
        if (StringUtils.hasText(category.getParentId())) {
            Category parentCategory = categoryRepository.findById(category.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id: " + category.getParentId()));
            category.setLevel(parentCategory.getLevel() + 1);
        } else {
            category.setLevel(1);
        }

        // Save category
        Category savedCategory = categoryRepository.save(category);

        // Send category created message to RabbitMQ
        messageProducerService.sendCategoryCreatedMessage(categoryMapper.toDTO(savedCategory));

        // Store category data to MinIO
        storeCategoryToMinIO(savedCategory);

        return categoryMapper.toDTO(savedCategory);
    }

    /**
     * Get category by ID
     */
    @Cacheable(value = "categories", key = "#id")
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toDTO(category);
    }

    /**
     * Get category by slug
     */
    @Cacheable(value = "categories", key = "#slug")
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with slug: " + slug));
        return categoryMapper.toDTO(category);
    }

    /**
     * Get all categories with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Get root categories
     */
    @Cacheable(value = "root-categories")
    @Transactional(readOnly = true)
    public List<CategoryDTO> getRootCategories() {
        List<Category> categories = categoryRepository.findByParentIdIsNull();
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Get root categories with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getRootCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findByParentIdIsNull(pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Get categories by parent
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoriesByParent(String parentId) {
        List<Category> categories = categoryRepository.findByParentId(parentId);
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Get categories by parent with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesByParent(String parentId, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByParentId(parentId, pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Get categories by status
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoriesByStatus(Category.CategoryStatus status) {
        List<Category> categories = categoryRepository.findByStatus(status);
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Get categories by status with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesByStatus(Category.CategoryStatus status, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByStatus(status, pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Get categories by level
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoriesByLevel(Integer level) {
        List<Category> categories = categoryRepository.findByLevel(level);
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Get categories by level with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesByLevel(Integer level, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByLevel(level, pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Get categories by level and status
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoriesByLevelAndStatus(Integer level, Category.CategoryStatus status) {
        List<Category> categories = categoryRepository.findByLevelAndStatus(level, status);
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Get categories by level and status with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesByLevelAndStatus(Integer level, Category.CategoryStatus status, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByLevelAndStatus(level, status, pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Get featured categories
     */
    @Cacheable(value = "featured-categories")
    @Transactional(readOnly = true)
    public List<CategoryDTO> getFeaturedCategories() {
        List<Category> categories = categoryRepository.findByFeaturedTrue();
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Get featured categories with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getFeaturedCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findByFeaturedTrue(pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Get featured categories by status
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getFeaturedCategoriesByStatus(Category.CategoryStatus status) {
        List<Category> categories = categoryRepository.findByFeaturedTrueAndStatus(status);
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Get featured categories by status with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getFeaturedCategoriesByStatus(Category.CategoryStatus status, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByFeaturedTrueAndStatus(status, pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Search categories by text
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> searchCategories(String search) {
        List<Category> categories = categoryRepository.findByTextSearch(search);
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Search categories by text with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> searchCategories(String search, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByTextSearch(search, pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Get categories by multiple criteria
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesByCriteria(String parentId, Category.CategoryStatus status,
                                                    Integer level, Boolean featured, String search, Pageable pageable) {
        Page<Category> categories = categoryRepository.findByMultipleCriteria(
            parentId, status, level, featured, search, pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Get category tree (hierarchical structure)
     */
    @Cacheable(value = "category-tree")
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoryTree() {
        List<Category> rootCategories = categoryRepository.findByParentIdIsNullOrderBySortOrderAsc();
        return categoryMapper.toDTOList(rootCategories);
    }

    /**
     * Get category tree by parent
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoryTreeByParent(String parentId) {
        List<Category> categories = categoryRepository.findByParentIdOrderBySortOrderAsc(parentId);
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Get category breadcrumb
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoryBreadcrumb(String categoryId) {
        List<CategoryDTO> breadcrumb = new java.util.ArrayList<>();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        
        // Add current category
        breadcrumb.add(categoryMapper.toDTO(category));
        
        // Add parent categories
        while (StringUtils.hasText(category.getParentId())) {
            String parentId = category.getParentId();
            category = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id: " + parentId));
            breadcrumb.add(0, categoryMapper.toDTO(category));
        }
        
        return breadcrumb;
    }

    /**
     * Get all subcategories
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllSubcategories(String parentId) {
        List<Category> subcategories = categoryRepository.findByParentIdOrderBySortOrderAsc(parentId);
        return categoryMapper.toDTOList(subcategories);
    }

    /**
     * Get categories by sort order
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoriesBySortOrder(Integer sortOrder) {
        List<Category> categories = categoryRepository.findBySortOrder(sortOrder);
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Get categories by sort order with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesBySortOrder(Integer sortOrder, Pageable pageable) {
        Page<Category> categories = categoryRepository.findBySortOrder(sortOrder, pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Get categories by sort order and status
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoriesBySortOrderAndStatus(Integer sortOrder, Category.CategoryStatus status) {
        List<Category> categories = categoryRepository.findBySortOrderAndStatus(sortOrder, status);
        return categoryMapper.toDTOList(categories);
    }

    /**
     * Get categories by sort order and status with pagination
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesBySortOrderAndStatus(Integer sortOrder, Category.CategoryStatus status, Pageable pageable) {
        Page<Category> categories = categoryRepository.findBySortOrderAndStatus(sortOrder, status, pageable);
        return categories.map(categoryMapper::toDTO);
    }

    /**
     * Update category
     */
    @CacheEvict(value = {"categories", "root-categories", "featured-categories", "category-tree"}, allEntries = true)
    public CategoryDTO updateCategory(String id, UpdateCategoryDTO updateCategoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        // Check if slug already exists (excluding current category)
        if (StringUtils.hasText(updateCategoryDTO.getSlug()) && 
            !category.getSlug().equals(updateCategoryDTO.getSlug()) &&
            categoryRepository.existsBySlugAndIdNot(updateCategoryDTO.getSlug(), id)) {
            throw new DuplicateResourceException("Slug already exists: " + updateCategoryDTO.getSlug());
        }

        // Update category fields
        categoryMapper.updateEntity(updateCategoryDTO, category);
        category.setUpdatedAt(LocalDateTime.now());

        // Update level if parent changed
        if (StringUtils.hasText(updateCategoryDTO.getParentId()) && 
            !updateCategoryDTO.getParentId().equals(category.getParentId())) {
            Category parentCategory = categoryRepository.findById(updateCategoryDTO.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found with id: " + updateCategoryDTO.getParentId()));
            category.setLevel(parentCategory.getLevel() + 1);
        } else if (!StringUtils.hasText(updateCategoryDTO.getParentId()) && 
                   StringUtils.hasText(category.getParentId())) {
            category.setLevel(1);
        }

        // Save updated category
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(updatedCategory);
    }

    /**
     * Delete category
     */
    @CacheEvict(value = {"categories", "root-categories", "featured-categories", "category-tree"}, allEntries = true)
    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        // Check if category has subcategories
        long subcategoryCount = categoryRepository.countByParentId(id);
        if (subcategoryCount > 0) {
            throw new ValidationException("Cannot delete category with subcategories. Please delete subcategories first.");
        }
        
        categoryRepository.delete(category);
    }

    /**
     * Activate category
     */
    @CacheEvict(value = {"categories", "root-categories", "featured-categories", "category-tree"}, allEntries = true)
    public CategoryDTO activateCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        category.setStatus(Category.CategoryStatus.ACTIVE);
        category.setUpdatedAt(LocalDateTime.now());
        
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(updatedCategory);
    }

    /**
     * Deactivate category
     */
    @CacheEvict(value = {"categories", "root-categories", "featured-categories", "category-tree"}, allEntries = true)
    public CategoryDTO deactivateCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        category.setStatus(Category.CategoryStatus.INACTIVE);
        category.setUpdatedAt(LocalDateTime.now());
        
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(updatedCategory);
    }

    /**
     * Set category as featured
     */
    @CacheEvict(value = {"categories", "featured-categories"}, allEntries = true)
    public CategoryDTO setFeatured(String id, Boolean featured) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        category.setFeatured(featured);
        category.setUpdatedAt(LocalDateTime.now());
        
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(updatedCategory);
    }

    /**
     * Update category sort order
     */
    @CacheEvict(value = {"categories", "root-categories", "featured-categories", "category-tree"}, allEntries = true)
    public CategoryDTO updateSortOrder(String id, Integer sortOrder) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        category.setSortOrder(sortOrder);
        category.setUpdatedAt(LocalDateTime.now());
        
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(updatedCategory);
    }

    /**
     * Get category count by parent
     */
    @Transactional(readOnly = true)
    public long getCategoryCountByParent(String parentId) {
        return categoryRepository.countByParentId(parentId);
    }

    /**
     * Get root category count
     */
    @Transactional(readOnly = true)
    public long getRootCategoryCount() {
        return categoryRepository.countByParentIdIsNull();
    }

    /**
     * Get category count by status
     */
    @Transactional(readOnly = true)
    public long getCategoryCountByStatus(Category.CategoryStatus status) {
        return categoryRepository.countByStatus(status);
    }

    /**
     * Get category count by level
     */
    @Transactional(readOnly = true)
    public long getCategoryCountByLevel(Integer level) {
        return categoryRepository.countByLevel(level);
    }

    /**
     * Get featured category count
     */
    @Transactional(readOnly = true)
    public long getFeaturedCategoryCount() {
        return categoryRepository.countByFeaturedTrue();
    }

    /**
     * Get category count created after date
     */
    @Transactional(readOnly = true)
    public long getCategoryCountCreatedAfter(LocalDateTime date) {
        return categoryRepository.countByCreatedAtAfter(date);
    }

    /**
     * Get category count created before date
     */
    @Transactional(readOnly = true)
    public long getCategoryCountCreatedBefore(LocalDateTime date) {
        return categoryRepository.countByCreatedAtBefore(date);
    }

    /**
     * Get category count created between dates
     */
    @Transactional(readOnly = true)
    public long getCategoryCountCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return categoryRepository.countByCreatedAtBetween(startDate, endDate);
    }

    /**
     * Check if slug exists
     */
    @Transactional(readOnly = true)
    public boolean slugExists(String slug) {
        return categoryRepository.existsBySlug(slug);
    }

    /**
     * Check if slug exists excluding category
     */
    @Transactional(readOnly = true)
    public boolean slugExistsExcludingCategory(String slug, String categoryId) {
        return categoryRepository.existsBySlugAndIdNot(slug, categoryId);
    }

    /**
     * Export category data
     */
    public String exportCategoryData(String format) {
        List<Category> categories = categoryRepository.findAll();
        
        // Generate export data
        String exportData = generateCategoryExportData(categories, format);
        
        // Store to MinIO
        String fileName = "category-export-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadCategoryExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Store category data to MinIO
     */
    private void storeCategoryToMinIO(Category category) {
        try {
            String categoryData = createCategoryDataJson(category);
            String fileName = "categories/" + category.getStatus() + "/" +
                            category.getCreatedAt().toLocalDate() + "/" +
                            category.getId() + ".json";
            
            fileStorageService.uploadCategoryData(fileName, categoryData);
        } catch (Exception e) {
            System.err.println("Failed to store category data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create category data JSON
     */
    private String createCategoryDataJson(Category category) {
        return String.format(
            "{\"id\":\"%s\",\"name\":\"%s\",\"slug\":\"%s\",\"status\":\"%s\",\"level\":%d,\"createdAt\":\"%s\"}",
            category.getId(),
            category.getName(),
            category.getSlug(),
            category.getStatus(),
            category.getLevel(),
            category.getCreatedAt()
        );
    }

    /**
     * Generate category export data
     */
    private String generateCategoryExportData(List<Category> categories, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateCategoryJsonExport(categories);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateCategoryCsvExport(categories);
        }
        return generateCategoryJsonExport(categories);
    }

    /**
     * Generate JSON export
     */
    private String generateCategoryJsonExport(List<Category> categories) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            json.append(createCategoryDataJson(category));
            if (i < categories.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateCategoryCsvExport(List<Category> categories) {
        StringBuilder csv = new StringBuilder("id,name,slug,status,level,createdAt\n");
        for (Category category : categories) {
            csv.append(String.format("%s,%s,%s,%s,%d,%s\n",
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getStatus(),
                category.getLevel(),
                category.getCreatedAt()
            ));
        }
        return csv.toString();
    }
}
