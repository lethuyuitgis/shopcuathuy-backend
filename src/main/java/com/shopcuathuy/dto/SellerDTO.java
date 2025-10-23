package com.shopcuathuy.dto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.shopcuathuy.entity.Seller;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Seller DTO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
public class SellerDTO {

    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private String businessName;
    private String businessType;
    private String businessDescription;
    private String businessAddress;
    private String businessPhone;
    private String businessEmail;
    private String businessWebsite;
    private String businessLicense;
    private String taxId;
    private String bankAccount;
    private String bankName;
    private String bankBranch;
    private Seller.SellerStatus status;
    private BigDecimal commissionRate;
    private BigDecimal totalSales;
    private Integer totalOrders;
    private Double averageRating;
    private Integer totalReviews;
    private String profileImage;
    private String coverImage;
    private String socialMedia;
    private String businessHours;
    private String returnPolicy;
    private String shippingPolicy;
    private String supportInfo;
    private Boolean isVerified;
    private LocalDateTime verifiedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public SellerDTO() {}

    public SellerDTO(Long id, Long userId, String userName, String userEmail, String businessName,
                    String businessType, String businessDescription, String businessAddress,
                    String businessPhone, String businessEmail, String businessWebsite,
                    String businessLicense, String taxId, String bankAccount, String bankName,
                    String bankBranch, Seller.SellerStatus status, BigDecimal commissionRate,
                    BigDecimal totalSales, Integer totalOrders, Double averageRating,
                    Integer totalReviews, String profileImage, String coverImage,
                    String socialMedia, String businessHours, String returnPolicy,
                    String shippingPolicy, String supportInfo, Boolean isVerified,
                    LocalDateTime verifiedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
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
        this.status = status;
        this.commissionRate = commissionRate;
        this.totalSales = totalSales;
        this.totalOrders = totalOrders;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
        this.profileImage = profileImage;
        this.coverImage = coverImage;
        this.socialMedia = socialMedia;
        this.businessHours = businessHours;
        this.returnPolicy = returnPolicy;
        this.shippingPolicy = shippingPolicy;
        this.supportInfo = supportInfo;
        this.isVerified = isVerified;
        this.verifiedAt = verifiedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

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

    public Seller.SellerStatus getStatus() { return status; }
    public void setStatus(Seller.SellerStatus status) { this.status = status; }

    public BigDecimal getCommissionRate() { return commissionRate; }
    public void setCommissionRate(BigDecimal commissionRate) { this.commissionRate = commissionRate; }

    public BigDecimal getTotalSales() { return totalSales; }
    public void setTotalSales(BigDecimal totalSales) { this.totalSales = totalSales; }

    public Integer getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Integer totalOrders) { this.totalOrders = totalOrders; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public Integer getTotalReviews() { return totalReviews; }
    public void setTotalReviews(Integer totalReviews) { this.totalReviews = totalReviews; }

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

    public Boolean getIsVerified() { return isVerified; }
    public void setIsVerified(Boolean isVerified) { this.isVerified = isVerified; }

    public LocalDateTime getVerifiedAt() { return verifiedAt; }
    public void setVerifiedAt(LocalDateTime verifiedAt) { this.verifiedAt = verifiedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
