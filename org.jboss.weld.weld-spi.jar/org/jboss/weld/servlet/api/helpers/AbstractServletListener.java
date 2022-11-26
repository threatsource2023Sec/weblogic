package org.jboss.weld.servlet.api.helpers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;
import org.jboss.weld.servlet.api.ServletListener;

public class AbstractServletListener implements ServletListener {
   public void contextDestroyed(ServletContextEvent sce) {
   }

   public void contextInitialized(ServletContextEvent sce) {
   }

   public void requestDestroyed(ServletRequestEvent sre) {
   }

   public void requestInitialized(ServletRequestEvent sre) {
   }

   public void sessionCreated(HttpSessionEvent se) {
   }

   public void sessionDestroyed(HttpSessionEvent se) {
   }
}
