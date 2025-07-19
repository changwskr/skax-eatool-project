package com.skax.eatool.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 사용자 활동을 자동으로 로깅하기 위한 어노테이션
 * 이 어노테이션이 붙은 메서드는 실행 시 자동으로 user_activities 테이블에 기록됩니다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogUserActivity {

    /**
     * 활동 타입 (기본값: "GENERAL")
     */
    String activityType() default "GENERAL";

    /**
     * 활동 설명 (기본값: 메서드명)
     */
    String description() default "";

    /**
     * 성공 시 상태 (기본값: "SUCCESS")
     */
    String successStatus() default "SUCCESS";

    /**
     * 실패 시 상태 (기본값: "FAILED")
     */
    String failureStatus() default "FAILED";

    /**
     * 추가 정보를 포함할지 여부 (기본값: true)
     */
    boolean includeDetails() default true;
}