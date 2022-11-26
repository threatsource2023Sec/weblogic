package weblogic.j2ee.descriptor.wl;

public interface EntityMappingBean {
   String getEntityMappingName();

   void setEntityMappingName(String var1);

   String getPublicId();

   void setPublicId(String var1);

   String getSystemId();

   void setSystemId(String var1);

   String getEntityUri();

   void setEntityUri(String var1);

   String getWhenToCache();

   void setWhenToCache(String var1);

   int getCacheTimeoutInterval();

   void setCacheTimeoutInterval(int var1);
}
