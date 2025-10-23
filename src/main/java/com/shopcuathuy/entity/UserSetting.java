package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * UserSetting entity representing user settings
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "user_settings", indexes = {
    @Index(name = "idx_user_setting_user_id", columnList = "user_id"),
    @Index(name = "idx_user_setting_key", columnList = "setting_key"),
    @Index(name = "idx_user_setting_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    @NotBlank(message = "User ID is required")
    private String userId;

    @Column(name = "setting_key", nullable = false, length = 100)
    @NotBlank(message = "Setting key is required")
    @Size(max = 100, message = "Setting key must not exceed 100 characters")
    private String settingKey;

    @Column(name = "setting_value", columnDefinition = "TEXT")
    private String settingValue;

    @Column(name = "setting_type", length = 50)
    @Size(max = 50, message = "Setting type must not exceed 50 characters")
    private String settingType;

    @Column(name = "description", length = 500)
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Column(name = "is_encrypted", nullable = false)
    @Builder.Default
    private Boolean isEncrypted = false;

    @Column(name = "is_public", nullable = false)
    @Builder.Default
    private Boolean isPublic = false;

    @Column(name = "category", length = 50)
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Sort order cannot be negative")
    private Integer sortOrder = 0;

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
     * Check if setting is encrypted
     */
    public boolean isEncrypted() {
        return isEncrypted != null && isEncrypted;
    }

    /**
     * Check if setting is public
     */
    public boolean isPublic() {
        return isPublic != null && isPublic;
    }

    /**
     * Check if setting has value
     */
    public boolean hasValue() {
        return settingValue != null && !settingValue.trim().isEmpty();
    }

    /**
     * Check if setting has description
     */
    public boolean hasDescription() {
        return description != null && !description.trim().isEmpty();
    }

    /**
     * Check if setting has category
     */
    public boolean hasCategory() {
        return category != null && !category.trim().isEmpty();
    }

    /**
     * Get setting type display name
     */
    public String getTypeDisplayName() {
        if (settingType == null) {
            return "String";
        }
        
        switch (settingType.toLowerCase()) {
            case "string":
                return "Text";
            case "number":
                return "Number";
            case "boolean":
                return "Yes/No";
            case "json":
                return "JSON";
            case "array":
                return "List";
            default:
                return settingType;
        }
    }

    /**
     * Get category display name
     */
    public String getCategoryDisplayName() {
        if (category == null) {
            return "General";
        }
        
        switch (category.toLowerCase()) {
            case "general":
                return "General";
            case "privacy":
                return "Privacy";
            case "notifications":
                return "Notifications";
            case "appearance":
                return "Appearance";
            case "security":
                return "Security";
            case "preferences":
                return "Preferences";
            default:
                return category;
        }
    }

    /**
     * Check if setting is recent (within last 30 days)
     */
    public boolean isRecent() {
        return createdAt != null && createdAt.isAfter(LocalDateTime.now().minusDays(30));
    }

    /**
     * Check if setting is today
     */
    public boolean isToday() {
        return createdAt != null && createdAt.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }

    /**
     * Get setting summary
     */
    public String getSettingSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(getCategoryDisplayName()).append(": ").append(settingKey);
        
        if (hasValue()) {
            String value = settingValue;
            if (value.length() > 50) {
                value = value.substring(0, 50) + "...";
            }
            summary.append(" = ").append(value);
        }
        
        return summary.toString();
    }

    /**
     * Get boolean value
     */
    public Boolean getBooleanValue() {
        if (settingValue == null) {
            return null;
        }
        
        if ("true".equalsIgnoreCase(settingValue) || "1".equals(settingValue)) {
            return true;
        } else if ("false".equalsIgnoreCase(settingValue) || "0".equals(settingValue)) {
            return false;
        }
        
        return null;
    }

    /**
     * Get integer value
     */
    public Integer getIntegerValue() {
        if (settingValue == null) {
            return null;
        }
        
        try {
            return Integer.parseInt(settingValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Get double value
     */
    public Double getDoubleValue() {
        if (settingValue == null) {
            return null;
        }
        
        try {
            return Double.parseDouble(settingValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Get array value
     */
    public String[] getArrayValue() {
        if (settingValue == null) {
            return new String[0];
        }
        
        return settingValue.split(",");
    }

    /**
     * Set boolean value
     */
    public void setBooleanValue(Boolean value) {
        this.settingValue = value != null ? value.toString() : null;
        this.settingType = "boolean";
    }

    /**
     * Set integer value
     */
    public void setIntegerValue(Integer value) {
        this.settingValue = value != null ? value.toString() : null;
        this.settingType = "number";
    }

    /**
     * Set double value
     */
    public void setDoubleValue(Double value) {
        this.settingValue = value != null ? value.toString() : null;
        this.settingType = "number";
    }

    /**
     * Set array value
     */
    public void setArrayValue(String[] value) {
        if (value != null && value.length > 0) {
            this.settingValue = String.join(",", value);
        } else {
            this.settingValue = null;
        }
        this.settingType = "array";
    }

    /**
     * Check if setting is valid
     */
    public boolean isValid() {
        return settingKey != null && !settingKey.trim().isEmpty();
    }

    /**
     * Get setting key display name
     */
    public String getKeyDisplayName() {
        if (settingKey == null) {
            return "Setting";
        }
        
        return settingKey.replace("_", " ").replace("-", " ");
    }
}
