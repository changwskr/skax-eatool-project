package com.skax.eatool.user.controller;

import com.skax.eatool.user.entity.TransactionLogEntity;
import com.skax.eatool.user.service.TransactionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 트랜잭션 로그 컨트롤러
 */
@Controller("userTransactionLogController")
@RequestMapping({ "/transaction-log", "/api/v1/transaction-log" })
@RequiredArgsConstructor
@Slf4j
public class UserTransactionLogController {

    private final TransactionLogService transactionLogService;

    /**
     * 트랜잭션 로그 메인 페이지
     */
    @GetMapping
    public String transactionLogMain(Model model) {
        log.info("[TransactionLogController] transactionLogMain START");

        try {
            // 통계 정보 조회
            long todayTransactions = transactionLogService.getTodayTransactionCount();
            long thisWeekTransactions = transactionLogService.getThisWeekTransactionCount();
            long failedTransactions = transactionLogService.getFailedTransactionCount();
            List<Map<String, Object>> systemStatistics = transactionLogService.getSystemStatistics();

            model.addAttribute("title", "트랜잭션 로그");
            model.addAttribute("todayTransactions", todayTransactions);
            model.addAttribute("thisWeekTransactions", thisWeekTransactions);
            model.addAttribute("failedTransactions", failedTransactions);
            model.addAttribute("systemStatistics", systemStatistics);

            log.info("[TransactionLogController] transactionLogMain END");
            return "user/transaction-log/main";
        } catch (Exception e) {
            log.error("Error in transaction log main page: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "트랜잭션 로그 정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("title", "트랜잭션 로그");
            return "user/transaction-log/main";
        }
    }

    /**
     * 트랜잭션 로그 목록 페이지
     */
    @GetMapping("/list")
    public String transactionLogList(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String systemName,
            @RequestParam(required = false) String methodName,
            @RequestParam(required = false) String errorCode,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {

        log.info("[TransactionLogController] transactionLogList START");

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

            // 검색 조건에 따른 로그 조회
            List<TransactionLogEntity> transactionLogs;
            long totalCount = 0;

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
            } else if (startDate != null && !startDate.trim().isEmpty() && endDate != null
                    && !endDate.trim().isEmpty()) {
                LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
                LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
                transactionLogs = transactionLogService.getTransactionLogsByDateRange(start, end);
                totalCount = transactionLogs.size();
            } else {
                // 기본적으로 최근 100개 로그 조회
                transactionLogs = transactionLogService.getTransactionLogsByDateRange(
                        LocalDateTime.now().minusDays(7), LocalDateTime.now());
                totalCount = transactionLogs.size();
            }

            // 페이징 처리
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, transactionLogs.size());
            List<TransactionLogEntity> pagedLogs = transactionLogs.subList(startIndex, endIndex);

            int totalPages = (int) Math.ceil((double) totalCount / size);

            model.addAttribute("title", "트랜잭션 로그 목록");
            model.addAttribute("transactionLogs", pagedLogs);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("searchUserId", userId);
            model.addAttribute("searchSystemName", systemName);
            model.addAttribute("searchMethodName", methodName);
            model.addAttribute("searchErrorCode", errorCode);
            model.addAttribute("searchStartDate", startDate);
            model.addAttribute("searchEndDate", endDate);

            log.info("[TransactionLogController] transactionLogList END - count: {}", pagedLogs.size());
            return "user/transaction-log/list";
        } catch (Exception e) {
            log.error("Error in transaction log list page: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "트랜잭션 로그 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("title", "트랜잭션 로그 목록");
            return "user/transaction-log/list";
        }
    }

