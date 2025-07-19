package com.skax.eatool.user.integration;

import com.skax.eatool.user.domain.User;
import com.skax.eatool.user.domain.UserStatistics;
import com.skax.eatool.user.domain.UserStatus;
import com.skax.eatool.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 사용자 통계 기능 통합 테스트
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserStatisticsIntegrationTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 생성
        createTestUsers();
    }

    private void createTestUsers() {
        // 테스트용 사용자들을 생성
        List<User> testUsers = List.of(
                createTestUser("user1", "user1@test.com", UserStatus.ACTIVE, "USER"),
                createTestUser("user2", "user2@test.com", UserStatus.ACTIVE, "USER"),
                createTestUser("admin1", "admin1@test.com", UserStatus.ACTIVE, "ADMIN"),
                createTestUser("user3", "user3@test.com", UserStatus.INACTIVE, "USER"),
                createTestUser("admin2", "admin2@test.com", UserStatus.ACTIVE, "ADMIN"));

        for (User user : testUsers) {
            try {
                userService.createUser(user);
            } catch (Exception e) {
                // 이미 존재하는 사용자는 무시
            }
        }
    }

    private User createTestUser(String userId, String email, UserStatus status, String userType) {
        return User.builder()
                .userId(userId)
                .email(email)
                .name("Test User")
                .username("testuser")
                .password("password123")
                .status(status)
                .userType(userType)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetUserStatistics_Integration() {
        // When
        UserStatistics statistics = userService.getUserStatistics();

        // Then
        assertNotNull(statistics);
        assertTrue(statistics.getTotalUsers() >= 5); // 최소 5명의 테스트 사용자
        assertTrue(statistics.getActiveUsers() >= 4); // 최소 4명의 활성 사용자
        assertTrue(statistics.getAdminUsers() >= 2); // 최소 2명의 관리자
        assertEquals("정상", statistics.getServiceStatus());

        // 로그 출력으로 실제 값 확인
        System.out.println("=== 통합 테스트 결과 ===");
        System.out.println("전체 사용자: " + statistics.getTotalUsers());
        System.out.println("활성 사용자: " + statistics.getActiveUsers());
        System.out.println("관리자: " + statistics.getAdminUsers());
        System.out.println("오늘 로그인: " + statistics.getTodayLoginUsers());
        System.out.println("서비스 상태: " + statistics.getServiceStatus());
    }

    @Test
    void testUserStatistics_Consistency() {
        // When
        UserStatistics statistics1 = userService.getUserStatistics();
        UserStatistics statistics2 = userService.getUserStatistics();

        // Then
        // 동일한 트랜잭션 내에서는 일관된 결과가 나와야 함
        assertEquals(statistics1.getTotalUsers(), statistics2.getTotalUsers());
        assertEquals(statistics1.getActiveUsers(), statistics2.getActiveUsers());
        assertEquals(statistics1.getAdminUsers(), statistics2.getAdminUsers());
        assertEquals(statistics1.getServiceStatus(), statistics2.getServiceStatus());
    }

    @Test
    void testUserStatistics_ValidRanges() {
        // When
        UserStatistics statistics = userService.getUserStatistics();

        // Then
        // 유효한 범위 검증
        assertTrue(statistics.getTotalUsers() >= 0);
        assertTrue(statistics.getActiveUsers() >= 0);
        assertTrue(statistics.getAdminUsers() >= 0);
        assertTrue(statistics.getTodayLoginUsers() >= 0);

        // 논리적 관계 검증
        assertTrue(statistics.getActiveUsers() <= statistics.getTotalUsers());
        assertTrue(statistics.getAdminUsers() <= statistics.getTotalUsers());
        assertTrue(statistics.getTodayLoginUsers() <= statistics.getActiveUsers());
    }
}