package org.glassfish.grizzly.http.server;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.Cookies;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.Note;
import org.glassfish.grizzly.http.Protocol;
import org.glassfish.grizzly.http.io.InputBuffer;
import org.glassfish.grizzly.http.io.NIOInputStream;
import org.glassfish.grizzly.http.io.NIOReader;
import org.glassfish.grizzly.http.server.http2.PushBuilder;
import org.glassfish.grizzly.http.server.io.ServerInputBuffer;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.server.util.ParameterMap;
import org.glassfish.grizzly.http.server.util.RequestUtils;
import org.glassfish.grizzly.http.server.util.SimpleDateFormats;
import org.glassfish.grizzly.http.server.util.StringParser;
import org.glassfish.grizzly.http.util.Chunk;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.FastHttpDateFormat;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.Parameters;
import org.glassfish.grizzly.http.util.DataChunk.Type;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.utils.Charsets;
import org.glassfish.grizzly.utils.JdkVersion;

public class Request {
   private static final Boolean FORCE_CLIENT_AUTH_ON_GET_USER_PRINCIPAL = Boolean.getBoolean(Request.class.getName() + ".force-client-auth-on-get-user-principal");
   private static final Logger LOGGER = Grizzly.logger(Request.class);
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(Request.class, 16);
   private static final String HTTP2_PUSH_ENABLED = "http2-push-enabled";
   private static final LocaleParser localeParser;
   public static final String SEND_FILE_ENABLED_ATTR = "org.glassfish.grizzly.http.SEND_FILE_ENABLED";
   public static final String SEND_FILE_ATTR = "org.glassfish.grizzly.http.SEND_FILE";
   public static final String SEND_FILE_START_OFFSET_ATTR = "org.glassfish.grizzly.http.FILE_START_OFFSET";
   public static final String SEND_FILE_WRITE_LEN_ATTR = "org.glassfish.grizzly.http.FILE_WRITE_LEN";
   private static final String match = ";jsessionid=";
   protected HttpRequestPacket request;
   protected FilterChainContext ctx;
   protected HttpServerFilter httpServerFilter;
   protected final List afterServicesList = new ArrayList(4);
   private Session session;
   private String scheme;
   private final PathData contextPath = new PathData(this, "", (PathResolver)null);
   private final PathData httpHandlerPath = new PathData(this);
   private final PathData pathInfo = new PathData(this);
   private MappingData cachedMappingData;
   protected Cookie[] cookies = null;
   protected Cookies rawCookies;
   protected String sessionCookieName;
   protected SessionManager sessionManager;
   protected static final Locale defaultLocale;
   protected final ArrayList locales = new ArrayList();
   protected Object dispatcherType = null;
   protected final ServerInputBuffer inputBuffer = new ServerInputBuffer();
   private final NIOInputStreamImpl inputStream = new NIOInputStreamImpl();
   private final NIOReaderImpl reader = new NIOReaderImpl();
   protected boolean usingInputStream = false;
   protected boolean usingReader = false;
   protected Principal userPrincipal = null;
   protected boolean sessionParsed = false;
   protected boolean requestParametersParsed = false;
   protected boolean cookiesParsed = false;
   protected boolean secure = false;
   protected Subject subject = null;
   protected final ParameterMap parameterMap = new ParameterMap();
   protected final Parameters parameters = new Parameters();
   protected Object requestDispatcherPath = null;
   protected boolean requestedSessionCookie = false;
   protected String requestedSessionId = null;
   protected boolean requestedSessionURL = false;
   protected boolean localesParsed = false;
   private StringParser parser;
   private int dispatchDepth = 0;
   private static int maxDispatchDepth;
   private String jrouteId;
   private RequestExecutorProvider requestExecutorProvider;
   protected final Response response;
   protected Map trailers;

   public static Request create() {
      Request request = (Request)ThreadCache.takeFromCache(CACHE_IDX);
      return request != null ? request : new Request(new Response());
   }

   public final MappingData obtainMappingData() {
      if (this.cachedMappingData == null) {
         this.cachedMappingData = new MappingData();
      }

      return this.cachedMappingData;
   }

   /** @deprecated */
   public Request() {
      this.response = null;
   }

   protected Request(Response response) {
      this.response = response;
   }

