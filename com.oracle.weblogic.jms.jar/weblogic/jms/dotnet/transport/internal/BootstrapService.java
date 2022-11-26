package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.jms.dotnet.transport.ServiceTwoWay;
import weblogic.jms.dotnet.transport.TransportError;

class BootstrapService implements ServiceTwoWay {
   public void onPeerGone(TransportError te) {
   }

   public void onShutdown() {
   }

   public void onUnregister() {
   }

   public void invoke(ReceivedTwoWay rr) {
      try {
         BootstrapRequest bsr = (BootstrapRequest)rr.getRequest();
         Class c = Class.forName(bsr.getBootstrapServiceClassName());
         ServiceTwoWay instance = (ServiceTwoWay)c.newInstance();
         ((TransportImpl)rr.getTransport()).startHeartbeatService(bsr);
         instance.invoke(rr);
      } catch (Throwable var5) {
         rr.send(new TransportError(var5));
      }

   }
}
