package weblogic.rmi.internal;

import java.rmi.RemoteException;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.InboundRequest;

class ClientInfoInterceptor extends ServerReferenceInterceptor {
   static ServerReferenceInterceptor INSTANCE = new ClientInfoInterceptor();

   public void preInvoke(ServerReference serverRef, InboundRequest request, RuntimeMethodDescriptor descriptor) throws RemoteException {
      ServerHelper.setClientInfo(request.getEndPoint(), request.getServerChannel());
   }

   boolean isUnconditional() {
      return true;
   }

   void postInvokeUnconditional(ServerReference serverRef, InboundRequest request, RuntimeMethodDescriptor md) {
      ServerHelper.setClientInfo((EndPoint)null, (ServerChannel)null);
   }

   private ClientInfoInterceptor() {
   }
}
