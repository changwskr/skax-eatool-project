package com.skax.eatool.user.service;

import com.skax.eatool.user.domain.User;
import com.skax.eatool.user.domain.UserStatistics;
import com.skax.eatool.user.domain.UserStatus;
import com.skax.eatool.user.service.port.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * UserService 통계 조회 메서드 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private List<User> testUsers;

    @BeforeEach
    void setUp() {
        // 테스트용 사용자 데이터 생성
        testUsers = Arrays.asList(
                createTestUser(1L, "user1", "user1@test.com", UserStatus.ACTIVE, "USER"),
                createTestUser(2L, "user2", "user2@test.com", UserStatus.ACTIVE, "USER"),
                createTestUser(3L, "admin1", "admin1@test.com", UserStatus.ACTIVE, "ADMIN"),
                createTestUser(4L, "user3", "user3@test.com", UserStatus.INACTIVE, "USER"),
                createTestUser(5L, "admin2", "admin2@test.com", UserStatus.ACTIVE, "ADMIN"));
    }

    private User createTestUser(Long id, String userId, String email, UserStatus status, String userType) {
        return User.builder()
                .id(id)
                .userId(userId)
                .email(email)
                .name("Test User")
                .status(status)
                .userType(userType)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetUserStatistics_Success() {
        // Given
        when(userRepositoryPort.findAll()).thenReturn(testUsers);

        // When
        UserStatistics statistics = userService.getUserStatistics();

        // Then
        assertNotNull(statistics);
        assertEquals(5, statistics.getTotalUsers());
        assertEquals(4, statistics.getActiveUsers()); // ACTIVE 상태 사용자 4명
        assertEquals(2, statistics.getAdminUsers()); // ADMIN 타입 사용자 2명
        assertEquals(0, statistics.getTodayLoginUsers()); // 기본값 0
        assertEquals("정상", statistics.getServiceStatus());

        verify(userRepositoryPort, times(1)).findAll();
    }

    @Test
    void testGetUserStatistics_EmptyUserList() {
        // Given
        when(userRepositoryPort.findAll()).thenReturn(Arrays.asList());

        // When
        UserStatistics statistics = userService.getUserStatistics();

        // Then
        assertNotNull(statistics);
        assertEquals(0, statistics.getTotalUsers());
        assertEquals(0, statistics.getActiveUsers());
        assertEquals(0, statistics.getAdminUsers());
        assertEquals(0, statistics.getTodayLoginUsers());
        assertEquals("정상", statistics.getServiceStatus());
    }

    @Test
    void testGetUserStatistics_OnlyActiveUsers() {
        // Given
        List<User> activeUsers = Arrays.asList(
                createTestUser(1L, "user1", "user1@test.com", UserStatus.ACTIVE, "USER"),
                createTestUser(2L, "user2", "user2@test.com", UserStatus.ACTIVE, "USER"));
        when(userRepositoryPort.findAll()).thenReturn(activeUsers);

        // When
        UserStatistics statistics = userService.getUserStatistics();

        // Then
        assertNotNull(statistics);
        assertEquals(2, statistics.getTotalUsers());
        assertEquals(2, statistics.getActiveUsers());
        assertEquals(0, statistics.getAdminUsers());
        assertEquals(0, statistics.getTodayLoginUsers());
    }

    @Test
    void testGetUserStatistics_OnlyAdminUsers() {
        // Given
        List<User> adminUsers = Arrays.asList(
                createTestUser(1L, "admin1", "admin1@test.com", UserStatus.ACTIVE, "ADMIN"),
                createTestUser(2L, "admin2", "admin2@test.com", UserStatus.ACTIVE, "ADMIN"),
                createTestUser(3L, "admin3", "admin3@test.com", UserStatus.INACTIVE, "ADMIN"));
        when(userRepositoryPort.findAll()).thenReturn(adminUsers);

        // When
        UserStatistics statistics = userService.getUserStatistics();

        // Then
        assertNotNull(statistics);
        assertEquals(3, statistics.getTotalUsers());
        assertEquals(2, statistics.getActiveUsers());
        assertEquals(3, statistics.getAdminUsers()); // 모든 ADMIN 타입 사용자
        assertEquals(0, statistics.getTodayLoginUsers());
    }
}