package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.ServerReachabilityMajorityService;
import weblogic.cluster.messaging.internal.ServerReachabilityMajorityServiceImpl;

final class EnvironmentFactory {
   static void initialize() {
      getDiscoveryService();
      getClusterMember();
   }

   static DiscoveryService getDiscoveryService() {
      return MulticastBasedDiscoveryService.getInstance();
   }

   public static ClusterFormationService getClusterFormationService() {
      return ClusterFormationServiceImpl.getInstance();
   }

   static ClusterLeader getClusterLeader() {
      return ClusterLeader.getInstance();
   }

   static ClusterMember getClusterMember() {
      return ClusterMember.getInstance();
   }

   static ClusterMemberDisconnectMonitor getClusterMemberDisconnectMonitor() {
      return RMIBasedDisconnectMonitorImpl.getInstance();
   }

   static ServerReachabilityMajorityService getServerReachabilityMajorityService() {
      return ServerReachabilityMajorityServiceImpl.getInstance();
   }

   public static ServerFailureDetector getFailureDetector(String serverName) {
      return new ServerFailureDetectorImpl();
   }
}
