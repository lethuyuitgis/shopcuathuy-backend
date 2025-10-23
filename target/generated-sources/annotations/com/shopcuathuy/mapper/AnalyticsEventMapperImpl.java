package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.AnalyticsEventDTO;
import com.shopcuathuy.entity.AnalyticsEvent;
import com.shopcuathuy.entity.Order;
import com.shopcuathuy.entity.Product;
import com.shopcuathuy.entity.User;
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
public class AnalyticsEventMapperImpl implements AnalyticsEventMapper {

    @Override
    public AnalyticsEventDTO toDTO(AnalyticsEvent event) {
        if ( event == null ) {
            return null;
        }

        AnalyticsEventDTO analyticsEventDTO = new AnalyticsEventDTO();

        String id = eventUserId( event );
        if ( id != null ) {
            analyticsEventDTO.setUserId( Long.parseLong( id ) );
        }
        analyticsEventDTO.setUserName( eventUserName( event ) );
        String id1 = eventProductId( event );
        if ( id1 != null ) {
            analyticsEventDTO.setProductId( Long.parseLong( id1 ) );
        }
        analyticsEventDTO.setProductName( eventProductName( event ) );
        String id2 = eventOrderId( event );
        if ( id2 != null ) {
            analyticsEventDTO.setOrderId( Long.parseLong( id2 ) );
        }
        analyticsEventDTO.setId( event.getId() );
        analyticsEventDTO.setEventName( event.getEventName() );
        analyticsEventDTO.setEventType( event.getEventType() );
        analyticsEventDTO.setEventData( event.getEventData() );
        analyticsEventDTO.setSessionId( event.getSessionId() );
        analyticsEventDTO.setIpAddress( event.getIpAddress() );
        analyticsEventDTO.setUserAgent( event.getUserAgent() );
        analyticsEventDTO.setReferrer( event.getReferrer() );
        analyticsEventDTO.setValue( event.getValue() );
        analyticsEventDTO.setProperties( event.getProperties() );
        analyticsEventDTO.setCreatedAt( event.getCreatedAt() );

        return analyticsEventDTO;
    }

    @Override
    public List<AnalyticsEventDTO> toDTOList(List<AnalyticsEvent> events) {
        if ( events == null ) {
            return null;
        }

        List<AnalyticsEventDTO> list = new ArrayList<AnalyticsEventDTO>( events.size() );
        for ( AnalyticsEvent analyticsEvent : events ) {
            list.add( toDTO( analyticsEvent ) );
        }

        return list;
    }

    private String eventUserId(AnalyticsEvent analyticsEvent) {
        if ( analyticsEvent == null ) {
            return null;
        }
        User user = analyticsEvent.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String eventUserName(AnalyticsEvent analyticsEvent) {
        if ( analyticsEvent == null ) {
            return null;
        }
        User user = analyticsEvent.getUser();
        if ( user == null ) {
            return null;
        }
        String name = user.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String eventProductId(AnalyticsEvent analyticsEvent) {
        if ( analyticsEvent == null ) {
            return null;
        }
        Product product = analyticsEvent.getProduct();
        if ( product == null ) {
            return null;
        }
        String id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String eventProductName(AnalyticsEvent analyticsEvent) {
        if ( analyticsEvent == null ) {
            return null;
        }
        Product product = analyticsEvent.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String eventOrderId(AnalyticsEvent analyticsEvent) {
        if ( analyticsEvent == null ) {
            return null;
        }
        Order order = analyticsEvent.getOrder();
        if ( order == null ) {
            return null;
        }
        String id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
