package weblogic.management.descriptors.weblogic;

public interface EntityCacheRefMBean extends WeblogicBeanDescriptorMBean {
   String getEntityCacheName();

   void setEntityCacheName(String var1);

   int getReadTimeoutSeconds();

   void setReadTimeoutSeconds(int var1);

   String getConcurrencyStrategy();

   void setConcurrencyStrategy(String var1);

   boolean getCacheBetweenTransactions();

   void setCacheBetweenTransactions(boolean var1);

   int getEstimatedBeanSize();

   void setEstimatedBeanSize(int var1);

   int getIdleTimeoutSeconds();

   void setIdleTimeoutSeconds(int var1);
}
