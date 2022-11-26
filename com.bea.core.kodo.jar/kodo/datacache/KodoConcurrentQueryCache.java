package kodo.datacache;

import kodo.util.MonitoringCacheMap;
import org.apache.openjpa.datacache.ConcurrentQueryCache;
import org.apache.openjpa.util.CacheMap;

public class KodoConcurrentQueryCache extends ConcurrentQueryCache {
   protected CacheMap newCacheMap() {
      return new MonitoringCacheMap();
   }
}
