package org.glassfish.soteria.servlet;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.spi.CDI;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import org.glassfish.soteria.Utils;
import org.glassfish.soteria.cdi.CdiExtension;
import org.glassfish.soteria.cdi.spi.CDIPerRequestInitializer;
import org.glassfish.soteria.cdi.spi.impl.LibertyCDIPerRequestInitializer;
import org.glassfish.soteria.mechanisms.jaspic.HttpBridgeServerAuthModule;
import org.glassfish.soteria.mechanisms.jaspic.Jaspic;

public class SamRegistrationInstaller implements ServletContainerInitializer, ServletContextListener {
   private static final Logger logger = Logger.getLogger(SamRegistrationInstaller.class.getName());

   public void onStartup(Set c, ServletContext ctx) throws ServletException {
      CDI cdi;
      try {
         cdi = CDI.current();
         if (logger.isLoggable(Level.INFO)) {
            String version = this.getClass().getPackage().getImplementationVersion();
            logger.log(Level.INFO, "Initializing Soteria {0} for context ''{1}''", new Object[]{version, ctx.getContextPath()});
         }
      } catch (IllegalStateException var6) {
         logger.log(Level.FINEST, "CDI not available for app context id: " + Jaspic.getAppContextID(ctx), var6);
         return;
      }

      CdiExtension cdiExtension = (CdiExtension)cdi.select(CdiExtension.class, new Annotation[0]).get();
      if (cdiExtension.isHttpAuthenticationMechanismFound()) {
         CDIPerRequestInitializer cdiPerRequestInitializer = null;
         if (!Utils.isEmpty(System.getProperty("wlp.server.name"))) {
            cdiPerRequestInitializer = new LibertyCDIPerRequestInitializer();
            logger.log(Level.INFO, "Running on Liberty - installing CDI request scope activator");
         }

         Jaspic.registerServerAuthModule(new HttpBridgeServerAuthModule(cdiPerRequestInitializer), ctx);
         ctx.addListener(this);
      }

   }

   public void contextInitialized(ServletContextEvent sce) {
   }

   public void contextDestroyed(ServletContextEvent sce) {
      Jaspic.deregisterServerAuthModule(sce.getServletContext());
   }
}
