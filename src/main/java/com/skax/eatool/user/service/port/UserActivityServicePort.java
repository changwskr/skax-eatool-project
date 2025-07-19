package com.skax.eatool.user.service.port;

import com.skax.eatool.user.domain.UserActivity;
import com.skax.eatool.user.domain.UserActivityStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 사용자 활동 로그 서비스 포트 인터페이스
 */
public interface UserActivityServicePort {

    /**
     * 활동 로그 저장
     */
    UserActivity saveActivity(UserActivity userActivity);

    /**
     * 로그인 활동 로그 생성 및 저장
     */
    UserActivity createLoginActivity(String userId, String ipAddress, String userAgent, String sessionId);

    /**
     * 로그아웃 활동 로그 생성 및 저장
     */
    UserActivity createLogoutActivity(String userId, String ipAddress, String userAgent, String sessionId);

    /**
     * 사용자 생성 활동 로그 생성 및 저장
     */
    UserActivity createUserCreateActivity(String userId, String ipAddress, String userAgent);

    /**
     * 사용자 수정 활동 로그 생성 및 저장
     */
    UserActivity createUserUpdateActivity(String userId, String ipAddress, String userAgent);

    /**
     * 사용자 삭제 활동 로그 생성 및 저장
     */
    UserActivity createUserDeleteActivity(String userId, String ipAddress, String userAgent);

    /**
     * ID로 활동 로그 조회
     */
    UserActivity getActivityById(Long id);

    /**
     * 모든 활동 로그 조회
     */
    List<UserActivity> getAllActivities();

    /**
     * 페이지네이션을 사용한 활동 로그 조회
     */
    Page<UserActivity> getActivities(Pageable pageable);

    /**
     * 사용자 ID로 활동 로그 조회
     */
    List<UserActivity> getActivitiesByUserId(String userId);

    /**
     * 사용자 ID와 페이지네이션으로 활동 로그 조회
     */
    Page<UserActivity> getActivitiesByUserId(String userId, Pageable pageable);

    /**
     * 활동 타입으로 활동 로그 조회
     */
    List<UserActivity> getActivitiesByType(String activityType);

    /**
     * 상태로 활동 로그 조회
     */
    List<UserActivity> getActivitiesByStatus(String status);

    /**
     * IP 주소로 활동 로그 조회
     */
    List<UserActivity> getActivitiesByIpAddress(String ipAddress);

    /**
     * 날짜 범위로 활동 로그 조회
     */
    List<UserActivity> getActivitiesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 사용자 ID와 날짜 범위로 활동 로그 조회
     */
    List<UserActivity> getActivitiesByUserIdAndDateRange(String userId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 최근 활동 로그 조회
     */
    List<UserActivity> getRecentActivities(Pageable pageable);

    /**
     * 활동 로그 통계 조회
     */
    UserActivityStatistics getActivityStatistics();

    /**
     * 활동 로그 삭제
     */
    void deleteActivity(Long id);

    /**
     * 오래된 활동 로그 삭제
     */
    void deleteOldActivities();

    /**
     * 고급 검색
     */
    List<UserActivity> searchActivities(String userId, String activityType, String status,
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