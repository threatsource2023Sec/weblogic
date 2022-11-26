package com.oracle.injection.provider.weld;

import com.oracle.injection.ejb.EjbInstanceManager;
import com.oracle.injection.ejb.EjbType;
import com.oracle.injection.spi.ContainerIntegrationService;
import java.util.Collection;
import java.util.HashMap;
import org.jboss.weld.ejb.api.SessionObjectReference;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.ejb.spi.EjbServices;
import org.jboss.weld.ejb.spi.InterceptorBindings;

class WeldEjbServicesAdapter implements EjbServices {
   private final ContainerIntegrationService m_integrationService;
   private HashMap interceptorBindings = new HashMap();

   WeldEjbServicesAdapter(ContainerIntegrationService integrationService) {
      if (integrationService == null) {
         throw new IllegalArgumentException("ContainerIntegrationService cannot be null");
      } else {
         this.m_integrationService = integrationService;
      }
   }

   public SessionObjectReference resolveEjb(EjbDescriptor ejbDescriptor) {
      if (ejbDescriptor instanceof WeldEjbDescriptorAdapter) {
         WeldEjbDescriptorAdapter adapter = (WeldEjbDescriptorAdapter)ejbDescriptor;
         com.oracle.injection.ejb.EjbDescriptor genericDescriptor = adapter.getInternalDescriptor();
         EjbInstanceManager ejbInstanceManager = genericDescriptor.getEjbInstanceManager();
         if (genericDescriptor.getEjbType() == EjbType.STATEFUL_SESSION) {
            Collection localInterfaceClasses = genericDescriptor.getLocalBusinessInterfaceClasses();
            if (localInterfaceClasses.size() > 0) {
               ejbInstanceManager.getEjbInstance((Class)localInterfaceClasses.iterator().next());
            }
         }

         return new WeldSessionObjectReferenceAdapter(ejbInstanceManager);
      } else {
         return null;
      }
   }

   public void registerInterceptors(EjbDescriptor ejbDescriptor, InterceptorBindings interceptorBindings) {
      String ejbName = ejbDescriptor.getEjbName();
      this.interceptorBindings.put(ejbName, interceptorBindings);
   }

   public InterceptorBindings getInterceptorBindings(String ejbName) {
      return (InterceptorBindings)this.interceptorBindings.get(ejbName);
   }

   public void cleanup() {
   }

   private static class WeldSessionObjectReferenceAdapter implements SessionObjectReference {
      private final EjbInstanceManager m_ejbInstanceManager;

      WeldSessionObjectReferenceAdapter(EjbInstanceManager ejbInstanceManager) {
         this.m_ejbInstanceManager = ejbInstanceManager;
      }

      public Object getBusinessObject(Class businessInterfaceType) {
         return this.m_ejbInstanceManager.getEjbInstance(businessInterfaceType);
      }

      public void remove() {
         this.m_ejbInstanceManager.remove();
      }

      public boolean isRemoved() {
         return this.m_ejbInstanceManager.isRemoved();
      }
   }
}
