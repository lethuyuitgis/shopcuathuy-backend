package com.shopcuathuy.mapper;
import org.mapstruct.MappingTarget;

import com.shopcuathuy.dto.CouponDTO;
import com.shopcuathuy.entity.Coupon;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

/**
 * Coupon Mapper
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CouponMapper {

    CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);

    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "createdByName", source = "createdBy.name")
    CouponDTO toDTO(Coupon coupon);

    List<CouponDTO> toDTOList(List<Coupon> coupons);
}
