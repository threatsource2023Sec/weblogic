package com.bea.core.repackaged.springframework.transaction.jta;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

public class SimpleTransactionFactory implements TransactionFactory {
   private final TransactionManager transactionManager;

   public SimpleTransactionFactory(TransactionManager transactionManager) {
      Assert.notNull(transactionManager, (String)"TransactionManager must not be null");
      this.transactionManager = transactionManager;
   }

   public Transaction createTransaction(@Nullable String name, int timeout) throws NotSupportedException, SystemException {
      if (timeout >= 0) {
         this.transactionManager.setTransactionTimeout(timeout);
      }

      this.transactionManager.begin();
      return new ManagedTransactionAdapter(this.transactionManager);
   }

   public boolean supportsResourceAdapterManagedTransactions() {
      return false;
   }
}
