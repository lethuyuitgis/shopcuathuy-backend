package com.shopcuathuy.controller;

import com.shopcuathuy.service.FileStorageService;
import com.shopcuathuy.service.FileStorageService.FileInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;




/**
 * REST Controller for file upload operations
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/files")
@Tag(name = "File Management", description = "APIs for managing file uploads and downloads")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Upload single file
     */
    @PostMapping("/upload")
    @Operation(summary = "Upload single file", description = "Upload a single file to the storage system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid file or upload failed")
    })
    public ResponseEntity<Map<String, Object>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", defaultValue = "general") String folder) {
        try {
            String objectName = fileStorageService.uploadFile(file, folder);
            String fileUrl = fileStorageService.getFileUrl(objectName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("objectName", objectName);
            response.put("fileUrl", fileUrl);
            response.put("originalFilename", file.getOriginalFilename());
            response.put("size", file.getSize());
            response.put("contentType", file.getContentType());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Upload multiple files
     */
    @PostMapping("/upload-multiple")
    @Operation(summary = "Upload multiple files", description = "Upload multiple files to the storage system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Files uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid files or upload failed")
    })
    public ResponseEntity<Map<String, Object>> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "folder", defaultValue = "general") String folder) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("uploadedFiles", new java.util.ArrayList<>());
            
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String objectName = fileStorageService.uploadFile(file, folder);
                    String fileUrl = fileStorageService.getFileUrl(objectName);
                    
                    Map<String, Object> fileInfo = new HashMap<>();
                    fileInfo.put("objectName", objectName);
                    fileInfo.put("fileUrl", fileUrl);
                    fileInfo.put("originalFilename", file.getOriginalFilename());
                    fileInfo.put("size", file.getSize());
                    fileInfo.put("contentType", file.getContentType());
                    
                    ((java.util.List<Map<String, Object>>) response.get("uploadedFiles")).add(fileInfo);
                }
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Upload file with custom name
     */
    @PostMapping("/upload-custom")
    @Operation(summary = "Upload file with custom name", description = "Upload a file with a custom filename")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid file or upload failed")
    })
    public ResponseEntity<Map<String, Object>> uploadFileWithCustomName(
            @RequestParam("file") MultipartFile file,
            @RequestParam("customName") String customName,
            @RequestParam(value = "folder", defaultValue = "general") String folder) {
        try {
            String objectName = fileStorageService.uploadFile(file, folder, customName);
            String fileUrl = fileStorageService.getFileUrl(objectName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("objectName", objectName);
            response.put("fileUrl", fileUrl);
            response.put("originalFilename", file.getOriginalFilename());
            response.put("customName", customName);
            response.put("size", file.getSize());
            response.put("contentType", file.getContentType());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Download file
     */
    @GetMapping("/download/{objectName}")
    @Operation(summary = "Download file", description = "Download a file from the storage system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File downloaded successfully"),
        @ApiResponse(responseCode = "404", description = "File not found")
    })
    public ResponseEntity<InputStream> downloadFile(@PathVariable String objectName) {
        try {
            InputStream fileStream = fileStorageService.downloadFile(objectName);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileStream);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete file
     */
    @DeleteMapping("/{objectName}")
    @Operation(summary = "Delete file", description = "Delete a file from the storage system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File deleted successfully"),
        @ApiResponse(responseCode = "404", description = "File not found")
    })
    public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable String objectName) {
        try {
            fileStorageService.deleteFile(objectName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "File deleted successfully");
            response.put("objectName", objectName);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Check if file exists
     */
    @GetMapping("/exists/{objectName}")
    @Operation(summary = "Check if file exists", description = "Check if a file exists in the storage system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File existence checked successfully")
    })
    public ResponseEntity<Map<String, Object>> fileExists(@PathVariable String objectName) {
        try {
            boolean exists = fileStorageService.fileExists(objectName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("exists", exists);
            response.put("objectName", objectName);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("exists", false);
            response.put("error", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Get file info
     */
    @GetMapping("/info/{objectName}")
    @Operation(summary = "Get file info", description = "Get information about a file in the storage system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File info retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "File not found")
    })
    public ResponseEntity<Map<String, Object>> getFileInfo(@PathVariable String objectName) {
        try {
            FileInfo fileInfo = fileStorageService.getFileInfo(objectName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("objectName", fileInfo.getObjectName());
            response.put("size", fileInfo.getSize());
            response.put("contentType", fileInfo.getContentType());
            response.put("lastModified", fileInfo.getLastModified());
            response.put("etag", fileInfo.getEtag());
            response.put("fileUrl", fileStorageService.getFileUrl(objectName));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Generate thumbnail
     */
    @PostMapping("/thumbnail/{objectName}")
    @Operation(summary = "Generate thumbnail", description = "Generate a thumbnail for an image file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Thumbnail generated successfully"),
        @ApiResponse(responseCode = "400", description = "Failed to generate thumbnail")
    })
    public ResponseEntity<Map<String, Object>> generateThumbnail(
            @PathVariable String objectName,
            @RequestParam(defaultValue = "150") int width,
            @RequestParam(defaultValue = "150") int height) {
        try {
            String thumbnailObjectName = fileStorageService.generateThumbnail(objectName, width, height);
            String thumbnailUrl = fileStorageService.getThumbnailUrl(objectName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("thumbnailObjectName", thumbnailObjectName);
            response.put("thumbnailUrl", thumbnailUrl);
            response.put("width", width);
            response.put("height", height);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get file URL
     */
    @GetMapping("/url/{objectName}")
    @Operation(summary = "Get file URL", description = "Get the public URL for a file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File URL retrieved successfully")
    })
    public ResponseEntity<Map<String, Object>> getFileUrl(@PathVariable String objectName) {
        try {
            String fileUrl = fileStorageService.getFileUrl(objectName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("objectName", objectName);
            response.put("fileUrl", fileUrl);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get thumbnail URL
     */
    @GetMapping("/thumbnail-url/{objectName}")
    @Operation(summary = "Get thumbnail URL", description = "Get the public URL for a thumbnail")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Thumbnail URL retrieved successfully")
    })
    public ResponseEntity<Map<String, Object>> getThumbnailUrl(@PathVariable String objectName) {
        try {
            String thumbnailUrl = fileStorageService.getThumbnailUrl(objectName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("objectName", objectName);
            response.put("thumbnailUrl", thumbnailUrl);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
