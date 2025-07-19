package com.skax.eatool.cashCard.business.cashCard.model;

import java.math.BigDecimal;

import com.skax.eatool.framework.transfer.DTO;
import com.skax.eatool.framework.constants.Constants;

public class CashCardDDTO extends DTO {
    private String bankType = Constants.BLANK;
    private String bankCode = Constants.BLANK;
    private String primaryAccountNo = Constants.BLANK;
    private String type = Constants.BLANK;
    private int sequenceNo = 0;
    private String cardNumber = Constants.BLANK;
    private String branchCode = Constants.BLANK;
    private String CIFNo = Constants.BLANK;
    private String CIFName = Constants.BLANK;
    // private String cardHolderName = Constants.BLANK;
    private String passwordNo = Constants.BLANK;
    private int invalidAttemptCnt = 0;
    private String secondaryAccountNo = Constants.BLANK;
    private String ternaryAccountNo = Constants.BLANK;
    private String dailyLimitCcy = Constants.BLANK;
    private BigDecimal dailyLimitAmount = Constants.ZERO;
    private BigDecimal dailyAccumAmount = Constants.ZERO;
    private String dailyTrfLimitCcy = Constants.BLANK;
    private BigDecimal dailyTrfLimitAmount = Constants.ZERO;
    private BigDecimal dailyTrfAccumAmount = Constants.ZERO;
    private String dailyAccumResetDate = Constants.BLANK;
    private String dailyAccumResetTime = Constants.BLANK;
    private String effectiveDate = Constants.BLANK;
    private String expiryDate = Constants.BLANK;
    private String status = Constants.BLANK;
    private String incidentCode = Constants.BLANK;
    private String feeWaive = Constants.BLANK;
    private String feeCcy = Constants.BLANK;
    private BigDecimal feeAmount = Constants.ZERO;
    private String registerDate = Constants.BLANK;
    private String registerTime = Constants.BLANK;
    private String registerBy = Constants.BLANK;
    private String remark = Constants.BLANK;
    private String lastUpdateDate = Constants.BLANK;
    private String lastUpdateTime = Constants.BLANK;
    private String lastUpdateUserID = Constants.BLANK;
    private String MISSendDate = Constants.BLANK;
    private String issueDate = Constants.BLANK;
    private String amendReason = Constants.BLANK;

    public CashCardDDTO() {
    }

    // Getter Method
    public String getBankCode() {
        return bankCode;
    }

    public String getBankType() {
        return bankType;
    }

    public String getBranchCode() {
        return branchCode;
    }

    /*
     * public String getCardHolderName()
     * {
     * return cardHolderName;
     * }
     */
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCIFNo() {
        return CIFNo;
    }

    public BigDecimal getDailyAccumAmount() {
        return dailyAccumAmount;
    }

    public String getDailyAccumResetDate() {
        return dailyAccumResetDate;
    }

    public String getDailyAccumResetTime() {
        return dailyAccumResetTime;
    }

    public BigDecimal getDailyLimitAmount() {
        return dailyLimitAmount;
    }

    public String getDailyLimitCcy() {
        return dailyLimitCcy;
    }

    public BigDecimal getDailyTrfAccumAmount() {
        return dailyTrfAccumAmount;
    }

    public BigDecimal getDailyTrfLimitAmount() {
        return dailyTrfLimitAmount;
    }

