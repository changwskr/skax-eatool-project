package com.skax.eatool.user.infrastructure;

import com.skax.eatool.user.domain.UserActivity;
import com.skax.eatool.user.infrastructure.mybatis.UserActivityDto;
import com.skax.eatool.user.infrastructure.mybatis.UserActivityRepositoryMybatis;
import com.skax.eatool.user.service.port.UserActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * MyBatis를 사용한 사용자 활동 로그 리포지토리 구현체
 */
@Repository("userActivityRepositoryPortMybatisImpl")
@RequiredArgsConstructor
@Slf4j
public class UserActivityRepositoryPortMybatisImpl implements UserActivityRepositoryPort {

    private final UserActivityRepositoryMybatis userActivityRepositoryMybatis;

    @Override
    public UserActivity save(UserActivity userActivity) {
        log.info("[UserActivityRepositoryPortMybatisImpl] save START - userId: {}, activityType: {}",
                userActivity.getUserId(), userActivity.getActivityType());

        UserActivityDto dto = UserActivityDto.from(userActivity);

        if (userActivity.getId() == null) {
            // 새로운 활동 로그 생성
            dto.setCreatedDate(LocalDateTime.now());
            dto.setLastModifiedDate(LocalDateTime.now());
            Long savedId = userActivityRepositoryMybatis.save(dto);
            dto.setId(savedId);
        } else {
            // 기존 활동 로그 업데이트
            dto.setLastModifiedDate(LocalDateTime.now());
            userActivityRepositoryMybatis.update(dto);
        }

        UserActivity result = dto.toModel();
        log.info("[UserActivityRepositoryPortMybatisImpl] save END - id: {}", result.getId());
        return result;
    }

    @Override
    public Optional<UserActivity> findById(Long id) {
        log.info("[UserActivityRepositoryPortMybatisImpl] findById START - id: {}", id);

        Optional<UserActivity> result = userActivityRepositoryMybatis.findById(id)
                .map(UserActivityDto::toModel);

        log.info("[UserActivityRepositoryPortMybatisImpl] findById END - found: {}", result.isPresent());
        return result;
    }

    @Override
    public List<UserActivity> findAll() {
        log.info("[UserActivityRepositoryPortMybatisImpl] findAll START");

        List<UserActivity> result = userActivityRepositoryMybatis.findAll()
                .stream()
                .map(UserActivityDto::toModel)
                .toList();

        log.info("[UserActivityRepositoryPortMybatisImpl] findAll END - count: {}", result.size());
        return result;
    }

    @Override
    public Page<UserActivity> findAll(Pageable pageable) {
        log.info("[UserActivityRepositoryPortMybatisImpl] findAll(Pageable) START - pageable: {}", pageable);

        List<UserActivityDto> dtos = userActivityRepositoryMybatis.findAllWithPageable(
                pageable.getOffset(), pageable.getPageSize());

        long totalCount = userActivityRepositoryMybatis.countAll();

        List<UserActivity> activities = dtos.stream()
                .map(UserActivityDto::toModel)
                .toList();

        Page<UserActivity> result = new PageImpl<>(activities, pageable, totalCount);

        log.info("[UserActivityRepositoryPortMybatisImpl] findAll(Pageable) END - totalElements: {}",
                result.getTotalElements());
        return result;
    }

    @Override
    public List<UserActivity> findByUserId(String userId) {
        log.info("[UserActivityRepositoryPortMybatisImpl] findByUserId START - userId: {}", userId);

        List<UserActivity> result = userActivityRepositoryMybatis.findByUserId(userId)
                .stream()
                .map(UserActivityDto::toModel)
                .toList();

        log.info("[UserActivityRepositoryPortMybatisImpl] findByUserId END - count: {}", result.size());
        return result;
    }

    @Override
    public Page<UserActivity> findByUserId(String userId, Pageable pageable) {
        log.info("[UserActivityRepositoryPortMybatisImpl] findByUserId(Pageable) START - userId: {}, pageable: {}",
                userId, pageable);

        List<UserActivityDto> dtos = userActivityRepositoryMybatis.findByUserIdWithPageable(
                userId, pageable.getOffset(), pageable.getPageSize());

        long totalCount = userActivityRepositoryMybatis.countByUserId(userId);

        List<UserActivity> activities = dtos.stream()
                .map(UserActivityDto::toModel)
                .toList();

        Page<UserActivity> result = new PageImpl<>(activities, pageable, totalCount);

        log.info("[UserActivityRepositoryPortMybatisImpl] findByUserId(Pageable) END - totalElements: {}",
                result.getTotalElements());
        return result;
    }

