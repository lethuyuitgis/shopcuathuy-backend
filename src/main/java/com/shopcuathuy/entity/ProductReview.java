package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * ProductReview entity representing product reviews
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "product_reviews", indexes = {
    @Index(name = "idx_product_review_product_id", columnList = "product_id"),
    @Index(name = "idx_product_review_user_id", columnList = "user_id"),
    @Index(name = "idx_product_review_rating", columnList = "rating"),
    @Index(name = "idx_product_review_status", columnList = "status"),
    @Index(name = "idx_product_review_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_id", nullable = false)
    @NotBlank(message = "Product ID is required")
    private String productId;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "rating", nullable = false)
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Column(name = "title", length = 255)
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    @Size(max = 2000, message = "Content must not exceed 2000 characters")
    private String content;

    @Column(name = "pros", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Pros must not exceed 1000 characters")
    private String pros;

    @Column(name = "cons", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Cons must not exceed 1000 characters")
    private String cons;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ReviewStatus status = ReviewStatus.PENDING;

    @Column(name = "is_verified_purchase", nullable = false)
    @Builder.Default
    private Boolean isVerifiedPurchase = false;

    @Column(name = "is_anonymous", nullable = false)
    @Builder.Default
    private Boolean isAnonymous = false;

    @Column(name = "helpful_count", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Helpful count cannot be negative")
    private Integer helpfulCount = 0;

    @Column(name = "not_helpful_count", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Not helpful count cannot be negative")
    private Integer notHelpfulCount = 0;

    @Column(name = "report_count", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Report count cannot be negative")
    private Integer reportCount = 0;

    @Column(name = "admin_notes", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Admin notes must not exceed 1000 characters")
    private String adminNotes;

    @Column(name = "moderated_at")
    private LocalDateTime moderatedAt;

    @Column(name = "moderated_by")
    private String moderatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewResponse> responses = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewImage> images = new ArrayList<>();

    /**
     * Review status enum
     */
    public enum ReviewStatus {
        PENDING, APPROVED, REJECTED, HIDDEN
    }

    /**
     * Check if review is pending
     */
    public boolean isPending() {
        return status == ReviewStatus.PENDING;
    }

    /**
     * Check if review is approved
     */
    public boolean isApproved() {
        return status == ReviewStatus.APPROVED;
    }

    /**
     * Check if review is rejected
     */
    public boolean isRejected() {
        return status == ReviewStatus.REJECTED;
    }

    /**
     * Check if review is hidden
     */
    public boolean isHidden() {
        return status == ReviewStatus.HIDDEN;
    }

    /**
     * Check if review is verified purchase
     */
    public boolean isVerifiedPurchase() {
        return isVerifiedPurchase != null && isVerifiedPurchase;
    }

    /**
     * Check if review is anonymous
     */
    public boolean isAnonymous() {
        return isAnonymous != null && isAnonymous;
    }

    /**
     * Get helpful percentage
     */
    public Double getHelpfulPercentage() {
        int total = helpfulCount + notHelpfulCount;
        if (total == 0) {
            return 0.0;
        }
        return (double) helpfulCount / total * 100;
    }

    /**
     * Get total helpful votes
     */
    public Integer getTotalHelpfulVotes() {
        return helpfulCount + notHelpfulCount;
    }

    /**
     * Check if review has content
     */
    public boolean hasContent() {
        return content != null && !content.trim().isEmpty();
    }

    /**
     * Check if review has title
     */
    public boolean hasTitle() {
        return title != null && !title.trim().isEmpty();
    }

    /**
     * Check if review has pros
     */
    public boolean hasPros() {
        return pros != null && !pros.trim().isEmpty();
    }

    /**
     * Check if review has cons
     */
    public boolean hasCons() {
        return cons != null && !cons.trim().isEmpty();
    }

    /**
     * Check if review has images
     */
    public boolean hasImages() {
        return images != null && !images.isEmpty();
    }

    /**
     * Check if review has responses
     */
    public boolean hasResponses() {
        return responses != null && !responses.isEmpty();
    }

    /**
     * Get review summary
     */
    public String getReviewSummary() {
        if (hasTitle()) {
            return title;
        } else if (hasContent()) {
            return content.length() > 100 ? content.substring(0, 100) + "..." : content;
        }
        return "Review #" + id.substring(0, 8);
    }

    /**
     * Check if review is recent (within last 30 days)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(30));
    }

    /**
     * Check if review is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Check if review can be edited
     */
    public boolean canBeEdited() {
        return isApproved() && createdAt != null && 
               createdAt.isAfter(LocalDateTime.now().minusDays(7));
    }

    /**
     * Check if review can be deleted
     */
    public boolean canBeDeleted() {
        return isApproved() && createdAt != null && 
               createdAt.isAfter(LocalDateTime.now().minusDays(7));
    }

    /**
     * Check if review is reported
     */
    public boolean isReported() {
        return reportCount > 0;
    }

    /**
     * Get rating as stars
     */
    public String getRatingStars() {
        if (rating == null) {
            return "☆☆☆☆☆";
        }
        
        StringBuilder stars = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            if (i <= rating) {
                stars.append("★");
            } else {
                stars.append("☆");
            }
        }
        return stars.toString();
    }
}
