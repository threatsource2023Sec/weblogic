package weblogic.server;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Map;
import org.jvnet.hk2.annotations.Service;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.PartitionRuntimeStateManager;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.management.partition.admin.PartitionLifecycleDelegator;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.utils.EmbeddedLDAPService;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.LocatorUtilities;

@Service
public class RemoteLifeCycleOperationsImpl implements RemoteLifeCycleOperations {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugCategory debugSLC = Debug.getCategory("weblogic.slc");
   private static final boolean PDEBUG = PartitionLifecycleDebugger.isDebugEnabled();

   /** @deprecated */
   @Deprecated
   public static RemoteLifeCycleOperationsImpl getInstance() {
      return RemoteLifeCycleOperationsImpl.Singleton.SINGLETON;
   }

   private RemoteLifeCycleOperationsImpl() {
   }

   public void shutdown() throws ServerLifecycleException {
      debug("executing shutdown(). Requested by '" + this.getUserName() + "'");
      getRuntimeAccess().getServerRuntime().shutdown();
   }

   public void shutdown(int timeout, boolean ignoreSessions) throws ServerLifecycleException {
      debug("executing shutdown(timeout). Requested by '" + this.getUserName() + "'");
      getRuntimeAccess().getServerRuntime().shutdown(timeout, ignoreSessions);
   }

   public void shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws ServerLifecycleException {
      debug("executing shutdown(timeout). Requested by '" + this.getUserName() + "'");
      getRuntimeAccess().getServerRuntime().shutdown(timeout, ignoreSessions, waitForAllSessions);
   }

   public void forceShutdown() throws ServerLifecycleException {
      debug("executing forceShutdown(). Requested by '" + this.getUserName() + "'");
      getRuntimeAccess().getServerRuntime().forceShutdown();
   }

   public void suspend() throws ServerLifecycleException {
      debug("executing suspend(). Requested by '" + this.getUserName() + "'");
      getRuntimeAccess().getServerRuntime().suspend();
   }

   public void suspend(int timeout, boolean ignoreSessions) throws ServerLifecycleException {
      debug("executing suspend(timeout). Requested by '" + this.getUserName() + "'");
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().suspend(timeout, ignoreSessions);
   }

   public void forceSuspend() throws ServerLifecycleException {
      debug("executing forceSuspend(). Requested by '" + this.getUserName() + "'");
      getRuntimeAccess().getServerRuntime().forceSuspend();
   }

   public void resume() throws ServerLifecycleException {
      debug("executing resume(). Requested by '" + this.getUserName() + "'");
      getRuntimeAccess().getServerRuntime().resume();
   }

   public String getState() {
      debug("executing getState(). Requested by '" + this.getUserName() + "'");
      return getRuntimeAccess().getServerRuntime().getState();
   }

   public void setState(String serverName, String state) {
      if (ServerLifeCycleService.isStarted()) {
         ServerLifeCycleRuntimeMBean slcRuntime = getDomainAccess().getDomainRuntime().lookupServerLifeCycleRuntime(serverName);
         debug("got slcRuntime '" + slcRuntime + "' for serverName '" + serverName + "'. Updating state to " + state);
         if (slcRuntime == null) {
            debug("slcRuntime is null, cannot update state to " + state);
         } else {
            slcRuntime.setState(state);
         }
      }
   }

   public String getWeblogicHome() {
      debug("executing getWeblogicHome(). Requested by '" + this.getUserName() + "'");
      return getRuntimeAccess().getServerRuntime().getWeblogicHome();
   }

   public String getMiddlewareHome() {
      debug("executing getMiddlewareHome(). Requested by '" + this.getUserName() + "'");
      return getRuntimeAccess().getServerRuntime().getMiddlewareHome();
   }

   private static void debug(String str) {
      if (debugSLC.isEnabled()) {
         T3SrvrLogger.logDebugSLC("<RemoteSLCOperationsImpl>" + str);
      }

   }

   private AuthenticatedSubject getUserName() {
      return debugSLC.isEnabled() ? SecurityServiceManager.getCurrentSubject(kernelId) : null;
   }

