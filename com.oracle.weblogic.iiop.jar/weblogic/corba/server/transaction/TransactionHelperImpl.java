package weblogic.corba.server.transaction;

import javax.transaction.UserTransaction;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;

public class TransactionHelperImpl extends TransactionHelper {
   public UserTransaction getUserTransaction() {
      return (UserTransaction)this.getTransactionManager();
   }

   public ClientTransactionManager getTransactionManager() {
      return TransactionManagerImpl.getTransactionManager();
   }
}
