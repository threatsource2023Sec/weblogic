package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class TimerEventManager extends Thread {
   private WTCService myWTC;
   private static long tick = 0L;
   static final ReentrantReadWriteLock myLock = new ReentrantReadWriteLock();
   static final Lock r;
   static final Lock w;
   private boolean goon = true;
   private long milli;

   public TimerEventManager(WTCService wtc) {
      this.myWTC = wtc;
      this.milli = System.currentTimeMillis();
   }

   public void shutdown() {
      this.goon = false;
      synchronized(this) {
         this.notifyAll();
      }
   }

   public void run() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);

      while(this.goon) {
         try {
            synchronized(this) {
               this.wait(1000L);
            }
         } catch (InterruptedException var9) {
         } catch (Exception var10) {
            if (traceEnabled) {
               ntrace.doTrace("/TimerEventManager/run/got exception: " + var10.getMessage());
            }
            continue;
         }

         if (this.goon) {
            long newMilli = System.currentTimeMillis();
            long delta = newMilli - this.milli;
            if (delta < 0L) {
               delta = Long.MAX_VALUE - this.milli + newMilli;
               if (delta < 1000L) {
                  continue;
               }
            }

            if (delta >= 1000L) {
               delta /= 1000L;
               this.milli = newMilli;
               w.lock();
               if ((tick += delta) == Long.MAX_VALUE || tick < 0L) {
                  tick = 0L;
               }

               w.unlock();
               this.myWTC.processTSessionKAEvents(tick);
            }
         }
      }

   }

   public static long getClockTick() {
      r.lock();
      long ret = tick;
      r.unlock();
      return ret;
   }

   static {
      r = myLock.readLock();
      w = myLock.writeLock();
   }
}
