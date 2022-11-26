package weblogic.servlet.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.WritableByteChannel;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Supplier;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.diagnostics.context.CorrelationManager;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.WebAppContainerMBean;
import weblogic.management.configuration.WebServerMBean;
import weblogic.protocol.MessageSenderStatistics;
import weblogic.servlet.FileSender;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.ServletResponseAttributeEvent;
import weblogic.servlet.ServletResponseAttributeListener;
import weblogic.servlet.http.WLHttpServletResponse;
import weblogic.servlet.internal.session.SessionConfigManager;
import weblogic.servlet.internal.session.SessionContext;
import weblogic.servlet.internal.session.SessionInternal;
import weblogic.servlet.security.Utils;
import weblogic.servlet.security.internal.SecurityModule;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.ClusterProvider;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.TransactionProvider;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.servlet.utils.ChunkedGzipOutputStream;
import weblogic.servlet.utils.HTTPDiagnosticHelper;
import weblogic.servlet.utils.URLMapping;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.http.BytesToString;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.http.HttpReasonPhraseCoder;
import weblogic.utils.io.FilenameEncoder;

public class ServletResponseImpl implements HttpServletResponse, FutureServletResponse, WLHttpServletResponse, MessageSenderStatistics {
   public static final String X_WEBLOGIC_REQUEST_CLUSTERINFO = "X-WebLogic-Request-ClusterInfo";
   public static final String X_WEBLOGIC_CLUSTER_HASH = "X-WebLogic-Cluster-Hash";
   public static final String X_WEBLOGIC_CLUSTER_LIST = "X-WebLogic-Cluster-List";
   public static final String X_WEBLOGIC_FAILOVERGROUP_LIST = "X-WebLogic-Cluster-FailoverGroup-List";
   public static final String X_WEBLOGIC_LOAD = "X-WebLogic-Load";
   public static final String X_WEBLOGIC_FORCE_JVMID = "X-WebLogic-Force-JVMID";
   public static final String X_WEBLOGIC_JVMID = "X-WebLogic-JVMID";
   public static final String X_WEBLOGIC_BUZZ_ADDRESS = "X-WebLogic-Buzz-Address";
   public static final String X_WEBLOGIC_KEEPALIVE_SECS = "X-WebLogic-KeepAliveSecs";
   private static final boolean P3P_ENABLED;
   private static final String P3P_HEADER_VALUE;
   private static final String PROXY_JROUTE = "proxy-jroute";
   private static WebServerRegistry registry;
   private static ArrayList noHttpOnlyInternalApps;
   private final ServletRequestImpl request;
   private final ServletOutputStreamImpl outputStream;
   private WebAppServletContext context;
   private int statusCode = 200;
   private String statusMessage = null;
   private long contentLength = -1L;
   private String encoding;
   private boolean encodingSetExplicitly;
   private PrintWriter printWriter;
   private ChunkWriter outputStreamWriter;
   private final ArrayList cookies = new ArrayList();
   private AbstractResponseHeaders headers;
   private Locale locale;
   private boolean gotOutputStream;
   private StackTraceElement[] callStackTrace = null;
   private boolean useKeepAlive = true;
   private static final String[] CRLF;
   private boolean isFutureResponseSent = false;
   private FileSender fileSender = null;
   private long bytesSent = 0L;
   private long messageSent = 0L;
   private Set attributeListeners = new HashSet(4);
   private boolean correlationSet = false;
   private Supplier trailerFields = null;
   static final long serialVersionUID = -8635682551030702251L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.internal.ServletResponseImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Response_Send_Around_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Response_Write_Headers_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   ServletResponseImpl() {
      this.request = null;
      this.outputStream = null;
   }

   public ServletResponseImpl(ServletRequestImpl req, OutputStream os) {
      this.outputStream = new ServletOutputStreamImpl(os, this);
      this.request = req;
      this.request.setResponse(this);
      this.headers = ResponseHeadersFactory.createResponseHeaders(this);
   }

   final void init() {
      this.statusCode = 200;
      this.statusMessage = null;
      this.contentLength = -1L;
      this.headers = ResponseHeadersFactory.createResponseHeaders(this);
      this.useKeepAlive = true;
      this.context = null;
      this.encoding = null;
      this.encodingSetExplicitly = false;
      this.printWriter = null;
      this.outputStreamWriter = null;
      this.cookies.clear();
      this.locale = null;
      this.gotOutputStream = false;
      this.fileSender = null;
      this.attributeListeners.clear();
      this.outputStream.resetGzipOutput();
      this.correlationSet = false;
   }

   public void flushBuffer() throws IOException {
      this.outputStream.flush();
   }

   public int getBufferSize() {
      return this.outputStream.getBufferSize();
   }

   public String getCharacterEncoding() {
      if (this.outputStreamWriter != null) {
         this.encoding = this.outputStreamWriter.getEncoding();
      }

      if (this.encoding == null) {
         if (this.context != null) {
            this.encoding = this.context.getResponseCharacterEncoding();
            if (this.encoding == null) {
               this.encoding = this.context.getConfigManager().getDefaultEncoding();
            }
         } else {
            this.encoding = "ISO-8859-1";
         }
      }

      return this.encoding;
   }

   public void setCharacterEncoding(String newEncoding) {
      if (!this.isCommitted()) {
         String oldEncoding = this.getCharacterEncoding();
         this.setEncoding(newEncoding);
         newEncoding = this.getCharacterEncoding();
         if (!newEncoding.equalsIgnoreCase(oldEncoding) || !this.encodingSetExplicitly) {
            this.encodingSetExplicitly = true;
            String contentType = this.getHeader("Content-Type");
            if (contentType == null) {
               return;
            }

            StringBuilder value = new StringBuilder();
            int semicol = contentType.indexOf(59);
            if (semicol != -1) {
               value.append(contentType.substring(0, semicol));
            } else {
               value.append(contentType);
            }

            value.append("; charset=");
            value.append(newEncoding);
            this.headers.setHeader("Content-Type", value.toString());
         }

      }
   }

   final boolean isOutputStreamInitialized() {
      return this.gotOutputStream || this.printWriter != null;
   }

   final void resetOutputState() {
      this.gotOutputStream = false;
      this.printWriter = null;
   }

   public ServletOutputStream getOutputStream() throws IOException {
      if (this.printWriter != null && this.request != null && this.request.getAttribute("javax.servlet.include.request_uri") == null) {
         String message = HTTPDebugLogger.isEnabled() ? PlatformConstants.EOL + " getWriter() is already called by " + PlatformConstants.EOL + this.getStackTrace() : "";
         throw new IllegalStateException("strict servlet API: cannot call getOutputStream() after getWriter() " + message);
      } else {
         this.gotOutputStream = true;
         if (HTTPDebugLogger.isEnabled()) {
            this.setStackTrace(Thread.currentThread().getStackTrace());
         }

         return this.getOutputStreamNoCheck();
      }
   }

   public ServletOutputStream getOutputStreamNoCheck() {
      return this.outputStream;
   }

   public final ServletOutputStreamImpl getServletOutputStream() {
      return this.outputStream;
   }

