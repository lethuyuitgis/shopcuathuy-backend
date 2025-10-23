package com.shopcuathuy.mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

import com.shopcuathuy.dto.PaymentMethodDTO;
import com.shopcuathuy.entity.PaymentMethod;
import java.util.List;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;


/**
 * Payment Method Mapper
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMethodMapper {

    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);

    PaymentMethodDTO toDTO(PaymentMethod paymentMethod);

    List<PaymentMethodDTO> toDTOList(List<PaymentMethod> paymentMethods);
}
