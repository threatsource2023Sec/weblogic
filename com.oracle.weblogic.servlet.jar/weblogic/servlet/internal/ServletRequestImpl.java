package weblogic.servlet.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.security.auth.message.config.ServerAuthContext;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import javax.servlet.http.PushBuilder;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.diagnostics.context.CorrelationManager;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.spi.BeanInfo;
import weblogic.i18n.logging.Loggable;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelStream;
import weblogic.security.jaspic.MessageInfoImpl;
import weblogic.security.jaspic.servlet.JaspicSecurityModule;
import weblogic.security.jaspic.servlet.JaspicUtilities;
import weblogic.security.jaspic.servlet.ServerAuthSupport;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.http2.PushBuilderImpl;
import weblogic.servlet.http2.Stream;
import weblogic.servlet.internal.async.AsyncContextImpl;
import weblogic.servlet.internal.session.ReplicatableSessionContext;
import weblogic.servlet.internal.session.SessionContext;
import weblogic.servlet.internal.session.SessionData;
import weblogic.servlet.internal.session.SessionInternal;
import weblogic.servlet.internal.session.SharedSessionData;
import weblogic.servlet.logging.HttpAccountingInfoImpl;
import weblogic.servlet.security.internal.ResourceConstraint;
import weblogic.servlet.security.internal.SecurityModule;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.servlet.utils.fileupload.Multipart;
import weblogic.servlet.utils.fileupload.PartItem;
import weblogic.utils.Debug;
import weblogic.utils.SanitizedDiagnosticInfo;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayMap;
import weblogic.utils.collections.Iterators;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.IteratorEnumerator;
import weblogic.utils.http.BytesToString;
import weblogic.utils.http.ChunkMaxPostSizeExceededException;
import weblogic.utils.http.HttpConstants;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.http.HttpRequestParser;
import weblogic.utils.http.QueryParams;
import weblogic.utils.http.RequestParser;
import weblogic.utils.io.FilenameEncoder;
import weblogic.utils.string.CachingDateFormat;
import weblogic.utils.string.ThreadLocalDateFormat;
import weblogic.work.WorkManager;

public final class ServletRequestImpl implements HttpServletRequest, ServerChannelStream, Runnable, SanitizedDiagnosticInfo {
   protected static final String WL_DEBUG_SESSION = "wl_debug_session";
   private static final String WL_AUTH_MASK = "*";
   private static final String WLS_HTTP_CONNECTION_FORCECLOSE = "weblogic.http.connection.forceClose";
   private static final String[] HEADERS_TO_SHOW_ON_MONITORING;
   private boolean futureResponse;
   private static final boolean isWLProxyHeadersAccessible;
   private static final boolean isRemoteUserHeaderAccessible;
   private static WebServerRegistry registry;
   private WebAppServletContext context;
   private ContextVersionManager contextManager;
   private ServletResponseImpl response;
   private ServletInputStream inputStream;
   private final VirtualConnection connection;
   private final HttpAccountingInfoImpl accountInfo = new HttpAccountingInfoImpl(this);
   private final SessionHelper sessionHelper;
   private final RequestInputHelper inputHelper = new RequestInputHelper();
   private final RequestHeaders headers = new RequestHeaders();
   private final RequestParameters parameters = new RequestParameters(this);
   private final AttributesMap attributes = new AttributesMap("request");
   private ServletStubImpl sstub;
   private HttpUpgradeHandler httpUpgradeHandler;
   private boolean useInputStream;
   private boolean useReader;
   private BufferedReader bufferedReader;
   private String relativeURI;
   private String servletPath;
   private String pathInfo;
   private HttpServletMapping mapping = null;
   private boolean sendRedirect;
   private String redirectLocation;
   private boolean checkIndexFile;
   private String inputEncoding;
   private boolean inputEncodingInitialized;
   private String queryParamsEncoding = null;
   private String serverName;
   private int serverPort;
   private Locale[] locales;
   private Locale locale = null;
   private Cookie[] cookies;
   private boolean cookiesParsed;
   private boolean performOverloadAction;
   private String overloadRejectionMessage;
   private int muxerThreadHash = 0;
   private boolean preventRedispatch;
   private boolean asyncSupported = true;
   private AsyncContextImpl async;
   private DispatcherType dispatchType;
   private boolean needToSyncSessionAfterSent;
   private SubjectHandle currentSubject;
   public static boolean ignorePluginParamsForCookiePath;
   private static ThreadLocal AUTH_RECURSION_CHECK;
   private static ThreadLocal LOGOUT_RECURSION_CHECK;
   private static final String POST_METHOD = "POST";
   static final long serialVersionUID = -2966711014117330891L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.internal.ServletRequestImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Request_Cancel_Around_High;
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Request_Run_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Request_Overload_Around_High;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

   public ServletRequestImpl(AbstractHttpConnectionHandler msh) {
      this.dispatchType = DispatcherType.REQUEST;
      this.needToSyncSessionAfterSent = false;
      this.currentSubject = null;
      this.connection = new VirtualConnection(this, msh);
      this.sessionHelper = new SessionHelper(this, (SessionHelper)null);
      if (msh != null) {
         this.getInputHelper().setRequsetParser(msh.createRequestParser());
      }

   }

   private ServletRequestImpl(AbstractHttpConnectionHandler msh, SessionHelper sh) {
      this.dispatchType = DispatcherType.REQUEST;
      this.needToSyncSessionAfterSent = false;
      this.currentSubject = null;
      this.connection = new VirtualConnection(this, msh);
      this.sessionHelper = new SessionHelper(this, sh);
      this.getInputHelper().setRequsetParser(msh.createRequestParser());
   }

   void reset() {
      this.skipUnreadBody();
      this.useInputStream = false;
      this.useReader = false;
      this.relativeURI = null;
      this.sendRedirect = false;
      this.redirectLocation = null;
      this.currentSubject = null;
      this.locale = null;
      this.locales = null;
      this.sstub = null;
      this.inputStream = null;
      this.bufferedReader = null;
      this.servletPath = null;
      this.pathInfo = null;
      this.mapping = null;
      this.attributes.clear();
      this.inputEncoding = null;
      this.queryParamsEncoding = null;
      this.inputEncodingInitialized = false;
      this.serverName = null;
      this.checkIndexFile = false;
      this.cookiesParsed = false;
      this.cookies = null;
      this.connection.reset();
      this.accountInfo.clear();
      this.sessionHelper.reset();
      this.inputHelper.reset();
      this.headers.reset();
      this.parameters.reset();
      this.context = null;
      this.preventRedispatch = false;
      this.muxerThreadHash = 0;
      this.async = null;
      this.asyncSupported = true;
      this.httpUpgradeHandler = null;
   }

   void skipUnreadBody() {
      if (!this.isConnectionClosingForcibly()) {
         if (!this.inputHelper.getRequestParser().isMethodSafe()) {
            boolean isChunked = "Chunked".equalsIgnoreCase(this.headers.getTransferEncoding());
            ServletInputStreamImpl sisi = this.getServletInputStreamImpl();
            if (sisi != null && !sisi.isClosed()) {
               try {
                  if (isChunked) {
                     sisi.ensureChunkedConsumed();
                  } else {
                     long totalLen = this.getContentLengthLong();
                     long readLen = sisi.getBytesRead();
                     if (readLen < totalLen) {
                        sisi.skip(totalLen - readLen);
                     }
                  }
               } catch (IOException var7) {
               }
            }
         }

      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder(512);
      buf.append(super.toString()).append("[\n").append(this.getMethod()).append(' ').append(this.inputHelper.getOriginalURI());
      String qStr = this.getQueryString();
      if (qStr != null) {
         buf.append('?').append(qStr);
      }

      buf.append(' ').append(this.getProtocol()).append('\n');
      buf.append(this.getHttpHeadersInfo(false));
      buf.append("\n]");
      return buf.toString();
   }

   private String getHttpHeadersInfo(boolean excludeCookie) {
      boolean includeCookie = excludeCookie ? false : HTTPDebugLogger.isEnabled();
      StringBuffer buf = new StringBuffer();
      ArrayList headerNames = this.headers.getHeaderNamesAsArrayList();
      ArrayList headerValues = this.headers.getHeaderValuesAsArrayList();
      if (headerNames != null) {
         for(int i = 0; i < headerNames.size(); ++i) {
            String name = (String)headerNames.get(i);
            if (name != null && (!eq(name, "Cookie", 6) || includeCookie)) {
               byte[] byteValue = (byte[])((byte[])headerValues.get(i));
               if (byteValue != null && !"Host".equalsIgnoreCase(name)) {
                  String value;
                  try {
                     value = new String(byteValue, this.getInputEncoding());
                  } catch (UnsupportedEncodingException var11) {
                     if (HTTPDebugLogger.isEnabled()) {
                        this.trace(var11, var11.getMessage());
                     }

                     value = new String(byteValue);
                  }

                  if ("authorization".equalsIgnoreCase(name)) {
                     buf.append(name).append(": ").append("*").append('\n');
                  } else {
                     buf.append(name).append(": ").append(value).append('\n');
                  }
               }
            }
         }
      }

      return buf.toString();
   }

   public SubjectHandle getCurrentSubject() {
      return this.currentSubject;
   }

   public void setCurrentSubject(SubjectHandle currentSubject) {
      this.currentSubject = currentSubject;
   }

   public String getSanitizedDescription() {
      StringBuilder buf = new StringBuilder();
      buf.append("Http Request Information: ").append(super.toString()).append("[").append(this.getMethod()).append(' ').append(this.getRequestURI()).append("]\n");
      return buf.toString();
   }

   public String getSanitizedDescriptionVerbose() {
      try {
         StringBuilder buf = new StringBuilder();
         if (this.context != null) {
            SubjectHandle subject = SecurityModule.getCurrentUser(this.context.getSecurityContext(), this);
            if (subject != null) {
               buf.append("Current user: " + subject.getUsername() + "\n");
            } else {
               buf.append("Current user: <null>\n");
            }
         }

         buf.append("Remote Address: " + this.getRemoteAddr() + "\n");
         SessionData sessionData = null;
         if (this.getContext() != null || this.contextManager != null) {
            HttpSession session = this.getSession(false);
            if (session instanceof SharedSessionData) {
               sessionData = (SessionData)((SharedSessionData)session).getSession();
            } else {
               sessionData = (SessionData)session;
            }
         }

         if (sessionData != null) {
            buf.append("Http Session: " + sessionData.getMonitoringId() + "\n");
         }

         buf.append("Http Request Information: ").append(super.toString()).append("[").append(this.getMethod()).append(' ').append(this.getRequestURI()).append("\n");
         String[] var10 = HEADERS_TO_SHOW_ON_MONITORING;
         int var4 = var10.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String header = var10[var5];
            String value = this.headers.getHeader(header, this.getInputEncoding());
            if (value != null) {
               buf.append(header).append(": ").append(value).append("\n");
            }
         }

         buf.append("]\n");
         return buf.toString();
      } catch (Throwable var8) {
         return "The request has been requeued and reset, no information available.";
      }
   }

   public String toStringSimple() {
      StringBuilder buf = new StringBuilder(512);
      buf.append(super.toString()).append(" - ").append(this.inputHelper.getOriginalURI());
      return buf.toString();
   }

   public RequestInputHelper getInputHelper() {
      return this.inputHelper;
   }

   public RequestHeaders getRequestHeaders() {
      return this.headers;
   }

   public RequestParameters getRequestParameters() {
      return this.parameters;
   }

   public void initFromRequestParser(RequestParser p) {
      this.inputHelper.initFromRequestParser(p, this.parameters);
      this.headers.setHeaders(p.getHeaderNames(), p.getHeaderValues());
      this.getConnection().init();
      this.initProxyHeaders();
      this.relativeURI = this.computeRelativeUri(this.inputHelper.getNormalizedURI());
   }

   void initFromRequestParser() {
      this.inputHelper.restore();
      this.relativeURI = this.computeRelativeUri(this.inputHelper.getNormalizedURI());
   }

   void saveMuxerThreadHash(int hash) {
      this.muxerThreadHash = hash;
   }

   public void initFromRequestURI(String uri) {
      this.inputHelper.initFromRequestURI(uri);
      this.relativeURI = this.computeRelativeUri(this.inputHelper.getNormalizedURI());
      if (HTTPDebugLogger.isEnabled()) {
         this.trace("URI: " + this.inputHelper.getNormalizedURI());
      }

   }

   public void addForwardParameter(String param) {
      if (param != null && param.length() != 0) {
         this.parameters.addForwardQueryString(param);
      }
   }

   void addIncludeParameter(String param) {
      if (param != null && param.length() != 0) {
         this.parameters.addIncludeQueryString(param);
      }
   }

   public void removeRequestDispatcherQueryString() {
      this.parameters.removeRequestDispatcherQueryString();
   }

   private void initProxyHeaders() {
      if (this.headers.isWlProxyFound()) {
         ArrayList headerNames = this.headers.getHeaderNamesAsArrayList();
         ArrayList headerValues = this.headers.getHeaderValuesAsArrayList();

         for(int i = 0; i < headerNames.size(); ++i) {
            String name = (String)headerNames.get(i);
            byte[] value = (byte[])((byte[])headerValues.get(i));
            if (StringUtils.indexOfIgnoreCase(name, "WL-Proxy-") >= 0) {
               this.getConnection().processProxyHeader(name, value);
               if (!isWLProxyHeadersAccessible) {
                  headerNames.remove(i);
                  headerValues.remove(i);
                  --i;
               }
            }
         }

      }
   }

   public void initContext(WebAppServletContext context) {
      WebAppServletContext oldContext = this.context;
      this.context = context;
      if (oldContext == null) {
         this.relativeURI = this.computeRelativeUri(this.inputHelper.getNormalizedURI());
      }

      this.parameters.init();
   }

   void initContextManager(ContextVersionManager cm) {
      this.contextManager = cm;
      this.context = null;
      if (cm != null) {
         this.sessionHelper.initSessionInfo();
      }

   }

