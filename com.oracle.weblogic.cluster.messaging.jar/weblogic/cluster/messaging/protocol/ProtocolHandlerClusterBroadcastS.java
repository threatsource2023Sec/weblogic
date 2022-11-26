package weblogic.cluster.messaging.protocol;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.Socket;
import weblogic.cluster.messaging.internal.Environment;
import weblogic.cluster.messaging.internal.LogService;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.socket.MuxableSocket;
import weblogic.socket.SocketLogger;
import weblogic.utils.io.Chunk;

public final class ProtocolHandlerClusterBroadcastS extends ProtocolHandlerClusterBroadcast {
   static final String PROTOCOL_NAME = "CLUSTER-BROADCAST-SECURE";
   private static final ProtocolHandler theOne = new ProtocolHandlerClusterBroadcastS();
   public static final Protocol PROTOCOL_CLUSTER_SECURE = ProtocolManager.createProtocol((byte)13, "CLUSTER-BROADCAST-SECURE", "CLUSTER-BROADCAST-SECURE", true, getProtocolHandler());

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   public final ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerClusterBroadcastS.ChannelInitializer.CHANNEL;
   }

   public Protocol getProtocol() {
      return PROTOCOL_CLUSTER_SECURE;
   }

   public boolean claimSocket(Chunk head) {
      return this.claimSocket(head, "CLUSTER-BROADCAST-SECURE");
   }

   public int getHeaderLength() {
      return "CLUSTER-BROADCAST-SECURE".length();
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      if (!MuxableSocketClusterBroadcastS.isEnabled()) {
         SocketLogger.logConnectionRejectedProtocol(networkChannel.getChannelName(), networkChannel.getConfiguredProtocol());
         throw new ProtocolException("unicast cluster messaging is disabled");
      } else {
         return new MuxableSocketClusterBroadcastS(head, s, networkChannel);
      }
   }

   protected void debug(String s) {
      LogService logger = Environment.getLogService();
      if (logger != null) {
         logger.debug("[UnicastProtocolHandlerSecure] " + s);
      }

   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = ServerChannelImpl.createDefaultServerChannel(ProtocolHandlerClusterBroadcastS.PROTOCOL_CLUSTER_SECURE);
      }
   }
}
