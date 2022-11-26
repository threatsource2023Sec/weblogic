package weblogic.management.configuration;

public interface PartitionFileSystemMBean extends ConfigurationMBean {
   String getRoot();

   void setRoot(String var1);

   boolean isCreateOnDemand();

   void setCreateOnDemand(boolean var1);

   boolean isPreserved();

   void setPreserved(boolean var1);
}
