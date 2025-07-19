package com.skax.eatool.cashCard.transfer;

import com.skax.eatool.framework.transfer.DTO;
import com.skax.eatool.framework.constants.Constants;

public class HotCardPageItemCDTO extends DTO {
    private String cardNumber = Constants.BLANK;
    private String primaryAccountNo = Constants.BLANK;
    private String CIFName = Constants.BLANK;
    private String incidentCode = Constants.BLANK;
    private String status = Constants.BLANK;
    private String registerDate = Constants.BLANK;
    private String releasedDate = Constants.BLANK;

    public HotCardPageItemCDTO() {
    }

    // Getter Method
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCIFName() {
        return CIFName;
    }

    public String getIncidentCode() {
        return incidentCode;
    }

    public String getPrimaryAccountNo() {
        return primaryAccountNo;
    }

    public String getStatus() {
        return status;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    // Setter Method
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCIFName(String CIFName) {
        this.CIFName = CIFName;
    }

    public void setIncidentCode(String incidentCode) {
        this.incidentCode = incidentCode;
    }

    public void setPrimaryAccountNo(String primaryAccountNo) {
        this.primaryAccountNo = primaryAccountNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + ((primaryAccountNo == null) ? 0 : primaryAccountNo.hashCode());
        result = prime * result + ((CIFName == null) ? 0 : CIFName.hashCode());
        result = prime * result + ((incidentCode == null) ? 0 : incidentCode.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
        result = prime * result + ((releasedDate == null) ? 0 : releasedDate.hashCode());
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
        HotCardPageItemCDTO other = (HotCardPageItemCDTO) obj;
        if (cardNumber == null) {
            if (other.cardNumber != null)
                return false;
        } else if (!cardNumber.equals(other.cardNumber))
            return false;
        if (primaryAccountNo == null) {
            if (other.primaryAccountNo != null)
                return false;
        } else if (!primaryAccountNo.equals(other.primaryAccountNo))
            return false;
        if (CIFName == null) {
            if (other.CIFName != null)
                return false;
        } else if (!CIFName.equals(other.CIFName))
            return false;
        if (incidentCode == null) {
            if (other.incidentCode != null)
                return false;
        } else if (!incidentCode.equals(other.incidentCode))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (registerDate == null) {
            if (other.registerDate != null)
                return false;
        } else if (!registerDate.equals(other.registerDate))
            return false;
        if (releasedDate == null) {
            if (other.releasedDate != null)
                return false;
        } else if (!releasedDate.equals(other.releasedDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HotCardPageItemCDTO{" +
                "cardNumber='" + cardNumber + '\'' +
                ", primaryAccountNo='" + primaryAccountNo + '\'' +
                ", CIFName='" + CIFName + '\'' +
                ", incidentCode='" + incidentCode + '\'' +
                ", status='" + status + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", releasedDate='" + releasedDate + '\'' +
                '}';
    }

    @Override
    public DTO clone() {
        HotCardPageItemCDTO cloned = new HotCardPageItemCDTO();
        cloned.cardNumber = this.cardNumber;
        cloned.primaryAccountNo = this.primaryAccountNo;
        cloned.CIFName = this.CIFName;
        cloned.incidentCode = this.incidentCode;
        cloned.status = this.status;
        cloned.registerDate = this.registerDate;
        cloned.releasedDate = this.releasedDate;
        return cloned;
    }
}
