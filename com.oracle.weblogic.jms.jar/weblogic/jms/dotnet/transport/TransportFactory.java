package weblogic.jms.dotnet.transport;

import weblogic.jms.dotnet.transport.internal.TransportImpl;

public class TransportFactory {
   private static ServiceOneWay listener;
   private static final TransportFactoryLock lock = new TransportFactoryLock();

   public static void registerTransportListener(ServiceOneWay service) {
      synchronized(lock) {
         listener = service;
      }
   }

   public static Transport createTransport(TransportPluginSPI plugin, TransportThreadPool ttp) {
      return new TransportImpl(plugin, ttp);
   }

   public static void announceTransport(Transport transport) {
      ServiceOneWay localListener;
      synchronized(lock) {
         localListener = listener;
      }

      if (localListener != null) {
         localListener.invoke(new TransportAnnouncement(transport));
      }
   }
}
