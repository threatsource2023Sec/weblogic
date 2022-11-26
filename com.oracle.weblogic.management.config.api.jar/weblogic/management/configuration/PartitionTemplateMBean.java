package weblogic.management.configuration;

public interface PartitionTemplateMBean extends PartitionMBean {
   PartitionConfiguratorMBean[] getPartitionConfigurators();

   PartitionConfiguratorMBean lookupPartitionConfigurator(String var1);

   void destroyPartitionConfigurator(PartitionConfiguratorMBean var1);

   PartitionConfiguratorMBean createPartitionConfigurator(String var1, String var2);

   String[] listPartitionConfiguratorServiceNames();
}
