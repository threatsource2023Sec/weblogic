package weblogic.utils.cmm.serverservice;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.StringTokenizer;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.cmm.MemoryPressureListener;

public class CMMGCRunner implements MemoryPressureListener {
   private static final String GC_INTERVALS = "com.weblogic.cmm.gc.intervals";
   private static final String GC_INTERVALS_DEFAULT = "3:3600000;6:5000;9:1000";
   private static final int TOP_LEVEL = 10;
   private static final long UNSET = -1L;
   private final TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
   private final TimerListener listener = new GCTimerListener();
   private final long[] levelIntervals = new long[10];
   private Timer currentTimer;
   private long currentInterval = -1L;
   private boolean closed = false;

   public CMMGCRunner() {
      String gcIntervals = System.getProperty("com.weblogic.cmm.gc.intervals", "3:3600000;6:5000;9:1000").trim();
      int lcv;
      if (gcIntervals.isEmpty()) {
         for(lcv = 0; lcv < 10; ++lcv) {
            this.levelIntervals[lcv] = 0L;
         }

      } else {
         for(lcv = 0; lcv < 10; ++lcv) {
            this.levelIntervals[lcv] = -1L;
         }

         int level;
         int levelIndex;
         long levelMillis;
         for(StringTokenizer st = new StringTokenizer(gcIntervals, ";"); st.hasMoreTokens(); this.levelIntervals[levelIndex] = levelMillis) {
            String token = st.nextToken();
            int colonSeparator = token.indexOf(58);
            if (colonSeparator <= 0) {
               throw new IllegalArgumentException("GC_INTERVAL value \"" + gcIntervals + "\" could not be parsed, no \":\" in entry " + token);
            }

            try {
               level = Integer.parseInt(token.substring(0, colonSeparator));
            } catch (NumberFormatException var11) {
               throw new IllegalArgumentException("GC_INTERVAL value \"" + gcIntervals + "\" had a bad level value in entry " + token, var11);
            }

            if (level < 1 || level > 10) {
               throw new IllegalArgumentException("GC_INTERVAL value \"" + gcIntervals + "\" had a bad level value " + level + " in entry " + token);
            }

            levelIndex = level - 1;
            String millis = token.substring(colonSeparator + 1);
            if (millis.isEmpty()) {
               throw new IllegalArgumentException("GC_INTERVAL value \"" + gcIntervals + "\" had no milliseconds value in entry " + token);
            }

            try {
               levelMillis = Long.parseLong(millis);
            } catch (NumberFormatException var12) {
               throw new IllegalArgumentException("GC_INTERVAL value \"" + gcIntervals + "\" had a bad millisecond value in entry " + token, var12);
            }

            if (levelMillis < 0L) {
               throw new IllegalArgumentException("GC_INTERVAL value \"" + gcIntervals + "\" had a bad milliseoncd value for level " + level + " which was " + levelMillis + " in entry " + token);
            }
         }

         long previousSpecifiedValue = 0L;

         for(level = 0; level < 10; ++level) {
            if (this.levelIntervals[level] == -1L) {
               this.levelIntervals[level] = previousSpecifiedValue;
            } else {
               previousSpecifiedValue = this.levelIntervals[level];
            }
         }

      }
   }

   public synchronized void handleCMMLevel(int newLevel) {
      if (!this.closed) {
         if (newLevel == 0) {
            if (this.currentTimer != null) {
               this.currentInterval = -1L;
               this.currentTimer.cancel();
               this.currentTimer = null;
            }

         } else {
            int levelIndex = newLevel - 1;
            long newInterval = this.levelIntervals[levelIndex];
            if (newInterval != this.currentInterval) {
               this.currentInterval = newInterval;
               if (this.currentTimer != null) {
                  this.currentTimer.cancel();
                  this.currentTimer = null;
               }

               if (this.currentInterval > 0L) {
                  this.currentTimer = this.timerManager.schedule(this.listener, this.currentInterval, this.currentInterval);
               }
            }
         }
      }
   }

   public synchronized void close() {
      if (!this.closed) {
         this.closed = true;
         if (this.currentTimer != null) {
            this.currentInterval = -1L;
            this.currentTimer.cancel();
            this.currentTimer = null;
         }

      }
   }

   private static class GCTimerListener implements TimerListener {
      private GCTimerListener() {
      }

      public void timerExpired(Timer timer) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               System.gc();
               return null;
            }
         });
      }

      // $FF: synthetic method
      GCTimerListener(Object x0) {
         this();
      }
   }
}
