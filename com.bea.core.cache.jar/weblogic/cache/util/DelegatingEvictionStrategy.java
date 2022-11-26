package weblogic.cache.util;

import java.util.Map;
import weblogic.cache.CacheEntry;

public class DelegatingEvictionStrategy extends BaseEvictionStrategy {
   private final BaseEvictionStrategy delegate;

   public DelegatingEvictionStrategy(BaseEvictionStrategy delegate) {
      super(delegate.getTTL(), delegate.getIdleTime());
      this.delegate = delegate;
   }

   public void setTTL(long ttl) {
      this.delegate.setTTL(ttl);
   }

   public void setIdleTime(long idleTime) {
      this.delegate.setIdleTime(idleTime);
   }

   public void clear() {
      this.delegate.clear();
   }

   public CacheEntry createEntry(Object key, Object value) {
      return this.delegate.createEntry(key, value);
   }

   public Map evict() {
      return this.delegate.evict();
   }

   public CacheEntry restoreEntry(CacheEntry entry) {
      return this.delegate.restoreEntry(entry);
   }

   public void updateEntry(CacheEntry entry, Object value) {
      this.delegate.updateEntry(entry, value);
   }
}
