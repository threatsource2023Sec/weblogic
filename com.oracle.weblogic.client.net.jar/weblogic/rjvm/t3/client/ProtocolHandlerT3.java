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

public class ProtocolHandlerT3 implements ProtocolHandler {
   private static final String PROTOCOL_NAME = "t3";
   private static final ProtocolHandler theOne = new ProtocolHandlerT3();
   public static final Protocol PROTOCOL_T3 = ProtocolManager.createProtocol((byte)0, "t3", "t3", false, getProtocolHandler());

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   public ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerT3.ChannelInitializer.CHANNEL;
   }

   protected ProtocolHandlerT3() {
   }

   public int getHeaderLength() {
      return "t3".length() + 1;
   }

   public int getPriority() {
      return 0;
   }

   public Protocol getProtocol() {
      return PROTOCOL_T3;
   }

   public boolean claimSocket(Chunk head) {
      return this.claimSocket(head, "t3");
   }

   boolean claimSocket(Chunk head, String protocol) {
      int protocolLen = protocol.length();
      if (head.end < protocolLen + 1) {
         return false;
      } else {
         byte[] buf = head.buf;

         for(int i = 0; i < protocolLen; ++i) {
            if (buf[i] != protocol.charAt(i)) {
               return false;
            }
         }

         if (buf[protocolLen] != 32) {
            return false;
         } else {
            return true;
         }
      }
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel nc) throws IOException {
      throw new UnsupportedOperationException("This method is not supported on the weblogic client side");
   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = BasicServerChannelImpl.createDefaultServerChannel(ProtocolHandlerT3.PROTOCOL_T3);
      }
   }
}
