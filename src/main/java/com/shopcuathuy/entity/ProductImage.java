package com.shopcuathuy.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * ProductImage entity representing product images
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "product_images", indexes = {
    @Index(name = "idx_product_image_product_id", columnList = "product_id"),
    @Index(name = "idx_product_image_is_primary", columnList = "is_primary"),
    @Index(name = "idx_product_image_sort_order", columnList = "sort_order"),
    @Index(name = "idx_product_image_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "product_id", nullable = false)
    @NotBlank(message = "Product ID is required")
    private String productId;

    @Column(name = "image_url", nullable = false, length = 500)
    @NotBlank(message = "Image URL is required")
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;

    @Column(name = "thumbnail_url", length = 500)
    @Size(max = 500, message = "Thumbnail URL must not exceed 500 characters")
    private String thumbnailUrl;

    @Column(name = "alt_text", length = 255)
    @Size(max = 255, message = "Alt text must not exceed 255 characters")
    private String altText;

    @Column(name = "title", length = 255)
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Column(name = "is_primary", nullable = false)
    @Builder.Default
    private Boolean isPrimary = false;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Sort order cannot be negative")
    private Integer sortOrder = 0;

    @Column(name = "file_size")
    @Min(value = 0, message = "File size cannot be negative")
    private Long fileSize;

    @Column(name = "width")
    @Min(value = 0, message = "Width cannot be negative")
    private Integer width;

    @Column(name = "height")
    @Min(value = 0, message = "Height cannot be negative")
    private Integer height;

    @Column(name = "mime_type", length = 100)
    @Size(max = 100, message = "MIME type must not exceed 100 characters")
    private String mimeType;

    @Column(name = "file_extension", length = 10)
    @Size(max = 10, message = "File extension must not exceed 10 characters")
    private String fileExtension;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    /**
     * Check if image is primary
     */
    public boolean isPrimary() {
        return isPrimary != null && isPrimary;
    }

    /**
     * Get image dimensions as string
     */
    public String getDimensions() {
        if (width != null && height != null) {
            return width + "x" + height;
        }
        return null;
    }

    /**
     * Get file size in human readable format
     */
    public String getFileSizeFormatted() {
        if (fileSize == null) {
            return null;
        }
        
        if (fileSize < 1024) {
            return fileSize + " B";
        } else if (fileSize < 1024 * 1024) {
            return String.format("%.1f KB", fileSize / 1024.0);
        } else if (fileSize < 1024 * 1024 * 1024) {
            return String.format("%.1f MB", fileSize / (1024.0 * 1024.0));
        } else {
            return String.format("%.1f GB", fileSize / (1024.0 * 1024.0 * 1024.0));
        }
    }

    /**
     * Check if image is valid
     */
    public boolean isValid() {
        return imageUrl != null && !imageUrl.trim().isEmpty();
    }

    /**
     * Get image type from MIME type
     */
    public String getImageType() {
        if (mimeType == null) {
            return null;
        }
        
        if (mimeType.startsWith("image/")) {
            return mimeType.substring(6);
        }
        
        return mimeType;
    }

    /**
     * Check if image is JPEG
     */
    public boolean isJpeg() {
        return "image/jpeg".equals(mimeType) || "jpg".equalsIgnoreCase(fileExtension);
    }

    /**
     * Check if image is PNG
     */
    public boolean isPng() {
        return "image/png".equals(mimeType) || "png".equalsIgnoreCase(fileExtension);
    }

    /**
     * Check if image is WebP
     */
    public boolean isWebP() {
        return "image/webp".equals(mimeType) || "webp".equalsIgnoreCase(fileExtension);
    }

    /**
     * Check if image is GIF
     */
    public boolean isGif() {
        return "image/gif".equals(mimeType) || "gif".equalsIgnoreCase(fileExtension);
    }
}
