package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.lang.Nullable;

public final class AopContext {
   private static final ThreadLocal currentProxy = new NamedThreadLocal("Current AOP proxy");

   private AopContext() {
   }

   public static Object currentProxy() throws IllegalStateException {
      Object proxy = currentProxy.get();
      if (proxy == null) {
         throw new IllegalStateException("Cannot find current proxy: Set 'exposeProxy' property on Advised to 'true' to make it available.");
      } else {
         return proxy;
      }
   }

   @Nullable
   static Object setCurrentProxy(@Nullable Object proxy) {
      Object old = currentProxy.get();
      if (proxy != null) {
         currentProxy.set(proxy);
      } else {
         currentProxy.remove();
      }

      return old;
   }
}
