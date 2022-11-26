package weblogic.cache;

public class CacheManagerFactory {
   public CacheManager createCacheManager() {
      return new CacheManagerImpl();
   }
}
