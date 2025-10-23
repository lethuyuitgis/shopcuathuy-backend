package com.shopcuathuy.mapper;
import org.mapstruct.MappingTarget;

import com.shopcuathuy.dto.AnalyticsEventDTO;
import com.shopcuathuy.entity.AnalyticsEvent;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


/**
 * Analytics Event Mapper
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnalyticsEventMapper {

    AnalyticsEventMapper INSTANCE = Mappers.getMapper(AnalyticsEventMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "orderId", source = "order.id")
    AnalyticsEventDTO toDTO(AnalyticsEvent event);

    List<AnalyticsEventDTO> toDTOList(List<AnalyticsEvent> events);
}
