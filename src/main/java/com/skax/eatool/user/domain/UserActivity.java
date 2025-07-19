package com.skax.eatool.user.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자 활동 로그 도메인 모델
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {

    /**
     * 활동 로그 ID
     */
    private Long id;

    /**
     * 사용자 ID
     */
    private String userId;

    /**
     * 활동 타입 (LOGIN, LOGOUT, CREATE, UPDATE, DELETE)
     */
    private String activityType;

    /**
     * 활동 설명
     */
    private String description;

    /**
     * IP 주소
     */
    private String ipAddress;

    /**
     * 사용자 에이전트
     */
    private String userAgent;

    /**
     * 세션 ID
     */
    private String sessionId;

    /**
     * 활동 상태 (SUCCESS, FAILED, PENDING)
     */
    private String status;

    /**
     * 처리 시간 (밀리초)
     */
    private Long processingTime;

    /**
     * 추가 정보 (JSON 형태)
     */
    private String additionalInfo;

    /**
     * 활동 발생 시간
     */
    private LocalDateTime timestamp;

    /**
     * 생성 시간
     */
    private LocalDateTime createdDate;

    /**
     * 수정 시간
     */
    private LocalDateTime lastModifiedDate;
}
