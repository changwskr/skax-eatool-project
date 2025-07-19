package com.skax.eatool.eplatonframework.business.facade.teller;


import com.skax.eatool.eplatonframework.transfer.EPlatonEvent;
import com.skax.eatool.eplatonframework.transfer.*;
import com.skax.eatool.framework.transaction.tcf.*;
import com.skax.eatool.foundation.logej.*;

/**
 * =============================================================================
 * ?�로그램 ?�명:
 * =============================================================================
 * ?�제 ?�무 ?�스?�의 facade?�이 ?�의?�는 부분이??
 * ?�재 ??�??�무?�스?�의 facade?�을 직접가지�?감으로서 모든 ?�스?�을 ?�나�?로직??관�? * ?�고 ?�랜??��???�률?�으�?관리하�??�한 로직?�다.
 * ?�제 ?�출?�는 ?�션빈에 ?�??메소?�는 execute()?�다.
 * ??메소?�내?�서 �??�무?�스?�과???�계�??�한 ?�나??메소?��? ?�의 ?�다.
 *
 * =============================================================================
 * 변경내???�보:
 * =============================================================================
 *  2004??03??16??1차버??release
 *
 *
 * =============================================================================
 *                                                        @author : ?�우??WooSungJang)
 *                                                        @company: IMS SYSTEM
 *                                                        @email  : changwskr@yahoo.co.kr
 *                                                        @version 1.0
 *  =============================================================================
 */

public class TellerManagementSBBean implements ITellerManagementSB
{

  public EPlatonEvent execute(EPlatonEvent event)
  {
    EPlatonEvent resp_event = null;
    try
    {
      resp_event = event;

      /*************************************************************************
       * 1??: 만약 TCF?�서 관리되??것�? USERTX,SESSIONCTX ?�이 ?�다.
       *       그런??만약 2?�처???�용?�에???�정보�? 1?�로 관리되므�?조심?�야 ?�다.
       *       Iellegalexception??많이 발생?�다.
       *
       *************************************************************************/
      TCF tcf = new TCF();
      // execute(String transactionId, String transactionType, EPlatonEvent event)
      String transactionId="1111";
      String transactionType="usertx";

      resp_event = (EPlatonEvent) tcf.execute(transactionId, transactionType, event );

    }
    catch (Exception re)
    {

      re.printStackTrace();
      //////////////////////////////////////////////////////////////////////////
      // ?�러코드 관�?      //////////////////////////////////////////////////////////////////////////
      {
        TPSVCINFODTO tpsvcinfo = ((EPlatonEvent)resp_event).getTPSVCINFODTO();

        switch( tpsvcinfo.getErrorcode().charAt(0) )
        {
          case 'I' :
            tpsvcinfo.setErrorcode("EFWK0041");
            tpsvcinfo.setError_message(this.getClass().getName()+ ".execute():" + re.toString());
            break;
          case 'E' :
            String errorcode = "EFWK0041"+"|"+tpsvcinfo.getErrorcode();
            tpsvcinfo.setErrorcode(errorcode);
            tpsvcinfo.setError_message(this.getClass().getName()+ ".execute():" + re.toString());
            break;
        }
      }
      //////////////////////////////////////////////////////////////////////////
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,re);
      LOGEJ.getInstance().printf(1,resp_event,this.getClass().getName()+ ".execute():" + re.toString());

    }

    return resp_event;

  }


}
