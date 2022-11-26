package weblogic.ejb.container.interfaces;

import java.rmi.RemoteException;
import javax.naming.Context;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.CacheKey;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb.spi.WLDeploymentException;

public interface CachingManager {
   void setup(BaseEJBRemoteHomeIntf var1, BaseEJBLocalHomeIntf var2, BeanInfo var3, Context var4, EJBCache var5, ISecurityHelper var6) throws WLDeploymentException;

   int getBeanSize();

   int getIdleTimeoutSeconds();

   void removedFromCache(CacheKey var1, Object var2);

   void removedOnError(CacheKey var1, Object var2);

   void swapIn(CacheKey var1, Object var2);

   void swapOut(CacheKey var1, Object var2, long var3);

   boolean needsRemoval(Object var1);

   void updateMaxBeansInCache(int var1);

   void updateIdleTimeoutSecondsCache(int var1);

   void doEjbRemove(Object var1) throws RemoteException, InternalException;
}
