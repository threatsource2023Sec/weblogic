package weblogic.servlet.spi;

import javax.transaction.TransactionManager;

public interface TransactionProvider {
   TransactionManager getTransactionManager();
}
