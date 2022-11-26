package org.jboss.weld.transaction.spi;

import javax.transaction.Synchronization;
import javax.transaction.UserTransaction;
import org.jboss.weld.bootstrap.api.Service;

public interface TransactionServices extends Service {
   void registerSynchronization(Synchronization var1);

   boolean isTransactionActive();

   UserTransaction getUserTransaction();
}
