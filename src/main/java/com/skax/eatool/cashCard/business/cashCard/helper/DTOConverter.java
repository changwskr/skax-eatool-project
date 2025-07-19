package com.skax.eatool.cashCard.business.cashCard.helper;

import com.skax.eatool.foundation.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skax.eatool.cashCard.business.cashCard.model.*;
import com.skax.eatool.cashCard.business.cashCard.entity.*;

public class DTOConverter {

    private static final Logger logger = LoggerFactory.getLogger(DTOConverter.class);

    public static CashCardDDTO getCashCardDDTO(CashCard cashCard,
            CashCardDDTO cashCardDDTO) {
        logger.debug("==================[DTOConverter.getCashCardDDTO START] - cardNumber: {}",
                cashCard.getCardNumber());
        try {
            cashCardDDTO.setBankCode(cashCard.getBankCode());
            cashCardDDTO.setBankType(cashCard.getBankType());
            cashCardDDTO.setBranchCode(cashCard.getBranchCode());
            // cashCardDDTO.setCardHolderName(cashCard.getCardHolderName());
            cashCardDDTO.setCardNumber(cashCard.getCardNumber());
            cashCardDDTO.setCIFNo(cashCard.getCifNo());
            cashCardDDTO.setCIFName(cashCard.getCifName());
            cashCardDDTO.setDailyAccumAmount(cashCard.getDailyAccumAmount());
            cashCardDDTO.setDailyAccumResetDate(cashCard.getDailyAccumResetDate());
            cashCardDDTO.setDailyAccumResetTime(cashCard.getDailyAccumResetTime());
            cashCardDDTO.setDailyLimitAmount(cashCard.getDailyLimitAmount());
            cashCardDDTO.setDailyLimitCcy(cashCard.getDailyLimitCcy());
            cashCardDDTO.setDailyTrfAccumAmount(cashCard.getDailyTrfAccumAmount());
            cashCardDDTO.setDailyTrfLimitAmount(cashCard.getDailyTrfLimitAmount());
            cashCardDDTO.setDailyTrfLimitCcy(cashCard.getDailyTrfLimitCcy());
            cashCardDDTO.setEffectiveDate(cashCard.getEffectiveDate());
            cashCardDDTO.setExpiryDate(cashCard.getExpiryDate());
            cashCardDDTO.setFeeAmount(cashCard.getFeeAmount());
            cashCardDDTO.setFeeCcy(cashCard.getFeeCcy());
            cashCardDDTO.setFeeWaive(cashCard.getFeeWaive());
            cashCardDDTO.setIncidentCode(cashCard.getIncidentCode());
            cashCardDDTO.setInvalidAttemptCnt(cashCard.getInvalidAttemptCnt());
            cashCardDDTO.setLastUpdateDate(cashCard.getLastUpdateDate());
            cashCardDDTO.setLastUpdateTime(cashCard.getLastUpdateTime());
            cashCardDDTO.setLastUpdateUserID(cashCard.getLastUpdateUserId());
            cashCardDDTO.setMISSendDate(cashCard.getMisSendDate());
            cashCardDDTO.setPasswordNo(cashCard.getPasswordNo());
            cashCardDDTO.setPrimaryAccountNo(cashCard.getPrimaryAccountNo());
            cashCardDDTO.setRegisterBy(cashCard.getRegisterBy());
            cashCardDDTO.setRegisterDate(cashCard.getRegisterDate());
            cashCardDDTO.setRegisterTime(cashCard.getRegisterTime());
            cashCardDDTO.setRemark(cashCard.getRemark());
            cashCardDDTO.setSecondaryAccountNo(cashCard.getSecondaryAccountNo());
            cashCardDDTO.setSequenceNo(cashCard.getId().intValue()); // id를 int로 변환하여 sequenceNo에 설정
            cashCardDDTO.setStatus(cashCard.getStatus());
            cashCardDDTO.setTernaryAccountNo(cashCard.getTernaryAccountNo());
            cashCardDDTO.setType(cashCard.getType());
            cashCardDDTO.setIssueDate(cashCard.getIssueDate());

            logger.debug("==================[DTOConverter.getCashCardDDTO END] - cardNumber: {}",
                    cashCard.getCardNumber());
            return cashCardDDTO;
        } catch (Exception e) {
            logger.error("==================[DTOConverter.getCashCardDDTO ERROR] - cardNumber: {}, 오류: {}",
                    cashCard.getCardNumber(), e.getMessage(), e);
            throw e;
        }
    }

