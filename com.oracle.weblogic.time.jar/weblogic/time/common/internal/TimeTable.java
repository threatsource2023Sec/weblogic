package weblogic.time.common.internal;

import weblogic.common.T3MiscLogger;
import weblogic.utils.AssertionError;
import weblogic.work.WorkManager;

public final class TimeTable extends TimeEvent {
   private long wakeupTime;
   private int executeCount;
   private int exceptionCount;
   private TimeEvent[] array;
   private int index;
   private long granularity;
   private TimeTable nextTable;

   public TimeTable() {
      this(100, 1000L, System.currentTimeMillis());
   }

   TimeTable(int arraySize, long granularity, long timebase) {
      this.executeCount = 0;
      this.exceptionCount = 0;
      this.index = 0;
      this.array = new TimeEvent[arraySize];
      this.granularity = granularity;
      this.time = timebase / granularity * granularity;
      this.next = this.prev = this;
      this.array[this.index] = this;
   }

   private final TimeEvent next() {
      TimeEvent nextCell = this.next;
      this.next = nextCell.next;
      nextCell.next.prev = this;
      nextCell.prev = null;
      return nextCell;
   }

   public synchronized void insert(TimeEvent tr) {
      long triggerTime = tr.time;
      if (triggerTime < this.time) {
         triggerTime = tr.time = this.time;
      }

      int tickDelta = (int)((triggerTime - this.time) / this.granularity);
      long temp = (triggerTime - this.time) / this.granularity;
      if (temp > 2147483647L) {
         tickDelta = Integer.MAX_VALUE;
      }

      if (tickDelta < 0) {
         throw new AssertionError("Time for scheduled trigger out of bounds: " + triggerTime);
      } else {
         if (tr.next != null) {
            tr.next.prev = null;
         }

         if (tr.prev != null) {
            tr.prev.next = null;
         }

         if (tickDelta >= this.array.length) {
            if (this.nextTable == null) {
               this.nextTable = new TimeTable(this.array.length, this.granularity * (long)this.array.length, this.time);
            }

            this.nextTable.insert(tr);
         } else {
            int point = (this.index + tickDelta) % this.array.length;
            if (this.array[point] != null) {
               if (tr.time < this.array[point].time) {
                  this.array[point].prev.insertAfter(tr);
                  this.array[point] = tr;
               } else {
                  this.array[point].insertAfter(tr);
               }
            } else {
               this.array[this.next(point)].prev.insertAfter(tr);
               this.array[point] = tr;
            }

            if (triggerTime < this.wakeupTime) {
               this.notify();
            }
         }

      }
   }

   private final int next(int i) {
      do {
         ++i;
         if (i == this.array.length) {
            i = 0;
         }
      } while(this.array[i] == null);

      return i;
   }

   public synchronized boolean delete(TimeEvent tr) {
      int tickDelta = (int)((tr.time - this.time) / this.granularity);
      long temp = (tr.time - this.time) / this.granularity;
      if (temp > 2147483647L) {
         tickDelta = Integer.MAX_VALUE;
      }

      if (tickDelta < 0) {
         return false;
      } else if (tickDelta >= this.array.length && this.nextTable != null) {
         return this.nextTable.delete(tr);
      } else {
         int point = (this.index + tickDelta) % this.array.length;
         if (this.array[point] == tr) {
            if (tr.next != null && (tr.next.time - this.time) / this.granularity == (long)tickDelta) {
               this.array[point] = tr.next;
            } else {
               this.array[point] = null;
            }
         }

         tr.remove();
         return true;
      }
   }

   public void executeTimer(long time, WorkManager manager, boolean sendEvent) {
      throw new AssertionError("TimeTable executed as timer event");
   }

   public int execute(long time, WorkManager manager, boolean sendEvent) {
      TimeEvent head = null;
      TimeEvent rest = null;
      int executions = 0;
      synchronized(this) {
         this.revise_structures(time);
         head = this.snip(this.time + this.granularity);
      }

      for(; head != null; head = rest) {
         try {
            rest = head.next;
            if (head.next != null) {
               head.next.prev = null;
               head.next = null;
            }

            if (head.prev != null) {
               head.prev.next = null;
               head.prev = null;
            }

            head.executeTimer(time, manager, sendEvent);
            ++executions;
         } catch (Throwable var11) {
            Throwable th = var11;

            try {
               T3MiscLogger.logExecution(head.toString(), th);
            } catch (Exception var10) {
            }

            ++this.exceptionCount;
         }
      }

      this.executeCount += executions;
      return executions;
   }

