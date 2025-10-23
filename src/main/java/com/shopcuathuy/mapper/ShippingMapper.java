package com.shopcuathuy.mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;

import com.shopcuathuy.dto.ShippingDTO;
import com.shopcuathuy.entity.Shipping;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;


/**
 * Shipping Mapper
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface ShippingMapper {

    ShippingMapper INSTANCE = Mappers.getMapper(ShippingMapper.class);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "orderNumber", source = "order.orderNumber")
    @Mapping(target = "shippingMethodId", source = "shippingMethod.id")
    @Mapping(target = "shippingMethodName", source = "shippingMethod.name")
    ShippingDTO toDTO(Shipping shipping);

    List<ShippingDTO> toDTOList(List<Shipping> shippings);
}
