package weblogic.work.concurrent.future;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import javax.enterprise.concurrent.AbortedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.logging.Loggable;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.work.ServerWorkAdapter;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.ConcurrentWork;
import weblogic.work.concurrent.TaskStateNotifier;
import weblogic.work.concurrent.TaskWrapper;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectCollection;
import weblogic.work.concurrent.spi.DaemonThreadTask;
import weblogic.work.concurrent.utils.LogUtils;

public abstract class AbstractFutureImpl extends ServerWorkAdapter implements Future, ConcurrentWork {
   protected final DebugLogger debugLogger;
   private final LockStrategy latch = this.createLockMethod();
   private volatile Thread thread;
   private volatile DaemonThreadTask daemonThreadTask = null;
   protected final AtomicReference state;
   private final TaskStateNotifier stateNotifier;
   private volatile Object result;
   protected volatile ExecutionException execErr;
   private volatile CancellationException cancelErr;
   private volatile boolean userCancel = false;
   private final CountDownLatch threadInterrupted = new CountDownLatch(1);
   protected final TaskWrapper task;

   public DaemonThreadTask getDaemonThreadTask() {
      return this.daemonThreadTask;
   }

   public void setDaemonThreadTask(DaemonThreadTask daemonThreadTask) {
      this.daemonThreadTask = daemonThreadTask;
   }

   protected boolean isInTaskThread() {
      return this.thread == Thread.currentThread();
   }

   protected LockStrategy createLockMethod() {
      return new LockStrategy() {
         private CountDownLatch lock = new CountDownLatch(1);

         public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException {
            return this.lock.await(timeout, unit);
         }

         public void release() {
            this.lock.countDown();
         }

         public void acquire() throws InterruptedException {
            this.lock.await();
         }
      };
   }

   public AbstractFutureImpl(TaskWrapper task, TaskStateNotifier stateNotifier, DebugLogger debugLogger) {
      super(task.getAuthenticatedSubject());
      this.stateNotifier = stateNotifier;
      this.task = task;
      this.state = new AtomicReference(TaskState.SCHEDULED);
      this.thread = null;
      this.debugLogger = debugLogger;
   }

   public TaskStateNotifier getStateNotifier() {
      return this.stateNotifier;
   }

   public final boolean cancel(boolean mayInterruptIfRunning) {
      this.userCancel = true;
      Loggable loggable = ConcurrencyLogger.logUserCancelTaskLoggable(this.task.getTaskName());
      return this.doCancel(mayInterruptIfRunning, loggable.getMessage());
   }

   public boolean isCancelled() {
      TaskState curState = (TaskState)this.state.get();
      return (curState == TaskState.CANCELING || curState == TaskState.CANCELED) && this.userCancel;
   }

   public boolean isDone() {
      TaskState curState = (TaskState)this.state.get();
      return curState != TaskState.RUNNING && curState != TaskState.SCHEDULED;
   }

   public Object get() throws InterruptedException, ExecutionException {
      this.latch.acquire();
      return this.doGet();
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      if (!this.latch.tryAcquire(timeout, unit)) {
         throw new TimeoutException();
      } else {
         return this.doGet();
      }
   }

