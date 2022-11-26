package javax.transaction;

import javax.transaction.xa.XAResource;

public interface Transaction {
   void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException;

   boolean delistResource(XAResource var1, int var2) throws IllegalStateException, SystemException;

   boolean enlistResource(XAResource var1) throws RollbackException, IllegalStateException, SystemException;

   int getStatus() throws SystemException;

   void registerSynchronization(Synchronization var1) throws RollbackException, IllegalStateException, SystemException;

   void rollback() throws IllegalStateException, SystemException;

   void setRollbackOnly() throws IllegalStateException, SystemException;
}
