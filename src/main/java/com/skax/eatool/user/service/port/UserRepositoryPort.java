package com.skax.eatool.user.service.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.skax.eatool.user.domain.User;
import com.skax.eatool.user.domain.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(String userId);

    User save(User user);

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Page<User> findAdminUsers(Pageable pageable);

    User updateStatus(User user);

    Page<User> findAdminUsers(Pageable pageable, List<Long> userIds);

    void deleteById(Long id);

    Page<User> searchUsers(String keyword, Pageable pageable);

    /**
     * 상태별 사용자 수 조회
     */
    long countByStatus(UserStatus status);

    /**
     * 사용자 타입별 사용자 수 조회
     */
    long countByUserType(String userType);

    /**
     * 오늘 로그인한 사용자 수 조회
     */
    long countTodayLoginUsers();
}
