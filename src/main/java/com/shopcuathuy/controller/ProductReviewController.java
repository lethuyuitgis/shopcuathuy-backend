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

import com.shopcuathuy.dto.CreateReviewDTO;
import com.shopcuathuy.dto.ProductReviewDTO;
import com.shopcuathuy.entity.ProductReview;
import com.shopcuathuy.service.ProductReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * Product Review Controller
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Product Review", description = "Product review management APIs")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    /**
     * Create product review
     */
    @PostMapping
    @Operation(summary = "Create product review", description = "Create a new product review")
    public ResponseEntity<ProductReviewDTO> createReview(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Valid @RequestBody CreateReviewDTO createReviewDTO) {
        ProductReviewDTO review = productReviewService.createReview(userId, createReviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    /**
     * Get reviews by product ID
     */
    @GetMapping("/product/{productId}")
    @Operation(summary = "Get product reviews", description = "Get all reviews for a product")
    public ResponseEntity<List<ProductReviewDTO>> getProductReviews(
            @Parameter(description = "Product ID") @PathVariable Long productId,
            @Parameter(description = "Review status") @RequestParam(defaultValue = "APPROVED") ProductReview.ReviewStatus status) {
        List<ProductReviewDTO> reviews = productReviewService.getProductReviews(productId, status);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get reviews by product ID with pagination
     */
    @GetMapping("/product/{productId}/paged")
    @Operation(summary = "Get product reviews with pagination", description = "Get paginated reviews for a product")
    public ResponseEntity<Page<ProductReviewDTO>> getProductReviews(
            @Parameter(description = "Product ID") @PathVariable Long productId,
            @Parameter(description = "Review status") @RequestParam(defaultValue = "APPROVED") ProductReview.ReviewStatus status,
            Pageable pageable) {
        Page<ProductReviewDTO> reviews = productReviewService.getProductReviews(productId, status, pageable);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get user's reviews
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user reviews", description = "Get all reviews by a user")
    public ResponseEntity<List<ProductReviewDTO>> getUserReviews(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<ProductReviewDTO> reviews = productReviewService.getUserReviews(userId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get user's reviews with pagination
     */
    @GetMapping("/user/{userId}/paged")
    @Operation(summary = "Get user reviews with pagination", description = "Get paginated reviews by a user")
    public ResponseEntity<Page<ProductReviewDTO>> getUserReviews(
            @Parameter(description = "User ID") @PathVariable Long userId,
            Pageable pageable) {
        Page<ProductReviewDTO> reviews = productReviewService.getUserReviews(userId, pageable);
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get review by ID
     */
    @GetMapping("/{reviewId}")
    @Operation(summary = "Get review by ID", description = "Get review details by ID")
    public ResponseEntity<ProductReviewDTO> getReviewById(
            @Parameter(description = "Review ID") @PathVariable Long reviewId) {
        ProductReviewDTO review = productReviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    /**
     * Update review
     */
    @PutMapping("/{reviewId}")
    @Operation(summary = "Update review", description = "Update a product review")
    public ResponseEntity<ProductReviewDTO> updateReview(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Review ID") @PathVariable Long reviewId,
            @Valid @RequestBody CreateReviewDTO updateReviewDTO) {
        ProductReviewDTO review = productReviewService.updateReview(userId, reviewId, updateReviewDTO);
        return ResponseEntity.ok(review);
    }

    /**
     * Delete review
     */
    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete review", description = "Delete a product review")
    public ResponseEntity<Void> deleteReview(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Review ID") @PathVariable Long reviewId) {
        productReviewService.deleteReview(userId, reviewId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark review as helpful
     */
    @PostMapping("/{reviewId}/helpful")
    @Operation(summary = "Mark review as helpful", description = "Mark a review as helpful")
    public ResponseEntity<ProductReviewDTO> markAsHelpful(
            @Parameter(description = "Review ID") @PathVariable Long reviewId) {
        ProductReviewDTO review = productReviewService.markAsHelpful(reviewId);
        return ResponseEntity.ok(review);
    }

    /**
     * Mark review as not helpful
     */
    @PostMapping("/{reviewId}/not-helpful")
    @Operation(summary = "Mark review as not helpful", description = "Mark a review as not helpful")
    public ResponseEntity<ProductReviewDTO> markAsNotHelpful(
            @Parameter(description = "Review ID") @PathVariable Long reviewId) {
        ProductReviewDTO review = productReviewService.markAsNotHelpful(reviewId);
        return ResponseEntity.ok(review);
    }

    /**
     * Get product rating summary
     */
    @GetMapping("/product/{productId}/rating-summary")
    @Operation(summary = "Get product rating summary", description = "Get rating summary for a product")
    public ResponseEntity<ProductReviewService.ProductRatingSummaryDTO> getProductRatingSummary(
            @Parameter(description = "Product ID") @PathVariable Long productId) {
        ProductReviewService.ProductRatingSummaryDTO summary = productReviewService.getProductRatingSummary(productId);
        return ResponseEntity.ok(summary);
    }
}
