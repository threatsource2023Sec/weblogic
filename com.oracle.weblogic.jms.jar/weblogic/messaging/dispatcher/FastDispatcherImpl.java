package weblogic.messaging.dispatcher;

import java.io.IOException;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.SmartStubInfo;
import weblogic.rmi.internal.Stub;

public final class FastDispatcherImpl extends DispatcherImpl implements SmartStubInfo {
   private final DispatcherObjectHandler objectHandler;

   FastDispatcherImpl(DispatcherImpl delegate) {
      super(delegate);
      this.objectHandler = DispatcherObjectHandler.load(this.objectHandlerClassName);
   }

   public DispatcherObjectHandler getObjectHandler() {
      return this.objectHandler;
   }

   public Object getSmartStub(Object stub) {
      RemoteReference ror = ((Stub)stub).getRemoteRef();
      DispatcherProxy dispatcherProxy = new DispatcherProxy(45, ror.getHostID(), (DispatcherPartition4rmic)this.dispatcherPartition4rmic, this.objectHandlerClassName);

      try {
         DispatcherUtils.assignPartitionAware(dispatcherProxy, this.getPartitionId(), this.getPartitionName(), this.getConnectionPartitionName());
         return dispatcherProxy;
      } catch (IOException var6) {
         RuntimeException rte = new RuntimeException(var6);
         throw rte;
      }
   }
}
