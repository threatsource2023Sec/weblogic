package weblogic.timers.internal.commonj;

import commonj.timers.Timer;
import java.io.Serializable;
import weblogic.timers.CancelTimerListener;
import weblogic.timers.StopTimerListener;
import weblogic.timers.TimerListener;

final class ListenerWrap implements TimerListener, Serializable, CancelTimerListener, StopTimerListener {
   private static final long serialVersionUID = -760404834546160798L;
   private final commonj.timers.TimerListener listener;
   private transient Timer timer;

   public ListenerWrap(commonj.timers.TimerListener listener) {
      this.listener = listener;
   }

   public void timerExpired(weblogic.timers.Timer timer) {
      this.initialize(timer);
      this.listener.timerExpired(this.timer);
   }

   private void initialize(weblogic.timers.Timer timer) {
      if (this.timer == null) {
         this.timer = new TimerWrap(this.listener, timer);
      }

   }

   public commonj.timers.TimerListener getTimerListener() {
      return this.listener;
   }

   public void timerCancelled(weblogic.timers.Timer timer) {
      if (this.listener instanceof commonj.timers.CancelTimerListener) {
         ((commonj.timers.CancelTimerListener)this.listener).timerCancel(this.timer);
      }

   }

   public void timerStopped(weblogic.timers.Timer timer) {
      if (this.listener instanceof commonj.timers.StopTimerListener) {
         ((commonj.timers.StopTimerListener)this.listener).timerStop(this.timer);
      }

   }

   public String toString() {
      return this.listener.toString();
   }
}
