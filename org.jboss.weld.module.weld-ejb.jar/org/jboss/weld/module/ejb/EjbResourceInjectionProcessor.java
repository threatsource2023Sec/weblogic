package org.jboss.weld.module.ejb;

import javax.ejb.EJB;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.injection.ResourceInjectionProcessor;
import org.jboss.weld.injection.spi.EjbInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

class EjbResourceInjectionProcessor extends ResourceInjectionProcessor {
   protected ResourceReferenceFactory getResourceReferenceFactory(InjectionPoint injectionPoint, EjbInjectionServices injectionServices, Object processorContext) {
      return (ResourceReferenceFactory)Reflections.cast(injectionServices.registerEjbInjectionPoint(injectionPoint));
   }

   protected Class getMarkerAnnotation(Object processorContext) {
      return EJB.class;
   }

   protected Object getProcessorContext(BeanManagerImpl manager) {
      return null;
   }

   protected EjbInjectionServices getInjectionServices(BeanManagerImpl manager) {
      return (EjbInjectionServices)manager.getServices().get(EjbInjectionServices.class);
   }
}
