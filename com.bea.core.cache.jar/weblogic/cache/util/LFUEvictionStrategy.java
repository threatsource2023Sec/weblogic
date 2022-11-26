package weblogic.cache.util;

import weblogic.cache.CacheEntry;

public class LFUEvictionStrategy extends LRUEvictionStrategy {
   public LFUEvictionStrategy(long ttl, long idleTime) {
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
         synchronized(this.strategyHeader) {
            if (this.previous != this.strategyHeader && this.previous != null) {
               this.previous.next = this.next;
               this.next.previous = this.previous;
               this.previous = this.previous.previous;
               this.next = this.next.previous;
            }
         }
      }
   }
}
