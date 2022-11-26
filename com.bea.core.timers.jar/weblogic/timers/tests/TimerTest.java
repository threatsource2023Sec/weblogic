package weblogic.timers.tests;

import weblogic.timers.StopTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class TimerTest implements StopTimerListener {
   private int count = 0;

   public void timerExpired(Timer timer) {
      if (this.count++ < 10) {
         System.out.println("expired " + timer);
      } else {
         System.out.println("cancel " + timer);
         timer.cancel();
      }

      try {
         Thread.sleep(500L);
      } catch (InterruptedException var3) {
      }

   }

   public void timerStopped(Timer timer) {
      System.out.println("shutdown " + timer);
   }

   public static void main(String[] args) {
      TimerManager manager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("TEST", WorkManagerFactory.getInstance().getDefault());
      manager.schedule(new TimerTest(), 0L, 5000L);

      try {
         Thread.sleep(5000L);
         Timer[] timer = new Timer[30];

         int i;
         for(i = 0; i < 30; ++i) {
            timer[i] = manager.schedule(new TimerTest(), (long)(i * 1000));
         }

         Thread.sleep(5000L);
         System.out.println("Suspending timer manager");
         manager.suspend();
         System.out.println("Timer manager is suspended");
         Thread.sleep(5000L);
         System.out.println("Resuming timer manager");
         manager.resume();
         System.out.println("Timer manager is resumed");

         for(i = 15; i < 20; ++i) {
            timer[i].cancel();
         }

         Thread.sleep(15000L);
         System.out.println("Stopping down timer manager");
         manager.stop();
      } catch (Throwable var4) {
         var4.printStackTrace();
      }

      System.out.println("End of main");
   }
}
