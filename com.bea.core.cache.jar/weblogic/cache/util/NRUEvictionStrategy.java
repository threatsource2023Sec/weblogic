package weblogic.cache.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import weblogic.cache.CacheEntry;
import weblogic.cache.EvictionStrategy;

public class NRUEvictionStrategy extends BaseEvictionStrategy implements EvictionStrategy {
   private final List list = new Vector();
   private long evictionCheckTime = 0L;

   public NRUEvictionStrategy(long ttl, long idleTime) {
      super(ttl, idleTime);
   }

   public CacheEntry createEntry(Object key, Object value) {
      CacheEntry e = super.createEntry(key, value);
      this.list.add(e);
      return e;
   }

   public void updateEntry(CacheEntry entry, Object value) {
      this.list.remove(entry);
      entry.setValue(value);
      this.list.add((BaseCacheEntry)entry);
   }

   public CacheEntry restoreEntry(CacheEntry entry) {
      BaseCacheEntry base;
      if (entry instanceof BaseCacheEntry) {
         base = (BaseCacheEntry)entry;
         base.restore();
         if (!this.list.contains(base)) {
            this.list.add(base);
         }
      } else {
         base = new BaseCacheEntry(entry, this.idleTime, this.ttl);
         this.list.add(base);
      }

      return base;
   }

   public Map evict() {
      label26:
      while(true) {
         long lastEvictionCheck = this.evictionCheckTime;
         this.evictionCheckTime = System.currentTimeMillis();
         Iterator var3 = this.list.iterator();

         CacheEntry candidate;
         do {
            do {
               if (!var3.hasNext()) {
                  continue label26;
               }

               candidate = (CacheEntry)var3.next();
            } while(candidate.isDiscarded());
         } while(candidate.getExpirationTime() >= this.evictionCheckTime && (lastEvictionCheck <= 0L || candidate.getLastAccessTime() >= lastEvictionCheck));

         this.list.remove(candidate);
         return Collections.singletonMap(candidate.getKey(), candidate.getValue());
      }
   }

   public void clear() {
      this.list.clear();
   }
}
