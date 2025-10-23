package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.shopcuathuy.config.MinIOConfig;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



/**
 * Service class for file storage operations using MinIO
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
public class FileStorageService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIOConfig minIOConfig;

    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    /**
     * Initialize bucket if it doesn't exist
     */
    public void initializeBucket() {
        try {
            String bucketName = minIOConfig.getBucketName();
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());

            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize bucket: " + e.getMessage(), e);
        }
    }

    /**
     * Upload file to MinIO
     */
    public String uploadFile(MultipartFile file, String folder) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String fileName = generateFileName(file.getOriginalFilename());
            String objectName = folder + "/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    /**
     * Upload file with custom name
     */
    public String uploadFile(MultipartFile file, String folder, String customFileName) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String fileName = customFileName + fileExtension;
            String objectName = folder + "/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    /**
     * Upload file from byte array
     */
    public String uploadFile(byte[] fileData, String fileName, String contentType, String folder) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = folder + "/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(fileData), fileData.length, -1)
                    .contentType(contentType)
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    /**
     * Download file from MinIO
     */
    public InputStream downloadFile(String objectName) {
        try {
            String bucketName = minIOConfig.getBucketName();
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file: " + e.getMessage(), e);
        }
    }

    /**
     * Delete file from MinIO
     */
    public void deleteFile(String objectName) {
        try {
            String bucketName = minIOConfig.getBucketName();
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file: " + e.getMessage(), e);
        }
    }

    /**
     * Check if file exists
     */
    public boolean fileExists(String objectName) {
        try {
            String bucketName = minIOConfig.getBucketName();
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get file info
     */
    public FileInfo getFileInfo(String objectName) {
        try {
            String bucketName = minIOConfig.getBucketName();
            var statObject = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());

            return FileInfo.builder()
                    .objectName(objectName)
                    .size(statObject.size())
                    .contentType(statObject.contentType())
                    .lastModified(statObject.lastModified())
                    .etag(statObject.etag())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get file info: " + e.getMessage(), e);
        }
    }

    /**
     * Generate unique file name
     */
    private String generateFileName(String originalFilename) {
        String fileExtension = getFileExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + fileExtension;
    }

    /**
     * Get file extension
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex == -1 ? "" : filename.substring(lastDotIndex);
    }

    /**
     * Get current date path
     */
    private String getCurrentDatePath() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    /**
     * Generate thumbnail for image
     */
    public String generateThumbnail(String originalObjectName, int width, int height) {
        try {
            // This is a simplified implementation
            // In a real application, you would use an image processing library
            // like ImageMagick or Java's BufferedImage to create thumbnails
            
            String bucketName = minIOConfig.getBucketName();
            String thumbnailObjectName = originalObjectName.replace("/", "/thumbnails/");
            
            // For now, we'll just return the original object name
            // In a real implementation, you would:
            // 1. Download the original image
            // 2. Resize it to the specified dimensions
            // 3. Upload the resized image as a thumbnail
            
            return thumbnailObjectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate thumbnail: " + e.getMessage(), e);
        }
    }

    /**
     * Get file URL
     */
    public String getFileUrl(String objectName) {
        // In a real application, you would configure MinIO to serve files via HTTP
        // For now, we'll return a placeholder URL
        return "https://your-minio-server.com/" + minIOConfig.getBucketName() + "/" + objectName;
    }

    /**
     * Get thumbnail URL
     */
    public String getThumbnailUrl(String objectName) {
        String thumbnailObjectName = objectName.replace("/", "/thumbnails/");
        return getFileUrl(thumbnailObjectName);
    }

    /**
     * Upload analytics data to MinIO
     */
    public String uploadAnalyticsData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "analytics/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload analytics data: " + e.getMessage(), e);
        }
    }

    /**
     * Upload analytics export to MinIO
     */
    public String uploadAnalyticsExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "analytics/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload analytics export: " + e.getMessage(), e);
        }
    }

    /**
     * Upload shipping data to MinIO
     */
    public String uploadShippingData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "shipping/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload shipping data: " + e.getMessage(), e);
        }
    }

    /**
     * Upload shipping export to MinIO
     */
    public String uploadShippingExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "shipping/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload shipping export: " + e.getMessage(), e);
        }
    }

    /**
     * Upload coupon data to MinIO
     */
    public String uploadCouponData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "coupons/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload coupon data: " + e.getMessage(), e);
        }
    }

    /**
     * Upload coupon export to MinIO
     */
    public String uploadCouponExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "coupons/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload coupon export: " + e.getMessage(), e);
        }
    }

    /**
     * Upload seller data to MinIO
     */
    public String uploadSellerData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "sellers/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload seller data: " + e.getMessage(), e);
        }
    }

    /**
     * Upload seller export to MinIO
     */
    public String uploadSellerExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "sellers/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload seller export: " + e.getMessage(), e);
        }
    }

    // Notification related methods
    public String uploadNotificationData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "notifications/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload notification data: " + e.getMessage(), e);
        }
    }

    public String uploadNotificationExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "notifications/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload notification export: " + e.getMessage(), e);
        }
    }

    // Wishlist related methods
    public String uploadWishlistData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "wishlists/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload wishlist data: " + e.getMessage(), e);
        }
    }

    public String uploadWishlistExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "wishlists/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload wishlist export: " + e.getMessage(), e);
        }
    }

    // Review related methods
    public String uploadReviewData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "reviews/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload review data: " + e.getMessage(), e);
        }
    }

    public String uploadReviewExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "reviews/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload review export: " + e.getMessage(), e);
        }
    }

    // Cart related methods
    public String uploadCartData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "carts/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload cart data: " + e.getMessage(), e);
        }
    }

    public String uploadCartExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "carts/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload cart export: " + e.getMessage(), e);
        }
    }

    // Payment related methods
    public String uploadPaymentData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "payments/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload payment data: " + e.getMessage(), e);
        }
    }

    public String uploadPaymentExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "payments/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload payment export: " + e.getMessage(), e);
        }
    }

    // Order related methods
    public String uploadOrderData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "orders/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload order data: " + e.getMessage(), e);
        }
    }

    public String uploadOrderExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "orders/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload order export: " + e.getMessage(), e);
        }
    }

    // Product related methods
    public String uploadProductData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "products/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload product data: " + e.getMessage(), e);
        }
    }

    public String uploadProductExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "products/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload product export: " + e.getMessage(), e);
        }
    }

    // User related methods
    public String uploadUserData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "users/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload user data: " + e.getMessage(), e);
        }
    }

    public String uploadUserExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "users/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload user export: " + e.getMessage(), e);
        }
    }

    // Category related methods
    public String uploadCategoryData(String fileName, String data) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "categories/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(data.getBytes()), data.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload category data: " + e.getMessage(), e);
        }
    }

    public String uploadCategoryExport(String fileName, String exportData) {
        try {
            String bucketName = minIOConfig.getBucketName();
            String objectName = "categories/exports/" + getCurrentDatePath() + "/" + fileName;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(new ByteArrayInputStream(exportData.getBytes()), exportData.getBytes().length, -1)
                    .contentType("application/json")
                    .build());

            return getFileUrl(objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload category export: " + e.getMessage(), e);
        }
    }

    /**
     * File info class
     */
    public static class FileInfo {
        private String objectName;
        private long size;
        private String contentType;
        private java.time.ZonedDateTime lastModified;
        private String etag;

        // Getters and setters
        public String getObjectName() { return objectName; }
        public void setObjectName(String objectName) { this.objectName = objectName; }
        public long getSize() { return size; }
        public void setSize(long size) { this.size = size; }
        public String getContentType() { return contentType; }
        public void setContentType(String contentType) { this.contentType = contentType; }
        public java.time.ZonedDateTime getLastModified() { return lastModified; }
        public void setLastModified(java.time.ZonedDateTime lastModified) { this.lastModified = lastModified; }
        public String getEtag() { return etag; }
        public void setEtag(String etag) { this.etag = etag; }

        public static FileInfoBuilder builder() {
            return new FileInfoBuilder();
        }

        public static class FileInfoBuilder {
            private String objectName;
            private long size;
            private String contentType;
            private java.time.ZonedDateTime lastModified;
            private String etag;

            public FileInfoBuilder objectName(String objectName) {
                this.objectName = objectName;
                return this;
            }

            public FileInfoBuilder size(long size) {
                this.size = size;
                return this;
            }

            public FileInfoBuilder contentType(String contentType) {
                this.contentType = contentType;
                return this;
            }

            public FileInfoBuilder lastModified(java.time.ZonedDateTime lastModified) {
                this.lastModified = lastModified;
                return this;
            }

            public FileInfoBuilder etag(String etag) {
                this.etag = etag;
                return this;
            }

            public FileInfo build() {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setObjectName(objectName);
                fileInfo.setSize(size);
                fileInfo.setContentType(contentType);
                fileInfo.setLastModified(lastModified);
                fileInfo.setEtag(etag);
                return fileInfo;
            }
        }
    }
}
