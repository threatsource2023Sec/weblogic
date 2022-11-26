package weblogic.cache.util;

import weblogic.cache.CacheMap;
import weblogic.cache.configuration.CacheBean;

public class CacheRegistryEntry {
   private final CacheMap cache;
   private CacheBean configuration;

   public CacheRegistryEntry(CacheMap cache, CacheBean configuration) {
      this.cache = cache;
      this.configuration = configuration;
   }

   public CacheMap getCache() {
      return this.cache;
   }

   public CacheBean getConfiguration() {
      return this.configuration;
   }

   public void setConfiguration(CacheBean configuration) {
      this.configuration = configuration;
   }
}
