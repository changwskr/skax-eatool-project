package com.skax.eatool.user.infrastructure;

import com.skax.eatool.user.domain.UserActivity;
import com.skax.eatool.user.infrastructure.jpa.UserActivityEntity;
import com.skax.eatool.user.infrastructure.jpa.UserActivityRepositoryJpa;
import com.skax.eatool.user.service.port.UserActivityRepositoryPort;
import org.springframework.context.annotation.Primary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 사용자 활동 로그 JPA Repository 구현체
 */
@Repository("userActivityRepositoryPortJpaImpl")
@Primary
@RequiredArgsConstructor
@Slf4j
public class UserActivityRepositoryPortJpaImpl implements UserActivityRepositoryPort {

        private final UserActivityRepositoryJpa userActivityRepositoryJpa;

        @Override
        public UserActivity save(UserActivity userActivity) {
                log.info("[UserActivityRepositoryPortJpaImpl] save START - userId: {}, activityType: {}",
                                userActivity.getUserId(), userActivity.getActivityType());

                UserActivityEntity entity = UserActivityEntity.fromDomain(userActivity);
                UserActivityEntity savedEntity = userActivityRepositoryJpa.save(entity);
                UserActivity result = savedEntity.toDomain();

                log.info("[UserActivityRepositoryPortJpaImpl] save END - activityId: {}", result.getId());
                return result;
        }

        @Override
        public Optional<UserActivity> findById(Long id) {
                log.info("[UserActivityRepositoryPortJpaImpl] findById START - id: {}", id);

                Optional<UserActivity> result = userActivityRepositoryJpa.findById(id)
                                .map(UserActivityEntity::toDomain);

                log.info("[UserActivityRepositoryPortJpaImpl] findById END - found: {}", result.isPresent());
                return result;
        }

