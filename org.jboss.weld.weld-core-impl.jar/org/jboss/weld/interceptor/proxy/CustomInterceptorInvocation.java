package org.jboss.weld.interceptor.proxy;

import java.util.Collections;
import java.util.List;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.interceptor.InvocationContext;

public class CustomInterceptorInvocation implements InterceptorInvocation {
   private final Interceptor interceptorBeanInstance;
   private final Object interceptorInstance;
   private final InterceptionType interceptionType;

   public CustomInterceptorInvocation(Interceptor interceptorBeanInstance, Object interceptorInstance, InterceptionType interceptionType) {
      this.interceptorBeanInstance = interceptorBeanInstance;
      this.interceptorInstance = interceptorInstance;
      this.interceptionType = interceptionType;
   }

   public List getInterceptorMethodInvocations() {
      return Collections.singletonList(new CustomInterceptorMethodInvocation());
   }

   private class CustomInterceptorMethodInvocation implements InterceptorMethodInvocation {
      private CustomInterceptorMethodInvocation() {
      }

      public Object invoke(InvocationContext invocationContext) throws Exception {
         return CustomInterceptorInvocation.this.interceptorBeanInstance.intercept(CustomInterceptorInvocation.this.interceptionType, CustomInterceptorInvocation.this.interceptorInstance, invocationContext);
      }

      public boolean expectsInvocationContext() {
         return true;
      }

      // $FF: synthetic method
      CustomInterceptorMethodInvocation(Object x1) {
         this();
      }
   }
}
