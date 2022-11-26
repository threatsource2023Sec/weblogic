package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.springframework.util.StopWatch;

public class PerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {
   public PerformanceMonitorInterceptor() {
   }

   public PerformanceMonitorInterceptor(boolean useDynamicLogger) {
      this.setUseDynamicLogger(useDynamicLogger);
   }

   protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
      String name = this.createInvocationTraceName(invocation);
      StopWatch stopWatch = new StopWatch(name);
      stopWatch.start(name);

      Object var5;
      try {
         var5 = invocation.proceed();
      } finally {
         stopWatch.stop();
         this.writeToLog(logger, stopWatch.shortSummary());
      }

      return var5;
   }
}
