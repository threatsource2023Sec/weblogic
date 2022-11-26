package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;

public abstract class AbstractTraceInterceptor implements MethodInterceptor, Serializable {
   @Nullable
   protected transient Log defaultLogger = LogFactory.getLog(this.getClass());
   private boolean hideProxyClassNames = false;
   private boolean logExceptionStackTrace = true;

   public void setUseDynamicLogger(boolean useDynamicLogger) {
      this.defaultLogger = useDynamicLogger ? null : LogFactory.getLog(this.getClass());
   }

   public void setLoggerName(String loggerName) {
      this.defaultLogger = LogFactory.getLog(loggerName);
   }

   public void setHideProxyClassNames(boolean hideProxyClassNames) {
      this.hideProxyClassNames = hideProxyClassNames;
   }

   public void setLogExceptionStackTrace(boolean logExceptionStackTrace) {
      this.logExceptionStackTrace = logExceptionStackTrace;
   }

   @Nullable
   public Object invoke(MethodInvocation invocation) throws Throwable {
      Log logger = this.getLoggerForInvocation(invocation);
      return this.isInterceptorEnabled(invocation, logger) ? this.invokeUnderTrace(invocation, logger) : invocation.proceed();
   }

   protected Log getLoggerForInvocation(MethodInvocation invocation) {
      if (this.defaultLogger != null) {
         return this.defaultLogger;
      } else {
         Object target = invocation.getThis();
         return LogFactory.getLog(this.getClassForLogging(target));
      }
   }

   protected Class getClassForLogging(Object target) {
      return this.hideProxyClassNames ? AopUtils.getTargetClass(target) : target.getClass();
   }

   protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
      return this.isLogEnabled(logger);
   }

   protected boolean isLogEnabled(Log logger) {
      return logger.isTraceEnabled();
   }

   protected void writeToLog(Log logger, String message) {
      this.writeToLog(logger, message, (Throwable)null);
   }

   protected void writeToLog(Log logger, String message, @Nullable Throwable ex) {
      if (ex != null && this.logExceptionStackTrace) {
         logger.trace(message, ex);
      } else {
         logger.trace(message);
      }

   }

   @Nullable
   protected abstract Object invokeUnderTrace(MethodInvocation var1, Log var2) throws Throwable;
}
