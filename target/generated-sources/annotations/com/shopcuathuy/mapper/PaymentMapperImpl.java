package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.PaymentDTO;
import com.shopcuathuy.entity.Order;
import com.shopcuathuy.entity.Payment;
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
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentDTO toDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDTO.PaymentDTOBuilder paymentDTO = PaymentDTO.builder();

        String id = paymentOrderId( payment );
        if ( id != null ) {
            paymentDTO.orderId( Long.parseLong( id ) );
        }
        paymentDTO.paymentMethodId( paymentPaymentMethodId( payment ) );
        paymentDTO.paymentMethodName( paymentPaymentMethodName( payment ) );
        paymentDTO.id( payment.getId() );
        paymentDTO.amount( payment.getAmount() );
        paymentDTO.processingFee( payment.getProcessingFee() );
        paymentDTO.currency( payment.getCurrency() );
        paymentDTO.transactionId( payment.getTransactionId() );
        paymentDTO.gatewayTransactionId( payment.getGatewayTransactionId() );
        paymentDTO.status( payment.getStatus() );
        paymentDTO.gatewayUrl( payment.getGatewayUrl() );
        paymentDTO.returnUrl( payment.getReturnUrl() );
        paymentDTO.cancelUrl( payment.getCancelUrl() );
        paymentDTO.paidAt( payment.getPaidAt() );
        paymentDTO.expiredAt( payment.getExpiredAt() );
        paymentDTO.failureReason( payment.getFailureReason() );
        paymentDTO.notes( payment.getNotes() );
        paymentDTO.createdAt( payment.getCreatedAt() );
        paymentDTO.updatedAt( payment.getUpdatedAt() );

        return paymentDTO.build();
    }

    @Override
    public List<PaymentDTO> toDTOList(List<Payment> payments) {
        if ( payments == null ) {
            return null;
        }

        List<PaymentDTO> list = new ArrayList<PaymentDTO>( payments.size() );
        for ( Payment payment : payments ) {
            list.add( toDTO( payment ) );
        }

        return list;
    }

    private String paymentOrderId(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        Order order = payment.getOrder();
        if ( order == null ) {
            return null;
        }
        String id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long paymentPaymentMethodId(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        PaymentMethod paymentMethod = payment.getPaymentMethod();
        if ( paymentMethod == null ) {
            return null;
        }
        Long id = paymentMethod.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String paymentPaymentMethodName(Payment payment) {
        if ( payment == null ) {
            return null;
        }
        PaymentMethod paymentMethod = payment.getPaymentMethod();
        if ( paymentMethod == null ) {
            return null;
        }
        String name = paymentMethod.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
