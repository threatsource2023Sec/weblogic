package weblogic.transaction.internal;

import weblogic.transaction.Transaction;

public interface JTARuntime extends JTAHealthListener {
   void healthEvent(HealthEvent var1);

   long getTransactionTotalCount();

   void tallyCompletion(Transaction var1);

   NonXAResourceRuntime registerNonXAResource(String var1) throws Exception;

   void unregisterNonXAResource(NonXAResourceRuntime var1) throws Exception;

   TransactionResourceRuntime registerResource(String var1) throws Exception;

   void unregisterResource(TransactionResourceRuntime var1) throws Exception;

   String getHealthStateString();

   String[] getHealthStateReasonCodes();
}
