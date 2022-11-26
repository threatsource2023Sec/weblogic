package weblogic.cache.util;

import java.util.Map;
import weblogic.cache.CacheMap;
import weblogic.cache.MapAdapterFactory;
import weblogic.cache.configuration.CacheBean;

public class MonitoredMapAdapterFactory implements MapAdapterFactory {
   public CacheMap adapt(CacheMap map, CacheBean conf) {
      return new MonitoredMapAdapter(map);
   }

   public CacheMap unwrap(CacheMap map) {
      return ((MonitoredMapAdapter)map).getDelegate();
   }

   public void reconfigure(CacheMap map, CacheBean oldConf, CacheBean conf, Map diff) {
   }
}
