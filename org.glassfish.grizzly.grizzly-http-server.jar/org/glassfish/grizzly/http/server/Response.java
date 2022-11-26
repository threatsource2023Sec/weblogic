package org.glassfish.grizzly.http.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CloseListener;
import org.glassfish.grizzly.CloseType;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GenericCloseListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.Cookies;
import org.glassfish.grizzly.http.HttpContext;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.Protocol;
import org.glassfish.grizzly.http.io.InputBuffer;
import org.glassfish.grizzly.http.io.NIOOutputStream;
import org.glassfish.grizzly.http.io.NIOWriter;
import org.glassfish.grizzly.http.io.OutputBuffer;
import org.glassfish.grizzly.http.server.io.ServerOutputBuffer;
import org.glassfish.grizzly.http.server.util.HtmlHelper;
import org.glassfish.grizzly.http.util.CharChunk;
import org.glassfish.grizzly.http.util.ContentType;
import org.glassfish.grizzly.http.util.CookieSerializerUtils;
import org.glassfish.grizzly.http.util.FastHttpDateFormat;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HeaderValue;
import org.glassfish.grizzly.http.util.HttpRequestURIDecoder;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.http.util.UEncoder;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.utils.DelayedExecutor;

public class Response {
   private static final Logger LOGGER = Grizzly.logger(Response.class);
   private boolean cacheEnabled = false;
   private static final Locale DEFAULT_LOCALE = Locale.getDefault();
   private static final String HTTP_RESPONSE_DATE_HEADER = "EEE, dd MMM yyyy HH:mm:ss zzz";
   protected SimpleDateFormat format = null;
   protected static final String info = "org.glassfish.grizzly.http.server.Response/2.0";
   protected Request request = null;
   protected HttpResponsePacket response;
   protected FilterChainContext ctx;
   protected HttpContext httpContext;
   protected final ServerOutputBuffer outputBuffer = new ServerOutputBuffer();
   private final NIOOutputStreamImpl outputStream = new NIOOutputStreamImpl();
   private final NIOWriterImpl writer = new NIOWriterImpl();
   protected boolean appCommitted = false;
   protected boolean error = false;
   protected boolean usingOutputStream = false;
   protected boolean usingWriter = false;
   protected final UEncoder urlEncoder = new UEncoder();
   protected final CharChunk redirectURLCC = new CharChunk();
   protected DelayedExecutor.DelayQueue delayQueue;
   SuspendState suspendState;
   private final SuspendedContextImpl suspendedContext;
   private SuspendStatus suspendStatus;
   private boolean sendFileEnabled;
   private ErrorPageGenerator errorPageGenerator;

   static DelayedExecutor.DelayQueue createDelayQueue(DelayedExecutor delayedExecutor) {
      return delayedExecutor.createDelayQueue(new DelayQueueWorker(), new DelayQueueResolver());
   }

   protected Response() {
      this.suspendState = Response.SuspendState.NONE;
      this.suspendedContext = new SuspendedContextImpl();
      this.urlEncoder.addSafeCharacter('/');
   }

   public void initialize(Request request, HttpResponsePacket response, FilterChainContext ctx, DelayedExecutor.DelayQueue delayQueue, HttpServerFilter serverFilter) {
      this.request = request;
      this.response = response;
      this.sendFileEnabled = serverFilter != null && serverFilter.getConfiguration().isSendFileEnabled();
      this.outputBuffer.initialize(this, ctx);
      this.ctx = ctx;
      this.httpContext = HttpContext.get(ctx);
      this.delayQueue = delayQueue;
   }

   SuspendStatus initSuspendStatus() {
      this.suspendStatus = SuspendStatus.create();
      return this.suspendStatus;
   }

   public Request getRequest() {
      return this.request;
   }

   public HttpResponsePacket getResponse() {
      return this.response;
   }

   protected void recycle() {
      this.delayQueue = null;
      this.outputBuffer.recycle();
      this.outputStream.recycle();
      this.writer.recycle();
      this.usingOutputStream = false;
      this.usingWriter = false;
      this.appCommitted = false;
      this.error = false;
      this.errorPageGenerator = null;
      this.request = null;
      this.response.recycle();
      this.sendFileEnabled = false;
      this.response = null;
      this.ctx = null;
      this.suspendState = Response.SuspendState.NONE;
      this.cacheEnabled = false;
   }

