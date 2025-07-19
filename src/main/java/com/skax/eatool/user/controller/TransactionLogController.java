package com.skax.eatool.user.controller;

import com.skax.eatool.user.entity.TransactionLogEntity;
import com.skax.eatool.user.service.TransactionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 트랜잭션 로그 컨트롤러
 */
@Controller
@RequestMapping("/transaction-logs")
@RequiredArgsConstructor
@Slf4j
public class TransactionLogController {

    private final TransactionLogService transactionLogService;

    /**
     * 트랜잭션 로그 목록 페이지
     */
    @GetMapping
    public String listTransactionLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String systemName,
            @RequestParam(required = false) String methodName,
            @RequestParam(required = false) String errorCode,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Model model) {

        log.info("[TransactionLogController] listTransactionLogs START - page: {}, size: {}, userId: {}, systemName: {}", 
                page, size, userId, systemName);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        // 검색 조건에 따른 데이터 조회
        List<TransactionLogEntity> transactionLogs;
        long totalCount;
        
        if (userId != null && !userId.trim().isEmpty()) {
            transactionLogs = transactionLogService.getTransactionLogsByUserId(userId);
            totalCount = transactionLogs.size();
        } else if (systemName != null && !systemName.trim().isEmpty()) {
            transactionLogs = transactionLogService.getTransactionLogsBySystemName(systemName);
            totalCount = transactionLogs.size();
        } else if (methodName != null && !methodName.trim().isEmpty()) {
            transactionLogs = transactionLogService.getTransactionLogsByMethodName(methodName);
            totalCount = transactionLogs.size();
        } else if (errorCode != null && !errorCode.trim().isEmpty()) {
            transactionLogs = transactionLogService.getTransactionLogsByErrorCode(errorCode);
            totalCount = transactionLogs.size();
        } else if (startDate != null && endDate != null && !startDate.trim().isEmpty() && !endDate.trim().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime start = LocalDateTime.parse(startDate, formatter);
            LocalDateTime end = LocalDateTime.parse(endDate, formatter);
            transactionLogs = transactionLogService.getTransactionLogsByDateRange(start, end);
            totalCount = transactionLogs.size();
        } else {
            // 전체 조회 (페이징 적용)
            transactionLogs = transactionLogService.getAllTransactionLogs(pageable);
            totalCount = transactionLogService.getTotalTransactionCount();
        }

        // 통계 정보
        long todayCount = transactionLogService.getTodayTransactionCount();
        long thisWeekCount = transactionLogService.getThisWeekTransactionCount();
        long failedCount = transactionLogService.getFailedTransactionCount();
        List<Map<String, Object>> systemStats = transactionLogService.getSystemStatistics();

        model.addAttribute("transactionLogs", transactionLogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / size));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("todayCount", todayCount);
        model.addAttribute("thisWeekCount", thisWeekCount);
        model.addAttribute("failedCount", failedCount);
        model.addAttribute("systemStats", systemStats);
        model.addAttribute("searchUserId", userId);
        model.addAttribute("searchSystemName", systemName);
        model.addAttribute("searchMethodName", methodName);
        model.addAttribute("searchErrorCode", errorCode);
        model.addAttribute("searchStartDate", startDate);
        model.addAttribute("searchEndDate", endDate);

        log.info("[TransactionLogController] listTransactionLogs END - count: {}", transactionLogs.size());
        return "transaction-logs/list";
    }

    /**
     * 트랜잭션 로그 상세 페이지
     */
    @GetMapping("/{id}")
    public String getTransactionLogDetail(@PathVariable Long id, Model model) {
        log.info("[TransactionLogController] getTransactionLogDetail START - id: {}", id);

        TransactionLogEntity transactionLog = transactionLogService.getTransactionLogById(id);
        if (transactionLog == null) {
            model.addAttribute("error", "트랜잭션 로그를 찾을 수 없습니다.");
            return "error/404";
        }

        model.addAttribute("transactionLog", transactionLog);
        
        // 응답 시간을 초 단위로 변환
        double responseTimeSeconds = transactionLog.getResponseTime() != null ? 
                transactionLog.getResponseTime() / 1000.0 : 0.0;
        model.addAttribute("responseTimeSeconds", String.format("%.3f", responseTimeSeconds));

        log.info("[TransactionLogController] getTransactionLogDetail END - transactionId: {}", 
                transactionLog.getTransactionId());
        return "transaction-logs/detail";
    }

    /**
     * 트랜잭션 로그 통계 페이지
     */
    @GetMapping("/statistics")
    public String getTransactionStatistics(Model model) {
        log.info("[TransactionLogController] getTransactionStatistics START");

        long todayCount = transactionLogService.getTodayTransactionCount();
        long thisWeekCount = transactionLogService.getThisWeekTransactionCount();
        long failedCount = transactionLogService.getFailedTransactionCount();
        List<Map<String, Object>> systemStats = transactionLogService.getSystemStatistics();
        List<Map<String, Object>> hourlyStats = transactionLogService.getHourlyStatistics();

        model.addAttribute("todayCount", todayCount);
        model.addAttribute("thisWeekCount", thisWeekCount);
        model.addAttribute("failedCount", failedCount);
        model.addAttribute("systemStats", systemStats);
        model.addAttribute("hourlyStats", hourlyStats);

        log.info("[TransactionLogController] getTransactionStatistics END");
        return "transaction-logs/statistics";
    }

    /**
     * 트랜잭션 로그 삭제 (소프트 삭제)
     */
    @PostMapping("/{id}/delete")
    @ResponseBody
    public Map<String, Object> deleteTransactionLog(@PathVariable Long id) {
        log.info("[TransactionLogController] deleteTransactionLog START - id: {}", id);

        try {
            transactionLogService.deleteTransactionLog(id);
            log.info("[TransactionLogController] deleteTransactionLog END - success");
            return Map.of("success", true, "message", "트랜잭션 로그가 삭제되었습니다.");
        } catch (Exception e) {
            log.error("[TransactionLogController] deleteTransactionLog ERROR", e);
            return Map.of("success", false, "message", "트랜잭션 로그 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    /**
     * 트랜잭션 로그 복구
     */
    @PostMapping("/{id}/restore")
    @ResponseBody
    public Map<String, Object> restoreTransactionLog(@PathVariable Long id) {
        log.info("[TransactionLogController] restoreTransactionLog START - id: {}", id);

        try {
            transactionLogService.restoreTransactionLog(id);
            log.info("[TransactionLogController] restoreTransactionLog END - success");
            return Map.of("success", true, "message", "트랜잭션 로그가 복구되었습니다.");
        } catch (Exception e) {
            log.error("[TransactionLogController] restoreTransactionLog ERROR", e);
            return Map.of("success", false, "message", "트랜잭션 로그 복구에 실패했습니다: " + e.getMessage());
        }
    }
} 