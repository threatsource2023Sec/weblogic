package weblogic.security.service;

import com.bea.common.engine.InvalidParameterException;
import com.bea.common.security.service.AuthorizationService;
import com.bea.common.security.service.IsProtectedResourceService;
import com.bea.common.security.service.PolicyConsumerService;
import com.bea.common.security.service.PolicyDeploymentService;
import com.bea.common.security.service.RoleConsumerService;
import com.bea.security.css.CSS;
import java.security.AccessController;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.management.security.ProviderMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.service.internal.ApplicationVersioningService;
import weblogic.security.service.internal.WLSIdentityImpl;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;
import weblogic.security.utils.SecurityUtils;

public class AuthorizationManagerImpl implements SecurityService, AuthorizationManager {
   private RealmServices realmServices = null;
   private ApplicationVersioningService appVerService = null;
   private AuthorizationService authorizationService = null;
   private PolicyDeploymentService policyDeploymentService = null;
   private IsProtectedResourceService isProtectedResourceService = null;
   private PolicyConsumerService policyConsumerService = null;
   private RoleConsumerService roleConsumerService = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private RoleManager roleManager = null;
   private boolean initialized;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtz");

   private void assertNotUsingCommon() {
      throw new AssertionError("This code should never be called when using common security");
   }

   public AuthorizationManagerImpl() {
   }

   public AuthorizationManagerImpl(RealmServices realmServices, ProviderMBean[] configuration) {
      this.initialize(realmServices, configuration);
   }

   public void initialize(RealmServices realmServices, ProviderMBean[] configuration) {
      if (null == realmServices) {
         throw new InvalidParameterException(SecurityLogger.getValidRealmNameMustBeSpecifed());
      } else {
         this.realmServices = realmServices;
         if (null == configuration) {
            throw new InvalidParameterException(SecurityLogger.getNoAuthAndNoAdjMBeans());
         } else if (configuration.length < 1) {
            throw new InvalidParameterException(SecurityLogger.getNeedAtLeastOneAuthMBean());
         } else {
            if (log.isDebugEnabled()) {
               log.debug("AuthorizationManager initializing for realm: " + realmServices.getRealmName());
            }

            if (log.isDebugEnabled()) {
               log.debug("AuthorizationManager will use common security");
            }

            try {
               CSS css = realmServices.getCSS();
               this.authorizationService = (AuthorizationService)css.getService("AuthorizationService");
               this.isProtectedResourceService = (IsProtectedResourceService)css.getService("IsProtectedResourceService");
               this.policyConsumerService = (PolicyConsumerService)css.getService("PolicyConsumerService");
               this.roleConsumerService = (RoleConsumerService)css.getService("RoleConsumerService");
               this.policyDeploymentService = (PolicyDeploymentService)css.getService("PolicyDeploymentService");
               this.appVerService = (ApplicationVersioningService)css.getService("ApplicationVersioningService");
            } catch (Exception var5) {
               if (log.isDebugEnabled()) {
                  SecurityLogger.logStackTrace(var5);
               }

               SecurityServiceRuntimeException ssre = new SecurityServiceRuntimeException(SecurityLogger.getExceptionObtainingService("Common AuthorizationService", var5.toString()));
               ssre.initCause(var5);
               throw ssre;
            }

            this.roleManager = (RoleManager)realmServices.getServices().get(ServiceType.ROLE);
            if (this.roleManager == null) {
               throw new NotYetInitializedException(SecurityLogger.getRoleMgrMustBeInitBeforeAuth());
            } else {
               this.initialized = true;
            }
         }
      }
   }

   public void start() {
   }

   public void suspend() {
   }

   public void shutdown() {
      this.authorizationService = null;
      this.policyDeploymentService = null;
      this.isProtectedResourceService = null;
      this.realmServices = null;
   }

   public boolean isAccessAllowed(AuthenticatedSubject aSubject, Map roles, Resource resource, ContextHandler handler, Direction direction) {
      if (!this.initialized) {
         throw new NotYetInitializedException(SecurityLogger.getCallingIsProtectedBeforeInit());
      } else if (null != aSubject && null != resource && null != direction) {
         if (log.isDebugEnabled()) {
            log.debug("AuthorizationManager will use common security for ATZ");
         }

         return this.authorizationService.isAccessAllowed(IdentityUtility.authenticatedSubjectToIdentity(aSubject), roles, resource, handler, direction);
      } else {
         throw new InvalidParameterException(SecurityLogger.getReqParamNotSuppliedIsAccess());
      }
   }

