package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Create Shipping DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class CreateShippingDTO {

    @NotNull
    private Long orderId;

    @NotNull
    private Long shippingMethodId;

    @NotNull
    @Size(max = 100)
    private String trackingNumber;

    @NotNull
    @Size(max = 200)
    private String carrier;

    private BigDecimal shippingCost;

    private LocalDateTime estimatedDeliveryDate;

    @Size(max = 500)
    private String shippingAddress;

    @Size(max = 100)
    private String recipientName;

    @Size(max = 20)
    private String recipientPhone;

    @Size(max = 1000)
    private String notes;

    @Size(max = 1000)
    private String trackingUrl;

    // Constructors
    public CreateShippingDTO() {}

    public CreateShippingDTO(Long orderId, Long shippingMethodId, String trackingNumber, String carrier,
                            BigDecimal shippingCost, LocalDateTime estimatedDeliveryDate, String shippingAddress,
                            String recipientName, String recipientPhone, String notes, String trackingUrl) {
        this.orderId = orderId;
        this.shippingMethodId = shippingMethodId;
        this.trackingNumber = trackingNumber;
        this.carrier = carrier;
        this.shippingCost = shippingCost;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.shippingAddress = shippingAddress;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.notes = notes;
        this.trackingUrl = trackingUrl;
    }

    // Getters and Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getShippingMethodId() { return shippingMethodId; }
    public void setShippingMethodId(Long shippingMethodId) { this.shippingMethodId = shippingMethodId; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public String getCarrier() { return carrier; }
    public void setCarrier(String carrier) { this.carrier = carrier; }

    public BigDecimal getShippingCost() { return shippingCost; }
    public void setShippingCost(BigDecimal shippingCost) { this.shippingCost = shippingCost; }

    public LocalDateTime getEstimatedDeliveryDate() { return estimatedDeliveryDate; }
    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) { this.estimatedDeliveryDate = estimatedDeliveryDate; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getRecipientPhone() { return recipientPhone; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getTrackingUrl() { return trackingUrl; }
    public void setTrackingUrl(String trackingUrl) { this.trackingUrl = trackingUrl; }
}
