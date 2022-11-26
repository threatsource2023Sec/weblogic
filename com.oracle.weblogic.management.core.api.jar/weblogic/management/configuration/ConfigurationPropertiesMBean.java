package weblogic.management.configuration;

public interface ConfigurationPropertiesMBean extends ConfigurationMBean {
   ConfigurationPropertyMBean[] getConfigurationProperties();

   ConfigurationPropertyMBean lookupConfigurationProperty(String var1);

   ConfigurationPropertyMBean createConfigurationProperty(String var1);

   void destroyConfigurationProperty(ConfigurationPropertyMBean var1);
}