   public boolean isAccessAllowed(AuthenticatedSubject aSubject, Resource resource, ContextHandler handler) {
      if (SecurityServiceManager.isKernelIdentity(aSubject)) {
         return true;
      } else {
         Map roles = null;
         if (this.roleManager != null) {
            roles = this.roleManager.getRoles(aSubject, resource, handler);
         }

         return this.isAccessAllowed(aSubject, roles, resource, handler, Direction.ONCE);
      }
   }

   public boolean isProtectedResource(Subject subject, Resource resource) {
      if (!this.initialized) {
         throw new NotYetInitializedException(SecurityLogger.getCallingIsProtectedBeforeInit());
      } else if (null != subject && null != resource) {
         if (log.isDebugEnabled()) {
            log.debug("common security for isProtectedResource");
         }

         return this.isProtectedResourceService.isProtectedResource(IdentityUtility.authenticatedSubjectToIdentity(new AuthenticatedSubject(subject)), resource);
      } else {
         throw new InvalidParameterException(SecurityLogger.getReqParamNotSuppliedIsProt());
      }
   }

   public boolean isProtectedResource(AuthenticatedSubject aSubject, Resource resource) {
      if (!this.initialized) {
         throw new NotYetInitializedException(SecurityLogger.getCallingIsProtectedBeforeInit());
      } else if (null != aSubject && null != resource) {
         if (log.isDebugEnabled()) {
            log.debug("common security for isProtectedResource");
         }

         return this.isProtectedResourceService.isProtectedResource(IdentityUtility.authenticatedSubjectToIdentity(aSubject), resource);
      } else {
         throw new InvalidParameterException(SecurityLogger.getReqParamNotSuppliedIsProt());
      }
   }

   public boolean isResourceProtected(Subject subject, Resource resource) {
      if (!this.initialized) {
         throw new NotYetInitializedException(SecurityLogger.getCallingIsProtectedBeforeInit());
      } else if (null != subject && null != resource) {
         if (log.isDebugEnabled()) {
            log.debug("common security for isProtectedResource");
         }

         return this.isProtectedResourceService.isProtectedResource(new WLSIdentityImpl(new AuthenticatedSubject(subject)), resource);
      } else {
         throw new InvalidParameterException(SecurityLogger.getReqParamNotSuppliedIsProt());
      }
   }

   public AuthorizationManagerDeployHandle startDeployPolicies(SecurityApplicationInfo appInfo) throws DeployHandleCreationException {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationManager.startDeployPolicies");
      }

      if (null == appInfo) {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common ATZ startDeployPolicies");
         }

