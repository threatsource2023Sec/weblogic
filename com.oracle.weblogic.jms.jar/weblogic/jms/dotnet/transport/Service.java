package weblogic.jms.dotnet.transport;

public interface Service {
   void onPeerGone(TransportError var1);

   void onShutdown();

   void onUnregister();
}