   public void reject(String reason) {
      while(true) {
         TaskState curState = (TaskState)this.state.get();
         switch (curState) {
            case CANCELED:
            case FAILED:
            case SUCCESS:
               return;
            case SCHEDULED:
               if (!this.state.compareAndSet(curState, TaskState.FAILED)) {
                  break;
               }

               LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.FAILED, this.debugLogger);
               this.execErr = new AbortedException(reason);
               this.latch.release();
               this.stateNotifier.taskRejected(this, this.execErr);
               return;
            default:
               LogUtils.logWrongTaskState(this.task.getTaskName(), curState, this.debugLogger);
               return;
         }
      }
   }

   protected void releaseLatch() {
      this.latch.release();
   }

   protected CancellationException getCancelationException() {
      return this.cancelErr;
   }

   protected Object getResult() {
      return this.result;
   }

   public final boolean doCancel(boolean mayInterruptIfRunning, String reason) {
      while(true) {
         TaskState curState = (TaskState)this.state.get();
         switch (curState) {
            case SCHEDULED:
               this.cancelErr = new CancellationException(reason);
               if (!this.state.compareAndSet(curState, TaskState.CANCELED)) {
                  continue;
               }

               LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.CANCELED, this.debugLogger);
               if (this.daemonThreadTask != null) {
                  this.daemonThreadTask.cancel();
               }

               this.onCanceling(this.cancelErr);
               this.onCanceled(this.cancelErr);
               break;
            case RUNNING:
               this.cancelErr = new CancellationException(reason);
               if (!this.state.compareAndSet(curState, TaskState.CANCELING)) {
                  continue;
               }

               LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.CANCELING, this.debugLogger);
               LogUtils.assertNotNull(this.thread, this.task.getTaskName(), "thread", this.debugLogger);
               if (mayInterruptIfRunning) {
                  this.thread.interrupt();
               }

               this.threadInterrupted.countDown();
               this.onCanceling(this.cancelErr);
               break;
            default:
               return false;
         }

         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug(String.format("Task %s is canceled!", this.task.getTaskName()));
         }

         return true;
      }
   }

   protected Object doGet() throws ExecutionException {
      TaskState curState = (TaskState)this.state.get();
      switch (curState) {
         case CANCELED:
         case CANCELING:
            LogUtils.assertNotNull(this.cancelErr, this.task.getTaskName(), "CancellationException", this.debugLogger);
            throw this.cancelErr;
         case FAILED:
            LogUtils.assertNotNull(this.execErr, this.task.getTaskName(), "ExecutionException", this.debugLogger);
            throw this.execErr;
         case SUCCESS:
            return this.result;
         case SCHEDULED:
         case RUNNING:
         default:
            LogUtils.logWrongTaskState(this.task.getTaskName(), curState, this.debugLogger);
            return null;
      }
   }

   protected void onCanceling(CancellationException cancelErr) {
      this.latch.release();
      this.stateNotifier.taskCanceling(this, cancelErr);
   }

   private void onCanceled(CancellationException cancelErr) {
      this.stateNotifier.taskCanceled(this, cancelErr);
   }

   protected boolean onFail(ExecutionException exception) {
      while(true) {
         TaskState curState = (TaskState)this.state.get();
         switch (curState) {
            case RUNNING:
               if (!this.state.compareAndSet(curState, TaskState.FAILED)) {
                  break;
               }

               LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.FAILED, this.debugLogger);
               this.latch.release();
               this.stateNotifier.taskFailed(this, exception);
               return true;
            case CANCELING:
               if (!this.state.compareAndSet(curState, TaskState.CANCELED)) {
                  break;
               }

               LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.CANCELED, this.debugLogger);
               return false;
            default:
               LogUtils.logWrongTaskState(this.task.getTaskName(), curState, this.debugLogger);
               return true;
         }
      }
   }

   protected boolean onSuccess(Object result) {
      while(true) {
         TaskState curState = (TaskState)this.state.get();
         switch (curState) {
            case RUNNING:
               if (!this.state.compareAndSet(curState, TaskState.SUCCESS)) {
                  break;
               }

               LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.SUCCESS, this.debugLogger);
               this.latch.release();
               this.stateNotifier.taskSuccess(this, result);
               return true;
            case CANCELING:
               if (!this.state.compareAndSet(curState, TaskState.CANCELED)) {
                  break;
               }

               LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.CANCELED, this.debugLogger);
               return false;
            default:
               LogUtils.logWrongTaskState(this.task.getTaskName(), curState, this.debugLogger);
               return true;
         }
      }
   }

   protected boolean onStarting() {
      while(true) {
         TaskState curState = (TaskState)this.state.get();
         switch (curState) {
            case CANCELED:
            case FAILED:
            case SUCCESS:
               return false;
            case SCHEDULED:
               if (!this.state.compareAndSet(curState, TaskState.RUNNING)) {
                  break;
               }

               LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.RUNNING, this.debugLogger);
               return true;
            default:
               LogUtils.logWrongTaskState(this.task.getTaskName(), curState, this.debugLogger);
               return false;
         }
      }
   }

   private void runTask() {
      try {
         this.task.checkSubmittingCompState();
      } catch (IllegalStateException var17) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug("cancel because submitting component was not started, task=" + this.task.getTaskName(), var17);
         }

         this.doCancel(true, var17.getMessage());
         return;
      }

      Throwable userTaskExcpetion = null;
      this.stateNotifier.taskStarting(this);
      boolean var13 = false;

      boolean notCancel;
      label180: {
         try {
            var13 = true;
            this.result = this.task.call();
            userTaskExcpetion = null;
            var13 = false;
            break label180;
         } catch (Throwable var18) {
            userTaskExcpetion = var18;
            var13 = false;
         } finally {
            if (var13) {
               boolean notCancel = false;
               if (userTaskExcpetion == null) {
                  this.execErr = null;
                  notCancel = this.onSuccess(this.result);
               } else {
                  this.execErr = new ExecutionException(userTaskExcpetion);
                  notCancel = this.onFail(this.execErr);
               }

               if (!notCancel) {
                  LogUtils.assertNotNull(this.cancelErr, this.task.getTaskName(), "CancellationException", this.debugLogger);

                  try {
                     this.threadInterrupted.await();
                  } catch (InterruptedException var14) {
                  }

                  this.onCanceled(this.cancelErr);
               }

            }
         }

         notCancel = false;
         if (userTaskExcpetion == null) {
            this.execErr = null;
            notCancel = this.onSuccess(this.result);
         } else {
            this.execErr = new ExecutionException(userTaskExcpetion);
            notCancel = this.onFail(this.execErr);
         }

         if (!notCancel) {
            LogUtils.assertNotNull(this.cancelErr, this.task.getTaskName(), "CancellationException", this.debugLogger);

            try {
               this.threadInterrupted.await();
            } catch (InterruptedException var15) {
            }

            this.onCanceled(this.cancelErr);
         }

         return;
      }

      notCancel = false;
      if (userTaskExcpetion == null) {
         this.execErr = null;
         notCancel = this.onSuccess(this.result);
      } else {
         this.execErr = new ExecutionException(userTaskExcpetion);
         notCancel = this.onFail(this.execErr);
      }

      if (!notCancel) {
         LogUtils.assertNotNull(this.cancelErr, this.task.getTaskName(), "CancellationException", this.debugLogger);

         try {
            this.threadInterrupted.await();
         } catch (InterruptedException var16) {
         }

         this.onCanceled(this.cancelErr);
      }

   }

   public final void doRun() {
      this.thread = Thread.currentThread();
      if (this.daemonThreadTask != null && this.daemonThreadTask.getThread() != null) {
         LogUtils.checkThread(this.task.getTaskName(), this.thread, this.daemonThreadTask.getThread(), this.debugLogger);
      }

      if (this.onStarting()) {
         this.execErr = null;
         this.result = null;

         try {
            this.runTask();
         } finally {
            Thread.interrupted();
         }

      }
   }

   public String toString() {
      return this.task.getTaskName();
   }

   public AuthenticatedSubject getAuthenticatedSubject() {
      return this.task.getAuthenticatedSubject();
   }

   protected boolean isAdminRequest() {
      return this.task.isAdminRequest();
   }

   public void release() {
      String msg;
      if (!this.stateNotifier.getService().isStarted()) {
         msg = ConcurrencyLogger.logMESReleaseTaskLoggable(this.task.getTaskName(), this.stateNotifier.getService().getName(), this.stateNotifier.getService().getAppId()).getMessage();
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug(msg);
         }

         this.doCancel(true, msg);
      } else if (this.getSubmittingCICInSharing() != null && !ConcurrentManagedObjectCollection.isStarted(this.getSubmittingCICInSharing().getApplicationId())) {
         msg = ConcurrencyLogger.logSubmittingCompReleaseTaskLoggable(this.task.getTaskName(), this.stateNotifier.getService().getName(), this.stateNotifier.getService().getAppId(), this.getSubmittingCICInSharing().getApplicationId()).getMessage();
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug(msg);
         }

         this.doCancel(true, msg);
      } else if (!this.isAdminRequest()) {
         msg = ConcurrencyLogger.logWMReleaseNonAdminTaskLoggable(this.task.getTaskName(), this.stateNotifier.getService().getName(), this.stateNotifier.getService().getWorkManager().getName(), this.stateNotifier.getService().getAppId()).getMessage();
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug(msg);
         }

         this.doCancel(true, msg);
      } else {
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug("do nothing in release for " + this.task.getTaskName() + "because " + this.stateNotifier.getService() + " is started and the task is submitted by an administrator");
         }

      }
   }

   public Runnable overloadAction(String reason) {
      if (this.isAdminRequest()) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug("skip overloadAction for " + this.task.getTaskName() + "belonging to " + this.stateNotifier.getService() + " because the task is submitted by an administrator");
         }

         return null;
      } else {
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug("return overloadAction for " + this.task.getTaskName() + "belonging to " + this.stateNotifier.getService());
         }

         return this.createOverloadRunnable(reason);
      }
   }

   protected abstract Runnable createOverloadRunnable(String var1);

   public Runnable cancel(String reason) {
      if (this.state.get() == TaskState.SCHEDULED && this.isStarted() && this.isAdminRequest() && this.stateNotifier.getService().isStarted()) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug("skip cancel for " + this.task.getTaskName() + " because at this time the work manager is shutdown, but " + this.stateNotifier.getService() + " is started and the task is submitted by an administrator. To be consistent with the behavior of work manager, we allow this task to run.");
         }

         return null;
      } else {
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug("return cancel action for " + this.task.getTaskName() + " reason=" + reason + " state.get()=" + this.state.get() + " isStarted()=" + this.isStarted() + " isAdminRequest()=" + this.isAdminRequest() + " MES=" + this.stateNotifier.getService() + " MES isStarted=" + this.stateNotifier.getService().isStarted());
         }

         return this.createCancelRunnable(reason);
      }
   }

   protected Runnable createCancelRunnable(final String reason) {
      return new Runnable() {
         public void run() {
            AbstractFutureImpl.this.doCancel(true, reason);
         }
      };
   }

   public DebugLogger getDebugLogger() {
      return this.debugLogger;
   }

   public ComponentInvocationContext getSubmittingCICInSharing() {
      return this.task.getSubmittingCICInSharing();
   }
}
