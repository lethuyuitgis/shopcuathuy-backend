package com.shopcuathuy.dto;

import java.time.LocalDateTime;


/**
 * Data Transfer Object for ProductAttribute
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeDTO {

    private String id;
    private String productId;
    private String name;
    private String value;
    private String displayName;
    private String attributeType;
    private Boolean isRequired;
    private Boolean isFilterable;
    private Boolean isSearchable;
    private Integer sortOrder;
    private String unit;
    private Double minValue;
    private Double maxValue;
    private String options;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
