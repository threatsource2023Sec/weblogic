package weblogic.net.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ContentHandlerFactory;
import java.net.FileNameMap;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Optional;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import weblogic.security.SSL.SSLClientInfo;
import weblogic.security.SSL.SSLSocketFactory;
import weblogic.security.SSL.TrustManager;

public final class CompatibleSOAPHttpsURLConnection extends javax.net.ssl.HttpsURLConnection {
   private static HostnameVerifier DEFAULT_HOSTNAME_VERIFIER;
   private static SSLSocketFactory DEFAULT_SOCKET_FACTORY;
   private HttpsURLConnection wlsHttps;
   private boolean isHVSet;
   private boolean isSFSet;

   public CompatibleSOAPHttpsURLConnection(URL theURL) {
      this(theURL, (Proxy)null);
   }

   public CompatibleSOAPHttpsURLConnection(URL theURL, Proxy theProxy) {
      super(theURL);
      this.wlsHttps = new HttpsURLConnection(theURL, theProxy);
      if (DEFAULT_SOCKET_FACTORY != null) {
         this.wlsHttps.setSSLSocketFactory(DEFAULT_SOCKET_FACTORY);
      }

      if (DEFAULT_HOSTNAME_VERIFIER != null) {
         this.wlsHttps.setHostnameVerifier(this.convert(DEFAULT_HOSTNAME_VERIFIER));
      }

   }

   public String getCipherSuite() {
      this.checkConnect();
      return this.wlsHttps.getCipherSuite();
   }

   public static HostnameVerifier getDefaultHostnameVerifier() {
      return DEFAULT_HOSTNAME_VERIFIER;
   }

   public static void setDefaultHostnameVerifier(HostnameVerifier v) {
      DEFAULT_HOSTNAME_VERIFIER = v;
   }

   public static javax.net.ssl.SSLSocketFactory getDefaultSSLSocketFactory() {
      if (DEFAULT_SOCKET_FACTORY == null) {
         Class var0 = WLSSSLSocketFactoryAdapter.class;
         synchronized(WLSSSLSocketFactoryAdapter.class) {
            if (DEFAULT_SOCKET_FACTORY == null) {
               DEFAULT_SOCKET_FACTORY = HttpsURLConnection.getDefaultSSLSocketFactory();
            }
         }
      }

      return new CompatibleSSLSocketFactoryAdapter(DEFAULT_SOCKET_FACTORY);
   }

   public static void setDefaultSSLSocketFactory(javax.net.ssl.SSLSocketFactory sf) {
      Class var1 = WLSSSLSocketFactoryAdapter.class;
      synchronized(WLSSSLSocketFactoryAdapter.class) {
         DEFAULT_SOCKET_FACTORY = new WLSSSLSocketFactoryAdapter(sf);
      }
   }

   public HostnameVerifier getHostnameVerifier() {
      weblogic.security.SSL.HostnameVerifier hv = this.wlsHttps.getHostnameVerifier();
      return hv == null ? getDefaultHostnameVerifier() : this.convert(hv);
   }

   public void setHostnameVerifier(HostnameVerifier v) {
      if (v != null) {
         this.wlsHttps.setHostnameVerifier(this.convert(v));
         this.isHVSet = true;
      } else {
         this.isHVSet = false;
      }

   }

   public void setHostnameVerifier(weblogic.security.SSL.HostnameVerifier v) {
      if (v != null) {
         this.wlsHttps.setHostnameVerifier(v);
         this.isHVSet = true;
      } else {
         this.isHVSet = false;
      }

   }

   public Certificate[] getLocalCertificates() {
      this.checkConnect();
      return this.wlsHttps.getSSLClientInfo().getClientLocalIdentityCert();
   }

   public Principal getLocalPrincipal() {
      this.checkConnect();
      Certificate[] certs = this.getLocalCertificates();
      return certs != null && certs.length > 0 && certs[0] instanceof X509Certificate ? ((X509Certificate)certs[0]).getIssuerX500Principal() : null;
   }

