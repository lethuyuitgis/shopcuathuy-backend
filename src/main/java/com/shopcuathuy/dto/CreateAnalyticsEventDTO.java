package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    @NotNull
    @Size(max = 100)
    private String eventName;

    @NotNull
    @Size(max = 50)
    private String eventType;

    private Long userId;
    private Long productId;
    private Long orderId;

    @Size(max = 1000)
    private String eventData;

    @Size(max = 100)
    private String sessionId;

    @Size(max = 100)
    private String ipAddress;

    @Size(max = 500)
    private String userAgent;

    @Size(max = 100)
    private String referrer;

    private Double value;

    @Size(max = 1000)
    private String properties;

}
