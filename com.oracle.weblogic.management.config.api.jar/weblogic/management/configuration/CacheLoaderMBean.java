package weblogic.management.configuration;

public interface CacheLoaderMBean extends ConfigurationMBean {
   String getCustomLoader();

   void setCustomLoader(String var1);

   boolean isCustomLoaderSet();
}
