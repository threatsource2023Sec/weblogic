package weblogic.cache.util;

import java.util.HashMap;
import weblogic.cache.CacheMap;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.configuration.CacheBean;

public class CacheRegistry extends HashMap {
   public void register(String name, CacheMap cache, CacheBean configuration) {
      if (name == null) {
         throw new CacheRuntimeException("Unable to register cache with null name");
      } else if (this.containsKey(name)) {
         throw new CacheRuntimeException("The cache already registered with name " + configuration.getName());
      } else {
         this.put(name, new CacheRegistryEntry(cache, configuration));
      }
   }

   public CacheRegistryEntry lookup(String name) {
      if (name == null) {
         throw new CacheRuntimeException("Unable to lookup a cache with name null");
      } else if (!this.containsKey(name)) {
         throw new CacheRuntimeException("Cache not registred");
      } else {
         return (CacheRegistryEntry)this.get(name);
      }
   }
}
