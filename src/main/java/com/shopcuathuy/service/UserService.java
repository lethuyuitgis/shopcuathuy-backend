package com.shopcuathuy.service;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.shopcuathuy.dto.CreateUserDTO;
import com.shopcuathuy.dto.UpdateUserDTO;
import com.shopcuathuy.dto.UserDTO;
import com.shopcuathuy.entity.User;
import com.shopcuathuy.exception.DuplicateResourceException;
import com.shopcuathuy.exception.ResourceNotFoundException;
import com.shopcuathuy.mapper.UserMapper;
import com.shopcuathuy.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


/**
 * Service class for User operations
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Create a new user
     */
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        // Check if email already exists
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + createUserDTO.getEmail());
        }

        // Check if phone already exists
        if (StringUtils.hasText(createUserDTO.getPhone()) && 
            userRepository.existsByPhone(createUserDTO.getPhone())) {
            throw new DuplicateResourceException("Phone already exists: " + createUserDTO.getPhone());
        }

        // Create user entity
        User user = userMapper.toEntity(createUserDTO);
        user.setPasswordHash(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Save user
        User savedUser = userRepository.save(user);

        // Send user registered message to RabbitMQ
        messageProducerService.sendUserRegisteredMessage(userMapper.toDTO(savedUser));

        // Store user data to MinIO
        storeUserToMinIO(savedUser);

        return userMapper.toDTO(savedUser);
    }

    /**
     * Get user by ID
     */
    @Transactional(readOnly = true)
    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    /**
     * Get user by email
     */
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userMapper.toDTO(user);
    }

    /**
     * Get all users with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by role
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByRole(User.UserRole role) {
        List<User> users = userRepository.findByRole(role);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by role with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByRole(User.UserRole role, Pageable pageable) {
        Page<User> users = userRepository.findByRole(role, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by status
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByStatus(User.UserStatus status) {
        List<User> users = userRepository.findByStatus(status);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by status with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByStatus(User.UserStatus status, Pageable pageable) {
        Page<User> users = userRepository.findByStatus(status, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by role and status
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByRoleAndStatus(User.UserRole role, User.UserStatus status) {
        List<User> users = userRepository.findByRoleAndStatus(role, status);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by role and status with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByRoleAndStatus(User.UserRole role, User.UserStatus status, Pageable pageable) {
        Page<User> users = userRepository.findByRoleAndStatus(role, status, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Search users by name or email
     */
    @Transactional(readOnly = true)
    public List<UserDTO> searchUsers(String search) {
        List<User> users = userRepository.findByNameOrEmailContaining(search);
        return userMapper.toDTOList(users);
    }

    /**
     * Search users by name or email with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> searchUsers(String search, Pageable pageable) {
        Page<User> users = userRepository.findByNameOrEmailContaining(search, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by multiple criteria
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByCriteria(User.UserRole role, User.UserStatus status, 
                                          Boolean emailVerified, String search, Pageable pageable) {
        Page<User> users = userRepository.findByMultipleCriteria(role, status, emailVerified, search, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Update user
     */
    public UserDTO updateUser(String id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Check if email already exists (excluding current user)
        if (StringUtils.hasText(updateUserDTO.getEmail()) && 
            !user.getEmail().equals(updateUserDTO.getEmail()) &&
            userRepository.existsByEmailAndIdNot(updateUserDTO.getEmail(), id)) {
            throw new DuplicateResourceException("Email already exists: " + updateUserDTO.getEmail());
        }

        // Check if phone already exists (excluding current user)
        if (StringUtils.hasText(updateUserDTO.getPhone()) &&
            !user.getPhone().equals(updateUserDTO.getPhone()) &&
            userRepository.existsByPhoneAndIdNot(updateUserDTO.getPhone(), id)) {
            throw new DuplicateResourceException("Phone already exists: " + updateUserDTO.getPhone());
        }

        // Update user fields
        userMapper.updateEntity(updateUserDTO, user);
        user.setUpdatedAt(LocalDateTime.now());

        // Save updated user
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Delete user
     */
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    /**
     * Activate user
     */
    public UserDTO activateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        user.setStatus(User.UserStatus.ACTIVE);
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Deactivate user
     */
    public UserDTO deactivateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        user.setStatus(User.UserStatus.INACTIVE);
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Ban user
     */
    public UserDTO banUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        user.setStatus(User.UserStatus.BANNED);
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Verify user email
     */
    public UserDTO verifyUserEmail(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        user.setEmailVerified(true);
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Update user password
     */
    public UserDTO updateUserPassword(String id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Update user last login
     */
    public void updateUserLastLogin(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * Get user count by role
     */
    @Transactional(readOnly = true)
    public long getUserCountByRole(User.UserRole role) {
        return userRepository.countByRole(role);
    }

    /**
     * Get user count by status
     */
    @Transactional(readOnly = true)
    public long getUserCountByStatus(User.UserStatus status) {
        return userRepository.countByStatus(status);
    }

    /**
     * Get user count by role and status
     */
    @Transactional(readOnly = true)
    public long getUserCountByRoleAndStatus(User.UserRole role, User.UserStatus status) {
        return userRepository.countByRoleAndStatus(role, status);
    }

    /**
     * Get user count by email verification status
     */
    @Transactional(readOnly = true)
    public long getUserCountByEmailVerified(Boolean emailVerified) {
        return userRepository.countByEmailVerified(emailVerified);
    }

    /**
     * Get user count created after date
     */
    @Transactional(readOnly = true)
    public long getUserCountCreatedAfter(LocalDateTime date) {
        return userRepository.countByCreatedAtAfter(date);
    }

    /**
     * Get user count created before date
     */
    @Transactional(readOnly = true)
    public long getUserCountCreatedBefore(LocalDateTime date) {
        return userRepository.countByCreatedAtBefore(date);
    }

    /**
     * Get user count by last login after date
     */
    @Transactional(readOnly = true)
    public long getUserCountByLastLoginAfter(LocalDateTime date) {
        return userRepository.countByLastLoginAfter(date);
    }

    /**
     * Get user count by last login before date
     */
    @Transactional(readOnly = true)
    public long getUserCountByLastLoginBefore(LocalDateTime date) {
        return userRepository.countByLastLoginBefore(date);
    }

    /**
     * Check if email exists
     */
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Check if phone exists
     */
    @Transactional(readOnly = true)
    public boolean phoneExists(String phone) {
        return userRepository.existsByPhone(phone);
    }

    /**
     * Check if email exists excluding user
     */
    @Transactional(readOnly = true)
    public boolean emailExistsExcludingUser(String email, String userId) {
        return userRepository.existsByEmailAndIdNot(email, userId);
    }

    /**
     * Check if phone exists excluding user
     */
    @Transactional(readOnly = true)
    public boolean phoneExistsExcludingUser(String phone, String userId) {
        return userRepository.existsByPhoneAndIdNot(phone, userId);
    }

    /**
     * Get users by address containing
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByAddress(String address) {
        List<User> users = userRepository.findByAddressContainingIgnoreCase(address);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by address containing with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByAddress(String address, Pageable pageable) {
        Page<User> users = userRepository.findByAddressContainingIgnoreCase(address, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by avatar containing
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByAvatar(String avatar) {
        List<User> users = userRepository.findByAvatarContaining(avatar);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by avatar containing with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByAvatar(String avatar, Pageable pageable) {
        Page<User> users = userRepository.findByAvatarContaining(avatar, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users without avatar
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersWithoutAvatar() {
        List<User> users = userRepository.findByAvatarIsNull();
        return userMapper.toDTOList(users);
    }

    /**
     * Get users without avatar with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersWithoutAvatar(Pageable pageable) {
        Page<User> users = userRepository.findByAvatarIsNull(pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users with avatar
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersWithAvatar() {
        List<User> users = userRepository.findByAvatarIsNotNull();
        return userMapper.toDTOList(users);
    }

    /**
     * Get users with avatar with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersWithAvatar(Pageable pageable) {
        Page<User> users = userRepository.findByAvatarIsNotNull(pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by name starting with
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByNameStartingWith(String name) {
        List<User> users = userRepository.findByNameStartingWithIgnoreCase(name);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by name starting with with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByNameStartingWith(String name, Pageable pageable) {
        Page<User> users = userRepository.findByNameStartingWithIgnoreCase(name, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by email starting with
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByEmailStartingWith(String email) {
        List<User> users = userRepository.findByEmailStartingWithIgnoreCase(email);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by email starting with with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByEmailStartingWith(String email, Pageable pageable) {
        Page<User> users = userRepository.findByEmailStartingWithIgnoreCase(email, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by phone starting with
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByPhoneStartingWith(String phone) {
        List<User> users = userRepository.findByPhoneStartingWith(phone);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by phone starting with with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByPhoneStartingWith(String phone, Pageable pageable) {
        Page<User> users = userRepository.findByPhoneStartingWith(phone, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by name ending with
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByNameEndingWith(String name) {
        List<User> users = userRepository.findByNameEndingWithIgnoreCase(name);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by name ending with with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByNameEndingWith(String name, Pageable pageable) {
        Page<User> users = userRepository.findByNameEndingWithIgnoreCase(name, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by email ending with
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByEmailEndingWith(String email) {
        List<User> users = userRepository.findByEmailEndingWithIgnoreCase(email);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by email ending with with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByEmailEndingWith(String email, Pageable pageable) {
        Page<User> users = userRepository.findByEmailEndingWithIgnoreCase(email, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Get users by phone ending with
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByPhoneEndingWith(String phone) {
        List<User> users = userRepository.findByPhoneEndingWith(phone);
        return userMapper.toDTOList(users);
    }

    /**
     * Get users by phone ending with with pagination
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersByPhoneEndingWith(String phone, Pageable pageable) {
        Page<User> users = userRepository.findByPhoneEndingWith(phone, pageable);
        return users.map(userMapper::toDTO);
    }

    /**
     * Export user data
     */
    public String exportUserData(LocalDateTime startDate, LocalDateTime endDate, String format) {
        List<User> users = userRepository.findByCreatedAtBetween(startDate, endDate);
        
        // Generate export data
        String exportData = generateUserExportData(users, format);
        
        // Store to MinIO
        String fileName = "user-export-" + System.currentTimeMillis() + "." + format;
        String fileUrl = fileStorageService.uploadUserExport(fileName, exportData);
        
        return fileUrl;
    }

    /**
     * Store user data to MinIO
     */
    private void storeUserToMinIO(User user) {
        try {
            String userData = createUserDataJson(user);
            String fileName = "users/" + user.getStatus() + "/" +
                            user.getCreatedAt().toLocalDate() + "/" +
                            user.getId() + ".json";
            
            fileStorageService.uploadUserData(fileName, userData);
        } catch (Exception e) {
            System.err.println("Failed to store user data to MinIO: " + e.getMessage());
        }
    }

    /**
     * Create user data JSON
     */
    private String createUserDataJson(User user) {
        return String.format(
            "{\"id\":%d,\"email\":\"%s\",\"fullName\":\"%s\",\"role\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\"}",
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getRole(),
            user.getStatus(),
            user.getCreatedAt()
        );
    }

    /**
     * Generate user export data
     */
    private String generateUserExportData(List<User> users, String format) {
        if ("json".equalsIgnoreCase(format)) {
            return generateUserJsonExport(users);
        } else if ("csv".equalsIgnoreCase(format)) {
            return generateUserCsvExport(users);
        }
        return generateUserJsonExport(users);
    }

    /**
     * Generate JSON export
     */
    private String generateUserJsonExport(List<User> users) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            json.append(createUserDataJson(user));
            if (i < users.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Generate CSV export
     */
    private String generateUserCsvExport(List<User> users) {
        StringBuilder csv = new StringBuilder("id,email,fullName,role,status,createdAt\n");
        for (User user : users) {
            csv.append(String.format("%d,%s,%s,%s,%s,%s\n",
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt()
            ));
        }
        return csv.toString();
    }
}
