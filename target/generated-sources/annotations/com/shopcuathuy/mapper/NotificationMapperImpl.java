package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.NotificationDTO;
import com.shopcuathuy.entity.Notification;
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
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public NotificationDTO toDTO(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationDTO notificationDTO = new NotificationDTO();

        String id = notificationUserId( notification );
        if ( id != null ) {
            notificationDTO.setUserId( Long.parseLong( id ) );
        }
        notificationDTO.setId( notification.getId() );
        notificationDTO.setTitle( notification.getTitle() );
        notificationDTO.setMessage( notification.getMessage() );
        notificationDTO.setActionUrl( notification.getActionUrl() );
        notificationDTO.setType( notification.getType() );
        notificationDTO.setPriority( notification.getPriority() );
        notificationDTO.setIsRead( notification.getIsRead() );
        notificationDTO.setIsSent( notification.getIsSent() );
        notificationDTO.setSentAt( notification.getSentAt() );
        notificationDTO.setReadAt( notification.getReadAt() );
        notificationDTO.setMetadata( notification.getMetadata() );
        notificationDTO.setCreatedAt( notification.getCreatedAt() );
        notificationDTO.setUpdatedAt( notification.getUpdatedAt() );

        return notificationDTO;
    }

    @Override
    public List<NotificationDTO> toDTOList(List<Notification> notifications) {
        if ( notifications == null ) {
            return null;
        }

        List<NotificationDTO> list = new ArrayList<NotificationDTO>( notifications.size() );
        for ( Notification notification : notifications ) {
            list.add( toDTO( notification ) );
        }

        return list;
    }

    private String notificationUserId(Notification notification) {
        if ( notification == null ) {
            return null;
        }
        User user = notification.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
