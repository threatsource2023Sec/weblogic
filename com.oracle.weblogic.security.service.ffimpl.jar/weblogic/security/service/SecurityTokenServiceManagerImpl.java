package weblogic.security.service;

import com.bea.common.security.service.SecurityTokenService;
import com.bea.common.security.service.TokenResponseContext;
import com.bea.security.css.CSS;
import java.security.AccessController;
import weblogic.management.security.RealmMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.Resource;

public class SecurityTokenServiceManagerImpl implements SecurityService, SecurityTokenServiceManager {
   private SecurityTokenService securityTokenService = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityCredMap");

   public SecurityTokenServiceManagerImpl() {
   }

   public SecurityTokenServiceManagerImpl(RealmServices realmServices, RealmMBean mBean) {
      this.initialize(realmServices, mBean);
   }

   public void initialize(RealmServices realmServices, RealmMBean realmMBean) {
      if (realmServices == null) {
         throw new InvalidParameterException(SecurityLogger.getValidRealmNameMustBeSpecifed());
      } else {
         try {
            CSS css = realmServices.getCSS();
            this.securityTokenService = (SecurityTokenService)css.getService("SecurityTokenService");
         } catch (Exception var5) {
            if (log.isDebugEnabled()) {
               SecurityLogger.logStackTrace(var5);
            }

            SecurityServiceRuntimeException ssre = new SecurityServiceRuntimeException(SecurityLogger.getExceptionObtainingService("Common SecurityTokenService", var5.toString()));
            ssre.initCause(var5);
            throw ssre;
         }
      }
   }

   public Object issueToken(String tokenType, AuthenticatedSubject requestor, AuthenticatedSubject secIdentity, Resource resource, ContextHandler handler) {
      return this.securityTokenService.issueToken(tokenType, IdentityUtility.authenticatedSubjectToIdentity(requestor), IdentityUtility.authenticatedSubjectToIdentity(secIdentity), resource, handler);
   }

   public TokenResponseContext challengeIssueToken(TokenRequestContext ctx) {
      throw new UnsupportedOperationException("challenge mechanism not supported");
   }

   public void shutdown() {
      this.securityTokenService = null;
   }

   public void start() {
   }

   public void suspend() {
   }
}
