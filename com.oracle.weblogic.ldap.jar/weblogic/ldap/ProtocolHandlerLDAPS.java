package weblogic.ldap;

import java.io.IOException;
import java.net.Socket;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.socket.MuxableSocket;
import weblogic.utils.io.Chunk;

final class ProtocolHandlerLDAPS extends ProtocolHandlerLDAP {
   private static ProtocolHandler theOne = new ProtocolHandlerLDAPS();
   private static final String PROTOCOL_NAME = "LDAPS";
   public static final Protocol PROTOCOL_LDAPS = ProtocolManager.createProtocol((byte)11, "ldaps", "ldaps", true, getProtocolHandler());

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   public final ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerLDAPS.ChannelInitializer.CHANNEL;
   }

   public Protocol getProtocol() {
      return PROTOCOL_LDAPS;
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      return new MuxableSocketLDAPS(head, s, networkChannel);
   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = ServerChannelImpl.createDefaultServerChannel(ProtocolHandlerLDAPS.PROTOCOL_LDAPS);
      }
   }
}
