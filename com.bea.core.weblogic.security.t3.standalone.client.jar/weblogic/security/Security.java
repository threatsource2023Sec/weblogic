package weblogic.security;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.security.auth.Subject;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class Security {
   private static AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static Object runAs(Subject user, PrivilegedAction action) {
      AuthenticatedSubject as = AuthenticatedSubject.getFromSubject(user);
      return SecurityServiceManager.runAs(kernelID, as, action);
   }

   public static Object runAs(Subject user, PrivilegedExceptionAction action) throws PrivilegedActionException {
      AuthenticatedSubject as = AuthenticatedSubject.getFromSubject(user);
      return SecurityServiceManager.runAs(kernelID, as, action);
   }

   public static Subject getCurrentSubject() {
      return SecurityServiceManager.getCurrentSubject(kernelID).getSubject();
   }
}
