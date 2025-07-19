package com.skax.eatool.cashCard.business.cashCardRule.helper;

import com.skax.eatool.common.business.facade.CommonManagementSBean;
import com.skax.eatool.reference.business.facade.IReferenceManagementSB;

public class JNDINamesRule {
    // 레거시 EJB 참조 - Spring Boot 환경에서는 사용하지 않음
    // public static final Class CASH_CARD_HOME = CashCardSBHome.class;
    public static final Class COMMON_MANAGEMENT_SERVICE = CommonManagementSBean.class;
    public static final Class REFERENCE_MANAGEMENT_SERVICE = IReferenceManagementSB.class;
}