   public void initialize(HttpRequestPacket request, FilterChainContext ctx, HttpServerFilter httpServerFilter) {
      this.request = request;
      this.ctx = ctx;
      this.httpServerFilter = httpServerFilter;
      this.inputBuffer.initialize(this, ctx);
      this.parameters.setHeaders(request.getHeaders());
      this.parameters.setQuery(request.getQueryStringDC());
      DataChunk remoteUser = request.remoteUser();
      if (httpServerFilter != null) {
         ServerFilterConfiguration configuration = httpServerFilter.getConfiguration();
         this.parameters.setQueryStringEncoding(configuration.getDefaultQueryEncoding());
         BackendConfiguration backendConfiguration = configuration.getBackendConfiguration();
         if (backendConfiguration != null) {
            if (backendConfiguration.getScheme() != null) {
               this.scheme = backendConfiguration.getScheme();
            } else if (backendConfiguration.getSchemeMapping() != null) {
               this.scheme = request.getHeader(backendConfiguration.getSchemeMapping());
            }

            if ("https".equalsIgnoreCase(this.scheme)) {
               request.setSecure(true);
            }

            if (remoteUser.isNull() && backendConfiguration.getRemoteUserMapping() != null) {
               remoteUser.setString(request.getHeader(backendConfiguration.getRemoteUserMapping()));
            }
         }
      }

      if (this.scheme == null) {
         this.scheme = request.isSecure() ? "https" : "http";
      }

      if (!remoteUser.isNull()) {
         this.setUserPrincipal(new GrizzlyPrincipal(remoteUser.toString()));
      }

   }

   final HttpServerFilter getServerFilter() {
      return this.httpServerFilter;
   }

   public HttpRequestPacket getRequest() {
      return this.request;
   }

   public Response getResponse() {
      return this.response;
   }

   public String getSessionCookieName() {
      return this.obtainSessionCookieName();
   }

   public void setSessionCookieName(String sessionCookieName) {
      this.sessionCookieName = sessionCookieName;
   }

   public boolean isPushEnabled() {
      Boolean result = (Boolean)this.getContext().getConnection().getAttributes().getAttribute("http2-push-enabled");
      return result != null ? result : false;
   }

   protected String obtainSessionCookieName() {
      return this.sessionCookieName != null ? this.sessionCookieName : this.getSessionManager().getSessionCookieName();
   }

   protected SessionManager getSessionManager() {
      return this.sessionManager != null ? this.sessionManager : DefaultSessionManager.instance();
   }

   protected void setSessionManager(SessionManager sessionManager) {
      this.sessionManager = sessionManager;
   }

   public Executor getRequestExecutor() {
      return this.requestExecutorProvider.getExecutor(this);
   }

   protected void setRequestExecutorProvider(RequestExecutorProvider requestExecutorProvider) {
      this.requestExecutorProvider = requestExecutorProvider;
   }

   public void addAfterServiceListener(AfterServiceListener listener) {
      this.afterServicesList.add(listener);
   }

   public void removeAfterServiceListener(AfterServiceListener listener) {
      this.afterServicesList.remove(listener);
   }

