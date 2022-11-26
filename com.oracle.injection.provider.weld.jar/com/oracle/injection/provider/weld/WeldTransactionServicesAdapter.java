package com.oracle.injection.provider.weld;

import com.oracle.injection.spi.ContainerIntegrationService;
import javax.transaction.Synchronization;
import javax.transaction.UserTransaction;
import org.jboss.weld.transaction.spi.TransactionServices;

class WeldTransactionServicesAdapter implements TransactionServices {
   private final ContainerIntegrationService m_integrationService;

   WeldTransactionServicesAdapter(ContainerIntegrationService integrationService) {
      this.m_integrationService = integrationService;
   }

   public void registerSynchronization(Synchronization synchronization) {
      this.m_integrationService.registerSynchronization(synchronization);
   }

   public boolean isTransactionActive() {
      return this.m_integrationService.isTransactionActive();
   }

   public UserTransaction getUserTransaction() {
      return this.m_integrationService.getUserTransaction();
   }

   public void cleanup() {
   }
}
