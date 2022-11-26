package weblogic.cache.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import weblogic.cache.CacheEntry;
import weblogic.cache.EvictionStrategy;

public class BatchLRUEvictionStrategy extends BaseEvictionStrategy implements EvictionStrategy {
   private final ArrayList l = new ArrayList();
   private final int low;
   private static final Comparator DISCARDED = new EntryComparator();

   public BatchLRUEvictionStrategy(int lowWaterMark, long cacheTTL, long cacheIdleTime) {
      super(cacheTTL, cacheIdleTime);
      this.low = lowWaterMark;
   }

   public CacheEntry createEntry(Object key, Object value) {
      CacheEntry e = super.createEntry(key, value);
      this.l.add(e);
      return e;
   }

   public void updateEntry(CacheEntry entry, Object value) {
      entry.setValue(value);
   }

   public CacheEntry restoreEntry(CacheEntry entry) {
      BaseCacheEntry base;
      if (entry instanceof BaseCacheEntry) {
         base = (BaseCacheEntry)entry;
         base.restore();
         if (!this.l.contains(base)) {
            this.l.add(base);
         }
      } else {
         base = new BaseCacheEntry(entry, this.idleTime, this.ttl);
         this.l.add(base);
      }

      return base;
   }

   public Map evict() {
      Map m = new HashMap();
      Collections.sort(this.l, DISCARDED);

      int i;
      CacheEntry e;
      for(i = this.l.size(); i > 0; --i) {
         e = (CacheEntry)this.l.get(i);
         if (!e.isDiscarded()) {
            break;
         }

         this.l.remove(i);
      }

      for(i = this.l.size(); i > this.low; --i) {
         e = (CacheEntry)this.l.remove(i);
         m.put(e.getKey(), e.getValue());
      }

      return m;
   }

   public void clear() {
      this.l.clear();
   }

   public static class EntryComparator implements Comparator {
      public int compare(BaseCacheEntry e1, BaseCacheEntry e2) {
         if (e1.isDiscarded()) {
            return e2.isDiscarded() ? 0 : -1;
         } else if (e2.isDiscarded()) {
            return 1;
         } else {
            long currTime = System.currentTimeMillis();
            long diff;
            if (currTime <= e1.getExpirationTime() && currTime <= e2.getExpirationTime()) {
               diff = e2.getLastAccessTime() - e1.getLastAccessTime();
            } else {
               diff = e2.getExpirationTime() - e1.getExpirationTime();
               if (diff == 0L) {
                  diff = e2.getLastAccessTime() - e1.getLastAccessTime();
               }
            }

            if (diff > 0L) {
               return 1;
            } else {
               return diff < 0L ? -1 : 0;
            }
         }
      }
   }
}
