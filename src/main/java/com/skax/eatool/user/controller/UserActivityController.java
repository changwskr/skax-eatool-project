package com.skax.eatool.user.controller;

import com.skax.eatool.user.domain.UserActivity;
import com.skax.eatool.user.domain.UserActivityStatistics;
import com.skax.eatool.user.service.port.UserActivityServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 사용자 활동 로그 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/user-management-web/activity-logs")
@RequiredArgsConstructor
public class UserActivityController {

    private final UserActivityServicePort userActivityServicePort;

    /**
     * 활동 로그 목록 페이지
     */
    @GetMapping
    public String activityLogsPage(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        log.info(
                "[UserActivityController] activityLogsPage START - userId: {}, activityType: {}, status: {}, page: {}, size: {}",
                userId, activityType, status, page, size);

        // 통계 정보 조회
        UserActivityStatistics statistics = userActivityServicePort.getActivityStatistics();
        model.addAttribute("statistics", statistics);

        // 페이지네이션 설정
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());

        // 활동 로그 조회
        Page<UserActivity> activitiesPage;
        if (userId != null && !userId.trim().isEmpty()) {
            activitiesPage = userActivityServicePort.getActivitiesByUserId(userId, pageable);
        } else {
            activitiesPage = userActivityServicePort.getActivities(pageable);
        }

        // 필터링된 결과 적용
        List<UserActivity> filteredActivities = activitiesPage.getContent();
        if (activityType != null && !activityType.trim().isEmpty()) {
            filteredActivities = filteredActivities.stream()
                    .filter(activity -> activityType.equals(activity.getActivityType()))
                    .toList();
        }
        if (status != null && !status.trim().isEmpty()) {
            filteredActivities = filteredActivities.stream()
                    .filter(activity -> status.equals(activity.getStatus()))
                    .toList();
        }

        // 모델에 데이터 추가
        model.addAttribute("activities", filteredActivities);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", activitiesPage.getTotalPages());
        model.addAttribute("totalElements", activitiesPage.getTotalElements());
        model.addAttribute("userId", userId);
        model.addAttribute("activityType", activityType);
        model.addAttribute("status", status);
        model.addAttribute("title", "활동 로그");

        log.info("[UserActivityController] activityLogsPage END - activitiesCount: {}", filteredActivities.size());
        return "user/management/activity-logs";
    }

    /**
     * 활동 로그 상세 정보 (AJAX)
     */
    @GetMapping("/{id}")
    @ResponseBody
    public UserActivity getActivityDetail(@PathVariable Long id) {
        log.info("[UserActivityController] getActivityDetail START - id: {}", id);

        UserActivity activity = userActivityServicePort.getActivityById(id);

        log.info("[UserActivityController] getActivityDetail END - id: {}", activity.getId());
        return activity;
    }

    /**
     * 사용자별 활동 로그 조회
     */
    @GetMapping("/user/{userId}")
    public String getUserActivities(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        log.info("[UserActivityController] getUserActivities START - userId: {}, page: {}, size: {}",
                userId, page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<UserActivity> activitiesPage = userActivityServicePort.getActivitiesByUserId(userId, pageable);

        model.addAttribute("activities", activitiesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", activitiesPage.getTotalPages());
        model.addAttribute("totalElements", activitiesPage.getTotalElements());
        model.addAttribute("userId", userId);
        model.addAttribute("title", "사용자 활동 로그 - " + userId);

        log.info("[UserActivityController] getUserActivities END - activitiesCount: {}",
                activitiesPage.getContent().size());
        return "user/management/activity-logs";
    }

    /**
     * 기간별 활동 로그 조회
     */
    @GetMapping("/date-range")
    public String getActivitiesByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        log.info("[UserActivityController] getActivitiesByDateRange START - startDate: {}, endDate: {}, userId: {}",
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

        // 페이지네이션 적용
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), activities.size());

        List<UserActivity> pagedActivities = activities.subList(start, end);
        Page<UserActivity> activitiesPage = new org.springframework.data.domain.PageImpl<>(
                pagedActivities, pageable, activities.size());

        model.addAttribute("activities", activitiesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", activitiesPage.getTotalPages());
        model.addAttribute("totalElements", activitiesPage.getTotalElements());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("userId", userId);
        model.addAttribute("title", "기간별 활동 로그");

        log.info("[UserActivityController] getActivitiesByDateRange END - activitiesCount: {}",
                activitiesPage.getContent().size());
        return "user/management/activity-logs";
    }

    /**
     * 활동 타입별 로그 조회
     */
    @GetMapping("/type/{activityType}")
    public String getActivitiesByType(
            @PathVariable String activityType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        log.info("[UserActivityController] getActivitiesByType START - activityType: {}, page: {}, size: {}",
                activityType, page, size);

        List<UserActivity> activities = userActivityServicePort.getActivitiesByType(activityType);

        // 페이지네이션 적용
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), activities.size());

        List<UserActivity> pagedActivities = activities.subList(start, end);
        Page<UserActivity> activitiesPage = new org.springframework.data.domain.PageImpl<>(
                pagedActivities, pageable, activities.size());

        model.addAttribute("activities", activitiesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", activitiesPage.getTotalPages());
        model.addAttribute("totalElements", activitiesPage.getTotalElements());
        model.addAttribute("activityType", activityType);
        model.addAttribute("title", "활동 타입별 로그 - " + activityType);

        log.info("[UserActivityController] getActivitiesByType END - activitiesCount: {}",
                activitiesPage.getContent().size());
        return "user/management/activity-logs";
    }

    /**
     * 활동 로그 삭제
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteActivity(@PathVariable Long id) {
        log.info("[UserActivityController] deleteActivity START - id: {}", id);

        try {
            userActivityServicePort.deleteActivity(id);
            log.info("[UserActivityController] deleteActivity END - success");
            return "success";
        } catch (Exception e) {
            log.error("[UserActivityController] deleteActivity ERROR - id: {}, error: {}", id, e.getMessage());
            return "error";
        }
    }

    /**
     * 오래된 활동 로그 삭제
     */
    @DeleteMapping("/old")
    @ResponseBody
    public String deleteOldActivities() {
        log.info("[UserActivityController] deleteOldActivities START");

        try {
            userActivityServicePort.deleteOldActivities();
            log.info("[UserActivityController] deleteOldActivities END - success");
            return "success";
        } catch (Exception e) {
            log.error("[UserActivityController] deleteOldActivities ERROR - error: {}", e.getMessage());
            return "error";
        }
    }

    /**
     * 활동 로그 통계 조회 (AJAX)
     */
    @GetMapping("/statistics")
    @ResponseBody
    public UserActivityStatistics getStatistics() {
        log.info("[UserActivityController] getStatistics START");

        UserActivityStatistics statistics = userActivityServicePort.getActivityStatistics();

        log.info("[UserActivityController] getStatistics END");
        return statistics;
    }
}
