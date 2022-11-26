package com.sun.faces.application.view;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import com.sun.faces.util.cdi11.CDIUtil;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

public class ViewScopeExtension implements Extension {
   private boolean isCdiOneOneOrGreater = false;
   private CDIUtil cdiUtil = null;
   private static final Logger LOGGER;

   public ViewScopeExtension() {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("Constructor @ViewScoped CDI Extension called");
      }

      this.isCdiOneOneOrGreater = Util.isCdiOneOneOrLater((FacesContext)null);
   }

   public void processBean(@Observes ProcessBean event) {
      ViewScoped viewScoped = (ViewScoped)event.getAnnotated().getAnnotation(ViewScoped.class);
      if (viewScoped != null && LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("Processing occurrence of @ViewScoped");
      }

   }

   public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event, BeanManager beanManager) {
      event.addScope(ViewScoped.class, true, true);
   }

   public void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager beanManager) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("Adding @ViewScoped context to CDI runtime");
      }

      event.addContext(new ViewScopeContext());
      if (this.isCdiOneOneOrGreater) {
         Class clazz = null;

         try {
            clazz = Class.forName("com.sun.faces.application.view.ViewScopedCDIEventFireHelperImpl");
         } catch (ClassNotFoundException var8) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "CDI 1.1 events not enabled", var8);
            }

            return;
         }

         if (null == this.cdiUtil) {
            ServiceLoader oneCdiUtil = ServiceLoader.load(CDIUtil.class);

            CDIUtil oneAndOnly;
            for(Iterator var5 = oneCdiUtil.iterator(); var5.hasNext(); this.cdiUtil = oneAndOnly) {
               oneAndOnly = (CDIUtil)var5.next();
               if (null != this.cdiUtil) {
                  String message = "Must only have one implementation of CDIUtil available";
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, message);
                  }

                  throw new IllegalStateException(message);
               }
            }
         }

         if (null != this.cdiUtil) {
            Bean bean = this.cdiUtil.createHelperBean(beanManager, clazz);
            event.addBean(bean);
         } else if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "Unable to obtain CDI 1.1 utilities for Mojarra");
         }
      }

   }

   static {
      LOGGER = FacesLogger.APPLICATION_VIEW.getLogger();
   }
}
