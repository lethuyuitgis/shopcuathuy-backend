package com.shopcuathuy.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * UserAddress entity representing user addresses
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "user_addresses", indexes = {
    @Index(name = "idx_user_address_user_id", columnList = "user_id"),
    @Index(name = "idx_user_address_is_default", columnList = "is_default"),
    @Index(name = "idx_user_address_type", columnList = "type"),
    @Index(name = "idx_user_address_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "type", nullable = false, length = 50)
    @NotBlank(message = "Address type is required")
    @Size(max = 50, message = "Address type must not exceed 50 characters")
    private String type;

    @Column(name = "full_name", nullable = false, length = 255)
    @NotBlank(message = "Full name is required")
    @Size(max = 255, message = "Full name must not exceed 255 characters")
    private String fullName;

    @Column(name = "phone", nullable = false, length = 20)
    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;

    @Column(name = "email", length = 255)
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @Column(name = "address_line_1", nullable = false, length = 500)
    @NotBlank(message = "Address line 1 is required")
    @Size(max = 500, message = "Address line 1 must not exceed 500 characters")
    private String addressLine1;

    @Column(name = "address_line_2", length = 500)
    @Size(max = 500, message = "Address line 2 must not exceed 500 characters")
    private String addressLine2;

    @Column(name = "city", nullable = false, length = 100)
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Column(name = "state", nullable = false, length = 100)
    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Column(name = "postal_code", nullable = false, length = 20)
    @NotBlank(message = "Postal code is required")
    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;

    @Column(name = "country", nullable = false, length = 100)
    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "notes", length = 500)
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "landmark", length = 255)
    @Size(max = 255, message = "Landmark must not exceed 255 characters")
    private String landmark;

    @Column(name = "delivery_instructions", length = 500)
    @Size(max = 500, message = "Delivery instructions must not exceed 500 characters")
    private String deliveryInstructions;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    /**
     * Check if address is default
     */
    public boolean isDefault() {
        return isDefault != null && isDefault;
    }

    /**
     * Check if address is active
     */
    public boolean isActive() {
        return isActive != null && isActive;
    }

    /**
     * Check if address has second line
     */
    public boolean hasAddressLine2() {
        return addressLine2 != null && !addressLine2.trim().isEmpty();
    }

    /**
     * Check if address has email
     */
    public boolean hasEmail() {
        return email != null && !email.trim().isEmpty();
    }

    /**
     * Check if address has notes
     */
    public boolean hasNotes() {
        return notes != null && !notes.trim().isEmpty();
    }

    /**
     * Check if address has coordinates
     */
    public boolean hasCoordinates() {
        return latitude != null && longitude != null;
    }

    /**
     * Check if address has landmark
     */
    public boolean hasLandmark() {
        return landmark != null && !landmark.trim().isEmpty();
    }

    /**
     * Check if address has delivery instructions
     */
    public boolean hasDeliveryInstructions() {
        return deliveryInstructions != null && !deliveryInstructions.trim().isEmpty();
    }

    /**
     * Get full address as string
     */
    public String getFullAddress() {
        StringBuilder address = new StringBuilder();
        address.append(addressLine1);
        
        if (hasAddressLine2()) {
            address.append(", ").append(addressLine2);
        }
        
        address.append(", ").append(city);
        address.append(", ").append(state);
        address.append(" ").append(postalCode);
        address.append(", ").append(country);
        
        return address.toString();
    }

    /**
     * Get short address as string
     */
    public String getShortAddress() {
        return city + ", " + state + " " + postalCode;
    }

    /**
     * Get address type display name
     */
    public String getTypeDisplayName() {
        if (type == null) {
            return "Address";
        }
        
        switch (type.toLowerCase()) {
            case "home":
                return "Home";
            case "work":
                return "Work";
            case "billing":
                return "Billing";
            case "shipping":
                return "Shipping";
            case "other":
                return "Other";
            default:
                return type;
        }
    }

    /**
     * Check if address is recent (within last 30 days)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(30));
    }

    /**
     * Check if address is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Get address summary
     */
    public String getAddressSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(getTypeDisplayName());
        
        if (isDefault()) {
            summary.append(" (Default)");
        }
        
        summary.append(": ").append(getShortAddress());
        
        return summary.toString();
    }

    /**
     * Get distance from another address (if coordinates are available)
     */
    public Double getDistanceFrom(UserAddress otherAddress) {
        if (!hasCoordinates() || !otherAddress.hasCoordinates()) {
            return null;
        }
        
        // Haversine formula to calculate distance between two points
        double earthRadius = 6371; // Earth's radius in kilometers
        
        double lat1Rad = Math.toRadians(latitude);
        double lat2Rad = Math.toRadians(otherAddress.getLatitude());
        double deltaLatRad = Math.toRadians(otherAddress.getLatitude() - latitude);
        double deltaLonRad = Math.toRadians(otherAddress.getLongitude() - longitude);
        
        double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return earthRadius * c;
    }

    /**
     * Check if address is valid
     */
    public boolean isValid() {
        return fullName != null && !fullName.trim().isEmpty() &&
               phone != null && !phone.trim().isEmpty() &&
               addressLine1 != null && !addressLine1.trim().isEmpty() &&
               city != null && !city.trim().isEmpty() &&
               state != null && !state.trim().isEmpty() &&
               postalCode != null && !postalCode.trim().isEmpty() &&
               country != null && !country.trim().isEmpty();
    }
}
