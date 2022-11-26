package weblogic.security.services;

import java.io.Serializable;
import java.security.AccessController;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.SecurityLogger;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AdminResource;
import weblogic.security.service.AppContextHandler;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.ChallengeContext;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.InvalidParameterException;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.IdentityAssertionException;
import weblogic.security.utils.ResourceIDDContextWrapper;

public final class Authentication {
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityAtn");
   private static AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static Subject login(CallbackHandler callbackHandler) throws LoginException {
      return login((CallbackHandler)callbackHandler, (AppContext)null);
   }

   public static Subject login(CallbackHandler callbackHandler, AppContext appContext) throws LoginException {
      PrincipalAuthenticator pa = SecurityServiceManager.getPrincipalAuthenticator(kernelID, SecurityServiceManager.getContextSensitiveRealmName());
      return doLogin(callbackHandler, appContext, pa);
   }

   /** @deprecated */
   @Deprecated
   public static Subject login(String realmName, CallbackHandler callbackHandler) throws LoginException {
      return login(realmName, callbackHandler, (AppContext)null);
   }

   /** @deprecated */
   @Deprecated
   public static Subject login(String realmName, CallbackHandler callbackHandler, AppContext appContext) throws LoginException {
      PrincipalAuthenticator pa = SecurityServiceManager.getPrincipalAuthenticator(kernelID, realmName);
      return doLogin(callbackHandler, appContext, pa);
   }

   private static Subject doLogin(CallbackHandler callbackHandler, AppContext appContext, PrincipalAuthenticator pa) throws LoginException {
      if (pa == null) {
         throw new InvalidParameterException(SecurityLogger.getSecurityServiceUnavailable());
      } else {
         ContextHandler contextHandler = AppContextHandler.getInstance(appContext);
         AuthenticatedSubject authSubject = pa.authenticate(callbackHandler, contextHandler);
         return authSubject.getSubject();
      }
   }

   public static Subject assertIdentity(String tokenType, Object token) throws LoginException {
      return assertIdentity(tokenType, (Object)token, (AppContext)null);
   }

   public static Subject assertIdentity(String tokenType, Object token, AppContext appContext) throws LoginException {
      return doAssertIdentity(tokenType, token, appContext, SecurityServiceManager.getContextSensitiveRealmName());
   }

   /** @deprecated */
   @Deprecated
   public static Subject assertIdentity(String realmName, String tokenType, Object token) throws LoginException {
      return assertIdentity(realmName, tokenType, token, (AppContext)null);
   }

   /** @deprecated */
   @Deprecated
   public static Subject assertIdentity(String realmName, String tokenType, Object token, AppContext appContext) throws LoginException {
      return doAssertIdentity(tokenType, token, appContext, realmName);
   }

   private static Subject doAssertIdentity(String tokenType, Object token, AppContext appContext, String realmName) throws LoginException {
      PrincipalAuthenticator pa = SecurityServiceManager.getPrincipalAuthenticator(kernelID, realmName);
      if (pa == null) {
         throw new InvalidParameterException("Security Service Unavailable");
      } else {
         AuthorizationManager authManager = SecurityServiceManager.getAuthorizationManager(kernelID, SecurityServiceManager.getAdministrativeRealmName());
         if (authManager != null) {
            AuthenticatedSubject currSubject = SecurityServiceManager.getCurrentSubject(kernelID);
            AdminResource res = new AdminResource("IdentityAssertion", realmName, "assertIdentity");
            if (log.isDebugEnabled()) {
               log.debug(" isAccessAllowed:  checking Permission for: '" + res + "', currentSubject: '" + SubjectUtils.displaySubject(currSubject) + "'");
            }

            if (!authManager.isAccessAllowed(currSubject, res, new ResourceIDDContextWrapper())) {
               if (log.isDebugEnabled()) {
                  log.debug(" isAccessAllowed:  currentSubject: " + currSubject + " does not have permission to assert identity of type " + tokenType + " in realm " + realmName);
               }

               throw new SecurityException(" isAccessAllowed:  currentSubject: " + currSubject + " does not have permission to assert identity of type " + tokenType + " in realm " + realmName);
            } else {
               ContextHandler contextHandler = AppContextHandler.getInstance(appContext);
               AuthenticatedSubject authSubject = pa.assertIdentity(tokenType, token, contextHandler);
               return authSubject.getSubject();
            }
         } else {
            throw new SecurityException("Security Service Unavailable");
         }
      }
   }

