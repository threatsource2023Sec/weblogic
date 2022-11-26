package weblogic.timers.internal;

import java.util.ArrayList;
import java.util.Arrays;

class TimerSet {
   private ArrayList timers = new ArrayList();
   private ArrayList borrow = new ArrayList();
   private volatile long min = -1L;

   public long peakMin() {
      return this.min;
   }

   public synchronized boolean add(TimerImpl t) {
      t.idx = this.timers.size();
      this.timers.add(t);
      return this.update(t);
   }

   public synchronized boolean remove(TimerImpl t) {
      if (t.idx < 0) {
         return false;
      } else {
         int size = this.timers.size();
         if (size == 0) {
            t.idx = -1;
            return false;
         } else {
            TimerImpl last = (TimerImpl)this.timers.remove(size - 1);
            if (last != t) {
               last.idx = t.idx;
               TimerImpl prev = (TimerImpl)this.timers.set(last.idx, last);

               assert t == prev;
            }

            t.idx = -1;
            if (this.timers.isEmpty()) {
               this.min = -1L;
            }

            return true;
         }
      }
   }

   public synchronized boolean update(TimerImpl t) {
      if (t.idx == -1) {
         return false;
      } else {
         t.running = false;
         long timeout = t.getTimeout();
         if ((timeout < this.min || this.min < 0L) && timeout >= 0L) {
            this.min = timeout;
            return true;
         } else {
            return false;
         }
      }
   }

   public TimerImpl[] getNotAfter(long timeout) {
      synchronized(this) {
         long m = -1L;
         int i = this.timers.size();

         while(true) {
            if (i-- <= 0) {
               this.min = m;
               break;
            }

            TimerImpl t = (TimerImpl)this.timers.get(i);

            assert t.idx != -1;

            if (!t.running) {
               long wakeup = t.getTimeout();
               if (wakeup <= timeout) {
                  t.running = true;
                  this.borrow.add(t);
               } else if (m < 0L || m > wakeup) {
                  m = wakeup;
               }
            }
         }
      }

      TimerImpl[] ts = new TimerImpl[this.borrow.size()];
      this.borrow.toArray(ts);
      this.borrow.clear();
      Arrays.sort(ts);
      return ts;
   }

   public synchronized TimerImpl[] getAll() {
      TimerImpl[] ts = new TimerImpl[this.timers.size()];
      return (TimerImpl[])this.timers.toArray(ts);
   }

   public synchronized void clear() {
      this.timers.clear();
   }

   public synchronized boolean isEmpty() {
      return this.timers.isEmpty();
   }
}
