package weblogic.security.service;

import com.bea.common.engine.InvalidParameterException;
import com.bea.common.security.service.BulkAuthorizationService;
import com.bea.security.css.CSS;
import java.security.AccessController;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.management.security.ProviderMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.Direction;

public class BulkAuthorizationManagerImpl implements SecurityService, BulkAuthorizationManager {
   private BulkAuthorizationService authorizationService = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private BulkRoleManager roleManager = null;
   private boolean initialized;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtz");

   public BulkAuthorizationManagerImpl() {
   }

   public BulkAuthorizationManagerImpl(RealmServices realmServices, ProviderMBean[] configuration) {
      this.initialize(realmServices, configuration);
   }

   public void initialize(RealmServices realmServices, ProviderMBean[] configuration) {
      if (null == realmServices) {
         throw new InvalidParameterException(SecurityLogger.getValidRealmNameMustBeSpecifed());
      } else if (null == configuration) {
         throw new InvalidParameterException(SecurityLogger.getNoAuthAndNoAdjMBeans());
      } else if (configuration.length < 1) {
         throw new InvalidParameterException(SecurityLogger.getNeedAtLeastOneAuthMBean());
      } else {
         if (log.isDebugEnabled()) {
            log.debug("BulkAuthorizationManager initializing for realm: " + realmServices.getRealmName());
         }

         try {
            CSS css = realmServices.getCSS();
            this.authorizationService = (BulkAuthorizationService)css.getService("BulkAuthorizationService");
         } catch (Exception var5) {
            if (log.isDebugEnabled()) {
               SecurityLogger.logStackTrace(var5);
            }

            SecurityServiceRuntimeException ssre = new SecurityServiceRuntimeException(SecurityLogger.getExceptionObtainingService("Common BulkAuthorizationService", var5.toString()));
            ssre.initCause(var5);
            throw ssre;
         }

         this.roleManager = (BulkRoleManager)realmServices.getServices().get(ServiceType.BULKROLE);
         if (this.roleManager == null) {
            throw new NotYetInitializedException(SecurityLogger.getRoleMgrMustBeInitBeforeAuth());
         } else {
            this.initialized = true;
         }
      }
   }

   public void start() {
   }

   public void suspend() {
   }

   public void shutdown() {
      this.authorizationService = null;
   }

   public Set isAccessAllowed(AuthenticatedSubject aSubject, Map roles, List resource, ContextHandler handler, Direction direction) {
      if (!this.initialized) {
         throw new NotYetInitializedException(SecurityLogger.getCallingIsProtectedBeforeInit());
      } else if (null != aSubject && null != resource && null != direction) {
         return this.authorizationService.isAccessAllowed(IdentityUtility.authenticatedSubjectToIdentity(aSubject), roles, resource, handler, direction);
      } else {
         throw new InvalidParameterException(SecurityLogger.getReqParamNotSuppliedIsAccess());
      }
   }

   public Set isAccessAllowed(AuthenticatedSubject aSubject, List resource, ContextHandler handler) {
      if (SecurityServiceManager.isKernelIdentity(aSubject)) {
         return new WLSBulkAuthorizationServiceWrapper.ResourceSet(resource);
      } else {
         Map roles = null;
         if (this.roleManager != null) {
            roles = this.roleManager.getRoles(aSubject, resource, handler);
         }

         return this.isAccessAllowed(aSubject, roles, resource, handler, Direction.ONCE);
      }
   }
}
