package weblogic.security.service;

import java.nio.charset.Charset;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.kernel.AuditableThread;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.SubjectManager;

public class SecurityManager {
   private static final boolean initialized = SubjectManagerImpl.ensureInitialized();
   private static Charset csUTF8 = Charset.forName("UTF-8");
   private static Charset cs88591 = Charset.forName("ISO-8859-1");
   private static java.lang.SecurityManager bootSecurityMgr;
   private static java.lang.SecurityManager userCodeSecurityMgr;
   private static int currentUserCodeCallouts;

   public static AuthenticatedSubject getCurrentSubject(AuthenticatedSubject kernelId) {
      return (AuthenticatedSubject)SubjectManager.getSubjectManager().getCurrentSubject(kernelId);
   }

   public static AuthenticatedSubject getCurrentSubject(AuthenticatedSubject kernelId, AuditableThread auditableThread) {
      return (AuthenticatedSubject)SubjectManager.getSubjectManager().getCurrentSubject(kernelId, auditableThread);
   }

   public static void pushSubject(AuthenticatedSubject kernelIdentity, AuthenticatedSubject userIdentity) {
      SubjectManager.getSubjectManager().pushSubject(kernelIdentity, userIdentity);
   }

   public static void popSubject(AuthenticatedSubject kernelIdentity) {
      SubjectManager.getSubjectManager().popSubject(kernelIdentity);
   }

   public static int getSubjectStackSize() {
      return SubjectManager.getSubjectManager().getSize();
   }

   public static Object runAs(AuthenticatedSubject kernelIdentity, AuthenticatedSubject userIdentity, PrivilegedAction action) {
      if (userIdentity == null) {
         throw new SecurityException(SecurityLogger.getNullUserIdentity());
      } else {
         return userIdentity.doAs(kernelIdentity, (PrivilegedAction)action);
      }
   }

   public static Object runAs(AuthenticatedSubject kernelIdentity, AuthenticatedSubject userIdentity, PrivilegedExceptionAction action) throws PrivilegedActionException {
      if (userIdentity == null) {
         throw new SecurityException(SecurityLogger.getNullUserIdentity());
      } else {
         return userIdentity.doAs(kernelIdentity, (PrivilegedExceptionAction)action);
      }
   }

   public static Object runAsForUserCode(AuthenticatedSubject kernelIdentity, AuthenticatedSubject userIdentity, PrivilegedAction action) {
      if (userIdentity == null) {
         throw new SecurityException(SecurityLogger.getNullUserIdentity());
      } else if (userCodeSecurityMgr != null) {
         setUserCodeSecurityMgr();

         Object var3;
         try {
            var3 = userIdentity.doAs(kernelIdentity, (PrivilegedAction)action);
         } finally {
            restoreSecurityMgr();
         }

         return var3;
      } else {
         return userIdentity.doAs(kernelIdentity, (PrivilegedAction)action);
      }
   }

   public static Object runAsForUserCode(AuthenticatedSubject kernelIdentity, AuthenticatedSubject userIdentity, PrivilegedExceptionAction action) throws PrivilegedActionException {
      if (userIdentity == null) {
         throw new SecurityException(SecurityLogger.getNullUserIdentity());
      } else if (userCodeSecurityMgr != null) {
         setUserCodeSecurityMgr();

         Object var3;
         try {
            var3 = userIdentity.doAs(kernelIdentity, (PrivilegedExceptionAction)action);
         } finally {
            restoreSecurityMgr();
         }

         return var3;
      } else {
         return userIdentity.doAs(kernelIdentity, (PrivilegedExceptionAction)action);
      }
   }

   static AuthenticatedSubject getKernelIdentity() {
      SubjectManager.getSubjectManager();
      return (AuthenticatedSubject)SubjectManager.getKernelIdentityAction().run();
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

   public static void setJava2SecurityMode(boolean mode) {
      SubjectManagerImpl var10000 = (SubjectManagerImpl)SubjectManager.getSubjectManager();
      SubjectManagerImpl.setJava2SecurityMode(mode);
   }

   public static void setDefaultUser(AuthenticatedSubject user) {
      SubjectManagerImpl var10000 = (SubjectManagerImpl)SubjectManager.getSubjectManager();
      SubjectManagerImpl.setDefaultUser(user);
   }

   public static AuthenticatedSubject getDefaultUser() {
      SubjectManagerImpl var10000 = (SubjectManagerImpl)SubjectManager.getSubjectManager();
      return (AuthenticatedSubject)SubjectManagerImpl.getDefaultUser();
   }

   public static synchronized void setSecurityManagersForBoot(AuthenticatedSubject kernelId, java.lang.SecurityManager bootSecMgr, java.lang.SecurityManager userSecMgr) {
      checkKernelIdentity(kernelId);
      bootSecurityMgr = bootSecMgr;
      userCodeSecurityMgr = userSecMgr;
   }

   public static synchronized void clearSecurityManagersForBoot(AuthenticatedSubject kernelId) {
      checkKernelIdentity(kernelId);
      bootSecurityMgr = null;
      userCodeSecurityMgr = null;
   }

   private static synchronized void setUserCodeSecurityMgr() {
      if (currentUserCodeCallouts <= 0 && userCodeSecurityMgr != null) {
         System.setSecurityManager(userCodeSecurityMgr);
      }

      ++currentUserCodeCallouts;
   }

   private static synchronized void restoreSecurityMgr() {
      --currentUserCodeCallouts;
      if (currentUserCodeCallouts <= 0 && bootSecurityMgr != null) {
         System.setSecurityManager(bootSecurityMgr);
      }

   }
}
