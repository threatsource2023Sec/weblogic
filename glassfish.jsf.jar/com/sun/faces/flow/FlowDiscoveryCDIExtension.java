package com.sun.faces.flow;

import com.sun.faces.util.FacesLogger;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.faces.flow.builder.FlowDefinition;

public class FlowDiscoveryCDIExtension implements Extension {
   private static final Logger LOGGER;
   private List flowProducers = new CopyOnWriteArrayList();

   public List getFlowProducers() {
      return this.flowProducers;
   }

   void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event, BeanManager beanManager) {
      AnnotatedType flowDiscoveryHelper = beanManager.createAnnotatedType(FlowDiscoveryCDIHelper.class);
      event.addAnnotatedType(flowDiscoveryHelper);
   }

   void findFlowDefiners(@Observes ProcessProducer pp) {
      if (pp.getAnnotatedMember().isAnnotationPresent(FlowDefinition.class)) {
         this.flowProducers.add(pp.getProducer());
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Discovered Flow Producer {0}", pp.getProducer().toString());
         }
      }

   }

   static {
      LOGGER = FacesLogger.FLOW.getLogger();
   }
}
