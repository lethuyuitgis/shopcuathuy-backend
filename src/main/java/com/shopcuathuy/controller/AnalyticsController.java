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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopcuathuy.dto.AnalyticsEventDTO;
import com.shopcuathuy.dto.CreateAnalyticsEventDTO;
import com.shopcuathuy.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Analytics Controller
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Analytics", description = "Analytics and tracking APIs")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * Track analytics event
     */
    @PostMapping("/track")
    @Operation(summary = "Track analytics event", description = "Track a new analytics event")
    public ResponseEntity<AnalyticsEventDTO> trackEvent(@Valid @RequestBody CreateAnalyticsEventDTO createEventDTO) {
        AnalyticsEventDTO event = analyticsService.trackEvent(createEventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    /**
     * Get user's analytics events
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's analytics events", description = "Get all analytics events for a user")
    public ResponseEntity<List<AnalyticsEventDTO>> getUserEvents(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<AnalyticsEventDTO> events = analyticsService.getUserEvents(userId);
        return ResponseEntity.ok(events);
    }

    /**
     * Get user's analytics events with pagination
     */
    @GetMapping("/user/{userId}/paged")
    @Operation(summary = "Get user's analytics events with pagination", description = "Get paginated analytics events for a user")
    public ResponseEntity<Page<AnalyticsEventDTO>> getUserEvents(
            @Parameter(description = "User ID") @PathVariable Long userId,
            Pageable pageable) {
        Page<AnalyticsEventDTO> events = analyticsService.getUserEvents(userId, pageable);
        return ResponseEntity.ok(events);
    }

    /**
     * Get analytics events by type
     */
    @GetMapping("/type/{eventType}")
    @Operation(summary = "Get analytics events by type", description = "Get analytics events by event type")
    public ResponseEntity<List<AnalyticsEventDTO>> getEventsByType(
            @Parameter(description = "Event type") @PathVariable String eventType) {
        List<AnalyticsEventDTO> events = analyticsService.getEventsByType(eventType);
        return ResponseEntity.ok(events);
    }

    /**
     * Get analytics events by product
     */
    @GetMapping("/product/{productId}")
    @Operation(summary = "Get analytics events by product", description = "Get analytics events for a product")
    public ResponseEntity<List<AnalyticsEventDTO>> getProductEvents(
            @Parameter(description = "Product ID") @PathVariable Long productId) {
        List<AnalyticsEventDTO> events = analyticsService.getProductEvents(productId);
        return ResponseEntity.ok(events);
    }

    /**
     * Get analytics events by date range
     */
    @GetMapping("/date-range")
    @Operation(summary = "Get analytics events by date range", description = "Get analytics events within a date range")
    public ResponseEntity<List<AnalyticsEventDTO>> getEventsByDateRange(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<AnalyticsEventDTO> events = analyticsService.getEventsByDateRange(startDate, endDate);
        return ResponseEntity.ok(events);
    }

    /**
     * Get analytics events by date range with pagination
     */
    @GetMapping("/date-range/paged")
    @Operation(summary = "Get analytics events by date range with pagination", description = "Get paginated analytics events within a date range")
    public ResponseEntity<Page<AnalyticsEventDTO>> getEventsByDateRange(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        Page<AnalyticsEventDTO> events = analyticsService.getEventsByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(events);
    }

    /**
     * Get analytics dashboard data
     */
    @GetMapping("/dashboard")
    @Operation(summary = "Get analytics dashboard data", description = "Get comprehensive analytics dashboard data")
    public ResponseEntity<AnalyticsService.AnalyticsDashboardDTO> getDashboardData(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        AnalyticsService.AnalyticsDashboardDTO dashboard = analyticsService.getDashboardData(startDate, endDate);
        return ResponseEntity.ok(dashboard);
    }

    /**
     * Export analytics data
     */
    @GetMapping("/export")
    @Operation(summary = "Export analytics data", description = "Export analytics data to file (JSON/CSV)")
    public ResponseEntity<String> exportAnalyticsData(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Export format") @RequestParam(defaultValue = "json") String format) {
        String fileUrl = analyticsService.exportAnalyticsData(startDate, endDate, format);
        return ResponseEntity.ok(fileUrl);
    }
}
