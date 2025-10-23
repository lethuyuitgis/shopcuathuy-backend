package com.shopcuathuy.mapper;
import org.mapstruct.MappingTarget;

import com.shopcuathuy.dto.SellerDTO;
import com.shopcuathuy.entity.Seller;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

/**
 * Seller Mapper
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SellerMapper {

    SellerMapper INSTANCE = Mappers.getMapper(SellerMapper.class);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "userEmail", ignore = true)
    SellerDTO toDTO(Seller seller);

    List<SellerDTO> toDTOList(List<Seller> sellers);
}
