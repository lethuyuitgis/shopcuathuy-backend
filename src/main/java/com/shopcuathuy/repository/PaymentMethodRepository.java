package com.shopcuathuy.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shopcuathuy.entity.PaymentMethod;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Payment Method Repository
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    /**
     * Find payment method by code
     */
    Optional<PaymentMethod> findByCode(String code);

    /**
     * Find active payment methods
     */
    List<PaymentMethod> findByIsActiveTrueOrderBySortOrderAsc();

    /**
     * Find online payment methods
     */
    List<PaymentMethod> findByIsOnlineTrueAndIsActiveTrueOrderBySortOrderAsc();

    /**
     * Find payment methods by active status
     */
    Page<PaymentMethod> findByIsActive(Boolean isActive, Pageable pageable);

    /**
     * Find payment methods by online status
     */
    Page<PaymentMethod> findByIsOnline(Boolean isOnline, Pageable pageable);

    /**
     * Find payment methods by active and online status
     */
    List<PaymentMethod> findByIsActiveAndIsOnline(Boolean isActive, Boolean isOnline);

    /**
     * Find payment methods by name containing
     */
    @Query("SELECT pm FROM PaymentMethod pm WHERE LOWER(pm.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<PaymentMethod> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    /**
     * Find payment methods by code containing
     */
    @Query("SELECT pm FROM PaymentMethod pm WHERE LOWER(pm.code) LIKE LOWER(CONCAT('%', :code, '%'))")
    Page<PaymentMethod> findByCodeContainingIgnoreCase(@Param("code") String code, Pageable pageable);

    /**
     * Count active payment methods
     */
    long countByIsActiveTrue();

    /**
     * Count online payment methods
     */
    long countByIsOnlineTrueAndIsActiveTrue();
}
