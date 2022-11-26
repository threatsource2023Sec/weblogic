package weblogic.security.service;

import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.JAASAuthenticationService;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.SubjectUtils;

class WLSJAASAuthenticationServiceWrapper implements JAASAuthenticationService {
   private LoggerSpi logger;
   private JAASAuthenticationService baseService;

   public WLSJAASAuthenticationServiceWrapper(JAASAuthenticationService baseService, LoggerService loggerService) {
      this.logger = loggerService.getLogger("SecurityAtn");
      this.baseService = baseService;
   }

   public Identity authenticate(CallbackHandler callbackHandler, ContextHandler contextHandler) throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".authenticate" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (callbackHandler == null) {
         if (debug) {
            this.logger.debug(this.getClass().getName() + ".authenticate anonymous shortcut");
         }

         return IdentityUtility.authenticatedSubjectToIdentity(SubjectUtils.getAnonymousSubject());
      } else {
         return this.baseService.authenticate(callbackHandler, contextHandler);
      }
   }
}
