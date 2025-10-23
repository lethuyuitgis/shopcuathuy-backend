package com.shopcuathuy.controller;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import com.shopcuathuy.dto.CreateUserDTO;
import com.shopcuathuy.dto.UpdateUserDTO;
import com.shopcuathuy.dto.UserDTO;
import com.shopcuathuy.entity.User;
import com.shopcuathuy.exception.DuplicateResourceException;
import com.shopcuathuy.exception.ResourceNotFoundException;
import com.shopcuathuy.exception.ValidationException;
import com.shopcuathuy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * REST Controller for User operations
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Create a new user
     */
    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Email or phone already exists")
    })
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            UserDTO user = userService.createUser(createUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (DuplicateResourceException e) {
            throw e;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + e.getMessage(), e);
        }
    }

    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        try {
            UserDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user: " + e.getMessage(), e);
        }
    }

    /**
     * Get user by email
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieve a user by their email address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        try {
            UserDTO user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user: " + e.getMessage(), e);
        }
    }

    /**
     * Get all users with pagination
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve all users with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        try {
            Page<UserDTO> users = userService.getAllUsers(pageable);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users: " + e.getMessage(), e);
        }
    }

    /**
     * Get users by role
     */
    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Retrieve users filtered by role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable User.UserRole role) {
        try {
            List<UserDTO> users = userService.getUsersByRole(role);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users by role: " + e.getMessage(), e);
        }
    }

    /**
     * Get users by role with pagination
     */
    @GetMapping("/role/{role}/paged")
    @Operation(summary = "Get users by role with pagination", description = "Retrieve users filtered by role with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<Page<UserDTO>> getUsersByRole(@PathVariable User.UserRole role, Pageable pageable) {
        try {
            Page<UserDTO> users = userService.getUsersByRole(role, pageable);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users by role: " + e.getMessage(), e);
        }
    }

    /**
     * Get users by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get users by status", description = "Retrieve users filtered by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<List<UserDTO>> getUsersByStatus(@PathVariable User.UserStatus status) {
        try {
            List<UserDTO> users = userService.getUsersByStatus(status);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get users by status with pagination
     */
    @GetMapping("/status/{status}/paged")
    @Operation(summary = "Get users by status with pagination", description = "Retrieve users filtered by status with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<Page<UserDTO>> getUsersByStatus(@PathVariable User.UserStatus status, Pageable pageable) {
        try {
            Page<UserDTO> users = userService.getUsersByStatus(status, pageable);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get users by role and status
     */
    @GetMapping("/role/{role}/status/{status}")
    @Operation(summary = "Get users by role and status", description = "Retrieve users filtered by role and status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<List<UserDTO>> getUsersByRoleAndStatus(@PathVariable User.UserRole role, 
                                                               @PathVariable User.UserStatus status) {
        try {
            List<UserDTO> users = userService.getUsersByRoleAndStatus(role, status);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users by role and status: " + e.getMessage(), e);
        }
    }

    /**
     * Get users by role and status with pagination
     */
    @GetMapping("/role/{role}/status/{status}/paged")
    @Operation(summary = "Get users by role and status with pagination", description = "Retrieve users filtered by role and status with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<Page<UserDTO>> getUsersByRoleAndStatus(@PathVariable User.UserRole role, 
                                                               @PathVariable User.UserStatus status, 
                                                               Pageable pageable) {
        try {
            Page<UserDTO> users = userService.getUsersByRoleAndStatus(role, status, pageable);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users by role and status: " + e.getMessage(), e);
        }
    }

    /**
     * Search users by name or email
     */
    @GetMapping("/search")
    @Operation(summary = "Search users", description = "Search users by name or email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users found")
    })
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String search) {
        try {
            List<UserDTO> users = userService.searchUsers(search);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search users: " + e.getMessage(), e);
        }
    }

    /**
     * Search users by name or email with pagination
     */
    @GetMapping("/search/paged")
    @Operation(summary = "Search users with pagination", description = "Search users by name or email with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users found")
    })
    public ResponseEntity<Page<UserDTO>> searchUsers(@RequestParam String search, Pageable pageable) {
        try {
            Page<UserDTO> users = userService.searchUsers(search, pageable);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search users: " + e.getMessage(), e);
        }
    }

    /**
     * Get users by multiple criteria
     */
    @GetMapping("/criteria")
    @Operation(summary = "Get users by criteria", description = "Retrieve users filtered by multiple criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<Page<UserDTO>> getUsersByCriteria(
            @RequestParam(required = false) User.UserRole role,
            @RequestParam(required = false) User.UserStatus status,
            @RequestParam(required = false) Boolean emailVerified,
            @RequestParam(required = false) String search,
            Pageable pageable) {
        try {
            Page<UserDTO> users = userService.getUsersByCriteria(role, status, emailVerified, search, pageable);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get users by criteria: " + e.getMessage(), e);
        }
    }

    /**
     * Update user
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Email or phone already exists")
    })
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        try {
            UserDTO user = userService.updateUser(id, updateUserDTO);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (DuplicateResourceException e) {
            throw e;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + e.getMessage(), e);
        }
    }

    /**
     * Delete user
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }

    /**
     * Activate user
     */
    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate user", description = "Activate a user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User activated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> activateUser(@PathVariable String id) {
        try {
            UserDTO user = userService.activateUser(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to activate user: " + e.getMessage(), e);
        }
    }

    /**
     * Deactivate user
     */
    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate user", description = "Deactivate a user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> deactivateUser(@PathVariable String id) {
        try {
            UserDTO user = userService.deactivateUser(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to deactivate user: " + e.getMessage(), e);
        }
    }

    /**
     * Ban user
     */
    @PatchMapping("/{id}/ban")
    @Operation(summary = "Ban user", description = "Ban a user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User banned successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> banUser(@PathVariable String id) {
        try {
            UserDTO user = userService.banUser(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to ban user: " + e.getMessage(), e);
        }
    }

    /**
     * Verify user email
     */
    @PatchMapping("/{id}/verify-email")
    @Operation(summary = "Verify user email", description = "Verify a user's email address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User email verified successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> verifyUserEmail(@PathVariable String id) {
        try {
            UserDTO user = userService.verifyUserEmail(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify user email: " + e.getMessage(), e);
        }
    }

    /**
     * Update user password
     */
    @PatchMapping("/{id}/password")
    @Operation(summary = "Update user password", description = "Update a user's password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User password updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> updateUserPassword(@PathVariable String id, @RequestBody String newPassword) {
        try {
            UserDTO user = userService.updateUserPassword(id, newPassword);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user password: " + e.getMessage(), e);
        }
    }

    /**
     * Get user count by role
     */
    @GetMapping("/count/role/{role}")
    @Operation(summary = "Get user count by role", description = "Get the total number of users by role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User count retrieved successfully")
    })
    public ResponseEntity<Long> getUserCountByRole(@PathVariable User.UserRole role) {
        try {
            long count = userService.getUserCountByRole(role);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user count by role: " + e.getMessage(), e);
        }
    }

    /**
     * Get user count by status
     */
    @GetMapping("/count/status/{status}")
    @Operation(summary = "Get user count by status", description = "Get the total number of users by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User count retrieved successfully")
    })
    public ResponseEntity<Long> getUserCountByStatus(@PathVariable User.UserStatus status) {
        try {
            long count = userService.getUserCountByStatus(status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user count by status: " + e.getMessage(), e);
        }
    }

    /**
     * Get user count by role and status
     */
    @GetMapping("/count/role/{role}/status/{status}")
    @Operation(summary = "Get user count by role and status", description = "Get the total number of users by role and status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User count retrieved successfully")
    })
    public ResponseEntity<Long> getUserCountByRoleAndStatus(@PathVariable User.UserRole role, 
                                                          @PathVariable User.UserStatus status) {
        try {
            long count = userService.getUserCountByRoleAndStatus(role, status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user count by role and status: " + e.getMessage(), e);
        }
    }

    /**
     * Get user count by email verification status
     */
    @GetMapping("/count/email-verified/{emailVerified}")
    @Operation(summary = "Get user count by email verification status", description = "Get the total number of users by email verification status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User count retrieved successfully")
    })
    public ResponseEntity<Long> getUserCountByEmailVerified(@PathVariable Boolean emailVerified) {
        try {
            long count = userService.getUserCountByEmailVerified(emailVerified);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user count by email verification status: " + e.getMessage(), e);
        }
    }

    /**
     * Check if email exists
     */
    @GetMapping("/exists/email/{email}")
    @Operation(summary = "Check if email exists", description = "Check if an email address is already registered")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email existence checked successfully")
    })
    public ResponseEntity<Boolean> emailExists(@PathVariable String email) {
        try {
            boolean exists = userService.emailExists(email);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check email existence: " + e.getMessage(), e);
        }
    }

    /**
     * Check if phone exists
     */
    @GetMapping("/exists/phone/{phone}")
    @Operation(summary = "Check if phone exists", description = "Check if a phone number is already registered")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Phone existence checked successfully")
    })
    public ResponseEntity<Boolean> phoneExists(@PathVariable String phone) {
        try {
            boolean exists = userService.phoneExists(phone);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check phone existence: " + e.getMessage(), e);
        }
    }
}
