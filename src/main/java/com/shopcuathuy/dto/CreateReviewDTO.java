package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Create Review DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class CreateReviewDTO {

    @NotNull
    private Long productId;

    private Long orderId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @Size(max = 200)
    private String title;

    @Size(max = 5000)
    private String content;

    // Constructors
    public CreateReviewDTO() {}

    public CreateReviewDTO(Long productId, Long orderId, Integer rating, String title, String content) {
        this.productId = productId;
        this.orderId = orderId;
        this.rating = rating;
        this.title = title;
        this.content = content;
    }

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
