package weblogic.work.j2ee;

import commonj.work.Work;
import commonj.work.WorkItem;
import commonj.work.WorkListener;
import commonj.work.WorkRejectedException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.protocol.LocalServerIdentity;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.AbstractSubject;
import weblogic.utils.LocatorUtilities;
import weblogic.work.CICCapturingWork;
import weblogic.work.DaemonTaskWorkManager;
import weblogic.work.InheritableThreadContext;
import weblogic.work.PartitionUtility;
import weblogic.work.ServerWorkAdapter;
import weblogic.work.ShutdownCallback;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;
import weblogic.work.api.WorkManagerCollector;
import weblogic.work.api.WorkManagerCollectorGenerator;
import weblogic.work.commonj.CommonjWorkManagerImpl;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.spi.WorkContextMapInterceptor;

public final class J2EEWorkManager extends CommonjWorkManagerImpl {
   private DaemonTaskWorkManager daemonTaskWM;
   private static final J2EEWorkManager DEFAULT = new J2EEWorkManager(WorkManagerFactory.getInstance().getDefault());

   private J2EEWorkManager(WorkManager workManager) {
      super(workManager);
   }

   public static commonj.work.WorkManager getDefault() {
      return PartitionUtility.getCurrentComponentInvocationContext().isGlobalRuntime() ? DEFAULT : new J2EEWorkManager(WorkManagerFactory.getInstance().getDefault());
   }

   public static commonj.work.WorkManager getDefault(String appName) {
      WorkManagerCollectorGenerator generator = (WorkManagerCollectorGenerator)LocatorUtilities.getService(WorkManagerCollectorGenerator.class);
      WorkManagerCollector collector = generator.getWorkManagerCollector(appName);
      return collector == null ? null : new J2EEWorkManager(collector.getWorkManagerCollection().getDefault());
   }

   public static commonj.work.WorkManager get(String appName, String moduleName, String wmName) {
      WorkManager wm = WorkManagerFactory.getInstance().find(wmName, appName, moduleName);
      return new J2EEWorkManager(wm);
   }

   public static commonj.work.WorkManager get(String wmName) {
      WorkManager wm = WorkManagerFactory.getInstance().find(wmName);
      return new J2EEWorkManager(wm);
   }

   public WorkItem schedule(Work e, WorkListener wl) {
      if (e == null) {
         throw new IllegalArgumentException("null work instance");
      } else {
         WorkStatus status = new WorkStatus(e);
         if (wl != null) {
            wl.workAccepted(status);
         }

         if (e.isDaemon()) {
            if (this.daemonTaskWM == null) {
               synchronized(this) {
                  if (this.daemonTaskWM == null) {
                     this.daemonTaskWM = new DaemonTaskWorkManager(this.getDelegate());
                  }
               }

               this.daemonTaskWM.start();
            }

            this.daemonTaskWM.schedule(new WorkWithListener(e, wl, status, InheritableThreadContext.getContext()));
         } else {
            this.workManager.schedule(new WorkWithListener(e, wl, status, InheritableThreadContext.getContext()));
         }

         return status;
      }
   }

   public void shutdown(ShutdownCallback callback) {
      if (this.daemonTaskWM != null) {
         this.daemonTaskWM.shutdown(callback);
      }

   }

   private static final class WorkStatus extends CommonjWorkManagerImpl.WorkStatus {
      private static final long VM_DIFFERENTIATOR = LocalServerIdentity.getIdentity().getTransientIdentity().getIdentityAsLong();
      private long serverIdentity;

      private WorkStatus(Work work) {
         super(work);
         this.serverIdentity = VM_DIFFERENTIATOR;
      }

      public String toString() {
         return "[" + VM_DIFFERENTIATOR + "][" + this.counter + "] executing: " + this.work;
      }

      public int hashCode() {
         return (int)(this.counter ^ VM_DIFFERENTIATOR);
      }

