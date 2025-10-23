package com.shopcuathuy.repository;

import com.shopcuathuy.entity.Payment;
import com.shopcuathuy.entity.Payment.PaymentStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Payment Repository
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Find payments by order ID
     */
    List<Payment> findByOrderId(Long orderId);

    /**
     * Find payments by user ID and created date range
     */
    @Query("SELECT p FROM Payment p WHERE p.order.user.id = :userId AND p.createdAt BETWEEN :startDate AND :endDate")
    List<Payment> findByUserIdAndCreatedAtBetween(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Find payment by transaction ID
     */
    Optional<Payment> findByTransactionId(String transactionId);

    /**
     * Find payment by gateway transaction ID
     */
    Optional<Payment> findByGatewayTransactionId(String gatewayTransactionId);

    /**
     * Find payments by status
     */
    List<Payment> findByStatus(PaymentStatus status);

    /**
     * Find payments by order ID and status
     */
    List<Payment> findByOrderIdAndStatus(Long orderId, PaymentStatus status);

    /**
     * Find payments by payment method ID
     */
    Page<Payment> findByPaymentMethodId(Long paymentMethodId, Pageable pageable);

    /**
     * Find payments by status with pagination
     */
    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);

    /**
     * Find expired payments
     */
    @Query("SELECT p FROM Payment p WHERE p.status = :status AND p.expiredAt < :now")
    List<Payment> findExpiredPayments(@Param("status") PaymentStatus status, @Param("now") LocalDateTime now);

    /**
     * Find payments by date range
     */
    @Query("SELECT p FROM Payment p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    Page<Payment> findPaymentsByDateRange(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate, 
                                         Pageable pageable);

    /**
     * Find payments by order ID with pagination
     */
    Page<Payment> findByOrderId(Long orderId, Pageable pageable);

    /**
     * Count payments by status
     */
    long countByStatus(PaymentStatus status);

    /**
     * Find successful payments by date range
     */
    @Query("SELECT p FROM Payment p WHERE p.status = 'SUCCESS' AND p.paidAt BETWEEN :startDate AND :endDate")
    List<Payment> findSuccessfulPaymentsByDateRange(@Param("startDate") LocalDateTime startDate, 
                                                   @Param("endDate") LocalDateTime endDate);
}
