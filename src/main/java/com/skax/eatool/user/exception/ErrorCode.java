package com.skax.eatool.user.exception;

public enum ErrorCode {
    NOT_FOUND_ELEMENT("요청한 정보를 찾을 수 없습니다."),
    EXIST_ELEMENT("이미 존재하는 정보입니다."),
    NOT_MATCHED_PASSWORD("비밀번호가 일치하지 않습니다."),
    INVALID_INPUT("잘못된 입력입니다."),
    UNAUTHORIZED("인증이 필요합니다."),
    FORBIDDEN("접근 권한이 없습니다."),
    NOT_FOUND_FILE("파일을 찾을 수 없습니다."),
    JWT_EXPIRED_TOKEN("JWT 토큰이 만료되었습니다."),
    JWT_INVALID_SIGNATURE("JWT 토큰의 서명이 유효하지 않습니다."),
    JWT_INVALID("JWT 토큰이 유효하지 않습니다."),
    JWT_NOT_FOUND("JWT 토큰을 찾을 수 없습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
