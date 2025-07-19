package com.skax.eatool.user.infrastructure.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * MyBatis용 사용자 활동 로그 리포지토리 인터페이스
 */
@Mapper
public interface UserActivityRepositoryMybatis {

        /**
         * 활동 로그 저장
         */
        Long save(UserActivityDto userActivityDto);

        /**
         * 활동 로그 업데이트
         */
        int update(UserActivityDto userActivityDto);

        /**
         * ID로 활동 로그 조회
         */
        Optional<UserActivityDto> findById(@Param("id") Long id);

        /**
         * 모든 활동 로그 조회
         */
        List<UserActivityDto> findAll();

        /**
         * 페이지네이션을 사용한 활동 로그 조회
         */
        List<UserActivityDto> findAllWithPageable(@Param("offset") long offset, @Param("pageSize") int pageSize);

        /**
         * 전체 활동 로그 수 조회
         */
        long countAll();

        /**
         * 사용자 ID로 활동 로그 조회
         */
        List<UserActivityDto> findByUserId(@Param("userId") String userId);

        /**
         * 사용자 ID와 페이지네이션으로 활동 로그 조회
         */
        List<UserActivityDto> findByUserIdWithPageable(@Param("userId") String userId,
                        @Param("offset") long offset,
                        @Param("pageSize") int pageSize);

        /**
         * 사용자 ID별 활동 로그 수 조회
         */
        long countByUserId(@Param("userId") String userId);

        /**
         * 활동 타입으로 활동 로그 조회
         */
        List<UserActivityDto> findByActivityType(@Param("activityType") String activityType);

        /**
         * 상태로 활동 로그 조회
         */
        List<UserActivityDto> findByStatus(@Param("status") String status);

        /**
         * IP 주소로 활동 로그 조회
         */
        List<UserActivityDto> findByIpAddress(@Param("ipAddress") String ipAddress);

        /**
         * 날짜 범위로 활동 로그 조회
         */
        List<UserActivityDto> findByTimestampBetween(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        /**
         * 사용자 ID와 날짜 범위로 활동 로그 조회
         */
        List<UserActivityDto> findByUserIdAndTimestampBetween(@Param("userId") String userId,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        /**
         * 최근 활동 로그 조회
         */
        List<UserActivityDto> findRecentActivities(@Param("limit") int limit);

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
        int deleteById(@Param("id") Long id);

        /**
         * 오래된 활동 로그 삭제 (30일 이상)
         */
        int deleteOldActivities();

        /**
         * 고급 검색
         */
        List<UserActivityDto> findByAdvancedSearch(@Param("userId") String userId,
                        @Param("activityType") String activityType,
                        @Param("status") String status,
                        @Param("ipAddress") String ipAddress,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate,
                        @Param("limit") Integer limit);

        /**
         * 고급 검색 결과 수 조회
         */
        long countByAdvancedSearch(@Param("userId") String userId,
                        @Param("activityType") String activityType,
                        @Param("status") String status,
                        @Param("ipAddress") String ipAddress,
                        @Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        /**
         * 활동 타입별 통계 조회
         */
        List<Map<String, Object>> getActivityTypeStatistics();

        /**
         * 시간대별 활동 통계 조회
         */
        List<Map<String, Object>> getHourlyStatistics();
}