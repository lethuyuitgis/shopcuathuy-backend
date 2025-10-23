package com.shopcuathuy.mapper;
import org.mapstruct.MappingTarget;

import com.shopcuathuy.dto.ProductReviewDTO;
import com.shopcuathuy.entity.ProductReview;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


/**
 * Product Review Mapper
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductReviewMapper {

    ProductReviewMapper INSTANCE = Mappers.getMapper(ProductReviewMapper.class);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "userAvatar", source = "user.avatar")
    @Mapping(target = "orderId", source = "order.id")
    ProductReviewDTO toDTO(ProductReview review);

    List<ProductReviewDTO> toDTOList(List<ProductReview> reviews);
}
