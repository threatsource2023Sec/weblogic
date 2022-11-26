package weblogic.net.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import weblogic.net.NetLogger;
import weblogic.security.SSL.SSLClientInfo;
import weblogic.security.SSL.SSLSocketFactory;
import weblogic.security.acl.internal.Security;
import weblogic.security.utils.SSLContextWrapper;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.security.utils.SSLSetup;
import weblogic.socket.SocketMuxer;
import weblogic.socket.utils.RegexpPool;
import weblogic.utils.StringUtils;

final class HttpsClient extends HttpClient {
   static final int MAX_TRIES = 3;
   static final int defaultHTTPSPort = 443;
   private String proxyAuthStr = null;
   private static Map proxyAuthStrTable = new ConcurrentHashMap();
   private static boolean isMuxerReady = false;
   private String proxyAuthStrHeader = null;
   private SSLClientInfo sslInfo;
   private SSLSocketFactory sslSocketFactory;

   public SSLClientInfo getSSLClientInfo() {
      return this.sslInfo;
   }

   public void setSSLClientInfo(SSLClientInfo sslci) {
      this.sslInfo = sslci;
   }

   Object getClientInfo() {
      return this.getSSLClientInfo();
   }

   public SSLSocketFactory getSSLSocketFactory() {
      return this.sslSocketFactory;
   }

   public void setSSLSocketFactory(SSLSocketFactory sslSF) {
      this.sslSocketFactory = sslSF;
   }

   private void resetSSLProperties() {
      try {
         String rawList;
         try {
            rawList = SecurityHelper.getSystemProperty("https.proxyPort");
            if (rawList == null) {
               rawList = SecurityHelper.getSystemProperty("ssl.proxyPort");
            }

            if (rawList == null) {
               return;
            }

            this.proxyPort = Integer.parseInt(rawList);
         } catch (NumberFormatException var4) {
            return;
         }

         this.proxyHost = SecurityHelper.getSystemProperty("https.proxyHost");
         if (this.proxyHost == null) {
            this.proxyHost = SecurityHelper.getSystemProperty("ssl.proxyHost");
         }

         rawList = SecurityHelper.getSystemProperty("https.nonProxyHosts");
         if (rawList != null) {
            this.dontProxy = new RegexpPool();
            StringTokenizer st = new StringTokenizer(rawList, "|", false);
            boolean hasDuplicates = false;

            while(st.hasMoreTokens()) {
               if (!this.dontProxy.add(st.nextToken().toLowerCase())) {
                  hasDuplicates = true;
               }
            }

            if (hasDuplicates) {
               NetLogger.logDuplicateExpression("https", rawList, (Exception)null);
            }
         }
      } catch (SecurityException var5) {
         this.proxyPort = -1;
         this.proxyHost = null;
         this.dontProxy = null;
      }

   }

   private static byte[] getConnectBytes(String host, int port, String proxyAuthString) {
      String connStr = "CONNECT " + host + ':' + port + " HTTP/1.0";
      if (proxyAuthString != null) {
         connStr = connStr + "\r\n" + proxyAuthString;
      }

      connStr = connStr + "\r\n\r\n";
      return connStr.getBytes();
   }

   /** @deprecated */
   @Deprecated
   public Socket getLayeredSocketUsingProxy(String host, int port) throws IOException {
      int maxTries = 0;

      while(true) {
         Socket s = new Socket(this.proxyHost, this.proxyPort);
         s.setTcpNoDelay(true);
         OutputStream os = s.getOutputStream();
         os.write(getConnectBytes(host, port, this.proxyAuthStr));
         DataInputStream dis = new DataInputStream(s.getInputStream());
         String firstLine = dis.readLine();
         if (firstLine != null && firstLine.length() != 0) {
            String[] rsp = StringUtils.splitCompletely(firstLine);
            if (rsp.length >= 2 && (rsp[0].equals("HTTP/1.0") || rsp[0].equals("HTTP/1.1"))) {
               if (rsp.length < 2 || !rsp[0].equals("HTTP/1.0") && !rsp[0].equals("HTTP/1.1")) {
                  continue;
               }

               if (rsp[1].equals("200")) {
                  while((firstLine = dis.readLine()) != null && firstLine.length() > 0) {
                  }

                  return s;
               }

               if (!rsp[1].equals("407")) {
                  s.close();
                  throw new ProtocolException("unrecognized response from SSL proxy: '" + firstLine + "'");
               }

               if (maxTries > 3) {
                  throw new ProtocolException("Server redirected too many times (" + maxTries + ")");
               }

               String line;
               while((line = dis.readLine()) != null && line.length() > 0) {
                  String[] hdrs = StringUtils.split(line, ':');
                  if (hdrs[0].equalsIgnoreCase("Proxy-Authenticate")) {
                     this.proxyAuthStr = HttpURLConnection.getAuthInfo(this.proxyHost, this.proxyPort, hdrs[1]);
                     if (this.proxyAuthStr == null) {
                        throw new HttpUnauthorizedException("Proxy Authentication required (407)");
                     }

                     this.proxyAuthStr = "Proxy-Authorization: " + this.proxyAuthStr;
                  }
               }

               ++maxTries;
               continue;
            }

            s.close();
            throw new ProtocolException("unrecognized response from SSL proxy: '" + firstLine + "'");
         }

         throw new ProtocolException("Empty response is detected from SSL proxy : '" + this.proxyHost + ":" + this.proxyPort + "'");
      }
   }

