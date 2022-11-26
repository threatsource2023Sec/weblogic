package weblogic.work;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.utils.Debug;
import weblogic.utils.collections.MaybeMapper;
import weblogic.utils.collections.PartialOrderSet;
import weblogic.utils.concurrent.WaterMark;

public class MaxThreadsConstraint {
   static final MaybeMapper CHECK_MAX_CONSTRAINT = new MaybeMapper() {
      public WorkAdapter unbox(WorkAdapter w, long v) {
         MaxThreadsConstraint mtc = w.getMaxThreadsConstraint();
         if (mtc == null) {
            WorkAdapter rx = (WorkAdapter)WorkAdapter.CLAIM_VERSION.unbox(w, v);
            return rx;
         } else {
            if (w.isCurrentVersion(v)) {
               boolean addSuccessful = true;

               try {
                  synchronized(mtc) {
                     if (mtc.reserveIfConstraintNotReached()) {
                        WorkAdapter r = (WorkAdapter)WorkAdapter.CLAIM_VERSION.unbox(w, v);
                        if (r == null) {
                           mtc.release();
                        }

                        WorkAdapter var8 = r;
                        return var8;
                     }

                     MinThreadsConstraint minTC = w.getMinThreadsConstraint();
                     addSuccessful = mtc.add(w, v, minTC != null);
                  }
               } finally {
                  if (!addSuccessful) {
                     mtc.logConstraintQueueFull(w.wm.getName());
                  }

               }

               mtc.logConstraintReached();
            }

            return null;
         }
      }
   };
   protected final String name;
   protected WaterMark mark;
   static final int DEFAULT_QUEUE_SIZE = 8192;
   protected PartialOrderSet queue;
   private final int maxCapacity;
   private final PartitionMaxThreadsConstraint partitionMaxThreadsConstraint;
   protected AtomicBoolean logConstraintReached;
   private AtomicInteger numberOfUnloggedConstraintReached;
   private volatile long lastLoggedLogConstraintReachedTime;
   private static long LOG_GATHERED_TIME = 180L;
   private AtomicInteger numberOfUnloggedQueueFull;
   private volatile long lastLoggedLogQueueFullTime;
   private int configuredCount;
   private boolean shared;
   private static boolean DEBUG = Debug.getCategory("weblogic.MaxThreadsConstraint").isEnabled();

   protected MaxThreadsConstraint(String name) {
      this(name, 0, 8192, (PartitionMaxThreadsConstraint)null);
   }

   protected MaxThreadsConstraint(String name, PartitionMaxThreadsConstraint partitionMaxThreadsConstraint) {
      this(name, 0, 8192, partitionMaxThreadsConstraint);
   }

   public MaxThreadsConstraint(String name, int count) {
      this(name, count, 8192, (PartitionMaxThreadsConstraint)null);
   }

   public MaxThreadsConstraint(String name, int count, int queueSize, PartitionMaxThreadsConstraint partitionMaxThreadsConstraint) {
      this.logConstraintReached = new AtomicBoolean();
      this.numberOfUnloggedConstraintReached = new AtomicInteger(0);
      this.lastLoggedLogConstraintReachedTime = -1L;
      this.numberOfUnloggedQueueFull = new AtomicInteger(0);
      this.lastLoggedLogQueueFullTime = -1L;
      this.shared = true;
      if (count < 0) {
         count = 0;
      }

      this.name = name;
      this.mark = new WaterMark(count);
      this.configuredCount = count;
      this.queue = new PartialOrderSet(queueSize);
      this.maxCapacity = this.queue.remainingCapacity();
      this.partitionMaxThreadsConstraint = partitionMaxThreadsConstraint;
      if (partitionMaxThreadsConstraint != null) {
         partitionMaxThreadsConstraint.add(this);
      }

   }

   void cleanup() {
      if (this.partitionMaxThreadsConstraint != null) {
         this.partitionMaxThreadsConstraint.remove(this);
      }

   }

   public final int getCount() {
      return this.mark.getOriginalLevel();
   }

   public final int getConfiguredCount() {
      return this.configuredCount;
   }

   final void setCountInternal(int newCount) {
      if (newCount < 0) {
         newCount = 0;
      }

      int oldCount = this.getCount();
      synchronized(this) {
         this.configuredCount = newCount;
         newCount = this.resetWaterMarkLevel(newCount);
      }

      if (newCount > oldCount) {
         RequestManager.getInstance().executeImmediately(this.getCanRunList(), false);
      }

   }

