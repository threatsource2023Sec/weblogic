package weblogic.cache.util;

import weblogic.cache.CacheEntry;

public class FIFOEvictionStrategy extends LRUEvictionStrategy {
   public FIFOEvictionStrategy(long ttl, long idleTime) {
      super(ttl, idleTime);
   }

   public CacheEntry createEntry(Object key, Object value) {
      return new Entry(key, value, this.idleTime, this.ttl, this.header);
   }

   protected static class Entry extends LRUEvictionStrategy.Entry {
      public Entry(Object key, Object value, long idleTime, long ttl, LRUEvictionStrategy.Entry header) {
         super(key, value, idleTime, ttl, header);
      }

      protected void reorder() {
      }
   }
}
