package com.skax.eatool.cashCard.business.cashCardRule.helper;

import com.skax.eatool.common.business.facade.CommonManagementSBean;
import com.skax.eatool.reference.business.facade.IReferenceManagementSB;

public class JNDINamesRule
{
    // ?�거??EJB ?�거 - Spring Boot ?�경?�서???�용?��? ?�음
    // public static final Class CASH_CARD_HOME = CashCardSBHome.class;
    public static final Class COMMON_MANAGEMENT_SERVICE = CommonManagementSBean.class;
    public static final Class REFERENCE_MANAGEMENT_SERVICE = IReferenceManagementSB.class;
}
