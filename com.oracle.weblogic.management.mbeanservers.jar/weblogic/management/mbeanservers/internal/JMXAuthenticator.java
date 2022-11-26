package weblogic.management.mbeanservers.internal;

import java.io.IOException;
import java.security.AccessController;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import weblogic.management.context.JMXContext;
import weblogic.management.context.JMXContextHelper;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.auth.callback.IdentityDomainUserCallback;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.utils.PartitionUtils;

public class JMXAuthenticator implements javax.management.remote.JMXAuthenticator {
   private PrincipalAuthenticator authenticator;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected JMXAuthenticator() {
      this.authenticator = SecurityServiceManager.getPrincipalAuthenticator(kernelId, "weblogicDEFAULT");
   }

   public Subject authenticate(Object credentials) {
      try {
         if (credentials == null) {
            AuthenticatedSubject currentSubject = SecurityServiceManager.getCurrentSubject(kernelId);
            if (System.getSecurityManager() != null && SecurityServiceManager.isKernelIdentity(currentSubject)) {
               JMXContext jmxContext = JMXContextHelper.getJMXContext(false);
               if (jmxContext != null) {
                  Subject subject = jmxContext.getSubject();
                  if (subject != null && SecurityServiceManager.isKernelIdentity(SecurityServiceManager.getASFromWire(AuthenticatedSubject.getFromSubject(subject)))) {
                     return currentSubject.getSubject();
                  }
               }
            }

            return AuthenticatedSubject.ANON.getSubject();
         } else if (!(credentials instanceof String[])) {
            throw new SecurityException("Invalid JMX credential type passed to JMX connector: " + credentials.getClass().getName());
         } else {
            String[] creds = (String[])((String[])credentials);
            if (creds.length >= 2 && creds[0] != null && creds[1] != null) {
               AuthenticatedSubject as = this.authenticator.authenticate(new SimpleCallbackHandler(creds[0], creds[1]));
               AuthenticatedSubject currentSubject = SecurityServiceManager.getCurrentSubject(kernelId);
               return currentSubject.equals(as) && currentSubject.getQOS() == as.getQOS() ? currentSubject.getSubject() : as.getSubject();
            } else {
               throw new SecurityException("Invalid JMX credential, empty username and/or password");
            }
         }
      } catch (LoginException var5) {
         throw new SecurityException(var5);
      }
   }

   private class JMXCallbackHandler implements CallbackHandler {
      String userName;
      String password;

      protected JMXCallbackHandler(String[] credentials) {
         this.userName = credentials[0];
         this.password = credentials[1];
      }

      public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
         String identityDomain = PartitionUtils.getCurrentIdentityDomain();

         for(int i = 0; i < callbacks.length; ++i) {
            Callback callback = callbacks[i];
            if (identityDomain == null) {
               if (callback instanceof NameCallback) {
                  ((NameCallback)callback).setName(this.userName);
                  continue;
               }
            } else if (callback instanceof IdentityDomainUserCallback) {
               ((IdentityDomainUserCallback)callback).setUser(new IdentityDomainNames(this.userName, identityDomain));
               continue;
            }

            if (!(callback instanceof PasswordCallback)) {
               throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }

            ((PasswordCallback)callback).setPassword(this.password.toCharArray());
         }

      }
   }
}
