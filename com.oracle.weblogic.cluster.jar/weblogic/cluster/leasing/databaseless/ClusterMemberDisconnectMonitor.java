package weblogic.cluster.leasing.databaseless;

public interface ClusterMemberDisconnectMonitor {
   void start(ClusterGroupView var1, DisconnectActionListener var2);

   void stop();
}
