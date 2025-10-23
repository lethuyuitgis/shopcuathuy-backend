package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.CreateUserDTO;
import com.shopcuathuy.dto.UpdateUserDTO;
import com.shopcuathuy.dto.UserDTO;
import com.shopcuathuy.entity.User;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


/**
 * Mapper interface for User entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Convert User entity to UserDTO
     */
    @Mapping(target = "passwordHash", ignore = true)
    UserDTO toDTO(User user);

    /**
     * Convert UserDTO to User entity
     */
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "settings", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "productViews", ignore = true)
    @Mapping(target = "wishlist", ignore = true)
    @Mapping(target = "shopFollowers", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "sentMessages", ignore = true)
    @Mapping(target = "receivedMessages", ignore = true)
    @Mapping(target = "discountCodeUsage", ignore = true)
    User toEntity(UserDTO userDTO);

    /**
     * Convert CreateUserDTO to User entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "settings", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "productViews", ignore = true)
    @Mapping(target = "wishlist", ignore = true)
    @Mapping(target = "shopFollowers", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "sentMessages", ignore = true)
    @Mapping(target = "receivedMessages", ignore = true)
    @Mapping(target = "discountCodeUsage", ignore = true)
    User toEntity(CreateUserDTO createUserDTO);

    /**
     * Update User entity from UpdateUserDTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "settings", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "productViews", ignore = true)
    @Mapping(target = "wishlist", ignore = true)
    @Mapping(target = "shopFollowers", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "sentMessages", ignore = true)
    @Mapping(target = "receivedMessages", ignore = true)
    @Mapping(target = "discountCodeUsage", ignore = true)
    void updateEntity(UpdateUserDTO updateUserDTO, @MappingTarget User user);

    /**
     * Convert list of User entities to list of UserDTOs
     */
    List<UserDTO> toDTOList(List<User> users);

    /**
     * Convert list of UserDTOs to list of User entities
     */
    List<User> toEntityList(List<UserDTO> userDTOs);
}