         try {
            PolicyDeploymentService.DeploymentHandler cssHandler = this.policyDeploymentService.startDeployPolicies(appInfo);
            HandlerAdaptor wrapper = new HandlerAdaptor(cssHandler);
            this.realmServices.registerCleanupHandler(wrapper);
            return new AuthorizationManagerDeployHandleImpl(wrapper);
         } catch (DeployHandleCreationException var4) {
            throw var4;
         } catch (Exception var5) {
            throw SecurityUtils.wrapRCMCloseException(var5);
         }
      }
   }

   public void deployPolicy(AuthorizationManagerDeployHandle handle, Resource resource, String[] roleNames) throws ResourceCreationException {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationManager.deployPolicy");
      }

      if (null == handle) {
         throw new InvalidParameterException(SecurityLogger.getDeployHandleNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common deployPolicy");
         }

         try {
            handle.getPolicyDeploymentHandler().deployPolicy(resource, roleNames);
         } catch (ResourceCreationException var5) {
            throw var5;
         } catch (Exception var6) {
            throw SecurityUtils.wrapRCMCloseException(var6);
         }
      }
   }

   public void deployUncheckedPolicy(AuthorizationManagerDeployHandle handle, Resource resource) throws ResourceCreationException {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationManager.deployUncheckedPolicy");
      }

      if (null == handle) {
         throw new InvalidParameterException(SecurityLogger.getDeployHandleNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common deployUncheckedPolicy");
         }

         try {
            handle.getPolicyDeploymentHandler().deployUncheckedPolicy(resource);
         } catch (ResourceCreationException var4) {
            throw var4;
         } catch (Exception var5) {
            throw SecurityUtils.wrapRCMCloseException(var5);
         }
      }
   }

   public void deployExcludedPolicy(AuthorizationManagerDeployHandle handle, Resource resource) throws ResourceCreationException {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationManager.deployExcludedPolicy");
      }

      if (null == handle) {
         throw new InvalidParameterException(SecurityLogger.getDeployHandleNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common deployExcludedPolicy");
         }

         try {
            handle.getPolicyDeploymentHandler().deployExcludedPolicy(resource);
         } catch (ResourceCreationException var4) {
            throw var4;
         } catch (Exception var5) {
            throw SecurityUtils.wrapRCMCloseException(var5);
         }
      }
   }

   public void endDeployPolicies(AuthorizationManagerDeployHandle handle) throws ResourceCreationException {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationManager.endDeployPolicies");
      }

      if (null == handle) {
         throw new InvalidParameterException(SecurityLogger.getDeployHandleNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common endDeployPolicies");
         }

         try {
            handle.getPolicyDeploymentHandler().endDeployPolicies();
         } catch (ResourceCreationException var3) {
            throw var3;
         } catch (Exception var4) {
            throw SecurityUtils.wrapRCMCloseException(var4);
         }
      }
   }

   public void undeployAllPolicies(AuthorizationManagerDeployHandle handle) throws ResourceRemovalException {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationManager.undeployAllPolicies");
      }

      if (null == handle) {
         throw new InvalidParameterException(SecurityLogger.getDeployHandleNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common undeployAllPolicies");
         }

         try {
            weblogic.security.service.internal.PolicyDeploymentService.DeploymentHandler wrapper = handle.getPolicyDeploymentHandler();
            if (wrapper instanceof HandlerAdaptor) {
               this.realmServices.removeCleanupHandler((HandlerAdaptor)wrapper);
            }

            handle.getPolicyDeploymentHandler().undeployAllPolicies();
         } catch (ResourceRemovalException var3) {
            throw var3;
         } catch (Exception var4) {
            throw SecurityUtils.wrapRCMCloseException(var4);
         }
      }
   }

   public void deleteApplicationPolicies(SecurityApplicationInfo appInfo) throws ResourceRemovalException {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationManager.deleteApplicationPolicies");
      }

      if (null == appInfo) {
         throw new InvalidParameterException(SecurityLogger.getApplicationInformationNotSupplied());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("Using Common deleteApplicationPolicies");
         }

         try {
            this.policyDeploymentService.deleteApplicationPolicies(appInfo);
         } catch (ResourceRemovalException var3) {
            throw var3;
         } catch (Exception var4) {
            throw SecurityUtils.wrapRCMCloseException(var4);
         }
      }
   }

   public boolean isVersionableApplicationSupported() {
      if (log.isDebugEnabled()) {
         log.debug("AuthorizationManager.isVersionableApplicationSupported");
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

   PolicyConsumerService getPolicyConsumerService() {
      return this.policyConsumerService;
   }

   RoleConsumerService getRoleConsumerService() {
      return this.roleConsumerService;
   }

   public AuthorizationPolicyHandler getAuthorizationPolicyHandler(String name, String version, String timeStamp, Resource[] resources) throws ConsumptionException {
      this.assertNotUsingCommon();
      if (name != null && version != null && timeStamp != null && resources != null && resources.length != 0) {
         if (log.isDebugEnabled()) {
            log.debug("AuthorizationManager.getAuthorizationPolicyHandler: " + name + " : " + version + " : " + timeStamp);
         }

         if (log.isDebugEnabled()) {
            log.debug("AuthorizationManager.getAuthorizationPolicyHandler: No policy handler");
         }

         return null;
      } else {
         throw new InvalidParameterException("NULL parameter supplied");
      }
   }

   private static class HandlerAdaptor implements RealmServicesCleanup, weblogic.security.service.internal.PolicyDeploymentService.DeploymentHandler {
      private volatile PolicyDeploymentService.DeploymentHandler cssHandler;

      public HandlerAdaptor(PolicyDeploymentService.DeploymentHandler cssHandler) {
         this.cssHandler = cssHandler;
      }

      public void cleanup() {
         this.cssHandler = null;
      }

      public void deployExcludedPolicy(Resource resource) throws ResourceCreationException {
         PolicyDeploymentService.DeploymentHandler handler = this.cssHandler;
         if (handler != null) {
            handler.deployExcludedPolicy(resource);
         }

      }

      public void deployPolicy(Resource resource, String[] roleNames) throws ResourceCreationException {
         PolicyDeploymentService.DeploymentHandler handler = this.cssHandler;
         if (handler != null) {
            handler.deployPolicy(resource, roleNames);
         }

      }

      public void deployUncheckedPolicy(Resource resource) throws ResourceCreationException {
         PolicyDeploymentService.DeploymentHandler handler = this.cssHandler;
         if (handler != null) {
            handler.deployUncheckedPolicy(resource);
         }

      }

      public void endDeployPolicies() throws ResourceCreationException {
         PolicyDeploymentService.DeploymentHandler handler = this.cssHandler;
         if (handler != null) {
            handler.endDeployPolicies();
         }

      }

      public void undeployAllPolicies() throws ResourceRemovalException {
         PolicyDeploymentService.DeploymentHandler handler = this.cssHandler;
         if (handler != null) {
            handler.undeployAllPolicies();
         }

      }
   }
}
