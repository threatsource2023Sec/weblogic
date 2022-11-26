package weblogic.rmi.internal;

import java.rmi.RemoteException;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.InboundRequest;

class CertificateValidationInterceptor extends ServerReferenceInterceptor {
   static ServerReferenceInterceptor INSTANCE = new CertificateValidationInterceptor();

   private CertificateValidationInterceptor() {
   }

   public void preInvoke(ServerReference serverRef, InboundRequest request, RuntimeMethodDescriptor descriptor) throws RemoteException {
      RMIEnvironment.getEnvironment().certificateValidate(request, serverRef.getObjectID());
   }
}
