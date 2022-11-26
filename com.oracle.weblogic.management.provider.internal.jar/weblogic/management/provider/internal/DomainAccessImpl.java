package weblogic.management.provider.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.DomainPartitionService;
import weblogic.management.ManagementException;
import weblogic.management.NodeManagerRuntimeService;
import weblogic.management.PartitionLifeCycleService;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.AccessCallback;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.DomainAccessSettable;
import weblogic.management.provider.DomainMigrationHistory;
import weblogic.management.provider.RuntimeAccess;
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
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.ServerLifeCycleService;
import weblogic.utils.annotation.Secure;

@Service
@Secure
@Named
public class DomainAccessImpl extends RegistrationManagerImpl implements DomainAccess, DomainAccessSettable {
   private final long activationTime = System.currentTimeMillis();
   private DeployerRuntimeMBean deployerRuntime;
   private DeploymentManagerMBean deploymentManager;
   @Inject
   private Provider lifecycleServiceProvider;
   private boolean serverLifeCycleServiceStarted = false;
   private MigratableServiceCoordinatorRuntimeMBean serverMigrationCoordinator;
   private DomainRuntimeMBean domainRuntime;
   private DomainRuntimeServiceMBean domainRuntimeService;
   private AppRuntimeStateRuntimeMBean appRuntimeStateRuntime;
   private SessionHelperManagerRuntimeMBean sessionHelperManager = null;
   private PartitionLifeCycleService partitionLifeCycleService;
   private DomainPartitionService domainPartitionService;
   private NodeManagerRuntimeService nmService;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private DomainMigrationHistory domainMigrationHistory;
   private List accessCallbackList = new ArrayList();
   private final RuntimeAccess runtimeAccess;

   @Inject
   public DomainAccessImpl(RuntimeAccess runtimeAccess) {
      this.runtimeAccess = runtimeAccess;
   }

   void initializeDomainRuntimeMBean() {
      try {
         this.domainRuntime = new DomainRuntimeMBeanImpl();
      } catch (ManagementException var2) {
         throw new Error(var2);
      }
   }

   public AppRuntimeStateRuntimeMBean getAppRuntimeStateRuntime() {
      return this.appRuntimeStateRuntime;
   }

   public DeployerRuntimeMBean getDeployerRuntime() {
      return this.deployerRuntime;
   }

   public DeploymentManagerMBean getDeploymentManager() {
      return this.deploymentManager;
   }

   public ServerLifeCycleRuntimeMBean[] getServerLifecycleRuntimes() {
      return ((ServerLifeCycleService)this.lifecycleServiceProvider.get()).getServerLifecycleRuntimes();
   }

   public ServerLifeCycleRuntimeMBean lookupServerLifecycleRuntime(String name) {
      return !this.serverLifeCycleServiceStarted ? null : ((ServerLifeCycleService)this.lifecycleServiceProvider.get()).lookupServerLifecycleRuntime(name);
   }

   /** @deprecated */
   @Deprecated
   public CoherenceServerLifeCycleRuntimeMBean[] getCoherenceServerLifecycleRuntimes() {
      return ((ServerLifeCycleService)this.lifecycleServiceProvider.get()).getCoherenceServerLifecycleRuntimes();
   }

   /** @deprecated */
   @Deprecated
   public CoherenceServerLifeCycleRuntimeMBean lookupCoherenceServerLifecycleRuntime(String name) {
      return !this.serverLifeCycleServiceStarted ? null : ((ServerLifeCycleService)this.lifecycleServiceProvider.get()).lookupCoherenceServerLifecycleRuntime(name);
   }

   public SystemComponentLifeCycleRuntimeMBean[] getSystemComponentLifecycleRuntimes() {
      return ((ServerLifeCycleService)this.lifecycleServiceProvider.get()).getSystemComponentLifecycleRuntimes();
   }

   public SystemComponentLifeCycleRuntimeMBean lookupSystemComponentLifecycleRuntime(String name) {
      return !this.serverLifeCycleServiceStarted ? null : ((ServerLifeCycleService)this.lifecycleServiceProvider.get()).lookupSystemComponentLifecycleRuntime(name);
   }

   public DomainRuntimeMBean getDomainRuntime() {
      return this.domainRuntime;
   }

   public long getActivationTime() {
      return this.activationTime;
   }

   public String[] getClusters() {
      ClusterMBean[] clusters = this.runtimeAccess.getDomain().getClusters();
      String[] result = new String[clusters.length];

      for(int i = 0; i < clusters.length; ++i) {
         ClusterMBean cluster = clusters[i];
         result[i] = cluster.getName();
      }

      return result;
   }

   public MigratableServiceCoordinatorRuntimeMBean getMigratableServiceCoordinatorRuntime() {
      return this.serverMigrationCoordinator;
   }

