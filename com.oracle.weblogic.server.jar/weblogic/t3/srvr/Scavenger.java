package weblogic.t3.srvr;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.NestedError;

public final class Scavenger implements TimerListener {
   private static final int SCAVENGE_INTERVAL_SECS = 60;
   private static final Hashtable TRASH = new Hashtable();
   private int passCount = 0;

   public Scavenger() {
      int seconds = 60;
      TimerManagerFactory.getTimerManagerFactory().getTimerManager("Scavenger").scheduleAtFixedRate(this, 0L, (long)(seconds * 1000));
   }

   public void timerExpired(Timer timer) {
      ++this.passCount;
      Hashtable trashClone = (Hashtable)TRASH.clone();
      Enumeration e = trashClone.elements();

      while(e.hasMoreElements()) {
         Scavengable s = (Scavengable)e.nextElement();

         try {
            s.scavenge(this.passCount);
         } catch (IOException var6) {
            throw new NestedError("IOException in trigger of " + timer, var6);
         }
      }

   }

   public static void addScavengable(String key, Scavengable s) {
      TRASH.put(key, s);
   }

   public static void removeScavengable(String key) {
      TRASH.remove(key);
   }
}
