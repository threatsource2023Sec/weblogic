package weblogic.work.concurrent.future;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import javax.enterprise.concurrent.AbortedException;
import weblogic.timers.Timer;
import weblogic.work.concurrent.TaskStateNotifier;
import weblogic.work.concurrent.TaskWrapper;
import weblogic.work.concurrent.utils.LogUtils;

public class ManagedPeriodFutureImpl extends ManagedScheduledFutureImpl {
   private final long period;
   private volatile boolean skipForOverload = false;
   private volatile long startTime;

   public ManagedPeriodFutureImpl(TaskWrapper task, TaskStateNotifier stateNotifier, Date firstTime, long period) {
      super(task, stateNotifier, firstTime);
      if (period == 0L) {
         throw new IllegalArgumentException();
      } else {
         this.period = period;
      }
   }

   protected boolean onStarting() {
      if (!super.onStarting()) {
         return false;
      } else {
         this.startTime = System.currentTimeMillis();
         return true;
      }
   }

   protected boolean onSuccess(Object result) {
      while(true) {
         TaskState curState = (TaskState)this.state.get();
         switch (curState) {
            case CANCELING:
               if (!this.state.compareAndSet(curState, TaskState.CANCELED)) {
                  break;
               }

               LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.CANCELED, this.debugLogger);
               return false;
            case RUNNING:
               if (!this.state.compareAndSet(curState, TaskState.SCHEDULED)) {
                  break;
               }

               LogUtils.logStateTransition(this.task.getTaskName(), curState, TaskState.SCHEDULED, this.debugLogger);
               this.computeNextRunTime();
               this.getStateNotifier().taskSuccess(this, result);
               return true;
            default:
               LogUtils.logWrongTaskState(this.task.getTaskName(), curState, this.debugLogger);
               return true;
         }
      }
   }

   public void reject(String reason) {
      this.computeNextRunTime();
      this.skipForOverload = true;
      this.getStateNotifier().taskRejected(this, new AbortedException(reason));
   }

   public void timerExpired(Timer timer) {
      this.setTimer(timer);
      if (this.skipForOverload) {
         this.skipForOverload = false;
      } else {
         super.timerExpired(timer);
      }
   }

   protected boolean onFail(ExecutionException exception) {
      this.cancelTimer();
      return super.onFail(exception);
   }

   private void computeNextRunTime() {
      long endTime = System.currentTimeMillis();
      if (this.period < 0L) {
         this.nextRunTime = new Date(endTime - this.period);
      } else {
         for(this.startTime += this.period; endTime > this.startTime; this.startTime += this.period) {
         }

         this.nextRunTime = new Date(this.startTime);
      }

   }
}