    public static void setCashCardDDTO(CashCardDDTO cashCardDDTO, CashCard cashCard) {
        logger.debug("==================[DTOConverter.setCashCardDDTO START] - cardNumber: {}",
                cashCardDDTO.getCardNumber());
        try {
            if (Log.SDBLogger.isDebugEnabled()) {
                Log.SDBLogger.debug("CashCardDDTO : " + cashCardDDTO.toString());
            }

            // cashCard.setBranchCode(cashCardDDTO.getBranchCode());
            // cashCard.setCardHolderName(cashCardDDTO.getCardHolderName());
            // cashCard.setCardNumber(cashCardDDTO.getCardNumber());
            cashCard.setCifNo(cashCardDDTO.getCIFNo());
            cashCard.setCifName(cashCardDDTO.getCIFName());
            cashCard.setDailyAccumAmount(cashCardDDTO.getDailyAccumAmount());
            cashCard.setDailyAccumResetDate(cashCardDDTO.getDailyAccumResetDate());
            cashCard.setDailyAccumResetTime(cashCardDDTO.getDailyAccumResetTime());
            cashCard.setDailyLimitAmount(cashCardDDTO.getDailyLimitAmount());
            cashCard.setDailyLimitCcy(cashCardDDTO.getDailyLimitCcy());
            cashCard.setDailyTrfAccumAmount(cashCardDDTO.getDailyTrfAccumAmount());
            cashCard.setDailyTrfLimitAmount(cashCardDDTO.getDailyTrfLimitAmount());
            cashCard.setDailyTrfLimitCcy(cashCardDDTO.getDailyTrfLimitCcy());
            cashCard.setEffectiveDate(cashCardDDTO.getEffectiveDate());
            cashCard.setExpiryDate(cashCardDDTO.getExpiryDate());
            cashCard.setFeeAmount(cashCardDDTO.getFeeAmount());
            cashCard.setFeeCcy(cashCardDDTO.getFeeCcy());
            cashCard.setFeeWaive(cashCardDDTO.getFeeWaive());
            cashCard.setIncidentCode(cashCardDDTO.getIncidentCode());
            cashCard.setInvalidAttemptCnt(cashCardDDTO.getInvalidAttemptCnt());
            cashCard.setLastUpdateDate(cashCardDDTO.getLastUpdateDate());
            cashCard.setLastUpdateTime(cashCardDDTO.getLastUpdateTime());
            cashCard.setLastUpdateUserId(cashCardDDTO.getLastUpdateUserID());
            cashCard.setMisSendDate(cashCardDDTO.getMISSendDate());
            cashCard.setPasswordNo(cashCardDDTO.getPasswordNo());
            cashCard.setRegisterBy(cashCardDDTO.getRegisterBy());
            cashCard.setRegisterDate(cashCardDDTO.getRegisterDate());
            cashCard.setRegisterTime(cashCardDDTO.getRegisterTime());
            cashCard.setRemark(cashCardDDTO.getRemark());
            cashCard.setSecondaryAccountNo(cashCardDDTO.getSecondaryAccountNo());
            cashCard.setTernaryAccountNo(cashCardDDTO.getTernaryAccountNo());
            cashCard.setIssueDate(cashCardDDTO.getIssueDate());
            logger.debug("==================[DTOConverter.setCashCardDDTO END] - cardNumber: {}",
                    cashCardDDTO.getCardNumber());
        } catch (Exception e) {
            logger.error("==================[DTOConverter.setCashCardDDTO ERROR] - cardNumber: {}, 오류: {}",
                    cashCardDDTO.getCardNumber(), e.getMessage(), e);
            throw e;
        }
    }

