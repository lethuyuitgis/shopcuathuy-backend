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
    private Long productId;

    private Long productVariantId;

    @NotNull
    @Positive
    private Integer quantity;

    private String notes;

    // Constructors
    public AddToCartDTO() {}

    public AddToCartDTO(Long productId, Long productVariantId, Integer quantity, String notes) {
        this.productId = productId;
        this.productVariantId = productVariantId;
        this.quantity = quantity;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getProductVariantId() { return productVariantId; }
    public void setProductVariantId(Long productVariantId) { this.productVariantId = productVariantId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
