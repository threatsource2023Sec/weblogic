package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.NestedTransactionNotSupportedException;
import com.bea.core.repackaged.springframework.transaction.SavepointManager;
import com.bea.core.repackaged.springframework.util.Assert;

public class DefaultTransactionStatus extends AbstractTransactionStatus {
   @Nullable
   private final Object transaction;
   private final boolean newTransaction;
   private final boolean newSynchronization;
   private final boolean readOnly;
   private final boolean debug;
   @Nullable
   private final Object suspendedResources;

   public DefaultTransactionStatus(@Nullable Object transaction, boolean newTransaction, boolean newSynchronization, boolean readOnly, boolean debug, @Nullable Object suspendedResources) {
      this.transaction = transaction;
      this.newTransaction = newTransaction;
      this.newSynchronization = newSynchronization;
      this.readOnly = readOnly;
      this.debug = debug;
      this.suspendedResources = suspendedResources;
   }

   public Object getTransaction() {
      Assert.state(this.transaction != null, "No transaction active");
      return this.transaction;
   }

   public boolean hasTransaction() {
      return this.transaction != null;
   }

   public boolean isNewTransaction() {
      return this.hasTransaction() && this.newTransaction;
   }

   public boolean isNewSynchronization() {
      return this.newSynchronization;
   }

   public boolean isReadOnly() {
      return this.readOnly;
   }

   public boolean isDebug() {
      return this.debug;
   }

   @Nullable
   public Object getSuspendedResources() {
      return this.suspendedResources;
   }

   public boolean isGlobalRollbackOnly() {
      return this.transaction instanceof SmartTransactionObject && ((SmartTransactionObject)this.transaction).isRollbackOnly();
   }

   public void flush() {
      if (this.transaction instanceof SmartTransactionObject) {
         ((SmartTransactionObject)this.transaction).flush();
      }

   }

   protected SavepointManager getSavepointManager() {
      Object transaction = this.transaction;
      if (!(transaction instanceof SavepointManager)) {
         throw new NestedTransactionNotSupportedException("Transaction object [" + this.transaction + "] does not support savepoints");
      } else {
         return (SavepointManager)transaction;
      }
   }

   public boolean isTransactionSavepointManager() {
      return this.transaction instanceof SavepointManager;
   }
}
