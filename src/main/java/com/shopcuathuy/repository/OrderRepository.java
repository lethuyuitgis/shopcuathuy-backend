package com.shopcuathuy.repository;

import com.shopcuathuy.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Repository interface for Order entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    /**
     * Find order by order number
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * Find orders by user
     */
    List<Order> findByUserId(String userId);

    /**
     * Find orders by user with pagination
     */
    Page<Order> findByUserId(String userId, Pageable pageable);

    /**
     * Find orders by seller
     */
    List<Order> findBySellerId(String sellerId);

    /**
     * Find orders by seller with pagination
     */
    Page<Order> findBySellerId(String sellerId, Pageable pageable);

    /**
     * Find orders by user and seller
     */
    List<Order> findByUserIdAndSellerId(String userId, String sellerId);

    /**
     * Find orders by user and seller with pagination
     */
    Page<Order> findByUserIdAndSellerId(String userId, String sellerId, Pageable pageable);

    /**
     * Find orders by status
     */
    List<Order> findByStatus(Order.OrderStatus status);

    /**
     * Find orders by status with pagination
     */
    Page<Order> findByStatus(Order.OrderStatus status, Pageable pageable);

    /**
     * Find orders by payment status
     */
    List<Order> findByPaymentStatus(Order.PaymentStatus paymentStatus);

    /**
     * Find orders by payment status with pagination
     */
    Page<Order> findByPaymentStatus(Order.PaymentStatus paymentStatus, Pageable pageable);

    /**
     * Find orders by payment method
     */
    List<Order> findByPaymentMethod(Order.PaymentMethod paymentMethod);

    /**
     * Find orders by payment method with pagination
     */
    Page<Order> findByPaymentMethod(Order.PaymentMethod paymentMethod, Pageable pageable);

    /**
     * Find orders by user and status
     */
    List<Order> findByUserIdAndStatus(String userId, Order.OrderStatus status);

    /**
     * Find orders by user and status with pagination
     */
    Page<Order> findByUserIdAndStatus(String userId, Order.OrderStatus status, Pageable pageable);

    /**
     * Find orders by seller and status
     */
    List<Order> findBySellerIdAndStatus(String sellerId, Order.OrderStatus status);

    /**
     * Find orders by seller and status with pagination
     */
    Page<Order> findBySellerIdAndStatus(String sellerId, Order.OrderStatus status, Pageable pageable);

    /**
     * Find orders by user and payment status
     */
    List<Order> findByUserIdAndPaymentStatus(String userId, Order.PaymentStatus paymentStatus);

    /**
     * Find orders by user and payment status with pagination
     */
    Page<Order> findByUserIdAndPaymentStatus(String userId, Order.PaymentStatus paymentStatus, Pageable pageable);

    /**
     * Find orders by seller and payment status
     */
    List<Order> findBySellerIdAndPaymentStatus(String sellerId, Order.PaymentStatus paymentStatus);

    /**
     * Find orders by seller and payment status with pagination
     */
    Page<Order> findBySellerIdAndPaymentStatus(String sellerId, Order.PaymentStatus paymentStatus, Pageable pageable);

    /**
     * Find orders by total amount range
     */
    List<Order> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    /**
     * Find orders by total amount range with pagination
     */
    Page<Order> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);

    /**
     * Find orders by total amount greater than
     */
    List<Order> findByTotalAmountGreaterThan(BigDecimal amount);

    /**
     * Find orders by total amount greater than with pagination
     */
    Page<Order> findByTotalAmountGreaterThan(BigDecimal amount, Pageable pageable);

    /**
     * Find orders by total amount less than
     */
    List<Order> findByTotalAmountLessThan(BigDecimal amount);

    /**
     * Find orders by total amount less than with pagination
     */
    Page<Order> findByTotalAmountLessThan(BigDecimal amount, Pageable pageable);

    /**
     * Find orders by subtotal range
     */
    List<Order> findBySubtotalBetween(BigDecimal minSubtotal, BigDecimal maxSubtotal);

    /**
     * Find orders by subtotal range with pagination
     */
    Page<Order> findBySubtotalBetween(BigDecimal minSubtotal, BigDecimal maxSubtotal, Pageable pageable);

    /**
     * Find orders by tax amount range
     */
    List<Order> findByTaxAmountBetween(BigDecimal minTax, BigDecimal maxTax);

    /**
     * Find orders by tax amount range with pagination
     */
    Page<Order> findByTaxAmountBetween(BigDecimal minTax, BigDecimal maxTax, Pageable pageable);

    /**
     * Find orders by shipping cost range
     */
    List<Order> findByShippingCostBetween(BigDecimal minShipping, BigDecimal maxShipping);

    /**
     * Find orders by shipping cost range with pagination
     */
    Page<Order> findByShippingCostBetween(BigDecimal minShipping, BigDecimal maxShipping, Pageable pageable);

    /**
     * Find orders by discount amount range
     */
    List<Order> findByDiscountAmountBetween(BigDecimal minDiscount, BigDecimal maxDiscount);

    /**
     * Find orders by discount amount range with pagination
     */
    Page<Order> findByDiscountAmountBetween(BigDecimal minDiscount, BigDecimal maxDiscount, Pageable pageable);

    /**
     * Find orders by shipping method
     */
    List<Order> findByShippingMethod(String shippingMethod);

    /**
     * Find orders by shipping method with pagination
     */
    Page<Order> findByShippingMethod(String shippingMethod, Pageable pageable);

    /**
     * Find orders by tracking number
     */
    Optional<Order> findByTrackingNumber(String trackingNumber);

    /**
     * Find orders by payment reference
     */
    List<Order> findByPaymentReference(String paymentReference);

    /**
     * Find orders by payment reference with pagination
     */
    Page<Order> findByPaymentReference(String paymentReference, Pageable pageable);

    /**
     * Find orders created after date
     */
    List<Order> findByCreatedAtAfter(LocalDateTime date);

    /**
     * Find orders created after date with pagination
     */
    Page<Order> findByCreatedAtAfter(LocalDateTime date, Pageable pageable);

    /**
     * Find orders created before date
     */
    List<Order> findByCreatedAtBefore(LocalDateTime date);

    /**
     * Find orders created before date with pagination
     */
    Page<Order> findByCreatedAtBefore(LocalDateTime date, Pageable pageable);

    /**
     * Find orders created between dates
     */
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find orders created between dates with pagination
     */
    Page<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find orders shipped after date
     */
    List<Order> findByShippedAtAfter(LocalDateTime date);

    /**
     * Find orders shipped after date with pagination
     */
    Page<Order> findByShippedAtAfter(LocalDateTime date, Pageable pageable);

    /**
     * Find orders shipped before date
     */
    List<Order> findByShippedAtBefore(LocalDateTime date);

    /**
     * Find orders shipped before date with pagination
     */
    Page<Order> findByShippedAtBefore(LocalDateTime date, Pageable pageable);

    /**
     * Find orders shipped between dates
     */
    List<Order> findByShippedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find orders shipped between dates with pagination
     */
    Page<Order> findByShippedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find orders delivered after date
     */
    List<Order> findByDeliveredAtAfter(LocalDateTime date);

    /**
     * Find orders delivered after date with pagination
     */
    Page<Order> findByDeliveredAtAfter(LocalDateTime date, Pageable pageable);

    /**
     * Find orders delivered before date
     */
    List<Order> findByDeliveredAtBefore(LocalDateTime date);

    /**
     * Find orders delivered before date with pagination
     */
    Page<Order> findByDeliveredAtBefore(LocalDateTime date, Pageable pageable);

    /**
     * Find orders delivered between dates
     */
    List<Order> findByDeliveredAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find orders delivered between dates with pagination
     */
    Page<Order> findByDeliveredAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find orders cancelled after date
     */
    List<Order> findByCancelledAtAfter(LocalDateTime date);

    /**
     * Find orders cancelled after date with pagination
     */
    Page<Order> findByCancelledAtAfter(LocalDateTime date, Pageable pageable);

    /**
     * Find orders cancelled before date
     */
    List<Order> findByCancelledAtBefore(LocalDateTime date);

    /**
     * Find orders cancelled before date with pagination
     */
    Page<Order> findByCancelledAtBefore(LocalDateTime date, Pageable pageable);

    /**
     * Find orders cancelled between dates
     */
    List<Order> findByCancelledAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find orders cancelled between dates with pagination
     */
    Page<Order> findByCancelledAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find orders by multiple criteria
     */
    @Query("SELECT o FROM Order o WHERE " +
           "(:userId IS NULL OR o.userId = :userId) AND " +
           "(:sellerId IS NULL OR o.sellerId = :sellerId) AND " +
           "(:status IS NULL OR o.status = :status) AND " +
           "(:paymentStatus IS NULL OR o.paymentStatus = :paymentStatus) AND " +
           "(:paymentMethod IS NULL OR o.paymentMethod = :paymentMethod) AND " +
           "(:minAmount IS NULL OR o.totalAmount >= :minAmount) AND " +
           "(:maxAmount IS NULL OR o.totalAmount <= :maxAmount) AND " +
           "(:startDate IS NULL OR o.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR o.createdAt <= :endDate)")
    Page<Order> findByMultipleCriteria(@Param("userId") String userId,
                                      @Param("sellerId") String sellerId,
                                      @Param("status") Order.OrderStatus status,
                                      @Param("paymentStatus") Order.PaymentStatus paymentStatus,
                                      @Param("paymentMethod") Order.PaymentMethod paymentMethod,
                                      @Param("minAmount") BigDecimal minAmount,
                                      @Param("maxAmount") BigDecimal maxAmount,
                                      @Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate,
                                      Pageable pageable);

    /**
     * Find orders by text search
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.orderNumber LIKE CONCAT('%', :search, '%') OR " +
           "o.paymentReference LIKE CONCAT('%', :search, '%') OR " +
           "o.trackingNumber LIKE CONCAT('%', :search, '%') OR " +
           "o.shippingMethod LIKE CONCAT('%', :search, '%')")
    List<Order> findByTextSearch(@Param("search") String search);

    /**
     * Find orders by text search with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.orderNumber LIKE CONCAT('%', :search, '%') OR " +
           "o.paymentReference LIKE CONCAT('%', :search, '%') OR " +
           "o.trackingNumber LIKE CONCAT('%', :search, '%') OR " +
           "o.shippingMethod LIKE CONCAT('%', :search, '%')")
    Page<Order> findByTextSearch(@Param("search") String search, Pageable pageable);

    /**
     * Find orders by multiple statuses
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.status IN :statuses")
    List<Order> findByStatusIn(@Param("statuses") List<Order.OrderStatus> statuses);

    /**
     * Find orders by multiple statuses with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.status IN :statuses")
    Page<Order> findByStatusIn(@Param("statuses") List<Order.OrderStatus> statuses, Pageable pageable);

    /**
     * Find orders by multiple payment statuses
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.paymentStatus IN :paymentStatuses")
    List<Order> findByPaymentStatusIn(@Param("paymentStatuses") List<Order.PaymentStatus> paymentStatuses);

    /**
     * Find orders by multiple payment statuses with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.paymentStatus IN :paymentStatuses")
    Page<Order> findByPaymentStatusIn(@Param("paymentStatuses") List<Order.PaymentStatus> paymentStatuses, Pageable pageable);

    /**
     * Find orders by multiple payment methods
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.paymentMethod IN :paymentMethods")
    List<Order> findByPaymentMethodIn(@Param("paymentMethods") List<Order.PaymentMethod> paymentMethods);

    /**
     * Find orders by multiple payment methods with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.paymentMethod IN :paymentMethods")
    Page<Order> findByPaymentMethodIn(@Param("paymentMethods") List<Order.PaymentMethod> paymentMethods, Pageable pageable);

    /**
     * Find orders by multiple users
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.userId IN :userIds")
    List<Order> findByUserIdIn(@Param("userIds") List<String> userIds);

    /**
     * Find orders by multiple users with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.userId IN :userIds")
    Page<Order> findByUserIdIn(@Param("userIds") List<String> userIds, Pageable pageable);

    /**
     * Find orders by multiple sellers
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.sellerId IN :sellerIds")
    List<Order> findBySellerIdIn(@Param("sellerIds") List<String> sellerIds);

    /**
     * Find orders by multiple sellers with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.sellerId IN :sellerIds")
    Page<Order> findBySellerIdIn(@Param("sellerIds") List<String> sellerIds, Pageable pageable);

    /**
     * Find recent orders
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.createdAt >= :date")
    List<Order> findRecentOrders(@Param("date") LocalDateTime date);

    /**
     * Find recent orders with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.createdAt >= :date")
    Page<Order> findRecentOrders(@Param("date") LocalDateTime date, Pageable pageable);

    /**
     * Find orders by date range
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByDateRange(@Param("startDate") LocalDateTime startDate,
                               @Param("endDate") LocalDateTime endDate);

    /**
     * Find orders by date range with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.createdAt BETWEEN :startDate AND :endDate")
    Page<Order> findByDateRange(@Param("startDate") LocalDateTime startDate,
                               @Param("endDate") LocalDateTime endDate,
                               Pageable pageable);

    /**
     * Find orders by shipped date range
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.shippedAt BETWEEN :startDate AND :endDate")
    List<Order> findByShippedDateRange(@Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

    /**
     * Find orders by shipped date range with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.shippedAt BETWEEN :startDate AND :endDate")
    Page<Order> findByShippedDateRange(@Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate,
                                      Pageable pageable);

    /**
     * Find orders by delivered date range
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.deliveredAt BETWEEN :startDate AND :endDate")
    List<Order> findByDeliveredDateRange(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);

    /**
     * Find orders by delivered date range with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.deliveredAt BETWEEN :startDate AND :endDate")
    Page<Order> findByDeliveredDateRange(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate,
                                        Pageable pageable);

    /**
     * Find orders by cancelled date range
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.cancelledAt BETWEEN :startDate AND :endDate")
    List<Order> findByCancelledDateRange(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);

    /**
     * Find orders by cancelled date range with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.cancelledAt BETWEEN :startDate AND :endDate")
    Page<Order> findByCancelledDateRange(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate,
                                        Pageable pageable);

    /**
     * Find orders by amount range
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.totalAmount BETWEEN :minAmount AND :maxAmount")
    List<Order> findByAmountRange(@Param("minAmount") BigDecimal minAmount,
                                 @Param("maxAmount") BigDecimal maxAmount);

    /**
     * Find orders by amount range with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.totalAmount BETWEEN :minAmount AND :maxAmount")
    Page<Order> findByAmountRange(@Param("minAmount") BigDecimal minAmount,
                                 @Param("maxAmount") BigDecimal maxAmount,
                                 Pageable pageable);

    /**
     * Find orders by subtotal range
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.subtotal BETWEEN :minSubtotal AND :maxSubtotal")
    List<Order> findBySubtotalRange(@Param("minSubtotal") BigDecimal minSubtotal,
                                   @Param("maxSubtotal") BigDecimal maxSubtotal);

    /**
     * Find orders by subtotal range with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.subtotal BETWEEN :minSubtotal AND :maxSubtotal")
    Page<Order> findBySubtotalRange(@Param("minSubtotal") BigDecimal minSubtotal,
                                   @Param("maxSubtotal") BigDecimal maxSubtotal,
                                   Pageable pageable);

    /**
     * Find orders by tax amount range
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.taxAmount BETWEEN :minTax AND :maxTax")
    List<Order> findByTaxAmountRange(@Param("minTax") BigDecimal minTax,
                                    @Param("maxTax") BigDecimal maxTax);

    /**
     * Find orders by tax amount range with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.taxAmount BETWEEN :minTax AND :maxTax")
    Page<Order> findByTaxAmountRange(@Param("minTax") BigDecimal minTax,
                                    @Param("maxTax") BigDecimal maxTax,
                                    Pageable pageable);

    /**
     * Find orders by shipping cost range
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.shippingCost BETWEEN :minShipping AND :maxShipping")
    List<Order> findByShippingCostRange(@Param("minShipping") BigDecimal minShipping,
                                        @Param("maxShipping") BigDecimal maxShipping);

    /**
     * Find orders by shipping cost range with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.shippingCost BETWEEN :minShipping AND :maxShipping")
    Page<Order> findByShippingCostRange(@Param("minShipping") BigDecimal minShipping,
                                        @Param("maxShipping") BigDecimal maxShipping,
                                        Pageable pageable);

    /**
     * Find orders by discount amount range
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.discountAmount BETWEEN :minDiscount AND :maxDiscount")
    List<Order> findByDiscountAmountRange(@Param("minDiscount") BigDecimal minDiscount,
                                          @Param("maxDiscount") BigDecimal maxDiscount);

    /**
     * Find orders by discount amount range with pagination
     */
    @Query("SELECT o FROM Order o WHERE " +
           "o.discountAmount BETWEEN :minDiscount AND :maxDiscount")
    Page<Order> findByDiscountAmountRange(@Param("minDiscount") BigDecimal minDiscount,
                                          @Param("maxDiscount") BigDecimal maxDiscount,
                                          Pageable pageable);

    /**
     * Find orders by shipping method containing
     */
    List<Order> findByShippingMethodContaining(String shippingMethod);

    /**
     * Find orders by shipping method containing with pagination
     */
    Page<Order> findByShippingMethodContaining(String shippingMethod, Pageable pageable);

    /**
     * Find orders by tracking number containing
     */
    List<Order> findByTrackingNumberContaining(String trackingNumber);

    /**
     * Find orders by tracking number containing with pagination
     */
    Page<Order> findByTrackingNumberContaining(String trackingNumber, Pageable pageable);

    /**
     * Find orders by payment reference containing
     */
    List<Order> findByPaymentReferenceContaining(String paymentReference);

    /**
     * Find orders by payment reference containing with pagination
     */
    Page<Order> findByPaymentReferenceContaining(String paymentReference, Pageable pageable);

    /**
     * Find orders by notes containing
     */
    List<Order> findByNotesContaining(String notes);

    /**
     * Find orders by notes containing with pagination
     */
    Page<Order> findByNotesContaining(String notes, Pageable pageable);

    /**
     * Find orders by cancellation reason containing
     */
    List<Order> findByCancellationReasonContaining(String cancellationReason);

    /**
     * Find orders by cancellation reason containing with pagination
     */
    Page<Order> findByCancellationReasonContaining(String cancellationReason, Pageable pageable);

    /**
     * Find orders by shipping address
     */
    List<Order> findByShippingAddressId(String shippingAddressId);

    /**
     * Find orders by shipping address with pagination
     */
    Page<Order> findByShippingAddressId(String shippingAddressId, Pageable pageable);

    /**
     * Find orders by billing address
     */
    List<Order> findByBillingAddressId(String billingAddressId);

    /**
     * Find orders by billing address with pagination
     */
    Page<Order> findByBillingAddressId(String billingAddressId, Pageable pageable);

    /**
     * Find orders by shipping address and billing address
     */
    List<Order> findByShippingAddressIdAndBillingAddressId(String shippingAddressId, String billingAddressId);

    /**
     * Find orders by shipping address and billing address with pagination
     */
    Page<Order> findByShippingAddressIdAndBillingAddressId(String shippingAddressId, String billingAddressId, Pageable pageable);

    /**
     * Count orders by user
     */
    long countByUserId(String userId);

    /**
     * Count orders by seller
     */
    long countBySellerId(String sellerId);

    /**
     * Count orders by status
     */
    long countByStatus(Order.OrderStatus status);

    /**
     * Count orders by payment status
     */
    long countByPaymentStatus(Order.PaymentStatus paymentStatus);

    /**
     * Count orders by payment method
     */
    long countByPaymentMethod(Order.PaymentMethod paymentMethod);

    /**
     * Count orders by user and status
     */
    long countByUserIdAndStatus(String userId, Order.OrderStatus status);

    /**
     * Count orders by seller and status
     */
    long countBySellerIdAndStatus(String sellerId, Order.OrderStatus status);

    /**
     * Count orders by user and payment status
     */
    long countByUserIdAndPaymentStatus(String userId, Order.PaymentStatus paymentStatus);

    /**
     * Count orders by seller and payment status
     */
    long countBySellerIdAndPaymentStatus(String sellerId, Order.PaymentStatus paymentStatus);

    /**
     * Count orders by total amount range
     */
    long countByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    /**
     * Count orders by subtotal range
     */
    long countBySubtotalBetween(BigDecimal minSubtotal, BigDecimal maxSubtotal);

    /**
     * Count orders by tax amount range
     */
    long countByTaxAmountBetween(BigDecimal minTax, BigDecimal maxTax);

    /**
     * Count orders by shipping cost range
     */
    long countByShippingCostBetween(BigDecimal minShipping, BigDecimal maxShipping);

    /**
     * Count orders by discount amount range
     */
    long countByDiscountAmountBetween(BigDecimal minDiscount, BigDecimal maxDiscount);

    /**
     * Count orders by shipping method
     */
    long countByShippingMethod(String shippingMethod);

    /**
     * Count orders by payment reference
     */
    long countByPaymentReference(String paymentReference);

    /**
     * Count orders created after date
     */
    long countByCreatedAtAfter(LocalDateTime date);

    /**
     * Count orders created before date
     */
    long countByCreatedAtBefore(LocalDateTime date);

    /**
     * Count orders created between dates
     */
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Count orders shipped after date
     */
    long countByShippedAtAfter(LocalDateTime date);

    /**
     * Count orders shipped before date
     */
    long countByShippedAtBefore(LocalDateTime date);

    /**
     * Count orders shipped between dates
     */
    long countByShippedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Count orders delivered after date
     */
    long countByDeliveredAtAfter(LocalDateTime date);

    /**
     * Count orders delivered before date
     */
    long countByDeliveredAtBefore(LocalDateTime date);

    /**
     * Count orders delivered between dates
     */
    long countByDeliveredAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Count orders cancelled after date
     */
    long countByCancelledAtAfter(LocalDateTime date);

    /**
     * Count orders cancelled before date
     */
    long countByCancelledAtBefore(LocalDateTime date);

    /**
     * Count orders cancelled between dates
     */
    long countByCancelledAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Check if order number exists
     */
    boolean existsByOrderNumber(String orderNumber);

    /**
     * Check if tracking number exists
     */
    boolean existsByTrackingNumber(String trackingNumber);

    /**
     * Check if payment reference exists
     */
    boolean existsByPaymentReference(String paymentReference);

    /**
     * Check if order number exists excluding order
     */
    boolean existsByOrderNumberAndIdNot(String orderNumber, String id);

    /**
     * Check if tracking number exists excluding order
     */
    boolean existsByTrackingNumberAndIdNot(String trackingNumber, String id);

    /**
     * Check if payment reference exists excluding order
     */
    boolean existsByPaymentReferenceAndIdNot(String paymentReference, String id);

    /**
     * Find orders by order number starting with
     */
    List<Order> findByOrderNumberStartingWith(String orderNumber);

    /**
     * Find orders by order number starting with with pagination
     */
    Page<Order> findByOrderNumberStartingWith(String orderNumber, Pageable pageable);

    /**
     * Find orders by order number ending with
     */
    List<Order> findByOrderNumberEndingWith(String orderNumber);

    /**
     * Find orders by order number ending with with pagination
     */
    Page<Order> findByOrderNumberEndingWith(String orderNumber, Pageable pageable);

    /**
     * Find orders by tracking number starting with
     */
    List<Order> findByTrackingNumberStartingWith(String trackingNumber);

    /**
     * Find orders by tracking number starting with with pagination
     */
    Page<Order> findByTrackingNumberStartingWith(String trackingNumber, Pageable pageable);

    /**
     * Find orders by tracking number ending with
     */
    List<Order> findByTrackingNumberEndingWith(String trackingNumber);

    /**
     * Find orders by tracking number ending with with pagination
     */
    Page<Order> findByTrackingNumberEndingWith(String trackingNumber, Pageable pageable);

    /**
     * Find orders by payment reference starting with
     */
    List<Order> findByPaymentReferenceStartingWith(String paymentReference);

    /**
     * Find orders by payment reference starting with with pagination
     */
    Page<Order> findByPaymentReferenceStartingWith(String paymentReference, Pageable pageable);

    /**
     * Find orders by payment reference ending with
     */
    List<Order> findByPaymentReferenceEndingWith(String paymentReference);

    /**
     * Find orders by payment reference ending with with pagination
     */
    Page<Order> findByPaymentReferenceEndingWith(String paymentReference, Pageable pageable);

    /**
     * Find orders by shipping method starting with
     */
    List<Order> findByShippingMethodStartingWith(String shippingMethod);

    /**
     * Find orders by shipping method starting with with pagination
     */
    Page<Order> findByShippingMethodStartingWith(String shippingMethod, Pageable pageable);

    /**
     * Find orders by shipping method ending with
     */
    List<Order> findByShippingMethodEndingWith(String shippingMethod);

    /**
     * Find orders by shipping method ending with with pagination
     */
    Page<Order> findByShippingMethodEndingWith(String shippingMethod, Pageable pageable);

    /**
     * Find orders by notes starting with
     */
    List<Order> findByNotesStartingWith(String notes);

    /**
     * Find orders by notes starting with with pagination
     */
    Page<Order> findByNotesStartingWith(String notes, Pageable pageable);

    /**
     * Find orders by notes ending with
     */
    List<Order> findByNotesEndingWith(String notes);

    /**
     * Find orders by notes ending with with pagination
     */
    Page<Order> findByNotesEndingWith(String notes, Pageable pageable);

    /**
     * Find orders by cancellation reason starting with
     */
    List<Order> findByCancellationReasonStartingWith(String cancellationReason);

    /**
     * Find orders by cancellation reason starting with with pagination
     */
    Page<Order> findByCancellationReasonStartingWith(String cancellationReason, Pageable pageable);

    /**
     * Find orders by cancellation reason ending with
     */
    List<Order> findByCancellationReasonEndingWith(String cancellationReason);

    /**
     * Find orders by cancellation reason ending with with pagination
     */
    Page<Order> findByCancellationReasonEndingWith(String cancellationReason, Pageable pageable);

    /**
     * Find orders by shipping address starting with
     */
    List<Order> findByShippingAddressIdStartingWith(String shippingAddressId);

    /**
     * Find orders by shipping address starting with with pagination
     */
    Page<Order> findByShippingAddressIdStartingWith(String shippingAddressId, Pageable pageable);

    /**
     * Find orders by shipping address ending with
     */
    List<Order> findByShippingAddressIdEndingWith(String shippingAddressId);

    /**
     * Find orders by shipping address ending with with pagination
     */
    Page<Order> findByShippingAddressIdEndingWith(String shippingAddressId, Pageable pageable);

    /**
     * Find orders by billing address starting with
     */
    List<Order> findByBillingAddressIdStartingWith(String billingAddressId);

    /**
     * Find orders by billing address starting with with pagination
     */
    Page<Order> findByBillingAddressIdStartingWith(String billingAddressId, Pageable pageable);

    /**
     * Find orders by billing address ending with
     */
    List<Order> findByBillingAddressIdEndingWith(String billingAddressId);

    /**
     * Find orders by billing address ending with with pagination
     */
    Page<Order> findByBillingAddressIdEndingWith(String billingAddressId, Pageable pageable);
}