   public void setTrailers(Supplier trailerSupplier) {
      if (this.isCommitted()) {
         throw new IllegalStateException("Response has already been committed.");
      } else {
         Protocol protocol = this.request.getProtocol();
         if (!protocol.equals(Protocol.HTTP_0_9) && !protocol.equals(Protocol.HTTP_1_0)) {
            if (protocol.equals(Protocol.HTTP_1_1)) {
               if (!this.response.isChunkingAllowed()) {
                  throw new IllegalStateException("Chunked transfer-encoding disabled.");
               }

               this.response.setChunked(true);
            }

            this.outputBuffer.setTrailers(trailerSupplier);
         } else {
            throw new IllegalStateException("Trailers not supported by response protocol version " + protocol);
         }
      }
   }

   public Supplier getTrailers() {
      return this.outputBuffer.getTrailers();
   }

   public String encodeURL(String url) {
      String absolute = this.toAbsolute(url, false);
      if (this.isEncodeable(absolute)) {
         if (url.equalsIgnoreCase("")) {
            url = absolute;
         }

         return this.toEncoded(url, this.request.getSession().getIdInternal());
      } else {
         return url;
      }
   }

   public String encodeRedirectURL(String url) {
      return this.isEncodeable(this.toAbsolute(url, false)) ? this.toEncoded(url, this.request.getSession().getIdInternal()) : url;
   }

   protected boolean isEncodeable(String location) {
      if (location == null) {
         return false;
      } else if (location.startsWith("#")) {
         return false;
      } else {
         Session session = this.request.getSession(false);
         return session != null && !this.request.isRequestedSessionIdFromCookie() && doIsEncodeable(this.request, session, location);
      }
   }

   private static boolean doIsEncodeable(Request request, Session session, String location) {
      URL url;
      try {
         url = new URL(location);
      } catch (MalformedURLException var8) {
         return false;
      }

      if (!request.getScheme().equalsIgnoreCase(url.getProtocol())) {
         return false;
      } else if (!request.getServerName().equalsIgnoreCase(url.getHost())) {
         return false;
      } else {
         int serverPort = request.getServerPort();
         if (serverPort == -1) {
            if ("https".equals(request.getScheme())) {
               serverPort = 443;
            } else {
               serverPort = 80;
            }
         }

         int urlPort = url.getPort();
         if (urlPort == -1) {
            if ("https".equals(url.getProtocol())) {
               urlPort = 443;
            } else {
               urlPort = 80;
            }
         }

         if (serverPort != urlPort) {
            return false;
         } else {
            String contextPath = "/";
            String file = url.getFile();
            if (file != null && file.startsWith(contextPath)) {
               return !file.contains(";jsessionid=" + session.getIdInternal());
            } else {
               return false;
            }
         }
      }
   }

   public String getInfo() {
      return "org.glassfish.grizzly.http.server.Response/2.0";
   }

   public void setError() {
      this.error = true;
   }

   public boolean isError() {
      return this.error;
   }

   public ErrorPageGenerator getErrorPageGenerator() {
      return this.errorPageGenerator;
   }

   public void setErrorPageGenerator(ErrorPageGenerator errorPageGenerator) {
      this.errorPageGenerator = errorPageGenerator;
   }

   public void setDetailMessage(String message) {
      this.checkResponse();
      this.response.setReasonPhrase(message);
   }

   public String getDetailMessage() {
      this.checkResponse();
      return this.response.getReasonPhrase();
   }

