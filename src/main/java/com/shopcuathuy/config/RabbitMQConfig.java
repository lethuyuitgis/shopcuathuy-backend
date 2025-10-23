package com.shopcuathuy.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Configuration
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Configuration
public class RabbitMQConfig {

    // Queue names
    public static final String ORDER_QUEUE = "order.queue";
    public static final String PRODUCT_QUEUE = "product.queue";
    public static final String USER_QUEUE = "user.queue";
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String EMAIL_QUEUE = "email.queue";
    public static final String SMS_QUEUE = "sms.queue";
    public static final String ANALYTICS_QUEUE = "analytics.queue";
    public static final String INVENTORY_QUEUE = "inventory.queue";
    public static final String PAYMENT_QUEUE = "payment.queue";
    public static final String SHIPPING_QUEUE = "shipping.queue";

    // Exchange names
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String PRODUCT_EXCHANGE = "product.exchange";
    public static final String USER_EXCHANGE = "user.exchange";
    public static final String NOTIFICATION_EXCHANGE = "notification.exchange";
    public static final String EMAIL_EXCHANGE = "email.exchange";
    public static final String SMS_EXCHANGE = "sms.exchange";
    public static final String ANALYTICS_EXCHANGE = "analytics.exchange";
    public static final String INVENTORY_EXCHANGE = "inventory.exchange";
    public static final String PAYMENT_EXCHANGE = "payment.exchange";
    public static final String SHIPPING_EXCHANGE = "shipping.exchange";
    public static final String COUPON_EXCHANGE = "coupon.exchange";
    public static final String SELLER_EXCHANGE = "seller.exchange";
    public static final String CATEGORY_EXCHANGE = "category.exchange";
    public static final String WISHLIST_EXCHANGE = "wishlist.exchange";
    public static final String REVIEW_EXCHANGE = "review.exchange";
    public static final String CART_EXCHANGE = "cart.exchange";

    // Routing keys
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";
    public static final String ORDER_UPDATED_ROUTING_KEY = "order.updated";
    public static final String ORDER_CANCELLED_ROUTING_KEY = "order.cancelled";
    public static final String ORDER_SHIPPED_ROUTING_KEY = "order.shipped";
    public static final String ORDER_DELIVERED_ROUTING_KEY = "order.delivered";

    public static final String PRODUCT_CREATED_ROUTING_KEY = "product.created";
    public static final String PRODUCT_UPDATED_ROUTING_KEY = "product.updated";
    public static final String PRODUCT_DELETED_ROUTING_KEY = "product.deleted";
    public static final String PRODUCT_STOCK_LOW_ROUTING_KEY = "product.stock.low";

    public static final String USER_REGISTERED_ROUTING_KEY = "user.registered";
    public static final String USER_UPDATED_ROUTING_KEY = "user.updated";
    public static final String USER_DEACTIVATED_ROUTING_KEY = "user.deactivated";

    public static final String NOTIFICATION_SEND_ROUTING_KEY = "notification.send";
    public static final String EMAIL_SEND_ROUTING_KEY = "email.send";
    public static final String SMS_SEND_ROUTING_KEY = "sms.send";

    public static final String ANALYTICS_TRACK_ROUTING_KEY = "analytics.track";
    public static final String ANALYTICS_EVENT_ROUTING_KEY = "analytics.event";
    public static final String INVENTORY_UPDATE_ROUTING_KEY = "inventory.update";
    
    public static final String PAYMENT_CREATED_ROUTING_KEY = "payment.created";
    public static final String PAYMENT_PROCESSING_ROUTING_KEY = "payment.processing";
    public static final String PAYMENT_SUCCESS_ROUTING_KEY = "payment.success";
    public static final String PAYMENT_FAILED_ROUTING_KEY = "payment.failed";
    public static final String PAYMENT_CANCELLED_ROUTING_KEY = "payment.cancelled";
    
