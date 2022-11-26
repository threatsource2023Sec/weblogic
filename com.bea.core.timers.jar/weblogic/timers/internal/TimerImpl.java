package weblogic.timers.internal;

import java.util.Arrays;
import weblogic.timers.CancelTimerListener;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.StopTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;

public class TimerImpl implements Timer, Comparable, Runnable {
   private TimerManagerImpl timerManager;
   private TimerListener listener;
   protected long timeout;
   private long period;
   long counter;
   volatile boolean stopped;
   volatile boolean cancelled;
   volatile boolean running;
   protected TimerContext context;
   protected int idx;
   private static final int HISTORY_SIZE = 100;
   private HistoryMany history;
   private static final long UNUSED_HISTORY = -1L;
   private long oneExpirationTime;

   TimerImpl(TimerManagerImpl timerManager, TimerListener listener, long timeout, long period) {
      this(timerManager, listener);
      this.timeout = timeout;
      this.period = period;
   }

   TimerImpl(TimerManagerImpl timerManager, TimerListener listener) {
      this.idx = -1;
      this.oneExpirationTime = -1L;
      this.timerManager = timerManager;
      this.listener = listener;
   }

   void cleanup() {
   }

   static TimerImpl[] getTimers() {
      TimerManagerImpl[] managers = TimerManagerImpl.getAllTimerManagers();
      TimerImpl[][] timersArray = new TimerImpl[managers.length][];

      int size;
      for(size = 0; size < timersArray.length; ++size) {
         timersArray[size] = managers[size].getTimers();
      }

      size = 0;
      TimerImpl[][] var3 = timersArray;
      int pos = timersArray.length;

      for(int var5 = 0; var5 < pos; ++var5) {
         TimerImpl[] timers = var3[var5];
         size += timers.length;
      }

      TimerImpl[] result = new TimerImpl[size];
      pos = 0;
      TimerImpl[][] var11 = timersArray;
      int var12 = timersArray.length;

      for(int var7 = 0; var7 < var12; ++var7) {
         TimerImpl[] timers = var11[var7];
         int len = timers.length;
         System.arraycopy(timers, 0, result, pos, len);
         pos += len;
      }

      return result.length == 0 ? null : result;
   }

   TimerManagerImpl getTimerManager() {
      return this.timerManager;
   }

   void setCounter(long counter) {
      this.counter = counter;
   }

   void setStopped() {
      this.stopped = true;
   }

   void setTimeout(long timeout) {
      this.timeout = timeout;
   }

   public long getTimeout() {
      return this.timeout;
   }

   public long getPeriod() {
      return this.period;
   }

   public TimerListener getListener() {
      return this.listener;
   }

   public String getListenerClassName() {
      return this.listener.getClass().getName();
   }

   public boolean isCalendarTimer() {
      return false;
   }

   public ScheduleExpression getSchedule() {
      return null;
   }

   public long getScheduledExecutionTime() throws IllegalStateException {
      return this.timeout;
   }

   public boolean cancel() {
      return this.timerManager.cancel(this);
   }

   public boolean isStopped() {
      return this.stopped;
   }

   public final void run() {
      try {
         if (this.context != null) {
            this.context.push();
         }

         if (this.stopped) {
            if (this.listener instanceof StopTimerListener) {
               ((StopTimerListener)this.listener).timerStopped(this);
            }
         } else if (this.cancelled) {
            if (this.listener instanceof CancelTimerListener) {
               ((CancelTimerListener)this.listener).timerCancelled(this);
            }
         } else {
            this.recordExpirationTime();
            this.listener.timerExpired(this);
         }
      } finally {
         if (this.context != null) {
            this.context.pop();
         }

         this.timerManager.complete(this);
      }

   }

   boolean isExpired() {
      return this.timeout <= System.currentTimeMillis();
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   boolean incrementTimeout() {
      if (this.period == 0L) {
         return false;
      } else {
         long currentTime = System.currentTimeMillis();
         if (this.period > 0L) {
            long nextTimeout = this.timeout;
            long begin = nextTimeout;

            do {
               nextTimeout += this.period;
               if (nextTimeout < begin) {
                  nextTimeout = this.timerManager.normalizeTimeout(this.period);
                  break;
               }
            } while(nextTimeout < currentTime);

            this.timeout = nextTimeout;
         } else {
            this.timeout = this.timerManager.normalizeTimeout(-this.period);
         }

         return true;
      }
   }

   public int compareTo(Object object) {
      TimerImpl timer = (TimerImpl)object;
      if (this.timeout > timer.timeout) {
         return 1;
      } else if (this.timeout < timer.timeout) {
         return -1;
      } else if (this.counter > timer.counter) {
         return 1;
      } else {
         return this.counter < timer.counter ? -1 : 0;
      }
   }

   public String toString() {
      return "" + this.timeout + "." + this.counter + "(" + this.period + ")";
   }

   private void recordExpirationTime() {
      HistoryMany stableHistory = this.history;
      if (stableHistory == null) {
         if (this.oneExpirationTime == -1L) {
            this.oneExpirationTime = System.currentTimeMillis();
            return;
         }

         this.history = stableHistory = new HistoryMany();
         stableHistory.recordExpirationTime(this.oneExpirationTime);
      }

      stableHistory.recordExpirationTime(System.currentTimeMillis());
   }

   long[] sortedExpirationTimes() {
      HistoryMany stableHistory = this.history;
      if (stableHistory == null) {
         if (this.oneExpirationTime == -1L) {
            return new long[0];
         } else {
            long[] events = new long[]{this.oneExpirationTime};
            return events;
         }
      } else {
         return stableHistory.sortedExpirationTimes();
      }
   }

   private static final class HistoryMany {
      private int historyIndex;
      private long[] pastExpirationTimes;

      private HistoryMany() {
         this.historyIndex = 0;
         this.pastExpirationTimes = new long[100];
      }

      private void recordExpirationTime(long time) {
         this.pastExpirationTimes[this.historyIndex] = time;
         if (this.historyIndex == 99) {
            this.historyIndex = 0;
         } else {
            ++this.historyIndex;
         }

      }

      private long[] sortedExpirationTimes() {
         long[] list1 = new long[this.pastExpirationTimes.length];
         System.arraycopy(this.pastExpirationTimes, 0, list1, 0, list1.length);
         Arrays.sort(list1);

         int index;
         for(index = 0; index < list1.length && list1[index] <= 0L; ++index) {
         }

         if (index > 0) {
            long[] finalList = new long[list1.length - index];
            System.arraycopy(list1, index, finalList, 0, finalList.length);
            return finalList;
         } else {
            return list1;
         }
      }

      // $FF: synthetic method
      HistoryMany(Object x0) {
         this();
      }
   }
}
