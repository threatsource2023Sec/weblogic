package weblogic.security.service;

import com.bea.common.engine.InvalidParameterException;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.RoleDeploymentService;
import weblogic.management.security.RealmMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.internal.ForceDDOnly;
import weblogic.security.spi.Resource;

class WLSRoleDeploymentServiceWrapper implements RoleDeploymentService {
   private LoggerSpi logger;
   private RoleDeploymentService baseService;
   private RealmMBean realmMBean;

   public WLSRoleDeploymentServiceWrapper(RoleDeploymentService baseService, LoggerService loggerService, RealmMBean realmMBean) {
      this.logger = loggerService.getLogger("SecurityAtz");
      this.baseService = baseService;
      this.realmMBean = realmMBean;
   }

   public RoleDeploymentService.DeploymentHandler startDeployRoles(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
      if (null == appInfo) {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      } else {
         return new DeploymentHandlerImpl(appInfo);
      }
   }

   public void deleteApplicationRoles(SecurityApplicationInfo appInfo) throws RoleRemovalException {
      if (null == appInfo) {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      } else {
         boolean debug = this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".deleteApplicationRoles" : null;
         if (debug) {
            this.logger.debug(method);
         }

         if (!this.isDeployRoleIgnored(appInfo)) {
            this.baseService.deleteApplicationRoles(appInfo);
         } else if (debug) {
            this.logger.debug(method + " did not delete application roles.");
         }

      }
   }

   private boolean isDeployRoleIgnored(SecurityApplicationInfo appInfo) {
      boolean ignore = false;
      if (!ForceDDOnly.isForceDDOnly()) {
         String deployModel = appInfo.getSecurityDDModel();
         ignore = true;
         if ("DDOnly".equals(deployModel)) {
            ignore = false;
         } else if ("Advanced".equals(deployModel)) {
            ignore = this.realmMBean.isDeployRoleIgnored();
         }
      }

      return ignore;
   }

   private class DeploymentHandlerImpl implements RoleDeploymentService.DeploymentHandler {
      private RoleDeploymentService.DeploymentHandler baseHandler;

      private DeploymentHandlerImpl(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
         boolean debug = WLSRoleDeploymentServiceWrapper.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".startDeployRoles" : null;
         if (debug) {
            WLSRoleDeploymentServiceWrapper.this.logger.debug(method);
         }

         if (!WLSRoleDeploymentServiceWrapper.this.isDeployRoleIgnored(appInfo)) {
            this.baseHandler = WLSRoleDeploymentServiceWrapper.this.baseService.startDeployRoles(appInfo);
         } else {
            this.baseHandler = null;
            if (debug) {
               WLSRoleDeploymentServiceWrapper.this.logger.debug(method + " did not start deploy roles.");
            }
         }

      }

      public void deployRole(Resource resource, String roleName, String[] userAndGroupNames) throws RoleCreationException {
         boolean debug = WLSRoleDeploymentServiceWrapper.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".deployRole" : null;
         if (debug) {
            WLSRoleDeploymentServiceWrapper.this.logger.debug(method);
         }

         if (this.baseHandler != null) {
            this.baseHandler.deployRole(resource, roleName, userAndGroupNames);
         } else {
            if (roleName != null && resource != null) {
               SecurityLogger.logIgnoredDeployRole(roleName, resource.toString());
            }

            if (debug) {
               WLSRoleDeploymentServiceWrapper.this.logger.debug(method + " did not deploy role.");
            }
         }

      }

      public void endDeployRoles() throws RoleCreationException {
         boolean debug = WLSRoleDeploymentServiceWrapper.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".endDeployRoles" : null;
         if (debug) {
            WLSRoleDeploymentServiceWrapper.this.logger.debug(method);
         }

         if (this.baseHandler != null) {
            this.baseHandler.endDeployRoles();
         } else if (debug) {
            WLSRoleDeploymentServiceWrapper.this.logger.debug(method + " did not end deploy roles.");
         }

      }

      public void undeployAllRoles() throws RoleRemovalException {
         boolean debug = WLSRoleDeploymentServiceWrapper.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".undeployAllRoles" : null;
         if (debug) {
            WLSRoleDeploymentServiceWrapper.this.logger.debug(method);
         }

         if (this.baseHandler != null) {
            this.baseHandler.undeployAllRoles();
         } else if (debug) {
            WLSRoleDeploymentServiceWrapper.this.logger.debug(method + " did not undeploy all roles.");
         }

      }

      // $FF: synthetic method
      DeploymentHandlerImpl(SecurityApplicationInfo x1, Object x2) throws DeployHandleCreationException {
         this(x1);
      }
   }
}
