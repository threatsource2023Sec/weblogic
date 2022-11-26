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

public class ProtocolHandlerHTTPS extends ProtocolHandlerHTTP {
   private static final ProtocolHandler theOne = new ProtocolHandlerHTTPS();
   public static final Protocol PROTOCOL_HTTPS = ProtocolManager.createProtocol((byte)3, "https", "https", true, getProtocolHandler());

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   public Protocol getProtocol() {
      return PROTOCOL_HTTPS;
   }

   public final ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerHTTPS.ChannelInitializer.CHANNEL;
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      throw new UnsupportedOperationException("This method is not supported on the weblogic client side");
   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = BasicServerChannelImpl.createDefaultServerChannel(ProtocolHandlerHTTPS.PROTOCOL_HTTPS);
      }
   }
}
