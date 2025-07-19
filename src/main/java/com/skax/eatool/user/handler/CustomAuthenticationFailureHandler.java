package com.skax.eatool.user.handler;

import com.skax.eatool.user.domain.UserActivity;
import com.skax.eatool.user.service.port.UserActivityServicePort;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 로그인 실패 시 사용자 활동을 로깅하는 핸들러
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final UserActivityServicePort userActivityServicePort;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter("username");
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String sessionId = request.getSession().getId();

        log.warn("[CustomAuthenticationFailureHandler] Login failed - username: {}, ipAddress: {}, error: {}",
                username, ipAddress, exception.getMessage());

        try {
            // 로그인 실패 활동 로그 생성
            UserActivity failedActivity = UserActivity.builder()
                    .userId(username != null ? username : "UNKNOWN")
                    .activityType("LOGIN")
                    .description("로그인 실패 - " + exception.getMessage())
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .sessionId(sessionId)
                    .status("FAILED")
                    .processingTime(0L)
                    .additionalInfo("{\"errorType\": \"" + exception.getClass().getSimpleName()
                            + "\", \"errorMessage\": \"" + exception.getMessage() + "\"}")
                    .activityTimestamp(LocalDateTime.now())
                    .build();

            userActivityServicePort.saveActivity(failedActivity);

            log.info("[CustomAuthenticationFailureHandler] Login failure activity logged successfully - username: {}",
                    username);

        } catch (Exception e) {
            log.error(
                    "[CustomAuthenticationFailureHandler] Failed to log login failure activity - username: {}, error: {}",
                    username, e.getMessage(), e);
        }

        // 로그인 실패 페이지로 리다이렉트
        response.sendRedirect("/login?error=true");
    }

    /**
     * 클라이언트 IP 주소 조회
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}