   public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
      this.checkConnect();
      Certificate[] certs = this.getServerCertificates();
      return certs != null && certs.length > 0 && certs[0] instanceof X509Certificate ? ((X509Certificate)certs[0]).getIssuerX500Principal() : null;
   }

   public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
      this.checkConnect();
      return this.wlsHttps.getServerCertificates();
   }

   public javax.net.ssl.SSLSocketFactory getSSLSocketFactory() {
      SSLSocketFactory sf = this.wlsHttps.getSSLSocketFactory();
      return (javax.net.ssl.SSLSocketFactory)(sf == null ? getDefaultSSLSocketFactory() : new CompatibleSSLSocketFactoryAdapter(sf));
   }

   public void setSSLSocketFactory(javax.net.ssl.SSLSocketFactory sf) {
      if (sf != null) {
         this.wlsHttps.setSSLSocketFactory(new WLSSSLSocketFactoryAdapter(sf));
         this.isSFSet = true;
      } else {
         this.isSFSet = false;
      }

   }

   public void setSSLSocketFactory(SSLSocketFactory sf) {
      if (sf != null) {
         this.wlsHttps.setSSLSocketFactory(sf);
         this.isSFSet = true;
      } else {
         this.isSFSet = false;
      }

   }

   public void connect() throws IOException {
      if (!this.isSFSet && DEFAULT_SOCKET_FACTORY != null) {
         this.wlsHttps.setSSLSocketFactory(DEFAULT_SOCKET_FACTORY);
      }

      if (!this.isHVSet && DEFAULT_HOSTNAME_VERIFIER != null) {
         this.wlsHttps.setHostnameVerifier(this.convert(DEFAULT_HOSTNAME_VERIFIER));
      }

      this.wlsHttps.connect();
   }

   public void disconnect() {
      this.wlsHttps.disconnect();
   }

   public boolean usingProxy() {
      return this.wlsHttps.usingProxy();
   }

   public SSLClientInfo getSSLClientInfo() {
      return this.wlsHttps.getSSLClientInfo();
   }

   public Optional getSSLSession() {
      return Optional.of(this.wlsHttps.getSSLSession());
   }

   public TrustManager getTrustManager() {
      return this.wlsHttps.getTrustManager();
   }

   public void u11() {
      this.wlsHttps.u11();
   }

   public InputStream getInputStream() throws IOException {
      try {
         return this.wlsHttps.getInputStream();
      } catch (FileNotFoundException var2) {
         if (this.getResponseCode() == 500) {
            return this.wlsHttps.getHttp().getInputStream();
         } else {
            throw var2;
         }
      }
   }

   public void loadLocalIdentity(Certificate[] certs, PrivateKey privateKey) {
      this.wlsHttps.loadLocalIdentity(certs, privateKey);
   }

   public void loadLocalIdentity(InputStream certStream, InputStream keyStream, char[] password) {
      this.wlsHttps.loadLocalIdentity(certStream, keyStream, password);
   }

   public void loadLocalIdentity(InputStream[] stream) {
      this.wlsHttps.loadLocalIdentity(stream);
   }

   public void setSSLClientCertificate(InputStream[] certs) {
      this.wlsHttps.setSSLClientCertificate(certs);
   }

   public void setTrustManager(TrustManager tm) {
      this.wlsHttps.setTrustManager(tm);
   }

   public void setIgnoreProxy(boolean v) {
      this.wlsHttps.setIgnoreProxy(v);
   }

   public static String getAuthInfo(String host, int port, String header) throws IOException {
      return HttpURLConnection.getAuthInfo(host, port, header);
   }

   public static String getProxyBasicCredentials(String proxyHost, int proxyPort, String authHeader, URL requestUrl) throws IOException {
      return HttpURLConnection.getProxyBasicCredentials(proxyHost, proxyPort, authHeader, requestUrl);
   }

   public static String getServerBasicCredentials(URL requestUrl, String authHeader) throws IOException {
      return HttpURLConnection.getServerBasicCredentials(requestUrl, authHeader);
   }

   void finish() {
      this.wlsHttps.finish();
   }

   static final void p(String s) {
      HttpURLConnection.p(s);
   }

   public static void setDefaultSocketFactory(SocketFactory nuDefaultSF) {
      HttpURLConnection.setDefaultSocketFactory(nuDefaultSF);
   }

   public void addRequestProperty(String key, String value) {
      this.wlsHttps.addRequestProperty(key, value);
   }

   public int getConnectTimeout() {
      return this.wlsHttps.getConnectTimeout();
   }

   public InputStream getErrorStream() {
      return this.wlsHttps.getErrorStream();
   }

   public String getHeaderField(int n) {
      return this.wlsHttps.getHeaderField(n);
   }

   public String getHeaderField(String name) {
      return this.wlsHttps.getHeaderField(name);
   }

   public String getHeaderFieldKey(int n) {
      return this.wlsHttps.getHeaderFieldKey(n);
   }

   public Map getHeaderFields() {
      return this.wlsHttps.getHeaderFields();
   }

   String getHttpVersion() {
      return this.wlsHttps.getHttpVersion();
   }

   String getMethod() {
      return this.wlsHttps.getMethod();
   }

   Object getMuxableSocket() {
      return this.wlsHttps.getMuxableSocket();
   }

   public OutputStream getOutputStream() throws IOException {
      return this.wlsHttps.getOutputStream();
   }

   public int getReadTimeout() {
      return this.wlsHttps.getReadTimeout();
   }

   public Map getRequestProperties() {
      return this.wlsHttps.getRequestProperties();
   }

   public String getRequestProperty(String key) {
      return this.wlsHttps.getRequestProperty(key);
   }

   public int getResponseCode() throws IOException {
      return this.wlsHttps.getResponseCode();
   }

   Socket getSocket() {
      return this.wlsHttps.getSocket();
   }

   public final SocketFactory getSocketFactory() {
      return this.wlsHttps.getSocketFactory();
   }

   public int getTimeout() {
      return this.wlsHttps.getTimeout();
   }

   boolean isConnected() {
      return this.wlsHttps.isConnected();
   }

   public void setChunkedStreamingMode(int chunkLength) {
      this.wlsHttps.setChunkedStreamingMode(chunkLength);
   }

   public void setConnectTimeout(int i) {
      this.wlsHttps.setConnectTimeout(i);
   }

   public void setEmptyRequestProperty(String key) {
      this.wlsHttps.setEmptyRequestProperty(key);
   }

   public void setFixedLengthStreamingMode(int fixedContentLength) {
      this.wlsHttps.setFixedLengthStreamingMode(fixedContentLength);
   }

   public void setIgnoreSystemNonProxyHosts(boolean v) {
      this.wlsHttps.setIgnoreSystemNonProxyHosts(v);
   }

   void setInputStream(InputStream inputStream) {
      this.wlsHttps.setInputStream(inputStream);
   }

   public void setMuxableSocket(Object socket) {
      this.wlsHttps.setMuxableSocket(socket);
   }

   public void setReadTimeout(int i) {
      this.wlsHttps.setReadTimeout(i);
   }

   public void setRequestMethod(String method) throws ProtocolException {
      this.wlsHttps.setRequestMethod(method);
   }

   public void setRequestProperty(String key, String value) {
      this.wlsHttps.setRequestProperty(key, value);
   }

   void setScavenger(Runnable scavenger) {
      this.wlsHttps.setScavenger(scavenger);
   }

   public final void setSocketFactory(SocketFactory fact) {
      this.wlsHttps.setSocketFactory(fact);
   }

   public void setTimeout(int i) {
      this.wlsHttps.setTimeout(i);
   }

   void writeRequestForAsyncResponse() throws IOException {
      this.wlsHttps.writeRequestForAsyncResponse();
   }

   public static boolean getFollowRedirects() {
      return HttpURLConnection.getFollowRedirects();
   }

   public long getHeaderFieldDate(String name, long Default) {
      return this.wlsHttps.getHeaderFieldDate(name, Default);
   }

   public boolean getInstanceFollowRedirects() {
      return this.wlsHttps.getInstanceFollowRedirects();
   }

   public Permission getPermission() throws IOException {
      return this.wlsHttps.getPermission();
   }

   public String getRequestMethod() {
      return this.wlsHttps.getRequestMethod();
   }

   public String getResponseMessage() throws IOException {
      return this.wlsHttps.getResponseMessage();
   }

   public void setFixedLengthStreamingMode(long contentLength) {
      this.wlsHttps.setFixedLengthStreamingMode(contentLength);
   }

   public static void setFollowRedirects(boolean set) {
      HttpURLConnection.setFollowRedirects(set);
   }

   public void setInstanceFollowRedirects(boolean followRedirects) {
      this.wlsHttps.setInstanceFollowRedirects(followRedirects);
   }

   public static FileNameMap getFileNameMap() {
      return HttpURLConnection.getFileNameMap();
   }

   public static void setFileNameMap(FileNameMap map) {
      HttpURLConnection.setFileNameMap(map);
   }

   public URL getURL() {
      return this.wlsHttps.getURL();
   }

   public int getContentLength() {
      return this.wlsHttps.getContentLength();
   }

   public long getContentLengthLong() {
      return this.wlsHttps.getContentLengthLong();
   }

   public String getContentType() {
      return this.wlsHttps.getContentType();
   }

   public String getContentEncoding() {
      return this.wlsHttps.getContentEncoding();
   }

   public long getExpiration() {
      return this.wlsHttps.getExpiration();
   }

   public long getDate() {
      return this.wlsHttps.getDate();
   }

   public long getLastModified() {
      return this.wlsHttps.getLastModified();
   }

   public int getHeaderFieldInt(String name, int Default) {
      return this.wlsHttps.getHeaderFieldInt(name, Default);
   }

   public long getHeaderFieldLong(String name, long Default) {
      return this.wlsHttps.getHeaderFieldLong(name, Default);
   }

   public Object getContent() throws IOException {
      return this.wlsHttps.getContent();
   }

   public Object getContent(Class[] classes) throws IOException {
      return this.wlsHttps.getContent(classes);
   }

   public String toString() {
      return "weblogic.net.http.CompatibleSOAPHttpsURLConnection: [" + this.wlsHttps.toString() + "]";
   }

   public void setDoInput(boolean doinput) {
      this.wlsHttps.setDoInput(doinput);
   }

   public boolean getDoInput() {
      return this.wlsHttps.getDoInput();
   }

   public void setDoOutput(boolean dooutput) {
      this.wlsHttps.setDoOutput(dooutput);
   }

   public boolean getDoOutput() {
      return this.wlsHttps.getDoOutput();
   }

   public void setAllowUserInteraction(boolean allowuserinteraction) {
      this.wlsHttps.setAllowUserInteraction(allowuserinteraction);
   }

   public boolean getAllowUserInteraction() {
      return this.wlsHttps.getAllowUserInteraction();
   }

   public static void setDefaultAllowUserInteraction(boolean defaultallowuserinteraction) {
      HttpURLConnection.setDefaultAllowUserInteraction(defaultallowuserinteraction);
   }

   public static boolean getDefaultAllowUserInteraction() {
      return HttpURLConnection.getDefaultAllowUserInteraction();
   }

   public void setUseCaches(boolean usecaches) {
      this.wlsHttps.setUseCaches(usecaches);
   }

   public boolean getUseCaches() {
      return this.wlsHttps.getUseCaches();
   }

   public void setIfModifiedSince(long ifmodifiedsince) {
      this.wlsHttps.setIfModifiedSince(ifmodifiedsince);
   }

   public long getIfModifiedSince() {
      return this.wlsHttps.getIfModifiedSince();
   }

   public boolean getDefaultUseCaches() {
      return this.wlsHttps.getDefaultUseCaches();
   }

   public void setDefaultUseCaches(boolean defaultusecaches) {
      this.wlsHttps.setDefaultUseCaches(defaultusecaches);
   }

   /** @deprecated */
   @Deprecated
   public static void setDefaultRequestProperty(String key, String value) {
      HttpURLConnection.setDefaultRequestProperty(key, value);
   }

   /** @deprecated */
   @Deprecated
   public static String getDefaultRequestProperty(String key) {
      return HttpURLConnection.getDefaultRequestProperty(key);
   }

   public static void setContentHandlerFactory(ContentHandlerFactory fac) {
      HttpURLConnection.setContentHandlerFactory(fac);
   }

   public static String guessContentTypeFromName(String fname) {
      return HttpURLConnection.guessContentTypeFromName(fname);
   }

   public static String guessContentTypeFromStream(InputStream is) throws IOException {
      return HttpURLConnection.guessContentTypeFromStream(is);
   }

   private weblogic.security.SSL.HostnameVerifier convert(final HostnameVerifier sf) {
      return new weblogic.security.SSL.HostnameVerifier() {
         public boolean verify(String hostname, SSLSession session) {
            return sf.verify(hostname, session);
         }
      };
   }

   private HostnameVerifier convert(final weblogic.security.SSL.HostnameVerifier sf) {
      return new HostnameVerifier() {
         public boolean verify(String hostname, SSLSession session) {
            return sf.verify(hostname, session);
         }
      };
   }

   private void checkConnect() {
      if (this.wlsHttps.getHttp() == null) {
         throw new IllegalStateException("connection not yet open");
      }
   }
}
