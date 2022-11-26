package weblogic.rmi.internal;

import java.rmi.RemoteException;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.InboundRequest;

public abstract class ServerReferenceInterceptor {
   void preInvoke(ServerReference serverRef, InboundRequest request, RuntimeMethodDescriptor descriptor) throws RemoteException {
   }

   boolean isUnconditional() {
      return true;
   }

   void postInvokeUnconditional(ServerReference serverRef, InboundRequest request, RuntimeMethodDescriptor md) {
   }

   void postInvoke(ServerReference serverRef) {
   }
}
