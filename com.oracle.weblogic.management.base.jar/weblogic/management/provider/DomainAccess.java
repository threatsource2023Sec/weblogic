package weblogic.management.provider;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.CoherenceServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.MigratableServiceCoordinatorRuntimeMBean;
import weblogic.management.runtime.MigrationDataRuntimeMBean;
import weblogic.management.runtime.NodeManagerRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServiceMigrationDataRuntimeMBean;
import weblogic.management.runtime.SessionHelperManagerRuntimeMBean;
import weblogic.management.runtime.SystemComponentLifeCycleRuntimeMBean;

@Contract
public interface DomainAccess extends RegistrationManager {
   DomainRuntimeMBean getDomainRuntime();

   long getActivationTime();

   DeployerRuntimeMBean getDeployerRuntime();

   DeploymentManagerMBean getDeploymentManager();

   ServerLifeCycleRuntimeMBean[] getServerLifecycleRuntimes();

   /** @deprecated */
   @Deprecated
   CoherenceServerLifeCycleRuntimeMBean lookupCoherenceServerLifecycleRuntime(String var1);

   /** @deprecated */
   @Deprecated
   CoherenceServerLifeCycleRuntimeMBean[] getCoherenceServerLifecycleRuntimes();

   SystemComponentLifeCycleRuntimeMBean lookupSystemComponentLifecycleRuntime(String var1);

   SystemComponentLifeCycleRuntimeMBean[] getSystemComponentLifecycleRuntimes();

   ServerLifeCycleRuntimeMBean lookupServerLifecycleRuntime(String var1);

   String[] getClusters();

   MigratableServiceCoordinatorRuntimeMBean getMigratableServiceCoordinatorRuntime();

   AppRuntimeStateRuntimeMBean getAppRuntimeStateRuntime();

   void setAppRuntimeStateRuntime(AppRuntimeStateRuntimeMBean var1);

   DomainRuntimeServiceMBean getDomainRuntimeService();

   void setDomainRuntimeService(DomainRuntimeServiceMBean var1);

   MigrationDataRuntimeMBean[] getMigrationDataRuntimes();

   ServiceMigrationDataRuntimeMBean[] getServiceMigrationDataRuntimes();

   void addAccessCallback(AccessCallback var1);

   void invokeAccessCallbacks(DomainMBean var1);

   SessionHelperManagerRuntimeMBean getSessionHelperManager();

   ResourceGroupLifeCycleRuntimeMBean lookupResourceGroupLifeCycleRuntime(String var1);

   ResourceGroupLifeCycleRuntimeMBean[] getResourceGroupLifeCycleRuntimes();

   NodeManagerRuntimeMBean[] getNodeManagerRuntimes();

   DomainPartitionRuntimeMBean lookupDomainPartitionRuntime(String var1);

   DomainPartitionRuntimeMBean[] getDomainPartitionRuntimes();
}
