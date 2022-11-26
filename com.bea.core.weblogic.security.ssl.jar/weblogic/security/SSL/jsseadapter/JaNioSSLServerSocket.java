package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocket;

final class JaNioSSLServerSocket extends SSLServerSocket {
   private final JaSSLContext jaSSLContext;
   private final JaSSLParameters sslParameters;
   private final ServerSocketChannel serverSocketChannel;
   private final ServerSocket serverSocket;

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

   public void bind(SocketAddress endpoint) throws IOException {
      this.serverSocket.bind(endpoint);
   }

   public void bind(SocketAddress endpoint, int backlog) throws IOException {
      this.serverSocket.bind(endpoint, backlog);
   }

   public InetAddress getInetAddress() {
      return this.serverSocket.getInetAddress();
   }

   public int getLocalPort() {
      return this.serverSocket.getLocalPort();
   }

   public SocketAddress getLocalSocketAddress() {
      return this.serverSocket.getLocalSocketAddress();
   }

   public Socket accept() throws IOException {
      SocketChannel sc = this.serverSocketChannel.accept();
      if (null == sc) {
         String msg = "Expected blocking ServerSocketChannel; accept() returned null.";
         if (JaLogger.isLoggable(Level.FINE)) {
            JaLogger.log(Level.FINE, JaLogger.Component.NIOSSLSERVERSOCKET, "Expected blocking ServerSocketChannel; accept() returned null.");
         }

         throw new IllegalStateException("Expected blocking ServerSocketChannel; accept() returned null.");
      } else {
         Socket channelSocket = sc.socket();
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.NIOSSLSERVERSOCKET, "Accepted connection from client: {0}", channelSocket.getInetAddress().getHostAddress());
         }

         sc.configureBlocking(true);
         JaNioSSLSocket socket = null;

         try {
            socket = new JaNioSSLSocket(sc, this.jaSSLContext, new JaSSLParameters(this.sslParameters), true);
         } finally {
            if (null == socket) {
               channelSocket.close();
               if (JaLogger.isLoggable(Level.FINE)) {
                  JaLogger.log(Level.FINE, JaLogger.Component.NIOSSLSERVERSOCKET, "Closed channel socket since unable to construct JaNioSSLSocket. Host={0}, Port={1}", channelSocket.getInetAddress().getHostAddress(), channelSocket.getPort());
               }
            }

         }

         return socket;
      }
   }

   public void close() throws IOException {
      this.serverSocket.close();
   }

   public ServerSocketChannel getChannel() {
      return this.serverSocketChannel;
   }

   public boolean isBound() {
      return this.serverSocket.isBound();
   }

   public boolean isClosed() {
      return this.serverSocket.isClosed();
   }

   public synchronized void setSoTimeout(int timeout) throws SocketException {
      this.serverSocket.setSoTimeout(timeout);
   }

   public synchronized int getSoTimeout() throws IOException {
      return this.serverSocket.getSoTimeout();
   }

   public void setReuseAddress(boolean on) throws SocketException {
      this.serverSocket.setReuseAddress(on);
   }

   public boolean getReuseAddress() throws SocketException {
      return this.serverSocket.getReuseAddress();
   }

   public String toString() {
      return this.serverSocket.toString();
   }

   public synchronized void setReceiveBufferSize(int size) throws SocketException {
      this.serverSocket.setReceiveBufferSize(size);
   }

   public synchronized int getReceiveBufferSize() throws SocketException {
      return this.serverSocket.getReceiveBufferSize();
   }

   public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
      this.serverSocket.setPerformancePreferences(connectionTime, latency, bandwidth);
   }

   public int hashCode() {
      return this.serverSocket.hashCode();
   }

   public boolean equals(Object obj) {
      return this.serverSocket.equals(obj);
   }

   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   JaNioSSLServerSocket(JaSSLContext jaSSLContext, int port, int backlog, InetAddress bindAddress) throws IOException {
      if (null == jaSSLContext) {
         throw new IllegalArgumentException("Expected non-null JaSSLContext.");
      } else {
         this.jaSSLContext = jaSSLContext;
         SSLContext jsseSSLContext = jaSSLContext.getSSLContext();
         this.sslParameters = new JaSSLParameters(jsseSSLContext);
         this.sslParameters.setUseClientMode(false);
         this.serverSocketChannel = ServerSocketChannel.open();
         this.serverSocketChannel.configureBlocking(true);
         ServerSocket ss = this.serverSocketChannel.socket();
         InetSocketAddress isa;
         if (null != bindAddress) {
            isa = new InetSocketAddress(bindAddress, port);
         } else {
            isa = new InetSocketAddress(port);
         }

         if (backlog > 0) {
            ss.bind(isa, backlog);
         } else {
            ss.bind(isa);
         }

         this.serverSocket = ss;
      }
   }
}
