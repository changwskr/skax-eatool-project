package com.skax.eatool.user.infrastructure.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 사용자 활동 로그 JPA Repository
 */
@Repository
public interface UserActivityRepositoryJpa extends JpaRepository<UserActivityEntity, Long> {

        // 사용자별 활동 로그 조회 (String userId)
        List<UserActivityEntity> findByUserIdOrderByActivityTimestampDesc(String userId);

        // 사용자별 활동 로그 페이징 조회 (String userId)
        Page<UserActivityEntity> findByUserIdOrderByActivityTimestampDesc(String userId, Pageable pageable);

        // 활동 타입별 조회 (activity_timestamp 기준)
        List<UserActivityEntity> findByActivityTypeOrderByActivityTimestampDesc(String activityType);

        // 기간별 활동 로그 조회
        @Query("SELECT ua FROM UserActivityEntity ua WHERE ua.activityTimestamp BETWEEN :startDate AND :endDate ORDER BY ua.activityTimestamp DESC")
        List<UserActivityEntity> findByTimestampBetween(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        // 사용자별 기간별 활동 로그 조회 (String userId, activity_timestamp 기준)
        @Query("SELECT ua FROM UserActivityEntity ua WHERE ua.userId = :userId AND ua.activityTimestamp BETWEEN :startDate AND :endDate ORDER BY ua.activityTimestamp DESC")
        List<UserActivityEntity> findByUserIdAndTimestampBetween(@Param("userId") String userId,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        // IP 주소별 활동 로그 조회 (activity_timestamp 기준)
        List<UserActivityEntity> findByIpAddressOrderByActivityTimestampDesc(String ipAddress);

        // 상태별 활동 로그 조회 (activity_timestamp 기준)
        List<UserActivityEntity> findByStatusOrderByActivityTimestampDesc(String status);

        // 최근 활동 로그 조회 (최근 100개)
        @Query("SELECT ua FROM UserActivityEntity ua ORDER BY ua.activityTimestamp DESC")
        List<UserActivityEntity> findRecentActivities(Pageable pageable);

        // 오늘 활동 로그 수 조회
        @Query("SELECT COUNT(ua) FROM UserActivityEntity ua WHERE CAST(ua.activityTimestamp AS date) = CURRENT_DATE")
        long countTodayActivities();

        // 이번 주 활동 로그 수 조회
        @Query("SELECT COUNT(ua) FROM UserActivityEntity ua WHERE ua.activityTimestamp >= :startOfWeek AND ua.activityTimestamp < :endOfWeek")
        long countThisWeekActivities(@Param("startOfWeek") LocalDateTime startOfWeek,
                        @Param("endOfWeek") LocalDateTime endOfWeek);

        // 상태별 활동 로그 수 조회
        long countByStatus(String status);

        // 활성 사용자 수 조회 (오늘 로그인한 사용자)
        @Query("SELECT COUNT(DISTINCT ua.userId) FROM UserActivityEntity ua WHERE ua.activityType = 'LOGIN' AND ua.status = 'SUCCESS' AND CAST(ua.activityTimestamp AS date) = CURRENT_DATE")
        long countActiveUsers();

        // 오래된 활동 로그 삭제 (30일 이상)
        @Query("DELETE FROM UserActivityEntity ua WHERE ua.activityTimestamp < :cutoffDate")
        void deleteOldActivities(@Param("cutoffDate") LocalDateTime cutoffDate);

        // 고급 검색
        @Query("SELECT ua FROM UserActivityEntity ua WHERE " +
                        "(:userId IS NULL OR ua.userId = :userId) AND " +
                        "(:activityType IS NULL OR ua.activityType = :activityType) AND " +
                        "(:status IS NULL OR ua.status = :status) AND " +
                        "(:ipAddress IS NULL OR ua.ipAddress = :ipAddress) AND " +
                        "(:startDate IS NULL OR ua.activityTimestamp >= :startDate) AND " +
                        "(:endDate IS NULL OR ua.activityTimestamp <= :endDate) " +
                        "ORDER BY ua.activityTimestamp DESC")
        List<UserActivityEntity> findByAdvancedSearch(@Param("userId") String userId,
                        @Param("activityType") String activityType,
                        @Param("status") String status,
                        @Param("ipAddress") String ipAddress,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        // 고급 검색 결과 수 조회
        @Query("SELECT COUNT(ua) FROM UserActivityEntity ua WHERE " +
                        "(:userId IS NULL OR ua.userId = :userId) AND " +
                        "(:activityType IS NULL OR ua.activityType = :activityType) AND " +
                        "(:status IS NULL OR ua.status = :status) AND " +
                        "(:ipAddress IS NULL OR ua.ipAddress = :ipAddress) AND " +
                        "(:startDate IS NULL OR ua.activityTimestamp >= :startDate) AND " +
                        "(:endDate IS NULL OR ua.activityTimestamp <= :endDate)")
        long countByAdvancedSearch(@Param("userId") String userId,
                        @Param("activityType") String activityType,
                        @Param("status") String status,
                        @Param("ipAddress") String ipAddress,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        // 활동 타입별 통계 조회
        @Query("SELECT ua.activityType, COUNT(ua), " +
                        "COUNT(CASE WHEN ua.status = 'SUCCESS' THEN 1 END), " +
                        "COUNT(CASE WHEN ua.status = 'FAILED' THEN 1 END) " +
                        "FROM UserActivityEntity ua " +
                        "WHERE ua.activityTimestamp >= :startDate " +
                        "GROUP BY ua.activityType " +
                        "ORDER BY COUNT(ua) DESC")
        List<Object[]> getActivityTypeStatistics(@Param("startDate") LocalDateTime startDate);

        // 시간대별 활동 통계 조회
        @Query("SELECT EXTRACT(HOUR FROM ua.activityTimestamp), COUNT(ua) " +
                        "FROM UserActivityEntity ua " +
                        "WHERE ua.activityTimestamp >= :startDate " +
                        "GROUP BY EXTRACT(HOUR FROM ua.activityTimestamp) " +
                        "ORDER BY EXTRACT(HOUR FROM ua.activityTimestamp)")
        List<Object[]> getHourlyStatistics(@Param("startDate") LocalDateTime startDate);

        // 특정 시간 이후의 활동 로그 수 조회
        @Query("SELECT COUNT(ua) FROM UserActivityEntity ua WHERE ua.activityTimestamp >= :timestamp")
        long countByActivityTimestampAfter(@Param("timestamp") LocalDateTime timestamp);

        // 특정 시간 이후의 특정 활동 타입별 고유 사용자 수 조회
        @Query("SELECT COUNT(DISTINCT ua.userId) FROM UserActivityEntity ua WHERE ua.activityTimestamp >= :timestamp AND ua.activityType = :activityType")
        long countDistinctUserIdByActivityTimestampAfterAndActivityType(@Param("timestamp") LocalDateTime timestamp,
                        @Param("activityType") String activityType);

        // 특정 시간 이전의 활동 로그 삭제
        @Query("DELETE FROM UserActivityEntity ua WHERE ua.activityTimestamp < :timestamp")
        long deleteByActivityTimestampBefore(@Param("timestamp") LocalDateTime timestamp);
}
