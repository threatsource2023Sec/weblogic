package weblogic.cluster;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;
import weblogic.cluster.singleton.Leasing;
import weblogic.cluster.singleton.LeasingBasis;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface ClusterServices {
   MulticastSession createMulticastSession(RecoverListener var1, int var2);

   MulticastSession createMulticastSession(RecoverListener var1, int var2, boolean var3);

   ClusterMemberInfo getLocalMember();

   Collection getRemoteMembers();

   Collection getAllRemoteMembers();

   Collection getClusterMasterMembers();

   void addClusterMembersListener(ClusterMembersChangeListener var1);

   void removeClusterMembersListener(ClusterMembersChangeListener var1);

   void addClusterMembersPartitionListener(ClusterMembersPartitionChangeListener var1);

   void removeClusterMembersPartitionListener(ClusterMembersPartitionChangeListener var1);

   void addHeartbeatMessage(GroupMessage var1);

   void removeHeartbeatMessage(GroupMessage var1);

   int getHeartbeatTimeoutMillis();

   void resendLocalAttributes();

   LeasingBasis getDefaultLeasingBasis();

   Leasing getLeasingService(String var1);

   Leasing getSingletonLeasingService();

   Leasing getServerLeasingService();

   boolean isSessionStateQueryProtocolEnabled();

   int getSessionStateQueryRequestTimeout();

   boolean isSessionLazyDeserializationEnabled();

   void setSessionLazyDeserializationEnabled(boolean var1);

   List getFailoverServerGroups();

   void setFailoverServerGroups(List var1);

   boolean isZDTAppRolloutInProgress();

   boolean isSessionReplicationOnShutdownEnabled();

   void setSessionReplicationOnShutdownEnabled(boolean var1);

   void disableSessionStateQueryProtocolAfter(int var1);

   void setSessionStateQueryProtocolEnabled(boolean var1);

   void setCleanupOrphanedSessionsEnabled(boolean var1);

   boolean isCleanupOrphanedSessionEnabled();

   boolean isSessionStateQueryProtocolEnabled(String var1);

   boolean isSessionLazyDeserializationEnabled(String var1);

   void setSessionLazyDeserializationEnabled(String var1, boolean var2);

   List getFailoverServerGroups(String var1);

   void setFailoverServerGroups(String var1, List var2);

   boolean isSessionReplicationOnShutdownEnabled(String var1);

   void setSessionReplicationOnShutdownEnabled(String var1, boolean var2);

   void disableSessionStateQueryProtocolAfter(String var1, int var2);

   void setSessionStateQueryProtocolEnabled(String var1, boolean var2);

   void setCleanupOrphanedSessionsEnabled(String var1, boolean var2);

   boolean isCleanupOrphanedSessionEnabled(String var1);

   boolean isReplicationTimeoutEnabled();

   boolean isMemberDeathDetectorEnabled();

   Collection getRemoteMembersWithActivePartition(String var1);

   Collection getClusterMemberInfoWithActivePartition(String var1);

   String getMigratableServerStateOnMachine(String var1, String var2);

   public static class Locator {
      public static ClusterServices locate() {
         return (ClusterServices)GlobalServiceLocator.getServiceLocator().getService(ClusterServices.class, new Annotation[0]);
      }
   }
}
