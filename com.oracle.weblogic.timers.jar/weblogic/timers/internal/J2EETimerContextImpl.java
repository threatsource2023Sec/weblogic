package weblogic.timers.internal;

import java.security.AccessControlException;
import java.security.AccessController;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.kernel.KernelStatus;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

class J2EETimerContextImpl implements TimerContext {
   private static final AbstractSubject kernelId = getKernelIdentity();
   private final SubjectManager subjectManager = SubjectManager.getSubjectManager();
   private ClassLoader classLoader;
   private ClassLoader savedClassLoader;
   private Context context;
   private AbstractSubject subject;
   private int flags;
   private static final int SUBJECT_PUSHED = 1;
   private static final int CONTEXT_PUSHED = 2;
   private static final int CLASS_LOADER_PUSHED = 4;

   private static final AbstractSubject getKernelIdentity() {
      try {
         return (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      } catch (AccessControlException var1) {
         return null;
      }
   }

   J2EETimerContextImpl() {
      this.subject = this.subjectManager.getCurrentSubject(kernelId);
      if (KernelStatus.isServer()) {
         this.classLoader = Thread.currentThread().getContextClassLoader();

         try {
            javaURLContextFactory contextFactory = new javaURLContextFactory();
            this.context = (Context)contextFactory.getObjectInstance((Object)null, (Name)null, (Context)null, (Hashtable)null);
         } catch (NamingException var2) {
         }
      }

   }

   public void push() {
      if (this.flags != 0) {
         throw new IllegalStateException();
      } else {
         this.subjectManager.pushSubject(kernelId, this.subject);
         this.flags |= 1;
         if (KernelStatus.isServer()) {
            javaURLContextFactory.pushContext(this.context);
            this.flags |= 2;
            Thread thread = Thread.currentThread();
            this.savedClassLoader = thread.getContextClassLoader();
            thread.setContextClassLoader(this.classLoader);
            this.flags |= 4;
         }

      }
   }

   public void pop() {
      if ((this.flags & 1) != 0) {
         this.subjectManager.popSubject(kernelId);
         this.flags &= -2;
      }

      if ((this.flags & 2) != 0) {
         javaURLContextFactory.popContext();
         this.flags &= -3;
      }

      if ((this.flags & 4) != 0) {
         Thread.currentThread().setContextClassLoader(this.savedClassLoader);
         this.flags &= -5;
      }

   }
}
