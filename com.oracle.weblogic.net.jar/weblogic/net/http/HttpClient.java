package weblogic.net.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.CookieHandler;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.StringTokenizer;
import javax.net.SocketFactory;
import weblogic.kernel.KernelStatus;
import weblogic.net.NetLogger;
import weblogic.security.Security;
import weblogic.security.subject.SubjectManager;
import weblogic.socket.WeblogicSocketFactory;
import weblogic.socket.utils.RegexpPool;
import weblogic.utils.http.HttpChunkInputStream;

class HttpClient {
   public static final String HTTP_10 = "HTTP/1.0";
   public static final String HTTP_11 = "HTTP/1.1";
   static final boolean SECURITY_MANAGER_ENABLED;
   private static final String HTTP_CONTINUE = "100";
   private static final int httpPortNumber = 80;
   protected Proxy instProxy;
   protected String proxyHost = null;
   protected int proxyPort = 80;
   private static KeepAliveCache kac = new KeepAliveCache();
   protected InputStream kas;
   protected Socket serverSocket;
   protected HttpOutputStream serverOutput;
   protected InputStream serverInput;
   protected boolean usingHttp11;
   private SocketFactory socketFactory;
   private CookieHandler cookieHandler;
   public boolean usingProxy = false;
   protected String host;
   protected int port;
   protected int numReq = 0;
   private static boolean keepAliveProp = true;
   boolean keepingAlive = false;
   protected int readTimeout = -1;
   protected int connectTimeout = -1;
   protected URL url;
   protected HttpURLConnection connection;
   protected boolean ignoreSystemNonProxyHosts = false;
   protected boolean ignoreProxy = false;
   protected RegexpPool dontProxy;
   long lastUsed = System.currentTimeMillis();
   protected Object muxableSocket;
   protected Runnable scavenger;

