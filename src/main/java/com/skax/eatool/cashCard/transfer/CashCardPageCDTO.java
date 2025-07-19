package com.skax.eatool.cashCard.transfer;

import java.io.Serializable;
import java.util.List;

import com.skax.eatool.framework.transfer.PageDTO;

public class CashCardPageCDTO extends PageDTO {

    public CashCardPageCDTO(List pageItems, int pageNumber,
            int totalLineCount) {
        super(pageItems, pageNumber, 10, totalLineCount);
    }

    public CashCardPageCDTO(List pageItems, int pageNumber,
            int totalLineCount, int linePerPage) {
        super(pageItems, pageNumber, linePerPage, totalLineCount);
    }
}
