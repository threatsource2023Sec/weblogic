package weblogic.cache.util;

import weblogic.cache.MapStatistics;

class MapStatisticsSupport implements MapStatistics {
   private long queries;
   private long reads;
   private long creates;
   private long updates;
   private long deletes;
   private long clears;

   public synchronized long getQueryCount() {
      return this.queries;
   }

   public synchronized void incrementQueryCount() {
      ++this.queries;
   }

   public synchronized long getReadCount() {
      return this.reads;
   }

   public synchronized void incrementReadCount() {
      ++this.reads;
   }

   public synchronized long getCreateCount() {
      return this.creates;
   }

   public synchronized void incrementCreateCount() {
      ++this.creates;
   }

   public synchronized long getUpdateCount() {
      return this.updates;
   }

   public synchronized void incrementUpdateCount() {
      ++this.updates;
   }

   public synchronized long getDeleteCount() {
      return this.deletes;
   }

   public synchronized void incrementDeleteCount() {
      ++this.deletes;
   }

   public synchronized long getClearCount() {
      return this.clears;
   }

   public synchronized void incrementClearCount() {
      ++this.clears;
   }

   public synchronized void reset() {
      this.queries = 0L;
      this.reads = 0L;
      this.creates = 0L;
      this.updates = 0L;
      this.deletes = 0L;
      this.clears = 0L;
   }
}
