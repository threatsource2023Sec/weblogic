package com.bea.core.repackaged.springframework.remoting.support;

import com.bea.core.repackaged.springframework.util.Assert;

public abstract class RemoteAccessor extends RemotingSupport {
   private Class serviceInterface;

   public void setServiceInterface(Class serviceInterface) {
      Assert.notNull(serviceInterface, (String)"'serviceInterface' must not be null");
      Assert.isTrue(serviceInterface.isInterface(), "'serviceInterface' must be an interface");
      this.serviceInterface = serviceInterface;
   }

   public Class getServiceInterface() {
      return this.serviceInterface;
   }
}
