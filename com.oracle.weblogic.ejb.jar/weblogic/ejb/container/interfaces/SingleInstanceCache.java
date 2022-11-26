package weblogic.ejb.container.interfaces;

import weblogic.ejb.container.cache.CacheKey;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb20.cache.CacheFullException;

public interface SingleInstanceCache extends EJBCache {
   Object get(CacheKey var1);

   void put(CacheKey var1, Object var2) throws CacheFullException;

   void release(CacheKey var1);

   void remove(CacheKey var1);

   void clear();
}
