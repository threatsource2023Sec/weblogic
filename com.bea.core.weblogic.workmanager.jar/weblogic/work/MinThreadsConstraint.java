package weblogic.work;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.utils.Debug;
import weblogic.utils.collections.PartialOrderSet;
import weblogic.utils.concurrent.WaterMark;

public final class MinThreadsConstraint {
   private static boolean DEBUG = Debug.getCategory("weblogic.MinThreadsConstraint").isEnabled();
   private final String name;
   private PartialOrderSet queue;
   private AtomicLong totalCompletedCount;
   private WaterMark mark;
   private long outOfOrderExecutionCount;
   private long maxWaitTime;
   private long currentWaitTime;
   private int configuredCount;
   private boolean shared;
   private final PartitionMinThreadsConstraint partitionMinThreadsConstraint;
   private int noOfTimesSkippedCheckingMinQ;
   private static final int CHECK_MIN_Q_AFTER_MAX_SKIPPED = Integer.getInteger("weblogic.pollMinQAfterMaxSkipped", 20);

   public MinThreadsConstraint(String name, int count) {
      this(name, count, (PartitionMinThreadsConstraint)null);
   }

   public MinThreadsConstraint(String name, int count, PartitionMinThreadsConstraint partitionMinThreadsConstraint) {
      this.queue = new PartialOrderSet(8192);
      this.totalCompletedCount = new AtomicLong();
      this.outOfOrderExecutionCount = 0L;
      this.shared = true;
      this.noOfTimesSkippedCheckingMinQ = 0;
      if (count < 0) {
         count = 0;
      }

      this.partitionMinThreadsConstraint = partitionMinThreadsConstraint;
      this.name = name;
      this.mark = new WaterMark(count);
      this.configuredCount = count;
      if (partitionMinThreadsConstraint != null) {
         partitionMinThreadsConstraint.add(this);
      }

      if (DEBUG) {
         log("created '" + name + "' with count " + count);
      }

   }

   void cleanup() {
      if (this.partitionMinThreadsConstraint != null) {
         this.partitionMinThreadsConstraint.remove(this);
      }

      RequestManager.getInstance().deregister(this);
   }

   public int getCount() {
      return this.mark.getOriginalLevel();
   }

   public final int getConfiguredCount() {
      return this.configuredCount;
   }

   public void setCount(int newCount) {
      if (newCount < 0) {
         newCount = 0;
      }

      int oldCount = this.getCount();
      this.configuredCount = newCount;
      this.mark.resetLevel(newCount);
      if (newCount > oldCount) {
         RequestManager.getInstance().executeImmediately(this.getMustRunList(), true);
      }

      if (this.partitionMinThreadsConstraint != null) {
         this.partitionMinThreadsConstraint.updateSumMinConstraints(oldCount, newCount);
      }

   }

   public String getName() {
      return this.name;
   }

   PartitionMinThreadsConstraint getPartitionMinThreadsConstraint() {
      return this.partitionMinThreadsConstraint;
   }

   void add(WorkAdapter entry, long version) throws ConstraintFullQueueException {
      if (!this.queue.offerMaybe(entry, version, WorkAdapter.CHECK_STALE, 1)) {
         throw new ConstraintFullQueueException("MinThreads constraint '" + this.name + "' queue exceeded maximum capacity of: '8192' elements");
      } else {
         if (DEBUG) {
            log("++: [" + entry + "] to '" + this.name + "'. Queue length=" + this.queue.size() + ", inProgress=" + this.inProgress());
         }

      }
   }

   boolean isConstraintSatisfied() {
      return this.mark.getCurrentLevel() <= 0;
   }

   void setShared(boolean value) {
      this.shared = value;
   }

   boolean isShared() {
      return this.shared;
   }

   private int inProgress() {
      return this.mark.diff();
   }

   boolean tryAcquire() {
      boolean success;
      if (DEBUG) {
         int beforeExec = this.mark.diff();
         success = this.mark.tryDecreaseByOne();
         int afterExec = this.mark.diff();
         log("tryAcquire: " + this.name + " before=" + beforeExec + " ,after=" + afterExec + " ,result=" + success);
      } else {
         success = this.mark.tryDecreaseByOne();
      }

      if (success && this.partitionMinThreadsConstraint != null) {
         success = this.partitionMinThreadsConstraint.tryAcquire();
         if (!success) {
            this.mark.increaseLevel(1);
         }
      }

      return success;
   }

   void acquire(boolean acquirePartitionConstraint) {
      if (DEBUG) {
         int beforeExec = this.mark.diff();
         this.mark.decreaseLevel(1);
         int afterExec = this.mark.diff();
         log("acquire: " + this.name + " before=" + beforeExec + " ,after=" + afterExec);
      } else {
         this.mark.decreaseLevel(1);
      }

      if (acquirePartitionConstraint && this.partitionMinThreadsConstraint != null) {
         this.partitionMinThreadsConstraint.acquire();
      }

   }

