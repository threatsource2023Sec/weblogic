package org.jboss.weld.module.web.servlet;

import java.security.AccessController;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.security.GetContextClassLoaderAction;

public class ServletContextService implements Service {
   private final Map servletContexts = new ConcurrentHashMap();

   void contextInitialized(ServletContext context) {
      ClassLoader cl = this.getContextClassLoader();
      if (cl != null) {
         this.servletContexts.put(cl, context);
      }

   }

   public ServletContext getCurrentServletContext() {
      ClassLoader cl = this.getContextClassLoader();
      return cl == null ? null : (ServletContext)this.servletContexts.get(cl);
   }

   private ClassLoader getContextClassLoader() {
      return System.getSecurityManager() == null ? GetContextClassLoaderAction.INSTANCE.run() : (ClassLoader)AccessController.doPrivileged(GetContextClassLoaderAction.INSTANCE);
   }

   public void cleanup() {
      this.servletContexts.clear();
   }

   public String toString() {
      return "ServletContextService [" + this.servletContexts + "]";
   }
}
