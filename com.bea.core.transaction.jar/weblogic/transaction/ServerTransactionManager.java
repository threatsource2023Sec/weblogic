package weblogic.transaction;

import javax.transaction.SystemException;
import weblogic.transaction.loggingresource.LoggingResource;

public interface ServerTransactionManager extends TransactionManager {
   TransactionLogger getTransactionLogger();

   Object getJTATransactionForThread(Thread var1);

   void registerBeginNotificationListener(BeginNotificationListener var1, Object var2);

   void unregisterBeginNotificationListener(BeginNotificationListener var1);

   void registerLoggingResourceTransactions(LoggingResource var1, boolean var2) throws SystemException;

   void setOverrideTransactionTimeout(int var1);
}
