package org.glassfish.grizzly.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.DispatcherType;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.Note;
import org.glassfish.grizzly.http.server.AfterServiceListener;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.SessionManager;
import org.glassfish.grizzly.http.server.util.ClassLoaderUtil;
import org.glassfish.grizzly.http.server.util.DispatcherHelper;
import org.glassfish.grizzly.http.server.util.HtmlHelper;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.CharChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpRequestURIDecoder;
import org.glassfish.grizzly.http.util.HttpStatus;

public class ServletHandler extends HttpHandler {
   private static final Logger LOGGER = Grizzly.logger(ServletHandler.class);
   static final Note SERVLET_REQUEST_NOTE = Request.createNote(HttpServletRequestImpl.class.getName());
   static final Note SERVLET_RESPONSE_NOTE = Request.createNote(HttpServletResponseImpl.class.getName());
   static final ServletAfterServiceListener servletAfterServiceListener = new ServletAfterServiceListener();
   protected String servletClassName;
   protected Class servletClass;
   protected volatile Servlet servletInstance = null;
   private String contextPath = "";
   private final Object lock = new Object();
   private final WebappContext servletCtx;
   private final ServletConfigImpl servletConfig;
   private SessionManager sessionManager = ServletSessionManager.instance();
   protected final Map properties = new HashMap();
   protected boolean initialize = true;
   protected ClassLoader classLoader;
   protected ExpectationHandler expectationHandler;
   protected FilterChainFactory filterChainFactory;
   private List onDestroyListeners;

   protected ServletHandler(ServletConfigImpl servletConfig) {
      this.servletConfig = servletConfig;
      this.servletCtx = (WebappContext)servletConfig.getServletContext();
   }

   public void start() {
      try {
         this.configureServletEnv();
      } catch (Throwable var2) {
         LOGGER.log(Level.SEVERE, "start", var2);
      }

   }

   protected boolean sendAcknowledgment(Request request, Response response) throws IOException {
      return this.expectationHandler != null || super.sendAcknowledgment(request, response);
   }

   public void service(Request request, Response response) throws Exception {
      if (this.classLoader != null) {
         ClassLoader prevClassLoader = Thread.currentThread().getContextClassLoader();
         Thread.currentThread().setContextClassLoader(this.classLoader);

         try {
            this.doServletService(request, response);
         } finally {
            Thread.currentThread().setContextClassLoader(prevClassLoader);
         }
      } else {
         this.doServletService(request, response);
      }

   }

   protected void doServletService(Request request, Response response) {
      try {
         String uri = request.getRequestURI();
         if (this.contextPath.length() > 0 && !uri.startsWith(this.contextPath)) {
            this.customizeErrorPage(response, "Resource Not Found", 404, (Throwable)null);
            return;
         }

         HttpServletRequestImpl servletRequest = HttpServletRequestImpl.create();
         HttpServletResponseImpl servletResponse = HttpServletResponseImpl.create();
         this.setPathData(request, servletRequest);
         servletRequest.initialize(request, servletResponse, this.servletCtx);
         servletResponse.initialize(response, servletRequest);
         request.setNote(SERVLET_REQUEST_NOTE, servletRequest);
         request.setNote(SERVLET_RESPONSE_NOTE, servletResponse);
         request.addAfterServiceListener(servletAfterServiceListener);
         this.loadServlet();
         this.setDispatcherPath(request, this.getCombinedPath(servletRequest));
         String serverInfo = this.servletCtx.getServerInfo();
         if (serverInfo != null && !serverInfo.isEmpty()) {
            servletResponse.addHeader(Header.Server.toString(), serverInfo);
         }

         if (this.expectationHandler != null) {
            AckActionImpl ackAction = new AckActionImpl(response);
            this.expectationHandler.onExpectAcknowledgement(servletRequest, servletResponse, ackAction);
            if (!ackAction.isAcknowledged()) {
               ackAction.acknowledge();
            } else if (ackAction.isFailAcknowledgement()) {
               return;
            }
         }

         FilterChainInvoker filterChain = this.getFilterChain(request);
         if (filterChain != null) {
            filterChain.invokeFilterChain(servletRequest, servletResponse);
         } else {
            this.servletInstance.service(servletRequest, servletResponse);
         }

         servletRequest.onAfterService();
      } catch (Throwable var8) {
         LOGGER.log(Level.SEVERE, "service exception:", var8);
         this.customizeErrorPage(response, "Internal Error", 500, var8);
      }

   }

