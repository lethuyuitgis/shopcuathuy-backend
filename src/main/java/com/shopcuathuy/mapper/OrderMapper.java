package com.shopcuathuy.mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.shopcuathuy.dto.CreateOrderDTO;
import com.shopcuathuy.dto.OrderDTO;
import com.shopcuathuy.dto.UpdateOrderDTO;
import com.shopcuathuy.entity.Order;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapper;


/**
 * Mapper interface for Order entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    /**
     * Convert Order entity to OrderDTO
     */
    OrderDTO toDTO(Order order);

    /**
     * Convert OrderDTO to Order entity
     */
    @Mapping(target = "orderItems", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    /**
     * Convert CreateOrderDTO to Order entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    @Mapping(target = "trackingNumber", ignore = true)
    @Mapping(target = "cancellationReason", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "shippedAt", ignore = true)
    @Mapping(target = "deliveredAt", ignore = true)
    @Mapping(target = "cancelledAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Order toEntity(CreateOrderDTO createOrderDTO);

    /**
     * Update Order entity from UpdateOrderDTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "shippedAt", ignore = true)
    @Mapping(target = "deliveredAt", ignore = true)
    @Mapping(target = "cancelledAt", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    void updateEntity(UpdateOrderDTO updateOrderDTO, @MappingTarget Order order);

    /**
     * Convert list of Order entities to list of OrderDTOs
     */
    List<OrderDTO> toDTOList(List<Order> orders);

    /**
     * Convert list of OrderDTOs to list of Order entities
     */
    List<Order> toEntityList(List<OrderDTO> orderDTOs);
}
