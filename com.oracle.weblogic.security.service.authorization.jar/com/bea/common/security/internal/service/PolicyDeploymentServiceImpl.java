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
import com.bea.common.security.service.PolicyDeploymentService;
import com.bea.common.security.servicecfg.PolicyDeploymentServiceConfig;
import com.bea.common.security.spi.PolicyDeployerProvider;
import weblogic.security.service.DeployHandleCreationException;
import weblogic.security.service.ResourceCreationException;
import weblogic.security.service.ResourceRemovalException;
import weblogic.security.service.SecurityApplicationInfo;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;

public class PolicyDeploymentServiceImpl implements ServiceLifecycleSpi {
   private static final String[] EXCLUDED = new String[]{"Excluded Policy"};
   private static final String[] UNCHECKED = new String[]{"Unchecked Policy"};
   private LoggerSpi logger;
   private AuditService auditService;
   private IdentityService identityService;
   private PolicyDeployerProvider[] PolicyDeployers;
   private String[] logNames;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.PolicyDeploymentService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      PolicyDeploymentServiceConfig myconfig = (PolicyDeploymentServiceConfig)config;
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

            String[] names = myconfig.getPolicyDeployerNames();
            this.PolicyDeployers = new PolicyDeployerProvider[names.length];
            this.logNames = new String[names.length];

