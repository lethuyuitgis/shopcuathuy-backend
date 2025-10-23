package com.shopcuathuy.mapper;

import com.shopcuathuy.dto.CreateUserDTO;
import com.shopcuathuy.dto.UpdateUserDTO;
import com.shopcuathuy.dto.UserDTO;
import com.shopcuathuy.entity.User;
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
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.email( user.getEmail() );
        userDTO.name( user.getName() );
        userDTO.phone( user.getPhone() );
        userDTO.avatar( user.getAvatar() );
        userDTO.address( user.getAddress() );
        userDTO.role( user.getRole() );
        userDTO.status( user.getStatus() );
        userDTO.emailVerified( user.getEmailVerified() );
        userDTO.createdAt( user.getCreatedAt() );
        userDTO.updatedAt( user.getUpdatedAt() );
        userDTO.lastLogin( user.getLastLogin() );

        return userDTO.build();
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDTO.getId() );
        user.email( userDTO.getEmail() );
        user.name( userDTO.getName() );
        user.phone( userDTO.getPhone() );
        user.avatar( userDTO.getAvatar() );
        user.address( userDTO.getAddress() );
        user.role( userDTO.getRole() );
        user.status( userDTO.getStatus() );
        user.emailVerified( userDTO.getEmailVerified() );

        return user.build();
    }

    @Override
    public User toEntity(CreateUserDTO createUserDTO) {
        if ( createUserDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( createUserDTO.getEmail() );
        user.name( createUserDTO.getName() );
        user.phone( createUserDTO.getPhone() );
        user.avatar( createUserDTO.getAvatar() );
        user.address( createUserDTO.getAddress() );
        user.role( createUserDTO.getRole() );
        user.status( createUserDTO.getStatus() );
        user.emailVerified( createUserDTO.getEmailVerified() );

        return user.build();
    }

    @Override
    public void updateEntity(UpdateUserDTO updateUserDTO, User user) {
        if ( updateUserDTO == null ) {
            return;
        }

        if ( updateUserDTO.getEmail() != null ) {
            user.setEmail( updateUserDTO.getEmail() );
        }
        if ( updateUserDTO.getName() != null ) {
            user.setName( updateUserDTO.getName() );
        }
        if ( updateUserDTO.getPhone() != null ) {
            user.setPhone( updateUserDTO.getPhone() );
        }
        if ( updateUserDTO.getAvatar() != null ) {
            user.setAvatar( updateUserDTO.getAvatar() );
        }
        if ( updateUserDTO.getAddress() != null ) {
            user.setAddress( updateUserDTO.getAddress() );
        }
        if ( updateUserDTO.getRole() != null ) {
            user.setRole( updateUserDTO.getRole() );
        }
        if ( updateUserDTO.getStatus() != null ) {
            user.setStatus( updateUserDTO.getStatus() );
        }
        if ( updateUserDTO.getEmailVerified() != null ) {
            user.setEmailVerified( updateUserDTO.getEmailVerified() );
        }
    }

    @Override
    public List<UserDTO> toDTOList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( users.size() );
        for ( User user : users ) {
            list.add( toDTO( user ) );
        }

        return list;
    }

    @Override
    public List<User> toEntityList(List<UserDTO> userDTOs) {
        if ( userDTOs == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userDTOs.size() );
        for ( UserDTO userDTO : userDTOs ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }
}
