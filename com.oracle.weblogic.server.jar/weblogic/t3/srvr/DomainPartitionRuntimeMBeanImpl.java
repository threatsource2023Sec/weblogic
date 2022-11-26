package weblogic.t3.srvr;

import weblogic.management.ManagementException;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.PartitionLifeCycleRuntimeMBean;
import weblogic.management.runtime.PartitionUserFileSystemManagerMBean;

public final class DomainPartitionRuntimeMBeanImpl extends DomainRuntimeMBeanDelegate implements DomainPartitionRuntimeMBean {
   private DeploymentManagerMBean deploymentManagerMBean;
   private DeployerRuntimeMBean deployerRuntimeMBean;
   private EditSessionConfigurationManagerMBean editSessionConfigurationManagerMBean;
   private PartitionLifeCycleRuntimeMBean partitionLifeCycleRuntimeMBean;
   private AppRuntimeStateRuntimeMBean appRuntimeStateRuntimeMBean;
   private PartitionUserFileSystemManagerMBean partitionUserFileSystemManagerMBean;
   private String partitionID;

   DomainPartitionRuntimeMBeanImpl(String name, String ID) throws ManagementException {
      super(name);
      this.partitionID = ID;
   }

   public String getPartitionID() {
      return this.partitionID;
   }

   public DeploymentManagerMBean getDeploymentManager() {
      return this.deploymentManagerMBean;
   }

   public void setDeploymentManager(DeploymentManagerMBean bean) {
      this.deploymentManagerMBean = bean;
   }

   public DeployerRuntimeMBean getDeployerRuntime() {
      return this.deployerRuntimeMBean;
   }

   public void setDeployerRuntime(DeployerRuntimeMBean bean) {
      this.deployerRuntimeMBean = bean;
   }

   public PartitionLifeCycleRuntimeMBean getPartitionLifeCycleRuntime() {
      return this.partitionLifeCycleRuntimeMBean;
   }

   public void setPartitionLifeCycleRuntime(PartitionLifeCycleRuntimeMBean bean) {
      this.partitionLifeCycleRuntimeMBean = bean;
   }

   public void setEditSessionConfigurationManager(EditSessionConfigurationManagerMBean bean) {
      this.editSessionConfigurationManagerMBean = bean;
   }

   public EditSessionConfigurationManagerMBean getEditSessionConfigurationManager() {
      return this.editSessionConfigurationManagerMBean;
   }

   public void setAppRuntimeStateRuntime(AppRuntimeStateRuntimeMBean bean) {
      this.appRuntimeStateRuntimeMBean = bean;
   }

   public AppRuntimeStateRuntimeMBean getAppRuntimeStateRuntime() {
      return this.appRuntimeStateRuntimeMBean;
   }

   public void setPartitionUserFileSystemManager(PartitionUserFileSystemManagerMBean bean) {
      this.partitionUserFileSystemManagerMBean = bean;
   }

   public PartitionUserFileSystemManagerMBean getPartitionUserFileSystemManager() {
      return this.partitionUserFileSystemManagerMBean;
   }
}
