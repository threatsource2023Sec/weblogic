package weblogic.rjvm.t3.client;

import java.io.IOException;
import java.net.Socket;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.server.channels.BasicServerChannelImpl;
import weblogic.socket.MuxableSocket;
import weblogic.utils.io.Chunk;

public class ProtocolHandlerHTTP implements ProtocolHandler {
   private static final ProtocolHandler theOne = new ProtocolHandlerHTTP();
   public static final Protocol PROTOCOL_HTTP = ProtocolManager.createProtocol((byte)1, "http", "http", false, getProtocolHandler());

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   public ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerHTTP.ChannelInitializer.CHANNEL;
   }

   public int getHeaderLength() {
      return 0;
   }

   public int getPriority() {
      return Integer.MAX_VALUE;
   }

   public Protocol getProtocol() {
      return PROTOCOL_HTTP;
   }

   public boolean claimSocket(Chunk head) {
      return true;
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      return null;
   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = BasicServerChannelImpl.createDefaultServerChannel(ProtocolHandlerHTTP.PROTOCOL_HTTP);
      }
   }
}
