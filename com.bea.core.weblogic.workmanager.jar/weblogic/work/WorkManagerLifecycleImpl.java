package weblogic.work;

import weblogic.logging.Loggable;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.StackTraceUtils;

public class WorkManagerLifecycleImpl implements WorkManagerLifecycle, WorkManager {
   private static final DebugCategory debugWMService = Debug.getCategory("weblogic.workmanagerservice");
   protected int state = 3;
   protected int workCount;
   protected final WorkManager delegate;
   protected ShutdownCallback callback;
   protected boolean internal;
   protected int stuckThreadCount;

   public WorkManagerLifecycleImpl(WorkManager workManager) {
      this.delegate = workManager;
      if (workManager instanceof SelfTuningWorkManagerImpl) {
         ((SelfTuningWorkManagerImpl)workManager).setWorkManagerService(this);
      }

      if (debugEnabled()) {
         debug("-- wmlifecycle created - " + this);
      }

   }

   public String getName() {
      return this.delegate.getName();
   }

   public String getApplicationName() {
      return this.delegate.getApplicationName();
   }

   public String getModuleName() {
      return this.delegate.getModuleName();
   }

   public int getType() {
      return this.delegate.getType();
   }

   public int getConfiguredThreadCount() {
      return this.delegate.getConfiguredThreadCount();
   }

   public synchronized int getState() {
      return this.state;
   }

   public synchronized void start() {
      this.state = 1;
      this.resetCallBack();
      if (debugEnabled()) {
         debug("-- wmlifecycle - " + this + " started");
      }

   }

   private void resetCallBack() {
      this.callback = null;
   }

   public boolean isShutdown() {
      if (debugEnabled()) {
         debug("-- wmlifecycle - " + this + " is shutdown : " + (this.state == 3));
      }

      return this.state == 3;
   }

   public synchronized void shutdown(ShutdownCallback callback) {
      if (!this.internal && this.state != 3 && this.state != 2) {
         if (debugEnabled()) {
            debug("-- wmlifecycle - " + this + " shutdown with callback " + callback + "\nstack trace:\n" + StackTraceUtils.throwable2StackTrace((Throwable)null));
         }

         this.callback = callback;
         if (debugEnabled()) {
            debug("-- wmlifecycle - " + this + " has no pending txn. commencing shutdown ...");
         }

         this.state = 3;
         this.releaseExecutingRequests();
         if (!this.workPending()) {
            if (debugEnabled()) {
               debug("-- wmlifecycle - " + this + " has no pending work and no pending txn. Invoking callback");
            }

            if (callback != null) {
               callback.completed();
            }

            this.callback = null;
         }
      } else {
         if (callback != null) {
            callback.completed();
         }

      }
   }

   protected void releaseExecutingRequests() {
      RequestManager.getInstance().releaseExecutingRequestFor(this.delegate);
   }

   public void releaseExecutingRequests(WorkFilter filter) {
      RequestManager.getInstance().releaseExecutingRequestFor(this.delegate, filter);
   }

   public void forceShutdown() {
      if (debugEnabled()) {
         debug("-- wmlifecycle - " + this + " force shutdown with callback " + this.callback + "\nstack trace:\n" + StackTraceUtils.throwable2StackTrace((Throwable)null));
      }

      this.state = 3;
   }

   public void schedule(Runnable runnable) {
      if (this.permitSchedule(runnable)) {
         try {
            this.delegate.schedule(runnable);
         } finally {
            if ("direct" == this.delegate.getName() || this.getType() == 2) {
               this.workCompleted();
            }

         }
      } else {
         this.cancelWork(runnable);
      }

   }

   String getCancelMessage() {
      Loggable loggable = WorkManagerLogger.logCancelBeforeEnqueueLoggable(this.getName(), this.getApplicationName());
      loggable.log();
      return loggable.getMessage();
   }

