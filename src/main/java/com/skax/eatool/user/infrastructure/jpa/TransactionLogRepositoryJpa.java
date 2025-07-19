package com.skax.eatool.user.infrastructure.jpa;

import com.skax.eatool.user.entity.TransactionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 트랜잭션 로그 리포지토리
 */
@Repository
public interface TransactionLogRepositoryJpa extends JpaRepository<TransactionLogEntity, Long> {

    /**
     * 사용자 ID로 트랜잭션 로그 조회
     */
    List<TransactionLogEntity> findByUserIdOrderByCreatedAtDesc(String userId);

    /**
     * 시스템명으로 트랜잭션 로그 조회
     */
    List<TransactionLogEntity> findBySystemNameOrderByCreatedAtDesc(String systemName);

    /**
     * 메서드명으로 트랜잭션 로그 조회
     */
    List<TransactionLogEntity> findByMethodNameOrderByCreatedAtDesc(String methodName);

    /**
     * 에러 코드로 트랜잭션 로그 조회
     */
    List<TransactionLogEntity> findByErrorCodeOrderByCreatedAtDesc(String errorCode);

    /**
     * 생성일시 범위로 트랜잭션 로그 조회
     */
    List<TransactionLogEntity> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * 사용자 ID와 생성일시 범위로 트랜잭션 로그 조회
     */
    List<TransactionLogEntity> findByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(String userId,
            LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 시스템명과 생성일시 범위로 트랜잭션 로그 조회
     */
    List<TransactionLogEntity> findBySystemNameAndCreatedAtBetweenOrderByCreatedAtDesc(String systemName,
            LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 오늘 트랜잭션 로그 수 조회
     */
    @Query("SELECT COUNT(t) FROM TransactionLogEntity t WHERE CAST(t.createdAt AS date) = CURRENT_DATE")
    long countTodayTransactions();

    /**
     * 이번 주 트랜잭션 로그 수 조회
     */
    @Query("SELECT COUNT(t) FROM TransactionLogEntity t WHERE t.createdAt >= :startOfWeek AND t.createdAt < :endOfWeek")
    long countThisWeekTransactions(@Param("startOfWeek") LocalDateTime startOfWeek,
            @Param("endOfWeek") LocalDateTime endOfWeek);

    /**
     * 실패한 트랜잭션 로그 수 조회
     */
    long countByErrorCodeNot(String errorCode);

    /**
     * 시스템별 트랜잭션 통계 조회
     */
    @Query("SELECT t.systemName, COUNT(t), " +
            "SUM(CASE WHEN t.errorCode = 'I0000' THEN 1 ELSE 0 END) as successCount, " +
            "SUM(CASE WHEN t.errorCode != 'I0000' THEN 1 ELSE 0 END) as failureCount " +
            "FROM TransactionLogEntity t " +
            "WHERE t.createdAt >= :startDate " +
            "GROUP BY t.systemName " +
            "ORDER BY COUNT(t) DESC")
    List<Object[]> getSystemStatistics(@Param("startDate") LocalDateTime startDate);

    /**
     * 시간대별 트랜잭션 통계 조회
     */
    @Query("SELECT HOUR(t.createdAt), COUNT(t) " +
            "FROM TransactionLogEntity t " +
            "WHERE t.createdAt >= :startDate " +
            "GROUP BY HOUR(t.createdAt) " +
            "ORDER BY HOUR(t.createdAt)")
    List<Object[]> getHourlyStatistics(@Param("startDate") LocalDateTime startDate);
}