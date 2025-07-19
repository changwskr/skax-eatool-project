package com.skax.eatool.user.service.port;

import com.skax.eatool.user.domain.UserActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 사용자 활동 로그 리포지토리 포트 인터페이스
 */
public interface UserActivityRepositoryPort {

        /**
         * 활동 로그 저장
         */
        UserActivity save(UserActivity userActivity);

        /**
         * ID로 활동 로그 조회
         */
        Optional<UserActivity> findById(Long id);

        /**
         * 모든 활동 로그 조회
         */
        List<UserActivity> findAll();

        /**
         * 페이지네이션을 사용한 활동 로그 조회
         */
        Page<UserActivity> findAll(Pageable pageable);

        /**
         * 사용자 ID로 활동 로그 조회
         */
        List<UserActivity> findByUserId(String userId);

        /**
         * 사용자 ID와 페이지네이션으로 활동 로그 조회
         */
        Page<UserActivity> findByUserId(String userId, Pageable pageable);

        /**
         * 활동 타입으로 활동 로그 조회
         */
        List<UserActivity> findByActivityType(String activityType);

        /**
         * 상태로 활동 로그 조회
         */
        List<UserActivity> findByStatus(String status);

        /**
         * IP 주소로 활동 로그 조회
         */
        List<UserActivity> findByIpAddress(String ipAddress);

        /**
         * 날짜 범위로 활동 로그 조회
         */
        List<UserActivity> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

        /**
         * 사용자 ID와 날짜 범위로 활동 로그 조회
         */
        List<UserActivity> findByUserIdAndTimestampBetween(String userId, LocalDateTime startDate,
                        LocalDateTime endDate);

        /**
         * 최근 활동 로그 조회
         */
        List<UserActivity> findRecentActivities(Pageable pageable);

        /**
         * 오늘 활동 로그 수 조회
         */
        long countTodayActivities();

        /**
         * 이번 주 활동 로그 수 조회
         */
        long countThisWeekActivities();

        /**
         * 실패한 로그 수 조회
         */
        long countFailedLogs();

        /**
         * 활성 사용자 수 조회 (오늘 로그인한 사용자)
         */
        long countActiveUsers();

        /**
         * 활동 로그 삭제
         */
        void deleteById(Long id);

        /**
         * 오래된 활동 로그 삭제 (30일 이상)
         */
        void deleteOldActivities();

        /**
         * 고급 검색
         */
        List<UserActivity> findByAdvancedSearch(String userId, String activityType, String status,
                        String ipAddress, LocalDateTime startDate, LocalDateTime endDate);

        /**
         * 고급 검색 결과 수 조회
         */
        long countByAdvancedSearch(String userId, String activityType, String status,
                        String ipAddress, LocalDateTime startDate, LocalDateTime endDate);

        /**
         * 활동 타입별 통계 조회
         */
        List<Map<String, Object>> getActivityTypeStatistics();

        /**
         * 시간대별 활동 통계 조회
         */
        List<Map<String, Object>> getHourlyStatistics();
}
