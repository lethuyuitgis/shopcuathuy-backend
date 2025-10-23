package com.shopcuathuy.dto;

import com.shopcuathuy.entity.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Data Transfer Object for Product
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String id;
    private String sellerId;
    private String categoryId;
    private String name;
    private String slug;
    private String description;
    private String shortDescription;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private BigDecimal discountPercent;
    private String sku;
    private Integer stockQuantity;
    private Integer minStockAlert;
    private BigDecimal weight;
    private String dimensions;
    private String brand;
    private String model;
    private Integer warrantyPeriod;
    private Product.WarrantyType warrantyType;
    private Product.ProductStatus status;
    private Boolean featured;
    private Boolean freeShipping;
    private BigDecimal rating;
    private Integer reviewCount;
    private Integer soldCount;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Related data
    private String sellerName;
    private String categoryName;
    private List<ProductImageDTO> images;
    private List<ProductAttributeDTO> attributes;
    private List<ProductVariantDTO> variants;
}
