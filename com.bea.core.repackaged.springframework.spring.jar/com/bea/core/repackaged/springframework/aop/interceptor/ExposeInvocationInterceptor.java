package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.support.DefaultPointcutAdvisor;
import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.core.PriorityOrdered;
import java.io.Serializable;

public final class ExposeInvocationInterceptor implements MethodInterceptor, PriorityOrdered, Serializable {
   public static final ExposeInvocationInterceptor INSTANCE = new ExposeInvocationInterceptor();
   public static final Advisor ADVISOR;
   private static final ThreadLocal invocation;

   public static MethodInvocation currentInvocation() throws IllegalStateException {
      MethodInvocation mi = (MethodInvocation)invocation.get();
      if (mi == null) {
         throw new IllegalStateException("No MethodInvocation found: Check that an AOP invocation is in progress, and that the ExposeInvocationInterceptor is upfront in the interceptor chain. Specifically, note that advices with order HIGHEST_PRECEDENCE will execute before ExposeInvocationInterceptor!");
      } else {
         return mi;
      }
   }

   private ExposeInvocationInterceptor() {
   }

   public Object invoke(MethodInvocation mi) throws Throwable {
      MethodInvocation oldInvocation = (MethodInvocation)invocation.get();
      invocation.set(mi);

      Object var3;
      try {
         var3 = mi.proceed();
      } finally {
         invocation.set(oldInvocation);
      }

      return var3;
   }

   public int getOrder() {
      return -2147483647;
   }

   private Object readResolve() {
      return INSTANCE;
   }

   static {
      ADVISOR = new DefaultPointcutAdvisor(INSTANCE) {
         public String toString() {
            return ExposeInvocationInterceptor.class.getName() + ".ADVISOR";
         }
      };
      invocation = new NamedThreadLocal("Current AOP method invocation");
   }
}