   public PrintWriter getWriter() throws IOException {
      if (this.gotOutputStream && this.request != null && this.request.getAttribute("javax.servlet.include.request_uri") == null) {
         String message = HTTPDebugLogger.isEnabled() ? PlatformConstants.EOL + " getOutputStream() is already called by " + PlatformConstants.EOL + this.getStackTrace() : "";
         throw new IllegalStateException("strict servlet API: cannot call getWriter() after getOutputStream()" + message);
      } else {
         this.initWriter();
         if (HTTPDebugLogger.isEnabled()) {
            this.setStackTrace(Thread.currentThread().getStackTrace());
         }

         return this.printWriter;
      }
   }

   private void setStackTrace(StackTraceElement[] stackTrace) {
      if (this.callStackTrace == null) {
         this.callStackTrace = stackTrace;
      }

   }

   private String getStackTrace() {
      String stackTrace = null;
      if (this.callStackTrace != null) {
         stackTrace = Arrays.toString(this.callStackTrace);
         stackTrace = stackTrace.replace(",", PlatformConstants.EOL + " at ");
         stackTrace = stackTrace + PlatformConstants.EOL;
      }

      return stackTrace;
   }

   protected final PrintWriter getWriterNoCheck() {
      this.initWriter();
      return this.printWriter;
   }

   public boolean isCommitted() {
      return this.outputStream.isCommitted() || this.outputStream.isSuspended();
   }

   public void reset() throws IllegalStateException {
      this.outputStream.reset();
      this.setStatus(200);
      this.headers = ResponseHeadersFactory.createResponseHeaders(this);
      this.cookies.clear();
      this.printWriter = null;
      this.gotOutputStream = false;
   }

   public void resetBuffer() {
      this.outputStream.clearBuffer();
   }

   public void setBufferSize(int size) {
      if (this.outputStream.getTotal() > 0L) {
         throw new IllegalStateException("Cannot resize buffer - " + this.outputStream.getTotal() + " bytes have already been written (Servlet 2.3, sec. 5.1)");
      } else {
         if (DebugHttpConciseLogger.isEnabled()) {
            HTTPDiagnosticHelper.analyzeAndDumpStackTraceForNonWeblogicCaller("setBufferSize", -1);
         }

         this.setBufferSizeNoWriteCheck(size);
      }
   }

   public void setBufferSizeNoWriteCheck(int size) {
      this.objectIfCommitted();
      this.outputStream.setBufferSize(size);
   }

   public void setContentLength(int len) {
      this.setContentLengthLong((long)len);
   }

   public void setContentLengthLong(long len) {
      if (!this.isCommitted()) {
         if (len != -1L) {
            this.contentLength = len;
            this.headers.setContentLength(len);

            try {
               this.outputStream.setContentLength(len);
            } catch (ProtocolException var4) {
               throw new RuntimeException(var4.toString());
            }
         }
      }
   }

   private void setContentLength(String value) {
      try {
         this.setContentLength(Integer.parseInt(value.trim()));
      } catch (NumberFormatException var3) {
      }

   }

   public void setTrailerFields(Supplier supplier) {
      if (this.isCommitted()) {
         throw new IllegalStateException("Trailer fields can't be set after response has been committed");
      } else if ("HTTP/1.0".equals(this.request.getInputHelper().getRequestParser().getProtocol())) {
         throw new IllegalStateException("Trailer fields are not supported when the underlying protocol is HTTP 1.0");
      } else if (this.request.getInputHelper().getRequestParser().isProtocolVersion_1_1() && this.contentLength != -1L) {
         throw new IllegalStateException("Trailer fields can't be set because the response does not use chunked transfer encoding");
      } else {
         this.trailerFields = supplier;
      }
   }

   public Supplier getTrailerFields() {
      return this.trailerFields;
   }

   public void setContentType(String type) {
      if (!this.isCommitted()) {
         if (type == null) {
            this.headers.setContentType((String)null);
         } else {
            int charsetPos = StringUtils.indexOfIgnoreCase(type, "charset");
            String newEncoding;
            if (charsetPos != -1) {
               newEncoding = null;
               int colPos = type.indexOf(59, charsetPos);
               int equalPos = type.indexOf(61, charsetPos);
               if (colPos == -1) {
                  if (equalPos != -1 && equalPos < type.length()) {
                     newEncoding = type.substring(equalPos + 1).trim();
                  }
               } else if (equalPos != -1 && equalPos < colPos) {
                  newEncoding = type.substring(equalPos + 1, colPos).trim();
               }

               newEncoding = HttpParsing.StripHTTPFieldValue(newEncoding);
               if (newEncoding != null && newEncoding.length() != 0) {
                  this.encodingSetExplicitly = true;
                  if (!newEncoding.equalsIgnoreCase(this.getCharacterEncoding())) {
                     this.setEncoding(newEncoding);
                     if (!newEncoding.equals(this.getCharacterEncoding())) {
                        int scolon = type.indexOf(";");
                        if (scolon != -1) {
                           type = type.substring(0, scolon);
                        }

                        if (this.encoding != null && CharsetMap.isCharsetAllowedForType(type)) {
                           type = type + "; charset=" + this.encoding;
                        }
                     }
                  }
               }
            } else if (type.toLowerCase().trim().startsWith("text/")) {
               if (this.printWriter != null) {
                  type = type + "; charset=" + this.outputStreamWriter.getEncoding();
               } else {
                  newEncoding = this.outputStream.getOutput().getOutput().getEncoding();
                  if (newEncoding != null && CharsetMap.isCharsetAllowedForType(type)) {
                     type = type + "; charset=" + newEncoding;
                  }
               }
            }

            this.headers.setContentType(type);
         }
      }
   }

   public final String getContentType() {
      return this.getHeader("Content-Type");
   }

   public Locale getLocale() {
      if (this.locale == null) {
         this.locale = Locale.getDefault();
      }

      return this.locale;
   }

   public void setLocale(Locale l) {
      if (!this.isCommitted()) {
         if (l != null) {
            if (!this.encodingSetExplicitly) {
               String enc = this.context.getConfigManager().getLocaleEncodingMap().getJavaCharset(l);
               String cType = this.getContentType();
               if (cType == null) {
                  this.setEncoding(enc);
               } else {
                  int idx = StringUtils.indexOfIgnoreCase(cType, "charset=");
                  cType = idx > -1 ? cType.substring(0, idx + 8) : cType + "; charset=";
                  this.setEncoding(enc);
                  this.headers.setHeader("Content-Type", cType + enc);
               }
            }

            StringBuffer tmpbuf = new StringBuffer(l.getLanguage());
            if (!"".equals(l.getCountry())) {
               tmpbuf.append('-').append(l.getCountry());
            }

            this.setHeader("Content-Language", tmpbuf.toString());
            this.locale = l;
         }
      }
   }

   public CharsetMap getCharsetMap() {
      return this.context.getCharsetMap();
   }

   public void addCookie(Cookie c) {
      if (c == null) {
         throw new NullPointerException("Invalid Cookie");
      } else {
         this.checkForCRLFChars(c);
         ServletRequestImpl var10000 = this.request;
         if (!ServletRequestImpl.ignorePluginParamsForCookiePath && c.getPath() != null) {
            c.setPath(this.processProxyPathHeaders(c.getPath()));
         }

         this.addCookieInternal(c);
      }
   }

   public void addCookieInternal(Cookie c) {
      if (!this.isCommitted()) {
         this.cookies.add(c);
      }
   }

