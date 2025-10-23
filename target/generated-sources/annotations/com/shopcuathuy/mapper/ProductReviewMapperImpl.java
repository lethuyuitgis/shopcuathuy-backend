package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.ProductReviewDTO;
import com.shopcuathuy.entity.Order;
import com.shopcuathuy.entity.Product;
import com.shopcuathuy.entity.ProductReview;
import com.shopcuathuy.entity.User;
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
public class ProductReviewMapperImpl implements ProductReviewMapper {

    @Override
    public ProductReviewDTO toDTO(ProductReview review) {
        if ( review == null ) {
            return null;
        }

        ProductReviewDTO productReviewDTO = new ProductReviewDTO();

        String id = reviewProductId( review );
        if ( id != null ) {
            productReviewDTO.setProductId( Long.parseLong( id ) );
        }
        productReviewDTO.setProductName( reviewProductName( review ) );
        String id1 = reviewUserId( review );
        if ( id1 != null ) {
            productReviewDTO.setUserId( Long.parseLong( id1 ) );
        }
        productReviewDTO.setUserName( reviewUserName( review ) );
        productReviewDTO.setUserAvatar( reviewUserAvatar( review ) );
        String id2 = reviewOrderId( review );
        if ( id2 != null ) {
            productReviewDTO.setOrderId( Long.parseLong( id2 ) );
        }
        productReviewDTO.setId( review.getId() );
        productReviewDTO.setRating( review.getRating() );
        productReviewDTO.setTitle( review.getTitle() );
        productReviewDTO.setContent( review.getContent() );
        productReviewDTO.setIsVerifiedPurchase( review.getIsVerifiedPurchase() );
        productReviewDTO.setHelpfulCount( review.getHelpfulCount() );
        productReviewDTO.setNotHelpfulCount( review.getNotHelpfulCount() );
        productReviewDTO.setStatus( review.getStatus() );
        productReviewDTO.setAdminNotes( review.getAdminNotes() );
        productReviewDTO.setCreatedAt( review.getCreatedAt() );
        productReviewDTO.setUpdatedAt( review.getUpdatedAt() );

        return productReviewDTO;
    }

    @Override
    public List<ProductReviewDTO> toDTOList(List<ProductReview> reviews) {
        if ( reviews == null ) {
            return null;
        }

        List<ProductReviewDTO> list = new ArrayList<ProductReviewDTO>( reviews.size() );
        for ( ProductReview productReview : reviews ) {
            list.add( toDTO( productReview ) );
        }

        return list;
    }

    private String reviewProductId(ProductReview productReview) {
        if ( productReview == null ) {
            return null;
        }
        Product product = productReview.getProduct();
        if ( product == null ) {
            return null;
        }
        String id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String reviewProductName(ProductReview productReview) {
        if ( productReview == null ) {
            return null;
        }
        Product product = productReview.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String reviewUserId(ProductReview productReview) {
        if ( productReview == null ) {
            return null;
        }
        User user = productReview.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String reviewUserName(ProductReview productReview) {
        if ( productReview == null ) {
            return null;
        }
        User user = productReview.getUser();
        if ( user == null ) {
            return null;
        }
        String name = user.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String reviewUserAvatar(ProductReview productReview) {
        if ( productReview == null ) {
            return null;
        }
        User user = productReview.getUser();
        if ( user == null ) {
            return null;
        }
        String avatar = user.getAvatar();
        if ( avatar == null ) {
            return null;
        }
        return avatar;
    }

    private String reviewOrderId(ProductReview productReview) {
        if ( productReview == null ) {
            return null;
        }
        Order order = productReview.getOrder();
        if ( order == null ) {
            return null;
        }
        String id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
