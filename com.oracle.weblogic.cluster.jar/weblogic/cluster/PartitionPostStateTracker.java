package weblogic.cluster;

import java.security.AccessController;
import javax.annotation.PostConstruct;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.migration.MigrationService;
import weblogic.management.configuration.util.PartitionManagerPartitionAPI;
import weblogic.management.configuration.util.PartitionManagerResourceGroupAPI;
import weblogic.management.configuration.util.ResourceGroupManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.configuration.util.Setup;
import weblogic.management.configuration.util.Teardown;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@Setup
@Teardown
@Interceptor
@PartitionManagerPartitionAPI
@PartitionManagerResourceGroupAPI
@ServerServiceInterceptor(MigrationService.class)
@ContractsProvided({MethodInterceptor.class, PartitionPostStateTracker.class})
public class PartitionPostStateTracker extends ResourceGroupManagerInterceptorAdapter {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean clustered;

   @PostConstruct
   private void initialize() {
      this.clustered = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster() != null;
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed for shutdownPartition", partitionName);
         methodInvocation.proceed();
         this.logMessage("Post-Proceed for shutdownPartition", partitionName);
         PartitionAwareSenderManager.theOne().handlePartitionStop(partitionName);
      }
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed for forceShutdownPartition", partitionName);
         methodInvocation.proceed();
         this.logMessage("Post-Proceed for forceShutdownPartition", partitionName);
         PartitionAwareSenderManager.theOne().handlePartitionStop(partitionName);
      }
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed for suspendPartition", partitionName);
         methodInvocation.proceed();
         this.logMessage("Post-Proceed for suspendPartition", partitionName);
         PartitionAwareSenderManager.theOne().handlePartitionStop(partitionName);
      }
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed for forceSuspendPartition", partitionName);
         methodInvocation.proceed();
         this.logMessage("Post-Proceed for forceSuspendPartition", partitionName);
         PartitionAwareSenderManager.theOne().handlePartitionStop(partitionName);
      }
   }

   public void suspendResourceGroup(MethodInvocation methodInvocation, String partitionName, String resourceGroupName, int timeout, boolean ignoreSessions) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed suspendResourceGroup", partitionName, resourceGroupName);
         methodInvocation.proceed();
         PartitionAwareSenderManager.theOne().deactivateSource(new MulticastSessionId(toPartitionId(partitionName), resourceGroupName));
         this.logMessage("Post-Proceed suspendResourceGroup", partitionName, resourceGroupName);
      }
   }

   public void forceSuspendResourceGroup(MethodInvocation methodInvocation, String partitionName, String resourceGroupName) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed forceSuspendResourceGroup", partitionName, resourceGroupName);
         methodInvocation.proceed();
         PartitionAwareSenderManager.theOne().deactivateSource(new MulticastSessionId(toPartitionId(partitionName), resourceGroupName));
         this.logMessage("Post-Proceed forceSuspendResourceGroup", partitionName, resourceGroupName);
      }
   }

   public void shutdownResourceGroup(MethodInvocation methodInvocation, String partitionName, String resourceGroupName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed shutdownResourceGroup", partitionName, resourceGroupName);
         methodInvocation.proceed();
         PartitionAwareSenderManager.theOne().deactivateSource(new MulticastSessionId(toPartitionId(partitionName), resourceGroupName));
         this.logMessage("Post-Proceed shutdownResourceGroup", partitionName, resourceGroupName);
      }
   }

   public void forceShutdownResourceGroup(MethodInvocation methodInvocation, String partitionName, String resourceGroupName) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed forceShutdownResourceGroup", partitionName, resourceGroupName);
         methodInvocation.proceed();
         PartitionAwareSenderManager.theOne().deactivateSource(new MulticastSessionId(toPartitionId(partitionName), resourceGroupName));
         this.logMessage("Post-Proceed forceShutdownResourceGroup", partitionName, resourceGroupName);
      }
   }

   static String toPartitionId(String partitionName) {
      return partitionName == null ? "0" : ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(partitionName).getPartitionID();
   }

   void logMessage(String str, String partitionName) {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("[PartitionStateTracker]: " + str + "; partitionName = " + partitionName);
      }

   }

   void logMessage(String str, String partitionName, String resourceGroupName) {
      if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
         ClusterAnnouncementsDebugLogger.debug("[PartitionStateTracker]: " + str + "; partitionName = " + partitionName + "; resourceGroupName = " + resourceGroupName);
      }

   }
}