   public final Cookie getCookie(String name) {
      if (this.cookies.size() > 0) {
         int i = this.cookies.size();

         while(true) {
            --i;
            if (i <= -1) {
               break;
            }

            Cookie cookie = (Cookie)this.cookies.get(i);
            if (cookie.getName().equals(name)) {
               return cookie;
            }
         }
      }

      return null;
   }

   public final ArrayList getCookies() {
      return this.cookies;
   }

   public final void removeCookie(String name, String path) {
      if (this.cookies.size() >= 1) {
         int i = this.cookies.size();

         while(true) {
            --i;
            if (i <= -1) {
               break;
            }

            Cookie cookie = (Cookie)this.cookies.get(i);
            if (cookie.getName() != null && cookie.getName().equals(name) && cookie.getPath() != null && cookie.getPath().equals(path)) {
               this.cookies.remove(cookie);
               break;
            }
         }

      }
   }

   public void addDateHeader(String name, long date) {
      if (!this.isCommitted()) {
         this.headers.addDateHeader(name, date);
      }
   }

   public void addHeader(String name, String value) {
      this.checkForCRLFChars(name, value);
      this.addHeaderInternal(name, value);
   }

   public void addHeaderInternal(String name, String value) {
      if (!this.isCommitted()) {
         if (value == null) {
            value = "";
         }

         if (name.equalsIgnoreCase("Content-Length")) {
            this.setContentLength(value);
         } else if (name.equalsIgnoreCase("Transfer-Encoding") && value.indexOf("chunked") > -1) {
            this.outputStream.enableChunkedTransferEncoding(false);
         } else {
            this.headers.addHeader(name, value);
         }
      }
   }

   public void addIntHeader(String name, int value) {
      if (!this.isCommitted()) {
         this.headers.addIntHeader(name, value);
      }
   }

   public boolean containsHeader(String name) {
      return this.headers.containsHeader(name);
   }

   public String encodeRedirectUrl(String string) {
      return this.encodeRedirectURL(string);
   }

   public String encodeRedirectURL(String string) {
      if (string.startsWith("/")) {
         string = this.processProxyPathHeaders(string);
      }

      return this.encodeURL(string);
   }

   public String encodeUrl(String string) {
      return this.encodeURL(string);
   }

   public String encodeURL(String string) {
      boolean skipEncode = false;

      try {
         String scheme = (new URI(string)).getScheme();
         if (scheme != null) {
            skipEncode = !scheme.startsWith("http");
         }
      } catch (URISyntaxException var4) {
      }

      return skipEncode ? string : this.encodeURL(string, this.request.getSession(false));
   }

   public void sendError(int code) throws IOException {
      this.sendError(code, ErrorMessages.getErrorPage(code), false);
   }

   public void sendError(int code, String msg) throws IOException {
      this.sendError(code, msg, true);
   }

   private void sendNoContentError() {
      this.disableKeepAlive();
      this.setContentLength(0);
      this.getWriterNoCheck().flush();
      this.outputStream.getOutput().setWriteEnabled(false);
      this.incrementBytesSentCount(this.outputStream.getTotal());
      this.incrementMessageSentCount();
   }

   private void sendContentError(int code, String msg) {
      this.disableKeepAliveOnSendError(code);
      this.setContentType("text/html");
      this.setCharacterEncoding("UTF-8");

      try {
         this.setContentLength(msg.getBytes("UTF-8").length);
      } catch (UnsupportedEncodingException var4) {
      }

      this.getWriterNoCheck().print(msg);
      this.getWriterNoCheck().flush();
      if (this.printWriter != null && this.printWriter instanceof UnsynchronizedPrintWriter) {
         IOException ioe = ((UnsynchronizedPrintWriter)this.printWriter).getIOException();
         if (ioe != null && HTTPDebugLogger.shouldLogIOException(ioe)) {
            HTTPLogger.logSendErrorResponseException(this.context.getLogContext(), ioe);
         }
      }

      this.incrementBytesSentCount(this.outputStream.getOutput().getTotal());
      this.incrementMessageSentCount();
   }

   protected void sendError(int code, String msg, boolean encodeXSS) throws IOException {
      if (DebugHttpConciseLogger.isEnabled() && (code / 10 == 40 || code / 10 == 50)) {
         HTTPDiagnosticHelper.analyzeAndDumpStackTraceForNonWeblogicCaller("sendError", code);
      }

      this.objectIfCommitted();
      this.outputStream.clearCurrentBuffer();
      this.resetOutputState();
      this.setStatus(code);
      if (encodeXSS) {
         msg = Utils.encodeXSS(msg);
      }

      if (code != 100 && code != 101 && code != 204 && code != 304) {
         String requestURI = this.request.getRequestURI();

         try {
            String location = null;
            if (this.context != null) {
               location = this.context.getErrorManager().getErrorLocation(String.valueOf(code));
            }

            int queryStringPos;
            if (this.request.getAttribute("javax.servlet.error.status_code") != null) {
               Integer val = (Integer)this.request.getAttribute("javax.servlet.error.status_code");
               queryStringPos = val;
               String origLocation = this.context.getErrorManager().getErrorLocation(String.valueOf(queryStringPos));
               if (queryStringPos > 0 && origLocation != null && code != queryStringPos && requestURI.endsWith(origLocation)) {
                  HTTPLogger.logNoLocation(this.context.getLogContext(), origLocation, queryStringPos);
               }
            }

            if (location == null) {
               this.sendContentError(code, msg);
               if (this.request.getInputHelper().getRequestParser().isMethodHead()) {
                  this.outputStream.commit();
               }

               return;
            }

            if (HTTPDebugLogger.isEnabled()) {
               this.trace("Configured error page location=" + location + ", requestURI=" + requestURI);
            }

            String locationWithoutQueryString = location;
            queryStringPos = location.indexOf(63);
            if (queryStringPos > 0) {
               locationWithoutQueryString = location.substring(0, queryStringPos);
               if (HTTPDebugLogger.isEnabled()) {
                  this.trace("Location without query string=" + locationWithoutQueryString);
               }
            }

            if (requestURI.endsWith(locationWithoutQueryString)) {
               HTTPLogger.logNoLocation(this.context.getLogContext(), location, code);
               this.sendContentError(code, msg);
               return;
            }

            if (WebAppServletContext.isAbsoluteURL(location)) {
               this.sendRedirect(location);
               return;
            }

            try {
               if (code != 401) {
                  this.reset();
               }

               this.setStatus(code);
            } catch (Exception var13) {
               HTTPLogger.logUnableToServeErrorPage(this.context.getLogContext(), location, code, var13);
               return;
            }

            URLMapping mapping = this.context.getServletMapping();
            URLMatchHelper umh = (URLMatchHelper)mapping.get(location);
            String elocation;
            RequestDispatcher rd;
            if (umh != null && !umh.getPattern().startsWith("*.") && !umh.isFileOrJspServlet()) {
               elocation = (String)this.request.getAttribute("weblogic.servlet.errorPage");
               if (elocation != null && elocation.equals(location)) {
                  HTTPLogger.logBadErrorPage(this.context.getLogContext(), location, code);
                  this.sendContentError(code, msg);
                  return;
               }

               this.request.setAttribute("weblogic.servlet.errorPage", location);
               rd = this.context.getNamedDispatcher(umh.getServletStub().getServletName(), 3);
            } else {
               if (location != null) {
                  this.request.setRedirectURI(false);
               }

               rd = this.context.getRequestDispatcher(location, 3);
            }

            if (rd == null) {
               this.sendContentError(code, msg);
               return;
            }

            ServletStubImpl eStub = null;
            if (requestURI.startsWith(this.context.getContextPath())) {
               elocation = requestURI.substring(this.context.getContextPath().length());
            } else {
               elocation = requestURI;
            }

            if (elocation != null) {
               eStub = this.context.getServletStub(elocation);
            }

            this.setErrorAttributes(eStub, code, msg);
            rd.forward(this.request, this);
         } catch (ServletException var14) {
            Throwable t = WebAppServletContext.getRootCause(var14);
            HTTPLogger.logSendError(this.context.getLogContext(), t);
         }

      } else {
         this.sendNoContentError();
      }
   }

