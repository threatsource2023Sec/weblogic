package weblogic.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocketFactory;
import weblogic.platform.JDK;
import weblogic.security.SSL.SSLEngineFactory;

public class JSSESocketFactory extends SSLSocketFactory {
   private static final boolean JDK7_OR_HIGHER = JDK.getJDK().getMajorVersion() >= 1 && JDK.getJDK().getMinorVersion() >= 7;
   private static boolean isMuxerReady;
   private final SSLEngineFactory sslEngineFactory;

   public static JSSESocketFactory getSSLSocketFactory(SSLEngineFactory sslEngineFactory) {
      return new JSSESocketFactory(sslEngineFactory);
   }

   private JSSESocketFactory(SSLEngineFactory sslEngineFactory) {
      this.sslEngineFactory = sslEngineFactory;
   }

   public Socket createSocket() throws IOException {
      JSSESocket jsseSock = new JSSESocket(new Socket(), this);
      jsseSock.setAutoClose(true);
      return jsseSock;
   }

   public Socket createSocket(Socket connectedSocket, String host, int port, boolean autoClose) throws IOException {
      JSSEFilterImpl filter = this.getJSSEFilterImpl(connectedSocket, host, port);
      JSSESocket jsseSock = new JSSESocket(connectedSocket, filter);
      jsseSock.setAutoClose(autoClose);
      return jsseSock;
   }

   public String[] getDefaultCipherSuites() {
      return this.sslEngineFactory.getDefaultCipherSuites();
   }

   public String[] getSupportedCipherSuites() {
      return this.sslEngineFactory.getSupportedCipherSuites();
   }

   public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
      Socket connectedSocket = this.getConnectedSocket(InetAddress.getByName(host), port, 0);
      return this.createSocket(connectedSocket, host, port, true);
   }

   public Socket createSocket(InetAddress host, int port) throws IOException {
      Socket connectedSocket = this.getConnectedSocket(host, port, 0);
      return this.createSocket(connectedSocket, getHostNameString(host), port, true);
   }

   public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
      Socket connectedSocket = this.getConnectedSocket(InetAddress.getByName(host), port, localHost, localPort, 0);
      return this.createSocket(connectedSocket, host, port, true);
   }

   public Socket createSocket(InetAddress host, int port, InetAddress localHost, int localPort) throws IOException {
      Socket connectedSocket = this.getConnectedSocket(host, port, localHost, localPort, 0);
      return this.createSocket(connectedSocket, getHostNameString(host), port, true);
   }

   JSSEFilterImpl getJSSEFilterImpl(Socket connectedSocket) throws IOException {
      String host = getHostNameString(connectedSocket.getRemoteSocketAddress());
      if (host == null) {
         host = connectedSocket.getInetAddress().getHostAddress();
      }

      return this.getJSSEFilterImpl(connectedSocket, host, connectedSocket.getPort());
   }

   JSSEFilterImpl getJSSEFilterImpl(Socket connectedSocket, String host, int port) throws IOException {
      SSLEngine sslEngine = this.getSSLEngine(host, port);
      return new JSSEFilterImpl(connectedSocket, sslEngine, true);
   }

   private Socket getConnectedSocket(InetAddress inetAddr, int port, int connectTimeoutMillis) throws IOException {
      Socket sock = null;
      if (!isMuxerReady) {
         isMuxerReady = SocketMuxer.isAvailable();
      }

      if (isMuxerReady) {
         sock = SocketMuxer.getMuxer().newSSLClientSocket(inetAddr, port, connectTimeoutMillis);
         if (sock == null) {
            sock = SocketMuxer.getMuxer().newSocket(inetAddr, port, connectTimeoutMillis);
         }
      } else {
         sock = new Socket();
         sock.setTcpNoDelay(true);
         sock.connect(new InetSocketAddress(inetAddr, port), connectTimeoutMillis);
      }

      return sock;
   }

   private Socket getConnectedSocket(InetAddress inetAddr, int port, InetAddress localAddr, int localPort, int connectTimeoutMillis) throws IOException {
      Socket sock = null;
      if (!isMuxerReady) {
         isMuxerReady = SocketMuxer.isAvailable();
      }

      if (isMuxerReady) {
         sock = SocketMuxer.getMuxer().newSSLClientSocket(inetAddr, port, localAddr, localPort, connectTimeoutMillis);
         if (sock == null) {
            sock = SocketMuxer.getMuxer().newSocket(inetAddr, port, localAddr, localPort, connectTimeoutMillis);
         }
      } else {
         sock = new Socket();
         sock.setTcpNoDelay(true);
         sock.bind(new InetSocketAddress(localAddr, localPort));
         sock.connect(new InetSocketAddress(inetAddr, port), connectTimeoutMillis);
      }

      return sock;
   }

   private SSLEngine getSSLEngine(String host, int port) throws IOException {
      SSLEngine sslEngine = this.sslEngineFactory.createSSLEngine(host, port);
      sslEngine.setUseClientMode(true);
      return sslEngine;
   }

   private static String getHostNameString(SocketAddress socketAddress) {
      if (!(socketAddress instanceof InetSocketAddress)) {
         return null;
      } else {
         return JDK7_OR_HIGHER ? JSSESocketFactory.JDK7HostNameResolver.getHostString((InetSocketAddress)socketAddress) : ((InetSocketAddress)socketAddress).getAddress().getHostAddress();
      }
   }

   private static String getHostNameString(InetAddress inetAddr) {
      return JDK7_OR_HIGHER ? JSSESocketFactory.JDK7HostNameResolver.getHostString(inetAddr) : inetAddr.getHostAddress();
   }

   private static final class JDK7HostNameResolver {
      public static String getHostString(InetSocketAddress addr) {
         return addr.getHostString();
      }

      public static String getHostString(InetAddress addr) {
         return (new InetSocketAddress(addr, 0)).getHostString();
      }
   }
}
