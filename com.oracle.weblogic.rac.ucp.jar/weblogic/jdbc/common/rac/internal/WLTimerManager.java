package weblogic.jdbc.common.rac.internal;

import oracle.ucp.util.TimerHandle;
import oracle.ucp.util.TimerManager;
import oracle.ucp.util.TimerTask;
import weblogic.timers.Timer;

public class WLTimerManager implements TimerManager {
   weblogic.timers.TimerManager wlTimerManager;

   public WLTimerManager(weblogic.timers.TimerManager tm) {
      this.wlTimerManager = tm;
   }

   public boolean isRunning() {
      return !this.wlTimerManager.isStopped() && !this.wlTimerManager.isStopping() && !this.wlTimerManager.isSuspended() && !this.wlTimerManager.isSuspending();
   }

   public TimerHandle schedule(TimerTask timerTask, long delay, long interval) {
      WLTimerListener wlTimerListener = new WLTimerListener(timerTask);
      Timer wlTimer = this.wlTimerManager.schedule(wlTimerListener, delay, interval);
      return new WLTimerHandle(wlTimer);
   }

   public TimerHandle scheduleAtFixedRate(TimerTask timerTask, long delay, long interval) {
      WLTimerListener wlTimerListener = new WLTimerListener(timerTask);
      Timer wlTimer = this.wlTimerManager.scheduleAtFixedRate(wlTimerListener, delay, interval);
      return new WLTimerHandle(wlTimer);
   }

   public void start() {
   }

   public void stop() {
   }
}
