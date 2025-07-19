package com.skax.eatool.eplatonframework.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skax.eatool.eplatonframework.transfer.EPlatonEvent;
import com.skax.eatool.eplatonframework.transfer.EPlatonCommonDTO;
import com.skax.eatool.eplatonframework.business.entity.TransactionLog;
import com.skax.eatool.eplatonframework.business.repository.TransactionLogRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Transaction Log Service Implementation for SKCC Oversea
 * 
 * Provides transaction log business operations
 * using Spring Boot and modern Java patterns.
 */
@Service
public class TransactionLogServiceImpl implements TransactionLogService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionLogServiceImpl.class);

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent getTransactionLog(EPlatonEvent event) {
        try {
            logger.info("Getting transaction log");
            // Implementation here
            EPlatonCommonDTO response = new EPlatonCommonDTO();
            response.setStatus("I0000");
            response.setMessage("Transaction log retrieved successfully");
            event.setResponse(response);
            return event;
        } catch (Exception e) {
            logger.error("Error getting transaction log", e);
            setErrorInfo(event, "ETXL001", "Failed to get transaction log: " + e.getMessage());
            return event;
        }
    }

    @Override
    @Transactional
    public EPlatonEvent createTransactionLog(EPlatonEvent event) {
        try {
            logger.info("Creating transaction log");
            // Implementation here
            EPlatonCommonDTO response = new EPlatonCommonDTO();
            response.setStatus("I0000");
            response.setMessage("Transaction log created successfully");
            event.setResponse(response);
            return event;
        } catch (Exception e) {
            logger.error("Error creating transaction log", e);
            setErrorInfo(event, "ETXL002", "Failed to create transaction log: " + e.getMessage());
            return event;
        }
    }

    @Override
    @Transactional
    public EPlatonEvent updateTransactionLog(EPlatonEvent event) {
        try {
            logger.info("Updating transaction log");
            // Implementation here
            EPlatonCommonDTO response = new EPlatonCommonDTO();
            response.setStatus("I0000");
            response.setMessage("Transaction log updated successfully");
            event.setResponse(response);
            return event;
        } catch (Exception e) {
            logger.error("Error updating transaction log", e);
            setErrorInfo(event, "ETXL003", "Failed to update transaction log: " + e.getMessage());
            return event;
        }
    }

    @Override
    @Transactional
    public EPlatonEvent deleteTransactionLog(EPlatonEvent event) {
        try {
            logger.info("Deleting transaction log");
            // Implementation here
            EPlatonCommonDTO response = new EPlatonCommonDTO();
            response.setStatus("I0000");
            response.setMessage("Transaction log deleted successfully");
            event.setResponse(response);
            return event;
        } catch (Exception e) {
            logger.error("Error deleting transaction log", e);
            setErrorInfo(event, "ETXL004", "Failed to delete transaction log: " + e.getMessage());
            return event;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent searchTransactionLogs(EPlatonEvent event) {
        try {
            logger.info("Searching transaction logs");
            // Implementation here
            EPlatonCommonDTO response = new EPlatonCommonDTO();
            response.setStatus("I0000");
            response.setMessage("Transaction logs searched successfully");
            event.setResponse(response);
            return event;
        } catch (Exception e) {
            logger.error("Error searching transaction logs", e);
            setErrorInfo(event, "ETXL005", "Failed to search transaction logs: " + e.getMessage());
            return event;
        }
    }

    // Additional methods for controller
    @Override
    @Transactional(readOnly = true)
    public List<TransactionLog> getAllTransactionLogs() {
        logger.info("Getting all transaction logs");
        return transactionLogRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionLog getTransactionLogById(Long id) {
        logger.info("Getting transaction log by ID: {}", id);
        return transactionLogRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionLog getTransactionLogByTransactionId(String transactionId) {
        logger.info("Getting transaction log by transaction ID: {}", transactionId);
        return transactionLogRepository.findByTransactionId(transactionId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionLog> getTransactionLogsByUserId(String userId) {
        logger.info("Getting transaction logs by user ID: {}", userId);
        return transactionLogRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionLog> getTransactionLogsBySystemName(String systemName) {
        logger.info("Getting transaction logs by system name: {}", systemName);
        return transactionLogRepository.findBySystemName(systemName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionLog> getTransactionLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        logger.info("Getting transaction logs by date range: {} to {}", startDate, endDate);
        return transactionLogRepository.findByRegisterDateBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionLog> getTransactionLogsByBusinessDate(String businessDate) {
        logger.info("Getting transaction logs by business date: {}", businessDate);
        return transactionLogRepository.findByBusinessDate(businessDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionLog> getTransactionLogsByErrorCode(String errorCode) {
        logger.info("Getting transaction logs by error code: {}", errorCode);
        return transactionLogRepository.findByErrorCode(errorCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionLog> getTransactionLogsByChannelType(String channelType) {
        logger.info("Getting transaction logs by channel type: {}", channelType);
        return transactionLogRepository.findByChannelType(channelType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionLog> getTransactionLogsByBankAndBranch(String bankCode, String branchCode) {
        logger.info("Getting transaction logs by bank: {} and branch: {}", bankCode, branchCode);
        return transactionLogRepository.findByBankCodeAndBranchCode(bankCode, branchCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionLog> getSlowTransactions(Long threshold) {
        logger.info("Getting slow transactions with threshold: {}", threshold);
        return transactionLogRepository.findByResponseTimeGreaterThan(threshold);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTransactionCountBySystemAndDateRange(String systemName, LocalDateTime startDate,
            LocalDateTime endDate) {
        logger.info("Getting transaction count by system: {} and date range: {} to {}", systemName, startDate, endDate);
        return transactionLogRepository.countBySystemNameAndRegisterDateBetween(systemName, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionLog> getLatestTransactionsByUserId(String userId) {
        logger.info("Getting latest transactions by user ID: {}", userId);
        return transactionLogRepository.findTop10ByUserIdOrderByRegisterDateDesc(userId);
    }

    // REST CRUD methods for controller
    @Override
    @Transactional
    public TransactionLog createTransactionLog(TransactionLog transactionLog) {
        logger.info("Creating transaction log (REST method)");
        try {
            // Set creation timestamp if not already set
            if (transactionLog.getRegisterDate() == null) {
                transactionLog.setRegisterDate(LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")));
            }
            return transactionLogRepository.save(transactionLog);
        } catch (Exception e) {
            logger.error("Error creating transaction log: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create transaction log: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public TransactionLog updateTransactionLog(TransactionLog transactionLog) {
        logger.info("Updating transaction log (REST method) with ID: {}", transactionLog.getId());
        try {
            // Check if transaction log exists
            if (!transactionLogRepository.existsById(transactionLog.getId())) {
                logger.warn("Transaction log not found with ID: {}", transactionLog.getId());
                return null;
            }
            
            // Set update timestamp
            transactionLog.setUpdateDate(LocalDateTime.now());
            return transactionLogRepository.save(transactionLog);
        } catch (Exception e) {
            logger.error("Error updating transaction log: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update transaction log: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public boolean deleteTransactionLog(Long id) {
        logger.info("Deleting transaction log (REST method) with ID: {}", id);
        try {
            if (!transactionLogRepository.existsById(id)) {
                logger.warn("Transaction log not found with ID: {}", id);
                return false;
            }
            
            transactionLogRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting transaction log: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete transaction log: " + e.getMessage(), e);
        }
    }

    private void setErrorInfo(EPlatonEvent event, String errorCode, String errorMessage) {
        EPlatonCommonDTO response = new EPlatonCommonDTO();
        response.setStatus(errorCode);
        response.setMessage(errorMessage);
        event.setResponse(response);
        event.getTPSVCINFODTO().setErrorcode(errorCode);
        event.getTPSVCINFODTO().setError_message(errorMessage);
    }
}
