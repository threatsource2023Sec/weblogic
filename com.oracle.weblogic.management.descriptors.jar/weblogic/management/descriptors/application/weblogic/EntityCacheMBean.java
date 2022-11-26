package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface EntityCacheMBean extends XMLElementMBean {
   String getEntityCacheName();

   void setEntityCacheName(String var1);

   int getMaxBeansInCache();

   void setMaxBeansInCache(int var1);

   MaxCacheSizeMBean getMaxCacheSize();

   void setMaxCacheSize(MaxCacheSizeMBean var1);

   void setCachingStrategy(String var1);

   String getCachingStrategy();
}
