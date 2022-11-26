package weblogic.ejb.container.interfaces;

import javax.transaction.Transaction;
import weblogic.ejb.container.InternalException;

public interface TxManager {
   void registerSynchronization(Object var1, Transaction var2) throws InternalException;

   boolean hasListener(Transaction var1);

   void undeploy();
}
