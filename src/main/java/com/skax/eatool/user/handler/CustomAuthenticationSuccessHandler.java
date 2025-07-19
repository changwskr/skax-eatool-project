package com.skax.eatool.user.handler;

import com.skax.eatool.user.service.port.UserActivityServicePort;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 로그인 성공 시 사용자 활동을 로깅하는 핸들러
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserActivityServicePort userActivityServicePort;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String userId = authentication.getName();
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String sessionId = request.getSession().getId();

        log.info("[CustomAuthenticationSuccessHandler] Login success - userId: {}, ipAddress: {}", userId, ipAddress);

        try {
            // 로그인 성공 활동 로그 생성
            userActivityServicePort.createLoginActivity(userId, ipAddress, userAgent, sessionId);

            log.info("[CustomAuthenticationSuccessHandler] Login activity logged successfully - userId: {}", userId);

        } catch (Exception e) {
            log.error("[CustomAuthenticationSuccessHandler] Failed to log login activity - userId: {}, error: {}",
                    userId, e.getMessage(), e);
        }

        // 기본 성공 페이지로 리다이렉트
        response.sendRedirect("/home");
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