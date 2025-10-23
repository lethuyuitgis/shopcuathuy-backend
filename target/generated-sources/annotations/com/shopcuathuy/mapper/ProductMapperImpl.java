package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.CreateProductDTO;
import com.shopcuathuy.dto.ProductAttributeDTO;
import com.shopcuathuy.dto.ProductDTO;
import com.shopcuathuy.dto.ProductImageDTO;
import com.shopcuathuy.dto.ProductVariantDTO;
import com.shopcuathuy.dto.UpdateProductDTO;
import com.shopcuathuy.entity.Product;
import com.shopcuathuy.entity.ProductAttribute;
import com.shopcuathuy.entity.ProductImage;
import com.shopcuathuy.entity.ProductVariant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T18:11:55+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO.ProductDTOBuilder productDTO = ProductDTO.builder();

        productDTO.id( product.getId() );
        productDTO.sellerId( product.getSellerId() );
        productDTO.categoryId( product.getCategoryId() );
        productDTO.name( product.getName() );
        productDTO.slug( product.getSlug() );
        productDTO.description( product.getDescription() );
        productDTO.shortDescription( product.getShortDescription() );
        productDTO.price( product.getPrice() );
        productDTO.originalPrice( product.getOriginalPrice() );
        productDTO.discountPercent( product.getDiscountPercent() );
        productDTO.sku( product.getSku() );
        productDTO.stockQuantity( product.getStockQuantity() );
        productDTO.minStockAlert( product.getMinStockAlert() );
        productDTO.weight( product.getWeight() );
        productDTO.dimensions( product.getDimensions() );
        productDTO.brand( product.getBrand() );
        productDTO.model( product.getModel() );
        productDTO.warrantyPeriod( product.getWarrantyPeriod() );
        productDTO.warrantyType( product.getWarrantyType() );
        productDTO.status( product.getStatus() );
        productDTO.featured( product.getFeatured() );
        productDTO.freeShipping( product.getFreeShipping() );
        productDTO.rating( product.getRating() );
        productDTO.reviewCount( product.getReviewCount() );
        productDTO.soldCount( product.getSoldCount() );
        productDTO.viewCount( product.getViewCount() );
        productDTO.createdAt( product.getCreatedAt() );
        productDTO.updatedAt( product.getUpdatedAt() );

        return productDTO.build();
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( productDTO.getId() );
        product.sellerId( productDTO.getSellerId() );
        product.categoryId( productDTO.getCategoryId() );
        product.name( productDTO.getName() );
        product.slug( productDTO.getSlug() );
        product.description( productDTO.getDescription() );
        product.shortDescription( productDTO.getShortDescription() );
        product.price( productDTO.getPrice() );
        product.originalPrice( productDTO.getOriginalPrice() );
        product.discountPercent( productDTO.getDiscountPercent() );
        product.sku( productDTO.getSku() );
        product.stockQuantity( productDTO.getStockQuantity() );
        product.minStockAlert( productDTO.getMinStockAlert() );
        product.weight( productDTO.getWeight() );
        product.dimensions( productDTO.getDimensions() );
        product.brand( productDTO.getBrand() );
        product.model( productDTO.getModel() );
        product.warrantyPeriod( productDTO.getWarrantyPeriod() );
        product.warrantyType( productDTO.getWarrantyType() );
        product.status( productDTO.getStatus() );
        product.featured( productDTO.getFeatured() );
        product.freeShipping( productDTO.getFreeShipping() );
        product.rating( productDTO.getRating() );
        product.reviewCount( productDTO.getReviewCount() );
        product.soldCount( productDTO.getSoldCount() );
        product.viewCount( productDTO.getViewCount() );
        product.createdAt( productDTO.getCreatedAt() );
        product.updatedAt( productDTO.getUpdatedAt() );

        return product.build();
    }

    @Override
    public Product toEntity(CreateProductDTO createProductDTO) {
        if ( createProductDTO == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.sellerId( createProductDTO.getSellerId() );
        product.categoryId( createProductDTO.getCategoryId() );
        product.name( createProductDTO.getName() );
        product.slug( createProductDTO.getSlug() );
        product.description( createProductDTO.getDescription() );
        product.shortDescription( createProductDTO.getShortDescription() );
        product.price( createProductDTO.getPrice() );
        product.originalPrice( createProductDTO.getOriginalPrice() );
        product.sku( createProductDTO.getSku() );
        product.stockQuantity( createProductDTO.getStockQuantity() );
        product.minStockAlert( createProductDTO.getMinStockAlert() );
        product.weight( createProductDTO.getWeight() );
        product.dimensions( createProductDTO.getDimensions() );
        product.brand( createProductDTO.getBrand() );
        product.model( createProductDTO.getModel() );
        product.warrantyPeriod( createProductDTO.getWarrantyPeriod() );
        product.warrantyType( createProductDTO.getWarrantyType() );
        product.status( createProductDTO.getStatus() );
        product.featured( createProductDTO.getFeatured() );
        product.freeShipping( createProductDTO.getFreeShipping() );

        return product.build();
    }

    @Override
    public List<ProductDTO> toDTOList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( products.size() );
        for ( Product product : products ) {
            list.add( toDTO( product ) );
        }

        return list;
    }

    @Override
    public List<Product> toEntityList(List<ProductDTO> productDTOs) {
        if ( productDTOs == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( productDTOs.size() );
        for ( ProductDTO productDTO : productDTOs ) {
            list.add( toEntity( productDTO ) );
        }

        return list;
    }

    @Override
    public ProductImageDTO toImageDTO(ProductImage productImage) {
        if ( productImage == null ) {
            return null;
        }

        ProductImageDTO.ProductImageDTOBuilder productImageDTO = ProductImageDTO.builder();

        productImageDTO.id( productImage.getId() );
        productImageDTO.productId( productImage.getProductId() );
        productImageDTO.imageUrl( productImage.getImageUrl() );
        productImageDTO.thumbnailUrl( productImage.getThumbnailUrl() );
        productImageDTO.altText( productImage.getAltText() );
        productImageDTO.title( productImage.getTitle() );
        productImageDTO.isPrimary( productImage.getIsPrimary() );
        productImageDTO.sortOrder( productImage.getSortOrder() );
        productImageDTO.fileSize( productImage.getFileSize() );
        productImageDTO.width( productImage.getWidth() );
        productImageDTO.height( productImage.getHeight() );
        productImageDTO.mimeType( productImage.getMimeType() );
        productImageDTO.fileExtension( productImage.getFileExtension() );
        productImageDTO.createdAt( productImage.getCreatedAt() );
        productImageDTO.updatedAt( productImage.getUpdatedAt() );

        return productImageDTO.build();
    }

    @Override
    public ProductImage toImageEntity(ProductImageDTO productImageDTO) {
        if ( productImageDTO == null ) {
            return null;
        }

        ProductImage.ProductImageBuilder productImage = ProductImage.builder();

        productImage.id( productImageDTO.getId() );
        productImage.productId( productImageDTO.getProductId() );
        productImage.imageUrl( productImageDTO.getImageUrl() );
        productImage.thumbnailUrl( productImageDTO.getThumbnailUrl() );
        productImage.altText( productImageDTO.getAltText() );
        productImage.title( productImageDTO.getTitle() );
        productImage.isPrimary( productImageDTO.getIsPrimary() );
        productImage.sortOrder( productImageDTO.getSortOrder() );
        productImage.fileSize( productImageDTO.getFileSize() );
        productImage.width( productImageDTO.getWidth() );
        productImage.height( productImageDTO.getHeight() );
        productImage.mimeType( productImageDTO.getMimeType() );
        productImage.fileExtension( productImageDTO.getFileExtension() );
        productImage.createdAt( productImageDTO.getCreatedAt() );
        productImage.updatedAt( productImageDTO.getUpdatedAt() );

        return productImage.build();
    }

    @Override
    public List<ProductImageDTO> toImageDTOList(List<ProductImage> productImages) {
        if ( productImages == null ) {
            return null;
        }

        List<ProductImageDTO> list = new ArrayList<ProductImageDTO>( productImages.size() );
        for ( ProductImage productImage : productImages ) {
            list.add( toImageDTO( productImage ) );
        }

        return list;
    }

    @Override
    public List<ProductImage> toImageEntityList(List<ProductImageDTO> productImageDTOs) {
        if ( productImageDTOs == null ) {
            return null;
        }

        List<ProductImage> list = new ArrayList<ProductImage>( productImageDTOs.size() );
        for ( ProductImageDTO productImageDTO : productImageDTOs ) {
            list.add( toImageEntity( productImageDTO ) );
        }

        return list;
    }

    @Override
    public ProductAttributeDTO toAttributeDTO(ProductAttribute productAttribute) {
        if ( productAttribute == null ) {
            return null;
        }

        ProductAttributeDTO.ProductAttributeDTOBuilder productAttributeDTO = ProductAttributeDTO.builder();

        productAttributeDTO.id( productAttribute.getId() );
        productAttributeDTO.productId( productAttribute.getProductId() );
        productAttributeDTO.name( productAttribute.getName() );
        productAttributeDTO.value( productAttribute.getValue() );
        productAttributeDTO.displayName( productAttribute.getDisplayName() );
        productAttributeDTO.attributeType( productAttribute.getAttributeType() );
        productAttributeDTO.isRequired( productAttribute.getIsRequired() );
        productAttributeDTO.isFilterable( productAttribute.getIsFilterable() );
        productAttributeDTO.isSearchable( productAttribute.getIsSearchable() );
        productAttributeDTO.sortOrder( productAttribute.getSortOrder() );
        productAttributeDTO.unit( productAttribute.getUnit() );
        productAttributeDTO.minValue( productAttribute.getMinValue() );
        productAttributeDTO.maxValue( productAttribute.getMaxValue() );
        if ( productAttribute.hasOptions() ) {
            productAttributeDTO.options( productAttribute.getOptions() );
        }
        productAttributeDTO.description( productAttribute.getDescription() );
        productAttributeDTO.createdAt( productAttribute.getCreatedAt() );
        productAttributeDTO.updatedAt( productAttribute.getUpdatedAt() );

        return productAttributeDTO.build();
    }

    @Override
    public ProductAttribute toAttributeEntity(ProductAttributeDTO productAttributeDTO) {
        if ( productAttributeDTO == null ) {
            return null;
        }

        ProductAttribute.ProductAttributeBuilder productAttribute = ProductAttribute.builder();

        productAttribute.id( productAttributeDTO.getId() );
        productAttribute.productId( productAttributeDTO.getProductId() );
        productAttribute.name( productAttributeDTO.getName() );
        productAttribute.value( productAttributeDTO.getValue() );
        productAttribute.displayName( productAttributeDTO.getDisplayName() );
        productAttribute.attributeType( productAttributeDTO.getAttributeType() );
        productAttribute.isRequired( productAttributeDTO.getIsRequired() );
        productAttribute.isFilterable( productAttributeDTO.getIsFilterable() );
        productAttribute.isSearchable( productAttributeDTO.getIsSearchable() );
        productAttribute.sortOrder( productAttributeDTO.getSortOrder() );
        productAttribute.unit( productAttributeDTO.getUnit() );
        productAttribute.minValue( productAttributeDTO.getMinValue() );
        productAttribute.maxValue( productAttributeDTO.getMaxValue() );
        productAttribute.options( productAttributeDTO.getOptions() );
        productAttribute.description( productAttributeDTO.getDescription() );
        productAttribute.createdAt( productAttributeDTO.getCreatedAt() );
        productAttribute.updatedAt( productAttributeDTO.getUpdatedAt() );

        return productAttribute.build();
    }

    @Override
    public List<ProductAttributeDTO> toAttributeDTOList(List<ProductAttribute> productAttributes) {
        if ( productAttributes == null ) {
            return null;
        }

        List<ProductAttributeDTO> list = new ArrayList<ProductAttributeDTO>( productAttributes.size() );
        for ( ProductAttribute productAttribute : productAttributes ) {
            list.add( toAttributeDTO( productAttribute ) );
        }

        return list;
    }

    @Override
    public List<ProductAttribute> toAttributeEntityList(List<ProductAttributeDTO> productAttributeDTOs) {
        if ( productAttributeDTOs == null ) {
            return null;
        }

        List<ProductAttribute> list = new ArrayList<ProductAttribute>( productAttributeDTOs.size() );
        for ( ProductAttributeDTO productAttributeDTO : productAttributeDTOs ) {
            list.add( toAttributeEntity( productAttributeDTO ) );
        }

        return list;
    }

    @Override
    public ProductVariantDTO toVariantDTO(ProductVariant productVariant) {
        if ( productVariant == null ) {
            return null;
        }

        ProductVariantDTO.ProductVariantDTOBuilder productVariantDTO = ProductVariantDTO.builder();

        productVariantDTO.id( productVariant.getId() );
        productVariantDTO.productId( productVariant.getProductId() );
        productVariantDTO.sku( productVariant.getSku() );
        productVariantDTO.variantName( productVariant.getVariantName() );
        productVariantDTO.variantDescription( productVariant.getVariantDescription() );
        productVariantDTO.price( productVariant.getPrice() );
        productVariantDTO.originalPrice( productVariant.getOriginalPrice() );
        productVariantDTO.costPrice( productVariant.getCostPrice() );
        productVariantDTO.stockQuantity( productVariant.getStockQuantity() );
        productVariantDTO.minStockAlert( productVariant.getMinStockAlert() );
        productVariantDTO.weight( productVariant.getWeight() );
        productVariantDTO.dimensions( productVariant.getDimensions() );
        productVariantDTO.barcode( productVariant.getBarcode() );
        productVariantDTO.imageUrl( productVariant.getImageUrl() );
        productVariantDTO.attributes( productVariant.getAttributes() );
        productVariantDTO.status( productVariant.getStatus() );
        productVariantDTO.isDefault( productVariant.getIsDefault() );
        productVariantDTO.sortOrder( productVariant.getSortOrder() );
        productVariantDTO.createdAt( productVariant.getCreatedAt() );
        productVariantDTO.updatedAt( productVariant.getUpdatedAt() );

        return productVariantDTO.build();
    }

    @Override
    public ProductVariant toVariantEntity(ProductVariantDTO productVariantDTO) {
        if ( productVariantDTO == null ) {
            return null;
        }

        ProductVariant.ProductVariantBuilder productVariant = ProductVariant.builder();

        productVariant.id( productVariantDTO.getId() );
        productVariant.productId( productVariantDTO.getProductId() );
        productVariant.sku( productVariantDTO.getSku() );
        productVariant.variantName( productVariantDTO.getVariantName() );
        productVariant.variantDescription( productVariantDTO.getVariantDescription() );
        productVariant.price( productVariantDTO.getPrice() );
        productVariant.originalPrice( productVariantDTO.getOriginalPrice() );
        productVariant.costPrice( productVariantDTO.getCostPrice() );
        productVariant.stockQuantity( productVariantDTO.getStockQuantity() );
        productVariant.minStockAlert( productVariantDTO.getMinStockAlert() );
        productVariant.weight( productVariantDTO.getWeight() );
        productVariant.dimensions( productVariantDTO.getDimensions() );
        productVariant.barcode( productVariantDTO.getBarcode() );
        productVariant.imageUrl( productVariantDTO.getImageUrl() );
        productVariant.attributes( productVariantDTO.getAttributes() );
        productVariant.status( productVariantDTO.getStatus() );
        productVariant.isDefault( productVariantDTO.getIsDefault() );
        productVariant.sortOrder( productVariantDTO.getSortOrder() );
        productVariant.createdAt( productVariantDTO.getCreatedAt() );
        productVariant.updatedAt( productVariantDTO.getUpdatedAt() );

        return productVariant.build();
    }

    @Override
    public List<ProductVariantDTO> toVariantDTOList(List<ProductVariant> productVariants) {
        if ( productVariants == null ) {
            return null;
        }

        List<ProductVariantDTO> list = new ArrayList<ProductVariantDTO>( productVariants.size() );
        for ( ProductVariant productVariant : productVariants ) {
            list.add( toVariantDTO( productVariant ) );
        }

        return list;
    }

    @Override
    public List<ProductVariant> toVariantEntityList(List<ProductVariantDTO> productVariantDTOs) {
        if ( productVariantDTOs == null ) {
            return null;
        }

        List<ProductVariant> list = new ArrayList<ProductVariant>( productVariantDTOs.size() );
        for ( ProductVariantDTO productVariantDTO : productVariantDTOs ) {
            list.add( toVariantEntity( productVariantDTO ) );
        }

        return list;
    }

    @Override
    public void updateEntity(UpdateProductDTO updateProductDTO, Product product) {
        if ( updateProductDTO == null ) {
            return;
        }

        if ( updateProductDTO.getSellerId() != null ) {
            product.setSellerId( updateProductDTO.getSellerId() );
        }
        if ( updateProductDTO.getCategoryId() != null ) {
            product.setCategoryId( updateProductDTO.getCategoryId() );
        }
        if ( updateProductDTO.getName() != null ) {
            product.setName( updateProductDTO.getName() );
        }
        if ( updateProductDTO.getSlug() != null ) {
            product.setSlug( updateProductDTO.getSlug() );
        }
        if ( updateProductDTO.getDescription() != null ) {
            product.setDescription( updateProductDTO.getDescription() );
        }
        if ( updateProductDTO.getShortDescription() != null ) {
            product.setShortDescription( updateProductDTO.getShortDescription() );
        }
        if ( updateProductDTO.getPrice() != null ) {
            product.setPrice( updateProductDTO.getPrice() );
        }
        if ( updateProductDTO.getOriginalPrice() != null ) {
            product.setOriginalPrice( updateProductDTO.getOriginalPrice() );
        }
        if ( updateProductDTO.getSku() != null ) {
            product.setSku( updateProductDTO.getSku() );
        }
        if ( updateProductDTO.getStockQuantity() != null ) {
            product.setStockQuantity( updateProductDTO.getStockQuantity() );
        }
        if ( updateProductDTO.getMinStockAlert() != null ) {
            product.setMinStockAlert( updateProductDTO.getMinStockAlert() );
        }
        if ( updateProductDTO.getWeight() != null ) {
            product.setWeight( updateProductDTO.getWeight() );
        }
        if ( updateProductDTO.getDimensions() != null ) {
            product.setDimensions( updateProductDTO.getDimensions() );
        }
        if ( updateProductDTO.getBrand() != null ) {
            product.setBrand( updateProductDTO.getBrand() );
        }
        if ( updateProductDTO.getModel() != null ) {
            product.setModel( updateProductDTO.getModel() );
        }
        if ( updateProductDTO.getWarrantyPeriod() != null ) {
            product.setWarrantyPeriod( updateProductDTO.getWarrantyPeriod() );
        }
        if ( updateProductDTO.getWarrantyType() != null ) {
            product.setWarrantyType( updateProductDTO.getWarrantyType() );
        }
        if ( updateProductDTO.getStatus() != null ) {
            product.setStatus( updateProductDTO.getStatus() );
        }
        if ( updateProductDTO.getFeatured() != null ) {
            product.setFeatured( updateProductDTO.getFeatured() );
        }
        if ( updateProductDTO.getFreeShipping() != null ) {
            product.setFreeShipping( updateProductDTO.getFreeShipping() );
        }
    }
}
