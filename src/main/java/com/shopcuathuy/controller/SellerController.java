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

import com.shopcuathuy.dto.CreateSellerDTO;
import com.shopcuathuy.dto.SellerDTO;
import com.shopcuathuy.entity.Seller;
import com.shopcuathuy.service.SellerService;
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


/**
 * Seller Controller
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/sellers")
@Tag(name = "Seller", description = "Seller management APIs")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    /**
     * Create seller
     */
    @PostMapping
    @Operation(summary = "Create seller", description = "Create a new seller")
    public ResponseEntity<SellerDTO> createSeller(@Valid @RequestBody CreateSellerDTO createSellerDTO) {
        SellerDTO seller = sellerService.createSeller(createSellerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(seller);
    }

    /**
     * Update seller status
     */
    @PutMapping("/{sellerId}/status")
    @Operation(summary = "Update seller status", description = "Update seller status")
    public ResponseEntity<SellerDTO> updateSellerStatus(
            @Parameter(description = "Seller ID") @PathVariable Long sellerId,
            @Parameter(description = "New status") @RequestParam Seller.SellerStatus status,
            @Parameter(description = "Reason") @RequestParam(required = false) String reason) {
        SellerDTO seller = sellerService.updateSellerStatus(sellerId, status, reason);
        return ResponseEntity.ok(seller);
    }

    /**
     * Get seller by ID
     */
    @GetMapping("/{sellerId}")
    @Operation(summary = "Get seller by ID", description = "Get seller details by ID")
    public ResponseEntity<SellerDTO> getSellerById(
            @Parameter(description = "Seller ID") @PathVariable Long sellerId) {
        SellerDTO seller = sellerService.getSellerById(sellerId);
        return ResponseEntity.ok(seller);
    }

    /**
     * Get seller by user ID
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get seller by user ID", description = "Get seller details by user ID")
    public ResponseEntity<SellerDTO> getSellerByUserId(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        SellerDTO seller = sellerService.getSellerByUserId(userId);
        return ResponseEntity.ok(seller);
    }

    /**
     * Get sellers by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get sellers by status", description = "Get sellers by status")
    public ResponseEntity<List<SellerDTO>> getSellersByStatus(
            @Parameter(description = "Seller status") @PathVariable Seller.SellerStatus status) {
        List<SellerDTO> sellers = sellerService.getSellersByStatus(status);
        return ResponseEntity.ok(sellers);
    }

    /**
     * Get sellers by status with pagination
     */
    @GetMapping("/status/{status}/paged")
    @Operation(summary = "Get sellers by status with pagination", description = "Get paginated sellers by status")
    public ResponseEntity<Page<SellerDTO>> getSellersByStatus(
            @Parameter(description = "Seller status") @PathVariable Seller.SellerStatus status,
            Pageable pageable) {
        Page<SellerDTO> sellers = sellerService.getSellersByStatus(status, pageable);
        return ResponseEntity.ok(sellers);
    }

    /**
     * Get all sellers with pagination
     */
    @GetMapping
    @Operation(summary = "Get all sellers", description = "Get all sellers with pagination")
    public ResponseEntity<Page<SellerDTO>> getAllSellers(Pageable pageable) {
        Page<SellerDTO> sellers = sellerService.getAllSellers(pageable);
        return ResponseEntity.ok(sellers);
    }

    /**
     * Search sellers
     */
    @GetMapping("/search")
    @Operation(summary = "Search sellers", description = "Search sellers by business name or description")
    public ResponseEntity<List<SellerDTO>> searchSellers(
            @Parameter(description = "Search term") @RequestParam String searchTerm) {
        List<SellerDTO> sellers = sellerService.searchSellers(searchTerm);
        return ResponseEntity.ok(sellers);
    }

    /**
     * Get top sellers
     */
    @GetMapping("/top")
    @Operation(summary = "Get top sellers", description = "Get top performing sellers")
    public ResponseEntity<List<SellerDTO>> getTopSellers(
            @Parameter(description = "Limit") @RequestParam(defaultValue = "10") int limit) {
        List<SellerDTO> sellers = sellerService.getTopSellers(limit);
        return ResponseEntity.ok(sellers);
    }

    /**
     * Get seller dashboard data
     */
    @GetMapping("/{sellerId}/dashboard")
    @Operation(summary = "Get seller dashboard data", description = "Get comprehensive seller dashboard data")
    public ResponseEntity<SellerService.SellerDashboardDTO> getSellerDashboard(
            @Parameter(description = "Seller ID") @PathVariable Long sellerId,
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        SellerService.SellerDashboardDTO dashboard = sellerService.getSellerDashboard(sellerId, startDate, endDate);
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Export seller data
     */
    @GetMapping("/{sellerId}/export")
    @Operation(summary = "Export seller data", description = "Export seller data to file (JSON/CSV)")
    public ResponseEntity<String> exportSellerData(
            @Parameter(description = "Seller ID") @PathVariable Long sellerId,
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Export format") @RequestParam(defaultValue = "json") String format) {
        String fileUrl = sellerService.exportSellerData(sellerId, startDate, endDate, format);
        return ResponseEntity.ok(fileUrl);
    }
}