    public String getDailyTrfLimitCcy() {
        return dailyTrfLimitCcy;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public String getFeeCcy() {
        return feeCcy;
    }

    public String getFeeWaive() {
        return feeWaive;
    }

    public String getIncidentCode() {
        return incidentCode;
    }

    public int getInvalidAttemptCnt() {
        return invalidAttemptCnt;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public String getLastUpdateUserID() {
        return lastUpdateUserID;
    }

    public String getMISSendDate() {
        return MISSendDate;
    }

    public String getPasswordNo() {
        return passwordNo;
    }

    public String getPrimaryAccountNo() {
        return primaryAccountNo;
    }

    public String getRegisterBy() {
        return registerBy;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public String getRemark() {
        return remark;
    }

    public String getSecondaryAccountNo() {
        return secondaryAccountNo;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public String getStatus() {
        return status;
    }

    public String getTernaryAccountNo() {
        return ternaryAccountNo;
    }

    public String getType() {
        return type;
    }

    public String getCIFName() {
        return CIFName;
    }

    public String getAmendReason() {
        return amendReason;
    }

    public String getIssueDate() {
        return issueDate;
    }

    // Setter Method
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /*
     * public void setCardHolderName(String cardHolderName)
     * {
     * this.cardHolderName = cardHolderName;
     * }
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCIFNo(String CIFNo) {
        this.CIFNo = CIFNo;
    }

    public void setDailyAccumAmount(BigDecimal dailyAccumAmount) {
        this.dailyAccumAmount = dailyAccumAmount;
    }

    public void setDailyAccumResetDate(String dailyAccumResetDate) {
        this.dailyAccumResetDate = dailyAccumResetDate;
    }

    public void setDailyAccumResetTime(String dailyAccumResetTime) {
        this.dailyAccumResetTime = dailyAccumResetTime;
    }

    public void setDailyLimitAmount(BigDecimal dailyLimitAmount) {
        this.dailyLimitAmount = dailyLimitAmount;
    }

    public void setDailyLimitCcy(String dailyLimitCcy) {
        this.dailyLimitCcy = dailyLimitCcy;
    }

    public void setDailyTrfAccumAmount(BigDecimal dailyTrfAccumAmount) {
        this.dailyTrfAccumAmount = dailyTrfAccumAmount;
    }

    public void setDailyTrfLimitAmount(BigDecimal dailyTrfLimitAmount) {
        this.dailyTrfLimitAmount = dailyTrfLimitAmount;
    }

    public void setDailyTrfLimitCcy(String dailyTrfLimitCcy) {
        this.dailyTrfLimitCcy = dailyTrfLimitCcy;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public void setFeeCcy(String feeCcy) {
        this.feeCcy = feeCcy;
    }

    public void setFeeWaive(String feeWaive) {
        this.feeWaive = feeWaive;
    }

    public void setIncidentCode(String incidentCode) {
        this.incidentCode = incidentCode;
    }

    public void setInvalidAttemptCnt(int invalidAttemptCnt) {
        this.invalidAttemptCnt = invalidAttemptCnt;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setLastUpdateUserID(String lastUpdateUserID) {
        this.lastUpdateUserID = lastUpdateUserID;
    }

    public void setMISSendDate(String MISSendDate) {
        this.MISSendDate = MISSendDate;
    }

    public void setPasswordNo(String passwordNo) {
        this.passwordNo = passwordNo;
    }

    public void setPrimaryAccountNo(String primaryAccountNo) {
        this.primaryAccountNo = primaryAccountNo;
    }

    public void setRegisterBy(String registerBy) {
        this.registerBy = registerBy;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setSecondaryAccountNo(String secondaryAccountNo) {
        this.secondaryAccountNo = secondaryAccountNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTernaryAccountNo(String ternaryAccountNo) {
        this.ternaryAccountNo = ternaryAccountNo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCIFName(String CIFName) {
        this.CIFName = CIFName;
    }

    public void setAmendReason(String amendReason) {
        this.amendReason = amendReason;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bankType == null) ? 0 : bankType.hashCode());
        result = prime * result + ((bankCode == null) ? 0 : bankCode.hashCode());
        result = prime * result + ((primaryAccountNo == null) ? 0 : primaryAccountNo.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + sequenceNo;
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + ((branchCode == null) ? 0 : branchCode.hashCode());
        result = prime * result + ((CIFNo == null) ? 0 : CIFNo.hashCode());
        result = prime * result + ((CIFName == null) ? 0 : CIFName.hashCode());
        result = prime * result + ((passwordNo == null) ? 0 : passwordNo.hashCode());
        result = prime * result + invalidAttemptCnt;
        result = prime * result + ((secondaryAccountNo == null) ? 0 : secondaryAccountNo.hashCode());
        result = prime * result + ((ternaryAccountNo == null) ? 0 : ternaryAccountNo.hashCode());
        result = prime * result + ((dailyLimitCcy == null) ? 0 : dailyLimitCcy.hashCode());
        result = prime * result + ((dailyLimitAmount == null) ? 0 : dailyLimitAmount.hashCode());
        result = prime * result + ((dailyAccumAmount == null) ? 0 : dailyAccumAmount.hashCode());
        result = prime * result + ((dailyTrfLimitCcy == null) ? 0 : dailyTrfLimitCcy.hashCode());
        result = prime * result + ((dailyTrfLimitAmount == null) ? 0 : dailyTrfLimitAmount.hashCode());
        result = prime * result + ((dailyTrfAccumAmount == null) ? 0 : dailyTrfAccumAmount.hashCode());
        result = prime * result + ((dailyAccumResetDate == null) ? 0 : dailyAccumResetDate.hashCode());
        result = prime * result + ((dailyAccumResetTime == null) ? 0 : dailyAccumResetTime.hashCode());
        result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
        result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((incidentCode == null) ? 0 : incidentCode.hashCode());
        result = prime * result + ((feeWaive == null) ? 0 : feeWaive.hashCode());
        result = prime * result + ((feeCcy == null) ? 0 : feeCcy.hashCode());
        result = prime * result + ((feeAmount == null) ? 0 : feeAmount.hashCode());
        result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
        result = prime * result + ((registerTime == null) ? 0 : registerTime.hashCode());
        result = prime * result + ((registerBy == null) ? 0 : registerBy.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
        result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
        result = prime * result + ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
        result = prime * result + ((lastUpdateUserID == null) ? 0 : lastUpdateUserID.hashCode());
        result = prime * result + ((MISSendDate == null) ? 0 : MISSendDate.hashCode());
        result = prime * result + ((issueDate == null) ? 0 : issueDate.hashCode());
        result = prime * result + ((amendReason == null) ? 0 : amendReason.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CashCardDDTO other = (CashCardDDTO) obj;
        if (bankType == null) {
            if (other.bankType != null)
                return false;
        } else if (!bankType.equals(other.bankType))
            return false;
        if (bankCode == null) {
            if (other.bankCode != null)
                return false;
        } else if (!bankCode.equals(other.bankCode))
            return false;
        if (primaryAccountNo == null) {
            if (other.primaryAccountNo != null)
                return false;
        } else if (!primaryAccountNo.equals(other.primaryAccountNo))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (sequenceNo != other.sequenceNo)
            return false;
        if (cardNumber == null) {
            if (other.cardNumber != null)
                return false;
        } else if (!cardNumber.equals(other.cardNumber))
            return false;
        if (branchCode == null) {
            if (other.branchCode != null)
                return false;
        } else if (!branchCode.equals(other.branchCode))
            return false;
        if (CIFNo == null) {
            if (other.CIFNo != null)
                return false;
        } else if (!CIFNo.equals(other.CIFNo))
            return false;
        if (CIFName == null) {
            if (other.CIFName != null)
                return false;
        } else if (!CIFName.equals(other.CIFName))
            return false;
        if (passwordNo == null) {
            if (other.passwordNo != null)
                return false;
        } else if (!passwordNo.equals(other.passwordNo))
            return false;
        if (invalidAttemptCnt != other.invalidAttemptCnt)
            return false;
        if (secondaryAccountNo == null) {
            if (other.secondaryAccountNo != null)
                return false;
        } else if (!secondaryAccountNo.equals(other.secondaryAccountNo))
            return false;
        if (ternaryAccountNo == null) {
            if (other.ternaryAccountNo != null)
                return false;
        } else if (!ternaryAccountNo.equals(other.ternaryAccountNo))
            return false;
        if (dailyLimitCcy == null) {
            if (other.dailyLimitCcy != null)
                return false;
        } else if (!dailyLimitCcy.equals(other.dailyLimitCcy))
            return false;
        if (dailyLimitAmount == null) {
            if (other.dailyLimitAmount != null)
                return false;
        } else if (!dailyLimitAmount.equals(other.dailyLimitAmount))
            return false;
        if (dailyAccumAmount == null) {
            if (other.dailyAccumAmount != null)
                return false;
        } else if (!dailyAccumAmount.equals(other.dailyAccumAmount))
            return false;
        if (dailyTrfLimitCcy == null) {
            if (other.dailyTrfLimitCcy != null)
                return false;
        } else if (!dailyTrfLimitCcy.equals(other.dailyTrfLimitCcy))
            return false;
        if (dailyTrfLimitAmount == null) {
            if (other.dailyTrfLimitAmount != null)
                return false;
        } else if (!dailyTrfLimitAmount.equals(other.dailyTrfLimitAmount))
            return false;
        if (dailyTrfAccumAmount == null) {
            if (other.dailyTrfAccumAmount != null)
                return false;
        } else if (!dailyTrfAccumAmount.equals(other.dailyTrfAccumAmount))
            return false;
        if (dailyAccumResetDate == null) {
            if (other.dailyAccumResetDate != null)
                return false;
        } else if (!dailyAccumResetDate.equals(other.dailyAccumResetDate))
            return false;
        if (dailyAccumResetTime == null) {
            if (other.dailyAccumResetTime != null)
                return false;
        } else if (!dailyAccumResetTime.equals(other.dailyAccumResetTime))
            return false;
        if (effectiveDate == null) {
            if (other.effectiveDate != null)
                return false;
        } else if (!effectiveDate.equals(other.effectiveDate))
            return false;
        if (expiryDate == null) {
            if (other.expiryDate != null)
                return false;
        } else if (!expiryDate.equals(other.expiryDate))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (incidentCode == null) {
            if (other.incidentCode != null)
                return false;
        } else if (!incidentCode.equals(other.incidentCode))
            return false;
        if (feeWaive == null) {
            if (other.feeWaive != null)
                return false;
        } else if (!feeWaive.equals(other.feeWaive))
            return false;
        if (feeCcy == null) {
            if (other.feeCcy != null)
                return false;
        } else if (!feeCcy.equals(other.feeCcy))
            return false;
        if (feeAmount == null) {
            if (other.feeAmount != null)
                return false;
        } else if (!feeAmount.equals(other.feeAmount))
            return false;
        if (registerDate == null) {
            if (other.registerDate != null)
                return false;
        } else if (!registerDate.equals(other.registerDate))
            return false;
        if (registerTime == null) {
            if (other.registerTime != null)
                return false;
        } else if (!registerTime.equals(other.registerTime))
            return false;
        if (registerBy == null) {
            if (other.registerBy != null)
                return false;
        } else if (!registerBy.equals(other.registerBy))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        if (lastUpdateDate == null) {
            if (other.lastUpdateDate != null)
                return false;
        } else if (!lastUpdateDate.equals(other.lastUpdateDate))
            return false;
        if (lastUpdateTime == null) {
            if (other.lastUpdateTime != null)
                return false;
        } else if (!lastUpdateTime.equals(other.lastUpdateTime))
            return false;
        if (lastUpdateUserID == null) {
            if (other.lastUpdateUserID != null)
                return false;
        } else if (!lastUpdateUserID.equals(other.lastUpdateUserID))
            return false;
        if (MISSendDate == null) {
            if (other.MISSendDate != null)
                return false;
        } else if (!MISSendDate.equals(other.MISSendDate))
            return false;
        if (issueDate == null) {
            if (other.issueDate != null)
                return false;
        } else if (!issueDate.equals(other.issueDate))
            return false;
        if (amendReason == null) {
            if (other.amendReason != null)
                return false;
        } else if (!amendReason.equals(other.amendReason))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CashCardDDTO{" +
                "bankType='" + bankType + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", primaryAccountNo='" + primaryAccountNo + '\'' +
                ", type='" + type + '\'' +
                ", sequenceNo=" + sequenceNo +
                ", cardNumber='" + cardNumber + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", CIFNo='" + CIFNo + '\'' +
                ", CIFName='" + CIFName + '\'' +
                ", passwordNo='" + passwordNo + '\'' +
                ", invalidAttemptCnt=" + invalidAttemptCnt +
                ", secondaryAccountNo='" + secondaryAccountNo + '\'' +
                ", ternaryAccountNo='" + ternaryAccountNo + '\'' +
                ", dailyLimitCcy='" + dailyLimitCcy + '\'' +
                ", dailyLimitAmount=" + dailyLimitAmount +
                ", dailyAccumAmount=" + dailyAccumAmount +
                ", dailyTrfLimitCcy='" + dailyTrfLimitCcy + '\'' +
                ", dailyTrfLimitAmount=" + dailyTrfLimitAmount +
                ", dailyTrfAccumAmount=" + dailyTrfAccumAmount +
                ", dailyAccumResetDate='" + dailyAccumResetDate + '\'' +
                ", dailyAccumResetTime='" + dailyAccumResetTime + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", status='" + status + '\'' +
                ", incidentCode='" + incidentCode + '\'' +
                ", feeWaive='" + feeWaive + '\'' +
                ", feeCcy='" + feeCcy + '\'' +
                ", feeAmount=" + feeAmount +
                ", registerDate='" + registerDate + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", registerBy='" + registerBy + '\'' +
                ", remark='" + remark + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", lastUpdateUserID='" + lastUpdateUserID + '\'' +
                ", MISSendDate='" + MISSendDate + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", amendReason='" + amendReason + '\'' +
                '}';
    }

    @Override
    public DTO clone() {
        CashCardDDTO cloned = new CashCardDDTO();
        cloned.bankType = this.bankType;
        cloned.bankCode = this.bankCode;
        cloned.primaryAccountNo = this.primaryAccountNo;
        cloned.type = this.type;
        cloned.sequenceNo = this.sequenceNo;
        cloned.cardNumber = this.cardNumber;
        cloned.branchCode = this.branchCode;
        cloned.CIFNo = this.CIFNo;
        cloned.CIFName = this.CIFName;
        cloned.passwordNo = this.passwordNo;
        cloned.invalidAttemptCnt = this.invalidAttemptCnt;
        cloned.secondaryAccountNo = this.secondaryAccountNo;
        cloned.ternaryAccountNo = this.ternaryAccountNo;
        cloned.dailyLimitCcy = this.dailyLimitCcy;
        cloned.dailyLimitAmount = this.dailyLimitAmount;
        cloned.dailyAccumAmount = this.dailyAccumAmount;
        cloned.dailyTrfLimitCcy = this.dailyTrfLimitCcy;
        cloned.dailyTrfLimitAmount = this.dailyTrfLimitAmount;
        cloned.dailyTrfAccumAmount = this.dailyTrfAccumAmount;
        cloned.dailyAccumResetDate = this.dailyAccumResetDate;
        cloned.dailyAccumResetTime = this.dailyAccumResetTime;
        cloned.effectiveDate = this.effectiveDate;
        cloned.expiryDate = this.expiryDate;
        cloned.status = this.status;
        cloned.incidentCode = this.incidentCode;
        cloned.feeWaive = this.feeWaive;
        cloned.feeCcy = this.feeCcy;
        cloned.feeAmount = this.feeAmount;
        cloned.registerDate = this.registerDate;
        cloned.registerTime = this.registerTime;
        cloned.registerBy = this.registerBy;
        cloned.remark = this.remark;
        cloned.lastUpdateDate = this.lastUpdateDate;
        cloned.lastUpdateTime = this.lastUpdateTime;
        cloned.lastUpdateUserID = this.lastUpdateUserID;
        cloned.MISSendDate = this.MISSendDate;
        cloned.issueDate = this.issueDate;
        cloned.amendReason = this.amendReason;
        return cloned;
    }
}
