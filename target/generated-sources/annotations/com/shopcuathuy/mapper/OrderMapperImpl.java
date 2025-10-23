package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.CreateOrderDTO;
import com.shopcuathuy.dto.OrderDTO;
import com.shopcuathuy.dto.UpdateOrderDTO;
import com.shopcuathuy.entity.Order;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T17:09:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDTO toDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId( order.getId() );
        orderDTO.setOrderNumber( order.getOrderNumber() );
        orderDTO.setUserId( order.getUserId() );
        orderDTO.setSellerId( order.getSellerId() );
        orderDTO.setSubtotal( order.getSubtotal() );
        orderDTO.setTaxAmount( order.getTaxAmount() );
        orderDTO.setShippingCost( order.getShippingCost() );
        orderDTO.setDiscountAmount( order.getDiscountAmount() );
        orderDTO.setTotalAmount( order.getTotalAmount() );
        orderDTO.setStatus( order.getStatus() );
        orderDTO.setPaymentStatus( order.getPaymentStatus() );
        orderDTO.setPaymentMethod( order.getPaymentMethod() );
        orderDTO.setPaymentReference( order.getPaymentReference() );
        orderDTO.setShippingMethod( order.getShippingMethod() );
        orderDTO.setTrackingNumber( order.getTrackingNumber() );
        orderDTO.setShippingAddressId( order.getShippingAddressId() );
        orderDTO.setBillingAddressId( order.getBillingAddressId() );
        orderDTO.setNotes( order.getNotes() );
        orderDTO.setCancellationReason( order.getCancellationReason() );
        orderDTO.setCreatedAt( order.getCreatedAt() );
        orderDTO.setUpdatedAt( order.getUpdatedAt() );
        orderDTO.setShippedAt( order.getShippedAt() );
        orderDTO.setDeliveredAt( order.getDeliveredAt() );
        orderDTO.setCancelledAt( order.getCancelledAt() );

        return orderDTO;
    }

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.id( orderDTO.getId() );
        order.orderNumber( orderDTO.getOrderNumber() );
        order.userId( orderDTO.getUserId() );
        order.sellerId( orderDTO.getSellerId() );
        order.shippingAddressId( orderDTO.getShippingAddressId() );
        order.billingAddressId( orderDTO.getBillingAddressId() );
        order.subtotal( orderDTO.getSubtotal() );
        order.taxAmount( orderDTO.getTaxAmount() );
        order.shippingCost( orderDTO.getShippingCost() );
        order.discountAmount( orderDTO.getDiscountAmount() );
        order.totalAmount( orderDTO.getTotalAmount() );
        order.status( orderDTO.getStatus() );
        order.paymentStatus( orderDTO.getPaymentStatus() );
        order.paymentMethod( orderDTO.getPaymentMethod() );
        order.paymentReference( orderDTO.getPaymentReference() );
        order.shippingMethod( orderDTO.getShippingMethod() );
        order.trackingNumber( orderDTO.getTrackingNumber() );
        order.notes( orderDTO.getNotes() );
        order.shippedAt( orderDTO.getShippedAt() );
        order.deliveredAt( orderDTO.getDeliveredAt() );
        order.cancelledAt( orderDTO.getCancelledAt() );
        order.cancellationReason( orderDTO.getCancellationReason() );
        order.createdAt( orderDTO.getCreatedAt() );
        order.updatedAt( orderDTO.getUpdatedAt() );

        return order.build();
    }

    @Override
    public Order toEntity(CreateOrderDTO createOrderDTO) {
        if ( createOrderDTO == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.userId( createOrderDTO.getUserId() );
        order.sellerId( createOrderDTO.getSellerId() );
        order.shippingAddressId( createOrderDTO.getShippingAddressId() );
        order.billingAddressId( createOrderDTO.getBillingAddressId() );
        order.subtotal( createOrderDTO.getSubtotal() );
        order.taxAmount( createOrderDTO.getTaxAmount() );
        order.shippingCost( createOrderDTO.getShippingCost() );
        order.discountAmount( createOrderDTO.getDiscountAmount() );
        order.totalAmount( createOrderDTO.getTotalAmount() );
        order.status( createOrderDTO.getStatus() );
        order.paymentStatus( createOrderDTO.getPaymentStatus() );
        order.paymentMethod( createOrderDTO.getPaymentMethod() );
        order.paymentReference( createOrderDTO.getPaymentReference() );
        order.shippingMethod( createOrderDTO.getShippingMethod() );
        order.notes( createOrderDTO.getNotes() );

        return order.build();
    }

    @Override
    public void updateEntity(UpdateOrderDTO updateOrderDTO, Order order) {
        if ( updateOrderDTO == null ) {
            return;
        }

        if ( updateOrderDTO.getUserId() != null ) {
            order.setUserId( updateOrderDTO.getUserId() );
        }
        if ( updateOrderDTO.getSellerId() != null ) {
            order.setSellerId( updateOrderDTO.getSellerId() );
        }
        if ( updateOrderDTO.getShippingAddressId() != null ) {
            order.setShippingAddressId( updateOrderDTO.getShippingAddressId() );
        }
        if ( updateOrderDTO.getBillingAddressId() != null ) {
            order.setBillingAddressId( updateOrderDTO.getBillingAddressId() );
        }
        if ( updateOrderDTO.getSubtotal() != null ) {
            order.setSubtotal( updateOrderDTO.getSubtotal() );
        }
        if ( updateOrderDTO.getTaxAmount() != null ) {
            order.setTaxAmount( updateOrderDTO.getTaxAmount() );
        }
        if ( updateOrderDTO.getShippingCost() != null ) {
            order.setShippingCost( updateOrderDTO.getShippingCost() );
        }
        if ( updateOrderDTO.getDiscountAmount() != null ) {
            order.setDiscountAmount( updateOrderDTO.getDiscountAmount() );
        }
        if ( updateOrderDTO.getTotalAmount() != null ) {
            order.setTotalAmount( updateOrderDTO.getTotalAmount() );
        }
        if ( updateOrderDTO.getStatus() != null ) {
            order.setStatus( updateOrderDTO.getStatus() );
        }
        if ( updateOrderDTO.getPaymentStatus() != null ) {
            order.setPaymentStatus( updateOrderDTO.getPaymentStatus() );
        }
        if ( updateOrderDTO.getPaymentMethod() != null ) {
            order.setPaymentMethod( updateOrderDTO.getPaymentMethod() );
        }
        if ( updateOrderDTO.getPaymentReference() != null ) {
            order.setPaymentReference( updateOrderDTO.getPaymentReference() );
        }
        if ( updateOrderDTO.getShippingMethod() != null ) {
            order.setShippingMethod( updateOrderDTO.getShippingMethod() );
        }
        if ( updateOrderDTO.getTrackingNumber() != null ) {
            order.setTrackingNumber( updateOrderDTO.getTrackingNumber() );
        }
        if ( updateOrderDTO.getNotes() != null ) {
            order.setNotes( updateOrderDTO.getNotes() );
        }
        if ( updateOrderDTO.getCancellationReason() != null ) {
            order.setCancellationReason( updateOrderDTO.getCancellationReason() );
        }
    }

    @Override
    public List<OrderDTO> toDTOList(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderDTO> list = new ArrayList<OrderDTO>( orders.size() );
        for ( Order order : orders ) {
            list.add( toDTO( order ) );
        }

        return list;
    }

    @Override
    public List<Order> toEntityList(List<OrderDTO> orderDTOs) {
        if ( orderDTOs == null ) {
            return null;
        }

        List<Order> list = new ArrayList<Order>( orderDTOs.size() );
        for ( OrderDTO orderDTO : orderDTOs ) {
            list.add( toEntity( orderDTO ) );
        }

        return list;
    }
}
