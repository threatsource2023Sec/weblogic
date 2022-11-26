package javax.faces.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
public final class FacesServlet implements Servlet {
   public static final String CONFIG_FILES_ATTR = "javax.faces.CONFIG_FILES";
   public static final String LIFECYCLE_ID_ATTR = "javax.faces.LIFECYCLE_ID";
   public static final String DISABLE_FACESSERVLET_TO_XHTML_PARAM_NAME = "javax.faces.DISABLE_FACESSERVLET_TO_XHTML";
   private static final Logger LOGGER = Logger.getLogger("javax.faces.webapp", "javax.faces.LogStrings");
   private static final String ALLOWED_HTTP_METHODS_ATTR = "com.sun.faces.allowedHttpMethods";
   private final Set defaultAllowedHttpMethods;
   private Set allowedUnknownHttpMethods;
   private Set allowedKnownHttpMethods;
   private Set allHttpMethods;
   private boolean allowAllMethods;
   private FacesContextFactory facesContextFactory;
   private Lifecycle lifecycle;
   private ServletConfig servletConfig;
   private boolean initFacesContextReleased;

   public FacesServlet() {
      this.defaultAllowedHttpMethods = EnumSet.range(FacesServlet.HttpMethod.OPTIONS, FacesServlet.HttpMethod.CONNECT);
   }

   public void init(ServletConfig servletConfig) throws ServletException {
      this.servletConfig = servletConfig;
      this.facesContextFactory = this.acquireFacesContextFactory();
      this.lifecycle = this.acquireLifecycle();
      this.initHttpMethodValidityVerificationWithCatch();
   }

   public void service(ServletRequest req, ServletResponse resp) throws IOException, ServletException {
      HttpServletRequest request = (HttpServletRequest)req;
      HttpServletResponse response = (HttpServletResponse)resp;
      this.requestStart(request.getRequestURI());
      if (!this.isHttpMethodValid(request)) {
         response.sendError(400);
      } else {
         this.logIfThreadInterruped();
         if (!this.notProcessWebInfIfPrefixMapped(request, response)) {
            this.releaseFacesInitContextIfNeeded();
            FacesContext context = this.acquireFacesContext(request, response);

            try {
               this.executeLifecyle(context);
            } finally {
               context.release();
            }

            this.requestEnd();
         }
      }
   }

   public void destroy() {
      this.facesContextFactory = null;
      this.lifecycle = null;
      this.servletConfig = null;
      this.uninitHttpMethodValidityVerification();
   }

   public ServletConfig getServletConfig() {
      return this.servletConfig;
   }

   public String getServletInfo() {
      return this.getClass().getName();
   }

   private FacesContextFactory acquireFacesContextFactory() throws UnavailableException {
      try {
         return (FacesContextFactory)FactoryFinder.getFactory("javax.faces.context.FacesContextFactory");
      } catch (FacesException var3) {
         String msg = LOGGER.getResourceBundle().getString("severe.webapp.facesservlet.init_failed");
         LOGGER.log(Level.SEVERE, msg, (Throwable)(var3.getCause() != null ? var3.getCause() : var3));
         throw new UnavailableException(msg);
      }
   }

   private Lifecycle acquireLifecycle() throws ServletException {
      try {
         LifecycleFactory lifecycleFactory = (LifecycleFactory)FactoryFinder.getFactory("javax.faces.lifecycle.LifecycleFactory");
         String lifecycleId = this.servletConfig.getInitParameter("javax.faces.LIFECYCLE_ID");
         if (lifecycleId == null) {
            lifecycleId = this.servletConfig.getServletContext().getInitParameter("javax.faces.LIFECYCLE_ID");
         }

         if (lifecycleId == null) {
            lifecycleId = "DEFAULT";
         }

         return lifecycleFactory.getLifecycle(lifecycleId);
      } catch (FacesException var3) {
         Throwable rootCause = var3.getCause();
         if (rootCause == null) {
            throw var3;
         } else {
            throw new ServletException(var3.getMessage(), rootCause);
         }
      }
   }

