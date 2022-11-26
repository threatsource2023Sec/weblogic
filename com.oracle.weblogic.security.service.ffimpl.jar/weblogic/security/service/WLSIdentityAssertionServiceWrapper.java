package weblogic.security.service;

import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityAssertionService;
import javax.security.auth.login.LoginException;
import weblogic.security.acl.internal.AuthenticatedSubject;

class WLSIdentityAssertionServiceWrapper implements IdentityAssertionService {
   private LoggerSpi logger;
   private IdentityAssertionService baseService;

   public WLSIdentityAssertionServiceWrapper(IdentityAssertionService baseService, LoggerService loggerService) {
      this.logger = loggerService.getLogger("SecurityAtn");
      this.baseService = baseService;
   }

   public boolean isTokenTypeSupported(String tokenType) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".isTokenTypeSupported" : null;
      if (debug) {
         this.logger.debug(method);
      }

      return "AuthenticatedUser".equals(tokenType) ? true : this.baseService.isTokenTypeSupported(tokenType);
   }

   public Identity assertIdentity(String tokenType, Object token, ContextHandler contextHandler) throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".assertIdentity" : null;
      if (debug) {
         this.logger.debug(method);
      }

      return "AuthenticatedUser".equals(tokenType) && token instanceof AuthenticatedSubject ? IdentityUtility.authenticatedSubjectToIdentity((AuthenticatedSubject)token) : this.baseService.assertIdentity(tokenType, token, contextHandler);
   }
}
