#!/bin/bash

echo "Fixing all missing imports..."

# Fix MapStruct enum annotation errors
echo "Fixing MapStruct enum annotation errors..."

# Fix CategoryMapper
sed -i '' 's/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/' src/main/java/com/shopcuathuy/mapper/CategoryMapper.java

# Fix PaymentMethodMapper
sed -i '' 's/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/' src/main/java/com/shopcuathuy/mapper/PaymentMethodMapper.java

# Fix AnalyticsEventMapper
sed -i '' 's/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/' src/main/java/com/shopcuathuy/mapper/AnalyticsEventMapper.java

# Fix WishlistMapper
sed -i '' 's/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/' src/main/java/com/shopcuathuy/mapper/WishlistMapper.java

# Fix ShippingMapper
sed -i '' 's/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/' src/main/java/com/shopcuathuy/mapper/ShippingMapper.java

# Fix SellerMapper
sed -i '' 's/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/' src/main/java/com/shopcuathuy/mapper/SellerMapper.java

# Fix CartMapper
sed -i '' 's/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE/' src/main/java/com/shopcuathuy/mapper/CartMapper.java

# Fix remaining repository imports
echo "Fixing repository imports..."

# Fix PaymentRepository
sed -i '' 's/import org.springframework.data.domain.Pageable;/import org.springframework.data.domain.Pageable;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.data.jpa.repository.Query;\nimport org.springframework.data.repository.query.Param;\nimport org.springframework.stereotype.Repository;/' src/main/java/com/shopcuathuy/repository/PaymentRepository.java

# Fix NotificationRepository
sed -i '' 's/import org.springframework.data.domain.Pageable;/import org.springframework.data.domain.Pageable;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.data.jpa.repository.Query;\nimport org.springframework.data.repository.query.Param;\nimport org.springframework.stereotype.Repository;/' src/main/java/com/shopcuathuy/repository/NotificationRepository.java

# Fix CouponUsageRepository
sed -i '' 's/import org.springframework.data.domain.Pageable;/import org.springframework.data.domain.Pageable;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.data.jpa.repository.Query;\nimport org.springframework.data.repository.query.Param;\nimport org.springframework.stereotype.Repository;/' src/main/java/com/shopcuathuy/repository/CouponUsageRepository.java

# Fix CartRepository
sed -i '' 's/import org.springframework.data.domain.Pageable;/import org.springframework.data.domain.Pageable;\nimport org.springframework.data.jpa.repository.JpaRepository;\nimport org.springframework.data.jpa.repository.Query;\nimport org.springframework.data.repository.query.Param;\nimport org.springframework.stereotype.Repository;/' src/main/java/com/shopcuathuy/repository/CartRepository.java

# Fix remaining controller imports
echo "Fixing controller imports..."

# Fix ProductController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.PostMapping;\nimport org.springframework.web.bind.annotation.GetMapping;\nimport org.springframework.web.bind.annotation.PathVariable;\nimport org.springframework.web.bind.annotation.RequestBody;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/ProductController.java

# Fix ShippingController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/ShippingController.java

# Fix NotificationController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/NotificationController.java

# Fix ProductReviewController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/ProductReviewController.java

# Fix OrderController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/OrderController.java

# Fix PaymentWebhookController
sed -i '' 's/import org.springframework.web.bind.annotation.*;/import org.springframework.web.bind.annotation.*;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport lombok.RequiredArgsConstructor;\nimport lombok.extern.slf4j.Slf4j;\nimport io.swagger.v3.oas.annotations.tags.Tag;/' src/main/java/com/shopcuathuy/controller/PaymentWebhookController.java

# Fix remaining service imports
echo "Fixing service imports..."

# Fix InventoryService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/InventoryService.java

# Fix SellerService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/SellerService.java

# Fix VNPayService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;\nimport lombok.extern.slf4j.Slf4j;\nimport lombok.RequiredArgsConstructor;/' src/main/java/com/shopcuathuy/service/VNPayService.java