   private final TimeEvent snip(long maxTime) {
      TimeEvent first = this.next;

      Object last;
      for(last = this; ((TimeEvent)last).next != this && ((TimeEvent)last).next.time < maxTime; last = ((TimeEvent)last).next) {
      }

      if (last == this) {
         return null;
      } else {
         this.next = ((TimeEvent)last).next;
         ((TimeEvent)last).next.prev = this;
         ((TimeEvent)last).next = null;
         first.prev = null;
         return first;
      }
   }

   public synchronized void snooze() {
      long now = System.currentTimeMillis();
      this.wakeupTime = this.wakeupTime(now);

      try {
         long waitTime = this.wakeupTime - now;
         if (waitTime <= 0L) {
            throw new AssertionError("Illegal wait time: " + waitTime);
         }

         this.wait(waitTime);
      } catch (InterruptedException var5) {
      }

   }

   private long wakeupTime(long currentTime) {
      long wakeTime;
      if (this.next != this && this.next != null) {
         wakeTime = this.next.time;
      } else {
         wakeTime = currentTime + this.granularity * (long)this.array.length;
      }

      if (this.nextTable != null) {
         long nextTableTime = this.nextTable.wakeupTime(currentTime);
         if (nextTableTime < wakeTime) {
            wakeTime = nextTableTime;
         }
      }

      if (wakeTime <= currentTime + 1L) {
         wakeTime = currentTime + 1L;
      }

      return wakeTime;
   }

   private final boolean revise_structures(long time) {
      int ticks = (int)((time - this.time) / this.granularity);
      if (ticks <= 0) {
         return false;
      } else {
         int units = this.array.length;

         for(this.time += (long)ticks * this.granularity; ticks-- > 0; this.index = (this.index + 1) % units) {
            this.array[this.index] = null;
         }

         this.array[this.index] = this;
         if (this.nextTable != null && this.nextTable.revise_structures(time)) {
            this.nextTable.collect(this, this.time + this.granularity * (long)units);
         }

         return true;
      }
   }

   private void collect(TimeTable tt, long horizon) {
      while(this.next != this && this.next.time < horizon) {
         TimeEvent node = this.next();
         int tickDelta = (int)((node.time - this.time) / this.granularity);
         if (tickDelta > 0) {
            int point = (this.index + tickDelta) % this.array.length;
            if (this.array[point] == node) {
               if (node.next != this && (node.next.time - this.time) / this.granularity == (long)tickDelta) {
                  this.array[point] = node.next;
               } else {
                  this.array[point] = null;
               }
            }
         }

         node.next = null;
         tt.insert(node);
      }

   }

   int executeCount() {
      return this.executeCount;
   }

   int exceptionCount() {
      return this.exceptionCount;
   }

   public synchronized int showState() {
      int i;
      for(i = 0; i < this.array.length; ++i) {
         System.out.print(this.array[i] == this ? "v" : "-");
      }

      System.out.println(": " + this.timeSymbol());

      for(i = 0; i < this.array.length; ++i) {
         System.out.print(this.array[i] == null ? " " : "*");
      }

      System.out.println("");
      TimeEvent node = this;

      int n;
      for(n = 0; ((TimeEvent)node).next != this; ++n) {
         if (((TimeEvent)node).next.prev != node) {
            throw new AssertionError("node.next.prev != node.next");
         }

         node = ((TimeEvent)node).next;
      }

      System.out.println(this.timeSymbol() + " N=" + n + " triggers");
      if (this.nextTable != null) {
         n += this.nextTable.showState();
      }

      if (this.granularity == 1000L) {
         System.out.println("Total Trigger Count = " + n);
      }

      return n;
   }

   private String timeSymbol() {
      int i = 0;

      for(long g = this.granularity; g > 1000L; g /= (long)this.array.length) {
         ++i;
      }

      return "S^" + i;
   }

   private String timeDelta(long time) {
      long delta = time - System.currentTimeMillis();
      return delta / 60000L + ":" + delta % 60000L / 1000L;
   }
}
