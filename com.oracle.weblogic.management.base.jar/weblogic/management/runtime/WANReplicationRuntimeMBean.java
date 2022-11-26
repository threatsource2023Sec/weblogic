package weblogic.management.runtime;

public interface WANReplicationRuntimeMBean extends ReplicationRuntimeMBean {
   long getNumberOfSessionsFlushedToTheDatabase();

   long getNumberOfSessionsRetrievedFromTheDatabase();

   void cleanupExpiredSessionsInTheDatabase();

   /** @deprecated */
   @Deprecated
   String getSecondaryServerName();

   boolean getRemoteClusterReachable();

   void setRemoteClusterReachable(boolean var1);
}
