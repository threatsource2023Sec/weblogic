package weblogic.timers.internal;

import weblogic.timers.NakedTimerListener;
import weblogic.timers.TimerListener;

public class ServerTimerImpl extends TimerImpl {
   ServerTimerImpl(TimerManagerImpl timerManager, TimerListener listener, long timeout, long period) {
      super(timerManager, listener, timeout, period);
      this.initContext(listener);
   }

   void initContext(TimerListener listener) {
      if (!(listener instanceof NakedTimerListener)) {
         this.context = new J2EETimerContextImpl();
      }

   }
}
