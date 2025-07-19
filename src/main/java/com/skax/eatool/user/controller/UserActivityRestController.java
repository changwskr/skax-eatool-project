package com.skax.eatool.user.controller;

import com.skax.eatool.user.controller.request.UserActivitySearchRequest;
import com.skax.eatool.user.domain.UserActivity;
import com.skax.eatool.user.domain.UserActivityStatistics;
import com.skax.eatool.user.service.port.UserActivityServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 사용자 활동 로그 REST API 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/user-activities")
@RequiredArgsConstructor
public class UserActivityRestController {

    private final UserActivityServicePort userActivityServicePort;

    /**
     * 활동 로그 목록 조회 (REST API)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getActivities(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info(
                "[UserActivityRestController] getActivities START - userId: {}, activityType: {}, status: {}, page: {}, size: {}",
                userId, activityType, status, page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<UserActivity> activitiesPage;

        if (userId != null && !userId.trim().isEmpty()) {
            activitiesPage = userActivityServicePort.getActivitiesByUserId(userId, pageable);
        } else {
            activitiesPage = userActivityServicePort.getActivities(pageable);
        }

        // 날짜 범위 필터링
        List<UserActivity> filteredActivities = activitiesPage.getContent();
        if (startDate != null && endDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T23:59:59");

            filteredActivities = filteredActivities.stream()
                    .filter(activity -> activity.getTimestamp().isAfter(startDateTime) &&
                            activity.getTimestamp().isBefore(endDateTime))
                    .toList();
        }

        // 활동 타입 필터링
        if (activityType != null && !activityType.trim().isEmpty()) {
            filteredActivities = filteredActivities.stream()
                    .filter(activity -> activityType.equals(activity.getActivityType()))
                    .toList();
        }

        // 상태 필터링
        if (status != null && !status.trim().isEmpty()) {
            filteredActivities = filteredActivities.stream()
                    .filter(activity -> status.equals(activity.getStatus()))
                    .toList();
        }

        Map<String, Object> response = Map.of(
                "activities", filteredActivities,
                "currentPage", page,
                "totalPages", activitiesPage.getTotalPages(),
                "totalElements", activitiesPage.getTotalElements(),
                "size", size);

        log.info("[UserActivityRestController] getActivities END - activitiesCount: {}", filteredActivities.size());
        return ResponseEntity.ok(response);
    }

    /**
     * 활동 로그 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserActivity> getActivityById(@PathVariable Long id) {
        log.info("[UserActivityRestController] getActivityById START - id: {}", id);

        UserActivity activity = userActivityServicePort.getActivityById(id);

        log.info("[UserActivityRestController] getActivityById END - id: {}", activity.getId());
        return ResponseEntity.ok(activity);
    }

    /**
     * 사용자별 활동 로그 조회
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getActivitiesByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("[UserActivityRestController] getActivitiesByUserId START - userId: {}, page: {}, size: {}",
                userId, page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<UserActivity> activitiesPage = userActivityServicePort.getActivitiesByUserId(userId, pageable);

        Map<String, Object> response = Map.of(
                "activities", activitiesPage.getContent(),
                "currentPage", page,
                "totalPages", activitiesPage.getTotalPages(),
                "totalElements", activitiesPage.getTotalElements(),
                "userId", userId,
                "size", size);

        log.info("[UserActivityRestController] getActivitiesByUserId END - activitiesCount: {}",
                activitiesPage.getContent().size());
        return ResponseEntity.ok(response);
    }

    /**
     * 활동 타입별 로그 조회
     */
    @GetMapping("/type/{activityType}")
    public ResponseEntity<List<UserActivity>> getActivitiesByType(@PathVariable String activityType) {
        log.info("[UserActivityRestController] getActivitiesByType START - activityType: {}", activityType);

        List<UserActivity> activities = userActivityServicePort.getActivitiesByType(activityType);

        log.info("[UserActivityRestController] getActivitiesByType END - activitiesCount: {}", activities.size());
        return ResponseEntity.ok(activities);
    }

