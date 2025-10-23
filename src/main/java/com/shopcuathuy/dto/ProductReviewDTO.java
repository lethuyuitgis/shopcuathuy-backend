package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.shopcuathuy.entity.ProductReview;
import java.time.LocalDateTime;

/**
 * Product Review DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class ProductReviewDTO {

    private Long id;
    private Long productId;
    private String productName;
    private Long userId;
    private String userName;
    private String userAvatar;
    private Long orderId;
    private Integer rating;
    private String title;
    private String content;
    private Boolean isVerifiedPurchase;
    private Integer helpfulCount;
    private Integer notHelpfulCount;
    private ProductReview.ReviewStatus status;
    private String adminNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public ProductReviewDTO() {}

    public ProductReviewDTO(Long id, Long productId, String productName, Long userId, String userName, 
                           String userAvatar, Long orderId, Integer rating, String title, String content,
                           Boolean isVerifiedPurchase, Integer helpfulCount, Integer notHelpfulCount,
                           ProductReview.ReviewStatus status, String adminNotes, LocalDateTime createdAt, 
                           LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.userId = userId;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.orderId = orderId;
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.isVerifiedPurchase = isVerifiedPurchase;
        this.helpfulCount = helpfulCount;
        this.notHelpfulCount = notHelpfulCount;
        this.status = status;
        this.adminNotes = adminNotes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserAvatar() { return userAvatar; }
    public void setUserAvatar(String userAvatar) { this.userAvatar = userAvatar; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Boolean getIsVerifiedPurchase() { return isVerifiedPurchase; }
    public void setIsVerifiedPurchase(Boolean isVerifiedPurchase) { this.isVerifiedPurchase = isVerifiedPurchase; }

    public Integer getHelpfulCount() { return helpfulCount; }
    public void setHelpfulCount(Integer helpfulCount) { this.helpfulCount = helpfulCount; }

    public Integer getNotHelpfulCount() { return notHelpfulCount; }
    public void setNotHelpfulCount(Integer notHelpfulCount) { this.notHelpfulCount = notHelpfulCount; }

    public ProductReview.ReviewStatus getStatus() { return status; }
    public void setStatus(ProductReview.ReviewStatus status) { this.status = status; }

    public String getAdminNotes() { return adminNotes; }
    public void setAdminNotes(String adminNotes) { this.adminNotes = adminNotes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
