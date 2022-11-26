package com.bea.core.repackaged.springframework.aop.target.dynamic;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class AbstractRefreshableTargetSource implements TargetSource, Refreshable {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   protected Object targetObject;
   private long refreshCheckDelay = -1L;
   private long lastRefreshCheck = -1L;
   private long lastRefreshTime = -1L;
   private long refreshCount = 0L;

   public void setRefreshCheckDelay(long refreshCheckDelay) {
      this.refreshCheckDelay = refreshCheckDelay;
   }

   public synchronized Class getTargetClass() {
      if (this.targetObject == null) {
         this.refresh();
      }

      return this.targetObject.getClass();
   }

   public boolean isStatic() {
      return false;
   }

   @Nullable
   public final synchronized Object getTarget() {
      if (this.refreshCheckDelayElapsed() && this.requiresRefresh() || this.targetObject == null) {
         this.refresh();
      }

      return this.targetObject;
   }

   public void releaseTarget(Object object) {
   }

   public final synchronized void refresh() {
      this.logger.debug("Attempting to refresh target");
      this.targetObject = this.freshTarget();
      ++this.refreshCount;
      this.lastRefreshTime = System.currentTimeMillis();
      this.logger.debug("Target refreshed successfully");
   }

   public synchronized long getRefreshCount() {
      return this.refreshCount;
   }

   public synchronized long getLastRefreshTime() {
      return this.lastRefreshTime;
   }

   private boolean refreshCheckDelayElapsed() {
      if (this.refreshCheckDelay < 0L) {
         return false;
      } else {
         long currentTimeMillis = System.currentTimeMillis();
         if (this.lastRefreshCheck >= 0L && currentTimeMillis - this.lastRefreshCheck <= this.refreshCheckDelay) {
            return false;
         } else {
            this.lastRefreshCheck = currentTimeMillis;
            this.logger.debug("Refresh check delay elapsed - checking whether refresh is required");
            return true;
         }
      }
   }

   protected boolean requiresRefresh() {
      return true;
   }

   protected abstract Object freshTarget();
}
