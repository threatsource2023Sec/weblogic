package weblogic.transaction.internal;

import weblogic.transaction.Transaction;

public interface JTAPartitionRuntime {
   long getTransactionTotalCount();

   void tallyCompletion(Transaction var1);

   void reset();

   void setTimeoutSeconds(int var1);

   int getTimeoutSeconds();

   void setDeterminers(String[] var1);

   String[] getDeterminers();

   NonXAResourceRuntime registerNonXAResource(String var1) throws Exception;

   void unregisterNonXAResource(NonXAResourceRuntime var1) throws Exception;

   TransactionResourceRuntime registerResource(String var1) throws Exception;

   void unregisterResource(TransactionResourceRuntime var1) throws Exception;
}