   protected FilterChainInvoker getFilterChain(Request request) {
      return this.filterChainFactory != null ? this.filterChainFactory.createFilterChain(request, this.servletInstance, DispatcherType.REQUEST) : null;
   }

   private void setDispatcherPath(Request request, String path) {
      request.setAttribute("org.apache.catalina.core.DISPATCHER_REQUEST_PATH", path);
   }

   private String getCombinedPath(HttpServletRequest request) {
      if (request.getServletPath() == null) {
         return null;
      } else {
         return request.getPathInfo() == null ? request.getServletPath() : request.getServletPath() + request.getPathInfo();
      }
   }

   protected void setPathData(Request from, HttpServletRequestImpl to) {
      MappingData data = from.obtainMappingData();
      to.setServletPath(data.wrapperPath.toString());
      to.setPathInfo(data.pathInfo.toString());
      to.setContextPath(data.contextPath.toString());
   }

   void doServletService(ServletRequest servletRequest, ServletResponse servletResponse, DispatcherType dispatcherType) throws IOException, ServletException {
      try {
         this.loadServlet();
         FilterChainImpl filterChain = this.filterChainFactory.createFilterChain(servletRequest, this.servletInstance, dispatcherType);
         if (filterChain != null) {
            filterChain.invokeFilterChain(servletRequest, servletResponse);
         } else {
            this.servletInstance.service(servletRequest, servletResponse);
         }

      } catch (ServletException var5) {
         LOGGER.log(Level.SEVERE, "service exception:", var5);
         throw var5;
      } catch (IOException var6) {
         LOGGER.log(Level.SEVERE, "service exception:", var6);
         throw var6;
      }
   }

   public void customizeErrorPage(Response response, String message, int errorCode, Throwable t) {
      if (!response.isCommitted()) {
         try {
            HtmlHelper.setErrorAndSendErrorPage(response.getRequest(), response, response.getErrorPageGenerator(), errorCode, message, message, t);
         } catch (IOException var6) {
         }
      }

   }

   protected void loadServlet() throws ServletException {
      if (this.servletInstance == null) {
         synchronized(this.lock) {
            if (this.servletInstance == null) {
               Servlet newServletInstance;
               if (this.servletClassName != null) {
                  newServletInstance = (Servlet)ClassLoaderUtil.load(this.servletClassName);
               } else {
                  try {
                     newServletInstance = (Servlet)this.servletClass.newInstance();
                  } catch (Exception var5) {
                     throw new RuntimeException(var5);
                  }
               }

               LOGGER.log(Level.INFO, "Loading Servlet: {0}", newServletInstance.getClass().getName());
               newServletInstance.init(this.servletConfig);
               this.servletInstance = newServletInstance;
            }
         }
      }

   }

   protected void configureServletEnv() throws ServletException {
      if (this.contextPath.length() > 0) {
         CharChunk cc = new CharChunk();
         char[] ch = this.contextPath.toCharArray();
         cc.setChars(ch, 0, ch.length);
         HttpRequestURIDecoder.normalizeChars(cc);
         this.contextPath = cc.toString();
      }

      if ("".equals(this.contextPath)) {
         this.contextPath = "";
      }

   }

   public Servlet getServletInstance() {
      return this.servletInstance;
   }

   protected void setServletInstance(Servlet servletInstance) {
      this.servletInstance = servletInstance;
   }

   protected void setServletClassName(String servletClassName) {
      this.servletClassName = servletClassName;
   }

   protected void setServletClass(Class servletClass) {
      this.servletClass = servletClass;
   }

   protected void setSessionManager(SessionManager sessionManager) {
      this.sessionManager = sessionManager;
   }

   public String getContextPath() {
      return this.contextPath;
   }

   public void setContextPath(String contextPath) {
      this.contextPath = contextPath;
   }

