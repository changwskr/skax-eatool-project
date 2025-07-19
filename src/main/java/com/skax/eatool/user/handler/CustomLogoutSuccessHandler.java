package com.skax.eatool.user.handler;

import com.skax.eatool.user.service.port.UserActivityServicePort;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 로그아웃 성공 시 사용자 활동을 로깅하는 핸들러
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final UserActivityServicePort userActivityServicePort;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            String userId = authentication.getName();
            String ipAddress = getClientIpAddress(request);
            String userAgent = request.getHeader("User-Agent");
            String sessionId = request.getSession().getId();

            log.info("[CustomLogoutSuccessHandler] Logout success - userId: {}, ipAddress: {}", userId, ipAddress);

            try {
                // 로그아웃 성공 활동 로그 생성
                userActivityServicePort.createLogoutActivity(userId, ipAddress, userAgent, sessionId);

                log.info("[CustomLogoutSuccessHandler] Logout activity logged successfully - userId: {}", userId);

            } catch (Exception e) {
                log.error("[CustomLogoutSuccessHandler] Failed to log logout activity - userId: {}, error: {}",
                        userId, e.getMessage(), e);
            }
        }

        // 로그아웃 성공 페이지로 리다이렉트
        response.sendRedirect("/login?logout=true");
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