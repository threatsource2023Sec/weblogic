package weblogic.cache.util;

import java.util.HashMap;
import java.util.Map;
import weblogic.cache.CacheEntry;
import weblogic.cache.EvictionStrategy;

public abstract class BaseEvictionStrategy implements EvictionStrategy {
   protected long ttl;
   protected long idleTime;
   private static final Map emptyEvictionMap = new HashMap();

   protected BaseEvictionStrategy(long ttl, long idleTime) {
      this.ttl = ttl;
      this.idleTime = idleTime;
   }

   public void setTTL(long ttl) {
      this.ttl = ttl;
   }

   public void setIdleTime(long idleTime) {
      this.idleTime = idleTime;
   }

   public long getTTL() {
      return this.ttl;
   }

   public long getIdleTime() {
      return this.idleTime;
   }

   public void clear() {
   }

   public CacheEntry createEntry(Object key, Object value) {
      return new BaseCacheEntry(key, value, this.idleTime, this.ttl);
   }

   public Map evict() {
      return emptyEvictionMap;
   }

   public CacheEntry restoreEntry(CacheEntry entry) {
      return new BaseCacheEntry(entry.getKey(), entry.getValue(), this.idleTime, this.ttl);
   }

   public void updateEntry(CacheEntry entry, Object value) {
      entry.setValue(value);
   }
}
