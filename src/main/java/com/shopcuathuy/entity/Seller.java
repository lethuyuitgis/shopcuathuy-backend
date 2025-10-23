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
 * Seller entity representing sellers in the system
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "sellers", indexes = {
    @Index(name = "idx_seller_user_id", columnList = "user_id"),
    @Index(name = "idx_seller_shop_slug", columnList = "shop_slug"),
    @Index(name = "idx_seller_verified", columnList = "verified"),
    @Index(name = "idx_seller_featured", columnList = "featured"),
    @Index(name = "idx_seller_rating", columnList = "rating"),
    @Index(name = "idx_seller_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false, unique = true)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "shop_name", nullable = false, length = 255)
    @NotBlank(message = "Shop name is required")
    @Size(max = 255, message = "Shop name must not exceed 255 characters")
    private String shopName;

    @Column(name = "shop_slug", nullable = false, length = 255, unique = true)
    @NotBlank(message = "Shop slug is required")
    @Size(max = 255, message = "Shop slug must not exceed 255 characters")
    private String shopSlug;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Column(name = "business_license", length = 100)
    @Size(max = 100, message = "Business license must not exceed 100 characters")
    private String businessLicense;

    @Column(name = "tax_code", length = 50)
    @Size(max = 50, message = "Tax code must not exceed 50 characters")
    private String taxCode;

    @Column(name = "bank_account", length = 50)
    @Size(max = 50, message = "Bank account must not exceed 50 characters")
    private String bankAccount;

    @Column(name = "bank_name", length = 100)
    @Size(max = 100, message = "Bank name must not exceed 100 characters")
    private String bankName;

    @Column(precision = 3, scale = 2)
    @Builder.Default
    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5.0")
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "followers_count", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Followers count cannot be negative")
    private Integer followersCount = 0;

    @Column(name = "products_count", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Products count cannot be negative")
    private Integer productsCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private Boolean verified = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean featured = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShopFollower> followers = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SalesAnalytics> salesAnalytics = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiscountCode> discountCodes = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShippingMethod> shippingMethods = new ArrayList<>();

    /**
     * Check if seller is verified
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Check if seller is featured
     */
    public boolean isFeatured() {
        return featured;
    }

    /**
     * Get average rating
     */
    public BigDecimal getAverageRating() {
        return rating != null ? rating : BigDecimal.ZERO;
    }

    /**
     * Get total followers
     */
    public Integer getTotalFollowers() {
        return followersCount != null ? followersCount : 0;
    }

    /**
     * Get total products
     */
    public Integer getTotalProducts() {
        return productsCount != null ? productsCount : 0;
    }

    /**
     * Increment followers count
     */
    public void incrementFollowersCount() {
        this.followersCount++;
    }

    /**
     * Decrement followers count
     */
    public void decrementFollowersCount() {
        if (this.followersCount > 0) {
            this.followersCount--;
        }
    }

    /**
     * Increment products count
     */
    public void incrementProductsCount() {
        this.productsCount++;
    }

    /**
     * Decrement products count
     */
    public void decrementProductsCount() {
        if (this.productsCount > 0) {
            this.productsCount--;
        }
    }

    /**
     * Update rating based on reviews
     */
    public void updateRating() {
        if (products != null && !products.isEmpty()) {
            double averageRating = products.stream()
                    .filter(product -> product.getRating() != null)
                    .mapToDouble(product -> product.getRating().doubleValue())
                    .average()
                    .orElse(0.0);
            this.rating = BigDecimal.valueOf(averageRating).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }
}
