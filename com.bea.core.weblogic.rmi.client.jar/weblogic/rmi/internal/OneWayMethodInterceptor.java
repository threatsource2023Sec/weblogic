package weblogic.rmi.internal;

import java.io.IOException;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.InboundRequest;

class OneWayMethodInterceptor extends ServerReferenceInterceptor {
   static final ServerReferenceInterceptor INSTANCE = new OneWayMethodInterceptor();

   boolean isUnconditional() {
      return true;
   }

   void postInvokeUnconditional(ServerReference serverRef, InboundRequest request, RuntimeMethodDescriptor methodDescriptor) {
      if (!methodDescriptor.getImplRespondsToClient()) {
         try {
            request.close();
         } catch (IOException var5) {
         }
      }

   }

   private OneWayMethodInterceptor() {
   }
}
