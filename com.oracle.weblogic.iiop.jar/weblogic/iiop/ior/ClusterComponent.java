package weblogic.iiop.ior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.rmi.cluster.ReplicaID;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.cluster.ReplicaVersion;
import weblogic.rmi.cluster.Version;
import weblogic.rmi.facades.RmiInvocationFacade;

public final class ClusterComponent extends TaggedComponent {
   private List replicas;
   private boolean idempotent;
   private boolean stickToFirstServer;
   private String algorithm;
   private String jndiName;
   private Version version;
   private static final String NULL_STRING = "";
   private ReplicaState replicaState;
   private String partitionName;

   public ClusterComponent(boolean idempotent, boolean stickToFirstServer, String algorithm, String jndiName, ArrayList replicas, Version version, String partitionName) {
      super(1111834883);
      this.replicaState = new ReplicaState();
      this.idempotent = idempotent;
      this.algorithm = algorithm;
      this.jndiName = jndiName;
      this.stickToFirstServer = stickToFirstServer;
      this.setIORs(replicas);
      this.version = version;
      this.partitionName = partitionName;
   }

   public ClusterComponent(boolean idempotent, boolean stickToFirstServer, String algorithm, String jndiName, ArrayList replicas, ReplicaList replicaList, String partitionName) {
      this(idempotent, stickToFirstServer, algorithm, jndiName, replicas, replicaList.version(), partitionName);
      this.setReplicaID(replicaList.getReplicaID());
      this.setReplicaVersion(replicaList.getReplicaVersion());
   }

   public ClusterComponent(ClusterComponent cc) {
      this(cc.idempotent, cc.stickToFirstServer, cc.algorithm, cc.jndiName, (ArrayList)null, (Version)cc.version, cc.partitionName);
   }

   ClusterComponent(CorbaInputStream in) {
      super(1111834883);
      this.replicaState = new ReplicaState();
      this.read(in);
   }

   public final List getIORs() {
      return Collections.unmodifiableList(this.replicas);
   }

   public final Version getVersion() {
      return this.version;
   }

   public void setIORs(List iors) {
      this.replicas = iors == null ? Collections.emptyList() : iors;
   }

   public final boolean getIdempotent() {
      return this.idempotent;
   }

   public final boolean getStickToFirstServer() {
      return this.stickToFirstServer;
   }

   public final String getClusterAlgorithm() {
      return this.algorithm;
   }

   public final String getJndiName() {
      return this.jndiName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   private void setReplicaID(ReplicaID replicaID) {
      this.replicaState.setReplicaID(replicaID);
   }

   public ReplicaID getReplicaID() {
      return this.replicaState.getReplicaID();
   }

   private void setReplicaVersion(ReplicaVersion replicaVersion) {
      this.replicaState.setReplicaVersion(replicaVersion);
   }

   public ReplicaVersion getReplicaVersion() {
      return this.replicaState.getReplicaVersion();
   }

   public final void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      this.algorithm = in.read_string();
      this.idempotent = in.read_boolean();
      this.stickToFirstServer = in.read_boolean();
      this.jndiName = this.readJndiName(in);
      this.readReplicas(in);
      if (in.bytesLeft(handle) > 0) {
         this.version = new Version(in.read_longlong());
         if (in.bytesLeft(handle) > 0) {
            this.readReplicaRecoveryData(in);
         } else {
            this.partitionName = RmiInvocationFacade.getGlobalPartitionName();
         }
      }

      in.endEncapsulation(handle);
   }

   private void readReplicaRecoveryData(CorbaInputStream in) {
      this.replicaState.read(in);
      this.partitionName = in.read_string();
   }

   private String readJndiName(CorbaInputStream in) {
      String jndiName = in.read_string();
      return jndiName.equals("") ? null : jndiName;
   }

   private void readReplicas(CorbaInputStream in) {
      int nreplicas = in.read_long();
      this.replicas = new ArrayList(nreplicas);

      for(int i = 0; i < nreplicas; ++i) {
         IOR ior = new IOR(in);
         this.replicas.add(ior);
      }

   }

   public final void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      long handle = out.startEncapsulation();
      out.write_string(this.algorithm);
      out.write_boolean(this.idempotent);
      out.write_boolean(this.stickToFirstServer);
      out.write_string(this.jndiName == null ? "" : this.jndiName);
      this.writeReplicas(out);
      out.write_longlong(this.version == null ? 0L : this.version.getVersion());
      this.replicaState.write(out);
      out.write_string(this.partitionName);
      out.endEncapsulation(handle);
   }

   private void writeReplicas(CorbaOutputStream out) {
      out.write_long(this.replicas.size());
      Iterator var2 = this.replicas.iterator();

      while(var2.hasNext()) {
         IOR ior = (IOR)var2.next();
         ior.write(out);
      }

   }

   public String toString() {
      return "ClusterComponent{algorithm='" + this.algorithm + '\'' + ", replicas=" + this.replicas + ", idempotent=" + this.idempotent + ", stickToFirstServer=" + this.stickToFirstServer + ", jndiName='" + this.jndiName + '\'' + ", version=" + this.version + '}';
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ClusterComponent component = (ClusterComponent)o;
         return this.idempotent == component.idempotent && this.stickToFirstServer == component.stickToFirstServer && Objects.equals(this.replicas, component.replicas) && Objects.equals(this.algorithm, component.algorithm) && Objects.equals(this.jndiName, component.jndiName) && Objects.equals(this.version, component.version);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.replicas != null ? this.replicas.hashCode() : 0;
      result = 31 * result + (this.idempotent ? 1 : 0);
      result = 31 * result + (this.stickToFirstServer ? 1 : 0);
      result = 31 * result + (this.algorithm != null ? this.algorithm.hashCode() : 0);
      result = 31 * result + (this.jndiName != null ? this.jndiName.hashCode() : 0);
      result = 31 * result + (this.version != null ? this.version.hashCode() : 0);
      return result;
   }
}
