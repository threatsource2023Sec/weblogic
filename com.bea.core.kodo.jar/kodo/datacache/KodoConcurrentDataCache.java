package kodo.datacache;

import kodo.util.MonitoringCacheMap;
import org.apache.openjpa.datacache.ConcurrentDataCache;
import org.apache.openjpa.util.CacheMap;

public class KodoConcurrentDataCache extends ConcurrentDataCache {
   protected CacheMap newCacheMap() {
      return new MonitoringCacheMap() {
         protected void entryRemoved(Object key, Object value, boolean expired) {
            KodoConcurrentDataCache.this.keyRemoved(key, expired);
         }
      };
   }

   protected void keyRemoved(Object key, boolean expired) {
      super.keyRemoved(key, expired);
   }
}
