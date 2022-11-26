package weblogic.management.configuration;

public interface CacheAsyncListenersMBean extends ConfigurationMBean {
   boolean getEnabled();

   void setEnabled(boolean var1);

   String getWorkManager();

   void setWorkManager(String var1);

   boolean isWorkManagerSet();
}
