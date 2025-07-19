package com.skax.eatool.user.service;

import com.skax.eatool.user.domain.UserActivity;
import com.skax.eatool.user.domain.UserActivityStatistics;
import com.skax.eatool.user.service.port.UserActivityRepositoryPort;
import com.skax.eatool.user.service.port.UserActivityServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 사용자 활동 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserActivityService implements UserActivityServicePort {

    private final UserActivityRepositoryPort userActivityRepositoryPort;

    @Override
    @Transactional
    public UserActivity saveActivity(UserActivity userActivity) {
        log.info("[UserActivityService] saveActivity START - userId: {}", userActivity.getUserId());

        UserActivity result = userActivityRepositoryPort.save(userActivity);

        log.info("[UserActivityService] saveActivity END - id: {}", result.getId());
        return result;
    }

    @Override
    @Transactional
    public UserActivity createLoginActivity(String userId, String ipAddress, String userAgent, String sessionId) {
        log.info("[UserActivityService] createLoginActivity START - userId: {}", userId);

        UserActivity activity = UserActivity.builder()
                .userId(userId)
                .activityType("LOGIN")
                .description("사용자 로그인 성공")
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .sessionId(sessionId)
                .status("SUCCESS")
                .activityTimestamp(LocalDateTime.now())
                .processingTime(0L)
                .additionalInfo("{\"loginAttempt\": 1}")
                .build();

        UserActivity result = saveActivity(activity);

        log.info("[UserActivityService] createLoginActivity END - id: {}", result.getId());
        return result;
    }

    @Override
    @Transactional
    public UserActivity createLogoutActivity(String userId, String ipAddress, String userAgent, String sessionId) {
        log.info("[UserActivityService] createLogoutActivity START - userId: {}", userId);

        UserActivity activity = UserActivity.builder()
                .userId(userId)
                .activityType("LOGOUT")
                .description("사용자 로그아웃")
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .sessionId(sessionId)
                .status("SUCCESS")
                .activityTimestamp(LocalDateTime.now())
                .processingTime(0L)
                .additionalInfo("{\"logoutReason\": \"user_request\"}")
                .build();

        UserActivity result = saveActivity(activity);

        log.info("[UserActivityService] createLogoutActivity END - id: {}", result.getId());
        return result;
    }

    @Override
    @Transactional
    public UserActivity createUserCreateActivity(String userId, String ipAddress, String userAgent) {
        log.info("[UserActivityService] createUserCreateActivity START - userId: {}", userId);

        UserActivity activity = UserActivity.builder()
                .userId(userId)
                .activityType("CREATE")
                .description("새 사용자 등록")
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .sessionId(UUID.randomUUID().toString())
                .status("SUCCESS")
                .activityTimestamp(LocalDateTime.now())
                .processingTime(0L)
                .additionalInfo("{\"operation\": \"user_registration\"}")
                .build();

        UserActivity result = saveActivity(activity);

        log.info("[UserActivityService] createUserCreateActivity END - id: {}", result.getId());
        return result;
    }

    @Override
    @Transactional
    public UserActivity createUserUpdateActivity(String userId, String ipAddress, String userAgent) {
        log.info("[UserActivityService] createUserUpdateActivity START - userId: {}", userId);

        UserActivity activity = UserActivity.builder()
                .userId(userId)
                .activityType("UPDATE")
                .description("사용자 정보 수정")
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .sessionId(UUID.randomUUID().toString())
                .status("SUCCESS")
                .activityTimestamp(LocalDateTime.now())
                .processingTime(0L)
                .additionalInfo("{\"operation\": \"user_update\"}")
                .build();

        UserActivity result = saveActivity(activity);

        log.info("[UserActivityService] createUserUpdateActivity END - id: {}", result.getId());
        return result;
    }

    @Override
    @Transactional
    public UserActivity createUserDeleteActivity(String userId, String ipAddress, String userAgent) {
        log.info("[UserActivityService] createUserDeleteActivity START - userId: {}", userId);

        UserActivity activity = UserActivity.builder()
                .userId(userId)
                .activityType("DELETE")
                .description("사용자 삭제")
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .sessionId(UUID.randomUUID().toString())
                .status("SUCCESS")
                .activityTimestamp(LocalDateTime.now())
                .processingTime(0L)
                .additionalInfo("{\"operation\": \"user_deletion\"}")
                .build();

        UserActivity result = saveActivity(activity);

        log.info("[UserActivityService] createUserDeleteActivity END - id: {}", result.getId());
        return result;
    }

    @Override
    public UserActivity getActivityById(Long id) {
        log.info("[UserActivityService] getActivityById START - id: {}", id);

        UserActivity result = userActivityRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));

        log.info("[UserActivityService] getActivityById END - id: {}", result.getId());
        return result;
    }

    @Override
    public List<UserActivity> getAllActivities() {
        log.info("[UserActivityService] getAllActivities START");

        List<UserActivity> result = userActivityRepositoryPort.findAll();

        log.info("[UserActivityService] getAllActivities END - count: {}", result.size());
        return result;
    }

    @Override
    public Page<UserActivity> getActivities(Pageable pageable) {
        log.info("[UserActivityService] getActivities START - pageable: {}", pageable);

        Page<UserActivity> result = userActivityRepositoryPort.findAll(pageable);

        log.info("[UserActivityService] getActivities END - totalElements: {}", result.getTotalElements());
        return result;
    }

    @Override
    public List<UserActivity> getActivitiesByUserId(String userId) {
        log.info("[UserActivityService] getActivitiesByUserId START - userId: {}", userId);

        List<UserActivity> result = userActivityRepositoryPort.findByUserId(userId);

        log.info("[UserActivityService] getActivitiesByUserId END - count: {}", result.size());
        return result;
    }

    @Override
    public Page<UserActivity> getActivitiesByUserId(String userId, Pageable pageable) {
        log.info("[UserActivityService] getActivitiesByUserId(Pageable) START - userId: {}, pageable: {}",
                userId, pageable);

        Page<UserActivity> result = userActivityRepositoryPort.findByUserId(userId, pageable);

        log.info("[UserActivityService] getActivitiesByUserId(Pageable) END - totalElements: {}",
                result.getTotalElements());
        return result;
    }

    @Override
    public List<UserActivity> getActivitiesByType(String activityType) {
        log.info("[UserActivityService] getActivitiesByType START - activityType: {}", activityType);

        List<UserActivity> result = userActivityRepositoryPort.findByActivityType(activityType);

        log.info("[UserActivityService] getActivitiesByType END - count: {}", result.size());
        return result;
    }

    @Override
    public List<UserActivity> getActivitiesByStatus(String status) {
        log.info("[UserActivityService] getActivitiesByStatus START - status: {}", status);

        List<UserActivity> result = userActivityRepositoryPort.findByStatus(status);

        log.info("[UserActivityService] getActivitiesByStatus END - count: {}", result.size());
        return result;
    }

    @Override
    public List<UserActivity> getActivitiesByIpAddress(String ipAddress) {
        log.info("[UserActivityService] getActivitiesByIpAddress START - ipAddress: {}", ipAddress);

        List<UserActivity> result = userActivityRepositoryPort.findByIpAddress(ipAddress);

        log.info("[UserActivityService] getActivitiesByIpAddress END - count: {}", result.size());
        return result;
    }

    @Override
    public List<UserActivity> getActivitiesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("[UserActivityService] getActivitiesByDateRange START - startDate: {}, endDate: {}",
                startDate, endDate);

        List<UserActivity> result = userActivityRepositoryPort.findByTimestampBetween(startDate, endDate);

        log.info("[UserActivityService] getActivitiesByDateRange END - count: {}", result.size());
        return result;
    }

    @Override
    public List<UserActivity> getActivitiesByUserIdAndDateRange(String userId, LocalDateTime startDate,
            LocalDateTime endDate) {
        log.info(
                "[UserActivityService] getActivitiesByUserIdAndDateRange START - userId: {}, startDate: {}, endDate: {}",
                userId, startDate, endDate);

        List<UserActivity> result = userActivityRepositoryPort.findByUserIdAndTimestampBetween(userId, startDate,
                endDate);

        log.info("[UserActivityService] getActivitiesByUserIdAndDateRange END - count: {}", result.size());
        return result;
    }

    @Override
    public List<UserActivity> getRecentActivities(Pageable pageable) {
        log.info("[UserActivityService] getRecentActivities START - pageable: {}", pageable);

        List<UserActivity> result = userActivityRepositoryPort.findRecentActivities(pageable);

        log.info("[UserActivityService] getRecentActivities END - count: {}", result.size());
        return result;
    }

    @Override
    public UserActivityStatistics getActivityStatistics() {
        log.info("[UserActivityService] getActivityStatistics START");

        // 최근 24시간 활동 수
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        long todayActivities = userActivityRepositoryPort.countByTimestampAfter(yesterday);

        // 최근 7일 활동 수
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);
        long thisWeekActivities = userActivityRepositoryPort.countByTimestampAfter(weekAgo);

        // 실패한 로그 수
        long failedLogs = userActivityRepositoryPort.countByStatus("FAILED");

        // 최근 로그인 사용자 수 (최근 24시간)
        long activeUsers = userActivityRepositoryPort.countDistinctUserIdByTimestampAfterAndActivityType(yesterday,
                "LOGIN");

        UserActivityStatistics statistics = UserActivityStatistics.builder()
                .todayActivities(todayActivities)
                .thisWeekActivities(thisWeekActivities)
                .failedLogs(failedLogs)
                .activeUsers(activeUsers)
                .build();

        log.info(
                "[UserActivityService] getActivityStatistics END - todayActivities: {}, thisWeekActivities: {}, failedLogs: {}, activeUsers: {}",
                todayActivities, thisWeekActivities, failedLogs, activeUsers);

        return statistics;
    }

    @Override
    @Transactional
    public void deleteActivity(Long id) {
        log.info("[UserActivityService] deleteActivity START - id: {}", id);

        userActivityRepositoryPort.deleteById(id);

        log.info("[UserActivityService] deleteActivity END - id: {}", id);
    }

    @Override
    @Transactional
    public void deleteOldActivities() {
        log.info("[UserActivityService] deleteOldActivities START");

        // 30일 이전의 활동 로그 삭제
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        long deletedCount = userActivityRepositoryPort.deleteByTimestampBefore(thirtyDaysAgo);

        log.info("[UserActivityService] deleteOldActivities END - deletedCount: {}", deletedCount);
    }

    @Override
    public List<UserActivity> searchActivities(String userId, String activityType, String status,
            String ipAddress, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("[UserActivityService] searchActivities START - userId: {}, activityType: {}, status: {}",
                userId, activityType, status);

        List<UserActivity> result = userActivityRepositoryPort.findByAdvancedSearch(userId, activityType, status,
                ipAddress, startDate, endDate);

        log.info("[UserActivityService] searchActivities END - count: {}", result.size());
        return result;
    }

    @Override
    public List<Map<String, Object>> getActivityTypeStatistics() {
        log.info("[UserActivityService] getActivityTypeStatistics START");

        List<Map<String, Object>> result = userActivityRepositoryPort.getActivityTypeStatistics();

        log.info("[UserActivityService] getActivityTypeStatistics END - count: {}", result.size());
        return result;
    }

    @Override
    public List<Map<String, Object>> getHourlyStatistics() {
        log.info("[UserActivityService] getHourlyStatistics START");

        List<Map<String, Object>> result = userActivityRepositoryPort.getHourlyStatistics();

        log.info("[UserActivityService] getHourlyStatistics END - count: {}", result.size());
        return result;
    }
}
