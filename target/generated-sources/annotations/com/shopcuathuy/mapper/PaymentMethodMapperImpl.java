package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.PaymentMethodDTO;
import com.shopcuathuy.entity.PaymentMethod;
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
public class PaymentMethodMapperImpl implements PaymentMethodMapper {

    @Override
    public PaymentMethodDTO toDTO(PaymentMethod paymentMethod) {
        if ( paymentMethod == null ) {
            return null;
        }

        PaymentMethodDTO.PaymentMethodDTOBuilder paymentMethodDTO = PaymentMethodDTO.builder();

        paymentMethodDTO.id( paymentMethod.getId() );
        paymentMethodDTO.name( paymentMethod.getName() );
        paymentMethodDTO.code( paymentMethod.getCode() );
        paymentMethodDTO.description( paymentMethod.getDescription() );
        paymentMethodDTO.logoUrl( paymentMethod.getLogoUrl() );
        paymentMethodDTO.isActive( paymentMethod.getIsActive() );
        paymentMethodDTO.isOnline( paymentMethod.getIsOnline() );
        paymentMethodDTO.processingFeePercentage( paymentMethod.getProcessingFeePercentage() );
        paymentMethodDTO.processingFeeFixed( paymentMethod.getProcessingFeeFixed() );
        paymentMethodDTO.minAmount( paymentMethod.getMinAmount() );
        paymentMethodDTO.maxAmount( paymentMethod.getMaxAmount() );
        paymentMethodDTO.configJson( paymentMethod.getConfigJson() );
        paymentMethodDTO.sortOrder( paymentMethod.getSortOrder() );
        paymentMethodDTO.createdAt( paymentMethod.getCreatedAt() );
        paymentMethodDTO.updatedAt( paymentMethod.getUpdatedAt() );

        return paymentMethodDTO.build();
    }

    @Override
    public List<PaymentMethodDTO> toDTOList(List<PaymentMethod> paymentMethods) {
        if ( paymentMethods == null ) {
            return null;
        }

        List<PaymentMethodDTO> list = new ArrayList<PaymentMethodDTO>( paymentMethods.size() );
        for ( PaymentMethod paymentMethod : paymentMethods ) {
            list.add( toDTO( paymentMethod ) );
        }

        return list;
    }
}
