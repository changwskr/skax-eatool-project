package com.skax.eatool.framework.transaction.tcf;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.*;

import com.skax.eatool.eplatonframework.transfer.EPlatonEvent;

public interface IETF
{
  public EPlatonEvent execute(EPlatonEvent event) ;
  public abstract boolean ETF_SPinit() ;
  public abstract boolean ETF_SPmiddle() ;
  public abstract boolean ETF_SPend() ;

}
