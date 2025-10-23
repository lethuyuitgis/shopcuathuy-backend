package com.shopcuathuy.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * VNPay Configuration
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "vnpay")
public class VNPayConfig {

    private String tmnCode;
    private String secretKey;
    private String url;
    private String returnUrl;
    private String version = "2.1.0";
    private String command = "pay";
    private String currency = "VND";
    private String locale = "vn";
    private String orderType = "other";
    private int timeout = 15; // minutes
    private String ipAddress;
}
