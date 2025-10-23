package com.shopcuathuy.controller;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopcuathuy.dto.CategoryDTO;
import com.shopcuathuy.dto.CreateCategoryDTO;
import com.shopcuathuy.dto.UpdateCategoryDTO;
import com.shopcuathuy.entity.Category;
import com.shopcuathuy.exception.DuplicateResourceException;
import com.shopcuathuy.exception.ResourceNotFoundException;
import com.shopcuathuy.exception.ValidationException;
import com.shopcuathuy.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST Controller for Category operations
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category Management", description = "APIs for managing categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Create a new category
     */
    @PostMapping
    @Operation(summary = "Create a new category", description = "Create a new category with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Category created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Slug already exists")
    })
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CreateCategoryDTO createCategoryDTO) {
        try {
            CategoryDTO category = categoryService.createCategory(createCategoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        } catch (DuplicateResourceException e) {
            throw e;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create category: " + e.getMessage(), e);
        }
    }

    /**
     * Get category by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieve a category by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category found"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String id) {
        try {
            CategoryDTO category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get category: " + e.getMessage(), e);
        }
    }

    /**
     * Get category by slug
     */
    @GetMapping("/slug/{slug}")
    @Operation(summary = "Get category by slug", description = "Retrieve a category by its slug")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category found"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> getCategoryBySlug(@PathVariable String slug) {
        try {
            CategoryDTO category = categoryService.getCategoryBySlug(slug);
            return ResponseEntity.ok(category);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get category: " + e.getMessage(), e);
        }
    }

    /**
     * Get all categories with pagination
     */
    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve all categories with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(Pageable pageable) {
        try {
            Page<CategoryDTO> categories = categoryService.getAllCategories(pageable);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get categories: " + e.getMessage(), e);
        }
    }

    /**
     * Get root categories
     */
    @GetMapping("/root")
    @Operation(summary = "Get root categories", description = "Retrieve root categories (no parent)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Root categories retrieved successfully")
    })
    public ResponseEntity<List<CategoryDTO>> getRootCategories() {
        try {
            List<CategoryDTO> categories = categoryService.getRootCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get root categories: " + e.getMessage(), e);
        }
    }

    /**
     * Get root categories with pagination
     */
    @GetMapping("/root/paged")
    @Operation(summary = "Get root categories with pagination", description = "Retrieve root categories with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Root categories retrieved successfully")
    })
    public ResponseEntity<Page<CategoryDTO>> getRootCategories(Pageable pageable) {
        try {
            Page<CategoryDTO> categories = categoryService.getRootCategories(pageable);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get root categories: " + e.getMessage(), e);
        }
    }

    /**
     * Get categories by parent
     */
    @GetMapping("/parent/{parentId}")
    @Operation(summary = "Get categories by parent", description = "Retrieve categories by parent ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<List<CategoryDTO>> getCategoriesByParent(@PathVariable String parentId) {
        try {
            List<CategoryDTO> categories = categoryService.getCategoriesByParent(parentId);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get categories by parent: " + e.getMessage(), e);
        }
    }

    /**
     * Get categories by parent with pagination
     */
    @GetMapping("/parent/{parentId}/paged")
    @Operation(summary = "Get categories by parent with pagination", description = "Retrieve categories by parent ID with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<Page<CategoryDTO>> getCategoriesByParent(@PathVariable String parentId, Pageable pageable) {
        try {
            Page<CategoryDTO> categories = categoryService.getCategoriesByParent(parentId, pageable);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get categories by parent: " + e.getMessage(), e);
        }
    }

    /**
     * Get categories by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get categories by status", description = "Retrieve categories by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<List<CategoryDTO>> getCategoriesByStatus(@PathVariable Category.CategoryStatus status) {
        try {
            List<CategoryDTO> categories = categoryService.getCategoriesByStatus(status);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get categories by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get categories by status with pagination
     */
    @GetMapping("/status/{status}/paged")
    @Operation(summary = "Get categories by status with pagination", description = "Retrieve categories by status with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<Page<CategoryDTO>> getCategoriesByStatus(@PathVariable Category.CategoryStatus status, Pageable pageable) {
        try {
            Page<CategoryDTO> categories = categoryService.getCategoriesByStatus(status, pageable);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get categories by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get categories by level
     */
    @GetMapping("/level/{level}")
    @Operation(summary = "Get categories by level", description = "Retrieve categories by level")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<List<CategoryDTO>> getCategoriesByLevel(@PathVariable Integer level) {
        try {
            List<CategoryDTO> categories = categoryService.getCategoriesByLevel(level);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get categories by level: " + e.getMessage(), e);
        }
    }

    /**
     * Get categories by level with pagination
     */
    @GetMapping("/level/{level}/paged")
    @Operation(summary = "Get categories by level with pagination", description = "Retrieve categories by level with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<Page<CategoryDTO>> getCategoriesByLevel(@PathVariable Integer level, Pageable pageable) {
        try {
            Page<CategoryDTO> categories = categoryService.getCategoriesByLevel(level, pageable);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get categories by level: " + e.getMessage(), e);
        }
    }

    /**
     * Get categories by level and status
     */
    @GetMapping("/level/{level}/status/{status}")
    @Operation(summary = "Get categories by level and status", description = "Retrieve categories by level and status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<List<CategoryDTO>> getCategoriesByLevelAndStatus(@PathVariable Integer level, @PathVariable Category.CategoryStatus status) {
        try {
            List<CategoryDTO> categories = categoryService.getCategoriesByLevelAndStatus(level, status);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get categories by level and status: " + e.getMessage(), e);
        }
    }

    /**
     * Get categories by level and status with pagination
     */
    @GetMapping("/level/{level}/status/{status}/paged")
    @Operation(summary = "Get categories by level and status with pagination", description = "Retrieve categories by level and status with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<Page<CategoryDTO>> getCategoriesByLevelAndStatus(@PathVariable Integer level, @PathVariable Category.CategoryStatus status, Pageable pageable) {
        try {
            Page<CategoryDTO> categories = categoryService.getCategoriesByLevelAndStatus(level, status, pageable);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get categories by level and status: " + e.getMessage(), e);
        }
    }

    /**
     * Get featured categories
     */
    @GetMapping("/featured")
    @Operation(summary = "Get featured categories", description = "Retrieve featured categories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Featured categories retrieved successfully")
    })
    public ResponseEntity<List<CategoryDTO>> getFeaturedCategories() {
        try {
            List<CategoryDTO> categories = categoryService.getFeaturedCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get featured categories: " + e.getMessage(), e);
        }
    }

    /**
     * Get featured categories with pagination
     */
    @GetMapping("/featured/paged")
    @Operation(summary = "Get featured categories with pagination", description = "Retrieve featured categories with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Featured categories retrieved successfully")
    })
    public ResponseEntity<Page<CategoryDTO>> getFeaturedCategories(Pageable pageable) {
        try {
            Page<CategoryDTO> categories = categoryService.getFeaturedCategories(pageable);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get featured categories: " + e.getMessage(), e);
        }
    }

    /**
     * Get featured categories by status
     */
    @GetMapping("/featured/status/{status}")
    @Operation(summary = "Get featured categories by status", description = "Retrieve featured categories by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Featured categories retrieved successfully")
    })
    public ResponseEntity<List<CategoryDTO>> getFeaturedCategoriesByStatus(@PathVariable Category.CategoryStatus status) {
        try {
            List<CategoryDTO> categories = categoryService.getFeaturedCategoriesByStatus(status);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get featured categories by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get featured categories by status with pagination
     */
    @GetMapping("/featured/status/{status}/paged")
    @Operation(summary = "Get featured categories by status with pagination", description = "Retrieve featured categories by status with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Featured categories retrieved successfully")
    })
    public ResponseEntity<Page<CategoryDTO>> getFeaturedCategoriesByStatus(@PathVariable Category.CategoryStatus status, Pageable pageable) {
        try {
            Page<CategoryDTO> categories = categoryService.getFeaturedCategoriesByStatus(status, pageable);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get featured categories by status: " + e.getMessage(), e);
        }
    }

    /**
     * Search categories
     */
    @GetMapping("/search")
    @Operation(summary = "Search categories", description = "Search categories by text")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories found")
    })
    public ResponseEntity<List<CategoryDTO>> searchCategories(@RequestParam String search) {
        try {
            List<CategoryDTO> categories = categoryService.searchCategories(search);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search categories: " + e.getMessage(), e);
        }
    }

    /**
     * Search categories with pagination
     */
    @GetMapping("/search/paged")
    @Operation(summary = "Search categories with pagination", description = "Search categories by text with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories found")
    })
    public ResponseEntity<Page<CategoryDTO>> searchCategories(@RequestParam String search, Pageable pageable) {
        try {
            Page<CategoryDTO> categories = categoryService.searchCategories(search, pageable);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search categories: " + e.getMessage(), e);
        }
    }

    /**
     * Get categories by multiple criteria
     */
    @GetMapping("/criteria")
    @Operation(summary = "Get categories by criteria", description = "Retrieve categories filtered by multiple criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<Page<CategoryDTO>> getCategoriesByCriteria(
            @RequestParam(required = false) String parentId,
            @RequestParam(required = false) Category.CategoryStatus status,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false) String search,
            Pageable pageable) {
        try {
            Page<CategoryDTO> categories = categoryService.getCategoriesByCriteria(
                parentId, status, level, featured, search, pageable);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get categories by criteria: " + e.getMessage(), e);
        }
    }

    /**
     * Get category tree
     */
    @GetMapping("/tree")
    @Operation(summary = "Get category tree", description = "Retrieve hierarchical category structure")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category tree retrieved successfully")
    })
    public ResponseEntity<List<CategoryDTO>> getCategoryTree() {
        try {
            List<CategoryDTO> categories = categoryService.getCategoryTree();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get category tree: " + e.getMessage(), e);
        }
    }

    /**
     * Get category tree by parent
     */
    @GetMapping("/tree/parent/{parentId}")
    @Operation(summary = "Get category tree by parent", description = "Retrieve hierarchical category structure by parent")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category tree retrieved successfully")
    })
    public ResponseEntity<List<CategoryDTO>> getCategoryTreeByParent(@PathVariable String parentId) {
        try {
            List<CategoryDTO> categories = categoryService.getCategoryTreeByParent(parentId);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get category tree by parent: " + e.getMessage(), e);
        }
    }

    /**
     * Get category breadcrumb
     */
    @GetMapping("/{id}/breadcrumb")
    @Operation(summary = "Get category breadcrumb", description = "Retrieve category breadcrumb path")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category breadcrumb retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<List<CategoryDTO>> getCategoryBreadcrumb(@PathVariable String id) {
        try {
            List<CategoryDTO> breadcrumb = categoryService.getCategoryBreadcrumb(id);
            return ResponseEntity.ok(breadcrumb);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get category breadcrumb: " + e.getMessage(), e);
        }
    }

    /**
     * Get all subcategories
     */
    @GetMapping("/{id}/subcategories")
    @Operation(summary = "Get all subcategories", description = "Retrieve all subcategories of a category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subcategories retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<List<CategoryDTO>> getAllSubcategories(@PathVariable String id) {
        try {
            List<CategoryDTO> subcategories = categoryService.getAllSubcategories(id);
            return ResponseEntity.ok(subcategories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get subcategories: " + e.getMessage(), e);
        }
    }

    /**
     * Update category
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Update an existing category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category updated successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Slug already exists")
    })
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String id, @Valid @RequestBody UpdateCategoryDTO updateCategoryDTO) {
        try {
            CategoryDTO category = categoryService.updateCategory(id, updateCategoryDTO);
            return ResponseEntity.ok(category);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (DuplicateResourceException e) {
            throw e;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update category: " + e.getMessage(), e);
        }
    }

    /**
     * Delete category
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Delete a category by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "400", description = "Cannot delete category with subcategories")
    })
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete category: " + e.getMessage(), e);
        }
    }

    /**
     * Activate category
     */
    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate category", description = "Activate a category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category activated successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> activateCategory(@PathVariable String id) {
        try {
            CategoryDTO category = categoryService.activateCategory(id);
            return ResponseEntity.ok(category);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to activate category: " + e.getMessage(), e);
        }
    }

    /**
     * Deactivate category
     */
    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate category", description = "Deactivate a category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> deactivateCategory(@PathVariable String id) {
        try {
            CategoryDTO category = categoryService.deactivateCategory(id);
            return ResponseEntity.ok(category);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to deactivate category: " + e.getMessage(), e);
        }
    }

    /**
     * Set category as featured
     */
    @PatchMapping("/{id}/featured")
    @Operation(summary = "Set category as featured", description = "Set or unset a category as featured")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category featured status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> setFeatured(@PathVariable String id, @RequestBody Map<String, Boolean> request) {
        try {
            Boolean featured = request.get("featured");
            CategoryDTO category = categoryService.setFeatured(id, featured);
            return ResponseEntity.ok(category);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to set category as featured: " + e.getMessage(), e);
        }
    }

    /**
     * Update category sort order
     */
    @PatchMapping("/{id}/sort-order")
    @Operation(summary = "Update category sort order", description = "Update category sort order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category sort order updated successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> updateSortOrder(@PathVariable String id, @RequestBody Map<String, Integer> request) {
        try {
            Integer sortOrder = request.get("sortOrder");
            CategoryDTO category = categoryService.updateSortOrder(id, sortOrder);
            return ResponseEntity.ok(category);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update category sort order: " + e.getMessage(), e);
        }
    }

    /**
     * Get category count by parent
     */
    @GetMapping("/count/parent/{parentId}")
    @Operation(summary = "Get category count by parent", description = "Get the total number of categories by parent")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category count retrieved successfully")
    })
    public ResponseEntity<Long> getCategoryCountByParent(@PathVariable String parentId) {
        try {
            long count = categoryService.getCategoryCountByParent(parentId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get category count by parent: " + e.getMessage(), e);
        }
    }

    /**
     * Get root category count
     */
    @GetMapping("/count/root")
    @Operation(summary = "Get root category count", description = "Get the total number of root categories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category count retrieved successfully")
    })
    public ResponseEntity<Long> getRootCategoryCount() {
        try {
            long count = categoryService.getRootCategoryCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get root category count: " + e.getMessage(), e);
        }
    }

    /**
     * Get category count by status
     */
    @GetMapping("/count/status/{status}")
    @Operation(summary = "Get category count by status", description = "Get the total number of categories by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category count retrieved successfully")
    })
    public ResponseEntity<Long> getCategoryCountByStatus(@PathVariable Category.CategoryStatus status) {
        try {
            long count = categoryService.getCategoryCountByStatus(status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get category count by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get category count by level
     */
    @GetMapping("/count/level/{level}")
    @Operation(summary = "Get category count by level", description = "Get the total number of categories by level")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category count retrieved successfully")
    })
    public ResponseEntity<Long> getCategoryCountByLevel(@PathVariable Integer level) {
        try {
            long count = categoryService.getCategoryCountByLevel(level);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get category count by level: " + e.getMessage(), e);
        }
    }

    /**
     * Get featured category count
     */
    @GetMapping("/count/featured")
    @Operation(summary = "Get featured category count", description = "Get the total number of featured categories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category count retrieved successfully")
    })
    public ResponseEntity<Long> getFeaturedCategoryCount() {
        try {
            long count = categoryService.getFeaturedCategoryCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get featured category count: " + e.getMessage(), e);
        }
    }

    /**
     * Check if slug exists
     */
    @GetMapping("/exists/slug/{slug}")
    @Operation(summary = "Check if slug exists", description = "Check if a slug is already in use")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Slug existence checked successfully")
    })
    public ResponseEntity<Boolean> slugExists(@PathVariable String slug) {
        try {
            boolean exists = categoryService.slugExists(slug);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check slug existence: " + e.getMessage(), e);
        }
    }
}
