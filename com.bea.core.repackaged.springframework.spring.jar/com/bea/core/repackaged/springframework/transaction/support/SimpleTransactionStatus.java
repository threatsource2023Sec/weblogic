package com.bea.core.repackaged.springframework.transaction.support;

public class SimpleTransactionStatus extends AbstractTransactionStatus {
   private final boolean newTransaction;

   public SimpleTransactionStatus() {
      this(true);
   }

   public SimpleTransactionStatus(boolean newTransaction) {
      this.newTransaction = newTransaction;
   }

   public boolean isNewTransaction() {
      return this.newTransaction;
   }
}
