package weblogic.jms.cache;

public interface CacheEntryChangeListener {
   void onCacheEntryAdd(CacheEntry var1);

   void onCacheEntryRemove(CacheEntry var1);

   void onCacheEntryAdd(CacheEntry[] var1);

   void onCacheEntryRemove(CacheEntry[] var1);
}
