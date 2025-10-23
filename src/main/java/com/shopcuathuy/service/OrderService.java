package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.shopcuathuy.dto.CreateOrderDTO;
import com.shopcuathuy.dto.OrderDTO;
import com.shopcuathuy.dto.UpdateOrderDTO;
import com.shopcuathuy.entity.Order;
import com.shopcuathuy.exception.ResourceNotFoundException;
import com.shopcuathuy.mapper.OrderMapper;
import com.shopcuathuy.repository.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;



/**
 * Service class for Order operations
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Create a new order
     */
    @CacheEvict(value = "orders", allEntries = true)
    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        // Generate unique order number
        String orderNumber = generateOrderNumber();
        
        // Check if order number already exists (very unlikely but safe)
        while (orderRepository.existsByOrderNumber(orderNumber)) {
            orderNumber = generateOrderNumber();
        }

        // Create order entity
        Order order = orderMapper.toEntity(createOrderDTO);
        order.setOrderNumber(orderNumber);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Send order created message to RabbitMQ
        messageProducerService.sendOrderCreatedMessage(orderMapper.toDTO(savedOrder));

        // Store order data to MinIO
        storeOrderToMinIO(savedOrder);

        return orderMapper.toDTO(savedOrder);
    }

    /**
     * Get order by ID
     */
    @Cacheable(value = "orders", key = "#id")
    @Transactional(readOnly = true)
    public OrderDTO getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toDTO(order);
    }

    /**
     * Get order by order number
     */
    @Cacheable(value = "orders", key = "#orderNumber")
    @Transactional(readOnly = true)
    public OrderDTO getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with order number: " + orderNumber));
        return orderMapper.toDTO(order);
    }

    /**
     * Get all orders with pagination
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(orderMapper::toDTO);
    }

    /**
     * Get orders by user
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByUser(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orderMapper.toDTOList(orders);
    }

    /**
     * Get orders by user with pagination
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersByUser(String userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        return orders.map(orderMapper::toDTO);
    }

    /**
     * Get orders by seller
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersBySeller(String sellerId) {
        List<Order> orders = orderRepository.findBySellerId(sellerId);
        return orderMapper.toDTOList(orders);
    }

    /**
     * Get orders by seller with pagination
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersBySeller(String sellerId, Pageable pageable) {
        Page<Order> orders = orderRepository.findBySellerId(sellerId, pageable);
        return orders.map(orderMapper::toDTO);
    }

    /**
     * Get orders by status
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByStatus(Order.OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return orderMapper.toDTOList(orders);
    }

    /**
     * Get orders by status with pagination
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersByStatus(Order.OrderStatus status, Pageable pageable) {
        Page<Order> orders = orderRepository.findByStatus(status, pageable);
        return orders.map(orderMapper::toDTO);
    }

    /**
     * Get orders by payment status
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByPaymentStatus(Order.PaymentStatus paymentStatus) {
        List<Order> orders = orderRepository.findByPaymentStatus(paymentStatus);
        return orderMapper.toDTOList(orders);
    }

    /**
     * Get orders by payment status with pagination
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersByPaymentStatus(Order.PaymentStatus paymentStatus, Pageable pageable) {
        Page<Order> orders = orderRepository.findByPaymentStatus(paymentStatus, pageable);
        return orders.map(orderMapper::toDTO);
    }

    /**
     * Get orders by user and status
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByUserAndStatus(String userId, Order.OrderStatus status) {
        List<Order> orders = orderRepository.findByUserIdAndStatus(userId, status);
        return orderMapper.toDTOList(orders);
    }

    /**
     * Get orders by user and status with pagination
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersByUserAndStatus(String userId, Order.OrderStatus status, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserIdAndStatus(userId, status, pageable);
        return orders.map(orderMapper::toDTO);
    }

    /**
     * Get orders by seller and status
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersBySellerAndStatus(String sellerId, Order.OrderStatus status) {
        List<Order> orders = orderRepository.findBySellerIdAndStatus(sellerId, status);
        return orderMapper.toDTOList(orders);
    }

    /**
     * Get orders by seller and status with pagination
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersBySellerAndStatus(String sellerId, Order.OrderStatus status, Pageable pageable) {
        Page<Order> orders = orderRepository.findBySellerIdAndStatus(sellerId, status, pageable);
        return orders.map(orderMapper::toDTO);
    }

    /**
     * Get orders by multiple criteria
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersByCriteria(String userId, String sellerId, Order.OrderStatus status,
                                            Order.PaymentStatus paymentStatus, Order.PaymentMethod paymentMethod,
                                            BigDecimal minAmount, BigDecimal maxAmount, LocalDateTime startDate,
                                            LocalDateTime endDate, Pageable pageable) {
        Page<Order> orders = orderRepository.findByMultipleCriteria(
            userId, sellerId, status, paymentStatus, paymentMethod,
            minAmount, maxAmount, startDate, endDate, pageable);
        return orders.map(orderMapper::toDTO);
    }

    /**
     * Search orders by text
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> searchOrders(String search) {
        List<Order> orders = orderRepository.findByTextSearch(search);
        return orderMapper.toDTOList(orders);
    }

    /**
     * Search orders by text with pagination
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> searchOrders(String search, Pageable pageable) {
        Page<Order> orders = orderRepository.findByTextSearch(search, pageable);
        return orders.map(orderMapper::toDTO);
    }

    /**
     * Get recent orders
     */
    @Cacheable(value = "recent-orders")
    @Transactional(readOnly = true)
    public List<OrderDTO> getRecentOrders(LocalDateTime date) {
        List<Order> orders = orderRepository.findRecentOrders(date);
        return orderMapper.toDTOList(orders);
    }

    /**
     * Get recent orders with pagination
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> getRecentOrders(LocalDateTime date, Pageable pageable) {
        Page<Order> orders = orderRepository.findRecentOrders(date, pageable);
        return orders.map(orderMapper::toDTO);
    }

    /**
     * Update order
     */
    @CacheEvict(value = {"orders", "recent-orders"}, allEntries = true)
    public OrderDTO updateOrder(String id, UpdateOrderDTO updateOrderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        // Update order fields
        orderMapper.updateEntity(updateOrderDTO, order);
        order.setUpdatedAt(LocalDateTime.now());

        // Save updated order
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    /**
     * Delete order
     */
    @CacheEvict(value = {"orders", "recent-orders"}, allEntries = true)
    public void deleteOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    /**
     * Update order status
     */
    @CacheEvict(value = {"orders", "recent-orders"}, allEntries = true)
    public OrderDTO updateOrderStatus(String id, Order.OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        
        // Set specific timestamps based on status
        switch (status) {
            case SHIPPED:
                order.setShippedAt(LocalDateTime.now());
                break;
            case DELIVERED:
                order.setDeliveredAt(LocalDateTime.now());
                break;
            case CANCELLED:
                order.setCancelledAt(LocalDateTime.now());
                break;
        }
        
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    /**
     * Update payment status
     */
    @CacheEvict(value = "orders", allEntries = true)
    public OrderDTO updatePaymentStatus(String id, Order.PaymentStatus paymentStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        order.setPaymentStatus(paymentStatus);
        order.setUpdatedAt(LocalDateTime.now());
        
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    /**
     * Update tracking number
     */
    @CacheEvict(value = "orders", allEntries = true)
    public OrderDTO updateTrackingNumber(String id, String trackingNumber) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        order.setTrackingNumber(trackingNumber);
        order.setUpdatedAt(LocalDateTime.now());
        
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    /**
     * Cancel order
     */
    @CacheEvict(value = {"orders", "recent-orders"}, allEntries = true)
    public OrderDTO cancelOrder(String id, String cancellationReason) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        order.setCancellationReason(cancellationReason);
        order.setCancelledAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    /**
     * Ship order
     */
    @CacheEvict(value = {"orders", "recent-orders"}, allEntries = true)
    public OrderDTO shipOrder(String id, String trackingNumber) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        order.setStatus(Order.OrderStatus.SHIPPED);
        order.setTrackingNumber(trackingNumber);
        order.setShippedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    /**
     * Deliver order
     */
    @CacheEvict(value = {"orders", "recent-orders"}, allEntries = true)
    public OrderDTO deliverOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        order.setStatus(Order.OrderStatus.DELIVERED);
        order.setDeliveredAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    /**
     * Get order count by user
     */
    @Transactional(readOnly = true)
    public long getOrderCountByUser(String userId) {
        return orderRepository.countByUserId(userId);
    }

    /**
     * Get order count by seller
     */
    @Transactional(readOnly = true)
    public long getOrderCountBySeller(String sellerId) {
        return orderRepository.countBySellerId(sellerId);
    }

    /**
     * Get order count by status
     */
    @Transactional(readOnly = true)
    public long getOrderCountByStatus(Order.OrderStatus status) {
        return orderRepository.countByStatus(status);
    }

    /**
     * Get order count by payment status
     */
    @Transactional(readOnly = true)
    public long getOrderCountByPaymentStatus(Order.PaymentStatus paymentStatus) {
        return orderRepository.countByPaymentStatus(paymentStatus);
    }

    /**
     * Get order count by user and status
     */
    @Transactional(readOnly = true)
    public long getOrderCountByUserAndStatus(String userId, Order.OrderStatus status) {
        return orderRepository.countByUserIdAndStatus(userId, status);
    }

    /**
     * Get order count by seller and status
     */
    @Transactional(readOnly = true)
    public long getOrderCountBySellerAndStatus(String sellerId, Order.OrderStatus status) {
        return orderRepository.countBySellerIdAndStatus(sellerId, status);
    }

    /**
     * Get order count by user and payment status
     */
    @Transactional(readOnly = true)
    public long getOrderCountByUserAndPaymentStatus(String userId, Order.PaymentStatus paymentStatus) {
        return orderRepository.countByUserIdAndPaymentStatus(userId, paymentStatus);
    }

    /**
     * Get order count by seller and payment status
     */
    @Transactional(readOnly = true)
    public long getOrderCountBySellerAndPaymentStatus(String sellerId, Order.PaymentStatus paymentStatus) {
        return orderRepository.countBySellerIdAndPaymentStatus(sellerId, paymentStatus);
    }

    /**
     * Get order count by total amount range
     */
    @Transactional(readOnly = true)
    public long getOrderCountByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        return orderRepository.countByTotalAmountBetween(minAmount, maxAmount);
    }

    /**
     * Get order count created after date
     */
    @Transactional(readOnly = true)
    public long getOrderCountCreatedAfter(LocalDateTime date) {
        return orderRepository.countByCreatedAtAfter(date);
    }

    /**
     * Get order count created before date
     */
    @Transactional(readOnly = true)
    public long getOrderCountCreatedBefore(LocalDateTime date) {
        return orderRepository.countByCreatedAtBefore(date);
    }

    /**
     * Get order count created between dates
     */
    @Transactional(readOnly = true)
    public long getOrderCountCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.countByCreatedAtBetween(startDate, endDate);
    }

    /**
     * Check if order number exists
     */
    @Transactional(readOnly = true)
    public boolean orderNumberExists(String orderNumber) {
        return orderRepository.existsByOrderNumber(orderNumber);
    }

    /**
     * Check if tracking number exists
     */
    @Transactional(readOnly = true)
    public boolean trackingNumberExists(String trackingNumber) {
        return orderRepository.existsByTrackingNumber(trackingNumber);
    }

    /**
     * Check if payment reference exists
     */
    @Transactional(readOnly = true)
    public boolean paymentReferenceExists(String paymentReference) {
        return orderRepository.existsByPaymentReference(paymentReference);
    }

    /**
     * Check if order number exists excluding order
     */
    @Transactional(readOnly = true)
    public boolean orderNumberExistsExcludingOrder(String orderNumber, String orderId) {
        return orderRepository.existsByOrderNumberAndIdNot(orderNumber, orderId);
    }

    /**
     * Check if tracking number exists excluding order
     */
    @Transactional(readOnly = true)
    public boolean trackingNumberExistsExcludingOrder(String trackingNumber, String orderId) {
        return orderRepository.existsByTrackingNumberAndIdNot(trackingNumber, orderId);
    }

    /**
     * Check if payment reference exists excluding order
     */
    @Transactional(readOnly = true)
    public boolean paymentReferenceExistsExcludingOrder(String paymentReference, String orderId) {
        return orderRepository.existsByPaymentReferenceAndIdNot(paymentReference, orderId);
    }

    /**
     * Generate unique order number
     */
    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Export order data
     */
    public String exportOrderData(Long userId, LocalDateTime startDate, LocalDateTime endDate, String format) {
        List<Order> orders = orderRepository.findByUserIdAndCreatedAtBetween(String.valueOf(userId), startDate, endDate);
        
        // Generate export data
        String exportData = generateOrderExportData(orders, format);
        
        // Store to MinIO
        String fileName = "order-export-" + userId + "-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadOrderExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Store order data to MinIO
     */
    private void storeOrderToMinIO(Order order) {
        try {
            String orderData = createOrderDataJson(order);
            String fileName = "orders/" + order.getStatus() + "/" +
                            order.getCreatedAt().toLocalDate() + "/" +
                            order.getId() + ".json";
            
            fileStorageService.uploadOrderData(fileName, orderData);
        } catch (Exception e) {
            System.err.println("Failed to store order data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create order data JSON
     */
    private String createOrderDataJson(Order order) {
        return String.format(
            "{\"id\":\"%s\",\"orderNumber\":\"%s\",\"userId\":%d,\"status\":\"%s\",\"totalAmount\":%s,\"createdAt\":\"%s\"}",
            order.getId(),
            order.getOrderNumber(),
            order.getUserId(),
            order.getStatus(),
            order.getTotalAmount(),
            order.getCreatedAt()
        );
    }

    /**
     * Generate order export data
     */
    private String generateOrderExportData(List<Order> orders, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateOrderJsonExport(orders);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateOrderCsvExport(orders);
        }
        return generateOrderJsonExport(orders);
    }

    /**
     * Generate JSON export
     */
    private String generateOrderJsonExport(List<Order> orders) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            json.append(createOrderDataJson(order));
            if (i < orders.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateOrderCsvExport(List<Order> orders) {
        StringBuilder csv = new StringBuilder("id,orderNumber,userId,status,totalAmount,createdAt\n");
        for (Order order : orders) {
            csv.append(String.format("%s,%s,%d,%s,%s,%s\n",
                order.getId(),
                order.getOrderNumber(),
                order.getUserId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt()
            ));
        }
        return csv.toString();
    }
}
