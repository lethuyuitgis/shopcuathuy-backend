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

import com.shopcuathuy.dto.AddToWishlistDTO;
import com.shopcuathuy.dto.WishlistDTO;
import com.shopcuathuy.service.WishlistService;
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


/**
 * Wishlist Controller
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist", description = "Wishlist management APIs")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    /**
     * Add product to wishlist
     */
    @PostMapping("/add")
    @Operation(summary = "Add product to wishlist", description = "Add a product to user's wishlist")
    public ResponseEntity<WishlistDTO> addToWishlist(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Valid @RequestBody AddToWishlistDTO addToWishlistDTO) {
        WishlistDTO wishlistItem = wishlistService.addToWishlist(userId, addToWishlistDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistItem);
    }

    /**
     * Get user's wishlist
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's wishlist", description = "Get all items in user's wishlist")
    public ResponseEntity<List<WishlistDTO>> getUserWishlist(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        List<WishlistDTO> wishlistItems = wishlistService.getUserWishlist(userId);
        return ResponseEntity.ok(wishlistItems);
    }

    /**
     * Get user's wishlist with pagination
     */
    @GetMapping("/user/{userId}/paged")
    @Operation(summary = "Get user's wishlist with pagination", description = "Get paginated items in user's wishlist")
    public ResponseEntity<Page<WishlistDTO>> getUserWishlist(
            @Parameter(description = "User ID") @PathVariable Long userId,
            Pageable pageable) {
        Page<WishlistDTO> wishlistItems = wishlistService.getUserWishlist(userId, pageable);
        return ResponseEntity.ok(wishlistItems);
    }

    /**
     * Remove product from wishlist
     */
    @DeleteMapping("/product/{productId}")
    @Operation(summary = "Remove product from wishlist", description = "Remove a product from user's wishlist")
    public ResponseEntity<Void> removeFromWishlist(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Product ID") @PathVariable Long productId) {
        wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remove wishlist item by ID
     */
    @DeleteMapping("/{wishlistItemId}")
    @Operation(summary = "Remove wishlist item", description = "Remove a wishlist item by ID")
    public ResponseEntity<Void> removeWishlistItem(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Wishlist item ID") @PathVariable Long wishlistItemId) {
        wishlistService.removeWishlistItem(userId, wishlistItemId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Clear user's wishlist
     */
    @DeleteMapping("/user/{userId}/clear")
    @Operation(summary = "Clear user's wishlist", description = "Remove all items from user's wishlist")
    public ResponseEntity<Void> clearWishlist(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        wishlistService.clearWishlist(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if product is in wishlist
     */
    @GetMapping("/check")
    @Operation(summary = "Check if product is in wishlist", description = "Check if a product is in user's wishlist")
    public ResponseEntity<Boolean> isProductInWishlist(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Product ID") @RequestParam Long productId) {
        boolean isInWishlist = wishlistService.isProductInWishlist(userId, productId);
        return ResponseEntity.ok(isInWishlist);
    }

    /**
     * Get wishlist count
     */
    @GetMapping("/user/{userId}/count")
    @Operation(summary = "Get wishlist count", description = "Get total number of items in user's wishlist")
    public ResponseEntity<Long> getWishlistCount(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        long count = wishlistService.getWishlistCount(userId);
        return ResponseEntity.ok(count);
    }

    /**
     * Search wishlist items
     */
    @GetMapping("/user/{userId}/search")
    @Operation(summary = "Search wishlist items", description = "Search items in user's wishlist by product name")
    public ResponseEntity<List<WishlistDTO>> searchWishlist(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Product name") @RequestParam String productName) {
        List<WishlistDTO> wishlistItems = wishlistService.searchWishlist(userId, productName);
        return ResponseEntity.ok(wishlistItems);
    }

    /**
     * Get most wishlisted products
     */
    @GetMapping("/most-wishlisted")
    @Operation(summary = "Get most wishlisted products", description = "Get products that are most wishlisted")
    public ResponseEntity<List<WishlistService.MostWishlistedProductDTO>> getMostWishlistedProducts(Pageable pageable) {
        List<WishlistService.MostWishlistedProductDTO> products = wishlistService.getMostWishlistedProducts(pageable);
        return ResponseEntity.ok(products);
    }
}
