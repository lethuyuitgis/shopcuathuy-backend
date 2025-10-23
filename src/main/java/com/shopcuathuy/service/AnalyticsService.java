package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.shopcuathuy.dto.AnalyticsEventDTO;
import com.shopcuathuy.dto.CreateAnalyticsEventDTO;
import com.shopcuathuy.entity.*;
import com.shopcuathuy.mapper.AnalyticsEventMapper;
import com.shopcuathuy.repository.AnalyticsEventRepository;
import com.shopcuathuy.repository.OrderRepository;
import com.shopcuathuy.repository.ProductRepository;
import com.shopcuathuy.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


/**
 * Analytics Service with RabbitMQ + MinIO integration
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final AnalyticsEventRepository analyticsEventRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final AnalyticsEventMapper analyticsEventMapper;
    private final MessageProducerService messageProducerService;
    private final FileStorageService fileStorageService;



    /**
     * Track analytics event
     */
    @Transactional
    public AnalyticsEventDTO trackEvent(CreateAnalyticsEventDTO createEventDTO) {
        // Validate entities if provided
        User user = null;
        if (createEventDTO.getUserId() != null) {
            user = userRepository.findById(String.valueOf(createEventDTO.getUserId()))
                    .orElse(null);
        }

        Product product = null;
        if (createEventDTO.getProductId() != null) {
            product = productRepository.findById(String.valueOf(createEventDTO.getProductId()))
                    .orElse(null);
        }

        Order order = null;
        if (createEventDTO.getOrderId() != null) {
            order = orderRepository.findById(String.valueOf(createEventDTO.getOrderId()))
                    .orElse(null);
        }

        // Create analytics event
        AnalyticsEvent event = new AnalyticsEvent();
        event.setEventName(createEventDTO.getEventName());
        event.setEventType(createEventDTO.getEventType());
        event.setUser(user);
        event.setProduct(product);
        event.setOrder(order);
        event.setEventData(createEventDTO.getEventData());
        event.setSessionId(createEventDTO.getSessionId());
        event.setIpAddress(createEventDTO.getIpAddress());
        event.setUserAgent(createEventDTO.getUserAgent());
        event.setReferrer(createEventDTO.getReferrer());
        event.setValue(createEventDTO.getValue());
        event.setProperties(String.valueOf(createEventDTO.getProperties()));

        event = analyticsEventRepository.save(event);

        // Send analytics event to RabbitMQ
        messageProducerService.sendAnalyticsEvent(event);

        // Store analytics data to MinIO for long-term storage
        storeAnalyticsToMinIO(event);

        return analyticsEventMapper.toDTO(event);
    }

    /**
     * Get analytics events by user
     */
    public List<AnalyticsEventDTO> getUserEvents(String userId) {
        List<AnalyticsEvent> events = analyticsEventRepository.findByUser_IdOrderByCreatedAtDesc(userId);
        return analyticsEventMapper.toDTOList(events);
    }

    /**
     * Get analytics events by user with pagination
     */
    public Page<AnalyticsEventDTO> getUserEvents(String userId, Pageable pageable) {
        Page<AnalyticsEvent> events = analyticsEventRepository.findByUser_IdOrderByCreatedAtDesc(userId, pageable);
        return events.map(analyticsEventMapper::toDTO);
    }

    /**
     * Get analytics events by event type
     */
    public List<AnalyticsEventDTO> getEventsByType(String eventType) {
        List<AnalyticsEvent> events = analyticsEventRepository.findByEventTypeOrderByCreatedAtDesc(eventType);
        return analyticsEventMapper.toDTOList(events);
    }

    /**
     * Get analytics events by product
     */
    public List<AnalyticsEventDTO> getProductEvents(String productId) {
        List<AnalyticsEvent> events = analyticsEventRepository.findByProduct_IdOrderByCreatedAtDesc(productId);
        return analyticsEventMapper.toDTOList(events);
    }

    /**
     * Get analytics events by date range
     */
    public List<AnalyticsEventDTO> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<AnalyticsEvent> events = analyticsEventRepository.findByCreatedAtBetween(startDate, endDate);
        return analyticsEventMapper.toDTOList(events);
    }

    /**
     * Get analytics events by date range with pagination
     */
    public Page<AnalyticsEventDTO> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<AnalyticsEvent> events = analyticsEventRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        return events.map(analyticsEventMapper::toDTO);
    }

    /**
     * Get analytics dashboard data
     */
    public AnalyticsDashboardDTO getDashboardData(LocalDateTime startDate, LocalDateTime endDate) {
        // Get event statistics
        List<Object[]> eventStats = analyticsEventRepository.getEventStatisticsByType();
        Map<String, Long> eventCounts = new HashMap<>();
        for (Object[] stat : eventStats) {
            eventCounts.put((String) stat[0], (Long) stat[1]);
        }

        // Get daily event counts
        List<Object[]> dailyCounts = analyticsEventRepository.getDailyEventCounts(startDate, endDate);
        Map<String, Long> dailyEventCounts = new HashMap<>();
        for (Object[] count : dailyCounts) {
            dailyEventCounts.put(count[0].toString(), (Long) count[1]);
        }

        // Get hourly event counts
        List<Object[]> hourlyCounts = analyticsEventRepository.getHourlyEventCounts(startDate, endDate);
        Map<Integer, Long> hourlyEventCounts = new HashMap<>();
        for (Object[] count : hourlyCounts) {
            hourlyEventCounts.put((Integer) count[0], (Long) count[1]);
        }

        // Get most popular products
        List<Object[]> popularProducts = analyticsEventRepository.findMostPopularProducts(Pageable.ofSize(10));
        Map<Long, Long> productViews = new HashMap<>();
        for (Object[] product : popularProducts) {
            productViews.put((Long) product[0], (Long) product[1]);
        }

        // Get most active users
        List<Object[]> activeUsers = analyticsEventRepository.findMostActiveUsers(Pageable.ofSize(10));
        Map<Long, Long> userActivity = new HashMap<>();
        for (Object[] user : activeUsers) {
            userActivity.put((Long) user[0], (Long) user[1]);
        }

        return new AnalyticsDashboardDTO(
            eventCounts,
            dailyEventCounts,
            hourlyEventCounts,
            productViews,
            userActivity
        );
    }

    /**
     * Export analytics data to MinIO
     */
    public String exportAnalyticsData(LocalDateTime startDate, LocalDateTime endDate, String format) {
        List<AnalyticsEvent> events = analyticsEventRepository.findByCreatedAtBetween(startDate, endDate);
        
        // Generate export data
        String exportData = generateExportData(events, format);
        
        // Store to MinIO
        String fileName = "analytics-export-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadAnalyticsExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Store analytics data to MinIO
     */
    private void storeAnalyticsToMinIO(AnalyticsEvent event) {
        try {
            // Create analytics data JSON
            String analyticsData = createAnalyticsDataJson(event);
            
            // Store to MinIO
            String fileName = "analytics/" + event.getEventType() + "/" + 
                            event.getCreatedAt().toLocalDate() + "/" + 
                            event.getId() + ".json";
            
            fileStorageService.uploadAnalyticsData(fileName, analyticsData);
        } catch (Exception e) {
            // Log error but don't fail the main operation
            System.err.println("Failed to store analytics data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create analytics data JSON
     */
    private String createAnalyticsDataJson(AnalyticsEvent event) {
        // Simple JSON creation - in production, use Jackson or Gson
        return String.format(
            "{\"id\":%d,\"eventName\":\"%s\",\"eventType\":\"%s\",\"userId\":%s,\"productId\":%s,\"createdAt\":\"%s\"}",
            event.getId(),
            event.getEventName(),
            event.getEventType(),
            event.getUser() != null ? event.getUser().getId() : null,
            event.getProduct() != null ? event.getProduct().getId() : null,
            event.getCreatedAt()
        );
    }

    /**
     * Generate export data
     */
    private String generateExportData(List<AnalyticsEvent> events, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateJsonExport(events);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateCsvExport(events);
        }
        return generateJsonExport(events);
    }

    /**
     * Generate JSON export
     */
    private String generateJsonExport(List<AnalyticsEvent> events) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < events.size(); i++) {
            AnalyticsEvent event = events.get(i);
            json.append(createAnalyticsDataJson(event));
            if (i < events.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateCsvExport(List<AnalyticsEvent> events) {
        StringBuilder csv = new StringBuilder("id,eventName,eventType,userId,productId,createdAt\n");
        for (AnalyticsEvent event : events) {
            csv.append(String.format("%d,%s,%s,%s,%s,%s\n",
                event.getId(),
                event.getEventName(),
                event.getEventType(),
                event.getUser() != null ? event.getUser().getId() : "",
                event.getProduct() != null ? event.getProduct().getId() : "",
                event.getCreatedAt()
            ));
        }
        return csv.toString();
    }

    /**
     * Analytics Dashboard DTO
     */
    public static class AnalyticsDashboardDTO {
        private Map<String, Long> eventCounts;
        private Map<String, Long> dailyEventCounts;
        private Map<Integer, Long> hourlyEventCounts;
        private Map<Long, Long> productViews;
        private Map<Long, Long> userActivity;

        public AnalyticsDashboardDTO(Map<String, Long> eventCounts, Map<String, Long> dailyEventCounts,
                                   Map<Integer, Long> hourlyEventCounts, Map<Long, Long> productViews,
                                   Map<Long, Long> userActivity) {
            this.eventCounts = eventCounts;
            this.dailyEventCounts = dailyEventCounts;
            this.hourlyEventCounts = hourlyEventCounts;
            this.productViews = productViews;
            this.userActivity = userActivity;
        }

        // Getters and Setters
        public Map<String, Long> getEventCounts() { return eventCounts; }
        public void setEventCounts(Map<String, Long> eventCounts) { this.eventCounts = eventCounts; }

        public Map<String, Long> getDailyEventCounts() { return dailyEventCounts; }
        public void setDailyEventCounts(Map<String, Long> dailyEventCounts) { this.dailyEventCounts = dailyEventCounts; }

        public Map<Integer, Long> getHourlyEventCounts() { return hourlyEventCounts; }
        public void setHourlyEventCounts(Map<Integer, Long> hourlyEventCounts) { this.hourlyEventCounts = hourlyEventCounts; }

        public Map<Long, Long> getProductViews() { return productViews; }
        public void setProductViews(Map<Long, Long> productViews) { this.productViews = productViews; }

        public Map<Long, Long> getUserActivity() { return userActivity; }
        public void setUserActivity(Map<Long, Long> userActivity) { this.userActivity = userActivity; }
    }

    /**
     * Track order created event
     */
    public void trackOrderCreated(Long userId, Long orderId, String status) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .userId(userId)
                .orderId(orderId)
                .eventType("ORDER_CREATED")
                .eventData("status=" + status)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track order status update event
     */
    public void trackOrderStatusUpdate(Long orderId, String status) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .orderId(orderId)
                .eventType("ORDER_STATUS_UPDATE")
                .eventData("status=" + status)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track order cancelled event
     */
    public void trackOrderCancelled(Long userId, Long orderId, String reason) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .userId(userId)
                .orderId(orderId)
                .eventType("ORDER_CANCELLED")
                .eventData("reason=" + reason)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track order shipped event
     */
    public void trackOrderShipped(Long orderId, String trackingNumber) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .orderId(orderId)
                .eventType("ORDER_SHIPPED")
                .eventData("trackingNumber=" + trackingNumber)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track order delivered event
     */
    public void trackOrderDelivered(Long orderId, String deliveryDate) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .orderId(orderId)
                .eventType("ORDER_DELIVERED")
                .eventData("deliveryDate=" + deliveryDate)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track product created event
     */
    public void trackProductCreated(String sellerId, Long productId) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .productId(productId)
                .eventType("PRODUCT_CREATED")
                .eventData("sellerId=" + sellerId)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track product updated event
     */
    public void trackProductUpdated(Long productId, String changes) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .productId(productId)
                .eventType("PRODUCT_UPDATED")
                .eventData("changes=" + changes)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track product deleted event
     */
    public void trackProductDeleted(Long productId, String reason) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .productId(productId)
                .eventType("PRODUCT_DELETED")
                .eventData("reason=" + reason)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track low stock event
     */
    public void trackLowStock(Long productId, String sellerId, Integer stockLevel) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .productId(productId)
                .eventType("LOW_STOCK")
                .eventData("sellerId=" + sellerId + ",stockLevel=" + stockLevel)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track user registered event
     */
    public void trackUserRegistered(Long userId, String email) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .userId(userId)
                .eventType("USER_REGISTERED")
                .eventData("email=" + email)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track user updated event
     */
    public void trackUserUpdated(Long userId, String changes) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .userId(userId)
                .eventType("USER_UPDATED")
                .eventData("changes=" + changes)
                .build();
        trackEvent(eventDTO);
    }

    /**
     * Track user deactivated event
     */
    public void trackUserDeactivated(Long userId, String reason) {
        CreateAnalyticsEventDTO eventDTO = CreateAnalyticsEventDTO.builder()
                .userId(userId)
                .eventType("USER_DEACTIVATED")
                .eventData("reason=" + reason)
                .build();
        trackEvent(eventDTO);
    }
}