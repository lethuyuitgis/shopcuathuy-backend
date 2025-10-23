package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Add to Cart DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class AddToCartDTO {

    @NotNull
    private String productId;

    private String productVariantId;

    @NotNull
    @Positive
    private Integer quantity;

    private String notes;

    // Constructors
    public AddToCartDTO() {}

    public AddToCartDTO(String productId, String productVariantId, Integer quantity, String notes) {
        this.productId = productId;
        this.productVariantId = productVariantId;
        this.quantity = quantity;
        this.notes = notes;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductVariantId() { return productVariantId; }
    public void setProductVariantId(String productVariantId) { this.productVariantId = productVariantId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
