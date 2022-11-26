package weblogic.connector.security.work;

import java.io.IOException;
import java.security.AccessController;
import java.security.Principal;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.auth.message.callback.GroupPrincipalCallback;
import javax.security.auth.message.callback.PasswordValidationCallback;
import weblogic.connector.common.Debug;
import weblogic.connector.security.SecurityHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ConnectorCallbackHandler implements CallbackHandler {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   boolean callerPrincipalCallbackHandled = false;
   SecurityContextPrincipalMapper mapper;
   SecurityHelper securityHelper;

   public ConnectorCallbackHandler(SecurityContextPrincipalMapper mapper, SecurityHelper securityHelper) {
      this.mapper = mapper;
      this.securityHelper = securityHelper;
   }

   public boolean isCallerPrincipalCallbackHandled() {
      return this.callerPrincipalCallbackHandled;
   }

   public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
      if (callbacks == null) {
         throw new IllegalStateException("callbacks must not be null");
      } else if (callbacks.length == 0) {
         throw new IllegalStateException("callbacks must not be empty array");
      } else {
         try {
            Callback[] var2 = callbacks;
            int var3 = callbacks.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Callback cb = var2[var4];
               if (cb instanceof CallerPrincipalCallback) {
                  this.processCallerPrincipalCallback(cb);
               } else if (cb instanceof PasswordValidationCallback) {
                  this.processPasswordValidationCallback(cb);
               } else {
                  if (!(cb instanceof GroupPrincipalCallback)) {
                     throw new UnsupportedCallbackException(cb);
                  }

                  this.processGroupPrincipalCallback(cb);
               }
            }

         } catch (LoginException var6) {
            throw new RuntimeException("unable to handle callback", var6);
         }
      }
   }

   private void processCallerPrincipalCallback(Callback cb) throws LoginException {
      CallerPrincipalCallback cpc = (CallerPrincipalCallback)cb;
      Subject executionSubject = cpc.getSubject();
      String username = null;
      Principal p = cpc.getPrincipal();
      if (Debug.isWorkEnabled()) {
         Debug.work("processCallerPrincipalCallback: executionSubject:" + executionSubject + "; Principal:" + p + "; naem:" + cpc.getName());
      }

      if (p != null) {
         username = p.getName();
      }

      if (username == null) {
         username = cpc.getName();
      }

      if (username == null) {
         this.setupAsAnonymous(executionSubject);
      } else {
         if (this.mapper != null) {
            String oldUsername = username;
            username = this.mapper.mapEISCallerPrincipal(username);
            if (Debug.isWorkEnabled()) {
               Debug.work("processCallerPrincipalCallback: map EIS username [" + oldUsername + "] to WLS caller principle: [" + username + "]");
            }
         }

         this.setupAsWLSUser(executionSubject, username);
      }

      this.callerPrincipalCallbackHandled = true;
   }

   private void processGroupPrincipalCallback(Callback cb) {
   }

   private void processPasswordValidationCallback(Callback cb) {
      if (this.mapper != null) {
         String errMsg = "PasswordValidationCallback is not allowed in CASE2";
         Debug.work("processPasswordValidationCallback: error: " + errMsg);
         throw new IllegalStateException(errMsg);
      } else {
         PasswordValidationCallback pvc = (PasswordValidationCallback)cb;
         AuthenticatedSubject subject = null;
         if (pvc.getUsername() != null && pvc.getUsername().trim().length() != 0) {
            if (pvc.getPassword() != null && pvc.getPassword().length != 0) {
               if (Debug.isWorkEnabled()) {
                  Debug.work("processPasswordValidationCallbackk: will authticate: username: " + pvc.getUsername() + "; password len: " + pvc.getPassword().length);
               }

               subject = this.securityHelper.authenticate(pvc.getUsername(), pvc.getPassword(), kernelId);
            } else {
               Debug.work("processPasswordValidationCallback: error: must have valid password: [" + (pvc.getPassword() == null ? null : "len=" + pvc.getPassword().length) + "]");
            }
         } else {
            Debug.work("processPasswordValidationCallback: error: must have valid username: [" + pvc.getUsername() + "]");
         }

         if (subject != null) {
            pvc.setResult(true);
            pvc.getSubject().getPrivateCredentials().add(subject);
            if (Debug.isWorkEnabled()) {
               Debug.work("processPasswordValidationCallbackk: ok: authenticated as " + subject);
            }
         }

      }
   }

   public void setupAsWLSUser(Subject executionSubject, String username) throws LoginException {
      if (Debug.isWorkEnabled()) {
         Debug.work("setupAsWLSUser: old executionSubject:" + executionSubject + "; username:" + username);
      }

      executionSubject.getPrivateCredentials().clear();
      AuthenticatedSubject s = this.securityHelper.getAuthenticatedSubject(username, kernelId);
      executionSubject.getPrivateCredentials().add(s);
   }

   public void setupAsAnonymous(Subject executionSubject) {
      if (Debug.isWorkEnabled()) {
         Debug.work("setupAsAnonymous: old executionSubject:" + executionSubject);
      }

      executionSubject.getPrivateCredentials().clear();
      AuthenticatedSubject s = this.securityHelper.getAnonymousSubject();
      executionSubject.getPrivateCredentials().add(s);
   }
}
