package weblogic.j2ee.descriptor.wl;

public interface EntityCacheBean {
   int getMaxBeansInCache();

   void setMaxBeansInCache(int var1);

   void setMaxQueriesInCache(int var1);

   int getMaxQueriesInCache();

   int getIdleTimeoutSeconds();

   void setIdleTimeoutSeconds(int var1);

   int getReadTimeoutSeconds();

   void setReadTimeoutSeconds(int var1);

   String getConcurrencyStrategy();

   void setConcurrencyStrategy(String var1);

   boolean isCacheBetweenTransactions();

   void setCacheBetweenTransactions(boolean var1);

   boolean isDisableReadyInstances();

   void setDisableReadyInstances(boolean var1);

   String getId();

   void setId(String var1);
}
