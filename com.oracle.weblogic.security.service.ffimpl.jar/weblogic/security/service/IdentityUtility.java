package weblogic.security.service;

import com.bea.common.security.service.Identity;
import java.security.AccessController;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.internal.WLSIdentityImpl;

public class IdentityUtility {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private IdentityUtility() {
   }

   public static Identity authenticatedSubjectToIdentity(AuthenticatedSubject subject) {
      if (subject == null) {
         return null;
      } else {
         AuthenticatedSubject sealedSubject = SecurityServiceManager.seal(kernelId, subject);
         return new WLSIdentityImpl(sealedSubject);
      }
   }

   public static AuthenticatedSubject identityToAuthenticatedSubject(Identity identity) {
      if (identity == null) {
         return null;
      } else if (!(identity instanceof WLSIdentityImpl)) {
         throw new IllegalArgumentException(SecurityLogger.getNotInstanceof("WLSIdentityImpl"));
      } else {
         WLSIdentityImpl wlsIdentity = (WLSIdentityImpl)identity;
         return wlsIdentity.getAuthenticatedSubject();
      }
   }
}
