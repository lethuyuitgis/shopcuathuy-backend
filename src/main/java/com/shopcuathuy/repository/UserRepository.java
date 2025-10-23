package com.shopcuathuy.repository;

import com.shopcuathuy.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Repository interface for User entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by email and status
     */
    Optional<User> findByEmailAndStatus(String email, User.UserStatus status);

    /**
     * Find user by phone
     */
    Optional<User> findByPhone(String phone);

    /**
     * Find users by role
     */
    List<User> findByRole(User.UserRole role);

    /**
     * Find users by status
     */
    List<User> findByStatus(User.UserStatus status);

    /**
     * Find users by role and status
     */
    List<User> findByRoleAndStatus(User.UserRole role, User.UserStatus status);

    /**
     * Find active users
     */
    List<User> findByStatusAndEmailVerified(User.UserStatus status, Boolean emailVerified);

    /**
     * Find users by name containing
     */
    List<User> findByNameContainingIgnoreCase(String name);

    /**
     * Find users by email containing
     */
    List<User> findByEmailContainingIgnoreCase(String email);

    /**
     * Find users by phone containing
     */
    List<User> findByPhoneContaining(String phone);

    /**
     * Find users created after date
     */
    List<User> findByCreatedAtAfter(LocalDateTime date);

    /**
     * Find users created before date
     */
    List<User> findByCreatedAtBefore(LocalDateTime date);

    /**
     * Find users by last login after date
     */
    List<User> findByLastLoginAfter(LocalDateTime date);

    /**
     * Find users by last login before date
     */
    List<User> findByLastLoginBefore(LocalDateTime date);

    /**
     * Find users with pagination
     */
    Page<User> findByStatus(User.UserStatus status, Pageable pageable);

    /**
     * Find users by role with pagination
     */
    Page<User> findByRole(User.UserRole role, Pageable pageable);

    /**
     * Find users by role and status with pagination
     */
    Page<User> findByRoleAndStatus(User.UserRole role, User.UserStatus status, Pageable pageable);

    /**
     * Find users by name containing with pagination
     */
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Find users by email containing with pagination
     */
    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    /**
     * Find users by phone containing with pagination
     */
    Page<User> findByPhoneContaining(String phone, Pageable pageable);

    /**
     * Find users created between dates
     */
    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find users by last login between dates
     */
    List<User> findByLastLoginBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Count users by role
     */
    long countByRole(User.UserRole role);

    /**
     * Count users by status
     */
    long countByStatus(User.UserStatus status);

    /**
     * Count users by role and status
     */
    long countByRoleAndStatus(User.UserRole role, User.UserStatus status);

    /**
     * Count active users
     */
    long countByStatusAndEmailVerified(User.UserStatus status, Boolean emailVerified);

    /**
     * Count users created after date
     */
    long countByCreatedAtAfter(LocalDateTime date);

    /**
     * Count users created before date
     */
    long countByCreatedAtBefore(LocalDateTime date);

    /**
     * Count users by last login after date
     */
    long countByLastLoginAfter(LocalDateTime date);

    /**
     * Count users by last login before date
     */
    long countByLastLoginBefore(LocalDateTime date);

    /**
     * Find users with email verification status
     */
    List<User> findByEmailVerified(Boolean emailVerified);

    /**
     * Find users by email verification status with pagination
     */
    Page<User> findByEmailVerified(Boolean emailVerified, Pageable pageable);

    /**
     * Count users by email verification status
     */
    long countByEmailVerified(Boolean emailVerified);

    /**
     * Find users by name or email containing
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<User> findByNameOrEmailContaining(@Param("search") String search);

    /**
     * Find users by name or email containing with pagination
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<User> findByNameOrEmailContaining(@Param("search") String search, Pageable pageable);

    /**
     * Find users by multiple criteria
     */
    @Query("SELECT u FROM User u WHERE " +
           "(:role IS NULL OR u.role = :role) AND " +
           "(:status IS NULL OR u.status = :status) AND " +
           "(:emailVerified IS NULL OR u.emailVerified = :emailVerified) AND " +
           "(:search IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<User> findByMultipleCriteria(@Param("role") User.UserRole role,
                                    @Param("status") User.UserStatus status,
                                    @Param("emailVerified") Boolean emailVerified,
                                    @Param("search") String search,
                                    Pageable pageable);

    /**
     * Find users by date range
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.createdAt BETWEEN :startDate AND :endDate")
    List<User> findByDateRange(@Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);

    /**
     * Find users by date range with pagination
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.createdAt BETWEEN :startDate AND :endDate")
    Page<User> findByDateRange(@Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate,
                              Pageable pageable);

    /**
     * Find users by last login date range
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.lastLogin BETWEEN :startDate AND :endDate")
    List<User> findByLastLoginDateRange(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    /**
     * Find users by last login date range with pagination
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.lastLogin BETWEEN :startDate AND :endDate")
    Page<User> findByLastLoginDateRange(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate,
                                       Pageable pageable);

    /**
     * Find recent users
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.createdAt >= :date")
    List<User> findRecentUsers(@Param("date") LocalDateTime date);

    /**
     * Find recent users with pagination
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.createdAt >= :date")
    Page<User> findRecentUsers(@Param("date") LocalDateTime date, Pageable pageable);

    /**
     * Find active users by role
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.role = :role AND u.status = 'ACTIVE'")
    List<User> findActiveUsersByRole(@Param("role") User.UserRole role);

    /**
     * Find active users by role with pagination
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.role = :role AND u.status = 'ACTIVE'")
    Page<User> findActiveUsersByRole(@Param("role") User.UserRole role, Pageable pageable);

    /**
     * Find users with unverified email
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.emailVerified = false")
    List<User> findUsersWithUnverifiedEmail();

    /**
     * Find users with unverified email with pagination
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.emailVerified = false")
    Page<User> findUsersWithUnverifiedEmail(Pageable pageable);

    /**
     * Find users by multiple roles
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.role IN :roles")
    List<User> findByRoleIn(@Param("roles") List<User.UserRole> roles);

    /**
     * Find users by multiple roles with pagination
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.role IN :roles")
    Page<User> findByRoleIn(@Param("roles") List<User.UserRole> roles, Pageable pageable);

    /**
     * Find users by multiple statuses
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.status IN :statuses")
    List<User> findByStatusIn(@Param("statuses") List<User.UserStatus> statuses);

    /**
     * Find users by multiple statuses with pagination
     */
    @Query("SELECT u FROM User u WHERE " +
           "u.status IN :statuses")
    Page<User> findByStatusIn(@Param("statuses") List<User.UserStatus> statuses, Pageable pageable);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Check if phone exists
     */
    boolean existsByPhone(String phone);

    /**
     * Check if email exists excluding user
     */
    boolean existsByEmailAndIdNot(String email, String id);

    /**
     * Check if phone exists excluding user
     */
    boolean existsByPhoneAndIdNot(String phone, String id);

    /**
     * Find users by address containing
     */
    List<User> findByAddressContainingIgnoreCase(String address);

    /**
     * Find users by address containing with pagination
     */
    Page<User> findByAddressContainingIgnoreCase(String address, Pageable pageable);

    /**
     * Find users by avatar containing
     */
    List<User> findByAvatarContaining(String avatar);

    /**
     * Find users by avatar containing with pagination
     */
    Page<User> findByAvatarContaining(String avatar, Pageable pageable);

    /**
     * Find users without avatar
     */
    List<User> findByAvatarIsNull();

    /**
     * Find users without avatar with pagination
     */
    Page<User> findByAvatarIsNull(Pageable pageable);

    /**
     * Find users with avatar
     */
    List<User> findByAvatarIsNotNull();

    /**
     * Find users with avatar with pagination
     */
    Page<User> findByAvatarIsNotNull(Pageable pageable);

    /**
     * Find users by name starting with
     */
    List<User> findByNameStartingWithIgnoreCase(String name);

    /**
     * Find users by name starting with with pagination
     */
    Page<User> findByNameStartingWithIgnoreCase(String name, Pageable pageable);

    /**
     * Find users by email starting with
     */
    List<User> findByEmailStartingWithIgnoreCase(String email);

    /**
     * Find users by email starting with with pagination
     */
    Page<User> findByEmailStartingWithIgnoreCase(String email, Pageable pageable);

    /**
     * Find users by phone starting with
     */
    List<User> findByPhoneStartingWith(String phone);

    /**
     * Find users by phone starting with with pagination
     */
    Page<User> findByPhoneStartingWith(String phone, Pageable pageable);

    /**
     * Find users by name ending with
     */
    List<User> findByNameEndingWithIgnoreCase(String name);

    /**
     * Find users by name ending with with pagination
     */
    Page<User> findByNameEndingWithIgnoreCase(String name, Pageable pageable);

    /**
     * Find users by email ending with
     */
    List<User> findByEmailEndingWithIgnoreCase(String email);

    /**
     * Find users by email ending with with pagination
     */
    Page<User> findByEmailEndingWithIgnoreCase(String email, Pageable pageable);

    /**
     * Find users by phone ending with
     */
    List<User> findByPhoneEndingWith(String phone);

    /**
     * Find users by phone ending with with pagination
     */
    Page<User> findByPhoneEndingWith(String phone, Pageable pageable);
}