   private HttpsClient(URL u, Proxy p, SocketFactory factory, SSLClientInfo sslinfo, SSLSocketFactory sslFactory, boolean http11, int conTimeout, int rdTimeout, String proxyAuthStr, boolean isIgnoreSystemNonProxyHosts, boolean isIgnoreProxy) throws IOException {
      super(u, p, factory, http11, conTimeout, rdTimeout, isIgnoreSystemNonProxyHosts, isIgnoreProxy);
      this.sslInfo = sslinfo;
      this.sslSocketFactory = sslFactory;
      if (proxyAuthStr != null) {
         this.proxyAuthStrHeader = "Proxy-Authorization: " + proxyAuthStr;
      }

   }

   protected void finalize() throws Throwable {
      try {
         if (this.serverSocket != null && this.serverSocket instanceof SSLSocket) {
            SSLIOContextTable.removeContext((SSLSocket)this.serverSocket);
         }

         if (this.scavenger != null) {
            this.scavenger.run();
         }

         this.scavenger = null;
      } catch (Throwable var2) {
      }

      super.finalize();
   }

   protected int getDefaultPort() {
      return 443;
   }

   private SocketFactory getInternalSocketFactory() throws IOException {
      if (this.sslSocketFactory == null) {
         if (this.sslInfo == null) {
            this.sslInfo = (SSLClientInfo)Security.getThreadSSLClientInfo();
         }

         this.sslSocketFactory = SSLSocketFactory.getInstance(this.sslInfo);
      }

      return this.sslSocketFactory;
   }

   protected void openServer(String server, int port) throws IOException {
      InetAddress[] addresses = InetAddress.getAllByName(server);
      SocketFactory theFactory = this.getInternalSocketFactory();
      int ct = this.connectTimeout;
      if (ct < 0) {
         ct = 0;
      }

      int i = 0;

      while(i < addresses.length) {
         try {
            this.serverSocket = this.openWrappedSSLSocket(addresses[i], server, port, ct);
            break;
         } catch (ConnectException var8) {
            ++i;
         }
      }

      if (this.serverSocket == null) {
         throw new ConnectException("Tried all: " + addresses.length + " addresses, but could not connect over HTTPS to server: " + server + " port: " + port);
      } else {
         this.serverSocket.setTcpNoDelay(true);
         if (this.readTimeout != -1) {
            this.serverSocket.setSoTimeout(this.readTimeout);
         }

         this.serverOutput = new HttpOutputStream(new BufferedOutputStream(this.serverSocket.getOutputStream()));
         this.serverInput = new BufferedInputStream(this.serverSocket.getInputStream());
      }
   }

   protected String getProtocol() {
      return "https";
   }

