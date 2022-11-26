package weblogic.rmi.internal;

import java.lang.reflect.Method;
import weblogic.rmi.cluster.RetryHandler;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;

public class ClusteredFutureResultRetryHelper {
   private final RuntimeMethodDescriptor md;
   private final Method method;
   private final Object[] params;
   private final RetryHandler retryHandler;
   private Integer forwardCount;

   public ClusteredFutureResultRetryHelper(RuntimeMethodDescriptor md, Method method, Object[] params, RetryHandler retryHandler, Integer forwardCount) {
      this.md = md;
      this.method = method;
      this.params = params;
      this.retryHandler = retryHandler;
      this.forwardCount = forwardCount;
   }

   public void setForwardCount(int count) {
      this.forwardCount = count;
   }

   public int getForwardCount() {
      return this.forwardCount;
   }

   public RuntimeMethodDescriptor getMethodDescriptor() {
      return this.md;
   }

   public Method getMethod() {
      return this.method;
   }

   public RetryHandler getRetryHandler() {
      return this.retryHandler;
   }

   public Object[] getParams() {
      return this.params;
   }
}
