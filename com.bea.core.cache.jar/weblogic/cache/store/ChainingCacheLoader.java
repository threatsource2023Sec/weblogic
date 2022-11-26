package weblogic.cache.store;

import weblogic.cache.CacheMap;

public class ChainingCacheLoader extends AggregatingCacheLoader {
   private final CacheMap chainedMap;

   public ChainingCacheLoader(CacheMap chainedMap) {
      this.chainedMap = chainedMap;
   }

   public Object load(Object key) {
      return this.chainedMap.get(key);
   }
}
