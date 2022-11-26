package weblogic.servlet;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.WebServerRegistry;

public final class AsyncInitServlet implements Servlet {
   private static final boolean DEBUG = false;
   String SERVLET_CLASS_NAME = "weblogic.servlet.AsyncInitServlet.servlet-class-name";
   private Servlet delegate;
   private ServletConfig config;

   private Servlet createDelegate(ServletConfig config) throws ServletException {
      String servletClassName = config.getInitParameter(this.SERVLET_CLASS_NAME);
      if (servletClassName == null) {
         throw new ServletException("Required init-param " + this.SERVLET_CLASS_NAME + " not found");
      } else {
         try {
            Class servletClass = Class.forName(servletClassName, true, Thread.currentThread().getContextClassLoader());
            return (Servlet)servletClass.newInstance();
         } catch (ClassNotFoundException var4) {
            throw new ServletException("Can't load " + this.SERVLET_CLASS_NAME + " named " + servletClassName, var4);
         } catch (NoClassDefFoundError var5) {
            throw new ServletException("Can't load " + this.SERVLET_CLASS_NAME + " named " + servletClassName, var5);
         } catch (ClassCastException var6) {
            throw new ServletException("Servlet class " + servletClassName + " does not implement javax.servlet.Servlet");
         } catch (InstantiationException var7) {
            throw new ServletException("Exception while creating servlet " + servletClassName, var7);
         } catch (IllegalAccessException var8) {
            throw new ServletException("Servlet " + servletClassName + " does not have a public constructor", var8);
         }
      }
   }

   public void init(ServletConfig config) throws ServletException {
      this.config = config;
      if (AsyncInitServlet.DevModeSingleton.isDevMode()) {
         WebLogicServletContext wsc = (WebLogicServletContext)config.getServletContext();
         if (((WebAppServletContext)wsc).isOnDemandDisplayRefresh()) {
            this.initDelegate();
         } else {
            wsc.addAsyncInitServlet(this);
         }
      } else {
         this.initDelegate();
      }

   }

   private boolean isShuttingDown() {
      return WebServerRegistry.getInstance().getManagementProvider().isServerShuttingDown();
   }

   public void initDelegate() throws ServletException {
      if (!this.isShuttingDown()) {
         try {
            this.delegate = this.createDelegate(this.config);
            this.delegate.init(this.config);
         } catch (Throwable var2) {
            if (!this.isShuttingDown()) {
               if (var2 instanceof ServletException) {
                  throw (ServletException)var2;
               } else {
                  throw new ServletException(var2);
               }
            }
         }
      }
   }

   public ServletConfig getServletConfig() {
      return this.delegate.getServletConfig();
   }

   public void service(ServletRequest req, ServletResponse rsp) throws ServletException, IOException {
      this.delegate.service(req, rsp);
   }

   public String getServletInfo() {
      return this.delegate.getServletInfo();
   }

   public void destroy() {
      if (this.delegate != null) {
         this.delegate.destroy();
      }

   }

   private static class DevModeSingleton {
      private static DevModeSingleton SINGLETON = new DevModeSingleton();
      private final boolean isDevMode = !WebServerRegistry.getInstance().getManagementProvider().getDomainMBean().isProductionModeEnabled();

      static boolean isDevMode() {
         return SINGLETON.isDevMode;
      }
   }
}
