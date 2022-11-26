package weblogic.management.runtime;

public interface JTARecoveryRuntimeMBean extends RuntimeMBean {
   boolean isActive();

   int getInitialRecoveredTransactionTotalCount();

   int getInitialRecoveredUnloggedTransactionTotalCount();

   int getRecoveredTransactionCompletionPercent();

   int getRecoveredUnloggedTransactionCompletionPercent();
}