    public static final String SHIPPING_UPDATE_ROUTING_KEY = "shipping.update";
    public static final String SHIPPING_CREATED_ROUTING_KEY = "shipping.created";
    public static final String SHIPPING_STATUS_UPDATE_ROUTING_KEY = "shipping.status.update";
    
    public static final String COUPON_CREATED_ROUTING_KEY = "coupon.created";
    public static final String COUPON_APPLIED_ROUTING_KEY = "coupon.applied";
    
    public static final String SELLER_CREATED_ROUTING_KEY = "seller.created";
    public static final String SELLER_STATUS_UPDATE_ROUTING_KEY = "seller.status.update";
    public static final String CATEGORY_CREATED_ROUTING_KEY = "category.created";
    public static final String WISHLIST_UPDATED_ROUTING_KEY = "wishlist.updated";
    public static final String WISHLIST_CLEARED_ROUTING_KEY = "wishlist.cleared";
    public static final String NOTIFICATION_CREATED_ROUTING_KEY = "notification.created";
    public static final String REVIEW_CREATED_ROUTING_KEY = "review.created";
    public static final String CART_UPDATED_ROUTING_KEY = "cart.updated";
    public static final String CART_CLEARED_ROUTING_KEY = "cart.cleared";

    /**
     * Message converter for JSON serialization
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate configuration
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    /**
     * RabbitListener container factory
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    // Order Queue Configuration
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_QUEUE).build();
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with("order.*");
    }

    // Product Queue Configuration
    @Bean
    public Queue productQueue() {
        return QueueBuilder.durable(PRODUCT_QUEUE).build();
    }

    @Bean
    public TopicExchange productExchange() {
        return new TopicExchange(PRODUCT_EXCHANGE);
    }

    @Bean
    public Binding productBinding() {
        return BindingBuilder
                .bind(productQueue())
                .to(productExchange())
                .with("product.*");
    }

    // User Queue Configuration
    @Bean
    public Queue userQueue() {
        return QueueBuilder.durable(USER_QUEUE).build();
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE);
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder
                .bind(userQueue())
                .to(userExchange())
                .with("user.*");
    }

    // Notification Queue Configuration
    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE).build();
    }

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(notificationExchange())
                .with("notification.*");
    }

    // Email Queue Configuration
    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE).build();
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(emailExchange())
                .with("email.*");
    }

    // SMS Queue Configuration
    @Bean
    public Queue smsQueue() {
        return QueueBuilder.durable(SMS_QUEUE).build();
    }

    @Bean
    public TopicExchange smsExchange() {
        return new TopicExchange(SMS_EXCHANGE);
    }

    @Bean
    public Binding smsBinding() {
        return BindingBuilder
                .bind(smsQueue())
                .to(smsExchange())
                .with("sms.*");
    }

    // Analytics Queue Configuration
    @Bean
    public Queue analyticsQueue() {
        return QueueBuilder.durable(ANALYTICS_QUEUE).build();
    }

    @Bean
    public TopicExchange analyticsExchange() {
        return new TopicExchange(ANALYTICS_EXCHANGE);
    }

    @Bean
    public Binding analyticsBinding() {
        return BindingBuilder
                .bind(analyticsQueue())
                .to(analyticsExchange())
                .with("analytics.*");
    }

    // Inventory Queue Configuration
    @Bean
    public Queue inventoryQueue() {
        return QueueBuilder.durable(INVENTORY_QUEUE).build();
    }

    @Bean
    public TopicExchange inventoryExchange() {
        return new TopicExchange(INVENTORY_EXCHANGE);
    }

    @Bean
    public Binding inventoryBinding() {
        return BindingBuilder
                .bind(inventoryQueue())
                .to(inventoryExchange())
                .with("inventory.*");
    }

    // Payment Queue Configuration
    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable(PAYMENT_QUEUE).build();
    }

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(PAYMENT_EXCHANGE);
    }

    @Bean
    public Binding paymentBinding() {
        return BindingBuilder
                .bind(paymentQueue())
                .to(paymentExchange())
                .with("payment.*");
    }

    // Shipping Queue Configuration
    @Bean
    public Queue shippingQueue() {
        return QueueBuilder.durable(SHIPPING_QUEUE).build();
    }

    @Bean
    public TopicExchange shippingExchange() {
        return new TopicExchange(SHIPPING_EXCHANGE);
    }

    @Bean
    public Binding shippingBinding() {
        return BindingBuilder
                .bind(shippingQueue())
                .to(shippingExchange())
                .with("shipping.*");
    }

    // Coupon Queue Configuration
    @Bean
    public Queue couponQueue() {
        return QueueBuilder.durable("coupon.queue").build();
    }

    @Bean
    public TopicExchange couponExchange() {
        return new TopicExchange(COUPON_EXCHANGE);
    }

    @Bean
    public Binding couponBinding() {
        return BindingBuilder
                .bind(couponQueue())
                .to(couponExchange())
                .with("coupon.*");
    }

    // Seller Queue Configuration
    @Bean
    public Queue sellerQueue() {
        return QueueBuilder.durable("seller.queue").build();
    }

    @Bean
    public TopicExchange sellerExchange() {
        return new TopicExchange(SELLER_EXCHANGE);
    }

    @Bean
    public Binding sellerBinding() {
        return BindingBuilder
                .bind(sellerQueue())
                .to(sellerExchange())
                .with("seller.*");
    }

    // Category Queue Configuration
    @Bean
    public Queue categoryQueue() {
        return QueueBuilder.durable("category.queue").build();
    }

    @Bean
    public TopicExchange categoryExchange() {
        return new TopicExchange(CATEGORY_EXCHANGE);
    }

    @Bean
    public Binding categoryBinding() {
        return BindingBuilder
                .bind(categoryQueue())
                .to(categoryExchange())
                .with("category.*");
    }

    // Wishlist Queue Configuration
    @Bean
    public Queue wishlistQueue() {
        return QueueBuilder.durable("wishlist.queue").build();
    }

    @Bean
    public TopicExchange wishlistExchange() {
        return new TopicExchange(WISHLIST_EXCHANGE);
    }

    @Bean
    public Binding wishlistBinding() {
        return BindingBuilder
                .bind(wishlistQueue())
                .to(wishlistExchange())
                .with("wishlist.*");
    }

    // Review Queue Configuration
    @Bean
    public Queue reviewQueue() {
        return QueueBuilder.durable("review.queue").build();
    }

    @Bean
    public TopicExchange reviewExchange() {
        return new TopicExchange(REVIEW_EXCHANGE);
    }

    @Bean
    public Binding reviewBinding() {
        return BindingBuilder
                .bind(reviewQueue())
                .to(reviewExchange())
                .with("review.*");
    }

    // Cart Queue Configuration
    @Bean
    public Queue cartQueue() {
        return QueueBuilder.durable("cart.queue").build();
    }

    @Bean
    public TopicExchange cartExchange() {
        return new TopicExchange(CART_EXCHANGE);
    }

    @Bean
    public Binding cartBinding() {
        return BindingBuilder
                .bind(cartQueue())
                .to(cartExchange())
                .with("cart.*");
    }

    // Dead Letter Queue Configuration
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("dead.letter.queue").build();
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange("dead.letter.exchange");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with("dead.letter.*");
    }

    // Retry Queue Configuration
    @Bean
    public Queue retryQueue() {
        return QueueBuilder.durable("retry.queue").build();
    }

    @Bean
    public TopicExchange retryExchange() {
        return new TopicExchange("retry.exchange");
    }

    @Bean
    public Binding retryBinding() {
        return BindingBuilder
                .bind(retryQueue())
                .to(retryExchange())
                .with("retry.*");
    }
}
