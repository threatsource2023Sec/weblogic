package weblogic.cache;

import java.util.Map;
import java.util.Set;
import weblogic.cache.configuration.CacheBean;

public interface CacheManager {
   CacheMap createCache(String var1, Map var2);

   void startCache(String var1);

   void stopCache(String var1);

   void forceStopCache(String var1);

   void unregisterCache(String var1);

   void destroyCache(String var1);

   CacheMap createCache(CacheBean var1);

   CacheMap getCache(String var1);

   Set getCacheNames();

   Map reconfigureCache(String var1, Map var2, boolean var3) throws CacheRuntimeException;

   CacheMap createUnregisteredCache(Map var1);
}
