package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.text.MessageFormat;
import javax.net.ssl.SSLSocketFactory;

final class JaNioSSLSocketFactory extends SSLSocketFactory {
   private final JaSSLContext jaSSLContext;

   public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
      SocketChannel sc = SocketChannel.open();
      sc.configureBlocking(true);
      sc.connect(new InetSocketAddress(host, port));
      JaSSLParameters sslParameters = new JaSSLParameters(this.jaSSLContext.getSSLContext());
      sslParameters.setUseClientMode(true);
      return new JaNioSSLSocket(sc, this.jaSSLContext, sslParameters, true);
   }

   public Socket createSocket(InetAddress address, int port) throws IOException {
      SocketChannel sc = SocketChannel.open();
      sc.configureBlocking(true);
      sc.connect(new InetSocketAddress(address, port));
      JaSSLParameters sslParameters = new JaSSLParameters(this.jaSSLContext.getSSLContext());
      sslParameters.setUseClientMode(true);
      return new JaNioSSLSocket(sc, this.jaSSLContext, sslParameters, true);
   }

   public Socket createSocket(String host, int port, InetAddress clientAddress, int clientPort) throws IOException, UnknownHostException {
      SocketChannel sc = SocketChannel.open();
      sc.configureBlocking(true);
      sc.socket().bind(new InetSocketAddress(clientAddress, clientPort));
      sc.connect(new InetSocketAddress(host, port));
      JaSSLParameters sslParameters = new JaSSLParameters(this.jaSSLContext.getSSLContext());
      sslParameters.setUseClientMode(true);
      return new JaNioSSLSocket(sc, this.jaSSLContext, sslParameters, true);
   }

   public Socket createSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort) throws IOException {
      SocketChannel sc = SocketChannel.open();
      sc.configureBlocking(true);
      sc.socket().bind(new InetSocketAddress(clientAddress, clientPort));
      sc.connect(new InetSocketAddress(address.getHostAddress(), port));
      JaSSLParameters sslParameters = new JaSSLParameters(this.jaSSLContext.getSSLContext());
      sslParameters.setUseClientMode(true);
      return new JaNioSSLSocket(sc, this.jaSSLContext, sslParameters, true);
   }

   public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
      SocketChannel sc = socket.getChannel();
      if (null == sc) {
         String msg = MessageFormat.format("The Socket argument is not created by SocketChannel.open() :{0}", socket.toString());
         throw new IllegalArgumentException(msg);
      } else {
         sc.configureBlocking(true);
         JaSSLParameters sslParameters = new JaSSLParameters(this.jaSSLContext.getSSLContext());
         sslParameters.setUseClientMode(true);
         return new JaNioSSLSocket(sc, this.jaSSLContext, sslParameters, autoClose);
      }
   }

   public String[] getDefaultCipherSuites() {
      return this.jaSSLContext.getDefaultCipherSuites();
   }

   public String[] getSupportedCipherSuites() {
      return this.jaSSLContext.getSupportedCipherSuites();
   }

   JaNioSSLSocketFactory(JaSSLContext sslContext) {
      if (null == sslContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext instance.");
      } else {
         this.jaSSLContext = sslContext;
      }
   }
}
