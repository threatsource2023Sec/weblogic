package com.bea.common.engine.internal;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceNotFoundException;
import com.bea.common.engine.Services;
import java.util.HashMap;

class ServicesImpl implements Services {
   private ServiceEngineImpl serviceEngine;
   private HashMap localServices;
   private HashMap localServiceLoggerNames;
   private HashMap localManagementObjects;

   ServicesImpl(ServiceEngineImpl serviceEngine, HashMap depServices, HashMap depServicesLoggingNames) {
      this.serviceEngine = null;
      this.localServices = new HashMap();
      this.localServiceLoggerNames = new HashMap();
      this.localManagementObjects = new HashMap();
      this.serviceEngine = serviceEngine;
      if (depServices != null) {
         this.localServices.putAll(depServices);
      }

      if (depServicesLoggingNames != null) {
         this.localServiceLoggerNames.putAll(depServicesLoggingNames);
      }

   }

   ServicesImpl(HashMap depServices, HashMap depServicesLoggingNames) {
      this((ServiceEngineImpl)null, depServices, depServicesLoggingNames);
   }

   public synchronized Object getService(String serviceName) throws ServiceNotFoundException, ServiceInitializationException {
      if (serviceName == null) {
         throw new IllegalArgumentException(EngineLogger.getServiceNameNotSpecified());
      } else {
         Object service = this.localServices.get(serviceName);
         if (service == null && this.serviceEngine != null) {
            service = this.serviceEngine.lookupService(serviceName);
            if (service != null) {
               this.localServices.put(serviceName, service);
            }
         }

         if (service == null) {
            throw new ServiceNotFoundException(EngineLogger.getServiceNotFound(serviceName));
         } else {
            return service;
         }
      }
   }

   public String getServiceLoggingName(String serviceName) throws ServiceNotFoundException {
      if (serviceName == null) {
         throw new IllegalArgumentException(EngineLogger.getServiceNameNotSpecified());
      } else {
         String serviceLoggingName = (String)this.localServiceLoggerNames.get(serviceName);
         if (serviceLoggingName == null) {
            throw new ServiceNotFoundException(EngineLogger.getServiceNotFound(serviceName));
         } else {
            return serviceLoggingName;
         }
      }
   }

   public Object getServiceManagementObject(String serviceName) throws ServiceNotFoundException, ServiceInitializationException {
      if (serviceName == null) {
         throw new IllegalArgumentException(EngineLogger.getServiceNameNotSpecified());
      } else {
         Object management = this.localManagementObjects.get(serviceName);
         if (management == null && this.serviceEngine != null) {
            management = this.serviceEngine.lookupServiceManagementObject(serviceName);
            if (management != null) {
               this.localManagementObjects.put(serviceName, management);
            }
         }

         if (management == null) {
            throw new ServiceNotFoundException(EngineLogger.getManageableServiceNotFound(serviceName));
         } else {
            return management;
         }
      }
   }
}
