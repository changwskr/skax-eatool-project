package com.skax.eatool.user.controller.request;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

/**
 * 사용자 활동 로그 검색 요청 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivitySearchRequest {

    /**
     * 사용자 ID
     */
    private String userId;

    /**
     * 활동 타입
     */
    private String activityType;

    /**
     * 상태
     */
    private String status;

    /**
     * 시작 날짜
     */
    private LocalDate startDate;

    /**
     * 종료 날짜
     */
    private LocalDate endDate;

    /**
     * IP 주소
     */
    private String ipAddress;

    /**
     * 페이지 번호 (기본값: 0)
     */
    @Builder.Default
    private Integer page = 0;

    /**
     * 페이지 크기 (기본값: 10)
     */
    @Builder.Default
    private Integer size = 10;

    /**
     * 정렬 필드 (기본값: timestamp)
     */
    @Builder.Default
    private String sortBy = "timestamp";

    /**
     * 정렬 방향 (기본값: DESC)
     */
    @Builder.Default
    private String sortDirection = "DESC";
}