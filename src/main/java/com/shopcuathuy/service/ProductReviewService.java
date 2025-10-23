package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.shopcuathuy.dto.CreateReviewDTO;
import com.shopcuathuy.dto.ProductReviewDTO;
import com.shopcuathuy.entity.*;
import com.shopcuathuy.mapper.ProductReviewMapper;
import com.shopcuathuy.repository.OrderRepository;
import com.shopcuathuy.repository.ProductRepository;
import com.shopcuathuy.repository.ProductReviewRepository;
import com.shopcuathuy.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


/**
 * Product Review Service
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
public class ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductReviewMapper productReviewMapper;
    private final MessageProducerService messageProducerService;
    private final FileStorageService fileStorageService;

    public ProductReviewService(ProductReviewRepository productReviewRepository, 
                               ProductRepository productRepository, UserRepository userRepository,
                               OrderRepository orderRepository, ProductReviewMapper productReviewMapper,
                               MessageProducerService messageProducerService, FileStorageService fileStorageService) {
        this.productReviewRepository = productReviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productReviewMapper = productReviewMapper;
        this.messageProducerService = messageProducerService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Create product review
     */
    @Transactional
    public ProductReviewDTO createReview(Long userId, CreateReviewDTO createReviewDTO) {
        // Validate user
        User user = userRepository.findById(String.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate product
        Product product = productRepository.findById(String.valueOf(createReviewDTO.getProductId()))
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if user already reviewed this product
        Optional<ProductReview> existingReview = productReviewRepository.findByUserIdAndProductId(userId, createReviewDTO.getProductId());
        if (existingReview.isPresent()) {
            throw new RuntimeException("You have already reviewed this product");
        }

        // Validate order if provided
        Order order = null;
        if (createReviewDTO.getOrderId() != null) {
            order = orderRepository.findById(String.valueOf(createReviewDTO.getOrderId()))
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            
            if (!order.getUser().getId().equals(userId)) {
                throw new RuntimeException("Order does not belong to user");
            }
        }

        // Create review
        ProductReview review = new ProductReview();
        review.setProduct(product);
        review.setUser(user);
        review.setOrder(order);
        review.setRating(createReviewDTO.getRating());
        review.setTitle(createReviewDTO.getTitle());
        review.setContent(createReviewDTO.getContent());
        review.setIsVerifiedPurchase(order != null);
        review.setStatus(ProductReview.ReviewStatus.PENDING);

        review = productReviewRepository.save(review);

        // Send review created message
        messageProducerService.sendReviewCreated(review);

        // Store review data to MinIO
        storeReviewToMinIO(review);

        return productReviewMapper.toDTO(review);
    }

    /**
     * Get reviews by product ID
     */
    public List<ProductReviewDTO> getProductReviews(Long productId, ProductReview.ReviewStatus status) {
        List<ProductReview> reviews = productReviewRepository.findByProductIdAndStatus(productId, status);
        return productReviewMapper.toDTOList(reviews);
    }

    /**
     * Get reviews by product ID with pagination
     */
    public Page<ProductReviewDTO> getProductReviews(Long productId, ProductReview.ReviewStatus status, Pageable pageable) {
        Page<ProductReview> reviews = productReviewRepository.findByProductIdAndStatus(productId, status, pageable);
        return reviews.map(productReviewMapper::toDTO);
    }

    /**
     * Get user's reviews
     */
    public List<ProductReviewDTO> getUserReviews(Long userId) {
        List<ProductReview> reviews = productReviewRepository.findByUserId(userId);
        return productReviewMapper.toDTOList(reviews);
    }

    /**
     * Get user's reviews with pagination
     */
    public Page<ProductReviewDTO> getUserReviews(Long userId, Pageable pageable) {
        Page<ProductReview> reviews = productReviewRepository.findByUserId(userId, pageable);
        return reviews.map(productReviewMapper::toDTO);
    }

    /**
     * Get review by ID
     */
    public ProductReviewDTO getReviewById(Long reviewId) {
        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return productReviewMapper.toDTO(review);
    }

    /**
     * Update review
     */
    @Transactional
    public ProductReviewDTO updateReview(Long userId, Long reviewId, CreateReviewDTO updateReviewDTO) {
        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to review");
        }

        if (review.getStatus() != ProductReview.ReviewStatus.PENDING) {
            throw new RuntimeException("Cannot update approved review");
        }

        review.setRating(updateReviewDTO.getRating());
        review.setTitle(updateReviewDTO.getTitle());
        review.setContent(updateReviewDTO.getContent());

        review = productReviewRepository.save(review);

        return productReviewMapper.toDTO(review);
    }

    /**
     * Delete review
     */
    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to review");
        }

        productReviewRepository.delete(review);
    }

    /**
     * Mark review as helpful
     */
    @Transactional
    public ProductReviewDTO markAsHelpful(Long reviewId) {
        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setHelpfulCount(review.getHelpfulCount() + 1);
        review = productReviewRepository.save(review);

        return productReviewMapper.toDTO(review);
    }

    /**
     * Mark review as not helpful
     */
    @Transactional
    public ProductReviewDTO markAsNotHelpful(Long reviewId) {
        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setNotHelpfulCount(review.getNotHelpfulCount() + 1);
        review = productReviewRepository.save(review);

        return productReviewMapper.toDTO(review);
    }

    /**
     * Get product rating summary
     */
    public ProductRatingSummaryDTO getProductRatingSummary(Long productId) {
        long totalReviews = productReviewRepository.countByProductIdAndStatus(productId, ProductReview.ReviewStatus.APPROVED);
        Double averageRating = productReviewRepository.calculateAverageRating(productId, ProductReview.ReviewStatus.APPROVED);
        
        long rating5 = productReviewRepository.countByProductIdAndRatingAndStatus(productId, 5, ProductReview.ReviewStatus.APPROVED);
        long rating4 = productReviewRepository.countByProductIdAndRatingAndStatus(productId, 4, ProductReview.ReviewStatus.APPROVED);
        long rating3 = productReviewRepository.countByProductIdAndRatingAndStatus(productId, 3, ProductReview.ReviewStatus.APPROVED);
        long rating2 = productReviewRepository.countByProductIdAndRatingAndStatus(productId, 2, ProductReview.ReviewStatus.APPROVED);
        long rating1 = productReviewRepository.countByProductIdAndRatingAndStatus(productId, 1, ProductReview.ReviewStatus.APPROVED);

        return new ProductRatingSummaryDTO(totalReviews, averageRating, rating5, rating4, rating3, rating2, rating1);
    }

    /**
     * Product Rating Summary DTO
     */
    public static class ProductRatingSummaryDTO {
        private long totalReviews;
        private Double averageRating;
        private long rating5;
        private long rating4;
        private long rating3;
        private long rating2;
        private long rating1;

        public ProductRatingSummaryDTO(long totalReviews, Double averageRating, long rating5, long rating4, 
                                     long rating3, long rating2, long rating1) {
            this.totalReviews = totalReviews;
            this.averageRating = averageRating;
            this.rating5 = rating5;
            this.rating4 = rating4;
            this.rating3 = rating3;
            this.rating2 = rating2;
            this.rating1 = rating1;
        }

        // Getters and Setters
        public long getTotalReviews() { return totalReviews; }
        public void setTotalReviews(long totalReviews) { this.totalReviews = totalReviews; }

        public Double getAverageRating() { return averageRating; }
        public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

        public long getRating5() { return rating5; }
        public void setRating5(long rating5) { this.rating5 = rating5; }

        public long getRating4() { return rating4; }
        public void setRating4(long rating4) { this.rating4 = rating4; }

        public long getRating3() { return rating3; }
        public void setRating3(long rating3) { this.rating3 = rating3; }

        public long getRating2() { return rating2; }
        public void setRating2(long rating2) { this.rating2 = rating2; }

        public long getRating1() { return rating1; }
        public void setRating1(long rating1) { this.rating1 = rating1; }
    }

    /**
     * Export review data
     */
    public String exportReviewData(Long productId, String format) {
        List<ProductReview> reviews = productReviewRepository.findByProductIdAndStatus(productId, ProductReview.ReviewStatus.APPROVED);
        
        // Generate export data
        String exportData = generateReviewExportData(reviews, format);
        
        // Store to MinIO
        String fileName = "review-export-" + productId + "-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadReviewExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Store review data to MinIO
     */
    private void storeReviewToMinIO(ProductReview review) {
        try {
            String reviewData = createReviewDataJson(review);
            String fileName = "reviews/" + review.getProduct().getId() + "/" +
                            review.getCreatedAt().toLocalDate() + "/" +
                            review.getId() + ".json";
            
            fileStorageService.uploadReviewData(fileName, reviewData);
        } catch (Exception e) {
            System.err.println("Failed to store review data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create review data JSON
     */
    private String createReviewDataJson(ProductReview review) {
        return String.format(
            "{\"id\":%d,\"userId\":%d,\"productId\":%d,\"rating\":%d,\"title\":\"%s\",\"comment\":\"%s\",\"createdAt\":\"%s\"}",
            review.getId(),
            review.getUser().getId(),
            review.getProduct().getId(),
            review.getRating(),
            review.getTitle(),
            review.getContent(),
            review.getCreatedAt()
        );
    }

    /**
     * Generate review export data
     */
    private String generateReviewExportData(List<ProductReview> reviews, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateReviewJsonExport(reviews);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateReviewCsvExport(reviews);
        }
        return generateReviewJsonExport(reviews);
    }

    /**
     * Generate JSON export
     */
    private String generateReviewJsonExport(List<ProductReview> reviews) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < reviews.size(); i++) {
            ProductReview review = reviews.get(i);
            json.append(createReviewDataJson(review));
            if (i < reviews.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateReviewCsvExport(List<ProductReview> reviews) {
        StringBuilder csv = new StringBuilder("id,userId,productId,rating,title,comment,createdAt\n");
        for (ProductReview review : reviews) {
            csv.append(String.format("%d,%d,%d,%d,%s,%s,%s\n",
                review.getId(),
                review.getUser().getId(),
                review.getProduct().getId(),
                review.getRating(),
                review.getTitle(),
                review.getContent(),
                review.getCreatedAt()
            ));
        }
        return csv.toString();
    }
}
