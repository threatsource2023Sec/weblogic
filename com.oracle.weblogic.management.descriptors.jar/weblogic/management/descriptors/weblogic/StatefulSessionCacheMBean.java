package weblogic.management.descriptors.weblogic;

public interface StatefulSessionCacheMBean extends WeblogicBeanDescriptorMBean {
   int getMaxBeansInCache();

   void setMaxBeansInCache(int var1);

   int getIdleTimeoutSeconds();

   void setIdleTimeoutSeconds(int var1);

   int getSessionTimeoutSeconds();

   void setSessionTimeoutSeconds(int var1);

   void setCacheType(String var1);

   String getCacheType();
}
