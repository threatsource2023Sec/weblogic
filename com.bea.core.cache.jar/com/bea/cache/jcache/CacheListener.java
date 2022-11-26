package com.bea.cache.jcache;

import java.util.Collection;
import java.util.Map;

public interface CacheListener {
   void onLoad(Map var1);

   void onLoadError(Collection var1, Throwable var2);

   void onStore(Map var1);

   void onStoreError(Map var1, Throwable var2);

   void onCreate(Map var1);

   void onUpdate(Map var1);

   void onEvict(Map var1);

   void onRemove(Map var1);

   void onClear();
}
