package com.bea.core.repackaged.springframework.aop.target;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class AbstractLazyCreationTargetSource implements TargetSource {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private Object lazyTarget;

   public synchronized boolean isInitialized() {
      return this.lazyTarget != null;
   }

   @Nullable
   public synchronized Class getTargetClass() {
      return this.lazyTarget != null ? this.lazyTarget.getClass() : null;
   }

   public boolean isStatic() {
      return false;
   }

   public synchronized Object getTarget() throws Exception {
      if (this.lazyTarget == null) {
         this.logger.debug("Initializing lazy target object");
         this.lazyTarget = this.createObject();
      }

      return this.lazyTarget;
   }

   public void releaseTarget(Object target) throws Exception {
   }

   protected abstract Object createObject() throws Exception;
}
