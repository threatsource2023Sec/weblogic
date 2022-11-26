package javax.transaction;

public interface TransactionSynchronizationRegistry {
   Object getTransactionKey();

   void putResource(Object var1, Object var2);

   Object getResource(Object var1);

   void registerInterposedSynchronization(Synchronization var1);

   int getTransactionStatus();

   void setRollbackOnly();

   boolean getRollbackOnly();
}
