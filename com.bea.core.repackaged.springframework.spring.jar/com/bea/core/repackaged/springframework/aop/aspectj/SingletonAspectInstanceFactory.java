package com.bea.core.repackaged.springframework.aop.aspectj;

import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class SingletonAspectInstanceFactory implements AspectInstanceFactory, Serializable {
   private final Object aspectInstance;

   public SingletonAspectInstanceFactory(Object aspectInstance) {
      Assert.notNull(aspectInstance, "Aspect instance must not be null");
      this.aspectInstance = aspectInstance;
   }

   public final Object getAspectInstance() {
      return this.aspectInstance;
   }

   @Nullable
   public ClassLoader getAspectClassLoader() {
      return this.aspectInstance.getClass().getClassLoader();
   }

   public int getOrder() {
      return this.aspectInstance instanceof Ordered ? ((Ordered)this.aspectInstance).getOrder() : this.getOrderForAspectClass(this.aspectInstance.getClass());
   }

   protected int getOrderForAspectClass(Class aspectClass) {
      return Integer.MAX_VALUE;
   }
}