   private void disableKeepAliveOnSendError(int code) {
      switch (code) {
         case 402:
         case 406:
         case 407:
         case 408:
         case 409:
         case 410:
         case 411:
         default:
            this.disableKeepAlive();
         case 401:
         case 403:
         case 404:
         case 405:
         case 412:
      }
   }

   public String getURLForRedirect(String uri) {
      uri = this.processProxyPathHeaders(uri);
      return this.getAbsoluteURL(uri);
   }

   private String getAbsoluteURL(String uri) {
      int port = this.request.getServerPort();
      String schema = this.request.getScheme().toLowerCase();
      return (port != 80 || !schema.equals("http")) && (port != 443 || !schema.equals("https")) ? schema + "://" + this.request.getServerName() + ':' + this.request.getServerPort() + uri : schema + "://" + this.request.getServerName() + uri;
   }

   public final String processProxyPathHeaders(String path) {
      String location = path;
      String pathPrepend = this.request.getHeader("WL-PATH-PREPEND");
      boolean processPathTrimHeader = true;
      if (pathPrepend != null) {
         if (pathPrepend.endsWith("/")) {
            pathPrepend = pathPrepend.substring(0, pathPrepend.length() - 1);
         }

         if (!path.startsWith(pathPrepend + '/') && !path.equals(pathPrepend)) {
            processPathTrimHeader = false;
         } else {
            location = path.substring(pathPrepend.length());
            if (location.length() == 0) {
               location = "/";
            } else if (location.charAt(0) != '/') {
               location = "/" + location;
            }
         }
      }

      String pathTrim = this.request.getHeader("WL-PATH-TRIM");
      if (processPathTrimHeader && pathTrim != null) {
         if (pathTrim.charAt(0) != '/') {
            pathTrim = "/" + pathTrim;
         }

         if (!location.startsWith(pathTrim + "/")) {
            location = pathTrim + location;
         }
      }

      return location;
   }

   public void sendRedirect(String location) throws IOException {
      this.sendRedirect(location, this.context.getConfigManager().isSendPermanentRedirects() ? RedirectStatus.SC_MOVED_PERMANENTLY : RedirectStatus.SC_MOVED_TEMPORARILY);
   }

   public void sendRedirect(String location, RedirectStatus redirectStatus) throws IOException {
      this.objectIfCommitted();
      String uri;
      String path;
      if (WebAppServletContext.isAbsoluteURL(location)) {
         if (this.request.getHeader("X-WebLogic-KeepAliveSecs") == null) {
            URL url = new URL(location);
            int port = url.getPort();
            port = port != -1 ? port : url.getDefaultPort();
            if (!url.getHost().equalsIgnoreCase(this.request.getServerName()) || port != this.request.getServerPort()) {
               this.disableKeepAlive();
            }
         }
      } else if (location.startsWith("//")) {
         StringBuilder sb = new StringBuilder(this.request.getScheme());
         location = sb.append(':').append(location).toString();
      } else if (this.context.getConfigManager().isRedirectWithAbsoluteURLEnabled()) {
         if (location.startsWith("/")) {
            location = this.processProxyPathHeaders(location);
         } else {
            uri = this.request.getRequestURI();
            path = uri;
            if (!uri.endsWith("/")) {
               path = uri.substring(0, uri.lastIndexOf("/") + 1);
            }

            path = this.processProxyPathHeaders(path);
            location = FilenameEncoder.resolveRelativeURIPath(path + location);
         }

         location = this.getAbsoluteURL(location);
      }

      this.setHeader("Location", location);
      uri = null;
      switch (redirectStatus) {
         case SC_SEE_OTHER:
            this.setStatus(303);
            uri = "303 See Other";
            break;
         case SC_MOVED_PERMANENTLY:
            this.setStatus(301);
            uri = "301 Moved Permanently";
            break;
         case SC_MOVED_TEMPORARILY:
            this.setStatus(302);
            uri = "302 Moved Temporarily";
      }

      path = this.headers.getHeader("Content-Type");
      if (path == null) {
         if (this.context.getConfigManager().getDefaultMimeType() != null) {
            this.setContentType(this.context.getConfigManager().getDefaultMimeType());
         } else {
            this.setContentType("text/html");
         }
      }

      this.outputStream.reset();
      this.outputStream.println("<html><head><title>" + uri + "</title></head>");
      this.outputStream.println("<body bgcolor=\"#FFFFFF\">");
      this.outputStream.println("<p>This document you requested has moved ");
      if (redirectStatus == RedirectStatus.SC_MOVED_PERMANENTLY) {
         this.outputStream.println("permanently.</p>");
      } else {
         this.outputStream.println("temporarily.</p>");
      }

      location = Utils.encodeXSS(location);
      this.outputStream.println("<p>It's now at <a href=\"" + location + "\">" + location + "</a>.</p>");
      this.outputStream.println("</body></html>");
      this.outputStream.setSuspended(true);
   }

   public void setDateHeader(String name, long date) {
      if (!this.isCommitted()) {
         this.headers.setDateHeader(name, date);
      }
   }

   public void setHeader(String name, String value) {
      this.checkForCRLFChars(name, value);
      this.setHeaderInternal(name, value);
   }

   public void setHeaderInternal(String name, String value) {
      if (!this.isCommitted()) {
         if (value == null) {
            value = "";
         }

         if (name.equalsIgnoreCase("Content-Type")) {
            this.setContentType(value);
         } else {
            if (name.equalsIgnoreCase("Content-Length")) {
               try {
                  this.setContentLength(value);
                  return;
               } catch (NumberFormatException var4) {
               }
            } else if (name.equalsIgnoreCase("Transfer-Encoding") && value.indexOf("chunked") > -1) {
               this.outputStream.enableChunkedTransferEncoding(true);
               return;
            }

            this.headers.setHeader(name, value);
         }
      }
   }

   public void setIntHeader(String name, int value) {
      if (!this.isCommitted()) {
         this.headers.setIntHeader(name, value);
      }
   }

   public void setStatus(int code) {
      if (!this.isCommitted()) {
         this.setStatus(code, HttpReasonPhraseCoder.getReasonPhrase(code));
      }
   }

   public void setStatus(int code, String msg) {
      if (!this.isCommitted()) {
         if (DebugHttpConciseLogger.isEnabled() && (code / 10 == 40 || code / 10 == 50)) {
            HTTPDiagnosticHelper.analyzeAndDumpStackTraceForNonWeblogicCaller("setStatus", code);
         }

         this.statusCode = code;
         this.statusMessage = msg;
      }
   }

