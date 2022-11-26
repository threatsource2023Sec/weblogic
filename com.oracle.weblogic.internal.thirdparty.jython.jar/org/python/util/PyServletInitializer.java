package org.python.util;

import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PyServletInitializer implements ServletContextListener {
   public void contextInitialized(ServletContextEvent evt) {
      PyServlet.init(new Properties(), evt.getServletContext());
   }

   public void contextDestroyed(ServletContextEvent evt) {
   }
}
