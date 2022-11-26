package weblogic.cache.store;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.cache.CacheLoader;

public abstract class AggregatingCacheLoader implements CacheLoader {
   public Map loadAll(Collection keys) {
      Iterator iter = keys.iterator();
      Map loadedValues = new HashMap();

      while(iter.hasNext()) {
         Object key = iter.next();
         Object value = this.load(key);
         if (value != null) {
            loadedValues.put(key, value);
         }
      }

      return loadedValues;
   }
}
