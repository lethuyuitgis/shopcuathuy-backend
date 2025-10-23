package com.shopcuathuy.dto;

import com.shopcuathuy.entity.ProductVariant;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object for ProductVariant
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantDTO {

    private String id;
    private String productId;
    private String sku;
    private String variantName;
    private String variantDescription;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private BigDecimal costPrice;
    private Integer stockQuantity;
    private Integer minStockAlert;
    private BigDecimal weight;
    private String dimensions;
    private String barcode;
    private String imageUrl;
    private String attributes;
    private ProductVariant.VariantStatus status;
    private Boolean isDefault;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
