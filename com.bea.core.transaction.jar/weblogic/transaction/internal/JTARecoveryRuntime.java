package weblogic.transaction.internal;

public interface JTARecoveryRuntime {
   void reset(int var1);

   void resetUnlogged(int var1);

   int getInitialRecoveredUnloggedTransactionTotalCount();

   void setFinalTransactionCompletionCount(int var1);

   void setFinalUnloggedTransactionCompletionCount(int var1);

   int getRecoveredUnloggedTransactionCompletionPercent();
}
