package org.glassfish.grizzly.http.util;

import org.glassfish.grizzly.ThreadCache;

public class CacheableDataChunk extends DataChunk {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(CacheableDataChunk.class, 2);

   public static CacheableDataChunk create() {
      CacheableDataChunk dataChunk = (CacheableDataChunk)ThreadCache.takeFromCache(CACHE_IDX);
      return dataChunk != null ? dataChunk : new CacheableDataChunk();
   }

   public void reset() {
      super.reset();
   }

   public void recycle() {
      this.reset();
      ThreadCache.putToCache(CACHE_IDX, this);
   }
}