   public DomainRuntimeServiceMBean getDomainRuntimeService() {
      return this.domainRuntimeService;
   }

   public void setDomainRuntimeService(DomainRuntimeServiceMBean service) {
      this.domainRuntimeService = service;
   }

   public MigrationDataRuntimeMBean[] getMigrationDataRuntimes() {
      return this.domainMigrationHistory.getMigrationDataRuntimes();
   }

   public ServiceMigrationDataRuntimeMBean[] getServiceMigrationDataRuntimes() {
      return this.domainMigrationHistory != null ? this.domainMigrationHistory.getServiceMigrationDataRuntimes() : null;
   }

   public void setDeployerRuntime(DeployerRuntimeMBean deployerRuntime) {
      if (this.deployerRuntime != null) {
         throw new Error("DeployerRuntime may only be initialized onced during during server startup");
      } else {
         this.deployerRuntime = deployerRuntime;
      }
   }

   public void setDeploymentManager(DeploymentManagerMBean deploymentManager) {
      if (this.deploymentManager != null) {
         throw new Error("DeploymentManager may only be initialized onced during during server startup");
      } else {
         this.deploymentManager = deploymentManager;
      }
   }

   public void startLifecycleService() {
      if (this.serverLifeCycleServiceStarted) {
         throw new Error("Lifecycle Service may only be initialized once during during server startup");
      } else {
         this.serverLifeCycleServiceStarted = true;
      }
   }

   public void setDomainMigrationHistory(DomainMigrationHistory history) {
      this.domainMigrationHistory = history;
   }

   public void setServerMigrationCoordinator(MigratableServiceCoordinatorRuntimeMBean serverMigrationCoordinator) {
      if (this.serverMigrationCoordinator != null) {
         throw new Error("The ServerMigrationCoordinator may only be initialized onced during during server startup");
      } else {
         this.serverMigrationCoordinator = serverMigrationCoordinator;
      }
   }

   public void setAppRuntimeStateRuntime(AppRuntimeStateRuntimeMBean appRuntime) {
      if (this.appRuntimeStateRuntime != null) {
         throw new Error("The AppRuntimeStateRuntime may only be initialized onced during during server startup");
      } else {
         this.appRuntimeStateRuntime = appRuntime;
      }
   }

   public void addAccessCallback(AccessCallback callback) {
      this.accessCallbackList.add(callback);
   }

   public void invokeAccessCallbacks(final DomainMBean domain) {
      SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
         public Object run() {
            DomainAccessImpl.this._invokeAccessCallbacks(domain);
            return null;
         }
      });
   }

   private void _invokeAccessCallbacks(DomainMBean domain) {
      Iterator var2 = this.accessCallbackList.iterator();

      while(var2.hasNext()) {
         AccessCallback cb = (AccessCallback)var2.next();

         try {
            cb.accessed(domain);
         } catch (Exception var5) {
            throw new RuntimeException("Failure invoking Access Callbacks", var5);
         }
      }

   }

   public SessionHelperManagerRuntimeMBean getSessionHelperManager() {
      return this.sessionHelperManager;
   }

   public void setSessionHelperManager(SessionHelperManagerRuntimeMBean mgr) {
      this.sessionHelperManager = mgr;
   }

   public void setPartitionLifeCycleService(PartitionLifeCycleService partitionLifeCycleService) {
      this.partitionLifeCycleService = partitionLifeCycleService;
   }

   public PartitionLifeCycleService getPartitionLifeCycleService() {
      return this.partitionLifeCycleService;
   }

   public ResourceGroupLifeCycleRuntimeMBean[] getResourceGroupLifeCycleRuntimes() {
      return this.partitionLifeCycleService.getResourceGroupLifeCycleRuntimes();
   }

   public ResourceGroupLifeCycleRuntimeMBean lookupResourceGroupLifeCycleRuntime(String name) {
      return this.partitionLifeCycleService == null ? null : this.partitionLifeCycleService.lookupResourceGroupLifeCycleRuntime(name);
   }

   public void setNodeManagerRuntimeService(NodeManagerRuntimeService nmService) {
      this.nmService = nmService;
   }

   public NodeManagerRuntimeMBean[] getNodeManagerRuntimes() {
      return this.nmService.getNodeManagerRuntimes();
   }

   public void setDomainPartitionService(DomainPartitionService domainPartitionService) {
      this.domainPartitionService = domainPartitionService;
   }

   public DomainPartitionService getDomainPartitionService() {
      return this.domainPartitionService;
   }

   public DomainPartitionRuntimeMBean[] getDomainPartitionRuntimes() {
      return this.domainPartitionService.getDomainPartitionRuntimes();
   }

   public DomainPartitionRuntimeMBean lookupDomainPartitionRuntime(String name) {
      return this.domainPartitionService == null ? null : this.domainPartitionService.lookupDomainPartitionRuntime(name);
   }
}
