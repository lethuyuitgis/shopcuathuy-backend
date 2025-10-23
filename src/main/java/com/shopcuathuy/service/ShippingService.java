package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.shopcuathuy.dto.CreateShippingDTO;
import com.shopcuathuy.dto.ShippingDTO;
import com.shopcuathuy.entity.*;
import com.shopcuathuy.mapper.ShippingMapper;
import com.shopcuathuy.repository.OrderRepository;
import com.shopcuathuy.repository.ShippingMethodRepository;
import com.shopcuathuy.repository.ShippingRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Shipping Service with RabbitMQ + MinIO integration
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
public class ShippingService {

    private final ShippingRepository shippingRepository;
    private final OrderRepository orderRepository;
    private final ShippingMethodRepository shippingMethodRepository;
    private final ShippingMapper shippingMapper;
    private final MessageProducerService messageProducerService;
    private final FileStorageService fileStorageService;

    public ShippingService(ShippingRepository shippingRepository, OrderRepository orderRepository,
                          ShippingMethodRepository shippingMethodRepository, ShippingMapper shippingMapper,
                          MessageProducerService messageProducerService, FileStorageService fileStorageService) {
        this.shippingRepository = shippingRepository;
        this.orderRepository = orderRepository;
        this.shippingMethodRepository = shippingMethodRepository;
        this.shippingMapper = shippingMapper;
        this.messageProducerService = messageProducerService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * Create shipping
     */
    @Transactional
    public ShippingDTO createShipping(CreateShippingDTO createShippingDTO) {
        // Validate order
        Order order = orderRepository.findById(String.valueOf(createShippingDTO.getOrderId()))
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != Order.OrderStatus.CONFIRMED) {
            throw new RuntimeException("Order must be confirmed before shipping");
        }

        // Validate shipping method
        ShippingMethod shippingMethod = shippingMethodRepository.findById(createShippingDTO.getShippingMethodId())
                .orElseThrow(() -> new RuntimeException("Shipping method not found"));

        if (shippingMethod.getStatus() != ShippingMethod.ShippingStatus.ACTIVE) {
            throw new RuntimeException("Shipping method is not active");
        }

        // Check if shipping already exists for this order
        if (shippingRepository.findByOrderId(createShippingDTO.getOrderId()).isPresent()) {
            throw new RuntimeException("Shipping already exists for this order");
        }

        // Create shipping
        Shipping shipping = new Shipping();
        shipping.setOrder(order);
        shipping.setShippingMethod(shippingMethod);
        shipping.setTrackingNumber(createShippingDTO.getTrackingNumber());
        shipping.setCarrier(createShippingDTO.getCarrier());
        shipping.setStatus(Shipping.ShippingStatus.PENDING);
        shipping.setShippingCost(createShippingDTO.getShippingCost());
        shipping.setEstimatedDeliveryDate(createShippingDTO.getEstimatedDeliveryDate());
        shipping.setShippingAddress(createShippingDTO.getShippingAddress());
        shipping.setRecipientName(createShippingDTO.getRecipientName());
        shipping.setRecipientPhone(createShippingDTO.getRecipientPhone());
        shipping.setNotes(createShippingDTO.getNotes());
        shipping.setTrackingUrl(createShippingDTO.getTrackingUrl());

        shipping = shippingRepository.save(shipping);

        // Update order status
        order.setStatus(Order.OrderStatus.SHIPPED);
        orderRepository.save(order);

        // Send shipping created message to RabbitMQ
        messageProducerService.sendShippingCreated(shipping);

        // Store shipping data to MinIO
        storeShippingToMinIO(shipping);

        return shippingMapper.toDTO(shipping);
    }

    /**
     * Update shipping status
     */
    @Transactional
    public ShippingDTO updateShippingStatus(Long shippingId, Shipping.ShippingStatus status, String notes) {
        Shipping shipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Shipping not found"));

        Shipping.ShippingStatus oldStatus = shipping.getStatus();
        shipping.setStatus(status);
        shipping.setNotes(notes);

        if (status == Shipping.ShippingStatus.DELIVERED) {
            shipping.setActualDeliveryDate(LocalDateTime.now());
        }

        shipping = shippingRepository.save(shipping);

        // Update order status based on shipping status
        updateOrderStatusBasedOnShipping(shipping);

        // Send shipping status update message to RabbitMQ
        messageProducerService.sendShippingStatusUpdate(shipping, oldStatus, status);

        // Store shipping update to MinIO
        storeShippingUpdateToMinIO(shipping, oldStatus, status);

        return shippingMapper.toDTO(shipping);
    }

    /**
     * Get shipping by ID
     */
    public ShippingDTO getShippingById(Long shippingId) {
        Shipping shipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Shipping not found"));
        return shippingMapper.toDTO(shipping);
    }

