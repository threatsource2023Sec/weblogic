package weblogic.store.internal;

import weblogic.store.OperationStatistics;

public class OperationStatisticsImpl implements OperationStatistics {
   protected String name;
   private StoreStatisticsImpl storeStats;
   private long createCount;
   private long readCount;
   private long updateCount;
   private long deleteCount;
   private long initialObjectCount;

   OperationStatisticsImpl(String name) {
      this(name, (StoreStatisticsImpl)null);
   }

   OperationStatisticsImpl(String name, StoreStatisticsImpl storeStats) {
      this.name = name;
      this.storeStats = storeStats;
   }

   public String getName() {
      return this.name;
   }

   public long getCreateCount() {
      return this.createCount;
   }

   void incrementCreateCount() {
      ++this.createCount;
      if (this.storeStats != null) {
         this.storeStats.incrementCreateCount();
      }

   }

   public long getReadCount() {
      return this.readCount;
   }

   void incrementReadCount() {
      ++this.readCount;
      if (this.storeStats != null) {
         this.storeStats.incrementReadCount();
      }

   }

   public long getUpdateCount() {
      return this.updateCount;
   }

   void incrementUpdateCount() {
      ++this.updateCount;
      if (this.storeStats != null) {
         this.storeStats.incrementUpdateCount();
      }

   }

   public long getDeleteCount() {
      return this.deleteCount;
   }

   void incrementDeleteCount() {
      ++this.deleteCount;
      if (this.storeStats != null) {
         this.storeStats.incrementDeleteCount();
      }

   }

   void incrementDeleteCount(long n) {
      this.deleteCount += n;
      if (this.storeStats != null) {
         this.storeStats.incrementDeleteCount(n);
      }

   }

   public long getObjectCount() {
      return this.initialObjectCount + this.createCount - this.deleteCount;
   }

   void setInitialObjectCount(long initialObjectCount) {
      this.initialObjectCount = initialObjectCount;
   }
}