   private void initServerNameAndPort() {
      String host = this.headers.getHost();
      int port = this.headers.getPort();
      HttpServer httpServer = this.context.getServer();
      int frontEndPort;
      short defaultPort;
      if (this.connection.isSecure()) {
         frontEndPort = httpServer.getFrontendHTTPSPort();
         defaultPort = 443;
      } else {
         frontEndPort = httpServer.getFrontendHTTPPort();
         defaultPort = 80;
      }

      String frHost = httpServer.getFrontendHost();
      if (frHost != null && frontEndPort != 0) {
         this.setServerName(frHost);
         this.setServerPort(frontEndPort);
      } else {
         if (host != null) {
            this.setServerName(host);
         } else {
            String serverName = httpServer.getListenAddress();
            serverName = serverName != null && !serverName.isEmpty() ? serverName : this.getLocalName();
            this.setServerName(serverName);
         }

         if (port != -1) {
            this.setServerPort(port);
         } else {
            if (host == null) {
               this.setServerPort(this.connection.getLocalPort());
            } else {
               this.setServerPort(defaultPort);
            }

         }
      }
   }

   public WebAppServletContext getContext() {
      return this.context;
   }

   public HttpAccountingInfoImpl getHttpAccountingInfo() {
      return this.accountInfo;
   }

   public String getInputEncoding() {
      if (this.inputEncoding != null) {
         return this.inputEncoding;
      } else {
         return this.context != null ? this.context.getConfigManager().getDefaultEncoding() : "ISO-8859-1";
      }
   }

   private String getQueryParamsEncoding() {
      return this.queryParamsEncoding != null ? this.queryParamsEncoding : HttpRequestParser.getURIDecodeEncoding();
   }

