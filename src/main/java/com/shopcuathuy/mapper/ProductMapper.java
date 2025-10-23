package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.ProductAttributeDTO;
import com.shopcuathuy.dto.ProductDTO;
import com.shopcuathuy.dto.ProductImageDTO;
import com.shopcuathuy.dto.ProductVariantDTO;
import com.shopcuathuy.entity.Product;
import com.shopcuathuy.entity.ProductAttribute;
import com.shopcuathuy.entity.ProductImage;
import com.shopcuathuy.entity.ProductVariant;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


/**
 * Mapper interface for Product entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * Convert Product entity to ProductDTO
     */
    @Mapping(target = "sellerName", ignore = true)
    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "variants", ignore = true)
    ProductDTO toDTO(Product product);

    /**
     * Convert ProductDTO to Product entity
     */
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "wishlist", ignore = true)
    Product toEntity(ProductDTO productDTO);

    /**
     * Convert list of Product entities to list of ProductDTOs
     */
    List<ProductDTO> toDTOList(List<Product> products);

    /**
     * Convert list of ProductDTOs to list of Product entities
     */
    List<Product> toEntityList(List<ProductDTO> productDTOs);

    /**
     * Convert ProductImage entity to ProductImageDTO
     */
    ProductImageDTO toImageDTO(ProductImage productImage);

    /**
     * Convert ProductImageDTO to ProductImage entity
     */
    @Mapping(target = "product", ignore = true)
    ProductImage toImageEntity(ProductImageDTO productImageDTO);

    /**
     * Convert list of ProductImage entities to list of ProductImageDTOs
     */
    List<ProductImageDTO> toImageDTOList(List<ProductImage> productImages);

    /**
     * Convert list of ProductImageDTOs to list of ProductImage entities
     */
    List<ProductImage> toImageEntityList(List<ProductImageDTO> productImageDTOs);

    /**
     * Convert ProductAttribute entity to ProductAttributeDTO
     */
    ProductAttributeDTO toAttributeDTO(ProductAttribute productAttribute);

    /**
     * Convert ProductAttributeDTO to ProductAttribute entity
     */
    @Mapping(target = "product", ignore = true)
    ProductAttribute toAttributeEntity(ProductAttributeDTO productAttributeDTO);

    /**
     * Convert list of ProductAttribute entities to list of ProductAttributeDTOs
     */
    List<ProductAttributeDTO> toAttributeDTOList(List<ProductAttribute> productAttributes);

    /**
     * Convert list of ProductAttributeDTOs to list of ProductAttribute entities
     */
    List<ProductAttribute> toAttributeEntityList(List<ProductAttributeDTO> productAttributeDTOs);

    /**
     * Convert ProductVariant entity to ProductVariantDTO
     */
    ProductVariantDTO toVariantDTO(ProductVariant productVariant);

    /**
     * Convert ProductVariantDTO to ProductVariant entity
     */
    @Mapping(target = "product", ignore = true)
    ProductVariant toVariantEntity(ProductVariantDTO productVariantDTO);

    /**
     * Convert list of ProductVariant entities to list of ProductVariantDTOs
     */
    List<ProductVariantDTO> toVariantDTOList(List<ProductVariant> productVariants);

    /**
     * Convert list of ProductVariantDTOs to list of ProductVariant entities
     */
    List<ProductVariant> toVariantEntityList(List<ProductVariantDTO> productVariantDTOs);
}
