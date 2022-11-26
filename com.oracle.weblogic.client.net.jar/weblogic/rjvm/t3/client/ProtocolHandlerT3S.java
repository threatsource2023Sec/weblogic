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

public class ProtocolHandlerT3S extends ProtocolHandlerT3 {
   private static final String PROTOCOL_NAME = "t3s";
   private static final ProtocolHandler theOne = new ProtocolHandlerT3S();
   public static final Protocol PROTOCOL_T3S = ProtocolManager.createProtocol((byte)2, "t3s", "t3s", true, getProtocolHandler());

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   private ProtocolHandlerT3S() {
   }

   public final ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerT3S.ChannelInitializer.CHANNEL;
   }

   public Protocol getProtocol() {
      return PROTOCOL_T3S;
   }

   public boolean claimSocket(Chunk head) {
      return this.claimSocket(head, "t3s");
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      throw new UnsupportedOperationException("This method is not supported on the weblogic client side");
   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = BasicServerChannelImpl.createDefaultServerChannel(ProtocolHandlerT3S.PROTOCOL_T3S);
      }
   }
}
