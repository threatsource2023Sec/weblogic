package weblogic.management.configuration;

public interface ConfigurationExtensionMBean extends ConfigurationMBean {
   String getDescriptorFileName();

   void setDescriptorFileName(String var1);
}
