package com.shopcuathuy.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * SystemSetting entity representing system settings
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "system_settings", indexes = {
    @Index(name = "idx_system_setting_key", columnList = "setting_key"),
    @Index(name = "idx_system_setting_category", columnList = "category"),
    @Index(name = "idx_system_setting_type", columnList = "type"),
    @Index(name = "idx_system_setting_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SystemSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "setting_key", nullable = false, length = 100, unique = true)
    @NotBlank(message = "Setting key is required")
    @Size(max = 100, message = "Setting key must not exceed 100 characters")
    private String settingKey;

    @Column(name = "setting_value", columnDefinition = "TEXT")
    private String settingValue;

    @Column(name = "default_value", columnDefinition = "TEXT")
    private String defaultValue;

    @Column(name = "type", nullable = false, length = 50)
    @NotBlank(message = "Type is required")
    @Size(max = 50, message = "Type must not exceed 50 characters")
    private String type;

    @Column(name = "category", nullable = false, length = 50)
    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;

    @Column(name = "name", nullable = false, length = 255)
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Column(name = "is_required", nullable = false)
    @Builder.Default
    private Boolean isRequired = false;

    @Column(name = "is_public", nullable = false)
    @Builder.Default
    private Boolean isPublic = false;

    @Column(name = "is_encrypted", nullable = false)
    @Builder.Default
    private Boolean isEncrypted = false;

    @Column(name = "is_readonly", nullable = false)
    @Builder.Default
    private Boolean isReadonly = false;

    @Column(name = "validation_rule", length = 500)
    @Size(max = 500, message = "Validation rule must not exceed 500 characters")
    private String validationRule;

    @Column(name = "options", columnDefinition = "TEXT")
    private String options;

    @Column(name = "min_value")
    private Double minValue;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "step_value")
    private Double stepValue;

    @Column(name = "unit", length = 50)
    @Size(max = 50, message = "Unit must not exceed 50 characters")
    private String unit;

    @Column(name = "placeholder", length = 255)
    @Size(max = 255, message = "Placeholder must not exceed 255 characters")
    private String placeholder;

    @Column(name = "help_text", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Help text must not exceed 1000 characters")
    private String helpText;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Sort order cannot be negative")
    private Integer sortOrder = 0;

    @Column(name = "group_name", length = 100)
    @Size(max = 100, message = "Group name must not exceed 100 characters")
    private String groupName;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "last_modified_by", length = 100)
    @Size(max = 100, message = "Last modified by must not exceed 100 characters")
    private String lastModifiedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Check if setting is required
     */
    public boolean isRequired() {
        return isRequired != null && isRequired;
    }

    /**
     * Check if setting is public
     */
    public boolean isPublic() {
        return isPublic != null && isPublic;
    }

    /**
     * Check if setting is encrypted
     */
    public boolean isEncrypted() {
        return isEncrypted != null && isEncrypted;
    }

    /**
     * Check if setting is readonly
     */
    public boolean isReadonly() {
        return isReadonly != null && isReadonly;
    }

    /**
     * Check if setting is active
     */
    public boolean isActive() {
        return isActive != null && isActive;
    }

    /**
     * Check if setting has value
     */
    public boolean hasValue() {
        return settingValue != null && !settingValue.trim().isEmpty();
    }

    /**
     * Check if setting has default value
     */
    public boolean hasDefaultValue() {
        return defaultValue != null && !defaultValue.trim().isEmpty();
    }

    /**
     * Check if setting has description
     */
    public boolean hasDescription() {
        return description != null && !description.trim().isEmpty();
    }

    /**
     * Check if setting has validation rule
     */
    public boolean hasValidationRule() {
        return validationRule != null && !validationRule.trim().isEmpty();
    }

    /**
     * Check if setting has options
     */
    public boolean hasOptions() {
        return options != null && !options.trim().isEmpty();
    }

    /**
     * Check if setting has min value
     */
    public boolean hasMinValue() {
        return minValue != null;
    }

    /**
     * Check if setting has max value
     */
    public boolean hasMaxValue() {
        return maxValue != null;
    }

    /**
     * Check if setting has step value
     */
    public boolean hasStepValue() {
        return stepValue != null;
    }

    /**
     * Check if setting has unit
     */
    public boolean hasUnit() {
        return unit != null && !unit.trim().isEmpty();
    }

    /**
     * Check if setting has placeholder
     */
    public boolean hasPlaceholder() {
        return placeholder != null && !placeholder.trim().isEmpty();
    }

    /**
     * Check if setting has help text
     */
    public boolean hasHelpText() {
        return helpText != null && !helpText.trim().isEmpty();
    }

    /**
     * Check if setting has group name
     */
    public boolean hasGroupName() {
        return groupName != null && !groupName.trim().isEmpty();
    }

    /**
     * Check if setting has last modified by
     */
    public boolean hasLastModifiedBy() {
        return lastModifiedBy != null && !lastModifiedBy.trim().isEmpty();
    }

    /**
     * Get type display name
     */
    public String getTypeDisplayName() {
        if (type == null) {
            return "String";
        }
        
        switch (type.toLowerCase()) {
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
            case "email":
                return "Email";
            case "url":
                return "URL";
            case "password":
                return "Password";
            case "textarea":
                return "Text Area";
            case "select":
                return "Select";
            case "radio":
                return "Radio";
            case "checkbox":
                return "Checkbox";
            case "date":
                return "Date";
            case "time":
                return "Time";
            case "datetime":
                return "Date & Time";
            default:
                return type;
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
            case "system":
                return "System";
            case "security":
                return "Security";
            case "email":
                return "Email";
            case "payment":
                return "Payment";
            case "shipping":
                return "Shipping";
            case "notification":
                return "Notification";
            case "appearance":
                return "Appearance";
            case "performance":
                return "Performance";
            case "integration":
                return "Integration";
            default:
                return category;
        }
    }

    /**
     * Get group display name
     */
    public String getGroupDisplayName() {
        if (groupName == null) {
            return "Default";
        }
        
        return groupName.replace("_", " ").replace("-", " ");
    }

    /**
     * Get setting summary
     */
    public String getSettingSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append(getCategoryDisplayName()).append(": ").append(name);
        
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
     * Get options as array
     */
    public String[] getOptionsArray() {
        if (hasOptions()) {
            return options.split(",");
        }
        return new String[0];
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
        this.type = "boolean";
    }

    /**
     * Set integer value
     */
    public void setIntegerValue(Integer value) {
        this.settingValue = value != null ? value.toString() : null;
        this.type = "number";
    }

    /**
     * Set double value
     */
    public void setDoubleValue(Double value) {
        this.settingValue = value != null ? value.toString() : null;
        this.type = "number";
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
        this.type = "array";
    }

    /**
     * Check if setting is valid
     */
    public boolean isValid() {
        return settingKey != null && !settingKey.trim().isEmpty() &&
               name != null && !name.trim().isEmpty() &&
               type != null && !type.trim().isEmpty() &&
               category != null && !category.trim().isEmpty();
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
     * Get days since creation
     */
    public long getDaysSinceCreation() {
        if (createdAt == null) {
            return 0;
        }
        return java.time.Duration.between(createdAt, LocalDateTime.now()).toDays();
    }

    /**
     * Get days since last modification
     */
    public long getDaysSinceLastModification() {
        if (updatedAt == null) {
            return 0;
        }
        return java.time.Duration.between(updatedAt, LocalDateTime.now()).toDays();
    }

    /**
     * Reset to default value
     */
    public void resetToDefault() {
        this.settingValue = this.defaultValue;
    }

    /**
     * Check if setting is at default value
     */
    public boolean isAtDefaultValue() {
        if (settingValue == null && defaultValue == null) {
            return true;
        }
        if (settingValue == null || defaultValue == null) {
            return false;
        }
        return settingValue.equals(defaultValue);
    }
}
