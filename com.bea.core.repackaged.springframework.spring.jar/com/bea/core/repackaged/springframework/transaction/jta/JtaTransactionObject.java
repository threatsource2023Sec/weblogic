package com.bea.core.repackaged.springframework.transaction.jta;

import com.bea.core.repackaged.springframework.transaction.TransactionSystemException;
import com.bea.core.repackaged.springframework.transaction.support.SmartTransactionObject;
import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronizationUtils;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class JtaTransactionObject implements SmartTransactionObject {
   private final UserTransaction userTransaction;
   boolean resetTransactionTimeout = false;

   public JtaTransactionObject(UserTransaction userTransaction) {
      this.userTransaction = userTransaction;
   }

   public final UserTransaction getUserTransaction() {
      return this.userTransaction;
   }

   public boolean isRollbackOnly() {
      try {
         int jtaStatus = this.userTransaction.getStatus();
         return jtaStatus == 1 || jtaStatus == 4;
      } catch (SystemException var2) {
         throw new TransactionSystemException("JTA failure on getStatus", var2);
      }
   }

   public void flush() {
      TransactionSynchronizationUtils.triggerFlush();
   }
}
