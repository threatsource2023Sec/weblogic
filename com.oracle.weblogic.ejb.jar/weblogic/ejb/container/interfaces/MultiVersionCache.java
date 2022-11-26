package weblogic.ejb.container.interfaces;

import javax.ejb.EntityBean;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.CacheKey;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.persistence.spi.RSInfo;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb20.cache.CacheFullException;

public interface MultiVersionCache extends EJBCache {
   boolean contains(Object var1, CacheKey var2);

   EntityBean get(Object var1, CacheKey var2, boolean var3) throws InternalException;

   EntityBean get(Object var1, CacheKey var2, RSInfo var3, boolean var4) throws InternalException;

   EntityBean getActive(Object var1, CacheKey var2, boolean var3);

   EntityBean getIfNotTimedOut(Object var1, CacheKey var2, boolean var3) throws InternalException;

   void put(Object var1, CacheKey var2, EntityBean var3, BaseEntityManager var4, boolean var5) throws CacheFullException;

   void release(Object var1, CacheKey var2);

   void removeOnError(Object var1, CacheKey var2);

   void remove(Object var1, CacheKey var2);
}
