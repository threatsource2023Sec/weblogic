package weblogic.work.concurrent.future;

import java.util.Date;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.work.concurrent.TaskStateNotifier;
import weblogic.work.concurrent.TaskWrapper;

public class ManagedScheduledFutureImpl extends AbstractFutureImpl implements NakedTimerListener, ScheduledFuture {
   protected volatile Date nextRunTime;
   private static final AtomicLong sequencer = new AtomicLong(0L);
   private final long sequenceNumber;
   private volatile Timer timer;
   private volatile Runnable runnable;

   public ManagedScheduledFutureImpl(TaskWrapper task, TaskStateNotifier stateNotifier, Date nextRunTime) {
      super(task, stateNotifier, DebugLogger.getDebugLogger("DebugConcurrentMSES"));
      this.nextRunTime = nextRunTime;
      this.sequenceNumber = sequencer.getAndIncrement();
   }

   public long getDelay(TimeUnit unit) {
      return unit.convert(this.nextRunTime.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
   }

   public int compareTo(Delayed other) {
      if (other == this) {
         return 0;
      } else if (other instanceof ManagedScheduledFutureImpl) {
         ManagedScheduledFutureImpl x = (ManagedScheduledFutureImpl)other;
         if (this.nextRunTime == null && x.nextRunTime == null) {
            return this.sequenceNumber < x.sequenceNumber ? -1 : 1;
         } else if (this.nextRunTime == null) {
            return 1;
         } else if (x.nextRunTime == null) {
            return -1;
         } else {
            long diff = this.nextRunTime.getTime() - x.nextRunTime.getTime();
            if (diff < 0L) {
               return -1;
            } else if (diff > 0L) {
               return 1;
            } else {
               return this.sequenceNumber < x.sequenceNumber ? -1 : 1;
            }
         }
      } else {
         return 1;
      }
   }

   public void timerExpired(Timer timer) {
      this.setTimer(timer);
      this.doRun();
   }

   public void run() {
      if (this.debugLogger.isDebugEnabled()) {
         this.debugLogger.debug("run " + this.runnable + " in " + this);
      }

      this.runnable.run();
   }

   protected final void onCanceling(CancellationException cancelErr) {
      this.nextRunTime = null;
      this.cancelTimer();
      super.onCanceling(cancelErr);
   }

   protected final void cancelTimer() {
      if (this.timer != null) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug("cancelTimer=" + this.timer + " in " + this);
         }

         this.timer.cancel();
      }

   }

   public final void setTimer(Timer timer) {
      if (this.debugLogger.isDebugEnabled()) {
         this.debugLogger.debug("setTimer=" + timer + " in " + this);
      }

      this.timer = timer;
   }

   public boolean setRunnable(Runnable runnable) {
      boolean isUsedBefore = this.runnable != null;
      this.runnable = runnable;
      if (this.debugLogger.isDebugEnabled()) {
         this.debugLogger.debug("set " + runnable + " into " + this + ", isUsedBefore=" + isUsedBefore);
      }

      return isUsedBefore;
   }

   public Runnable createOverloadRunnable(final String reason) {
      return new Runnable() {
         public void run() {
            ManagedScheduledFutureImpl.this.reject(reason);
            ManagedScheduledFutureImpl.this.runnable.run();
         }
      };
   }

   protected Runnable createCancelRunnable(final String reason) {
      return new Runnable() {
         public void run() {
            TaskState oldState = (TaskState)ManagedScheduledFutureImpl.this.state.get();
            if (ManagedScheduledFutureImpl.this.doCancel(true, reason) && oldState == TaskState.SCHEDULED) {
               if (ManagedScheduledFutureImpl.this.debugLogger.isDebugEnabled()) {
                  ManagedScheduledFutureImpl.this.debugLogger.debug("call " + ManagedScheduledFutureImpl.this.runnable + " to notify TimerManager");
               }

               ManagedScheduledFutureImpl.this.runnable.run();
            } else if (ManagedScheduledFutureImpl.this.debugLogger.isDebugEnabled()) {
               ManagedScheduledFutureImpl.this.debugLogger.debug("skip calling " + ManagedScheduledFutureImpl.this.runnable + " because it is running, oldState=" + oldState + " state=" + ManagedScheduledFutureImpl.this.state.get());
            }

         }
      };
   }
}
