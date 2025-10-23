#!/bin/bash

echo "🔧 Fixing missing imports..."

# Fix RabbitMQConfig
echo "Fixing RabbitMQConfig..."
sed -i '' 's/import org.springframework.context.annotation.Bean;/import org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;/' src/main/java/com/shopcuathuy/config/RabbitMQConfig.java

# Fix Payment entities
echo "Fixing Payment entities..."
find src/main/java/com/shopcuathuy/entity/ -name "*.java" -exec sed -i '' '1i\
import jakarta.persistence.*;\
import lombok.*;\
import org.springframework.data.annotation.CreatedDate;\
import org.springframework.data.annotation.LastModifiedDate;\
import org.springframework.data.jpa.domain.support.AuditingEntityListener;\
' {} \;

# Fix Service classes
echo "Fixing Service classes..."
find src/main/java/com/shopcuathuy/service/ -name "*.java" -exec sed -i '' '1i\
import org.springframework.stereotype.Service;\
' {} \;

# Fix Repository classes
echo "Fixing Repository classes..."
find src/main/java/com/shopcuathuy/repository/ -name "*.java" -exec sed -i '' '1i\
import org.springframework.data.jpa.repository.JpaRepository;\
import org.springframework.stereotype.Repository;\
' {} \;

# Fix Controller classes
echo "Fixing Controller classes..."
find src/main/java/com/shopcuathuy/controller/ -name "*.java" -exec sed -i '' '1i\
import org.springframework.web.bind.annotation.*;\
import org.springframework.http.ResponseEntity;\
import org.springframework.stereotype.Controller;\
' {} \;

# Fix Mapper classes
echo "Fixing Mapper classes..."
find src/main/java/com/shopcuathuy/mapper/ -name "*.java" -exec sed -i '' '1i\
import org.mapstruct.Mapper;\
import org.mapstruct.NullValuePropertyMappingStrategy;\
' {} \;

# Fix Security classes
echo "Fixing Security classes..."
find src/main/java/com/shopcuathuy/security/ -name "*.java" -exec sed -i '' '1i\
import org.springframework.stereotype.Component;\
import org.springframework.web.filter.OncePerRequestFilter;\
' {} \;

# Fix main application
echo "Fixing main application..."
sed -i '' '1i\
import org.springframework.boot.autoconfigure.SpringBootApplication;\
import org.springframework.cache.annotation.EnableCaching;\
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;\
import org.springframework.scheduling.annotation.EnableAsync;\
import org.springframework.scheduling.annotation.EnableScheduling;\
' src/main/java/com/shopcuathuy/ShopCuaThuyBackendApplication.java

echo "✅ Imports fixed!"
