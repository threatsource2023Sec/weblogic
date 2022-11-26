package weblogic.transaction.internal;

import weblogic.timers.Timer;
import weblogic.timers.TimerListener;

public class WLSTimer implements TimerListener {
   private static WLSTimer theOne = null;

   private WLSTimer() {
      PlatformHelper.getPlatformHelper().timerManagerSchedule(this);
   }

   public void timerExpired(Timer timer) {
      TransactionManagerImpl.getTransactionManager().wakeUp();
   }

   public static void initialize() {
      if (theOne == null) {
         Class var0 = WLSTimer.class;
         synchronized(WLSTimer.class) {
            if (theOne == null) {
               theOne = new WLSTimer();
            }
         }
      }

   }
}
