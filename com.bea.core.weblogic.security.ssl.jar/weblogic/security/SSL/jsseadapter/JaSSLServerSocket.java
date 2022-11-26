package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocket;

final class JaSSLServerSocket extends SSLServerSocket {
   private final JaSSLContext jaSSLContext;
   private final JaSSLParameters sslParameters;

   public String[] getEnabledCipherSuites() {
      return JaCipherSuiteNameMap.fromJsse(this.sslParameters.getEnabledCipherSuites());
   }

   public void setEnabledCipherSuites(String[] suites) {
      String[] jsseSuites = JaCipherSuiteNameMap.toJsse(suites);
      SSLContext sslCtx = this.jaSSLContext.getSSLContext();
      SSLEngine sslEngine = sslCtx.createSSLEngine();
      sslEngine.setEnabledCipherSuites(jsseSuites);
      this.sslParameters.setEnabledCipherSuites(jsseSuites);
   }

   public String[] getSupportedCipherSuites() {
      return this.jaSSLContext.getSupportedCipherSuites();
   }

   public String[] getSupportedProtocols() {
      return this.jaSSLContext.getSupportedProtocols();
   }

   public String[] getEnabledProtocols() {
      return this.sslParameters.getEnabledProtocols();
   }

   public void setEnabledProtocols(String[] protocols) {
      SSLContext sslCtx = this.jaSSLContext.getSSLContext();
      SSLEngine sslEngine = sslCtx.createSSLEngine();
      sslEngine.setEnabledProtocols(protocols);
      this.sslParameters.setEnabledProtocols(protocols);
   }

   public void setNeedClientAuth(boolean b) {
      this.sslParameters.setNeedClientAuth(b);
   }

   public boolean getNeedClientAuth() {
      return this.sslParameters.getNeedClientAuth();
   }

   public void setWantClientAuth(boolean b) {
      this.sslParameters.setWantClientAuth(b);
   }

   public boolean getWantClientAuth() {
      return this.sslParameters.getWantClientAuth();
   }

   public void setUseClientMode(boolean b) {
      this.sslParameters.setUseClientMode(b);
   }

   public boolean getUseClientMode() {
      return this.sslParameters.getUseClientMode();
   }

   public void setEnableSessionCreation(boolean b) {
      this.sslParameters.setEnableSessionCreation(b);
   }

   public boolean getEnableSessionCreation() {
      return this.sslParameters.getEnableSessionCreation();
   }

   public Socket accept() throws IOException {
      JaSSLSocket socket = null;
      boolean success = false;

      try {
         socket = new JaSSLSocket(this.jaSSLContext);
         this.implAccept(socket);
         socket.init(this.sslParameters);
         success = true;
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLSERVERSOCKET, "Accepted connection from client: {0}:{1}", socket.getInetAddress().getHostAddress(), socket.getPort());
         }
      } finally {
         if (null != socket && !success) {
            socket.close();
            if (JaLogger.isLoggable(Level.FINE)) {
               JaLogger.log(Level.FINE, JaLogger.Component.SSLSERVERSOCKET, "Closed socket due to failure during accept. Host={0}, Port={1}", socket.getInetAddress().getHostAddress(), socket.getPort());
            }
         }

      }

      return socket;
   }

   public void close() throws IOException {
      super.close();
   }

   public boolean isClosed() {
      return super.isClosed();
   }

   public String toString() {
      return super.toString();
   }

   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   JaSSLServerSocket(JaSSLContext jaSSLContext, int port, int backlog, InetAddress bindAddress) throws IOException {
      super(port, backlog, bindAddress);
      if (null == jaSSLContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext.");
      } else {
         this.jaSSLContext = jaSSLContext;
         this.sslParameters = new JaSSLParameters(jaSSLContext.getSSLContext());
         this.sslParameters.setUseClientMode(false);
      }
   }
}
