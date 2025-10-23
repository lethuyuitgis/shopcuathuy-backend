package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * ProductAttribute entity representing product attributes
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "product_attributes", indexes = {
    @Index(name = "idx_product_attribute_product_id", columnList = "product_id"),
    @Index(name = "idx_product_attribute_name", columnList = "name"),
    @Index(name = "idx_product_attribute_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_id", nullable = false)
    @NotBlank(message = "Product ID is required")
    private String productId;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Attribute name is required")
    @Size(max = 255, message = "Attribute name must not exceed 255 characters")
    private String name;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Attribute value is required")
    @Size(max = 500, message = "Attribute value must not exceed 500 characters")
    private String value;

    @Column(name = "display_name", length = 255)
    @Size(max = 255, message = "Display name must not exceed 255 characters")
    private String displayName;

    @Column(name = "attribute_type", length = 50)
    @Size(max = 50, message = "Attribute type must not exceed 50 characters")
    private String attributeType;

    @Column(name = "is_required", nullable = false)
    @Builder.Default
    private Boolean isRequired = false;

    @Column(name = "is_filterable", nullable = false)
    @Builder.Default
    private Boolean isFilterable = false;

    @Column(name = "is_searchable", nullable = false)
    @Builder.Default
    private Boolean isSearchable = false;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Sort order cannot be negative")
    private Integer sortOrder = 0;

    @Column(name = "unit", length = 50)
    @Size(max = 50, message = "Unit must not exceed 50 characters")
    private String unit;

    @Column(name = "min_value")
    private Double minValue;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "options", columnDefinition = "TEXT")
    private String options;

    @Column(name = "description", columnDefinition = "TEXT")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    /**
     * Check if attribute is required
     */
    public boolean isRequired() {
        return isRequired != null && isRequired;
    }

    /**
     * Check if attribute is filterable
     */
    public boolean isFilterable() {
        return isFilterable != null && isFilterable;
    }

    /**
     * Check if attribute is searchable
     */
    public boolean isSearchable() {
        return isSearchable != null && isSearchable;
    }

    /**
     * Get display name or fallback to name
     */
    public String getDisplayName() {
        return displayName != null && !displayName.trim().isEmpty() ? displayName : name;
    }

    /**
     * Get value with unit
     */
    public String getValueWithUnit() {
        if (unit != null && !unit.trim().isEmpty()) {
            return value + " " + unit;
        }
        return value;
    }

    /**
     * Check if attribute has numeric value
     */
    public boolean isNumeric() {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Get numeric value
     */
    public Double getNumericValue() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Check if attribute has boolean value
     */
    public boolean isBoolean() {
        return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
    }

    /**
     * Get boolean value
     */
    public Boolean getBooleanValue() {
        if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        return null;
    }

    /**
     * Check if attribute has options
     */
    public boolean hasOptions() {
        return options != null && !options.trim().isEmpty();
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
     * Check if value is within range
     */
    public boolean isValueInRange() {
        if (!isNumeric() || minValue == null || maxValue == null) {
            return true;
        }
        
        Double numericValue = getNumericValue();
        return numericValue != null && numericValue >= minValue && numericValue <= maxValue;
    }
}
