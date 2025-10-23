package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.shopcuathuy.dto.AddToWishlistDTO;
import com.shopcuathuy.dto.WishlistDTO;
import com.shopcuathuy.entity.*;
import com.shopcuathuy.mapper.WishlistMapper;
import com.shopcuathuy.repository.ProductRepository;
import com.shopcuathuy.repository.UserRepository;
import com.shopcuathuy.repository.WishlistRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


/**
 * Wishlist Service
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistMapper wishlistMapper;
    private final MessageProducerService messageProducerService;
    private final FileStorageService fileStorageService;

    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository,
                          ProductRepository productRepository, WishlistMapper wishlistMapper,
                          MessageProducerService messageProducerService, FileStorageService fileStorageService) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.wishlistMapper = wishlistMapper;
        this.messageProducerService = messageProducerService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Add product to wishlist
     */
    @Transactional
    public WishlistDTO addToWishlist(Long userId, AddToWishlistDTO addToWishlistDTO) {
        // Validate user
        User user = userRepository.findById(String.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate product
        Product product = productRepository.findById(String.valueOf(addToWishlistDTO.getProductId()))
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStatus() != Product.ProductStatus.ACTIVE) {
            throw new RuntimeException("Product is not available");
        }

        // Check if product is already in wishlist
        Optional<Wishlist> existingWishlist = wishlistRepository.findByUserIdAndProductIdAndIsActiveTrue(userId, addToWishlistDTO.getProductId());
        if (existingWishlist.isPresent()) {
            throw new RuntimeException("Product is already in wishlist");
        }

        // Create wishlist item
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlist.setNotes(addToWishlistDTO.getNotes());
        wishlist.setIsActive(true);

        wishlist = wishlistRepository.save(wishlist);

        // Send wishlist updated message
        messageProducerService.sendWishlistUpdated(userId, wishlist.getId());

        // Store wishlist data to MinIO
        storeWishlistToMinIO(wishlist);

        return wishlistMapper.toDTO(wishlist);
    }

    /**
     * Get user's wishlist
     */
    public List<WishlistDTO> getUserWishlist(Long userId) {
        List<Wishlist> wishlistItems = wishlistRepository.findByUserIdAndIsActiveTrueOrderByCreatedAtDesc(userId);
        return wishlistMapper.toDTOList(wishlistItems);
    }

    /**
     * Get user's wishlist with pagination
     */
    public Page<WishlistDTO> getUserWishlist(Long userId, Pageable pageable) {
        Page<Wishlist> wishlistItems = wishlistRepository.findByUserIdAndIsActiveTrueOrderByCreatedAtDesc(userId, pageable);
        return wishlistItems.map(wishlistMapper::toDTO);
    }

    /**
     * Remove product from wishlist
     */
    @Transactional
    public void removeFromWishlist(Long userId, Long productId) {
        Wishlist wishlist = wishlistRepository.findByUserIdAndProductIdAndIsActiveTrue(userId, productId)
                .orElseThrow(() -> new RuntimeException("Product not found in wishlist"));

        wishlist.setIsActive(false);
        wishlistRepository.save(wishlist);

        // Send wishlist updated message
        messageProducerService.sendWishlistUpdated(userId, wishlist.getId());
    }

    /**
     * Remove wishlist item by ID
     */
    @Transactional
    public void removeWishlistItem(Long userId, Long wishlistItemId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistItemId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));

        if (!wishlist.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to wishlist item");
        }

        wishlist.setIsActive(false);
        wishlistRepository.save(wishlist);

        // Send wishlist updated message
        messageProducerService.sendWishlistUpdated(userId, wishlistItemId);
    }

    /**
     * Clear user's wishlist
     */
    @Transactional
    public void clearWishlist(Long userId) {
        wishlistRepository.deleteByUserIdAndIsActiveTrue(userId);
        
        // Send wishlist cleared message
        messageProducerService.sendWishlistCleared(userId);
    }

    /**
     * Check if product is in user's wishlist
     */
    public boolean isProductInWishlist(Long userId, Long productId) {
        return wishlistRepository.existsByUserIdAndProductIdAndIsActiveTrue(userId, productId);
    }

    /**
     * Get wishlist count for user
     */
    public long getWishlistCount(Long userId) {
        return wishlistRepository.countByUserIdAndIsActiveTrue(userId);
    }

    /**
     * Search wishlist items
     */
    public List<WishlistDTO> searchWishlist(Long userId, String productName) {
        List<Wishlist> wishlistItems = wishlistRepository.findByUserIdAndProductNameContainingIgnoreCase(userId, productName);
        return wishlistMapper.toDTOList(wishlistItems);
    }

    /**
     * Get most wishlisted products
     */
    public List<MostWishlistedProductDTO> getMostWishlistedProducts(Pageable pageable) {
        List<Object[]> results = wishlistRepository.findMostWishlistedProducts(pageable);
        return results.stream()
                .map(result -> new MostWishlistedProductDTO((Long) result[0], (Long) result[1]))
                .toList();
    }

    /**
     * Most Wishlisted Product DTO
     */
    public static class MostWishlistedProductDTO {
        private Long productId;
        private Long wishlistCount;

        public MostWishlistedProductDTO(Long productId, Long wishlistCount) {
            this.productId = productId;
            this.wishlistCount = wishlistCount;
        }

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        public Long getWishlistCount() { return wishlistCount; }
        public void setWishlistCount(Long wishlistCount) { this.wishlistCount = wishlistCount; }
    }

    /**
     * Export wishlist data
     */
    public String exportWishlistData(Long userId, String format) {
        List<Wishlist> wishlistItems = wishlistRepository.findByUserIdAndIsActiveTrue(userId);
        
        // Generate export data
        String exportData = generateWishlistExportData(wishlistItems, format);
        
        // Store to MinIO
        String fileName = "wishlist-export-" + userId + "-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadWishlistExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Store wishlist data to MinIO
     */
    private void storeWishlistToMinIO(Wishlist wishlist) {
        try {
            String wishlistData = createWishlistDataJson(wishlist);
            String fileName = "wishlists/" + wishlist.getUser().getId() + "/" +
                            wishlist.getCreatedAt().toLocalDate() + "/" +
                            wishlist.getId() + ".json";
            
            fileStorageService.uploadWishlistData(fileName, wishlistData);
        } catch (Exception e) {
            System.err.println("Failed to store wishlist data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create wishlist data JSON
     */
    private String createWishlistDataJson(Wishlist wishlist) {
        return String.format(
            "{\"id\":%d,\"userId\":%d,\"productId\":%d,\"createdAt\":\"%s\"}",
            wishlist.getId(),
            wishlist.getUser().getId(),
            wishlist.getProduct().getId(),
            wishlist.getCreatedAt()
        );
    }

    /**
     * Generate wishlist export data
     */
    private String generateWishlistExportData(List<Wishlist> wishlistItems, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateWishlistJsonExport(wishlistItems);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateWishlistCsvExport(wishlistItems);
        }
        return generateWishlistJsonExport(wishlistItems);
    }

    /**
     * Generate JSON export
     */
    private String generateWishlistJsonExport(List<Wishlist> wishlistItems) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < wishlistItems.size(); i++) {
            Wishlist wishlist = wishlistItems.get(i);
            json.append(createWishlistDataJson(wishlist));
            if (i < wishlistItems.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateWishlistCsvExport(List<Wishlist> wishlistItems) {
        StringBuilder csv = new StringBuilder("id,userId,productId,createdAt\n");
        for (Wishlist wishlist : wishlistItems) {
            csv.append(String.format("%d,%d,%d,%s\n",
                wishlist.getId(),
                wishlist.getUser().getId(),
                wishlist.getProduct().getId(),
                wishlist.getCreatedAt()
            ));
        }
        return csv.toString();
    }
}