   public void destroy() {
      boolean var10 = false;

      try {
         var10 = true;
         if (this.classLoader != null) {
            ClassLoader prevClassLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(this.classLoader);

            try {
               super.destroy();
               if (this.servletInstance != null) {
                  this.servletInstance.destroy();
                  this.servletInstance = null;
               }
            } finally {
               Thread.currentThread().setContextClassLoader(prevClassLoader);
            }

            var10 = false;
         } else {
            super.destroy();
            var10 = false;
         }
      } finally {
         if (var10) {
            if (this.onDestroyListeners != null) {
               for(int i = 0; i < this.onDestroyListeners.size(); ++i) {
                  try {
                     ((Runnable)this.onDestroyListeners.get(i)).run();
                  } catch (Throwable var15) {
                     LOGGER.log(Level.WARNING, "onDestroyListener error", var15);
                  }
               }

               this.onDestroyListeners = null;
            }

         }
      }

      if (this.onDestroyListeners != null) {
         for(int i = 0; i < this.onDestroyListeners.size(); ++i) {
            try {
               ((Runnable)this.onDestroyListeners.get(i)).run();
            } catch (Throwable var16) {
               LOGGER.log(Level.WARNING, "onDestroyListener error", var16);
            }
         }

         this.onDestroyListeners = null;
      }

   }

   protected WebappContext getServletCtx() {
      return this.servletCtx;
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public void setClassLoader(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public ServletConfigImpl getServletConfig() {
      return this.servletConfig;
   }

   public String getName() {
      return this.servletConfig.getServletName();
   }

   public ExpectationHandler getExpectationHandler() {
      return this.expectationHandler;
   }

   public void setExpectationHandler(ExpectationHandler expectationHandler) {
      this.expectationHandler = expectationHandler;
   }

   protected void setDispatcherHelper(DispatcherHelper dispatcherHelper) {
      this.servletCtx.setDispatcherHelper(dispatcherHelper);
   }

   protected void setFilterChainFactory(FilterChainFactory filterChainFactory) {
      this.filterChainFactory = filterChainFactory;
   }

   protected String getSessionCookieName() {
      return this.servletCtx.getSessionCookieConfig().getName();
   }

   protected SessionManager getSessionManager(Request request) {
      SessionManager sm = request.getHttpFilter().getConfiguration().getSessionManager();
      return sm != null ? sm : this.sessionManager;
   }

   void addOnDestroyListener(Runnable r) {
      if (this.onDestroyListeners == null) {
         this.onDestroyListeners = new ArrayList(2);
      }

      this.onDestroyListeners.add(r);
   }

   static HttpServletRequestImpl getServletRequest(Request request) {
      return (HttpServletRequestImpl)request.getNote(SERVLET_REQUEST_NOTE);
   }

   static HttpServletResponseImpl getServletResponse(Request request) {
      return (HttpServletResponseImpl)request.getNote(SERVLET_RESPONSE_NOTE);
   }

   static final class AckActionImpl implements ExpectationHandler.AckAction {
      private boolean isAcknowledged;
      private boolean isFailAcknowledgement;
      private final Response response;

      private AckActionImpl(Response response) {
         this.response = response;
      }

      public void acknowledge() throws IOException {
         if (this.isAcknowledged) {
            throw new IllegalStateException("Already acknowledged");
         } else {
            this.isAcknowledged = true;
            this.response.setStatus(HttpStatus.CONINTUE_100);
            this.response.sendAcknowledgement();
         }
      }

      public void fail() throws IOException {
         if (this.isAcknowledged) {
            throw new IllegalStateException("Already acknowledged");
         } else {
            this.isAcknowledged = true;
            this.isFailAcknowledgement = true;
            this.response.setStatus(HttpStatus.EXPECTATION_FAILED_417);
            this.response.finish();
         }
      }

      public boolean isAcknowledged() {
         return this.isAcknowledged;
      }

      public boolean isFailAcknowledgement() {
         return this.isFailAcknowledgement;
      }

      // $FF: synthetic method
      AckActionImpl(Response x0, Object x1) {
         this(x0);
      }
   }

   static final class ServletAfterServiceListener implements AfterServiceListener {
      public void onAfterService(Request request) {
         HttpServletRequestImpl servletRequest = ServletHandler.getServletRequest(request);
         HttpServletResponseImpl servletResponse = ServletHandler.getServletResponse(request);
         if (servletRequest != null) {
            servletRequest.recycle();
            servletResponse.recycle();
         }

      }
   }
}
