package weblogic.net.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.ConnectException;
import java.net.CookieHandler;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.net.Authenticator.RequestorType;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.SocketFactory;
import javax.net.ssl.SSLException;
import weblogic.common.ProxyAuthenticator;
import weblogic.kernel.KernelStatus;
import weblogic.security.Security;
import weblogic.security.subject.SubjectManager;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.utils.http.HttpChunkOutputStream;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.http.HttpReasonPhraseCoder;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.NullInputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public class HttpURLConnection extends java.net.HttpURLConnection {
   private static String userAgent;
   private static boolean strictPostRedirect = false;
   private static String proxyAuthClassName;
   private static String proxyAuthString = null;
   private static boolean http11 = true;
   private static boolean bufferPostForRetry = false;
   static final boolean debug;
   private static final String acceptString = "text/html, image/gif, image/jpeg, */*; q=.2";
   private static final int MAX_REDIRECTS = 5;
   private static final int MAX_TRIES = 3;
   private static final int MAX_LENGTH_OF_REQUEST_METHOD = 32;
   protected HttpClient http;
   protected Proxy instProxy;
   protected MessageHeader requests;
   protected MessageHeader responses;
   protected InputStream inputStream;
   protected OutputStream streamedPostOS;
   protected UnsyncByteArrayOutputStream bufferedPostOS;
   protected boolean setRequests;
   protected boolean useHttp11;
   private boolean wroteRequests;
   private SocketFactory socketFactory;
   private String redirectCookieStr;
   private static final String COOKIE_SEPARATOR = ";";
   private static boolean sendCookiesRedirect = false;
   private int readTimeout;
   private int connectTimeout;
   private int chunkLength;
   private int fixedContentLength;
   private CookieHandler cookieHandler;
   private boolean setUserCookies;
   private String userCookies;
   private String userCookies2;
   private static int defaultReadTimeout;
   protected static int defaultConnectTimeout;
   protected IOException rememberedException;
   protected boolean ignoreSystemNonProxyHosts;
   protected boolean ignoreProxy;
   private static SocketFactory defaultSocketFactory;
   private static final String[] EXCLUDE_HEADERS = new String[]{"Proxy-Authorization", "Authorization"};
   private static final String[] EXCLUDE_HEADERS2 = new String[]{"Proxy-Authorization", "Authorization", "Cookie", "Cookie2"};
   private static boolean retryPost = true;
   private boolean triedOnce;
   private boolean fixedLengthStreamingModeEnabled;
   private boolean chunkStreamingModeEnabled;

   public HttpURLConnection(URL u) {
      this(u, (Proxy)null);
   }

   public HttpURLConnection(URL u, Proxy p) {
      super(u);
      this.inputStream = null;
      this.streamedPostOS = null;
      this.bufferedPostOS = null;
      this.setRequests = false;
      this.useHttp11 = http11;
      this.wroteRequests = false;
      this.redirectCookieStr = null;
      this.readTimeout = defaultReadTimeout;
      this.connectTimeout = defaultConnectTimeout;
      this.chunkLength = -1;
      this.fixedContentLength = -1;
      this.setUserCookies = true;
      this.userCookies = null;
      this.userCookies2 = null;
      this.rememberedException = null;
      this.ignoreSystemNonProxyHosts = false;
      this.ignoreProxy = false;
      this.triedOnce = false;
      this.fixedLengthStreamingModeEnabled = false;
      this.chunkStreamingModeEnabled = false;
      this.requests = new MessageHeader();
      this.responses = new MessageHeader();
      this.instProxy = p == null ? Proxy.NO_PROXY : p;
      this.cookieHandler = HttpClient.getCookieHandler();
   }

   protected String getProtocol() {
      return "http";
   }

   public final void setSocketFactory(SocketFactory fact) {
      this.socketFactory = fact;
   }

   public final SocketFactory getSocketFactory() {
      return this.socketFactory != null ? this.socketFactory : defaultSocketFactory;
   }

   public static void setDefaultSocketFactory(SocketFactory nuDefaultSF) {
      defaultSocketFactory = nuDefaultSF;
   }

   protected synchronized void writeRequests() throws IOException {
      if (!this.wroteRequests) {
         this.wroteRequests = true;
         if (!this.setRequests) {
            this.doSetRequests();
         }

         this.setCookieHeader();
         this.requests.print(this.http.getOutputStream());
         this.http.getOutputStream().flush();
         if (debug) {
            p("wrote request - " + this.requests);
         }

         if (this.http != null) {
            this.http.setLastUsed(System.currentTimeMillis());
         }

         if (this.bufferedPostOS != null) {
            this.bufferedPostOS.writeTo(this.http.getOutputStream());
            this.http.getOutputStream().flush();
         }

      }
   }

   public void setIgnoreSystemNonProxyHosts(boolean v) {
      this.ignoreSystemNonProxyHosts = v;
   }

   public void setIgnoreProxy(boolean v) {
      this.ignoreProxy = v;
   }

   public void connect() throws IOException {
      if (!this.connected) {
         try {
            this.http = HttpClient.New(this.url, this.instProxy, this.getSocketFactory(), this.useHttp11, this.getConnectTimeout(), this.getReadTimeout(), this.ignoreSystemNonProxyHosts, this.ignoreProxy);
         } catch (SocketTimeoutException var2) {
            this.rememberedException = var2;
            throw var2;
         }

         this.http.setConnection(this);
         this.connected = true;
         if (debug) {
            p("connected " + this.http + " HTTP/1." + (this.useHttp11 ? "1" : "0"));
         }

      }
   }

   protected HttpClient getHttpClient() throws IOException {
      HttpClient h = HttpClient.New(this.url, this.instProxy, this.getSocketFactory(), this.useHttp11, this.getConnectTimeout(), this.getReadTimeout(), false, this.ignoreSystemNonProxyHosts, this.ignoreProxy);
      h.setConnection(this);
      this.connected = true;
      if (debug) {
         p("new HttpClient=" + h);
      }

      return h;
   }

   public synchronized OutputStream getOutputStream() throws IOException {
      try {
         if (!this.doOutput) {
            throw new ProtocolException("cannot write to a URLConnection if doOutput=false - call setDoOutput(true)");
         } else {
            if (this.method.equals("GET")) {
               this.setRequestMethod("POST");
            }

            if (("HEAD".equals(this.method) || "OPTIONS".equals(this.method) || "DELETE".equals(this.method) || "TRACE".equals(this.method)) && this.getProtocol().equals(this.url.getProtocol())) {
               throw new ProtocolException("HTTP method " + this.method + " doesn't support output");
            } else if (this.inputStream != null) {
               throw new ProtocolException("Cannot write output after reading input.");
            } else {
               if (!bufferPostForRetry) {
                  if (this.useHttp11 && this.chunkStreamingModeEnabled) {
                     this.connect();
                     this.writeRequests();
                     this.streamedPostOS = this.chunkLength > 0 ? new HttpChunkOutputStream(this.http.getOutputStream(), this.chunkLength) : new HttpChunkOutputStream(this.http.getOutputStream());
                     if (debug) {
                        p("using chunked streaming. ChunkSize=" + (this.chunkLength > 0 ? this.chunkLength : Chunk.CHUNK_SIZE - 6 - 2));
                     }

                     return this.streamedPostOS;
                  }

                  if (this.hasFixedContentLength()) {
                     this.connect();
                     this.writeRequests();
                     String clen = this.getRequestProperty("Content-Length");
                     this.streamedPostOS = new ContentLengthOutputStream(this.http.getOutputStream(), Integer.parseInt(clen));
                     if (debug) {
                        p("using content length streaming. CL=" + Integer.parseInt(clen));
                     }

                     return this.streamedPostOS;
                  }
               }

               if (this.bufferedPostOS == null) {
                  this.bufferedPostOS = new UnsyncByteArrayOutputStream();
               } else if (!this.connected) {
                  this.bufferedPostOS.reset();
               }

               if (debug) {
                  p("using buffered stream");
               }

               return this.bufferedPostOS;
            }
         }
      } catch (RuntimeException var2) {
         this.disconnect();
         throw var2;
      } catch (IOException var3) {
         this.disconnect();
         throw var3;
      }
   }

   private boolean hasFixedContentLength() {
      String clen = this.getRequestProperty("Content-Length");
      if (clen == null && this.useHttp11 && this.fixedContentLength > 0) {
         clen = Integer.toString(this.fixedContentLength);
      }

      return clen != null;
   }

   protected static String getProxyAuthString() {
      return proxyAuthString;
   }

   public static String getAuthInfo(String host, int port, String header) throws IOException {
      if (debug) {
         p("getAuthInfo(" + host + ", " + port + ", " + header + ") called");
      }

      if (proxyAuthClassName != null && header != null) {
         if (debug) {
            p("using ProxyAuthenticator = " + proxyAuthClassName);
         }

         ProxyAuthenticator pauth = null;
         String authType = null;
         String authPrompt = null;
         String authHeader = header.trim();
         int ind = authHeader.indexOf(32);
         if (ind == -1) {
            authType = authHeader;
            authPrompt = "Login to Proxy";
         } else {
            authType = authHeader.substring(0, ind);
            authPrompt = authHeader.substring(ind + 1);
            ind = authPrompt.indexOf(61);
            if (ind != -1) {
               authPrompt = authPrompt.substring(ind + 1);
            }
         }

         try {
            pauth = (ProxyAuthenticator)Class.forName(proxyAuthClassName).newInstance();
         } catch (Exception var13) {
            throw new HttpUnauthorizedException("Proxy authenticator " + proxyAuthClassName + " failed: " + var13);
         }

         pauth.init(host, port, authType, authPrompt);
         String[] unp = pauth.getLoginAndPassword();
         if (unp != null && unp.length == 2) {
            String unpstr = unp[0] + ':' + unp[1];
            byte[] buf = unpstr.getBytes();
            BASE64Encoder benc = new BASE64Encoder();
            String ret = "Basic " + benc.encodeBuffer(buf);
            if (debug) {
               p("getAuthString() returning '" + ret + "'");
            }

            return ret;
         } else {
            throw new HttpUnauthorizedException("Proxy authentication failed");
         }
      } else {
         throw new HttpUnauthorizedException("Proxy or Server Authentication Required");
      }
   }

   public static String getProxyBasicCredentials(String proxyHost, int proxyPort, String authHeader, URL requestUrl) throws IOException {
      if (debug) {
         p("getProxyBasicCredentials(" + proxyHost + ", " + proxyPort + ", " + authHeader + ", " + requestUrl + ") called");
      }

      if (authHeader == null) {
         throw new HttpUnauthorizedException("Proxy authentication required but Proxy-Authenticate header is null");
      } else {
         String[] schemeAndRealm = getSchemeAndRealm(authHeader.trim());
         InetAddress requestInetAddress = getRequestInetAddress(proxyHost);
         String unpstr = null;
         PasswordAuthentication auth = priviledgedRequestPasswordAuthentication(proxyHost, requestInetAddress, proxyPort, "http", schemeAndRealm[1], schemeAndRealm[0], requestUrl, RequestorType.PROXY);
         if (auth == null) {
            if (proxyAuthClassName == null) {
               throw new HttpUnauthorizedException("Proxy Authentication Required");
            }

            ProxyAuthenticator pauth = null;

            try {
               pauth = (ProxyAuthenticator)Class.forName(proxyAuthClassName).newInstance();
            } catch (Exception var11) {
               throw new HttpUnauthorizedException("Proxy authenticator " + proxyAuthClassName + " failed: " + var11);
            }

            pauth.init(proxyHost, proxyPort, schemeAndRealm[0], schemeAndRealm[1]);
            String[] unp = pauth.getLoginAndPassword();
            if (unp == null || unp.length != 2) {
               throw new HttpUnauthorizedException("Proxy authentication failed");
            }

            unpstr = unp[0] + ':' + unp[1];
         } else {
            unpstr = auth.getUserName() + ':' + new String(auth.getPassword());
         }

         byte[] buf = unpstr.getBytes();
         BASE64Encoder benc = new BASE64Encoder();
         String ret = "Basic " + benc.encodeBuffer(buf);
         if (debug) {
            p("getProxyBasicCredentials() returning '" + ret + "'");
         }

         return ret;
      }
   }

   public static String getServerBasicCredentials(URL requestUrl, String authHeader) throws IOException {
      if (debug) {
         p("getServerBasicCredentials(" + requestUrl + ", " + authHeader + ") called");
      }

      if (authHeader == null) {
         throw new HttpUnauthorizedException("Server authentication required but WWW-Authenticate header is null");
      } else {
         String[] schemeAndRealm = getSchemeAndRealm(authHeader.trim());
         InetAddress requestInetAddress = getRequestInetAddress(requestUrl.getHost());
         int requestPort = getRequestPort(requestUrl);
         PasswordAuthentication auth = priviledgedRequestPasswordAuthentication(requestUrl.getHost(), requestInetAddress, requestPort, requestUrl.getProtocol(), schemeAndRealm[1], schemeAndRealm[0], requestUrl, RequestorType.SERVER);
         if (auth == null) {
            throw new HttpUnauthorizedException("Server Authentication Required");
         } else {
            String unpstr = auth.getUserName() + ':' + new String(auth.getPassword());
            byte[] buf = unpstr.getBytes();
            BASE64Encoder benc = new BASE64Encoder();
            String ret = "Basic " + benc.encodeBuffer(buf);
            if (debug) {
               p("getServerBasicCredentials() returning '" + ret + "'");
            }

            return ret;
         }
      }
   }

   private static String[] getSchemeAndRealm(String authHeader) throws IOException {
      String authScheme = null;
      String authPrompt = null;
      int ind = authHeader.indexOf(32);
      if (ind == -1) {
         authScheme = authHeader;
         authPrompt = "";
      } else {
         authScheme = authHeader.substring(0, ind);
         authPrompt = authHeader.substring(ind + 1);
         ind = authPrompt.indexOf(61);
         if (ind != -1) {
            authPrompt = authPrompt.substring(ind + 1);
            if (authPrompt.length() >= 2 && authPrompt.charAt(0) == '"' && authPrompt.charAt(authPrompt.length() - 1) == '"') {
               authPrompt = authPrompt.substring(1, authPrompt.length() - 1);
            } else {
               authPrompt = "";
            }
         }
      }

      if (!"basic".equalsIgnoreCase(authScheme)) {
         throw new HttpUnauthorizedException("Auth scheme " + authScheme + " is not supported!");
      } else {
         return new String[]{authScheme.toLowerCase(), authPrompt};
      }
   }

   private static InetAddress getRequestInetAddress(String host) {
      InetAddress requestInetAddress = null;

      try {
         requestInetAddress = InetAddress.getByName(host);
      } catch (UnknownHostException var3) {
      }

      return requestInetAddress;
   }

   private static int getRequestPort(URL requestUrl) {
      int requestPort = requestUrl.getPort();
      if (requestPort == -1) {
         requestPort = requestUrl.getDefaultPort();
      }

      return requestPort;
   }

   private static PasswordAuthentication priviledgedRequestPasswordAuthentication(final String host, final InetAddress addr, final int port, final String protocol, final String prompt, final String scheme, final URL url, final Authenticator.RequestorType authType) {
      return KernelStatus.isServer() ? (PasswordAuthentication)Security.runAs(SubjectManager.getSubjectManager().getAnonymousSubject().getSubject(), new PrivilegedAction() {
         public PasswordAuthentication run() {
            return Authenticator.requestPasswordAuthentication(host, addr, port, protocol, prompt, scheme, url, authType);
         }
      }) : Authenticator.requestPasswordAuthentication(host, addr, port, protocol, prompt, scheme, url, authType);
   }

   public synchronized InputStream getInputStream() throws IOException {
      if (!this.doInput) {
         throw new ProtocolException("Cannot read from URLConnection if doInput=false (call setDoInput(true))");
      } else if (this.responseCode >= 400 && this.rememberedException != null) {
         throw this.rememberedException;
      } else if (this.inputStream != null) {
         if (this.http != null) {
            this.http.setLastUsed(System.currentTimeMillis());
         }

         return this.inputStream;
      } else {
         if (this.streamedPostOS != null) {
            try {
               this.streamedPostOS.close();
            } catch (IOException var8) {
               this.disconnect();
               throw var8;
            }
         }

         int redirects = 0;
         int tries = 0;
         int maxTries = 3;
         if (this.getMethod().equals("POST") && !retryPost) {
            maxTries = 1;
            if (this.triedOnce) {
               throw this.rememberedException;
            }
         }

         do {
            String authStr;
            try {
               if (debug) {
                  p("########### connecting try " + tries + " of " + maxTries);
               }

               this.triedOnce = true;
               this.connect();
               this.writeRequests();
               this.http.parseHTTP(this.responses);
            } catch (HttpUnauthorizedException var9) {
               if (debug) {
                  p("Authentication required for this request");
               }

               this.disconnect();
               this.wroteRequests = false;
               this.setRequests = false;
               this.requests = new MessageHeader();
               authStr = new String("-Authenticate: Basic Realm=WebLogic Realm");
               proxyAuthString = getProxyBasicCredentials(this.getURL().getHost(), this.getURL().getPort(), var9.getMessage() + authStr, this.url);
               ++redirects;
               continue;
            } catch (ConnectException var10) {
               if (debug) {
                  p("ConnectException " + var10.getMessage());
               }

               throw var10;
            } catch (InterruptedIOException var11) {
               if (debug) {
                  p("InterruptedIOException " + var11.getMessage());
               }

               throw var11;
            } catch (SSLException var12) {
               if (debug) {
                  p("SSLException " + var12.getMessage());
               }

               throw var12;
            } catch (IOException var13) {
               if (debug) {
                  p("IOException " + var13.getMessage());
               }

               if (this.rememberedException != null && this.rememberedException instanceof SocketTimeoutException) {
                  throw var13;
               }

               if (this.http != null) {
                  InputStream existingInputStream = this.http.getInputStream();
                  if (var13 instanceof SocketTimeoutException || existingInputStream != null && (existingInputStream instanceof SocketClosedNotification || existingInputStream instanceof SocketTimeoutNotification)) {
                     throw var13;
                  }
               }

               ++tries;
               if (tries >= maxTries) {
                  this.rememberedException = var13;
                  throw var13;
               }

               this.wroteRequests = false;
               if (this.http != null) {
                  this.http.closeServer();
               }

               try {
                  Thread.sleep((long)(tries * 100));
               } catch (InterruptedException var7) {
               }

               this.connected = false;
               this.http = this.getHttpClient();
               continue;
            }

            this.inputStream = this.http.getInputStream();
            int respCode = this.getResponseCode();
            if (debug) {
               p("response - " + this.responses);
            }

            if (respCode == 100) {
               this.handleContinueResponse();
            }

            if (this.followRedirect()) {
               this.wroteRequests = false;
               ++redirects;
            } else {
               if (this.method.equals("HEAD") || respCode == 204) {
                  this.disconnect();
                  if (this.http != null) {
                     this.http.setLastUsed(System.currentTimeMillis());
                  }

                  return this.inputStream = new NullInputStream();
               }

               if (respCode == 407) {
                  if (debug) {
                     p("Proxy Authentication required");
                  }

                  authStr = this.http.getProxyHost();
                  int proxyPort = this.http.getProxyPort();
                  this.disconnect();
                  this.wroteRequests = false;
                  proxyAuthString = getProxyBasicCredentials(authStr, proxyPort, this.responses.findValue("Proxy-Authenticate"), this.url);
                  if (proxyAuthString != null) {
                     this.requests.set("Proxy-Authorization", proxyAuthString);
                  }

                  ++redirects;
               } else {
                  if (respCode != 401) {
                     if (respCode >= 400) {
                        authStr = HttpReasonPhraseCoder.getReasonPhrase(respCode);
                        throw new FileNotFoundException("Response: '" + respCode + ": " + authStr + "' for url: '" + this.toStringWithoutUserinfo(this.url) + "'");
                     }

                     if (this.http != null) {
                        this.http.setLastUsed(System.currentTimeMillis());
                     }

                     return this.inputStream;
                  }

                  if (debug) {
                     p("Server Authentication required");
                  }

                  this.wroteRequests = false;
                  authStr = getServerBasicCredentials(this.url, this.responses.findValue("WWW-Authenticate"));
                  this.requests.set("Authorization", authStr);
                  this.disconnect();
                  ++redirects;
               }
            }
         } while(redirects < 5);

         throw new ProtocolException("Server redirected too many times (" + redirects + ")");
      }
   }

   void writeRequestForAsyncResponse() throws IOException {
      int tries = 0;

      while(true) {
         try {
            this.connect();
            this.writeRequests();
            return;
         } catch (IOException var5) {
            ++tries;
            if (tries >= 3) {
               throw var5;
            }

            this.wroteRequests = false;
            if (this.http != null) {
               this.http.closeServer();
            }

            try {
               Thread.sleep((long)(tries * 100));
            } catch (InterruptedException var4) {
            }

            this.connected = false;
            this.http = this.getHttpClient();
         }
      }
   }

   private void setCookieHeader() throws IOException {
      if (this.cookieHandler != null) {
         synchronized(this) {
            if (this.setUserCookies) {
               this.userCookies = this.requests.findValue("Cookie");
               this.userCookies2 = this.requests.findValue("Cookie2");
               if (debug) {
                  p("userCookies retrieved: " + this.userCookies);
                  p("userCookies2 retrieved: " + this.userCookies2);
               }

               this.setUserCookies = false;
            }
         }

         this.requests.remove("Cookie");
         this.requests.remove("Cookie2");
         StringBuilder cookieBuilder = new StringBuilder();
         StringBuilder cookie2Builder = new StringBuilder();
         final URI uri = ParseUtil.toURI(this.url);
         if (uri != null) {
            if (debug) {
               p("CookieHandler request for " + uri);
            }

            Map cookies = null;
            if (KernelStatus.isServer()) {
               cookies = (Map)Security.runAs(SubjectManager.getSubjectManager().getAnonymousSubject().getSubject(), new PrivilegedAction() {
                  public Map run() {
                     try {
                        return HttpURLConnection.this.cookieHandler.get(uri, HttpURLConnection.this.responses.getHeaders(HttpURLConnection.EXCLUDE_HEADERS));
                     } catch (IOException var2) {
                        if (HttpURLConnection.debug) {
                           HttpURLConnection.p(var2.toString());
                        }

                        return null;
                     }
                  }
               });
            } else {
               cookies = this.cookieHandler.get(uri, this.responses.getHeaders(EXCLUDE_HEADERS));
            }

            if (cookies != null && !cookies.isEmpty()) {
               if (debug) {
                  p("Cookies retrieved: " + cookies.toString());
               }

               Iterator var5 = cookies.entrySet().iterator();

               label104:
               while(true) {
                  String key;
                  List valueList;
                  do {
                     do {
                        Map.Entry entry;
                        do {
                           if (!var5.hasNext()) {
                              break label104;
                           }

                           entry = (Map.Entry)var5.next();
                           key = (String)entry.getKey();
                        } while(!"Cookie".equalsIgnoreCase(key) && !"Cookie2".equalsIgnoreCase(key));

                        valueList = (List)entry.getValue();
                     } while(valueList == null);
                  } while(valueList.isEmpty());

                  StringBuilder cookieValue = new StringBuilder();
                  Iterator var10 = valueList.iterator();

                  while(var10.hasNext()) {
                     String value = (String)var10.next();
                     cookieValue.append(value).append("; ");
                  }

                  cookieValue.setLength(cookieValue.length() - 2);
                  if ("Cookie".equalsIgnoreCase(key)) {
                     cookieBuilder.append(cookieValue);
                  } else {
                     cookie2Builder.append(cookieValue);
                  }
               }
            }
         }

         if (this.userCookies != null) {
            if (cookieBuilder.length() > 0) {
               cookieBuilder.append("; ").append(this.userCookies);
            } else {
               cookieBuilder.append(this.userCookies);
            }
         }

         if (this.userCookies2 != null) {
            if (cookie2Builder.length() > 0) {
               cookie2Builder.append("; ").append(this.userCookies2);
            } else {
               cookie2Builder.append(this.userCookies2);
            }
         }

         if (debug) {
            p("The Cookie for current request: " + cookieBuilder.toString());
            p("The Cookie2 for current request: " + cookie2Builder.toString());
         }

         if (cookieBuilder.length() > 0) {
            this.requests.set("Cookie", cookieBuilder.toString());
         }

         if (cookie2Builder.length() > 0) {
            this.requests.set("Cookie2", cookie2Builder.toString());
         }
      }

   }

   private void handleContinueResponse() throws IOException {
      this.responseCode = -1;
      this.responses = new MessageHeader();
      this.http.parseHTTP(this.responses);
      this.inputStream = this.http.getInputStream();
      this.http.setLastUsed(System.currentTimeMillis());
      if (debug) {
         p("handled 100 Continue. status=" + this.getResponseCode());
      }

   }

   public InputStream getErrorStream() {
      if (this.http != null) {
         this.http.setLastUsed(System.currentTimeMillis());
      }

      return this.connected && this.responseCode >= 400 ? this.inputStream : null;
   }

   protected boolean followRedirect() throws IOException {
      if (!this.getInstanceFollowRedirects()) {
         return false;
      } else {
         int stat = this.getResponseCode();
         if (stat >= 300 && stat <= 307 && stat != 304) {
            String loc = this.getHeaderField("Location");
            if (loc == null) {
               return false;
            } else {
               URL u = new URL(this.getURL(), loc);
               if (!u.getProtocol().equals(u.getProtocol())) {
                  return false;
               } else {
                  if (debug) {
                     p("followRedirect Location=" + loc);
                  }

                  this.disconnect();
                  MessageHeader oldResponses = this.responses;
                  this.responses = new MessageHeader();
                  StringBuffer cookiesSB = new StringBuffer();
                  int i = 0;

                  while(true) {
                     String k = oldResponses.getKey(i);
                     String v = oldResponses.getValue(i);
                     if (k == null && v == null) {
                        if (sendCookiesRedirect && cookiesSB.length() != 0) {
                           this.redirectCookieStr = cookiesSB.substring(0, cookiesSB.length() - ";".length());
                           this.requests.add("Cookie", this.redirectCookieStr);
                        }

                        if (stat == 305) {
                           this.requests.set(0, this.method + " " + this.http.getURLFile() + " " + this.getHttpVersion(), (String)null);
                           this.connected = true;
                        } else {
                           this.url = new URL(this.url, loc);
                           if (this.method.equals("POST") && !strictPostRedirect) {
                              this.requests = new MessageHeader();
                              this.setRequests = false;
                              this.setRequestMethod("GET");
                              this.bufferedPostOS = null;
                              this.connect();
                           } else {
                              this.connect();
                              this.requests.set(0, this.method + " " + this.http.getURLFile() + " " + this.getHttpVersion(), (String)null);
                              this.requests.set("Host", this.url.getHost() + (this.url.getPort() != -1 && this.url.getPort() != 80 ? ":" + String.valueOf(this.url.getPort()) : ""));
                           }
                        }

                        return true;
                     }

                     if (k != null && v != null && k.equalsIgnoreCase("Set-Cookie")) {
                        if (sendCookiesRedirect) {
                           cookiesSB.append(v.split(";", 2)[0]);
                           cookiesSB.append(";");
                        } else {
                           this.responses.add(k, v);
                        }
                     }

                     ++i;
                  }
               }
            }
         } else {
            return false;
         }
      }
   }

   public void disconnect() {
      this.responseCode = -1;
      if (this.http != null) {
         if (debug) {
            p("disconnect called on " + this.http);
         }

         this.http.closeServer();
         this.http = null;
         this.connected = false;
      }

   }

   boolean isConnected() {
      return this.connected;
   }

   public boolean usingProxy() {
      return this.http != null ? this.http.usingProxy : false;
   }

   public String getHeaderField(String name) {
      try {
         this.getInputStream();
      } catch (IOException var3) {
         if (var3 instanceof SocketTimeoutException) {
            this.rememberedException = var3;
         }
      }

      return this.responses.findValue(name);
   }

   public String getHeaderField(int n) {
      try {
         this.getInputStream();
      } catch (IOException var3) {
      }

      return this.responses.getValue(n);
   }

   public Map getHeaderFields() {
      try {
         this.getInputStream();
      } catch (IOException var2) {
      }

      return this.responses.getHeaders();
   }

   public String getHeaderFieldKey(int n) {
      try {
         this.getInputStream();
      } catch (IOException var3) {
      }

      return this.responses.getKey(n);
   }

   public void addRequestProperty(String key, String value) {
      if (this.connected) {
         throw new IllegalAccessError("Already connected");
      } else if (key == null) {
         throw new IllegalArgumentException("key is null");
      } else if (value == null) {
         throw new IllegalArgumentException("value is null");
      } else {
         if (key.length() > 0) {
            this.requests.add(key, value);
         }

      }
   }

   public synchronized Map getRequestProperties() {
      if (this.setUserCookies) {
         return this.requests.getHeaders(EXCLUDE_HEADERS);
      } else {
         Map userCookiesMap = null;
         if (this.userCookies != null || this.userCookies2 != null) {
            userCookiesMap = new HashMap();
            if (this.userCookies != null) {
               userCookiesMap.put("Cookie", this.userCookies);
            }

            if (this.userCookies2 != null) {
               userCookiesMap.put("Cookie2", this.userCookies2);
            }
         }

         return this.requests.filterAndAddHeaders(EXCLUDE_HEADERS2, userCookiesMap);
      }
   }

   public void setRequestProperty(String key, String value) {
      if (this.connected) {
         throw new IllegalAccessError("Already connected");
      } else if (key == null) {
         throw new IllegalArgumentException("key is null");
      } else if (value == null) {
         throw new IllegalArgumentException("value is null");
      } else {
         if (key.length() > 0) {
            this.requests.set(key, value);
         }

      }
   }

   public void setEmptyRequestProperty(String key) {
      if (this.connected) {
         throw new IllegalAccessError("Already connected");
      } else {
         if (key.length() > 0) {
            this.requests.set(key, "");
         }

      }
   }

   public synchronized String getRequestProperty(String key) {
      if (key == null) {
         return null;
      } else {
         for(int i = 0; i < EXCLUDE_HEADERS.length; ++i) {
            if (key.equalsIgnoreCase(EXCLUDE_HEADERS[i])) {
               return null;
            }
         }

         if (!this.setUserCookies) {
            if (key.equalsIgnoreCase("Cookie")) {
               return this.userCookies;
            }

            if (key.equalsIgnoreCase("Cookie2")) {
               return this.userCookies2;
            }
         }

         return this.requests.findValue(key);
      }
   }

   String getMethod() {
      return this.method;
   }

   protected void doSetRequests() {
      this.requests.prepend(this.method + " " + this.http.getURLFile() + " " + this.getHttpVersion(), (String)null);
      this.requests.setIfNotSet("User-Agent", userAgent);
      int port = this.url.getPort();
      String host = this.url.getHost();
      if (port != -1 && port != 80) {
         host = host + ":" + port;
      }

      this.requests.setIfNotSet("Host", host);
      this.requests.setIfNotSet("Accept", "text/html, image/gif, image/jpeg, */*; q=.2");
      if (proxyAuthString != null) {
         this.requests.set("Proxy-Authorization", proxyAuthString);
      }

      if (sendCookiesRedirect && this.redirectCookieStr != null) {
         this.requests.setIfNotSet("Cookie", this.redirectCookieStr);
      }

      if (this.http.getHttpKeepAliveSet()) {
         this.requests.setIfNotSet("Connection", "Keep-Alive");
      }

      if (!bufferPostForRetry) {
         if (this.useHttp11 && this.chunkStreamingModeEnabled) {
            this.requests.set("Transfer-Encoding", "chunked");
         }

         if (this.useHttp11 && this.fixedContentLength > 0) {
            this.requests.setIfNotSet("Content-Length", Integer.toString(this.fixedContentLength));
         }
      }

      if (this.bufferedPostOS != null) {
         synchronized(this.bufferedPostOS) {
            this.requests.setIfNotSet("Content-Type", "application/x-www-form-urlencoded");
            this.requests.set("Content-Length", String.valueOf(this.bufferedPostOS.size()));
         }
      }

      Collection contributors = HttpContributorRegistrar.getHttpContributorRegistrar().getRequestHeaderContributors();
      Iterator var4 = contributors.iterator();

      while(var4.hasNext()) {
         HttpRequestHeaderContributor contributor = (HttpRequestHeaderContributor)var4.next();
         Set headersToAdd = contributor.getHeadersForOutgoingRequest().entrySet();
         Iterator var7 = headersToAdd.iterator();

         while(var7.hasNext()) {
            Map.Entry headerToAdd = (Map.Entry)var7.next();
            if (this.getRequestProperty((String)headerToAdd.getKey()) == null) {
               this.requests.add((String)headerToAdd.getKey(), (String)headerToAdd.getValue());
            }

            if (debug) {
               p("ERROR with HttpRequestHeaderContributor: " + contributor + " attempting to add existing HTTP header: " + (String)headerToAdd.getKey());
            }
         }
      }

      this.setRequests = true;
   }

   /** @deprecated */
   @Deprecated
   public void setTimeout(int i) {
      this.setReadTimeout(i);
   }

   /** @deprecated */
   @Deprecated
   public int getTimeout() {
      return this.getReadTimeout();
   }

   public void setReadTimeout(int i) {
      this.readTimeout = i;
      if (this.http != null) {
         this.http.setReadTimeout(i);
      }

   }

   public int getReadTimeout() {
      return this.readTimeout;
   }

   public void setConnectTimeout(int i) {
      this.connectTimeout = i;
   }

   public int getConnectTimeout() {
      return this.connectTimeout;
   }

   public void setFixedLengthStreamingMode(int fixedContentLength) {
      if (this.chunkStreamingModeEnabled) {
         throw new IllegalStateException("Chunked encoding streaming mode set");
      } else {
         this.fixedLengthStreamingModeEnabled = true;
         this.fixedContentLength = fixedContentLength;
      }
   }

   public void setChunkedStreamingMode(int chunkLength) {
      if (this.fixedLengthStreamingModeEnabled) {
         throw new IllegalStateException("Fixed length streaming mode set");
      } else {
         this.chunkStreamingModeEnabled = true;
         this.chunkLength = chunkLength;
      }
   }

   public int getResponseCode() throws IOException {
      if (this.responseCode != -1) {
         return this.responseCode;
      } else if (this.rememberedException != null && this.rememberedException instanceof SocketTimeoutException) {
         throw this.rememberedException;
      } else {
         IOException ioe = null;

         try {
            this.getInputStream();
         } catch (SocketTimeoutException var5) {
            this.rememberedException = var5;
            throw var5;
         } catch (ConnectException var6) {
            throw var6;
         } catch (InterruptedIOException var7) {
            throw var7;
         } catch (SSLException var8) {
            throw var8;
         } catch (IOException var9) {
            ioe = var9;
         }

         String resp = this.getHeaderField(0);
         if (resp == null && ioe != null) {
            throw ioe;
         } else {
            this.rememberedException = ioe;

            try {
               int ind;
               for(ind = resp.indexOf(32); resp.charAt(ind) == ' '; ++ind) {
               }

               this.responseCode = Integer.parseInt(resp.substring(ind, ind + 3));
               this.responseMessage = resp.substring(ind + 4).trim();
               return this.responseCode;
            } catch (Exception var10) {
               return this.responseCode;
            }
         }
      }
   }

   String getHttpVersion() {
      return this.useHttp11 ? "HTTP/1.1" : "HTTP/1.0";
   }

   private String toStringWithoutUserinfo(URL u) {
      StringBuilder result = new StringBuilder();
      result.append(u.getProtocol());
      result.append(":");
      String hostPort = u.getPort() == -1 ? u.getHost() : u.getHost() + ":" + u.getPort();
      if (hostPort != null && hostPort.length() > 0) {
         result.append("//");
         result.append(hostPort);
      }

      if (u.getPath() != null) {
         result.append(u.getPath());
      }

      if (u.getQuery() != null) {
         result.append('?');
         result.append(u.getQuery());
      }

      if (u.getRef() != null) {
         result.append("#");
         result.append(u.getRef());
      }

      return result.toString();
   }

   public void u11() {
      if (!this.connected) {
         this.useHttp11 = true;
         if (debug) {
            p("will use HTTP/1.1");
         }
      }

   }

   void finish() {
      this.http = null;
   }

   static final void p(String s) {
      System.out.println("[" + new Date() + "] [" + Thread.currentThread().getName() + "] " + s);
   }

   private static boolean getBoolean(String prop, boolean def) {
      String s = SecurityHelper.getSystemProperty(prop);
      return s != null ? Boolean.valueOf(s) : def;
   }

   Socket getSocket() {
      return this.http.getSocket();
   }

   void setInputStream(InputStream inputStream) {
      this.http.setInputStream(inputStream);
   }

   public void setMuxableSocket(Object socket) {
      this.http.setMuxableSocket(socket);
   }

   Object getMuxableSocket() {
      return this.http.getMuxableSocket();
   }

   void setScavenger(Runnable scavenger) {
      this.http.setScavenger(scavenger);
   }

   public void setRequestMethod(String method) throws ProtocolException {
      if (this.connected) {
         throw new ProtocolException("Can't reset method: already connected");
      } else if (method == null) {
         throw new ProtocolException("Invalid HTTP method: " + method);
      } else if (method.length() > 32) {
         throw new ProtocolException("Invalid HTTP method: " + method + ", maximum length allowed is " + 32);
      } else if (!HttpParsing.isTokenClean(method)) {
         throw new ProtocolException("Invalid HTTP method: " + method + ", invalid character(s) was found");
      } else {
         this.method = method;
      }
   }

   static {
      boolean httpDebug = false;
      userAgent = "Java" + SecurityHelper.getSystemProperty("java.version");

      try {
         userAgent = SecurityHelper.getSystemProperty("http.agent", userAgent);
         defaultReadTimeout = SecurityHelper.getInteger("weblogic.http.client.defaultReadTimeout", -1);
         defaultConnectTimeout = SecurityHelper.getInteger("weblogic.http.client.defaultConnectTimeout", -1);
         strictPostRedirect = getBoolean("http.strictPostRedirect", strictPostRedirect);
         sendCookiesRedirect = getBoolean("weblogic.http.sendCookiesRedirect", sendCookiesRedirect);
         httpDebug = getBoolean("http.debug", httpDebug);
         http11 = getBoolean("http.11", http11);
         bufferPostForRetry = getBoolean("http.bufferPostForRetry", bufferPostForRetry);
         proxyAuthClassName = SecurityHelper.getSystemProperty("weblogic.net.proxyAuthenticatorClassName");
         retryPost = getBoolean("http.retryPost", retryPost);
      } catch (SecurityException var2) {
         defaultReadTimeout = -1;
         defaultConnectTimeout = -1;
      }

      debug = httpDebug;
   }
}
