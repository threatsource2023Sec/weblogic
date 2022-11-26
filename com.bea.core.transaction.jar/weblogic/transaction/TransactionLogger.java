package weblogic.transaction;

public interface TransactionLogger {
   void store(TransactionLoggable var1);

   void release(TransactionLoggable var1);

   void checkpoint();

   String getMigratedCoordinatorURL();

   int getInitialRecoveredTransactionTotalCount();

   int getRecoveredTransactionCompletionCount();

   boolean hasTransactionLogRecords();
}
