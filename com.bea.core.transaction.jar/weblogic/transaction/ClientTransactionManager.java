package weblogic.transaction;

public interface ClientTransactionManager extends javax.transaction.TransactionManager {
   void forceResume(javax.transaction.Transaction var1);

   javax.transaction.Transaction forceSuspend();
}