   private FacesContext acquireFacesContext(HttpServletRequest request, HttpServletResponse response) {
      return this.facesContextFactory.getFacesContext(this.servletConfig.getServletContext(), request, response, this.lifecycle);
   }

   private void initHttpMethodValidityVerificationWithCatch() throws ServletException {
      try {
         this.initHttpMethodValidityVerification();
      } catch (FacesException var3) {
         Throwable rootCause = var3.getCause();
         if (rootCause == null) {
            throw var3;
         } else {
            throw new ServletException(var3.getMessage(), rootCause);
         }
      }
   }

   private void initHttpMethodValidityVerification() {
      this.allHttpMethods = EnumSet.allOf(HttpMethod.class);
      this.allowedUnknownHttpMethods = Collections.emptySet();
      this.allowedKnownHttpMethods = this.defaultAllowedHttpMethods;
      String allowedHttpMethodsString = this.servletConfig.getServletContext().getInitParameter("com.sun.faces.allowedHttpMethods");
      if (allowedHttpMethodsString != null) {
         String[] methods = allowedHttpMethodsString.split("\\s+");
         this.allowedUnknownHttpMethods = new HashSet(methods.length);
         List allowedKnownHttpMethodsStringList = new ArrayList();
         String[] var4 = methods;
         int var5 = methods.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String httpMethod = var4[var6];
            if (httpMethod.equals("*")) {
               this.allowAllMethods = true;
               this.allowedUnknownHttpMethods = Collections.emptySet();
               return;
            }

            if (!this.isKnownHttpMethod(httpMethod)) {
               this.logUnknownHttpMethod(httpMethod);
               if (!this.allowedUnknownHttpMethods.contains(httpMethod)) {
                  this.allowedUnknownHttpMethods.add(httpMethod);
               }
            } else if (!allowedKnownHttpMethodsStringList.contains(httpMethod)) {
               allowedKnownHttpMethodsStringList.add(httpMethod);
            }
         }

         this.initializeAllowedKnownHttpMethods(allowedKnownHttpMethodsStringList);
      }

   }

   private void initializeAllowedKnownHttpMethods(List allowedKnownHttpMethodsStringList) {
      if (5 == allowedKnownHttpMethodsStringList.size()) {
         this.allowedKnownHttpMethods = EnumSet.of(FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(0)), FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(1)), FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(2)), FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(3)), FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(4)));
      } else if (4 == allowedKnownHttpMethodsStringList.size()) {
         this.allowedKnownHttpMethods = EnumSet.of(FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(0)), FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(1)), FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(2)), FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(3)));
      } else if (3 == allowedKnownHttpMethodsStringList.size()) {
         this.allowedKnownHttpMethods = EnumSet.of(FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(0)), FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(1)), FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(2)));
      } else if (2 == allowedKnownHttpMethodsStringList.size()) {
         this.allowedKnownHttpMethods = EnumSet.of(FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(0)), FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(1)));
      } else if (1 == allowedKnownHttpMethodsStringList.size()) {
         this.allowedKnownHttpMethods = EnumSet.of(FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(0)));
      } else {
         List restList = new ArrayList(allowedKnownHttpMethodsStringList.size() - 1);

         for(int i = 1; i < allowedKnownHttpMethodsStringList.size() - 1; ++i) {
            restList.add(FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(i)));
         }

         HttpMethod first = FacesServlet.HttpMethod.valueOf((String)allowedKnownHttpMethodsStringList.get(0));
         HttpMethod[] rest = new HttpMethod[restList.size()];
         restList.toArray(rest);
         this.allowedKnownHttpMethods = EnumSet.of(first, rest);
      }

   }

   private void logIfThreadInterruped() {
      if (Thread.currentThread().isInterrupted() && LOGGER.isLoggable(Level.FINER)) {
         LOGGER.log(Level.FINE, "Thread {0} given to FacesServlet.service() in interrupted state", Thread.currentThread().getName());
      }

   }

   private void logUnknownHttpMethod(String httpMethod) {
      if (LOGGER.isLoggable(Level.WARNING)) {
         HttpMethod[] values = FacesServlet.HttpMethod.values();
         Object[] arg = new Object[values.length + 1];
         arg[0] = httpMethod;
         System.arraycopy(values, FacesServlet.HttpMethod.OPTIONS.ordinal(), arg, 1, values.length);
         LOGGER.log(Level.WARNING, "warning.webapp.facesservlet.init_invalid_http_method", arg);
      }

   }

   private boolean isKnownHttpMethod(String httpMethod) {
      try {
         FacesServlet.HttpMethod.valueOf(httpMethod);
         return true;
      } catch (IllegalArgumentException var3) {
         return false;
      }
   }

   private void releaseFacesInitContextIfNeeded() {
      if (!this.initFacesContextReleased) {
         FacesContext initFacesContext = FacesContext.getCurrentInstance();
         if (initFacesContext != null) {
            initFacesContext.release();
         }

         FactoryFinder.getFactory("com.sun.faces.ServletContextFacesContextFactory_Removal");
         this.initFacesContextReleased = true;
      }

   }

   private boolean notProcessWebInfIfPrefixMapped(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String pathInfo = request.getPathInfo();
      if (pathInfo != null) {
         pathInfo = pathInfo.toUpperCase();
         if (pathInfo.contains("/WEB-INF/") || pathInfo.contains("/WEB-INF") || pathInfo.contains("/META-INF/") || pathInfo.contains("/META-INF")) {
            response.sendError(404);
            return true;
         }
      }

      return false;
   }

   private void executeLifecyle(FacesContext context) throws IOException, ServletException {
      try {
         ResourceHandler handler = context.getApplication().getResourceHandler();
         if (handler.isResourceRequest(context)) {
            handler.handleResourceRequest(context);
         } else {
            this.lifecycle.attachWindow(context);
            this.lifecycle.execute(context);
            this.lifecycle.render(context);
         }

      } catch (FacesException var4) {
         Throwable t = var4.getCause();
         if (t == null) {
            throw new ServletException(var4.getMessage(), var4);
         } else if (t instanceof ServletException) {
            throw (ServletException)t;
         } else if (t instanceof IOException) {
            throw (IOException)t;
         } else {
            throw new ServletException(t.getMessage(), t);
         }
      }
   }

   private void uninitHttpMethodValidityVerification() {
      assert null != this.allowedUnknownHttpMethods;

      assert null != this.defaultAllowedHttpMethods;

      assert null != this.allHttpMethods;

      this.allowedUnknownHttpMethods.clear();
      this.allowedUnknownHttpMethods = null;
      this.allowedKnownHttpMethods.clear();
      this.allowedKnownHttpMethods = null;
      this.allHttpMethods.clear();
      this.allHttpMethods = null;
   }

   private boolean isHttpMethodValid(HttpServletRequest request) {
      boolean result = false;
      if (this.allowAllMethods) {
         result = true;
      } else {
         String requestMethodString = request.getMethod();
         HttpMethod requestMethod = null;

         boolean isKnownHttpMethod;
         try {
            requestMethod = FacesServlet.HttpMethod.valueOf(requestMethodString);
            isKnownHttpMethod = true;
         } catch (IllegalArgumentException var7) {
            isKnownHttpMethod = false;
         }

         if (isKnownHttpMethod) {
            result = this.allowedKnownHttpMethods.contains(requestMethod);
         } else {
            result = this.allowedUnknownHttpMethods.contains(requestMethodString);
         }
      }

      return result;
   }

   private void requestStart(String requestUri) {
   }

   private void requestEnd() {
   }

   private static enum HttpMethod {
      OPTIONS("OPTIONS"),
      GET("GET"),
      HEAD("HEAD"),
      POST("POST"),
      PUT("PUT"),
      DELETE("DELETE"),
      TRACE("TRACE"),
      CONNECT("CONNECT");

      private String name;

      private HttpMethod(String name) {
         this.name = name;
      }

      public String toString() {
         return this.name;
      }
   }
}
