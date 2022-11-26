package weblogic.j2ee.descriptor.wl;

public interface EntityCacheRefBean {
   String getEntityCacheName();

   void setEntityCacheName(String var1);

   int getIdleTimeoutSeconds();

   void setIdleTimeoutSeconds(int var1);

   int getReadTimeoutSeconds();

   void setReadTimeoutSeconds(int var1);

   String getConcurrencyStrategy();

   void setConcurrencyStrategy(String var1);

   boolean isCacheBetweenTransactions();

   void setCacheBetweenTransactions(boolean var1);

   int getEstimatedBeanSize();

   void setEstimatedBeanSize(int var1);

   String getId();

   void setId(String var1);
}
