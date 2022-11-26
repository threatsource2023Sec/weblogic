package weblogic.cache.util;

import weblogic.cache.CacheMap;

public class MonitoredMapAdapter extends DelegatingCacheMap {
   public MonitoredMapAdapter(CacheMap map) {
      super(map);
   }
}
