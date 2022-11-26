package weblogic.management.runtime;

import java.util.Date;
import weblogic.management.ManagementException;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.SystemResourceMBean;

public interface DomainRuntimeMBean extends RuntimeMBean {
   String getName();

   Date getActivationTime();

   ServerLifeCycleRuntimeMBean[] getServerLifeCycleRuntimes();

   ServerLifeCycleRuntimeMBean lookupServerLifeCycleRuntime(String var1);

   /** @deprecated */
   @Deprecated
   CoherenceServerLifeCycleRuntimeMBean[] getCoherenceServerLifeCycleRuntimes();

   /** @deprecated */
   @Deprecated
   CoherenceServerLifeCycleRuntimeMBean lookupCoherenceServerLifeCycleRuntime(String var1);

   SystemComponentLifeCycleRuntimeMBean[] getSystemComponentLifeCycleRuntimes();

   SystemComponentLifeCycleRuntimeMBean lookupSystemComponentLifeCycleRuntime(String var1);

   /** @deprecated */
   @Deprecated
   DeployerRuntimeMBean getDeployerRuntime();

   DeploymentManagerMBean getDeploymentManager();

   AppRuntimeStateRuntimeMBean getAppRuntimeStateRuntime();

   MigratableServiceCoordinatorRuntimeMBean getMigratableServiceCoordinatorRuntime();

   MigrationDataRuntimeMBean[] getMigrationDataRuntimes();

   LogRuntimeMBean getLogRuntime();

   void setLogRuntime(LogRuntimeMBean var1);

   MessageDrivenControlEJBRuntimeMBean getMessageDrivenControlEJBRuntime();

   void setMessageDrivenControlEJBRuntime(MessageDrivenControlEJBRuntimeMBean var1);

   SNMPAgentRuntimeMBean getSNMPAgentRuntime();

   void setSNMPAgentRuntime(SNMPAgentRuntimeMBean var1);

   ConsoleRuntimeMBean getConsoleRuntime();

   void setConsoleRuntime(ConsoleRuntimeMBean var1);

   WseePolicySubjectManagerRuntimeMBean getPolicySubjectManagerRuntime();

   void setPolicySubjectManagerRuntime(WseePolicySubjectManagerRuntimeMBean var1);

   void restartSystemResource(SystemResourceMBean var1) throws ManagementException;

   ServiceMigrationDataRuntimeMBean[] getServiceMigrationDataRuntimes();

   PartitionLifeCycleTaskRuntimeMBean startPartitionWait(PartitionMBean var1, String var2, int var3) throws ManagementException, PartitionLifeCycleException, InterruptedException, IllegalArgumentException;

   PartitionLifeCycleTaskRuntimeMBean forceShutdownPartitionWait(PartitionMBean var1, int var2) throws ManagementException, PartitionLifeCycleException, InterruptedException, IllegalArgumentException;

   ResourceGroupLifeCycleRuntimeMBean[] getResourceGroupLifeCycleRuntimes();

   ResourceGroupLifeCycleRuntimeMBean lookupResourceGroupLifeCycleRuntime(String var1);

   DomainPartitionRuntimeMBean[] getDomainPartitionRuntimes();

   DomainPartitionRuntimeMBean lookupDomainPartitionRuntime(String var1);

   ElasticServiceManagerRuntimeMBean getElasticServiceManagerRuntime();

   void setElasticServiceManagerRuntime(ElasticServiceManagerRuntimeMBean var1);

   RolloutServiceRuntimeMBean getRolloutService();

   void setRolloutService(RolloutServiceRuntimeMBean var1);

   BatchJobRepositoryRuntimeMBean getBatchJobRepositoryRuntime();

   void setBatchJobRepositoryRuntime(BatchJobRepositoryRuntimeMBean var1);

   void setEditSessionConfigurationManager(EditSessionConfigurationManagerMBean var1);

   EditSessionConfigurationManagerMBean getEditSessionConfigurationManager();

   NodeManagerRuntimeMBean[] getNodeManagerRuntimes();

   DomainPartitionRuntimeMBean getCurrentDomainPartitionRuntime();
}
