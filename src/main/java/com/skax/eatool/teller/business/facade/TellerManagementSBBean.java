package com.skax.eatool.teller.business.facade;

import java.text.*;
import java.util.*;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skax.eatool.framework.exception.CosesAppException;
import com.skax.eatool.deposit.business.facade.*;
import com.skax.eatool.deposit.transfer.*;

import com.skax.eatool.eplatonframework.transfer.*;
import com.skax.eatool.foundation.logej.*;
import com.skax.eatool.eplatonframework.transfer.EPLcommonCDTO;

/**
 * Teller Management Service for SKCC Oversea
 * Spring Boot service replacing EJB session bean
 */
@Service
@Transactional
public class TellerManagementSBBean implements ITellerManagementSB {

  private static final Logger logger = LoggerFactory.getLogger(TellerManagementSBBean.class);

  /**
   * Call method 01 - Teller business logic
   */
  @Transactional(readOnly = false)
  public EPlatonEvent callmethod01(EPlatonEvent event) throws CosesAppException {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    EPLcommonCDTO rescdto = null;
    EPLcommonCDTO reqcdto = new EPLcommonCDTO();

    logger.info("==================[callmethod01 START]");

    try {
      commonDTO = (EPlatonCommonDTO) event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (EPLcommonCDTO) event.getRequest();

      logger.info("Setting up new account information for teller");
      reqcdto.setAccountNumber("0001100100000088");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("8888888888888888");

      EPlatonCommonDTO response = new EPlatonCommonDTO();
      response.setErrorCode("I0000");
      response.setErrorMessage("Teller info retrieved successfully");
      event.setResponse(response);
      logger.info("==================[callmethod01 END]");

      return event;

    } catch (Exception e) {
      logger.error("Error in callmethod01", e);
      throw new CosesAppException("Teller method 01 failed: " + e.getMessage(), e);
    }
  }

  /**
   * Call method 02 - Teller business logic
   */
  @Transactional(readOnly = false)
  public EPlatonEvent callmethod02(EPlatonEvent event) throws CosesAppException {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    EPLcommonCDTO rescdto = null;
    EPLcommonCDTO reqcdto = new EPLcommonCDTO();

    logger.info("==================[callmethod02 START]");

    try {
      commonDTO = (EPlatonCommonDTO) event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (EPLcommonCDTO) event.getRequest();

      logger.info("Setting up new account information for teller");
      reqcdto.setAccountNumber("0001100100000088");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("8888888888888888");

      event.setResponse(reqcdto);
      logger.info("==================[callmethod02 END]");

      return event;

    } catch (Exception e) {
      logger.error("Error in callmethod02", e);
      throw new CosesAppException("Teller method 02 failed: " + e.getMessage(), e);
    }
  }
}