   final void initHttpServer(HttpServer httpServer) {
      if (!this.isCommitted()) {
         if (httpServer.getMBean().isSendServerHeaderEnabled()) {
            this.headers.setHeader("Server", httpServer.getName() + " - " + HttpServer.SERVER_INFO);
         }

         if (httpServer.isXPoweredByHeaderEnabled()) {
            this.headers.setHeader("X-Powered-By", httpServer.getXPoweredByHeaderValue());
         }

         if (P3P_ENABLED) {
            this.headers.setHeader("P3P", P3P_HEADER_VALUE);
         }

      }
   }

   public final void initContext(WebAppServletContext c) {
      this.context = c;
   }

   public final ServletRequestImpl getRequest() {
      return this.request;
   }

   final boolean hasKeepAliveHeader() {
      return this.headers.getKeepAlive();
   }

   public final void disableKeepAlive() {
      this.useKeepAlive = false;
   }

   final boolean getUseKeepAlive() {
      return this.request.getInputHelper().getRequestParser().isKeepAlive() && this.useKeepAlive && !this.request.isConnectionClosingForcibly();
   }

   boolean isFutureResponseSent() {
      return this.isFutureResponseSent;
   }

   public final String getHeader(String name) {
      String value = this.headers.getHeader(name);
      return value;
   }

   public Socket getSocket() throws IOException {
      if (this.request == null) {
         return null;
      } else if (this.request.getInputHelper().getRequestParser().isProtocolVersion_2()) {
         throw new IllegalStateException("WLHttpServletResponse.getSocket() does not be supported when HTTP/2 is enabled");
      } else {
         VirtualConnection conn = this.request.getConnection();
         if (conn == null) {
            return null;
         } else {
            this.outputStream.setNativeControlsPipe(true);
            return conn.getSocket();
         }
      }
   }

   public WritableByteChannel getWritableByteChannel() throws IOException {
      if (this.request != null && this.request.getInputHelper().getRequestParser().isProtocolVersion_2()) {
         throw new IllegalStateException("WLHttpServletResponse.getWritableByteChannel() does not be supported when HTTP/2 is enabled");
      } else {
         return registry.getContainerSupportProvider().getWritableByteChannel(this.getSocket());
      }
   }

   final void setDefaultEncoding(String enc) {
      if (!BytesToString.is8BitUnicodeSubset(enc)) {
         if (this.outputStream != null && this.context != null) {
            ChunkOutputWrapper wrapper = this.outputStream.getOutput();
            String enc0 = wrapper.getEncoding();
            if (enc != null && !enc.equalsIgnoreCase(enc0)) {
               try {
                  wrapper.changeToCharset(enc, this.context.getCharsetMap());
                  this.outputStream.setOutput(wrapper);
               } catch (UnsupportedEncodingException var5) {
               }
            }
         }

      }
   }

   private void setEncoding(String newEncoding) {
      if (newEncoding != null) {
         if (this.printWriter != null) {
            String enc = this.getCharacterEncoding();
            if (enc != null && !enc.equalsIgnoreCase(newEncoding)) {
               return;
            }
         }

         ChunkOutputWrapper co = this.outputStream.getOutput();

         try {
            CharsetMap charsetMap;
            if (this.context != null) {
               charsetMap = this.context.getCharsetMap();
            } else {
               charsetMap = new CharsetMap((Map)null);
            }

            co.changeToCharset(newEncoding, charsetMap);
            Iterator it = this.attributeListeners.iterator();

            while(it.hasNext()) {
               ServletResponseAttributeListener listener = (ServletResponseAttributeListener)it.next();
               listener.attributeChanged(new ServletResponseAttributeEvent(this, "ENCODING", this.encoding));
            }

            this.encoding = newEncoding;
            this.headers.setEncoding(charsetMap.getJavaCharset(newEncoding));
         } catch (UnsupportedEncodingException var6) {
            throw new IllegalArgumentException("unsupported encoding: '" + newEncoding + "': " + var6);
         }
      }
   }

   private void initWriter() {
      if (this.printWriter == null) {
         ChunkOutputWrapper cow = this.outputStream.getOutput();
         this.outputStreamWriter = new ChunkWriter(cow);
         this.printWriter = new UnsynchronizedPrintWriter(this.outputStreamWriter, false);
      }
   }

