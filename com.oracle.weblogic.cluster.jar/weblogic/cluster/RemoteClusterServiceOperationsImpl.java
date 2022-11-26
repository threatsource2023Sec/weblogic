package weblogic.cluster;

import java.rmi.RemoteException;
import java.util.List;

public class RemoteClusterServiceOperationsImpl implements RemoteClusterServicesOperations {
   public void setSessionLazyDeserializationEnabled(boolean enabled) throws RemoteException {
      ClusterService.getClusterServiceInternal().setSessionLazyDeserializationEnabled(enabled);
   }

   public void setFailoverServerGroups(List serverGroups) throws RemoteException {
      ClusterService.getClusterServiceInternal().setFailoverServerGroups(serverGroups);
   }

   public void setSessionReplicationOnShutdownEnabled(boolean enabled) throws RemoteException {
      ClusterService.getClusterServiceInternal().setSessionReplicationOnShutdownEnabled(enabled);
   }

   public void disableSessionStateQueryProtocolAfter(int secondsToWait) throws RemoteException {
      ClusterService.getClusterServiceInternal().disableSessionStateQueryProtocolAfter(secondsToWait);
   }

   public void setSessionStateQueryProtocolEnabled(boolean enabled) throws RemoteException {
      ClusterService.getClusterServiceInternal().setSessionStateQueryProtocolEnabled(enabled);
   }

   public void setCleanupOrphanedSessionsEnabled(boolean enabled) throws RemoteException {
      ClusterService.getClusterServiceInternal().setCleanupOrphanedSessionsEnabled(enabled);
   }

   public void setSessionLazyDeserializationEnabled(String partitionName, boolean enabled) throws RemoteException {
      ClusterService.getClusterServiceInternal().setSessionLazyDeserializationEnabled(partitionName, enabled);
   }

   public void setFailoverServerGroups(String partitionName, List serverGroups) throws RemoteException {
      ClusterService.getClusterServiceInternal().setFailoverServerGroups(partitionName, serverGroups);
   }

   public void setSessionReplicationOnShutdownEnabled(String partitionName, boolean enabled) throws RemoteException {
      ClusterService.getClusterServiceInternal().setSessionReplicationOnShutdownEnabled(partitionName, enabled);
   }

   public void disableSessionStateQueryProtocolAfter(String partitionName, int secondsToWait) throws RemoteException {
      ClusterService.getClusterServiceInternal().disableSessionStateQueryProtocolAfter(partitionName, secondsToWait);
   }

   public void setSessionStateQueryProtocolEnabled(String partitionName, boolean enabled) throws RemoteException {
      ClusterService.getClusterServiceInternal().setSessionStateQueryProtocolEnabled(partitionName, enabled);
   }

   public void setCleanupOrphanedSessionsEnabled(String partitionName, boolean enabled) throws RemoteException {
      ClusterService.getClusterServiceInternal().setCleanupOrphanedSessionsEnabled(partitionName, enabled);
   }
}
