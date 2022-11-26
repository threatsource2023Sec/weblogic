package com.sun.faces.application;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.flow.FlowDiscoveryCDIExtension;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Producer;
import javax.faces.context.FacesContext;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowHandler;
import javax.faces.flow.builder.FlowDefinition;

class JavaFlowLoaderHelper {
   private static final Logger LOGGER;

   synchronized void loadFlows(FacesContext context, FlowHandler flowHandler) throws IOException {
      BeanManager beanManager = Util.getCdiBeanManager(context);
      Bean extensionImpl = beanManager.resolve(beanManager.getBeans(FlowDiscoveryCDIExtension.class, new Annotation[0]));
      if (extensionImpl == null) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "Unable to obtain {0} from CDI implementation.  Flows described with {1} are unavailable.", new String[]{FlowDiscoveryCDIExtension.class.getName(), FlowDefinition.class.getName()});
         }

      } else {
         CreationalContext creationalContext = beanManager.createCreationalContext(extensionImpl);
         FlowDiscoveryCDIExtension myExtension = (FlowDiscoveryCDIExtension)beanManager.getReference(extensionImpl, FlowDiscoveryCDIExtension.class, creationalContext);
         List flowProducers = myExtension.getFlowProducers();
         WebConfiguration config = WebConfiguration.getInstance();
         if (!flowProducers.isEmpty()) {
            this.enableClientWindowModeIfNecessary(context);
         }

         Iterator var9 = flowProducers.iterator();

         while(var9.hasNext()) {
            Producer flowProducer = (Producer)var9.next();
            Flow toAdd = (Flow)flowProducer.produce(beanManager.createCreationalContext((Contextual)null));
            if (null == toAdd) {
               LOGGER.log(Level.SEVERE, "Flow producer method {0}() returned null.  Ignoring.", flowProducer.toString());
            } else {
               flowHandler.addFlow(context, toAdd);
               config.setHasFlows(true);
            }
         }

      }
   }

   private void enableClientWindowModeIfNecessary(FacesContext context) {
      WebConfiguration config = WebConfiguration.getInstance(context.getExternalContext());
      String optionValue = config.getOptionValue(WebConfiguration.WebContextInitParameter.ClientWindowMode);
      boolean clientWindowNeedsEnabling = false;
      if ("none".equals(optionValue)) {
         clientWindowNeedsEnabling = true;
         LOGGER.log(Level.WARNING, "{0} was set to none, but Faces Flows requires {0} is enabled.  Setting to ''url''.", new Object[]{WebConfiguration.WebContextInitParameter.ClientWindowMode.getQualifiedName()});
      } else if (optionValue == null) {
         clientWindowNeedsEnabling = true;
      }

      if (clientWindowNeedsEnabling) {
         config.setOptionValue(WebConfiguration.WebContextInitParameter.ClientWindowMode, "url");
      }

   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
