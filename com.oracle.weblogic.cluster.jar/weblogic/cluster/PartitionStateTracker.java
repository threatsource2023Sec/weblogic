package weblogic.cluster;

import java.security.AccessController;
import javax.annotation.PostConstruct;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
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
@ServerServiceInterceptor(InboundService.class)
@ContractsProvided({MethodInterceptor.class, PartitionStateTracker.class})
public class PartitionStateTracker extends ResourceGroupManagerInterceptorAdapter {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean clustered;

   @PostConstruct
   private void initialize() {
      this.clustered = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster() != null;
      if (this.clustered) {
         PartitionAwareSenderManager.theOne().findOrCreateAnnouncementManager(new MulticastSessionId("0", "NO_RESOURCE_GROUP"));
      }

   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed for startPartitionInAdmin", partitionName);
         methodInvocation.proceed();
         PartitionAwareSenderManager.theOne().handlePartitionStart(partitionName);
         this.logMessage("Post-Proceed for startPartitionInAdmin", partitionName);
      }
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed for startPartition", partitionName);
         methodInvocation.proceed();
         PartitionAwareSenderManager.theOne().handlePartitionStart(partitionName);
         this.logMessage("Post-Proceed for startPartition", partitionName);
      }
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed for resumePartition", partitionName);
         methodInvocation.proceed();
         PartitionAwareSenderManager.theOne().handlePartitionStart(partitionName);
         this.logMessage("Post-Proceed for resumePartition", partitionName);
      }
   }

   public void startResourceGroup(MethodInvocation methodInvocation, String partitionName, String resourceGroupName) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed startResourceGroup", partitionName, resourceGroupName);
         methodInvocation.proceed();
         PartitionAwareSenderManager.theOne().activateSource(new MulticastSessionId(toPartitionId(partitionName), resourceGroupName));
         this.logMessage("Post-Proceed startResourceGroup", partitionName, resourceGroupName);
      }
   }

   public void startResourceGroupInAdmin(MethodInvocation methodInvocation, String partitionName, String resourceGroupName) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed startResourceGroupInAdmin", partitionName, resourceGroupName);
         methodInvocation.proceed();
         PartitionAwareSenderManager.theOne().activateSource(new MulticastSessionId(toPartitionId(partitionName), resourceGroupName));
         this.logMessage("Post-Proceed startResourceGroupInAdmin", partitionName, resourceGroupName);
      }
   }

   public void resumeResourceGroup(MethodInvocation methodInvocation, String partitionName, String resourceGroupName) throws Throwable {
      if (!this.clustered) {
         methodInvocation.proceed();
      } else {
         this.logMessage("Pre-Proceed resumeResourceGroup", partitionName, resourceGroupName);
         methodInvocation.proceed();
         PartitionAwareSenderManager.theOne().activateSource(new MulticastSessionId(toPartitionId(partitionName), resourceGroupName));
         this.logMessage("Post-Proceed resumeResourceGroup", partitionName, resourceGroupName);
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
