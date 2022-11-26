package com.bea.core.repackaged.springframework.transaction.jta;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronization;
import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronizationManager;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.transaction.Synchronization;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

public class SpringJtaSynchronizationAdapter implements Synchronization {
   protected static final Log logger = LogFactory.getLog(SpringJtaSynchronizationAdapter.class);
   private final TransactionSynchronization springSynchronization;
   @Nullable
   private UserTransaction jtaTransaction;
   private boolean beforeCompletionCalled;

   public SpringJtaSynchronizationAdapter(TransactionSynchronization springSynchronization) {
      this.beforeCompletionCalled = false;
      Assert.notNull(springSynchronization, (String)"TransactionSynchronization must not be null");
      this.springSynchronization = springSynchronization;
   }

   public SpringJtaSynchronizationAdapter(TransactionSynchronization springSynchronization, @Nullable UserTransaction jtaUserTransaction) {
      this(springSynchronization);
      if (jtaUserTransaction != null && !jtaUserTransaction.getClass().getName().startsWith("weblogic.")) {
         this.jtaTransaction = jtaUserTransaction;
      }

   }

   public SpringJtaSynchronizationAdapter(TransactionSynchronization springSynchronization, @Nullable TransactionManager jtaTransactionManager) {
      this(springSynchronization);
      if (jtaTransactionManager != null && !jtaTransactionManager.getClass().getName().startsWith("weblogic.")) {
         this.jtaTransaction = new UserTransactionAdapter(jtaTransactionManager);
      }

   }

   public void beforeCompletion() {
      try {
         boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
         this.springSynchronization.beforeCommit(readOnly);
      } catch (Error | RuntimeException var5) {
         this.setRollbackOnlyIfPossible();
         throw var5;
      } finally {
         this.beforeCompletionCalled = true;
         this.springSynchronization.beforeCompletion();
      }

   }

   private void setRollbackOnlyIfPossible() {
      if (this.jtaTransaction != null) {
         try {
            this.jtaTransaction.setRollbackOnly();
         } catch (UnsupportedOperationException var2) {
            logger.debug("JTA transaction handle does not support setRollbackOnly method - relying on JTA provider to mark the transaction as rollback-only based on the exception thrown from beforeCompletion", var2);
         } catch (Throwable var3) {
            logger.error("Could not set JTA transaction rollback-only", var3);
         }
      } else {
         logger.debug("No JTA transaction handle available and/or running on WebLogic - relying on JTA provider to mark the transaction as rollback-only based on the exception thrown from beforeCompletion");
      }

   }

   public void afterCompletion(int status) {
      if (!this.beforeCompletionCalled) {
         this.springSynchronization.beforeCompletion();
      }

      switch (status) {
         case 3:
            this.springSynchronization.afterCompletion(0);
            break;
         case 4:
            this.springSynchronization.afterCompletion(1);
            break;
         default:
            this.springSynchronization.afterCompletion(2);
      }

   }
}