   final void writeHeaders() throws IOException {
      LocalHolder var14;
      if ((var14 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var14.argsCapture) {
            var14.args = new Object[1];
            var14.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var14);
         InstrumentationSupport.preProcess(var14);
         var14.resetPostBegin();
      }

      label490: {
         try {
            if (this.outputStream.isCommitted()) {
               break label490;
            }

            if (HTTPDebugLogger.isEnabled()) {
               this.trace("Writing headers for " + this.request.toStringSimple());
            }

            this.setCorrelationResponse();
            ServletOutputStreamImpl ps = this.outputStream;
            this.headers.setDate(this.request.getHttpAccountingInfo().getInvokeTime());
            SessionConfigManager scm = this.context == null ? null : this.context.getSessionContext().getConfigMgr();
            String c;
            if (this.cookies.size() > 0) {
               int i = 0;

               while(true) {
                  if (i >= this.cookies.size()) {
                     if (scm != null && !scm.isCacheSessionCookieEnabled()) {
                        this.headers.disableCacheControlForCookie();
                     }
                     break;
                  }

                  Cookie cook = (Cookie)this.cookies.get(i);
                  boolean enableHttpOnlyForCookie = cook.isHttpOnly();
                  if (scm != null || !cook.getName().equals("JSESSIONID") && !cook.getName().equals("_WL_AUTHCOOKIE_JSESSIONID")) {
                     if (scm != null && (cook.getName().equals(scm.getCookieName()) || cook.getName().equals(scm.getWLSAuthCookieName()))) {
                        enableHttpOnlyForCookie = scm.isCookieHttpOnly();
                     }
                  } else {
                     enableHttpOnlyForCookie = true;
                  }

                  if (this.context.isInternalApp() && noHttpOnlyInternalApps.contains(this.context.getContextPath())) {
                     enableHttpOnlyForCookie = false;
                  }

                  c = CookieParser.formatCookie(cook, enableHttpOnlyForCookie);
                  if (HTTPDebugLogger.isEnabled()) {
                     this.trace("Wrote cookie: " + c);
                  }

                  this.headers.addHeader("Set-Cookie", c);
                  ++i;
               }
            }

            HttpServer httper = this.getHttpServer();
            String fieldValue;
            String trailer;
            if (httper != null) {
               boolean isPlugin = false;
               if (registry.getClusterMBean() != null && this.request.getRequestHeaders().getXWeblogicRequestClusterInfo() != null) {
                  ClusterProvider clusterProvider = registry.getClusterProvider();
                  isPlugin = true;
                  this.headers.unsetHeader("X-WebLogic-Cluster-List");
                  this.headers.unsetHeader("X-WebLogic-Cluster-Hash");
                  if (HTTPDebugLogger.isEnabled()) {
                     this.trace("writing server list for " + this.request.toString());
                  }

                  c = this.request.getRequestHeaders().getXWeblogicClusterHash();
                  fieldValue = c == null ? "" : c;
                  trailer = clusterProvider.getHash();
                  String passedHash = this.headers.getHeader("X-WebLogic-Cluster-Hash");
                  if (trailer != null && !trailer.equals(fieldValue)) {
                     String[] servers = clusterProvider.getClusterList(this.request.getConnection().getChannel());
                     if (servers != null && servers.length > 0) {
                        StringBuffer sb = new StringBuffer();

                        for(int i = 0; i < servers.length; ++i) {
                           String srvr = servers[i];
                           sb.append(srvr);
                           if (i < servers.length - 1) {
                              sb.append('|');
                           }
                        }

                        this.headers.setHeader("X-WebLogic-Cluster-Hash", trailer);
                        this.headers.setHeader("X-WebLogic-Cluster-List", sb.toString());
                     }
                  }
               }

               if (this.request.getRequestHeaders().getXWeblogicForceJvmId() != null) {
                  isPlugin = true;
                  this.headers.unsetHeader("X-WebLogic-JVMID");
                  this.headers.setHeader("X-WebLogic-JVMID", httper.getServerHash());
               }

               if (this.request.getRequestHeaders().getXWeblogicBuzzAddress() != null) {
                  isPlugin = true;
                  if (KernelStatus.getBuzzSocketAddress() == null) {
                     KernelStatus.setBuzzSocketAddress(new InetSocketAddress(this.request.getLocalAddr(), this.request.getLocalPort()));
                  }

                  this.headers.setHeader("X-WebLogic-Buzz-Address", KernelStatus.getBuzzAddress());
               }

               if (isPlugin) {
                  this.headers.unsetHeader("X-WebLogic-KeepAliveSecs");
                  String proxyKeepAliveStr = this.request.getRequestHeaders().getXWeblogicKeepaliveSecs();
                  if (this.context != null && proxyKeepAliveStr != null) {
                     WebServerMBean webServerMBean = this.context.getServer().getMBean();
                     int keepAliveSecs = this.request.getConnection().isSSL() ? webServerMBean.getHttpsKeepAliveSecs() : webServerMBean.getKeepAliveSecs();

                     try {
                        int proxyKeepAliveSecs = Integer.parseInt(proxyKeepAliveStr);
                        if (proxyKeepAliveSecs > keepAliveSecs) {
                           this.headers.setHeader("X-WebLogic-KeepAliveSecs", "" + keepAliveSecs);
                        }
                     } catch (NumberFormatException var17) {
                        this.headers.setHeader("X-WebLogic-KeepAliveSecs", "" + keepAliveSecs);
                     }
                  }
               }
            }

            String contentType = this.getHeader("Content-Type");
            if (contentType != null && this.request.getAttribute("weblogic.servlet.jsp") != null && contentType.toLowerCase().trim().startsWith("text/") && contentType.indexOf(59) < 0) {
               StringBuilder value = new StringBuilder();
               value.append(contentType);
               value.append("; charset=");
               value.append(this.getCharacterEncoding());
               this.headers.setContentType(value.toString());
            }

            if (!this.request.getInputHelper().getRequestParser().isProtocolVersion_2() && !this.getUseKeepAlive()) {
               this.headers.setConnection("close");
            }

            if (this.context != null && this.context.getConfigManager().isGzipCompressionEnabled() && this.isGzipSupported() && this.isCompressable()) {
               this.addHeader("Content-Encoding", "gzip");
               this.headers.unsetHeader("Content-Length");
               this.addHeader("Transfer-Encoding", "chunked");
               this.outputStream.setUseGzip(true);
            }

            if (this.trailerFields != null) {
               Map trailersMap = (Map)this.trailerFields.get();
               if (trailersMap != null) {
                  StringBuilder trailerBuilder = new StringBuilder();
                  Iterator var29 = trailersMap.keySet().iterator();

                  while(true) {
                     if (!var29.hasNext()) {
                        fieldValue = trailerBuilder.toString();
                        this.addHeader("Trailer", fieldValue.substring(0, fieldValue.length() - 2));
                        break;
                     }

                     trailer = (String)var29.next();
                     if (!this.skipDisallowedTrailerField(trailer)) {
                        trailerBuilder.append(trailer);
                        trailerBuilder.append(", ");
                     }
                  }
               }
            }

            if (HSTSHeader.isHSTSEnabled() && this.request.isSecure() && this.headers.getHeader("Strict-Transport-Security") == null) {
               this.setHeader("Strict-Transport-Security", HSTSHeader.getHSTSValue());
            }

            if (this.getStatus() == 204) {
               this.outputStream.setWriteEnabled(false);
            }

            int len = this.headers.writeHeaders(ps, this.statusMessage);
            this.incrementBytesSentCount((long)len);
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Response committed. request: '" + this.request.toStringSimple() + "' response: " + this.toString());
            }
         } catch (Throwable var18) {
            if (var14 != null) {
               var14.th = var18;
               InstrumentationSupport.postProcess(var14);
            }

            throw var18;
         }

         if (var14 != null) {
            InstrumentationSupport.postProcess(var14);
         }

         return;
      }

