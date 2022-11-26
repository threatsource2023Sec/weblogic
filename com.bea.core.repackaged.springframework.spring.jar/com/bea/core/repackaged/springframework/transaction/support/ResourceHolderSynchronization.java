package com.bea.core.repackaged.springframework.transaction.support;

public abstract class ResourceHolderSynchronization implements TransactionSynchronization {
   private final ResourceHolder resourceHolder;
   private final Object resourceKey;
   private volatile boolean holderActive = true;

   public ResourceHolderSynchronization(ResourceHolder resourceHolder, Object resourceKey) {
      this.resourceHolder = resourceHolder;
      this.resourceKey = resourceKey;
   }

   public void suspend() {
      if (this.holderActive) {
         TransactionSynchronizationManager.unbindResource(this.resourceKey);
      }

   }

   public void resume() {
      if (this.holderActive) {
         TransactionSynchronizationManager.bindResource(this.resourceKey, this.resourceHolder);
      }

   }

   public void flush() {
      this.flushResource(this.resourceHolder);
   }

   public void beforeCommit(boolean readOnly) {
   }

   public void beforeCompletion() {
      if (this.shouldUnbindAtCompletion()) {
         TransactionSynchronizationManager.unbindResource(this.resourceKey);
         this.holderActive = false;
         if (this.shouldReleaseBeforeCompletion()) {
            this.releaseResource(this.resourceHolder, this.resourceKey);
         }
      }

   }

   public void afterCommit() {
      if (!this.shouldReleaseBeforeCompletion()) {
         this.processResourceAfterCommit(this.resourceHolder);
      }

   }

   public void afterCompletion(int status) {
      if (this.shouldUnbindAtCompletion()) {
         boolean releaseNecessary = false;
         if (this.holderActive) {
            this.holderActive = false;
            TransactionSynchronizationManager.unbindResourceIfPossible(this.resourceKey);
            this.resourceHolder.unbound();
            releaseNecessary = true;
         } else {
            releaseNecessary = this.shouldReleaseAfterCompletion(this.resourceHolder);
         }

         if (releaseNecessary) {
            this.releaseResource(this.resourceHolder, this.resourceKey);
         }
      } else {
         this.cleanupResource(this.resourceHolder, this.resourceKey, status == 0);
      }

      this.resourceHolder.reset();
   }

   protected boolean shouldUnbindAtCompletion() {
      return true;
   }

   protected boolean shouldReleaseBeforeCompletion() {
      return true;
   }

   protected boolean shouldReleaseAfterCompletion(ResourceHolder resourceHolder) {
      return !this.shouldReleaseBeforeCompletion();
   }

   protected void flushResource(ResourceHolder resourceHolder) {
   }

   protected void processResourceAfterCommit(ResourceHolder resourceHolder) {
   }

   protected void releaseResource(ResourceHolder resourceHolder, Object resourceKey) {
   }

   protected void cleanupResource(ResourceHolder resourceHolder, Object resourceKey, boolean committed) {
   }
}
