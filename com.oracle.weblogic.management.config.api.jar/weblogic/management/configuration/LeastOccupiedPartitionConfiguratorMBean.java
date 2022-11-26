package weblogic.management.configuration;

public interface LeastOccupiedPartitionConfiguratorMBean extends PartitionConfiguratorMBean {
   String[] getQueryTags();

   void setQueryTags(String[] var1);

   boolean isMustContainAllQueryTags();

   void setMustContainAllQueryTags(boolean var1);

   void setTargetResourceGroups(ResourceGroupMBean[] var1);

   void addTargetResourceGroup(ResourceGroupMBean var1);

   ResourceGroupMBean[] getTargetResourceGroups();

   void setVirtualTargetOrHostName(String var1);

   String getVirtualTargetOrHostName();
}
