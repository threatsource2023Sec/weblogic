package weblogic.corba.client.transaction;

import javax.transaction.UserTransaction;
import weblogic.corba.j2ee.transaction.TransactionManagerWrapper;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;

public class TransactionHelperImpl extends TransactionHelper {
   public UserTransaction getUserTransaction() {
      return (UserTransaction)this.getTransactionManager();
   }

   public ClientTransactionManager getTransactionManager() {
      return TransactionManagerWrapper.getTransactionManager();
   }
}
