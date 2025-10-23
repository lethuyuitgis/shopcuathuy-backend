package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.CouponDTO;
import com.shopcuathuy.entity.Coupon;
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
public class CouponMapperImpl implements CouponMapper {

    @Override
    public CouponDTO toDTO(Coupon coupon) {
        if ( coupon == null ) {
            return null;
        }

        CouponDTO couponDTO = new CouponDTO();

        String id = couponCreatedById( coupon );
        if ( id != null ) {
            couponDTO.setCreatedById( Long.parseLong( id ) );
        }
        couponDTO.setCreatedByName( couponCreatedByName( coupon ) );
        couponDTO.setId( coupon.getId() );
        couponDTO.setCode( coupon.getCode() );
        couponDTO.setName( coupon.getName() );
        couponDTO.setDescription( coupon.getDescription() );
        couponDTO.setType( coupon.getType() );
        couponDTO.setValue( coupon.getValue() );
        couponDTO.setMinimumOrderAmount( coupon.getMinimumOrderAmount() );
        couponDTO.setMaximumDiscountAmount( coupon.getMaximumDiscountAmount() );
        couponDTO.setUsageLimit( coupon.getUsageLimit() );
        couponDTO.setUsedCount( coupon.getUsedCount() );
        couponDTO.setUsageLimitPerUser( coupon.getUsageLimitPerUser() );
        couponDTO.setIsActive( coupon.getIsActive() );
        couponDTO.setStartDate( coupon.getStartDate() );
        couponDTO.setEndDate( coupon.getEndDate() );
        couponDTO.setTermsAndConditions( coupon.getTermsAndConditions() );
        couponDTO.setIsPublic( coupon.getIsPublic() );
        couponDTO.setApplicableProducts( coupon.getApplicableProducts() );
        couponDTO.setApplicableCategories( coupon.getApplicableCategories() );
        couponDTO.setCreatedAt( coupon.getCreatedAt() );
        couponDTO.setUpdatedAt( coupon.getUpdatedAt() );

        return couponDTO;
    }

    @Override
    public List<CouponDTO> toDTOList(List<Coupon> coupons) {
        if ( coupons == null ) {
            return null;
        }

        List<CouponDTO> list = new ArrayList<CouponDTO>( coupons.size() );
        for ( Coupon coupon : coupons ) {
            list.add( toDTO( coupon ) );
        }

        return list;
    }

    private String couponCreatedById(Coupon coupon) {
        if ( coupon == null ) {
            return null;
        }
        User createdBy = coupon.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        String id = createdBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String couponCreatedByName(Coupon coupon) {
        if ( coupon == null ) {
            return null;
        }
        User createdBy = coupon.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        String name = createdBy.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