    @Override
    public List<UserActivity> findByActivityType(String activityType) {
        log.info("[UserActivityRepositoryPortMybatisImpl] findByActivityType START - activityType: {}", activityType);

        List<UserActivity> result = userActivityRepositoryMybatis.findByActivityType(activityType)
                .stream()
                .map(UserActivityDto::toModel)
                .toList();

        log.info("[UserActivityRepositoryPortMybatisImpl] findByActivityType END - count: {}", result.size());
        return result;
    }

    @Override
    public List<UserActivity> findByStatus(String status) {
        log.info("[UserActivityRepositoryPortMybatisImpl] findByStatus START - status: {}", status);

        List<UserActivity> result = userActivityRepositoryMybatis.findByStatus(status)
                .stream()
                .map(UserActivityDto::toModel)
                .toList();

        log.info("[UserActivityRepositoryPortMybatisImpl] findByStatus END - count: {}", result.size());
        return result;
    }

    @Override
    public List<UserActivity> findByIpAddress(String ipAddress) {
        log.info("[UserActivityRepositoryPortMybatisImpl] findByIpAddress START - ipAddress: {}", ipAddress);

        List<UserActivity> result = userActivityRepositoryMybatis.findByIpAddress(ipAddress)
                .stream()
                .map(UserActivityDto::toModel)
                .toList();

        log.info("[UserActivityRepositoryPortMybatisImpl] findByIpAddress END - count: {}", result.size());
        return result;
    }

    @Override
    public List<UserActivity> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("[UserActivityRepositoryPortMybatisImpl] findByTimestampBetween START - startDate: {}, endDate: {}",
                startDate, endDate);

        List<UserActivity> result = userActivityRepositoryMybatis.findByTimestampBetween(startDate, endDate)
                .stream()
                .map(UserActivityDto::toModel)
                .toList();

