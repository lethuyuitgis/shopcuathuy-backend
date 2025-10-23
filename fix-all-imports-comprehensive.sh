#!/bin/bash

echo "Fixing all missing imports comprehensively..."

# Function to add import if not exists
add_import_if_missing() {
    local file="$1"
    local import="$2"
    local after_line="$3"
    
    if ! grep -q "$import" "$file"; then
        sed -i '' -e "/$after_line/a\\
$import" "$file"
    fi
}

# Fix all repository files
echo "Fixing repository imports..."
find src/main/java/com/shopcuathuy/repository -name "*.java" -print0 | while IFS= read -r -d $'\0' file; do
    echo "Processing $file"
    add_import_if_missing "$file" "import org.springframework.data.jpa.repository.JpaRepository;" "package com.shopcuathuy.repository;"
    add_import_if_missing "$file" "import org.springframework.stereotype.Repository;" "package com.shopcuathuy.repository;"
done

# Fix all controller files
echo "Fixing controller imports..."
find src/main/java/com/shopcuathuy/controller -name "*.java" -print0 | while IFS= read -r -d $'\0' file; do
    echo "Processing $file"
    add_import_if_missing "$file" "import org.springframework.web.bind.annotation.RestController;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import org.springframework.web.bind.annotation.RequestMapping;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import org.springframework.web.bind.annotation.PostMapping;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import org.springframework.web.bind.annotation.GetMapping;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import org.springframework.web.bind.annotation.PutMapping;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import org.springframework.web.bind.annotation.DeleteMapping;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import org.springframework.web.bind.annotation.RequestBody;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import org.springframework.web.bind.annotation.PathVariable;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import org.springframework.web.bind.annotation.RequestParam;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import io.swagger.v3.oas.annotations.tags.Tag;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import io.swagger.v3.oas.annotations.Operation;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import io.swagger.v3.oas.annotations.responses.ApiResponse;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import io.swagger.v3.oas.annotations.responses.ApiResponses;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import lombok.RequiredArgsConstructor;" "package com.shopcuathuy.controller;"
    add_import_if_missing "$file" "import lombok.extern.slf4j.Slf4j;" "package com.shopcuathuy.controller;"
done

# Fix all service files
echo "Fixing service imports..."
find src/main/java/com/shopcuathuy/service -name "*.java" -print0 | while IFS= read -r -d $'\0' file; do
    echo "Processing $file"
    add_import_if_missing "$file" "import org.springframework.stereotype.Service;" "package com.shopcuathuy.service;"
    add_import_if_missing "$file" "import lombok.RequiredArgsConstructor;" "package com.shopcuathuy.service;"
    add_import_if_missing "$file" "import lombok.extern.slf4j.Slf4j;" "package com.shopcuathuy.service;"
done

# Fix all mapper files
echo "Fixing mapper imports..."
find src/main/java/com/shopcuathuy/mapper -name "*.java" -print0 | while IFS= read -r -d $'\0' file; do
    echo "Processing $file"
    add_import_if_missing "$file" "import org.mapstruct.Mapper;" "package com.shopcuathuy.mapper;"
    add_import_if_missing "$file" "import org.mapstruct.Mapping;" "package com.shopcuathuy.mapper;"
    add_import_if_missing "$file" "import org.mapstruct.MappingTarget;" "package com.shopcuathuy.mapper;"
    add_import_if_missing "$file" "import org.mapstruct.NullValuePropertyMappingStrategy;" "package com.shopcuathuy.mapper;"
    add_import_if_missing "$file" "import org.mapstruct.factory.Mappers;" "package com.shopcuathuy.mapper;"
done

# Fix all DTO files
echo "Fixing DTO imports..."
find src/main/java/com/shopcuathuy/dto -name "*.java" -print0 | while IFS= read -r -d $'\0' file; do
    echo "Processing $file"
    add_import_if_missing "$file" "import lombok.Data;" "package com.shopcuathuy.dto;"
    add_import_if_missing "$file" "import lombok.Builder;" "package com.shopcuathuy.dto;"
    add_import_if_missing "$file" "import lombok.NoArgsConstructor;" "package com.shopcuathuy.dto;"
    add_import_if_missing "$file" "import lombok.AllArgsConstructor;" "package com.shopcuathuy.dto;"
done

# Fix all entity files
echo "Fixing entity imports..."
find src/main/java/com/shopcuathuy/entity -name "*.java" -print0 | while IFS= read -r -d $'\0' file; do
    echo "Processing $file"
    add_import_if_missing "$file" "import jakarta.persistence.*;" "package com.shopcuathuy.entity;"
    add_import_if_missing "$file" "import lombok.*;" "package com.shopcuathuy.entity;"
    add_import_if_missing "$file" "import org.springframework.data.annotation.CreatedDate;" "package com.shopcuathuy.entity;"
    add_import_if_missing "$file" "import org.springframework.data.annotation.LastModifiedDate;" "package com.shopcuathuy.entity;"
    add_import_if_missing "$file" "import org.springframework.data.jpa.domain.support.AuditingEntityListener;" "package com.shopcuathuy.entity;"
done

echo "All imports fixed!"
