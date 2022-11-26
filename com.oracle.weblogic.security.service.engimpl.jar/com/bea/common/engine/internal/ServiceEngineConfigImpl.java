package com.bea.common.engine.internal;

import com.bea.common.classloader.service.ClassLoaderService;
import com.bea.common.engine.ServiceEngine;
import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.engine.ServiceNotFoundException;
import com.bea.common.logger.service.LoggerService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ServiceEngineConfigImpl implements ServiceEngineConfig {
   private boolean engineStarted = false;
   private HashMap serviceInfos = new HashMap();

   public void addEnvironmentManagedServiceConfig(String serviceName, Object service, boolean exposedToEnvironment) {
      this.addEnvironmentManagedServiceConfig(serviceName, service, exposedToEnvironment, serviceName);
   }

   public void addEnvironmentManagedServiceConfig(String serviceName, Object service, boolean exposedToEnvironment, String serviceLoggingName) {
      this.addServiceConfig(new ServiceInfo(new EnvironmentManagedServiceConfigImpl(serviceName, service, exposedToEnvironment, serviceLoggingName)));
   }

   public ServiceEngineManagedServiceConfig addServiceEngineManagedServiceConfig(String serviceName, String className, boolean exposedToEnvironment) {
      return this.addServiceEngineManagedServiceConfig(serviceName, className, exposedToEnvironment, serviceName);
   }

   public ServiceEngineManagedServiceConfig addServiceEngineManagedServiceConfig(String serviceName, String className, boolean exposedToEnvironment, String serviceLoggingName) {
      ServiceEngineManagedServiceConfigImpl cfg = new ServiceEngineManagedServiceConfigImpl(serviceName, className, exposedToEnvironment, serviceLoggingName);
      this.addServiceConfig(new ServiceInfo(cfg));
      return cfg;
   }

   public synchronized Object getEnvironmentManagedService(String serviceName) throws ServiceNotFoundException {
      ServiceInfo theInfo = this.getServiceInfo(serviceName);
      if (theInfo != null && theInfo.isExposed() && theInfo.getService() != null) {
         return theInfo.getService();
      } else {
         throw new ServiceNotFoundException(EngineLogger.getServiceNotFound(serviceName));
      }
   }

   public synchronized ServiceEngine startEngine() {
      if (this.engineStarted) {
         throw new IllegalStateException(EngineLogger.getEngineAlreadyStarted());
      } else {
         if (!this.serviceInfos.containsKey(LoggerService.SERVICE_NAME)) {
            this.addServiceConfig(new ServiceInfo(new EnvironmentManagedServiceConfigImpl(LoggerService.SERVICE_NAME, new NoopLoggerServiceImpl(), true, LoggerService.SERVICE_NAME)));
         }

         if (!this.serviceInfos.containsKey(ClassLoaderService.SERVICE_NAME)) {
            this.addServiceConfig(new ServiceInfo(new EnvironmentManagedServiceConfigImpl(ClassLoaderService.SERVICE_NAME, new DefaultClassLoaderServiceImpl(), true, ClassLoaderService.SERVICE_NAME)));
         }

         if (this.serviceInfos.size() == 0) {
            throw new IllegalArgumentException(EngineLogger.getServiceConfigNotFound());
         } else {
            this.engineStarted = true;
            Iterator infos = this.serviceInfos.values().iterator();

            while(infos.hasNext()) {
               ((ServiceInfo)infos.next()).lockDownConfig();
            }

            return new ServiceEngineImpl(this);
         }
      }
   }

   private synchronized void addServiceConfig(ServiceInfo serviceInfo) {
      if (this.engineStarted) {
         throw new IllegalStateException(EngineLogger.getConfigurationNotModifiedAfterStart());
      } else {
         String serviceName = serviceInfo.getServiceName();
         if (this.serviceInfos.containsKey(serviceName)) {
            throw new IllegalArgumentException(EngineLogger.getDuplicateServiceName(serviceName));
         } else {
            this.serviceInfos.put(serviceName, serviceInfo);
         }
      }
   }

   synchronized ServiceInfo getServiceInfo(String serviceName) {
      return (ServiceInfo)this.serviceInfos.get(serviceName);
   }

   synchronized ServiceInfo[] getServiceInfos() {
      Collection values = this.serviceInfos.values();
      return (ServiceInfo[])((ServiceInfo[])values.toArray(new ServiceInfo[values.size()]));
   }
}
