package weblogic.iiop;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.Socket;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.socket.MuxableSocket;
import weblogic.socket.SocketLogger;
import weblogic.utils.io.Chunk;

public class ProtocolHandlerIIOP implements ProtocolHandler {
   protected static final String PROTOCOL_NAME = "GIOP";
   private static final ProtocolHandler theOne = new ProtocolHandlerIIOP();
   public static final Protocol PROTOCOL_IIOP = ProtocolManager.createProtocol((byte)4, "iiop", "iiop", false, getProtocolHandler());

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   public ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerIIOP.ChannelInitializer.CHANNEL;
   }

   protected ProtocolHandlerIIOP() {
   }

   public int getHeaderLength() {
      return "GIOP".length();
   }

   public int getPriority() {
      return 0;
   }

   public Protocol getProtocol() {
      return PROTOCOL_IIOP;
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel nc) throws IOException {
      if (!MuxableSocketIIOP.isEnabled()) {
         SocketLogger.logConnectionRejectedProtocol(nc.getChannelName(), nc.getConfiguredProtocol());
         throw new ProtocolException("IIOP is disabled");
      } else {
         MuxableSocket ms = new MuxableSocketIIOP(head, s, nc);
         return ms;
      }
   }

   public boolean claimSocket(Chunk head) {
      return this.claimSocket(head, "GIOP");
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

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = ServerChannelImpl.createDefaultServerChannel(ProtocolHandlerIIOP.PROTOCOL_IIOP);
      }
   }
}