    /**
     * Get shipping by order ID
     */
    public ShippingDTO getShippingByOrderId(Long orderId) {
        Shipping shipping = shippingRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Shipping not found for this order"));
        return shippingMapper.toDTO(shipping);
    }

    /**
     * Get shipping by tracking number
     */
    public ShippingDTO getShippingByTrackingNumber(String trackingNumber) {
        Shipping shipping = shippingRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RuntimeException("Shipping not found with tracking number: " + trackingNumber));
        return shippingMapper.toDTO(shipping);
    }

    /**
     * Get shipping by status
     */
    public List<ShippingDTO> getShippingByStatus(Shipping.ShippingStatus status) {
        List<Shipping> shippings = shippingRepository.findByStatus(status);
        return shippingMapper.toDTOList(shippings);
    }

    /**
     * Get shipping by status with pagination
     */
    public Page<ShippingDTO> getShippingByStatus(Shipping.ShippingStatus status, Pageable pageable) {
        Page<Shipping> shippings = shippingRepository.findByStatus(status, pageable);
        return shippings.map(shippingMapper::toDTO);
    }

    /**
     * Get shipping by carrier
     */
    public List<ShippingDTO> getShippingByCarrier(String carrier) {
        List<Shipping> shippings = shippingRepository.findByCarrier(carrier);
        return shippingMapper.toDTOList(shippings);
    }

    /**
     * Get shipping by date range
     */
    public List<ShippingDTO> getShippingByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Shipping> shippings = shippingRepository.findByCreatedAtBetween(startDate, endDate);
        return shippingMapper.toDTOList(shippings);
    }

    /**
     * Get overdue shipping
     */
    public List<ShippingDTO> getOverdueShipping() {
        List<Shipping> shippings = shippingRepository.findOverdueShipping(LocalDateTime.now());
        return shippingMapper.toDTOList(shippings);
    }

    /**
     * Search shipping
     */
    public List<ShippingDTO> searchShipping(String searchTerm) {
        List<Shipping> shippings = shippingRepository.findByTrackingNumberContaining(searchTerm);
        return shippingMapper.toDTOList(shippings);
    }

    /**
     * Get shipping dashboard data
     */
    public ShippingDashboardDTO getShippingDashboard(LocalDateTime startDate, LocalDateTime endDate) {
        // Get shipping statistics by status
        List<Object[]> statusStats = shippingRepository.getShippingStatisticsByStatus();
        Map<String, Long> statusCounts = new HashMap<>();
        for (Object[] stat : statusStats) {
            statusCounts.put(stat[0].toString(), (Long) stat[1]);
        }

        // Get shipping statistics by carrier
        List<Object[]> carrierStats = shippingRepository.getShippingStatisticsByCarrier();
        Map<String, Long> carrierCounts = new HashMap<>();
        for (Object[] stat : carrierStats) {
            carrierCounts.put(stat[0].toString(), (Long) stat[1]);
        }

        // Get daily shipping counts
        List<Object[]> dailyCounts = shippingRepository.getDailyShippingCounts(startDate, endDate);
        Map<String, Long> dailyShippingCounts = new HashMap<>();
        for (Object[] count : dailyCounts) {
            dailyShippingCounts.put(count[0].toString(), (Long) count[1]);
        }

        // Get overdue shipping count
        long overdueCount = shippingRepository.findOverdueShipping(LocalDateTime.now()).size();

        return new ShippingDashboardDTO(
            statusCounts,
            carrierCounts,
            dailyShippingCounts,
            overdueCount
        );
    }

    /**
     * Export shipping data
     */
    public String exportShippingData(LocalDateTime startDate, LocalDateTime endDate, String format) {
        List<Shipping> shippings = shippingRepository.findByCreatedAtBetween(startDate, endDate);
        
        // Generate export data
        String exportData = generateShippingExportData(shippings, format);
        
        // Store to MinIO
        String fileName = "shipping-export-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadShippingExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Update order status based on shipping status
     */
    private void updateOrderStatusBasedOnShipping(Shipping shipping) {
        Order order = shipping.getOrder();
        
        switch (shipping.getStatus()) {
            case PICKED_UP:
            case IN_TRANSIT:
            case OUT_FOR_DELIVERY:
                order.setStatus(Order.OrderStatus.SHIPPED);
                break;
            case DELIVERED:
                order.setStatus(Order.OrderStatus.DELIVERED);
                break;
            case FAILED_DELIVERY:
            case RETURNED:
                order.setStatus(Order.OrderStatus.REFUNDED);
                break;
            case CANCELLED:
                order.setStatus(Order.OrderStatus.CANCELLED);
                break;
        }
        
        orderRepository.save(order);
    }

    /**
     * Store shipping data to MinIO
     */
    private void storeShippingToMinIO(Shipping shipping) {
        try {
            // Create shipping data JSON
            String shippingData = createShippingDataJson(shipping);
            
            // Store to MinIO
            String fileName = "shipping/" + shipping.getStatus() + "/" + 
                            shipping.getCreatedAt().toLocalDate() + "/" + 
                            shipping.getId() + ".json";
            
            fileStorageService.uploadShippingData(fileName, shippingData);
        } catch (Exception e) {
            // Log error but don't fail the main operation
            System.err.println("Failed to store shipping data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Store shipping update to MinIO
     */
    private void storeShippingUpdateToMinIO(Shipping shipping, Shipping.ShippingStatus oldStatus, Shipping.ShippingStatus newStatus) {
        try {
            // Create shipping update data JSON
            String updateData = createShippingUpdateDataJson(shipping, oldStatus, newStatus);
            
            // Store to MinIO
            String fileName = "shipping/updates/" + 
                            shipping.getUpdatedAt().toLocalDate() + "/" + 
                            shipping.getId() + "-" + System.currentTimeMillis() + ".json";
            
            fileStorageService.uploadShippingData(fileName, updateData);
        } catch (Exception e) {
            // Log error but don't fail the main operation
            System.err.println("Failed to store shipping update data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create shipping data JSON
     */
    private String createShippingDataJson(Shipping shipping) {
        return String.format(
            "{\"id\":%d,\"orderId\":%d,\"trackingNumber\":\"%s\",\"carrier\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\"}",
            shipping.getId(),
            shipping.getOrder().getId(),
            shipping.getTrackingNumber(),
            shipping.getCarrier(),
            shipping.getStatus(),
            shipping.getCreatedAt()
        );
    }

    /**
     * Create shipping update data JSON
     */
    private String createShippingUpdateDataJson(Shipping shipping, Shipping.ShippingStatus oldStatus, Shipping.ShippingStatus newStatus) {
        return String.format(
            "{\"id\":%d,\"orderId\":%d,\"trackingNumber\":\"%s\",\"oldStatus\":\"%s\",\"newStatus\":\"%s\",\"updatedAt\":\"%s\"}",
            shipping.getId(),
            shipping.getOrder().getId(),
            shipping.getTrackingNumber(),
            oldStatus,
            newStatus,
            shipping.getUpdatedAt()
        );
    }

    /**
     * Generate shipping export data
     */
    private String generateShippingExportData(List<Shipping> shippings, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateShippingJsonExport(shippings);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateShippingCsvExport(shippings);
        }
        return generateShippingJsonExport(shippings);
    }

    /**
     * Generate JSON export
     */
    private String generateShippingJsonExport(List<Shipping> shippings) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < shippings.size(); i++) {
            Shipping shipping = shippings.get(i);
            json.append(createShippingDataJson(shipping));
            if (i < shippings.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateShippingCsvExport(List<Shipping> shippings) {
        StringBuilder csv = new StringBuilder("id,orderId,trackingNumber,carrier,status,createdAt\n");
        for (Shipping shipping : shippings) {
            csv.append(String.format("%d,%d,%s,%s,%s,%s\n",
                shipping.getId(),
                shipping.getOrder().getId(),
                shipping.getTrackingNumber(),
                shipping.getCarrier(),
                shipping.getStatus(),
                shipping.getCreatedAt()
            ));
        }
        return csv.toString();
    }

    /**
     * Shipping Dashboard DTO
     */
    public static class ShippingDashboardDTO {
        private Map<String, Long> statusCounts;
        private Map<String, Long> carrierCounts;
        private Map<String, Long> dailyShippingCounts;
        private long overdueCount;

        public ShippingDashboardDTO(Map<String, Long> statusCounts, Map<String, Long> carrierCounts,
                                 Map<String, Long> dailyShippingCounts, long overdueCount) {
            this.statusCounts = statusCounts;
            this.carrierCounts = carrierCounts;
            this.dailyShippingCounts = dailyShippingCounts;
            this.overdueCount = overdueCount;
        }

        // Getters and Setters
        public Map<String, Long> getStatusCounts() { return statusCounts; }
        public void setStatusCounts(Map<String, Long> statusCounts) { this.statusCounts = statusCounts; }

        public Map<String, Long> getCarrierCounts() { return carrierCounts; }
        public void setCarrierCounts(Map<String, Long> carrierCounts) { this.carrierCounts = carrierCounts; }

        public Map<String, Long> getDailyShippingCounts() { return dailyShippingCounts; }
        public void setDailyShippingCounts(Map<String, Long> dailyShippingCounts) { this.dailyShippingCounts = dailyShippingCounts; }

        public long getOverdueCount() { return overdueCount; }
        public void setOverdueCount(long overdueCount) { this.overdueCount = overdueCount; }
    }
}
