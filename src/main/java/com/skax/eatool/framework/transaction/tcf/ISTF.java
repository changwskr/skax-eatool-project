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

public interface ISTF
{
  public EPlatonEvent execute(EPlatonEvent event) ;
  public abstract boolean STF_SPinit() ;
  public abstract boolean STF_SPmiddle() ;
  public abstract boolean STF_SPend() ;

}