    /**
     * 트랜잭션 로그 상세 페이지
     */
    @GetMapping("/detail/{id}")
    public String transactionLogDetail(@PathVariable Long id, Model model) {
        log.info("[TransactionLogController] transactionLogDetail START - id: {}", id);

        try {
            // TODO: ID로 트랜잭션 로그 조회 메서드 추가 필요
            // TransactionLogEntity transactionLog =
            // transactionLogService.getTransactionLogById(id);

            model.addAttribute("title", "트랜잭션 로그 상세");
            // model.addAttribute("transactionLog", transactionLog);
            model.addAttribute("message", "트랜잭션 로그 상세 조회 기능은 개발 중입니다.");

            log.info("[TransactionLogController] transactionLogDetail END");
            return "user/transaction-log/detail";
        } catch (Exception e) {
            log.error("Error in transaction log detail page: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "트랜잭션 로그 상세 정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("title", "트랜잭션 로그 상세");
            return "user/transaction-log/detail";
        }
    }

    /**
     * 트랜잭션 통계 페이지
     */
    @GetMapping("/statistics")
    public String transactionStatistics(Model model) {
        log.info("[TransactionLogController] transactionStatistics START");

        try {
            // 통계 정보 조회
            long todayTransactions = transactionLogService.getTodayTransactionCount();
            long thisWeekTransactions = transactionLogService.getThisWeekTransactionCount();
            long failedTransactions = transactionLogService.getFailedTransactionCount();
            List<Map<String, Object>> systemStatistics = transactionLogService.getSystemStatistics();
            List<Map<String, Object>> hourlyStatistics = transactionLogService.getHourlyStatistics();

            model.addAttribute("title", "트랜잭션 통계");
            model.addAttribute("todayTransactions", todayTransactions);
            model.addAttribute("thisWeekTransactions", thisWeekTransactions);
            model.addAttribute("failedTransactions", failedTransactions);
            model.addAttribute("systemStatistics", systemStatistics);
            model.addAttribute("hourlyStatistics", hourlyStatistics);

            log.info("[TransactionLogController] transactionStatistics END");
            return "user/transaction-log/statistics";
        } catch (Exception e) {
            log.error("Error in transaction statistics page: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "트랜잭션 통계를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("title", "트랜잭션 통계");
            return "user/transaction-log/statistics";
        }
    }

    // ==================== REST API 엔드포인트 ====================

    /**
     * 트랜잭션 로그 통계 조회 (REST API)
     */
    @GetMapping(value = "/api/statistics", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTransactionStatistics() {
        log.info("[UserTransactionLogController] getTransactionStatistics START");

        try {
            Map<String, Object> response = new HashMap<>();

            // 기본 통계
            response.put("todayTransactions", transactionLogService.getTodayTransactionCount());
            response.put("thisWeekTransactions", transactionLogService.getThisWeekTransactionCount());
            response.put("failedTransactions", transactionLogService.getFailedTransactionCount());

            // 시스템별 통계
            response.put("systemStatistics", transactionLogService.getSystemStatistics());

            // 시간대별 통계
            response.put("hourlyStatistics", transactionLogService.getHourlyStatistics());

            response.put("success", true);
            response.put("message", "트랜잭션 통계 조회 성공");

            log.info("[UserTransactionLogController] getTransactionStatistics END");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting transaction statistics: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "트랜잭션 통계 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 트랜잭션 로그 목록 조회 (REST API)
     */
    @GetMapping(value = "/api/list", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTransactionLogs(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String systemName,
            @RequestParam(required = false) String methodName,
            @RequestParam(required = false) String errorCode,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("[UserTransactionLogController] getTransactionLogs START");

        try {
            Map<String, Object> response = new HashMap<>();

            // 검색 조건에 따른 로그 조회
            List<TransactionLogEntity> transactionLogs;
            long totalCount = 0;

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
            } else if (startDate != null && endDate != null) {
                transactionLogs = transactionLogService.getTransactionLogsByDateRange(startDate, endDate);
                totalCount = transactionLogs.size();
            } else {
                // 기본적으로 최근 100개 로그 조회
                transactionLogs = transactionLogService.getTransactionLogsByDateRange(
                        LocalDateTime.now().minusDays(7), LocalDateTime.now());
                totalCount = transactionLogs.size();
            }

            // 페이징 처리
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, transactionLogs.size());
            List<TransactionLogEntity> pagedLogs = transactionLogs.subList(startIndex, endIndex);

            int totalPages = (int) Math.ceil((double) totalCount / size);

            response.put("transactionLogs", pagedLogs);
            response.put("currentPage", page);
            response.put("totalPages", totalPages);
            response.put("totalCount", totalCount);
            response.put("pageSize", size);
            response.put("success", true);
            response.put("message", "트랜잭션 로그 목록 조회 성공");

            log.info("[UserTransactionLogController] getTransactionLogs END - count: {}", pagedLogs.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting transaction logs: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "트랜잭션 로그 목록 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 트랜잭션 로그 상세 조회 (REST API)
     */
    @GetMapping(value = "/api/detail/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTransactionLogDetail(@PathVariable Long id) {
        log.info("[UserTransactionLogController] getTransactionLogDetail START - id: {}", id);

        try {
            Map<String, Object> response = new HashMap<>();

            // TODO: ID로 트랜잭션 로그 조회 메서드 추가 필요
            // TransactionLogEntity transactionLog =
            // transactionLogService.getTransactionLogById(id);

            response.put("success", false);
            response.put("message", "트랜잭션 로그 상세 조회 기능은 개발 중입니다.");

            log.info("[UserTransactionLogController] getTransactionLogDetail END");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting transaction log detail: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "트랜잭션 로그 상세 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 사용자별 트랜잭션 로그 조회 (REST API)
     */
    @GetMapping(value = "/api/user/{userId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTransactionLogsByUser(@PathVariable String userId) {
        log.info("[UserTransactionLogController] getTransactionLogsByUser START - userId: {}", userId);

        try {
            Map<String, Object> response = new HashMap<>();

            List<TransactionLogEntity> logs = transactionLogService.getTransactionLogsByUserId(userId);

            response.put("transactionLogs", logs);
            response.put("userId", userId);
            response.put("count", logs.size());
            response.put("success", true);
            response.put("message", "사용자별 트랜잭션 로그 조회 성공");

            log.info("[UserTransactionLogController] getTransactionLogsByUser END - count: {}", logs.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting transaction logs by user: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "사용자별 트랜잭션 로그 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 시스템별 트랜잭션 로그 조회 (REST API)
     */
    @GetMapping(value = "/api/system/{systemName}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTransactionLogsBySystem(@PathVariable String systemName) {
        log.info("[UserTransactionLogController] getTransactionLogsBySystem START - systemName: {}", systemName);

        try {
            Map<String, Object> response = new HashMap<>();

            List<TransactionLogEntity> logs = transactionLogService.getTransactionLogsBySystemName(systemName);

            response.put("transactionLogs", logs);
            response.put("systemName", systemName);
            response.put("count", logs.size());
            response.put("success", true);
            response.put("message", "시스템별 트랜잭션 로그 조회 성공");

            log.info("[UserTransactionLogController] getTransactionLogsBySystem END - count: {}", logs.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting transaction logs by system: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "시스템별 트랜잭션 로그 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 날짜 범위별 트랜잭션 로그 조회 (REST API)
     */
    @GetMapping(value = "/api/date-range", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTransactionLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        log.info("[UserTransactionLogController] getTransactionLogsByDateRange START - startDate: {}, endDate: {}",
                startDate, endDate);

        try {
            Map<String, Object> response = new HashMap<>();

            List<TransactionLogEntity> logs = transactionLogService.getTransactionLogsByDateRange(startDate, endDate);

            response.put("transactionLogs", logs);
            response.put("startDate", startDate);
            response.put("endDate", endDate);
            response.put("count", logs.size());
            response.put("success", true);
            response.put("message", "날짜 범위별 트랜잭션 로그 조회 성공");

            log.info("[UserTransactionLogController] getTransactionLogsByDateRange END - count: {}", logs.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting transaction logs by date range: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "날짜 범위별 트랜잭션 로그 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * 에러 코드별 트랜잭션 로그 조회 (REST API)
     */
    @GetMapping(value = "/api/error/{errorCode}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTransactionLogsByErrorCode(@PathVariable String errorCode) {
        log.info("[UserTransactionLogController] getTransactionLogsByErrorCode START - errorCode: {}", errorCode);

        try {
            Map<String, Object> response = new HashMap<>();

            List<TransactionLogEntity> logs = transactionLogService.getTransactionLogsByErrorCode(errorCode);

            response.put("transactionLogs", logs);
            response.put("errorCode", errorCode);
            response.put("count", logs.size());
            response.put("success", true);
            response.put("message", "에러 코드별 트랜잭션 로그 조회 성공");

            log.info("[UserTransactionLogController] getTransactionLogsByErrorCode END - count: {}", logs.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting transaction logs by error code: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "에러 코드별 트랜잭션 로그 조회 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}