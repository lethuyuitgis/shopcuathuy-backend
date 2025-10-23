package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.SellerDTO;
import com.shopcuathuy.entity.Seller;
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
public class SellerMapperImpl implements SellerMapper {

    @Override
    public SellerDTO toDTO(Seller seller) {
        if ( seller == null ) {
            return null;
        }

        SellerDTO sellerDTO = new SellerDTO();

        if ( seller.getUserId() != null ) {
            sellerDTO.setUserId( Long.parseLong( seller.getUserId() ) );
        }
        if ( seller.getId() != null ) {
            sellerDTO.setId( Long.parseLong( seller.getId() ) );
        }
        sellerDTO.setBusinessLicense( seller.getBusinessLicense() );
        sellerDTO.setBankAccount( seller.getBankAccount() );
        sellerDTO.setBankName( seller.getBankName() );
        if ( seller.getAverageRating() != null ) {
            sellerDTO.setAverageRating( seller.getAverageRating().doubleValue() );
        }
        sellerDTO.setCreatedAt( seller.getCreatedAt() );
        sellerDTO.setUpdatedAt( seller.getUpdatedAt() );

        return sellerDTO;
    }

    @Override
    public List<SellerDTO> toDTOList(List<Seller> sellers) {
        if ( sellers == null ) {
            return null;
        }

        List<SellerDTO> list = new ArrayList<SellerDTO>( sellers.size() );
        for ( Seller seller : sellers ) {
            list.add( toDTO( seller ) );
        }

        return list;
    }
}
