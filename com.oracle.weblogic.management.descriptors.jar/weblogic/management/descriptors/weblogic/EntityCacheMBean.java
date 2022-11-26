package weblogic.management.descriptors.weblogic;

public interface EntityCacheMBean extends WeblogicBeanDescriptorMBean {
   int getMaxBeansInCache();

   void setMaxBeansInCache(int var1);

   int getIdleTimeoutSeconds();

   void setIdleTimeoutSeconds(int var1);

   int getReadTimeoutSeconds();

   void setReadTimeoutSeconds(int var1);

   String getConcurrencyStrategy();

   void setConcurrencyStrategy(String var1);

   boolean getCacheBetweenTransactions();

   void setCacheBetweenTransactions(boolean var1);
}
