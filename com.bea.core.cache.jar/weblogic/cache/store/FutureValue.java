package weblogic.cache.store;

import weblogic.cache.CacheLoadListener;

public interface FutureValue {
   boolean isLoaded();

   Object getValue();

   boolean addListener(CacheLoadListener var1);
}
