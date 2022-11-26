package weblogic.security.service;

import com.bea.common.engine.InvalidParameterException;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.PolicyDeploymentService;
import weblogic.management.security.RealmMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.internal.ForceDDOnly;
import weblogic.security.spi.Resource;

class WLSPolicyDeploymentServiceWrapper implements PolicyDeploymentService {
   private LoggerSpi logger;
   private PolicyDeploymentService baseService;
   private RealmMBean realmMBean;

   public WLSPolicyDeploymentServiceWrapper(PolicyDeploymentService baseService, LoggerService loggerService, RealmMBean realmMBean) {
      this.logger = loggerService.getLogger("SecurityAtz");
      this.baseService = baseService;
      this.realmMBean = realmMBean;
   }

   public void shutdown() {
   }

   public PolicyDeploymentService.DeploymentHandler startDeployPolicies(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
      if (null == appInfo) {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      } else {
         return new DeploymentHandlerImpl(appInfo);
      }
   }

   public void deleteApplicationPolicies(SecurityApplicationInfo appInfo) throws ResourceRemovalException {
      if (null == appInfo) {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      } else {
         boolean debug = this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".deleteApplicationPolicies" : null;
         if (debug) {
            this.logger.debug(method);
         }

         if (!this.isDeployPolicyIgnored(appInfo)) {
            this.baseService.deleteApplicationPolicies(appInfo);
         } else if (debug) {
            this.logger.debug(method + " did not delete application policies.");
         }

      }
   }

   private boolean isDeployPolicyIgnored(SecurityApplicationInfo appInfo) {
      boolean ignore = false;
      if (!ForceDDOnly.isForceDDOnly()) {
         String deployModel = appInfo.getSecurityDDModel();
         if ("CustomRolesAndPolicies".equals(deployModel)) {
            ignore = true;
         } else if ("Advanced".equals(deployModel)) {
            ignore = this.realmMBean.isDeployPolicyIgnored();
         }
      }

      return ignore;
   }

   private class DeploymentHandlerImpl implements PolicyDeploymentService.DeploymentHandler {
      PolicyDeploymentService.DeploymentHandler baseHandler;

      private DeploymentHandlerImpl(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
         boolean debug = WLSPolicyDeploymentServiceWrapper.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".startDeployPolicies" : null;
         if (debug) {
            WLSPolicyDeploymentServiceWrapper.this.logger.debug(method);
         }

         if (!WLSPolicyDeploymentServiceWrapper.this.isDeployPolicyIgnored(appInfo)) {
            this.baseHandler = WLSPolicyDeploymentServiceWrapper.this.baseService.startDeployPolicies(appInfo);
         } else {
            this.baseHandler = null;
            if (debug) {
               WLSPolicyDeploymentServiceWrapper.this.logger.debug(method + " did not start deploy policies.");
            }
         }

      }

      public void deployPolicy(Resource resource, String[] roleNames) throws ResourceCreationException {
         boolean debug = WLSPolicyDeploymentServiceWrapper.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".deployPolicy" : null;
         if (debug) {
            WLSPolicyDeploymentServiceWrapper.this.logger.debug(method);
         }

         if (this.baseHandler != null) {
            this.baseHandler.deployPolicy(resource, roleNames);
         } else {
            if (resource != null) {
               SecurityLogger.logIgnoredDeployPolicy(resource.toString());
            }

            if (debug) {
               WLSPolicyDeploymentServiceWrapper.this.logger.debug(method + " did not deploy policy.");
            }
         }

      }

      public void deployUncheckedPolicy(Resource resource) throws ResourceCreationException {
         boolean debug = WLSPolicyDeploymentServiceWrapper.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".deployUncheckedPolicy" : null;
         if (debug) {
            WLSPolicyDeploymentServiceWrapper.this.logger.debug(method);
         }

         if (this.baseHandler != null) {
            this.baseHandler.deployUncheckedPolicy(resource);
         } else {
            if (resource != null) {
               SecurityLogger.logIgnoredUncheckedPolicy(resource.toString());
            }

            if (debug) {
               WLSPolicyDeploymentServiceWrapper.this.logger.debug(method + " did not deploy unchecked policy.");
            }
         }

      }

      public void deployExcludedPolicy(Resource resource) throws ResourceCreationException {
         boolean debug = WLSPolicyDeploymentServiceWrapper.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".deployExcludedPolicy" : null;
         if (debug) {
            WLSPolicyDeploymentServiceWrapper.this.logger.debug(method);
         }

         if (this.baseHandler != null) {
            this.baseHandler.deployExcludedPolicy(resource);
         } else {
            if (resource != null) {
               SecurityLogger.logIgnoredDeployPolicy(resource.toString());
            }

            if (debug) {
               WLSPolicyDeploymentServiceWrapper.this.logger.debug(method + " did not deploy excluded policy.");
            }
         }

      }

      public void endDeployPolicies() throws ResourceCreationException {
         boolean debug = WLSPolicyDeploymentServiceWrapper.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".endDeployPolicies" : null;
         if (debug) {
            WLSPolicyDeploymentServiceWrapper.this.logger.debug(method);
         }

         if (this.baseHandler != null) {
            this.baseHandler.endDeployPolicies();
         } else if (debug) {
            WLSPolicyDeploymentServiceWrapper.this.logger.debug(method + " did not end deploy policies.");
         }

      }

      public void undeployAllPolicies() throws ResourceRemovalException {
         boolean debug = WLSPolicyDeploymentServiceWrapper.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".undeployAllPolicies" : null;
         if (debug) {
            WLSPolicyDeploymentServiceWrapper.this.logger.debug(method);
         }

         if (this.baseHandler != null) {
            this.baseHandler.undeployAllPolicies();
         } else if (debug) {
            WLSPolicyDeploymentServiceWrapper.this.logger.debug(method + " did not undeploy all policies.");
         }

      }

      // $FF: synthetic method
      DeploymentHandlerImpl(SecurityApplicationInfo x1, Object x2) throws DeployHandleCreationException {
         this(x1);
      }
   }
}
