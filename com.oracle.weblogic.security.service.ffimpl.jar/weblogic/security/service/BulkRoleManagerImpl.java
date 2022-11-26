package weblogic.security.service;

import com.bea.common.engine.InvalidParameterException;
import com.bea.common.security.service.BulkRoleMappingService;
import com.bea.security.css.CSS;
import java.security.AccessController;
import java.util.List;
import java.util.Map;
import weblogic.management.security.ProviderMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.shared.LoggerWrapper;

public class BulkRoleManagerImpl implements SecurityService, BulkRoleManager {
   private BulkRoleMappingService roleService = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean initialized;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityRoleMap");

   public BulkRoleManagerImpl() {
   }

   public BulkRoleManagerImpl(RealmServices realmServices, ProviderMBean[] configuration) {
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
            log.debug("BulkRoleManager initializing for realm: " + realmServices.getRealmName());
         }

         try {
            CSS css = realmServices.getCSS();
            this.roleService = (BulkRoleMappingService)css.getService("BulkRoleMappingService");
         } catch (Exception var5) {
            if (log.isDebugEnabled()) {
               SecurityLogger.logStackTrace(var5);
            }

            SecurityServiceRuntimeException ssre = new SecurityServiceRuntimeException(SecurityLogger.getExceptionObtainingService("Common BulkRoleMappingService", var5.toString()));
            ssre.initCause(var5);
            throw ssre;
         }

         this.initialized = true;
      }
   }

   public void start() {
   }

   public void suspend() {
   }

   public void shutdown() {
      this.roleService = null;
   }

   public Map getRoles(AuthenticatedSubject aSubject, List resource, ContextHandler handler) {
      if (!this.initialized) {
         throw new NotYetInitializedException(SecurityLogger.getRoleMgrNotYetInitialized());
      } else if (null != aSubject && null != resource) {
         return this.roleService.getRoles(IdentityUtility.authenticatedSubjectToIdentity(aSubject), resource, handler);
      } else {
         throw new InvalidParameterException(SecurityLogger.getReqParamNotSuppliedIsAccess());
      }
   }
}
