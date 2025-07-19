package com.skax.eatool.eplatonframework.business.service;

import com.skax.eatool.eplatonframework.transfer.EPlatonEvent;
import com.skax.eatool.eplatonframework.business.entity.Teller;
import java.util.List;

/**
 * Teller Service Interface for SKCC Oversea
 * 
 * Defines teller business operations
 * using Spring Boot and modern Java patterns.
 */
public interface TellerService {

    // EPlaton Event methods
    /**
     * Get teller info
     */
    EPlatonEvent getTellerInfo(EPlatonEvent event);

    /**
     * Create teller
     */
    EPlatonEvent createTeller(EPlatonEvent event);

    /**
     * Update teller
     */
    EPlatonEvent updateTeller(EPlatonEvent event);

    /**
     * Delete teller
     */
    EPlatonEvent deleteTeller(EPlatonEvent event);

    /**
     * Validate teller
     */
    EPlatonEvent validateTeller(EPlatonEvent event);

    // Additional methods for TellerBizAction
    /**
     * Login teller
     */
    EPlatonEvent loginTeller(EPlatonEvent event);

    /**
     * Logout teller
     */
    EPlatonEvent logoutTeller(EPlatonEvent event);

    /**
     * Update teller info
     */
    EPlatonEvent updateTellerInfo(EPlatonEvent event);

    /**
     * Get teller permissions
     */
    EPlatonEvent getTellerPermissions(EPlatonEvent event);

    /**
     * Validate teller session
     */
    EPlatonEvent validateTellerSession(EPlatonEvent event);

    /**
     * Get teller transactions
     */
    EPlatonEvent getTellerTransactions(EPlatonEvent event);

    // Controller expected methods
    /**
     * Find all tellers
     */
    List<Teller> findAll();

    /**
     * Find by ID
     */
    Teller findById(Long id);

    /**
     * Find by teller ID
     */
    Teller findByTellerId(String tellerId);

    /**
     * Find by branch code
     */
    List<Teller> findByBranchCode(String branchCode);

    /**
     * Find by teller type
     */
    List<Teller> findByTellerType(String tellerType);

    /**
     * Find by teller status
     */
    List<Teller> findByTellerStatus(String tellerStatus);

    /**
     * Save teller
     */
    Teller save(Teller teller);

    /**
     * Delete by ID
     */
    void deleteById(Long id);

    /**
     * Check if exists by ID
     */
    boolean existsById(Long id);
}
