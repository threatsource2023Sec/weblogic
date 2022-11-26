package weblogic.security.jaspic.servlet;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.config.AuthConfigFactory;
import javax.security.auth.message.config.AuthConfigProvider;
import javax.security.auth.message.config.RegistrationListener;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.servlet.security.internal.ServletSecurityContext;
import weblogic.servlet.spi.SubjectHandle;

public class JaspicUtilities {
   public static ServerAuthConfig getServerAuthConfig(ServletSecurityContext ctx, String messageLayer, String appContextId, RegistrationListener listener) {
      try {
         AuthConfigFactory factory = AuthConfigFactory.getFactory();
         if (factory == null) {
            return null;
         } else {
            AuthConfigProvider provider = factory.getConfigProvider(messageLayer, appContextId, listener);
            return provider == null ? null : provider.getServerAuthConfig(messageLayer, appContextId, new JaspicCallbackHandler(new ContextImpl(ctx)));
         }
      } catch (AuthException var6) {
         return null;
      }
   }

   private static class ContextImpl implements JaspicCallbackHandler.Context {
      private ServletSecurityContext securityContext;

      private ContextImpl(ServletSecurityContext securityContext) {
         this.securityContext = securityContext;
      }

      public SubjectHandle authenticateAndSaveCredential(String username, char[] password) throws LoginException {
         return this.securityContext.getAppSecurityProvider().authenticateAndSaveCredential(username, password, this.securityContext.getSecurityRealmName());
      }

      public SubjectHandle authenticateAndSaveCredential(String username, String password, HttpServletRequest request, HttpServletResponse response) throws LoginException {
         return this.securityContext.getAppSecurityProvider().authenticateAndSaveCredential(username, password, this.securityContext.getSecurityRealmName(), request, response);
      }

      public void populateSubject(Subject subject, SubjectHandle subjectHandle) {
         this.securityContext.getAppSecurityProvider().populateSubject(subject, subjectHandle);
      }

      // $FF: synthetic method
      ContextImpl(ServletSecurityContext x0, Object x1) {
         this(x0);
      }
   }
}
