package weblogic.work.concurrent;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.work.ServerWorkAdapter;
import weblogic.work.WorkManager;
import weblogic.work.concurrent.future.ManagedScheduledFutureImpl;
import weblogic.work.concurrent.spi.DaemonThreadManager;
import weblogic.work.concurrent.spi.DaemonThreadTask;
import weblogic.work.concurrent.spi.RejectException;
import weblogic.work.concurrent.spi.ServiceShutdownException;

public class ScheduledWorkManager implements WorkManager {
   private static DebugLogger loggable = DebugLogger.getDebugLogger("DebugConcurrentMSES");
   private final WorkManager workManager;
   private final DaemonThreadManager daemonThreadManager;

   public ScheduledWorkManager(WorkManager workManager, DaemonThreadManager daemonThreadManager) {
      this.workManager = workManager;
      this.daemonThreadManager = daemonThreadManager;
   }

   public boolean executeIfIdle(Runnable arg0) {
      throw new UnsupportedOperationException();
   }

   public String getApplicationName() {
      return this.workManager.getApplicationName();
   }

   public int getConfiguredThreadCount() {
      return this.workManager.getConfiguredThreadCount();
   }

   public String getModuleName() {
      return this.workManager.getModuleName();
   }

   public String getName() {
      return this.workManager.getName();
   }

   public int getQueueDepth() {
      throw new UnsupportedOperationException();
   }

   public int getType() {
      return this.workManager.getType();
   }

   public boolean isInternal() {
      throw new UnsupportedOperationException();
   }

   public boolean isThreadOwner(Thread arg0) {
      throw new UnsupportedOperationException();
   }

   public void schedule(Runnable work) {
      Timer timer = (Timer)work;
      if (!timer.isStopped() && !timer.isCancelled()) {
         TimerListener listener = timer.getListener();
         if (!(listener instanceof ManagedScheduledFutureImpl)) {
            String actualClass = listener == null ? "NULL" : listener.getClass().getName();
            IllegalStateException ex = new IllegalStateException(String.format("unexpected TimerListener implementation class %s, expect %s ", actualClass, ManagedScheduledFutureImpl.class.getName()));
            if (loggable.isDebugEnabled()) {
               loggable.debug(ex.getMessage(), ex);
            }

            throw ex;
         } else {
            ManagedScheduledFutureImpl future = (ManagedScheduledFutureImpl)listener;
            DaemonThreadTask daemonThreadTask = future.getDaemonThreadTask();
            if (daemonThreadTask != null) {
               try {
                  if (daemonThreadTask.getThread() == null) {
                     this.daemonThreadManager.createAndStart(daemonThreadTask);
                  }

                  if (loggable.isDebugEnabled()) {
                     loggable.debug(String.format("ScheduledWorkManager run daemon task %s with listener %s", work.getClass(), listener.toString()));
                  }

                  daemonThreadTask.addSubTask(work);
               } catch (ServiceShutdownException var7) {
                  future.doCancel(true, var7.getMessage());
                  work.run();
               } catch (RejectException var8) {
                  future.reject(var8.getMessage());
                  work.run();
               }

            } else {
               if (loggable.isDebugEnabled()) {
                  loggable.debug(String.format("ScheduledWorkManager run task %s with listener %s", work.getClass(), listener.toString()));
               }

               if (future.setRunnable(work)) {
                  this.workManager.schedule(new ScheduledWork(future));
               } else {
                  this.workManager.schedule(future);
               }

            }
         }
      } else {
         work.run();
      }
   }

   public boolean scheduleIfBusy(Runnable arg0) {
      throw new UnsupportedOperationException();
   }

   public void setInternal() {
      throw new UnsupportedOperationException();
   }

   private static class ScheduledWork extends ServerWorkAdapter implements ConcurrentWork {
      private final ManagedScheduledFutureImpl future;

      public ScheduledWork(ManagedScheduledFutureImpl future) {
         this.future = future;
      }

      public void run() {
         this.future.run();
      }

      public Runnable overloadAction(String reason) {
         return this.future.overloadAction(reason);
      }

      public Runnable cancel(String reason) {
         return this.future.cancel(reason);
      }

      public void release() {
         this.future.release();
      }

      protected AuthenticatedSubject getAuthenticatedSubject() {
         return this.future.getAuthenticatedSubject();
      }

      public ComponentInvocationContext getSubmittingCICInSharing() {
         return this.future.getSubmittingCICInSharing();
      }
   }
}
