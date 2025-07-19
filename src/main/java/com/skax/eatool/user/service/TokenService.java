package com.skax.eatool.user.service;

import com.skax.eatool.app.util.JwtUtil;
import com.skax.eatool.user.exception.CustomException;
import com.skax.eatool.user.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final JwtUtil jwtUtil;

    /**
     * JWT 토큰을 검증하고 사용자 ID를 추출합니다.
     *
     * @param token JWT 토큰
     * @return 사용자 ID
     * @throws CustomException 토큰이 유효하지 않은 경우
     */
    public String validateTokenAndExtractUserId(String token) {
        log.debug("[TokenService] validateTokenAndExtractUserId START - token length: {}",
                token != null ? token.length() : 0);

        try {
            String userId = jwtUtil.validateTokenAndExtractUID(token);
            log.debug("[TokenService] validateTokenAndExtractUserId END - userId: {}", userId);
            return userId;
        } catch (Exception e) {
            log.error("[TokenService] validateTokenAndExtractUserId ERROR - token validation failed: {}",
                    e.getMessage());
            throw new CustomException(ErrorCode.JWT_INVALID);
        }
    }

    /**
     * JWT 토큰을 생성합니다.
     *
     * @param claims 토큰에 포함할 클레임 정보
     * @return 생성된 JWT 토큰
     */
    public String generateToken(Map<String, Object> claims) {
        log.debug("[TokenService] generateToken START - claims size: {}", claims != null ? claims.size() : 0);

        String token = jwtUtil.generateToken(claims);
        log.debug("[TokenService] generateToken END - token length: {}", token.length());
        return token;
    }
}