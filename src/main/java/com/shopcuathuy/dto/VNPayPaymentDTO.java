package com.shopcuathuy.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VNPay Payment DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VNPayPaymentDTO {

    private String vnp_TmnCode;
    private String vnp_Amount;
    private String vnp_Command;
    private String vnp_CreateDate;
    private String vnp_CurrCode;
    private String vnp_IpAddr;
    private String vnp_Locale;
    private String vnp_OrderInfo;
    private String vnp_OrderType;
    private String vnp_ReturnUrl;
    private String vnp_TxnRef;
    private String vnp_Version;
    private String vnp_SecureHash;
    private String paymentUrl;
}
