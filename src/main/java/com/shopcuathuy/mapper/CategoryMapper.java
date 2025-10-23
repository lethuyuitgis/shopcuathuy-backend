package com.shopcuathuy.mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.shopcuathuy.dto.CategoryDTO;
import com.shopcuathuy.dto.CreateCategoryDTO;
import com.shopcuathuy.dto.UpdateCategoryDTO;
import com.shopcuathuy.entity.Category;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;


/**
 * Mapper interface for Category entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    /**
     * Convert Category entity to CategoryDTO
     */
    CategoryDTO toDTO(Category category);

    /**
     * Convert CategoryDTO to Category entity
     */
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    /**
     * Convert CreateCategoryDTO to Category entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CreateCategoryDTO createCategoryDTO);

    /**
     * Update Category entity from UpdateCategoryDTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateEntity(UpdateCategoryDTO updateCategoryDTO, @MappingTarget Category category);

    /**
     * Convert list of Category entities to list of CategoryDTOs
     */
    List<CategoryDTO> toDTOList(List<Category> categories);

    /**
     * Convert list of CategoryDTOs to list of Category entities
     */
    List<Category> toEntityList(List<CategoryDTO> categoryDTOs);
}