   protected void openServer() throws IOException {
      this.resetSSLProperties();
      SecurityManager security = System.getSecurityManager();
      if (security != null && this.shouldCheckAccessForProxiedHost()) {
         security.checkConnect(this.host, this.port);
      }

      if (!this.keepingAlive) {
         if (!this.url.getProtocol().equals(this.getProtocol())) {
            throw new ProtocolException("unsupported protocol: " + this.url.getProtocol());
         } else if (!this.ignoreProxy && this.instProxy != Proxy.NO_PROXY && (this.ignoreSystemNonProxyHosts || isProxyAllowed(this.host, this.dontProxy))) {
            InetSocketAddress proxyAddress = (InetSocketAddress)this.instProxy.address();
            String instProxyHost = proxyAddress.getHostName();
            int instProxyPort = proxyAddress.getPort();

            try {
               this.makeConnectionUsingProxy(instProxyHost, instProxyPort, true);
            } catch (IOException var6) {
               NetLogger.logIOExceptionWithoutRetry(instProxyHost, "" + instProxyPort, var6);
               throw var6;
            }
         } else {
            if (!this.ignoreProxy && this.proxyHost != null && isProxyAllowed(this.host, this.dontProxy)) {
               try {
                  this.makeConnectionUsingProxy(this.proxyHost, this.proxyPort, false);
                  return;
               } catch (IOException var7) {
                  NetLogger.logIOException(this.proxyHost, "" + this.proxyPort, this.host, "" + this.port, var7);
               }
            }

            this.openServer(this.host, this.port);
         }
      }
   }

   private void makeConnectionUsingProxy(String proxyHost, int proxyPort, boolean usingInstProxy) throws IOException {
      SecurityManager security = System.getSecurityManager();
      if (security != null && this.shouldCheckAccessForProxiedHost()) {
         security.checkConnect(proxyHost, proxyPort);
      }

      String originalThreadName = null;

      try {
         originalThreadName = Thread.currentThread().getName();
         Thread.currentThread().setName(originalThreadName + " [connecting to " + this.host + ":" + this.port + "using proxy " + proxyHost + ":" + proxyPort + "]");
         SSLContextWrapper sslContext = SSLSetup.getSSLContext(this.sslInfo);
         sslContext.getHostnameVerifier().setProxyMapping(proxyHost, this.host);
         sslContext.getTrustManager().setProxyMapping(proxyHost, this.host);
         SSLSocketFactoryAdapter theFactory = new SSLSocketFactoryAdapter(this.sslSocketFactory, sslContext);
         if (usingInstProxy) {
            this.proxyAuthStr = (String)proxyAuthStrTable.get(this.instProxy);
         }

         if (this.proxyAuthStr == null) {
            try {
               this.proxyAuthStr = HttpURLConnection.getProxyBasicCredentials(proxyHost, proxyPort, "Basic", this.url);
               this.proxyAuthStr = "Proxy-Authorization: " + this.proxyAuthStr;
               if (usingInstProxy) {
                  proxyAuthStrTable.put(this.instProxy, this.proxyAuthStr);
               }
            } catch (HttpUnauthorizedException var19) {
            }
         }

         int maxTries = 0;

         while(true) {
            Socket s = this.getSocketFactory().createSocket(proxyHost, proxyPort);
            s.setTcpNoDelay(true);
            if (this.readTimeout != -1) {
               s.setSoTimeout(this.readTimeout);
            }

            OutputStream os = s.getOutputStream();
            os.write(getConnectBytes(this.host, this.port, this.proxyAuthStrHeader != null ? this.proxyAuthStrHeader : this.proxyAuthStr));
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String line = dis.readLine();
            if (line == null || line.length() == 0) {
               s.close();
               throw new ProtocolException("Empty response is detected from SSL proxy : '" + proxyHost + ":" + proxyPort + "'");
            }

            String[] rsp = StringUtils.splitCompletely(line);
            if (rsp.length < 2 || !rsp[0].equals("HTTP/1.0") && !rsp[0].equals("HTTP/1.1")) {
               s.close();
               throw new ProtocolException("unrecognized response from SSL proxy: '" + line + "'");
            }

            if (rsp[1].equals("200")) {
               while((line = dis.readLine()) != null && line.length() > 0) {
               }

               this.serverSocket = theFactory.createSocket(s, this.host, this.port, true);
               this.serverOutput = new HttpOutputStream(new BufferedOutputStream(this.serverSocket.getOutputStream()));
               this.serverInput = new BufferedInputStream(this.serverSocket.getInputStream());
               this.usingProxy = true;
               return;
            }

            if (!rsp[1].equals("407")) {
               s.close();
               throw new ProtocolException("Unrecognized response from SSL proxy: '" + line + "'");
            }

            if (maxTries > 3) {
               throw new ProtocolException("Server redirected too many times (" + maxTries + ")");
            }

            while((line = dis.readLine()) != null && line.length() > 0) {
               String[] hdrs = StringUtils.split(line, ':');
               if (hdrs[0].equalsIgnoreCase("Proxy-Authenticate")) {
                  this.proxyAuthStr = HttpURLConnection.getProxyBasicCredentials(proxyHost, proxyPort, hdrs[1], this.url);
                  if (this.proxyAuthStr == null) {
                     throw new HttpUnauthorizedException("Proxy Authentication required (407)");
                  }

                  this.proxyAuthStr = "Proxy-Authorization: " + this.proxyAuthStr;
                  if (usingInstProxy) {
                     proxyAuthStrTable.put(this.instProxy, this.proxyAuthStr);
                  }
               }
            }

            s.close();
            ++maxTries;
         }
      } catch (IOException var20) {
         throw var20;
      } finally {
         if (originalThreadName != null) {
            Thread.currentThread().setName(originalThreadName);
         }

      }
   }

