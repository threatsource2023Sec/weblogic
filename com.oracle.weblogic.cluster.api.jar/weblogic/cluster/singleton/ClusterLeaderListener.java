package weblogic.cluster.singleton;

public interface ClusterLeaderListener {
   void localServerIsClusterLeader();

   void localServerLostClusterLeadership();
}
