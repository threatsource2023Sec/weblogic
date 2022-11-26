package weblogic.jdbc.common.rac.internal;

import oracle.ucp.util.TimerHandle;
import weblogic.timers.Timer;

public class WLTimerHandle implements TimerHandle {
   private Timer wlTimer;

   public WLTimerHandle(Timer timer) {
      this.wlTimer = timer;
   }

   public void cancel() {
      this.wlTimer.cancel();
   }
}
