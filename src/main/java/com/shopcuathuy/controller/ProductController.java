package com.shopcuathuy.controller;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import com.shopcuathuy.dto.CreateProductDTO;
import com.shopcuathuy.dto.ProductDTO;
import com.shopcuathuy.dto.UpdateProductDTO;
import com.shopcuathuy.entity.Product;
import com.shopcuathuy.exception.DuplicateResourceException;
import com.shopcuathuy.exception.ResourceNotFoundException;
import com.shopcuathuy.exception.ValidationException;
import com.shopcuathuy.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;




/**
 * REST Controller for Product operations
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Create a new product
     */
    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "SKU or slug already exists")
    })
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductDTO createProductDTO) {
        try {
            ProductDTO product = productService.createProduct(createProductDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (DuplicateResourceException e) {
            throw e;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product: " + e.getMessage(), e);
        }
    }

    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a product by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        try {
            ProductDTO product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product: " + e.getMessage(), e);
        }
    }

    /**
     * Get product by slug
     */
    @GetMapping("/slug/{slug}")
    @Operation(summary = "Get product by slug", description = "Retrieve a product by its slug")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> getProductBySlug(@PathVariable String slug) {
        try {
            ProductDTO product = productService.getProductBySlug(slug);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product: " + e.getMessage(), e);
        }
    }

    /**
     * Get all products with pagination
     */
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve all products with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        try {
            Page<ProductDTO> products = productService.getAllProducts(pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products: " + e.getMessage(), e);
        }
    }

    /**
     * Get products by seller
     */
    @GetMapping("/seller/{sellerId}")
    @Operation(summary = "Get products by seller", description = "Retrieve products by seller ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getProductsBySeller(@PathVariable String sellerId) {
        try {
            List<ProductDTO> products = productService.getProductsBySeller(sellerId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products by seller: " + e.getMessage(), e);
        }
    }

    /**
     * Get products by seller with pagination
     */
    @GetMapping("/seller/{sellerId}/paged")
    @Operation(summary = "Get products by seller with pagination", description = "Retrieve products by seller ID with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<Page<ProductDTO>> getProductsBySeller(@PathVariable String sellerId, Pageable pageable) {
        try {
            Page<ProductDTO> products = productService.getProductsBySeller(sellerId, pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products by seller: " + e.getMessage(), e);
        }
    }

    /**
     * Get products by category
     */
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category", description = "Retrieve products by category ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String categoryId) {
        try {
            List<ProductDTO> products = productService.getProductsByCategory(categoryId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products by category: " + e.getMessage(), e);
        }
    }

    /**
     * Get products by category with pagination
     */
    @GetMapping("/category/{categoryId}/paged")
    @Operation(summary = "Get products by category with pagination", description = "Retrieve products by category ID with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(@PathVariable String categoryId, Pageable pageable) {
        try {
            Page<ProductDTO> products = productService.getProductsByCategory(categoryId, pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products by category: " + e.getMessage(), e);
        }
    }

    /**
     * Get products by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get products by status", description = "Retrieve products by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getProductsByStatus(@PathVariable Product.ProductStatus status) {
        try {
            List<ProductDTO> products = productService.getProductsByStatus(status);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get products by status with pagination
     */
    @GetMapping("/status/{status}/paged")
    @Operation(summary = "Get products by status with pagination", description = "Retrieve products by status with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<Page<ProductDTO>> getProductsByStatus(@PathVariable Product.ProductStatus status, Pageable pageable) {
        try {
            Page<ProductDTO> products = productService.getProductsByStatus(status, pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get featured products
     */
    @GetMapping("/featured")
    @Operation(summary = "Get featured products", description = "Retrieve featured products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Featured products retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts() {
        try {
            List<ProductDTO> products = productService.getFeaturedProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get featured products: " + e.getMessage(), e);
        }
    }

    /**
     * Get featured products with pagination
     */
    @GetMapping("/featured/paged")
    @Operation(summary = "Get featured products with pagination", description = "Retrieve featured products with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Featured products retrieved successfully")
    })
    public ResponseEntity<Page<ProductDTO>> getFeaturedProducts(Pageable pageable) {
        try {
            Page<ProductDTO> products = productService.getFeaturedProducts(pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get featured products: " + e.getMessage(), e);
        }
    }

    /**
     * Search products
     */
    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by text")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products found")
    })
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String search) {
        try {
            List<ProductDTO> products = productService.searchProducts(search);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search products: " + e.getMessage(), e);
        }
    }

    /**
     * Search products with pagination
     */
    @GetMapping("/search/paged")
    @Operation(summary = "Search products with pagination", description = "Search products by text with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products found")
    })
    public ResponseEntity<Page<ProductDTO>> searchProducts(@RequestParam String search, Pageable pageable) {
        try {
            Page<ProductDTO> products = productService.searchProducts(search, pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search products: " + e.getMessage(), e);
        }
    }

    /**
     * Get products by multiple criteria
     */
    @GetMapping("/criteria")
    @Operation(summary = "Get products by criteria", description = "Retrieve products filtered by multiple criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<Page<ProductDTO>> getProductsByCriteria(
            @RequestParam(required = false) String sellerId,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) Product.ProductStatus status,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false) Boolean freeShipping,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) String search,
            Pageable pageable) {
        try {
            Page<ProductDTO> products = productService.getProductsByCriteria(
                sellerId, categoryId, status, featured, freeShipping,
                minPrice, maxPrice, minRating, minStock, search, pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products by criteria: " + e.getMessage(), e);
        }
    }

    /**
     * Get top selling products
     */
    @GetMapping("/top-selling")
    @Operation(summary = "Get top selling products", description = "Retrieve top selling products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Top selling products retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getTopSellingProducts(Pageable pageable) {
        try {
            List<ProductDTO> products = productService.getTopSellingProducts(pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get top selling products: " + e.getMessage(), e);
        }
    }

    /**
     * Get most viewed products
     */
    @GetMapping("/most-viewed")
    @Operation(summary = "Get most viewed products", description = "Retrieve most viewed products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Most viewed products retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getMostViewedProducts(Pageable pageable) {
        try {
            List<ProductDTO> products = productService.getMostViewedProducts(pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get most viewed products: " + e.getMessage(), e);
        }
    }

    /**
     * Get highest rated products
     */
    @GetMapping("/highest-rated")
    @Operation(summary = "Get highest rated products", description = "Retrieve highest rated products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Highest rated products retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getHighestRatedProducts(Pageable pageable) {
        try {
            List<ProductDTO> products = productService.getHighestRatedProducts(pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get highest rated products: " + e.getMessage(), e);
        }
    }

    /**
     * Get newest products
     */
    @GetMapping("/newest")
    @Operation(summary = "Get newest products", description = "Retrieve newest products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Newest products retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getNewestProducts(Pageable pageable) {
        try {
            List<ProductDTO> products = productService.getNewestProducts(pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get newest products: " + e.getMessage(), e);
        }
    }

    /**
     * Get products on sale
     */
    @GetMapping("/on-sale")
    @Operation(summary = "Get products on sale", description = "Retrieve products on sale")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products on sale retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getProductsOnSale(Pageable pageable) {
        try {
            List<ProductDTO> products = productService.getProductsOnSale(pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products on sale: " + e.getMessage(), e);
        }
    }

    /**
     * Get low stock products
     */
    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock products", description = "Retrieve products with low stock")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Low stock products retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getLowStockProducts() {
        try {
            List<ProductDTO> products = productService.getLowStockProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get low stock products: " + e.getMessage(), e);
        }
    }

    /**
     * Get low stock products with pagination
     */
    @GetMapping("/low-stock/paged")
    @Operation(summary = "Get low stock products with pagination", description = "Retrieve products with low stock with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Low stock products retrieved successfully")
    })
    public ResponseEntity<Page<ProductDTO>> getLowStockProducts(Pageable pageable) {
        try {
            Page<ProductDTO> products = productService.getLowStockProducts(pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get low stock products: " + e.getMessage(), e);
        }
    }

    /**
     * Get out of stock products
     */
    @GetMapping("/out-of-stock")
    @Operation(summary = "Get out of stock products", description = "Retrieve out of stock products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Out of stock products retrieved successfully")
    })
    public ResponseEntity<List<ProductDTO>> getOutOfStockProducts() {
        try {
            List<ProductDTO> products = productService.getOutOfStockProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get out of stock products: " + e.getMessage(), e);
        }
    }

    /**
     * Get out of stock products with pagination
     */
    @GetMapping("/out-of-stock/paged")
    @Operation(summary = "Get out of stock products with pagination", description = "Retrieve out of stock products with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Out of stock products retrieved successfully")
    })
    public ResponseEntity<Page<ProductDTO>> getOutOfStockProducts(Pageable pageable) {
        try {
            Page<ProductDTO> products = productService.getOutOfStockProducts(pageable);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get out of stock products: " + e.getMessage(), e);
        }
    }

    /**
     * Update product
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "SKU or slug already exists")
    })
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String id, @Valid @RequestBody UpdateProductDTO updateProductDTO) {
        try {
            ProductDTO product = productService.updateProduct(id, updateProductDTO);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (DuplicateResourceException e) {
            throw e;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
        }
    }

    /**
     * Delete product
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete a product by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product: " + e.getMessage(), e);
        }
    }

    /**
     * Activate product
     */
    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate product", description = "Activate a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product activated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> activateProduct(@PathVariable String id) {
        try {
            ProductDTO product = productService.activateProduct(id);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to activate product: " + e.getMessage(), e);
        }
    }

    /**
     * Deactivate product
     */
    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate product", description = "Deactivate a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> deactivateProduct(@PathVariable String id) {
        try {
            ProductDTO product = productService.deactivateProduct(id);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to deactivate product: " + e.getMessage(), e);
        }
    }

    /**
     * Set product as featured
     */
    @PatchMapping("/{id}/featured")
    @Operation(summary = "Set product as featured", description = "Set or unset a product as featured")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product featured status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> setFeatured(@PathVariable String id, @RequestBody Map<String, Boolean> request) {
        try {
            Boolean featured = request.get("featured");
            ProductDTO product = productService.setFeatured(id, featured);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to set product as featured: " + e.getMessage(), e);
        }
    }

    /**
     * Update product stock
     */
    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update product stock", description = "Update product stock quantity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product stock updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> updateStock(@PathVariable String id, @RequestBody Map<String, Integer> request) {
        try {
            Integer stockQuantity = request.get("stockQuantity");
            ProductDTO product = productService.updateStock(id, stockQuantity);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product stock: " + e.getMessage(), e);
        }
    }

    /**
     * Update product price
     */
    @PatchMapping("/{id}/price")
    @Operation(summary = "Update product price", description = "Update product price")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product price updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> updatePrice(@PathVariable String id, @RequestBody Map<String, BigDecimal> request) {
        try {
            BigDecimal price = request.get("price");
            ProductDTO product = productService.updatePrice(id, price);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product price: " + e.getMessage(), e);
        }
    }

    /**
     * Increment product view count
     */
    @PostMapping("/{id}/view")
    @Operation(summary = "Increment product view count", description = "Increment product view count")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product view count incremented successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Map<String, String>> incrementViewCount(@PathVariable String id) {
        try {
            productService.incrementViewCount(id);
            Map<String, String> response = Map.of("message", "View count incremented successfully");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to increment view count: " + e.getMessage(), e);
        }
    }

    /**
     * Increment product sold count
     */
    @PostMapping("/{id}/sold")
    @Operation(summary = "Increment product sold count", description = "Increment product sold count")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product sold count incremented successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Map<String, String>> incrementSoldCount(@PathVariable String id, @RequestBody Map<String, Integer> request) {
        try {
            Integer quantity = request.get("quantity");
            productService.incrementSoldCount(id, quantity);
            Map<String, String> response = Map.of("message", "Sold count incremented successfully");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to increment sold count: " + e.getMessage(), e);
        }
    }

    /**
     * Update product rating
     */
    @PatchMapping("/{id}/rating")
    @Operation(summary = "Update product rating", description = "Update product rating and review count")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product rating updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> updateRating(@PathVariable String id, @RequestBody Map<String, Object> request) {
        try {
            BigDecimal rating = new BigDecimal(request.get("rating").toString());
            Integer reviewCount = (Integer) request.get("reviewCount");
            ProductDTO product = productService.updateRating(id, rating, reviewCount);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product rating: " + e.getMessage(), e);
        }
    }

    /**
     * Get product count by seller
     */
    @GetMapping("/count/seller/{sellerId}")
    @Operation(summary = "Get product count by seller", description = "Get the total number of products by seller")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product count retrieved successfully")
    })
    public ResponseEntity<Long> getProductCountBySeller(@PathVariable String sellerId) {
        try {
            long count = productService.getProductCountBySeller(sellerId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product count by seller: " + e.getMessage(), e);
        }
    }

    /**
     * Get product count by category
     */
    @GetMapping("/count/category/{categoryId}")
    @Operation(summary = "Get product count by category", description = "Get the total number of products by category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product count retrieved successfully")
    })
    public ResponseEntity<Long> getProductCountByCategory(@PathVariable String categoryId) {
        try {
            long count = productService.getProductCountByCategory(categoryId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product count by category: " + e.getMessage(), e);
        }
    }

    /**
     * Get product count by status
     */
    @GetMapping("/count/status/{status}")
    @Operation(summary = "Get product count by status", description = "Get the total number of products by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product count retrieved successfully")
    })
    public ResponseEntity<Long> getProductCountByStatus(@PathVariable Product.ProductStatus status) {
        try {
            long count = productService.getProductCountByStatus(status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product count by status: " + e.getMessage(), e);
        }
    }

    /**
     * Check if SKU exists
     */
    @GetMapping("/exists/sku/{sku}")
    @Operation(summary = "Check if SKU exists", description = "Check if a SKU is already in use")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SKU existence checked successfully")
    })
    public ResponseEntity<Boolean> skuExists(@PathVariable String sku) {
        try {
            boolean exists = productService.skuExists(sku);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check SKU existence: " + e.getMessage(), e);
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
            boolean exists = productService.slugExists(slug);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check slug existence: " + e.getMessage(), e);
        }
    }
}
