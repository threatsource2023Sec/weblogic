package weblogic.management.configuration;

public interface CoherenceManagementAddressProviderMBean extends ConfigurationMBean {
   String getAddress();

   void setAddress(String var1);

   int getPort();

   void setPort(int var1);
}
