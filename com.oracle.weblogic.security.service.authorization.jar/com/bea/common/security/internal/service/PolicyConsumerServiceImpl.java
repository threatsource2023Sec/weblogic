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
import com.bea.common.security.service.PolicyConsumerService;
import com.bea.common.security.servicecfg.PolicyConsumerServiceConfig;
import com.bea.common.security.spi.PolicyConsumerProvider;
import weblogic.security.service.ConsumptionException;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.PolicyCollectionHandler;
import weblogic.security.spi.PolicyCollectionInfo;
import weblogic.security.spi.Resource;

public class PolicyConsumerServiceImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;
   private AuditService auditService;
   private IdentityService identityService;
   private PolicyConsumerProvider[] policyConsumers;
   private String[] policyConsumerLogNames;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.PolicyConsumerService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof PolicyConsumerServiceConfig) {
         PolicyConsumerServiceConfig myconfig = (PolicyConsumerServiceConfig)config;
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

               String[] policyConsumerNames = myconfig.getPolicyConsumerNames();
               this.policyConsumers = new PolicyConsumerProvider[policyConsumerNames.length];
               this.policyConsumerLogNames = new String[policyConsumerNames.length];

               for(int i = 0; i < policyConsumerNames.length; ++i) {
                  this.policyConsumers[i] = (PolicyConsumerProvider)dependentServices.getService(policyConsumerNames[i]);
                  this.policyConsumerLogNames[i] = dependentServices.getServiceLoggingName(policyConsumerNames[i]);
                  if (debug) {
                     this.logger.debug(method + " got PolicyConsumer " + this.policyConsumerLogNames[i]);
                  }
               }

               return new ServiceImpl();
            }
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "PolicyConsumerServiceConfig"));
      }
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown");
      }

   }

   private void writeAuditEvent(AuditSeverity severity, String eventType, PolicyCollectionInfo pci, Exception exc) {
      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditPolicyConsumerEventImpl(severity, eventType, this.identityService.getCurrentIdentity(), pci, exc));
      }

   }

   private void writeAuditEvent(AuditSeverity severity, String eventType, Resource resource, String[] roleNames, Exception exc) {
      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditPolicyConsumerEventImpl(severity, eventType, this.identityService.getCurrentIdentity(), resource, roleNames, exc));
      }

   }

   private static class PolicyCollectionInfoImpl implements PolicyCollectionInfo {
      private String name;
      private String version;
      private String timeStamp;
      private Resource[] resTypes;

      PolicyCollectionInfoImpl(String name, String version, String timeStamp, Resource[] resTypes) {
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

   private class PolicyCollectionHandlerImpl implements PolicyConsumerService.PolicyCollectionHandler {
      private PolicyCollectionHandler[] handlers;

      PolicyCollectionHandlerImpl(PolicyCollectionHandler[] handlers) {
         this.handlers = handlers;
      }

      public void setPolicy(Resource resource, String[] roleNames) throws ConsumptionException {
         boolean debug = PolicyConsumerServiceImpl.this.logger.isDebugEnabled();
         if (debug) {
            PolicyConsumerServiceImpl.this.logger.debug("PolicyCollectionHandlerImpl.setPolicy");
         }

         for(int i = 0; i < this.handlers.length; ++i) {
            try {
               if (this.handlers[i] != null) {
                  this.handlers[i].setPolicy(resource, roleNames);
               }
            } catch (Exception var6) {
               if (debug) {
                  PolicyConsumerServiceImpl.this.logger.debug("PolicyCollectionHandlerImpl.setPolicy got an exception: " + var6, var6);
               }

               SecurityLogger.logPolicyConsumerProviderError(PolicyConsumerServiceImpl.this.logger, PolicyConsumerServiceImpl.this.policyConsumerLogNames[i], var6);
               PolicyConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.ERROR, "Policy Consumer Set Policy", resource, roleNames, var6);
               throw new ConsumptionException(var6);
            }
         }

         PolicyConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Policy Consumer Set Policy", resource, roleNames, (Exception)null);
      }

      public void setUncheckedPolicy(Resource resource) throws ConsumptionException {
         boolean debug = PolicyConsumerServiceImpl.this.logger.isDebugEnabled();
         if (debug) {
            PolicyConsumerServiceImpl.this.logger.debug("PolicyCollectionHandlerImpl.setUncheckedPolicy");
         }

         for(int i = 0; i < this.handlers.length; ++i) {
            try {
               if (this.handlers[i] != null) {
                  this.handlers[i].setUncheckedPolicy(resource);
               }
            } catch (Exception var5) {
               if (debug) {
                  PolicyConsumerServiceImpl.this.logger.debug("PolicyCollectionHandlerImpl.setUncheckedPolicy got an exception: " + var5, var5);
               }

               SecurityLogger.logPolicyConsumerProviderError(PolicyConsumerServiceImpl.this.logger, PolicyConsumerServiceImpl.this.policyConsumerLogNames[i], var5);
               PolicyConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.ERROR, "Policy Consumer Set Unchecked Policy", resource, (String[])null, var5);
               throw new ConsumptionException(var5);
            }
         }

         PolicyConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Policy Consumer Set Unchecked Policy", resource, (String[])null, (Exception)null);
      }

      public void done() throws ConsumptionException {
         boolean debug = PolicyConsumerServiceImpl.this.logger.isDebugEnabled();
         if (debug) {
            PolicyConsumerServiceImpl.this.logger.debug("PolicyCollectionHandlerImpl.done");
         }

         for(int i = 0; i < this.handlers.length; ++i) {
            try {
               if (this.handlers[i] != null) {
                  this.handlers[i].done();
               }
            } catch (Exception var4) {
               if (debug) {
                  PolicyConsumerServiceImpl.this.logger.debug("PolicyCollectionHandlerImpl.done got an exception: " + var4, var4);
               }

               SecurityLogger.logPolicyConsumerProviderError(PolicyConsumerServiceImpl.this.logger, PolicyConsumerServiceImpl.this.policyConsumerLogNames[i], var4);
               PolicyConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "Policy Consumer Done", (PolicyCollectionInfo)null, var4);
               throw new ConsumptionException(var4);
            }
         }

         PolicyConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Policy Consumer Done", (PolicyCollectionInfo)null, (Exception)null);
      }
   }

   private class ServiceImpl implements PolicyConsumerService {
      private ServiceImpl() {
      }

      public boolean isPolicyConsumerAvailable() {
         return PolicyConsumerServiceImpl.this.policyConsumers.length != 0;
      }

      public PolicyConsumerService.PolicyCollectionHandler getPolicyCollectionHandler(String name, String version, String timeStamp, Resource[] resources) throws ConsumptionException {
         boolean debug = PolicyConsumerServiceImpl.this.logger.isDebugEnabled();
         String method = null;
         if (debug) {
            method = this.getClass().getName() + ".getPolicyCollectionHandler";
            PolicyConsumerServiceImpl.this.logger.debug(method + ": " + name + " : " + version + " : " + timeStamp);
         }

         if (name != null && version != null && timeStamp != null && resources != null && resources.length != 0) {
            if (PolicyConsumerServiceImpl.this.policyConsumers.length == 0) {
               throw new IllegalStateException(SecurityLogger.getConsumerNotConfigured("Policy"));
            } else {
               PolicyCollectionHandler[] handlers = new PolicyCollectionHandler[PolicyConsumerServiceImpl.this.policyConsumers.length];
               PolicyCollectionInfo pci = new PolicyCollectionInfoImpl(name, version, timeStamp, resources);

               int i;
               for(i = 0; i < PolicyConsumerServiceImpl.this.policyConsumers.length; ++i) {
                  PolicyConsumerProvider pc = PolicyConsumerServiceImpl.this.policyConsumers[i];

                  try {
                     handlers[i] = pc.getPolicyCollectionHandler(pci);
                  } catch (Exception var12) {
                     if (debug) {
                        PolicyConsumerServiceImpl.this.logger.debug(method + " got an exception: " + var12.toString(), var12);
                     }

                     SecurityLogger.logPolicyConsumerProviderError(PolicyConsumerServiceImpl.this.logger, PolicyConsumerServiceImpl.this.policyConsumerLogNames[i], var12);
                     PolicyConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.ERROR, "Policy Consumer Get Handler", pci, var12);
                     throw new ConsumptionException(var12);
                  }
               }

               PolicyConsumerServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Policy Consumer Get Handler", pci, (Exception)null);

               for(i = 0; i < handlers.length; ++i) {
                  if (handlers[i] != null) {
                     return PolicyConsumerServiceImpl.this.new PolicyCollectionHandlerImpl(handlers);
                  }
               }

               if (debug) {
                  PolicyConsumerServiceImpl.this.logger.debug(method + ": No policy handler");
               }

               return null;
            }
         } else {
            throw new InvalidParameterException(ServiceLogger.getNullParameterSupplied("getPolicyCollectionHandler"));
         }
      }

      // $FF: synthetic method
      ServiceImpl(Object x1) {
         this();
      }
   }
}
