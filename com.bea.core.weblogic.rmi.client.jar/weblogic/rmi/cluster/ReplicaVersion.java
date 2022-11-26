package weblogic.rmi.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;

public final class ReplicaVersion implements PiggybackResponse, Externalizable {
   private static final long serialVersionUID = 1540638010284712931L;
   private int version = 0;
   private transient boolean versionChanged = false;

   public ReplicaVersion() {
   }

   public ReplicaVersion(int version) {
      this.version = version;
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int v) {
      if (this.version != v) {
         this.version = v;
         this.versionChanged = true;
      } else {
         this.versionChanged = false;
      }

   }

   public void copy(ReplicaVersion v) {
      if (v.version != this.version) {
         this.version = v.version;
         this.versionChanged = true;
      } else {
         this.versionChanged = false;
      }

   }

   public boolean hasVersionChanged() {
      boolean b = this.versionChanged;
      this.versionChanged = false;
      return b;
   }

   public int hashCode() {
      return (new Integer(this.version)).hashCode();
   }

   public boolean equals(Object other) {
      if (other instanceof ReplicaVersion) {
         return this.version == ((ReplicaVersion)other).version;
      } else {
         return false;
      }
   }

   public String toString() {
      return "[ReplicaVersion:" + this.version + "]";
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(this.version);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.version = in.readInt();
   }

   public static boolean doesPeerSupportReplicaVersion(Object o) {
      if (!(o instanceof PeerInfoable)) {
         return false;
      } else {
         PeerInfo pi = ((PeerInfoable)o).getPeerInfo();
         if (pi == null) {
            return false;
         } else if (pi.compareTo(PeerInfo.VERSION_122110) < 0) {
            return false;
         } else if (pi.compareTo(PeerInfo.VERSION_122210) >= 0) {
            return true;
         } else {
            return !PeerInfo.isPeerVersionInVersionGap(pi, PeerInfo.VERSION_122200, PeerInfo.VERSION_122210);
         }
      }
   }
}
