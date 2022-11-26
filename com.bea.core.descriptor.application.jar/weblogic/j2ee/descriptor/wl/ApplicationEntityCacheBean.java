package weblogic.j2ee.descriptor.wl;

public interface ApplicationEntityCacheBean {
   String getEntityCacheName();

   void setEntityCacheName(String var1);

   int getMaxBeansInCache();

   void setMaxBeansInCache(int var1);

   MaxCacheSizeBean getMaxCacheSize();

   MaxCacheSizeBean createMaxCacheSize();

   void destroyMaxCacheSize(MaxCacheSizeBean var1);

   int getMaxQueriesInCache();

   void setMaxQueriesInCache(int var1);

   String getCachingStrategy();

   void setCachingStrategy(String var1);
}
