package weblogic.security.service;

import com.bea.common.engine.InvalidParameterException;
import com.bea.common.security.service.RoleDeploymentService;
import com.bea.common.security.service.RoleMappingService;
import com.bea.security.css.CSS;
import java.security.AccessController;
import java.util.Map;
import weblogic.management.security.ProviderMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.internal.ApplicationVersioningService;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.Resource;
import weblogic.security.utils.SecurityUtils;

public class RoleManagerImpl implements SecurityService, RoleManager {
   private RealmServices realmServices = null;
   private ApplicationVersioningService appVerService = null;
   private RoleMappingService roleMappingService = null;
   private RoleDeploymentService roleDeploymentService = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean initialized;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityRoleMap");

   private void assertNotUsingCommon() {
      throw new AssertionError("This code should never be called when using common security");
   }

   public RoleManagerImpl() {
   }

   public RoleManagerImpl(RealmServices realmServices, ProviderMBean[] configuration) {
      this.initialize(realmServices, configuration);
   }

   public void initialize(RealmServices realmServices, ProviderMBean[] configuration) {
      if (null == realmServices) {
         throw new InvalidParameterException(SecurityLogger.getValidRealmNameMustBeSpecifed());
      } else {
         this.realmServices = realmServices;
         if (configuration != null && configuration.length != 0) {
            if (log.isDebugEnabled()) {
               log.debug("RoleManager initializing for realm: " + realmServices.getRealmName());
            }

            if (log.isDebugEnabled()) {
               log.debug("RoleManager will use common security");
            }

            try {
               CSS css = realmServices.getCSS();
               this.roleMappingService = (RoleMappingService)css.getService("RoleMappingService");
               this.roleDeploymentService = (RoleDeploymentService)css.getService("RoleDeploymentService");
               this.appVerService = (ApplicationVersioningService)css.getService("ApplicationVersioningService");
            } catch (Exception var5) {
               if (log.isDebugEnabled()) {
                  SecurityLogger.logStackTrace(var5);
               }

               SecurityServiceRuntimeException ssre = new SecurityServiceRuntimeException(SecurityLogger.getExceptionObtainingService("Common RoleMappingService", var5.toString()));
               ssre.initCause(var5);
               throw ssre;
            }

            this.initialized = true;
         } else {
            throw new InvalidParameterException(SecurityLogger.getNoProviderMBeans());
         }
      }
   }

   public void start() {
   }

   public void suspend() {
   }

   public void shutdown() {
      this.roleMappingService = null;
      this.roleDeploymentService = null;
      this.realmServices = null;
   }

   public Map getRoles(AuthenticatedSubject subject, Resource resource, ContextHandler handler) {
      if (!this.initialized) {
         throw new NotYetInitializedException(SecurityLogger.getRoleMgrNotYetInitialized());
      } else if (null != subject && null != resource) {
         Map result = null;
         if (log.isDebugEnabled()) {
            log.debug("Using Common RoleMappingService");
         }

         result = this.roleMappingService.getRoles(IdentityUtility.authenticatedSubjectToIdentity(subject), resource, handler);
         return result;
      } else {
         throw new InvalidParameterException(SecurityLogger.getReqParamNotSuppliedIsAccess());
      }
   }

