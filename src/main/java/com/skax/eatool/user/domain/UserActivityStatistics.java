package com.skax.eatool.user.domain;

import lombok.Builder;
import lombok.Data;

/**
 * 사용자 활동 로그 통계 도메인 모델
 */
@Data
@Builder
public class UserActivityStatistics {

    /**
     * 오늘 활동 수
     */
    private long todayActivities;

    /**
     * 이번 주 활동 수
     */
    private long thisWeekActivities;

    /**
     * 실패한 로그 수
     */
    private long failedLogs;

    /**
     * 활성 사용자 수 (오늘 로그인한 사용자)
     */
    private long activeUsers;

    /**
     * 전체 활동 로그 수
     */
    private long totalActivities;

    /**
     * 성공한 로그 수
     */
    private long successLogs;

    /**
     * 대기 중인 로그 수
     */
    private long pendingLogs;
}