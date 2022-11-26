package weblogic.j2ee.descriptor.wl;

public interface StatefulSessionCacheBean {
   int getMaxBeansInCache();

   void setMaxBeansInCache(int var1);

   int getIdleTimeoutSeconds();

   void setIdleTimeoutSeconds(int var1);

   int getSessionTimeoutSeconds();

   void setSessionTimeoutSeconds(int var1);

   String getCacheType();

   void setCacheType(String var1);

   String getId();

   void setId(String var1);
}
