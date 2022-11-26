package weblogic.ejb.container.interfaces;

import java.util.Collection;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.CacheKey;
import weblogic.ejb.container.cache.QueryCacheElement;
import weblogic.ejb.container.cache.QueryCacheKey;

public interface QueryCache extends weblogic.ejb.spi.QueryCache {
   Object NULL_VALUE = new Object() {
   };

   Object get(Object var1, QueryCacheKey var2, boolean var3, boolean var4) throws InternalException;

   boolean put(QueryCacheKey var1, Collection var2);

   boolean put(QueryCacheKey var1, QueryCacheElement var2);

   void invalidate(CacheKey var1);

   void invalidateAll();
}