      public int compareTo(Object o) {
         try {
            return this.compare((WorkStatus)o);
         } catch (ClassCastException var3) {
            return -1;
         }
      }

      public int compare(WorkStatus id) {
         if (this.counter > id.counter) {
            return 1;
         } else if (this.counter < id.counter) {
            return -1;
         } else if (this.serverIdentity > id.serverIdentity) {
            return 1;
         } else {
            return this.serverIdentity < id.serverIdentity ? -1 : 0;
         }
      }

      public boolean equals(Object o) {
         if (!(o instanceof WorkStatus)) {
            return false;
         } else {
            WorkStatus id = (WorkStatus)o;
            return this.counter == id.counter && this.serverIdentity == id.serverIdentity;
         }
      }

      // $FF: synthetic method
      WorkStatus(Work x0, Object x1) {
         this(x0);
      }
   }

   private static final class WorkWithListener extends ServerWorkAdapter implements ComponentRequest, CICCapturingWork {
      private final Work work;
      private final WorkListener listener;
      private final WorkStatus status;
      private final InheritableThreadContext inheritableThreadContext;
      private WorkContextMapInterceptor workAreaContext;
      private final ComponentInvocationContext cic;

      private WorkWithListener(Work work, WorkListener listener, WorkStatus status, InheritableThreadContext context) {
         this.work = work;
         this.listener = listener;
         this.status = status;
         this.inheritableThreadContext = context;
         this.cic = PartitionUtility.getCurrentComponentInvocationContext();
         this.workAreaContext = WorkContextHelper.getWorkContextHelper().getInterceptor().copyThreadContexts(2);
      }

      protected AuthenticatedSubject getAuthenticatedSubject() {
         AbstractSubject subject = this.inheritableThreadContext.getSubject();
         return subject instanceof AuthenticatedSubject ? (AuthenticatedSubject)subject : null;
      }

      private boolean isAdminRequest() {
         AuthenticatedSubject as = this.getAuthenticatedSubject();
         return as != null && SubjectUtils.doesUserHaveAnyAdminRoles(as);
      }

      public Runnable overloadAction(final String reason) {
         return this.isAdminRequest() ? null : new Runnable() {
            public void run() {
               WorkWithListener.this.status.setType(2);
               WorkWithListener.this.status.setThrowable(new WorkRejectedException(reason));

               try {
                  if (WorkWithListener.this.listener != null) {
                     WorkWithListener.this.listener.workRejected(WorkWithListener.this.status);
                  }
               } catch (Throwable var2) {
               }

            }
         };
      }

      public Runnable cancel(String reason) {
         return this.status.getStatus() != 1 ? null : this.overloadAction(reason);
      }

      public void run() {
         try {
            if (this.inheritableThreadContext != null) {
               this.inheritableThreadContext.push();
            }

            if (this.workAreaContext != null) {
               WorkContextHelper.getWorkContextHelper().getInterceptor().restoreThreadContexts(this.workAreaContext);
            }

            this.status.setType(3);

            try {
               if (this.listener != null) {
                  this.listener.workStarted(this.status);
               }
            } catch (Throwable var11) {
            }

            try {
               this.work.run();
            } catch (Throwable var10) {
               this.status.setThrowable(var10);
            }
         } finally {
            this.status.setType(4);

            try {
               if (this.listener != null) {
                  this.listener.workCompleted(this.status);
               }
            } catch (Throwable var9) {
            }

            if (this.inheritableThreadContext != null) {
               this.inheritableThreadContext.pop();
            }

         }

      }

      public void release() {
         this.work.release();
      }

      public ComponentInvocationContext getComponentInvocationContext() {
         return this.cic;
      }

      public ComponentInvocationContext getSubmittingThreadCIC() {
         return this.cic;
      }

      // $FF: synthetic method
      WorkWithListener(Work x0, WorkListener x1, WorkStatus x2, InheritableThreadContext x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
