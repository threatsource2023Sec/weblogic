package com.sun.faces.flow;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import com.sun.faces.util.cdi11.CDIUtil;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
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
import javax.faces.flow.FlowScoped;

public class FlowCDIExtension implements Extension {
   private Map flowScopedBeanFlowIds = new ConcurrentHashMap();
   private boolean isCdiOneOneOrGreater = false;
   private CDIUtil cdiUtil = null;
   private static final Logger LOGGER;

   public FlowCDIExtension() {
      this.isCdiOneOneOrGreater = Util.isCdiOneOneOrLater((FacesContext)null);
   }

   public void processBean(@Observes ProcessBean event) {
      FlowScoped flowScoped = (FlowScoped)event.getAnnotated().getAnnotation(FlowScoped.class);
      if (null != flowScoped) {
         FlowCDIContext.FlowBeanInfo fbi = new FlowCDIContext.FlowBeanInfo();
         fbi.definingDocumentId = flowScoped.definingDocumentId();
         fbi.id = flowScoped.value();
         this.flowScopedBeanFlowIds.put(event.getBean(), fbi);
      }

   }

   public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event, BeanManager beanManager) {
      event.addScope(FlowScoped.class, true, true);
   }

   void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager beanManager) {
      event.addContext(new FlowCDIContext(this.flowScopedBeanFlowIds));
      this.flowScopedBeanFlowIds.clear();
      if (this.isCdiOneOneOrGreater) {
         Class clazz = null;

         try {
            clazz = Class.forName("com.sun.faces.flow.FlowCDIEventFireHelperImpl");
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
      LOGGER = FacesLogger.FLOW.getLogger();
   }
}