   public final void setCount(int count) {
      if (count < 0) {
         count = 0;
      }

      synchronized(this) {
         this.configuredCount = count;
         this.resetWaterMarkLevel(count);
      }
   }

   protected int resetWaterMarkLevel(int newCount) {
      this.mark.resetLevel(newCount);
      return newCount;
   }

   public final String getName() {
      return this.name;
   }

   boolean add(WorkAdapter entry, long version, boolean doNotThrowIfFull) {
      if (!this.queue.offerMaybe(entry, version, WorkAdapter.CHECK_STALE, 1)) {
         if (doNotThrowIfFull) {
            return false;
         } else {
            throw new RuntimeException(this.getConstraintQueueFullExceptionMessage(entry.wm.getName(), this.maxCapacity));
         }
      } else {
         if (DEBUG) {
            log("++: [" + entry + "] to '" + this.name + "'. Queue length=" + this.queue.size() + ", inProgress=" + this.getExecutingCount());
         }

         return true;
      }
   }

   boolean reserveIfConstraintNotReached() {
      if (this.partitionMaxThreadsConstraint != null && !this.partitionMaxThreadsConstraint.reserveIfConstraintNotReached()) {
         if (DEBUG) {
            log("reserveIfConstraintNotReached: " + this.name + ", returning false due to partition max constraint");
         }

         return false;
      } else if (!this.mark.isEnabled()) {
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
         if (!b && this.partitionMaxThreadsConstraint != null) {
            this.partitionMaxThreadsConstraint.release();
         }

         return b;
      } else {
         boolean result = this.mark.tryDecreaseByOne();
         if (!result && this.partitionMaxThreadsConstraint != null) {
            this.partitionMaxThreadsConstraint.release();
         }

         return result;
      }
   }

   boolean releaseIfConstraintOverSubscribed() {
      if (this.partitionMaxThreadsConstraint != null && this.partitionMaxThreadsConstraint.releaseIfConstraintOverSubscribed()) {
         this.mark.increaseLevel(1);
         if (DEBUG) {
            log("releaseIfConstraintOverSubscribed: " + this.name + " returning false due to partition max constraint");
         }

         return true;
      } else if (!this.mark.isEnabled()) {
         if (DEBUG) {
            log("releaseIfConstraintOverSubscribed: " + this.name + " returning false because constraint is not enabled");
         }

         return false;
      } else if (DEBUG) {
         int beforeExec = this.mark.diff();
         boolean b = this.mark.tryIncreaseByOneIfBelowZero();
         int afterExec = this.mark.diff();
         log("releaseIfConstraintOverSubscribed: " + this.name + " before=" + beforeExec + " ,after=" + afterExec + " ,result=" + b);
         if (b && this.partitionMaxThreadsConstraint != null) {
            this.partitionMaxThreadsConstraint.release();
         }

         return b;
      } else {
         boolean result = this.mark.tryIncreaseByOneIfBelowZero();
         if (result && this.partitionMaxThreadsConstraint != null) {
            this.partitionMaxThreadsConstraint.release();
         }

         return result;
      }
   }

   void acquire() {
      if (DEBUG) {
         int beforeExec = this.mark.diff();
         this.mark.decreaseLevel(1);
         int afterExec = this.mark.diff();
         log("acquire: " + this.name + " before=" + beforeExec + " ,after=" + afterExec);
         if (this.partitionMaxThreadsConstraint != null) {
            this.partitionMaxThreadsConstraint.acquire();
         }

      } else {
         this.mark.decreaseLevel(1);
         if (this.partitionMaxThreadsConstraint != null) {
            this.partitionMaxThreadsConstraint.acquire();
         }

      }
   }

   void release() {
      this.release(true);
   }

   void release(boolean releasePartition) {
      if (DEBUG) {
         int beforeExec = this.mark.diff();
         this.mark.increaseLevel(1);
         int afterExec = this.mark.diff();
         log("release: " + this.name + " before=" + beforeExec + " ,after=" + afterExec);
         if (releasePartition && this.partitionMaxThreadsConstraint != null) {
            this.partitionMaxThreadsConstraint.release();
         }

      } else {
         this.mark.increaseLevel(1);
         if (releasePartition && this.partitionMaxThreadsConstraint != null) {
            this.partitionMaxThreadsConstraint.release();
         }

      }
   }

