package weblogic.management.runtime;

public interface MANReplicationRuntimeMBean extends ReplicationRuntimeMBean {
   String getSecondaryServerName();

   String[] getActiveServersInRemoteCluster();

   boolean getRemoteClusterReachable();
}
