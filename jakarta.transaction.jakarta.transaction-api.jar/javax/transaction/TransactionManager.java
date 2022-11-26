package javax.transaction;

public interface TransactionManager {
   void begin() throws NotSupportedException, SystemException;

   void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException;

   int getStatus() throws SystemException;

   Transaction getTransaction() throws SystemException;

   void resume(Transaction var1) throws InvalidTransactionException, IllegalStateException, SystemException;

   void rollback() throws IllegalStateException, SecurityException, SystemException;

   void setRollbackOnly() throws IllegalStateException, SystemException;

   void setTransactionTimeout(int var1) throws SystemException;

   Transaction suspend() throws SystemException;
}
