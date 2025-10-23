package com.shopcuathuy.controller;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopcuathuy.dto.PaymentMethodDTO;
import com.shopcuathuy.entity.PaymentMethod;
import com.shopcuathuy.mapper.PaymentMethodMapper;
import com.shopcuathuy.repository.PaymentMethodRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Payment Method Controller
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
@Tag(name = "Payment Method", description = "Payment method management APIs")
public class PaymentMethodController {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    /**
     * Get all payment methods
     */
    @GetMapping
    @Operation(summary = "Get all payment methods", description = "Get all payment methods with pagination")
    public ResponseEntity<Page<PaymentMethodDTO>> getAllPaymentMethods(Pageable pageable) {
        Page<PaymentMethod> paymentMethods = paymentMethodRepository.findAll(pageable);
        Page<PaymentMethodDTO> paymentMethodDTOs = paymentMethods.map(paymentMethodMapper::toDTO);
        return ResponseEntity.ok(paymentMethodDTOs);
    }

    /**
     * Get active payment methods
     */
    @GetMapping("/active")
    @Operation(summary = "Get active payment methods", description = "Get all active payment methods")
    public ResponseEntity<List<PaymentMethodDTO>> getActivePaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findByIsActiveTrueOrderBySortOrderAsc();
        List<PaymentMethodDTO> paymentMethodDTOs = paymentMethodMapper.toDTOList(paymentMethods);
        return ResponseEntity.ok(paymentMethodDTOs);
    }

    /**
     * Get online payment methods
     */
    @GetMapping("/online")
    @Operation(summary = "Get online payment methods", description = "Get all online payment methods")
    public ResponseEntity<List<PaymentMethodDTO>> getOnlinePaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findByIsOnlineTrueAndIsActiveTrueOrderBySortOrderAsc();
        List<PaymentMethodDTO> paymentMethodDTOs = paymentMethodMapper.toDTOList(paymentMethods);
        return ResponseEntity.ok(paymentMethodDTOs);
    }

    /**
     * Get payment method by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get payment method by ID", description = "Get payment method details by ID")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethodById(
            @Parameter(description = "Payment method ID") @PathVariable Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDTO(paymentMethod);
        return ResponseEntity.ok(paymentMethodDTO);
    }

    /**
     * Get payment method by code
     */
    @GetMapping("/code/{code}")
    @Operation(summary = "Get payment method by code", description = "Get payment method by code")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethodByCode(
            @Parameter(description = "Payment method code") @PathVariable String code) {
        PaymentMethod paymentMethod = paymentMethodRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
        PaymentMethodDTO paymentMethodDTO = paymentMethodMapper.toDTO(paymentMethod);
        return ResponseEntity.ok(paymentMethodDTO);
    }
}