    /**
     * 날짜 범위별 활동 로그 조회
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<UserActivity>> getActivitiesByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String userId) {

        log.info("[UserActivityRestController] getActivitiesByDateRange START - startDate: {}, endDate: {}, userId: {}",
                startDate, endDate, userId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T23:59:59");

        List<UserActivity> activities;
        if (userId != null && !userId.trim().isEmpty()) {
            activities = userActivityServicePort.getActivitiesByUserIdAndDateRange(userId, startDateTime, endDateTime);
        } else {
            activities = userActivityServicePort.getActivitiesByDateRange(startDateTime, endDateTime);
        }

        log.info("[UserActivityRestController] getActivitiesByDateRange END - activitiesCount: {}", activities.size());
        return ResponseEntity.ok(activities);
    }

    /**
     * 활동 로그 통계 조회
     */
    @GetMapping("/statistics")
    public ResponseEntity<UserActivityStatistics> getStatistics() {
        log.info("[UserActivityRestController] getStatistics START");

        UserActivityStatistics statistics = userActivityServicePort.getActivityStatistics();

        log.info("[UserActivityRestController] getStatistics END");
        return ResponseEntity.ok(statistics);
    }

    /**
     * 활동 로그 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteActivity(@PathVariable Long id) {
        log.info("[UserActivityRestController] deleteActivity START - id: {}", id);

        userActivityServicePort.deleteActivity(id);

        log.info("[UserActivityRestController] deleteActivity END - id: {}", id);
        return ResponseEntity.ok(Map.of("message", "Activity deleted successfully", "id", id.toString()));
    }

    /**
     * 오래된 활동 로그 삭제
     */
    @DeleteMapping("/old")
    public ResponseEntity<Map<String, String>> deleteOldActivities() {
        log.info("[UserActivityRestController] deleteOldActivities START");

        userActivityServicePort.deleteOldActivities();

        log.info("[UserActivityRestController] deleteOldActivities END");
        return ResponseEntity.ok(Map.of("message", "Old activities deleted successfully"));
    }

    /**
     * 실시간 활동 로그 조회 (최근 10개)
     */
    @GetMapping("/recent")
    public ResponseEntity<List<UserActivity>> getRecentActivities() {
        log.info("[UserActivityRestController] getRecentActivities START");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("timestamp").descending());
        List<UserActivity> activities = userActivityServicePort.getRecentActivities(pageable);

        log.info("[UserActivityRestController] getRecentActivities END - activitiesCount: {}", activities.size());
        return ResponseEntity.ok(activities);
    }

    /**
     * 고급 검색
     */
    @PostMapping("/search")
    public ResponseEntity<List<UserActivity>> searchActivities(@RequestBody UserActivitySearchRequest request) {
        log.info("[UserActivityRestController] searchActivities START - request: {}", request);

        LocalDateTime startDate = request.getStartDate() != null ? request.getStartDate().atStartOfDay() : null;
        LocalDateTime endDate = request.getEndDate() != null ? request.getEndDate().atTime(23, 59, 59) : null;

        List<UserActivity> activities = userActivityServicePort.searchActivities(
                request.getUserId(), request.getActivityType(), request.getStatus(),
                request.getIpAddress(), startDate, endDate);

        log.info("[UserActivityRestController] searchActivities END - activitiesCount: {}", activities.size());
        return ResponseEntity.ok(activities);
    }

    /**
     * 활동 타입별 통계 조회
     */
    @GetMapping("/statistics/activity-types")
    public ResponseEntity<List<Map<String, Object>>> getActivityTypeStatistics() {
        log.info("[UserActivityRestController] getActivityTypeStatistics START");

        List<Map<String, Object>> statistics = userActivityServicePort.getActivityTypeStatistics();

        log.info("[UserActivityRestController] getActivityTypeStatistics END - count: {}", statistics.size());
        return ResponseEntity.ok(statistics);
    }

    /**
     * 시간대별 활동 통계 조회
     */
    @GetMapping("/statistics/hourly")
    public ResponseEntity<List<Map<String, Object>>> getHourlyStatistics() {
        log.info("[UserActivityRestController] getHourlyStatistics START");

        List<Map<String, Object>> statistics = userActivityServicePort.getHourlyStatistics();

        log.info("[UserActivityRestController] getHourlyStatistics END - count: {}", statistics.size());
        return ResponseEntity.ok(statistics);
    }
}