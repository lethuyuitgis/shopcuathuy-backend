package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.CartDTO;
import com.shopcuathuy.entity.Cart;
import com.shopcuathuy.entity.Product;
import com.shopcuathuy.entity.ProductVariant;
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
public class CartMapperImpl implements CartMapper {

    @Override
    public CartDTO toDTO(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();

        String id = cartUserId( cart );
        if ( id != null ) {
            cartDTO.setUserId( Long.parseLong( id ) );
        }
        String id1 = cartProductId( cart );
        if ( id1 != null ) {
            cartDTO.setProductId( Long.parseLong( id1 ) );
        }
        cartDTO.setProductName( cartProductName( cart ) );
        String id2 = cartProductVariantId( cart );
        if ( id2 != null ) {
            cartDTO.setProductVariantId( Long.parseLong( id2 ) );
        }
        cartDTO.setId( cart.getId() );
        cartDTO.setQuantity( cart.getQuantity() );
        cartDTO.setUnitPrice( cart.getUnitPrice() );
        cartDTO.setTotalPrice( cart.getTotalPrice() );
        cartDTO.setNotes( cart.getNotes() );
        cartDTO.setIsActive( cart.getIsActive() );
        cartDTO.setCreatedAt( cart.getCreatedAt() );
        cartDTO.setUpdatedAt( cart.getUpdatedAt() );

        return cartDTO;
    }

    @Override
    public List<CartDTO> toDTOList(List<Cart> carts) {
        if ( carts == null ) {
            return null;
        }

        List<CartDTO> list = new ArrayList<CartDTO>( carts.size() );
        for ( Cart cart : carts ) {
            list.add( toDTO( cart ) );
        }

        return list;
    }

    private String cartUserId(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        User user = cart.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String cartProductId(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        Product product = cart.getProduct();
        if ( product == null ) {
            return null;
        }
        String id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String cartProductName(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        Product product = cart.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String cartProductVariantId(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        ProductVariant productVariant = cart.getProductVariant();
        if ( productVariant == null ) {
            return null;
        }
        String id = productVariant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
