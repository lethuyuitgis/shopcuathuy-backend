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

import com.shopcuathuy.dto.AddToCartDTO;
import com.shopcuathuy.dto.CartDTO;
import com.shopcuathuy.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Cart Controller
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Shopping cart management APIs")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Add item to cart
     */
    @PostMapping("/add")
    @Operation(summary = "Add item to cart", description = "Add a product to user's shopping cart")
    public ResponseEntity<CartDTO> addToCart(
            @Parameter(description = "User ID") @RequestParam String userId,
            @Valid @RequestBody AddToCartDTO addToCartDTO) {
        CartDTO cartItem = cartService.addToCart(userId, addToCartDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
    }

    /**
     * Get user's cart items
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's cart", description = "Get all items in user's shopping cart")
    public ResponseEntity<List<CartDTO>> getUserCart(
            @Parameter(description = "User ID") @PathVariable String userId) {
        List<CartDTO> cartItems = cartService.getUserCart(userId);
        return ResponseEntity.ok(cartItems);
    }

    /**
     * Update cart item quantity
     */
    @PutMapping("/{cartItemId}/quantity")
    @Operation(summary = "Update cart item quantity", description = "Update quantity of a cart item")
    public ResponseEntity<CartDTO> updateCartItemQuantity(
            @Parameter(description = "User ID") @RequestParam String userId,
            @Parameter(description = "Cart item ID") @PathVariable String cartItemId,
            @Parameter(description = "New quantity") @RequestParam Integer quantity) {
        CartDTO cartItem = cartService.updateCartItemQuantity(userId, cartItemId, quantity);
        return ResponseEntity.ok(cartItem);
    }

    /**
     * Remove item from cart
     */
    @DeleteMapping("/{cartItemId}")
    @Operation(summary = "Remove item from cart", description = "Remove an item from user's cart")
    public ResponseEntity<Void> removeFromCart(
            @Parameter(description = "User ID") @RequestParam String userId,
            @Parameter(description = "Cart item ID") @PathVariable String cartItemId) {
        cartService.removeFromCart(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Clear user's cart
     */
    @DeleteMapping("/user/{userId}/clear")
    @Operation(summary = "Clear user's cart", description = "Remove all items from user's cart")
    public ResponseEntity<Void> clearCart(
            @Parameter(description = "User ID") @PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get cart summary
     */
    @GetMapping("/user/{userId}/summary")
    @Operation(summary = "Get cart summary", description = "Get summary of user's cart (total items and value)")
    public ResponseEntity<CartService.CartSummaryDTO> getCartSummary(
            @Parameter(description = "User ID") @PathVariable String userId) {
        CartService.CartSummaryDTO summary = cartService.getCartSummary(userId);
        return ResponseEntity.ok(summary);
    }
}
