package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;


/**
 * Create Payment DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentDTO {

    @NotNull
    private Long orderId;

    @NotNull
    private Long paymentMethodId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    private String currency = "VND";

    private String returnUrl;

    private String cancelUrl;

    private String notes;
}
