package org.apache.openjpa.event;

public interface EndTransactionListener {
   void beforeCommit(TransactionEvent var1);

   void afterCommit(TransactionEvent var1);

   void afterRollback(TransactionEvent var1);

   void afterStateTransitions(TransactionEvent var1);

   void afterCommitComplete(TransactionEvent var1);

   void afterRollbackComplete(TransactionEvent var1);
}
