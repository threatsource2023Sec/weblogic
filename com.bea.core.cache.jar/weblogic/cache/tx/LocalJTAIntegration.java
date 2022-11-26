package weblogic.cache.tx;

import javax.transaction.TransactionManager;

public class LocalJTAIntegration extends AbstractTransactionManagerJTAIntegration {
   public TransactionManager getTransactionManager() {
      return LocalTransactionManager.getInstance();
   }
}
