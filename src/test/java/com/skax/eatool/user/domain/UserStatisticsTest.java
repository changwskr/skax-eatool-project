package com.skax.eatool.user.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * UserStatistics 클래스 단위 테스트
 */
class UserStatisticsTest {

    @Test
    void testUserStatisticsBuilder() {
        // Given
        long totalUsers = 100;
        long activeUsers = 80;
        long adminUsers = 5;
        long todayLoginUsers = 25;
        String serviceStatus = "정상";

        // When
        UserStatistics statistics = UserStatistics.builder()
                .totalUsers(totalUsers)
                .activeUsers(activeUsers)
                .adminUsers(adminUsers)
                .todayLoginUsers(todayLoginUsers)
                .serviceStatus(serviceStatus)
                .build();

        // Then
        assertNotNull(statistics);
        assertEquals(totalUsers, statistics.getTotalUsers());
        assertEquals(activeUsers, statistics.getActiveUsers());
        assertEquals(adminUsers, statistics.getAdminUsers());
        assertEquals(todayLoginUsers, statistics.getTodayLoginUsers());
        assertEquals(serviceStatus, statistics.getServiceStatus());
    }

    @Test
    void testUserStatisticsDefaultValues() {
        // When
        UserStatistics statistics = UserStatistics.builder().build();

        // Then
        assertNotNull(statistics);
        assertEquals(0, statistics.getTotalUsers());
        assertEquals(0, statistics.getActiveUsers());
        assertEquals(0, statistics.getAdminUsers());
        assertEquals(0, statistics.getTodayLoginUsers());
        assertNull(statistics.getServiceStatus());
    }

    @Test
    void testUserStatisticsToString() {
        // Given
        UserStatistics statistics = UserStatistics.builder()
                .totalUsers(100)
                .activeUsers(80)
                .adminUsers(5)
                .todayLoginUsers(25)
                .serviceStatus("정상")
                .build();

        // When
        String result = statistics.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("totalUsers=100"));
        assertTrue(result.contains("activeUsers=80"));
        assertTrue(result.contains("adminUsers=5"));
        assertTrue(result.contains("todayLoginUsers=25"));
        assertTrue(result.contains("serviceStatus=정상"));
    }
}