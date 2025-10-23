package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Create Seller DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class CreateSellerDTO {

    @NotNull
    private Long userId;

    @NotNull
    @Size(max = 200)
    private String businessName;

    @Size(max = 100)
    private String businessType;

    @Size(max = 1000)
    private String businessDescription;

    @Size(max = 500)
    private String businessAddress;

    @Size(max = 20)
    private String businessPhone;

    @Size(max = 100)
    private String businessEmail;

    @Size(max = 200)
    private String businessWebsite;

    @Size(max = 100)
    private String businessLicense;

    @Size(max = 50)
    private String taxId;

    @Size(max = 50)
    private String bankAccount;

    @Size(max = 100)
    private String bankName;

    @Size(max = 100)
    private String bankBranch;

    private BigDecimal commissionRate;

    @Size(max = 200)
    private String profileImage;

    @Size(max = 200)
    private String coverImage;

    @Size(max = 1000)
    private String socialMedia;

    @Size(max = 500)
    private String businessHours;

    @Size(max = 1000)
    private String returnPolicy;

    @Size(max = 1000)
    private String shippingPolicy;

    @Size(max = 1000)
    private String supportInfo;

    // Constructors
    public CreateSellerDTO() {}

    public CreateSellerDTO(Long userId, String businessName, String businessType, String businessDescription,
                          String businessAddress, String businessPhone, String businessEmail, String businessWebsite,
                          String businessLicense, String taxId, String bankAccount, String bankName, String bankBranch,
                          BigDecimal commissionRate, String profileImage, String coverImage, String socialMedia,
                          String businessHours, String returnPolicy, String shippingPolicy, String supportInfo) {
        this.userId = userId;
        this.businessName = businessName;
        this.businessType = businessType;
        this.businessDescription = businessDescription;
        this.businessAddress = businessAddress;
        this.businessPhone = businessPhone;
        this.businessEmail = businessEmail;
        this.businessWebsite = businessWebsite;
        this.businessLicense = businessLicense;
        this.taxId = taxId;
        this.bankAccount = bankAccount;
        this.bankName = bankName;
        this.bankBranch = bankBranch;
        this.commissionRate = commissionRate;
        this.profileImage = profileImage;
        this.coverImage = coverImage;
        this.socialMedia = socialMedia;
        this.businessHours = businessHours;
        this.returnPolicy = returnPolicy;
        this.shippingPolicy = shippingPolicy;
        this.supportInfo = supportInfo;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public String getBusinessDescription() { return businessDescription; }
    public void setBusinessDescription(String businessDescription) { this.businessDescription = businessDescription; }

    public String getBusinessAddress() { return businessAddress; }
    public void setBusinessAddress(String businessAddress) { this.businessAddress = businessAddress; }

    public String getBusinessPhone() { return businessPhone; }
    public void setBusinessPhone(String businessPhone) { this.businessPhone = businessPhone; }

    public String getBusinessEmail() { return businessEmail; }
    public void setBusinessEmail(String businessEmail) { this.businessEmail = businessEmail; }

    public String getBusinessWebsite() { return businessWebsite; }
    public void setBusinessWebsite(String businessWebsite) { this.businessWebsite = businessWebsite; }

    public String getBusinessLicense() { return businessLicense; }
    public void setBusinessLicense(String businessLicense) { this.businessLicense = businessLicense; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getBankAccount() { return bankAccount; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBankBranch() { return bankBranch; }
    public void setBankBranch(String bankBranch) { this.bankBranch = bankBranch; }

    public BigDecimal getCommissionRate() { return commissionRate; }
    public void setCommissionRate(BigDecimal commissionRate) { this.commissionRate = commissionRate; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public String getSocialMedia() { return socialMedia; }
    public void setSocialMedia(String socialMedia) { this.socialMedia = socialMedia; }

    public String getBusinessHours() { return businessHours; }
    public void setBusinessHours(String businessHours) { this.businessHours = businessHours; }

    public String getReturnPolicy() { return returnPolicy; }
    public void setReturnPolicy(String returnPolicy) { this.returnPolicy = returnPolicy; }

    public String getShippingPolicy() { return shippingPolicy; }
    public void setShippingPolicy(String shippingPolicy) { this.shippingPolicy = shippingPolicy; }

    public String getSupportInfo() { return supportInfo; }
    public void setSupportInfo(String supportInfo) { this.supportInfo = supportInfo; }
}