   public void finish() {
      try {
         this.outputBuffer.endRequest();
      } catch (IOException var2) {
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_RESPONSE_FINISH_ERROR(), var2);
         }
      } catch (Throwable var3) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_RESPONSE_FINISH_ERROR(), var3);
         }
      }

   }

   public int getContentLength() {
      this.checkResponse();
      return (int)this.response.getContentLength();
   }

   public long getContentLengthLong() {
      this.checkResponse();
      return this.response.getContentLength();
   }

   public String getContentType() {
      this.checkResponse();
      return this.response.getContentType();
   }

   public int getBufferSize() {
      return this.outputBuffer.getBufferSize();
   }

   public String getCharacterEncoding() {
      this.checkResponse();
      String characterEncoding = this.response.getCharacterEncoding();
      return characterEncoding == null ? org.glassfish.grizzly.http.util.Constants.DEFAULT_HTTP_CHARACTER_ENCODING : characterEncoding;
   }

   public void setCharacterEncoding(String charset) {
      this.checkResponse();
      if (!this.isCommitted()) {
         if (!this.usingWriter) {
            this.response.setCharacterEncoding(charset);
         }
      }
   }

   public NIOOutputStream createOutputStream() {
      this.outputStream.setOutputBuffer(this.outputBuffer);
      return this.outputStream;
   }

   public NIOOutputStream getNIOOutputStream() {
      if (this.usingWriter) {
         throw new IllegalStateException("Illegal attempt to call getOutputStream() after getWriter() has already been called.");
      } else {
         this.usingOutputStream = true;
         this.outputStream.setOutputBuffer(this.outputBuffer);
         return this.outputStream;
      }
   }

   public OutputStream getOutputStream() {
      return this.getNIOOutputStream();
   }

   public Locale getLocale() {
      this.checkResponse();
      Locale locale = this.response.getLocale();
      if (locale == null) {
         locale = DEFAULT_LOCALE;
         this.response.setLocale(locale);
      }

      return locale;
   }

   public Writer getWriter() {
      return this.getNIOWriter();
   }

   public NIOWriter getNIOWriter() {
      if (this.usingOutputStream) {
         throw new IllegalStateException("Illegal attempt to call getWriter() after getOutputStream() has already been called.");
      } else {
         this.setCharacterEncoding(this.getCharacterEncoding());
         this.usingWriter = true;
         this.outputBuffer.prepareCharacterEncoder();
         this.writer.setOutputBuffer(this.outputBuffer);
         return this.writer;
      }
   }

   public boolean isCommitted() {
      this.checkResponse();
      return this.response.isCommitted();
   }

   public void flush() throws IOException {
      this.outputBuffer.flush();
   }

   public OutputBuffer getOutputBuffer() {
      return this.outputBuffer;
   }

   public void reset() {
      this.checkResponse();
      if (this.isCommitted()) {
         throw new IllegalStateException();
      } else {
         this.response.getHeaders().clear();
         this.response.setContentLanguage((String)null);
         if (this.response.getContentLength() > 0L) {
            this.response.setContentLengthLong(-1L);
         }

         this.response.setCharacterEncoding((String)null);
         this.response.setStatus((HttpStatus)null);
         this.response.setContentType((String)null);
         this.response.setLocale((Locale)null);
         this.outputBuffer.reset();
         this.usingWriter = false;
         this.usingOutputStream = false;
      }
   }

   public void resetBuffer() {
      this.resetBuffer(false);
   }

   public void resetBuffer(boolean resetWriterStreamFlags) {
      if (this.isCommitted()) {
         throw new IllegalStateException("Cannot reset buffer after response has been committed.");
      } else {
         this.outputBuffer.reset();
         if (resetWriterStreamFlags) {
            this.usingOutputStream = false;
            this.usingWriter = false;
         }

      }
   }

   public void setBufferSize(int size) {
      if (this.isCommitted()) {
         throw new IllegalStateException("Unable to change buffer size as the response has been committed");
      } else {
         this.outputBuffer.setBufferSize(size);
      }
   }

   public void setContentLengthLong(long length) {
      this.checkResponse();
      if (!this.isCommitted()) {
         if (!this.usingWriter) {
            this.response.setContentLengthLong(length);
         }
      }
   }

   public void setContentLength(int length) {
      this.setContentLengthLong((long)length);
   }

   public void setContentType(String type) {
      this.checkResponse();
      if (!this.isCommitted()) {
         if (this.usingWriter && type != null) {
            int index = type.indexOf(";");
            if (index != -1) {
               type = type.substring(0, index);
            }
         }

         this.response.setContentType(type);
      }
   }

   public void setContentType(ContentType type) {
      this.checkResponse();
      if (!this.isCommitted()) {
         if (type == null) {
            this.response.setContentType((String)null);
         } else {
            if (!this.usingWriter) {
               this.response.setContentType(type);
            } else {
               this.response.setContentType(type.getMimeType());
            }

         }
      }
   }

   public void setLocale(Locale locale) {
      this.checkResponse();
      if (!this.isCommitted()) {
         this.response.setLocale(locale);
      }
   }

   public Cookie[] getCookies() {
      Cookies cookies = new Cookies();
      cookies.setHeaders(this.response.getHeaders(), false);
      return cookies.get();
   }

   public String getHeader(String name) {
      this.checkResponse();
      return this.response.getHeader(name);
   }

   public String[] getHeaderNames() {
      this.checkResponse();
      MimeHeaders headers = this.response.getHeaders();
      int n = headers.size();
      String[] result = new String[n];

      for(int i = 0; i < n; ++i) {
         result[i] = headers.getName(i).toString();
      }

      return result;
   }

   public String[] getHeaderValues(String name) {
      this.checkResponse();
      Collection result = new LinkedList();
      Iterator var3 = this.response.getHeaders().values(name).iterator();

      while(var3.hasNext()) {
         String headerValue = (String)var3.next();
         result.add(headerValue);
      }

      return (String[])result.toArray(new String[result.size()]);
   }

   public String getMessage() {
      this.checkResponse();
      return this.response.getReasonPhrase();
   }

   public int getStatus() {
      this.checkResponse();
      return this.response.getStatus();
   }

   public void reset(int status, String message) {
      this.reset();
      this.setStatus(status, message);
   }

   public void addCookie(final Cookie cookie) {
      if (!this.isCommitted()) {
         final StringBuilder sb = new StringBuilder();
         if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  CookieSerializerUtils.serializeServerCookie(sb, cookie);
                  return null;
               }
            });
         } else {
            CookieSerializerUtils.serializeServerCookie(sb, cookie);
         }

         this.addHeader(Header.SetCookie, sb.toString());
      }
   }

   protected void addSessionCookieInternal(final Cookie cookie) {
      if (!this.isCommitted()) {
         String name = cookie.getName();
         String headername = Header.SetCookie.toString();
         String startsWith = name + "=";
         final StringBuilder sb = new StringBuilder();
         if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  CookieSerializerUtils.serializeServerCookie(sb, cookie);
                  return null;
               }
            });
         } else {
            CookieSerializerUtils.serializeServerCookie(sb, cookie);
         }

         String cookieString = sb.toString();
         boolean set = false;
         MimeHeaders headers = this.response.getHeaders();
         int n = headers.size();

         for(int i = 0; i < n; ++i) {
            if (headers.getName(i).toString().equals(headername) && headers.getValue(i).toString().startsWith(startsWith)) {
               headers.getValue(i).setString(cookieString);
               set = true;
            }
         }

         if (!set) {
            this.addHeader(headername, cookieString);
         }

      }
   }

   protected void removeSessionCookies() {
      String sessionCookieName = this.request.getSessionCookieName();
      String pattern = sessionCookieName != null ? '^' + sessionCookieName + "(?:SSO)?=.*" : "^JSESSIONID(?:SSO)?=.*";
      this.response.getHeaders().removeHeaderMatches(Header.SetCookie, pattern);
   }

   public void addDateHeader(String name, long value) {
      if (!this.isCommitted()) {
         if (this.format == null) {
            this.format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
            this.format.setTimeZone(TimeZone.getTimeZone("GMT"));
         }

         this.addHeader(name, FastHttpDateFormat.formatDate(value, this.format));
      }
   }

   public void addDateHeader(Header header, long value) {
      if (!this.isCommitted()) {
         if (this.format == null) {
            this.format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
            this.format.setTimeZone(TimeZone.getTimeZone("GMT"));
         }

         this.addHeader(header, FastHttpDateFormat.formatDate(value, this.format));
      }
   }

   public void addHeader(String name, String value) {
      this.checkResponse();
      if (!this.isCommitted()) {
         this.response.addHeader(name, value);
      }
   }

   public void addHeader(String name, HeaderValue value) {
      this.checkResponse();
      if (!this.isCommitted()) {
         this.response.addHeader(name, value);
      }
   }

   public void addHeader(Header header, String value) {
      this.checkResponse();
      if (!this.isCommitted()) {
         this.response.addHeader(header, value);
      }
   }

   public void addHeader(Header header, HeaderValue value) {
      this.checkResponse();
      if (!this.isCommitted()) {
         this.response.addHeader(header, value);
      }
   }

   public void addIntHeader(String name, int value) {
      if (!this.isCommitted()) {
         this.addHeader(name, "" + value);
      }
   }

   public void addIntHeader(Header header, int value) {
      if (!this.isCommitted()) {
         this.addHeader(header, Integer.toString(value));
      }
   }

   public boolean containsHeader(String name) {
      this.checkResponse();
      return this.response.containsHeader(name);
   }

   public boolean containsHeader(Header header) {
      this.checkResponse();
      return this.response.containsHeader(header);
   }

   public void sendAcknowledgement() throws IOException {
      if (!this.isCommitted() && this.request.requiresAcknowledgement()) {
         this.response.setAcknowledgement(true);
         this.outputBuffer.acknowledge();
      }
   }

   public void sendError(int status) throws IOException {
      this.sendError(status, (String)null);
   }

   public void sendError(int status, String message) throws IOException {
      this.checkResponse();
      if (this.isCommitted()) {
         throw new IllegalStateException("Illegal attempt to call sendError() after the response has been committed.");
      } else {
         this.setError();
         this.response.getHeaders().removeHeader(Header.TransferEncoding);
         this.response.setContentLanguage((String)null);
         this.response.setContentLengthLong(-1L);
         this.response.setChunked(false);
         this.response.setCharacterEncoding((String)null);
         this.response.setContentType((String)null);
         this.response.setLocale((Locale)null);
         this.outputBuffer.reset();
         this.usingWriter = false;
         this.usingOutputStream = false;
         this.setStatus(status, message);
         String nonNullMsg = message;
         if (message == null) {
            HttpStatus httpStatus = HttpStatus.getHttpStatus(status);
            if (httpStatus != null && httpStatus.getReasonPhrase() != null) {
               nonNullMsg = httpStatus.getReasonPhrase();
            } else {
               nonNullMsg = "Unknown Error";
            }
         }

         HtmlHelper.sendErrorPage(this.request, this, this.getErrorPageGenerator(), status, nonNullMsg, nonNullMsg, (Throwable)null);
         this.finish();
      }
   }

   public void sendRedirect(String location) throws IOException {
      if (this.isCommitted()) {
         throw new IllegalStateException("Illegal attempt to redirect the response as the response has been committed.");
      } else {
         this.resetBuffer();

         try {
            String absolute = this.toAbsolute(location, true);
            this.setStatus(HttpStatus.FOUND_302);
            this.setHeader(Header.Location, absolute);
            this.setContentType("text/html");
            this.setLocale(Locale.getDefault());
            String filteredMsg = filter(absolute);
            StringBuilder sb = new StringBuilder(150 + absolute.length());
            sb.append("<html>\r\n");
            sb.append("<head><title>Document moved</title></head>\r\n");
            sb.append("<body><h1>Document moved</h1>\r\n");
            sb.append("This document has moved <a href=\"");
            sb.append(filteredMsg);
            sb.append("\">here</a>.<p>\r\n");
            sb.append("</body>\r\n");
            sb.append("</html>\r\n");

            try {
               this.getWriter().write(sb.toString());
               this.getWriter().flush();
            } catch (IllegalStateException var8) {
               try {
                  this.getOutputStream().write(sb.toString().getBytes(org.glassfish.grizzly.http.util.Constants.DEFAULT_HTTP_CHARSET));
               } catch (IllegalStateException var7) {
               }
            }
         } catch (IllegalArgumentException var9) {
            this.sendError(404);
         }

         this.finish();
      }
   }

   public void setDateHeader(String name, long value) {
      if (!this.isCommitted()) {
         if (this.format == null) {
            this.format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
            this.format.setTimeZone(TimeZone.getTimeZone("GMT"));
         }

         this.setHeader(name, FastHttpDateFormat.formatDate(value, this.format));
      }
   }

   public void setDateHeader(Header header, long value) {
      if (!this.isCommitted()) {
         if (this.format == null) {
            this.format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
            this.format.setTimeZone(TimeZone.getTimeZone("GMT"));
         }

         this.setHeader(header, FastHttpDateFormat.formatDate(value, this.format));
      }
   }

   public void setHeader(String name, String value) {
      this.checkResponse();
      if (!this.isCommitted()) {
         this.response.setHeader(name, value);
      }
   }

   public void setHeader(String name, HeaderValue value) {
      this.checkResponse();
      if (!this.isCommitted()) {
         this.response.setHeader(name, value);
      }
   }

   public void setHeader(Header header, String value) {
      this.checkResponse();
      if (!this.isCommitted()) {
         this.response.setHeader(header, value);
      }
   }

   public void setHeader(Header header, HeaderValue value) {
      this.checkResponse();
      if (!this.isCommitted()) {
         this.response.setHeader(header, value);
      }
   }

   public void setIntHeader(String name, int value) {
      if (!this.isCommitted()) {
         this.setHeader(name, "" + value);
      }
   }

   public void setIntHeader(Header header, int value) {
      if (!this.isCommitted()) {
         this.setHeader(header, Integer.toString(value));
      }
   }

   public void setStatus(int status) {
      this.setStatus(status, (String)null);
   }

   public void setStatus(int status, String message) {
      this.checkResponse();
      if (!this.isCommitted()) {
         this.response.setStatus(status);
         this.response.setReasonPhrase(message);
      }
   }

   public void setStatus(HttpStatus status) {
      this.checkResponse();
      if (!this.isCommitted()) {
         status.setValues(this.response);
      }
   }

   protected String toAbsolute(String location, boolean normalize) {
      if (location == null) {
         return null;
      } else {
         boolean leadingSlash = location.startsWith("/");
         if (!leadingSlash && location.contains("://")) {
            return location;
         } else {
            String scheme = this.request.getScheme();
            String name = this.request.getServerName();
            int port = this.request.getServerPort();
            this.redirectURLCC.recycle();
            CharChunk cc = this.redirectURLCC;

            try {
               cc.append(scheme, 0, scheme.length());
               cc.append("://", 0, 3);
               cc.append(name, 0, name.length());
               final String relativePath;
               if (scheme.equals("http") && port != 80 || scheme.equals("https") && port != 443) {
                  cc.append(':');
                  relativePath = port + "";
                  cc.append(relativePath, 0, relativePath.length());
               }

               if (!leadingSlash) {
                  relativePath = this.request.getDecodedRequestURI();
                  int pos = relativePath.lastIndexOf(47);
                  relativePath = relativePath.substring(0, pos);
                  String encodedURI;
                  if (System.getSecurityManager() != null) {
                     try {
                        encodedURI = (String)AccessController.doPrivileged(new PrivilegedExceptionAction() {
                           public String run() throws IOException {
                              return Response.this.urlEncoder.encodeURL(relativePath);
                           }
                        });
                     } catch (PrivilegedActionException var12) {
                        throw new IllegalArgumentException(location, var12.getCause());
                     }
                  } else {
                     encodedURI = this.urlEncoder.encodeURL(relativePath);
                  }

                  cc.append(encodedURI, 0, encodedURI.length());
                  cc.append('/');
               }

               cc.append(location, 0, location.length());
            } catch (IOException var13) {
               throw new IllegalArgumentException(location, var13);
            }

            if (normalize) {
               HttpRequestURIDecoder.normalizeChars(cc);
            }

            return cc.toString();
         }
      }
   }

   public static String filter(String message) {
      if (message == null) {
         return null;
      } else {
         char[] content = new char[message.length()];
         message.getChars(0, message.length(), content, 0);
         StringBuilder result = new StringBuilder(content.length + 50);

         for(int i = 0; i < content.length; ++i) {
            switch (content[i]) {
               case '"':
                  result.append("&quot;");
                  break;
               case '&':
                  result.append("&amp;");
                  break;
               case '<':
                  result.append("&lt;");
                  break;
               case '>':
                  result.append("&gt;");
                  break;
               default:
                  result.append(content[i]);
            }
         }

         return result.toString();
      }
   }

   protected String toEncoded(String url, String sessionId) {
      if (url != null && sessionId != null) {
         String path = url;
         String query = "";
         String anchor = "";
         int question = url.indexOf(63);
         if (question >= 0) {
            path = url.substring(0, question);
            query = url.substring(question);
         }

         int pound = path.indexOf(35);
         if (pound >= 0) {
            anchor = path.substring(pound);
            path = path.substring(0, pound);
         }

         StringBuilder sb = new StringBuilder(path);
         if (sb.length() > 0) {
            sb.append(";jsessionid=");
            sb.append(sessionId);
         }

         String jrouteId = this.request.getHeader("proxy-jroute");
         if (jrouteId != null) {
            sb.append(":");
            sb.append(jrouteId);
         }

         sb.append(anchor);
         sb.append(query);
         return sb.toString();
      } else {
         return url;
      }
   }

   public boolean isCacheEnabled() {
      return this.cacheEnabled;
   }

   public SuspendContext getSuspendContext() {
      return this.suspendedContext;
   }

   public boolean isSuspended() {
      this.checkResponse();
      SuspendState state;
      synchronized(this.suspendedContext) {
         state = this.suspendState;
      }

      return state == Response.SuspendState.SUSPENDED || state == Response.SuspendState.RESUMING || state == Response.SuspendState.CANCELLING;
   }

   public void suspend() {
      this.suspend(-1L, TimeUnit.MILLISECONDS);
   }

   /** @deprecated */
   public void suspend(long timeout, TimeUnit timeunit) {
      this.suspend(timeout, timeunit, (CompletionHandler)null);
   }

   public void suspend(long timeout, TimeUnit timeunit, CompletionHandler completionHandler) {
      this.suspend(timeout, timeunit, completionHandler, (TimeoutHandler)null);
   }

   public void suspend(long timeout, TimeUnit timeunit, CompletionHandler completionHandler, TimeoutHandler timeoutHandler) {
      this.checkResponse();
      if (this.suspendState != Response.SuspendState.NONE) {
         throw new IllegalStateException("Already Suspended");
      } else {
         this.suspendState = Response.SuspendState.SUSPENDED;
         this.suspendStatus.suspend();
         this.suspendedContext.init(completionHandler, timeoutHandler);
         HttpServerProbeNotifier.notifyRequestSuspend(this.request.httpServerFilter, this.ctx.getConnection(), this.request);
         this.httpContext.getCloseable().addCloseListener(this.suspendedContext.closeListener);
         if (timeout > 0L) {
            long timeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
            this.delayQueue.add(this.suspendedContext.suspendTimeout, timeoutMillis, TimeUnit.MILLISECONDS);
            this.suspendedContext.suspendTimeout.delayMillis = timeoutMillis;
         }

      }
   }

   public void resume() {
      this.checkResponse();
      this.suspendedContext.markResumed();
      this.ctx.resume();
   }

   /** @deprecated */
   public void cancel() {
      this.checkResponse();
      this.suspendedContext.markCancelled();
      this.ctx.resume();
   }

   final void checkResponse() {
      if (this.response == null) {
         throw new IllegalStateException("Internal org.glassfish.grizzly.http.server.Response has not been set");
      }
   }

   public boolean isSendFileEnabled() {
      return this.sendFileEnabled;
   }

   private static class DelayQueueResolver implements DelayedExecutor.Resolver {
      private DelayQueueResolver() {
      }

      public boolean removeTimeout(SuspendTimeout element) {
         if (element.timeoutTimeMillis != -1L) {
            element.timeoutTimeMillis = -1L;
            return true;
         } else {
            return false;
         }
      }

      public long getTimeoutMillis(SuspendTimeout element) {
         return element.timeoutTimeMillis;
      }

      public void setTimeoutMillis(SuspendTimeout element, long timeoutMillis) {
         element.timeoutTimeMillis = timeoutMillis;
      }

      // $FF: synthetic method
      DelayQueueResolver(Object x0) {
         this();
      }
   }

   private static class DelayQueueWorker implements DelayedExecutor.Worker {
      private DelayQueueWorker() {
      }

      public boolean doWork(SuspendTimeout element) {
         return element.onTimeout();
      }

      // $FF: synthetic method
      DelayQueueWorker(Object x0) {
         this();
      }
   }

   protected class SuspendTimeout {
      private final int expectedModCount;
      TimeoutHandler timeoutHandler;
      long delayMillis;
      volatile long timeoutTimeMillis;

      private SuspendTimeout(int modCount, TimeoutHandler timeoutHandler) {
         this.expectedModCount = modCount;
         this.timeoutHandler = timeoutHandler;
      }

      boolean onTimeout() {
         this.timeoutTimeMillis = -1L;
         TimeoutHandler localTimeoutHandler = this.timeoutHandler;
         if (localTimeoutHandler != null && !localTimeoutHandler.onTimeout(Response.this)) {
            return false;
         } else {
            HttpServerProbeNotifier.notifyRequestTimeout(Response.this.request.httpServerFilter, Response.this.ctx.getConnection(), Response.this.request);

            try {
               Response.this.checkResponse();
               if (Response.this.suspendedContext.markCancelled(this.expectedModCount)) {
               }
            } catch (Exception var3) {
            }

            return true;
         }
      }

      private long getTimeout(TimeUnit timeunit) {
         return this.delayMillis > 0L ? timeunit.convert(this.delayMillis, TimeUnit.MILLISECONDS) : this.delayMillis;
      }

      private void setTimeout(long timeout, TimeUnit timeunit) {
         if (timeout > 0L) {
            this.delayMillis = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
         } else {
            this.delayMillis = -1L;
         }

         Response.this.delayQueue.add(this, this.delayMillis, TimeUnit.MILLISECONDS);
      }

      private void reset() {
         this.timeoutTimeMillis = -1L;
         this.timeoutHandler = null;
      }

      // $FF: synthetic method
      SuspendTimeout(int x1, TimeoutHandler x2, Object x3) {
         this(x1, x2);
      }
   }

   public final class SuspendedContextImpl implements SuspendContext {
      private int modCount;
      CompletionHandler completionHandler;
      SuspendTimeout suspendTimeout;
      private CloseListener closeListener;

      public synchronized boolean markResumed() {
         ++this.modCount;
         if (Response.this.suspendState != Response.SuspendState.SUSPENDED) {
            if (Response.this.suspendState != Response.SuspendState.CANCELLED && Response.this.suspendState != Response.SuspendState.CANCELLING) {
               throw new IllegalStateException("Not Suspended");
            } else {
               return false;
            }
         } else {
            Response.this.suspendState = Response.SuspendState.RESUMING;
            Response.this.httpContext.getCloseable().removeCloseListener(this.closeListener);
            if (this.completionHandler != null) {
               this.completionHandler.completed(Response.this);
            }

            this.reset();
            Response.this.suspendState = Response.SuspendState.RESUMED;
            HttpServerProbeNotifier.notifyRequestResume(Response.this.request.httpServerFilter, Response.this.ctx.getConnection(), Response.this.request);
            return true;
         }
      }

      protected synchronized boolean markCancelled(int expectedModCount) {
         if (this.modCount != expectedModCount) {
            return false;
         } else {
            ++this.modCount;
            if (Response.this.suspendState != Response.SuspendState.SUSPENDED) {
               throw new IllegalStateException("Not Suspended");
            } else {
               Response.this.suspendState = Response.SuspendState.CANCELLING;
               Response.this.httpContext.getCloseable().removeCloseListener(this.closeListener);
               if (this.completionHandler != null) {
                  this.completionHandler.cancelled();
               }

               Response.this.suspendState = Response.SuspendState.CANCELLED;
               this.reset();
               HttpServerProbeNotifier.notifyRequestCancel(Response.this.request.httpServerFilter, Response.this.ctx.getConnection(), Response.this.request);
               InputBuffer inputBuffer = Response.this.request.getInputBuffer();
               if (!inputBuffer.isFinished()) {
                  inputBuffer.terminate();
               }

               return true;
            }
         }
      }

      /** @deprecated */
      public synchronized void markCancelled() {
         this.markCancelled(this.modCount);
      }

      private void init(CompletionHandler completionHandler, TimeoutHandler timeoutHandler) {
         this.completionHandler = completionHandler;
         this.suspendTimeout = Response.this.new SuspendTimeout(this.modCount, timeoutHandler);
         this.closeListener = new SuspendCloseListener(this.modCount);
      }

      void reset() {
         this.suspendTimeout.reset();
         this.suspendTimeout = null;
         this.completionHandler = null;
         this.closeListener = null;
      }

      public CompletionHandler getCompletionHandler() {
         return this.completionHandler;
      }

      public TimeoutHandler getTimeoutHandler() {
         return this.suspendTimeout.timeoutHandler;
      }

      public long getTimeout(TimeUnit timeunit) {
         return this.suspendTimeout.getTimeout(timeunit);
      }

      public void setTimeout(long timeout, TimeUnit timeunit) {
         synchronized(Response.this.suspendedContext) {
            if (Response.this.suspendState == Response.SuspendState.SUSPENDED && this.suspendTimeout != null) {
               this.suspendTimeout.setTimeout(timeout, timeunit);
            }
         }
      }

      public boolean isSuspended() {
         return Response.this.isSuspended();
      }

      public SuspendStatus getSuspendStatus() {
         return Response.this.suspendStatus;
      }

      private class SuspendCloseListener implements GenericCloseListener {
         private final int expectedModCount;

         public SuspendCloseListener(int expectedModCount) {
            this.expectedModCount = expectedModCount;
         }

         public void onClosed(Closeable connection, CloseType closeType) throws IOException {
            Response.this.checkResponse();
            if (Response.this.suspendedContext.markCancelled(this.expectedModCount)) {
               Response.this.ctx.completeAndRelease();
            }

         }
      }
   }

   static enum SuspendState {
      NONE,
      SUSPENDED,
      RESUMING,
      RESUMED,
      CANCELLING,
      CANCELLED;
   }
}
