package weblogic.management.partition.admin;

import javax.inject.Singleton;
import org.glassfish.hk2.extras.interception.Intercepted;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.util.PartitionManagerPartitionAPI;
import weblogic.management.configuration.util.PartitionManagerResourceGroupAPI;
import weblogic.management.configuration.util.Setup;
import weblogic.management.configuration.util.Teardown;
import weblogic.rmi.utils.PartitionManagerPartitionStateAPI;

@Service(
   name = "PartitionManagerService"
)
@Singleton
@Intercepted
public class PartitionManagerService {
   /** @deprecated */
   @Deprecated
   @Setup
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void beginStartPartition(String partitionName) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> beginStartPartition " + partitionName);
   }

   /** @deprecated */
   @Deprecated
   @Teardown
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void beginShutdownPartition(String partitionName) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> beginStartPartition " + partitionName);
   }

   @Setup
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void bootPartition(String partitionName) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> bootPartition " + partitionName);
   }

   @Setup
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void startPartition(String partitionName) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> startPartition " + partitionName);
   }

   @Setup
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void startPartitionInAdmin(String partitionName) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> startPartitionInAdmin " + partitionName);
   }

   @Teardown
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void suspendPartition(String partitionName, int timeout, boolean ignoreSessions) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> suspendPartition " + partitionName);
   }

   @Teardown
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void forceSuspendPartition(String partitionName) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> forceSuspendPartition " + partitionName);
   }

   @Setup
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void resumePartition(String partitionName) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> resumePartition " + partitionName);
   }

   @Teardown
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void shutdownPartition(String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> shutdownPartition " + partitionName);
   }

   @Teardown
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void forceShutdownPartition(String partitionName) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> forceShutdownPartition " + partitionName);
   }

   @Teardown
   @PartitionManagerPartitionAPI
   @PartitionManagerPartitionStateAPI
   public void haltPartition(String partitionName) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> haltPartition " + partitionName);
   }

   @Setup
   @PartitionManagerResourceGroupAPI
   public void startResourceGroup(String partitionName, String ResourceGroup) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> startResourceGroup " + ResourceGroup + " belonging to partition " + partitionName);
   }

   @Setup
   @PartitionManagerResourceGroupAPI
   public void startResourceGroupInAdmin(String partitionName, String ResourceGroup) {
      PartitionLifecycleDebugger.debug("Intercepting startResourceGroupInAdmin " + ResourceGroup + " belonging to partition " + partitionName);
   }

   @Teardown
   @PartitionManagerResourceGroupAPI
   public void suspendResourceGroup(String partitionName, String ResourceGroup, int timeout, boolean ignoreSessions) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> suspendResourceGroup " + ResourceGroup + " belonging to partition " + partitionName);
   }

   @Teardown
   @PartitionManagerResourceGroupAPI
   public void forceSuspendResourceGroup(String partitionName, String ResourceGroup) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> forceSuspendResourceGroup " + ResourceGroup + " belonging to partition " + partitionName);
   }

   @Setup
   @PartitionManagerResourceGroupAPI
   public void resumeResourceGroup(String partitionName, String ResourceGroup) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> resumeResourceGroup " + ResourceGroup + " belonging to partition " + partitionName);
   }

   @Teardown
   @PartitionManagerResourceGroupAPI
   public void shutdownResourceGroup(String partitionName, String ResourceGroup, int timeout, boolean ignoreSessions, boolean waitForAllSessions) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> shutdownResourceGroup " + ResourceGroup + " belonging to partition " + partitionName);
   }

   @Teardown
   @PartitionManagerResourceGroupAPI
   public void forceShutdownResourceGroup(String partitionName, String ResourceGroup) {
      PartitionLifecycleDebugger.debug("<PartitionManagerService> forceShutdownResourceGroup " + ResourceGroup + " belonging to partition " + partitionName);
   }
}
