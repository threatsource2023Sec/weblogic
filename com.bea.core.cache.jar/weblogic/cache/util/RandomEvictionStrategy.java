package weblogic.cache.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import weblogic.cache.CacheEntry;
import weblogic.cache.EvictionStrategy;

public class RandomEvictionStrategy extends BaseEvictionStrategy implements EvictionStrategy {
   private final List entries = new Vector();
   private final Random random = new Random(System.currentTimeMillis());

   public RandomEvictionStrategy(long ttl, long idleTime) {
      super(ttl, idleTime);
   }

   public CacheEntry createEntry(Object key, Object value) {
      Entry entry = new Entry(key, value, this.idleTime, this.ttl);
      this.entries.add(entry);
      return entry;
   }

   public void clear() {
      this.entries.clear();
      super.clear();
   }

   public Map evict() {
      Entry evictedEntry = null;
      synchronized(this.entries) {
         int randomEntry = this.random.nextInt(this.entries.size());
         evictedEntry = (Entry)this.entries.remove(randomEntry);
      }

      return Collections.singletonMap(evictedEntry.getKey(), evictedEntry.getValue());
   }

   public class Entry extends BaseCacheEntry {
      public Entry(Object key, Object value, long idleTime, long ttl) {
         super(key, value, idleTime, ttl);
      }

      public void discard() {
         RandomEvictionStrategy.this.entries.remove(this);
         super.discard();
      }
   }
}
