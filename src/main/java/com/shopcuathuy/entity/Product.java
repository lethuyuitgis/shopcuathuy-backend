package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * Product entity representing products in the system
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_product_slug", columnList = "slug"),
    @Index(name = "idx_product_seller_id", columnList = "seller_id"),
    @Index(name = "idx_product_category_id", columnList = "category_id"),
    @Index(name = "idx_product_status", columnList = "status"),
    @Index(name = "idx_product_featured", columnList = "featured"),
    @Index(name = "idx_product_price", columnList = "price"),
    @Index(name = "idx_product_rating", columnList = "rating"),
    @Index(name = "idx_product_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "seller_id", nullable = false)
    @NotBlank(message = "Seller ID is required")
    private String sellerId;

    @Column(name = "category_id", nullable = false)
    @NotBlank(message = "Category ID is required")
    private String categoryId;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Product name is required")
    @Size(max = 500, message = "Product name must not exceed 500 characters")
    private String name;

    @Column(nullable = false, length = 500, unique = true)
    @NotBlank(message = "Product slug is required")
    @Size(max = 500, message = "Product slug must not exceed 500 characters")
    private String slug;

    @Column(columnDefinition = "TEXT")
    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    @Column(name = "short_description", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Short description must not exceed 1000 characters")
    private String shortDescription;

    @Column(nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Column(name = "original_price", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Original price must be greater than 0")
    private BigDecimal originalPrice;

    @Column(name = "discount_percent", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal discountPercent = BigDecimal.ZERO;

    @Column(length = 100)
    @Size(max = 100, message = "SKU must not exceed 100 characters")
    private String sku;

    @Column(name = "stock_quantity", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity = 0;

    @Column(name = "min_stock_alert", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Minimum stock alert cannot be negative")
    private Integer minStockAlert = 5;

    @Column(precision = 8, scale = 2)
    @DecimalMin(value = "0.0", message = "Weight cannot be negative")
    private BigDecimal weight;

    @Column(length = 100)
    @Size(max = 100, message = "Dimensions must not exceed 100 characters")
    private String dimensions;

    @Column(length = 100)
    @Size(max = 100, message = "Brand must not exceed 100 characters")
    private String brand;

    @Column(length = 100)
    @Size(max = 100, message = "Model must not exceed 100 characters")
    private String model;

    @Column(name = "warranty_period")
    @Min(value = 0, message = "Warranty period cannot be negative")
    private Integer warrantyPeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "warranty_type", nullable = false)
    @Builder.Default
    private WarrantyType warrantyType = WarrantyType.MONTHS;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ProductStatus status = ProductStatus.DRAFT;

    @Column(nullable = false)
    @Builder.Default
    private Boolean featured = false;

    @Column(name = "free_shipping", nullable = false)
    @Builder.Default
    private Boolean freeShipping = false;

    @Column(precision = 3, scale = 2)
    @Builder.Default
    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5.0")
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "review_count", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Review count cannot be negative")
    private Integer reviewCount = 0;

    @Column(name = "sold_count", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Sold count cannot be negative")
    private Integer soldCount = 0;

    @Column(name = "view_count", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "View count cannot be negative")
    private Integer viewCount = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductAttribute> attributes = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductVariant> variants = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductView> views = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Wishlist> wishlist = new ArrayList<>();

    /**
     * Product status enum
     */
    public enum ProductStatus {
        ACTIVE, INACTIVE, DRAFT, OUT_OF_STOCK
    }

    /**
     * Warranty type enum
     */
    public enum WarrantyType {
        MONTHS, YEARS
    }

    /**
     * Get primary image URL
     */
    public String getPrimaryImageUrl() {
        return images.stream()
                .filter(ProductImage::getIsPrimary)
                .findFirst()
                .map(ProductImage::getImageUrl)
                .orElse(images.isEmpty() ? null : images.get(0).getImageUrl());
    }

    /**
     * Check if product is on sale
     */
    public boolean isOnSale() {
        return originalPrice != null && originalPrice.compareTo(price) > 0;
    }

    /**
     * Get discount amount
     */
    public BigDecimal getDiscountAmount() {
        if (isOnSale()) {
            return originalPrice.subtract(price);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Check if product is in stock
     */
    public boolean isInStock() {
        return stockQuantity > 0 && status == ProductStatus.ACTIVE;
    }

    /**
     * Calculate discount percentage
     */
    public BigDecimal calculateDiscountPercent() {
        if (originalPrice != null && originalPrice.compareTo(price) > 0) {
            return originalPrice.subtract(price)
                    .divide(originalPrice, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Update rating based on reviews
     */
    public void updateRating() {
        if (reviews != null && !reviews.isEmpty()) {
            double averageRating = reviews.stream()
                    .mapToInt(ProductReview::getRating)
                    .average()
                    .orElse(0.0);
            this.rating = BigDecimal.valueOf(averageRating).setScale(2, BigDecimal.ROUND_HALF_UP);
            this.reviewCount = reviews.size();
        }
    }

    /**
     * Increment view count
     */
    public void incrementViewCount() {
        this.viewCount++;
    }

    /**
     * Decrement stock quantity
     */
    public boolean decrementStock(int quantity) {
        if (stockQuantity >= quantity) {
            stockQuantity -= quantity;
            return true;
        }
        return false;
    }

    /**
     * Increment stock quantity
     */
    public void incrementStock(int quantity) {
        stockQuantity += quantity;
    }

    /**
     * Increment sold count
     */
    public void incrementSoldCount(int quantity) {
        soldCount += quantity;
    }
}