   public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
      if (!this.useReader) {
         this.setCharacterEncodingInternal(enc);
      }
   }

   private void setCharacterEncodingInternal(String enc) throws UnsupportedEncodingException {
      boolean isSupported;
      try {
         isSupported = Charset.isSupported(enc);
      } catch (IllegalCharsetNameException var4) {
         throw new UnsupportedEncodingException("Unsupported Encoding " + enc);
      }

      if (!isSupported) {
         throw new UnsupportedEncodingException("Unsupported Encoding" + enc);
      } else {
         this.inputEncoding = this.context.getCharsetMap().getJavaCharset(enc);
         if (!this.inputEncoding.equals(this.queryParamsEncoding)) {
            this.queryParamsEncoding = this.inputEncoding;
            this.parameters.resetQueryParams();
         }

      }
   }

   public PushBuilder newPushBuilder() {
      Stream currentStream = this.getConnection().getConnectionHandler().getStream();
      return currentStream != null && currentStream.isPushSupported() ? new PushBuilderImpl(this) : null;
   }

   public Map getTrailerFields() {
      if (!this.isTrailerFieldsReady()) {
         throw new IllegalStateException("The trailer fields are not ready to read.");
      } else {
         return this.inputHelper.getRequestParser().getTrailers();
      }
   }

   public boolean isTrailerFieldsReady() {
      if ("HTTP/1.0".equals(this.getProtocol())) {
         return true;
      } else {
         return "HTTP/1.1".equals(this.getProtocol()) && !this.headers.isChunked() ? true : this.inputHelper.getRequestParser().isTrailerFieldsReady();
      }
   }

   public String getCharacterEncoding() {
      try {
         this.initRequestEncoding();
      } catch (UnsupportedEncodingException var2) {
         return null;
      }

      return this.inputEncoding;
   }

   public void setQueryCharacterEncoding(String enc) throws UnsupportedEncodingException {
      if (!Charset.isSupported(enc)) {
         throw new UnsupportedEncodingException("Unsupported Encoding " + enc);
      } else {
         this.queryParamsEncoding = this.context.getCharsetMap().getJavaCharset(enc);
         this.parameters.resetQueryParams();
      }
   }

   public ServletResponseImpl getResponse() {
      return this.response;
   }

   void setResponse(ServletResponseImpl res) {
      this.response = res;
   }

   public ServletStubImpl getServletStub() {
      return this.sstub;
   }

   void setServletStub(ServletStubImpl stub) {
      this.sstub = stub;
   }

   boolean isFutureResponseEnabled() {
      return this.futureResponse;
   }

   void disableFutureResponse() {
      this.futureResponse = false;
   }

   public void enableFutureResponse() {
      this.futureResponse = true;
      this.connection.getConnectionHandler().disableReuse();
   }

   boolean getSendRedirect() {
      return this.sendRedirect;
   }

   void setRedirectURI(String location) {
      this.sendRedirect = true;
      this.redirectLocation = location;
   }

   void setRedirectURI(boolean sendRedirect) {
      this.sendRedirect = sendRedirect;
   }

   String getRedirectURI() {
      return this.redirectLocation;
   }

   public String getMethod() {
      return this.inputHelper.getRequestParser().getMethod();
   }

   public void setMethod(String method) {
      this.inputHelper.getRequestParser().setMethod(method);
      this.response.getServletOutputStream().setWriteEnabled(!this.inputHelper.getRequestParser().isMethodHead());
   }

   public int getContentLength() {
      long length = this.headers.getContentLength();
      return length <= 2147483647L ? (int)length : -1;
   }

   public long getContentLengthLong() {
      return this.headers.getContentLength();
   }

   public String getContentType() {
      return this.headers.getContentType();
   }

   public StringBuffer getRequestURL() {
      StringBuffer result = new StringBuffer(this.getScheme());
      result.append("://");
      result.append(this.serverName);
      result.append(":");
      result.append(this.serverPort);
      result.append(this.inputHelper.getOriginalURI());
      return result;
   }

   public String getRequestURI() {
      if (this.context != null) {
         String cookieName = this.context.getSessionContext().getConfigMgr().getCookieName();
         return this.inputHelper.getRequestURI(cookieName);
      } else {
         return this.inputHelper.getRequestURI((String)null);
      }
   }

   public static String getResolvedURI(HttpServletRequest request) {
      if (request instanceof ServletRequestImpl) {
         return ((ServletRequestImpl)request).inputHelper.getNormalizedURI();
      } else {
         String orig = HttpParsing.unescape(request.getRequestURI(), HttpRequestParser.getURIDecodeEncoding()).trim();
         return FilenameEncoder.resolveRelativeURIPath(orig);
      }
   }

   public static String getResolvedContextPath(HttpServletRequest request) {
      if (request instanceof ServletRequestImpl) {
         return ((ServletRequestImpl)request).getContext().getContextPath();
      } else {
         String orig = HttpParsing.unescape(request.getContextPath(), HttpRequestParser.getURIDecodeEncoding()).trim();
         return FilenameEncoder.resolveRelativeURIPath(orig);
      }
   }

   public String getRealPath(String path) {
      return this.context.getRealPath(path);
   }

   public String getContextPath() {
      String contextPath = null;
      String dispatchType = (String)this.getAttribute("weblogic.servlet.internal.crosscontext.type");
      if ("include" == dispatchType) {
         contextPath = (String)this.getAttribute("weblogic.servlet.internal.crosscontext.path");
      }

      if (contextPath == null) {
         contextPath = this.context.getContextPath();
      }

      if (contextPath.length() < 2) {
         return "";
      } else if ("forward" == dispatchType) {
         return contextPath;
      } else {
         int contextPathLen = contextPath.length();
         String originalURI = this.inputHelper.getOriginalURI();
         int originalURILen = originalURI.length();
         String normalizedURI = this.inputHelper.getNormalizedURI();
         if (originalURI.equals(normalizedURI)) {
            return contextPath;
         } else {
            String path = null;
            String temp = null;
            int start = 0;
            int end = originalURILen - contextPathLen;
            int middle = (end - start) / 2;

            while(middle != start || middle != end) {
               path = originalURI.substring(0, middle + contextPathLen);
               temp = HttpParsing.unescape(path, HttpRequestParser.getURIDecodeEncoding());
               temp = FilenameEncoder.resolveRelativeURIPath(temp, true);
               if (temp == null) {
                  break;
               }

               boolean startsWith = temp.startsWith(contextPath);
               boolean endsWith = temp.endsWith(contextPath);
               if (startsWith) {
                  if (endsWith && temp.length() == contextPath.length()) {
                     break;
                  }

                  end = middle;
                  middle = start + (middle - start) / 2;
               } else if (start == middle) {
                  start = contextPathLen;
                  end = middle;
                  middle = (middle - contextPathLen) / 2;
               } else {
                  start = middle;
                  middle = end - (end - middle) / 2;
               }
            }

            Debug.assertion(path != null, "Cannot determine encoded context path");
            return path;
         }
      }
   }

   public String getServletPath() {
      return this.servletPath;
   }

   void setServletPath(String sp) {
      this.servletPath = sp;
   }

   public String getPathInfo() {
      return this.pathInfo;
   }

   void setPathInfo(String pi) {
      this.pathInfo = pi;
   }

   void setServletPathAndPathInfo(String relUri, String sp) {
      this.servletPath = sp;
      this.pathInfo = computePathInfo(relUri, this.servletPath);
   }

   static String computePathInfo(String relUri, String sp) {
      if (sp == null) {
         return null;
      } else {
         int len = sp.length();
         return len >= relUri.length() ? null : relUri.substring(len);
      }
   }

   public String getPathTranslated() {
      return this.pathInfo != null ? this.getRealPath(this.pathInfo) : null;
   }

   public HttpServletMapping getHttpServletMapping() {
      return this.dispatchType == DispatcherType.ASYNC ? (HttpServletMapping)this.getAttribute("javax.servlet.async.mapping") : this.mapping;
   }

   void setHttpServletMapping(HttpServletMapping mapping) {
      this.mapping = mapping;
   }

   public Object getAttribute(String name) {
      return this.attributes.get(name, this.context);
   }

   public void setAttribute(String name, Object o) {
      if (o == null) {
         this.removeAttribute(name);
      } else {
         this.attributes.put(name, o, this.context);
      }
   }

   public void removeAttribute(String name) {
      this.attributes.remove(name);
   }

   public Enumeration getAttributeNames() {
      return (Enumeration)(this.attributes.isEmpty() ? new EmptyEnumerator() : new IteratorEnumerator(this.attributes.keys()));
   }

   public RequestDispatcher getRequestDispatcher(String path) {
      if (path == null) {
         return null;
      } else {
         String queryString = null;
         if (path.length() > 0) {
            if (path.charAt(0) != '/') {
               String relUri = this.getRelativeUri();
               int lastSlash = relUri.lastIndexOf(47);
               if (lastSlash < 1) {
                  path = "/" + path;
               } else {
                  path = relUri.substring(0, lastSlash + 1) + path;
               }
            }

            int queryStringPos = path.indexOf(63);
            if (queryStringPos > 0 && queryStringPos < path.length()) {
               queryString = path.substring(queryStringPos + 1);
               if (queryString.equals("")) {
                  queryString = null;
               }

               path = path.substring(0, queryStringPos);
            }
         }

         path = FilenameEncoder.resolveRelativeURIPath(path, true);
         return path == null ? null : new RequestDispatcherImpl(path, queryString, this.context, -1);
      }
   }

   public String getQueryString() {
      return this.parameters.getQueryString(this.getQueryParamsEncoding());
   }

   String getOriginalQueryString() {
      return this.parameters.getOriginalQueryString(this.getQueryParamsEncoding());
   }

   public String getParameter(String name) {
      return this.parameters.getParameter(name);
   }

   public Enumeration getParameterNames() {
      return this.parameters.getParameterNames();
   }

   public String[] getParameterValues(String name) {
      if (HTTPDebugLogger.isEnabled()) {
         this.trace("Querying multiple: " + name);
      }

      return this.parameters.getParameterValues(name);
   }

   public String getProtocol() {
      return this.inputHelper.getRequestParser().getProtocol();
   }

   public String getServerName() {
      return this.serverName;
   }

   void setServerName(String s) {
      this.serverName = s;
      if (HTTPDebugLogger.isEnabled()) {
         this.trace("Servername: " + s);
      }

   }

   public int getServerPort() {
      return this.serverPort;
   }

   void setServerPort(int p) {
      this.serverPort = p;
      if (HTTPDebugLogger.isEnabled()) {
         this.trace("Serverport: " + p);
      }

   }

   public String getRemoteUser() {
      if (isRemoteUserHeaderAccessible) {
         String remoteUser = this.headers.getRemoteUser();
         if (remoteUser != null) {
            return remoteUser;
         }
      }

      SubjectHandle subject = SecurityModule.getCurrentUser(this.context.getSecurityContext(), this);
      return subject == null ? null : subject.getUsername();
   }

   public Map getParameterMap() {
      ArrayMap map = new ArrayMap();
      Enumeration e = this.getParameterNames();

      while(e.hasMoreElements()) {
         String key = (String)e.nextElement();
         String[] values = this.getParameterValues(key);
         map.put(key, values);
      }

      return Collections.unmodifiableMap(map);
   }

   public String getRemoteAddr() {
      if (this.context != null) {
         String clientIpHeader = this.context.getServer().getClientIpHeader();
         if (clientIpHeader != null) {
            String value = this.getHeader(clientIpHeader);
            if (value != null) {
               int index = value.indexOf(44);
               if (index >= 0) {
                  value = value.substring(0, index);
               }

               return value;
            }
         }
      }

      return this.connection.getRemoteAddr();
   }

   public String getRemoteHost() {
      return this.connection.getRemoteHost();
   }

   public int getRemotePort() {
      return this.connection.getRemotePort();
   }

   public String getLocalAddr() {
      return this.connection.getLocalAddr();
   }

   public String getLocalName() {
      return this.connection.getLocalName();
   }

   public int getLocalPort() {
      return this.connection.getLocalPort();
   }

   public String getAuthType() {
      SubjectHandle subject = SecurityModule.getCurrentUser(this.context.getSecurityContext(), this);
      if (subject == null) {
         return null;
      } else {
         String proxyAuthType = this.headers.getProxyAuthType();
         return proxyAuthType != null ? proxyAuthType : this.context.getSecurityManagerWithPrivilege().getAuthMethod();
      }
   }

   public boolean isSecure() {
      return this.connection.isSecure();
   }

   public boolean isUserInRole(String rolename) {
      if (rolename != null && !"*".equals(rolename)) {
         SubjectHandle subject = SecurityModule.getCurrentUser(this.context.getSecurityContext(), this);
         return this.context.getSecurityManagerWithPrivilege().isSubjectInRole(subject, rolename, this, this.getResponse(), this.sstub);
      } else {
         return false;
      }
   }

   public Principal getUserPrincipal() {
      SubjectHandle subject = SecurityModule.getCurrentUser(this.context.getSecurityContext(), this);
      return subject == null ? null : subject.getPrincipal();
   }

   public String getHeader(String name) {
      return this.headers.getHeader(name, this.getInputEncoding());
   }

   public int getIntHeader(String name) {
      String strval = this.getHeader(name);
      return strval == null ? -1 : Integer.parseInt(strval);
   }

   public long getDateHeader(String name) {
      String header = this.getHeader(name);
      if (header != null) {
         DateFormat[] threadLocalFormats = ThreadLocalDateFormat.getInstance().getDateFormats();
         long result = CachingDateFormat.parseDate(StringUtils.upto(header, ';'), threadLocalFormats);
         if (result != -1L) {
            return result;
         } else {
            throw new IllegalArgumentException("Bad date header: '" + header + "'");
         }
      } else {
         return -1L;
      }
   }

   public Enumeration getHeaderNames() {
      return this.headers.getHeaderNames();
   }

   public Enumeration getHeaders(String name) {
      return this.headers.getHeaders(name, this.getInputEncoding());
   }

   public Locale getLocale() {
      if (this.locale == null) {
         this.initLocales();
      }

      return this.locale;
   }

   public Enumeration getLocales() {
      if (this.locales == null) {
         this.initLocales();
      }

      return new IteratorEnumerator(Arrays.asList((Object[])this.locales).iterator());
   }

   private void initLocales() {
      String acceptLang = this.headers.getAcceptLanguages();
      if (acceptLang != null && acceptLang.trim().length() != 0) {
         String[] langs = StringUtils.splitCompletely(acceptLang, ",");
         List localeList = new ArrayList();
         LocaleGenerator localeGenerator = this.getLocaleGenerator();
         Set localeSet = new HashSet();

         for(int i = 0; i < langs.length; ++i) {
            String[] lq_pair = StringUtils.split(langs[i], ';');
            Locale[] tmpLocales = localeGenerator.getLocale(lq_pair[0]);
            Locale[] var9 = tmpLocales;
            int var10 = tmpLocales.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               Locale tmpLocale = var9[var11];
               if (tmpLocale != null && !StringUtils.isEmptyString(tmpLocale.getLanguage()) && !localeSet.contains(tmpLocale.toString())) {
                  localeList.add(tmpLocale);
                  localeSet.add(tmpLocale.toString());
               }
            }

            if (this.locale == null) {
               if (localeGenerator.isLangtagFallbackEnabled() && LocaleGenerator.FORCE_REGIONAL_CONVETIONS && localeGenerator.getAddHandleLangTag(lq_pair[0].trim()) != null) {
                  this.locale = tmpLocales[1];
               } else {
                  this.locale = tmpLocales[0];
               }
            }
         }

         if (localeList.isEmpty()) {
            this.locales = new Locale[1];
            this.locales[0] = Locale.getDefault();
         } else {
            this.locales = (Locale[])localeList.toArray(new Locale[localeList.size()]);
         }

      } else {
         this.locales = new Locale[1];
         this.locales[0] = Locale.getDefault();
         this.locale = this.locales[0];
      }
   }

   private LocaleGenerator getLocaleGenerator() {
      LocaleGenerator localeGenerator = null;
      if (this.context != null) {
         localeGenerator = this.context.getLocaleGenerator();
      } else {
         try {
            localeGenerator = (LocaleGenerator)Class.forName("weblogic.servlet.internal.JDK7LocaleGenerator").newInstance();
         } catch (Exception var3) {
         }
      }

      return localeGenerator;
   }

   public void setInputStream(ServletInputStream sis) {
      this.inputStream = sis;
   }

   private ServletInputStreamImpl getServletInputStreamImpl() {
      return (ServletInputStreamImpl)this.inputStream;
   }

   public ServletInputStream getInputStream() {
      if (this.useReader) {
         throw new IllegalStateException("getInputStream() called after getReader() called");
      } else {
         this.useInputStream = true;
         return this.inputStream;
      }
   }

   public BufferedReader getReader() throws UnsupportedEncodingException {
      if (this.useInputStream) {
         throw new IllegalStateException("getReader() called after getInputStream() called");
      } else {
         this.useReader = true;
         this.initReader();
         return this.bufferedReader;
      }
   }

   private void initReader() throws UnsupportedEncodingException {
      if (this.bufferedReader == null) {
         this.initRequestEncoding();
         this.bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream, this.getInputEncoding()));
      }
   }

   private void initRequestEncoding() throws UnsupportedEncodingException {
      if (!this.inputEncodingInitialized) {
         String charset = this.findCharSetFromContentType();
         if (charset == null || charset.length() == 0) {
            charset = this.context.getRequestCharacterEncoding();
         }

         if (charset != null && charset.length() != 0) {
            try {
               this.setCharacterEncodingInternal(charset);
            } catch (UnsupportedEncodingException var4) {
               String ctx = this.context == null ? "" : this.context.getLogContext();
               HTTPLogger.logUnsupportedEncoding(ctx, charset, var4);
               throw var4;
            }
         }

         this.inputEncodingInitialized = true;
      }
   }

   private String findCharSetFromContentType() {
      String ctype = this.getContentType();
      if (ctype != null && ctype.length() != 0) {
         int maxIndex = ctype.length() - 1;
         int startFrom = 0;

         while(startFrom <= maxIndex && (startFrom = ctype.indexOf(59, startFrom)) != -1) {
            ++startFrom;

            while(startFrom <= maxIndex && HttpParsing.isWS(ctype.charAt(startFrom))) {
               ++startFrom;
            }

            if (ctype.startsWith("charset", startFrom) || ctype.startsWith("CHARSET", startFrom)) {
               int indexEquals = ctype.indexOf(61, startFrom + "charset".length());
               if (indexEquals != -1 && indexEquals < maxIndex) {
                  int indexSemicolon = ctype.indexOf(59, indexEquals);
                  String charset;
                  if (indexSemicolon == -1) {
                     charset = ctype.substring(indexEquals + 1);
                  } else {
                     charset = ctype.substring(indexEquals + 1, indexSemicolon);
                  }

                  return HttpParsing.StripHTTPFieldValue(charset.trim());
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public void initInputEncoding() {
      if (this.context != null) {
         String enc = this.context.getRequestCharacterEncoding();
         if (enc == null) {
            HashMap m = this.context.getConfigManager().getInputEncodings();
            if (m.isEmpty()) {
               return;
            }

            String key = this.getRelativeUri();

            while(true) {
               enc = (String)m.get(key);
               if (enc != null) {
                  break;
               }

               int nextName = key.lastIndexOf(47);
               if (nextName <= 0) {
                  break;
               }

               key = key.substring(0, nextName);
            }

            if (enc == null) {
               enc = (String)m.get("");
            }

            if (enc == null) {
               return;
            }
         }

         if (!this.getInputEncoding().equals(this.context.getCharsetMap().getJavaCharset(enc))) {
            try {
               this.setCharacterEncoding(enc);
            } catch (UnsupportedEncodingException var5) {
            }

         }
      }
   }

   public String getRelativeUri() {
      return this.relativeURI;
   }

   private String computeRelativeUri(String uri) {
      if (uri != null && this.context != null && this.context.getContextPath().length() != 0) {
         int cplen = this.context.getContextPath().length();
         return uri.length() <= cplen ? "" : uri.substring(cplen);
      } else {
         return uri;
      }
   }

   public void setInputStream(InputStream is) {
      this.inputStream = new ServletInputStreamImpl(is, this.connection.getConnectionHandler());
   }

   static boolean eq(String name, String constant, int len) {
      return name == constant || constant.regionMatches(true, 0, name, 0, len);
   }

   public String getScheme() {
      return this.connection.isSecure() ? "https" : "http";
   }

   public Cookie[] getCookies() {
      if (this.cookies == null && !this.cookiesParsed) {
         this.parseCookies();
      }

      return this.cookies;
   }

   private void parseCookies() {
      if (HTTPDebugLogger.isEnabled()) {
         this.trace("Parsing cookies");
      }

      this.cookiesParsed = true;
      ArrayList cookiesV = new ArrayList();
      List cookieHeaders = this.headers.getCookieHeaders();
      Iterator it = cookieHeaders.iterator();

      while(it.hasNext()) {
         byte[] cookieBytes = (byte[])((byte[])it.next());
         String value = BytesToString.newString(cookieBytes, this.getInputEncoding());

         try {
            Iterators.addAll(cookiesV, CookieParser.parseCookies(value));
         } catch (MalformedCookieHeaderException var7) {
            HTTPLogger.logBadCookieHeader(this.getContext().getLogContext(), var7.getHeader(), this.headers.getUserAgent(), var7);
         }
      }

      if (cookiesV.size() != 0) {
         this.cookies = (Cookie[])((Cookie[])cookiesV.toArray(new Cookie[cookiesV.size()]));
      }
   }

   public SessionHelper getSessionHelper() {
      return this.sessionHelper;
   }

   public HttpSession getSession() {
      return this.getSession(true);
   }

   public HttpSession getSession(boolean flag) {
      HttpSession sess = this.sessionHelper.getSession(flag);
      this.checkAndSetDebugSessionFlag(sess);
      this.needToSyncSessionAfterSent = this.response.isFutureResponseSent() && sess != null;
      return sess;
   }

   public String getRequestedSessionId() {
      return this.sessionHelper.getRequestedSessionId();
   }

   public String getIncomingSessionCookieValue() {
      return this.sessionHelper.getIncomingSessionCookieValue();
   }

   public boolean isRequestedSessionIdFromCookie() {
      return this.sessionHelper.isRequestedSessionIdFromCookie();
   }

   public boolean isRequestedSessionIdFromURL() {
      return this.sessionHelper.isRequestedSessionIdFromURL();
   }

   public boolean isRequestedSessionIdFromUrl() {
      return this.sessionHelper.isRequestedSessionIdFromUrl();
   }

   public boolean isRequestedSessionIdValid() {
      return this.sessionHelper.isRequestedSessionIdValid();
   }

   private void trace(String s) {
      HTTPDebugLogger.debug(this.toStringSimple() + ": " + s);
   }

   private void trace(Exception e, String s) {
      HTTPDebugLogger.debug(this.toStringSimple() + ": " + s, e);
   }

   public ServerChannel getServerChannel() {
      return this.connection.getChannel();
   }

   public boolean isAdminChannelRequest() {
      return this.getConnection().isInternalDispatch() ? ApplicationVersionUtils.getCurrentAdminMode() : registry.getContainerSupportProvider().isAdminChannel(this.getServerChannel());
   }

   private final void checkAndSetDebugSessionFlag(HttpSession sess) {
      if (!registry.isProductionMode() && sess != null && sess instanceof SessionData && !((SessionData)sess).isDebuggingSession() && this.parameters.peekQueryParameter("wl_debug_session") != null) {
         ((SessionData)sess).setDebugFlag(true);
      }

   }

   boolean validate(HttpServer httpServer) throws IOException {
      if (this.inputHelper.getRequestParser().isProtocolVersion_1_1() && this.headers.getHost() == null) {
         this.connection.getConnectionHandler().sendError(this.response, 400);
         if (HTTPDebugLogger.isEnabled()) {
            this.trace("HOST header was missing from HTTP1.1 request");
         }

         return false;
      } else if (this.inputHelper.getNormalizedURI() == null) {
         this.connection.getConnectionHandler().sendError(this.response, 404);
         return false;
      } else if (this.response.getContext() == null) {
         if (HTTPDebugLogger.isEnabled()) {
            Loggable l = HTTPLogger.logNoContextLoggable(httpServer.toString(), this.inputHelper.getNormalizedURI());
            HTTPDebugLogger.debug(l.getMessage());
         }

         this.connection.getConnectionHandler().sendError(this.response, 404);
         return false;
      } else if (!this.response.getContext().isStarted()) {
         this.connection.getConnectionHandler().sendError(this.response, 503);
         return false;
      } else {
         long clen = this.getContentLengthLong();
         if (clen < -1L && !this.inputHelper.getRequestParser().isProtocolVersion_2() && !this.headers.isChunked()) {
            this.connection.getConnectionHandler().sendError(this.response, 400);
            return false;
         } else {
            if (clen > 0L) {
               int maxPostSize = httpServer.getMaxPostSize();
               if (maxPostSize > 0 && clen > (long)maxPostSize) {
                  HTTPLogger.logPOSTSizeExceeded(maxPostSize);
                  this.connection.getConnectionHandler().sendError(this.response, 413);
                  return false;
               }
            }

            return true;
         }
      }
   }

   void setCheckIndexFile(boolean b) {
      this.checkIndexFile = b;
   }

   public static ServletRequestImpl getOriginalRequest(ServletRequest rq) {
      if (rq == null) {
         return null;
      } else {
         while(rq instanceof ServletRequestWrapper) {
            rq = ((ServletRequestWrapper)rq).getRequest();
         }

         if (rq == null) {
            throw new AssertionError("Original request not available");
         } else {
            return (ServletRequestImpl)rq;
         }
      }
   }

   public VirtualConnection getConnection() {
      return this.connection;
   }

   public void run() {
      this.handleCorrelationIfPresent();
      this.runInternal();
   }

   private void runInternal() {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[1];
            var5.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var5);
         InstrumentationSupport.preProcess(var5);
         var5.resetPostBegin();
      }

      label657: {
         label658: {
            try {
               WebAppServletContext ctx = this.context;
               if (this.dispatchDueToRemoteSessionFetching()) {
                  break label657;
               }

               try {
                  if (WebAppConfigManager.useExtendedSessionFormat()) {
                     registry.getContainerSupportProvider().setServerChannelThreadLocal(this.connection.getChannel());
                  }

                  try {
                     if (this.context.getVersionId() != null && this.dispatchVersion()) {
                        break label658;
                     }
                  } catch (RuntimeException var31) {
                     this.response.disableKeepAlive();
                     this.response.cleanupRequest(var31);
                     throw var31;
                  }

                  MethodInvocationHelper.pushMethodObject((BeanInfo)null);
                  this.context.getSecurityManager().initContextHandler(this);

                  try {
                     if (!this.context.getServer().getMBean().isKeepAliveEnabled()) {
                        this.response.disableKeepAlive();
                     }

                     this.initServerNameAndPort();
                     this.connection.initCerts();
                     if (this.checkIndexFile) {
                        ServletStubImpl servletStub = this.context.getIndexServletStub(this.getRelativeUri(), this, this);
                        if (servletStub != null) {
                           this.setServletStub(servletStub);
                        }

                        this.checkIndexFile = false;
                     }

                     if (this.performOverloadAction) {
                        this.sendOverLoadResponse(ctx);
                     } else {
                        this.context.execute(this, this.response);
                     }
                  } finally {
                     MethodInvocationHelper.popMethodObject((BeanInfo)null);
                     ctx.getSecurityManager().resetContextHandler();
                     if (!this.performOverloadAction && !this.deferCompleteResponse() && !this.getConnection().isClosed()) {
                        this.response.send();
                     }

                     this.performOverloadAction = false;
                     if (this.needToSyncSessionAfterSent) {
                        this.response.syncSession();
                     }

                  }
               } catch (SocketTimeoutException var32) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPLogger.logIOException(this.context.toString(), var32);
                  }

                  this.response.disableKeepAlive();
                  this.response.cleanupRequest(var32);
               } catch (ServletNestedRuntimeException var33) {
                  if (var33.getCause() instanceof SocketTimeoutException) {
                     if (HTTPDebugLogger.isEnabled()) {
                        HTTPLogger.logIOException(this.context.toString(), var33);
                     }

                     this.response.disableKeepAlive();
                     this.response.cleanupRequest(var33);
                  }
               } catch (SocketException var34) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPLogger.logServletFailed(ctx.getLogContext(), var34);
                  }
               } catch (IOException var35) {
                  if (HTTPDebugLogger.shouldLogIOException(var35)) {
                     HTTPLogger.logServletFailed(ctx.getLogContext(), var35);
                  }
               } finally {
                  if (WebAppConfigManager.useExtendedSessionFormat()) {
                     registry.getContainerSupportProvider().setServerChannelThreadLocal((ServerChannel)null);
                  }

               }
            } catch (Throwable var37) {
               if (var5 != null) {
                  var5.th = var37;
                  InstrumentationSupport.postProcess(var5);
               }

               throw var37;
            }

            if (var5 != null) {
               InstrumentationSupport.postProcess(var5);
            }

            return;
         }

         if (var5 != null) {
            InstrumentationSupport.postProcess(var5);
         }

         return;
      }

      if (var5 != null) {
         InstrumentationSupport.postProcess(var5);
      }

   }

   private void sendOverLoadResponse(WebAppServletContext ctx) throws IOException {
      this.setAttribute("javax.servlet.error.message", this.overloadRejectionMessage);
      ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(ctx.getServletClassLoader());
         this.response.sendError(this.context.getServer().getMBean().getOverloadResponseCode());
      } finally {
         this.getSessionHelper().syncSession();
         this.context.getServer().getLogManager().log(this, this.response);
         this.getConnection().close();
         Thread.currentThread().setContextClassLoader(oldLoader);
      }

   }

   public Runnable overloadAction(String reason) {
      Object[] var10000;
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var2.argsCapture) {
            var2.args = new Object[2];
            var10000 = var2.args;
            var10000[0] = this;
            var10000[1] = reason;
         }

         InstrumentationSupport.createDynamicJoinPoint(var2);
         InstrumentationSupport.preProcess(var2);
         var2.resetPostBegin();
      }

      ServletRequestImpl var5;
      label90: {
         try {
            if (!this.context.isInternalApp() && !this.isAdminChannelRequest()) {
               this.performOverloadAction = true;
               this.overloadRejectionMessage = reason;
               var5 = this;
               break label90;
            }

            var10000 = null;
         } catch (Throwable var4) {
            if (var2 != null) {
               var2.th = var4;
               var2.ret = null;
               InstrumentationSupport.postProcess(var2);
            }

            throw var4;
         }

         if (var2 != null) {
            var2.ret = var10000;
            InstrumentationSupport.postProcess(var2);
         }

         return var10000;
      }

      if (var2 != null) {
         var2.ret = var5;
         InstrumentationSupport.postProcess(var2);
      }

      return var5;
   }

   public Runnable cancel(String reason) {
      Object[] var10000;
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var2.argsCapture) {
            var2.args = new Object[2];
            var10000 = var2.args;
            var10000[0] = this;
            var10000[1] = reason;
         }

         InstrumentationSupport.createDynamicJoinPoint(var2);
         InstrumentationSupport.preProcess(var2);
         var2.resetPostBegin();
      }

      Runnable var5;
      label85: {
         try {
            if (System.identityHashCode(Thread.currentThread()) == this.muxerThreadHash) {
               var5 = this.overloadAction(reason);
               break label85;
            }

            var10000 = null;
         } catch (Throwable var4) {
            if (var2 != null) {
               var2.th = var4;
               var2.ret = null;
               InstrumentationSupport.postProcess(var2);
            }

            throw var4;
         }

         if (var2 != null) {
            var2.ret = var10000;
            InstrumentationSupport.postProcess(var2);
         }

         return var10000;
      }

      if (var2 != null) {
         var2.ret = var5;
         InstrumentationSupport.postProcess(var2);
      }

      return var5;
   }

   public void send100ContinueResponse() throws IOException {
      OutputStream out = this.getConnection().getSocket().getOutputStream();
      out.write(HttpConstants.SC_CONTINUE_RESPONSE, 0, HttpConstants.SC_CONTINUE_RESPONSE.length);
   }

   private void reInitContextIfNeeded(SessionInternal session) {
      if (this.context == null || this.context.getVersionId() != null && !this.context.getVersionId().equals(session.getVersionId())) {
         WebAppServletContext ctx = (WebAppServletContext)((HttpSession)session).getServletContext();
         if (HTTPDebugLogger.isEnabled()) {
            this.trace("reInitContext from session for session=" + this.sessionHelper.getSessionID() + ", old version=" + this.context.getVersionId() + ", new ctx=" + ctx);
         }

         this.initContext(ctx);
         this.response.initContext(ctx);
      }

   }

   private boolean dispatchDueToRemoteSessionFetching() {
      if (this.preventRedispatch) {
         return false;
      } else {
         WorkManager sessionFetchingWorkManager = this.getServletStub().getWorkManagerForSessionFetching();
         if (HTTPDebugLogger.isEnabled()) {
            this.trace("[RemoteSessionFetching] obtained workManager: " + sessionFetchingWorkManager);
         }

         if (sessionFetchingWorkManager == null) {
            return false;
         } else {
            SessionContext sessionContext = this.context.getSessionContext();
            if (HTTPDebugLogger.isEnabled()) {
               this.trace("[RemoteSessionFetching] obtained session context: " + sessionContext);
            }

            if (!(sessionContext instanceof ReplicatableSessionContext)) {
               return false;
            } else {
               String sessionID = this.sessionHelper.getRequestedSessionId(false);
               if (HTTPDebugLogger.isEnabled()) {
                  this.trace("[RemoteSessionFetching] obtained sessionId: " + sessionID);
               }

               if (((ReplicatableSessionContext)sessionContext).isPrimaryOrSecondary(sessionID)) {
                  if (HTTPDebugLogger.isEnabled()) {
                     this.trace("[RemoteSessionFetching] session is primary or secondary !");
                  }

                  return false;
               } else {
                  if (HTTPDebugLogger.isEnabled()) {
                     this.trace("[RemoteSessionFetching] obtained sessionFetchingWorkManager and dispatching to " + sessionFetchingWorkManager);
                  }

                  this.preventRedispatch = true;
                  sessionFetchingWorkManager.schedule(this);
                  return true;
               }
            }
         }
      }
   }

   private boolean dispatchVersion() throws IOException {
      WebAppServletContext oldCtx = this.getContext();
      WebAppServletContext newCtx = this.findVersionedContext();
      if (newCtx != null && newCtx != oldCtx) {
         if (HTTPDebugLogger.isEnabled()) {
            this.trace("re-dispatch request to " + newCtx);
         }

         this.initContext(newCtx);
         this.response.initContext(newCtx);
         AbstractHttpConnectionHandler connHandler = this.connection.getConnectionHandler();
         if (connHandler != null && connHandler.initAndValidateRequest(newCtx)) {
            WorkManager newWorkMgr = this.getServletStub().getWorkManager();
            if (newWorkMgr == null) {
               throw new AssertionError("Could not determine WorkManager for : " + this);
            }

            newWorkMgr.schedule(this);
         }

         return true;
      } else {
         if (HTTPDebugLogger.isEnabled()) {
            this.trace("dispatch request to " + oldCtx);
         }

         return false;
      }
   }

   private WebAppServletContext findVersionedContext() {
      WebAppServletContext oldCtx = this.getContext();
      if (this.contextManager == null) {
         return oldCtx;
      } else {
         String rsid = this.sessionHelper.getRequestedSessionId(false);
         if (rsid == null) {
            return oldCtx;
         } else {
            WebAppServletContext newCtx = this.contextManager.getContextForSession(rsid);
            if (newCtx == null) {
               if (HTTPDebugLogger.isEnabled()) {
                  this.trace("Finding Versioned ServletContext with Application Version Info for SessionID : " + rsid);
               }

               String verId = null;

               try {
                  verId = oldCtx.getSessionContext().lookupAppVersionIdForSession(rsid, this, this.response);
               } catch (Throwable var10) {
                  HTTPLogger.logErrorWithThrowable(oldCtx.getLogContext(), "Error Fetching Version Infomation for Application Version ", var10);
               }

               if (verId == null) {
                  boolean adminMode = this.isAdminChannelRequest();
                  Iterator all = this.contextManager.getServletContexts(adminMode);

                  while(verId == null && all.hasNext()) {
                     WebAppServletContext otherCtx = (WebAppServletContext)all.next();
                     if (otherCtx != null && otherCtx != oldCtx) {
                        try {
                           verId = otherCtx.getSessionContext().lookupAppVersionIdForSession(rsid, this, this.getResponse());
                        } catch (Throwable var9) {
                           HTTPLogger.logErrorWithThrowable(otherCtx.getLogContext(), "Error Fetching Version Infomation for Application Version ", var9);
                        }
                     }
                  }
               }

               if (verId != null) {
                  newCtx = this.contextManager.getContext(verId);
               }
            }

            return newCtx == null ? oldCtx : newCtx;
         }
      }
   }

   HttpServletRequest copy() {
      if (this.sessionHelper.isClone) {
         throw new IllegalStateException("Cannot clone a request from a cloned request.");
      } else {
         ServletRequestImpl copy = new ServletRequestImpl(this.connection.getConnectionHandler(), this.sessionHelper);
         copy.initFromRequestParser(this.getInputHelper().getRequestParser());
         if (this.getAttribute("javax.servlet.forward.request_uri") != null) {
            String fwdUri = this.getRequestURI();
            copy.initFromRequestURI(fwdUri);
         }

         copy.setResponse(this.getResponse());
         if (this.contextManager != null && this.contextManager.isVersioned()) {
            copy.initContextManager(this.contextManager);
         } else {
            copy.initContext(this.context);
         }

         if (this.inputEncoding != null) {
            try {
               copy.setCharacterEncoding(this.inputEncoding);
            } catch (UnsupportedEncodingException var4) {
            }
         }

         copy.initLocales();
         copy.setInputStream(this.inputStream);
         copy.setServletStub(this.getServletStub());
         copy.useInputStream = this.useInputStream;
         copy.useReader = this.useReader;
         copy.setServerName(this.serverName);
         copy.setServerPort(this.serverPort);
         copy.servletPath = this.servletPath;
         copy.pathInfo = this.pathInfo;
         copy.setCheckIndexFile(this.checkIndexFile);
         copy.setCurrentSubject(this.currentSubject);
         if (this.getSendRedirect()) {
            copy.setRedirectURI(this.redirectLocation);
         }

         this.parameters.parseQueryParams(true);
         copy.parameters.setQueryParams(this.parameters.getQueryParams());
         copy.parameters.setExtraQueryParams(this.parameters.getExtraQueryParams());
         Iterator it = this.attributes.keys();

         while(it.hasNext()) {
            String key = (String)it.next();
            copy.attributes.put(key, this.attributes.get(key, this.context));
         }

         return copy;
      }
   }

   public AsyncContext getAsyncContext() {
      if (!this.isAsyncMode()) {
         throw new IllegalStateException("The request has not been put into asynchronous mode: " + this);
      } else {
         return this.async;
      }
   }

   public ServletContext getServletContext() {
      return this.context;
   }

   public boolean isAsyncStarted() {
      return this.isAsyncMode() && (this.async.isStarted() || this.async.isAsyncWait() || this.async.isAsyncCompleting());
   }

   public boolean isAsyncSupported() {
      return this.asyncSupported;
   }

   public AsyncContext startAsync() throws IllegalStateException {
      return this.startAsync(this, this.getResponse(), false);
   }

   public AsyncContext startAsync(ServletRequest req, ServletResponse resp) throws IllegalStateException {
      if (req != null && resp != null) {
         return this.startAsync(req, resp, true);
      } else {
         throw new IllegalArgumentException("Neither ServletRequest nor ServletResponse can be null.");
      }
   }

   private AsyncContext startAsync(ServletRequest req, ServletResponse resp, boolean initWithReqAndResp) throws IllegalStateException {
      if (!this.isAsyncSupported()) {
         throw new IllegalStateException("The async-support is disabled on this request: " + this);
      } else {
         this.connection.getConnectionHandler().disableReuse();
         if (this.isAsyncMode()) {
            this.async.reInitialize(this.getWebAppServletContext(req), req, resp, initWithReqAndResp);
            return this.async;
         } else {
            this.async = new AsyncContextImpl(this.getWebAppServletContext(req), req, resp, initWithReqAndResp);
            return this.async;
         }
      }
   }

   private WebAppServletContext getWebAppServletContext(ServletRequest req) {
      ServletContext sc = req.getServletContext();
      return sc instanceof WebAppServletContext ? (WebAppServletContext)sc : this.context;
   }

   void setAsyncSupported(boolean isAsyncSupported) {
      this.asyncSupported &= isAsyncSupported;
   }

   public boolean isAsyncMode() {
      return this.async != null;
   }

   public Part getPart(String name) throws IOException, ServletException {
      return this.parameters.getPart(name);
   }

   public Collection getParts() throws IOException, ServletException {
      return this.parameters.getParts();
   }

   public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
      if (this.getUserPrincipal() != null) {
         return true;
      } else {
         AuthenticateResponseWrapper responseWrapper = new AuthenticateResponseWrapper(response);
         boolean ret = false;

         boolean var4;
         try {
            if (AUTH_RECURSION_CHECK.get() == Boolean.TRUE) {
               ret = this.context.getSecurityManagerWithPrivilege().checkAccess(this, responseWrapper, true, false, true);
               var4 = this.postAuthenticate(ret, responseWrapper);
               return var4;
            }

            AUTH_RECURSION_CHECK.set(true);
            ret = this.context.getSecurityManagerWithPrivilege().checkAccess(this, responseWrapper, true, false, false);
            var4 = this.postAuthenticate(ret, responseWrapper);
         } finally {
            AUTH_RECURSION_CHECK.remove();
         }

         return var4;
      }
   }

   private boolean postAuthenticate(boolean ret, AuthenticateResponseWrapper responseWrapper) throws IOException, ServletException {
      SubjectHandle subject = SecurityModule.getCurrentUser(this.context.getSecurityContext(), this);
      if (this.context.getSecurityManagerWithPrivilege().postCheckAccess(responseWrapper)) {
         this.pushSubjectToSecurityProvider(subject);
         return ret;
      } else if (responseWrapper.getErrorCode() != -1) {
         throw new ServletException("Authenticate Error happened, Error Code is: " + responseWrapper.getErrorCode() + ", and Error Message is: " + responseWrapper.getErrorMsg());
      } else {
         this.pushSubjectToSecurityProvider(subject);
         return ret;
      }
   }

   private boolean isSAMConfigured() {
      WebAppSecurity securityManager = this.context.getSecurityManagerWithPrivilege();
      ServerAuthConfig serverAuthConfig = JaspicUtilities.getServerAuthConfig(this.context.getSecurityContext(), "HttpServlet", SecurityModule.getAppContextId(this.context.getSecurityContext()), securityManager.getJaspicListener());
      return securityManager.isJaspicEnabled() && serverAuthConfig != null;
   }

   private ServerAuthContext getServerAuthContext(ServerAuthConfig serverAuthConfig, MessageInfoImpl messageInfo) throws AuthException {
      String authContextID = serverAuthConfig.getAuthContextID(messageInfo);
      ServerAuthContext serverContext = serverAuthConfig.getAuthContext(authContextID, (Subject)null, JaspicSecurityModule.createOptionsMap(new ServerAuthSupport() {
         public String getRealmBanner() {
            return SecurityModule.constructAuthRealmBanner(ServletRequestImpl.this.context.getSecurityContext().getAuthRealmName());
         }

         public String getErrorPage(int code) {
            return ServletRequestImpl.this.context.getSecurityContext().getErrorPage(code);
         }

         public boolean isEnforceBasicAuth() {
            return WebAppSecurity.getProvider().getEnforceValidBasicAuthCredentials();
         }
      }));
      return serverContext;
   }

   public void login(String username, String password) throws ServletException {
      if (this.isSAMConfigured()) {
         throw new ServletException(HTTPLogger.logError("Operation not supported for configured authentication mechanism", ""));
      } else if (this.getUserPrincipal() != null) {
         throw new ServletException("Attempt to re-login while the user already exists");
      } else {
         this.context.getSecurityManagerWithPrivilege().login(username, password, this, this.response);
      }
   }

   public void logout() throws ServletException {
      WebAppSecurity securityManager = this.context.getSecurityManagerWithPrivilege();

      try {
         if (LOGOUT_RECURSION_CHECK.get() == null && this.isSAMConfigured()) {
            LOGOUT_RECURSION_CHECK.set(true);
            ServerAuthConfig serverAuthConfig = null;
            serverAuthConfig = JaspicUtilities.getServerAuthConfig(this.context.getSecurityContext(), "HttpServlet", SecurityModule.getAppContextId(this.context.getSecurityContext()), securityManager.getJaspicListener());
            ResourceConstraint resourceConstraint = securityManager.getConstraint(this);
            MessageInfoImpl messageInfo = new MessageInfoImpl(this, this.response, JaspicSecurityModule.createMap(securityManager, this, this.response, SecurityModule.getCurrentUser(this.context.getSecurityContext(), this), this.context.getSecurityContext(), resourceConstraint));
            ServerAuthContext serverContext = this.getServerAuthContext(serverAuthConfig, messageInfo);
            Subject subject = null;
            SubjectHandle subjectHandle = (SubjectHandle)AccessController.doPrivileged(new PrivilegedAction() {
               public SubjectHandle run() {
                  return WebAppSecurity.getProvider().getCurrentSubject();
               }
            });
            if (subjectHandle != null) {
               subject = WebAppSecurity.getSecurityServices().toSubject(subjectHandle);
            }

            if (subject == null) {
               subject = new Subject();
            }

            this.currentSubject = null;
            serverContext.cleanSubject(messageInfo, subject);
         }
      } catch (AuthException var11) {
         HTTPLogger.logException(var11.getMessage(), var11);
         throw new ServletNestedRuntimeException(var11);
      } finally {
         securityManager.logout(this);
         this.context.getEventsManager().notifyLogoutEvent(this.getSession(false));
         LOGOUT_RECURSION_CHECK.remove();
      }

   }

   public DispatcherType getDispatcherType() {
      return this.dispatchType;
   }

   public void setDispatcherType(DispatcherType dispatchType) {
      this.dispatchType = dispatchType;
   }

   private void pushSubjectToSecurityProvider(final SubjectHandle subject) {
      if (subject != null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Void run() {
               WebAppSecurity.getProvider().pushSubject(subject);
               return null;
            }
         });
      }

   }

   private boolean deferCompleteResponse() {
      return this.isFutureResponseEnabled() || this.isAsyncMode();
   }

   boolean isUpgrade() {
      return this.getServletInputStreamImpl() == null ? false : this.getServletInputStreamImpl().isUpgrade();
   }

   HttpUpgradeHandler getHttpUpgradeHandler() {
      return this.httpUpgradeHandler;
   }

   public HttpUpgradeHandler upgrade(Class handlerClass) throws IOException {
      try {
         this.httpUpgradeHandler = (HttpUpgradeHandler)this.getContext().createInstance(handlerClass);
         ((ServletInputStreamImpl)this.getInputStream()).setUpgrade(true);
         ((ServletOutputStreamImpl)this.getResponse().getOutputStreamNoCheck()).setUpgrade(true);
      } catch (InstantiationException var3) {
         HTTPLogger.logRequestUpgradeError("InstantiationException", var3.getMessage());
         var3.printStackTrace();
      } catch (IllegalAccessException var4) {
         HTTPLogger.logRequestUpgradeError("IllegalAccessException", var4.getMessage());
         var4.printStackTrace();
      } catch (ClassNotFoundException var5) {
         HTTPLogger.logRequestUpgradeError("ClassNotFoundException", var5.getMessage());
         var5.printStackTrace();
      }

      return this.httpUpgradeHandler;
   }

   public String changeSessionId() {
      HttpSession session = this.sessionHelper.getSession(false);
      if (session == null) {
         throw new IllegalStateException("There is no session associated with the request");
      } else {
         this.sessionHelper.updateSessionId();
         return session.getId();
      }
   }

   private void handleCorrelationIfPresent() {
      if (!CorrelationManager.DMS_HTTP_HEADER_DISABLED) {
         String dmsWrap = this.getHeader("ECID-Context");
         if (dmsWrap == null || !CorrelationManager.unwrapHTTP(dmsWrap)) {
            if ("POST".equals(this.getMethod())) {
               dmsWrap = CorrelationManager.getWrappedContextFromQueryString(this.getQueryString());
               if (dmsWrap == null || !CorrelationManager.unwrapHTTP(dmsWrap)) {
                  ;
               }
            } else {
               dmsWrap = this.getParameter("ECID-Context");
               if (dmsWrap != null) {
                  CorrelationManager.unwrapHTTP(dmsWrap);
               }

            }
         }
      }
   }

   boolean isConnectionClosingForcibly() {
      return Boolean.TRUE.equals(this.getAttribute("weblogic.http.connection.forceClose"));
   }

   static {
      _WLDF$INST_FLD_Servlet_Request_Cancel_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Request_Cancel_Around_High");
      _WLDF$INST_FLD_Servlet_Request_Run_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Request_Run_Around_Medium");
      _WLDF$INST_FLD_Servlet_Request_Overload_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Request_Overload_Around_High");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ServletRequestImpl.java", "weblogic.servlet.internal.ServletRequestImpl", "runInternal", "()V", 1740, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Request_Run_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Request_Run_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ServletRequestImpl.java", "weblogic.servlet.internal.ServletRequestImpl", "overloadAction", "(Ljava/lang/String;)Ljava/lang/Runnable;", 1848, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Request_Overload_Around_High"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Request_Overload_Around_High};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ServletRequestImpl.java", "weblogic.servlet.internal.ServletRequestImpl", "cancel", "(Ljava/lang/String;)Ljava/lang/Runnable;", 1862, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Request_Cancel_Around_High"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Request_Cancel_Around_High};
      HEADERS_TO_SHOW_ON_MONITORING = new String[]{"HOST", "REMOTE_USER", "SM_USER", "HTTP_SM_USER", "SMUSER"};
      isWLProxyHeadersAccessible = Boolean.getBoolean("weblogic.http.isWLProxyHeadersAccessible");
      isRemoteUserHeaderAccessible = Boolean.getBoolean("weblogic.http.enableRemoteUserHeader");
      registry = WebServerRegistry.getInstance();
      ignorePluginParamsForCookiePath = Boolean.getBoolean("weblogic.cookies.ignorePluginParamsForCookiePath");
      AUTH_RECURSION_CHECK = new ThreadLocal();
      LOGOUT_RECURSION_CHECK = new ThreadLocal();
   }

   private final class AuthenticateResponseWrapper extends HttpServletResponseWrapper {
      private String errorMsg;
      private int errorCode = -1;

      public AuthenticateResponseWrapper(HttpServletResponse response) {
         super(response);
      }

      public void sendError(int sc, String msg) throws IOException {
         this.errorCode = sc;
         this.errorMsg = msg;
      }

      public void sendError(int sc) throws IOException {
         this.errorCode = sc;
      }

      public void flushBuffer() throws IOException {
         if (this.errorCode == -1) {
            ServletRequestImpl.this.response.flushBuffer();
         } else if (this.errorMsg == null) {
            ServletRequestImpl.this.response.sendError(this.errorCode);
         } else {
            ServletRequestImpl.this.response.sendError(this.errorCode, this.errorMsg);
         }

      }

      public int getErrorCode() {
         return this.errorCode;
      }

      public String getErrorMsg() {
         return this.errorMsg;
      }
   }

   public static final class SessionHelper {
      private final ServletRequestImpl request;
      private HttpSession session;
      private final Map allSessions;
      private final Map allExpiringSessions;
      private boolean sessionInitialized;
      private boolean fromCookie;
      private boolean fromURL;
      private boolean sessionExistanceChecked;
      private String sessionID;
      private String requestedSessionID;
      private String encodedSessionID;
      private final HashMap sessionIds;
      private final boolean isClone;
      private static final boolean tryAllCookies = Boolean.getBoolean("weblogic.http.tryAllCookies");

      private SessionHelper(ServletRequestImpl r, SessionHelper orig) {
         this.request = r;
         if (orig != null) {
            this.allSessions = orig.allSessions;
            this.allExpiringSessions = orig.allExpiringSessions;
            this.sessionIds = orig.sessionIds;
            this.isClone = true;
         } else {
            this.allSessions = new HashMap();
            this.allExpiringSessions = new HashMap();
            this.sessionIds = new HashMap();
            this.isClone = false;
         }

      }

      private void reset() {
         this.sessionInitialized = false;
         this.sessionExistanceChecked = false;
         this.fromCookie = false;
         this.fromURL = false;
         this.sessionIds.clear();
         this.session = null;
         this.allSessions.clear();
         this.allExpiringSessions.clear();
         this.encodedSessionID = null;
         this.sessionID = null;
         this.requestedSessionID = null;
      }

      private String getSessionIDFromMap(String cookieName, String cookiePath) {
         return (String)this.sessionIds.get(new CookieKey(cookieName, cookiePath));
      }

      void rememberSessionID(String cookieName, String cookiePath, String s) {
         this.sessionIds.put(new CookieKey(cookieName, cookiePath), s);
      }

      void setSession(HttpSession s) {
         this.session = s;
         this.sessionInitialized = true;
      }

      public String getEncodedSessionID() {
         return this.encodedSessionID;
      }

      boolean getSessionExistanceChecked() {
         return this.sessionExistanceChecked;
      }

      void setSessionExistanceChecked(boolean b) {
         this.sessionExistanceChecked = b;
      }

      public HttpSession getSession() {
         return this.getSession(true);
      }

      public HttpSession getSession(boolean flag) {
         if (!this.isClone) {
            return this.getSessionInternal(flag);
         } else {
            synchronized(this.allSessions) {
               return this.getSessionInternal(flag);
            }
         }
      }

      private HttpSession getSessionInternal(boolean flag) {
         HttpSession session = this._getSessionInternal(flag);
         if (flag) {
            return session;
         } else {
            SessionInternal s = (SessionInternal)session;
            return session == null || !s.isInvalidating() && s.isExpiring() ? null : session;
         }
      }

      private HttpSession _getSessionInternal(boolean flag) {
         if (!this.sessionInitialized) {
            this.initSessionInfo();
         }

         if (this.isValidSession(this.session)) {
            return this.session;
         } else {
            String contextPath = this.request.getContext().getContextPath();
            this.session = (HttpSession)this.allSessions.get(contextPath);
            if (this.isValidSession(this.session)) {
               return this.session;
            } else {
               SessionInternal s = (SessionInternal)this.allSessions.remove(contextPath);
               if (s != null && s.isValid()) {
                  this.allExpiringSessions.put(contextPath, s);
               }

               if (!flag && this.sessionExistanceChecked) {
                  return null;
               } else {
                  this.sessionExistanceChecked = true;
                  String sid = this.getSessionIDFromMap(this.request.getContext().getSessionContext().getConfigMgr().getCookieName(), this.request.getContext().getSessionContext().getConfigMgr().getCookiePath());
                  if (sid == null) {
                     sid = this.sessionID != null && !this.sessionID.equals(SessionData.getID(this.requestedSessionID)) ? this.sessionID : this.requestedSessionID;
                  }

                  if (sid == null) {
                     if (!flag) {
                        return null;
                     }

                     this.getNewSession((String)null);
                  } else {
                     this.session = this.getValidSession(sid);
                     if (this.session == null) {
                        if (!flag) {
                           return null;
                        }

                        this.getNewSession(sid);
                        if (this.session == null) {
                           return null;
                        }
                     }

                     if (this.sessionID.length() != this.request.getContext().getSessionContext().getConfigMgr().getIDLength()) {
                        this.updateSessionId();
                     }
                  }

                  this.storeSessionInAllSessions(contextPath, this.session);
                  return this.session;
               }
            }
         }
      }

      private boolean isValidSession(HttpSession session) {
         boolean isSessionValid = false;
         SessionInternal s = (SessionInternal)session;
         if (session != null && s.isValid() && (s.isInvalidating() || !s.isExpiring())) {
            isSessionValid = true;
         }

         return isSessionValid;
      }

      public void killOldSession() {
         Iterator iter = this.allSessions.values().iterator();

         while(iter.hasNext()) {
            HttpSession s = (HttpSession)iter.next();
            if (((SessionInternal)s).isValid()) {
               s.invalidate();
            }
         }

         String name = this.request.getContext().getSessionContext().getConfigMgr().getCookieName();
         String path = null;
         if (!ServletRequestImpl.ignorePluginParamsForCookiePath) {
            path = this.request.getResponse().processProxyPathHeaders(this.request.getContext().getSessionContext().getConfigMgr().getCookiePath());
         } else {
            path = this.request.getContext().getSessionContext().getConfigMgr().getCookiePath();
         }

         this.invalidateCookie(name, path);
         if (this.request.getContext().getServer().isAuthCookieEnabled()) {
            this.invalidateCookie(this.request.getContext().getSessionContext().getConfigMgr().getWLSAuthCookieName(), path);
         }

         this.clearInvalidSessions();
         this.session = null;
         this.sessionID = null;
         this.requestedSessionID = null;
      }

      void clearInvalidSessions() {
         Iterator iter = this.allSessions.entrySet().iterator();

         while(true) {
            while(iter.hasNext()) {
               Map.Entry entry = (Map.Entry)iter.next();
               SessionInternal session = (SessionInternal)entry.getValue();
               if (session != null && !session.isValid()) {
                  iter.remove();
               } else if (session != null && HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("[OOME_debug] [ServletRequestImpl.clearInvalidSessions] session " + session.getIdWithServerInfo() + ", activeRequestCount=" + session.getConcurrentRequestCount() + " remains in allSessions table.");
               }
            }

            return;
         }
      }

      void invalidateCookie(String name, String path) {
         Cookie cookie = this.request.getResponse().getCookie(name);
         if (cookie != null) {
            cookie.setValue("");
            cookie.setMaxAge(0);
         } else {
            cookie = new Cookie(name, "");
            cookie.setValue("");
            cookie.setMaxAge(0);
            String domain = this.request.getContext().getSessionContext().getConfigMgr().getCookieDomain();
            if (domain != null) {
               cookie.setDomain(domain);
            }

            cookie.setPath(path);
            if (this.request.getContext().getSessionContext().getConfigMgr().isCookieSecure()) {
               cookie.setSecure(true);
            }

            this.request.getResponse().addCookie(cookie);
         }

      }

      String getIncomingSessionCookieValue() {
         return this.requestedSessionID;
      }

      public String getRequestedSessionId() {
         return this.getRequestedSessionId(true);
      }

      public String getRequestedSessionId(boolean fetchSession) {
         if (!this.sessionInitialized) {
            this.initSessionInfo();
         }

         if (this.requestedSessionID != null && fetchSession) {
            HttpSession data = this.getSession(false);

            try {
               if (data != null && !data.isNew()) {
                  return data.getId();
               }
            } catch (IllegalStateException var5) {
               SessionInternal sess = (SessionInternal)data;
               if (sess.isValid()) {
                  throw var5;
               }
            }
         }

         return this.requestedSessionID;
      }

      public String getSessionID() {
         return this.sessionID;
      }

      public boolean isRequestedSessionIdFromCookie() {
         if (!this.sessionInitialized) {
            this.initSessionInfo();
         }

         return this.fromCookie;
      }

      public boolean isRequestedSessionIdFromURL() {
         if (!this.sessionInitialized) {
            this.initSessionInfo();
         }

         return this.fromURL;
      }

      public boolean isRequestedSessionIdFromUrl() {
         return this.isRequestedSessionIdFromURL();
      }

      public boolean isRequestedSessionIdValid() {
         if (this.requestedSessionID == null) {
            return false;
         } else {
            if (!this.sessionInitialized) {
               this.initSessionInfo();
            }

            SessionInternal hs = (SessionInternal)this.getSession(false);
            if (hs == null) {
               return false;
            } else {
               String oldid = SessionData.getID(this.requestedSessionID);
               String newid = hs.getInternalId();
               return oldid.equals(newid);
            }
         }
      }

      private boolean getSessionIdFromCookieHeaders(String sessionCookieName) {
         List cookieHeaders = this.request.getRequestHeaders().getCookieHeaders();
         if (cookieHeaders.size() == 0) {
            return false;
         } else {
            int namelen = sessionCookieName.length();
            byte startChar = (byte)sessionCookieName.charAt(0);
            String tmpSessionID = null;

            for(int k = cookieHeaders.size() - 1; k > -1; --k) {
               byte[] cookieHeader = (byte[])((byte[])cookieHeaders.get(k));
               int headerlen = cookieHeader.length;
               int readTo = headerlen - namelen;

               for(int i = 0; i < readTo; ++i) {
                  if (cookieHeader[i] != startChar) {
                     if (cookieHeader[i] != 32) {
                        while(i < readTo && cookieHeader[i] != 59 && cookieHeader[i] != 44) {
                           ++i;
                        }
                     }
                  } else {
                     int j;
                     for(j = 1; j < namelen && cookieHeader[i + j] == (byte)sessionCookieName.charAt(j); ++j) {
                     }

                     if (j >= namelen && cookieHeader[i + j] == 61) {
                        i += j;

                        boolean foundTerminator;
                        for(foundTerminator = false; i < headerlen && cookieHeader[i] == 32; ++i) {
                        }

                        while(i < headerlen && cookieHeader[i] == 61) {
                           ++i;
                        }

                        while(i < headerlen && cookieHeader[i] == 32) {
                           ++i;
                        }

                        while(i < headerlen && cookieHeader[i] == 34) {
                           ++i;
                        }

                        for(j = 0; i + j < headerlen; ++j) {
                           byte byteAt = cookieHeader[i + j];
                           if (byteAt == 59 || byteAt == 44 || byteAt == 34) {
                              foundTerminator = true;
                              break;
                           }
                        }

                        if (foundTerminator) {
                           this.requestedSessionID = StringUtils.getString(cookieHeader, i, j);
                        } else {
                           this.requestedSessionID = StringUtils.getString(cookieHeader, i, headerlen - i);
                        }

                        if (!tryAllCookies) {
                           return true;
                        }

                        if (tmpSessionID == null) {
                           tmpSessionID = this.requestedSessionID;
                        }

                        if (this.request.getContext() != null && this.request.getContext().getSessionContext().hasSession(this.requestedSessionID)) {
                           return true;
                        }
                     }
                  }
               }
            }

            if (tmpSessionID != null) {
               this.requestedSessionID = tmpSessionID;
               return true;
            } else {
               return false;
            }
         }
      }

      private void initSessionInfo() {
         if (!this.sessionInitialized) {
            WebAppServletContext context = this.request.getContext();
            if (context != null) {
               this.initSessionInfoWithContext(context);
            } else {
               boolean adminMode = this.request.isAdminChannelRequest();
               WebAppServletContext activeCtx = this.request.contextManager.getActiveContext(adminMode);
               if (activeCtx != null && activeCtx.getVersionId() == null) {
                  this.initSessionInfoWithContext(activeCtx);
               } else {
                  WebAppServletContext sessionIdCtx = null;
                  if (activeCtx == null || !this.initSessionInfoWithContext(activeCtx)) {
                     Iterator i = this.request.contextManager.getServletContexts(adminMode);

                     while(i.hasNext()) {
                        WebAppServletContext ctx = (WebAppServletContext)i.next();
                        if (ctx != activeCtx && this.initSessionInfoWithContext(ctx)) {
                           sessionIdCtx = ctx;
                           break;
                        }
                     }
                  }

                  if (this.sessionID != null) {
                     WebAppServletContext ctx = this.request.contextManager.getContextForSession(this.sessionID);
                     this.request.initContext(ctx);
                     if (HTTPDebugLogger.isEnabled() && ctx != null) {
                        this.request.trace("initContext from ctxManager lookup for sessionID=" + this.requestedSessionID + ", context=" + ctx);
                     }
                  }

                  if (this.request.context == null) {
                     if (activeCtx != null) {
                        this.request.initContext(activeCtx);
                        if (HTTPDebugLogger.isEnabled()) {
                           this.request.trace("initContext with active context=" + activeCtx);
                        }
                     } else {
                        this.request.initContext(sessionIdCtx);
                        if (HTTPDebugLogger.isEnabled()) {
                           this.request.trace("initContext with sessionId context=" + sessionIdCtx);
                        }
                     }
                  }
               }
            }

            this.sessionInitialized = true;
         }
      }

      private boolean initSessionInfoWithContext(WebAppServletContext ctx) {
         if (!ctx.getSessionContext().getConfigMgr().isSessionTrackingEnabled()) {
            return false;
         } else {
            String sessionCookieName = ctx.getSessionContext().getConfigMgr().getCookieName();
            if (sessionCookieName == null) {
               if (HTTPDebugLogger.isEnabled()) {
                  this.request.trace("session-cookie-name is set to null");
               }

               return false;
            } else {
               if (ctx.getSessionContext().getConfigMgr().isSessionCookiesEnabled()) {
                  if (this.request.cookiesParsed) {
                     this.getSessionIdFromParsedCookies(sessionCookieName);
                  } else if (this.getSessionIdFromCookieHeaders(sessionCookieName)) {
                     if (HTTPDebugLogger.isEnabled()) {
                        this.request.trace("SessionID: " + this.requestedSessionID + " found in cookie header");
                     }

                     this.fromCookie = true;
                  }
               }

               if (this.requestedSessionID == null && this.isUrlRewritingEnabled(ctx)) {
                  this.getSessionIdFromEncodedURL(sessionCookieName);
                  if (this.requestedSessionID == null) {
                     this.getSessionIdFromQueryParams(sessionCookieName);
                  }

                  if (this.requestedSessionID == null) {
                     this.getSessionIdFromPostData(ctx, sessionCookieName);
                  }
               }

               if (this.requestedSessionID == null) {
                  this.getSessionIdFromSSL(ctx);
               }

               if (this.requestedSessionID != null) {
                  this.sessionID = SessionData.getID(this.requestedSessionID);
                  if (HTTPDebugLogger.isEnabled()) {
                     this.request.trace("SessionID= " + this.sessionID + " found for WASC=" + ctx);
                  }
               } else if (HTTPDebugLogger.isEnabled()) {
                  this.request.trace("SessionID not found for WASC=" + ctx);
               }

               return this.requestedSessionID != null;
            }
         }
      }

      private boolean isUrlRewritingEnabled(WebAppServletContext ctx) {
         return ctx.getSessionContext().getConfigMgr().isUrlRewritingEnabled();
      }

      private void getSessionIdFromQueryParams(String sessionCookieName) {
         this.requestedSessionID = this.request.getRequestParameters().peekQueryParameter(sessionCookieName);
         if (this.requestedSessionID != null) {
            if (HTTPDebugLogger.isEnabled()) {
               this.request.trace("SessionID: " + this.requestedSessionID + " found in query params or URL");
            }

            this.fromURL = true;
         }

      }

      private void getSessionIdFromEncodedURL(String sessionCookieName) {
         List pathParameters = this.request.getInputHelper().getPathParameters();
         if (pathParameters != null && pathParameters.size() != 0) {
            Iterator var3 = pathParameters.iterator();

            while(var3.hasNext()) {
               String pathParam = (String)var3.next();
               int idx = pathParam.toLowerCase().indexOf(";" + sessionCookieName.toLowerCase() + "=");
               if (idx >= 0) {
                  int sessionIdStart = idx + sessionCookieName.length() + 2;
                  int sessionIdEnd = pathParam.indexOf(";", sessionIdStart);
                  if (sessionIdEnd < 0) {
                     this.encodedSessionID = pathParam.substring(sessionIdStart);
                  } else {
                     this.encodedSessionID = pathParam.substring(sessionIdStart, sessionIdEnd);
                  }
               }
            }

            if (this.encodedSessionID != null) {
               int jrouteIndex = this.encodedSessionID.lastIndexOf(58);
               if (jrouteIndex > 0) {
                  this.requestedSessionID = this.encodedSessionID.substring(0, jrouteIndex);
               } else {
                  this.requestedSessionID = this.encodedSessionID;
               }

               if (HTTPDebugLogger.isEnabled()) {
                  this.request.trace("SessionID: " + this.requestedSessionID + " found encoded with the URL");
               }

               this.fromURL = true;
            }
         }
      }

      private void getSessionIdFromParsedCookies(String sessionCookieName) {
         Cookie[] cookies = this.request.getCookies();
         if (cookies != null) {
            String tmpSessionID = null;

            for(int i = 0; i < cookies.length; ++i) {
               if (sessionCookieName.equalsIgnoreCase(cookies[i].getName())) {
                  this.requestedSessionID = cookies[i].getValue();
                  if (HTTPDebugLogger.isEnabled()) {
                     this.request.trace("SessionID: " + this.requestedSessionID + " found in cookie");
                  }

                  this.fromCookie = true;
                  if (!tryAllCookies) {
                     break;
                  }

                  if (tmpSessionID == null) {
                     tmpSessionID = this.requestedSessionID;
                  }

                  if (this.request.getContext() != null && this.request.getContext().getSessionContext().hasSession(this.requestedSessionID)) {
                     break;
                  }

                  this.requestedSessionID = tmpSessionID;
               }
            }

         }
      }

      private void getSessionIdFromPostData(WebAppServletContext ctx, String sessionCookieName) {
         if (ctx.getSessionContext().getConfigMgr().isUrlRewritingEnabled()) {
            this.requestedSessionID = this.request.getRequestParameters().peekPostParameter(sessionCookieName);
            if (this.requestedSessionID != null) {
               if (HTTPDebugLogger.isEnabled()) {
                  this.request.trace("SessionID: " + this.requestedSessionID + " found in post data");
               }

               this.fromURL = true;
            }

         }
      }

      private void getSessionIdFromSSL(WebAppServletContext ctx) {
         if (ctx.getSessionContext().getConfigMgr().isSSLTrackingEnabled()) {
            this.requestedSessionID = (String)this.request.getAttribute("javax.servlet.request.ssl_session_id");
            if (this.requestedSessionID != null && HTTPDebugLogger.isEnabled()) {
               this.request.trace("SessionID: " + this.requestedSessionID + " found in SSL");
            }

         }
      }

      void syncSession() {
         if (this.request.getContext() != null) {
            this.request.getContext().getServer().getWorkContextManager().copyThreadContexts(this.request.getContext(), this.request);
         }

         this.syncSessions(this.allSessions.values().iterator());
         this.syncSessions(this.allExpiringSessions.values().iterator());
         this.session = null;
         this.allSessions.clear();
         this.allExpiringSessions.clear();
         this.sessionInitialized = false;
      }

      private void syncSessions(Iterator iter) {
         while(iter.hasNext()) {
            HttpSession s = (HttpSession)iter.next();
            this.syncSession(s, this.request.getHttpAccountingInfo().getInvokeTime());
         }

      }

      private void syncSession(HttpSession session, long invokeTime) {
         if (session instanceof SharedSessionData) {
            session = ((SharedSessionData)session).getSession();
         }

         SessionData sess = (SessionData)session;
         if (sess.isValid()) {
            SessionContext ctx = sess.getContext();
            if (!ctx.getConfigMgr().isSessionTrackingEnabled()) {
               session.invalidate();
            } else {
               try {
                  if (session.isNew() || sess.getLAT() < invokeTime) {
                     sess.setLastAccessedTime(invokeTime);
                  }

                  sess.setNew(false);
                  ctx.sync(sess);
               } catch (IllegalStateException var7) {
                  if (sess.isValid()) {
                     throw var7;
                  }
               }

            }
         }
      }

      private void getNewSession(String id) {
         if (HTTPDebugLogger.isEnabled()) {
            this.request.trace("Creating new session");
         }

         this.session = this.request.getContext().getSessionContext().getNewSession(id, this.request, this.request.getResponse());
         if (this.session == null) {
            this.sessionID = null;
            HTTPLogger.logSessionCreateError(this.request.getContext().getLogContext());
         } else {
            this.sessionID = ((SessionInternal)this.session).getInternalId();
            if (HTTPDebugLogger.isEnabled()) {
               this.request.trace("New Session: " + this.sessionID);
            }

            this.request.getResponse().setSessionCookie(this.session);
            this.request.getContext().getServer().getSessionLogin().register(((SessionInternal)this.session).getInternalId(), this.request.getContext().getContextPath());
         }

      }

      private HttpSession getValidSession(String id) {
         if (id != null && id.length() >= 1) {
            if (HTTPDebugLogger.isEnabled()) {
               this.request.trace("Trying to find session: " + id);
            }

            Object sess;
            try {
               sess = this.request.getContext().getSessionContext().getSessionInternal(id, this.request, this.request.getResponse());
            } catch (IllegalStateException var4) {
               if (HTTPDebugLogger.isEnabled()) {
                  this.request.trace(var4, "Exception finding session for id: " + id);
               }

               sess = null;
            }

            if (sess == null) {
               if (HTTPDebugLogger.isEnabled()) {
                  this.request.trace("Trying other contexts to find valid session for id: " + id);
               }

               sess = this.request.getContext().getSessionContext().getSessionFromOtherContexts(id, this.request, this.request.getResponse());
            }

            if (sess == null) {
               sess = this.request.getContext().getSessionContext().getSharedSession(id, this.request, this.request.getResponse());
            }

            if (sess == null) {
               if (HTTPDebugLogger.isEnabled()) {
                  this.request.trace("Couldn't find valid session for id: " + id);
               }

               return null;
            } else {
               this.request.reInitContextIfNeeded((SessionInternal)sess);
               this.request.getResponse().setSessionCookie((HttpSession)sess);
               return (HttpSession)sess;
            }
         } else {
            return null;
         }
      }

      void resetSession(boolean initSess) {
         this.session = null;
         if (initSess) {
            this.sessionInitialized = false;
         }

         this.sessionExistanceChecked = false;
      }

      public void updateSessionId() {
         String oldSessionID = this.sessionID;
         String oldId = this.session.getId();
         this.sessionID = ((SessionInternal)this.session).changeSessionId((String)null);
         if (HTTPDebugLogger.isEnabled()) {
            this.request.trace("Update old sessionID: " + oldSessionID + " to new sessionID: " + this.sessionID);
         }

         this.request.getResponse().setSessionCookie(this.session);
         this.request.getContext().getEventsManager().notifySessionIdChangedEvent(this.session, oldId);
         WebAppServletContext[] contexts = this.request.getContext().getServer().getServletContextManager().getAllContexts();
         boolean isSessionSharingEnabled = false;
         SessionContext reqSessionCtxt = ((SessionInternal)this.session).getContext();
         if (reqSessionCtxt != null) {
            isSessionSharingEnabled = reqSessionCtxt.getConfigMgr().isSessionSharingEnabled();
         }

         for(int i = 0; i < contexts.length; ++i) {
            if (contexts[i].getSessionContext() != null && this.request.context != contexts[i] && (!isSessionSharingEnabled || this.request.context.getApplicationContext() != contexts[i].getApplicationContext())) {
               SessionContext sessionCtxt = contexts[i].getSessionContext();
               HttpSession sessionInOtherContext = sessionCtxt.getSessionInternal(oldSessionID, (ServletRequestImpl)null, (ServletResponseImpl)null);
               if (sessionInOtherContext != null) {
                  oldId = sessionInOtherContext.getId();
                  ((SessionInternal)sessionInOtherContext).changeSessionId(this.sessionID);
                  contexts[i].getServer().getSessionLogin().unregister(oldSessionID, contexts[i].getContextPath());
                  contexts[i].getServer().getSessionLogin().register(((SessionInternal)sessionInOtherContext).getInternalId(), contexts[i].getContextPath());
                  contexts[i].getEventsManager().notifySessionIdChangedEvent(sessionInOtherContext, oldId);
               }
            }
         }

      }

      public SessionInternal getSessionOnThisServer(SessionContext context) {
         String sid = this.getSessionIDFromMap(context.getConfigMgr().getCookieName(), context.getConfigMgr().getCookiePath());
         if (sid == null) {
            sid = this.requestedSessionID;
         }

         return sid == null ? null : context.getSessionInternal(sid, (ServletRequestImpl)null, (ServletResponseImpl)null);
      }

      private void storeSessionInAllSessions(String contextPath, HttpSession session) {
         if (session == null) {
            this.allSessions.remove(contextPath);
         } else {
            this.allSessions.put(contextPath, session);
         }

      }

      // $FF: synthetic method
      SessionHelper(ServletRequestImpl x0, SessionHelper x1, Object x2) {
         this(x0, x1);
      }
   }

   public static final class RequestInputHelper {
      private RequestParser parser = null;
      private String originalURI;
      private String normalizedURI;
      private List pathParameters;
      private boolean pathParamsParsed;

      private void reset() {
         this.normalizedURI = null;
         this.pathParamsParsed = false;
         this.pathParameters = null;
         this.originalURI = null;
         this.parser.reset();
      }

      void restore() {
         this.originalURI = this.parser.getRequestURI();
         this.pathParameters = this.parser.getPathParameters();
         this.pathParamsParsed = false;
         this.normalizedURI = this.parser.getNormalizedURI();
      }

      void initFromRequestParser(RequestParser p, RequestParameters parameters) {
         this.parser = p;
         this.originalURI = this.parser.getRequestURI();
         this.pathParameters = this.parser.getPathParameters();
         this.pathParamsParsed = false;
         this.normalizedURI = this.parser.getNormalizedURI();
         parameters.queryStringBuffer = this.parser.getHttpRequestBuffer();
         parameters.queryStringStart = this.parser.getQueryStringStart();
         parameters.queryStringLength = this.parser.getQueryStringLength();
      }

      void initFromRequestURI(String uri) {
         assert uri.indexOf("://") == -1 : "passed in uri should not have scheme";

         assert uri.indexOf(63) == -1 : "passed in uri should not have request parameter";

         if (uri.length() == 0) {
            uri = "/";
         }

         this.originalURI = uri;
         this.pathParamsParsed = false;
         this.pathParameters = new ArrayList();
         StringBuilder normalizedURIBuilder = new StringBuilder();
         int idx = 0;
         int semicolonIdx = 0;

         for(int slashIdx = 0; idx < this.originalURI.length(); ++idx) {
            char c = this.originalURI.charAt(idx);
            if (c == ';' && semicolonIdx <= slashIdx) {
               semicolonIdx = idx;
               normalizedURIBuilder.append(this.originalURI.substring(slashIdx, idx));
            } else if (c == '/' && slashIdx < semicolonIdx) {
               slashIdx = idx;
               this.pathParameters.add(this.originalURI.substring(semicolonIdx, idx));
               if (idx == this.originalURI.length() - 1) {
                  normalizedURIBuilder.append("/");
               }
            } else if (idx == this.originalURI.length() - 1) {
               if (slashIdx >= semicolonIdx) {
                  normalizedURIBuilder.append(this.originalURI.substring(slashIdx));
               } else {
                  this.pathParameters.add(this.originalURI.substring(semicolonIdx));
               }
            }
         }

         this.normalizedURI = HttpParsing.unescape(normalizedURIBuilder.toString(), HttpRequestParser.getURIDecodeEncoding());
         this.normalizedURI = FilenameEncoder.resolveRelativeURIPath(this.normalizedURI, true);
         if (this.normalizedURI == null) {
            throw new IllegalArgumentException("Unsafe path for the incoming request");
         }
      }

      public void setRequsetParser(RequestParser parser) {
         this.parser = parser;
      }

      public RequestParser getRequestParser() {
         return this.parser;
      }

      public String getRequestURI(String cookieName) {
         if (this.pathParameters != null && !this.pathParamsParsed && cookieName != null) {
            int idx = this.originalURI.toLowerCase().indexOf(";" + cookieName.toLowerCase() + "=");
            if (idx != -1) {
               int sessionIdEnd = idx + cookieName.length() + 2;

               int idIndex;
               for(idIndex = sessionIdEnd; idIndex < this.originalURI.length(); ++idIndex) {
                  char c = this.originalURI.charAt(idIndex);
                  if (c == '/' || c == ';' || c == '?') {
                     sessionIdEnd = idIndex;
                     break;
                  }
               }

               if (sessionIdEnd == idIndex) {
                  this.originalURI = this.originalURI.substring(0, idx) + this.originalURI.substring(sessionIdEnd);
               } else {
                  this.originalURI = this.originalURI.substring(0, idx);
               }
            }

            this.pathParamsParsed = true;
         }

         return this.originalURI;
      }

      public String getNormalizedURI() {
         return this.normalizedURI;
      }

      private String getOriginalURI() {
         return this.originalURI;
      }

      public List getPathParameters() {
         return this.pathParameters;
      }
   }

   public static final class RequestParameters {
      private final ServletRequestImpl request;
      private byte[] postData;
      private boolean postDataRead;
      private boolean postParamsParsed;
      private boolean queryParamsParsed;
      private QueryParams queryParameters = new QueryParams();
      private List extraParameters = new ArrayList();
      private byte[] queryStringBuffer = null;
      private int queryStringStart = -1;
      private int queryStringLength = -1;
      private boolean partParamsParsed = false;
      private Multipart multipart = null;

      public RequestParameters(ServletRequestImpl r) {
         this.request = r;
      }

      public void init() {
         if (this.request.context != null) {
            this.queryParameters.setLimit(this.request.context.getServer().getMaxRequestParameterCount());
         }

      }

      public void reset() {
         this.postParamsParsed = false;
         this.queryParamsParsed = false;
         this.postDataRead = false;
         this.postData = null;
         this.queryStringBuffer = null;
         this.queryStringStart = -1;
         this.queryStringLength = -1;
         this.queryParameters.clear();
         this.extraParameters.clear();
         this.partParamsParsed = false;
         this.multipart = null;
      }

      public String getQueryString(String encoding) {
         StringBuilder buffer = null;
         int size = this.extraParameters.size();
         if (size > 0) {
            buffer = new StringBuilder();

            for(int i = 0; i < size; ++i) {
               String s = this.extraParameters.get(i).toString();
               if (s != null && this.preferFowardQueryString() && !(this.extraParameters.get(i) instanceof ExtraIncludeParams)) {
                  return s;
               }

               if (s.length() != 0) {
                  buffer.append(s);
                  if (i < size - 1 && this.extraParameters.get(i + 1).toString().length() > 0) {
                     buffer.append('&');
                  }
               }
            }
         }

         if (this.queryStringBuffer != null && this.queryStringStart != -1 && this.queryStringLength != -1) {
            String result = BytesToString.newString(this.queryStringBuffer, this.queryStringStart, this.queryStringLength, encoding);
            return buffer != null && buffer.length() != 0 ? buffer.append('&').append(result).toString() : result;
         } else {
            return buffer != null && buffer.length() != 0 ? buffer.toString() : null;
         }
      }

      private boolean preferFowardQueryString() {
         return this.request.getContext() != null && this.request.getContext().getConfigManager().isPreferForwardQueryString();
      }

      String getOriginalQueryString(String encoding) {
         return this.queryStringStart != -1 && this.queryStringLength != -1 ? BytesToString.newString(this.queryStringBuffer, this.queryStringStart, this.queryStringLength, encoding) : null;
      }

      private void addForwardQueryString(String s) {
         this.extraParameters.add(0, new ExtraParams(s, this.request.context.getServer().getMaxRequestParameterCount()));
      }

      private void addIncludeQueryString(String s) {
         this.extraParameters.add(0, new ExtraIncludeParams(s, this.request.context.getServer().getMaxRequestParameterCount()));
      }

      private void removeRequestDispatcherQueryString() {
         this.extraParameters.remove(0);
      }

      public QueryParams getQueryParams() {
         this.parseQueryParams(false);
         return this.queryParameters;
      }

      public List getExtraQueryParams() {
         this.parseExtraQueryParams();
         return this.extraParameters;
      }

      public void setQueryParams(QueryParams qp) {
         this.queryParameters.clear();
         if (qp != null && !qp.isEmpty()) {
            Iterator i = qp.keySet().iterator();

            while(true) {
               String key;
               String[] values;
               do {
                  if (!i.hasNext()) {
                     return;
                  }

                  key = (String)i.next();
                  values = qp.getValues(key);
               } while(values == null);

               for(int j = 0; j < values.length; ++j) {
                  this.queryParameters.put(key, values[j]);
               }
            }
         }
      }

      public void setExtraQueryParams(List eqp) {
         this.extraParameters.clear();
         if (eqp != null && !eqp.isEmpty()) {
            Iterator it = eqp.iterator();

            while(it.hasNext()) {
               ExtraParams ep = (ExtraParams)it.next();
               this.extraParameters.add(0, ep.clone());
            }

         }
      }

      private Enumeration getParameterNames() {
         List extraQueryParams = this.getExtraQueryParams();
         if (extraQueryParams.size() == 0) {
            return new IteratorEnumerator(this.getQueryParams().keySet().iterator());
         } else {
            Set set = new HashSet();
            Iterator it = extraQueryParams.iterator();

            while(it.hasNext()) {
               ExtraParams params = (ExtraParams)it.next();
               set.addAll(params.params.keySet());
            }

            set.addAll(this.getQueryParams().keySet());
            return new IteratorEnumerator(set.iterator());
         }
      }

      private String[] getParameterValues(String name) {
         List result = new ArrayList();
         List extraQueryParams = this.getExtraQueryParams();
         String[] queryParamValues;
         if (extraQueryParams.size() == 0) {
            queryParamValues = this.getQueryParams().getValues(name);
            if (queryParamValues != null) {
               result.addAll(Arrays.asList(queryParamValues));
            }
         } else {
            Iterator it = extraQueryParams.iterator();

            while(it.hasNext()) {
               ExtraParams params = (ExtraParams)it.next();
               QueryParams qp = params.params;
               String[] values = qp.getValues(name);
               if (values != null) {
                  result.addAll(Arrays.asList(values));
               }
            }

            queryParamValues = this.getQueryParams().getValues(name);
            if (queryParamValues != null) {
               result.addAll(Arrays.asList(queryParamValues));
            }
         }

         return result.size() != 0 ? (String[])result.toArray(new String[0]) : null;
      }

      private String getParameter(String name) {
         String s = this.getExtraParameter(name);
         if (s != null) {
            return s;
         } else {
            s = this.getQueryParams().getValue(name);
            return s != null ? s : s;
         }
      }

      private String getExtraParameter(String name) {
         List extraQueryParams = this.getExtraQueryParams();
         Iterator it = extraQueryParams.iterator();

         String s;
         do {
            if (!it.hasNext()) {
               return null;
            }

            ExtraParams params = (ExtraParams)it.next();
            QueryParams qp = params.params;
            s = qp.getValue(name);
         } while(s == null);

         return s;
      }

      void resetQueryParams() {
         this.queryParamsParsed = false;
         this.postParamsParsed = false;
         this.partParamsParsed = false;
         this.queryParameters.clear();
         Iterator it = this.extraParameters.iterator();

         while(it.hasNext()) {
            ExtraParams ep = (ExtraParams)it.next();
            ep.clear();
         }

      }

      private void parseQueryParams(boolean internalRead) {
         if (!this.queryParamsParsed) {
            this.mergeQueryParams();
         }

         if (!this.postParamsParsed) {
            this.mergePostParams(internalRead);
         }

         if (!this.partParamsParsed) {
            this.mergePartParams(internalRead);
         }

      }

      private void parseExtraQueryParams() {
         Iterator it = this.extraParameters.iterator();

         while(it.hasNext()) {
            ExtraParams erp = (ExtraParams)it.next();
            erp.parseQueryParams(this.request.getQueryParamsEncoding());
         }

      }

      private void mergePartParams(boolean internalRead) {
         if (!internalRead) {
            if (this.isMultiPartRequest() && !this.partParamsParsed) {
               this.partParamsParsed = true;
               if (this.multipart == null) {
                  ServletStubImpl stub = this.request.getServletStub();
                  MultipartConfigElement multipartConfig = stub.getMultipartConfig();
                  this.multipart = new Multipart(this.request, multipartConfig.getLocation(), multipartConfig.getMaxFileSize(), multipartConfig.getMaxRequestSize(), multipartConfig.getFileSizeThreshold());
               }

               try {
                  Iterator var7 = this.multipart.getParts().iterator();

                  while(var7.hasNext()) {
                     Part part = (Part)var7.next();
                     PartItem partItem = (PartItem)part;
                     if (partItem.isFormField()) {
                        this.queryParameters.append(partItem.getName(), partItem.getString(this.request.getQueryParamsEncoding()));
                     }
                  }
               } catch (IOException var5) {
               } catch (ServletException var6) {
               }
            }

         }
      }

      private Multipart getMultipart() {
         if (!this.partParamsParsed) {
            this.mergePartParams(false);
         }

         return this.multipart;
      }

      private Part getPart(String name) throws IOException, ServletException {
         if (this.isMultiPartRequest()) {
            try {
               this.throwIfPartsParseException();
               return this.getMultipart().getPart(name);
            } catch (IOException var3) {
               HTTPLogger.logServletRequestGetPartException(var3.getMessage());
               throw var3;
            } catch (ServletException var4) {
               HTTPLogger.logServletRequestGetPartException(var4.getMessage());
               throw var4;
            }
         } else {
            IllegalStateException e = new IllegalStateException("the request was rejected because no multipart config was found");
            throw e;
         }
      }

      private void throwIfPartsParseException() throws ServletException, IOException {
         Multipart mp = this.getMultipart();
         if (mp.getPartsParseException() != null) {
            Exception e = mp.getPartsParseException();
            if (e instanceof ServletException) {
               throw (ServletException)e;
            } else if (e instanceof IOException) {
               throw (IOException)e;
            } else if (e instanceof IllegalStateException) {
               throw (IllegalStateException)e;
            } else {
               throw new ServletException(e);
            }
         }
      }

      private Collection getParts() throws IOException, ServletException {
         if (this.isMultiPartRequest()) {
            try {
               this.throwIfPartsParseException();
               return this.getMultipart().getParts();
            } catch (IOException var2) {
               HTTPLogger.logServletRequestGetPartException(var2.getMessage());
               throw var2;
            } catch (ServletException var3) {
               HTTPLogger.logServletRequestGetPartException(var3.getMessage());
               throw var3;
            }
         } else {
            IllegalStateException e = new IllegalStateException("the request was rejected because no multipart config was found");
            throw e;
         }
      }

      private boolean isMultiPartRequest() {
         ServletStubImpl stub = this.request.getServletStub();
         return stub != null && stub.isMultipartConfigPresent();
      }

      private void mergeQueryParams() {
         if (!this.queryParamsParsed) {
            this.queryParamsParsed = true;
            if (this.queryStringBuffer != null && this.queryStringStart != -1 && this.queryStringLength != -1) {
               HttpParsing.parseQueryString(this.queryStringBuffer, this.queryStringStart, this.queryStringLength, this.queryParameters, this.request.getQueryParamsEncoding());
            }

         }
      }

      private void mergePostParams(boolean internalRead) {
         if (!this.postParamsParsed) {
            String ctype = this.request.getContentType();
            if (this.request.inputHelper.getRequestParser().isMethodPost() && ctype != null && ctype.startsWith("application/x-www-form-urlencoded")) {
               this.postParamsParsed = true;
               if (this.postDataRead && this.postData != null) {
                  HttpParsing.parseQueryString(this.postData, 0, this.postData.length, this.queryParameters, this.request.getInputEncoding());
               } else if (this.request.inputStream != null) {
                  if (((ServletInputStreamImpl)this.request.inputStream).getBytesRead() > 0L) {
                     if (!internalRead) {
                        HTTPLogger.logInvalidGetParameterInvocation(this.request.getContext().getLogContext());
                     }
                  } else {
                     int clen = this.request.getContentLength();
                     boolean isChunked = this.request.getRequestHeaders().isChunked();
                     int read;
                     if (isChunked) {
                        read = this.request.getContext().getServer().getMaxPostSize();
                        clen = read > -1 ? read + 1 : 8192;
                     }

                     if (clen >= 1) {
                        this.postData = new byte[clen];

                        try {
                           if (internalRead) {
                              this.request.inputStream.mark(clen);
                           }

                           this.request.initRequestEncoding();

                           int r;
                           for(read = 0; read < clen; read += r) {
                              r = this.request.inputStream.read(this.postData, read, clen - read);
                              if ((isChunked || this.request.getInputHelper().getRequestParser().isProtocolVersion_2()) && r == -1) {
                                 break;
                              }

                              Debug.assertion(r > -1);
                           }

                           if ((isChunked || this.request.getInputHelper().getRequestParser().isProtocolVersion_2()) && read < this.postData.length) {
                              byte[] tmp = new byte[read];
                              System.arraycopy(this.postData, 0, tmp, 0, read);
                              this.postData = tmp;
                           }

                           if (internalRead) {
                              this.request.inputStream.reset();
                           }

                           this.mergeQueryParams();
                           this.postDataRead = true;
                           HttpParsing.parseQueryString(this.postData, 0, this.postData.length, this.queryParameters, this.request.getInputEncoding());
                           this.postParamsParsed = true;
                        } catch (IOException var8) {
                           if (var8.getCause() instanceof ChunkMaxPostSizeExceededException) {
                              try {
                                 this.request.getResponse().sendError(413);
                              } catch (Exception var7) {
                              }
                           }

                           throw new ServletNestedRuntimeException("Cannot parse POST parameters of request: '" + this.request.getRequestURI() + "'", var8);
                        }
                     }
                  }
               }
            }
         }
      }

      private String peekQueryParameter(String name) {
         boolean origParsedFlag = this.postParamsParsed;
         this.postParamsParsed = true;

         String var3;
         try {
            var3 = this.peekParameter(name);
         } finally {
            this.postParamsParsed = origParsedFlag;
         }

         return var3;
      }

      private String peekPostParameter(String name) {
         boolean origParsedFlag = this.queryParamsParsed;
         this.queryParamsParsed = true;

         String var3;
         try {
            var3 = this.peekParameter(name);
         } finally {
            this.queryParamsParsed = origParsedFlag;
         }

         return var3;
      }

      String peekParameter(String name) {
         String s = this.getExtraParameter(name);
         if (s != null) {
            return s;
         } else {
            this.parseQueryParams(true);
            return this.queryParameters.getValue(name);
         }
      }
   }

   private static class ExtraIncludeParams extends ExtraParams {
      private ExtraIncludeParams() {
         super(null);
      }

      public ExtraIncludeParams(String str, int limit) {
         super(str, limit);
      }

      public String toString() {
         return "";
      }

      protected Object clone() {
         ExtraIncludeParams o = new ExtraIncludeParams();
         o.parsed = this.parsed;
         o.str = this.str;
         o.params = (QueryParams)this.params.clone();
         return o;
      }
   }

   private static class ExtraParams {
      protected String str;
      protected boolean parsed;
      protected QueryParams params;

      private ExtraParams() {
      }

      public ExtraParams(String str, int limit) {
         this.str = str;
         this.parsed = false;
         this.params = new QueryParams(limit);
      }

      public void parseQueryParams(String encoding) {
         if (!this.parsed) {
            HttpParsing.parseQueryString(this.str, this.params, encoding);
            this.parsed = true;
         }

      }

      public void clear() {
         this.parsed = false;
         this.params.clear();
      }

      public String toString() {
         return this.str;
      }

      protected Object clone() {
         ExtraParams o = new ExtraParams();
         o.parsed = this.parsed;
         o.str = this.str;
         o.params = (QueryParams)this.params.clone();
         return o;
      }

      // $FF: synthetic method
      ExtraParams(Object x0) {
         this();
      }
   }

   private static final class CookieKey {
      private final String cookieName;
      private final String cookiePath;

      CookieKey(String name, String path) {
         this.cookieName = name;
         this.cookiePath = path;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof CookieKey)) {
            return false;
         } else {
            CookieKey sessionIDKey = (CookieKey)o;
            if (!this.cookieName.equals(sessionIDKey.cookieName)) {
               return false;
            } else {
               return this.cookiePath.equals(sessionIDKey.cookiePath);
            }
         }
      }

      public int hashCode() {
         int result = this.cookieName.hashCode();
         result = 29 * result + this.cookiePath.hashCode();
         return result;
      }
   }
}
