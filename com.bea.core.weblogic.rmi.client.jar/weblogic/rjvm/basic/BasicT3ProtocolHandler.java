package weblogic.rjvm.basic;

import java.io.IOException;
import java.net.Socket;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.socket.MuxableSocket;
import weblogic.utils.io.Chunk;

public class BasicT3ProtocolHandler implements ProtocolHandler {
   private static final ProtocolHandler singleton = new BasicT3ProtocolHandler();
   private static final Protocol T3_PROTOCOL;
   private ServerChannel defaultChannel;

   public static ProtocolHandler getHandler() {
      return singleton;
   }

   public boolean claimSocket(Chunk c) {
      return false;
   }

   public MuxableSocket createSocket(Chunk c, Socket s, ServerChannel sc) throws IOException {
      return null;
   }

   public Protocol getProtocol() {
      return T3_PROTOCOL;
   }

   public ServerChannel getDefaultServerChannel() {
      if (this.defaultChannel == null) {
         this.defaultChannel = new BasicServerChannel(T3_PROTOCOL);
      }

      return this.defaultChannel;
   }

   public int getHeaderLength() {
      return 0;
   }

   public int getPriority() {
      return 0;
   }

   static {
      T3_PROTOCOL = ProtocolManager.createProtocol((byte)0, "t3", "t3", false, singleton);
   }
}
