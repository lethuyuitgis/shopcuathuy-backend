package com.shopcuathuy.controller;
import com.shopcuathuy.dto.CouponDTO;
import com.shopcuathuy.dto.CreateCouponDTO;
import com.shopcuathuy.entity.Coupon;
import com.shopcuathuy.entity.CouponUsage;
import com.shopcuathuy.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;



/**
 * Coupon Controller
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/coupons")
@Tag(name = "Coupon", description = "Coupon management APIs")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * Create coupon
     */
    @PostMapping
    @Operation(summary = "Create coupon", description = "Create a new coupon")
    public ResponseEntity<CouponDTO> createCoupon(
            @Parameter(description = "Creator user ID") @RequestParam Long createdById,
            @Valid @RequestBody CreateCouponDTO createCouponDTO) {
        CouponDTO coupon = couponService.createCoupon(createdById, createCouponDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(coupon);
    }

    /**
     * Validate coupon
     */
    @PostMapping("/validate")
    @Operation(summary = "Validate coupon", description = "Validate a coupon code")
    public ResponseEntity<CouponService.CouponValidationResult> validateCoupon(
            @Parameter(description = "Coupon code") @RequestParam String couponCode,
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Order amount") @RequestParam BigDecimal orderAmount,
            @Parameter(description = "Order ID") @RequestParam Long orderId) {
        CouponService.CouponValidationResult result = couponService.validateCoupon(couponCode, userId, orderAmount, orderId);
        return ResponseEntity.ok(result);
    }

    /**
     * Apply coupon
     */
    @PostMapping("/apply")
    @Operation(summary = "Apply coupon", description = "Apply a coupon to an order")
    public ResponseEntity<CouponService.CouponUsage> applyCoupon(
            @Parameter(description = "Coupon code") @RequestParam String couponCode,
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Order ID") @RequestParam Long orderId,
            @Parameter(description = "Order amount") @RequestParam BigDecimal orderAmount) {
        CouponService.CouponUsage couponUsage = couponService.applyCoupon(couponCode, userId, orderId, orderAmount);
        return ResponseEntity.ok(couponUsage);
    }

    /**
     * Get valid coupons for user
     */
    @GetMapping("/valid")
    @Operation(summary = "Get valid coupons", description = "Get valid coupons for a user and order amount")
    public ResponseEntity<List<CouponDTO>> getValidCoupons(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Order amount") @RequestParam BigDecimal orderAmount) {
        List<CouponDTO> coupons = couponService.getValidCoupons(userId, orderAmount);
        return ResponseEntity.ok(coupons);
    }

    /**
     * Get coupon by code
     */
    @GetMapping("/code/{code}")
    @Operation(summary = "Get coupon by code", description = "Get coupon details by code")
    public ResponseEntity<CouponDTO> getCouponByCode(
            @Parameter(description = "Coupon code") @PathVariable String code) {
        CouponDTO coupon = couponService.getCouponByCode(code);
        return ResponseEntity.ok(coupon);
    }

    /**
     * Get coupon by ID
     */
    @GetMapping("/{couponId}")
    @Operation(summary = "Get coupon by ID", description = "Get coupon details by ID")
    public ResponseEntity<CouponDTO> getCouponById(
            @Parameter(description = "Coupon ID") @PathVariable Long couponId) {
        CouponDTO coupon = couponService.getCouponById(couponId);
        return ResponseEntity.ok(coupon);
    }

    /**
     * Get coupons by type
     */
    @GetMapping("/type/{type}")
    @Operation(summary = "Get coupons by type", description = "Get coupons by type")
    public ResponseEntity<List<CouponDTO>> getCouponsByType(
            @Parameter(description = "Coupon type") @PathVariable Coupon.CouponType type) {
        List<CouponDTO> coupons = couponService.getCouponsByType(type);
        return ResponseEntity.ok(coupons);
    }

    /**
     * Get coupons by creator
     */
    @GetMapping("/creator/{createdById}")
    @Operation(summary = "Get coupons by creator", description = "Get coupons created by a user")
    public ResponseEntity<List<CouponDTO>> getCouponsByCreator(
            @Parameter(description = "Creator user ID") @PathVariable Long createdById) {
        List<CouponDTO> coupons = couponService.getCouponsByCreator(createdById);
        return ResponseEntity.ok(coupons);
    }

    /**
     * Get coupon dashboard data
     */
    @GetMapping("/dashboard")
    @Operation(summary = "Get coupon dashboard data", description = "Get comprehensive coupon dashboard data")
    public ResponseEntity<CouponService.CouponDashboardDTO> getCouponDashboard(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        CouponService.CouponDashboardDTO dashboard = couponService.getCouponDashboard(startDate, endDate);
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Export coupon data
     */
    @GetMapping("/export")
    @Operation(summary = "Export coupon data", description = "Export coupon data to file (JSON/CSV)")
    public ResponseEntity<String> exportCouponData(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Export format") @RequestParam(defaultValue = "json") String format) {
        String fileUrl = couponService.exportCouponData(startDate, endDate, format);
        return ResponseEntity.ok(fileUrl);
    }
}
