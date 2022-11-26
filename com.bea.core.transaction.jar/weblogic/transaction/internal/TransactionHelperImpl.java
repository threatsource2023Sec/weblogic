package weblogic.transaction.internal;

import javax.transaction.UserTransaction;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;

public class TransactionHelperImpl extends TransactionHelper {
   public void setTransactionManagerService(Object ths) {
   }

   public UserTransaction getUserTransaction() {
      return (UserTransaction)this.getTransactionManager();
   }

   public ClientTransactionManager getTransactionManager() {
      return TransactionManagerImpl.getTransactionManager();
   }

   static {
      PlatformHelper.getPlatformHelper().makeTransactionAware();
   }
}
