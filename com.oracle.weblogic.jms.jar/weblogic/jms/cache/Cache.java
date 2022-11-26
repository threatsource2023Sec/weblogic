package weblogic.jms.cache;

import javax.naming.Context;

public interface Cache {
   String getName();

   void setName(String var1);

   CacheEntry[] getCacheEntries();

   void setCacheContextInfo(CacheContextInfo var1);

   CacheContextInfo getCacheContextInfo();

   void setProviderContext(Context var1);

   Context getProviderContext();

   boolean isForLocalCluster();
}
