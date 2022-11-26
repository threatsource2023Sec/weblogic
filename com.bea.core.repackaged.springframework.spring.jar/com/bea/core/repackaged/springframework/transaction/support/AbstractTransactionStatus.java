package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.NestedTransactionNotSupportedException;
import com.bea.core.repackaged.springframework.transaction.SavepointManager;
import com.bea.core.repackaged.springframework.transaction.TransactionException;
import com.bea.core.repackaged.springframework.transaction.TransactionStatus;
import com.bea.core.repackaged.springframework.transaction.TransactionUsageException;

public abstract class AbstractTransactionStatus implements TransactionStatus {
   private boolean rollbackOnly = false;
   private boolean completed = false;
   @Nullable
   private Object savepoint;

   public void setRollbackOnly() {
      this.rollbackOnly = true;
   }

   public boolean isRollbackOnly() {
      return this.isLocalRollbackOnly() || this.isGlobalRollbackOnly();
   }

   public boolean isLocalRollbackOnly() {
      return this.rollbackOnly;
   }

   public boolean isGlobalRollbackOnly() {
      return false;
   }

   public void flush() {
   }

   public void setCompleted() {
      this.completed = true;
   }

   public boolean isCompleted() {
      return this.completed;
   }

   protected void setSavepoint(@Nullable Object savepoint) {
      this.savepoint = savepoint;
   }

   @Nullable
   protected Object getSavepoint() {
      return this.savepoint;
   }

   public boolean hasSavepoint() {
      return this.savepoint != null;
   }

   public void createAndHoldSavepoint() throws TransactionException {
      this.setSavepoint(this.getSavepointManager().createSavepoint());
   }

   public void rollbackToHeldSavepoint() throws TransactionException {
      Object savepoint = this.getSavepoint();
      if (savepoint == null) {
         throw new TransactionUsageException("Cannot roll back to savepoint - no savepoint associated with current transaction");
      } else {
         this.getSavepointManager().rollbackToSavepoint(savepoint);
         this.getSavepointManager().releaseSavepoint(savepoint);
         this.setSavepoint((Object)null);
      }
   }

   public void releaseHeldSavepoint() throws TransactionException {
      Object savepoint = this.getSavepoint();
      if (savepoint == null) {
         throw new TransactionUsageException("Cannot release savepoint - no savepoint associated with current transaction");
      } else {
         this.getSavepointManager().releaseSavepoint(savepoint);
         this.setSavepoint((Object)null);
      }
   }

   public Object createSavepoint() throws TransactionException {
      return this.getSavepointManager().createSavepoint();
   }

   public void rollbackToSavepoint(Object savepoint) throws TransactionException {
      this.getSavepointManager().rollbackToSavepoint(savepoint);
   }

   public void releaseSavepoint(Object savepoint) throws TransactionException {
      this.getSavepointManager().releaseSavepoint(savepoint);
   }

   protected SavepointManager getSavepointManager() {
      throw new NestedTransactionNotSupportedException("This transaction does not support savepoints");
   }
}
