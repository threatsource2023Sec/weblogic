package org.glassfish.grizzly.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.MappingMatch;
import javax.servlet.http.Part;
import javax.servlet.http.PushBuilder;
import javax.servlet.http.WebConnection;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.Session;
import org.glassfish.grizzly.http.server.TimeoutHandler;
import org.glassfish.grizzly.http.server.util.Enumerator;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.localization.LogMessages;

public class HttpServletRequestImpl implements HttpServletRequest, Holders.RequestHolder {
   private static final Logger LOGGER = Grizzly.logger(HttpServletRequestImpl.class);
   protected Request request = null;
   protected HttpServletResponseImpl servletResponse;
   private boolean isAsyncSupported = true;
   private final AtomicBoolean asyncStarted = new AtomicBoolean();
   private AsyncContextImpl asyncContext;
   private boolean isAsyncComplete;
   private Thread asyncStartedThread;
   private final ServletInputStreamImpl inputStream = new ServletInputStreamImpl(this);
   private ServletReaderImpl reader;
   private HttpSessionImpl httpSession = null;
   private WebappContext contextImpl;
   private String contextPath = "";
   private String servletPath = "";
   private String pathInfo;
   protected boolean usingInputStream = false;
   protected boolean usingReader = false;
   private boolean upgrade = false;
   private HttpUpgradeHandler httpUpgradeHandler;
   private HttpServletMapping httpServletMapping;
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(HttpServletRequestImpl.class, 2);

   public static HttpServletRequestImpl create() {
      HttpServletRequestImpl request = (HttpServletRequestImpl)ThreadCache.takeFromCache(CACHE_IDX);
      return request != null ? request : new HttpServletRequestImpl();
   }

   protected HttpServletRequestImpl() {
   }

   public void initialize(Request request, HttpServletResponseImpl servletResponse, WebappContext context) throws IOException {
      this.request = request;
      this.servletResponse = servletResponse;
      this.inputStream.initialize();
      this.contextImpl = context;
      if (context.getRequestCharacterEncoding() != null) {
         request.setCharacterEncoding(context.getResponseCharacterEncoding());
      }

      if (context.getResponseCharacterEncoding() != null) {
         servletResponse.setCharacterEncoding(context.getResponseCharacterEncoding());
      }

   }

