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
import com.bea.common.security.service.RoleDeploymentService;
import com.bea.common.security.servicecfg.RoleDeploymentServiceConfig;
import com.bea.common.security.spi.RoleDeployerProvider;
import weblogic.security.service.DeployHandleCreationException;
import weblogic.security.service.RoleCreationException;
import weblogic.security.service.RoleRemovalException;
import weblogic.security.service.SecurityApplicationInfo;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;

public class RoleDeploymentServiceImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;
   private AuditService auditService;
   private IdentityService identityService;
   private RoleDeployerProvider[] roleDeployers;
   private String[] logNames;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.RoleDeploymentService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      RoleDeploymentServiceConfig myconfig = (RoleDeploymentServiceConfig)config;
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

            String[] names = myconfig.getRoleDeployerNames();
            this.roleDeployers = new RoleDeployerProvider[names.length];
            this.logNames = new String[names.length];

            for(int i = 0; i < names.length; ++i) {
               this.roleDeployers[i] = (RoleDeployerProvider)dependentServices.getService(names[i]);
               this.logNames[i] = dependentServices.getServiceLoggingName(names[i]);
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Obtained RoleDeployer " + this.logNames[i]);
               }
            }

            return new ServiceImpl();
         }
      }
   }

   public void shutdown() {
   }

   private void writeAuditEvent(AuditSeverity severity, String eventType, Resource resource, String roleName, String[] userAndGroupNames, Exception exc) {
      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditRoleDeploymentEventImpl(severity, eventType, this.identityService.getCurrentIdentity(), resource, roleName, userAndGroupNames, exc));
      }

   }

   private void writeAuditEvent(AuditSeverity severity, String eventType, String appIdentifier, Exception exc) {
      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditRoleDeploymentEventImpl(severity, eventType, this.identityService.getCurrentIdentity(), appIdentifier, exc));
      }

   }

   private class DeploymentHandlerImpl implements RoleDeploymentService.DeploymentHandler {
      String appIdentifier;
      RoleDeployerProvider.DeploymentHandler[] deploymentHandlers;

      private DeploymentHandlerImpl(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
         boolean debug = RoleDeploymentServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".startDeployRoles" : null;
         if (debug) {
            RoleDeploymentServiceImpl.this.logger.debug(method);
         }

         this.appIdentifier = appInfo.getApplicationIdentifier();
         this.deploymentHandlers = new RoleDeployerProvider.DeploymentHandler[RoleDeploymentServiceImpl.this.roleDeployers.length];

         for(int i = 0; i < RoleDeploymentServiceImpl.this.roleDeployers.length; ++i) {
            try {
               this.deploymentHandlers[i] = RoleDeploymentServiceImpl.this.roleDeployers[i].startDeployRoles(appInfo);
            } catch (Exception var7) {
               if (debug) {
                  RoleDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var7.toString());
               }

               SecurityLogger.logDeployableRoleProviderError(RoleDeploymentServiceImpl.this.logger, RoleDeploymentServiceImpl.this.logNames[i], var7);
               RoleDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "RoleManager Start Deploy Role Audit Event", this.appIdentifier, var7);
               throw new DeployHandleCreationException(var7);
            }
         }

         if (debug) {
            RoleDeploymentServiceImpl.this.logger.debug(method + " success.");
         }

         RoleDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "RoleManager Start Deploy Role Audit Event", this.appIdentifier, (Exception)null);
      }

      public void deployRole(Resource resource, String roleName, String[] userAndGroupNames) throws RoleCreationException {
         boolean debug = RoleDeploymentServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".startDeployRoles" : null;
         if (debug) {
            RoleDeploymentServiceImpl.this.logger.debug(method);
         }

         for(int i = 0; this.deploymentHandlers != null && i < this.deploymentHandlers.length; ++i) {
            try {
               this.deploymentHandlers[i].deployRole(resource, roleName, userAndGroupNames);
            } catch (Exception var8) {
               if (debug) {
                  RoleDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var8.toString());
               }

               SecurityLogger.logDeployableRoleProviderError(RoleDeploymentServiceImpl.this.logger, RoleDeploymentServiceImpl.this.logNames[i], var8);
               RoleDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "RoleManager Deploy Audit Event", resource, roleName, userAndGroupNames, var8);
               throw new RoleCreationException(var8);
            }
         }

         if (this.deploymentHandlers != null) {
            if (debug) {
               RoleDeploymentServiceImpl.this.logger.debug(method + " success.");
            }

            RoleDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "RoleManager Deploy Audit Event", resource, roleName, userAndGroupNames, (Exception)null);
         }

      }

      public void endDeployRoles() throws RoleCreationException {
         boolean debug = RoleDeploymentServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".endDeployRoles" : null;
         if (debug) {
            RoleDeploymentServiceImpl.this.logger.debug(method);
         }

         for(int i = 0; this.deploymentHandlers != null && i < this.deploymentHandlers.length; ++i) {
            try {
               this.deploymentHandlers[i].endDeployRoles();
            } catch (Exception var5) {
               if (debug) {
                  RoleDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var5.toString());
               }

               SecurityLogger.logDeployableRoleProviderError(RoleDeploymentServiceImpl.this.logger, RoleDeploymentServiceImpl.this.logNames[i], var5);
               RoleDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "RoleManager End Deploy Role Audit Event", this.appIdentifier, var5);
               throw new RoleCreationException(var5);
            }
         }

         if (this.deploymentHandlers != null) {
            if (debug) {
               RoleDeploymentServiceImpl.this.logger.debug(method + " success.");
            }

            RoleDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "RoleManager End Deploy Role Audit Event", this.appIdentifier, (Exception)null);
         }

      }

      public void undeployAllRoles() throws RoleRemovalException {
         boolean debug = RoleDeploymentServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".undeployAllRoles" : null;
         if (debug) {
            RoleDeploymentServiceImpl.this.logger.debug(method);
         }

         for(int i = 0; this.deploymentHandlers != null && i < this.deploymentHandlers.length; ++i) {
            try {
               this.deploymentHandlers[i].undeployAllRoles();
            } catch (Exception var5) {
               if (debug) {
                  RoleDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var5.toString());
               }

               SecurityLogger.logDeployableRoleProviderError(RoleDeploymentServiceImpl.this.logger, RoleDeploymentServiceImpl.this.logNames[i], var5);
               RoleDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, "RoleManager Undeploy Audit Event", this.appIdentifier, var5);
               throw new RoleRemovalException(var5);
            }
         }

         if (this.deploymentHandlers != null) {
            if (debug) {
               RoleDeploymentServiceImpl.this.logger.debug(method + " success.");
            }

            RoleDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.SUCCESS, "RoleManager Undeploy Audit Event", this.appIdentifier, (Exception)null);
         }

      }

      // $FF: synthetic method
      DeploymentHandlerImpl(SecurityApplicationInfo x1, Object x2) throws DeployHandleCreationException {
         this(x1);
      }
   }

   private class ServiceImpl implements RoleDeploymentService {
      private ServiceImpl() {
      }

      public RoleDeploymentService.DeploymentHandler startDeployRoles(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
         if (null == appInfo) {
            throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
         } else {
            return RoleDeploymentServiceImpl.this.new DeploymentHandlerImpl(appInfo);
         }
      }

      public void deleteApplicationRoles(SecurityApplicationInfo appInfo) throws RoleRemovalException {
         if (null == appInfo) {
            throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
         } else {
            boolean debug = RoleDeploymentServiceImpl.this.logger.isDebugEnabled();
            String method = debug ? this.getClass().getName() + ".deleteApplicationRoles" : null;
            if (debug) {
               RoleDeploymentServiceImpl.this.logger.debug(method);
            }

            for(int i = 0; i < RoleDeploymentServiceImpl.this.roleDeployers.length; ++i) {
               try {
                  RoleDeploymentServiceImpl.this.roleDeployers[i].deleteApplicationRoles(appInfo);
               } catch (Exception var6) {
                  if (debug) {
                     RoleDeploymentServiceImpl.this.logger.debug(method + " got an exception: " + var6.toString());
                  }

                  SecurityLogger.logDeployableRoleProviderError(RoleDeploymentServiceImpl.this.logger, RoleDeploymentServiceImpl.this.logNames[i], var6);
                  RoleDeploymentServiceImpl.this.writeAuditEvent(AuditSeverity.FAILURE, " RoleManager Delete Application Roles Audit Event", appInfo.getApplicationIdentifier(), var6);
                  throw new RoleRemovalException(var6);
               }
            }

            if (debug) {
               RoleDeploymentServiceImpl.this.logger.debug(method + " success.");
            }

         }
      }

      // $FF: synthetic method
      ServiceImpl(Object x1) {
         this();
      }
   }
}
