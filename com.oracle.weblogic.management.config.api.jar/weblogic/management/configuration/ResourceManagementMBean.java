package weblogic.management.configuration;

public interface ResourceManagementMBean extends ConfigurationMBean {
   ResourceManagerMBean[] getResourceManagers();

   ResourceManagerMBean lookupResourceManager(String var1);

   ResourceManagerMBean createResourceManager(String var1);

   void destroyResourceManager(ResourceManagerMBean var1);
}
