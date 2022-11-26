package weblogic.ejb.container.cache;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public final class CacheScrubberTimer {
   private static final DebugLogger debugLogger;
   private final TimerListener listener;
   private final String cacheName;
   private Timer timer;
   private long scrubInterval;

   CacheScrubberTimer(TimerListener listener, long scrubInterval, String cacheName) {
      this.listener = listener;
      this.cacheName = cacheName;
      this.scrubInterval = scrubInterval;
   }

   void setScrubInterval(long interval) {
      this.scrubInterval = interval;
   }

   void resetScrubInterval(long newInterval) {
      if (this.timer == null) {
         this.startScrubber(newInterval, newInterval);
      } else {
         assert this.timer != null;

         while(!this.timer.isCancelled()) {
            try {
               this.timer.cancel();
            } catch (Exception var7) {
               EJBLogger.logErrorStoppingCacheTimer(this.cacheName, var7.getMessage());
            }
         }

         long timeLeft = this.timer.getTimeout() - System.currentTimeMillis();
         long delayValue = newInterval - timeLeft;
         if (delayValue < 0L) {
            delayValue = 0L;
         }

         this.timer = null;
         this.startScrubber(newInterval, delayValue);
      }
   }

   public void stopScrubber() {
      if (this.timer != null) {
         if (debugLogger.isDebugEnabled()) {
            this.debug(this.cacheName + "   STOP SCRUBBER \n");
         }

         try {
            this.timer.cancel();
         } catch (Exception var2) {
            EJBLogger.logErrorStoppingCacheTimer(this.cacheName, var2.getMessage());
         }

         this.timer = null;
      }

   }

   public void startScrubber() {
      this.startScrubber(this.scrubInterval, this.scrubInterval);
   }

   private void startScrubber(long period, long delay) {
      if (period > 0L) {
         if (debugLogger.isDebugEnabled()) {
            this.debug(this.cacheName + " startScrubber()  period == " + period + " delay == " + delay + " register timer.");
         }

         if (this.timer == null) {
            try {
               TimerManager tm = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
               this.timer = tm.scheduleAtFixedRate(this.listener, delay, period);
            } catch (Exception var6) {
               EJBLogger.logErrorStartingCacheTimer(this.cacheName, var6.getMessage());
            }

         }
      }
   }

   private void debug(String s) {
      debugLogger.debug("[" + this.getClass().getName() + "]" + s);
   }

   static {
      debugLogger = EJBDebugService.cachingLogger;
   }
}
