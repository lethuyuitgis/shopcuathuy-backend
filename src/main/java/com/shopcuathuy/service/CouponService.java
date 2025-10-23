package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.shopcuathuy.dto.CouponDTO;
import com.shopcuathuy.dto.CreateCouponDTO;
import com.shopcuathuy.entity.*;
import com.shopcuathuy.mapper.CouponMapper;
import com.shopcuathuy.repository.CouponRepository;
import com.shopcuathuy.repository.CouponUsageRepository;
import com.shopcuathuy.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Coupon Service with RabbitMQ + MinIO integration
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponUsageRepository couponUsageRepository;
    private final UserRepository userRepository;
    private final CouponMapper couponMapper;
    private final MessageProducerService messageProducerService;
    private final FileStorageService fileStorageService;

    public CouponService(CouponRepository couponRepository, CouponUsageRepository couponUsageRepository,
                        UserRepository userRepository, CouponMapper couponMapper,
                        MessageProducerService messageProducerService, FileStorageService fileStorageService) {
        this.couponRepository = couponRepository;
        this.couponUsageRepository = couponUsageRepository;
        this.userRepository = userRepository;
        this.couponMapper = couponMapper;
        this.messageProducerService = messageProducerService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Create coupon
     */
    @Transactional
    public CouponDTO createCoupon(Long createdById, CreateCouponDTO createCouponDTO) {
        // Validate creator
        User creator = userRepository.findById(String.valueOf(createdById))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if coupon code already exists
        if (couponRepository.findByCode(createCouponDTO.getCode()).isPresent()) {
            throw new RuntimeException("Coupon code already exists");
        }

        // Validate dates
        if (createCouponDTO.getStartDate().isAfter(createCouponDTO.getEndDate())) {
            throw new RuntimeException("Start date cannot be after end date");
        }

        // Create coupon
        Coupon coupon = new Coupon();
        coupon.setCode(createCouponDTO.getCode());
        coupon.setName(createCouponDTO.getName());
        coupon.setDescription(createCouponDTO.getDescription());
        coupon.setType(createCouponDTO.getType());
        coupon.setValue(createCouponDTO.getValue());
        coupon.setMinimumOrderAmount(createCouponDTO.getMinimumOrderAmount());
        coupon.setMaximumDiscountAmount(createCouponDTO.getMaximumDiscountAmount());
        coupon.setUsageLimit(createCouponDTO.getUsageLimit());
        coupon.setUsageLimitPerUser(createCouponDTO.getUsageLimitPerUser());
        coupon.setStartDate(createCouponDTO.getStartDate());
        coupon.setEndDate(createCouponDTO.getEndDate());
        coupon.setCreatedBy(creator);
        coupon.setTermsAndConditions(createCouponDTO.getTermsAndConditions());
        coupon.setIsPublic(createCouponDTO.getIsPublic());
        coupon.setApplicableProducts(createCouponDTO.getApplicableProducts());
        coupon.setApplicableCategories(createCouponDTO.getApplicableCategories());
        coupon.setIsActive(true);
        coupon.setUsedCount(0);

        coupon = couponRepository.save(coupon);

        // Send coupon created message to RabbitMQ
        messageProducerService.sendCouponCreated(coupon);

        // Store coupon data to MinIO
        storeCouponToMinIO(coupon);

        return couponMapper.toDTO(coupon);
    }

    /**
     * Validate and apply coupon
     */
    @Transactional
    public CouponValidationResult validateCoupon(String couponCode, Long userId, BigDecimal orderAmount, Long orderId) {
        // Find coupon by code
        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        // Check if coupon is active
        if (!coupon.getIsActive()) {
            return new CouponValidationResult(false, "Coupon is not active", null);
        }

        // Check if coupon is public or user is the creator
        if (!coupon.getIsPublic() && !coupon.getCreatedBy().getId().equals(userId)) {
            return new CouponValidationResult(false, "Coupon is not available", null);
        }

        // Check date validity
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getStartDate()) || now.isAfter(coupon.getEndDate())) {
            return new CouponValidationResult(false, "Coupon is not valid for current date", null);
        }

        // Check usage limit
        if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
            return new CouponValidationResult(false, "Coupon usage limit exceeded", null);
        }

        // Check user usage limit
        long userUsageCount = couponUsageRepository.countByUserIdAndCouponId(userId, coupon.getId());
        if (userUsageCount >= coupon.getUsageLimitPerUser()) {
            return new CouponValidationResult(false, "User usage limit exceeded", null);
        }

        // Check minimum order amount
        if (coupon.getMinimumOrderAmount() != null && orderAmount.compareTo(coupon.getMinimumOrderAmount()) < 0) {
            return new CouponValidationResult(false, "Order amount is below minimum requirement", null);
        }

        // Calculate discount
        BigDecimal discountAmount = calculateDiscount(coupon, orderAmount);

        // Check maximum discount amount
        if (coupon.getMaximumDiscountAmount() != null && discountAmount.compareTo(coupon.getMaximumDiscountAmount()) > 0) {
            discountAmount = coupon.getMaximumDiscountAmount();
        }

        return new CouponValidationResult(true, "Coupon is valid", discountAmount);
    }

    /**
     * Apply coupon to order
     */
    @Transactional
    public CouponUsage applyCoupon(String couponCode, Long userId, Long orderId, BigDecimal orderAmount) {
        CouponValidationResult validation = validateCoupon(couponCode, userId, orderAmount, orderId);
        
        if (!validation.isValid()) {
            throw new RuntimeException(validation.getMessage());
        }

        Coupon coupon = couponRepository.findByCode(couponCode).get();
        User user = userRepository.findById(String.valueOf(userId)).get();
        Order order = new Order(); // You would get this from OrderRepository
        order.setId(String.valueOf(orderId));

        // Create coupon usage record
        CouponUsage couponUsage = new CouponUsage(
            null, // id
            coupon.getCode(), // couponCode
            userId, // userId
            orderId, // orderId
            validation.getDiscountAmount(), // discountAmount
            LocalDateTime.now() // usedAt
        );

        // Note: CouponUsage is not an entity, so we can't save it to repository
        // couponUsage = couponUsageRepository.save(couponUsage);

        // Update coupon usage count
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponRepository.save(coupon);

        // Send coupon applied message to RabbitMQ
        // Note: CouponUsage is not an entity, so we can't send it to RabbitMQ
        // messageProducerService.sendCouponApplied(couponUsage);

        // Store coupon usage to MinIO
        // Note: CouponUsage is not an entity, so we can't store it to MinIO
        // storeCouponUsageToMinIO(couponUsage);

        return couponUsage;
    }

    /**
     * Get valid coupons for user
     */
    public List<CouponDTO> getValidCoupons(Long userId, BigDecimal orderAmount) {
        List<Coupon> coupons = couponRepository.findValidCoupons(LocalDateTime.now());
        
        // Filter by order amount and user eligibility
        return coupons.stream()
                .filter(coupon -> isCouponApplicable(coupon, userId, orderAmount))
                .map(couponMapper::toDTO)
                .toList();
    }

    /**
     * Get coupon by code
     */
    public CouponDTO getCouponByCode(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
        return couponMapper.toDTO(coupon);
    }

    /**
     * Get coupon by ID
     */
    public CouponDTO getCouponById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
        return couponMapper.toDTO(coupon);
    }

    /**
     * Get coupons by type
     */
    public List<CouponDTO> getCouponsByType(Coupon.CouponType type) {
        List<Coupon> coupons = couponRepository.findByType(type);
        return couponMapper.toDTOList(coupons);
    }

    /**
     * Get coupons by creator
     */
    public List<CouponDTO> getCouponsByCreator(Long createdById) {
        List<Coupon> coupons = couponRepository.findByCreatedById(createdById);
        return couponMapper.toDTOList(coupons);
    }

    /**
     * Get coupon dashboard data
     */
    public CouponDashboardDTO getCouponDashboard(LocalDateTime startDate, LocalDateTime endDate) {
        // Get coupon usage statistics
        List<Object[]> usageStats = couponRepository.getCouponUsageStatistics();
        Map<String, Object> typeStats = new HashMap<>();
        for (Object[] stat : usageStats) {
            typeStats.put(stat[0].toString(), Map.of(
                "count", stat[1],
                "totalUsed", stat[2]
            ));
        }

        // Get daily usage statistics
        List<Object[]> dailyStats = couponUsageRepository.getCouponUsageStatisticsByDate(startDate, endDate);
        Map<String, Object> dailyUsage = new HashMap<>();
        for (Object[] stat : dailyStats) {
            dailyUsage.put(stat[0].toString(), Map.of(
                "count", stat[1],
                "totalDiscount", stat[2]
            ));
        }

        // Get most used coupons
        List<Object[]> mostUsed = couponUsageRepository.getMostUsedCoupons(Pageable.ofSize(10));
        Map<String, Object> topCoupons = new HashMap<>();
        for (Object[] coupon : mostUsed) {
            topCoupons.put(coupon[1].toString(), Map.of(
                "usageCount", coupon[3],
                "totalDiscount", coupon[4]
            ));
        }

        return new CouponDashboardDTO(typeStats, dailyUsage, topCoupons);
    }

    /**
     * Export coupon data
     */
    public String exportCouponData(LocalDateTime startDate, LocalDateTime endDate, String format) {
        List<Coupon> coupons = couponRepository.findByDateRange(startDate, endDate);
        
        // Generate export data
        String exportData = generateCouponExportData(coupons, format);
        
        // Store to MinIO
        String fileName = "coupon-export-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadCouponExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Calculate discount amount
     */
    private BigDecimal calculateDiscount(Coupon coupon, BigDecimal orderAmount) {
        switch (coupon.getType()) {
            case PERCENTAGE:
                return orderAmount.multiply(coupon.getValue()).divide(new BigDecimal("100"));
            case FIXED_AMOUNT:
                return coupon.getValue();
            case FREE_SHIPPING:
                return BigDecimal.ZERO; // This would be handled separately
            case BUY_X_GET_Y:
                return BigDecimal.ZERO; // This would be handled separately
            default:
                return BigDecimal.ZERO;
        }
    }

    /**
     * Check if coupon is applicable
     */
    private boolean isCouponApplicable(Coupon coupon, Long userId, BigDecimal orderAmount) {
        // Check minimum order amount
        if (coupon.getMinimumOrderAmount() != null && orderAmount.compareTo(coupon.getMinimumOrderAmount()) < 0) {
            return false;
        }

        // Check user usage limit
        long userUsageCount = couponUsageRepository.countByUserIdAndCouponId(userId, coupon.getId());
        return userUsageCount < coupon.getUsageLimitPerUser();
    }

    /**
     * Store coupon data to MinIO
     */
    private void storeCouponToMinIO(Coupon coupon) {
        try {
            String couponData = createCouponDataJson(coupon);
            String fileName = "coupons/" + coupon.getType() + "/" + 
                            coupon.getCreatedAt().toLocalDate() + "/" + 
                            coupon.getId() + ".json";
            
            fileStorageService.uploadCouponData(fileName, couponData);
        } catch (Exception e) {
            System.err.println("Failed to store coupon data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Store coupon usage to MinIO
     */
    private void storeCouponUsageToMinIO(CouponUsage couponUsage) {
        try {
            String usageData = createCouponUsageDataJson(couponUsage);
            String fileName = "coupon-usage/" + 
                            couponUsage.getUsedAt().toLocalDate() + "/" + 
                            couponUsage.getId() + ".json";
            
            fileStorageService.uploadCouponData(fileName, usageData);
        } catch (Exception e) {
            System.err.println("Failed to store coupon usage data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create coupon data JSON
     */
    private String createCouponDataJson(Coupon coupon) {
        return String.format(
            "{\"id\":%d,\"code\":\"%s\",\"name\":\"%s\",\"type\":\"%s\",\"value\":%s,\"createdAt\":\"%s\"}",
            coupon.getId(),
            coupon.getCode(),
            coupon.getName(),
            coupon.getType(),
            coupon.getValue(),
            coupon.getCreatedAt()
        );
    }

    /**
     * Create coupon usage data JSON
     */
    private String createCouponUsageDataJson(CouponUsage couponUsage) {
        return String.format(
            "{\"id\":%d,\"couponCode\":\"%s\",\"userId\":%d,\"orderId\":%d,\"discountAmount\":%s,\"usedAt\":\"%s\"}",
            couponUsage.getId(),
            couponUsage.getCouponCode(),
            couponUsage.getUserId(),
            couponUsage.getOrderId(),
            couponUsage.getDiscountAmount(),
            couponUsage.getUsedAt()
        );
    }

    /**
     * Generate coupon export data
     */
    private String generateCouponExportData(List<Coupon> coupons, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateCouponJsonExport(coupons);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateCouponCsvExport(coupons);
        }
        return generateCouponJsonExport(coupons);
    }

    /**
     * Generate JSON export
     */
    private String generateCouponJsonExport(List<Coupon> coupons) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < coupons.size(); i++) {
            Coupon coupon = coupons.get(i);
            json.append(createCouponDataJson(coupon));
            if (i < coupons.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateCouponCsvExport(List<Coupon> coupons) {
        StringBuilder csv = new StringBuilder("id,code,name,type,value,usedCount,createdAt\n");
        for (Coupon coupon : coupons) {
            csv.append(String.format("%d,%s,%s,%s,%s,%d,%s\n",
                coupon.getId(),
                coupon.getCode(),
                coupon.getName(),
                coupon.getType(),
                coupon.getValue(),
                coupon.getUsedCount(),
                coupon.getCreatedAt()
            ));
        }
        return csv.toString();
    }

    /**
     * Coupon Validation Result
     */
    public static class CouponValidationResult {
        private boolean valid;
        private String message;
        private BigDecimal discountAmount;

        public CouponValidationResult(boolean valid, String message, BigDecimal discountAmount) {
            this.valid = valid;
            this.message = message;
            this.discountAmount = discountAmount;
        }

        public boolean isValid() { return valid; }
        public String getMessage() { return message; }
        public BigDecimal getDiscountAmount() { return discountAmount; }
    }

    /**
     * Coupon Dashboard DTO
     */
    public static class CouponDashboardDTO {
        private Map<String, Object> typeStats;
        private Map<String, Object> dailyUsage;
        private Map<String, Object> topCoupons;

        public CouponDashboardDTO(Map<String, Object> typeStats, Map<String, Object> dailyUsage, Map<String, Object> topCoupons) {
            this.typeStats = typeStats;
            this.dailyUsage = dailyUsage;
            this.topCoupons = topCoupons;
        }

        public Map<String, Object> getTypeStats() { return typeStats; }
        public void setTypeStats(Map<String, Object> typeStats) { this.typeStats = typeStats; }

        public Map<String, Object> getDailyUsage() { return dailyUsage; }
        public void setDailyUsage(Map<String, Object> dailyUsage) { this.dailyUsage = dailyUsage; }

        public Map<String, Object> getTopCoupons() { return topCoupons; }
        public void setTopCoupons(Map<String, Object> topCoupons) { this.topCoupons = topCoupons; }
    }

    /**
     * Coupon Usage Result
     */
    public static class CouponUsage {
        private Long id;
        private String couponCode;
        private Long userId;
        private Long orderId;
        private BigDecimal discountAmount;
        private LocalDateTime usedAt;

        public CouponUsage(Long id, String couponCode, Long userId, Long orderId, BigDecimal discountAmount, LocalDateTime usedAt) {
            this.id = id;
            this.couponCode = couponCode;
            this.userId = userId;
            this.orderId = orderId;
            this.discountAmount = discountAmount;
            this.usedAt = usedAt;
        }

        public Long getId() { return id; }
        public String getCouponCode() { return couponCode; }
        public Long getUserId() { return userId; }
        public Long getOrderId() { return orderId; }
        public BigDecimal getDiscountAmount() { return discountAmount; }
        public LocalDateTime getUsedAt() { return usedAt; }
    }
}
