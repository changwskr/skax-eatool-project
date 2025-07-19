package com.skax.eatool.user.domain;

import lombok.Builder;
import lombok.Data;

/**
 * 사용자 통계 정보를 담는 클래스
 */
@Data
@Builder
public class UserStatistics {

    /**
     * 전체 사용자 수
     */
    private long totalUsers;

    /**
     * 활성 사용자 수
     */
    private long activeUsers;

    /**
     * 관리자 수
     */
    private long adminUsers;

    /**
     * 오늘 로그인한 사용자 수
     */
    private long todayLoginUsers;

    /**
     * 서비스 상태
     */
    private String serviceStatus;
}