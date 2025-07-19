package com.skax.eatool.cashCard.business.facade.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skax.eatool.cashCard.business.cashCardRule.ICashCardRuleSB;
import com.skax.eatool.reference.business.facade.IAccountManagement;
import com.skax.eatool.reference.business.facade.IReferenceManagementSB;

/**
 * Spring Service Locator for Cash Card Business
 * 
 * Provides centralized access to business services
 * using Spring dependency injection.
 */
@Component
public class SpringServiceLocator {

    private static final Logger logger = LoggerFactory.getLogger(SpringServiceLocator.class);

    @Autowired(required = false)
    private ICashCardRuleSB cashCardRuleService;

    @Autowired
    private IAccountManagement accountManagementService;

    @Autowired
    private IReferenceManagementSB referenceManagementService;

    public ICashCardRuleSB getCashCardRuleService() {
        logger.info("==================[SpringServiceLocator.getCashCardRuleService START]");
        try {
            if (cashCardRuleService == null) {
                logger.warn("CashCardRuleService is not available");
                return null;
            }
            logger.info("==================[SpringServiceLocator.getCashCardRuleService END]");
            return cashCardRuleService;
        } catch (Exception e) {
            logger.error("==================[SpringServiceLocator.getCashCardRuleService ERROR] - {}", e.getMessage(), e);
            throw e;
        }
    }

    public IAccountManagement getAccountManagementService() {
        logger.info("==================[SpringServiceLocator.getAccountManagementService START]");
        try {
            logger.info("==================[SpringServiceLocator.getAccountManagementService END]");
            return accountManagementService;
        } catch (Exception e) {
            logger.error("==================[SpringServiceLocator.getAccountManagementService ERROR] - {}", e.getMessage(), e);
            throw e;
        }
    }

    public IReferenceManagementSB getReferenceManagementService() {
        logger.info("==================[SpringServiceLocator.getReferenceManagementService START]");
        try {
            logger.info("==================[SpringServiceLocator.getReferenceManagementService END]");
            return referenceManagementService;
        } catch (Exception e) {
            logger.error("==================[SpringServiceLocator.getReferenceManagementService ERROR] - {}", e.getMessage(), e);
            throw e;
        }
    }
}

