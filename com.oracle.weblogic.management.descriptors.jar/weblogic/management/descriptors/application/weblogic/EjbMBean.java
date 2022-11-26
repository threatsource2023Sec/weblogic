package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface EjbMBean extends XMLElementMBean {
   EntityCacheMBean[] getEntityCaches();

   void setEntityCaches(EntityCacheMBean[] var1);

   void addEntityCache(EntityCacheMBean var1);

   void removeEntityCache(EntityCacheMBean var1);

   boolean getStartMdbsWithApplication();

   void setStartMdbsWithApplication(boolean var1);
}
