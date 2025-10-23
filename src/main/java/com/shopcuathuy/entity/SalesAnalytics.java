package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * SalesAnalytics entity representing sales analytics data
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "sales_analytics", indexes = {
    @Index(name = "idx_sales_analytics_seller_id", columnList = "seller_id"),
    @Index(name = "idx_sales_analytics_date", columnList = "date"),
    @Index(name = "idx_sales_analytics_type", columnList = "type"),
    @Index(name = "idx_sales_analytics_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SalesAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "seller_id", nullable = false)
    @NotBlank(message = "Seller ID is required")
    private String sellerId;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Date is required")
    private LocalDateTime date;

    @Column(name = "type", nullable = false, length = 50)
    @NotBlank(message = "Type is required")
    @Size(max = 50, message = "Type must not exceed 50 characters")
    private String type;

    @Column(name = "period", nullable = false, length = 20)
    @NotBlank(message = "Period is required")
    @Size(max = 20, message = "Period must not exceed 20 characters")
    private String period;

    @Column(name = "total_orders", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total orders cannot be negative")
    private Integer totalOrders = 0;

    @Column(name = "total_revenue", nullable = false, precision = 15, scale = 2)
    @Builder.Default
    @DecimalMin(value = "0.0", message = "Total revenue cannot be negative")
    private BigDecimal totalRevenue = BigDecimal.ZERO;

    @Column(name = "total_products_sold", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total products sold cannot be negative")
    private Integer totalProductsSold = 0;

    @Column(name = "average_order_value", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Average order value cannot be negative")
    private BigDecimal averageOrderValue;

    @Column(name = "total_customers", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total customers cannot be negative")
    private Integer totalCustomers = 0;

    @Column(name = "new_customers", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "New customers cannot be negative")
    private Integer newCustomers = 0;

    @Column(name = "returning_customers", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Returning customers cannot be negative")
    private Integer returningCustomers = 0;

    @Column(name = "total_views", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total views cannot be negative")
    private Integer totalViews = 0;

    @Column(name = "total_clicks", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total clicks cannot be negative")
    private Integer totalClicks = 0;

    @Column(name = "conversion_rate", precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "Conversion rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Conversion rate cannot exceed 100")
    private BigDecimal conversionRate;

    @Column(name = "click_through_rate", precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "Click through rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Click through rate cannot exceed 100")
    private BigDecimal clickThroughRate;

    @Column(name = "bounce_rate", precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "Bounce rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Bounce rate cannot exceed 100")
    private BigDecimal bounceRate;

    @Column(name = "total_refunds", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total refunds cannot be negative")
    private Integer totalRefunds = 0;

    @Column(name = "refund_amount", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Refund amount cannot be negative")
    private BigDecimal refundAmount;

    @Column(name = "total_cancellations", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total cancellations cannot be negative")
    private Integer totalCancellations = 0;

    @Column(name = "cancellation_rate", precision = 5, scale = 2)
    @DecimalMin(value = "0.0", message = "Cancellation rate cannot be negative")
    @DecimalMax(value = "100.0", message = "Cancellation rate cannot exceed 100")
    private BigDecimal cancellationRate;

    @Column(name = "total_reviews", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total reviews cannot be negative")
    private Integer totalReviews = 0;

    @Column(name = "average_rating", precision = 3, scale = 2)
    @DecimalMin(value = "0.0", message = "Average rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Average rating cannot exceed 5.0")
    private BigDecimal averageRating;

    @Column(name = "total_followers", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total followers cannot be negative")
    private Integer totalFollowers = 0;

    @Column(name = "new_followers", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "New followers cannot be negative")
    private Integer newFollowers = 0;

    @Column(name = "total_products", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total products cannot be negative")
    private Integer totalProducts = 0;

    @Column(name = "new_products", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "New products cannot be negative")
    private Integer newProducts = 0;

    @Column(name = "active_products", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Active products cannot be negative")
    private Integer activeProducts = 0;

    @Column(name = "out_of_stock_products", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Out of stock products cannot be negative")
    private Integer outOfStockProducts = 0;

    @Column(name = "total_categories", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Total categories cannot be negative")
    private Integer totalCategories = 0;

    @Column(name = "top_category", length = 255)
    @Size(max = 255, message = "Top category must not exceed 255 characters")
    private String topCategory;

    @Column(name = "top_product", length = 255)
    @Size(max = 255, message = "Top product must not exceed 255 characters")
    private String topProduct;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

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

    /**
     * Get type display name
     */
    public String getTypeDisplayName() {
        if (type == null) {
            return "Analytics";
        }
        
        switch (type.toLowerCase()) {
            case "daily":
                return "Daily Analytics";
            case "weekly":
                return "Weekly Analytics";
            case "monthly":
                return "Monthly Analytics";
            case "yearly":
                return "Yearly Analytics";
            case "custom":
                return "Custom Analytics";
            default:
                return type;
        }
    }

    /**
     * Get period display name
     */
    public String getPeriodDisplayName() {
        if (period == null) {
            return "Unknown";
        }
        
        switch (period.toLowerCase()) {
            case "day":
                return "Day";
            case "week":
                return "Week";
            case "month":
                return "Month";
            case "year":
                return "Year";
            case "quarter":
                return "Quarter";
            default:
                return period;
        }
    }

    /**
     * Get analytics summary
     */
    public String getAnalyticsSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(getTypeDisplayName());
        summary.append(": $").append(totalRevenue);
        summary.append(" revenue, ").append(totalOrders).append(" orders");
        
        if (totalCustomers > 0) {
            summary.append(", ").append(totalCustomers).append(" customers");
        }
        
        return summary.toString();
    }

    /**
     * Calculate conversion rate
     */
    public BigDecimal calculateConversionRate() {
        if (totalViews == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(totalOrders)
                .divide(BigDecimal.valueOf(totalViews), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Calculate click through rate
     */
    public BigDecimal calculateClickThroughRate() {
        if (totalViews == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(totalClicks)
                .divide(BigDecimal.valueOf(totalViews), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Calculate bounce rate
     */
    public BigDecimal calculateBounceRate() {
        if (totalViews == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(totalViews - totalClicks)
                .divide(BigDecimal.valueOf(totalViews), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Calculate cancellation rate
     */
    public BigDecimal calculateCancellationRate() {
        if (totalOrders == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(totalCancellations)
                .divide(BigDecimal.valueOf(totalOrders), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Calculate average order value
     */
    public BigDecimal calculateAverageOrderValue() {
        if (totalOrders == 0) {
            return BigDecimal.ZERO;
        }
        return totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Get revenue per customer
     */
    public BigDecimal getRevenuePerCustomer() {
        if (totalCustomers == 0) {
            return BigDecimal.ZERO;
        }
        return totalRevenue.divide(BigDecimal.valueOf(totalCustomers), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Get products per order
     */
    public BigDecimal getProductsPerOrder() {
        if (totalOrders == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(totalProductsSold)
                .divide(BigDecimal.valueOf(totalOrders), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Get orders per customer
     */
    public BigDecimal getOrdersPerCustomer() {
        if (totalCustomers == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(totalOrders)
                .divide(BigDecimal.valueOf(totalCustomers), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Get customer retention rate
     */
    public BigDecimal getCustomerRetentionRate() {
        if (totalCustomers == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(returningCustomers)
                .divide(BigDecimal.valueOf(totalCustomers), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Get new customer rate
     */
    public BigDecimal getNewCustomerRate() {
        if (totalCustomers == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(newCustomers)
                .divide(BigDecimal.valueOf(totalCustomers), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Get product performance rate
     */
    public BigDecimal getProductPerformanceRate() {
        if (totalProducts == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(activeProducts)
                .divide(BigDecimal.valueOf(totalProducts), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Get out of stock rate
     */
    public BigDecimal getOutOfStockRate() {
        if (totalProducts == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(outOfStockProducts)
                .divide(BigDecimal.valueOf(totalProducts), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * Check if analytics is recent (within last 7 days)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(7));
    }

    /**
     * Check if analytics is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Get days since creation
     */
    public long getDaysSinceCreation() {
        if (createdAt == null) {
            return 0;
        }
        return java.time.Duration.between(createdAt, LocalDateTime.now()).toDays();
    }

    /**
     * Get performance score (0-100)
     */
    public Integer getPerformanceScore() {
        int score = 0;
        
        // Revenue score (0-30)
        if (totalRevenue.compareTo(BigDecimal.valueOf(1000)) > 0) score += 10;
        if (totalRevenue.compareTo(BigDecimal.valueOf(5000)) > 0) score += 10;
        if (totalRevenue.compareTo(BigDecimal.valueOf(10000)) > 0) score += 10;
        
        // Orders score (0-20)
        if (totalOrders > 10) score += 5;
        if (totalOrders > 50) score += 5;
        if (totalOrders > 100) score += 10;
        
        // Customers score (0-20)
        if (totalCustomers > 5) score += 5;
        if (totalCustomers > 20) score += 5;
        if (totalCustomers > 50) score += 10;
        
        // Conversion rate score (0-15)
        if (conversionRate != null && conversionRate.compareTo(BigDecimal.valueOf(1)) > 0) score += 5;
        if (conversionRate != null && conversionRate.compareTo(BigDecimal.valueOf(3)) > 0) score += 5;
        if (conversionRate != null && conversionRate.compareTo(BigDecimal.valueOf(5)) > 0) score += 5;
        
        // Rating score (0-15)
        if (averageRating != null && averageRating.compareTo(BigDecimal.valueOf(3)) > 0) score += 5;
        if (averageRating != null && averageRating.compareTo(BigDecimal.valueOf(4)) > 0) score += 5;
        if (averageRating != null && averageRating.compareTo(BigDecimal.valueOf(4.5)) > 0) score += 5;
        
        return Math.min(100, score);
    }

    /**
     * Get performance level
     */
    public String getPerformanceLevel() {
        int score = getPerformanceScore();
        
        if (score >= 90) return "Excellent";
        if (score >= 80) return "Very Good";
        if (score >= 70) return "Good";
        if (score >= 60) return "Average";
        if (score >= 50) return "Below Average";
        return "Poor";
    }
}
