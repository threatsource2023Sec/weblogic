package weblogic.work.concurrent.future;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.enterprise.concurrent.AbortedException;
import javax.enterprise.concurrent.SkippedException;
import javax.enterprise.concurrent.Trigger;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.work.concurrent.TaskStateNotifier;
import weblogic.work.concurrent.TaskWrapper;
import weblogic.work.concurrent.utils.LogUtils;

public class ManagedTriggerFutureImpl extends ManagedScheduledFutureImpl {
   private final Trigger trigger;
   private volatile LastExecutionImpl lastExec;
   private final Date scheduleStart = new Date();
   private final TimerManager tm;
   private volatile boolean skipForOverload = false;

   public ManagedTriggerFutureImpl(TaskWrapper task, TaskStateNotifier stateNotifier, TimerManager tm, Date firstTime, Trigger trigger) {
      super(task, stateNotifier, firstTime);
      this.trigger = trigger;
      this.lastExec = null;
      if (tm == null) {
         throw new NullPointerException();
      } else {
         this.tm = tm;
      }
   }

   protected LockStrategy createLockMethod() {
      return new LockStrategy() {
         private final Lock lock = new ReentrantLock();
         private final Condition runDone;

         {
            this.runDone = this.lock.newCondition();
         }

         public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException {
            if (ManagedTriggerFutureImpl.this.isInTaskThread()) {
               return true;
            } else if (ManagedTriggerFutureImpl.this.nextRunTime == null) {
               return true;
            } else {
               boolean var4;
               try {
                  this.lock.lock();
                  var4 = this.runDone.await(timeout, unit);
               } finally {
                  this.lock.unlock();
               }

               return var4;
            }
         }

         public void release() {
            try {
               this.lock.lock();
               this.runDone.signalAll();
            } finally {
               this.lock.unlock();
            }

         }

         public void acquire() throws InterruptedException {
            if (!ManagedTriggerFutureImpl.this.isInTaskThread()) {
               if (ManagedTriggerFutureImpl.this.nextRunTime != null) {
                  try {
                     this.lock.lock();
                     this.runDone.await();
                  } finally {
                     this.lock.unlock();
                  }

               }
            }
         }
      };
   }

   protected Object doGet() throws ExecutionException {
      TaskState curState = (TaskState)this.state.get();
      switch (curState) {
         case CANCELING:
         case CANCELED:
            throw this.getCancelationException();
         default:
            if (this.execErr != null) {
               throw this.execErr;
            } else {
               return this.getResult();
            }
      }
   }

   protected boolean onStarting() {
      if (!super.onStarting()) {
         return false;
      } else {
         this.lastExec = new LastExecutionImpl(this.task.getTaskName(), this.nextRunTime);
         this.lastExec.setRunStart(new Date());
         return true;
      }
   }

   private boolean afterTaskDone(ExecutionException exception, Object result, boolean skipped) {
      if (!skipped) {
         this.lastExec.setResult(result);
         this.lastExec.setRunEnd(new Date());
      }

      while(true) {
         TaskState curState = (TaskState)this.state.get();
         switch (curState) {
            case CANCELING:
               if (this.state.compareAndSet(curState, TaskState.CANCELED)) {
                  LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.CANCELED, this.debugLogger);
                  this.nextRunTime = null;
                  return false;
               }
               break;
            case CANCELED:
            default:
               LogUtils.logWrongTaskState(this.task.getTaskName(), curState, this.debugLogger);
               return true;
            case RUNNING:
            case SCHEDULED:
               if (this.nextRunTime != null) {
                  this.nextRunTime = this.trigger.getNextRunTime(this.lastExec, this.scheduleStart);
               }

               TaskState newState;
               if (this.nextRunTime == null) {
                  if (exception == null) {
                     newState = TaskState.SUCCESS;
                  } else {
                     newState = TaskState.FAILED;
                  }
               } else {
                  newState = TaskState.SCHEDULED;
               }

               if (this.state.compareAndSet(curState, newState)) {
                  LogUtils.logStateTransition(this.task.getTaskName(), curState, newState, this.debugLogger);
                  this.releaseLatch();
                  if (exception == null) {
                     this.getStateNotifier().taskSuccess(this, result);
                  } else if (!skipped) {
                     this.getStateNotifier().taskFailed(this, exception);
                  } else {
                     this.getStateNotifier().taskSkipped(this, exception);
                  }

                  if (this.nextRunTime != null) {
                     try {
                        this.tm.schedule(this, this.nextRunTime);
                        return true;
                     } catch (IllegalStateException var6) {
                        if (this.debugLogger.isDebugEnabled()) {
                           this.debugLogger.debug("the timer manager may be stopped, tm=" + this.tm, var6);
                        }
                     }
                  }

                  this.cancelTimer();
                  if (this.nextRunTime != null) {
                     this.nextRunTime = null;
                     this.releaseLatch();
                  }

                  return true;
               }
         }
      }
   }

   protected boolean onFail(ExecutionException exception) {
      return this.afterTaskDone(exception, (Object)null, false);
   }

   protected boolean onSuccess(Object result) {
      return this.afterTaskDone((ExecutionException)null, result, false);
   }

   public void reject(String reason) {
      this.skipForOverload = true;
      this.execErr = new AbortedException(reason);
   }

   public void timerExpired(Timer timer) {
      if (this.nextRunTime != null) {
         this.setTimer(timer);
         boolean skip = false;
         skip = this.trigger.skipRun(this.lastExec, this.nextRunTime);
         if (skip) {
            this.skipForOverload = false;
            this.execErr = new SkippedException();
            this.afterTaskDone(this.execErr, (Object)null, true);
         } else if (this.skipForOverload) {
            this.afterTaskDone(this.execErr, (Object)null, true);
            this.skipForOverload = false;
         } else {
            this.doRun();
         }
      }
   }
}
