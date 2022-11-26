package weblogic.timers.internal.commonj;

import commonj.timers.Timer;
import commonj.timers.TimerListener;

final class TimerWrap implements Timer {
   private final weblogic.timers.Timer timer;
   private final TimerListener listener;

   TimerWrap(TimerListener listener, weblogic.timers.Timer timer) {
      this.listener = listener;
      this.timer = timer;
   }

   public boolean cancel() {
      return this.timer.cancel();
   }

   public TimerListener getTimerListener() {
      return this.listener;
   }

   public long getScheduledExecutionTime() {
      return this.timer.getTimeout();
   }

   public long getPeriod() {
      return Math.abs(this.timer.getPeriod());
   }

   public int hashCode() {
      return this.timer.hashCode();
   }

   public boolean equals(Object o) {
      if (o instanceof TimerWrap) {
         TimerWrap other = (TimerWrap)o;
         return this.timer.equals(other.timer);
      } else {
         return false;
      }
   }

   public String toString() {
      return this.timer.toString();
   }
}
