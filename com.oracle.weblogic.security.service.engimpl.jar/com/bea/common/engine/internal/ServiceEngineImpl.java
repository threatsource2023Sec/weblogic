package com.bea.common.engine.internal;

import com.bea.common.classloader.service.ClassLoaderService;
import com.bea.common.engine.ManageableServiceLifecycleSpi;
import com.bea.common.engine.ServiceEngine;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.ServiceNotFoundException;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class ServiceEngineImpl implements ServiceEngine {
   private boolean shutdown = false;
   private ServiceEngineConfigImpl serviceEngineConfig = null;
   private LoggerService loggerService = null;
   private LoggerSpi logger = null;
   private ClassLoaderService classLoaderService = null;
   private ClassLoader serviceEngineClassLoader = null;
   private ServicesImpl publicServices = null;
   private List shutdownList = null;

   ServiceEngineImpl(ServiceEngineConfigImpl serviceEngineConfig) {
      if (serviceEngineConfig == null) {
         throw new IllegalArgumentException(EngineLogger.getServiceConfigNotFound());
      } else {
         this.serviceEngineConfig = serviceEngineConfig;
         this.loggerService = (LoggerService)serviceEngineConfig.getServiceInfo(LoggerService.SERVICE_NAME).getService();
         this.logger = this.loggerService.getLogger("com.bea.common.engine.ServiceEngine");
         if (this.logger == null) {
            throw new IllegalArgumentException(EngineLogger.getLoggerNotFound());
         } else {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("ServiceEngine intializing");
            }

            this.classLoaderService = (ClassLoaderService)serviceEngineConfig.getServiceInfo(ClassLoaderService.SERVICE_NAME).getService();
            this.serviceEngineClassLoader = this.getClass().getClassLoader();
            this.processConfiguration(serviceEngineConfig);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("ServiceEngine constructed");
            }

         }
      }
   }

   public synchronized Services getServices() {
      if (this.shutdown) {
         throw new IllegalStateException(EngineLogger.getNoAccessServiceEngineShutdown());
      } else {
         return this.publicServices;
      }
   }

   public synchronized void shutdown() {
      if (this.shutdown) {
         throw new IllegalStateException(EngineLogger.getEngineAlreadyShutdown());
      } else {
         this.shutdown = true;
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("ServiceEngine.shutdown() called");
         }

         if (this.publicServices != null && this.shutdownList != null && this.shutdownList.size() != 0) {
            Iterator e = this.shutdownList.iterator();

            while(e.hasNext()) {
               this.shutdownServiceAndDependencies((ServiceInfo)e.next());
            }

         } else {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("No services to shutdown. shutdown completed");
            }

         }
      }
   }

   private void shutdownServiceAndDependencies(ServiceInfo serviceToShutdown) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("shutdownServiceAndDependencies for " + serviceToShutdown.getServiceName());
      }

      int depCount = serviceToShutdown.getDependencyCount();
      if (depCount < 0) {
         throw new IllegalStateException(EngineLogger.getInternalConsistencyFailure("shutdownServiceAndDependencies"));
      } else {
         if (depCount == 0) {
            this.shutdown(serviceToShutdown);
         }

         String[] shutdownNameList = serviceToShutdown.getStartupList();
         if (shutdownNameList == null) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("No services to shutdown for " + serviceToShutdown.getServiceName());
            }

         } else {
            ServiceInfo currServiceToShutdown = null;

            for(int i = shutdownNameList.length - 1; i >= 0; --i) {
               currServiceToShutdown = this.serviceEngineConfig.getServiceInfo(shutdownNameList[i]);
               if (currServiceToShutdown == null) {
                  throw new IllegalStateException(EngineLogger.getServiceInfoNotFoundForShutDown(shutdownNameList[i]));
               }

               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("decrement dependency count for " + shutdownNameList[i]);
               }

               depCount = currServiceToShutdown.removeDependency();
               if (depCount == 0) {
                  this.shutdown(currServiceToShutdown);
               }
            }

         }
      }
   }

   private void shutdown(ServiceInfo serviceToShutdown) {
      ServiceLifecycleSpi lcImpl = serviceToShutdown.getLifecycleImpl();
      if (lcImpl != null) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("calling lifecycleImpl.shutdown for " + serviceToShutdown.getServiceName());
         }

         lcImpl.shutdown();
         serviceToShutdown.removeLifecycleImpl();
      }

   }

   synchronized void processConfiguration(ServiceEngineConfigImpl serviceEngineConfig) {
      if (this.shutdown) {
         throw new IllegalStateException(EngineLogger.getNoAccessServiceEngineShutdown());
      } else {
         boolean configurationIsBad = false;
         HashMap depServices = new HashMap();
         HashMap depServiceLoggingNames = new HashMap();
         this.shutdownList = new ArrayList();
         ServiceInfo[] serviceInfos = serviceEngineConfig.getServiceInfos();

         for(int i = 0; i < serviceInfos.length; ++i) {
            String serviceName = serviceInfos[i].getServiceName();
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("processing " + serviceName);
            }

            if (serviceInfos[i].isExposed()) {
               ServiceEngineManagedServiceConfigImpl serviceConfig = serviceInfos[i].getServiceEngineManagedServiceConfigImpl();
               if (serviceConfig == null) {
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("processConfiguration: public precreated service, no dependencies");
                  }

                  depServices.put(serviceName, serviceInfos[i].getService());
                  depServiceLoggingNames.put(serviceName, serviceInfos[i].getServiceLoggingName());
               } else {
                  this.preCalculateStartupOrder(serviceInfos[i], 0);
                  String[] startupNames = serviceInfos[i].getStartupList();
                  if (startupNames != null) {
                     for(int j = 0; j < startupNames.length; ++j) {
                        ServiceInfo depInfo = serviceEngineConfig.getServiceInfo(startupNames[j]);
                        depInfo.addDependency();
                     }
                  }

                  this.shutdownList.add(serviceInfos[i]);
               }
            } else if (this.logger.isDebugEnabled()) {
               this.logger.debug("service not exposed, don't calculate dependencies directly");
            }
         }

         if (configurationIsBad) {
            throw new IllegalArgumentException(EngineLogger.getConfigurationProblemsDetected());
         } else {
            if (this.logger.isDebugEnabled()) {
               this.debugServiceInfos(serviceInfos);
            }

            this.publicServices = new ServicesImpl(this, depServices, depServiceLoggingNames);
         }
      }
   }

   synchronized Object lookupService(String serviceName) throws ServiceInitializationException, ServiceNotFoundException {
      if (this.shutdown) {
         throw new IllegalStateException(EngineLogger.getNoAccessServiceEngineShutdown());
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("lookupService(" + serviceName + ")");
         }

         ServiceInfo serviceInfo = this.serviceEngineConfig.getServiceInfo(serviceName);
         if (serviceInfo == null) {
            throw new ServiceNotFoundException(EngineLogger.getServiceNotFound(serviceName));
         } else if (!serviceInfo.isExposed()) {
            throw new ServiceNotFoundException(EngineLogger.getServiceNotExposed(serviceName));
         } else {
            return this.findOrStartService(serviceInfo, 0);
         }
      }
   }

   synchronized Object lookupServiceManagementObject(String serviceName) throws ServiceInitializationException, ServiceNotFoundException {
      if (this.shutdown) {
         throw new IllegalStateException(EngineLogger.getNoAccessServiceEngineShutdown());
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("lookupService(" + serviceName + ")");
         }

         ServiceInfo serviceInfo = this.serviceEngineConfig.getServiceInfo(serviceName);
         if (serviceInfo == null) {
            throw new ServiceNotFoundException(EngineLogger.getServiceNotFound(serviceName));
         } else {
            this.findOrStartService(serviceInfo, 0);
            ServiceLifecycleSpi lc = serviceInfo.getLifecycleImpl();
            if (lc != null && lc instanceof ManageableServiceLifecycleSpi) {
               return ((ManageableServiceLifecycleSpi)lc).getManagementObject();
            } else {
               throw new ServiceNotFoundException(EngineLogger.getServiceNotManageable(serviceName));
            }
         }
      }
   }

   private Object findOrStartService(ServiceInfo serviceInfo, int level) throws ServiceInitializationException, ServiceNotFoundException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.debugIndent(level) + "findOrStartService(" + serviceInfo.getServiceName() + ")");
      }

      Object theService = serviceInfo.getService();
      if (theService != null) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.debugIndent(level) + "findOrStartService found running service");
         }

         return theService;
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.debugIndent(level) + "findOrStartService no running service found");
         }

         String[] startNames = serviceInfo.getStartupList();
         if (startNames != null) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug(this.debugIndent(level) + "findOrStartService " + serviceInfo.getServiceName() + " has " + startNames.length + " start names");
            }

            for(int i = 0; i < startNames.length; ++i) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug(this.debugIndent(level) + "findOrStartService findorstart " + startNames[i] + " before loading " + serviceInfo.getServiceName());
               }

               this.findOrStartService(this.serviceEngineConfig.getServiceInfo(startNames[i]), level + 1);
            }
         }

         ServiceEngineManagedServiceConfigImpl serviceConfig = serviceInfo.getServiceEngineManagedServiceConfigImpl();
         if (serviceConfig == null) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug(this.debugIndent(level) + "findOrStartService service " + serviceInfo.getServiceName() + " is not already running, and has no ServiceEngine config to enable loading it");
            }

            throw new ServiceNotFoundException(EngineLogger.getServiceNotFound(serviceInfo.getServiceName()));
         } else {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug(this.debugIndent(level) + "findOrStartService loading " + serviceInfo.getServiceName());
            }

            theService = this.loadService(serviceConfig, level);
            if (theService instanceof ServiceLifecycleSpi) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug(this.debugIndent(level) + "findOrStartService " + serviceInfo.getServiceName() + " has service lifecycle");
               }

               ServiceLifecycleSpi initableService = (ServiceLifecycleSpi)theService;
               String[] depNames = serviceConfig.getDependentServiceNames();
               Services depServices = null;
               ServiceInfo currServiceInfo = null;
               if (depNames != null) {
                  HashMap tempServiceLoggingNames = new HashMap();
                  HashMap tempServices = new HashMap();

                  for(int i = 0; i < depNames.length; ++i) {
                     if (this.logger.isDebugEnabled()) {
                        this.logger.debug(this.debugIndent(level) + "findOrStartService: depNames[" + i + "]: " + depNames[i]);
                     }

                     currServiceInfo = this.serviceEngineConfig.getServiceInfo(depNames[i]);
                     tempServices.put(depNames[i], currServiceInfo.getService());
                     tempServiceLoggingNames.put(depNames[i], currServiceInfo.getServiceLoggingName());
                  }

                  depServices = new ServicesImpl(tempServices, tempServiceLoggingNames);
               } else {
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug(this.debugIndent(level) + "findOrStartService: no dependencies");
                  }

                  depServices = new ServicesImpl((HashMap)null, (HashMap)null);
               }

               try {
                  theService = initableService.init(serviceConfig.getConfig(), depServices);
               } catch (RuntimeException var13) {
                  throw new ServiceInitializationException(var13);
               }

               serviceInfo.setLifecycleImpl(initableService);
               if (theService == null) {
                  throw new ServiceInitializationException(EngineLogger.getNullServiceLoaded(serviceInfo.getServiceName()));
               } else {
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug(this.debugIndent(level) + "findOrStartService Service stored for " + serviceInfo.getServiceName());
                  }

                  serviceInfo.setService(theService);
                  return theService;
               }
            } else {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug(this.debugIndent(level) + "findOrStartService " + serviceInfo.getServiceName() + " has no service lifecycle");
               }

               throw new ServiceInitializationException(EngineLogger.getServiceDoesNotImplementServiceLifecycle(serviceInfo.getServiceName()));
            }
         }
      }
   }

   private Object loadService(ServiceEngineManagedServiceConfigImpl serviceConfig, int level) throws ServiceInitializationException {
      String className = serviceConfig.getClassName();
      ClassLoader theLoader = this.lookupClassLoader(serviceConfig);
      if (theLoader == null) {
         throw new ServiceInitializationException(EngineLogger.getClassloaderNotFound(serviceConfig.getServiceName()));
      } else {
         Object serviceObject = null;

         try {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug(this.debugIndent(level) + "loading " + className + " for " + serviceConfig.getServiceName());
            }

            serviceObject = Class.forName(className, true, theLoader).newInstance();
            if (this.logger.isDebugEnabled()) {
               this.logger.debug(this.debugIndent(level) + "load succeeded");
            }

            return serviceObject;
         } catch (Exception var7) {
            throw new ServiceInitializationException(EngineLogger.getFailureLoadingService(serviceConfig.getServiceName(), className), var7);
         }
      }
   }

   private ClassLoader lookupClassLoader(ServiceEngineManagedServiceConfigImpl serviceConfig) throws IllegalArgumentException {
      String specificLoaderName = serviceConfig.getClassLoaderName();
      if (specificLoaderName != null && specificLoaderName.length() != 0) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Looking up classloader: " + specificLoaderName);
         }

         return this.classLoaderService.getClassLoader(specificLoaderName);
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Using ServiceEngine classloader");
         }

         return this.serviceEngineClassLoader;
      }
   }

   private void preCalculateStartupOrder(ServiceInfo serviceInfo, int level) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.debugIndent(level) + "preCalculateStartupOrder(" + serviceInfo.getServiceName() + ")");
      }

      if (serviceInfo.getStartupList() == null) {
         ServiceEngineManagedServiceConfigImpl serviceConfig = serviceInfo.getServiceEngineManagedServiceConfigImpl();
         String[] depNames = serviceConfig.getDependentServiceNames();
         if (depNames == null) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug(this.debugIndent(level) + "preCalculateStartupOrder: No dependent services specified");
            }

            serviceInfo.setStartupList(new String[0]);
         } else {
            List startupList = new ArrayList();

            for(int i = 0; i < depNames.length; ++i) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug(this.debugIndent(level) + "preCalculateStartupOrder: " + serviceInfo.getServiceName() + " depends on " + depNames[i]);
               }

               if (!startupList.contains(depNames[i])) {
                  ServiceInfo depInfo = this.serviceEngineConfig.getServiceInfo(depNames[i]);
                  if (depInfo == null) {
                     throw new IllegalArgumentException(EngineLogger.getDependentOnNonExistentService(serviceInfo.getServiceName(), depNames[i]));
                  }

                  if (depInfo.getServiceEngineManagedServiceConfigImpl() != null) {
                     this.preCalculateStartupOrder(depInfo, level + 1);
                     String[] depStartupList = depInfo.getStartupList();

                     for(int j = 0; j < depStartupList.length; ++j) {
                        if (!startupList.contains(depStartupList[j])) {
                           startupList.add(depStartupList[j]);
                        }
                     }

                     startupList.add(depNames[i]);
                  }
               }
            }

            serviceInfo.setStartupList((String[])((String[])startupList.toArray(new String[startupList.size()])));
         }
      }
   }

   private String debugIndent(int indent) {
      String ret = "";

      for(int i = 0; i < indent; ++i) {
         ret = ret + " ";
      }

      return ret;
   }

   private void debugServiceInfos(ServiceInfo[] serviceInfos) {
      if (this.logger.isDebugEnabled()) {
         for(int i = 0; i < serviceInfos.length; ++i) {
            serviceInfos[i].debug(this.logger);
         }

      }
   }
}
