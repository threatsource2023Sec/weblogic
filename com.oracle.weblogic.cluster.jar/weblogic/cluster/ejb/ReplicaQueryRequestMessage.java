package weblogic.cluster.ejb;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.UUID;
import weblogic.cluster.ClusterDebugLogger;
import weblogic.cluster.ReplicaIDInternal;
import weblogic.cluster.replication.QuerySessionRequestMessage;
import weblogic.cluster.replication.QuerySessionResponseMessage;
import weblogic.cluster.replication.ROID;
import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ReplicationServicesFactory;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.spi.HostID;
import weblogic.utils.ArrayUtils;

public class ReplicaQueryRequestMessage implements QuerySessionRequestMessage, Externalizable {
   private static final long serialVersionUID = -8194574116589583885L;
   private ReplicaIDInternal idi = null;
   private int version;
   private boolean primary = true;
   private String uuid = null;

   public ReplicaQueryRequestMessage() {
   }

   public ReplicaQueryRequestMessage(ReplicaIDInternal idi, HostID from, int version, boolean primary) {
      this.idi = idi;
      this.version = version;
      this.primary = primary;
      this.uuid = UUID.randomUUID() + "[" + from + "]";
   }

   public QuerySessionResponseMessage execute(HostID sender) {
      try {
         ReplicationServices repServ = Locator.locate().getReplicationService((ReplicationServicesFactory.ServiceType)this.idi.getReplicationServiceType());
         Object id = this.idi.getReplicaID().getID();
         ROID roid;
         if (id instanceof ROID) {
            roid = (ROID)id;
         } else {
            if (!(id instanceof byte[])) {
               throw new Exception("Unknown object type " + id + " in ReplicaID " + this.idi.getReplicaID());
            }

            roid = (ROID)ArrayUtils.byteArrayToObject((byte[])((byte[])id));
         }

         RemoteReference ref = repServ.lookupReplicaRemoteRef(roid, this.idi.getReplicationKey(), this.version, this.primary);
         if (ref != null) {
            return new ReplicaQueryResponseMessage(this.getID(), ref);
         }
      } catch (Exception var6) {
         if (ClusterDebugLogger.isDebugEnabled()) {
            ClusterDebugLogger.debug("ReplicaQueryRequestMessage: exception on looking up replica " + this.idi + " for request " + this.uuid, var6);
         }
      }

      return null;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.idi);
      out.writeInt(this.version);
      out.writeBoolean(this.primary);
      out.writeUTF(this.uuid);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.idi = (ReplicaIDInternal)in.readObject();
      this.version = in.readInt();
      this.primary = in.readBoolean();
      this.uuid = in.readUTF();
   }

   public String getID() {
      return this.uuid;
   }
}
