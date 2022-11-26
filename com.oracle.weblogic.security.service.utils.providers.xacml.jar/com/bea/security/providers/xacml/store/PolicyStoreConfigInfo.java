package com.bea.security.providers.xacml.store;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.JAXPFactoryService;
import com.bea.common.store.bootstrap.BootStrapService;
import com.bea.common.store.service.StoreService;

public class PolicyStoreConfigInfo {
   private String domainName;
   private String realmName;
   private BootStrapService bootstrapService;
   private JAXPFactoryService jaxpService;
   private StoreService storeService;
   private LoggerSpi logger;

   public PolicyStoreConfigInfo(String domainName, String realmName, LoggerSpi log, JAXPFactoryService jaxpService, BootStrapService bootstrapService, StoreService storeService) {
      this.bootstrapService = bootstrapService;
      this.domainName = domainName;
      this.jaxpService = jaxpService;
      this.logger = log;
      this.realmName = realmName;
      this.storeService = storeService;
   }

   public BootStrapService getBootstrapService() {
      return this.bootstrapService;
   }

   public String getDomainName() {
      return this.domainName;
   }

   public JAXPFactoryService getJaxpService() {
      return this.jaxpService;
   }

   public LoggerSpi getLogger() {
      return this.logger;
   }

   public String getRealmName() {
      return this.realmName;
   }

   public StoreService getStoreService() {
      return this.storeService;
   }
}
