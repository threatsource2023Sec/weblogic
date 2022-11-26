package org.jboss.weld.servlet.api.helpers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;
import org.jboss.weld.servlet.api.ServletListener;

public abstract class ForwardingServletListener implements ServletListener {
   protected abstract ServletListener delegate();

   public void contextDestroyed(ServletContextEvent sce) {
      this.delegate().contextDestroyed(sce);
   }

   public void contextInitialized(ServletContextEvent sce) {
      this.delegate().contextInitialized(sce);
   }

   public void requestDestroyed(ServletRequestEvent sre) {
      this.delegate().requestDestroyed(sre);
   }

   public void requestInitialized(ServletRequestEvent sre) {
      this.delegate().requestInitialized(sre);
   }

   public void sessionCreated(HttpSessionEvent se) {
      this.delegate().sessionCreated(se);
   }

   public void sessionDestroyed(HttpSessionEvent se) {
      this.delegate().sessionDestroyed(se);
   }
}
