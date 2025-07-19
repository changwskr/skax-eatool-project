package com.skax.eatool.user.controller.response;

import com.skax.eatool.user.domain.UserActivity;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 사용자 활동 로그 응답 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityResponse {

    /**
     * 활동 로그 ID
     */
    private Long id;

    /**
     * 사용자 ID
     */
    private String userId;

    /**
     * 활동 타입
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
     * 활동 상태
     */
    private String status;

    /**
     * 처리 시간 (밀리초)
     */
    private Long processingTime;

    /**
     * 추가 정보
     */
    private String additionalInfo;

    /**
     * 활동 발생 시간 (포맷된 문자열)
     */
    private String timestamp;

    /**
     * 생성 시간 (포맷된 문자열)
     */
    private String createdDate;

    /**
     * 수정 시간 (포맷된 문자열)
     */
    private String lastModifiedDate;

    /**
     * 활동 타입별 색상 클래스
     */
    private String activityTypeColor;

    /**
     * 상태별 색상 클래스
     */
    private String statusColor;

    /**
     * 도메인 모델을 응답 DTO로 변환
     */
    public static UserActivityResponse from(UserActivity userActivity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return UserActivityResponse.builder()
                .id(userActivity.getId())
                .userId(userActivity.getUserId())
                .activityType(userActivity.getActivityType())
                .description(userActivity.getDescription())
                .ipAddress(userActivity.getIpAddress())
                .userAgent(userActivity.getUserAgent())
                .sessionId(userActivity.getSessionId())
                .status(userActivity.getStatus())
                .processingTime(userActivity.getProcessingTime())
                .additionalInfo(userActivity.getAdditionalInfo())
                .timestamp(userActivity.getActivityTimestamp() != null
                        ? userActivity.getActivityTimestamp().format(formatter)
                        : null)
                .createdDate(
                        userActivity.getCreatedDate() != null ? userActivity.getCreatedDate().format(formatter) : null)
                .lastModifiedDate(userActivity.getLastModifiedDate() != null
                        ? userActivity.getLastModifiedDate().format(formatter)
                        : null)
                .activityTypeColor(getActivityTypeColor(userActivity.getActivityType()))
                .statusColor(getStatusColor(userActivity.getStatus()))
                .build();
    }

    /**
     * 활동 타입별 색상 클래스 반환
     */
    private static String getActivityTypeColor(String activityType) {
        return switch (activityType) {
            case "LOGIN" -> "text-success";
            case "LOGOUT" -> "text-warning";
            case "CREATE" -> "text-primary";
            case "UPDATE" -> "text-info";
            case "DELETE" -> "text-danger";
            default -> "text-secondary";
        };
    }

    /**
     * 상태별 색상 클래스 반환
     */
    private static String getStatusColor(String status) {
        return switch (status) {
            case "SUCCESS" -> "text-success";
            case "FAILED" -> "text-danger";
            case "PENDING" -> "text-warning";
            default -> "text-secondary";
        };
    }
}