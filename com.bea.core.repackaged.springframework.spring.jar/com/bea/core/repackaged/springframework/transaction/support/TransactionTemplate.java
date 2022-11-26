package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.PlatformTransactionManager;
import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;
import com.bea.core.repackaged.springframework.transaction.TransactionException;
import com.bea.core.repackaged.springframework.transaction.TransactionStatus;
import com.bea.core.repackaged.springframework.transaction.TransactionSystemException;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.UndeclaredThrowableException;

public class TransactionTemplate extends DefaultTransactionDefinition implements TransactionOperations, InitializingBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private PlatformTransactionManager transactionManager;

   public TransactionTemplate() {
   }

   public TransactionTemplate(PlatformTransactionManager transactionManager) {
      this.transactionManager = transactionManager;
   }

   public TransactionTemplate(PlatformTransactionManager transactionManager, TransactionDefinition transactionDefinition) {
      super(transactionDefinition);
      this.transactionManager = transactionManager;
   }

   public void setTransactionManager(@Nullable PlatformTransactionManager transactionManager) {
      this.transactionManager = transactionManager;
   }

   @Nullable
   public PlatformTransactionManager getTransactionManager() {
      return this.transactionManager;
   }

   public void afterPropertiesSet() {
      if (this.transactionManager == null) {
         throw new IllegalArgumentException("Property 'transactionManager' is required");
      }
   }

   @Nullable
   public Object execute(TransactionCallback action) throws TransactionException {
      Assert.state(this.transactionManager != null, "No PlatformTransactionManager set");
      if (this.transactionManager instanceof CallbackPreferringPlatformTransactionManager) {
         return ((CallbackPreferringPlatformTransactionManager)this.transactionManager).execute(this, action);
      } else {
         TransactionStatus status = this.transactionManager.getTransaction(this);

         Object result;
         try {
            result = action.doInTransaction(status);
         } catch (Error | RuntimeException var5) {
            this.rollbackOnException(status, var5);
            throw var5;
         } catch (Throwable var6) {
            this.rollbackOnException(status, var6);
            throw new UndeclaredThrowableException(var6, "TransactionCallback threw undeclared checked exception");
         }

         this.transactionManager.commit(status);
         return result;
      }
   }

   private void rollbackOnException(TransactionStatus status, Throwable ex) throws TransactionException {
      Assert.state(this.transactionManager != null, "No PlatformTransactionManager set");
      this.logger.debug("Initiating transaction rollback on application exception", ex);

      try {
         this.transactionManager.rollback(status);
      } catch (TransactionSystemException var4) {
         this.logger.error("Application exception overridden by rollback exception", ex);
         var4.initApplicationException(ex);
         throw var4;
      } catch (Error | RuntimeException var5) {
         this.logger.error("Application exception overridden by rollback exception", ex);
         throw var5;
      }
   }

   public boolean equals(Object other) {
      return this == other || super.equals(other) && (!(other instanceof TransactionTemplate) || this.getTransactionManager() == ((TransactionTemplate)other).getTransactionManager());
   }
}