   void release(boolean releasePartitionConstraint) {
      if (DEBUG) {
         int beforeExec = this.mark.diff();
         this.mark.increaseLevel(1);
         int afterExec = this.mark.diff();
         log("release: " + this.name + " before=" + beforeExec + " ,after=" + afterExec);
      } else {
         this.mark.increaseLevel(1);
      }

      if (releasePartitionConstraint && this.partitionMinThreadsConstraint != null) {
         this.partitionMinThreadsConstraint.release();
      }

   }

   private boolean releaseIfSatisfied(boolean releasePartitionConstraint) {
      boolean result = this.mark.tryIncreaseByOneIfBelowZero();
      if (result && releasePartitionConstraint && this.partitionMinThreadsConstraint != null) {
         this.partitionMinThreadsConstraint.release();
      }

      return result;
   }

   void completed() {
      this.totalCompletedCount.getAndIncrement();
   }

   int getMustRunCount() {
      int available = this.mark.getCurrentLevel();
      return available <= 0 ? 0 : Math.min(this.queue.size(), available);
   }

   final WorkAdapter getNext(boolean isStandbyThread, boolean idleThreadsExist) {
      WorkAdapter workAdapter;
      if (this.noOfTimesSkippedCheckingMinQ < CHECK_MIN_Q_AFTER_MAX_SKIPPED) {
         if (isStandbyThread && this.partitionMinThreadsConstraint != null && this.partitionMinThreadsConstraint.isEnabled()) {
            workAdapter = this.partitionMinThreadsConstraint.getNext();
            if (workAdapter == null) {
               this.release(true);
            } else {
               MinThreadsConstraint other = workAdapter.getMinThreadsConstraint();
               if (other != this) {
                  this.release(false);
                  other.acquire(false);
               }
            }

            ++this.noOfTimesSkippedCheckingMinQ;
            return workAdapter;
         }

         if (this.releaseIfSatisfied(isStandbyThread)) {
            ++this.noOfTimesSkippedCheckingMinQ;
            return null;
         }

         if (!isStandbyThread && !idleThreadsExist && this.partitionMinThreadsConstraint != null && this.partitionMinThreadsConstraint.limitReached()) {
            this.release(isStandbyThread);
            ++this.noOfTimesSkippedCheckingMinQ;
            return null;
         }
      }

      this.noOfTimesSkippedCheckingMinQ = 0;
      workAdapter = this.getNextInternal();
      if (workAdapter == null) {
         this.release(isStandbyThread);
      }

      return workAdapter;
   }

   WorkAdapter getNextInternal() {
      WorkAdapter entry = (WorkAdapter)this.queue.poll(WorkAdapter.CLAIM_VERSION);
      if (entry != null) {
         ++this.outOfOrderExecutionCount;
         this.currentWaitTime = System.currentTimeMillis() - entry.creationTimeStamp;
         this.maxWaitTime = Math.max(this.maxWaitTime, this.currentWaitTime);
         if (DEBUG) {
            log("--: must run [" + entry + "] from '" + this.name + "'. Queue length=" + this.queue.size() + ", inProgress=" + this.inProgress());
         }

         return entry;
      } else {
         return null;
      }
   }

   public int getQueueSize() {
      return this.queue.size();
   }

   public int getExecutingCount() {
      return this.inProgress();
   }

   public long getCompletedCount() {
      return this.totalCompletedCount.get();
   }

   public long getOutOfOrderExecutionCount() {
      return this.outOfOrderExecutionCount;
   }

   public int getPendingCount() {
      return this.queue.size();
   }

   public long getMaxWaitTime() {
      return this.maxWaitTime;
   }

   public long getCurrentWaitTime() {
      return this.currentWaitTime;
   }

   public boolean isPartitionLimitReached() {
      return this.partitionMinThreadsConstraint != null ? this.partitionMinThreadsConstraint.limitReached() : false;
   }

   private WorkAdapter[] getMustRunList() {
      ArrayList list = new ArrayList();

      while(true) {
         WorkAdapter work;
         synchronized(this.mark) {
            if (!this.tryAcquire()) {
               break;
            }

            work = this.getNextInternal();
         }

         if (work == null) {
            this.release(true);
            break;
         }

         if (work.wm != null) {
            work.wm.increaseMaxThreadConstraintInProgress();
         }

         list.add(work);
      }

      if (list.size() == 0) {
         return null;
      } else {
         WorkAdapter[] array = new WorkAdapter[list.size()];
         list.toArray(array);
         return array;
      }
   }

   private static void log(String str) {
      WorkManagerLogger.logDebug("<MinTC>" + str);
   }

   public String toString() {
      return this.name + ", count=" + this.getCount() + ", queueSize=" + this.getQueueSize() + ", executing=" + this.getExecutingCount() + ", mustRun=" + this.getMustRunCount() + ", outOfOrder=" + this.getOutOfOrderExecutionCount();
   }

   final void dumpAndDestroy() {
      for(int count = 0; count < this.queue.size(); ++count) {
         WorkAdapter obj = (WorkAdapter)this.queue.poll();
         System.out.println("--- count " + count + " --- ");
         System.out.println(obj.dump() + "\n");
      }

   }
}
