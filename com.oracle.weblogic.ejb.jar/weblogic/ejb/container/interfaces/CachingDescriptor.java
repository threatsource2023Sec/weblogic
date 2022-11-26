package weblogic.ejb.container.interfaces;

public interface CachingDescriptor {
   int getMaxBeansInCache();

   int getMaxQueriesInCache();

   int getMaxBeansInFreePool();

   int getInitialBeansInFreePool();

   void setIdleTimeoutSecondsCache(int var1);

   int getIdleTimeoutSecondsCache();

   String getCacheType();

   void setReadTimeoutSeconds(int var1);

   int getReadTimeoutSeconds();

   int getIdleTimeoutSecondsPool();

   String getConcurrencyStrategy();
}
