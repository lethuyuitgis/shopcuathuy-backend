package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.shopcuathuy.dto.AddToCartDTO;
import com.shopcuathuy.dto.CartDTO;
import com.shopcuathuy.entity.*;
import com.shopcuathuy.mapper.CartMapper;
import com.shopcuathuy.repository.CartRepository;
import com.shopcuathuy.repository.ProductRepository;
import com.shopcuathuy.repository.ProductVariantRepository;
import com.shopcuathuy.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Cart Service
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartMapper cartMapper;
    private final MessageProducerService messageProducerService;
    private final FileStorageService fileStorageService;

    public CartService(CartRepository cartRepository, UserRepository userRepository, 
                      ProductRepository productRepository, ProductVariantRepository productVariantRepository,
                      CartMapper cartMapper, MessageProducerService messageProducerService,
                      FileStorageService fileStorageService) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
        this.cartMapper = cartMapper;
        this.messageProducerService = messageProducerService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Add item to cart
     */
    @Transactional
    public CartDTO addToCart(Long userId, AddToCartDTO addToCartDTO) {
        // Validate user
        User user = userRepository.findById(String.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate product
        Product product = productRepository.findById(String.valueOf(addToCartDTO.getProductId()))
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStatus() != Product.ProductStatus.ACTIVE) {
            throw new RuntimeException("Product is not available");
        }

        // Validate product variant if provided
        ProductVariant productVariant = null;
        if (addToCartDTO.getProductVariantId() != null) {
            productVariant = productVariantRepository.findById(addToCartDTO.getProductVariantId())
                    .orElseThrow(() -> new RuntimeException("Product variant not found"));
            
            if (!productVariant.getProduct().getId().equals(product.getId())) {
                throw new RuntimeException("Product variant does not belong to the product");
            }
        }

        // Check if item already exists in cart
        Optional<Cart> existingCart = cartRepository.findByUserIdAndProductIdAndProductVariantIdAndIsActiveTrue(
                userId, addToCartDTO.getProductId(), addToCartDTO.getProductVariantId());

        Cart cartItem;
        if (existingCart.isPresent()) {
            // Update quantity
            cartItem = existingCart.get();
            cartItem.setQuantity(cartItem.getQuantity() + addToCartDTO.getQuantity());
        } else {
            // Create new cart item
            cartItem = new Cart();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setProductVariant(productVariant);
            cartItem.setQuantity(addToCartDTO.getQuantity());
            cartItem.setNotes(addToCartDTO.getNotes());
        }

        // Calculate prices
        BigDecimal unitPrice = productVariant != null ? productVariant.getPrice() : product.getPrice();
        cartItem.setUnitPrice(unitPrice);
        cartItem.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        cartItem = cartRepository.save(cartItem);

        // Send cart updated message
        messageProducerService.sendCartUpdated(userId, cartItem.getId());

        // Store cart data to MinIO
        storeCartToMinIO(cartItem);

        return cartMapper.toDTO(cartItem);
    }

    /**
     * Get user's cart items
     */
    public List<CartDTO> getUserCart(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserIdAndIsActiveTrue(userId);
        return cartMapper.toDTOList(cartItems);
    }

    /**
     * Update cart item quantity
     */
    @Transactional
    public CartDTO updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity) {
        Cart cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to cart item");
        }

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));

        cartItem = cartRepository.save(cartItem);

        return cartMapper.toDTO(cartItem);
    }

    /**
     * Remove item from cart
     */
    @Transactional
    public void removeFromCart(Long userId, Long cartItemId) {
        Cart cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to cart item");
        }

        cartItem.setIsActive(false);
        cartRepository.save(cartItem);

        // Send cart updated message
        messageProducerService.sendCartUpdated(userId, cartItemId);
    }

    /**
     * Clear user's cart
     */
    @Transactional
    public void clearCart(Long userId) {
        cartRepository.deleteByUserIdAndIsActiveTrue(userId);
        
        // Send cart cleared message
        messageProducerService.sendCartCleared(userId);
    }

    /**
     * Get cart summary
     */
    public CartSummaryDTO getCartSummary(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserIdAndIsActiveTrue(userId);
        
        int totalItems = cartItems.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
        
        BigDecimal totalValue = cartItems.stream()
                .map(Cart::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartSummaryDTO(totalItems, totalValue);
    }

    /**
     * Cart Summary DTO
     */
    public static class CartSummaryDTO {
        private int totalItems;
        private BigDecimal totalValue;

        public CartSummaryDTO(int totalItems, BigDecimal totalValue) {
            this.totalItems = totalItems;
            this.totalValue = totalValue;
        }

        public int getTotalItems() { return totalItems; }
        public void setTotalItems(int totalItems) { this.totalItems = totalItems; }

        public BigDecimal getTotalValue() { return totalValue; }
        public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }
    }

    /**
     * Export cart data
     */
    public String exportCartData(Long userId, String format) {
        List<Cart> cartItems = cartRepository.findByUserIdAndIsActiveTrue(userId);
        
        // Generate export data
        String exportData = generateCartExportData(cartItems, format);
        
        // Store to MinIO
        String fileName = "cart-export-" + userId + "-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadCartExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Store cart data to MinIO
     */
    private void storeCartToMinIO(Cart cartItem) {
        try {
            String cartData = createCartDataJson(cartItem);
            String fileName = "carts/" + cartItem.getUser().getId() + "/" +
                            cartItem.getCreatedAt().toLocalDate() + "/" +
                            cartItem.getId() + ".json";
            
            fileStorageService.uploadCartData(fileName, cartData);
        } catch (Exception e) {
            System.err.println("Failed to store cart data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create cart data JSON
     */
    private String createCartDataJson(Cart cartItem) {
        return String.format(
            "{\"id\":%d,\"userId\":%d,\"productId\":%d,\"quantity\":%d,\"unitPrice\":%s,\"totalPrice\":%s,\"createdAt\":\"%s\"}",
            cartItem.getId(),
            cartItem.getUser().getId(),
            cartItem.getProduct().getId(),
            cartItem.getQuantity(),
            cartItem.getUnitPrice(),
            cartItem.getTotalPrice(),
            cartItem.getCreatedAt()
        );
    }

    /**
     * Generate cart export data
     */
    private String generateCartExportData(List<Cart> cartItems, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateCartJsonExport(cartItems);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateCartCsvExport(cartItems);
        }
        return generateCartJsonExport(cartItems);
    }

    /**
     * Generate JSON export
     */
    private String generateCartJsonExport(List<Cart> cartItems) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < cartItems.size(); i++) {
            Cart cartItem = cartItems.get(i);
            json.append(createCartDataJson(cartItem));
            if (i < cartItems.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateCartCsvExport(List<Cart> cartItems) {
        StringBuilder csv = new StringBuilder("id,userId,productId,quantity,unitPrice,totalPrice,createdAt\n");
        for (Cart cartItem : cartItems) {
            csv.append(String.format("%d,%d,%d,%d,%s,%s,%s\n",
                cartItem.getId(),
                cartItem.getUser().getId(),
                cartItem.getProduct().getId(),
                cartItem.getQuantity(),
                cartItem.getUnitPrice(),
                cartItem.getTotalPrice(),
                cartItem.getCreatedAt()
            ));
        }
        return csv.toString();
    }
}
