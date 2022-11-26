package weblogic.jms.dotnet.transport.t3client;

import weblogic.jms.dotnet.transport.ReceivedOneWay;
import weblogic.jms.dotnet.transport.ServiceOneWay;
import weblogic.jms.dotnet.transport.TransportError;

class ShutdownListener implements ServiceOneWay {
   private final T3Connection t3conn;

   ShutdownListener(T3Connection t3conn) {
      this.t3conn = t3conn;
   }

   public void invoke(ReceivedOneWay row) {
   }

   public void onPeerGone(TransportError te) {
      this.t3conn.close();
   }

   public void onShutdown() {
      this.t3conn.close();
   }

   public void onUnregister() {
      this.t3conn.close();
   }
}
