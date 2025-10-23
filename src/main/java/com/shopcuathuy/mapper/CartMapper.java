package com.shopcuathuy.mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;

import com.shopcuathuy.dto.CartDTO;
import com.shopcuathuy.entity.Cart;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.mapstruct.Mapper;
/**
 * Cart Mapper
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productImage", ignore = true)
    @Mapping(target = "productVariantId", source = "productVariant.id")
    @Mapping(target = "variantName", ignore = true)
    CartDTO toDTO(Cart cart);

    List<CartDTO> toDTOList(List<Cart> carts);
}
