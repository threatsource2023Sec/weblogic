package com.bea.common.engine.internal;

import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.logger.spi.LoggerSpi;

class ServiceInfo {
   private int depCount = 0;
   private boolean preCreatedService = false;
   private boolean exposed = false;
   private String serviceName = null;
   private String serviceLoggingName = null;
   private Object service = null;
   private ServiceEngineManagedServiceConfigImpl serviceEngineManagedServiceConfigImpl = null;
   private String[] startupList = null;
   private ServiceLifecycleSpi lifecycleImpl = null;

   ServiceInfo(EnvironmentManagedServiceConfigImpl serviceConfig) {
      this.init(serviceConfig);
      this.preCreatedService = true;
      this.service = serviceConfig.getService();
   }

   ServiceInfo(ServiceEngineManagedServiceConfigImpl serviceConfig) {
      this.init(serviceConfig);
      this.preCreatedService = false;
      this.serviceEngineManagedServiceConfigImpl = serviceConfig;
   }

   private void init(BaseServiceConfigImpl serviceConfig) {
      if (serviceConfig == null) {
         throw new IllegalArgumentException(EngineLogger.getServiceConfigNotFound());
      } else {
         this.serviceName = serviceConfig.getServiceName();
         this.serviceLoggingName = serviceConfig.getServiceLoggingName();
         this.exposed = serviceConfig.isExposedToEnvironment();
      }
   }

   boolean isExposed() {
      return this.exposed;
   }

   ServiceEngineManagedServiceConfigImpl getServiceEngineManagedServiceConfigImpl() {
      return this.serviceEngineManagedServiceConfigImpl;
   }

   Object getService() {
      return this.service;
   }

   String getServiceName() {
      return this.serviceName;
   }

   String getServiceLoggingName() {
      return this.serviceLoggingName;
   }

   void setService(Object service) {
      this.service = service;
   }

   void setLifecycleImpl(ServiceLifecycleSpi lifecycleImpl) {
      this.lifecycleImpl = lifecycleImpl;
   }

   void setStartupList(String[] startupList) {
      this.startupList = startupList;
   }

   String[] getStartupList() {
      return this.startupList;
   }

   ServiceLifecycleSpi getLifecycleImpl() {
      return this.lifecycleImpl;
   }

   void removeLifecycleImpl() {
      if (this.depCount > 0) {
         throw new IllegalStateException(EngineLogger.getDependenciesStillExist(this.serviceLoggingName, this.depCount));
      } else {
         this.lifecycleImpl = null;
      }
   }

   synchronized void addDependency() {
      ++this.depCount;
   }

   synchronized int removeDependency() {
      if (this.depCount <= 0) {
         throw new IllegalStateException(EngineLogger.getNoDependencies(this.serviceLoggingName));
      } else {
         --this.depCount;
         return this.depCount;
      }
   }

   synchronized int getDependencyCount() {
      return this.depCount;
   }

   void debug(LoggerSpi logger) {
      if (logger != null && logger.isDebugEnabled()) {
         logger.debug("ServiceInfo:");
         logger.debug("  ServiceName:          " + this.serviceName);
         logger.debug("  ServiceLoggingName:   " + this.serviceLoggingName);
         logger.debug("  Exposed:              " + this.exposed);
         logger.debug("  Precreated:           " + this.preCreatedService);
         logger.debug("  Service:              " + (this.service == null ? "not loaded" : "loaded"));
         if (this.serviceEngineManagedServiceConfigImpl != null) {
            logger.debug("  ServiceConfig:");
            logger.debug("      ClassName:        " + this.serviceEngineManagedServiceConfigImpl.getClassName());
            String loaderName = this.serviceEngineManagedServiceConfigImpl.getClassLoaderName();
            logger.debug("      ClassLoaderName:  " + (loaderName == null ? "using default" : loaderName));
         }

         logger.debug("  Dependencies:         " + this.depCount);
         if (this.startupList != null) {
            logger.debug("  startup list: ");

            for(int i = 0; i < this.startupList.length; ++i) {
               logger.debug("     [" + i + "] " + this.startupList[i]);
            }
         } else {
            logger.debug("  No startup list");
         }

      }
   }

   void lockDownConfig() {
      if (this.serviceEngineManagedServiceConfigImpl != null) {
         this.serviceEngineManagedServiceConfigImpl.lockDownConfig();
      }

   }
}
