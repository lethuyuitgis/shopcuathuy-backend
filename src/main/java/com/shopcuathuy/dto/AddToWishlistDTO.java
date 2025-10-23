package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * Add to Wishlist DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class AddToWishlistDTO {

    @NotNull
    private Long productId;

    private String notes;

    // Constructors
    public AddToWishlistDTO() {}

    public AddToWishlistDTO(Long productId, String notes) {
        this.productId = productId;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
