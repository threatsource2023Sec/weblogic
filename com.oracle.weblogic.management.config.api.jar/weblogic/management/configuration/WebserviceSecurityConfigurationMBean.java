package weblogic.management.configuration;

public interface WebserviceSecurityConfigurationMBean extends ConfigurationMBean {
   String getClassName();

   void setClassName(String var1);

   void setTokenType(String var1);

   String getTokenType();

   ConfigurationPropertyMBean[] getConfigurationProperties();

   ConfigurationPropertyMBean lookupConfigurationProperty(String var1);

   ConfigurationPropertyMBean createConfigurationProperty(String var1);

   void destroyConfigurationProperty(ConfigurationPropertyMBean var1);
}
