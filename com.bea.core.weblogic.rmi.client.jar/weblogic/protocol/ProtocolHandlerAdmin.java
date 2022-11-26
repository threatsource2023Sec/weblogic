package weblogic.protocol;

import java.io.IOException;
import java.net.Socket;
import weblogic.kernel.KernelStatus;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.socket.MuxableSocket;
import weblogic.utils.io.Chunk;

public class ProtocolHandlerAdmin implements ProtocolHandler {
   private static final ProtocolHandler theOne = new ProtocolHandlerAdmin(ProtocolManager.getDefaultAdminProtocol().getHandler());
   public static final int PROTOCOL_ADMIN_FLAG = 64;
   public static final Protocol PROTOCOL_ADMIN = ProtocolManager.createProtocol((byte)6, "admin", KernelStatus.isServer() ? "admin" : ProtocolManager.getDefaultAdminProtocol().getAsURLPrefix(), false, getProtocolHandler());
   private final ProtocolHandler delegate;

   public static ProtocolHandler getProtocolHandler() {
      return theOne;
   }

   private ProtocolHandlerAdmin(ProtocolHandler delegate) {
      this.delegate = delegate;
   }

   public final ServerChannel getDefaultServerChannel() {
      return ProtocolHandlerAdmin.ChannelInitializer.CHANNEL;
   }

   public boolean claimSocket(Chunk head) {
      return this.delegate.claimSocket(head);
   }

   public MuxableSocket createSocket(Chunk head, Socket s, ServerChannel networkChannel) throws IOException {
      return this.delegate.createSocket(head, s, networkChannel);
   }

   public Protocol getProtocol() {
      return ProtocolManager.getDefaultAdminProtocol();
   }

   public int getHeaderLength() {
      return this.delegate.getHeaderLength();
   }

   public int getPriority() {
      return this.delegate.getPriority();
   }

   private static final class ChannelInitializer {
      private static final ServerChannel CHANNEL;

      static {
         CHANNEL = RJVMEnvironment.getEnvironment().createDefaultChannel(ProtocolHandlerAdmin.PROTOCOL_ADMIN);
      }
   }
}
