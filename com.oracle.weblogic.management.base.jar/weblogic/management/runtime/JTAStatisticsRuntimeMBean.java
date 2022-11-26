package weblogic.management.runtime;

public interface JTAStatisticsRuntimeMBean extends RuntimeMBean {
   long getTransactionTotalCount();

   long getTransactionCommittedTotalCount();

   long getTransactionRolledBackTotalCount();

   long getTransactionHeuristicsTotalCount();
}