    public static HotCardDDTO getHotCardCDTO(HotCard hotCard,
            HotCardDDTO hotCardDDTO) {
        logger.debug("==================[DTOConverter.getHotCardCDTO START] - cardNumber: {}", hotCard.getCardNumber());
        try {
            hotCardDDTO.setCardNumber(hotCard.getCardNumber());
            hotCardDDTO.setPrimaryAccountNo(hotCard.getPrimaryAccountNo());
            hotCardDDTO.setCIFNo(hotCard.getCifNo());
            hotCardDDTO.setCIFName(hotCard.getCifName());
            hotCardDDTO.setStatus(hotCard.getStatus());
            hotCardDDTO.setIncidentCode(hotCard.getIncidentCode());
            hotCardDDTO.setRegisterDate(hotCard.getRegisterDate());
            hotCardDDTO.setRegisterTime(hotCard.getRegisterTime());
            hotCardDDTO.setRegisterBy(hotCard.getRegisterBy());
            hotCardDDTO.setReleasedDate(hotCard.getReleasedDate());
            hotCardDDTO.setReleasedTime(hotCard.getReleasedTime());
            hotCardDDTO.setReleasedBy(hotCard.getReleasedBy());
            hotCardDDTO.setRemark(hotCard.getRemark());
            hotCardDDTO.setSequenceNo(hotCard.getSequenceNo());

            logger.debug("==================[DTOConverter.getHotCardCDTO END] - cardNumber: {}",
                    hotCard.getCardNumber());
            return hotCardDDTO;
        } catch (Exception e) {
            logger.error("==================[DTOConverter.getHotCardCDTO ERROR] - cardNumber: {}, 오류: {}",
                    hotCard.getCardNumber(), e.getMessage(), e);
            throw e;
        }
    }

    public static void setHotCardDDTO(HotCardDDTO hotCardDDTO, HotCard hotCard) {
        logger.debug("==================[DTOConverter.setHotCardDDTO START] - cardNumber: {}",
                hotCardDDTO.getCardNumber());
        try {
            if (Log.SDBLogger.isDebugEnabled()) {
                Log.SDBLogger.debug("HotCardDDTO : " + hotCardDDTO.toString());
            }

            hotCard.setPrimaryAccountNo(hotCardDDTO.getPrimaryAccountNo());
            hotCard.setCifNo(hotCardDDTO.getCIFNo());
            hotCard.setCifName(hotCardDDTO.getCIFName());
            hotCard.setStatus(hotCardDDTO.getStatus());
            hotCard.setIncidentCode(hotCardDDTO.getIncidentCode());
            hotCard.setRegisterDate(hotCardDDTO.getRegisterDate());
            hotCard.setRegisterTime(hotCardDDTO.getRegisterTime());
            hotCard.setRegisterBy(hotCardDDTO.getRegisterBy());
            hotCard.setReleasedDate(hotCardDDTO.getReleasedDate());
            hotCard.setReleasedTime(hotCardDDTO.getReleasedTime());
            hotCard.setReleasedBy(hotCardDDTO.getReleasedBy());
            hotCard.setRemark(hotCardDDTO.getRemark());
            logger.debug("==================[DTOConverter.setHotCardDDTO END] - cardNumber: {}",
                    hotCardDDTO.getCardNumber());
        } catch (Exception e) {
            logger.error("==================[DTOConverter.setHotCardDDTO ERROR] - cardNumber: {}, 오류: {}",
                    hotCardDDTO.getCardNumber(), e.getMessage(), e);
            throw e;
        }
    }
}