   public RoleManagerDeployHandle startDeployRoles(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
      if (log.isDebugEnabled()) {
         log.debug("RoleManager.startDeployRoles");
      }

      if (null == appInfo) {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common RoleDeploymentService.startDeployRoles");
         }

         try {
            RoleDeploymentService.DeploymentHandler cssHandler = this.roleDeploymentService.startDeployRoles(appInfo);
            HandlerAdaptor wrapper = new HandlerAdaptor(cssHandler);
            this.realmServices.registerCleanupHandler(wrapper);
            return new RoleManagerDeployHandleImpl(wrapper);
         } catch (DeployHandleCreationException var4) {
            throw var4;
         } catch (Exception var5) {
            throw SecurityUtils.wrapRCMCloseException(var5);
         }
      }
   }

   public void deployRole(RoleManagerDeployHandle handle, Resource resource, String roleName, String[] userAndGroupNames) throws RoleCreationException {
      if (log.isDebugEnabled()) {
         log.debug("RoleManager.deployRole");
      }

      if (null == handle) {
         throw new InvalidParameterException(SecurityLogger.getDeployHandleNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common deployRole");
         }

         try {
            handle.getRoleDeploymentHandler().deployRole(resource, roleName, userAndGroupNames);
         } catch (RoleCreationException var6) {
            throw var6;
         } catch (Exception var7) {
            throw SecurityUtils.wrapRCMCloseException(var7);
         }
      }
   }

   public void endDeployRoles(RoleManagerDeployHandle handle) throws RoleCreationException {
      if (log.isDebugEnabled()) {
         log.debug("RoleManager.endDeployRoles");
      }

      if (null == handle) {
         throw new InvalidParameterException(SecurityLogger.getDeployHandleNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common endDeployRoles");
         }

         try {
            handle.getRoleDeploymentHandler().endDeployRoles();
         } catch (RoleCreationException var3) {
            throw var3;
         } catch (Exception var4) {
            throw SecurityUtils.wrapRCMCloseException(var4);
         }
      }
   }

   public void undeployAllRoles(RoleManagerDeployHandle handle) throws RoleRemovalException {
      if (log.isDebugEnabled()) {
         log.debug("RoleManager.undeployAllRoles");
      }

      if (null == handle) {
         throw new InvalidParameterException(SecurityLogger.getDeployHandleNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common undeployAllRoles");
         }

         try {
            weblogic.security.service.internal.RoleDeploymentService.DeploymentHandler wrapper = handle.getRoleDeploymentHandler();
            if (wrapper instanceof HandlerAdaptor) {
               this.realmServices.removeCleanupHandler((HandlerAdaptor)wrapper);
            }

            handle.getRoleDeploymentHandler().undeployAllRoles();
         } catch (RoleRemovalException var3) {
            throw var3;
         } catch (Exception var4) {
            throw SecurityUtils.wrapRCMCloseException(var4);
         }
      }
   }

   public void deleteApplicationRoles(SecurityApplicationInfo appInfo) throws RoleRemovalException {
      if (log.isDebugEnabled()) {
         log.debug("RoleManager.deleteApplicationRoles");
      }

      if (null == appInfo) {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common deleteApplicationRoles");
         }

         try {
            this.roleDeploymentService.deleteApplicationRoles(appInfo);
         } catch (RoleRemovalException var3) {
            throw var3;
         } catch (Exception var4) {
            throw SecurityUtils.wrapRCMCloseException(var4);
         }
      }
   }

   public boolean isVersionableApplicationSupported() {
      if (log.isDebugEnabled()) {
         log.debug("RoleManager.isVersionableApplicationSupported");
      }

      return this.appVerService.isApplicationVersioningSupported();
   }

   public void createApplicationVersion(String appIdentifier, String sourceAppIdentifier) throws ApplicationVersionCreationException {
      this.assertNotUsingCommon();
   }

   public void deleteApplicationVersion(String appIdentifier) throws ApplicationVersionRemovalException {
      this.assertNotUsingCommon();
   }

   public void deleteApplication(String appName) throws ApplicationRemovalException {
      this.assertNotUsingCommon();
   }

   public boolean isUserInRole(AuthenticatedSubject subject, String roleName, Resource resource, ContextHandler handler) {
      Map roles = this.getRoles(subject, resource, handler);
      if (roles == null) {
         return false;
      } else if (roleName == null) {
         return false;
      } else {
         return roles.get(roleName) != null;
      }
   }

   private static class HandlerAdaptor implements RealmServicesCleanup, weblogic.security.service.internal.RoleDeploymentService.DeploymentHandler {
      private volatile RoleDeploymentService.DeploymentHandler cssHandler;

      public HandlerAdaptor(RoleDeploymentService.DeploymentHandler cssHandler) {
         this.cssHandler = cssHandler;
      }

      public void cleanup() {
         this.cssHandler = null;
      }

      public void deployRole(Resource resource, String roleName, String[] userAndGroupNames) throws RoleCreationException {
         RoleDeploymentService.DeploymentHandler handler = this.cssHandler;
         if (handler != null) {
            handler.deployRole(resource, roleName, userAndGroupNames);
         }

      }

      public void endDeployRoles() throws RoleCreationException {
         RoleDeploymentService.DeploymentHandler handler = this.cssHandler;
         if (handler != null) {
            handler.endDeployRoles();
         }

      }

      public void undeployAllRoles() throws RoleRemovalException {
         RoleDeploymentService.DeploymentHandler handler = this.cssHandler;
         if (handler != null) {
            handler.undeployAllRoles();
         }

      }
   }
}