   private void resetProperties() {
      try {
         this.proxyHost = SecurityHelper.getSystemProperty("http.proxyHost");
         this.proxyPort = SecurityHelper.getInteger("http.proxyPort", 80);
         if (this.proxyHost == null) {
            this.proxyHost = SecurityHelper.getSystemProperty("proxyHost");
            this.proxyPort = SecurityHelper.getInteger("proxyPort", 80);
         }

         if (this.proxyHost != null && this.proxyHost.length() == 0) {
            this.proxyHost = null;
         }

         String rawList = SecurityHelper.getSystemProperty("http.nonProxyHosts");
         if (rawList == null) {
            rawList = SecurityHelper.getSystemProperty("nonProxyHosts");
         }

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
               NetLogger.logDuplicateExpression("http", rawList, (Exception)null);
            }
         }
      } catch (SecurityException var4) {
         this.proxyHost = null;
         this.proxyPort = 80;
         this.dontProxy = null;
      }

   }

   protected boolean shouldCheckAccessForProxiedHost() {
      return Boolean.valueOf(SecurityHelper.getSystemProperty("weblogic.http.client.performAccessCheckForProxiedHost", "true"));
   }

   protected HttpClient(URL url, Proxy p, SocketFactory factory, boolean http11, int conTimeout, int rdTimeout, boolean isIgnoreSystemNonProxyHosts, boolean isIgnoreProxy) throws IOException {
      this.url = url;
      this.port = url.getPort();
      this.host = url.getHost();
      this.setSocketFactory(factory);
      if (this.port == -1) {
         this.port = this.getDefaultPort();
      }

      this.setReadTimeout(rdTimeout);
      this.connectTimeout = conTimeout;
      this.instProxy = p == null ? Proxy.NO_PROXY : p;
      this.numReq = 0;
      this.usingHttp11 = http11;
      this.ignoreSystemNonProxyHosts = isIgnoreSystemNonProxyHosts;
      this.ignoreProxy = isIgnoreProxy;
      this.cookieHandler = getCookieHandler();
   }

   static CookieHandler getCookieHandler() {
      return SECURITY_MANAGER_ENABLED ? (CookieHandler)AccessController.doPrivileged(new PrivilegedAction() {
         public CookieHandler run() {
            return CookieHandler.getDefault();
         }
      }) : CookieHandler.getDefault();
   }

   public void setHttp11(boolean useHttp11) {
      this.usingHttp11 = useHttp11;
   }

   public String toString() {
      return super.toString() + "[url:" + this.url + " sock:" + this.serverSocket + " requests:" + this.numReq + " ka:" + this.keepingAlive + "]";
   }

   public boolean getHttpKeepAliveSet() {
      return keepAliveProp;
   }

   public final boolean isKeepingAlive() {
      return this.getHttpKeepAliveSet() && this.keepingAlive;
   }

   void setKeepingAlive(boolean b) {
      this.keepingAlive = b;
   }

   public InputStream getInputStream() {
      return this.kas != null ? this.kas : this.serverInput;
   }

   public HttpOutputStream getOutputStream() {
      return this.serverOutput;
   }

   String getProxyHost() {
      return this.instProxy != Proxy.NO_PROXY ? ((InetSocketAddress)this.instProxy.address()).getHostName() : this.proxyHost;
   }

   int getProxyPort() {
      return this.instProxy != Proxy.NO_PROXY ? ((InetSocketAddress)this.instProxy.address()).getPort() : this.proxyPort;
   }

   URL getURL() {
      return this.url;
   }

   Object getClientInfo() {
      return null;
   }

   public SocketFactory getSocketFactory() {
      return this.socketFactory == null ? SocketFactory.getDefault() : this.socketFactory;
   }

   public void setSocketFactory(SocketFactory fact) {
      this.socketFactory = fact;
   }

   protected int getDefaultPort() {
      return 80;
   }

   protected static HttpClient findInCache(URL url, Object info, Proxy proxy) {
      return kac.get(url, info, proxy);
   }

   static HttpClient New(URL url, Proxy p, SocketFactory factory, boolean http11, int conTimeout, int rdTimeout, boolean isIgnoreSystemNonProxyHosts, boolean isIgnoreProxy) throws IOException {
      return !isIgnoreSystemNonProxyHosts && !isIgnoreProxy ? New(url, p, factory, http11, conTimeout, rdTimeout, true, isIgnoreSystemNonProxyHosts, isIgnoreProxy) : New(url, p, factory, http11, conTimeout, rdTimeout, false, isIgnoreSystemNonProxyHosts, isIgnoreProxy);
   }

   static HttpClient New(URL url, Proxy p, SocketFactory factory, boolean http11, int conTimeout, int rdTimeout, boolean useCache, boolean isIgnoreSystemNonProxyHosts, boolean isIgnoreProxy) throws IOException {
      HttpClient ret = null;
      if (useCache) {
         ret = findInCache(url, (Object)null, p);
      }

      if (ret == null) {
         ret = new HttpClient(url, p, factory, http11, conTimeout, rdTimeout, isIgnoreSystemNonProxyHosts, isIgnoreProxy);
         ret.openServer();
      } else {
         ret.url = url;
         ret.setReadTimeout(rdTimeout < 0 ? 0 : rdTimeout);
         ret.setHttp11(http11);
      }

      return ret;
   }

   public static void finished(HttpClient done) {
      if (HttpURLConnection.debug) {
         HttpURLConnection.p("Closing " + done);
      }

      if (!done.ignoreProxy && !done.ignoreSystemNonProxyHosts && done.isKeepingAlive()) {
         ++done.numReq;
         done.kas = null;
         done.usingHttp11 = false;
         if (done.connection != null) {
            done.connection.finish();
            done.connection = null;
         }

         kac.put(done);
      } else {
         done.closeServer();
      }

   }

   public void setLastUsed(long lastUsed) {
      this.lastUsed = lastUsed;
   }

   public long getLastUsed() {
      return this.lastUsed;
   }

   protected void openServer(String server, int port) throws IOException {
      StringBuilder sb = new StringBuilder();
      SecurityManager security = System.getSecurityManager();
      if (security != null && this.shouldCheckAccessForProxiedHost()) {
         security.checkConnect(server, port);
      }

      this.resetAsyncState();
      InetAddress[] addresses = InetAddress.getAllByName(server);
      int i = 0;

      while(i < addresses.length) {
         try {
            if (this.connectTimeout > 0) {
               SocketFactory factory = this.getSocketFactory();
               if (factory instanceof WeblogicSocketFactory) {
                  this.serverSocket = ((WeblogicSocketFactory)factory).createSocket(addresses[i], port, this.connectTimeout);
               } else {
                  this.serverSocket = this.getSocketFactory().createSocket();
                  this.serverSocket.connect(new InetSocketAddress(addresses[i], port), this.connectTimeout);
               }
            } else {
               this.serverSocket = this.getSocketFactory().createSocket(addresses[i], port);
            }
            break;
         } catch (ConnectException var8) {
            sb.append("  [" + i + "] address:'" + addresses[i] + "',port:'" + port + "' : " + var8.toString() + "\n");
            ++i;
         }
      }

      if (this.serverSocket == null) {
         throw new ConnectException("Tried all: '" + addresses.length + "' addresses, but could not connect over HTTP to server: '" + server + "', port: '" + port + "'\n failed reasons:\n" + sb.toString());
      } else {
         this.serverSocket.setTcpNoDelay(true);
         if (this.readTimeout > -1) {
            this.serverSocket.setSoTimeout(this.readTimeout);
         }

         this.serverOutput = new HttpOutputStream(new BufferedOutputStream(this.serverSocket.getOutputStream()));
         this.serverInput = new BufferedInputStream(this.serverSocket.getInputStream());
      }
   }

   protected String getProtocol() {
      return "http";
   }

   static boolean isProxyAllowed(String hostStr, RegexpPool dontProxy) {
      boolean proxyAllowed = true;
      String host = hostStr.toLowerCase();
      if (dontProxy != null) {
         if (dontProxy.match(host)) {
            proxyAllowed = false;
         } else {
            try {
               InetAddress hostInetAddr = InetAddress.getByName(host);
               String hostName = hostInetAddr.getHostName();
               if (dontProxy.match(hostName)) {
                  proxyAllowed = false;
               }
            } catch (UnknownHostException var6) {
            }
         }
      }

      return proxyAllowed;
   }

   protected void openServer() throws IOException {
      this.resetProperties();
      SecurityManager security = System.getSecurityManager();
      boolean accessCheckProxiedHost = this.shouldCheckAccessForProxiedHost();
      if (security != null) {
         if ((this.instProxy != Proxy.NO_PROXY || this.proxyHost != null) && !accessCheckProxiedHost) {
            InetSocketAddress proxyAdd = (InetSocketAddress)this.instProxy.address();
            security.checkConnect(proxyAdd.getHostName(), proxyAdd.getPort());
         } else if (accessCheckProxiedHost) {
            security.checkConnect(this.host, this.port);
         }
      }

      if (!this.isKeepingAlive()) {
         String p = this.url.getProtocol();
         if (!this.getProtocol().equals(p)) {
            throw new ProtocolException("Unsupported protocol: " + p + "'");
         } else if (this.ignoreProxy || this.instProxy == Proxy.NO_PROXY || !this.ignoreSystemNonProxyHosts && !isProxyAllowed(this.host, this.dontProxy)) {
            if (!this.ignoreProxy && this.proxyHost != null && isProxyAllowed(this.host, this.dontProxy)) {
               try {
                  this.openServer(this.proxyHost, this.proxyPort);
                  this.usingProxy = true;
                  return;
               } catch (IOException var7) {
                  NetLogger.logIOException(this.proxyHost, "" + this.proxyPort, this.host, "" + this.port, var7);
               }
            }

            this.openServer(this.host, this.port);
         } else {
            InetSocketAddress proxyAdd = (InetSocketAddress)this.instProxy.address();

            try {
               this.openServer(proxyAdd.getHostName(), proxyAdd.getPort());
               this.usingProxy = true;
            } catch (IOException var6) {
               NetLogger.logIOExceptionWithoutRetry(proxyAdd.getHostName(), "" + proxyAdd.getPort(), var6);
               throw var6;
            }
         }
      }
   }

   public String getURLFile() {
      String file;
      if (this.usingProxy) {
         file = this.url.getProtocol() + "://" + this.url.getHost();
         if (this.url.getPort() != -1) {
            file = file + ":" + this.url.getPort();
         }

         return file + this.url.getFile();
      } else {
         file = this.url.getFile();
         if (file == null || file.length() == 0) {
            file = "/";
         }

         return file;
      }
   }

   public void setReadTimeout(int i) {
      this.readTimeout = i;
      if (this.serverSocket != null && this.readTimeout > -1) {
         try {
            this.serverSocket.setSoTimeout(this.readTimeout);
         } catch (SocketException var3) {
         }
      }

   }

   void setConnection(HttpURLConnection con) {
      this.connection = con;
   }

   void parseHTTP(final MessageHeader responses) throws IOException {
      this.keepingAlive = false;

      try {
         responses.parseHeader(this.serverInput);
      } catch (InterruptedIOException var8) {
         this.resetAsyncState();
         throw var8;
      }

      if (this.cookieHandler != null) {
         final URI uri = ParseUtil.toURI(this.url);
         if (uri != null) {
            if (KernelStatus.isServer()) {
               Security.runAs(SubjectManager.getSubjectManager().getAnonymousSubject().getSubject(), new PrivilegedAction() {
                  public Object run() {
                     try {
                        if (HttpURLConnection.debug) {
                           HttpURLConnection.p("save cookies: " + responses.getHeaders());
                        }

                        HttpClient.this.cookieHandler.put(uri, responses.getHeaders());
                     } catch (IOException var2) {
                        if (HttpURLConnection.debug) {
                           HttpURLConnection.p(var2.toString());
                        }
                     }

                     return null;
                  }
               });
            } else {
               this.cookieHandler.put(uri, responses.getHeaders());
               if (HttpURLConnection.debug) {
                  HttpURLConnection.p("save cookies: " + responses.getHeaders());
               }
            }
         }
      }

      String resp = responses.getValue(0);
      boolean isHttp11Response = resp != null && resp.startsWith("HTTP/1.1");
      int cl;
      String keep;
      if (resp != null) {
         for(cl = resp.indexOf(32); resp.charAt(cl) == ' '; ++cl) {
         }

         keep = resp.substring(cl, cl + 3);
         if ("100".equals(keep)) {
            this.parseHTTP(responses);
         }
      }

      cl = -1;

      try {
         cl = Integer.parseInt(responses.findValue("content-length"));
      } catch (Exception var7) {
      }

      if (this.usingHttp11) {
         this.keepingAlive = true;
      }

      keep = null;
      if (this.usingProxy) {
         keep = responses.findValue("Proxy-Connection");
      }

      if (keep == null) {
         keep = responses.findValue("Connection");
      }

      if (keep != null) {
         keep = keep.toLowerCase();
         if (keep.equals("keep-alive")) {
            if (cl > 0) {
               this.keepingAlive = true;
            }
         } else if (keep.equals("close")) {
            this.keepingAlive = false;
         }
      } else if (!isHttp11Response) {
         this.keepingAlive = false;
      }

      this.kas = this.serverInput;
      if (this.usingHttp11) {
         String chunked = responses.findValue("Transfer-Encoding");
         if (chunked != null && chunked.equalsIgnoreCase("chunked")) {
            this.kas = new HttpChunkInputStream(this.serverInput) {
               public void close() throws IOException {
                  if (this.in != null) {
                     int skipCount = this.available();
                     this.skip((long)skipCount);
                     this.in.close();
                     this.in = null;
                  }
               }
            };
            cl = -1;
         } else if (cl == -1) {
            this.keepingAlive = false;
         }
      }

      this.kas = new KeepAliveStream(this, this.kas, cl);
   }

   void closeServer() {
      try {
         this.keepingAlive = false;
         this.resetAsyncState();
         this.serverSocket.close();
      } catch (Exception var2) {
      }

      if (HttpURLConnection.debug) {
         HttpURLConnection.p("Closed " + this);
      }

   }

   protected void finalize() throws Throwable {
      this.resetAsyncState();

      try {
         if (this.serverSocket != null) {
            this.serverSocket.close();
         }
      } catch (IOException var2) {
      }

      super.finalize();
   }

   Socket getSocket() {
      return this.serverSocket;
   }

   void setInputStream(InputStream overridingInputStream) {
      this.serverInput = overridingInputStream;
   }

   void setMuxableSocket(Object socket) {
      this.muxableSocket = socket;
   }

   Object getMuxableSocket() {
      return this.muxableSocket;
   }

   private void resetAsyncState() {
      if (this.scavenger != null) {
         try {
            this.scavenger.run();
         } catch (Throwable var2) {
         }
      }

      this.muxableSocket = null;
      this.scavenger = null;
   }

   void setScavenger(Runnable scavenger) {
      this.scavenger = scavenger;
   }

   static {
      try {
         String keepAlive = SecurityHelper.getSystemProperty("http.keepAlive");
         if (keepAlive != null) {
            keepAliveProp = Boolean.valueOf(keepAlive);
         }
      } catch (SecurityException var1) {
      }

      SECURITY_MANAGER_ENABLED = System.getSecurityManager() != null;
   }
}
