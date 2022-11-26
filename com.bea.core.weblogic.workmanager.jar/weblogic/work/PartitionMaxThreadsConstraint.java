package weblogic.work;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.utils.Debug;

public class PartitionMaxThreadsConstraint extends MaxThreadsConstraint {
   private List maxThreadsConstraintList = new CopyOnWriteArrayList();
   private AtomicInteger lastGetNextIndex = new AtomicInteger(0);
   private static boolean DEBUG = Debug.getCategory("weblogic.MaxThreadsConstraint").isEnabled();
   private int slowDownLevel = 10;

   protected PartitionMaxThreadsConstraint(String name) {
      super(name);
   }

   protected PartitionMaxThreadsConstraint(String name, int count) {
      super(name, count);
   }

   public PartitionMaxThreadsConstraint(String name, int count, int queueSize) {
      super(name, count, queueSize, (PartitionMaxThreadsConstraint)null);
   }

   void add(MaxThreadsConstraint maxThreadsConstraint) {
      this.maxThreadsConstraintList.add(maxThreadsConstraint);
   }

   void remove(MaxThreadsConstraint maxThreadsConstraint) {
      this.maxThreadsConstraintList.remove(maxThreadsConstraint);
   }

   WorkAdapter getNext() {
      WorkAdapter entry = this.getNextInternal();
      if (entry == null && this.mark.isEnabled() && !this.maxThreadsConstraintList.isEmpty()) {
         int size = this.maxThreadsConstraintList.size();
         if (size == 1) {
            return ((MaxThreadsConstraint)this.maxThreadsConstraintList.get(0)).getNextInternal();
         }

         int endPos = this.lastGetNextIndex.getAndIncrement() % size;
         if (endPos < 0) {
            endPos = 0;
            this.lastGetNextIndex.set(0);
         }

         int index = endPos;

         do {
            ++index;
            index %= size;
            MaxThreadsConstraint maxThreadsConstraint = (MaxThreadsConstraint)this.maxThreadsConstraintList.get(index);
            if (maxThreadsConstraint.reserveIfConstraintNotReached()) {
               entry = maxThreadsConstraint.getNextInternal();
               if (entry == null) {
                  maxThreadsConstraint.release();
               }
            }
         } while(entry == null && index != endPos);
      }

      return entry;
   }

   protected WorkAdapter getNextInternal() {
      WorkAdapter entry = (WorkAdapter)this.queue.poll(WorkAdapter.CLAIM_VERSION);
      if (entry == null) {
         this.logConstraintReached.compareAndSet(true, false);
      } else if (DEBUG) {
         log("--: [" + entry + "] to '" + this.name + "'. Queue length=" + this.queue.size() + ", inProgress=" + this.getExecutingCount());
      }

      return entry;
   }

   WorkAdapter getNext(MaxThreadsConstraint other) {
      assert other != null;

      WorkAdapter entry;
      if (other.getQueueSize() >= this.getQueueSize()) {
         entry = other.getNextInternal();
         if (entry != null) {
            return entry;
         }
      }

      entry = this.getNextInternal();
      if (entry == null) {
         entry = other.getNextInternal();
      } else {
         other.release(false);
      }

      return entry;
   }

   boolean reserveIfConstraintNotReached() {
      if (!this.mark.isEnabled()) {
         this.mark.decreaseLevel(1);
         if (DEBUG) {
            log("reserveIfConstraintNotReached: " + this.name + " returning true because constraint is not enabled");
         }

         return true;
      } else if (DEBUG) {
         int beforeExec = this.mark.diff();
         boolean b = this.mark.tryDecreaseByOne();
         int afterExec = this.mark.diff();
         log("reserveIfConstraintNotReached: " + this.name + " before=" + beforeExec + " ,after=" + afterExec + " ,result=" + b);
         return b;
      } else {
         return this.mark.tryDecreaseByOne();
      }
   }

   boolean releaseIfConstraintOverSubscribed() {
      if (!this.mark.isEnabled()) {
         if (DEBUG) {
            log("releaseIfConstraintOverSubscribed: " + this.name + " returning false because constraint is not enabled");
         }

         return false;
      } else if (DEBUG) {
         int beforeExec = this.mark.diff();
         boolean b = this.mark.tryIncreaseByOneIfBelowZero();
         int afterExec = this.mark.diff();
         log("releaseIfConstraintOverSubscribed: " + this.name + " before=" + beforeExec + " ,after=" + afterExec + " ,result=" + b);
         return b;
      } else {
         return this.mark.tryIncreaseByOneIfBelowZero();
      }
   }

   void acquire() {
      if (DEBUG) {
         int beforeExec = this.mark.diff();
         this.mark.decreaseLevel(1);
         int afterExec = this.mark.diff();
         log("acquire: " + this.name + " before=" + beforeExec + " ,after=" + afterExec);
      } else {
         this.mark.decreaseLevel(1);
      }
   }

   void release() {
      if (DEBUG) {
         int beforeExec = this.mark.diff();
         this.mark.increaseLevel(1);
         int afterExec = this.mark.diff();
         log("release: " + this.name + " before=" + beforeExec + " ,after=" + afterExec);
      } else {
         this.mark.increaseLevel(1);
      }
   }

   boolean setSlowDownLevel(int newLevel) {
      if (newLevel >= 1 && newLevel <= 10) {
         synchronized(this) {
            this.slowDownLevel = newLevel;
            int targetCount = this.getTargetCount(this.getConfiguredCount(), newLevel);
            this.mark.resetLevel(targetCount);
            return true;
         }
      } else {
         return false;
      }
   }

   private int getTargetCount(int newCount, int newSlowDownLevel) {
      if (newSlowDownLevel == 10) {
         return newCount;
      } else {
         int startingCount = newCount;
         if (newCount == 0) {
            startingCount = IncrementAdvisor2.getMaxThreadPoolSize();
         }

         int targetCount = (int)((double)((float)startingCount / 10.0F * (float)newSlowDownLevel) + 0.5);
         if (targetCount < 1) {
            targetCount = 1;
         }

         return targetCount;
      }
   }

   protected int resetWaterMarkLevel(int newCount) {
      int targetCount = this.getTargetCount(newCount, this.slowDownLevel);
      this.mark.resetLevel(targetCount);
      return targetCount;
   }

   public boolean isPartitionMaxThreadsConstraint() {
      return true;
   }

   private static void log(String str) {
      if (DEBUG) {
         WorkManagerLogger.logDebug("<PartitionMaxTC>" + str);
      }

   }

   protected void doLogConstraintReached() {
      WorkManagerLogger.logPartitionMaxThreadsConstraintReached(this.name);
   }

   protected void doLogConstraintReachedGathered(int currentValue, long duration) {
      WorkManagerLogger.logPartitionMaxThreadsConstraintReachedGathered(this.name, currentValue, duration);
   }

   protected void doLogConstraintQueueFull(String wmName, int maxCapacity) {
      WorkManagerLogger.logPartitionMaxThreadsConstraintQueueFull(this.name, wmName, maxCapacity);
   }

   protected void doLogConstraintQueueFullGathered(String wmName, int maxCapacity, int currentValue, long duration) {
      WorkManagerLogger.logPartitionMaxThreadsConstraintQueueFullGathered(this.name, wmName, maxCapacity, currentValue, duration);
   }

   protected String getConstraintQueueFullExceptionMessage(String wmName, int maxCapacity) {
      return WorkManagerLogger.logPartitionMaxThreadsConstraintQueueFullLoggable(this.name, wmName, maxCapacity).getMessage();
   }

   List getMaxThreadsConstraintList() {
      return this.maxThreadsConstraintList;
   }
}
