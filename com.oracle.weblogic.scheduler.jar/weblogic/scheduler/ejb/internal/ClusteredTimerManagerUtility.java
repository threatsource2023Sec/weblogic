package weblogic.scheduler.ejb.internal;

import java.security.AccessController;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.scheduler.ejb.ConfigurationException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.PartitionUtility;

public class ClusteredTimerManagerUtility {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static void verifyJobSchedulerConfig() throws ConfigurationException {
      ComponentInvocationContext currentCIC = PartitionUtility.getCurrentComponentInvocationContext();
      if (currentCIC != null && !currentCIC.isGlobalRuntime()) {
         verifyConfigForPartition(currentCIC.getPartitionName());
      } else {
         verifyConfigForGlobal();
      }

   }

   private static void verifyConfigForGlobal() throws ConfigurationException {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      ServerMBean serverMBean = runtimeAccess.getServer();
      if (serverMBean != null && serverMBean.getCluster() != null) {
         if (serverMBean.getCluster().getDataSourceForJobScheduler() == null) {
            throw new ConfigurationException(ClusterExtensionLogger.getJobSchedulerNotConfiguredForClusteredTimers());
         }
      } else {
         throw new ConfigurationException(ClusterExtensionLogger.getClusteredTimersRequireCluster());
      }
   }

   private static void verifyConfigForPartition(String partitionName) throws ConfigurationException {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      PartitionMBean partition = domain.lookupPartition(partitionName);
      if (partition == null || partition.getDataSourceForJobScheduler() == null) {
         throw new ConfigurationException(ClusterExtensionLogger.getJobSchedulerNotConfiguredInPartition(partitionName));
      }
   }

   public static String getClusterOrPartitionName(ComponentInvocationContext cic) {
      if (cic != null && !cic.isGlobalRuntime()) {
         return cic.getPartitionId();
      } else {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
         return isCurrentApplicationFromGlobalDomainResourceGroup(cic, runtimeAccess) ? "DOMAIN" : runtimeAccess.getServer().getCluster().getName();
      }
   }

   private static boolean isCurrentApplicationFromGlobalDomainResourceGroup(ComponentInvocationContext cic, RuntimeAccess runtimeAccess) {
      if (cic != null && cic.isGlobalRuntime()) {
         String appName = cic.getApplicationName();
         return isApplicationFromGlobalDomainResourceGroup(appName, runtimeAccess);
      } else {
         return false;
      }
   }

   private static boolean isApplicationFromGlobalDomainResourceGroup(String appName, RuntimeAccess runtimeAccess) {
      return false;
   }

   public static boolean isCurrentApplicationFromGlobalDomainResourceGroup() {
      ComponentInvocationContext cic = PartitionUtility.getCurrentComponentInvocationContext();
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      return isCurrentApplicationFromGlobalDomainResourceGroup(cic, runtimeAccess);
   }

   static boolean isApplicationFromGlobalDomainResourceGroup(String appName) {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      return isApplicationFromGlobalDomainResourceGroup(appName, runtimeAccess);
   }
}
