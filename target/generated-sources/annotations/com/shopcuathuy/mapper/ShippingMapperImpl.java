package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.ShippingDTO;
import com.shopcuathuy.entity.Order;
import com.shopcuathuy.entity.Shipping;
import com.shopcuathuy.entity.ShippingMethod;
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
public class ShippingMapperImpl implements ShippingMapper {

    @Override
    public ShippingDTO toDTO(Shipping shipping) {
        if ( shipping == null ) {
            return null;
        }

        ShippingDTO shippingDTO = new ShippingDTO();

        String id = shippingOrderId( shipping );
        if ( id != null ) {
            shippingDTO.setOrderId( Long.parseLong( id ) );
        }
        shippingDTO.setOrderNumber( shippingOrderOrderNumber( shipping ) );
        String id1 = shippingShippingMethodId( shipping );
        if ( id1 != null ) {
            shippingDTO.setShippingMethodId( Long.parseLong( id1 ) );
        }
        shippingDTO.setShippingMethodName( shippingShippingMethodName( shipping ) );
        shippingDTO.setId( shipping.getId() );
        shippingDTO.setTrackingNumber( shipping.getTrackingNumber() );
        shippingDTO.setCarrier( shipping.getCarrier() );
        shippingDTO.setStatus( shipping.getStatus() );
        shippingDTO.setShippingCost( shipping.getShippingCost() );
        shippingDTO.setEstimatedDeliveryDate( shipping.getEstimatedDeliveryDate() );
        shippingDTO.setActualDeliveryDate( shipping.getActualDeliveryDate() );
        shippingDTO.setShippingAddress( shipping.getShippingAddress() );
        shippingDTO.setRecipientName( shipping.getRecipientName() );
        shippingDTO.setRecipientPhone( shipping.getRecipientPhone() );
        shippingDTO.setNotes( shipping.getNotes() );
        shippingDTO.setTrackingUrl( shipping.getTrackingUrl() );
        shippingDTO.setDeliveryProof( shipping.getDeliveryProof() );
        shippingDTO.setCreatedAt( shipping.getCreatedAt() );
        shippingDTO.setUpdatedAt( shipping.getUpdatedAt() );

        return shippingDTO;
    }

    @Override
    public List<ShippingDTO> toDTOList(List<Shipping> shippings) {
        if ( shippings == null ) {
            return null;
        }

        List<ShippingDTO> list = new ArrayList<ShippingDTO>( shippings.size() );
        for ( Shipping shipping : shippings ) {
            list.add( toDTO( shipping ) );
        }

        return list;
    }

    private String shippingOrderId(Shipping shipping) {
        if ( shipping == null ) {
            return null;
        }
        Order order = shipping.getOrder();
        if ( order == null ) {
            return null;
        }
        String id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String shippingOrderOrderNumber(Shipping shipping) {
        if ( shipping == null ) {
            return null;
        }
        Order order = shipping.getOrder();
        if ( order == null ) {
            return null;
        }
        String orderNumber = order.getOrderNumber();
        if ( orderNumber == null ) {
            return null;
        }
        return orderNumber;
    }

    private String shippingShippingMethodId(Shipping shipping) {
        if ( shipping == null ) {
            return null;
        }
        ShippingMethod shippingMethod = shipping.getShippingMethod();
        if ( shippingMethod == null ) {
            return null;
        }
        String id = shippingMethod.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String shippingShippingMethodName(Shipping shipping) {
        if ( shipping == null ) {
            return null;
        }
        ShippingMethod shippingMethod = shipping.getShippingMethod();
        if ( shippingMethod == null ) {
            return null;
        }
        String name = shippingMethod.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
