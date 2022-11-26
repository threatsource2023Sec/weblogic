package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;

public abstract class AbstractMonitoringInterceptor extends AbstractTraceInterceptor {
   private String prefix = "";
   private String suffix = "";
   private boolean logTargetClassInvocation = false;

   public void setPrefix(@Nullable String prefix) {
      this.prefix = prefix != null ? prefix : "";
   }

   protected String getPrefix() {
      return this.prefix;
   }

   public void setSuffix(@Nullable String suffix) {
      this.suffix = suffix != null ? suffix : "";
   }

   protected String getSuffix() {
      return this.suffix;
   }

   public void setLogTargetClassInvocation(boolean logTargetClassInvocation) {
      this.logTargetClassInvocation = logTargetClassInvocation;
   }

   protected String createInvocationTraceName(MethodInvocation invocation) {
      StringBuilder sb = new StringBuilder(this.getPrefix());
      Method method = invocation.getMethod();
      Class clazz = method.getDeclaringClass();
      if (this.logTargetClassInvocation && clazz.isInstance(invocation.getThis())) {
         clazz = invocation.getThis().getClass();
      }

      sb.append(clazz.getName());
      sb.append('.').append(method.getName());
      sb.append(this.getSuffix());
      return sb.toString();
   }
}
