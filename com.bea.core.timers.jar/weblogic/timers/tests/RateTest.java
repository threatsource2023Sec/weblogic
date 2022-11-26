package weblogic.timers.tests;

import java.util.Timer;
import java.util.TimerTask;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class RateTest extends TimerTask implements TimerListener {
   int count;
   long lastTime;

   public static void main(String[] args) throws InterruptedException {
      if (args.length > 0) {
         Timer timer = new Timer();
         timer.scheduleAtFixedRate(new RateTest(), 0L, 1000L);
         System.out.println("USING JAVA UTIL TIMER ...");
      } else {
         System.out.println("USING WEBLOGIC TIMER ...");
         TimerManager manager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("TEST", WorkManagerFactory.getInstance().getDefault());
         manager.scheduleAtFixedRate(new RateTest(), 0L, 1000L);
      }

      Thread.sleep(100000L);
   }

   public void timerExpired(weblogic.timers.Timer timer) {
      System.out.println("Timer fired after " + (System.currentTimeMillis() - this.lastTime) + "ms");
      ++this.count;

      try {
         if (this.count % 5 == 0) {
            Thread.sleep(1800L);
         }
      } catch (InterruptedException var3) {
         var3.printStackTrace();
      }

      this.lastTime = System.currentTimeMillis();
   }

   public void run() {
      this.timerExpired((weblogic.timers.Timer)null);
   }
}
