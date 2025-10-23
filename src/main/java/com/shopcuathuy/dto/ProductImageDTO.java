package com.shopcuathuy.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object for ProductImage
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO {

    private String id;
    private String productId;
    private String imageUrl;
    private String thumbnailUrl;
    private String altText;
    private String title;
    private Boolean isPrimary;
    private Integer sortOrder;
    private Long fileSize;
    private Integer width;
    private Integer height;
    private String mimeType;
    private String fileExtension;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
