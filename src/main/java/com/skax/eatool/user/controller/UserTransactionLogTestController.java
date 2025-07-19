package com.skax.eatool.user.controller;

import com.skax.eatool.user.annotation.TransactionLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 트랜잭션 로그 테스트 컨트롤러
 */
@Controller("userTransactionLogTestController")
@RequestMapping("/test/transaction")
@RequiredArgsConstructor
@Slf4j
public class UserTransactionLogTestController {

    /**
     * 트랜잭션 로그 테스트 페이지
     */
    @GetMapping
    public String testPage(Model model) {
        log.info("[TransactionLogTestController] testPage START");
        model.addAttribute("title", "트랜잭션 로그 테스트");
        log.info("[TransactionLogTestController] testPage END");
        return "user/test/transaction-test";
    }

    /**
     * 기본 트랜잭션 테스트
     */
    @PostMapping("/basic")
    @TransactionLog(systemName = "TEST_SYSTEM", methodName = "basicTransaction")
    public String testBasicTransaction(@RequestParam String message, Model model) {
        log.info("[TransactionLogTestController] testBasicTransaction START - message: {}", message);

        // 의도적으로 지연을 주어 처리 시간 측정
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        model.addAttribute("title", "트랜잭션 로그 테스트");
        model.addAttribute("message", "기본 트랜잭션이 성공적으로 로깅되었습니다: " + message);
        log.info("[TransactionLogTestController] testBasicTransaction END");
        return "user/test/transaction-test";
    }

    /**
     * 성공 트랜잭션 테스트
     */
    @PostMapping("/success")
    @TransactionLog(systemName = "TEST_SYSTEM", methodName = "successTransaction", bankCode = "001", branchCode = "001", channelType = "WEB")
    public String testSuccessTransaction(@RequestParam String message, Model model) {
        log.info("[TransactionLogTestController] testSuccessTransaction START - message: {}", message);

        model.addAttribute("title", "트랜잭션 로그 테스트");
        model.addAttribute("message", "성공 트랜잭션이 성공적으로 로깅되었습니다: " + message);
        log.info("[TransactionLogTestController] testSuccessTransaction END");
        return "user/test/transaction-test";
    }

    /**
     * 실패 트랜잭션 테스트
     */
    @PostMapping("/failure")
    @TransactionLog(systemName = "TEST_SYSTEM", methodName = "failureTransaction")
    public String testFailureTransaction(@RequestParam String message, Model model) {
        log.info("[TransactionLogTestController] testFailureTransaction START - message: {}", message);

        // 의도적으로 예외를 발생시켜 실패 로그 테스트
        if ("error".equalsIgnoreCase(message)) {
            throw new RuntimeException("의도적인 트랜잭션 오류: " + message);
        }

        model.addAttribute("title", "트랜잭션 로그 테스트");
        model.addAttribute("message", "실패 트랜잭션 테스트가 완료되었습니다: " + message);
        log.info("[TransactionLogTestController] testFailureTransaction END");
        return "user/test/transaction-test";
    }

    /**
     * 상세 정보 포함 트랜잭션 테스트
     */
    @PostMapping("/detailed")
    @TransactionLog(systemName = "TEST_SYSTEM", methodName = "detailedTransaction", transactionNo = "CUSTOM_TXN", eventNo = "CUSTOM_EVT", includeDetails = true)
    public String testDetailedTransaction(@RequestParam String message,
            @RequestParam(required = false) String additionalInfo,
            Model model) {
        log.info("[TransactionLogTestController] testDetailedTransaction START - message: {}, additionalInfo: {}",
                message, additionalInfo);

        model.addAttribute("title", "트랜잭션 로그 테스트");
        model.addAttribute("message", "상세 정보 포함 트랜잭션이 성공적으로 로깅되었습니다: " + message);
        model.addAttribute("additionalInfo", additionalInfo);
        log.info("[TransactionLogTestController] testDetailedTransaction END");
        return "user/test/transaction-test";
    }

    /**
     * 시스템별 트랜잭션 테스트
     */
    @PostMapping("/system")
    @TransactionLog(systemName = "CASH_CARD", methodName = "cardTransaction", bankCode = "001", branchCode = "002", channelType = "WEB")
    public String testSystemTransaction(@RequestParam String message, Model model) {
        log.info("[TransactionLogTestController] testSystemTransaction START - message: {}", message);

        model.addAttribute("title", "트랜잭션 로그 테스트");
        model.addAttribute("message", "시스템별 트랜잭션이 성공적으로 로깅되었습니다: " + message);
        log.info("[TransactionLogTestController] testSystemTransaction END");
        return "user/test/transaction-test";
    }

    /**
     * REST API 테스트 페이지
     */
    @GetMapping("/api-test")
    public String apiTestPage(Model model) {
        log.info("[UserTransactionLogTestController] apiTestPage START");
        model.addAttribute("title", "트랜잭션 로그 REST API 테스트");
        log.info("[UserTransactionLogTestController] apiTestPage END");
        return "user/test/transaction-api-test";
    }
}