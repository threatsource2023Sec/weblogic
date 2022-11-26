package com.bea.common.security.internal.service;

import com.bea.common.engine.InvalidParameterException;
import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.RoleConsumerService;
import com.bea.common.security.servicecfg.RoleConsumerServiceConfig;
import com.bea.common.security.spi.RoleConsumerProvider;
import weblogic.security.service.ConsumptionException;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;
import weblogic.security.spi.RoleCollectionHandler;
import weblogic.security.spi.RoleCollectionInfo;

public class RoleConsumerServiceImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;
   private AuditService auditService;
   private IdentityService identityService;
   private RoleConsumerProvider[] roleConsumers;
   private String[] roleConsumerLogNames;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.RoleConsumerService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof RoleConsumerServiceConfig) {
         RoleConsumerServiceConfig myconfig = (RoleConsumerServiceConfig)config;
         String identName = myconfig.getIdentityServiceName();
         this.identityService = (IdentityService)dependentServices.getService(identName);
         if (this.identityService == null) {
            throw new ServiceConfigurationException(ServiceLogger.getServiceNotFound(identName, method));
         } else {
            if (debug) {
               this.logger.debug(method + " got IdentityService " + dependentServices.getServiceLoggingName(identName));
            }

            String auditServiceName = myconfig.getAuditServiceName();
            this.auditService = (AuditService)dependentServices.getService(auditServiceName);
            if (this.auditService == null) {
               throw new ServiceConfigurationException(ServiceLogger.getServiceNotFound(auditServiceName, method));
            } else {
               if (debug) {
                  this.logger.debug(method + " got AuditService " + dependentServices.getServiceLoggingName(auditServiceName));
               }

               String[] roleConsumerNames = myconfig.getRoleConsumerNames();
               this.roleConsumers = new RoleConsumerProvider[roleConsumerNames.length];
               this.roleConsumerLogNames = new String[roleConsumerNames.length];

               for(int i = 0; i < roleConsumerNames.length; ++i) {
                  this.roleConsumers[i] = (RoleConsumerProvider)dependentServices.getService(roleConsumerNames[i]);
                  this.roleConsumerLogNames[i] = dependentServices.getServiceLoggingName(roleConsumerNames[i]);
                  if (debug) {
                     this.logger.debug(method + " got RoleConsumer " + this.roleConsumerLogNames[i]);
                  }
               }

               return new ServiceImpl();
            }
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "RoleConsumerServiceConfig"));
      }
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown");
      }

   }

   private void writeAuditEvent(AuditSeverity severity, String eventType, RoleCollectionInfo info, Exception exc) {
      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditRoleConsumerEventImpl(severity, eventType, this.identityService.getCurrentIdentity(), info, exc));
      }

   }

   private void writeAuditEvent(AuditSeverity severity, String eventType, Resource resource, String roleName, String[] userAndGroupNames, Exception exc) {
      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditRoleConsumerEventImpl(severity, eventType, this.identityService.getCurrentIdentity(), resource, roleName, userAndGroupNames, exc));
      }

   }

   private static class RoleCollectionInfoImpl implements RoleCollectionInfo {
      private String name;
      private String version;
      private String timeStamp;
      private Resource[] resTypes;

      RoleCollectionInfoImpl(String name, String version, String timeStamp, Resource[] resTypes) {
         this.name = name;
         this.version = version;
         this.timeStamp = timeStamp;
         this.resTypes = resTypes;
      }

      public String getName() {
         return this.name;
      }

      public String getVersion() {
         return this.version;
      }

      public String getTimestamp() {
         return this.timeStamp;
      }

      public Resource[] getResourceTypes() {
         return this.resTypes;
      }
   }

   private class RoleCollectionHandlerImpl implements RoleConsumerService.RoleCollectionHandler {
      private RoleCollectionHandler[] handlers;

      RoleCollectionHandlerImpl(RoleCollectionHandler[] handlers) {
         this.handlers = handlers;
      }

      public void setRole(Resource resource, String roleName, String[] userAndGroupNames) throws ConsumptionException {
         boolean debug = RoleConsumerServiceImpl.this.logger.isDebugEnabled();
         if (debug) {
            RoleConsumerServiceImpl.this.logger.debug("RoleCollectionHandlerImpl.setRole");
         }

         for(int i = 0; i < this.handlers.length; ++i) {
            try {
               if (this.handlers[i] != null) {
                  this.handlers[i].setRole(resource, roleName, userAndGroupNames);
               }
            } catch (Exception var7) {
               if (debug) {
                  RoleConsumerServiceImpl.this.logger.debug("RoleCollectionHandlerImpl.setRole got an exception: " + var7, var7);
               }

               SecurityLogger.logRoleConsumerProviderError(RoleConsumerServiceImpl.this.logger, RoleConsumerServiceImpl.this.roleConsumerLogNames[i], var7);
               RoleConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.ERROR, "Role Consumer Set Role", resource, roleName, userAndGroupNames, var7);
               throw new ConsumptionException(var7);
            }
         }

         RoleConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Role Consumer Set Role", resource, roleName, userAndGroupNames, (Exception)null);
      }

      public void done() throws ConsumptionException {
         boolean debug = RoleConsumerServiceImpl.this.logger.isDebugEnabled();
         if (debug) {
            RoleConsumerServiceImpl.this.logger.debug("RoleCollectionHandlerImpl.done");
         }

         for(int i = 0; i < this.handlers.length; ++i) {
            try {
               if (this.handlers[i] != null) {
                  this.handlers[i].done();
               }
            } catch (Exception var4) {
               if (debug) {
                  RoleConsumerServiceImpl.this.logger.debug("RoleCollectionHandlerImpl.done got an exception: " + var4, var4);
               }

               SecurityLogger.logRoleConsumerProviderError(RoleConsumerServiceImpl.this.logger, RoleConsumerServiceImpl.this.roleConsumerLogNames[i], var4);
               RoleConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.ERROR, "Role Consumer Done", (RoleCollectionInfo)null, var4);
               throw new ConsumptionException(var4);
            }
         }

         RoleConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Role Consumer Done", (RoleCollectionInfo)null, (Exception)null);
      }
   }

   private class ServiceImpl implements RoleConsumerService {
      private ServiceImpl() {
      }

      public boolean isRoleConsumerAvailable() {
         return RoleConsumerServiceImpl.this.roleConsumers.length != 0;
      }

      public RoleConsumerService.RoleCollectionHandler getRoleCollectionHandler(String name, String version, String timeStamp, Resource[] resources) throws ConsumptionException {
         boolean debug = RoleConsumerServiceImpl.this.logger.isDebugEnabled();
         String method = null;
         if (debug) {
            method = this.getClass().getName() + ".getRoleCollectionHandler";
            RoleConsumerServiceImpl.this.logger.debug(method + ": " + name + " : " + version + " : " + timeStamp);
         }

         if (name != null && version != null && timeStamp != null && resources != null && resources.length != 0) {
            if (RoleConsumerServiceImpl.this.roleConsumers.length == 0) {
               throw new IllegalStateException(SecurityLogger.getConsumerNotConfigured("Role"));
            } else {
               RoleCollectionHandler[] handlers = new RoleCollectionHandler[RoleConsumerServiceImpl.this.roleConsumers.length];
               RoleCollectionInfo rci = new RoleCollectionInfoImpl(name, version, timeStamp, resources);

               int i;
               for(i = 0; i < RoleConsumerServiceImpl.this.roleConsumers.length; ++i) {
                  RoleConsumerProvider rc = RoleConsumerServiceImpl.this.roleConsumers[i];

                  try {
                     handlers[i] = rc.getRoleCollectionHandler(rci);
                  } catch (Exception var12) {
                     if (debug) {
                        RoleConsumerServiceImpl.this.logger.debug(method + " got an exception: " + var12.toString(), var12);
                     }

                     SecurityLogger.logRoleConsumerProviderError(RoleConsumerServiceImpl.this.logger, RoleConsumerServiceImpl.this.roleConsumerLogNames[i], var12);
                     RoleConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.ERROR, "Role Consumer Get Handler", rci, var12);
                     throw new ConsumptionException(var12);
                  }
               }

               RoleConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Role Consumer Get Handler", rci, (Exception)null);

               for(i = 0; i < handlers.length; ++i) {
                  if (handlers[i] != null) {
                     return RoleConsumerServiceImpl.this.new RoleCollectionHandlerImpl(handlers);
                  }
               }

               if (debug) {
                  RoleConsumerServiceImpl.this.logger.debug(method + ": No role handler");
               }

               return null;
            }
         } else {
            throw new InvalidParameterException(ServiceLogger.getNullParameterSupplied("getRoleCollectionHandler"));
         }
      }

      // $FF: synthetic method
      ServiceImpl(Object x1) {
         this();
      }
   }
}
