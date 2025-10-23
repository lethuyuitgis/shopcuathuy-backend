package com.shopcuathuy.controller;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopcuathuy.dto.CreateShippingDTO;
import com.shopcuathuy.dto.ShippingDTO;
import com.shopcuathuy.entity.Shipping;
import com.shopcuathuy.service.ShippingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Shipping Controller
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/shipping")
@Tag(name = "Shipping", description = "Shipping management APIs")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    /**
     * Create shipping
     */
    @PostMapping
    @Operation(summary = "Create shipping", description = "Create a new shipping record")
    public ResponseEntity<ShippingDTO> createShipping(@Valid @RequestBody CreateShippingDTO createShippingDTO) {
        ShippingDTO shipping = shippingService.createShipping(createShippingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(shipping);
    }

    /**
     * Update shipping status
     */
    @PutMapping("/{shippingId}/status")
    @Operation(summary = "Update shipping status", description = "Update shipping status")
    public ResponseEntity<ShippingDTO> updateShippingStatus(
            @Parameter(description = "Shipping ID") @PathVariable Long shippingId,
            @Parameter(description = "New status") @RequestParam Shipping.ShippingStatus status,
            @Parameter(description = "Notes") @RequestParam(required = false) String notes) {
        ShippingDTO shipping = shippingService.updateShippingStatus(shippingId, status, notes);
        return ResponseEntity.ok(shipping);
    }

    /**
     * Get shipping by ID
     */
    @GetMapping("/{shippingId}")
    @Operation(summary = "Get shipping by ID", description = "Get shipping details by ID")
    public ResponseEntity<ShippingDTO> getShippingById(
            @Parameter(description = "Shipping ID") @PathVariable Long shippingId) {
        ShippingDTO shipping = shippingService.getShippingById(shippingId);
        return ResponseEntity.ok(shipping);
    }

    /**
     * Get shipping by order ID
     */
    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get shipping by order ID", description = "Get shipping details by order ID")
    public ResponseEntity<ShippingDTO> getShippingByOrderId(
            @Parameter(description = "Order ID") @PathVariable Long orderId) {
        ShippingDTO shipping = shippingService.getShippingByOrderId(orderId);
        return ResponseEntity.ok(shipping);
    }

    /**
     * Get shipping by tracking number
     */
    @GetMapping("/track/{trackingNumber}")
    @Operation(summary = "Get shipping by tracking number", description = "Get shipping details by tracking number")
    public ResponseEntity<ShippingDTO> getShippingByTrackingNumber(
            @Parameter(description = "Tracking number") @PathVariable String trackingNumber) {
        ShippingDTO shipping = shippingService.getShippingByTrackingNumber(trackingNumber);
        return ResponseEntity.ok(shipping);
    }

    /**
     * Get shipping by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get shipping by status", description = "Get shipping records by status")
    public ResponseEntity<List<ShippingDTO>> getShippingByStatus(
            @Parameter(description = "Shipping status") @PathVariable Shipping.ShippingStatus status) {
        List<ShippingDTO> shippings = shippingService.getShippingByStatus(status);
        return ResponseEntity.ok(shippings);
    }

    /**
     * Get shipping by status with pagination
     */
    @GetMapping("/status/{status}/paged")
    @Operation(summary = "Get shipping by status with pagination", description = "Get paginated shipping records by status")
    public ResponseEntity<Page<ShippingDTO>> getShippingByStatus(
            @Parameter(description = "Shipping status") @PathVariable Shipping.ShippingStatus status,
            Pageable pageable) {
        Page<ShippingDTO> shippings = shippingService.getShippingByStatus(status, pageable);
        return ResponseEntity.ok(shippings);
    }

    /**
     * Get shipping by carrier
     */
    @GetMapping("/carrier/{carrier}")
    @Operation(summary = "Get shipping by carrier", description = "Get shipping records by carrier")
    public ResponseEntity<List<ShippingDTO>> getShippingByCarrier(
            @Parameter(description = "Carrier name") @PathVariable String carrier) {
        List<ShippingDTO> shippings = shippingService.getShippingByCarrier(carrier);
        return ResponseEntity.ok(shippings);
    }

    /**
     * Get shipping by date range
     */
    @GetMapping("/date-range")
    @Operation(summary = "Get shipping by date range", description = "Get shipping records within a date range")
    public ResponseEntity<List<ShippingDTO>> getShippingByDateRange(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ShippingDTO> shippings = shippingService.getShippingByDateRange(startDate, endDate);
        return ResponseEntity.ok(shippings);
    }

    /**
     * Get overdue shipping
     */
    @GetMapping("/overdue")
    @Operation(summary = "Get overdue shipping", description = "Get shipping records that are overdue")
    public ResponseEntity<List<ShippingDTO>> getOverdueShipping() {
        List<ShippingDTO> shippings = shippingService.getOverdueShipping();
        return ResponseEntity.ok(shippings);
    }

    /**
     * Search shipping
     */
    @GetMapping("/search")
    @Operation(summary = "Search shipping", description = "Search shipping records by tracking number")
    public ResponseEntity<List<ShippingDTO>> searchShipping(
            @Parameter(description = "Search term") @RequestParam String searchTerm) {
        List<ShippingDTO> shippings = shippingService.searchShipping(searchTerm);
        return ResponseEntity.ok(shippings);
    }

    /**
     * Get shipping dashboard data
     */
    @GetMapping("/dashboard")
    @Operation(summary = "Get shipping dashboard data", description = "Get comprehensive shipping dashboard data")
    public ResponseEntity<ShippingService.ShippingDashboardDTO> getShippingDashboard(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        ShippingService.ShippingDashboardDTO dashboard = shippingService.getShippingDashboard(startDate, endDate);
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Export shipping data
     */
    @GetMapping("/export")
    @Operation(summary = "Export shipping data", description = "Export shipping data to file (JSON/CSV)")
    public ResponseEntity<String> exportShippingData(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Export format") @RequestParam(defaultValue = "json") String format) {
        String fileUrl = shippingService.exportShippingData(startDate, endDate, format);
        return ResponseEntity.ok(fileUrl);
    }
}
