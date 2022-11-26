package weblogic.cache.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weblogic.cache.CacheLoader;

public class MultiCacheLoader extends AggregatingCacheLoader {
   private final List loaders;

   public MultiCacheLoader() {
      this.loaders = new ArrayList();
   }

   public MultiCacheLoader(Collection loaderCollection) {
      this.loaders = new ArrayList(loaderCollection);
   }

   public void addCacheLoader(CacheLoader loader) {
      this.loaders.add(loader);
   }

   public Object load(Object key) {
      for(int count = 0; count < this.loaders.size(); ++count) {
         Object value = ((CacheLoader)this.loaders.get(count)).load(key);
         if (value != null) {
            return value;
         }
      }

      return null;
   }
}
