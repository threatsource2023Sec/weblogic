package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocketFactory;

final class JaSSLSocketFactory extends SSLSocketFactory {
   private final JaSSLContext jaSSLContext;

   public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
      JaSSLParameters sslParameters = new JaSSLParameters(this.jaSSLContext.getSSLContext());
      sslParameters.setUseClientMode(true);
      return new JaSSLSocket(this.jaSSLContext, sslParameters, host, port);
   }

   public Socket createSocket(InetAddress address, int port) throws IOException {
      JaSSLParameters sslParameters = new JaSSLParameters(this.jaSSLContext.getSSLContext());
      sslParameters.setUseClientMode(true);
      return new JaSSLSocket(this.jaSSLContext, sslParameters, address, port);
   }

   public Socket createSocket(String host, int port, InetAddress clientAddress, int clientPort) throws IOException, UnknownHostException {
      JaSSLParameters sslParameters = new JaSSLParameters(this.jaSSLContext.getSSLContext());
      sslParameters.setUseClientMode(true);
      return new JaSSLSocket(this.jaSSLContext, sslParameters, host, port, clientAddress, clientPort);
   }

   public Socket createSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort) throws IOException {
      JaSSLParameters sslParameters = new JaSSLParameters(this.jaSSLContext.getSSLContext());
      sslParameters.setUseClientMode(true);
      return new JaSSLSocket(this.jaSSLContext, sslParameters, address, port, clientAddress, clientPort);
   }

   public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
      JaSSLParameters sslParameters = new JaSSLParameters(this.jaSSLContext.getSSLContext());
      sslParameters.setUseClientMode(true);
      return new JaLayeredSSLSocket(socket, this.jaSSLContext, sslParameters, autoClose);
   }

   public String[] getDefaultCipherSuites() {
      return this.jaSSLContext.getDefaultCipherSuites();
   }

   public String[] getSupportedCipherSuites() {
      return this.jaSSLContext.getSupportedCipherSuites();
   }

   JaSSLSocketFactory(JaSSLContext sslContext) {
      if (null == sslContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext instance.");
      } else {
         this.jaSSLContext = sslContext;
      }
   }
}
