package com.skax.eatool.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 트랜잭션 로그 엔티티
 */
@Entity
@Table(name = "transaction_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TransactionLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "transaction_no", nullable = false)
    private String transactionNo;

    @Column(name = "host_name", nullable = false)
    private String hostName;

    @Column(name = "system_name", nullable = false)
    private String systemName;

    @Column(name = "method_name", nullable = false)
    private String methodName;

    @Column(name = "bank_code", nullable = false)
    private String bankCode;

    @Column(name = "branch_code", nullable = false)
    private String branchCode;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "channel_type", nullable = false)
    private String channelType;

    @Column(name = "business_date", nullable = false)
    private String businessDate;

    @Column(name = "register_date", nullable = false)
    private String registerDate;

    @Column(name = "in_time", nullable = false)
    private String inTime;

    @Column(name = "out_time", nullable = false)
    private String outTime;

    @Column(name = "response_time", nullable = false)
    private Long responseTime;

    @Column(name = "error_code", nullable = false)
    private String errorCode;

    @Column(name = "event_no", nullable = false)
    private String eventNo;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}