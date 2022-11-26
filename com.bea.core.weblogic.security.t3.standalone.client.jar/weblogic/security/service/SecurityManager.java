package weblogic.security.service;

import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.kernel.AuditableThread;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

public class SecurityManager {
   public static AuthenticatedSubject getCurrentSubject(AuthenticatedSubject kernelId) {
      AbstractSubject as = SubjectManager.getSubjectManager().getCurrentSubject(kernelId);
      if (as == null) {
         return null;
      } else {
         return as instanceof AuthenticatedSubject ? (AuthenticatedSubject)as : new AuthenticatedSubject(as.getSubject());
      }
   }

   public static AuthenticatedSubject getCurrentSubject(AuthenticatedSubject kernelId, AuditableThread auditableThread) {
      AbstractSubject as = SubjectManager.getSubjectManager().getCurrentSubject(kernelId, auditableThread);
      if (as == null) {
         return null;
      } else {
         return as instanceof AuthenticatedSubject ? (AuthenticatedSubject)as : new AuthenticatedSubject(as.getSubject());
      }
   }

   public static void pushSubject(AuthenticatedSubject kernelIdentity, AuthenticatedSubject userIdentity) {
      SubjectManager.getSubjectManager().pushSubject(kernelIdentity, userIdentity);
   }

   public static void popSubject(AuthenticatedSubject kernelIdentity) {
      SubjectManager.getSubjectManager().popSubject(kernelIdentity);
   }

   public static Object runAs(AuthenticatedSubject kernelIdentity, AuthenticatedSubject userIdentity, PrivilegedAction action) {
      return userIdentity.doAs(kernelIdentity, (PrivilegedAction)action);
   }

   public static Object runAs(AuthenticatedSubject kernelIdentity, AuthenticatedSubject userIdentity, PrivilegedExceptionAction action) throws PrivilegedActionException {
      return userIdentity.doAs(kernelIdentity, (PrivilegedExceptionAction)action);
   }

   static AuthenticatedSubject getKernelIdentity() {
      AbstractSubject as = (AbstractSubject)SubjectManager.getKernelIdentityAction().run();
      if (as == null) {
         return null;
      } else {
         return as instanceof AuthenticatedSubject ? (AuthenticatedSubject)as : new AuthenticatedSubject(as.getSubject());
      }
   }

   public static void checkKernelPermission() {
      SubjectManager.getSubjectManager().checkKernelPermission();
   }

   public static boolean isKernelIdentity(AuthenticatedSubject s) {
      return SubjectManager.getSubjectManager().isKernelIdentity(s);
   }

   public static void checkKernelIdentity(AuthenticatedSubject s) {
      SubjectManager.getSubjectManager().checkKernelIdentity(s);
   }
}
