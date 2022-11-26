package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;

public class DebugInterceptor extends SimpleTraceInterceptor {
   private volatile long count;

   public DebugInterceptor() {
   }

   public DebugInterceptor(boolean useDynamicLogger) {
      this.setUseDynamicLogger(useDynamicLogger);
   }

   public Object invoke(MethodInvocation invocation) throws Throwable {
      synchronized(this) {
         ++this.count;
      }

      return super.invoke(invocation);
   }

   protected String getInvocationDescription(MethodInvocation invocation) {
      return invocation + "; count=" + this.count;
   }

   public long getCount() {
      return this.count;
   }

   public synchronized void resetCount() {
      this.count = 0L;
   }
}
