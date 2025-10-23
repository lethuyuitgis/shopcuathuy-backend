package com.shopcuathuy.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * ReviewResponse entity representing responses to product reviews
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "review_responses", indexes = {
    @Index(name = "idx_review_response_review_id", columnList = "review_id"),
    @Index(name = "idx_review_response_user_id", columnList = "user_id"),
    @Index(name = "idx_review_response_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReviewResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "review_id", nullable = false)
    @NotBlank(message = "Review ID is required")
    private String reviewId;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Response content is required")
    @Size(max = 1000, message = "Response content must not exceed 1000 characters")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ResponseType type = ResponseType.SELLER_RESPONSE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ResponseStatus status = ResponseStatus.ACTIVE;

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
    @Size(max = 500, message = "Admin notes must not exceed 500 characters")
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
    @JoinColumn(name = "review_id", insertable = false, updatable = false)
    private ProductReview review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    /**
     * Response type enum
     */
    public enum ResponseType {
        SELLER_RESPONSE, ADMIN_RESPONSE, CUSTOMER_RESPONSE
    }

    /**
     * Response status enum
     */
    public enum ResponseStatus {
        ACTIVE, HIDDEN, DELETED
    }

    /**
     * Check if response is active
     */
    public boolean isActive() {
        return status == ResponseStatus.ACTIVE;
    }

    /**
     * Check if response is hidden
     */
    public boolean isHidden() {
        return status == ResponseStatus.HIDDEN;
    }

    /**
     * Check if response is deleted
     */
    public boolean isDeleted() {
        return status == ResponseStatus.DELETED;
    }

    /**
     * Check if response is anonymous
     */
    public boolean isAnonymous() {
        return isAnonymous != null && isAnonymous;
    }

    /**
     * Check if response is from seller
     */
    public boolean isSellerResponse() {
        return type == ResponseType.SELLER_RESPONSE;
    }

    /**
     * Check if response is from admin
     */
    public boolean isAdminResponse() {
        return type == ResponseType.ADMIN_RESPONSE;
    }

    /**
     * Check if response is from customer
     */
    public boolean isCustomerResponse() {
        return type == ResponseType.CUSTOMER_RESPONSE;
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
     * Check if response is reported
     */
    public boolean isReported() {
        return reportCount > 0;
    }

    /**
     * Check if response is recent (within last 7 days)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(7));
    }

    /**
     * Check if response is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Check if response can be edited
     */
    public boolean canBeEdited() {
        return isActive() && createdAt != null && 
               createdAt.isAfter(LocalDateTime.now().minusDays(1));
    }

    /**
     * Check if response can be deleted
     */
    public boolean canBeDeleted() {
        return isActive() && createdAt != null && 
               createdAt.isAfter(LocalDateTime.now().minusDays(1));
    }

    /**
     * Get response summary
     */
    public String getResponseSummary() {
        if (content != null && content.length() > 100) {
            return content.substring(0, 100) + "...";
        }
        return content;
    }

    /**
     * Get response type display name
     */
    public String getTypeDisplayName() {
        switch (type) {
            case SELLER_RESPONSE:
                return "Seller Response";
            case ADMIN_RESPONSE:
                return "Admin Response";
            case CUSTOMER_RESPONSE:
                return "Customer Response";
            default:
                return "Response";
        }
    }
}
