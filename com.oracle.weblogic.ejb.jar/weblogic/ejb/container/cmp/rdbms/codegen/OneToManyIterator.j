@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@packageStatement

import java.util.Iterator;
import java.util.Set;

import javax.transaction.xa.Xid;

import weblogic.ejb.container.persistence.spi.EoWrapper;
import weblogic.ejb.container.persistence.spi.EloWrapper;
import weblogic.ejb.container.EJBTextTextFormatter;
import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.Debug;


public class @iteratorClassName implements Iterator {

  private @EoWrapper currWrapper = null;
  private Iterator @iterVar;
  private @setClassName @creatorVar;
  private Xid @createTxIdVar;

  public @iteratorClassName(Iterator @iterVar,
                            @setClassName @creatorVar) 
  {
    this.@iterVar = @iterVar;
    this.@creatorVar = @creatorVar;
    this.@createTxIdVar = @creatorVar.getXid();
  }

  public boolean hasNext() 
  {
    checkTransaction();

    return @iterVar.hasNext();
  }

  // Note: IllegalStateException checking for illegal
  //       calls to next() and remove() are deferred to
  //       the underlying Iterator.

  public Object next() 
  {
    checkTransaction();

    currWrapper = (@EoWrapper)@iterVar.next();
    return currWrapper.@getEJBObjectForField();
  }

  public void remove() 
  {
    checkTransaction();

    @iterVar.remove();
    if (null == currWrapper) {
      return;
    }
    @creatorVar.remove(currWrapper.@getEJBObjectForField(),
                       false,      // do ejbStore()
                       false);     // do Collection.remove()
  }

  private void checkTransaction()
  {
    Transaction tx = (weblogic.transaction.Transaction)TransactionHelper.getTransactionHelper().getTransaction();
    
    if ((tx == null) && (@createTxIdVar == null))
      return;
    else if ((tx == null) && (@createTxIdVar != null)) 
      if (! @isReadOnly) {
          Loggable l1 = EJBLogger.logaccessedCmrCollectionInDifferentTransactionLoggable("@ejbName", "@cmrName");
          throw new IllegalStateException(l1.getMessageText());
      }
    else if (!tx.getXid().equals(@createTxIdVar) && ! @isReadOnly ) {
      Loggable l1 = EJBLogger.logaccessedCmrCollectionInDifferentTransactionLoggable("@ejbName", "@cmrName");
      throw new IllegalStateException(l1.getMessageText());
    }    
    
  }
}
@end rule: main














