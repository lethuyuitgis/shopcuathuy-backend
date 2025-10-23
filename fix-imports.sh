#!/bin/bash

# Script to fix missing imports in Java files

echo "Fixing imports for repository files..."

# Fix CategoryRepository
sed -i '' 's/import org.springframework.data.domain.Pageable;/import org.springframework.data.domain.Pageable;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.data.jpa.repository.Query;\nimport org.springframework.data.repository.query.Param;\nimport org.springframework.stereotype.Repository;/' src/main/java/com/shopcuathuy/repository/CategoryRepository.java

# Fix OrderRepository  
sed -i '' 's/import org.springframework.data.domain.Pageable;/import org.springframework.data.domain.Pageable;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.data.jpa.repository.Query;\nimport org.springframework.data.repository.query.Param;\nimport org.springframework.stereotype.Repository;/' src/main/java/com/shopcuathuy/repository/OrderRepository.java

# Fix AnalyticsEventRepository
sed -i '' 's/import org.springframework.data.domain.Pageable;/import org.springframework.data.domain.Pageable;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.data.jpa.repository.Query;\nimport org.springframework.data.repository.query.Param;\nimport org.springframework.stereotype.Repository;/' src/main/java/com/shopcuathuy/repository/AnalyticsEventRepository.java

# Fix WishlistRepository
sed -i '' 's/import org.springframework.data.domain.Pageable;/import org.springframework.data.domain.Pageable;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.data.jpa.repository.Query;\nimport org.springframework.data.repository.query.Param;\nimport org.springframework.stereotype.Repository;/' src/main/java/com/shopcuathuy/repository/WishlistRepository.java

# Fix ProductReviewRepository
sed -i '' 's/import org.springframework.data.domain.Pageable;/import org.springframework.data.domain.Pageable;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.data.jpa.repository.Query;\nimport org.springframework.data.repository.query.Param;\nimport org.springframework.stereotype.Repository;/' src/main/java/com/shopcuathuy/repository/ProductReviewRepository.java

# Fix ShippingRepository
sed -i '' 's/import org.springframework.data.domain.Pageable;/import org.springframework.data.domain.Pageable;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.data.jpa.repository.Query;\nimport org.springframework.data.repository.query.Param;\nimport org.springframework.stereotype.Repository;/' src/main/java/com/shopcuathuy/repository/ShippingRepository.java

echo "Fixing imports for controller files..."

# Fix ProductController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/ProductController.java

# Fix PaymentMethodController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport lombok.RequiredArgsConstructor;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/PaymentMethodController.java

# Fix WishlistController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/WishlistController.java

# Fix SellerController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/SellerController.java

# Fix PaymentController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport lombok.RequiredArgsConstructor;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/PaymentController.java

echo "Fixing imports for service files..."

# Fix AnalyticsService
sed -i '' 's/import org.springframework.transaction.annotation.Transactional;/import org.springframework.transaction.annotation.Transactional;\nimport org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/AnalyticsService.java

# Fix WishlistService
sed -i '' 's/import org.springframework.transaction.annotation.Transactional;/import org.springframework.transaction.annotation.Transactional;\nimport org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/WishlistService.java

# Fix InventoryService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/InventoryService.java

# Fix VNPayService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;\nimport lombok.extern.slf4j.Slf4j;\nimport lombok.RequiredArgsConstructor;/' src/main/java/com/shopcuathuy/service/VNPayService.java

# Fix PaymentService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;\nimport lombok.extern.slf4j.Slf4j;\nimport lombok.RequiredArgsConstructor;/' src/main/java/com/shopcuathuy/service/PaymentService.java

# Fix SellerService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/SellerService.java

echo "Fixing imports for mapper files..."

# Fix CategoryMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/CategoryMapper.java

# Fix PaymentMethodMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/PaymentMethodMapper.java

# Fix AnalyticsEventMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/AnalyticsEventMapper.java

# Fix WishlistMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/WishlistMapper.java

# Fix ShippingMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/ShippingMapper.java

# Fix SellerMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/SellerMapper.java

# Fix CartMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/CartMapper.java

echo "Fixing imports for DTO files..."

# Fix VNPayPaymentDTO
sed -i '' 's/import java.time.LocalDateTime;/import java.time.LocalDateTime;\nimport lombok.Data;\nimport lombok.Builder;\nimport lombok.NoArgsConstructor;\nimport lombok.AllArgsConstructor;/' src/main/java/com/shopcuathuy/dto/VNPayPaymentDTO.java

# Fix PaymentMethodDTO
sed -i '' 's/import java.time.LocalDateTime;/import java.time.LocalDateTime;\nimport lombok.Data;\nimport lombok.Builder;\nimport lombok.NoArgsConstructor;\nimport lombok.AllArgsConstructor;/' src/main/java/com/shopcuathuy/dto/PaymentMethodDTO.java

# Fix CreatePaymentDTO
sed -i '' 's/import java.time.LocalDateTime;/import java.time.LocalDateTime;\nimport lombok.Data;\nimport lombok.Builder;\nimport lombok.NoArgsConstructor;\nimport lombok.AllArgsConstructor;/' src/main/java/com/shopcuathuy/dto/CreatePaymentDTO.java

# Fix PaymentDTO
sed -i '' 's/import java.time.LocalDateTime;/import java.time.LocalDateTime;\nimport lombok.Data;\nimport lombok.Builder;\nimport lombok.NoArgsConstructor;/' src/main/java/com/shopcuathuy/dto/PaymentDTO.java

echo "All imports fixed!"
