package weblogic.security.subject;

import java.security.AccessController;
import java.security.Permission;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.Subject;
import weblogic.kernel.AuditableThread;

public abstract class SubjectManager implements SubjectStack {
   public static final String KERNEL_USERNAME = "<WLS Kernel>";
   public static final String ANONYMOUS_USERNAME = initAnonymousUsername();
   private static final String DEFAULT_ANONYMOUS_USERNAME = "<anonymous>";
   private static final GetKernelIdentityAction THE_ONE = new GetKernelIdentityAction();
   protected static final Permission KERNEL_PERM = new RuntimePermission("weblogic.kernelPermission");
   private static SubjectManager singleton;

   public static final PrivilegedAction getKernelIdentityAction() {
      return THE_ONE;
   }

   public static final Permission getKernelPermission() {
      return KERNEL_PERM;
   }

   public void checkKernelPermission() {
      AccessController.checkPermission(KERNEL_PERM);
   }

   public boolean isKernelIdentity(AbstractSubject s) {
      return s == this.getCachedKernelIdentity();
   }

   private AbstractSubject getCachedKernelIdentity() {
      return SubjectManager.KernelIdentityMaker.KERNEL_ID;
   }

   public void checkKernelIdentity(AbstractSubject s) {
      if (!this.isKernelIdentity(s)) {
         throw new SecurityException("Subject '" + (s == null ? "<null>" : s.toString()) + "' is not the kernel identity");
      }
   }

   protected abstract AbstractSubject getKernelIdentity();

   public static final SubjectManager getSubjectManager() {
      return singleton == null ? new SubjectManager() {
         protected AbstractSubject getKernelIdentity() {
            return null;
         }

         public AbstractSubject getCurrentSubject(AbstractSubject k) {
            throw new SecurityException("SubjectManager not installed");
         }

         public AbstractSubject getCurrentSubject(AbstractSubject k, AuditableThread thread) {
            throw new SecurityException("SubjectManager not installed");
         }

         public void pushSubject(AbstractSubject k, AbstractSubject u) {
            throw new SecurityException("SubjectManager not installed");
         }

         public void popSubject(AbstractSubject k) {
            throw new SecurityException("SubjectManager not installed");
         }

         protected AbstractSubject createAbstractSubject(Subject subject) {
            throw new SecurityException("SubjectManager not installed");
         }

         public int getSize() {
            throw new SecurityException("SubjectManager not installed");
         }

         public AbstractSubject getAnonymousSubject() {
            throw new SecurityException("SubjectManager not installed");
         }
      } : singleton;
   }

   protected static final boolean subjectManagerInstalled() {
      return singleton != null;
   }

   public static final void setSubjectManager(SubjectManager manager) {
      if (singleton != null) {
         AccessController.checkPermission(KERNEL_PERM);
      }

      singleton = manager;
   }

   public static final PrivilegedAction setSubjectManagerAction(SubjectManager manager) {
      return new SetSubjectManagerAction(manager);
   }

   public Object runAs(Subject userIdentity, PrivilegedAction action) {
      if (userIdentity == null) {
         throw new SecurityException("Null user identity");
      } else {
         return this.getAbstractSubject(userIdentity).doAs(this.getCachedKernelIdentity(), action);
      }
   }

   public Object runAs(Subject userIdentity, PrivilegedExceptionAction action) throws PrivilegedActionException {
      if (userIdentity == null) {
         throw new SecurityException("Null user identity");
      } else {
         return this.getAbstractSubject(userIdentity).doAs(this.getCachedKernelIdentity(), action);
      }
   }

   protected abstract AbstractSubject createAbstractSubject(Subject var1);

   private final AbstractSubject getAbstractSubject(final Subject subject) {
      return (AbstractSubject)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            Iterator i = subject.getPrivateCredentials().iterator();

            Object obj;
            do {
               if (!i.hasNext()) {
                  return SubjectManager.this.createAbstractSubject(subject);
               }

               obj = i.next();
            } while(!(obj instanceof AbstractSubject));

            return obj;
         }
      });
   }

   public String getSubjectName(AbstractSubject subject) {
      if (subject == null) {
         throw new AssertionError("subject is null");
      } else if (this.isKernelIdentity(subject)) {
         return "<WLS Kernel>";
      } else {
         Principal user = getOnePrincipal(subject, SubjectProxy.class);
         return user == null ? ANONYMOUS_USERNAME : user.getName();
      }
   }

   public static Principal getOnePrincipal(AbstractSubject subject, Class type) {
      return getOnePrincipalClass(subject.getPrincipals(), type);
   }

   public static Principal getOnePrincipalClass(Set principals, Class type) {
      Iterator i = principals.iterator();

      Principal p;
      do {
         if (!i.hasNext()) {
            return null;
         }

         p = (Principal)i.next();
      } while(!type.isAssignableFrom(p.getClass()));

      return p;
   }

   private static String initAnonymousUsername() {
      try {
         return System.getProperty("weblogic.security.anonymousUserName", "<anonymous>");
      } catch (Exception var1) {
         return "<anonymous>";
      }
   }

   public abstract AbstractSubject getAnonymousSubject();

   private static final class SetSubjectManagerAction implements PrivilegedAction {
      private final SubjectManager manager;

      private SetSubjectManagerAction(SubjectManager manager) {
         this.manager = manager;
      }

      public final Object run() {
         SubjectManager.setSubjectManager(this.manager);
         return null;
      }

      // $FF: synthetic method
      SetSubjectManagerAction(SubjectManager x0, Object x1) {
         this(x0);
      }
   }

   private static final class GetKernelIdentityAction implements PrivilegedAction {
      private GetKernelIdentityAction() {
      }

      public final Object run() {
         return SubjectManager.getSubjectManager().getKernelIdentity();
      }

      // $FF: synthetic method
      GetKernelIdentityAction(Object x0) {
         this();
      }
   }

   private static class KernelIdentityMaker {
      private static AbstractSubject KERNEL_ID = (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   }
}
