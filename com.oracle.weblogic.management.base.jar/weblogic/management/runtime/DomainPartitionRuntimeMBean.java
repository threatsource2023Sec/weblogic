package weblogic.management.runtime;

public interface DomainPartitionRuntimeMBean extends RuntimeMBean {
   void setDeploymentManager(DeploymentManagerMBean var1);

   DeploymentManagerMBean getDeploymentManager();

   void setDeployerRuntime(DeployerRuntimeMBean var1);

   DeployerRuntimeMBean getDeployerRuntime();

   void setPartitionLifeCycleRuntime(PartitionLifeCycleRuntimeMBean var1);

   PartitionLifeCycleRuntimeMBean getPartitionLifeCycleRuntime();

   void setEditSessionConfigurationManager(EditSessionConfigurationManagerMBean var1);

   EditSessionConfigurationManagerMBean getEditSessionConfigurationManager();

   void setAppRuntimeStateRuntime(AppRuntimeStateRuntimeMBean var1);

   AppRuntimeStateRuntimeMBean getAppRuntimeStateRuntime();

   void setPartitionUserFileSystemManager(PartitionUserFileSystemManagerMBean var1);

   PartitionUserFileSystemManagerMBean getPartitionUserFileSystemManager();

   String getPartitionID();
}
