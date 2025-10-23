package com.shopcuathuy.controller;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.shopcuathuy.dto.CreateOrderDTO;
import com.shopcuathuy.dto.OrderDTO;
import com.shopcuathuy.dto.UpdateOrderDTO;
import com.shopcuathuy.entity.Order;
import com.shopcuathuy.exception.DuplicateResourceException;
import com.shopcuathuy.exception.ResourceNotFoundException;
import com.shopcuathuy.exception.ValidationException;
import com.shopcuathuy.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;




/**
 * REST Controller for Order operations
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "APIs for managing orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Create a new order
     */
    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Order number already exists")
    })
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderDTO createOrderDTO) {
        try {
            OrderDTO order = orderService.createOrder(createOrderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (DuplicateResourceException e) {
            throw e;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create order: " + e.getMessage(), e);
        }
    }

    /**
     * Get order by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve an order by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id) {
        try {
            OrderDTO order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order: " + e.getMessage(), e);
        }
    }

    /**
     * Get order by order number
     */
    @GetMapping("/order-number/{orderNumber}")
    @Operation(summary = "Get order by order number", description = "Retrieve an order by its order number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> getOrderByOrderNumber(@PathVariable String orderNumber) {
        try {
            OrderDTO order = orderService.getOrderByOrderNumber(orderNumber);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order: " + e.getMessage(), e);
        }
    }

    /**
     * Get all orders with pagination
     */
    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve all orders with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<Page<OrderDTO>> getAllOrders(Pageable pageable) {
        try {
            Page<OrderDTO> orders = orderService.getAllOrders(pageable);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by user
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get orders by user", description = "Retrieve orders by user ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable String userId) {
        try {
            List<OrderDTO> orders = orderService.getOrdersByUser(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by user: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by user with pagination
     */
    @GetMapping("/user/{userId}/paged")
    @Operation(summary = "Get orders by user with pagination", description = "Retrieve orders by user ID with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<Page<OrderDTO>> getOrdersByUser(@PathVariable String userId, Pageable pageable) {
        try {
            Page<OrderDTO> orders = orderService.getOrdersByUser(userId, pageable);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by user: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by seller
     */
    @GetMapping("/seller/{sellerId}")
    @Operation(summary = "Get orders by seller", description = "Retrieve orders by seller ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<List<OrderDTO>> getOrdersBySeller(@PathVariable String sellerId) {
        try {
            List<OrderDTO> orders = orderService.getOrdersBySeller(sellerId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by seller: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by seller with pagination
     */
    @GetMapping("/seller/{sellerId}/paged")
    @Operation(summary = "Get orders by seller with pagination", description = "Retrieve orders by seller ID with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<Page<OrderDTO>> getOrdersBySeller(@PathVariable String sellerId, Pageable pageable) {
        try {
            Page<OrderDTO> orders = orderService.getOrdersBySeller(sellerId, pageable);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by seller: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get orders by status", description = "Retrieve orders by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable Order.OrderStatus status) {
        try {
            List<OrderDTO> orders = orderService.getOrdersByStatus(status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by status with pagination
     */
    @GetMapping("/status/{status}/paged")
    @Operation(summary = "Get orders by status with pagination", description = "Retrieve orders by status with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<Page<OrderDTO>> getOrdersByStatus(@PathVariable Order.OrderStatus status, Pageable pageable) {
        try {
            Page<OrderDTO> orders = orderService.getOrdersByStatus(status, pageable);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by payment status
     */
    @GetMapping("/payment-status/{paymentStatus}")
    @Operation(summary = "Get orders by payment status", description = "Retrieve orders by payment status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<List<OrderDTO>> getOrdersByPaymentStatus(@PathVariable Order.PaymentStatus paymentStatus) {
        try {
            List<OrderDTO> orders = orderService.getOrdersByPaymentStatus(paymentStatus);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by payment status: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by payment status with pagination
     */
    @GetMapping("/payment-status/{paymentStatus}/paged")
    @Operation(summary = "Get orders by payment status with pagination", description = "Retrieve orders by payment status with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<Page<OrderDTO>> getOrdersByPaymentStatus(@PathVariable Order.PaymentStatus paymentStatus, Pageable pageable) {
        try {
            Page<OrderDTO> orders = orderService.getOrdersByPaymentStatus(paymentStatus, pageable);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by payment status: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by user and status
     */
    @GetMapping("/user/{userId}/status/{status}")
    @Operation(summary = "Get orders by user and status", description = "Retrieve orders by user ID and status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<List<OrderDTO>> getOrdersByUserAndStatus(@PathVariable String userId, @PathVariable Order.OrderStatus status) {
        try {
            List<OrderDTO> orders = orderService.getOrdersByUserAndStatus(userId, status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by user and status: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by user and status with pagination
     */
    @GetMapping("/user/{userId}/status/{status}/paged")
    @Operation(summary = "Get orders by user and status with pagination", description = "Retrieve orders by user ID and status with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<Page<OrderDTO>> getOrdersByUserAndStatus(@PathVariable String userId, @PathVariable Order.OrderStatus status, Pageable pageable) {
        try {
            Page<OrderDTO> orders = orderService.getOrdersByUserAndStatus(userId, status, pageable);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by user and status: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by seller and status
     */
    @GetMapping("/seller/{sellerId}/status/{status}")
    @Operation(summary = "Get orders by seller and status", description = "Retrieve orders by seller ID and status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<List<OrderDTO>> getOrdersBySellerAndStatus(@PathVariable String sellerId, @PathVariable Order.OrderStatus status) {
        try {
            List<OrderDTO> orders = orderService.getOrdersBySellerAndStatus(sellerId, status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by seller and status: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by seller and status with pagination
     */
    @GetMapping("/seller/{sellerId}/status/{status}/paged")
    @Operation(summary = "Get orders by seller and status with pagination", description = "Retrieve orders by seller ID and status with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<Page<OrderDTO>> getOrdersBySellerAndStatus(@PathVariable String sellerId, @PathVariable Order.OrderStatus status, Pageable pageable) {
        try {
            Page<OrderDTO> orders = orderService.getOrdersBySellerAndStatus(sellerId, status, pageable);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by seller and status: " + e.getMessage(), e);
        }
    }

    /**
     * Get orders by multiple criteria
     */
    @GetMapping("/criteria")
    @Operation(summary = "Get orders by criteria", description = "Retrieve orders filtered by multiple criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    public ResponseEntity<Page<OrderDTO>> getOrdersByCriteria(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String sellerId,
            @RequestParam(required = false) Order.OrderStatus status,
            @RequestParam(required = false) Order.PaymentStatus paymentStatus,
            @RequestParam(required = false) Order.PaymentMethod paymentMethod,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            Pageable pageable) {
        try {
            Page<OrderDTO> orders = orderService.getOrdersByCriteria(
                userId, sellerId, status, paymentStatus, paymentMethod,
                minAmount, maxAmount, startDate, endDate, pageable);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get orders by criteria: " + e.getMessage(), e);
        }
    }

    /**
     * Search orders
     */
    @GetMapping("/search")
    @Operation(summary = "Search orders", description = "Search orders by text")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders found")
    })
    public ResponseEntity<List<OrderDTO>> searchOrders(@RequestParam String search) {
        try {
            List<OrderDTO> orders = orderService.searchOrders(search);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search orders: " + e.getMessage(), e);
        }
    }

    /**
     * Search orders with pagination
     */
    @GetMapping("/search/paged")
    @Operation(summary = "Search orders with pagination", description = "Search orders by text with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders found")
    })
    public ResponseEntity<Page<OrderDTO>> searchOrders(@RequestParam String search, Pageable pageable) {
        try {
            Page<OrderDTO> orders = orderService.searchOrders(search, pageable);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search orders: " + e.getMessage(), e);
        }
    }

    /**
     * Get recent orders
     */
    @GetMapping("/recent")
    @Operation(summary = "Get recent orders", description = "Retrieve recent orders")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recent orders retrieved successfully")
    })
    public ResponseEntity<List<OrderDTO>> getRecentOrders(@RequestParam LocalDateTime date) {
        try {
            List<OrderDTO> orders = orderService.getRecentOrders(date);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get recent orders: " + e.getMessage(), e);
        }
    }

    /**
     * Get recent orders with pagination
     */
    @GetMapping("/recent/paged")
    @Operation(summary = "Get recent orders with pagination", description = "Retrieve recent orders with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recent orders retrieved successfully")
    })
    public ResponseEntity<Page<OrderDTO>> getRecentOrders(@RequestParam LocalDateTime date, Pageable pageable) {
        try {
            Page<OrderDTO> orders = orderService.getRecentOrders(date, pageable);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get recent orders: " + e.getMessage(), e);
        }
    }

    /**
     * Update order
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update order", description = "Update an existing order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order updated successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable String id, @Valid @RequestBody UpdateOrderDTO updateOrderDTO) {
        try {
            OrderDTO order = orderService.updateOrder(id, updateOrderDTO);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update order: " + e.getMessage(), e);
        }
    }

    /**
     * Delete order
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order", description = "Delete an order by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete order: " + e.getMessage(), e);
        }
    }

    /**
     * Update order status
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update order status", description = "Update order status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable String id, @RequestBody Map<String, Order.OrderStatus> request) {
        try {
            Order.OrderStatus status = request.get("status");
            OrderDTO order = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update order status: " + e.getMessage(), e);
        }
    }

    /**
     * Update payment status
     */
    @PatchMapping("/{id}/payment-status")
    @Operation(summary = "Update payment status", description = "Update order payment status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> updatePaymentStatus(@PathVariable String id, @RequestBody Map<String, Order.PaymentStatus> request) {
        try {
            Order.PaymentStatus paymentStatus = request.get("paymentStatus");
            OrderDTO order = orderService.updatePaymentStatus(id, paymentStatus);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update payment status: " + e.getMessage(), e);
        }
    }

    /**
     * Update tracking number
     */
    @PatchMapping("/{id}/tracking")
    @Operation(summary = "Update tracking number", description = "Update order tracking number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tracking number updated successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> updateTrackingNumber(@PathVariable String id, @RequestBody Map<String, String> request) {
        try {
            String trackingNumber = request.get("trackingNumber");
            OrderDTO order = orderService.updateTrackingNumber(id, trackingNumber);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update tracking number: " + e.getMessage(), e);
        }
    }

    /**
     * Cancel order
     */
    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel order", description = "Cancel an order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable String id, @RequestBody Map<String, String> request) {
        try {
            String cancellationReason = request.get("cancellationReason");
            OrderDTO order = orderService.cancelOrder(id, cancellationReason);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to cancel order: " + e.getMessage(), e);
        }
    }

    /**
     * Ship order
     */
    @PatchMapping("/{id}/ship")
    @Operation(summary = "Ship order", description = "Ship an order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order shipped successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> shipOrder(@PathVariable String id, @RequestBody Map<String, String> request) {
        try {
            String trackingNumber = request.get("trackingNumber");
            OrderDTO order = orderService.shipOrder(id, trackingNumber);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to ship order: " + e.getMessage(), e);
        }
    }

    /**
     * Deliver order
     */
    @PatchMapping("/{id}/deliver")
    @Operation(summary = "Deliver order", description = "Mark order as delivered")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order delivered successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDTO> deliverOrder(@PathVariable String id) {
        try {
            OrderDTO order = orderService.deliverOrder(id);
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to deliver order: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count by user
     */
    @GetMapping("/count/user/{userId}")
    @Operation(summary = "Get order count by user", description = "Get the total number of orders by user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountByUser(@PathVariable String userId) {
        try {
            long count = orderService.getOrderCountByUser(userId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count by user: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count by seller
     */
    @GetMapping("/count/seller/{sellerId}")
    @Operation(summary = "Get order count by seller", description = "Get the total number of orders by seller")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountBySeller(@PathVariable String sellerId) {
        try {
            long count = orderService.getOrderCountBySeller(sellerId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count by seller: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count by status
     */
    @GetMapping("/count/status/{status}")
    @Operation(summary = "Get order count by status", description = "Get the total number of orders by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountByStatus(@PathVariable Order.OrderStatus status) {
        try {
            long count = orderService.getOrderCountByStatus(status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count by payment status
     */
    @GetMapping("/count/payment-status/{paymentStatus}")
    @Operation(summary = "Get order count by payment status", description = "Get the total number of orders by payment status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountByPaymentStatus(@PathVariable Order.PaymentStatus paymentStatus) {
        try {
            long count = orderService.getOrderCountByPaymentStatus(paymentStatus);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count by payment status: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count by user and status
     */
    @GetMapping("/count/user/{userId}/status/{status}")
    @Operation(summary = "Get order count by user and status", description = "Get the total number of orders by user and status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountByUserAndStatus(@PathVariable String userId, @PathVariable Order.OrderStatus status) {
        try {
            long count = orderService.getOrderCountByUserAndStatus(userId, status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count by user and status: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count by seller and status
     */
    @GetMapping("/count/seller/{sellerId}/status/{status}")
    @Operation(summary = "Get order count by seller and status", description = "Get the total number of orders by seller and status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountBySellerAndStatus(@PathVariable String sellerId, @PathVariable Order.OrderStatus status) {
        try {
            long count = orderService.getOrderCountBySellerAndStatus(sellerId, status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count by seller and status: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count by user and payment status
     */
    @GetMapping("/count/user/{userId}/payment-status/{paymentStatus}")
    @Operation(summary = "Get order count by user and payment status", description = "Get the total number of orders by user and payment status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountByUserAndPaymentStatus(@PathVariable String userId, @PathVariable Order.PaymentStatus paymentStatus) {
        try {
            long count = orderService.getOrderCountByUserAndPaymentStatus(userId, paymentStatus);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count by user and payment status: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count by seller and payment status
     */
    @GetMapping("/count/seller/{sellerId}/payment-status/{paymentStatus}")
    @Operation(summary = "Get order count by seller and payment status", description = "Get the total number of orders by seller and payment status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountBySellerAndPaymentStatus(@PathVariable String sellerId, @PathVariable Order.PaymentStatus paymentStatus) {
        try {
            long count = orderService.getOrderCountBySellerAndPaymentStatus(sellerId, paymentStatus);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count by seller and payment status: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count by amount range
     */
    @GetMapping("/count/amount-range")
    @Operation(summary = "Get order count by amount range", description = "Get the total number of orders by amount range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountByAmountRange(@RequestParam BigDecimal minAmount, @RequestParam BigDecimal maxAmount) {
        try {
            long count = orderService.getOrderCountByAmountRange(minAmount, maxAmount);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count by amount range: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count created after date
     */
    @GetMapping("/count/created-after")
    @Operation(summary = "Get order count created after date", description = "Get the total number of orders created after a specific date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountCreatedAfter(@RequestParam LocalDateTime date) {
        try {
            long count = orderService.getOrderCountCreatedAfter(date);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count created after date: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count created before date
     */
    @GetMapping("/count/created-before")
    @Operation(summary = "Get order count created before date", description = "Get the total number of orders created before a specific date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountCreatedBefore(@RequestParam LocalDateTime date) {
        try {
            long count = orderService.getOrderCountCreatedBefore(date);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count created before date: " + e.getMessage(), e);
        }
    }

    /**
     * Get order count created between dates
     */
    @GetMapping("/count/created-between")
    @Operation(summary = "Get order count created between dates", description = "Get the total number of orders created between two dates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order count retrieved successfully")
    })
    public ResponseEntity<Long> getOrderCountCreatedBetween(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        try {
            long count = orderService.getOrderCountCreatedBetween(startDate, endDate);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order count created between dates: " + e.getMessage(), e);
        }
    }

    /**
     * Check if order number exists
     */
    @GetMapping("/exists/order-number/{orderNumber}")
    @Operation(summary = "Check if order number exists", description = "Check if an order number is already in use")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order number existence checked successfully")
    })
    public ResponseEntity<Boolean> orderNumberExists(@PathVariable String orderNumber) {
        try {
            boolean exists = orderService.orderNumberExists(orderNumber);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check order number existence: " + e.getMessage(), e);
        }
    }

    /**
     * Check if tracking number exists
     */
    @GetMapping("/exists/tracking-number/{trackingNumber}")
    @Operation(summary = "Check if tracking number exists", description = "Check if a tracking number is already in use")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tracking number existence checked successfully")
    })
    public ResponseEntity<Boolean> trackingNumberExists(@PathVariable String trackingNumber) {
        try {
            boolean exists = orderService.trackingNumberExists(trackingNumber);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check tracking number existence: " + e.getMessage(), e);
        }
    }

    /**
     * Check if payment reference exists
     */
    @GetMapping("/exists/payment-reference/{paymentReference}")
    @Operation(summary = "Check if payment reference exists", description = "Check if a payment reference is already in use")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment reference existence checked successfully")
    })
    public ResponseEntity<Boolean> paymentReferenceExists(@PathVariable String paymentReference) {
        try {
            boolean exists = orderService.paymentReferenceExists(paymentReference);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check payment reference existence: " + e.getMessage(), e);
        }
    }
}
