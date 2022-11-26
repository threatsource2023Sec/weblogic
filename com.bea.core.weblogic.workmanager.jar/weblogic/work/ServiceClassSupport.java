package weblogic.work;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class ServiceClassSupport extends ServiceClassStatsSupport implements RequestClass, Comparable {
   private long vtDeltaFirst;
   private long vtDeltaRepeat;
   private long myLast;
   private AtomicInteger pendingRequestCount;
   private long vtIncrement;
   private int threadPriority;
   private boolean internal;
   private final long requestQueueMaxValue;
   private boolean isShared;
   private long queueEmptiedCounter;
   protected PartitionFairShare partitionFairShare;

   public ServiceClassSupport(String n) {
      this(n, (PartitionFairShare)null);
   }

   public ServiceClassSupport(String n, PartitionFairShare partitionFairShare) {
      super(n);
      this.pendingRequestCount = new AtomicInteger();
      this.threadPriority = 5;
      RequestManager.getInstance().register(this);
      this.requestQueueMaxValue = RequestManager.getInstance().getRequestQueueMaxValue();
      this.partitionFairShare = partitionFairShare;
   }

   public void cleanup() {
      RequestManager.getInstance().deregister(this);
   }

   public ServiceClassSupport(String n, long d1, long dr) {
      this(n);
      this.vtDeltaFirst = d1;
      this.vtDeltaRepeat = dr;
   }

   public final synchronized long getVirtualTimeIncrement(long present, long count) {
      if (count != this.queueEmptiedCounter) {
         this.myLast = 0L;
         this.queueEmptiedCounter = count;
      }

      long ahead = this.myLast - present;
      if (ahead < 0L) {
         this.myLast = present + this.vtDeltaFirst;
         return this.vtDeltaFirst;
      } else {
         this.myLast += this.vtDeltaRepeat;
         this.vtIncrement = ahead + this.vtDeltaRepeat;
         if (this.vtIncrement > this.requestQueueMaxValue - present) {
            this.vtIncrement = this.requestQueueMaxValue - present;
         }

         return this.vtIncrement;
      }
   }

   protected final synchronized void setIncrements(long d1, long dr) {
      this.vtDeltaFirst = d1;
      this.vtDeltaRepeat = dr;
   }

   protected final long getDeltaFirst() {
      return this.vtDeltaFirst;
   }

   protected final long getDelta() {
      return this.vtDeltaRepeat;
   }

   protected long getIncrementForThreadPriorityCalculation() {
      return this.vtDeltaRepeat;
   }

   public final int compareTo(Object o) {
      ServiceClassSupport scs = (ServiceClassSupport)o;
      return this.vtDeltaRepeat == scs.vtDeltaRepeat ? scs.getPendingRequestsCount() - this.getPendingRequestsCount() : (int)(this.vtDeltaRepeat - scs.vtDeltaRepeat);
   }

   public final int getPendingRequestsCount() {
      return this.pendingRequestCount.get();
   }

   void incrementPendingRequestCount() {
      this.pendingRequestCount.getAndIncrement();
   }

   void decrementPendingRequestCount() {
      this.pendingRequestCount.getAndDecrement();
   }

   public final long getMyLast() {
      return this.myLast;
   }

   public final long getVirtualTimeIncrement() {
      return this.vtIncrement;
   }

   void setThreadPriority(int pri) {
      if (this.threadPriority != pri) {
         this.threadPriority = pri;
      }
   }

   public int getThreadPriority() {
      return this.threadPriority;
   }

   public boolean isInternal() {
      return this.internal;
   }

   void setInternal(boolean internal) {
      this.internal = internal;
   }

   void setShared(boolean shared) {
      this.isShared = shared;
   }

   boolean isShared() {
      return this.isShared;
   }

   public PartitionFairShare getPartitionFairShare() {
      return this.partitionFairShare;
   }

   ServiceClassSupport createCopy(PartitionFairShare partitionFairShare) {
      return this;
   }
}
