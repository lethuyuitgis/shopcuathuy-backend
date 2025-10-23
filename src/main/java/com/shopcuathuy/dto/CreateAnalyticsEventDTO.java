package com.shopcuathuy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Create Analytics Event DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnalyticsEventDTO {
    
    private String eventName;
    private String eventType;
    private Long userId;
    private Long productId;
    private Long orderId;
    private String eventData;
    private String sessionId;
    private String ipAddress;
    private String userAgent;
    private String referrer;
    private Double value;
    private Map<String, Object> properties;
}