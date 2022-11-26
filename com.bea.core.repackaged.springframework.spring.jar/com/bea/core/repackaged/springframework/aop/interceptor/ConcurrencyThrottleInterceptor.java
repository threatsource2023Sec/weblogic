package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.util.ConcurrencyThrottleSupport;
import java.io.Serializable;

public class ConcurrencyThrottleInterceptor extends ConcurrencyThrottleSupport implements MethodInterceptor, Serializable {
   public ConcurrencyThrottleInterceptor() {
      this.setConcurrencyLimit(1);
   }

   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      this.beforeAccess();

      Object var2;
      try {
         var2 = methodInvocation.proceed();
      } finally {
         this.afterAccess();
      }

      return var2;
   }
}
