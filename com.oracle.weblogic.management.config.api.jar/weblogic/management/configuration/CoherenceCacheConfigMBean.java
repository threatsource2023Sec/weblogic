package weblogic.management.configuration;

public interface CoherenceCacheConfigMBean extends TargetInfoMBean {
   String getCacheConfigurationFile();

   void setCacheConfigurationFile(String var1);

   String getRuntimeCacheConfigurationUri();

   void setRuntimeCacheConfigurationUri(String var1);

   void importCacheConfigurationFile();

   void importCacheConfigurationFile(String var1);

   String getJNDIName();

   void setJNDIName(String var1);
}
