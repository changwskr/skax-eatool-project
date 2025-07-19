package com.skax.eatool.user.controller;

import com.skax.eatool.user.annotation.LogUserActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 활동 로깅 테스트 컨트롤러
 */
@Controller
@RequestMapping("/test/activity")
@RequiredArgsConstructor
@Slf4j
public class UserActivityTestController {

    /**
     * 활동 로깅 테스트 페이지
     */
    @GetMapping
    public String testPage(Model model) {
        log.info("[UserActivityTestController] testPage START");
        model.addAttribute("title", "활동 로깅 테스트");
        log.info("[UserActivityTestController] testPage END");
        return "user/test/activity-test";
    }

    /**
     * 일반 활동 테스트
     */
    @PostMapping("/general")
    @LogUserActivity(activityType = "GENERAL", description = "일반 활동 테스트")
    public String testGeneralActivity(@RequestParam String message, Model model) {
        log.info("[UserActivityTestController] testGeneralActivity START - message: {}", message);

        // 의도적으로 지연을 주어 처리 시간 측정
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        model.addAttribute("title", "활동 로깅 테스트");
        model.addAttribute("message", "일반 활동이 성공적으로 로깅되었습니다: " + message);
        log.info("[UserActivityTestController] testGeneralActivity END");
        return "user/test/activity-test";
    }

    /**
     * 성공 활동 테스트
     */
    @PostMapping("/success")
    @LogUserActivity(activityType = "SUCCESS", description = "성공 활동 테스트")
    public String testSuccessActivity(@RequestParam String message, Model model) {
        log.info("[UserActivityTestController] testSuccessActivity START - message: {}", message);

        model.addAttribute("title", "활동 로깅 테스트");
        model.addAttribute("message", "성공 활동이 성공적으로 로깅되었습니다: " + message);
        log.info("[UserActivityTestController] testSuccessActivity END");
        return "user/test/activity-test";
    }

    /**
     * 실패 활동 테스트
     */
    @PostMapping("/failure")
    @LogUserActivity(activityType = "FAILURE", description = "실패 활동 테스트")
    public String testFailureActivity(@RequestParam String message, Model model) {
        log.info("[UserActivityTestController] testFailureActivity START - message: {}", message);

        // 의도적으로 예외를 발생시켜 실패 로그 테스트
        if ("error".equalsIgnoreCase(message)) {
            throw new RuntimeException("의도적인 테스트 오류: " + message);
        }

        model.addAttribute("title", "활동 로깅 테스트");
        model.addAttribute("message", "실패 활동 테스트가 완료되었습니다: " + message);
        log.info("[UserActivityTestController] testFailureActivity END");
        return "user/test/activity-test";
    }

    /**
     * 상세 정보 포함 활동 테스트
     */
    @PostMapping("/detailed")
    @LogUserActivity(activityType = "DETAILED", description = "상세 정보 포함 활동 테스트", includeDetails = true)
    public String testDetailedActivity(@RequestParam String message,
            @RequestParam(required = false) String additionalInfo,
            Model model) {
        log.info("[UserActivityTestController] testDetailedActivity START - message: {}, additionalInfo: {}",
                message, additionalInfo);

        model.addAttribute("title", "활동 로깅 테스트");
        model.addAttribute("message", "상세 정보 포함 활동이 성공적으로 로깅되었습니다: " + message);
        model.addAttribute("additionalInfo", additionalInfo);
        log.info("[UserActivityTestController] testDetailedActivity END");
        return "user/test/activity-test";
    }
}