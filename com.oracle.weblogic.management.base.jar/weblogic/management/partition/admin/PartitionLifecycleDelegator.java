package weblogic.management.partition.admin;

import java.rmi.RemoteException;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.PartitionRuntimeStateManager;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
public class PartitionLifecycleDelegator {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ServerRuntimeMBean serverBean;
   @Inject
   @Named("PartitionRuntimeStateManager")
   private PartitionRuntimeStateManager partitionRuntimeStateManager;
   private static final boolean DEBUG = PartitionLifecycleDebugger.isDebugEnabled();

   public void shutDownPartition(String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions, String... servers) throws PartitionLifeCycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storePartitionDesiredState(partitionName, PartitionRuntimeMBean.State.SHUTDOWN, servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("shutDownPartition " + partitionName + " persisted desired state " + PartitionRuntimeMBean.State.SHUTDOWN);
         }

         partitionRuntimeMBean.shutdown(timeout, ignoreSessions, waitForAllSessions);
      }

   }

   public void forceShutDownPartition(String partitionName, String... servers) throws PartitionLifeCycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storePartitionDesiredState(partitionName, PartitionRuntimeMBean.State.SHUTDOWN, servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("forceShutDownPartition " + partitionName + " persisted desired state " + PartitionRuntimeMBean.State.SHUTDOWN);
         }

         partitionRuntimeMBean.forceShutdown();
      }

   }

   public void suspendPartition(String partitionName, int timeout, boolean ignoreSessions, String... servers) throws PartitionLifeCycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storePartitionDesiredState(partitionName, PartitionRuntimeMBean.State.ADMIN, servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("suspendPartition " + partitionName + " persisted desired state " + PartitionRuntimeMBean.State.ADMIN);
         }

         partitionRuntimeMBean.suspend(timeout, ignoreSessions);
      }

   }

   public void forceSuspendPartition(String partitionName, String... servers) throws PartitionLifeCycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storePartitionDesiredState(partitionName, PartitionRuntimeMBean.State.ADMIN, servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("forceSuspendPartition " + partitionName + " persisted desired state " + PartitionRuntimeMBean.State.ADMIN);
         }

         partitionRuntimeMBean.forceSuspend();
      }

   }

   public void startPartition(String partitionName, String partitionId, boolean isAdminMode, String... servers) throws PartitionLifeCycleException, RemoteException {
      this.storePartitionDesiredState(partitionName, PartitionRuntimeMBean.State.runningState(), servers);
      if (DEBUG) {
         debug("startPartition " + partitionName + " persisted desired state " + PartitionRuntimeMBean.State.runningState() + " isAdminMode " + isAdminMode);
      }

      if (!isAdminMode) {
         ManagementService.getRuntimeAccess(kernelId).getServerRuntime().startPartition(partitionName);
      } else {
         ManagementService.getRuntimeAccess(kernelId).getServerRuntime().startPartitionInAdmin(partitionName);
      }

   }

   public void resumePartition(String partitionName, String... servers) throws PartitionLifeCycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storePartitionDesiredState(partitionName, PartitionRuntimeMBean.State.runningState(), servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("resumePartition " + partitionName + " persisted desired state " + PartitionRuntimeMBean.State.runningState());
         }

         partitionRuntimeMBean.resume();
      }

   }

   public void bootPartition(String partitionName, String... servers) throws PartitionLifeCycleException, RemoteException {
      this.storePartitionDesiredState(partitionName, PartitionRuntimeMBean.State.BOOTED, servers);
      if (DEBUG) {
         debug("bootPartition " + partitionName);
      }

      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().bootPartition(partitionName);
   }

   public void haltPartition(String partitionName, String... servers) throws PartitionLifeCycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storePartitionDesiredState(partitionName, PartitionRuntimeMBean.State.HALTED, servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("haltPartition " + partitionName + " persisted desired state " + PartitionRuntimeMBean.State.runningState());
         }

         partitionRuntimeMBean.halt();
      }

   }

   public void forceRestartPartition(String partitionName, String... servers) throws PartitionLifeCycleException, RemoteException {
      if (DEBUG) {
         debug("forceRestartPartition " + partitionName);
      }

      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().forceRestartPartition(partitionName);
   }

   public void shutDownResourceGroup(String partitionName, String resourceGroupName, int timeout, boolean ignoreSessions, boolean waitForAllSessions, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storeResourceGroupDesiredState(partitionName, resourceGroupName, PartitionRuntimeMBean.State.SHUTDOWN, servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("shutDownResourceGroup " + partitionName + " ResourceGroup : " + resourceGroupName + " persisted desired state " + PartitionRuntimeMBean.State.SHUTDOWN);
         }

         partitionRuntimeMBean.shutdownResourceGroup(resourceGroupName, timeout, ignoreSessions, waitForAllSessions);
      } else if (this.isDomainRG(resourceGroupName)) {
         this.getServerBean().shutdownResourceGroup(resourceGroupName, timeout, ignoreSessions, waitForAllSessions);
      }

   }

   public void forceShutDownResourceGroup(String partitionName, String resourceGroupName, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storeResourceGroupDesiredState(partitionName, resourceGroupName, PartitionRuntimeMBean.State.SHUTDOWN, servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("forceShutDownResourceGroup " + partitionName + " ResourceGroup : " + resourceGroupName + " persisted desired state " + PartitionRuntimeMBean.State.SHUTDOWN);
         }

         partitionRuntimeMBean.forceShutdownResourceGroup(resourceGroupName);
      } else if (this.isDomainRG(resourceGroupName)) {
         this.getServerBean().forceShutdownResourceGroup(resourceGroupName);
      }

   }

   public void suspendResourceGroup(String partitionName, String resourceGroupName, int timeout, boolean ignoreSessions, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storeResourceGroupDesiredState(partitionName, resourceGroupName, PartitionRuntimeMBean.State.ADMIN, servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("suspendResourceGroup " + partitionName + " ResourceGroup : " + resourceGroupName + " persisted desired state " + PartitionRuntimeMBean.State.ADMIN);
         }

         partitionRuntimeMBean.suspendResourceGroup(resourceGroupName, timeout, ignoreSessions);
      } else if (this.isDomainRG(resourceGroupName)) {
         this.getServerBean().suspendResourceGroup(resourceGroupName, timeout, ignoreSessions);
      }

   }

   public void forceSuspendResourceGroup(String partitionName, String resourceGroupName, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storeResourceGroupDesiredState(partitionName, resourceGroupName, PartitionRuntimeMBean.State.ADMIN, servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("forceSuspendResourceGroup " + partitionName + " ResourceGroup : " + resourceGroupName + " persisted desired state " + PartitionRuntimeMBean.State.ADMIN);
         }

         partitionRuntimeMBean.forceSuspendResourceGroup(resourceGroupName);
      } else if (this.isDomainRG(resourceGroupName)) {
         this.getServerBean().forceSuspendResourceGroup(resourceGroupName);
      }

   }

   public void startResourceGroup(String partitionName, String resourceGroupName, boolean isAdminMode, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storeResourceGroupDesiredState(partitionName, resourceGroupName, PartitionRuntimeMBean.State.runningState(), servers);
      if (DEBUG) {
         debug("startResourceGroup " + partitionName + "ResourceGroup : " + resourceGroupName + " persisted desired state " + PartitionRuntimeMBean.State.runningState() + " isAdminMode " + isAdminMode);
      }

      if (isAdminMode) {
         if (partitionRuntimeMBean != null) {
            partitionRuntimeMBean.startResourceGroupInAdmin(resourceGroupName);
         } else if (this.isDomainRG(resourceGroupName)) {
            this.getServerBean().startResourceGroupInAdmin(resourceGroupName);
         }
      } else if (partitionRuntimeMBean != null) {
         partitionRuntimeMBean.startResourceGroup(resourceGroupName);
      } else if (this.isDomainRG(resourceGroupName)) {
         this.getServerBean().startResourceGroup(resourceGroupName);
      }

   }

   public void resumeResourceGroup(String partitionName, String resourceGroupName, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.getPartitionRuntime(partitionName);
      this.storeResourceGroupDesiredState(partitionName, resourceGroupName, PartitionRuntimeMBean.State.runningState(), servers);
      if (partitionRuntimeMBean != null) {
         if (DEBUG) {
            debug("forceSuspendResourceGroup " + partitionName + " ResourceGroup : " + resourceGroupName + " persisted desired state " + PartitionRuntimeMBean.State.runningState());
         }

         partitionRuntimeMBean.resumeResourceGroup(resourceGroupName);
      } else if (this.isDomainRG(resourceGroupName)) {
         this.getServerBean().resumeResourceGroup(resourceGroupName);
      }

   }

   private PartitionRuntimeMBean getPartitionRuntime(String partitionName) {
      return ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupPartitionRuntime(partitionName);
   }

   public synchronized void storePartitionDesiredState(String partitionName, PartitionRuntimeMBean.State desiredState, String... serversAffected) throws PartitionLifeCycleException {
      try {
         this.partitionRuntimeStateManager.setPartitionState(partitionName, desiredState.name(), serversAffected);
      } catch (ManagementException var5) {
         throw new PartitionLifeCycleException(var5);
      }
   }

   public synchronized void storeResourceGroupDesiredState(String partitionName, String resourceGroupName, PartitionRuntimeMBean.State desiredState, String... serversAffected) throws ResourceGroupLifecycleException {
      try {
         this.partitionRuntimeStateManager.setResourceGroupState(partitionName, resourceGroupName, desiredState.name(), serversAffected);
      } catch (ManagementException var6) {
         throw new ResourceGroupLifecycleException(var6);
      }
   }

   private final ServerRuntimeMBean getServerBean() {
      if (this.serverBean == null) {
         this.serverBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      }

      return this.serverBean;
   }

   private boolean isDomainRG(String resourceGroupName) {
      ResourceGroupMBean domainRG = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupResourceGroup(resourceGroupName);
      return domainRG != null;
   }

   private static void debug(String debugMessage) {
      PartitionLifecycleDebugger.debug("<PartitionLifecycleDelegator>  " + debugMessage);
   }
}
