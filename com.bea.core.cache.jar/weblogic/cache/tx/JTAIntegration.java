package weblogic.cache.tx;

import javax.transaction.Synchronization;

public interface JTAIntegration {
   int getTransactionStatus();

   void registerSynchronization(Synchronization var1);
}
