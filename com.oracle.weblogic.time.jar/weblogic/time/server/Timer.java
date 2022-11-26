package weblogic.time.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;
import weblogic.time.common.Schedulable;
import weblogic.time.common.TimeTriggerException;
import weblogic.time.common.Triggerable;
import weblogic.utils.AssertionError;

public class Timer {
   private Collection triggers = new ArrayList();

   public Timer() {
   }

   public Timer(boolean isDaemon) {
   }

   public void schedule(TimerTask task, long delay) {
      if (delay < 0L) {
         throw new IllegalArgumentException("Negative delay.");
      } else {
         this.sched(task, System.currentTimeMillis() + delay, 0L);
      }
   }

   public void schedule(TimerTask task, Date time) {
      this.sched(task, time.getTime(), 0L);
   }

   public void schedule(TimerTask task, long delay, long period) {
      if (delay < 0L) {
         throw new IllegalArgumentException("Negative delay.");
      } else if (period <= 0L) {
         throw new IllegalArgumentException("Non-positive period.");
      } else {
         this.sched(task, System.currentTimeMillis() + delay, -period);
      }
   }

   public void schedule(TimerTask task, Date firstTime, long period) {
      if (period <= 0L) {
         throw new IllegalArgumentException("Non-positive period.");
      } else {
         this.sched(task, firstTime.getTime(), -period);
      }
   }

   public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
      if (delay < 0L) {
         throw new IllegalArgumentException("Negative delay.");
      } else if (period <= 0L) {
         throw new IllegalArgumentException("Non-positive period.");
      } else {
         this.sched(task, System.currentTimeMillis() + delay, period);
      }
   }

   public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
      if (period <= 0L) {
         throw new IllegalArgumentException("Non-positive period.");
      } else {
         this.sched(task, firstTime.getTime(), period);
      }
   }

   private void sched(TimerTask task, long time, long period) {
      if (time < 0L) {
         throw new IllegalArgumentException("Illegal execution time.");
      } else if (this.triggers == null) {
         throw new IllegalStateException("Timer already cancelled.");
      } else {
         PeriodicTask p = new PeriodicTask(task, time, period);
         ScheduledTrigger st = new ScheduledTrigger(p, p);
         this.triggers.add(st);

         try {
            st.schedule();
         } catch (TimeTriggerException var10) {
            IllegalStateException e = new IllegalStateException("scheduling problem: ");
            e.initCause(var10);
            throw e;
         }
      }
   }

   public void cancel() {
      if (this.triggers != null) {
         Iterator i = this.triggers.iterator();
         this.triggers = null;

         while(i.hasNext()) {
            ScheduledTrigger st = (ScheduledTrigger)i.next();

            try {
               st.cancel();
            } catch (TimeTriggerException var4) {
               throw new AssertionError("Problem canceling timer", var4);
            }
         }

      }
   }

   static class PeriodicTask implements Schedulable, Triggerable {
      private final TimerTask task;
      private final long period;
      private long executionTime;

      PeriodicTask(TimerTask task, long time, long period) {
         this.task = task;
         this.period = period;
         this.executionTime = time;
      }

      public final void trigger(Schedulable sched) {
         this.task.run();
      }

      public final long schedule(long currentTime) {
         long result = this.executionTime;
         if (this.period == 0L) {
            this.executionTime = 0L;
         } else if (this.period < 0L) {
            this.executionTime -= this.period;
         } else {
            this.executionTime = currentTime + this.period;
         }

         return result;
      }
   }
}
