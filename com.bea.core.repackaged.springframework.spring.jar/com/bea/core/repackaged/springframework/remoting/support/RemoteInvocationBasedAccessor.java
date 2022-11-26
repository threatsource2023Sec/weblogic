package com.bea.core.repackaged.springframework.remoting.support;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;

public abstract class RemoteInvocationBasedAccessor extends UrlBasedRemoteAccessor {
   private RemoteInvocationFactory remoteInvocationFactory = new DefaultRemoteInvocationFactory();

   public void setRemoteInvocationFactory(RemoteInvocationFactory remoteInvocationFactory) {
      this.remoteInvocationFactory = (RemoteInvocationFactory)(remoteInvocationFactory != null ? remoteInvocationFactory : new DefaultRemoteInvocationFactory());
   }

   public RemoteInvocationFactory getRemoteInvocationFactory() {
      return this.remoteInvocationFactory;
   }

   protected RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
      return this.getRemoteInvocationFactory().createRemoteInvocation(methodInvocation);
   }

   protected Object recreateRemoteInvocationResult(RemoteInvocationResult result) throws Throwable {
      return result.recreate();
   }
}