   public String getURLFile() {
      String file = this.url.getFile();
      if (file == null || file.length() == 0) {
         file = "/";
      }

      return file;
   }

   public SSLSession getSSLSession() {
      if (this.serverSocket != null && this.serverSocket instanceof SSLSocket) {
         SSLSocket sslSocket = (SSLSocket)this.serverSocket;
         return sslSocket.getSession();
      } else {
         return null;
      }
   }

   static HttpsClient New(URL url, Proxy p, SocketFactory factory, SSLClientInfo sslinfo, SSLSocketFactory sslSockFactory, boolean http11, int conTimeout, int rdTimeout, String proxyAuthStr, boolean isIgnoreSystemNonProxyHosts, boolean isIgnoreProxy) throws IOException {
      return !isIgnoreSystemNonProxyHosts && !isIgnoreProxy ? New(url, p, factory, sslinfo, sslSockFactory, http11, conTimeout, rdTimeout, true, proxyAuthStr, isIgnoreSystemNonProxyHosts, isIgnoreProxy) : New(url, p, factory, sslinfo, sslSockFactory, http11, conTimeout, rdTimeout, false, proxyAuthStr, isIgnoreSystemNonProxyHosts, isIgnoreProxy);
   }

   static HttpsClient New(URL url, Proxy p, SocketFactory factory, SSLClientInfo sslinfo, SSLSocketFactory sslSockFactory, boolean http11, int conTimeout, int rdTimeout, boolean useCache, String proxyAuthStr, boolean isIgnoreSystemNonProxyHosts, boolean isIgnoreProxy) throws IOException {
      HttpsClient ret = null;
      if (useCache) {
         ret = (HttpsClient)findInCache(url, sslinfo, p);
      }

      if (ret == null) {
         ret = new HttpsClient(url, p, factory, sslinfo, sslSockFactory, http11, conTimeout, rdTimeout, proxyAuthStr, isIgnoreSystemNonProxyHosts, isIgnoreProxy);
         ret.openServer();
         ((SSLSocket)ret.getSocket()).startHandshake();
      } else {
         ret.url = url;
         ret.setReadTimeout(rdTimeout < 0 ? 0 : rdTimeout);
         ret.setHttp11(http11);
      }

      return ret;
   }

   private Socket openWrappedSSLSocket(InetAddress address, String host, int port, int connecttimeout) throws IOException {
      if (!isMuxerReady) {
         isMuxerReady = SocketMuxer.isAvailable();
      }

      Socket s;
      if (isMuxerReady) {
         s = SocketMuxer.getMuxer().newSocket(address, port, connecttimeout);
      } else {
         s = new Socket();
         s.setTcpNoDelay(true);
         s.connect(new InetSocketAddress(address, port), connecttimeout);
      }

      Socket sslSock = this.sslSocketFactory.createSocket(s, host, port, true);
      return sslSock;
   }

   private class SSLSocketFactoryAdapter {
      private SSLSocketFactory sslSocketFactory = null;
      private javax.net.ssl.SSLSocketFactory theFactory = null;

      SSLSocketFactoryAdapter(SSLSocketFactory sslSocketFactory, SSLContextWrapper sslContext) {
         this.sslSocketFactory = sslSocketFactory;
         if (sslSocketFactory == null) {
            this.theFactory = sslContext.getSSLSocketFactory();
         }

      }

      Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
         return this.sslSocketFactory != null ? this.sslSocketFactory.createSocket(s, host, port, autoClose) : this.theFactory.createSocket(s, host, port, autoClose);
      }
   }
}
