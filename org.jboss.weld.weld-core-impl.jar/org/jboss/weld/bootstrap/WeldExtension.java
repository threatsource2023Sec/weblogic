package org.jboss.weld.bootstrap;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import org.jboss.weld.contexts.activator.ActivateRequestContextInterceptor;
import org.jboss.weld.contexts.activator.CdiRequestContextActivatorInterceptor;
import org.jboss.weld.util.annotated.VetoedSuppressedAnnotatedType;

class WeldExtension implements Extension {
   void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event, BeanManager beanManager) {
      event.addAnnotatedType(VetoedSuppressedAnnotatedType.from(ActivateRequestContextInterceptor.class, beanManager), ActivateRequestContextInterceptor.class.getName());
      event.addAnnotatedType(VetoedSuppressedAnnotatedType.from(CdiRequestContextActivatorInterceptor.class, beanManager), CdiRequestContextActivatorInterceptor.class.getName());
   }
}
