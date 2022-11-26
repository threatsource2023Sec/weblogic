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

public class ProtocolHandlerClusterBroadcast implements ProtocolHandler {
   private static final String PROTOCOL_NAME = "CLUSTER-BROADCAST";
   private static final ProtocolHandler theOne = new ProtocolHandlerClusterBroadcast();
   public static final Protocol PROTOCOL_CLUSTER = ProtocolManager.createProtocol((byte)12, "CLUSTER-BROADCAST", "CLUSTER-BROADCAST", false, getProtocolHandler());
   private static final boolean DEBUG;

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   public ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerClusterBroadcast.ChannelInitializer.CHANNEL;
   }

   public int getHeaderLength() {
      return "CLUSTER-BROADCAST".length();
   }

   public int getPriority() {
      return 1;
   }

   public Protocol getProtocol() {
      return PROTOCOL_CLUSTER;
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel nc) throws IOException {
      if (!MuxableSocketClusterBroadcast.isEnabled()) {
         SocketLogger.logConnectionRejectedProtocol(nc.getChannelName(), nc.getConfiguredProtocol());
         throw new ProtocolException("unicast cluster messaging is disabled");
      } else {
         MuxableSocket ms = new MuxableSocketClusterBroadcast(head, s, nc);
         return ms;
      }
   }

   public boolean claimSocket(Chunk head) {
      return this.claimSocket(head, "CLUSTER-BROADCAST");
   }

   protected boolean claimSocket(Chunk head, String protocol) {
      int protocolLen = protocol.length();
      if (head.end < protocolLen) {
         return false;
      } else {
         byte[] buf = head.buf;

         for(int i = 0; i < protocolLen; ++i) {
            if (buf[i] != protocol.charAt(i)) {
               return false;
            }
         }

         return true;
      }
   }

   protected void debug(String s) {
      LogService logger = Environment.getLogService();
      if (logger != null) {
         logger.debug("[UnicastProtocolHandler] " + s);
      }

   }

   static {
      DEBUG = Environment.DEBUG;
   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = ServerChannelImpl.createDefaultServerChannel(ProtocolHandlerClusterBroadcast.PROTOCOL_CLUSTER);
      }
   }
}
