package com.shopcuathuy.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


/**
 * Category entity representing product categories
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "categories", indexes = {
    @Index(name = "idx_category_slug", columnList = "slug"),
    @Index(name = "idx_category_parent_id", columnList = "parent_id"),
    @Index(name = "idx_category_active", columnList = "active"),
    @Index(name = "idx_category_sort_order", columnList = "sort_order"),
    @Index(name = "idx_category_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Category name is required")
    @Size(max = 255, message = "Category name must not exceed 255 characters")
    private String name;

    @Column(nullable = false, length = 255, unique = true)
    @NotBlank(message = "Category slug is required")
    @Size(max = 255, message = "Category slug must not exceed 255 characters")
    private String slug;

    @Column(length = 100)
    @Size(max = 100, message = "Icon must not exceed 100 characters")
    private String icon;

    @Column(length = 500)
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String image;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    @Min(value = 0, message = "Sort order cannot be negative")
    private Integer sortOrder = 0;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "meta_title", length = 255)
    @Size(max = 255, message = "Meta title must not exceed 255 characters")
    private String metaTitle;

    @Column(name = "meta_description", length = 500)
    @Size(max = 500, message = "Meta description must not exceed 500 characters")
    private String metaDescription;

    @Column(name = "meta_keywords", length = 500)
    @Size(max = 500, message = "Meta keywords must not exceed 500 characters")
    private String metaKeywords;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    /**
     * Check if category is active
     */
    public boolean isActive() {
        return active != null && active;
    }

    /**
     * Check if category is root (no parent)
     */
    public boolean isRoot() {
        return parentId == null;
    }

    /**
     * Check if category has children
     */
    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    /**
     * Get total products count
     */
    public int getTotalProductsCount() {
        return products != null ? products.size() : 0;
    }

    /**
     * Get total children count
     */
    public int getTotalChildrenCount() {
        return children != null ? children.size() : 0;
    }

    /**
     * Get category level (depth in hierarchy)
     */
    public int getLevel() {
        if (isRoot()) {
            return 0;
        }
        return parent != null ? parent.getLevel() + 1 : 1;
    }

    /**
     * Get full category path
     */
    public String getFullPath() {
        if (isRoot()) {
            return name;
        }
        return parent != null ? parent.getFullPath() + " > " + name : name;
    }
}
