package weblogic.jdbc.common.rac.internal;

import oracle.ucp.util.TimerTask;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;

public class WLTimerListener implements TimerListener {
   private TimerTask ucpTimerTask;

   public WLTimerListener(TimerTask timerTask) {
      this.ucpTimerTask = timerTask;
   }

   public void timerExpired(Timer timer) {
      this.ucpTimerTask.run();
   }
}