   protected void onAfterService() {
      if (!this.inputBuffer.isFinished()) {
         this.inputBuffer.terminate();
      }

      if (!this.afterServicesList.isEmpty()) {
         int i = 0;

         for(int size = this.afterServicesList.size(); i < size; ++i) {
            AfterServiceListener anAfterServicesList = (AfterServiceListener)this.afterServicesList.get(i);

            try {
               anAfterServicesList.onAfterService(this);
            } catch (Exception var5) {
               LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUEST_AFTERSERVICE_NOTIFICATION_ERROR(), var5);
            }
         }
      }

   }

   protected void recycle() {
      this.scheme = null;
      this.contextPath.setPath("");
      this.httpHandlerPath.reset();
      this.pathInfo.reset();
      this.dispatcherType = null;
      this.requestDispatcherPath = null;
      this.inputBuffer.recycle();
      this.inputStream.recycle();
      this.reader.recycle();
      this.usingInputStream = false;
      this.usingReader = false;
      this.userPrincipal = null;
      this.subject = null;
      this.sessionParsed = false;
      this.requestParametersParsed = false;
      this.cookiesParsed = false;
      if (this.rawCookies != null) {
         this.rawCookies.recycle();
      }

      this.locales.clear();
      this.localesParsed = false;
      this.secure = false;
      this.request.recycle();
      this.request = null;
      this.ctx = null;
      this.httpServerFilter = null;
      this.cookies = null;
      this.requestedSessionId = null;
      this.sessionCookieName = null;
      this.sessionManager = null;
      this.session = null;
      this.dispatchDepth = 0;
      this.parameterMap.setLocked(false);
      this.parameterMap.clear();
      this.parameters.recycle();
      this.requestExecutorProvider = null;
      this.trailers = null;
      this.afterServicesList.clear();
      if (this.cachedMappingData != null) {
         this.cachedMappingData.recycle();
      }

      ThreadCache.putToCache(CACHE_IDX, this);
   }

   public String getAuthorization() {
      return this.request.getHeader("authorization");
   }

   public PushBuilder newPushBuilder() {
      return this.isPushEnabled() ? new PushBuilder(this) : null;
   }

   public void replayPayload(Buffer buffer) {
      this.inputBuffer.replayPayload(buffer);
      this.usingReader = false;
      this.usingInputStream = false;
      if (Method.POST.equals(this.getMethod()) && this.requestParametersParsed) {
         this.requestParametersParsed = false;
         this.parameterMap.setLocked(false);
         this.parameterMap.clear();
         this.parameters.recycle();
      }

   }

   public NIOInputStream createInputStream() {
      this.inputStream.setInputBuffer(this.inputBuffer);
      return this.inputStream;
   }

   public static Note createNote(String name) {
      return HttpRequestPacket.createNote(name);
   }

   public Object getNote(Note note) {
      return this.request.getNote(note);
   }

   public Set getNoteNames() {
      return this.request.getNoteNames();
   }

   public Object removeNote(Note note) {
      return this.request.removeNote(note);
   }

   public void setNote(Note note, Object value) {
      this.request.setNote(note, value);
   }

   public void setServerName(String name) {
      this.request.serverName().setString(name);
   }

   public void setServerPort(int port) {
      this.request.setServerPort(port);
   }

   public HttpServerFilter getHttpFilter() {
      return this.httpServerFilter;
   }

   public String getContextPath() {
      return this.contextPath.get();
   }

   protected void setContextPath(String contextPath) {
      this.contextPath.setPath(contextPath);
   }

   protected void setContextPath(PathResolver contextPath) {
      this.contextPath.setResolver(contextPath);
   }

   public String getHttpHandlerPath() {
      return this.httpHandlerPath.get();
   }

   protected void setHttpHandlerPath(String httpHandlerPath) {
      this.httpHandlerPath.setPath(httpHandlerPath);
   }

   protected void setHttpHandlerPath(PathResolver httpHandlerPath) {
      this.httpHandlerPath.setResolver(httpHandlerPath);
   }

   public String getPathInfo() {
      return this.pathInfo.get();
   }

   protected void setPathInfo(String pathInfo) {
      this.pathInfo.setPath(pathInfo);
   }

   protected void setPathInfo(PathResolver pathInfo) {
      this.pathInfo.setResolver(pathInfo);
   }

   public Object getAttribute(String name) {
      if ("org.glassfish.grizzly.http.SEND_FILE_ENABLED".equals(name)) {
         assert this.response != null;

         return this.response.isSendFileEnabled();
      } else {
         Object attribute = this.request.getAttribute(name);
         if (attribute != null) {
            return attribute;
         } else {
            if ("org.apache.coyote.request.X509Certificate".equals(name)) {
               attribute = RequestUtils.populateCertificateAttribute(this);
               if (attribute != null) {
                  this.request.setAttribute(name, attribute);
               }
            } else if (isSSLAttribute(name)) {
               RequestUtils.populateSSLAttributes(this);
               attribute = this.request.getAttribute(name);
            } else if ("org.apache.catalina.core.DISPATCHER_REQUEST_PATH".equals(name)) {
               return this.requestDispatcherPath;
            }

            return attribute;
         }
      }
   }

   static boolean isSSLAttribute(String name) {
      return "javax.servlet.request.X509Certificate".equals(name) || "javax.servlet.request.cipher_suite".equals(name) || "javax.servlet.request.key_size".equals(name);
   }

   public Set getAttributeNames() {
      return this.request.getAttributeNames();
   }

   public String getCharacterEncoding() {
      return this.request.getCharacterEncoding();
   }

   public int getContentLength() {
      return (int)this.request.getContentLength();
   }

   public long getContentLengthLong() {
      return this.request.getContentLength();
   }

   public String getContentType() {
      return this.request.getContentType();
   }

   public InputStream getInputStream() {
      return this.getNIOInputStream();
   }

   public NIOInputStream getNIOInputStream() {
      if (this.usingReader) {
         throw new IllegalStateException("Illegal attempt to call getInputStream() after getReader() has already been called.");
      } else {
         this.usingInputStream = true;
         this.inputStream.setInputBuffer(this.inputBuffer);
         return this.inputStream;
      }
   }

   /** @deprecated */
   public boolean asyncInput() {
      return true;
   }

   public boolean requiresAcknowledgement() {
      return this.request.requiresAcknowledgement();
   }

   public Locale getLocale() {
      if (!this.localesParsed) {
         this.parseLocales();
      }

      return !this.locales.isEmpty() ? (Locale)this.locales.get(0) : defaultLocale;
   }

   public List getLocales() {
      if (!this.localesParsed) {
         this.parseLocales();
      }

      if (!this.locales.isEmpty()) {
         return this.locales;
      } else {
         ArrayList results = new ArrayList();
         results.add(defaultLocale);
         return results;
      }
   }

   public Parameters getParameters() {
      return this.parameters;
   }

   public String getParameter(String name) {
      if (!this.requestParametersParsed) {
         this.parseRequestParameters();
      }

      return this.parameters.getParameter(name);
   }

   public Map getParameterMap() {
      if (this.parameterMap.isLocked()) {
         return this.parameterMap;
      } else {
         Iterator var1 = this.getParameterNames().iterator();

         while(var1.hasNext()) {
            String name = (String)var1.next();
            String[] values = this.getParameterValues(name);
            this.parameterMap.put(name, values);
         }

         this.parameterMap.setLocked(true);
         return this.parameterMap;
      }
   }

   public Set getParameterNames() {
      if (!this.requestParametersParsed) {
         this.parseRequestParameters();
      }

      return this.parameters.getParameterNames();
   }

   public String[] getParameterValues(String name) {
      if (!this.requestParametersParsed) {
         this.parseRequestParameters();
      }

      return this.parameters.getParameterValues(name);
   }

   public Protocol getProtocol() {
      return this.request.getProtocol();
   }

   public Reader getReader() {
      return this.getNIOReader();
   }

   public NIOReader getNIOReader() {
      if (this.usingInputStream) {
         throw new IllegalStateException("Illegal attempt to call getReader() after getInputStream() has alread been called.");
      } else {
         this.usingReader = true;
         this.inputBuffer.processingChars();
         this.reader.setInputBuffer(this.inputBuffer);
         return this.reader;
      }
   }

   public String getRemoteAddr() {
      return this.request.getRemoteAddress();
   }

   public String getRemoteHost() {
      return this.request.getRemoteHost();
   }

   public int getRemotePort() {
      return this.request.getRemotePort();
   }

   public String getLocalName() {
      return this.request.getLocalName();
   }

   public String getLocalAddr() {
      return this.request.getLocalAddress();
   }

   public int getLocalPort() {
      return this.request.getLocalPort();
   }

   public String getScheme() {
      return this.scheme;
   }

   public String getServerName() {
      return this.request.serverName().toString();
   }

   public int getServerPort() {
      return this.request.getServerPort();
   }

   public boolean isSecure() {
      return this.request.isSecure();
   }

   public void removeAttribute(String name) {
      this.request.removeAttribute(name);
   }

   public void setAttribute(String name, Object value) {
      if (name == null) {
         throw new IllegalArgumentException("Argument 'name' cannot be null");
      } else if (value == null) {
         this.removeAttribute(name);
      } else if (name.equals("org.apache.catalina.core.DISPATCHER_TYPE")) {
         this.dispatcherType = value;
      } else if (name.equals("org.apache.catalina.core.DISPATCHER_REQUEST_PATH")) {
         this.requestDispatcherPath = value;
      } else {
         this.request.setAttribute(name, value);

         assert this.response != null;

         if (this.response.isSendFileEnabled() && "org.glassfish.grizzly.http.SEND_FILE".equals(name)) {
            RequestUtils.handleSendFile(this);
         }

      }
   }

   public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
      if (!this.requestParametersParsed && !this.usingReader) {
         Charsets.lookupCharset(encoding);
         this.request.setCharacterEncoding(encoding);
      }
   }

   public static void setMaxDispatchDepth(int depth) {
      maxDispatchDepth = depth;
   }

   public static int getMaxDispatchDepth() {
      return maxDispatchDepth;
   }

   public int incrementDispatchDepth() {
      return ++this.dispatchDepth;
   }

   public int decrementDispatchDepth() {
      return --this.dispatchDepth;
   }

   public boolean isMaxDispatchDepthReached() {
      return this.dispatchDepth > maxDispatchDepth;
   }

   public void addCookie(Cookie cookie) {
      if (!this.cookiesParsed) {
         this.parseCookies();
      }

      int size = 0;
      if (cookie != null) {
         size = this.cookies.length;
      }

      Cookie[] newCookies = new Cookie[size + 1];
      System.arraycopy(this.cookies, 0, newCookies, 0, size);
      newCookies[size] = cookie;
      this.cookies = newCookies;
   }

   public void addLocale(Locale locale) {
      this.locales.add(locale);
   }

   public void addParameter(String name, String[] values) {
      this.parameters.addParameterValues(name, values);
   }

   public void clearCookies() {
      this.cookiesParsed = true;
      this.cookies = null;
   }

   public void clearHeaders() {
   }

   public void clearLocales() {
      this.locales.clear();
   }

   public void clearParameters() {
   }

   public String getDecodedRequestURI() throws CharConversionException {
      return this.request.getRequestURIRef().getDecodedURI();
   }

   public void setUserPrincipal(Principal principal) {
      this.userPrincipal = principal;
   }

   public String getAuthType() {
      return this.request.authType().toString();
   }

   public Cookie[] getCookies() {
      if (!this.cookiesParsed) {
         this.parseCookies();
      }

      return this.cookies;
   }

   public void setCookies(Cookie[] cookies) {
      this.cookies = cookies;
   }

   public long getDateHeader(String name) {
      String value = this.getHeader(name);
      if (value == null) {
         return -1L;
      } else {
         SimpleDateFormats formats = SimpleDateFormats.create();

         long var6;
         try {
            long result = FastHttpDateFormat.parseDate(value, formats.getFormats());
            if (result == -1L) {
               throw new IllegalArgumentException(value);
            }

            var6 = result;
         } finally {
            formats.recycle();
         }

         return var6;
      }
   }

   public long getDateHeader(Header header) {
      String value = this.getHeader(header);
      if (value == null) {
         return -1L;
      } else {
         SimpleDateFormats formats = SimpleDateFormats.create();

         long var6;
         try {
            long result = FastHttpDateFormat.parseDate(value, formats.getFormats());
            if (result == -1L) {
               throw new IllegalArgumentException(value);
            }

            var6 = result;
         } finally {
            formats.recycle();
         }

         return var6;
      }
   }

   public String getHeader(String name) {
      return this.request.getHeader(name);
   }

   public String getHeader(Header header) {
      return this.request.getHeader(header);
   }

   public Iterable getHeaders(String name) {
      return this.request.getHeaders().values(name);
   }

   public Iterable getHeaders(Header header) {
      return this.request.getHeaders().values(header);
   }

   public Map getTrailers() {
      if (this.inputBuffer.isFinished()) {
         return this.inputBuffer.getTrailers();
      } else {
         throw new IllegalStateException();
      }
   }

   public boolean areTrailersAvailable() {
      return this.inputBuffer.areTrailersAvailable();
   }

   public Iterable getHeaderNames() {
      return this.request.getHeaders().names();
   }

   public int getIntHeader(String name) {
      String value = this.getHeader(name);
      return value == null ? -1 : Integer.parseInt(value);
   }

   public int getIntHeader(Header header) {
      String value = this.getHeader(header);
      return value == null ? -1 : Integer.parseInt(value);
   }

   public Method getMethod() {
      return this.request.getMethod();
   }

   public void setMethod(String method) {
      this.request.setMethod(method);
   }

   public String getQueryString() {
      String queryString = this.request.getQueryStringDC().toString(this.parameters.getQueryStringEncoding());
      return queryString != null && !queryString.isEmpty() ? queryString : null;
   }

   public void setQueryString(String queryString) {
      this.request.setQueryString(queryString);
   }

   public String getRemoteUser() {
      return this.userPrincipal != null ? this.userPrincipal.getName() : null;
   }

   public String getRequestedSessionId() {
      return this.requestedSessionId;
   }

   public String getRequestURI() {
      return this.request.getRequestURI();
   }

   public void setRequestURI(String uri) {
      this.request.setRequestURI(uri);
   }

   public StringBuilder getRequestURL() {
      StringBuilder url = new StringBuilder();
      return appendRequestURL(this, url);
   }

   public static StringBuilder appendRequestURL(Request request, StringBuilder buffer) {
      String scheme = request.getScheme();
      int port = request.getServerPort();
      if (port < 0) {
         port = 80;
      }

      buffer.append(scheme);
      buffer.append("://");
      buffer.append(request.getServerName());
      if (scheme.equals("http") && port != 80 || scheme.equals("https") && port != 443) {
         buffer.append(':');
         buffer.append(port);
      }

      buffer.append(request.getRequestURI());
      return buffer;
   }

   public static StringBuffer appendRequestURL(Request request, StringBuffer buffer) {
      String scheme = request.getScheme();
      int port = request.getServerPort();
      if (port < 0) {
         port = 80;
      }

      buffer.append(scheme);
      buffer.append("://");
      buffer.append(request.getServerName());
      if (scheme.equals("http") && port != 80 || scheme.equals("https") && port != 443) {
         buffer.append(':');
         buffer.append(port);
      }

      buffer.append(request.getRequestURI());
      return buffer;
   }

   public Principal getUserPrincipal() {
      if (this.userPrincipal == null && this.getRequest().isSecure()) {
         X509Certificate[] certs = (X509Certificate[])((X509Certificate[])this.getAttribute("javax.servlet.request.X509Certificate"));
         if (FORCE_CLIENT_AUTH_ON_GET_USER_PRINCIPAL && (certs == null || certs.length < 1)) {
            certs = (X509Certificate[])((X509Certificate[])this.getAttribute("org.apache.coyote.request.X509Certificate"));
         }

         if (certs != null && certs.length > 0) {
            this.userPrincipal = certs[0].getSubjectX500Principal();
         }
      }

      return this.userPrincipal;
   }

   public FilterChainContext getContext() {
      return this.ctx;
   }

   protected String unescape(String s) {
      if (s == null) {
         return null;
      } else if (s.indexOf(92) == -1) {
         return s;
      } else {
         StringBuilder buf = new StringBuilder();

         for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c != '\\') {
               buf.append(c);
            } else {
               ++i;
               if (i >= s.length()) {
                  throw new IllegalArgumentException();
               }

               c = s.charAt(i);
               buf.append(c);
            }
         }

         return buf.toString();
      }
   }

   protected void parseCookies() {
      this.cookiesParsed = true;
      Cookies serverCookies = this.getRawCookies();
      this.cookies = serverCookies.get();
   }

   public InputBuffer getInputBuffer() {
      return this.inputBuffer;
   }

   public void setRequestParameters(Parameters parameters) {
      this.requestParametersParsed = true;
      Iterator var2 = parameters.getParameterNames().iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         this.parameters.addParameterValues(name, parameters.getParameterValues(name));
      }

   }

   protected Cookies getRawCookies() {
      if (this.rawCookies == null) {
         this.rawCookies = new Cookies();
      }

      if (!this.rawCookies.initialized()) {
         this.rawCookies.setHeaders(this.request.getHeaders());
      }

      return this.rawCookies;
   }

   protected void parseRequestParameters() {
      this.requestParametersParsed = true;
      Charset charset = null;
      if (this.parameters.getEncoding() == null) {
         charset = this.lookupCharset(this.getCharacterEncoding());
         this.parameters.setEncoding(charset);
      }

      if (this.parameters.getQueryStringEncoding() == null) {
         if (charset == null) {
            charset = this.lookupCharset(this.getCharacterEncoding());
         }

         this.parameters.setQueryStringEncoding(charset);
      }

      this.parameters.handleQueryParameters();
      if (!this.usingInputStream && !this.usingReader) {
         if (Method.POST.equals(this.getMethod())) {
            if (this.checkPostContentType(this.getContentType())) {
               int maxFormPostSize = this.httpServerFilter.getConfiguration().getMaxFormPostSize();
               int len = this.getContentLength();
               if (len < 0) {
                  if (!this.request.isChunked()) {
                     return;
                  }

                  len = maxFormPostSize;
               }

               if (maxFormPostSize > 0 && len > maxFormPostSize) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.warning(LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUEST_POST_TOO_LARGE());
                  }

                  throw new IllegalStateException(LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUEST_POST_TOO_LARGE());
               } else {
                  int read = 0;

                  try {
                     Buffer formData = this.getPostBody(len);
                     read = formData.remaining();
                     this.parameters.processParameters(formData, formData.position(), read);
                  } catch (Exception var14) {
                  } finally {
                     try {
                        this.skipPostBody(read);
                     } catch (Exception var13) {
                        LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUEST_BODY_SKIP(), var13);
                     }

                  }

               }
            }
         }
      }
   }

   private Charset lookupCharset(String enc) {
      Charset charset;
      if (enc != null) {
         try {
            charset = Charsets.lookupCharset(enc);
         } catch (Exception var4) {
            charset = org.glassfish.grizzly.http.util.Constants.DEFAULT_HTTP_CHARSET;
         }
      } else {
         charset = org.glassfish.grizzly.http.util.Constants.DEFAULT_HTTP_CHARSET;
      }

      return charset;
   }

   private boolean checkPostContentType(String contentType) {
      return contentType != null && contentType.trim().startsWith("application/x-www-form-urlencoded");
   }

   public Buffer getPostBody(int len) throws IOException {
      this.inputBuffer.fillFully(len);
      return this.inputBuffer.getBuffer();
   }

   protected void skipPostBody(int len) throws IOException {
      this.inputBuffer.skip((long)len);
   }

   protected void parseLocales() {
      this.localesParsed = true;
      Iterable values = this.getHeaders("accept-language");
      Iterator var2 = values.iterator();

      while(var2.hasNext()) {
         String value = (String)var2.next();
         this.parseLocalesHeader(value);
      }

   }

   protected void parseLocalesHeader(String value) {
      TreeMap localLocalesMap = new TreeMap();
      int white = value.indexOf(32);
      if (white < 0) {
         white = value.indexOf(9);
      }

      int length;
      int end;
      if (white >= 0) {
         length = value.length();
         StringBuilder sb = new StringBuilder(length - 1);

         for(end = 0; end < length; ++end) {
            char ch = value.charAt(end);
            if (ch != ' ' && ch != '\t') {
               sb.append(ch);
            }
         }

         value = sb.toString();
      }

      if (this.parser == null) {
         this.parser = new StringParser();
      }

      this.parser.setString(value);
      length = this.parser.getLength();

      while(true) {
         int start = this.parser.getIndex();
         if (start >= length) {
            Iterator var16 = localLocalesMap.values().iterator();

            while(var16.hasNext()) {
               List localLocales = (List)var16.next();
               Iterator var19 = localLocales.iterator();

               while(var19.hasNext()) {
                  Locale locale = (Locale)var19.next();
                  this.addLocale(locale);
               }
            }

            return;
         }

         end = this.parser.findChar(',');
         String entry = this.parser.extract(start, end).trim();
         this.parser.advance();
         double quality = 1.0;
         int semi = entry.indexOf(";q=");
         if (semi >= 0) {
            String qvalue = entry.substring(semi + 3);
            if (qvalue.length() <= 5) {
               try {
                  quality = Double.parseDouble(qvalue);
               } catch (NumberFormatException var14) {
                  quality = 0.0;
               }
            } else {
               quality = 0.0;
            }

            entry = entry.substring(0, semi);
         }

         if (!(quality < 5.0E-5) && !"*".equals(entry)) {
            Locale locale = localeParser.parseLocale(entry);
            if (locale != null) {
               Double key = -quality;
               List values = (List)localLocalesMap.get(key);
               if (values == null) {
                  values = new ArrayList();
                  localLocalesMap.put(key, values);
               }

               ((List)values).add(locale);
            }
         }
      }
   }

   void parseJrouteCookie() {
      if (!this.cookiesParsed) {
         this.parseCookies();
      }

      Cookie cookie = this.getRawCookies().findByName("JROUTE");
      if (cookie != null) {
         this.setJrouteId(cookie.getValue());
      }

   }

   static boolean isAlpha(String value) {
      if (value == null) {
         return false;
      } else {
         for(int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
               return false;
            }
         }

         return true;
      }
   }

   void setJrouteId(String jrouteId) {
      this.jrouteId = jrouteId;
   }

   public String getJrouteId() {
      return this.jrouteId;
   }

   public Session getSession() {
      return this.doGetSession(true);
   }

   public Session getSession(boolean create) {
      return this.doGetSession(create);
   }

   public String changeSessionId() {
      Session sessionLocal = this.doGetSession(false);
      if (sessionLocal == null) {
         throw new IllegalStateException("changeSessionId has been called without a session");
      } else {
         String oldSessionId = this.getSessionManager().changeSessionId(this, sessionLocal);
         String newSessionId = sessionLocal.getIdInternal();
         this.requestedSessionId = newSessionId;
         if (this.isRequestedSessionIdFromURL()) {
            return oldSessionId;
         } else {
            if (this.response != null) {
               Cookie cookie = new Cookie(this.obtainSessionCookieName(), newSessionId);
               this.configureSessionCookie(cookie);
               this.response.addSessionCookieInternal(cookie);
            }

            return oldSessionId;
         }
      }
   }

   protected Session doGetSession(boolean create) {
      if (this.session != null && this.session.isValid()) {
         return this.session;
      } else {
         this.session = null;
         if (this.requestedSessionId == null) {
            Cookie[] cookiesLocale = this.getCookies();

            assert cookiesLocale != null;

            String sessionCookieNameLocal = this.obtainSessionCookieName();

            for(int i = 0; i < cookiesLocale.length; ++i) {
               Cookie c = cookiesLocale[i];
               if (sessionCookieNameLocal.equals(c.getName())) {
                  this.setRequestedSessionId(c.getValue());
                  this.setRequestedSessionCookie(true);
                  break;
               }
            }
         }

         this.session = this.getSessionManager().getSession(this, this.requestedSessionId);
         if (this.session != null && !this.session.isValid()) {
            this.session = null;
         }

         if (this.session != null) {
            this.session.access();
            return this.session;
         } else if (!create) {
            return null;
         } else {
            this.session = this.getSessionManager().createSession(this);
            this.session.setSessionTimeout((long)(this.httpServerFilter.getConfiguration().getSessionTimeoutSeconds() * 1000));
            this.requestedSessionId = this.session.getIdInternal();
            Cookie cookie = new Cookie(this.obtainSessionCookieName(), this.session.getIdInternal());
            this.configureSessionCookie(cookie);

            assert this.response != null;

            this.response.addCookie(cookie);
            return this.session;
         }
      }
   }

   public boolean isRequestedSessionIdFromCookie() {
      return this.requestedSessionId != null && this.requestedSessionCookie;
   }

   public boolean isRequestedSessionIdFromURL() {
      return this.requestedSessionId != null && this.requestedSessionURL;
   }

   public boolean isRequestedSessionIdValid() {
      if (this.requestedSessionId == null) {
         return false;
      } else if (this.session != null && this.requestedSessionId.equals(this.session.getIdInternal())) {
         return this.session.isValid();
      } else {
         Session localSession = this.getSessionManager().getSession(this, this.requestedSessionId);
         return localSession != null && localSession.isValid();
      }
   }

   protected void configureSessionCookie(Cookie cookie) {
      cookie.setMaxAge(-1);
      cookie.setPath("/");
      if (this.isSecure()) {
         cookie.setSecure(true);
      }

      this.getSessionManager().configureSessionCookie(this, cookie);
   }

   protected void parseSessionId() {
      if (!this.sessionParsed) {
         this.sessionParsed = true;
         DataChunk uriDC = this.request.getRequestURIRef().getRequestURIBC();
         boolean isUpdated;
         switch (uriDC.getType()) {
            case Bytes:
               isUpdated = this.parseSessionId((Chunk)uriDC.getByteChunk());
               break;
            case Buffer:
               isUpdated = this.parseSessionId((Chunk)uriDC.getBufferChunk());
               break;
            case Chars:
               isUpdated = this.parseSessionId((Chunk)uriDC.getCharChunk());
               break;
            case String:
               isUpdated = this.parseSessionId(uriDC);
               break;
            default:
               throw new IllegalStateException("Unexpected DataChunk type: " + uriDC.getType());
         }

         if (isUpdated) {
            uriDC.notifyDirectUpdate();
         }

      }
   }

   private boolean parseSessionId(Chunk uriChunk) {
      String sessionParamNameMatch = this.sessionCookieName != null ? ';' + this.sessionCookieName + '=' : ";jsessionid=";
      boolean isUpdated = false;
      int semicolon = uriChunk.indexOf(sessionParamNameMatch, 0);
      if (semicolon > 0) {
         int sessionIdStart = semicolon + sessionParamNameMatch.length();
         int semicolon2 = uriChunk.indexOf(';', sessionIdStart);
         isUpdated = semicolon2 >= 0;
         int end = isUpdated ? semicolon2 : uriChunk.getLength();
         String sessionId = uriChunk.toString(sessionIdStart, end);
         int jrouteIndex = sessionId.lastIndexOf(58);
         if (jrouteIndex > 0) {
            this.setRequestedSessionId(sessionId.substring(0, jrouteIndex));
            if (jrouteIndex < sessionId.length() - 1) {
               this.setJrouteId(sessionId.substring(jrouteIndex + 1));
            }
         } else {
            this.setRequestedSessionId(sessionId);
         }

         this.setRequestedSessionURL(true);
         uriChunk.delete(semicolon, end);
      } else {
         this.setRequestedSessionId((String)null);
         this.setRequestedSessionURL(false);
      }

      return isUpdated;
   }

   private boolean parseSessionId(DataChunk dataChunkStr) {
      assert dataChunkStr.getType() == Type.String;

      String uri = dataChunkStr.toString();
      String sessionParamNameMatch = this.sessionCookieName != null ? ';' + this.sessionCookieName + '=' : ";jsessionid=";
      boolean isUpdated = false;
      int semicolon = uri.indexOf(sessionParamNameMatch);
      if (semicolon > 0) {
         int sessionIdStart = semicolon + sessionParamNameMatch.length();
         int semicolon2 = uri.indexOf(59, sessionIdStart);
         isUpdated = semicolon2 >= 0;
         int end = isUpdated ? semicolon2 : uri.length();
         String sessionId = uri.substring(sessionIdStart, end);
         int jrouteIndex = sessionId.lastIndexOf(58);
         if (jrouteIndex > 0) {
            this.setRequestedSessionId(sessionId.substring(0, jrouteIndex));
            if (jrouteIndex < sessionId.length() - 1) {
               this.setJrouteId(sessionId.substring(jrouteIndex + 1));
            }
         } else {
            this.setRequestedSessionId(sessionId);
         }

         this.setRequestedSessionURL(true);
         dataChunkStr.setString(uri.substring(0, semicolon));
      } else {
         this.setRequestedSessionId((String)null);
         this.setRequestedSessionURL(false);
      }

      return isUpdated;
   }

   public void setRequestedSessionCookie(boolean flag) {
      this.requestedSessionCookie = flag;
   }

   public void setRequestedSessionId(String id) {
      this.requestedSessionId = id;
   }

   public void setRequestedSessionURL(boolean flag) {
      this.requestedSessionURL = flag;
   }

   static {
      JdkVersion version = JdkVersion.getJdkVersion();
      Object lp;
      if (version.compareTo("1.7.0") >= 0) {
         try {
            Class localeParserClazz = Class.forName("org.glassfish.grizzly.http.server.TagLocaleParser");
            lp = (LocaleParser)localeParserClazz.newInstance();
         } catch (Throwable var3) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Can't load JDK7 TagLocaleParser", var3);
            }

            lp = new LegacyLocaleParser();
         }
      } else {
         lp = new LegacyLocaleParser();
      }

      localeParser = (LocaleParser)lp;

      assert localeParser != null;

      defaultLocale = Locale.getDefault();
      maxDispatchDepth = 20;
   }

   protected interface PathResolver {
      String resolve(Request var1);
   }

   private static class PathData {
      private final Request request;
      private String path;
      private PathResolver resolver;

      public PathData(Request request) {
         this.request = request;
      }

      public PathData(Request request, String path, PathResolver resolver) {
         this.request = request;
         this.path = path;
         this.resolver = resolver;
      }

      public void setPath(String path) {
         this.path = path;
         this.resolver = null;
      }

      public void setResolver(PathResolver resolver) {
         this.resolver = resolver;
         this.path = null;
      }

      public String get() {
         return this.path != null ? this.path : (this.resolver != null ? (this.path = this.resolver.resolve(this.request)) : null);
      }

      public void reset() {
         this.path = null;
         this.resolver = null;
      }
   }
}