   public void shutDownPartition(String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions, String... servers) throws PartitionLifeCycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> shutdown partition " + partitionName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().shutDownPartition(partitionName, timeout, ignoreSessions, waitForAllSessions, servers);
   }

   public void forceShutDownPartition(String partitionName, String... servers) throws PartitionLifeCycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> forceShutDownPartition " + partitionName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().forceShutDownPartition(partitionName, servers);
   }

   public void suspendPartition(String partitionName, int timeout, boolean ignoreSessions, String... servers) throws PartitionLifeCycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> suspendPartition " + partitionName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().suspendPartition(partitionName, timeout, ignoreSessions, servers);
   }

   public void forceSuspendPartition(String partitionName, String... servers) throws PartitionLifeCycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> forceSuspendPartition " + partitionName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().forceSuspendPartition(partitionName, servers);
   }

   public void startPartition(String partitionName, String partitionId, boolean isAdminMode, String... servers) throws PartitionLifeCycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> startPartition " + partitionName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().startPartition(partitionName, partitionId, isAdminMode, servers);
   }

   public void resumePartition(String partitionName, String... servers) throws PartitionLifeCycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> resumePartition " + partitionName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().resumePartition(partitionName, servers);
   }

   public void haltPartition(String partitionName, String partitionId, String... servers) throws PartitionLifeCycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> haltPartition " + partitionName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().haltPartition(partitionName, servers);
   }

   public void bootPartition(String partitionName, String partitionId, String... servers) throws PartitionLifeCycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> bootPartition " + partitionName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().bootPartition(partitionName, servers);
   }

   public void forceRestartPartition(String partitionName, String partitionId, String... servers) throws PartitionLifeCycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> forceRestartPartition " + partitionName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().forceRestartPartition(partitionName, servers);
   }

   public void shutDownResourceGroup(String partitionName, String resourceGroupName, int timeout, boolean ignoreSessions, boolean waitForAllSessions, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> shutDownResourceGroup " + partitionName + "Resource Group " + resourceGroupName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().shutDownResourceGroup(partitionName, resourceGroupName, timeout, ignoreSessions, waitForAllSessions, servers);
   }

   public void forceShutDownResourceGroup(String partitionName, String resourceGroupName, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> forceShutDownResourceGroup " + partitionName + "Resource Group " + resourceGroupName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().forceShutDownResourceGroup(partitionName, resourceGroupName, servers);
   }

   public void suspendResourceGroup(String partitionName, String resourceGroupName, int timeout, boolean ignoreSessions, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> suspendResourceGroup " + partitionName + "Resource Group " + resourceGroupName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().suspendResourceGroup(partitionName, resourceGroupName, timeout, ignoreSessions, servers);
   }

   public void forceSuspendResourceGroup(String partitionName, String resourceGroupName, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> forceSuspendResourceGroup " + partitionName + "Resource Group " + resourceGroupName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().forceSuspendResourceGroup(partitionName, resourceGroupName, servers);
   }

   public void startResourceGroup(String partitionName, String resourceGroupName, boolean isAdminMode, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> startResourceGroup " + partitionName + "Resource Group " + resourceGroupName + "isAdminMode " + isAdminMode + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().startResourceGroup(partitionName, resourceGroupName, isAdminMode, servers);
   }

   public void resumeResourceGroup(String partitionName, String resourceGroupName, String... servers) throws ResourceGroupLifecycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> resumeResourceGroup " + partitionName + "Resource Group " + resourceGroupName + " on the servers " + Arrays.toString(servers));
      }

      getPartitionLifecycleDelegator().resumeResourceGroup(partitionName, resourceGroupName, servers);
   }

   public void setDesiredPartitionState(String partitionName, String desiredState, String... serversAffected) throws PartitionLifeCycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> set desired partition state " + partitionName + " desiredState  " + desiredState + " Servers affected " + Arrays.toString(serversAffected));
      }

      getPartitionLifecycleDelegator().storePartitionDesiredState(partitionName, State.valueOf(desiredState), serversAffected);
   }

   public void setDesiredResourceGroupState(String partitionName, String resourceGroupName, String desiredState, String... serversAffected) throws ResourceGroupLifecycleException, RemoteException {
      if (PDEBUG) {
         pDebug("<RemoteLifecycleOperations> set desired resourceGroupName state " + resourceGroupName + " desiredState  " + desiredState + " Servers affected " + Arrays.toString(serversAffected));
      }

      getPartitionLifecycleDelegator().storeResourceGroupDesiredState(partitionName, resourceGroupName, State.valueOf(desiredState), serversAffected);
   }

   public Map getRuntimeStates() {
      PartitionRuntimeStateManager mgr = (PartitionRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(PartitionRuntimeStateManager.class, new Annotation[0]);
      return mgr.getStates();
   }

   public void setInvalid(String serverName) throws RemoteException {
      ((EmbeddedLDAPService)LocatorUtilities.getService(EmbeddedLDAPService.class)).setReplicaInvalid();
   }

   private static PartitionLifecycleDelegator getPartitionLifecycleDelegator() {
      return (PartitionLifecycleDelegator)GlobalServiceLocator.getServiceLocator().getService(PartitionLifecycleDelegator.class, new Annotation[0]);
   }

   private static RuntimeAccess getRuntimeAccess() {
      return (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
   }

   private static DomainAccess getDomainAccess() {
      return (DomainAccess)GlobalServiceLocator.getServiceLocator().getService(DomainAccess.class, new Annotation[0]);
   }

   private static void pDebug(String debugMessage) {
      PartitionLifecycleDebugger.debug(debugMessage);
   }

   private static class Singleton {
      private static final RemoteLifeCycleOperationsImpl SINGLETON = (RemoteLifeCycleOperationsImpl)LocatorUtilities.getService(RemoteLifeCycleOperationsImpl.class);
   }
}