            for(int i = 0; i < names.length; ++i) {
               this.PolicyDeployers[i] = (PolicyDeployerProvider)dependentServices.getService(names[i]);
               this.logNames[i] = dependentServices.getServiceLoggingName(names[i]);
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Obtained PolicyDeployer " + this.logNames[i]);
               }
            }

            return new ServiceImpl();
         }
      }
   }

   public void shutdown() {
   }

   private void writeAuditEvent(AuditSeverity severity, String eventType, Resource resource, String[] roleNames, Exception exc) {
      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditPolicyDeploymentEventImpl(severity, eventType, this.identityService.getCurrentIdentity(), resource, roleNames, exc));
      }

   }

   private void writeAuditEvent(AuditSeverity severity, String eventType, String appIdentifier, Exception exc) {
      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditPolicyDeploymentEventImpl(severity, eventType, this.identityService.getCurrentIdentity(), appIdentifier, exc));
      }

   }

   private class DeploymentHandlerImpl implements PolicyDeploymentService.DeploymentHandler {
      String appIdentifier;
      PolicyDeployerProvider.DeploymentHandler[] deploymentHandlers;

      private DeploymentHandlerImpl(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
         boolean debug = PolicyDeploymentServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".startDeployPolicies" : null;
         if (debug) {
            PolicyDeploymentServiceImpl.this.logger.debug(method);
         }

         this.appIdentifier = appInfo.getApplicationIdentifier();
         this.deploymentHandlers = new PolicyDeployerProvider.DeploymentHandler[PolicyDeploymentServiceImpl.this.PolicyDeployers.length];

         for(int i = 0; i < PolicyDeploymentServiceImpl.this.PolicyDeployers.length; ++i) {
            try {
               this.deploymentHandlers[i] = PolicyDeploymentServiceImpl.this.PolicyDeployers[i].startDeployPolicies(appInfo);
            } catch (Exception var7) {
               if (debug) {
                  PolicyDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var7.toString());
               }

               SecurityLogger.logDeployableAuthorizationProviderError(PolicyDeploymentServiceImpl.this.logger, PolicyDeploymentServiceImpl.this.logNames[i], var7);
               PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "Authorization Start Policy Deploy Audit Event", this.appIdentifier, var7);
               throw new DeployHandleCreationException(var7);
            }
         }

         if (debug) {
            PolicyDeploymentServiceImpl.this.logger.debug(method + " success.");
         }

         PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Authorization Start Policy Deploy Audit Event", this.appIdentifier, (Exception)null);
      }

      public void deployPolicy(Resource resource, String[] roleNames) throws ResourceCreationException {
         boolean debug = PolicyDeploymentServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".deployPolicy" : null;
         if (debug) {
            PolicyDeploymentServiceImpl.this.logger.debug(method);
         }

         for(int i = 0; this.deploymentHandlers != null && i < this.deploymentHandlers.length; ++i) {
            try {
               this.deploymentHandlers[i].deployPolicy(resource, roleNames);
            } catch (Exception var7) {
               if (debug) {
                  PolicyDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var7.toString());
               }

               SecurityLogger.logDeployableAuthorizationProviderError(PolicyDeploymentServiceImpl.this.logger, PolicyDeploymentServiceImpl.this.logNames[i], var7);
               PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "Authorization Policy Deploy Audit Event", resource, roleNames, var7);
               throw new ResourceCreationException(var7);
            }
         }

         if (this.deploymentHandlers != null) {
            if (debug) {
               PolicyDeploymentServiceImpl.this.logger.debug(method + " success.");
            }

            PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Authorization Policy Deploy Audit Event", resource, roleNames, (Exception)null);
         }

      }

      public void deployUncheckedPolicy(Resource resource) throws ResourceCreationException {
         boolean debug = PolicyDeploymentServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".deployUncheckedPolicy" : null;
         if (debug) {
            PolicyDeploymentServiceImpl.this.logger.debug(method);
         }

         for(int i = 0; this.deploymentHandlers != null && i < this.deploymentHandlers.length; ++i) {
            try {
               this.deploymentHandlers[i].deployUncheckedPolicy(resource);
            } catch (Exception var6) {
               if (debug) {
                  PolicyDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var6.toString());
               }

               SecurityLogger.logDeployableAuthorizationProviderError(PolicyDeploymentServiceImpl.this.logger, PolicyDeploymentServiceImpl.this.logNames[i], var6);
               PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "Authorization Policy Deploy Audit Event", resource, PolicyDeploymentServiceImpl.UNCHECKED, var6);
               throw new ResourceCreationException(var6);
            }
         }

         if (this.deploymentHandlers != null) {
            if (debug) {
               PolicyDeploymentServiceImpl.this.logger.debug(method + " success.");
            }

            PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Authorization Policy Deploy Audit Event", resource, PolicyDeploymentServiceImpl.UNCHECKED, (Exception)null);
         }

      }

      public void deployExcludedPolicy(Resource resource) throws ResourceCreationException {
         boolean debug = PolicyDeploymentServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".deployExcludedPolicy" : null;
         if (debug) {
            PolicyDeploymentServiceImpl.this.logger.debug(method);
         }

         for(int i = 0; this.deploymentHandlers != null && i < this.deploymentHandlers.length; ++i) {
            try {
               this.deploymentHandlers[i].deployExcludedPolicy(resource);
            } catch (Exception var6) {
               if (debug) {
                  PolicyDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var6.toString());
               }

               SecurityLogger.logDeployableAuthorizationProviderError(PolicyDeploymentServiceImpl.this.logger, PolicyDeploymentServiceImpl.this.logNames[i], var6);
               PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "Authorization Policy Deploy Audit Event", resource, PolicyDeploymentServiceImpl.EXCLUDED, var6);
               throw new ResourceCreationException(var6);
            }
         }

         if (this.deploymentHandlers != null) {
            if (debug) {
               PolicyDeploymentServiceImpl.this.logger.debug(method + " success.");
            }

            PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Authorization Policy Deploy Audit Event", resource, PolicyDeploymentServiceImpl.EXCLUDED, (Exception)null);
         }

      }

      public void endDeployPolicies() throws ResourceCreationException {
         boolean debug = PolicyDeploymentServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".endDeployPolicies" : null;
         if (debug) {
            PolicyDeploymentServiceImpl.this.logger.debug(method);
         }

         for(int i = 0; this.deploymentHandlers != null && i < this.deploymentHandlers.length; ++i) {
            try {
               this.deploymentHandlers[i].endDeployPolicies();
            } catch (Exception var5) {
               if (debug) {
                  PolicyDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var5.toString());
               }

               SecurityLogger.logDeployableAuthorizationProviderError(PolicyDeploymentServiceImpl.this.logger, PolicyDeploymentServiceImpl.this.logNames[i], var5);
               PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "Authorization End Policy Deploy Audit Event", this.appIdentifier, var5);
               throw new ResourceCreationException(var5);
            }
         }

         if (this.deploymentHandlers != null) {
            if (debug) {
               PolicyDeploymentServiceImpl.this.logger.debug(method + " success.");
            }

            PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Authorization End Policy Deploy Audit Event", this.appIdentifier, (Exception)null);
         }

      }

      public void undeployAllPolicies() throws ResourceRemovalException {
         boolean debug = PolicyDeploymentServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".undeployAllPolicies" : null;
         if (debug) {
            PolicyDeploymentServiceImpl.this.logger.debug(method);
         }

         for(int i = 0; this.deploymentHandlers != null && i < this.deploymentHandlers.length; ++i) {
            try {
               this.deploymentHandlers[i].undeployAllPolicies();
            } catch (Exception var5) {
               if (debug) {
                  PolicyDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var5.toString());
               }

               SecurityLogger.logDeployableAuthorizationProviderError(PolicyDeploymentServiceImpl.this.logger, PolicyDeploymentServiceImpl.this.logNames[i], var5);
               PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "Authorization Policy Undeploy Audit Event", this.appIdentifier, var5);
               throw new ResourceRemovalException(var5);
            }
         }

         if (this.deploymentHandlers != null) {
            if (debug) {
               PolicyDeploymentServiceImpl.this.logger.debug(method + " success.");
            }

            PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Authorization Policy Undeploy Audit Event", this.appIdentifier, (Exception)null);
         }

      }

      // $FF: synthetic method
      DeploymentHandlerImpl(SecurityApplicationInfo x1, Object x2) throws DeployHandleCreationException {
         this(x1);
      }
   }

   private class ServiceImpl implements PolicyDeploymentService {
      private ServiceImpl() {
      }

      public PolicyDeploymentService.DeploymentHandler startDeployPolicies(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
         if (null == appInfo) {
            throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
         } else {
            return PolicyDeploymentServiceImpl.this.new DeploymentHandlerImpl(appInfo);
         }
      }

      public void deleteApplicationPolicies(SecurityApplicationInfo appInfo) throws ResourceRemovalException {
         if (null == appInfo) {
            throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
         } else {
            boolean debug = PolicyDeploymentServiceImpl.this.logger.isDebugEnabled();
            String method = debug ? this.getClass().getName() + ".deleteApplicationPolicies" : null;
            if (debug) {
               PolicyDeploymentServiceImpl.this.logger.debug(method);
            }

            for(int i = 0; i < PolicyDeploymentServiceImpl.this.PolicyDeployers.length; ++i) {
               try {
                  PolicyDeploymentServiceImpl.this.PolicyDeployers[i].deleteApplicationPolicies(appInfo);
               } catch (Exception var6) {
                  if (debug) {
                     PolicyDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var6.toString());
                  }

                  SecurityLogger.logDeployableAuthorizationProviderError(PolicyDeploymentServiceImpl.this.logger, PolicyDeploymentServiceImpl.this.logNames[i], var6);
                  PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "Authorization Delete Application Policies Audit Event", appInfo.getApplicationIdentifier(), var6);
                  throw new ResourceRemovalException(var6);
               }
            }

            if (debug) {
               PolicyDeploymentServiceImpl.this.logger.debug(method + " success.");
            }

            PolicyDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "Authorization Delete Application Policies Audit Event", appInfo.getApplicationIdentifier(), (Exception)null);
         }
      }

      // $FF: synthetic method
      ServiceImpl(Object x1) {
         this();
      }
   }
}