        @Override
        public List<UserActivity> findAll() {
                log.info("[UserActivityRepositoryPortJpaImpl] findAll START");

                List<UserActivity> result = userActivityRepositoryJpa.findAll()
                                .stream()
                                .map(UserActivityEntity::toDomain)
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] findAll END - count: {}", result.size());
                return result;
        }

        @Override
        public Page<UserActivity> findAll(Pageable pageable) {
                log.info("[UserActivityRepositoryPortJpaImpl] findAll(Pageable) START - pageable: {}", pageable);

                Page<UserActivity> result = userActivityRepositoryJpa.findAll(pageable)
                                .map(UserActivityEntity::toDomain);

                log.info("[UserActivityRepositoryPortJpaImpl] findAll(Pageable) END - totalElements: {}",
                                result.getTotalElements());
                return result;
        }

        @Override
        public List<UserActivity> findByUserId(String userId) {
                log.info("[UserActivityRepositoryPortJpaImpl] findByUserId START - userId: {}", userId);

                List<UserActivity> result = userActivityRepositoryJpa.findByUserIdOrderByActivityTimestampDesc(userId)
                                .stream()
                                .map(UserActivityEntity::toDomain)
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] findByUserId END - count: {}", result.size());
                return result;
        }

        @Override
        public Page<UserActivity> findByUserId(String userId, Pageable pageable) {
                log.info("[UserActivityRepositoryPortJpaImpl] findByUserId(Pageable) START - userId: {}, pageable: {}",
                                userId, pageable);

                Page<UserActivity> result = userActivityRepositoryJpa
                                .findByUserIdOrderByActivityTimestampDesc(userId, pageable)
                                .map(UserActivityEntity::toDomain);

                log.info("[UserActivityRepositoryPortJpaImpl] findByUserId(Pageable) END - totalElements: {}",
                                result.getTotalElements());
                return result;
        }

        @Override
        public List<UserActivity> findByActivityType(String activityType) {
                log.info("[UserActivityRepositoryPortJpaImpl] findByActivityType START - activityType: {}",
                                activityType);

                List<UserActivity> result = userActivityRepositoryJpa
                                .findByActivityTypeOrderByActivityTimestampDesc(activityType)
                                .stream()
                                .map(UserActivityEntity::toDomain)
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] findByActivityType END - count: {}", result.size());
                return result;
        }

        @Override
        public List<UserActivity> findByStatus(String status) {
                log.info("[UserActivityRepositoryPortJpaImpl] findByStatus START - status: {}", status);

                List<UserActivity> result = userActivityRepositoryJpa.findByStatusOrderByActivityTimestampDesc(status)
                                .stream()
                                .map(UserActivityEntity::toDomain)
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] findByStatus END - count: {}", result.size());
                return result;
        }

        @Override
        public List<UserActivity> findByIpAddress(String ipAddress) {
                log.info("[UserActivityRepositoryPortJpaImpl] findByIpAddress START - ipAddress: {}", ipAddress);

                List<UserActivity> result = userActivityRepositoryJpa
                                .findByIpAddressOrderByActivityTimestampDesc(ipAddress)
                                .stream()
                                .map(UserActivityEntity::toDomain)
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] findByIpAddress END - count: {}", result.size());
                return result;
        }

        @Override
        public List<UserActivity> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate) {
                log.info("[UserActivityRepositoryPortJpaImpl] findByTimestampBetween START - startDate: {}, endDate: {}",
                                startDate, endDate);

                List<UserActivity> result = userActivityRepositoryJpa.findByTimestampBetween(startDate, endDate)
                                .stream()
                                .map(UserActivityEntity::toDomain)
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] findByTimestampBetween END - count: {}", result.size());
                return result;
        }

        @Override
        public List<UserActivity> findByUserIdAndTimestampBetween(String userId, LocalDateTime startDate,
                        LocalDateTime endDate) {
                log.info("[UserActivityRepositoryPortJpaImpl] findByUserIdAndTimestampBetween START - userId: {}, startDate: {}, endDate: {}",
                                userId, startDate, endDate);

                List<UserActivity> result = userActivityRepositoryJpa
                                .findByUserIdAndTimestampBetween(userId, startDate, endDate)
                                .stream()
                                .map(UserActivityEntity::toDomain)
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] findByUserIdAndTimestampBetween END - count: {}",
                                result.size());
                return result;
        }

        @Override
        public List<UserActivity> findRecentActivities(Pageable pageable) {
                log.info("[UserActivityRepositoryPortJpaImpl] findRecentActivities START - pageable: {}", pageable);

                List<UserActivity> result = userActivityRepositoryJpa.findRecentActivities(pageable)
                                .stream()
                                .map(UserActivityEntity::toDomain)
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] findRecentActivities END - count: {}", result.size());
                return result;
        }

        @Override
        public long countTodayActivities() {
                log.info("[UserActivityRepositoryPortJpaImpl] countTodayActivities START");

                long result = userActivityRepositoryJpa.countTodayActivities();

                log.info("[UserActivityRepositoryPortJpaImpl] countTodayActivities END - count: {}", result);
                return result;
        }

        @Override
        public long countThisWeekActivities() {
                log.info("[UserActivityRepositoryPortJpaImpl] countThisWeekActivities START");

                // 이번 주 시작일과 종료일 계산
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime startOfWeek = now.toLocalDate().atStartOfDay().with(java.time.DayOfWeek.MONDAY);
                LocalDateTime endOfWeek = startOfWeek.plusWeeks(1);

                long result = userActivityRepositoryJpa.countThisWeekActivities(startOfWeek, endOfWeek);

                log.info("[UserActivityRepositoryPortJpaImpl] countThisWeekActivities END - count: {}", result);
                return result;
        }

        @Override
        public long countFailedLogs() {
                log.info("[UserActivityRepositoryPortJpaImpl] countFailedLogs START");

                long result = userActivityRepositoryJpa.countByStatus("FAILED");

                log.info("[UserActivityRepositoryPortJpaImpl] countFailedLogs END - count: {}", result);
                return result;
        }

        @Override
        public long countActiveUsers() {
                log.info("[UserActivityRepositoryPortJpaImpl] countActiveUsers START");

                long result = userActivityRepositoryJpa.countActiveUsers();

                log.info("[UserActivityRepositoryPortJpaImpl] countActiveUsers END - count: {}", result);
                return result;
        }

        @Override
        public void deleteById(Long id) {
                log.info("[UserActivityRepositoryPortJpaImpl] deleteById START - id: {}", id);

                userActivityRepositoryJpa.deleteById(id);

                log.info("[UserActivityRepositoryPortJpaImpl] deleteById END - id: {}", id);
        }

        @Override
        public void deleteOldActivities() {
                log.info("[UserActivityRepositoryPortJpaImpl] deleteOldActivities START");

                LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);
                userActivityRepositoryJpa.deleteOldActivities(cutoffDate);

                log.info("[UserActivityRepositoryPortJpaImpl] deleteOldActivities END");
        }

        @Override
        public List<UserActivity> findByAdvancedSearch(String userId, String activityType, String status,
                        String ipAddress, LocalDateTime startDate, LocalDateTime endDate) {
                log.info("[UserActivityRepositoryPortJpaImpl] findByAdvancedSearch START - userId: {}, activityType: {}, status: {}",
                                userId, activityType, status);

                List<UserActivity> result = userActivityRepositoryJpa
                                .findByAdvancedSearch(userId, activityType, status, ipAddress, startDate, endDate)
                                .stream()
                                .map(UserActivityEntity::toDomain)
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] findByAdvancedSearch END - count: {}", result.size());
                return result;
        }

        @Override
        public long countByAdvancedSearch(String userId, String activityType, String status,
                        String ipAddress, LocalDateTime startDate, LocalDateTime endDate) {
                log.info("[UserActivityRepositoryPortJpaImpl] countByAdvancedSearch START");

                long result = userActivityRepositoryJpa.countByAdvancedSearch(userId, activityType, status, ipAddress,
                                startDate, endDate);

                log.info("[UserActivityRepositoryPortJpaImpl] countByAdvancedSearch END - count: {}", result);
                return result;
        }

        @Override
        public List<Map<String, Object>> getActivityTypeStatistics() {
                log.info("[UserActivityRepositoryPortJpaImpl] getActivityTypeStatistics START");

                LocalDateTime startDate = LocalDateTime.now().minusDays(30);
                List<Object[]> results = userActivityRepositoryJpa.getActivityTypeStatistics(startDate);

                List<Map<String, Object>> result = results.stream()
                                .map(row -> Map.of(
                                                "activityType", row[0],
                                                "count", row[1],
                                                "successCount", row[2],
                                                "failedCount", row[3]))
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] getActivityTypeStatistics END - count: {}",
                                result.size());
                return result;
        }

        @Override
        public List<Map<String, Object>> getHourlyStatistics() {
                log.info("[UserActivityRepositoryPortJpaImpl] getHourlyStatistics START");

                LocalDateTime startDate = LocalDateTime.now().minusDays(7);
                List<Object[]> results = userActivityRepositoryJpa.getHourlyStatistics(startDate);

                List<Map<String, Object>> result = results.stream()
                                .map(row -> Map.of(
                                                "hour", row[0],
                                                "count", row[1]))
                                .collect(Collectors.toList());

                log.info("[UserActivityRepositoryPortJpaImpl] getHourlyStatistics END - count: {}", result.size());
                return result;
        }

        @Override
        public long countByTimestampAfter(LocalDateTime timestamp) {
                log.info("[UserActivityRepositoryPortJpaImpl] countByTimestampAfter START - timestamp: {}", timestamp);

                long result = userActivityRepositoryJpa.countByActivityTimestampAfter(timestamp);

                log.info("[UserActivityRepositoryPortJpaImpl] countByTimestampAfter END - count: {}", result);
                return result;
        }

        @Override
        public long countByStatus(String status) {
                log.info("[UserActivityRepositoryPortJpaImpl] countByStatus START - status: {}", status);

                long result = userActivityRepositoryJpa.countByStatus(status);

                log.info("[UserActivityRepositoryPortJpaImpl] countByStatus END - count: {}", result);
                return result;
        }

        @Override
        public long countDistinctUserIdByTimestampAfterAndActivityType(LocalDateTime timestamp, String activityType) {
                log.info("[UserActivityRepositoryPortJpaImpl] countDistinctUserIdByTimestampAfterAndActivityType START - timestamp: {}, activityType: {}",
                                timestamp, activityType);

                long result = userActivityRepositoryJpa
                                .countDistinctUserIdByActivityTimestampAfterAndActivityType(timestamp, activityType);

                log.info("[UserActivityRepositoryPortJpaImpl] countDistinctUserIdByTimestampAfterAndActivityType END - count: {}",
                                result);
                return result;
        }

        @Override
        public long deleteByTimestampBefore(LocalDateTime timestamp) {
                log.info("[UserActivityRepositoryPortJpaImpl] deleteByTimestampBefore START - timestamp: {}",
                                timestamp);

                long result = userActivityRepositoryJpa.deleteByActivityTimestampBefore(timestamp);

                log.info("[UserActivityRepositoryPortJpaImpl] deleteByTimestampBefore END - deletedCount: {}", result);
                return result;
        }
}
