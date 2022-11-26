package weblogic.security.service;

import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.AuthorizationService;
import com.bea.common.security.service.Identity;
import java.util.Map;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;

class WLSAuthorizationServiceWrapper implements AuthorizationService {
   private LoggerSpi logger;
   private AuthorizationService baseService;
   private String[] jndiHomeName;
   private String[] localJndiHomeName;
   private String[] adminJndiHomeName;
   private int jndiHomeMinLength = 0;
   private int jndiCommonLength = 0;
   private boolean permitAnonymousAdmin = false;

   public WLSAuthorizationServiceWrapper(AuthorizationService baseService, LoggerService loggerService) {
      this.logger = loggerService.getLogger("SecurityAtz");
      this.baseService = baseService;
      this.permitAnonymousAdmin = SecurityServiceManager.isAnonymousAdminLookupEnabled();
      if (!this.permitAnonymousAdmin) {
         this.jndiHomeName = "weblogic.management.home".split("\\.");
         this.localJndiHomeName = "weblogic.management.home.localhome".split("\\.");
         this.adminJndiHomeName = "weblogic.management.adminhome".split("\\.");
         this.jndiHomeMinLength = this.jndiHomeName.length;
         if (this.localJndiHomeName.length < this.jndiHomeMinLength) {
            this.jndiHomeMinLength = this.localJndiHomeName.length;
         }

         if (this.adminJndiHomeName.length < this.jndiHomeMinLength) {
            this.jndiHomeMinLength = this.adminJndiHomeName.length;
         }

         for(int i = 0; i < this.jndiHomeMinLength; ++i) {
            if (!this.jndiHomeName[i].equals(this.localJndiHomeName[i]) || !this.jndiHomeName[i].equals(this.adminJndiHomeName[i])) {
               this.jndiCommonLength = i;
               break;
            }
         }
      }

   }

   private boolean comparePathArrays(String[] jndiString, String[] resourceString, int startingIndex) {
      if (resourceString.length < jndiString.length) {
         return false;
      } else {
         for(int i = startingIndex; i < jndiString.length; ++i) {
            if (!resourceString[i].equals(jndiString[i])) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isAccessAllowed(Identity identity, Map roles, Resource resource, ContextHandler contextHandler, Direction direction) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".isAccessAllowed" : null;
      if (debug) {
         this.logger.debug(method);
      }

      AuthenticatedSubject aSubject = IdentityUtility.identityToAuthenticatedSubject(identity);
      if (SecurityServiceManager.isKernelIdentity(aSubject)) {
         return true;
      } else {
         if (!this.permitAnonymousAdmin && resource instanceof JNDIResource && SubjectUtils.isUserAnonymous(aSubject)) {
            JNDIResource jndiResource = (JNDIResource)resource;
            String[] path = jndiResource.getPath();
            if (path != null && path.length >= this.jndiHomeMinLength) {
               boolean match = true;

               for(int i = 0; i < this.jndiCommonLength; ++i) {
                  if (!this.jndiHomeName[i].equals(path[i])) {
                     match = false;
                     break;
                  }
               }

               if (match && (this.comparePathArrays(this.jndiHomeName, path, this.jndiCommonLength) || this.comparePathArrays(this.localJndiHomeName, path, this.jndiCommonLength) || this.comparePathArrays(this.adminJndiHomeName, path, this.jndiCommonLength))) {
                  if (this.logger.isDebugEnabled()) {
                     this.logger.debug("AuthorizationManager.isAccessAllowed returning false on MBeanHome");
                  }

                  return false;
               }
            }
         }

         return this.baseService.isAccessAllowed(identity, roles, resource, contextHandler, direction);
      }
   }
}
