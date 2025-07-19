package com.skax.eatool.user.aspect;

import com.skax.eatool.user.annotation.TransactionLog;
import com.skax.eatool.user.service.TransactionLogService;
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
import java.time.format.DateTimeFormatter;

/**
 * 트랜잭션 로그를 자동으로 남기는 AOP Aspect
 * 
 * @Service 어노테이션이 붙은 모든 컴포넌트의 메서드 실행을 추적하여 transaction_log 테이블에 기록
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionLogAspect {

    private final TransactionLogService transactionLogService;

    /**
     * @Service 어노테이션이 붙은 모든 컴포넌트의 메서드 실행을 추적
     *          단, TransactionLogService는 제외하여 무한 재귀 방지
     */
    @Around("@within(org.springframework.stereotype.Service) && !within(com.skax.eatool.user.service.TransactionLogService)")
    public Object logTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String inTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));

        // 기본 정보 추출
        String userId = getCurrentUserId();
        String ipAddress = getClientIpAddress();
        String systemName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        // 어노테이션 정보 추출 (있는 경우)
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        TransactionLog annotation = signature.getMethod().getAnnotation(TransactionLog.class);

        // 어노테이션이 있으면 어노테이션 값 사용, 없으면 기본값 사용
        String transactionNo = annotation != null && !annotation.transactionNo().isEmpty() ? annotation.transactionNo()
                : "";
        String hostName = annotation != null ? annotation.hostName() : "HOST001";
        String customSystemName = annotation != null && !annotation.systemName().isEmpty() ? annotation.systemName()
                : systemName;
        String customMethodName = annotation != null && !annotation.methodName().isEmpty() ? annotation.methodName()
                : methodName;
        String bankCode = annotation != null ? annotation.bankCode() : "001";
        String branchCode = annotation != null ? annotation.branchCode() : "001";
        String channelType = annotation != null ? annotation.channelType() : "WEB";
        String eventNo = annotation != null && !annotation.eventNo().isEmpty() ? annotation.eventNo() : "";

        log.info("[TransactionLogAspect] Transaction started - userId: {}, systemName: {}, methodName: {}",
                userId, customSystemName, customMethodName);

        try {
            // 메서드 실행
            Object result = joinPoint.proceed();

            // 성공 로그 기록
            long responseTime = System.currentTimeMillis() - startTime;
            String outTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
            String errorCode = "I0000"; // 성공 코드

            transactionLogService.createTransactionLog(
                    userId, customSystemName, customMethodName, bankCode, branchCode, channelType,
                    inTime, outTime, responseTime, errorCode, ipAddress);

            log.info(
                    "[TransactionLogAspect] Transaction logged successfully - userId: {}, systemName: {}, methodName: {}, responseTime: {}ms",
                    userId, customSystemName, customMethodName, responseTime);

            return result;

        } catch (Exception e) {
            // 실패 로그 기록
            long responseTime = System.currentTimeMillis() - startTime;
            String outTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
            String errorCode = "E" + String.format("%04d", Math.abs(e.hashCode() % 10000)); // 에러 코드 생성

            transactionLogService.createTransactionLog(
                    userId, customSystemName, customMethodName, bankCode, branchCode, channelType,
                    inTime, outTime, responseTime, errorCode, ipAddress);

            log.error(
                    "[TransactionLogAspect] Transaction failed and logged - userId: {}, systemName: {}, methodName: {}, errorCode: {}, error: {}",
                    userId, customSystemName, customMethodName, errorCode, e.getMessage());

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
            log.warn("[TransactionLogAspect] Failed to get current user ID: {}", e.getMessage());
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
            log.warn("[TransactionLogAspect] Failed to get client IP address: {}", e.getMessage());
        }
        return "unknown";
    }
}