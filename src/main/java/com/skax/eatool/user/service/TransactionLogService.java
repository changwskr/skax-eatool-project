package com.skax.eatool.user.service;

import com.skax.eatool.user.entity.TransactionLogEntity;
import com.skax.eatool.user.infrastructure.jpa.TransactionLogRepositoryJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/**
 * 트랜잭션 로그 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionLogService {

    private final TransactionLogRepositoryJpa transactionLogRepository;

    /**
     * 트랜잭션 로그 저장
     */
    @Transactional
    public TransactionLogEntity saveTransactionLog(TransactionLogEntity transactionLog) {
        log.info("[TransactionLogService] saveTransactionLog START - transactionId: {}",
                transactionLog.getTransactionId());

        TransactionLogEntity savedLog = transactionLogRepository.save(transactionLog);

        log.info("[TransactionLogService] saveTransactionLog END - id: {}", savedLog.getId());
        return savedLog;
    }

    /**
     * 트랜잭션 로그 생성
     */
    @Transactional
    public TransactionLogEntity createTransactionLog(String userId, String systemName, String methodName,
            String bankCode, String branchCode, String channelType,
            String inTime, String outTime, Long responseTime,
            String errorCode, String ipAddress) {

        String transactionId = generateTransactionId();
        String transactionNo = generateTransactionNo();
        String eventNo = generateEventNo();
        String businessDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String registerDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        TransactionLogEntity transactionLog = TransactionLogEntity.builder()
                .transactionId(transactionId)
                .transactionNo(transactionNo)
                .hostName("HOST001")
                .systemName(systemName)
                .methodName(methodName)
                .bankCode(bankCode)
                .branchCode(branchCode)
                .userId(userId)
                .channelType(channelType)
                .businessDate(businessDate)
                .registerDate(registerDate)
                .inTime(inTime)
                .outTime(outTime)
                .responseTime(responseTime)
                .errorCode(errorCode)
                .eventNo(eventNo)
                .ipAddress(ipAddress)
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();

        return saveTransactionLog(transactionLog);
    }

    /**
     * 사용자 ID로 트랜잭션 로그 조회
     */
    public List<TransactionLogEntity> getTransactionLogsByUserId(String userId) {
        log.info("[TransactionLogService] getTransactionLogsByUserId START - userId: {}", userId);

        List<TransactionLogEntity> result = transactionLogRepository.findByUserIdOrderByCreatedAtDesc(userId);

        log.info("[TransactionLogService] getTransactionLogsByUserId END - count: {}", result.size());
        return result;
    }

    /**
     * 시스템명으로 트랜잭션 로그 조회
     */
    public List<TransactionLogEntity> getTransactionLogsBySystemName(String systemName) {
        log.info("[TransactionLogService] getTransactionLogsBySystemName START - systemName: {}", systemName);

        List<TransactionLogEntity> result = transactionLogRepository.findBySystemNameOrderByCreatedAtDesc(systemName);

        log.info("[TransactionLogService] getTransactionLogsBySystemName END - count: {}", result.size());
        return result;
    }

    /**
     * 메서드명으로 트랜잭션 로그 조회
     */
    public List<TransactionLogEntity> getTransactionLogsByMethodName(String methodName) {
        log.info("[TransactionLogService] getTransactionLogsByMethodName START - methodName: {}", methodName);

        List<TransactionLogEntity> result = transactionLogRepository.findByMethodNameOrderByCreatedAtDesc(methodName);

        log.info("[TransactionLogService] getTransactionLogsByMethodName END - count: {}", result.size());
        return result;
    }

    /**
     * 에러 코드로 트랜잭션 로그 조회
     */
    public List<TransactionLogEntity> getTransactionLogsByErrorCode(String errorCode) {
        log.info("[TransactionLogService] getTransactionLogsByErrorCode START - errorCode: {}", errorCode);

        List<TransactionLogEntity> result = transactionLogRepository.findByErrorCodeOrderByCreatedAtDesc(errorCode);

        log.info("[TransactionLogService] getTransactionLogsByErrorCode END - count: {}", result.size());
        return result;
    }

    /**
     * 생성일시 범위로 트랜잭션 로그 조회
     */
    public List<TransactionLogEntity> getTransactionLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("[TransactionLogService] getTransactionLogsByDateRange START - startDate: {}, endDate: {}", startDate,
                endDate);

        List<TransactionLogEntity> result = transactionLogRepository
                .findByCreatedAtBetweenOrderByCreatedAtDesc(startDate, endDate);

        log.info("[TransactionLogService] getTransactionLogsByDateRange END - count: {}", result.size());
        return result;
    }

    /**
     * 오늘 트랜잭션 로그 수 조회
     */
    public long getTodayTransactionCount() {
        log.info("[TransactionLogService] getTodayTransactionCount START");

        long result = transactionLogRepository.countTodayTransactions();

        log.info("[TransactionLogService] getTodayTransactionCount END - count: {}", result);
        return result;
    }

    /**
     * 이번 주 트랜잭션 로그 수 조회
     */
    public long getThisWeekTransactionCount() {
        log.info("[TransactionLogService] getThisWeekTransactionCount START");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.toLocalDate().atStartOfDay().with(java.time.DayOfWeek.MONDAY);
        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1);

        long result = transactionLogRepository.countThisWeekTransactions(startOfWeek, endOfWeek);

        log.info("[TransactionLogService] getThisWeekTransactionCount END - count: {}", result);
        return result;
    }

    /**
     * 실패한 트랜잭션 로그 수 조회
     */
    public long getFailedTransactionCount() {
        log.info("[TransactionLogService] getFailedTransactionCount START");

        long result = transactionLogRepository.countByErrorCodeNot("I0000");

        log.info("[TransactionLogService] getFailedTransactionCount END - count: {}", result);
        return result;
    }

    /**
     * 시스템별 트랜잭션 통계 조회
     */
    public List<Map<String, Object>> getSystemStatistics() {
        log.info("[TransactionLogService] getSystemStatistics START");

        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        List<Object[]> results = transactionLogRepository.getSystemStatistics(startDate);

        List<Map<String, Object>> result = results.stream()
                .map(row -> Map.of(
                        "systemName", row[0],
                        "totalCount", row[1],
                        "successCount", row[2],
                        "failureCount", row[3]))
                .toList();

        log.info("[TransactionLogService] getSystemStatistics END - count: {}", result.size());
        return result;
    }

    /**
     * 시간대별 트랜잭션 통계 조회
     */
    public List<Map<String, Object>> getHourlyStatistics() {
        log.info("[TransactionLogService] getHourlyStatistics START");

        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        List<Object[]> results = transactionLogRepository.getHourlyStatistics(startDate);

        List<Map<String, Object>> result = results.stream()
                .map(row -> Map.of(
                        "hour", row[0],
                        "count", row[1]))
                .toList();

        log.info("[TransactionLogService] getHourlyStatistics END - count: {}", result.size());
        return result;
    }

    /**
     * 트랜잭션 ID 생성
     */
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * 트랜잭션 번호 생성
     */
    private String generateTransactionNo() {
        return "TXN" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) +
                String.format("%03d", (int) (Math.random() * 1000));
    }

    /**
     * 이벤트 번호 생성
     */
    private String generateEventNo() {
        return "EVT" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) +
                String.format("%03d", (int) (Math.random() * 1000));
    }

    /**
     * 페이징을 사용한 전체 트랜잭션 로그 조회
     */
    public List<TransactionLogEntity> getAllTransactionLogs(Pageable pageable) {
        log.info("[TransactionLogService] getAllTransactionLogs START - page: {}, size: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        List<TransactionLogEntity> result = transactionLogRepository.findAll(pageable).getContent();

        log.info("[TransactionLogService] getAllTransactionLogs END - count: {}", result.size());
        return result;
    }

    /**
     * 전체 트랜잭션 로그 수 조회
     */
    public long getTotalTransactionCount() {
        log.info("[TransactionLogService] getTotalTransactionCount START");

        long result = transactionLogRepository.count();

        log.info("[TransactionLogService] getTotalTransactionCount END - count: {}", result);
        return result;
    }

    /**
     * ID로 트랜잭션 로그 조회
     */
    public TransactionLogEntity getTransactionLogById(Long id) {
        log.info("[TransactionLogService] getTransactionLogById START - id: {}", id);

        TransactionLogEntity result = transactionLogRepository.findById(id).orElse(null);

        log.info("[TransactionLogService] getTransactionLogById END - found: {}", result != null);
        return result;
    }

    /**
     * 트랜잭션 로그 삭제 (소프트 삭제)
     */
    @Transactional
    public void deleteTransactionLog(Long id) {
        log.info("[TransactionLogService] deleteTransactionLog START - id: {}", id);

        TransactionLogEntity transactionLog = transactionLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("트랜잭션 로그를 찾을 수 없습니다: " + id));

        transactionLog.setIsDeleted(true);
        transactionLogRepository.save(transactionLog);

        log.info("[TransactionLogService] deleteTransactionLog END - success");
    }

    /**
     * 트랜잭션 로그 복구
     */
    @Transactional
    public void restoreTransactionLog(Long id) {
        log.info("[TransactionLogService] restoreTransactionLog START - id: {}", id);

        TransactionLogEntity transactionLog = transactionLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("트랜잭션 로그를 찾을 수 없습니다: " + id));

        transactionLog.setIsDeleted(false);
        transactionLogRepository.save(transactionLog);

        log.info("[TransactionLogService] restoreTransactionLog END - success");
    }
}