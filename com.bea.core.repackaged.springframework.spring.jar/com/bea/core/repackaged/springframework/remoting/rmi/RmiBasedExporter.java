package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocation;
import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocationBasedExporter;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;

public abstract class RmiBasedExporter extends RemoteInvocationBasedExporter {
   protected Remote getObjectToExport() {
      if (!(this.getService() instanceof Remote) || this.getServiceInterface() != null && !Remote.class.isAssignableFrom(this.getServiceInterface())) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("RMI service [" + this.getService() + "] is an RMI invoker");
         }

         return new RmiInvocationWrapper(this.getProxyForService(), this);
      } else {
         return (Remote)this.getService();
      }
   }

   protected Object invoke(RemoteInvocation invocation, Object targetObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      return super.invoke(invocation, targetObject);
   }
}
