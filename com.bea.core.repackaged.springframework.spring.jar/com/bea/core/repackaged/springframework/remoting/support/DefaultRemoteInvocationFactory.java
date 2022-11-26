package com.bea.core.repackaged.springframework.remoting.support;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;

public class DefaultRemoteInvocationFactory implements RemoteInvocationFactory {
   public RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
      return new RemoteInvocation(methodInvocation);
   }
}
