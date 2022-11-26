package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.TransactionStatus;

public abstract class TransactionCallbackWithoutResult implements TransactionCallback {
   @Nullable
   public final Object doInTransaction(TransactionStatus status) {
      this.doInTransactionWithoutResult(status);
      return null;
   }

   protected abstract void doInTransactionWithoutResult(TransactionStatus var1);
}
