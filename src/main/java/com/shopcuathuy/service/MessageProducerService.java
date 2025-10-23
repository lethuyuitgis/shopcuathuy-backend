package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.shopcuathuy.config.RabbitMQConfig;
import com.shopcuathuy.dto.OrderDTO;
import com.shopcuathuy.dto.ProductDTO;
import com.shopcuathuy.dto.UserDTO;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service for producing messages to RabbitMQ
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
public class MessageProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Order Messages
    /**
     * Send order created message
     */
    public void sendOrderCreatedMessage(OrderDTO order) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "ORDER_CREATED");
        message.put("orderId", order.getId());
        message.put("orderNumber", order.getOrderNumber());
        message.put("userId", order.getUserId());
        message.put("sellerId", order.getSellerId());
        message.put("totalAmount", order.getTotalAmount());
        message.put("status", order.getStatus());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ORDER_EXCHANGE,
            RabbitMQConfig.ORDER_CREATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send order updated message
     */
    public void sendOrderUpdatedMessage(OrderDTO order) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "ORDER_UPDATED");
        message.put("orderId", order.getId());
        message.put("orderNumber", order.getOrderNumber());
        message.put("userId", order.getUserId());
        message.put("sellerId", order.getSellerId());
        message.put("status", order.getStatus());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ORDER_EXCHANGE,
            RabbitMQConfig.ORDER_UPDATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send order cancelled message
     */
    public void sendOrderCancelledMessage(OrderDTO order) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "ORDER_CANCELLED");
        message.put("orderId", order.getId());
        message.put("orderNumber", order.getOrderNumber());
        message.put("userId", order.getUserId());
        message.put("sellerId", order.getSellerId());
        message.put("cancellationReason", order.getCancellationReason());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ORDER_EXCHANGE,
            RabbitMQConfig.ORDER_CANCELLED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send order shipped message
     */
    public void sendOrderShippedMessage(OrderDTO order) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "ORDER_SHIPPED");
        message.put("orderId", order.getId());
        message.put("orderNumber", order.getOrderNumber());
        message.put("userId", order.getUserId());
        message.put("sellerId", order.getSellerId());
        message.put("trackingNumber", order.getTrackingNumber());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ORDER_EXCHANGE,
            RabbitMQConfig.ORDER_SHIPPED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send order delivered message
     */
    public void sendOrderDeliveredMessage(OrderDTO order) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "ORDER_DELIVERED");
        message.put("orderId", order.getId());
        message.put("orderNumber", order.getOrderNumber());
        message.put("userId", order.getUserId());
        message.put("sellerId", order.getSellerId());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ORDER_EXCHANGE,
            RabbitMQConfig.ORDER_DELIVERED_ROUTING_KEY,
            message
        );
    }

    // Product Messages
    /**
     * Send product created message
     */
    public void sendProductCreatedMessage(ProductDTO product) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "PRODUCT_CREATED");
        message.put("productId", product.getId());
        message.put("name", product.getName());
        message.put("sellerId", product.getSellerId());
        message.put("categoryId", product.getCategoryId());
        message.put("price", product.getPrice());
        message.put("stock", product.getStockQuantity());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PRODUCT_EXCHANGE,
            RabbitMQConfig.PRODUCT_CREATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send product updated message
     */
    public void sendProductUpdatedMessage(ProductDTO product) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "PRODUCT_UPDATED");
        message.put("productId", product.getId());
        message.put("name", product.getName());
        message.put("sellerId", product.getSellerId());
        message.put("price", product.getPrice());
        message.put("stock", product.getStockQuantity());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PRODUCT_EXCHANGE,
            RabbitMQConfig.PRODUCT_UPDATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send product deleted message
     */
    public void sendProductDeletedMessage(String productId, String sellerId) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "PRODUCT_DELETED");
        message.put("productId", productId);
        message.put("sellerId", sellerId);
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PRODUCT_EXCHANGE,
            RabbitMQConfig.PRODUCT_DELETED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send product stock low message
     */
    public void sendProductStockLowMessage(ProductDTO product) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "PRODUCT_STOCK_LOW");
        message.put("productId", product.getId());
        message.put("name", product.getName());
        message.put("sellerId", product.getSellerId());
        message.put("stock", product.getStockQuantity());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PRODUCT_EXCHANGE,
            RabbitMQConfig.PRODUCT_STOCK_LOW_ROUTING_KEY,
            message
        );
    }

    // User Messages
    /**
     * Send user registered message
     */
    public void sendUserRegisteredMessage(UserDTO user) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "USER_REGISTERED");
        message.put("userId", user.getId());
        message.put("email", user.getEmail());
        message.put("role", user.getRole());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.USER_EXCHANGE,
            RabbitMQConfig.USER_REGISTERED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send user updated message
     */
    public void sendUserUpdatedMessage(UserDTO user) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "USER_UPDATED");
        message.put("userId", user.getId());
        message.put("email", user.getEmail());
        message.put("role", user.getRole());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.USER_EXCHANGE,
            RabbitMQConfig.USER_UPDATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send user deactivated message
     */
    public void sendUserDeactivatedMessage(String userId, String email) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "USER_DEACTIVATED");
        message.put("userId", userId);
        message.put("email", email);
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.USER_EXCHANGE,
            RabbitMQConfig.USER_DEACTIVATED_ROUTING_KEY,
            message
        );
    }

    // Notification Messages
    /**
     * Send notification message
     */
    public void sendNotificationMessage(String userId, String title, String message, String type) {
        Map<String, Object> notificationMessage = new HashMap<>();
        notificationMessage.put("eventType", "NOTIFICATION_SEND");
        notificationMessage.put("userId", userId);
        notificationMessage.put("title", title);
        notificationMessage.put("message", message);
        notificationMessage.put("type", type);
        notificationMessage.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.NOTIFICATION_EXCHANGE,
            RabbitMQConfig.NOTIFICATION_SEND_ROUTING_KEY,
            notificationMessage
        );
    }

    // Email Messages
    /**
     * Send email message
     */
    public void sendEmailMessage(String to, String subject, String body, String template) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "EMAIL_SEND");
        message.put("to", to);
        message.put("subject", subject);
        message.put("body", body);
        message.put("template", template);
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EMAIL_EXCHANGE,
            RabbitMQConfig.EMAIL_SEND_ROUTING_KEY,
            message
        );
    }

    // SMS Messages
    /**
     * Send SMS message
     */
    public void sendSMSMessage(String phoneNumber, String message) {
        Map<String, Object> smsMessage = new HashMap<>();
        smsMessage.put("eventType", "SMS_SEND");
        smsMessage.put("phoneNumber", phoneNumber);
        smsMessage.put("message", message);
        smsMessage.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.SMS_EXCHANGE,
            RabbitMQConfig.SMS_SEND_ROUTING_KEY,
            smsMessage
        );
    }

    // Analytics Messages
    /**
     * Send analytics tracking message
     */
    public void sendAnalyticsMessage(String event, String userId, Map<String, Object> data) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "ANALYTICS_TRACK");
        message.put("event", event);
        message.put("userId", userId);
        message.put("data", data);
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ANALYTICS_EXCHANGE,
            RabbitMQConfig.ANALYTICS_TRACK_ROUTING_KEY,
            message
        );
    }

    /**
     * Send analytics event message
     */
    public void sendAnalyticsEvent(com.shopcuathuy.entity.AnalyticsEvent event) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "ANALYTICS_EVENT");
        message.put("eventId", event.getId());
        message.put("eventName", event.getEventName());
        message.put("eventType", event.getEventType());
        message.put("userId", event.getUser() != null ? event.getUser().getId() : null);
        message.put("productId", event.getProduct() != null ? event.getProduct().getId() : null);
        message.put("orderId", event.getOrder() != null ? event.getOrder().getId() : null);
        message.put("sessionId", event.getSessionId());
        message.put("ipAddress", event.getIpAddress());
        message.put("value", event.getValue());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ANALYTICS_EXCHANGE,
            RabbitMQConfig.ANALYTICS_EVENT_ROUTING_KEY,
            message
        );
    }

    // Inventory Messages
    /**
     * Send inventory update message
     */
    public void sendInventoryUpdateMessage(String productId, Integer oldStock, Integer newStock) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "INVENTORY_UPDATE");
        message.put("productId", productId);
        message.put("oldStock", oldStock);
        message.put("newStock", newStock);
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.INVENTORY_EXCHANGE,
            RabbitMQConfig.INVENTORY_UPDATE_ROUTING_KEY,
            message
        );
    }

    // Payment Messages
    /**
     * Send payment created message
     */
    public void sendPaymentCreated(com.shopcuathuy.entity.Payment payment) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "PAYMENT_CREATED");
        message.put("paymentId", payment.getId());
        message.put("orderId", payment.getOrder().getId());
        message.put("paymentMethodId", payment.getPaymentMethod().getId());
        message.put("amount", payment.getAmount());
        message.put("status", payment.getStatus());
        message.put("transactionId", payment.getTransactionId());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PAYMENT_EXCHANGE,
            RabbitMQConfig.PAYMENT_CREATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send payment processing message
     */
    public void sendPaymentProcessing(com.shopcuathuy.entity.Payment payment) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "PAYMENT_PROCESSING");
        message.put("paymentId", payment.getId());
        message.put("orderId", payment.getOrder().getId());
        message.put("paymentMethodId", payment.getPaymentMethod().getId());
        message.put("amount", payment.getAmount());
        message.put("status", payment.getStatus());
        message.put("gatewayUrl", payment.getGatewayUrl());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PAYMENT_EXCHANGE,
            RabbitMQConfig.PAYMENT_PROCESSING_ROUTING_KEY,
            message
        );
    }

    /**
     * Send payment success message
     */
    public void sendPaymentSuccess(com.shopcuathuy.entity.Payment payment) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "PAYMENT_SUCCESS");
        message.put("paymentId", payment.getId());
        message.put("orderId", payment.getOrder().getId());
        message.put("paymentMethodId", payment.getPaymentMethod().getId());
        message.put("amount", payment.getAmount());
        message.put("status", payment.getStatus());
        message.put("gatewayTransactionId", payment.getGatewayTransactionId());
        message.put("paidAt", payment.getPaidAt());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PAYMENT_EXCHANGE,
            RabbitMQConfig.PAYMENT_SUCCESS_ROUTING_KEY,
            message
        );
    }

    /**
     * Send payment failed message
     */
    public void sendPaymentFailed(com.shopcuathuy.entity.Payment payment) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "PAYMENT_FAILED");
        message.put("paymentId", payment.getId());
        message.put("orderId", payment.getOrder().getId());
        message.put("paymentMethodId", payment.getPaymentMethod().getId());
        message.put("amount", payment.getAmount());
        message.put("status", payment.getStatus());
        message.put("failureReason", payment.getFailureReason());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PAYMENT_EXCHANGE,
            RabbitMQConfig.PAYMENT_FAILED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send payment cancelled message
     */
    public void sendPaymentCancelled(com.shopcuathuy.entity.Payment payment) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "PAYMENT_CANCELLED");
        message.put("paymentId", payment.getId());
        message.put("orderId", payment.getOrder().getId());
        message.put("paymentMethodId", payment.getPaymentMethod().getId());
        message.put("amount", payment.getAmount());
        message.put("status", payment.getStatus());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PAYMENT_EXCHANGE,
            RabbitMQConfig.PAYMENT_CANCELLED_ROUTING_KEY,
            message
        );
    }

    // Shipping Messages
    /**
     * Send shipping update message
     */
    public void sendShippingUpdateMessage(String orderId, String trackingNumber, String status) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "SHIPPING_UPDATE");
        message.put("orderId", orderId);
        message.put("trackingNumber", trackingNumber);
        message.put("status", status);
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.SHIPPING_EXCHANGE,
            RabbitMQConfig.SHIPPING_UPDATE_ROUTING_KEY,
            message
        );
    }

    /**
     * Send shipping created message
     */
    public void sendShippingCreated(com.shopcuathuy.entity.Shipping shipping) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "SHIPPING_CREATED");
        message.put("shippingId", shipping.getId());
        message.put("orderId", shipping.getOrder().getId());
        message.put("trackingNumber", shipping.getTrackingNumber());
        message.put("carrier", shipping.getCarrier());
        message.put("status", shipping.getStatus());
        message.put("shippingCost", shipping.getShippingCost());
        message.put("estimatedDeliveryDate", shipping.getEstimatedDeliveryDate());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.SHIPPING_EXCHANGE,
            RabbitMQConfig.SHIPPING_CREATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send shipping status update message
     */
    public void sendShippingStatusUpdate(com.shopcuathuy.entity.Shipping shipping, 
                                       com.shopcuathuy.entity.Shipping.ShippingStatus oldStatus, 
                                       com.shopcuathuy.entity.Shipping.ShippingStatus newStatus) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "SHIPPING_STATUS_UPDATE");
        message.put("shippingId", shipping.getId());
        message.put("orderId", shipping.getOrder().getId());
        message.put("trackingNumber", shipping.getTrackingNumber());
        message.put("oldStatus", oldStatus);
        message.put("newStatus", newStatus);
        message.put("carrier", shipping.getCarrier());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.SHIPPING_EXCHANGE,
            RabbitMQConfig.SHIPPING_STATUS_UPDATE_ROUTING_KEY,
            message
        );
    }

    // Coupon Messages
    /**
     * Send coupon created message
     */
    public void sendCouponCreated(com.shopcuathuy.entity.Coupon coupon) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "COUPON_CREATED");
        message.put("couponId", coupon.getId());
        message.put("code", coupon.getCode());
        message.put("name", coupon.getName());
        message.put("type", coupon.getType());
        message.put("value", coupon.getValue());
        message.put("createdById", coupon.getCreatedBy().getId());
        message.put("startDate", coupon.getStartDate());
        message.put("endDate", coupon.getEndDate());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.COUPON_EXCHANGE,
            RabbitMQConfig.COUPON_CREATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send coupon applied message
     */
    public void sendCouponApplied(com.shopcuathuy.entity.CouponUsage couponUsage) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "COUPON_APPLIED");
        message.put("couponUsageId", couponUsage.getId());
        message.put("couponId", couponUsage.getCoupon().getId());
        message.put("userId", couponUsage.getUser().getId());
        message.put("orderId", couponUsage.getOrder().getId());
        message.put("discountAmount", couponUsage.getDiscountAmount());
        message.put("orderAmount", couponUsage.getOrderAmount());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.COUPON_EXCHANGE,
            RabbitMQConfig.COUPON_APPLIED_ROUTING_KEY,
            message
        );
    }

    // Seller Messages
    /**
     * Send seller created message
     */
    public void sendSellerCreated(com.shopcuathuy.entity.Seller seller) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "SELLER_CREATED");
        message.put("sellerId", seller.getId());
        message.put("userId", seller.getUser().getId());
        message.put("businessName", seller.getShopName());
        message.put("businessType", seller.getDescription());
        message.put("status", seller.getVerified() ? "VERIFIED" : "PENDING");
        message.put("isVerified", seller.getVerified());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.SELLER_EXCHANGE,
            RabbitMQConfig.SELLER_CREATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send seller status update message
     */
    public void sendSellerStatusUpdate(com.shopcuathuy.entity.Seller seller, 
                                      com.shopcuathuy.entity.Seller.SellerStatus oldStatus, 
                                      com.shopcuathuy.entity.Seller.SellerStatus newStatus, 
                                      String reason) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "SELLER_STATUS_UPDATE");
        message.put("sellerId", seller.getId());
        message.put("userId", seller.getUser().getId());
        message.put("businessName", seller.getShopName());
        message.put("oldStatus", oldStatus);
        message.put("newStatus", newStatus);
        message.put("reason", reason);
        message.put("isVerified", seller.getVerified());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.SELLER_EXCHANGE,
            RabbitMQConfig.SELLER_STATUS_UPDATE_ROUTING_KEY,
            message
        );
    }

    // Generic message sending
    /**
     * Send custom message to any exchange and routing key
     */
    public void sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    /**
     * Send message with delay
     */
    public void sendDelayedMessage(String exchange, String routingKey, Object message, long delay) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message, msg -> {
            msg.getMessageProperties().setDelay((int) delay);
            return msg;
        });
    }

    /**
     * Send category created message
     */
    public void sendCategoryCreatedMessage(com.shopcuathuy.dto.CategoryDTO categoryDTO) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "CATEGORY_CREATED");
        message.put("categoryId", categoryDTO.getId());
        message.put("categoryName", categoryDTO.getName());
        message.put("parentId", categoryDTO.getParentId());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.CATEGORY_EXCHANGE,
            RabbitMQConfig.CATEGORY_CREATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send wishlist updated message
     */
    public void sendWishlistUpdated(Long userId, Long productId) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "WISHLIST_UPDATED");
        message.put("userId", userId);
        message.put("productId", productId);
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.WISHLIST_EXCHANGE,
            RabbitMQConfig.WISHLIST_UPDATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send wishlist cleared message
     */
    public void sendWishlistCleared(Long userId) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "WISHLIST_CLEARED");
        message.put("userId", userId);
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.WISHLIST_EXCHANGE,
            RabbitMQConfig.WISHLIST_CLEARED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send notification created message
     */
    public void sendNotificationCreated(com.shopcuathuy.entity.Notification notification) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "NOTIFICATION_CREATED");
        message.put("notificationId", notification.getId());
        message.put("userId", notification.getUser().getId());
        message.put("title", notification.getTitle());
        message.put("type", notification.getType());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.NOTIFICATION_EXCHANGE,
            RabbitMQConfig.NOTIFICATION_CREATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send review created message
     */
    public void sendReviewCreated(com.shopcuathuy.entity.ProductReview review) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "REVIEW_CREATED");
        message.put("reviewId", review.getId());
        message.put("userId", review.getUser().getId());
        message.put("productId", review.getProduct().getId());
        message.put("rating", review.getRating());
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.REVIEW_EXCHANGE,
            RabbitMQConfig.REVIEW_CREATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send cart updated message
     */
    public void sendCartUpdated(Long userId, Long productId) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "CART_UPDATED");
        message.put("userId", userId);
        message.put("productId", productId);
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.CART_EXCHANGE,
            RabbitMQConfig.CART_UPDATED_ROUTING_KEY,
            message
        );
    }

    /**
     * Send cart cleared message
     */
    public void sendCartCleared(Long userId) {
        Map<String, Object> message = new HashMap<>();
        message.put("eventType", "CART_CLEARED");
        message.put("userId", userId);
        message.put("timestamp", LocalDateTime.now());
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.CART_EXCHANGE,
            RabbitMQConfig.CART_CLEARED_ROUTING_KEY,
            message
        );
    }
}
