package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.AnalyticsEventDTO;
import com.shopcuathuy.dto.CreateAnalyticsEventDTO;
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
    date = "2025-10-23T18:12:40+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class AnalyticsEventMapperImpl implements AnalyticsEventMapper {

    @Override
    public AnalyticsEventDTO toDTO(AnalyticsEvent analyticsEvent) {
        if ( analyticsEvent == null ) {
            return null;
        }

        AnalyticsEventDTO.AnalyticsEventDTOBuilder analyticsEventDTO = AnalyticsEventDTO.builder();

        String id = analyticsEventUserId( analyticsEvent );
        if ( id != null ) {
            analyticsEventDTO.userId( Long.parseLong( id ) );
        }
        String id1 = analyticsEventProductId( analyticsEvent );
        if ( id1 != null ) {
            analyticsEventDTO.productId( Long.parseLong( id1 ) );
        }
        String id2 = analyticsEventOrderId( analyticsEvent );
        if ( id2 != null ) {
            analyticsEventDTO.orderId( Long.parseLong( id2 ) );
        }
        analyticsEventDTO.id( analyticsEvent.getId() );
        analyticsEventDTO.eventName( analyticsEvent.getEventName() );
        analyticsEventDTO.eventType( analyticsEvent.getEventType() );
        analyticsEventDTO.eventData( analyticsEvent.getEventData() );
        analyticsEventDTO.sessionId( analyticsEvent.getSessionId() );
        analyticsEventDTO.ipAddress( analyticsEvent.getIpAddress() );
        analyticsEventDTO.userAgent( analyticsEvent.getUserAgent() );
        analyticsEventDTO.referrer( analyticsEvent.getReferrer() );
        analyticsEventDTO.value( analyticsEvent.getValue() );
        analyticsEventDTO.createdAt( analyticsEvent.getCreatedAt() );

        analyticsEventDTO.properties( analyticsEvent.getProperties() != null ? analyticsEvent.getProperties().toString() : null );

        return analyticsEventDTO.build();
    }

    @Override
    public AnalyticsEvent toEntity(AnalyticsEventDTO analyticsEventDTO) {
        if ( analyticsEventDTO == null ) {
            return null;
        }

        AnalyticsEvent.AnalyticsEventBuilder analyticsEvent = AnalyticsEvent.builder();

        analyticsEvent.id( analyticsEventDTO.getId() );
        analyticsEvent.eventName( analyticsEventDTO.getEventName() );
        analyticsEvent.eventType( analyticsEventDTO.getEventType() );
        analyticsEvent.eventData( analyticsEventDTO.getEventData() );
        analyticsEvent.sessionId( analyticsEventDTO.getSessionId() );
        analyticsEvent.ipAddress( analyticsEventDTO.getIpAddress() );
        analyticsEvent.userAgent( analyticsEventDTO.getUserAgent() );
        analyticsEvent.referrer( analyticsEventDTO.getReferrer() );
        analyticsEvent.value( analyticsEventDTO.getValue() );
        analyticsEvent.properties( analyticsEventDTO.getProperties() );
        analyticsEvent.createdAt( analyticsEventDTO.getCreatedAt() );

        return analyticsEvent.build();
    }

    @Override
    public AnalyticsEvent toEntity(CreateAnalyticsEventDTO createAnalyticsEventDTO) {
        if ( createAnalyticsEventDTO == null ) {
            return null;
        }

        AnalyticsEvent.AnalyticsEventBuilder analyticsEvent = AnalyticsEvent.builder();

        analyticsEvent.eventName( createAnalyticsEventDTO.getEventName() );
        analyticsEvent.eventType( createAnalyticsEventDTO.getEventType() );
        analyticsEvent.eventData( createAnalyticsEventDTO.getEventData() );
        analyticsEvent.sessionId( createAnalyticsEventDTO.getSessionId() );
        analyticsEvent.ipAddress( createAnalyticsEventDTO.getIpAddress() );
        analyticsEvent.userAgent( createAnalyticsEventDTO.getUserAgent() );
        analyticsEvent.referrer( createAnalyticsEventDTO.getReferrer() );
        analyticsEvent.value( createAnalyticsEventDTO.getValue() );

        return analyticsEvent.build();
    }

    @Override
    public void updateEntity(AnalyticsEventDTO analyticsEventDTO, AnalyticsEvent analyticsEvent) {
        if ( analyticsEventDTO == null ) {
            return;
        }

        if ( analyticsEventDTO.getId() != null ) {
            analyticsEvent.setId( analyticsEventDTO.getId() );
        }
        if ( analyticsEventDTO.getEventName() != null ) {
            analyticsEvent.setEventName( analyticsEventDTO.getEventName() );
        }
        if ( analyticsEventDTO.getEventType() != null ) {
            analyticsEvent.setEventType( analyticsEventDTO.getEventType() );
        }
        if ( analyticsEventDTO.getEventData() != null ) {
            analyticsEvent.setEventData( analyticsEventDTO.getEventData() );
        }
        if ( analyticsEventDTO.getSessionId() != null ) {
            analyticsEvent.setSessionId( analyticsEventDTO.getSessionId() );
        }
        if ( analyticsEventDTO.getIpAddress() != null ) {
            analyticsEvent.setIpAddress( analyticsEventDTO.getIpAddress() );
        }
        if ( analyticsEventDTO.getUserAgent() != null ) {
            analyticsEvent.setUserAgent( analyticsEventDTO.getUserAgent() );
        }
        if ( analyticsEventDTO.getReferrer() != null ) {
            analyticsEvent.setReferrer( analyticsEventDTO.getReferrer() );
        }
        if ( analyticsEventDTO.getValue() != null ) {
            analyticsEvent.setValue( analyticsEventDTO.getValue() );
        }
        if ( analyticsEventDTO.getCreatedAt() != null ) {
            analyticsEvent.setCreatedAt( analyticsEventDTO.getCreatedAt() );
        }
    }

    @Override
    public List<AnalyticsEventDTO> toDTOList(List<AnalyticsEvent> analyticsEvents) {
        if ( analyticsEvents == null ) {
            return null;
        }

        List<AnalyticsEventDTO> list = new ArrayList<AnalyticsEventDTO>( analyticsEvents.size() );
        for ( AnalyticsEvent analyticsEvent : analyticsEvents ) {
            list.add( toDTO( analyticsEvent ) );
        }

        return list;
    }

    private String analyticsEventUserId(AnalyticsEvent analyticsEvent) {
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

    private String analyticsEventProductId(AnalyticsEvent analyticsEvent) {
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

    private String analyticsEventOrderId(AnalyticsEvent analyticsEvent) {
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
