package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.shopcuathuy.entity.Coupon;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Create Coupon DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class CreateCouponDTO {

    @NotNull
    @Size(max = 50)
    private String code;

    @NotNull
    @Size(max = 200)
    private String name;

    @Size(max = 1000)
    private String description;

    @NotNull
    private Coupon.CouponType type;

    @NotNull
    private BigDecimal value;

    private BigDecimal minimumOrderAmount;

    private BigDecimal maximumDiscountAmount;

    @NotNull
    private Integer usageLimit;

    @NotNull
    private Integer usageLimitPerUser;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @Size(max = 1000)
    private String termsAndConditions;

    private Boolean isPublic = true;

    @Size(max = 1000)
    private String applicableProducts;

    @Size(max = 1000)
    private String applicableCategories;

    // Constructors
    public CreateCouponDTO() {}

    public CreateCouponDTO(String code, String name, String description, Coupon.CouponType type,
                          BigDecimal value, BigDecimal minimumOrderAmount, BigDecimal maximumDiscountAmount,
                          Integer usageLimit, Integer usageLimitPerUser, LocalDateTime startDate,
                          LocalDateTime endDate, String termsAndConditions, Boolean isPublic,
                          String applicableProducts, String applicableCategories) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
        this.minimumOrderAmount = minimumOrderAmount;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.usageLimit = usageLimit;
        this.usageLimitPerUser = usageLimitPerUser;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termsAndConditions = termsAndConditions;
        this.isPublic = isPublic;
        this.applicableProducts = applicableProducts;
        this.applicableCategories = applicableCategories;
    }

    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Coupon.CouponType getType() { return type; }
    public void setType(Coupon.CouponType type) { this.type = type; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public BigDecimal getMinimumOrderAmount() { return minimumOrderAmount; }
    public void setMinimumOrderAmount(BigDecimal minimumOrderAmount) { this.minimumOrderAmount = minimumOrderAmount; }

    public BigDecimal getMaximumDiscountAmount() { return maximumDiscountAmount; }
    public void setMaximumDiscountAmount(BigDecimal maximumDiscountAmount) { this.maximumDiscountAmount = maximumDiscountAmount; }

    public Integer getUsageLimit() { return usageLimit; }
    public void setUsageLimit(Integer usageLimit) { this.usageLimit = usageLimit; }

    public Integer getUsageLimitPerUser() { return usageLimitPerUser; }
    public void setUsageLimitPerUser(Integer usageLimitPerUser) { this.usageLimitPerUser = usageLimitPerUser; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public String getTermsAndConditions() { return termsAndConditions; }
    public void setTermsAndConditions(String termsAndConditions) { this.termsAndConditions = termsAndConditions; }

    public Boolean getIsPublic() { return isPublic; }
    public void setIsPublic(Boolean isPublic) { this.isPublic = isPublic; }

    public String getApplicableProducts() { return applicableProducts; }
    public void setApplicableProducts(String applicableProducts) { this.applicableProducts = applicableProducts; }

    public String getApplicableCategories() { return applicableCategories; }
    public void setApplicableCategories(String applicableCategories) { this.applicableCategories = applicableCategories; }
}