        log.info("[UserActivityRepositoryPortMybatisImpl] findByTimestampBetween END - count: {}", result.size());
        return result;
    }

    @Override
    public List<UserActivity> findByUserIdAndTimestampBetween(String userId, LocalDateTime startDate,
            LocalDateTime endDate) {
        log.info(
                "[UserActivityRepositoryPortMybatisImpl] findByUserIdAndTimestampBetween START - userId: {}, startDate: {}, endDate: {}",
                userId, startDate, endDate);

        List<UserActivity> result = userActivityRepositoryMybatis
                .findByUserIdAndTimestampBetween(userId, startDate, endDate)
                .stream()
                .map(UserActivityDto::toModel)
                .toList();

        log.info("[UserActivityRepositoryPortMybatisImpl] findByUserIdAndTimestampBetween END - count: {}",
                result.size());
        return result;
    }

    @Override
    public List<UserActivity> findRecentActivities(Pageable pageable) {
        log.info("[UserActivityRepositoryPortMybatisImpl] findRecentActivities START - pageable: {}", pageable);

        List<UserActivity> result = userActivityRepositoryMybatis.findRecentActivities(pageable.getPageSize())
                .stream()
                .map(UserActivityDto::toModel)
                .toList();

        log.info("[UserActivityRepositoryPortMybatisImpl] findRecentActivities END - count: {}", result.size());
        return result;
    }

    @Override
    public long countTodayActivities() {
        log.info("[UserActivityRepositoryPortMybatisImpl] countTodayActivities START");

        long count = userActivityRepositoryMybatis.countTodayActivities();

        log.info("[UserActivityRepositoryPortMybatisImpl] countTodayActivities END - count: {}", count);
        return count;
    }

    @Override
    public long countThisWeekActivities() {
        log.info("[UserActivityRepositoryPortMybatisImpl] countThisWeekActivities START");

        long count = userActivityRepositoryMybatis.countThisWeekActivities();

        log.info("[UserActivityRepositoryPortMybatisImpl] countThisWeekActivities END - count: {}", count);
        return count;
    }

    @Override
    public long countFailedLogs() {
        log.info("[UserActivityRepositoryPortMybatisImpl] countFailedLogs START");

        long count = userActivityRepositoryMybatis.countFailedLogs();

        log.info("[UserActivityRepositoryPortMybatisImpl] countFailedLogs END - count: {}", count);
        return count;
    }

    @Override
    public long countActiveUsers() {
        log.info("[UserActivityRepositoryPortMybatisImpl] countActiveUsers START");

        long count = userActivityRepositoryMybatis.countActiveUsers();

        log.info("[UserActivityRepositoryPortMybatisImpl] countActiveUsers END - count: {}", count);
        return count;
    }

    @Override
    public void deleteById(Long id) {
        log.info("[UserActivityRepositoryPortMybatisImpl] deleteById START - id: {}", id);

        userActivityRepositoryMybatis.deleteById(id);

        log.info("[UserActivityRepositoryPortMybatisImpl] deleteById END - id: {}", id);
    }

    @Override
    public void deleteOldActivities() {
        log.info("[UserActivityRepositoryPortMybatisImpl] deleteOldActivities START");

        int deletedCount = userActivityRepositoryMybatis.deleteOldActivities();

        log.info("[UserActivityRepositoryPortMybatisImpl] deleteOldActivities END - deletedCount: {}", deletedCount);
    }

    @Override
    public List<UserActivity> findByAdvancedSearch(String userId, String activityType, String status,
            String ipAddress, LocalDateTime startDate, LocalDateTime endDate) {
        log.info(
                "[UserActivityRepositoryPortMybatisImpl] findByAdvancedSearch START - userId: {}, activityType: {}, status: {}",
                userId, activityType, status);

        List<UserActivity> result = userActivityRepositoryMybatis.findByAdvancedSearch(userId, activityType, status,
                ipAddress, startDate, endDate, null)
                .stream()
                .map(UserActivityDto::toModel)
                .toList();

        log.info("[UserActivityRepositoryPortMybatisImpl] findByAdvancedSearch END - count: {}", result.size());
        return result;
    }

    @Override
    public long countByAdvancedSearch(String userId, String activityType, String status,
            String ipAddress, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("[UserActivityRepositoryPortMybatisImpl] countByAdvancedSearch START");

        long result = userActivityRepositoryMybatis.countByAdvancedSearch(userId, activityType, status,
                ipAddress, startDate, endDate);

        log.info("[UserActivityRepositoryPortMybatisImpl] countByAdvancedSearch END - count: {}", result);
        return result;
    }

    @Override
    public List<Map<String, Object>> getActivityTypeStatistics() {
        log.info("[UserActivityRepositoryPortMybatisImpl] getActivityTypeStatistics START");

        List<Map<String, Object>> result = userActivityRepositoryMybatis.getActivityTypeStatistics();

        log.info("[UserActivityRepositoryPortMybatisImpl] getActivityTypeStatistics END - count: {}", result.size());
        return result;
    }

    @Override
    public List<Map<String, Object>> getHourlyStatistics() {
        log.info("[UserActivityRepositoryPortMybatisImpl] getHourlyStatistics START");

        List<Map<String, Object>> result = userActivityRepositoryMybatis.getHourlyStatistics();

        log.info("[UserActivityRepositoryPortMybatisImpl] getHourlyStatistics END - count: {}", result.size());
        return result;
    }

    @Override
    public long deleteByTimestampBefore(java.time.LocalDateTime timestamp) {
        return userActivityRepositoryMybatis.deleteByActivityTimestampBefore(timestamp);
    }

    @Override
    public long countByTimestampAfter(java.time.LocalDateTime timestamp) {
        throw new UnsupportedOperationException("Mybatis 구현체는 지원하지 않습니다.");
    }

    @Override
    public long countByStatus(String status) {
        throw new UnsupportedOperationException("Mybatis 구현체는 지원하지 않습니다.");
    }

    @Override
    public long countDistinctUserIdByTimestampAfterAndActivityType(java.time.LocalDateTime timestamp,
            String activityType) {
        throw new UnsupportedOperationException("Mybatis 구현체는 지원하지 않습니다.");
    }
}