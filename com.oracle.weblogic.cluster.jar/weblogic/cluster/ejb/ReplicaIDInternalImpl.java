package weblogic.cluster.ejb;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.RemoteException;
import weblogic.cluster.ReplicaIDInternal;
import weblogic.cluster.replication.Replicatable;
import weblogic.cluster.replication.ResourceGroupMigrationHandler;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rmi.cluster.ReplicaID;
import weblogic.rmi.cluster.ReplicaInfo;
import weblogic.rmi.cluster.ReplicaVersion;
import weblogic.rmi.cluster.ejb.ReplicaIDImpl;
import weblogic.rmi.extensions.server.RemoteReference;

public class ReplicaIDInternalImpl implements ReplicaIDInternal, Externalizable {
   private static final long serialVersionUID = -8856336118592860454L;
   private ReplicaIDImpl replicaID;

   public ReplicaIDInternalImpl() {
   }

   public ReplicaIDInternalImpl(ReplicaID replicaID) throws RemoteException {
      if (!(replicaID instanceof ReplicaIDImpl)) {
         throw new RemoteException("Unknown ReplicaID implementation class: " + replicaID.getClass().getName());
      } else {
         this.replicaID = (ReplicaIDImpl)replicaID;
      }
   }

   public ReplicaID getReplicaID() {
      return this.replicaID;
   }

   public Object getReplicationKey() {
      return Replicatable.DEFAULT_KEY;
   }

   public Object getReplicationServiceType() {
      return ServiceType.SYNC;
   }

   public Object buildReplicaQueryRequestMessage(ReplicaVersion version, boolean primary) {
      return new ReplicaQueryRequestMessage(this, LocalServerIdentity.getIdentity(), version.getVersion(), primary);
   }

   public ReplicaInfo processReplicaQueryResponseMessage(Object response) throws RemoteException {
      RemoteReference ref = ((ReplicaQueryResponseMessage)response).getRemoteRef();
      return ref == null ? null : new ReplicaInfoImpl(ref);
   }

   public ReplicaInfo getReplicaInfoWithTargetClusterAddressForMigratedPartition(String partitionName) {
      String[] addresses = this.getClusterAddressesForMigratedPartition(partitionName);
      return addresses != null && addresses.length > 0 ? new ReplicaInfoImpl(addresses) : null;
   }

   private String[] getClusterAddressesForMigratedPartition(String partitionName) {
      return ResourceGroupMigrationHandler.getInstance().getTargetClusterAddressesForPartition(partitionName);
   }

   public String toString() {
      return this.replicaID.toString();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.replicaID);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.replicaID = (ReplicaIDImpl)in.readObject();
   }
}