   public Object getChallengeToken(String tokenType, AppContext appContext) throws LoginException {
      PrincipalAuthenticator pa = SecurityServiceManager.getPrincipalAuthenticator(kernelID, SecurityServiceManager.getContextSensitiveRealmName());
      if (pa == null) {
         throw new InvalidParameterException("Security Service Unavailable");
      } else {
         Object cToken = null;
         ContextHandler contextHandler = AppContextHandler.getInstance(appContext);

         try {
            cToken = pa.getChallengeToken(tokenType, contextHandler);
            return cToken;
         } catch (IdentityAssertionException var7) {
            throw new LoginException(var7.getMessage());
         }
      }
   }

   public void continueChallengeIdentity(AppChallengeContext context, String tokenType, Object token, AppContext appContext) throws LoginException {
      PrincipalAuthenticator pa = SecurityServiceManager.getPrincipalAuthenticator(kernelID, SecurityServiceManager.getContextSensitiveRealmName());
      if (pa == null) {
         throw new InvalidParameterException("Security Service Unavailable");
      } else {
         ContextHandler contextHandler = AppContextHandler.getInstance(appContext);
         ChallengeContext cContext = ((AppChallengeContextImpl)context).getChallengeContext();
         pa.continueChallengeIdentity(cContext, tokenType, token, contextHandler);
      }
   }

   public AppChallengeContext assertChallengeIdentity(String tokenType, Object token, AppContext appContext) throws LoginException {
      String realmName = SecurityServiceManager.getContextSensitiveRealmName();
      PrincipalAuthenticator pa = SecurityServiceManager.getPrincipalAuthenticator(kernelID, realmName);
      if (pa == null) {
         throw new InvalidParameterException("Security Service Unavailable");
      } else {
         AuthorizationManager authManager = SecurityServiceManager.getAuthorizationManager(kernelID, SecurityServiceManager.getAdministrativeRealmName());
         if (authManager != null) {
            AuthenticatedSubject currSubject = SecurityServiceManager.getCurrentSubject(kernelID);
            AdminResource res = new AdminResource("IdentityAssertion", realmName, "assertIdentity");
            if (log.isDebugEnabled()) {
               log.debug(" isAccessAllowed:  checking Permission for: '" + res + "', currentSubject: '" + SubjectUtils.displaySubject(currSubject) + "'");
            }

            if (!authManager.isAccessAllowed(currSubject, res, new ResourceIDDContextWrapper())) {
               if (log.isDebugEnabled()) {
                  log.debug(" isAccessAllowed:  currentSubject: " + currSubject + " does not have permission to assert identity of type " + tokenType + " in realm " + realmName);
               }

               throw new SecurityException(" isAccessAllowed:  currentSubject: " + currSubject + " does not have permission to assert identity of type " + tokenType + " in realm " + realmName);
            } else {
               ContextHandler contextHandler = AppContextHandler.getInstance(appContext);
               ChallengeContext challengeConext = pa.assertChallengeIdentity(tokenType, token, contextHandler);
               return new AppChallengeContextImpl(challengeConext, log);
            }
         } else {
            throw new SecurityException("Security Service Unavailable");
         }
      }
   }

   private static final class AppChallengeContextImpl implements AppChallengeContext, Serializable {
      private static final long serialVersionUID = 5847810460507567453L;
      private ChallengeContext challengeContext;

      public AppChallengeContextImpl(ChallengeContext challengeCtx, LoggerWrapper log) {
         this.challengeContext = challengeCtx;
      }

      public boolean hasChallengeIdentityCompleted() {
         return this.challengeContext.hasChallengeIdentityCompleted();
      }

      public Subject getAuthenticatedSubject() {
         if (!this.hasChallengeIdentityCompleted()) {
            throw new IllegalStateException(SecurityLogger.getChallengeNotCompleted());
         } else {
            AuthenticatedSubject subject = this.challengeContext.getAuthenticatedSubject();
            return subject != null ? subject.getSubject() : null;
         }
      }

      public Object getChallengeToken() {
         if (this.hasChallengeIdentityCompleted()) {
            throw new IllegalStateException(SecurityLogger.getChallengeHasCompleted());
         } else {
            return this.challengeContext.getChallengeToken();
         }
      }

      public ChallengeContext getChallengeContext() {
         return this.challengeContext;
      }
   }
}
