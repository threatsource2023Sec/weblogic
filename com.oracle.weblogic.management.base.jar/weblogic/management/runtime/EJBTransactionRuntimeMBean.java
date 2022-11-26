package weblogic.management.runtime;

public interface EJBTransactionRuntimeMBean extends RuntimeMBean {
   long getTransactionsCommittedTotalCount();

   long getTransactionsRolledBackTotalCount();

   long getTransactionsTimedOutTotalCount();
}
