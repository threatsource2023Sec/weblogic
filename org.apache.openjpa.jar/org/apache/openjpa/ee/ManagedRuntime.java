package org.apache.openjpa.ee;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

public interface ManagedRuntime {
   TransactionManager getTransactionManager() throws Exception;

   void setRollbackOnly(Throwable var1) throws Exception;

   Throwable getRollbackCause() throws Exception;

   Object getTransactionKey() throws Exception, SystemException;
}
