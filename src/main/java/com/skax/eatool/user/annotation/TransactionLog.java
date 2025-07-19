package com.skax.eatool.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 트랜잭션 로그를 자동으로 남기기 위한 어노테이션
 * 이 어노테이션이 붙은 메서드는 실행 시 자동으로 transaction_log 테이블에 기록됩니다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionLog {

    /**
     * 트랜잭션 번호 (기본값: 자동 생성)
     */
    String transactionNo() default "";

    /**
     * 호스트명 (기본값: "HOST001")
     */
    String hostName() default "HOST001";

    /**
     * 시스템명 (기본값: 메서드가 속한 클래스명)
     */
    String systemName() default "";

    /**
     * 메서드명 (기본값: 실제 메서드명)
     */
    String methodName() default "";

    /**
     * 은행 코드 (기본값: "001")
     */
    String bankCode() default "001";

    /**
     * 지점 코드 (기본값: "001")
     */
    String branchCode() default "001";

    /**
     * 채널 타입 (기본값: "WEB")
     */
    String channelType() default "WEB";

    /**
     * 이벤트 번호 (기본값: 자동 생성)
     */
    String eventNo() default "";

    /**
     * 상세 정보를 포함할지 여부 (기본값: true)
     */
    boolean includeDetails() default true;
}