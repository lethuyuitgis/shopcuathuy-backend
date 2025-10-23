package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.AnalyticsEventDTO;
import com.shopcuathuy.dto.CreateAnalyticsEventDTO;
import com.shopcuathuy.entity.AnalyticsEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Analytics Event Mapper
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnalyticsEventMapper {
    
    /**
     * Convert AnalyticsEvent to AnalyticsEventDTO
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(target = "properties", expression = "java(analyticsEvent.getProperties() != null ? analyticsEvent.getProperties().toString() : null)")
    AnalyticsEventDTO toDTO(AnalyticsEvent analyticsEvent);
    
    /**
     * Convert AnalyticsEventDTO to AnalyticsEvent
     */
    AnalyticsEvent toEntity(AnalyticsEventDTO analyticsEventDTO);
    
    /**
     * Convert CreateAnalyticsEventDTO to AnalyticsEvent
     */
    @Mapping(target = "properties", ignore = true)
    AnalyticsEvent toEntity(CreateAnalyticsEventDTO createAnalyticsEventDTO);
    
    /**
     * Update AnalyticsEvent from AnalyticsEventDTO
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "properties", ignore = true)
    void updateEntity(AnalyticsEventDTO analyticsEventDTO, @MappingTarget AnalyticsEvent analyticsEvent);
    
    /**
     * Convert list of AnalyticsEvent to list of AnalyticsEventDTO
     */
    List<AnalyticsEventDTO> toDTOList(List<AnalyticsEvent> analyticsEvents);
}