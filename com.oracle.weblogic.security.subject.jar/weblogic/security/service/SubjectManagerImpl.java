package weblogic.security.service;

import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Random;
import javax.security.auth.Subject;
import weblogic.kernel.AuditableThread;
import weblogic.kernel.KernelStatus;
import weblogic.security.SecurityLogger;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.principal.WLSKernelIdentity;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.DelegatingSubjectStack;
import weblogic.security.subject.SubjectManager;
import weblogic.utils.collections.ArraySet;

public class SubjectManagerImpl extends SubjectManager {
   private static AbstractSubject kernelIdentity = initializeKernelID();
   static WLSKernelIdentity kernelPrincipal = null;
   private static boolean usingJava2Security = false;
   private static boolean java2SecurityModeSet = false;
   private static AbstractSubject defaultUser;
   private static final boolean isTracing = false;
   private DelegatingSubjectStack stack = new DelegatingSubjectStack();

   public AbstractSubject getCurrentSubject(AbstractSubject kernelId) {
      AbstractSubject subject = this.stack.getCurrentSubject(kernelId);
      if (subject == null) {
         if (KernelStatus.isServer() && Thread.currentThread() instanceof AuditableThread) {
            subject = kernelId;
         } else if (defaultUser != null && !KernelStatus.isServer()) {
            subject = defaultUser;
         } else {
            subject = AuthenticatedSubject.ANON;
         }
      }

      return (AbstractSubject)subject;
   }

   public AbstractSubject getCurrentSubject(AbstractSubject kernelId, AuditableThread auditableThread) {
      AbstractSubject subject = this.stack.getCurrentSubject(kernelId, auditableThread);
      if (subject == null) {
         if (KernelStatus.isServer()) {
            subject = kernelId;
         } else {
            subject = AuthenticatedSubject.ANON;
         }
      }

      return (AbstractSubject)subject;
   }

   public void pushSubject(AbstractSubject kernelIdentity, AbstractSubject userIdentity) {
      this.stack.pushSubject(kernelIdentity, userIdentity);
   }

   public void popSubject(AbstractSubject kernelIdentity) {
      this.stack.popSubject(kernelIdentity);
   }

   public int getSize() {
      return this.stack.getSize();
   }

   protected AbstractSubject getKernelIdentity() {
      this.checkKernelPermission();
      return kernelIdentity;
   }

   protected AbstractSubject createAbstractSubject(Subject subject) {
      return new AuthenticatedSubject(subject);
   }

   public void checkKernelPermission() {
      if (usingJava2Security) {
         try {
            AccessController.checkPermission(KERNEL_PERM);
         } catch (final AccessControlException var2) {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  SecurityLogger.logKernelPermissionFailure(var2);
                  return null;
               }
            });
            throw var2;
         }
      }

   }

   public void checkKernelIdentity(AbstractSubject s) {
      if (!this.isKernelIdentity(s)) {
         throw new NotAuthorizedRuntimeException(SecurityLogger.getSubjectIsNotTheKernelIdentity(s == null ? "<null>" : s.toString()));
      }
   }

   public boolean isKernelIdentity(AbstractSubject s) {
      return s == kernelIdentity;
   }

   public static void setJava2SecurityMode(boolean mode) {
      if (java2SecurityModeSet) {
         throw new IllegalStateException(SecurityLogger.getCanNotCallSetJava2SecurityMoreThanOnce());
      } else {
         java2SecurityModeSet = true;
         usingJava2Security = mode;
      }
   }

   public static void setDefaultUser(AbstractSubject user) {
      if (defaultUser == null) {
         defaultUser = user;
      }

   }

   public static AbstractSubject getDefaultUser() {
      return (AbstractSubject)(defaultUser == null ? AuthenticatedSubject.ANON : defaultUser);
   }

   public static void resetDefaultUser() {
      defaultUser = null;
   }

   protected static AbstractSubject initializeKernelID() {
      Random random = new Random();
      int valInt = random.nextInt();
      String valStr = Integer.toString(valInt);
      WLSKernelIdentity kID = new WLSKernelIdentity(valStr);
      ArraySet set = new ArraySet();
      set.add(kID);
      kernelPrincipal = kID;
      return new AuthenticatedSubject(true, set);
   }

   public static synchronized boolean ensureInitialized() {
      if (!subjectManagerInstalled()) {
         setSubjectManager(new SubjectManagerImpl());
         return false;
      } else {
         return true;
      }
   }

   public String getSubjectName(AbstractSubject subject) {
      return subject instanceof AuthenticatedSubject ? SubjectUtils.getUsername((AuthenticatedSubject)subject) : super.getSubjectName(subject);
   }

   public AbstractSubject getAnonymousSubject() {
      return AuthenticatedSubject.ANON;
   }
}
