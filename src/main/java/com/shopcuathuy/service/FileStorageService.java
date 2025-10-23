package com.shopcuathuy.service;

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
