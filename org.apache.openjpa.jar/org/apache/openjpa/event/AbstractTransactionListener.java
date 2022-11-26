package org.apache.openjpa.event;

public abstract class AbstractTransactionListener implements TransactionListener {
   protected void eventOccurred(TransactionEvent event) {
   }

   public void afterBegin(TransactionEvent event) {
      this.eventOccurred(event);
   }

   public void beforeFlush(TransactionEvent event) {
      this.eventOccurred(event);
   }

   public void afterFlush(TransactionEvent event) {
      this.eventOccurred(event);
   }

   public void beforeCommit(TransactionEvent event) {
      this.eventOccurred(event);
   }

   public void afterCommit(TransactionEvent event) {
      this.eventOccurred(event);
   }

   public void afterRollback(TransactionEvent event) {
      this.eventOccurred(event);
   }

   public void afterStateTransitions(TransactionEvent event) {
      this.eventOccurred(event);
   }

   public void afterCommitComplete(TransactionEvent event) {
      this.eventOccurred(event);
   }

   public void afterRollbackComplete(TransactionEvent event) {
      this.eventOccurred(event);
   }
}
