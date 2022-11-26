package com.bea.core.repackaged.springframework.remoting.support;

import java.lang.reflect.InvocationTargetException;

public abstract class RemoteInvocationBasedExporter extends RemoteExporter {
   private RemoteInvocationExecutor remoteInvocationExecutor = new DefaultRemoteInvocationExecutor();

   public void setRemoteInvocationExecutor(RemoteInvocationExecutor remoteInvocationExecutor) {
      this.remoteInvocationExecutor = remoteInvocationExecutor;
   }

   public RemoteInvocationExecutor getRemoteInvocationExecutor() {
      return this.remoteInvocationExecutor;
   }

   protected Object invoke(RemoteInvocation invocation, Object targetObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      if (this.logger.isTraceEnabled()) {
         this.logger.trace("Executing " + invocation);
      }

      try {
         return this.getRemoteInvocationExecutor().invoke(invocation, targetObject);
      } catch (NoSuchMethodException var4) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Could not find target method for " + invocation, var4);
         }

         throw var4;
      } catch (IllegalAccessException var5) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Could not access target method for " + invocation, var5);
         }

         throw var5;
      } catch (InvocationTargetException var6) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Target method failed for " + invocation, var6.getTargetException());
         }

         throw var6;
      }
   }

   protected RemoteInvocationResult invokeAndCreateResult(RemoteInvocation invocation, Object targetObject) {
      try {
         Object value = this.invoke(invocation, targetObject);
         return new RemoteInvocationResult(value);
      } catch (Throwable var4) {
         return new RemoteInvocationResult(var4);
      }
   }
}
