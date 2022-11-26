package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface EntityMappingMBean extends XMLElementMBean {
   String getEntityMappingName();

   void setEntityMappingName(String var1);

   String getPublicId();

   void setPublicId(String var1);

   String getSystemId();

   void setSystemId(String var1);

   String getEntityURI();

   void setEntityURI(String var1);

   String getWhenToCache();

   void setWhenToCache(String var1);

   int getCacheTimeoutInterval();

   void setCacheTimeoutInterval(int var1);
}
