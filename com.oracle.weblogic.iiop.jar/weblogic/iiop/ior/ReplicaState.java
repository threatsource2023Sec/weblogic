package weblogic.iiop.ior;

import java.io.IOException;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.rmi.cluster.ReplicaID;
import weblogic.rmi.cluster.ReplicaVersion;

public class ReplicaState {
   private static final ReplicaVersion NULL_REPLICA_VERSION = new ReplicaVersion(0);
   private ReplicaType replicaType;
   private ReplicaID replicaID;
   private ReplicaVersion replicaVersion;

   public ReplicaState() {
      this.replicaType = ReplicaType.none;
      this.replicaID = null;
      this.replicaVersion = NULL_REPLICA_VERSION;
   }

   public void copy(ReplicaState other) {
      this.setReplicaID(other.replicaID);
      this.setReplicaVersion(other.replicaVersion);
   }

   public ReplicaID getReplicaID() {
      return this.replicaID;
   }

   public void setReplicaID(ReplicaID id) {
      this.replicaID = id;
      this.replicaType = ReplicaType.forId(id);
   }

   public boolean isReplicaVersionChanged() {
      return this.replicaVersion != null && this.replicaVersion.hasVersionChanged();
   }

   public ReplicaVersion getReplicaVersion() {
      return this.replicaVersion;
   }

   public void setReplicaVersion(ReplicaVersion rv) {
      if (rv == null) {
         this.replicaVersion = NULL_REPLICA_VERSION;
      } else if (this.replicaVersion.equals(NULL_REPLICA_VERSION)) {
         this.replicaVersion = new ReplicaVersion(rv.getVersion());
      } else {
         this.replicaVersion.copy(rv);
      }

   }

   public void read(CorbaInputStream in) {
      this.readReplicaId(in);
      this.readReplicaVersion(in);
   }

   private void readReplicaVersion(CorbaInputStream in) {
      this.replicaVersion = new ReplicaVersion(in.read_long());
   }

   private void readReplicaId(CorbaInputStream in) {
      try {
         this.replicaType = ReplicaType.values()[in.read_ulong()];
         this.replicaID = this.replicaType.read_value(in);
      } catch (ClassNotFoundException | IOException var3) {
         throw new RuntimeException("unable to deserialize replicaId", var3);
      }
   }

   public void write(CorbaOutputStream out) {
      this.writeReplicaId(out);
      this.writeReplicaVersion(out);
   }

   private void writeReplicaVersion(CorbaOutputStream out) {
      out.write_long(this.replicaVersion.getVersion());
   }

   private void writeReplicaId(CorbaOutputStream out) {
      try {
         out.write_ulong(this.replicaType.ordinal());
         this.replicaType.write_value(out, this.replicaID);
      } catch (IOException var3) {
         throw new RuntimeException("unable to serialize replicaId", var3);
      }
   }
}