   private static void log(String str) {
      if (DEBUG) {
         WorkManagerLogger.logDebug("<MaxTC>" + str);
      }

   }

   public String toString() {
      return " MAX: " + this.name + ", count=" + this.getCount() + ", queueSize=" + this.getQueueSize() + ", executing=" + this.getExecutingCount();
   }

   protected void doLogConstraintReached() {
      WorkManagerLogger.logMaxThreadsConstraintReached(this.name);
   }

   protected void doLogConstraintReachedGathered(int currentValue, long duration) {
      WorkManagerLogger.logMaxThreadsConstraintReachedGathered(this.name, currentValue, duration);
   }

   protected void doLogConstraintQueueFull(String wmName, int maxCapacity) {
      WorkManagerLogger.logMaxThreadsConstraintQueueFull(this.name, wmName, maxCapacity);
   }

   protected void doLogConstraintQueueFullGathered(String wmName, int maxCapacity, int currentValue, long duration) {
      WorkManagerLogger.logMaxThreadsConstraintQueueFullGathered(this.name, wmName, maxCapacity, currentValue, duration);
   }

   protected String getConstraintQueueFullExceptionMessage(String wmName, int maxCapacity) {
      return WorkManagerLogger.logMaxThreadsConstraintQueueFullLoggable(this.name, wmName, maxCapacity).getMessage();
   }

   private void logConstraintReached() {
      if (this.logConstraintReached.compareAndSet(false, true)) {
         long currentTime = System.currentTimeMillis();
         long duration = (currentTime - this.lastLoggedLogConstraintReachedTime) / 1000L;
         if (duration <= LOG_GATHERED_TIME) {
            this.numberOfUnloggedConstraintReached.incrementAndGet();
         } else {
            this.lastLoggedLogConstraintReachedTime = currentTime;
            if (this.numberOfUnloggedConstraintReached.get() == 0) {
               this.doLogConstraintReached();
            } else {
               int currentValue = this.numberOfUnloggedConstraintReached.incrementAndGet();
               this.doLogConstraintReachedGathered(currentValue, duration);
               this.numberOfUnloggedConstraintReached.set(0);
            }
         }
      }

   }

   private void logConstraintQueueFull(String wmName) {
      long currentTime = System.currentTimeMillis();
      long duration = (currentTime - this.lastLoggedLogQueueFullTime) / 1000L;
      if (duration <= LOG_GATHERED_TIME) {
         this.numberOfUnloggedQueueFull.incrementAndGet();
      } else {
         this.lastLoggedLogQueueFullTime = currentTime;
         if (this.numberOfUnloggedQueueFull.get() == 0) {
            this.doLogConstraintQueueFull(wmName, this.maxCapacity);
         } else {
            int currentValue = this.numberOfUnloggedQueueFull.incrementAndGet();
            this.doLogConstraintQueueFullGathered(wmName, this.maxCapacity, currentValue, duration);
            this.numberOfUnloggedQueueFull.set(0);
         }
      }

   }

   WorkAdapter getNext() {
      return this.partitionMaxThreadsConstraint != null ? this.partitionMaxThreadsConstraint.getNext(this) : this.getNextInternal();
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

   public final int getQueueSize() {
      return this.queue.size();
   }

   public final int getExecutingCount() {
      return this.mark.diff();
   }

   PartitionMaxThreadsConstraint getPartitionMaxThreadsConstraint() {
      return this.partitionMaxThreadsConstraint;
   }

   public boolean isPartitionMaxThreadsConstraint() {
      return false;
   }

   void setShared(boolean value) {
      this.shared = value;
   }

   boolean isShared() {
      return this.shared;
   }

   private WorkAdapter[] getCanRunList() {
      ArrayList list = new ArrayList();
      WorkAdapter work;
      synchronized(this) {
         for(; this.reserveIfConstraintNotReached(); list.add(work)) {
            work = this.getNext();
            if (work == null) {
               this.release();
               break;
            }

            if (work.wm != null) {
               work.wm.increaseMinThreadConstraintInProgress(true);
            }
         }
      }

      if (list.size() == 0) {
         return null;
      } else {
         WorkAdapter[] array = new WorkAdapter[list.size()];
         list.toArray(array);
         return array;
      }
   }
}
