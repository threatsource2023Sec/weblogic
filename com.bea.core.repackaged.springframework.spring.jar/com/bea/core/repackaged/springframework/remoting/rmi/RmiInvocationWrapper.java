package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocation;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

class RmiInvocationWrapper implements RmiInvocationHandler {
   private final Object wrappedObject;
   private final RmiBasedExporter rmiExporter;

   public RmiInvocationWrapper(Object wrappedObject, RmiBasedExporter rmiExporter) {
      Assert.notNull(wrappedObject, "Object to wrap is required");
      Assert.notNull(rmiExporter, (String)"RMI exporter is required");
      this.wrappedObject = wrappedObject;
      this.rmiExporter = rmiExporter;
   }

   @Nullable
   public String getTargetInterfaceName() {
      Class ifc = this.rmiExporter.getServiceInterface();
      return ifc != null ? ifc.getName() : null;
   }

   @Nullable
   public Object invoke(RemoteInvocation invocation) throws RemoteException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      return this.rmiExporter.invoke(invocation, this.wrappedObject);
   }
}
