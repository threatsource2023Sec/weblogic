package weblogic.servlet.provider;

import javax.transaction.TransactionManager;
import weblogic.servlet.spi.TransactionProvider;
import weblogic.transaction.TxHelper;

public class WlsTransactionProvider implements TransactionProvider {
   public TransactionManager getTransactionManager() {
      return TxHelper.getTransactionManager();
   }
}
