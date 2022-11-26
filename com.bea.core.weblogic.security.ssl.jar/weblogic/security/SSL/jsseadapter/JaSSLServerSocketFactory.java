package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

final class JaSSLServerSocketFactory extends SSLServerSocketFactory {
   private final JaSSLContext jaSSLContext;
   private final boolean nio;

   JaSSLServerSocketFactory(JaSSLContext sslContext, boolean nio) {
      if (null == sslContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext instance.");
      } else {
         this.jaSSLContext = sslContext;
         this.nio = nio;
      }
   }

   boolean isNio() {
      return this.nio;
   }

   public ServerSocket createServerSocket() throws IOException {
      throw new UnsupportedOperationException("Unbound ServerSocket instances are not supported.");
   }

   public ServerSocket createServerSocket(int port) throws IOException {
      return this.createServerSocket(port, -1, (InetAddress)null);
   }

   public ServerSocket createServerSocket(int port, int backlog) throws IOException {
      return this.createServerSocket(port, backlog, (InetAddress)null);
   }

   public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException {
      Object ssi;
      if (this.isNio()) {
         ssi = new JaNioSSLServerSocket(this.jaSSLContext, port, backlog, ifAddress);
      } else {
         ssi = new JaSSLServerSocket(this.jaSSLContext, port, backlog, ifAddress);
      }

      return (ServerSocket)ssi;
   }

   public String[] getDefaultCipherSuites() {
      return this.jaSSLContext.getDefaultCipherSuites();
   }

   public String[] getSupportedCipherSuites() {
      return this.jaSSLContext.getSupportedCipherSuites();
   }
}
