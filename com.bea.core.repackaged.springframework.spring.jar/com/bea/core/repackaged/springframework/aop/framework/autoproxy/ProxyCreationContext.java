package com.bea.core.repackaged.springframework.aop.framework.autoproxy;

import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.lang.Nullable;

public final class ProxyCreationContext {
   private static final ThreadLocal currentProxiedBeanName = new NamedThreadLocal("Name of currently proxied bean");

   private ProxyCreationContext() {
   }

   @Nullable
   public static String getCurrentProxiedBeanName() {
      return (String)currentProxiedBeanName.get();
   }

   static void setCurrentProxiedBeanName(@Nullable String beanName) {
      if (beanName != null) {
         currentProxiedBeanName.set(beanName);
      } else {
         currentProxiedBeanName.remove();
      }

   }
}
