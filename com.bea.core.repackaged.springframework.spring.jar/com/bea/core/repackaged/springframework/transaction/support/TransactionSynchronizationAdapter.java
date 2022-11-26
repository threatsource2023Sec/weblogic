package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.core.Ordered;

public abstract class TransactionSynchronizationAdapter implements TransactionSynchronization, Ordered {
   public int getOrder() {
      return Integer.MAX_VALUE;
   }

   public void suspend() {
   }

   public void resume() {
   }

   public void flush() {
   }

   public void beforeCommit(boolean readOnly) {
   }

   public void beforeCompletion() {
   }

   public void afterCommit() {
   }

   public void afterCompletion(int status) {
   }
}
