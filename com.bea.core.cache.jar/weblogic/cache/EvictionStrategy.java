package weblogic.cache;

import java.io.Serializable;
import java.util.Map;

public interface EvictionStrategy extends Serializable {
   CacheEntry createEntry(Object var1, Object var2);

   void updateEntry(CacheEntry var1, Object var2);

   CacheEntry restoreEntry(CacheEntry var1);

   Map evict();

   void clear();
}
