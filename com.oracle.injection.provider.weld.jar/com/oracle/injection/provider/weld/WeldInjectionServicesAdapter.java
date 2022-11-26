package com.oracle.injection.provider.weld;

import com.oracle.injection.InjectionArchive;
import com.oracle.injection.spi.ContainerIntegrationService;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.xml.ws.WebServiceRef;
import org.jboss.weld.injection.spi.InjectionContext;
import org.jboss.weld.injection.spi.InjectionServices;

class WeldInjectionServicesAdapter implements InjectionServices {
   static final Logger m_logger = Logger.getLogger(WeldInjectionServicesAdapter.class.getName());
   private final ContainerIntegrationService m_integrationService;
   private final InjectionArchive m_injectionArchive;

   public WeldInjectionServicesAdapter(ContainerIntegrationService integrationService, InjectionArchive injectionArchive) {
      if (integrationService == null) {
         throw new IllegalArgumentException("ContainerIntegrationService cannot be null");
      } else if (injectionArchive == null) {
         throw new IllegalArgumentException("InjectionArchive cannot be null");
      } else {
         this.m_integrationService = integrationService;
         this.m_injectionArchive = injectionArchive;
      }
   }

   public void aroundInject(InjectionContext injectionContext) {
      AnnotatedType annotatedType = injectionContext.getAnnotatedType();
      this.m_integrationService.performJavaEEInjection(annotatedType.getJavaClass(), injectionContext.getTarget(), this.m_injectionArchive);
      injectionContext.proceed();
   }

   public void registerInjectionTarget(InjectionTarget injectionTarget, AnnotatedType annotatedType) {
      Iterator var3 = annotatedType.getFields().iterator();

      while(var3.hasNext()) {
         AnnotatedField annotatedField = (AnnotatedField)var3.next();
         if (this.isResourceProducerField(annotatedField)) {
            this.m_integrationService.registerProducerField(this.m_injectionArchive, annotatedField);
         }
      }

   }

   private boolean isResourceProducerField(AnnotatedField annotatedField) {
      return annotatedField.isAnnotationPresent(Produces.class) && (annotatedField.isAnnotationPresent(Resource.class) || annotatedField.isAnnotationPresent(EJB.class) || annotatedField.isAnnotationPresent(PersistenceContext.class) || annotatedField.isAnnotationPresent(PersistenceUnit.class) || annotatedField.isAnnotationPresent(WebServiceRef.class));
   }

   public void cleanup() {
   }
}
