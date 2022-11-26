package weblogic.management.provider;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.DomainPartitionService;
import weblogic.management.NodeManagerRuntimeService;
import weblogic.management.PartitionLifeCycleService;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.management.runtime.MigratableServiceCoordinatorRuntimeMBean;
import weblogic.management.runtime.SessionHelperManagerRuntimeMBean;

@Contract
public interface DomainAccessSettable {
   void setServerMigrationCoordinator(MigratableServiceCoordinatorRuntimeMBean var1);

   void setDomainMigrationHistory(DomainMigrationHistory var1);

   void startLifecycleService();

   void setDeployerRuntime(DeployerRuntimeMBean var1);

   void setDeploymentManager(DeploymentManagerMBean var1);

   void setSessionHelperManager(SessionHelperManagerRuntimeMBean var1);

   void setPartitionLifeCycleService(PartitionLifeCycleService var1);

   void setDomainPartitionService(DomainPartitionService var1);

   void setNodeManagerRuntimeService(NodeManagerRuntimeService var1);
}
