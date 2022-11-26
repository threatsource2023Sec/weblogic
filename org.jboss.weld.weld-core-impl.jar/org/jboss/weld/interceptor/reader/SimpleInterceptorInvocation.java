package org.jboss.weld.interceptor.reader;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import javax.interceptor.InvocationContext;
import org.jboss.weld.interceptor.proxy.InterceptorInvocation;
import org.jboss.weld.interceptor.proxy.InterceptorMethodInvocation;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.util.collections.ImmutableList;

class SimpleInterceptorInvocation implements InterceptorInvocation {
   private final List interceptorMethodInvocations;
   private final Object instance;
   private final boolean targetClass;
   private final InterceptionType interceptionType;

   public SimpleInterceptorInvocation(Object instance, InterceptionType interceptionType, List interceptorMethods, boolean targetClass) {
      this.instance = instance;
      this.interceptionType = interceptionType;
      this.targetClass = targetClass;
      if (interceptorMethods.size() == 1) {
         this.interceptorMethodInvocations = ImmutableList.of(new SimpleMethodInvocation((Method)interceptorMethods.get(0)));
      } else {
         ImmutableList.Builder builder = ImmutableList.builder();
         Iterator var6 = interceptorMethods.iterator();

         while(var6.hasNext()) {
            Method method = (Method)var6.next();
            builder.add(new SimpleMethodInvocation(method));
         }

         this.interceptorMethodInvocations = builder.build();
      }

   }

   public List getInterceptorMethodInvocations() {
      return this.interceptorMethodInvocations;
   }

   class SimpleMethodInvocation implements InterceptorMethodInvocation {
      private final Method method;

      SimpleMethodInvocation(Method method) {
         this.method = method;
      }

      public Object invoke(InvocationContext invocationContext) throws Exception {
         return invocationContext != null ? this.method.invoke(SimpleInterceptorInvocation.this.instance, invocationContext) : this.method.invoke(SimpleInterceptorInvocation.this.instance);
      }

      public boolean expectsInvocationContext() {
         return !SimpleInterceptorInvocation.this.targetClass || !SimpleInterceptorInvocation.this.interceptionType.isLifecycleCallback();
      }

      public String toString() {
         return "SimpleMethodInvocation [method=" + this.method + ']';
      }
   }
}
