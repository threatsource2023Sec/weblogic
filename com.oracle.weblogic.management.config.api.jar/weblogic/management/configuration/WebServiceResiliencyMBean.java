package weblogic.management.configuration;

public interface WebServiceResiliencyMBean extends ConfigurationMBean {
   int getRetryCount();

   void setRetryCount(int var1);

   String getRetryDelay();

   void setRetryDelay(String var1);
}
