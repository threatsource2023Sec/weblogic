package com.oracle.injection.provider.weld;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionTarget;

public class ManagedInstance {
   private final Object beanInstance;
   private final InjectionTarget injectionTarget;
   private final CreationalContext creationalContext;

   public ManagedInstance(Object beanInstance, InjectionTarget injectionTarget, CreationalContext creationalContext) {
      this.beanInstance = beanInstance;
      this.injectionTarget = injectionTarget;
      this.creationalContext = creationalContext;
   }

   public void destroyInstance() {
      if (this.injectionTarget != null) {
         this.injectionTarget.dispose(this.beanInstance);
      }

      this.creationalContext.release();
   }

   public InjectionTarget getInjectionTarget() {
      return this.injectionTarget;
   }

   public CreationalContext getCreationalContext() {
      return this.creationalContext;
   }
}
