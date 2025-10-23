package com.shopcuathuy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Analytics Event DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsEventDTO {
    
    private Long id;
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
    private String properties;
    private LocalDateTime createdAt;
}