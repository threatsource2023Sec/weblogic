package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.nio.channels.SocketChannel;

final class JaNioSSLSocket extends JaAbstractLayeredSSLSocket {
   private final SocketChannel socketChannel;

   public SocketChannel getChannel() {
      return this.socketChannel;
   }

   JaNioSSLSocket(SocketChannel socketChannel, JaSSLContext jaSSLContext, JaSSLParameters sslParameters, boolean autoClose) throws IOException {
      super(null == socketChannel ? null : socketChannel.socket(), jaSSLContext, autoClose);
      if (null == socketChannel) {
         throw new IllegalArgumentException("Expected non-null SocketChannel.");
      } else {
         this.socketChannel = socketChannel;
         this.init(socketChannel, sslParameters);
      }
   }
}
