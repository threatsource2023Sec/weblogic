package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;

public class SimpleTraceInterceptor extends AbstractTraceInterceptor {
   public SimpleTraceInterceptor() {
   }

   public SimpleTraceInterceptor(boolean useDynamicLogger) {
      this.setUseDynamicLogger(useDynamicLogger);
   }

   protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
      String invocationDescription = this.getInvocationDescription(invocation);
      this.writeToLog(logger, "Entering " + invocationDescription);

      try {
         Object rval = invocation.proceed();
         this.writeToLog(logger, "Exiting " + invocationDescription);
         return rval;
      } catch (Throwable var5) {
         this.writeToLog(logger, "Exception thrown in " + invocationDescription, var5);
         throw var5;
      }
   }

   protected String getInvocationDescription(MethodInvocation invocation) {
      return "method '" + invocation.getMethod().getName() + "' of class [" + invocation.getThis().getClass().getName() + "]";
   }
}
