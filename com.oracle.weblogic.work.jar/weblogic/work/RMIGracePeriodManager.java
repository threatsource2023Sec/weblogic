package weblogic.work;

import java.util.Iterator;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class RMIGracePeriodManager implements WorkManagerService.WorkListener {
   private static final DebugCategory debugWMService = Debug.getCategory("weblogic.workmanagerservice");
   private static final DebugCategory debugAppVersion = Debug.getCategory("weblogic.AppVersion");
   private int rmiGracePeriodSecs = -1;
   private long lastScheduledTimeMillis = -1L;
   private WorkManagerCollection wmColl;

   public RMIGracePeriodManager(WorkManagerCollection wmColl, int rmiGracePeriodSecs) {
      this.wmColl = wmColl;
      this.rmiGracePeriodSecs = rmiGracePeriodSecs;
      if (rmiGracePeriodSecs > 0) {
         this.initialize();
      }

   }

   private void initialize() {
      this.updateLastScheduledTimeMillis();
      if (debugEnabled()) {
         debug("-- wmservice - " + this + " RMI grace period will expire in " + this.rmiGracePeriodSecs + " secs");
      }

      Iterator iter = this.wmColl.iterator();

      while(iter.hasNext()) {
         WorkManagerService wmService = (WorkManagerService)iter.next();
         wmService.startRMIGracePeriod(this);
      }

      TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(new RMIGracePeriodChecker(), (long)(this.rmiGracePeriodSecs * 1000));
   }

   private void updateLastScheduledTimeMillis() {
      synchronized(this) {
         this.lastScheduledTimeMillis = System.currentTimeMillis();
         if (debugEnabled()) {
            debug("-- wmservice - " + this + " updateLastScheduledTime " + this.lastScheduledTimeMillis);
         }

      }
   }

   public void preScheduleWork() {
      this.updateLastScheduledTimeMillis();
   }

   public void postScheduleWork() {
   }

   private long getRMIGracePeriodTimeToExpireMillis() {
      return this.rmiGracePeriodSecs == -1 ? -1L : this.getLastScheduledTimeMillis() + (long)(this.rmiGracePeriodSecs * 1000) - System.currentTimeMillis();
   }

   private long getLastScheduledTimeMillis() {
      synchronized(this) {
         return this.lastScheduledTimeMillis;
      }
   }

   private void notifyRMIGracePeriodExpired() {
      Iterator iter = this.wmColl.iterator();

      while(iter.hasNext()) {
         WorkManagerService wmService = (WorkManagerService)iter.next();
         wmService.endRMIGracePeriod();
      }

   }

   private static boolean debugEnabled() {
      return debugWMService.isEnabled() || debugAppVersion.isEnabled();
   }

   private static void debug(String str) {
      WorkManagerLogger.logDebug(str);
   }

   private final class RMIGracePeriodChecker implements TimerListener {
      private RMIGracePeriodChecker() {
      }

      public void timerExpired(Timer timer) {
         long timeToExpireMillis = RMIGracePeriodManager.this.getRMIGracePeriodTimeToExpireMillis();
         if (RMIGracePeriodManager.debugEnabled()) {
            RMIGracePeriodManager.debug("-- wmservice - " + this + " RMI grace period checker, timeToExpireMillis=" + timeToExpireMillis);
         }

         if (timeToExpireMillis <= 0L) {
            RMIGracePeriodManager.this.notifyRMIGracePeriodExpired();
         } else {
            TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().schedule(this, timeToExpireMillis);
         }

      }

      // $FF: synthetic method
      RMIGracePeriodChecker(Object x1) {
         this();
      }
   }
}
