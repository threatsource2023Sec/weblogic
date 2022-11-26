package weblogic.connector.security;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.security.auth.login.LoginException;
import weblogic.connector.common.Debug;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SupplementalPolicyObject;
import weblogic.security.service.SecurityService.ServiceType;

public class WLSSecurityHelper implements SecurityHelper {
   public AuthenticatedSubject getCurrentSubject(AuthenticatedSubject kernelId) {
      return SecurityServiceManager.getCurrentSubject(kernelId);
   }

   public void pushSubject(AuthenticatedSubject kernelId, AuthenticatedSubject subject) {
      SecurityManager.pushSubject(kernelId, subject);
   }

   public void popSubject(AuthenticatedSubject kernelId) {
      SecurityManager.popSubject(kernelId);
   }

   public AuthenticatedSubject getAuthenticatedSubject(final String username, AuthenticatedSubject kernelId) throws LoginException {
      AuthenticatedSubject subject = null;
      final PrincipalAuthenticator pa = this.getPrincipalAuthenticator(kernelId);

      try {
         subject = (AuthenticatedSubject)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public AuthenticatedSubject run() throws Exception {
               return pa.impersonateIdentity(username);
            }
         });
         if (Debug.isWorkEnabled()) {
            Debug.work("get AuthenticatedSubject ok for " + username + " to subject " + subject);
         }

         return subject;
      } catch (PrivilegedActionException var7) {
         LoginException le = new LoginException("Failed to get AuthenticatedSubject for " + username);
         le.initCause(var7);
         throw le;
      }
   }

   public AuthenticatedSubject getAnonymousSubject() {
      return SubjectUtils.getAnonymousSubject();
   }

   public boolean isUserAnAdministrator(AuthenticatedSubject subject) {
      return SubjectUtils.isUserAnAdministrator(subject);
   }

   public boolean isUserAnonymous(AuthenticatedSubject subject) {
      return SubjectUtils.isUserAnonymous(subject);
   }

   public boolean isKernelIdentity(AuthenticatedSubject subject) {
      return SecurityServiceManager.isKernelIdentity(subject);
   }

   public boolean isAdminPrivilegeEscalation(AuthenticatedSubject currentSubject, AuthenticatedSubject requestedSubject) {
      return SubjectUtils.isAdminPrivilegeEscalation(currentSubject, requestedSubject);
   }

   public AuthenticatedSubject authenticate(String username, char[] password, AuthenticatedSubject kernelId) {
      final SimpleCallbackHandler handler = new SimpleCallbackHandler(username, new String(password));
      final PrincipalAuthenticator pa = this.getPrincipalAuthenticator(kernelId);

      try {
         AuthenticatedSubject subject = (AuthenticatedSubject)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public AuthenticatedSubject run() throws Exception {
               return pa.authenticate(handler);
            }
         });
         if (Debug.isWorkEnabled()) {
            Debug.work("authenticate ok for username " + username + " to subject " + subject);
         }

         return subject;
      } catch (Throwable var7) {
         if (Debug.isWorkEnabled()) {
            Debug.work("validation failed for username " + username, var7);
         }

         return null;
      }
   }

   public Object runAs(AuthenticatedSubject kernelId, AuthenticatedSubject userId, PrivilegedAction action) {
      return SecurityServiceManager.runAs(kernelId, userId, action);
   }

   public Object runAs(AuthenticatedSubject kernelId, AuthenticatedSubject userId, PrivilegedExceptionAction action) throws PrivilegedActionException {
      return SecurityServiceManager.runAs(kernelId, userId, action);
   }

   public void setPoliciesFromGrantStatement(AuthenticatedSubject kernelId, URL url, String permSpec) {
      SupplementalPolicyObject.setPoliciesFromGrantStatement(kernelId, url, permSpec, "CONNECTOR");
   }

   public void removePolicies(AuthenticatedSubject kernelId, URL url) {
      SupplementalPolicyObject.removePolicies(kernelId, url);
   }

   private PrincipalAuthenticator getPrincipalAuthenticator(AuthenticatedSubject kernelId) {
      PrincipalAuthenticator pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelId, "weblogicDEFAULT", ServiceType.AUTHENTICATION);
      return pa;
   }
}
