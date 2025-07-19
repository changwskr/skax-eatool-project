package com.skax.eatool.user.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * ?�용???�동 로그 JPA ?�티??
 */
@Entity
@Table(name = "user_activities")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActivityEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "activity_type", nullable = false, length = 50)
    private String activityType;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "session_id", length = 255)
    private String sessionId;

    @Column(name = "activity_timestamp", nullable = false)
    private LocalDateTime activityTimestamp;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "processing_time")
    private Long processingTime;

    @Column(name = "additional_info", columnDefinition = "TEXT")
    private String additionalInfo;

    // 도메인 객체로 변환
    public com.skax.eatool.user.domain.UserActivity toDomain() {
        return com.skax.eatool.user.domain.UserActivity.builder()
                .id(this.id)
                .userId(this.userId)
                .activityType(this.activityType)
                .description(this.description)
                .ipAddress(this.ipAddress)
                .userAgent(this.userAgent)
                .sessionId(this.sessionId)
                .activityTimestamp(this.activityTimestamp)
                .status(this.status)
                .processingTime(this.processingTime)
                .additionalInfo(this.additionalInfo)
                .build();
    }

    // 도메인 객체에서 엔티티 생성
    public static UserActivityEntity fromDomain(com.skax.eatool.user.domain.UserActivity userActivity) {
        return UserActivityEntity.builder()
                .id(userActivity.getId())
                .userId(userActivity.getUserId())
                .activityType(userActivity.getActivityType())
                .description(userActivity.getDescription())
                .ipAddress(userActivity.getIpAddress())
                .userAgent(userActivity.getUserAgent())
                .sessionId(userActivity.getSessionId())
                .activityTimestamp(userActivity.getActivityTimestamp())
                .status(userActivity.getStatus())
                .processingTime(userActivity.getProcessingTime())
                .additionalInfo(userActivity.getAdditionalInfo())
                .build();
    }
}
