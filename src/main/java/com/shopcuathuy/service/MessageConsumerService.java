package com.shopcuathuy.service;
import com.shopcuathuy.dto.CreateAnalyticsEventDTO;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.shopcuathuy.config.RabbitMQConfig;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service for consuming messages from RabbitMQ
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
public class MessageConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumerService.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private InventoryService inventoryService;

    // Order Message Consumers
    /**
     * Consume order created messages
     */
    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void handleOrderMessage(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            logger.info("Received order message: {}", eventType);

            switch (eventType) {
                case "ORDER_CREATED":
                    handleOrderCreated(message);
                    break;
                case "ORDER_UPDATED":
                    handleOrderUpdated(message);
                    break;
                case "ORDER_CANCELLED":
                    handleOrderCancelled(message);
                    break;
                case "ORDER_SHIPPED":
                    handleOrderShipped(message);
                    break;
                case "ORDER_DELIVERED":
                    handleOrderDelivered(message);
                    break;
                default:
                    logger.warn("Unknown order event type: {}", eventType);
            }
        } catch (Exception e) {
            logger.error("Error processing order message: {}", e.getMessage(), e);
        }
    }

    private void handleOrderCreated(Map<String, Object> message) {
        String orderId = (String) message.get("orderId");
        String userId = (String) message.get("userId");
        String sellerId = (String) message.get("sellerId");
        
        // Send notification to user
        notificationService.sendOrderCreatedNotification(userId, orderId);
        
        // Send notification to seller
        notificationService.sendOrderCreatedNotification(sellerId, orderId);
        
        // Send email to user
        emailService.sendOrderConfirmationEmail(userId, orderId);
        
        // Track analytics
        analyticsService.trackOrderCreated(Long.valueOf(orderId), Long.valueOf(userId), sellerId);
        
        logger.info("Order created event processed for order: {}", orderId);
    }

    private void handleOrderUpdated(Map<String, Object> message) {
        String orderId = (String) message.get("orderId");
        String userId = (String) message.get("userId");
        String status = (String) message.get("status");
        
        // Send notification to user
        notificationService.sendOrderStatusUpdateNotification(userId, orderId, status);
        
        // Track analytics
        analyticsService.trackOrderStatusUpdate(Long.valueOf(orderId), status);
        
        logger.info("Order updated event processed for order: {}", orderId);
    }

    private void handleOrderCancelled(Map<String, Object> message) {
        String orderId = (String) message.get("orderId");
        String userId = (String) message.get("userId");
        String sellerId = (String) message.get("sellerId");
        
        // Send notification to user
        notificationService.sendOrderCancelledNotification(userId, orderId);
        
        // Send notification to seller
        notificationService.sendOrderCancelledNotification(sellerId, orderId);
        
        // Send email to user
        emailService.sendOrderCancelledEmail(userId, orderId);
        
        // Track analytics
        analyticsService.trackOrderCancelled(Long.valueOf(orderId), Long.valueOf(userId), sellerId);
        
        logger.info("Order cancelled event processed for order: {}", orderId);
    }

    private void handleOrderShipped(Map<String, Object> message) {
        String orderId = (String) message.get("orderId");
        String userId = (String) message.get("userId");
        String trackingNumber = (String) message.get("trackingNumber");
        
        // Send notification to user
        notificationService.sendOrderShippedNotification(userId, orderId, trackingNumber);
        
        // Send email to user
        emailService.sendOrderShippedEmail(userId, orderId, trackingNumber);
        
        // Track analytics
        analyticsService.trackOrderShipped(Long.valueOf(orderId), userId);
        
        logger.info("Order shipped event processed for order: {}", orderId);
    }

    private void handleOrderDelivered(Map<String, Object> message) {
        String orderId = (String) message.get("orderId");
        String userId = (String) message.get("userId");
        
        // Send notification to user
        notificationService.sendOrderDeliveredNotification(userId, orderId);
        
        // Send email to user
        emailService.sendOrderDeliveredEmail(userId, orderId);
        
        // Track analytics
        analyticsService.trackOrderDelivered(Long.valueOf(orderId), userId);
        
        logger.info("Order delivered event processed for order: {}", orderId);
    }

    // Product Message Consumers
    /**
     * Consume product messages
     */
    @RabbitListener(queues = RabbitMQConfig.PRODUCT_QUEUE)
    public void handleProductMessage(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            logger.info("Received product message: {}", eventType);

            switch (eventType) {
                case "PRODUCT_CREATED":
                    handleProductCreated(message);
                    break;
                case "PRODUCT_UPDATED":
                    handleProductUpdated(message);
                    break;
                case "PRODUCT_DELETED":
                    handleProductDeleted(message);
                    break;
                case "PRODUCT_STOCK_LOW":
                    handleProductStockLow(message);
                    break;
                default:
                    logger.warn("Unknown product event type: {}", eventType);
            }
        } catch (Exception e) {
            logger.error("Error processing product message: {}", e.getMessage(), e);
        }
    }

    private void handleProductCreated(Map<String, Object> message) {
        String productId = (String) message.get("productId");
        String sellerId = (String) message.get("sellerId");
        
        // Track analytics
        analyticsService.trackProductCreated(productId, Long.valueOf(sellerId));
        
        logger.info("Product created event processed for product: {}", productId);
    }

    private void handleProductUpdated(Map<String, Object> message) {
        String productId = (String) message.get("productId");
        String sellerId = (String) message.get("sellerId");
        
        // Track analytics
        analyticsService.trackProductUpdated(Long.valueOf(productId), sellerId);
        
        logger.info("Product updated event processed for product: {}", productId);
    }

    private void handleProductDeleted(Map<String, Object> message) {
        String productId = (String) message.get("productId");
        String sellerId = (String) message.get("sellerId");
        
        // Track analytics
        analyticsService.trackProductDeleted(Long.valueOf(productId), sellerId);
        
        logger.info("Product deleted event processed for product: {}", productId);
    }

    private void handleProductStockLow(Map<String, Object> message) {
        String productId = (String) message.get("productId");
        String sellerId = (String) message.get("sellerId");
        Integer stock = (Integer) message.get("stock");
        
        // Send notification to seller
        notificationService.sendLowStockNotification(sellerId, productId, stock);
        
        // Send email to seller
        emailService.sendLowStockEmail(sellerId, productId, stock);
        
        // Track analytics
        analyticsService.trackLowStock(Long.valueOf(productId), sellerId, stock);
        
        logger.info("Product stock low event processed for product: {}", productId);
    }

    // User Message Consumers
    /**
     * Consume user messages
     */
    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void handleUserMessage(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            logger.info("Received user message: {}", eventType);

            switch (eventType) {
                case "USER_REGISTERED":
                    handleUserRegistered(message);
                    break;
                case "USER_UPDATED":
                    handleUserUpdated(message);
                    break;
                case "USER_DEACTIVATED":
                    handleUserDeactivated(message);
                    break;
                default:
                    logger.warn("Unknown user event type: {}", eventType);
            }
        } catch (Exception e) {
            logger.error("Error processing user message: {}", e.getMessage(), e);
        }
    }

    private void handleUserRegistered(Map<String, Object> message) {
        String userId = (String) message.get("userId");
        String email = (String) message.get("email");
        
        // Send welcome email
        emailService.sendWelcomeEmail(email);
        
        // Track analytics
        analyticsService.trackUserRegistered(Long.valueOf(userId), email);
        
        logger.info("User registered event processed for user: {}", userId);
    }

    private void handleUserUpdated(Map<String, Object> message) {
        String userId = (String) message.get("userId");
        String email = (String) message.get("email");
        
        // Track analytics
        analyticsService.trackUserUpdated(Long.valueOf(userId), email);
        
        logger.info("User updated event processed for user: {}", userId);
    }

    private void handleUserDeactivated(Map<String, Object> message) {
        String userId = (String) message.get("userId");
        String email = (String) message.get("email");
        
        // Send deactivation email
        emailService.sendAccountDeactivatedEmail(email);
        
        // Track analytics
        analyticsService.trackUserDeactivated(Long.valueOf(userId), email);
        
        logger.info("User deactivated event processed for user: {}", userId);
    }

    // Notification Message Consumers
    /**
     * Consume notification messages
     */
    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleNotificationMessage(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            logger.info("Received notification message: {}", eventType);

            if ("NOTIFICATION_SEND".equals(eventType)) {
                String userId = (String) message.get("userId");
                String title = (String) message.get("title");
                String messageText = (String) message.get("message");
                String type = (String) message.get("type");
                
                notificationService.sendNotification(userId, title, messageText, type);
                
                logger.info("Notification sent to user: {}", userId);
            }
        } catch (Exception e) {
            logger.error("Error processing notification message: {}", e.getMessage(), e);
        }
    }

    // Email Message Consumers
    /**
     * Consume email messages
     */
    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void handleEmailMessage(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            logger.info("Received email message: {}", eventType);

            if ("EMAIL_SEND".equals(eventType)) {
                String to = (String) message.get("to");
                String subject = (String) message.get("subject");
                String body = (String) message.get("body");
                String template = (String) message.get("template");
                
                emailService.sendEmail(to, subject, body, template);
                
                logger.info("Email sent to: {}", to);
            }
        } catch (Exception e) {
            logger.error("Error processing email message: {}", e.getMessage(), e);
        }
    }

    // SMS Message Consumers
    /**
     * Consume SMS messages
     */
    @RabbitListener(queues = RabbitMQConfig.SMS_QUEUE)
    public void handleSMSMessage(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            logger.info("Received SMS message: {}", eventType);

            if ("SMS_SEND".equals(eventType)) {
                String phoneNumber = (String) message.get("phoneNumber");
                String messageText = (String) message.get("message");
                
                smsService.sendSMS(phoneNumber, messageText);
                
                logger.info("SMS sent to: {}", phoneNumber);
            }
        } catch (Exception e) {
            logger.error("Error processing SMS message: {}", e.getMessage(), e);
        }
    }

    // Analytics Message Consumers
    /**
     * Consume analytics messages
     */
    @RabbitListener(queues = RabbitMQConfig.ANALYTICS_QUEUE)
    public void handleAnalyticsMessage(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            logger.info("Received analytics message: {}", eventType);

            if ("ANALYTICS_TRACK".equals(eventType)) {
                String event = (String) message.get("event");
                String userId = (String) message.get("userId");
                @SuppressWarnings("unchecked")
                Map<String, Object> data = (Map<String, Object>) message.get("data");
                
                // Create CreateAnalyticsEventDTO from the message data
                CreateAnalyticsEventDTO createEventDTO = CreateAnalyticsEventDTO.builder()
                        .eventName(event)
                        .eventType(event)
                        .userId(Long.valueOf(userId))
                        .eventData(data.toString())
                        .build();
                
                analyticsService.trackEvent(createEventDTO);
                
                logger.info("Analytics event tracked: {}", event);
            }
        } catch (Exception e) {
            logger.error("Error processing analytics message: {}", e.getMessage(), e);
        }
    }

    // Inventory Message Consumers
    /**
     * Consume inventory messages
     */
    @RabbitListener(queues = RabbitMQConfig.INVENTORY_QUEUE)
    public void handleInventoryMessage(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            logger.info("Received inventory message: {}", eventType);

            if ("INVENTORY_UPDATE".equals(eventType)) {
                String productId = (String) message.get("productId");
                Integer oldStock = (Integer) message.get("oldStock");
                Integer newStock = (Integer) message.get("newStock");
                
                inventoryService.updateInventory(productId, oldStock, newStock);
                
                logger.info("Inventory updated for product: {}", productId);
            }
        } catch (Exception e) {
            logger.error("Error processing inventory message: {}", e.getMessage(), e);
        }
    }

    // Payment Message Consumers
    /**
     * Consume payment messages
     */
    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void handlePaymentMessage(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            logger.info("Received payment message: {}", eventType);

            if ("PAYMENT_PROCESSED".equals(eventType)) {
                String orderId = (String) message.get("orderId");
                String paymentMethod = (String) message.get("paymentMethod");
                String status = (String) message.get("status");
                Double amount = (Double) message.get("amount");
                
                // Process payment
                logger.info("Payment processed for order: {} with method: {} and status: {}", 
                    orderId, paymentMethod, status);
            }
        } catch (Exception e) {
            logger.error("Error processing payment message: {}", e.getMessage(), e);
        }
    }

    // Shipping Message Consumers
    /**
     * Consume shipping messages
     */
    @RabbitListener(queues = RabbitMQConfig.SHIPPING_QUEUE)
    public void handleShippingMessage(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            logger.info("Received shipping message: {}", eventType);

            if ("SHIPPING_UPDATE".equals(eventType)) {
                String orderId = (String) message.get("orderId");
                String trackingNumber = (String) message.get("trackingNumber");
                String status = (String) message.get("status");
                
                // Process shipping update
                logger.info("Shipping updated for order: {} with tracking: {} and status: {}", 
                    orderId, trackingNumber, status);
            }
        } catch (Exception e) {
            logger.error("Error processing shipping message: {}", e.getMessage(), e);
        }
    }
}
