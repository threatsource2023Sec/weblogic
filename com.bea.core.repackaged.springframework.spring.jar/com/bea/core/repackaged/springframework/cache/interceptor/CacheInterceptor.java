package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;
import java.lang.reflect.Method;

public class CacheInterceptor extends CacheAspectSupport implements MethodInterceptor, Serializable {
   @Nullable
   public Object invoke(MethodInvocation invocation) throws Throwable {
      Method method = invocation.getMethod();
      CacheOperationInvoker aopAllianceInvoker = () -> {
         try {
            return invocation.proceed();
         } catch (Throwable var2) {
            throw new CacheOperationInvoker.ThrowableWrapper(var2);
         }
      };

      try {
         return this.execute(aopAllianceInvoker, invocation.getThis(), method, invocation.getArguments());
      } catch (CacheOperationInvoker.ThrowableWrapper var5) {
         throw var5.getOriginal();
      }
   }
}
