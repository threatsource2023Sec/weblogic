package com.oracle.injection.provider.weld;

import com.oracle.injection.spi.ContainerIntegrationService;
import java.security.Principal;
import org.jboss.weld.security.spi.SecurityServices;

class WeldSecurityServicesAdapter implements SecurityServices {
   private final ContainerIntegrationService m_integrationService;

   WeldSecurityServicesAdapter(ContainerIntegrationService integrationService) {
      if (integrationService == null) {
         throw new IllegalArgumentException("ContainerIntegrationService cannot be null");
      } else {
         this.m_integrationService = integrationService;
      }
   }

   public Principal getPrincipal() {
      return this.m_integrationService.getPrincipal();
   }

   public void cleanup() {
   }
}