   protected Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   public Object getAttribute(String name) {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getAttribute(name);
      }
   }

   public Enumeration getAttributeNames() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return (Enumeration)(System.getSecurityManager() != null ? (Enumeration)AccessController.doPrivileged(new GetAttributePrivilegedAction()) : new Enumerator(this.request.getAttributeNames()));
      }
   }

   public String getCharacterEncoding() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return System.getSecurityManager() != null ? (String)AccessController.doPrivileged(new GetCharacterEncodingPrivilegedAction()) : this.request.getCharacterEncoding();
      }
   }

   public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         this.request.setCharacterEncoding(env);
      }
   }

   public int getContentLength() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getContentLength();
      }
   }

   public long getContentLengthLong() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getContentLengthLong();
      }
   }

   public String getContentType() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getContentType();
      }
   }

   public ServletInputStream getInputStream() throws IOException {
      if (this.usingReader) {
         throw new IllegalStateException("Illegal attempt to call getInputStream() after getReader() has already been called.");
      } else {
         this.usingInputStream = true;
         return this.inputStream;
      }
   }

   void recycle() {
      this.request = null;
      this.servletResponse = null;
      this.reader = null;
      this.inputStream.recycle();
      this.usingInputStream = false;
      this.usingReader = false;
      this.upgrade = false;
      this.httpUpgradeHandler = null;
      if (this.asyncContext != null) {
         this.asyncContext.clear();
         this.asyncContext = null;
      }

      this.isAsyncSupported = true;
      this.asyncStarted.set(false);
      this.isAsyncComplete = false;
      this.asyncStartedThread = null;
   }

   public String getParameter(String name) {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return System.getSecurityManager() != null ? (String)AccessController.doPrivileged(new GetParameterPrivilegedAction(name)) : this.request.getParameter(name);
      }
   }

   public Enumeration getParameterNames() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return System.getSecurityManager() != null ? new Enumerator((Collection)AccessController.doPrivileged(new GetParameterNamesPrivilegedAction())) : new Enumerator(this.request.getParameterNames());
      }
   }

   public String[] getParameterValues(String name) {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         String[] ret;
         if (System.getSecurityManager() != null) {
            ret = (String[])AccessController.doPrivileged(new GetParameterValuePrivilegedAction(name));
            if (ret != null) {
               ret = (String[])ret.clone();
            }
         } else {
            ret = this.request.getParameterValues(name);
         }

         return ret;
      }
   }

   public Map getParameterMap() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return System.getSecurityManager() != null ? (Map)AccessController.doPrivileged(new GetParameterMapPrivilegedAction()) : this.request.getParameterMap();
      }
   }

   public String getProtocol() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getProtocol().getProtocolString();
      }
   }

   public String getScheme() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getScheme();
      }
   }

   public String getServerName() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getServerName();
      }
   }

   public int getServerPort() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getServerPort();
      }
   }

   public BufferedReader getReader() throws IOException {
      if (this.usingInputStream) {
         throw new IllegalStateException("Illegal attempt to call getReader() after getInputStream() has already been called.");
      } else {
         this.usingReader = true;
         if (this.reader == null) {
            this.reader = new ServletReaderImpl(this.request.getReader());
         }

         return this.reader;
      }
   }

   public String getRemoteAddr() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getRemoteAddr();
      }
   }

   public String getRemoteHost() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getRemoteHost();
      }
   }

   public void setAttribute(String name, Object value) {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         Object oldValue = this.request.getAttribute(name);
         this.request.setAttribute(name, value);
         EventListener[] listeners = this.contextImpl.getEventListeners();
         if (listeners.length != 0) {
            ServletRequestAttributeEvent event = null;
            int i = 0;

            for(int len = listeners.length; i < len; ++i) {
               if (listeners[i] instanceof ServletRequestAttributeListener) {
                  ServletRequestAttributeListener listener = (ServletRequestAttributeListener)listeners[i];

                  try {
                     if (event == null) {
                        if (oldValue != null) {
                           event = new ServletRequestAttributeEvent(this.contextImpl, this, name, oldValue);
                        } else {
                           event = new ServletRequestAttributeEvent(this.contextImpl, this, name, value);
                        }
                     }

                     if (oldValue != null) {
                        listener.attributeReplaced(event);
                     } else {
                        listener.attributeAdded(event);
                     }
                  } catch (Throwable var10) {
                     if (LOGGER.isLoggable(Level.WARNING)) {
                        LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_ADD_ERROR("ServletRequestAttributeListener", listener.getClass().getName()), var10);
                     }
                  }
               }
            }

         }
      }
   }

   public void removeAttribute(String name) {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         Object value = this.request.getAttribute(name);
         this.request.removeAttribute(name);
         EventListener[] listeners = this.contextImpl.getEventListeners();
         if (listeners.length != 0) {
            ServletRequestAttributeEvent event = null;
            int i = 0;

            for(int len = listeners.length; i < len; ++i) {
               if (listeners[i] instanceof ServletRequestAttributeListener) {
                  ServletRequestAttributeListener listener = (ServletRequestAttributeListener)listeners[i];

                  try {
                     if (event == null) {
                        event = new ServletRequestAttributeEvent(this.contextImpl, this, name, value);
                     }

                     listener.attributeRemoved(event);
                  } catch (Throwable var9) {
                     LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_REMOVE_ERROR("ServletRequestAttributeListener", listener.getClass().getName()), var9);
                  }
               }
            }

         }
      }
   }

   public Locale getLocale() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return System.getSecurityManager() != null ? (Locale)AccessController.doPrivileged(new GetLocalePrivilegedAction()) : this.request.getLocale();
      }
   }

   public Enumeration getLocales() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return (Enumeration)(System.getSecurityManager() != null ? (Enumeration)AccessController.doPrivileged(new GetLocalesPrivilegedAction()) : new Enumerator(this.request.getLocales()));
      }
   }

   public boolean isSecure() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.isSecure();
      }
   }

   public RequestDispatcher getRequestDispatcher(String path) {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return System.getSecurityManager() != null ? (RequestDispatcher)AccessController.doPrivileged(new GetRequestDispatcherPrivilegedAction(path)) : this.getRequestDispatcherInternal(path);
      }
   }

   private RequestDispatcher getRequestDispatcherInternal(String path) {
      if (this.contextImpl == null) {
         return null;
      } else if (path == null) {
         return null;
      } else if (path.startsWith("/")) {
         return this.contextImpl.getRequestDispatcher(path);
      } else {
         String servletPath = (String)this.getAttribute("javax.servlet.include.servlet_path");
         if (servletPath == null) {
            servletPath = this.getServletPath();
         }

         String pathInfo = this.getPathInfo();
         String requestPath;
         if (pathInfo == null) {
            requestPath = servletPath;
         } else {
            requestPath = servletPath + pathInfo;
         }

         int pos = requestPath.lastIndexOf(47);
         String relative;
         if (pos >= 0) {
            relative = requestPath.substring(0, pos + 1) + path;
         } else {
            relative = requestPath + path;
         }

         return this.contextImpl.getRequestDispatcher(relative);
      }
   }

   public String getRealPath(String path) {
      return this.contextImpl.getRealPath(path);
   }

   public String getAuthType() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getAuthType();
      }
   }

   public Cookie[] getGrizzlyCookies() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         Cookie[] ret;
         if (System.getSecurityManager() != null) {
            ret = (Cookie[])AccessController.doPrivileged(new GetCookiesPrivilegedAction());
            if (ret != null) {
               ret = (Cookie[])ret.clone();
            }
         } else {
            ret = this.request.getCookies();
         }

         return ret;
      }
   }

   public long getDateHeader(String name) {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getDateHeader(name);
      }
   }

   public String getHeader(String name) {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getHeader(name);
      }
   }

   public Enumeration getHeaders(String name) {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return (Enumeration)(System.getSecurityManager() != null ? (Enumeration)AccessController.doPrivileged(new GetHeadersPrivilegedAction(name)) : new Enumerator(this.request.getHeaders(name).iterator()));
      }
   }

   public Enumeration getHeaderNames() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return (Enumeration)(System.getSecurityManager() != null ? (Enumeration)AccessController.doPrivileged(new GetHeaderNamesPrivilegedAction()) : new Enumerator(this.request.getHeaderNames().iterator()));
      }
   }

   public int getIntHeader(String name) {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getIntHeader(name);
      }
   }

   public String getMethod() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getMethod().getMethodString();
      }
   }

   public String getPathInfo() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.pathInfo;
      }
   }

   public String getPathTranslated() {
      return this.getPathInfo() == null ? null : this.contextImpl.getRealPath(this.getPathInfo());
   }

   public String getContextPath() {
      return this.contextPath;
   }

   protected void setContextPath(String contextPath) {
      if (contextPath == null) {
         this.contextPath = "";
      } else {
         this.contextPath = contextPath;
      }

   }

   public String getQueryString() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getQueryString();
      }
   }

   public String getRemoteUser() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getRemoteUser();
      }
   }

   public boolean isUserInRole(String role) {
      throw new IllegalStateException("Not yet implemented");
   }

   public Principal getUserPrincipal() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getUserPrincipal();
      }
   }

   public String getRequestedSessionId() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getRequestedSessionId();
      }
   }

   public String getRequestURI() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getRequestURI();
      }
   }

   public StringBuffer getRequestURL() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return Request.appendRequestURL(this.request, new StringBuffer());
      }
   }

   public String getServletPath() {
      return this.servletPath;
   }

   public HttpSession getSession(boolean create) {
      if (this.httpSession != null && this.httpSession.isValid()) {
         return this.httpSession;
      } else {
         this.httpSession = null;
         Session internalSession = this.request.getSession(create);
         if (internalSession == null) {
            return null;
         } else {
            internalSession.setSessionTimeout(TimeUnit.MILLISECONDS.convert((long)this.contextImpl.sessionTimeoutInSeconds, TimeUnit.SECONDS));
            this.httpSession = new HttpSessionImpl(this.contextImpl, internalSession);
            if (this.httpSession.isNew()) {
               this.httpSession.notifyNew();
            }

            return this.httpSession;
         }
      }
   }

   public HttpSession getSession() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.getSession(true);
      }
   }

   public String changeSessionId() {
      String oldSessionId = this.request.changeSessionId();
      this.getSession(false);
      this.httpSession.notifyIdChanged(oldSessionId);
      return oldSessionId;
   }

   public boolean isRequestedSessionIdValid() {
      return this.request.isRequestedSessionIdValid();
   }

   public boolean isRequestedSessionIdFromCookie() {
      return this.request.isRequestedSessionIdFromCookie();
   }

   public boolean isRequestedSessionIdFromURL() {
      return this.request.isRequestedSessionIdFromURL();
   }

   public boolean isRequestedSessionIdFromUrl() {
      return this.isRequestedSessionIdFromURL();
   }

   public javax.servlet.http.Cookie[] getCookies() {
      Cookie[] internalCookies = this.request.getCookies();
      if (internalCookies == null) {
         return null;
      } else {
         int cookieIdx = 0;
         javax.servlet.http.Cookie[] cookies = new javax.servlet.http.Cookie[internalCookies.length];
         Cookie[] var4 = internalCookies;
         int var5 = internalCookies.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Cookie cookie = var4[var6];
            if (cookie instanceof CookieWrapper) {
               cookies[cookieIdx++] = ((CookieWrapper)cookie).getWrappedCookie();
            } else {
               try {
                  javax.servlet.http.Cookie currentCookie = new javax.servlet.http.Cookie(cookie.getName(), cookie.getValue());
                  currentCookie.setComment(cookie.getComment());
                  if (cookie.getDomain() != null) {
                     currentCookie.setDomain(cookie.getDomain());
                  }

                  currentCookie.setMaxAge(cookie.getMaxAge());
                  currentCookie.setPath(cookie.getPath());
                  currentCookie.setSecure(cookie.isSecure());
                  currentCookie.setVersion(cookie.getVersion());
                  cookies[cookieIdx++] = currentCookie;
               } catch (IllegalArgumentException var9) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVLET_COOKIE_CREATE_ERROR(cookie.getName(), var9.getLocalizedMessage()));
                  }
               }
            }
         }

         return cookieIdx == cookies.length ? cookies : (javax.servlet.http.Cookie[])Arrays.copyOf(cookies, cookieIdx);
      }
   }

   public int getRemotePort() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getRemotePort();
      }
   }

   public String getLocalName() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getLocalName();
      }
   }

   public String getLocalAddr() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getLocalAddr();
      }
   }

   public int getLocalPort() {
      if (this.request == null) {
         throw new IllegalStateException("Null request object");
      } else {
         return this.request.getLocalPort();
      }
   }

   protected WebappContext getContextImpl() {
      return this.contextImpl;
   }

   protected void setContextImpl(WebappContext contextImpl) {
      this.contextImpl = contextImpl;
   }

   public void setServletPath(String servletPath) {
      if (servletPath != null) {
         if (servletPath.length() == 0) {
            this.servletPath = "";
         } else {
            this.servletPath = servletPath;
         }
      }

   }

   protected void setPathInfo(String pathInfo) {
      this.pathInfo = pathInfo;
   }

   public Request getRequest() {
      return this.request;
   }

   public ServletContext getServletContext() {
      return this.contextImpl;
   }

   public Request getInternalRequest() {
      return this.request;
   }

   public DispatcherType getDispatcherType() {
      DispatcherType dispatcher = (DispatcherType)this.getAttribute("org.apache.catalina.core.DISPATCHER_TYPE");
      if (dispatcher == null) {
         dispatcher = DispatcherType.REQUEST;
      }

      return dispatcher;
   }

   public AsyncContext startAsync() throws IllegalStateException {
      return this.startAsync(this, this.servletResponse);
   }

   public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
      return this.startAsync(servletRequest, servletResponse, false);
   }

   private AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse, boolean isStartAsyncWithZeroArg) throws IllegalStateException {
      if (servletRequest != null && servletResponse != null) {
         if (!this.isAsyncSupported()) {
            throw new IllegalStateException("Request is within the scope of a filter or servlet that does not support asynchronous operations");
         } else {
            AsyncContextImpl asyncContextLocal = this.asyncContext;
            if (asyncContextLocal != null) {
               if (this.isAsyncStarted()) {
                  throw new IllegalStateException("ServletRequest.startAsync called again without any asynchronous dispatch, or called outside the scope of any such dispatch, or called again within the scope of the same dispatch");
               }

               if (this.isAsyncComplete) {
                  throw new IllegalStateException("Response already closed");
               }

               if (!asyncContextLocal.isStartAsyncInScope()) {
                  throw new IllegalStateException("ServletRequest.startAsync called outside the scope of an async dispatch");
               }

               asyncContextLocal.reinitialize(servletRequest, servletResponse, isStartAsyncWithZeroArg);
            } else {
               final AsyncContextImpl asyncContextFinal = new AsyncContextImpl(this, servletRequest, servletResponse, isStartAsyncWithZeroArg);
               this.asyncContext = asyncContextFinal;
               CompletionHandler requestCompletionHandler = new EmptyCompletionHandler() {
                  public void completed(Response response) {
                     asyncContextFinal.notifyAsyncListeners(AsyncContextImpl.AsyncEventType.COMPLETE, (Throwable)null);
                  }
               };
               TimeoutHandler timeoutHandler = new TimeoutHandler() {
                  public boolean onTimeout(Response response) {
                     return HttpServletRequestImpl.this.processTimeout();
                  }
               };
               this.request.getResponse().suspend(-1L, TimeUnit.MILLISECONDS, requestCompletionHandler, timeoutHandler);
               this.asyncStartedThread = Thread.currentThread();
            }

            this.getInternalRequest().getContext().suspend();
            this.asyncStarted.set(true);
            return this.asyncContext;
         }
      } else {
         throw new IllegalArgumentException("Null request or response");
      }
   }

   public boolean isAsyncStarted() {
      return this.asyncStarted.get();
   }

   void setAsyncStarted(boolean asyncStarted) {
      this.asyncStarted.set(asyncStarted);
   }

   public void disableAsyncSupport() {
      this.isAsyncSupported = false;
   }

   void setAsyncTimeout(long timeout) {
      this.request.getResponse().getSuspendContext().setTimeout(timeout, TimeUnit.MILLISECONDS);
   }

   public boolean isAsyncSupported() {
      return this.isAsyncSupported;
   }

   public AsyncContext getAsyncContext() {
      if (!this.isAsyncStarted()) {
         throw new IllegalStateException("The request has not been put into asynchronous mode, must call ServletRequest.startAsync first");
      } else {
         return this.asyncContext;
      }
   }

   void asyncComplete() {
      if (this.isAsyncComplete) {
         throw new IllegalStateException("Request already released from asynchronous mode");
      } else {
         this.isAsyncComplete = true;
         this.asyncStarted.set(false);
         if (this.asyncStartedThread == Thread.currentThread() && this.asyncContext.isOkToConfigure()) {
            Response.SuspendedContextImpl suspendContext = (Response.SuspendedContextImpl)this.request.getResponse().getSuspendContext();
            suspendContext.markResumed();
            suspendContext.getSuspendStatus().reset();
         } else {
            this.request.getResponse().resume();
         }

      }
   }

   void asyncTimeout() {
      if (this.asyncContext != null) {
         this.asyncContext.notifyAsyncListeners(AsyncContextImpl.AsyncEventType.TIMEOUT, (Throwable)null);
      }

      this.errorDispatchAndComplete((Throwable)null);
   }

   void onAfterService() throws IOException {
      if (this.asyncContext != null) {
         this.asyncContext.setOkToConfigure(false);
         if (this.asyncStarted.get()) {
            this.request.getResponse().getSuspendContext().setTimeout(this.asyncContext.getTimeout(), TimeUnit.MILLISECONDS);
         }
      } else if (this.isUpgrade()) {
         if (this.httpUpgradeHandler != null) {
            WebConnection wc = HttpServletRequestImpl.WebConnectionFactory.create(this, this.getInputStream(), this.servletResponse.getOutputStream());
            this.httpUpgradeHandler.init(wc);
         } else {
            LOGGER.log(Level.SEVERE, "HttpUpgradeHandler handler cannot be null");
         }
      }

   }

   private boolean processTimeout() {
      AsyncContextImpl asyncContextLocal = this.asyncContext;

      boolean result;
      try {
         this.asyncTimeout();
      } finally {
         result = asyncContextLocal != null && !asyncContextLocal.getAndResetDispatchInScope();
      }

      return result;
   }

   void errorDispatchAndComplete(Throwable t) {
      if (this.asyncContext != null && !this.asyncContext.isDispatchInScope() && !this.isAsyncComplete && this.isAsyncStarted()) {
         this.servletResponse.setStatus(500);
         if (t != null) {
            this.setAttribute("javax.servlet.error.exception", t);
         }

         if (!this.isAsyncComplete && this.isAsyncStarted()) {
            this.asyncComplete();
         }
      }

   }

   public HttpUpgradeHandler upgrade(Class handlerClass) throws IOException {
      this.upgrade = true;

      HttpUpgradeHandler handler;
      try {
         handler = this.contextImpl.createHttpUpgradeHandlerInstance(handlerClass);
      } catch (Exception var4) {
         if (var4 instanceof IOException) {
            throw (IOException)var4;
         }

         throw new IOException(var4);
      }

      this.httpUpgradeHandler = handler;
      this.request.getResponse().suspend();
      return handler;
   }

   public boolean isUpgrade() {
      return this.upgrade;
   }

   public HttpUpgradeHandler getHttpUpgradeHandler() {
      return this.httpUpgradeHandler;
   }

   public boolean authenticate(HttpServletResponse hsr) throws IOException, ServletException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void login(String string, String string1) throws ServletException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void logout() throws ServletException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Collection getParts() throws IOException, ServletException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public Part getPart(String string) throws IOException, ServletException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public HttpServletMapping getHttpServletMapping() {
      if (this.httpServletMapping == null) {
         this.httpServletMapping = new Mapping(this.request);
      }

      return this.httpServletMapping;
   }

   public PushBuilder newPushBuilder() {
      if (this.request.isPushEnabled()) {
         final org.glassfish.grizzly.http.server.http2.PushBuilder pushBuilder = this.request.newPushBuilder();
         return new PushBuilder() {
            public PushBuilder method(String method) {
               pushBuilder.method(method);
               return this;
            }

            public PushBuilder queryString(String queryString) {
               pushBuilder.queryString(queryString);
               return this;
            }

            public PushBuilder sessionId(String sessionId) {
               pushBuilder.sessionId(sessionId);
               return this;
            }

            public PushBuilder setHeader(String name, String value) {
               pushBuilder.setHeader(name, value);
               return this;
            }

            public PushBuilder addHeader(String name, String value) {
               pushBuilder.addHeader(name, value);
               return this;
            }

            public PushBuilder removeHeader(String name) {
               pushBuilder.removeHeader(name);
               return this;
            }

            public PushBuilder path(String path) {
               pushBuilder.path(path);
               return this;
            }

            public void push() {
               pushBuilder.push();
            }

            public String getMethod() {
               return pushBuilder.getMethod();
            }

            public String getQueryString() {
               return pushBuilder.getQueryString();
            }

            public String getSessionId() {
               return pushBuilder.getSessionId();
            }

            public Set getHeaderNames() {
               Iterable i = pushBuilder.getHeaderNames();
               Set names = new HashSet();
               Iterator var3 = i.iterator();

               while(var3.hasNext()) {
                  String s = (String)var3.next();
                  names.add(s);
               }

               return names;
            }

            public String getHeader(String name) {
               return pushBuilder.getHeader(name);
            }

            public String getPath() {
               return pushBuilder.getPath();
            }
         };
      } else {
         return null;
      }
   }

   public Map getTrailerFields() {
      return this.request.getTrailers();
   }

   public boolean isTrailerFieldsReady() {
      return this.request.areTrailersAvailable();
   }

   private static final class Mapping implements HttpServletMapping {
      private String matchValue;
      private String pattern;
      private String servletName;
      private MappingMatch mappingMatch;

      private Mapping(Request request) {
         MappingData data = request.obtainMappingData();
         if (data == null) {
            throw new NullPointerException("No MappingData available.");
         } else {
            this.matchValue = data.matchedPath != null && data.matchedPath.length() >= 2 ? data.matchedPath.substring(1) : "";
            this.pattern = data.descriptorPath != null ? data.descriptorPath : "";
            this.servletName = data.servletName != null ? data.servletName : "";
            int i;
            switch (data.mappingType) {
               case 1:
                  this.mappingMatch = MappingMatch.CONTEXT_ROOT;
                  break;
               case 2:
                  this.mappingMatch = MappingMatch.DEFAULT;
                  break;
               case 4:
                  this.mappingMatch = MappingMatch.EXACT;
                  break;
               case 8:
                  this.mappingMatch = MappingMatch.EXTENSION;
                  if (this.pattern.charAt(0) == '*') {
                     i = this.matchValue.indexOf(this.pattern.substring(1));
                     if (-1 != i) {
                        this.matchValue = this.matchValue.substring(0, i);
                     }
                  }
                  break;
               case 16:
                  this.mappingMatch = MappingMatch.PATH;
                  i = this.pattern.length();
                  if (i > 0 && this.pattern.charAt(i - 1) == '*') {
                     int indexOfPatternStart = i - 2;
                     int matchValueLen = this.matchValue.length();
                     if (0 <= indexOfPatternStart && indexOfPatternStart < matchValueLen) {
                        this.matchValue = this.matchValue.substring(indexOfPatternStart);
                     }
                  }
            }

         }
      }

      public String getMatchValue() {
         return this.matchValue;
      }

      public String getPattern() {
         return this.pattern;
      }

      public String getServletName() {
         return this.servletName;
      }

      public MappingMatch getMappingMatch() {
         return this.mappingMatch;
      }

      public String toString() {
         return "Mapping{matchValue='" + this.matchValue + '\'' + ", pattern='" + this.pattern + '\'' + ", servletName='" + this.servletName + '\'' + ", mappingMatch=" + this.mappingMatch + '}';
      }

      // $FF: synthetic method
      Mapping(Request x0, Object x1) {
         this(x0);
      }
   }

   static class WebConnectionFactory {
      static WebConnection create(HttpServletRequestImpl req, ServletInputStream inputStream, ServletOutputStream outputStream) {
         return new WebConnectionImpl(req, inputStream, outputStream);
      }
   }

   private final class GetLocalesPrivilegedAction implements PrivilegedAction {
      private GetLocalesPrivilegedAction() {
      }

      public Enumeration run() {
         return new Enumerator(HttpServletRequestImpl.this.request.getLocales());
      }

      // $FF: synthetic method
      GetLocalesPrivilegedAction(Object x1) {
         this();
      }
   }

   private final class GetLocalePrivilegedAction implements PrivilegedAction {
      private GetLocalePrivilegedAction() {
      }

      public Locale run() {
         return HttpServletRequestImpl.this.request.getLocale();
      }

      // $FF: synthetic method
      GetLocalePrivilegedAction(Object x1) {
         this();
      }
   }

   private final class GetHeaderNamesPrivilegedAction implements PrivilegedAction {
      private GetHeaderNamesPrivilegedAction() {
      }

      public Enumeration run() {
         return new Enumerator(HttpServletRequestImpl.this.request.getHeaderNames());
      }

      // $FF: synthetic method
      GetHeaderNamesPrivilegedAction(Object x1) {
         this();
      }
   }

   private final class GetHeadersPrivilegedAction implements PrivilegedAction {
      private final String name;

      public GetHeadersPrivilegedAction(String name) {
         this.name = name;
      }

      public Enumeration run() {
         return new Enumerator(HttpServletRequestImpl.this.request.getHeaders(this.name));
      }
   }

   private final class GetCharacterEncodingPrivilegedAction implements PrivilegedAction {
      private GetCharacterEncodingPrivilegedAction() {
      }

      public String run() {
         return HttpServletRequestImpl.this.request.getCharacterEncoding();
      }

      // $FF: synthetic method
      GetCharacterEncodingPrivilegedAction(Object x1) {
         this();
      }
   }

   private final class GetCookiesPrivilegedAction implements PrivilegedAction {
      private GetCookiesPrivilegedAction() {
      }

      public Cookie[] run() {
         return HttpServletRequestImpl.this.request.getCookies();
      }

      // $FF: synthetic method
      GetCookiesPrivilegedAction(Object x1) {
         this();
      }
   }

   private final class GetParameterValuePrivilegedAction implements PrivilegedAction {
      public final String name;

      public GetParameterValuePrivilegedAction(String name) {
         this.name = name;
      }

      public String[] run() {
         return HttpServletRequestImpl.this.request.getParameterValues(this.name);
      }
   }

   private final class GetParameterNamesPrivilegedAction implements PrivilegedAction {
      private GetParameterNamesPrivilegedAction() {
      }

      public Set run() {
         return HttpServletRequestImpl.this.request.getParameterNames();
      }

      // $FF: synthetic method
      GetParameterNamesPrivilegedAction(Object x1) {
         this();
      }
   }

   private final class GetParameterPrivilegedAction implements PrivilegedAction {
      public final String name;

      public GetParameterPrivilegedAction(String name) {
         this.name = name;
      }

      public String run() {
         return HttpServletRequestImpl.this.request.getParameter(this.name);
      }
   }

   private final class GetRequestDispatcherPrivilegedAction implements PrivilegedAction {
      private final String path;

      public GetRequestDispatcherPrivilegedAction(String path) {
         this.path = path;
      }

      public RequestDispatcher run() {
         return HttpServletRequestImpl.this.getRequestDispatcherInternal(this.path);
      }
   }

   private final class GetParameterMapPrivilegedAction implements PrivilegedAction {
      private GetParameterMapPrivilegedAction() {
      }

      public Map run() {
         return HttpServletRequestImpl.this.request.getParameterMap();
      }

      // $FF: synthetic method
      GetParameterMapPrivilegedAction(Object x1) {
         this();
      }
   }

   private final class GetAttributePrivilegedAction implements PrivilegedAction {
      private GetAttributePrivilegedAction() {
      }

      public Enumeration run() {
         return new Enumerator(HttpServletRequestImpl.this.request.getAttributeNames());
      }

      // $FF: synthetic method
      GetAttributePrivilegedAction(Object x1) {
         this();
      }
   }
}
