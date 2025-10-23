package com.shopcuathuy.mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;

import com.shopcuathuy.dto.PaymentDTO;
import com.shopcuathuy.entity.Payment;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapper;


/**
 * Payment Mapper
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "paymentMethodId", source = "paymentMethod.id")
    @Mapping(target = "paymentMethodName", source = "paymentMethod.name")
    PaymentDTO toDTO(Payment payment);

    List<PaymentDTO> toDTOList(List<Payment> payments);
}
