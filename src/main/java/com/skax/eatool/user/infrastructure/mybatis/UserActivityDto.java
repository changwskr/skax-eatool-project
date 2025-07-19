package com.skax.eatool.user.infrastructure.mybatis;

import com.skax.eatool.user.domain.UserActivity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * MyBatis용 사용자 활동 로그 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityDto {

    private Long id;
    private String userId;
    private String activityType;
    private String description;
    private String ipAddress;
    private String userAgent;
    private String sessionId;
    private String status;
    private Long processingTime;
    private String additionalInfo;
    private LocalDateTime timestamp;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    /**
     * 도메인 모델을 DTO로 변환
     */
    public static UserActivityDto from(UserActivity userActivity) {
        return UserActivityDto.builder()
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
                .timestamp(userActivity.getTimestamp())
                .createdDate(userActivity.getCreatedDate())
                .lastModifiedDate(userActivity.getLastModifiedDate())
                .build();
    }

    /**
     * DTO를 도메인 모델로 변환
     */
    public UserActivity toModel() {
        return UserActivity.builder()
                .id(this.id)
                .userId(this.userId)
                .activityType(this.activityType)
                .description(this.description)
                .ipAddress(this.ipAddress)
                .userAgent(this.userAgent)
                .sessionId(this.sessionId)
                .status(this.status)
                .processingTime(this.processingTime)
                .additionalInfo(this.additionalInfo)
                .timestamp(this.timestamp)
                .createdDate(this.createdDate)
                .lastModifiedDate(this.lastModifiedDate)
                .build();
    }
}