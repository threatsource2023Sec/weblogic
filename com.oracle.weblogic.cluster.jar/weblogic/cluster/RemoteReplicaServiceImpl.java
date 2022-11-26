package weblogic.cluster;

import java.rmi.RemoteException;
import weblogic.cluster.ejb.ReplicaIDInternalImpl;
import weblogic.cluster.replication.QuerySessionRequestMessage;
import weblogic.cluster.replication.QuerySessionResponseMessage;
import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ReplicationServicesFactory;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.jndi.annotation.CrossPartitionAware;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.cluster.RemoteReplicaService;
import weblogic.rmi.cluster.ReplicaID;
import weblogic.rmi.cluster.ReplicaInfo;
import weblogic.rmi.cluster.ReplicaVersion;
import weblogic.rmi.extensions.RequestTimeoutException;

@CrossPartitionAware
public class RemoteReplicaServiceImpl implements RemoteReplicaService {
   public ReplicaInfo findReplica(ReplicaID id, ReplicaVersion version, String partitionName) throws RemoteException {
      if (ClusterDebugLogger.isDebugEnabled()) {
         ClusterDebugLogger.debug("RemoteReplicaServiceImpl.findReplica(id=" + id + ", version=" + version + ")");
      }

      if (!id.getType().equals("EJB")) {
         throw new RemoteException("Unknown ReplicaID type: " + id.getType());
      } else {
         ReplicaIDInternal idi = new ReplicaIDInternalImpl(id);
         ReplicaInfo info = this.searchReplica(idi, version, true);
         if (info != null) {
            return info;
         } else {
            info = this.searchReplica(idi, version, false);
            if (info != null) {
               return info;
            } else {
               info = idi.getReplicaInfoWithTargetClusterAddressForMigratedPartition(partitionName);
               if (info != null) {
                  return info;
               } else {
                  throw new RequestTimeoutException("Timed out in finding replica " + id + ", version " + version);
               }
            }
         }
      }
   }

   private ReplicaInfo searchReplica(ReplicaIDInternal idi, ReplicaVersion version, boolean primary) throws RemoteException {
      if (ClusterDebugLogger.isDebugEnabled()) {
         ClusterDebugLogger.debug("RemoteReplicaServiceImpl.searchReplica(idi=" + idi + ", version=" + version + ", primary=" + primary + ")");
      }

      QuerySessionRequestMessage request = (QuerySessionRequestMessage)idi.buildReplicaQueryRequestMessage(version, primary);
      QuerySessionResponseMessage response = request.execute(LocalServerIdentity.getIdentity());
      if (response != null) {
         ReplicaInfo info = idi.processReplicaQueryResponseMessage(response);
         if (info != null) {
            return info;
         }
      }

      ReplicationServices repServ = Locator.locate().getReplicationService((ReplicationServicesFactory.ServiceType)idi.getReplicationServiceType());
      request = (QuerySessionRequestMessage)idi.buildReplicaQueryRequestMessage(version, primary);
      response = repServ.sendQuerySessionRequest(request, ClusterService.getClusterServiceInternal().getSessionStateQueryRequestTimeout());
      if (response == null) {
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("RemoteReplicaServiceImpl.searchReplica(idi=" + idi + ", version=" + version + ", primary=" + primary + ") timed out");
         }

         return null;
      } else {
         return idi.processReplicaQueryResponseMessage(response);
      }
   }
}
