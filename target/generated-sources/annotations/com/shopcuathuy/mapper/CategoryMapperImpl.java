package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.CategoryDTO;
import com.shopcuathuy.dto.CreateCategoryDTO;
import com.shopcuathuy.dto.UpdateCategoryDTO;
import com.shopcuathuy.entity.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-23T17:09:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDTO toDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( category.getId() );
        categoryDTO.setName( category.getName() );
        categoryDTO.setSlug( category.getSlug() );
        categoryDTO.setDescription( category.getDescription() );
        categoryDTO.setParentId( category.getParentId() );
        categoryDTO.setLevel( category.getLevel() );
        categoryDTO.setSortOrder( category.getSortOrder() );
        categoryDTO.setIcon( category.getIcon() );
        categoryDTO.setImage( category.getImage() );
        categoryDTO.setFeatured( category.getFeatured() );
        categoryDTO.setStatus( category.getStatus() );
        categoryDTO.setMetaTitle( category.getMetaTitle() );
        categoryDTO.setMetaDescription( category.getMetaDescription() );
        categoryDTO.setMetaKeywords( category.getMetaKeywords() );
        categoryDTO.setCreatedAt( category.getCreatedAt() );
        categoryDTO.setUpdatedAt( category.getUpdatedAt() );

        return categoryDTO;
    }

    @Override
    public Category toEntity(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( categoryDTO.getId() );
        category.name( categoryDTO.getName() );
        category.slug( categoryDTO.getSlug() );
        category.icon( categoryDTO.getIcon() );
        category.image( categoryDTO.getImage() );
        category.description( categoryDTO.getDescription() );
        category.parentId( categoryDTO.getParentId() );
        category.sortOrder( categoryDTO.getSortOrder() );
        category.status( categoryDTO.getStatus() );
        category.featured( categoryDTO.getFeatured() );
        category.level( categoryDTO.getLevel() );
        category.metaTitle( categoryDTO.getMetaTitle() );
        category.metaDescription( categoryDTO.getMetaDescription() );
        category.metaKeywords( categoryDTO.getMetaKeywords() );
        category.createdAt( categoryDTO.getCreatedAt() );
        category.updatedAt( categoryDTO.getUpdatedAt() );

        return category.build();
    }

    @Override
    public Category toEntity(CreateCategoryDTO createCategoryDTO) {
        if ( createCategoryDTO == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( createCategoryDTO.getName() );
        category.slug( createCategoryDTO.getSlug() );
        category.icon( createCategoryDTO.getIcon() );
        category.image( createCategoryDTO.getImage() );
        category.description( createCategoryDTO.getDescription() );
        category.parentId( createCategoryDTO.getParentId() );
        category.sortOrder( createCategoryDTO.getSortOrder() );
        category.status( createCategoryDTO.getStatus() );
        category.featured( createCategoryDTO.getFeatured() );
        category.metaTitle( createCategoryDTO.getMetaTitle() );
        category.metaDescription( createCategoryDTO.getMetaDescription() );
        category.metaKeywords( createCategoryDTO.getMetaKeywords() );

        return category.build();
    }

    @Override
    public void updateEntity(UpdateCategoryDTO updateCategoryDTO, Category category) {
        if ( updateCategoryDTO == null ) {
            return;
        }

        if ( updateCategoryDTO.getName() != null ) {
            category.setName( updateCategoryDTO.getName() );
        }
        if ( updateCategoryDTO.getSlug() != null ) {
            category.setSlug( updateCategoryDTO.getSlug() );
        }
        if ( updateCategoryDTO.getIcon() != null ) {
            category.setIcon( updateCategoryDTO.getIcon() );
        }
        if ( updateCategoryDTO.getImage() != null ) {
            category.setImage( updateCategoryDTO.getImage() );
        }
        if ( updateCategoryDTO.getDescription() != null ) {
            category.setDescription( updateCategoryDTO.getDescription() );
        }
        if ( updateCategoryDTO.getParentId() != null ) {
            category.setParentId( updateCategoryDTO.getParentId() );
        }
        if ( updateCategoryDTO.getSortOrder() != null ) {
            category.setSortOrder( updateCategoryDTO.getSortOrder() );
        }
        if ( updateCategoryDTO.getStatus() != null ) {
            category.setStatus( updateCategoryDTO.getStatus() );
        }
        if ( updateCategoryDTO.getFeatured() != null ) {
            category.setFeatured( updateCategoryDTO.getFeatured() );
        }
        if ( updateCategoryDTO.getMetaTitle() != null ) {
            category.setMetaTitle( updateCategoryDTO.getMetaTitle() );
        }
        if ( updateCategoryDTO.getMetaDescription() != null ) {
            category.setMetaDescription( updateCategoryDTO.getMetaDescription() );
        }
        if ( updateCategoryDTO.getMetaKeywords() != null ) {
            category.setMetaKeywords( updateCategoryDTO.getMetaKeywords() );
        }
    }

    @Override
    public List<CategoryDTO> toDTOList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryDTO> list = new ArrayList<CategoryDTO>( categories.size() );
        for ( Category category : categories ) {
            list.add( toDTO( category ) );
        }

        return list;
    }

    @Override
    public List<Category> toEntityList(List<CategoryDTO> categoryDTOs) {
        if ( categoryDTOs == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( categoryDTOs.size() );
        for ( CategoryDTO categoryDTO : categoryDTOs ) {
            list.add( toEntity( categoryDTO ) );
        }

        return list;
    }
}
