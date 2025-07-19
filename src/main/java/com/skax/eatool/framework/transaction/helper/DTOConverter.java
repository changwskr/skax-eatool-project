package com.skax.eatool.framework.transaction.helper;

import com.skax.eatool.eplatonframework.transfer.*;
import com.skax.eatool.framework.transfer.*;
import com.skax.eatool.framework.transaction.model.TransactionUpDownDDTO;
import com.skax.eatool.framework.transaction.model.TransactionLogDDTO;
import com.skax.eatool.foundation.utility.CommonUtil;

/**
 * =============================================================================
 * 프로그램 명:
 * =============================================================================
 *
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 * 2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 * 
 * @author : 장우성(WooSungJang)
 * @company: IMS SYSTEM
 * @email : changwskr@yahoo.co.kr
 * @version 1.0
 *          =============================================================================
 */
public class DTOConverter {

  public DTOConverter() {
  }

  public static TransactionLogDDTO getTransactionLogDDTO(EPlatonEvent event) {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    TPMSVCINFO tpmsvcinfo = null;

    commonDTO = (EPlatonCommonDTO) event.getCommon();
    tpsvcinfo = event.getTPSVCINFODTO();

    String transactionId = tpsvcinfo.getBp_sequence();
    String hostName = CommonUtil.GetHostName();
    String bankCode = commonDTO.getBankCode();
    String branchCode = commonDTO.getBranchCode();
    String userId = commonDTO.getUserID();
    String channelType = commonDTO.getChannelType();
    String businessDate = commonDTO.getBusinessDate();
    String eventNo = commonDTO.getEventNo();
    String action = tpsvcinfo.getReqName();
    String IPAddress = commonDTO.getIPAddress();

    TransactionLogDDTO transactionLogDDTO = new TransactionLogDDTO();
    int index = action.indexOf('-');
    transactionLogDDTO.setTransactionId(transactionId);
    transactionLogDDTO.setHostName(hostName);
    transactionLogDDTO.setSystemName(action.substring(0, index));
    transactionLogDDTO.setMethodName(action.substring(index + 1));
    transactionLogDDTO.setBankCode(bankCode);
    transactionLogDDTO.setBranchCode(branchCode);
    if (userId == null) {
      userId = "other";
    }
    transactionLogDDTO.setUserId(userId);
    transactionLogDDTO.setChannelType(channelType);
    if (businessDate == null) {
      businessDate = "yyyymmdd";
    }
    transactionLogDDTO.setBusinessDate(businessDate);
    transactionLogDDTO.setEventNo(eventNo);
    transactionLogDDTO.setIPAddress(IPAddress);

    return transactionLogDDTO;
  }

  public static TransactionUpDownDDTO getTransactionUpDownDDTO(EPlatonEvent event) {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfoDTO = null;
    TPMSVCINFO tpmsvcinfo = null;

    commonDTO = (EPlatonCommonDTO) event.getCommon();
    tpsvcinfoDTO = event.getTPSVCINFODTO();

    TransactionUpDownDDTO transactionUPDOWNDDTO = new TransactionUpDownDDTO();

    transactionUPDOWNDDTO.setBankCode(commonDTO.getBankCode());
    transactionUPDOWNDDTO.setBaseCurrency(commonDTO.getBaseCurrency());
    transactionUPDOWNDDTO.setBranchCode(commonDTO.getBranchCode());
    transactionUPDOWNDDTO.setBusinessDate(commonDTO.getBusinessDate());
    transactionUPDOWNDDTO.setChannelType(commonDTO.getChannelType());
    transactionUPDOWNDDTO.setEventNo(commonDTO.getEventNo());
    transactionUPDOWNDDTO.setFxRateCount(commonDTO.getFxRateCount());
    transactionUPDOWNDDTO.setGlPostBranchCode(commonDTO.getGlPostBranchCode());
    transactionUPDOWNDDTO.setIPAddress(commonDTO.getIPAddress());
    transactionUPDOWNDDTO.setMultiPL(commonDTO.getMultiPL());
    transactionUPDOWNDDTO.setSystemInTime(commonDTO.getSystemInTime());
    transactionUPDOWNDDTO.setSystemDate(commonDTO.getSystemDate());
    transactionUPDOWNDDTO.setReqName(commonDTO.getReqName());
    transactionUPDOWNDDTO.setRegionCode(commonDTO.getRegionCode());
    transactionUPDOWNDDTO.setTerminalType(commonDTO.getTerminalType());
    transactionUPDOWNDDTO.setTransactionNo(commonDTO.getTransactionNo());
    transactionUPDOWNDDTO.setUserID(commonDTO.getUserID());
    transactionUPDOWNDDTO.setUserLevel(commonDTO.getUserLevel());
    transactionUPDOWNDDTO.setXmlSeq(commonDTO.getXmlSeq());
    transactionUPDOWNDDTO.setTimeZone(commonDTO.getTimeZone());
    transactionUPDOWNDDTO.setTerminalID(commonDTO.getTerminalID());
    transactionUPDOWNDDTO.setSystemOutTime(commonDTO.getSystemOutTime());
    transactionUPDOWNDDTO.setNation(commonDTO.getNation());

    transactionUPDOWNDDTO.setAction_name(tpsvcinfoDTO.getAction_name());
    transactionUPDOWNDDTO.setCdto_name(tpsvcinfoDTO.getCdto_name());
    transactionUPDOWNDDTO.setError_message(tpsvcinfoDTO.getError_message());
    transactionUPDOWNDDTO.setErrorcode(tpsvcinfoDTO.getErrorcode());
    transactionUPDOWNDDTO.setHostseq(tpsvcinfoDTO.getHostseq());
    transactionUPDOWNDDTO.setOperation_method_name(tpsvcinfoDTO.getOperation_method());
    transactionUPDOWNDDTO.setOperation_name(tpsvcinfoDTO.getOperation_name());
    transactionUPDOWNDDTO.setOrgseq(tpsvcinfoDTO.getOrgseq());
    transactionUPDOWNDDTO.setRequest_cdto(tpsvcinfoDTO.getCdto_name());
    transactionUPDOWNDDTO.setSystem_name(tpsvcinfoDTO.getSystem_name());
    transactionUPDOWNDDTO.setSystem_date(tpsvcinfoDTO.getSystem_date());
    transactionUPDOWNDDTO.setTpfq(tpsvcinfoDTO.getTpfq());
    transactionUPDOWNDDTO.setTrclass(tpsvcinfoDTO.getTrclass());
    transactionUPDOWNDDTO.setTx_timer(tpsvcinfoDTO.getTx_timer());
    transactionUPDOWNDDTO.setBp_hostseq(tpsvcinfoDTO.getBp_sequence());
    transactionUPDOWNDDTO.setWeb_intime(tpsvcinfoDTO.getWeb_intime());
    transactionUPDOWNDDTO.setWeb_outtime(tpsvcinfoDTO.getWeb_outtime());
    transactionUPDOWNDDTO.setWeb_timeout(tpsvcinfoDTO.getWeb_timeout());

    IDTO reqcdto = event.getRequest();
    IDTO rescdto = event.getResponse();

    if (reqcdto != null) {
      transactionUPDOWNDDTO.setRequest_cdto(event.getRequest() != null ? event.getRequest().toString() : "null");
    }
    if (rescdto != null) {
      transactionUPDOWNDDTO.setResponse_cdto(event.getResponse() != null ? event.getResponse().toString() : "null");
    }

    return transactionUPDOWNDDTO;
  }

}
