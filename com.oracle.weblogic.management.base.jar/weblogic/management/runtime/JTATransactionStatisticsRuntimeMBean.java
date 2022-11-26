package weblogic.management.runtime;

public interface JTATransactionStatisticsRuntimeMBean extends JTAStatisticsRuntimeMBean {
   long getTransactionRolledBackTimeoutTotalCount();

   long getTransactionRolledBackResourceTotalCount();

   long getTransactionRolledBackAppTotalCount();

   long getTransactionRolledBackSystemTotalCount();

   long getSecondsActiveTotalCount();

   long getTransactionAbandonedTotalCount();

   long getTransactionNoResourcesCommittedTotalCount();

   long getTransactionOneResourceOnePhaseCommittedTotalCount();

   long getTransactionReadOnlyOnePhaseCommittedTotalCount();

   long getTransactionTwoPhaseCommittedTotalCount();

   long getTransactionLLRCommittedTotalCount();

   long getTransactionTwoPhaseCommittedLoggedTotalCount();

   long getTransactionTwoPhaseCommittedNotLoggedTotalCount();
}
