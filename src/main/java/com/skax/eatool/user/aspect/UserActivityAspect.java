package com.skax.eatool.user.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skax.eatool.user.annotation.LogUserActivity;
import com.skax.eatool.user.domain.UserActivity;
import com.skax.eatool.user.service.port.UserActivityServicePort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 사용자 활동을 자동으로 로깅하는 AOP Aspect
 * 
 * @LogUserActivity 어노테이션이 붙은 메서드의 실행을 추적하여 user_activities 테이블에 기록
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class UserActivityAspect {

    private final UserActivityServicePort userActivityServicePort;
    private final ObjectMapper objectMapper;

    /**
     * @LogUserActivity 어노테이션이 붙은 메서드 실행을 추적
     */
    @Around("@annotation(com.skax.eatool.user.annotation.LogUserActivity)")
    public Object logUserActivity(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String userId = getCurrentUserId();
        String ipAddress = getClientIpAddress();
        String userAgent = getUserAgent();
        String sessionId = getSessionId();

        // 어노테이션 정보 추출
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogUserActivity annotation = signature.getMethod().getAnnotation(LogUserActivity.class);

        String activityType = annotation.activityType();
        String description = annotation.description().isEmpty() ? signature.getMethod().getName()
                : annotation.description();
        String successStatus = annotation.successStatus();
        String failureStatus = annotation.failureStatus();
        boolean includeDetails = annotation.includeDetails();

        log.info("[UserActivityAspect] Activity started - userId: {}, activityType: {}, description: {}",
                userId, activityType, description);

        try {
            // 메서드 실행
            Object result = joinPoint.proceed();

            // 성공 로그 기록
            long processingTime = System.currentTimeMillis() - startTime;
            logActivity(userId, activityType, description, ipAddress, userAgent, sessionId,
                    successStatus, processingTime, includeDetails ? getMethodDetails(joinPoint, result) : null);

            return result;

        } catch (Exception e) {
            // 실패 로그 기록
            long processingTime = System.currentTimeMillis() - startTime;
            String errorDescription = description + " - 오류: " + e.getMessage();
            logActivity(userId, activityType, errorDescription, ipAddress, userAgent, sessionId,
                    failureStatus, processingTime, includeDetails ? getErrorDetails(e) : null);

            throw e;
        }
    }

    /**
     * 현재 로그인한 사용자 ID 조회
     */
    private String getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() &&
                    !"anonymousUser".equals(authentication.getName())) {
                return authentication.getName();
            }
        } catch (Exception e) {
            log.warn("[UserActivityAspect] Failed to get current user ID: {}", e.getMessage());
        }
        return "SYSTEM";
    }

    /**
     * 클라이언트 IP 주소 조회
     */
    private String getClientIpAddress() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String xForwardedFor = request.getHeader("X-Forwarded-For");
                if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                    return xForwardedFor.split(",")[0].trim();
                }
                return request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.warn("[UserActivityAspect] Failed to get client IP address: {}", e.getMessage());
        }
        return "unknown";
    }

    /**
     * 사용자 에이전트 조회
     */
    private String getUserAgent() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                return request.getHeader("User-Agent");
            }
        } catch (Exception e) {
            log.warn("[UserActivityAspect] Failed to get user agent: {}", e.getMessage());
        }
        return "unknown";
    }

    /**
     * 세션 ID 조회
     */
    private String getSessionId() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                return request.getSession().getId();
            }
        } catch (Exception e) {
            log.warn("[UserActivityAspect] Failed to get session ID: {}", e.getMessage());
        }
        return "unknown";
    }

    /**
     * 메서드 실행 상세 정보 생성
     */
    private String getMethodDetails(ProceedingJoinPoint joinPoint, Object result) {
        try {
            Map<String, Object> details = new HashMap<>();
            details.put("methodName", joinPoint.getSignature().getName());
            details.put("className", joinPoint.getTarget().getClass().getSimpleName());
            details.put("parameters", joinPoint.getArgs());
            details.put("result", result);
            details.put("timestamp", LocalDateTime.now().toString());

            return objectMapper.writeValueAsString(details);
        } catch (Exception e) {
            log.warn("[UserActivityAspect] Failed to create method details: {}", e.getMessage());
            return "{\"error\": \"Failed to serialize details\"}";
        }
    }

    /**
     * 오류 상세 정보 생성
     */
    private String getErrorDetails(Exception e) {
        try {
            Map<String, Object> details = new HashMap<>();
            details.put("errorType", e.getClass().getSimpleName());
            details.put("errorMessage", e.getMessage());
            details.put("timestamp", LocalDateTime.now().toString());

            return objectMapper.writeValueAsString(details);
        } catch (Exception ex) {
            log.warn("[UserActivityAspect] Failed to create error details: {}", ex.getMessage());
            return "{\"error\": \"Failed to serialize error details\"}";
        }
    }

    /**
     * 사용자 활동 로그 저장
     */
    private void logActivity(String userId, String activityType, String description,
            String ipAddress, String userAgent, String sessionId,
            String status, long processingTime, String additionalInfo) {
        try {
            UserActivity activity = UserActivity.builder()
                    .userId(userId)
                    .activityType(activityType)
                    .description(description)
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .sessionId(sessionId)
                    .status(status)
                    .processingTime(processingTime)
                    .additionalInfo(additionalInfo)
                    .activityTimestamp(LocalDateTime.now())
                    .build();

            userActivityServicePort.saveActivity(activity);

            log.info("[UserActivityAspect] Activity logged successfully - userId: {}, activityType: {}, status: {}",
                    userId, activityType, status);

        } catch (Exception e) {
            log.error("[UserActivityAspect] Failed to log activity - userId: {}, activityType: {}, error: {}",
                    userId, activityType, e.getMessage(), e);
        }
    }
}