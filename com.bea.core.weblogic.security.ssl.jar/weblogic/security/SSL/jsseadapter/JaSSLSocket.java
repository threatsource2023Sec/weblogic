package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.net.InetAddress;

final class JaSSLSocket extends JaAbstractSSLSocket {
   JaSSLSocket(JaSSLContext jaSSLContext) throws IOException {
      super(jaSSLContext);
   }

   JaSSLSocket(JaSSLContext jaSSLContext, JaSSLParameters sslParameters, String host, int port) throws IOException {
      super(jaSSLContext, host, port);
      this.init(sslParameters);
   }

   JaSSLSocket(JaSSLContext jaSSLContext, JaSSLParameters sslParameters, InetAddress address, int port) throws IOException {
      super(jaSSLContext, address, port);
      this.init(sslParameters);
   }

   JaSSLSocket(JaSSLContext jaSSLContext, JaSSLParameters sslParameters, String host, int port, InetAddress clientAddress, int clientPort) throws IOException {
      super(jaSSLContext, host, port, clientAddress, clientPort);
      this.init(sslParameters);
   }

   JaSSLSocket(JaSSLContext jaSSLContext, JaSSLParameters sslParameters, InetAddress address, int port, InetAddress clientAddress, int clientPort) throws IOException {
      super(jaSSLContext, address, port, clientAddress, clientPort);
      this.init(sslParameters);
   }
}
