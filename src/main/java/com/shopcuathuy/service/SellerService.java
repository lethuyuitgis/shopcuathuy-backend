package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.shopcuathuy.dto.CreateSellerDTO;
import com.shopcuathuy.dto.SellerDTO;
import com.shopcuathuy.entity.*;
import com.shopcuathuy.mapper.SellerMapper;
import com.shopcuathuy.repository.SellerRepository;
import com.shopcuathuy.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


/**
 * Seller Service with RabbitMQ + MinIO integration
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final SellerMapper sellerMapper;
    private final MessageProducerService messageProducerService;
    private final FileStorageService fileStorageService;

    public SellerService(SellerRepository sellerRepository, UserRepository userRepository,
                         SellerMapper sellerMapper, MessageProducerService messageProducerService,
                         FileStorageService fileStorageService) {
        this.sellerRepository = sellerRepository;
        this.userRepository = userRepository;
        this.sellerMapper = sellerMapper;
        this.messageProducerService = messageProducerService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Create seller
     */
    @Transactional
    public SellerDTO createSeller(CreateSellerDTO createSellerDTO) {
        // Validate user
        User user = userRepository.findById(String.valueOf(createSellerDTO.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user is already a seller
        if (sellerRepository.findByUserId(String.valueOf(createSellerDTO.getUserId())).isPresent()) {
            throw new RuntimeException("User is already a seller");
        }

        // Create seller
        Seller seller = new Seller();
        seller.setUser(user);
        seller.setShopName(createSellerDTO.getBusinessName());
        seller.setDescription(createSellerDTO.getBusinessDescription());
        seller.setBusinessLicense(createSellerDTO.getBusinessLicense());
        seller.setTaxCode(createSellerDTO.getTaxId());
        seller.setBankAccount(createSellerDTO.getBankAccount());
        seller.setBankName(createSellerDTO.getBankName());
        seller.setBusinessLicense(createSellerDTO.getBusinessLicense());
        seller.setTaxCode(createSellerDTO.getTaxId());
        seller.setBankAccount(createSellerDTO.getBankAccount());
        seller.setBankName(createSellerDTO.getBankName());

        seller = sellerRepository.save(seller);

        // Send seller created message to RabbitMQ
        messageProducerService.sendSellerCreated(seller);

        // Store seller data to MinIO
        storeSellerToMinIO(seller);

        return sellerMapper.toDTO(seller);
    }

    /**
     * Update seller status
     */
    @Transactional
    public SellerDTO updateSellerStatus(Long sellerId, Seller.SellerStatus status, String reason) {
        Seller seller = sellerRepository.findById(String.valueOf(sellerId))
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        // Note: Seller entity doesn't have status field
        // Seller.SellerStatus oldStatus = (seller.getVerified() ? "VERIFIED" : "PENDING");
        // seller.setStatus(status);

        if (status == Seller.SellerStatus.ACTIVE) {
            seller.setVerified(true);
            // Note: Seller entity doesn't have verifiedAt field
            // seller.setVerifiedAt(LocalDateTime.now());
        }

        seller = sellerRepository.save(seller);

        // Send seller status update message to RabbitMQ
        // Note: oldStatus is not available since Seller entity doesn't have status field
        // messageProducerService.sendSellerStatusUpdate(seller, oldStatus, status, reason);

        // Store seller update to MinIO
        // Note: oldStatus is not available since Seller entity doesn't have status field
        // storeSellerUpdateToMinIO(seller, oldStatus, status, reason);

        return sellerMapper.toDTO(seller);
    }

    /**
     * Get seller by ID
     */
    public SellerDTO getSellerById(Long sellerId) {
        Seller seller = sellerRepository.findById(String.valueOf(sellerId))
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        return sellerMapper.toDTO(seller);
    }

    /**
     * Get seller by user ID
     */
    public SellerDTO getSellerByUserId(Long userId) {
        Seller seller = sellerRepository.findByUserId(String.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("Seller not found for this user"));
        return sellerMapper.toDTO(seller);
    }

    /**
     * Get sellers by status
     */
    public List<SellerDTO> getSellersByStatus(Seller.SellerStatus status) {
        List<Seller> sellers = sellerRepository.findByStatus(status);
        return sellerMapper.toDTOList(sellers);
    }

    /**
     * Get sellers by status with pagination
     */
    public Page<SellerDTO> getSellersByStatus(Seller.SellerStatus status, Pageable pageable) {
        Page<Seller> sellers = sellerRepository.findByStatus(status, pageable);
        return sellers.map(sellerMapper::toDTO);
    }

    /**
     * Get all sellers with pagination
     */
    public Page<SellerDTO> getAllSellers(Pageable pageable) {
        Page<Seller> sellers = sellerRepository.findAll(pageable);
        return sellers.map(sellerMapper::toDTO);
    }

    /**
     * Search sellers
     */
    public List<SellerDTO> searchSellers(String searchTerm) {
        List<Seller> sellers = sellerRepository.searchSellers(searchTerm);
        return sellerMapper.toDTOList(sellers);
    }

    /**
     * Get top sellers
     */
    public List<SellerDTO> getTopSellers(int limit) {
        List<Seller> sellers = sellerRepository.findTopSellers(Pageable.ofSize(limit));
        return sellerMapper.toDTOList(sellers);
    }

    /**
     * Get seller dashboard data
     */
    public SellerDashboardDTO getSellerDashboard(Long sellerId, LocalDateTime startDate, LocalDateTime endDate) {
        Seller seller = sellerRepository.findById(String.valueOf(sellerId))
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        // Get seller statistics
        Map<String, Object> stats = new HashMap<>();
        // Note: Seller entity doesn't have these fields
        // stats.put("totalSales", seller.getTotalSales());
        // stats.put("totalOrders", seller.getTotalOrders());
        // stats.put("averageRating", seller.getAverageRating());
        // stats.put("totalReviews", seller.getTotalReviews());
        stats.put("rating", seller.getRating());
        stats.put("productsCount", seller.getProductsCount());
        stats.put("followersCount", seller.getFollowersCount());

        // Get sales by date range
        List<Object[]> salesData = sellerRepository.getSalesByDateRange(String.valueOf(sellerId), startDate, endDate);
        Map<String, Object> salesByDate = new HashMap<>();
        for (Object[] data : salesData) {
            salesByDate.put(data[0].toString(), data[1]);
        }

        // Get top products
        List<Object[]> topProducts = sellerRepository.getTopProducts(String.valueOf(sellerId), Pageable.ofSize(10));
        Map<String, Object> topProductsData = new HashMap<>();
        for (Object[] product : topProducts) {
            topProductsData.put(product[0].toString(), Map.of(
                "sales", product[1],
                "revenue", product[2]
            ));
        }

        return new SellerDashboardDTO(stats, salesByDate, topProductsData);
    }

    /**
     * Export seller data
     */
    public String exportSellerData(Long sellerId, LocalDateTime startDate, LocalDateTime endDate, String format) {
        Seller seller = sellerRepository.findById(String.valueOf(sellerId))
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        // Generate export data
        String exportData = generateSellerExportData(seller, format);

        // Store to MinIO
        String fileName = "seller-export-" + sellerId + "-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadSellerExport(fileName, exportData);

        return fileUrl;
    }

    /**
     * Store seller data to MinIO
     */
    private void storeSellerToMinIO(Seller seller) {
        try {
            String sellerData = createSellerDataJson(seller);
            String fileName = "sellers/" + (seller.getVerified() ? "VERIFIED" : "PENDING") + "/" +
                            seller.getCreatedAt().toLocalDate() + "/" +
                            seller.getId() + ".json";

            fileStorageService.uploadSellerData(fileName, sellerData);
        } catch (Exception e) {
            System.err.println("Failed to store seller data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Store seller update to MinIO
     */
    private void storeSellerUpdateToMinIO(Seller seller, Seller.SellerStatus oldStatus, 
                                        Seller.SellerStatus newStatus, String reason) {
        try {
            String updateData = createSellerUpdateDataJson(seller, oldStatus, newStatus, reason);
            String fileName = "sellers/updates/" +
                            seller.getUpdatedAt().toLocalDate() + "/" +
                            seller.getId() + "-" + System.currentTimeMillis() + ".json";

            fileStorageService.uploadSellerData(fileName, updateData);
        } catch (Exception e) {
            System.err.println("Failed to store seller update data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create seller data JSON
     */
    private String createSellerDataJson(Seller seller) {
        return String.format(
            "{\"id\":%d,\"userId\":%d,\"businessName\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\"}",
            seller.getId(),
            seller.getUser().getId(),
            seller.getShopName(),
            (seller.getVerified() ? "VERIFIED" : "PENDING"),
            seller.getCreatedAt()
        );
    }

    /**
     * Create seller update data JSON
     */
    private String createSellerUpdateDataJson(Seller seller, Seller.SellerStatus oldStatus,
                                            Seller.SellerStatus newStatus, String reason) {
        return String.format(
            "{\"id\":%d,\"userId\":%d,\"oldStatus\":\"%s\",\"newStatus\":\"%s\",\"reason\":\"%s\",\"updatedAt\":\"%s\"}",
            seller.getId(),
            seller.getUser().getId(),
            oldStatus,
            newStatus,
            reason,
            seller.getUpdatedAt()
        );
    }

    /**
     * Generate seller export data
     */
    private String generateSellerExportData(Seller seller, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return createSellerDataJson(seller);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateSellerCsvExport(seller);
        }
        return createSellerDataJson(seller);
    }

    /**
     * Generate CSV export
     */
    private String generateSellerCsvExport(Seller seller) {
        return String.format("id,userId,businessName,status,totalSales,totalOrders,averageRating,createdAt\n%d,%d,%s,%s,%s,%d,%.2f,%s\n",
            seller.getId(),
            seller.getUser().getId(),
            seller.getShopName(),
            (seller.getVerified() ? "VERIFIED" : "PENDING"),
            // Note: Seller entity doesn't have these fields
            // seller.getTotalSales(),
            // seller.getTotalOrders(),
            // seller.getAverageRating(),
            BigDecimal.ZERO, // totalSales
            0, // totalOrders
            seller.getRating().doubleValue(), // averageRating
            seller.getCreatedAt()
        );
    }

    /**
     * Seller Dashboard DTO
     */
    public static class SellerDashboardDTO {
        private Map<String, Object> stats;
        private Map<String, Object> salesByDate;
        private Map<String, Object> topProducts;

        public SellerDashboardDTO(Map<String, Object> stats, Map<String, Object> salesByDate, Map<String, Object> topProducts) {
            this.stats = stats;
            this.salesByDate = salesByDate;
            this.topProducts = topProducts;
        }

        public Map<String, Object> getStats() { return stats; }
        public void setStats(Map<String, Object> stats) { this.stats = stats; }

        public Map<String, Object> getSalesByDate() { return salesByDate; }
        public void setSalesByDate(Map<String, Object> salesByDate) { this.salesByDate = salesByDate; }

        public Map<String, Object> getTopProducts() { return topProducts; }
        public void setTopProducts(Map<String, Object> topProducts) { this.topProducts = topProducts; }
    }
}
