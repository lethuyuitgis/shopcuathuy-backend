package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.WishlistDTO;
import com.shopcuathuy.entity.Product;
import com.shopcuathuy.entity.User;
import com.shopcuathuy.entity.Wishlist;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T18:11:55+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class WishlistMapperImpl implements WishlistMapper {

    @Override
    public WishlistDTO toDTO(Wishlist wishlist) {
        if ( wishlist == null ) {
            return null;
        }

        WishlistDTO wishlistDTO = new WishlistDTO();

        String id = wishlistUserId( wishlist );
        if ( id != null ) {
            wishlistDTO.setUserId( Long.parseLong( id ) );
        }
        String id1 = wishlistProductId( wishlist );
        if ( id1 != null ) {
            wishlistDTO.setProductId( Long.parseLong( id1 ) );
        }
        wishlistDTO.setProductName( wishlistProductName( wishlist ) );
        wishlistDTO.setProductPrice( wishlistProductPrice( wishlist ) );
        wishlistDTO.setProductSlug( wishlistProductSlug( wishlist ) );
        wishlistDTO.setId( wishlist.getId() );
        wishlistDTO.setNotes( wishlist.getNotes() );
        wishlistDTO.setIsActive( wishlist.getIsActive() );
        wishlistDTO.setCreatedAt( wishlist.getCreatedAt() );

        return wishlistDTO;
    }

    @Override
    public List<WishlistDTO> toDTOList(List<Wishlist> wishlists) {
        if ( wishlists == null ) {
            return null;
        }

        List<WishlistDTO> list = new ArrayList<WishlistDTO>( wishlists.size() );
        for ( Wishlist wishlist : wishlists ) {
            list.add( toDTO( wishlist ) );
        }

        return list;
    }

    private String wishlistUserId(Wishlist wishlist) {
        if ( wishlist == null ) {
            return null;
        }
        User user = wishlist.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String wishlistProductId(Wishlist wishlist) {
        if ( wishlist == null ) {
            return null;
        }
        Product product = wishlist.getProduct();
        if ( product == null ) {
            return null;
        }
        String id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String wishlistProductName(Wishlist wishlist) {
        if ( wishlist == null ) {
            return null;
        }
        Product product = wishlist.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal wishlistProductPrice(Wishlist wishlist) {
        if ( wishlist == null ) {
            return null;
        }
        Product product = wishlist.getProduct();
        if ( product == null ) {
            return null;
        }
        BigDecimal price = product.getPrice();
        if ( price == null ) {
            return null;
        }
        return price;
    }

    private String wishlistProductSlug(Wishlist wishlist) {
        if ( wishlist == null ) {
            return null;
        }
        Product product = wishlist.getProduct();
        if ( product == null ) {
            return null;
        }
        String slug = product.getSlug();
        if ( slug == null ) {
            return null;
        }
        return slug;
    }
}