      if (var14 != null) {
         InstrumentationSupport.postProcess(var14);
      }

   }

   private boolean isGzipSupported() {
      String acceptEncoding = this.request.getHeader("Accept-Encoding");
      return acceptEncoding != null && acceptEncoding.indexOf("gzip") != -1;
   }

   private boolean isCompressable() {
      String contentEncoding = this.getHeader("Content-Encoding");
      if (contentEncoding != null && contentEncoding.indexOf("gzip") != -1) {
         return false;
      } else {
         long contentLength = this.getContentLengthLong();
         return contentLength != -1L && contentLength <= this.context.getConfigManager().getMinCompressionContentLength() && (!this.outputStream.getOutput().isChunking() || (long)this.outputStream.getCount() <= this.context.getConfigManager().getMinCompressionContentLength()) ? false : this.context.getConfigManager().isContentTypeCompressable(this.getContentType());
      }
   }

   private String encodeURL(String url, HttpSession session) {
      if (session != null && !this.request.isRequestedSessionIdFromCookie() && this.context.getSessionContext().getConfigMgr().isSessionTrackingEnabled() && this.context.getSessionContext().getConfigMgr().isUrlRewritingEnabled() && ((SessionInternal)session).isValid()) {
         String queryString = null;
         String anchor = null;
         int questionMark = url.indexOf(63);
         if (questionMark != -1) {
            queryString = url.substring(questionMark + 1);
            url = url.substring(0, questionMark);
         } else {
            int i = url.indexOf(35);
            if (i != -1) {
               anchor = url.substring(i + 1);
               url = url.substring(0, i);
            }
         }

         String jrouteId = this.request.getHeader("proxy-jroute");
         if (!this.context.getSessionContext().getConfigMgr().isEncodeSessionIdInQueryParamsEnabled()) {
            String cookieName = this.context.getSessionContext().getConfigMgr().getCookieName();
            if (cookieName.equals("JSESSIONID")) {
               cookieName = "jsessionid";
            }

            int semiColon = url.indexOf(59);
            StringBuffer sb = new StringBuffer();
            if (semiColon == -1) {
               sb.append(url).append(';').append(cookieName).append('=').append(((SessionInternal)session).getIdWithServerInfo());
            } else {
               String uri = url.substring(0, semiColon);
               sb.append(uri).append(';').append(cookieName).append('=').append(((SessionInternal)session).getIdWithServerInfo());
            }

            if (jrouteId != null && "jsessionid".equals(cookieName)) {
               sb.append(":");
               sb.append(jrouteId);
            }

            url = sb.toString();
         } else {
            StringBuffer queryStringBuf = (new StringBuffer(this.context.getSessionContext().getConfigMgr().getCookieName())).append('=').append(((SessionInternal)session).getIdWithServerInfo());
            if (queryString != null) {
               queryStringBuf.append('&').append(queryString);
            }

            queryString = queryStringBuf.toString();
         }

         if (queryString != null) {
            url = url + '?' + queryString;
         } else if (anchor != null) {
            url = url + "#" + anchor;
         }

         return url;
      } else {
         return url;
      }
   }

   private void setErrorAttributes(ServletStubImpl jstub, int code, String msg) {
      String requestURI = this.request.getRequestURI();
      this.request.setAttribute("javax.servlet.error.status_code", new Integer(code));
      if (this.request.getAttribute("javax.servlet.error.message") == null) {
         String message;
         if (code == 404) {
            message = requestURI != null ? requestURI : ErrorMessages.getSection(code);
            this.request.setAttribute("javax.servlet.error.message", message);
         } else {
            message = msg != null ? msg : ErrorMessages.getSection(code);
            this.request.setAttribute("javax.servlet.error.message", message);
         }
      }

      this.request.setAttribute("javax.servlet.error.request_uri", requestURI);
      if (jstub != null) {
         this.request.setAttribute("javax.servlet.error.servlet_name", jstub.getServletName());
      }

   }

   final void setSessionCookie(HttpSession session) {
      if (this.context.getSessionContext().getConfigMgr().isSessionCookiesEnabled() && this.context.getSessionContext().getConfigMgr().isSessionTrackingEnabled()) {
         SessionContext sessionContext = this.context.getSessionContext();
         this.removeCookie(sessionContext.getConfigMgr().getCookieName(), sessionContext.getConfigMgr().getCookiePath());
         Cookie c = new Cookie(sessionContext.getConfigMgr().getCookieName(), ((SessionInternal)session).getIdWithServerInfo());
         if (!this.requestHadCookie(c)) {
            if (HTTPDebugLogger.isEnabled()) {
               this.trace("Setting cookie for " + this.request.toStringSimple());
            }

            c.setComment(sessionContext.getConfigMgr().getCookieComment());
            c.setMaxAge(sessionContext.getConfigMgr().getCookieMaxAgeSecs());
            String domain = sessionContext.getConfigMgr().getCookieDomain();
            if (domain != null) {
               c.setDomain(domain);
            }

            ServletRequestImpl var10000 = this.request;
            if (!ServletRequestImpl.ignorePluginParamsForCookiePath) {
               c.setPath(this.processProxyPathHeaders(sessionContext.getConfigMgr().getCookiePath()));
            } else {
               c.setPath(sessionContext.getConfigMgr().getCookiePath());
            }

            if (sessionContext.getConfigMgr().isCookieSecure()) {
               c.setSecure(true);
            }

            this.addCookie(c);
         }
      }
   }

   public final int getStatus() {
      return this.statusCode;
   }

   public final int getContentLength() {
      return (int)this.getContentLengthLong();
   }

   public final long getContentLengthLong() {
      return this.contentLength > -1L ? this.contentLength : this.outputStream.getTotal();
   }

   private void ensureContentLength() throws IOException {
      if (this.fileSender != null && !this.fileSender.usesServletOutputStream()) {
         this.outputStream.ensureContentLength(this.fileSender.getBytesSent());
      } else {
         this.outputStream.ensureContentLength(0L);
      }

   }

   public void setResponseReady() {
      if (this.request != null) {
         this.request.disableFutureResponse();
      }

   }

   public final void send() throws IOException {
      LocalHolder var7;
      if ((var7 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var7.argsCapture) {
            var7.args = new Object[1];
            var7.args[0] = this;
         }

         InstrumentationSupport.createDynamicJoinPoint(var7);
         InstrumentationSupport.preProcess(var7);
         var7.resetPostBegin();
      }

      try {
         HttpServer httpServer = this.getHttpServer();
         Throwable throwable = null;

         try {
            if (httpServer.getLogManager() != null && httpServer.getLogManager().isExtendedFormat()) {
               this.request.getHttpAccountingInfo().init(this.request, SecurityModule.getCurrentUser(this.context.getSecurityContext(), this.request));
            }

            this.syncSession();
            this.isFutureResponseSent = this.request.isFutureResponseEnabled();
            this.ensureContentLength();
            this.outputStream.commit();
            if (this.outputStream.useGzip()) {
               long gzipedContentLength;
               if (HTTPDebugLogger.isEnabled()) {
                  gzipedContentLength = this.outputStream.getCalculator().getGzipedContentLength();
               } else {
                  gzipedContentLength = ((ChunkedGzipOutputStream)this.outputStream.getOutput().getOutput().getOutput()).getGzipedContentLength();
               }

               ServletOutputStreamImpl var10002 = this.outputStream;
               this.incrementBytesSentCount(gzipedContentLength + (long)ServletOutputStreamImpl.FINAL_CHUNK.length);
            } else {
               this.incrementBytesSentCount(this.outputStream.getTotal());
            }

            this.incrementMessageSentCount();
         } catch (Throwable var21) {
            if (HTTPDebugLogger.isEnabled()) {
               this.trace(var21.toString());
            }

            throwable = var21;
         } finally {
            this.abortActiveTx();
            httpServer.getLogManager().log(this.request, this);

            try {
               this.outputStream.finish();
            } catch (IOException var20) {
               throwable = var20;
            }

            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Response finished for request '" + this.request.toStringSimple() + "'");
            }

            if (this.getUseKeepAlive() && throwable == null) {
               if (HTTPDebugLogger.isEnabled()) {
                  this.trace("Requeuing Keep-Alive connection");
               }

               this.request.getConnection().requeue();
            } else if (this.request.getInputHelper().getRequestParser().isProtocolVersion_2()) {
               this.request.getConnection().requeue();
            } else if (this.request.isUpgrade()) {
               this.request.getConnection().requeue();
            } else {
               if (HTTPDebugLogger.isEnabled()) {
                  this.trace("Closing when it's non Keep-Alive connection or exception happens");
               }

               if (HTTPDebugLogger.isEnabled() && throwable instanceof Exception) {
                  HTTPDebugLogger.debug("Catching Exception", (Exception)throwable);
               }

               this.cleanupRequest((Throwable)throwable);
            }

         }
      } catch (Throwable var23) {
         if (var7 != null) {
            var7.th = var23;
            InstrumentationSupport.postProcess(var7);
         }

         throw var23;
      }

      if (var7 != null) {
         InstrumentationSupport.postProcess(var7);
      }

   }

   public void syncSession() throws IOException {
      Thread th = Thread.currentThread();
      ClassLoader cl = this.context.pushEnvironment(th);
      SubjectHandle subject = SecurityModule.getCurrentUser(this.context.getSecurityContext(), this.request);
      if (subject == null) {
         subject = WebAppSecurity.getProvider().getAnonymousSubject();
      }

      Throwable t = (Throwable)subject.run(new PrivilegedAction() {
         public Object run() {
            try {
               ServletResponseImpl.this.request.getSessionHelper().syncSession();
               return null;
            } catch (Throwable var2) {
               return var2;
            }
         }
      });
      WebAppServletContext var10000 = this.context;
      WebAppServletContext.popEnvironment(th, cl);
      if (t != null) {
         if (t instanceof Error) {
            throw (Error)t;
         } else if (t instanceof RuntimeException) {
            throw (RuntimeException)t;
         } else {
            throw new IOException(StackTraceUtils.throwable2StackTrace(t));
         }
      }
   }

   void cleanupRequest(Throwable t) {
      this.request.reset();
      if (t instanceof IOException) {
         this.request.getConnection().deliverHasException((IOException)t);
      } else {
         this.request.getConnection().close();
      }

   }

   private void abortActiveTx() {
      if (!this.request.isFutureResponseEnabled()) {
         try {
            TransactionProvider tp = registry.getTransactionProvider();
            if (tp == null) {
               return;
            }

            Transaction current = tp.getTransactionManager().getTransaction();
            if (current == null) {
               return;
            }

            if (current.getStatus() == 1 || current.getStatus() == 0) {
               if (HTTPDebugLogger.isEnabled()) {
                  this.trace("Aborting unhandled Tx - " + current);
               }

               current.rollback();
            }
         } catch (SystemException var3) {
            HTTPLogger.logFailedToLookupTransaction(this.context.getLogContext(), var3);
         }

      }
   }

   private void trace(String s) {
      HTTPDebugLogger.debug(this.request.toStringSimple() + ": " + s);
   }

   private boolean requestHadCookie(Cookie c) {
      if (!this.request.isRequestedSessionIdFromCookie()) {
         return false;
      } else {
         String requestedSessionId = this.request.getSessionHelper().getIncomingSessionCookieValue();
         return requestedSessionId == null ? false : requestedSessionId.startsWith(c.getValue());
      }
   }

   public final WebAppServletContext getContext() {
      return this.context;
   }

   HttpServer getHttpServer() {
      return this.context == null ? registry.getDefaultHttpServer() : this.context.getServer();
   }

   private void objectIfCommitted() {
      if (this.isCommitted()) {
         throw new IllegalStateException("Response already committed");
      }
   }

   public static ServletResponseImpl getOriginalResponse(ServletResponse rp) {
      while(rp instanceof ServletResponseWrapper) {
         rp = ((ServletResponseWrapper)rp).getResponse();
      }

      if (rp == null) {
         throw new AssertionError("Original response not available");
      } else {
         return (ServletResponseImpl)rp;
      }
   }

   public static ArrayList getNoHttpOnlyInternalApps() {
      return noHttpOnlyInternalApps;
   }

   public final boolean isInternalDispatch() {
      return this.request.getConnection().isInternalDispatch();
   }

   public long getBytesSentCount() {
      return this.bytesSent;
   }

   public long getMessagesSentCount() {
      return this.messageSent;
   }

   public void registerAttributeListener(ServletResponseAttributeListener listener) {
      if (listener != null) {
         this.attributeListeners.add(listener);
      }
   }

   private void checkForCRLFChars(String name, String value) {
      if (containsCRLFChars(value)) {
         throw new IllegalArgumentException("Header:" + name + " Cannot contain CRLF Charcters");
      }
   }

   private void checkForCRLFChars(Cookie c) {
      if (c != null && containsCRLFChars(c.getValue())) {
         throw new IllegalArgumentException("Cookie:" + c.getName() + " Cannot contain CRLF Charcters");
      }
   }

   private static boolean containsCRLFChars(String value) {
      if (value == null) {
         return false;
      } else {
         for(int i = 0; i < CRLF.length; ++i) {
            if (value.indexOf(CRLF[i]) > -1) {
               return true;
            }
         }

         return false;
      }
   }

   void incrementBytesSentCount(long increment) {
      this.bytesSent += increment;
   }

   void incrementMessageSentCount() {
      ++this.messageSent;
   }

   FileSender getFileSender() {
      return this.fileSender;
   }

   void setFileSender(FileSender fs) {
      if (this.fileSender != null) {
         throw new AssertionError("Attempt to replace FileSender");
      } else {
         this.fileSender = fs;
      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append(super.toString()).append("[\n").append(this.headers == null ? "" : this.headers.getResponseInfo(this.statusMessage)).append(this.headers == null ? "" : this.headers.toString()).append(']');
      return buf.toString();
   }

   public Collection getHeaderNames() {
      return this.headers.getHeaderNames();
   }

   public Collection getHeaders(String name) {
      return this.headers.getHeaders(name);
   }

   void setCorrelationResponse() {
      if (!this.correlationSet && !CorrelationManager.DMS_HTTP_HEADER_DISABLED) {
         Correlation correlation = CorrelationFactory.findOrCreateCorrelation(true);
         if (correlation != null) {
            this.addHeader("X-ORACLE-DMS-ECID", correlation.getECID());
            String rid = correlation.getRID();
            if (rid != null) {
               this.addHeader("X-ORACLE-DMS-RID", rid);
            }
         }

         this.correlationSet = true;
      }
   }

   public boolean isFlushOk() {
      return this.outputStream.isFlushOK();
   }

   public long getBufferCount() {
      return this.outputStream == null ? 0L : this.outputStream.getTotal() + (long)this.outputStream.getCount();
   }

   void enableChunkedTransferEncodingHeader(boolean isSet) {
      this.headers.unsetHeader("Content-Length");
      if (isSet) {
         this.headers.setHeader("Transfer-Encoding", "chunked");
      } else {
         this.headers.addHeader("Transfer-Encoding", "chunked");
      }

   }

   public boolean skipDisallowedTrailerField(String name) {
      return this.headers.skipDisallowedTrailerField(name);
   }

   static {
      _WLDF$INST_FLD_Servlet_Response_Send_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Response_Send_Around_Medium");
      _WLDF$INST_FLD_Servlet_Response_Write_Headers_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Response_Write_Headers_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ServletResponseImpl.java", "weblogic.servlet.internal.ServletResponseImpl", "writeHeaders", "()V", 1361, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Response_Write_Headers_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Response_Write_Headers_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ServletResponseImpl.java", "weblogic.servlet.internal.ServletResponseImpl", "send", "()V", 1768, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Response_Send_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null)}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Response_Send_Around_Medium};
      registry = WebServerRegistry.getInstance();
      noHttpOnlyInternalApps = new ArrayList();
      WebAppContainerMBean wacMBean = registry.getWebAppContainerMBean();
      P3P_HEADER_VALUE = wacMBean.getP3PHeaderValue();
      P3P_ENABLED = P3P_HEADER_VALUE != null;
      String noHttpOnlyCookieInternalAppList = System.getProperty("weblogic.cookies.HttpOnlyDisabledInternalApps");
      if (noHttpOnlyCookieInternalAppList != null) {
         StringTokenizer appTokens = new StringTokenizer(noHttpOnlyCookieInternalAppList, "|");

         while(appTokens.hasMoreTokens()) {
            noHttpOnlyInternalApps.add(appTokens.nextToken().trim());
         }
      }

      CRLF = new String[]{"\r", "\n", "%0a", "%0d", "%0A", "%0D"};
   }
}
