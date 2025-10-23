package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.shopcuathuy.dto.CreateProductDTO;
import com.shopcuathuy.dto.ProductDTO;
import com.shopcuathuy.dto.UpdateProductDTO;
import com.shopcuathuy.entity.Product;
import com.shopcuathuy.exception.DuplicateResourceException;
import com.shopcuathuy.exception.ResourceNotFoundException;
import com.shopcuathuy.mapper.ProductMapper;
import com.shopcuathuy.repository.ProductRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;



/**
 * Service class for Product operations
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Create a new product
     */
    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO createProduct(CreateProductDTO createProductDTO) {
        // Check if SKU already exists
        if (productRepository.existsBySku(createProductDTO.getSku())) {
            throw new DuplicateResourceException("SKU already exists: " + createProductDTO.getSku());
        }

        // Check if slug already exists
        if (productRepository.existsBySlug(createProductDTO.getSlug())) {
            throw new DuplicateResourceException("Slug already exists: " + createProductDTO.getSlug());
        }

        // Create product entity
        Product product = productMapper.toEntity(createProductDTO);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        // Save product
        Product savedProduct = productRepository.save(product);

        // Send product created message to RabbitMQ
        messageProducerService.sendProductCreatedMessage(productMapper.toDTO(savedProduct));

        // Store product data to MinIO
        storeProductToMinIO(savedProduct);

        return productMapper.toDTO(savedProduct);
    }

    /**
     * Get product by ID
     */
    @Cacheable(value = "products", key = "#id")
    @Transactional(readOnly = true)
    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toDTO(product);
    }

    /**
     * Get product by slug
     */
    @Cacheable(value = "products", key = "#slug")
    @Transactional(readOnly = true)
    public ProductDTO getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with slug: " + slug));
        return productMapper.toDTO(product);
    }

    /**
     * Get all products with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toDTO);
    }

    /**
     * Get products by seller
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsBySeller(String sellerId) {
        List<Product> products = productRepository.findBySellerId(sellerId);
        return productMapper.toDTOList(products);
    }

    /**
     * Get products by seller with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsBySeller(String sellerId, Pageable pageable) {
        Page<Product> products = productRepository.findBySellerId(sellerId, pageable);
        return products.map(productMapper::toDTO);
    }

    /**
     * Get products by category
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(String categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return productMapper.toDTOList(products);
    }

    /**
     * Get products by category with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsByCategory(String categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
        return products.map(productMapper::toDTO);
    }

    /**
     * Get products by status
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByStatus(Product.ProductStatus status) {
        List<Product> products = productRepository.findByStatus(status);
        return productMapper.toDTOList(products);
    }

    /**
     * Get products by status with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsByStatus(Product.ProductStatus status, Pageable pageable) {
        Page<Product> products = productRepository.findByStatus(status, pageable);
        return products.map(productMapper::toDTO);
    }

    /**
     * Get featured products
     */
    @Cacheable(value = "featured-products")
    @Transactional(readOnly = true)
    public List<ProductDTO> getFeaturedProducts() {
        List<Product> products = productRepository.findByFeaturedTrue();
        return productMapper.toDTOList(products);
    }

    /**
     * Get featured products with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getFeaturedProducts(Pageable pageable) {
        Page<Product> products = productRepository.findByFeaturedTrue(pageable);
        return products.map(productMapper::toDTO);
    }

    /**
     * Search products by text
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String search) {
        List<Product> products = productRepository.findByTextSearch(search);
        return productMapper.toDTOList(products);
    }

    /**
     * Search products by text with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> searchProducts(String search, Pageable pageable) {
        Page<Product> products = productRepository.findByTextSearch(search, pageable);
        return products.map(productMapper::toDTO);
    }

    /**
     * Get products by multiple criteria
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsByCriteria(String sellerId, String categoryId, 
                                                 Product.ProductStatus status, Boolean featured,
                                                 Boolean freeShipping, BigDecimal minPrice, 
                                                 BigDecimal maxPrice, BigDecimal minRating,
                                                 Integer minStock, String search, Pageable pageable) {
        Page<Product> products = productRepository.findByMultipleCriteria(
            sellerId, categoryId, status, featured, freeShipping, 
            minPrice, maxPrice, minRating, minStock, search, pageable);
        return products.map(productMapper::toDTO);
    }

    /**
     * Get top selling products
     */
    @Cacheable(value = "top-selling-products")
    @Transactional(readOnly = true)
    public List<ProductDTO> getTopSellingProducts(Pageable pageable) {
        List<Product> products = productRepository.findTopSellingProducts(pageable);
        return productMapper.toDTOList(products);
    }

    /**
     * Get most viewed products
     */
    @Cacheable(value = "most-viewed-products")
    @Transactional(readOnly = true)
    public List<ProductDTO> getMostViewedProducts(Pageable pageable) {
        List<Product> products = productRepository.findMostViewedProducts(pageable);
        return productMapper.toDTOList(products);
    }

    /**
     * Get highest rated products
     */
    @Cacheable(value = "highest-rated-products")
    @Transactional(readOnly = true)
    public List<ProductDTO> getHighestRatedProducts(Pageable pageable) {
        List<Product> products = productRepository.findHighestRatedProducts(pageable);
        return productMapper.toDTOList(products);
    }

    /**
     * Get newest products
     */
    @Cacheable(value = "newest-products")
    @Transactional(readOnly = true)
    public List<ProductDTO> getNewestProducts(Pageable pageable) {
        List<Product> products = productRepository.findNewestProducts(pageable);
        return productMapper.toDTOList(products);
    }

    /**
     * Get products on sale
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsOnSale(Pageable pageable) {
        Page<Product> productPage = productRepository.findProductsOnSale(pageable);
        return productMapper.toDTOList(productPage.getContent());
    }

    /**
     * Get low stock products
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getLowStockProducts() {
        List<Product> products = productRepository.findLowStockProducts();
        return productMapper.toDTOList(products);
    }

    /**
     * Get low stock products with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getLowStockProducts(Pageable pageable) {
        Page<Product> products = productRepository.findLowStockProducts(pageable);
        return products.map(productMapper::toDTO);
    }

    /**
     * Get out of stock products
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getOutOfStockProducts() {
        List<Product> products = productRepository.findOutOfStockProducts();
        return productMapper.toDTOList(products);
    }

    /**
     * Get out of stock products with pagination
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getOutOfStockProducts(Pageable pageable) {
        Page<Product> products = productRepository.findOutOfStockProducts(pageable);
        return products.map(productMapper::toDTO);
    }

    /**
     * Update product
     */
    @CacheEvict(value = {"products", "featured-products", "top-selling-products", 
                        "most-viewed-products", "highest-rated-products", "newest-products"}, 
                allEntries = true)
    public ProductDTO updateProduct(String id, UpdateProductDTO updateProductDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Check if SKU already exists (excluding current product)
        if (StringUtils.hasText(updateProductDTO.getSku()) && 
            !product.getSku().equals(updateProductDTO.getSku()) &&
            productRepository.existsBySkuAndIdNot(updateProductDTO.getSku(), id)) {
            throw new DuplicateResourceException("SKU already exists: " + updateProductDTO.getSku());
        }

        // Check if slug already exists (excluding current product)
        if (StringUtils.hasText(updateProductDTO.getSlug()) && 
            !product.getSlug().equals(updateProductDTO.getSlug()) &&
            productRepository.existsBySlugAndIdNot(updateProductDTO.getSlug(), id)) {
            throw new DuplicateResourceException("Slug already exists: " + updateProductDTO.getSlug());
        }

        // Update product fields
        productMapper.updateEntity(updateProductDTO, product);
        product.setUpdatedAt(LocalDateTime.now());

        // Save updated product
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Delete product
     */
    @CacheEvict(value = {"products", "featured-products", "top-selling-products", 
                        "most-viewed-products", "highest-rated-products", "newest-products"}, 
                allEntries = true)
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    /**
     * Activate product
     */
    @CacheEvict(value = {"products", "featured-products"}, allEntries = true)
    public ProductDTO activateProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setStatus(Product.ProductStatus.ACTIVE);
        product.setUpdatedAt(LocalDateTime.now());
        
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Deactivate product
     */
    @CacheEvict(value = {"products", "featured-products"}, allEntries = true)
    public ProductDTO deactivateProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setStatus(Product.ProductStatus.INACTIVE);
        product.setUpdatedAt(LocalDateTime.now());
        
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Set product as featured
     */
    @CacheEvict(value = {"products", "featured-products"}, allEntries = true)
    public ProductDTO setFeatured(String id, Boolean featured) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setFeatured(featured);
        product.setUpdatedAt(LocalDateTime.now());
        
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Update product stock
     */
    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO updateStock(String id, Integer stockQuantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setStockQuantity(stockQuantity);
        product.setUpdatedAt(LocalDateTime.now());
        
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Update product price
     */
    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO updatePrice(String id, BigDecimal price) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setPrice(price);
        product.setUpdatedAt(LocalDateTime.now());
        
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Increment product view count
     */
    @CacheEvict(value = {"products", "most-viewed-products"}, allEntries = true)
    public void incrementViewCount(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setViewCount(product.getViewCount() + 1);
        productRepository.save(product);
    }

    /**
     * Increment product sold count
     */
    @CacheEvict(value = {"products", "top-selling-products"}, allEntries = true)
    public void incrementSoldCount(String id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setSoldCount(product.getSoldCount() + quantity);
        product.setStockQuantity(product.getStockQuantity() - quantity);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    /**
     * Update product rating
     */
    @CacheEvict(value = {"products", "highest-rated-products"}, allEntries = true)
    public ProductDTO updateRating(String id, BigDecimal rating, Integer reviewCount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setRating(rating);
        product.setReviewCount(reviewCount);
        product.setUpdatedAt(LocalDateTime.now());
        
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Get product count by seller
     */
    @Transactional(readOnly = true)
    public long getProductCountBySeller(String sellerId) {
        return productRepository.countBySellerId(sellerId);
    }

    /**
     * Get product count by category
     */
    @Transactional(readOnly = true)
    public long getProductCountByCategory(String categoryId) {
        return productRepository.countByCategoryId(categoryId);
    }

    /**
     * Get product count by status
     */
    @Transactional(readOnly = true)
    public long getProductCountByStatus(Product.ProductStatus status) {
        return productRepository.countByStatus(status);
    }

    /**
     * Check if SKU exists
     */
    @Transactional(readOnly = true)
    public boolean skuExists(String sku) {
        return productRepository.existsBySku(sku);
    }

    /**
     * Check if slug exists
     */
    @Transactional(readOnly = true)
    public boolean slugExists(String slug) {
        return productRepository.existsBySlug(slug);
    }

    /**
     * Check if SKU exists excluding product
     */
    @Transactional(readOnly = true)
    public boolean skuExistsExcludingProduct(String sku, String productId) {
        return productRepository.existsBySkuAndIdNot(sku, productId);
    }

    /**
     * Check if slug exists excluding product
     */
    @Transactional(readOnly = true)
    public boolean slugExistsExcludingProduct(String slug, String productId) {
        return productRepository.existsBySlugAndIdNot(slug, productId);
    }

    /**
     * Export product data
     */
    public String exportProductData(Long sellerId, LocalDateTime startDate, LocalDateTime endDate, String format) {
        List<Product> products = productRepository.findBySellerIdAndCreatedAtBetween(String.valueOf(sellerId), startDate, endDate);
        
        // Generate export data
        String exportData = generateProductExportData(products, format);
        
        // Store to MinIO
        String fileName = "product-export-" + sellerId + "-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadProductExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Store product data to MinIO
     */
    private void storeProductToMinIO(Product product) {
        try {
            String productData = createProductDataJson(product);
            String fileName = "products/" + product.getStatus() + "/" +
                            product.getCreatedAt().toLocalDate() + "/" +
                            product.getId() + ".json";
            
            fileStorageService.uploadProductData(fileName, productData);
        } catch (Exception e) {
            System.err.println("Failed to store product data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create product data JSON
     */
    private String createProductDataJson(Product product) {
        return String.format(
            "{\"id\":\"%s\",\"name\":\"%s\",\"sku\":\"%s\",\"slug\":\"%s\",\"status\":\"%s\",\"price\":%s,\"createdAt\":\"%s\"}",
            product.getId(),
            product.getName(),
            product.getSku(),
            product.getSlug(),
            product.getStatus(),
            product.getPrice(),
            product.getCreatedAt()
        );
    }

    /**
     * Generate product export data
     */
    private String generateProductExportData(List<Product> products, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateProductJsonExport(products);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateProductCsvExport(products);
        }
        return generateProductJsonExport(products);
    }

    /**
     * Generate JSON export
     */
    private String generateProductJsonExport(List<Product> products) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            json.append(createProductDataJson(product));
            if (i < products.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateProductCsvExport(List<Product> products) {
        StringBuilder csv = new StringBuilder("id,name,sku,slug,status,price,createdAt\n");
        for (Product product : products) {
            csv.append(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getSlug(),
                product.getStatus(),
                product.getPrice(),
                product.getCreatedAt()
            ));
        }
        return csv.toString();
    }
}
