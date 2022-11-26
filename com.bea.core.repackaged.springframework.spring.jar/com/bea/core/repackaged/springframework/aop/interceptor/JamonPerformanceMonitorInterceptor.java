package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.jamonapi.MonKey;
import com.jamonapi.MonKeyImp;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import com.jamonapi.utils.Misc;

public class JamonPerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {
   private boolean trackAllInvocations = false;

   public JamonPerformanceMonitorInterceptor() {
   }

   public JamonPerformanceMonitorInterceptor(boolean useDynamicLogger) {
      this.setUseDynamicLogger(useDynamicLogger);
   }

   public JamonPerformanceMonitorInterceptor(boolean useDynamicLogger, boolean trackAllInvocations) {
      this.setUseDynamicLogger(useDynamicLogger);
      this.setTrackAllInvocations(trackAllInvocations);
   }

   public void setTrackAllInvocations(boolean trackAllInvocations) {
      this.trackAllInvocations = trackAllInvocations;
   }

   protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
      return this.trackAllInvocations || this.isLogEnabled(logger);
   }

   protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
      String name = this.createInvocationTraceName(invocation);
      MonKey key = new MonKeyImp(name, name, "ms.");
      Monitor monitor = MonitorFactory.start(key);

      Object var6;
      try {
         var6 = invocation.proceed();
      } catch (Throwable var10) {
         this.trackException(key, var10);
         throw var10;
      } finally {
         monitor.stop();
         if (!this.trackAllInvocations || this.isLogEnabled(logger)) {
            this.writeToLog(logger, "JAMon performance statistics for method [" + name + "]:\n" + monitor);
         }

      }

      return var6;
   }

   protected void trackException(MonKey key, Throwable ex) {
      String stackTrace = "stackTrace=" + Misc.getExceptionTrace(ex);
      key.setDetails(stackTrace);
      MonitorFactory.add(new MonKeyImp(ex.getClass().getName(), stackTrace, "Exception"), 1.0);
      MonitorFactory.add(new MonKeyImp("com.jamonapi.Exceptions", stackTrace, "Exception"), 1.0);
   }
}