# Fix PaymentService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;\nimport lombok.extern.slf4j.Slf4j;\nimport lombok.RequiredArgsConstructor;/' src/main/java/com/shopcuathuy/service/PaymentService.java

# Fix WishlistService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/WishlistService.java

# Fix NotificationService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/NotificationService.java

# Fix ProductReviewService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/ProductReviewService.java

# Fix CartService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/CartService.java

# Fix OrderService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/OrderService.java

# Fix EmailService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/EmailService.java

# Fix SMSService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/SMSService.java

# Fix ShippingService
sed -i '' 's/import org.springframework.stereotype.Service;/import org.springframework.stereotype.Service;/' src/main/java/com/shopcuathuy/service/ShippingService.java

# Fix remaining mapper imports
echo "Fixing mapper imports..."

# Fix PaymentMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/PaymentMapper.java

# Fix CouponMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/CouponMapper.java

# Fix NotificationMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/NotificationMapper.java

# Fix ProductReviewMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/ProductReviewMapper.java

# Fix OrderMapper
sed -i '' 's/import org.mapstruct.factory.Mappers;/import org.mapstruct.factory.Mappers;\nimport org.mapstruct.Mapper;/' src/main/java/com/shopcuathuy/mapper/OrderMapper.java

# Fix remaining DTO imports
echo "Fixing DTO imports..."

# Fix VNPayPaymentDTO
sed -i '' 's/import java.time.LocalDateTime;/import java.time.LocalDateTime;\nimport lombok.Data;\nimport lombok.Builder;\nimport lombok.NoArgsConstructor;\nimport lombok.AllArgsConstructor;/' src/main/java/com/shopcuathuy/dto/VNPayPaymentDTO.java

# Fix PaymentMethodDTO
sed -i '' 's/import java.time.LocalDateTime;/import java.time.LocalDateTime;\nimport lombok.Data;\nimport lombok.Builder;\nimport lombok.NoArgsConstructor;\nimport lombok.AllArgsConstructor;/' src/main/java/com/shopcuathuy/dto/PaymentMethodDTO.java

# Fix CreatePaymentDTO
sed -i '' 's/import java.time.LocalDateTime;/import java.time.LocalDateTime;\nimport lombok.Data;\nimport lombok.Builder;\nimport lombok.NoArgsConstructor;\nimport lombok.AllArgsConstructor;/' src/main/java/com/shopcuathuy/dto/CreatePaymentDTO.java

# Fix PaymentDTO
sed -i '' 's/import java.time.LocalDateTime;/import java.time.LocalDateTime;\nimport lombok.Data;\nimport lombok.Builder;\nimport lombok.NoArgsConstructor;\nimport lombok.AllArgsConstructor;/' src/main/java/com/shopcuathuy/dto/PaymentDTO.java

# Fix main application imports
echo "Fixing main application imports..."

# Fix ShopCuaThuyBackendApplication
sed -i '' 's/import org.springframework.boot.SpringApplication;/import org.springframework.boot.SpringApplication;\nimport org.springframework.boot.autoconfigure.SpringBootApplication;\nimport org.springframework.data.jpa.repository.config.EnableJpaAuditing;\nimport org.springframework.cache.annotation.EnableCaching;\nimport org.springframework.scheduling.annotation.EnableAsync;\nimport org.springframework.scheduling.annotation.EnableScheduling;/' src/main/java/com/shopcuathuy/ShopCuaThuyBackendApplication.java

# Fix validation imports
echo "Fixing validation imports..."

# Fix Message entity
sed -i '' 's/import jakarta.validation.constraints.Size;/import jakarta.validation.constraints.Size;\nimport jakarta.validation.constraints.Min;/' src/main/java/com/shopcuathuy/entity/Message.java

# Fix ProductView entity
sed -i '' 's/import jakarta.validation.constraints.Size;/import jakarta.validation.constraints.Size;\nimport jakarta.validation.constraints.Min;/' src/main/java/com/shopcuathuy/entity/ProductView.java

echo "All imports fixed!"
