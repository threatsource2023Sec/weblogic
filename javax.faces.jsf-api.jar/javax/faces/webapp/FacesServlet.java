package javax.faces.webapp;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class FacesServlet implements Servlet {
   public static final String CONFIG_FILES_ATTR = "javax.faces.CONFIG_FILES";
   public static final String LIFECYCLE_ID_ATTR = "javax.faces.LIFECYCLE_ID";
   private static final Logger LOGGER = Logger.getLogger("javax.faces.webapp", "javax.faces.LogStrings");
   private FacesContextFactory facesContextFactory = null;
   private Lifecycle lifecycle = null;
   private ServletConfig servletConfig = null;

   public void destroy() {
      this.facesContextFactory = null;
      this.lifecycle = null;
      this.servletConfig = null;
   }

   public ServletConfig getServletConfig() {
      return this.servletConfig;
   }

   public String getServletInfo() {
      return this.getClass().getName();
   }

   public void init(ServletConfig servletConfig) throws ServletException {
      this.servletConfig = servletConfig;

      try {
         this.facesContextFactory = (FacesContextFactory)FactoryFinder.getFactory("javax.faces.context.FacesContextFactory");
      } catch (FacesException var7) {
         ResourceBundle rb = LOGGER.getResourceBundle();
         String msg = rb.getString("severe.webapp.facesservlet.init_failed");
         Throwable rootCause = var7.getCause() != null ? var7.getCause() : var7;
         LOGGER.log(Level.SEVERE, msg, (Throwable)rootCause);
         throw new UnavailableException(msg);
      }

      try {
         LifecycleFactory lifecycleFactory = (LifecycleFactory)FactoryFinder.getFactory("javax.faces.lifecycle.LifecycleFactory");
         String lifecycleId;
         if (null == (lifecycleId = servletConfig.getInitParameter("javax.faces.LIFECYCLE_ID"))) {
            lifecycleId = servletConfig.getServletContext().getInitParameter("javax.faces.LIFECYCLE_ID");
         }

         if (lifecycleId == null) {
            lifecycleId = "DEFAULT";
         }

         this.lifecycle = lifecycleFactory.getLifecycle(lifecycleId);
      } catch (FacesException var6) {
         Throwable rootCause = var6.getCause();
         if (rootCause == null) {
            throw var6;
         } else {
            throw new ServletException(var6.getMessage(), rootCause);
         }
      }
   }

   public void service(ServletRequest request, ServletResponse response) throws IOException, ServletException {
      String pathInfo = ((HttpServletRequest)request).getPathInfo();
      if (pathInfo != null) {
         pathInfo = pathInfo.toUpperCase();
         if (pathInfo.startsWith("/WEB-INF/") || pathInfo.equals("/WEB-INF") || pathInfo.startsWith("/META-INF/") || pathInfo.equals("/META-INF")) {
            ((HttpServletResponse)response).sendError(404);
            return;
         }
      }

      FacesContext context = this.facesContextFactory.getFacesContext(this.servletConfig.getServletContext(), request, response, this.lifecycle);

      try {
         this.lifecycle.execute(context);
         this.lifecycle.render(context);
      } catch (FacesException var10) {
         Throwable t = var10.getCause();
         if (t == null) {
            throw new ServletException(var10.getMessage(), var10);
         }

         if (t instanceof ServletException) {
            throw (ServletException)t;
         }

         if (t instanceof IOException) {
            throw (IOException)t;
         }

         throw new ServletException(t.getMessage(), t);
      } finally {
         context.release();
      }

   }
}
