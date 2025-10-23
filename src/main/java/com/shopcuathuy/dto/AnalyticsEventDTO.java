package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Analytics Event DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class AnalyticsEventDTO {

    private Long id;
    private String eventName;
    private String eventType;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;
    private Long orderId;
    private String eventData;
    private String sessionId;
    private String ipAddress;
    private String userAgent;
    private String referrer;
    private Double value;
    private String properties;
    private LocalDateTime createdAt;

    // Constructors
    public AnalyticsEventDTO() {}

    public AnalyticsEventDTO(Long id, String eventName, String eventType, Long userId, String userName,
                           Long productId, String productName, Long orderId, String eventData,
                           String sessionId, String ipAddress, String userAgent, String referrer,
                           Double value, String properties, LocalDateTime createdAt) {
        this.id = id;
        this.eventName = eventName;
        this.eventType = eventType;
        this.userId = userId;
        this.userName = userName;
        this.productId = productId;
        this.productName = productName;
        this.orderId = orderId;
        this.eventData = eventData;
        this.sessionId = sessionId;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.referrer = referrer;
        this.value = value;
        this.properties = properties;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getEventData() { return eventData; }
    public void setEventData(String eventData) { this.eventData = eventData; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public String getReferrer() { return referrer; }
    public void setReferrer(String referrer) { this.referrer = referrer; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getProperties() { return properties; }
    public void setProperties(String properties) { this.properties = properties; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