   public boolean executeIfIdle(Runnable runnable) {
      boolean executed = false;
      if (this.permitSchedule(runnable)) {
         try {
            executed = this.delegate.executeIfIdle(runnable);
         } finally {
            if (!executed || "direct" == this.delegate.getName() || this.getType() == 2) {
               this.workCompleted();
            }

         }
      }

      return executed;
   }

   public boolean scheduleIfBusy(Runnable runnable) {
      if (this.permitSchedule(runnable)) {
         boolean isScheduled = false;

         try {
            isScheduled = this.delegate.scheduleIfBusy(runnable);
         } finally {
            if (!isScheduled || "direct" == this.delegate.getName() || this.getType() == 2) {
               this.workCompleted();
            }

         }

         return isScheduled;
      } else {
         this.cancelWork(runnable);
         return false;
      }
   }

   private void cancelWork(Runnable runnable) {
      Debug.assertion(runnable instanceof Work, "Only work instances can be submitted to WorkManagerService");
      Runnable cancel = ((Work)runnable).cancel(this.getCancelMessage());
      Debug.assertion(cancel != null, "cancel task cannot be null");
      WorkManagerFactory.getInstance().getRejector().schedule(cancel);
   }

   public int getQueueDepth() {
      return this.workCount;
   }

   public void setInternal() {
      this.internal = true;
      this.delegate.setInternal();
      if (debugEnabled()) {
         debug("-- wmservice - " + this + " marked internal");
      }

   }

   public boolean isInternal() {
      return this.internal;
   }

   public boolean isThreadOwner(Thread th) {
      return false;
   }

   protected boolean permitSchedule(Runnable runnable) {
      if (this.internal) {
         return true;
      } else {
         boolean allowWork = false;
         synchronized(this) {
            if (this.state == 1 || !(runnable instanceof Work)) {
               ++this.workCount;
               allowWork = true;
            }
         }

         if (allowWork) {
            return true;
         } else {
            if (debugEnabled()) {
               debug("-- wmlifecycle - " + this + " is shutdown");
            }

            return false;
         }
      }
   }

   public void workAccepted() {
   }

   public void workStarted() {
   }

   public void workStuck() {
      if (!this.internal) {
         boolean pendingWork;
         synchronized(this) {
            ++this.stuckThreadCount;
            if (this.state != 3) {
               return;
            }

            pendingWork = this.workPending();
         }

         if (!pendingWork) {
            this.invokeCallback();
         }

      }
   }

   public void workUnstuck() {
      if (!this.internal) {
         synchronized(this) {
            --this.stuckThreadCount;
         }
      }
   }

   protected void invokeCallback() {
      try {
         if (this.callback == null) {
            return;
         }

         ShutdownCallback temp;
         synchronized(this) {
            if (this.callback == null) {
               return;
            }

            temp = this.callback;
            this.callback = null;
         }

         if (debugEnabled()) {
            debug("-- wmlifecycle - " + this + " has no pending work. Invoking callback");
         }

         temp.completed();
      } catch (Throwable var5) {
         WorkManagerLogger.logShutdownCallbackFailed(var5);
      }

   }

   protected boolean workPending() {
      return this.workCount - this.stuckThreadCount > 0;
   }

   public void workCompleted() {
      if (!this.internal) {
         boolean pendingWork;
         synchronized(this) {
            --this.workCount;
            if (this.state != 3) {
               return;
            }

            pendingWork = this.workPending();
         }

         if (pendingWork) {
            if (debugEnabled()) {
               debug("-- wmlifecycle - " + this + " is shutdown and waiting for " + this.workCount + " to finish");
            }
         } else {
            this.invokeCallback();
         }

      }
   }

   public String toString() {
      return super.toString() + "[" + this.delegate.toString() + "]";
   }

   protected static boolean debugEnabled() {
      return debugWMService.isEnabled();
   }

   protected static void debug(String str) {
      WorkManagerLogger.logDebug(str);
   }
}
