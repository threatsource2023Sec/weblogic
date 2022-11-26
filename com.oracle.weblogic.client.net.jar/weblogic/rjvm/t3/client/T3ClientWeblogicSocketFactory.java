package weblogic.rjvm.t3.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import javax.net.SocketFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.protocol.ServerChannel;
import weblogic.socket.WeblogicSocketFactory;
import weblogic.socket.utils.ProxyUtils;

public class T3ClientWeblogicSocketFactory extends WeblogicSocketFactory {
   public static final String SSL_MINIMUM_PROTOCOL_VERSION_PROP = "weblogic.security.SSL.minimumProtocolVersion";
   public static final String SSL_PREFERRED_PROTOCOL_VERSION_PROP = "weblogic.security.SSL.preferredProtocolVersion";
   private static final String JDK_TLS_CLIENT_PROTOCOLS_PROP = "jdk.tls.client.protocols";
   private final SSLSocketFactory sslSocketFactory;
   private final ServerChannel channel;
   private String minProtocolVersion;
   private String protocolVersion;
   private final boolean jdkTlsClientProtocolsSpecified;
   private final boolean minProtocolVersionSpecified;
   private DebugLogger log = DebugLogger.getDebugLogger("DebugConnection");

   private static boolean contains(String[] array, String value) {
      String[] var2 = array;
      int var3 = array.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String elem = var2[var4];
         if (value.equals(elem)) {
            return true;
         }
      }

      return false;
   }

   public T3ClientWeblogicSocketFactory(SSLSocketFactory sslSocketFactory, ServerChannel channel) {
      this.sslSocketFactory = sslSocketFactory;
      this.channel = channel;
      this.minProtocolVersion = System.getProperty("weblogic.security.SSL.minimumProtocolVersion", "TLSv1");
      this.protocolVersion = System.getProperty("weblogic.security.SSL.preferredProtocolVersion");
      this.jdkTlsClientProtocolsSpecified = System.getProperty("jdk.tls.client.protocols") != null;
      this.minProtocolVersionSpecified = System.getProperty("weblogic.security.SSL.minimumProtocolVersion") != null;
   }

   public T3ClientWeblogicSocketFactory(SocketFactory socketFactory, ServerChannel channel) {
      throw new UnsupportedOperationException("Only SSLSocketFactory is supported");
   }

   public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
      return this.createSocket(InetAddress.getByName(host), port);
   }

   public Socket createSocket(InetAddress host, int port) throws IOException {
      return this.createSocket(host, port, 0);
   }

   public Socket createSocket(InetAddress host, int port, int connectionTimeoutMillis) throws IOException {
      int timeout = connectionTimeoutMillis <= 0 && this.channel != null ? this.channel.getConnectTimeout() * 1000 : connectionTimeoutMillis;
      SSLSocket sslSocket = null;
      boolean alreadyConnected = false;
      if (ProxyUtils.canProxy(host, true)) {
         Socket sock = ProxyUtils.getSSLClientProxy(host.getCanonicalHostName(), port, connectionTimeoutMillis);
         sslSocket = (SSLSocket)this.sslSocketFactory.createSocket(sock, host.getCanonicalHostName(), port, true);
         alreadyConnected = true;
      } else {
         sslSocket = (SSLSocket)this.sslSocketFactory.createSocket();
      }

      this.log.debug("Proxy is used: " + ProxyUtils.canProxy(host, true) + " Proxy Host: " + ProxyUtils.getProxyHost() + " Proxy port: " + ProxyUtils.getProxyPort() + " Dont Proxy: " + ProxyUtils.getDontProxy() + " Proxy Auth: " + ProxyUtils.getProxyAuthStr() + " Proxy Auth Class: " + ProxyUtils.getProxyAuthClassName() + " SSL Proxy Host: " + ProxyUtils.getSSLProxyHost() + " SSL Proxy Port: " + ProxyUtils.getSSLProxyPort() + " SSL Dont Proxy: " + ProxyUtils.getSSLDontProxy());
      ArrayList enabledProtocols = this.getEnabledProtocols(sslSocket.getSupportedProtocols(), sslSocket.getEnabledProtocols());
      int i = enabledProtocols.size() - 1;

      while(i >= 0) {
         try {
            this.configureSocket(sslSocket, host, port, timeout, alreadyConnected);
            sslSocket.setEnabledProtocols(new String[]{(String)enabledProtocols.get(i)});
            sslSocket.startHandshake();
            this.log.debug("Handshake succeeded: " + (String)enabledProtocols.get(i));
            return sslSocket;
         } catch (SSLException var10) {
            sslSocket.close();
            this.log.debug("Handshake failed: " + (String)enabledProtocols.get(i) + ", error = " + var10.getMessage());
            if (i == 0) {
               throw var10;
            }

            sslSocket = (SSLSocket)this.sslSocketFactory.createSocket();
            --i;
         }
      }

      return null;
   }

   private void configureSocket(SSLSocket sslSocket, InetAddress host, int port, int timeout, boolean alreadyConnected) throws IOException {
      if (!alreadyConnected) {
         sslSocket.connect(new InetSocketAddress(host, port), timeout);
      }

      sslSocket.setTcpNoDelay(true);
      sslSocket.setTrafficClass(16);
   }

   private ArrayList getEnabledProtocols(String[] supportedProtocols, String[] socketEnabledProtocols) {
      ArrayList enabledProtocols = new ArrayList();
      if (this.jdkTlsClientProtocolsSpecified) {
         if (this.minProtocolVersionSpecified) {
            this.log.debug("weblogic.security.SSL.minimumProtocolVersion property is ignored because jdk.tls.client.protocols property is set");
         }

         if (socketEnabledProtocols != null) {
            String[] var4 = socketEnabledProtocols;
            int var5 = socketEnabledProtocols.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String protocol = var4[var6];
               enabledProtocols.add(protocol);
            }
         }
      } else {
         for(int i = 0; i < supportedProtocols.length; ++i) {
            if (this.minProtocolVersion.compareTo(supportedProtocols[i]) <= 0) {
               enabledProtocols.add(supportedProtocols[i]);
            }
         }

         if (enabledProtocols.isEmpty()) {
            this.log.debug("Invalid value: weblogic.security.SSL.minimumProtocolVersion=" + this.minProtocolVersion);
            throw new RuntimeException("Invalid value: weblogic.security.SSL.minimumProtocolVersion=" + this.minProtocolVersion);
         }
      }

      if (this.protocolVersion != null && !enabledProtocols.contains(this.protocolVersion)) {
         this.log.debug("Invalid value: weblogic.security.SSL.preferredProtocolVersion=" + this.protocolVersion + ". Either the protocol is not valid or does not comply with " + (this.jdkTlsClientProtocolsSpecified ? "jdk.tls.client.protocols property" : "weblogic.security.SSL.minimumProtocolVersion=" + this.minProtocolVersion));
         throw new RuntimeException("Invalid value: weblogic.security.SSL.preferredProtocolVersion=" + this.protocolVersion);
      } else {
         if (this.protocolVersion != null) {
            enabledProtocols.remove(this.protocolVersion);
         }

         Collections.sort(enabledProtocols);
         if (this.protocolVersion != null) {
            enabledProtocols.add(this.protocolVersion);
         }

         return enabledProtocols;
      }
   }

   public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) {
      throw new UnsupportedOperationException("Binding characteristics are determined by the channel");
   }

   public Socket createSocket(String address, int port, InetAddress localHost, int localPort) {
      throw new UnsupportedOperationException("Binding characteristics are determined by the channel");
   }
}
