package weblogic.jms.client;

import java.security.AccessController;
import javax.naming.Context;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.JMSEnvironment;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

public class JMSContext {
   private static final AbstractSubject KERNEL_ID = (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private static final SubjectManager subjectManager = SubjectManager.getSubjectManager();
   protected ClassLoader classLoader;
   protected Context jndiContext;
   protected AbstractSubject subject;
   protected ComponentInvocationContext cic;
   private static boolean isWLSServerSet;
   private static boolean isWLSServer;

   public JMSContext() {
      this(false);
   }

   public JMSContext(boolean isInbound) {
      this.cic = null;
      AbstractSubject sub = null;
      if (isInbound) {
         sub = subjectManager.getAnonymousSubject();
      } else {
         sub = subjectManager.getCurrentSubject(KERNEL_ID);
      }

      if (KernelStatus.isServer()) {
         ComponentInvocationContext currentCic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         this.setComponentInvocationContext(currentCic);
      }

      this.init(sub);
   }

   private void init(AbstractSubject subject) {
      this.subject = subject;
      if (KernelStatus.isServer()) {
         this.classLoader = Thread.currentThread().getContextClassLoader();
         if (isWLSServer()) {
            this.jndiContext = this.getLocalJNDIContext();
         }
      }

   }

   private static synchronized boolean isWLSServer() {
      if (isWLSServerSet) {
         return isWLSServer;
      } else {
         try {
            Class.forName("weblogic.jms.common.JMSServerUtilities");
            isWLSServer = true;
         } catch (ClassNotFoundException var1) {
         }

         isWLSServerSet = true;
         return isWLSServer;
      }
   }

   public Context getJNDIContext() {
      return this.jndiContext;
   }

   public void setJNDIContext(Context c) {
      this.jndiContext = c;
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public void setClassLoader(ClassLoader cl) {
      this.classLoader = cl;
   }

   public void setComponentInvocationContext(ComponentInvocationContext cic) {
      this.cic = cic;
   }

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.cic;
   }

   public AbstractSubject getSubject() {
      return this.subject;
   }

   public void setSubject(AbstractSubject s) {
      this.subject = s;
   }

   public AbstractSubject getKernelId() {
      return KERNEL_ID;
   }

   public SubjectManager getSubjectManager() {
      return subjectManager;
   }

   public AutoCloseable pushAll() {
      subjectManager.pushSubject(KERNEL_ID, this.subject);
      if (!KernelStatus.isServer()) {
         return new AutoCloseable() {
            public void close() throws Exception {
               JMSContext.subjectManager.popSubject(JMSContext.KERNEL_ID);
            }
         };
      } else {
         JMSEnvironment.getJMSEnvironment().pushLocalJNDIContext(this.jndiContext);
         ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
         Thread.currentThread().setContextClassLoader(this.classLoader);
         ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
         return new ContextCache(oldClassLoader, mic);
      }
   }

   static JMSContext push(JMSContext jc) {
      return push((Thread)null, jc, false);
   }

   static JMSContext push(JMSContext jc, boolean pushSubject) {
      return push((Thread)null, jc, pushSubject);
   }

   static void pop(JMSContext jc) {
      pop((Thread)null, jc, false);
   }

   static void pop(JMSContext jc, boolean pushSubject) {
      pop((Thread)null, jc, pushSubject);
   }

   static JMSContext push(Thread t, JMSContext jc, boolean includeSubject) {
      if (includeSubject && jc != null) {
         pushSubject(jc.getSubject());
      }

      if (KernelStatus.isServer()) {
         Thread currentThread = t != null ? t : Thread.currentThread();
         JMSContext currentContext = new JMSContext();
         if (jc != null) {
            ClassLoader cl = jc.getClassLoader();
            if (cl != currentContext.getClassLoader()) {
               currentThread.setContextClassLoader(cl);
            }

            if (isWLSServer() && jc.getJNDIContext() != null) {
               pushLocalJNDIContext(jc.getJNDIContext());
            }
         }

         return currentContext;
      } else {
         return null;
      }
   }

   static void pop(Thread t, JMSContext jc, boolean includeSubject) {
      if (KernelStatus.isServer()) {
         Thread currentThread = t != null ? t : Thread.currentThread();
         if (jc != null) {
            if (isWLSServer() && jc.getJNDIContext() != null) {
               popLocalJNDIContext();
            }

            currentThread.setContextClassLoader(jc.getClassLoader());
         }
      }

      if (includeSubject) {
         popSubject();
      }

   }

   static void pushSubject(AbstractSubject userIdentity) {
      subjectManager.pushSubject(KERNEL_ID, userIdentity);
   }

   static void popSubject() {
      subjectManager.popSubject(KERNEL_ID);
   }

   private Context getLocalJNDIContext() {
      return JMSEnvironment.getJMSEnvironment().getLocalJNDIContext();
   }

   private static void pushLocalJNDIContext(Context ctx) {
      JMSEnvironment.getJMSEnvironment().pushLocalJNDIContext(ctx);
   }

   private static void popLocalJNDIContext() {
      JMSEnvironment.getJMSEnvironment().popLocalJNDIContext();
   }

   static boolean equals(JMSContext c1, JMSContext c2) {
      if (c1.getClassLoader() != c2.getClassLoader()) {
         return false;
      } else if (c1.getJNDIContext() != c2.getJNDIContext()) {
         return false;
      } else {
         return c1.getSubject() == c2.getSubject();
      }
   }

   final class ContextCache implements AutoCloseable {
      ClassLoader savedClassLoader;
      ManagedInvocationContext savedMic;

      ContextCache(ClassLoader c, ManagedInvocationContext m) {
         this.savedClassLoader = c;
         this.savedMic = m;
      }

      public void close() {
         JMSContext.subjectManager.popSubject(JMSContext.KERNEL_ID);
         JMSEnvironment.getJMSEnvironment().popLocalJNDIContext();
         Thread.currentThread().setContextClassLoader(this.savedClassLoader);
         this.savedMic.close();
      }
   }
}
