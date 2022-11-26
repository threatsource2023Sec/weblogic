package weblogic.diagnostics.collections;

import weblogic.diagnostics.debug.DebugLogger;

public class DataIteratorImpl implements DataIterator {
   private static final DebugLogger ACC_DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private BulkIterator bulkIterator;
   private int fetchSize;
   private Object[] currentCache;
   private int currentIndex;

   public DataIteratorImpl(BulkIterator iter) {
      this(iter, 100);
   }

   public DataIteratorImpl(BulkIterator iter, int maxCacheSize) {
      this.currentIndex = 0;
      this.bulkIterator = iter;
      this.fetchSize = maxCacheSize;
   }

   public boolean hasNext() {
      if (this.isCacheStale()) {
         if (ACC_DEBUG.isDebugEnabled()) {
            ACC_DEBUG.debug("Calling hasNext() on remote stub");
         }

         return this.bulkIterator.hasNext();
      } else {
         return this.currentIndex < this.currentCache.length;
      }
   }

   public Object next() {
      if (this.isCacheStale()) {
         if (ACC_DEBUG.isDebugEnabled()) {
            ACC_DEBUG.debug("Calling fetchNext() on remote stub");
         }

         this.currentCache = this.bulkIterator.fetchNext(this.fetchSize);
         this.currentIndex = 0;
      }

      return this.currentCache[this.currentIndex++];
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   private boolean isCacheStale() {
      return this.currentCache == null || this.currentIndex > this.currentCache.length - 1;
   }
}
