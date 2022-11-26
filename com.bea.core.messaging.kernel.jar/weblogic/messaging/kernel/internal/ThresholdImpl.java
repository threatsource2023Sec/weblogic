package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.messaging.kernel.Threshold;
import weblogic.messaging.kernel.ThresholdListener;
import weblogic.utils.collections.AbstractEmbeddedListElement;
import weblogic.utils.collections.EmbeddedList;
import weblogic.work.WorkManager;

public abstract class ThresholdImpl extends AbstractEmbeddedListElement implements Threshold, Runnable {
   private boolean armed;
   private long low;
   private long high;
   private long time;
   private long start;
   protected StatisticsImpl statistics;
   private final List listeners = new LinkedList();
   private final EmbeddedList events = new EmbeddedList();
   private boolean running;
   private WorkManager workManager;

   protected ThresholdImpl(StatisticsImpl statistics, long high, long low, WorkManager workManager) {
      this.statistics = statistics;
      this.high = high;
      this.low = low;
      this.workManager = workManager;
   }

   public long getHighThreshold() {
      synchronized(this.statistics) {
         return this.high;
      }
   }

   public long getLowThreshold() {
      synchronized(this.statistics) {
         return this.low;
      }
   }

   public long getThresholdTime() {
      synchronized(this.statistics) {
         return !this.armed ? this.time : this.time + System.currentTimeMillis() - this.start;
      }
   }

   public void setThresholds(long proposedLow, long proposedHigh) {
      synchronized(this.statistics) {
         if (proposedLow < 0L) {
            throw new IllegalArgumentException("Negative threshold");
         } else if (proposedHigh <= proposedLow) {
            throw new IllegalArgumentException("Inverted threshold");
         } else {
            boolean lowChanged = false;
            if (proposedLow != this.low) {
               this.low = proposedLow;
               lowChanged = true;
            }

            boolean highChanged = false;
            if (proposedHigh != this.high) {
               this.high = proposedHigh;
               highChanged = true;
            }

            if (lowChanged) {
               this.checkThresholdLow();
            }

            if (highChanged) {
               this.checkThresholdHigh();
            }

         }
      }
   }

   public void addListener(ThresholdListener listener) {
      synchronized(this.statistics) {
         this.listeners.add(listener);
         if (this.armed) {
            this.addEvent(listener);
         }

      }
   }

   public synchronized void removeListener(ThresholdListener listener) {
      this.listeners.remove(listener);
   }

   void checkThresholdLow() {
      if (this.armed) {
         if (!(this.armed = this.getValue() > this.low)) {
            this.time += System.currentTimeMillis() - this.start;
            List copyList = new ArrayList(this.listeners);
            this.addEvent(copyList.iterator());
         }

      }
   }

   void checkThresholdHigh() {
      assert Thread.holdsLock(this.statistics);

      if (!this.armed) {
         if (this.armed = this.getValue() > this.high) {
            this.start = System.currentTimeMillis();
            List copyList = new ArrayList(this.listeners);
            this.addEvent(copyList.iterator());
         }

      }
   }

   private void addEvent(Iterator iterator) {
      Event event = new Event(this.armed, iterator);
      synchronized(this) {
         this.events.add(event);
         if (!this.running) {
            this.workManager.schedule(this);
            this.running = true;
         }
      }
   }

   private void addEvent(ThresholdListener listener) {
      LinkedList listeners = new LinkedList();
      listeners.add(listener);
      this.addEvent(listeners.iterator());
   }

   public void run() {
      label100:
      while(true) {
         boolean var13 = false;

         try {
            label102: {
               var13 = true;
               Event event;
               synchronized(this) {
                  if (this.events.isEmpty()) {
                     var13 = false;
                     break label102;
                  }

                  event = (Event)this.events.get(0);
                  this.events.remove(event);
               }

               Iterator iterator = event.getIterator();

               while(true) {
                  if (!iterator.hasNext()) {
                     continue label100;
                  }

                  ThresholdListener listener = (ThresholdListener)iterator.next();
                  listener.onThreshold(this, event.getArmed());
               }
            }
         } finally {
            if (var13) {
               synchronized(this) {
                  this.running = false;
               }
            }
         }

         synchronized(this) {
            this.running = false;
            return;
         }
      }
   }

   protected abstract long getValue();

   private static final class Event extends AbstractEmbeddedListElement {
      private boolean armed;
      private Iterator iterator;

      Event(boolean armed, Iterator iterator) {
         this.armed = armed;
         this.iterator = iterator;
      }

      boolean getArmed() {
         return this.armed;
      }

      Iterator getIterator() {
         return this.iterator;
      }
   }
}
