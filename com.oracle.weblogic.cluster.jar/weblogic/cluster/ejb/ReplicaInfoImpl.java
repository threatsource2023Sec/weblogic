package weblogic.cluster.ejb;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import weblogic.rmi.cluster.ReplicaInfo;
import weblogic.rmi.extensions.server.RemoteReference;

public class ReplicaInfoImpl implements ReplicaInfo, Externalizable {
   private static final long serialVersionUID = -3603665250999472829L;
   private RemoteReference ref = null;
   private String[] targetClusterAddresses = null;

   public ReplicaInfoImpl() {
   }

   public ReplicaInfoImpl(RemoteReference ref) {
      this.ref = ref;
   }

   public ReplicaInfoImpl(String[] targetClusterAddresses) {
      this.targetClusterAddresses = targetClusterAddresses;
   }

   public RemoteReference getRemoteRef() {
      return this.ref;
   }

   public String[] getTargetClusterAddresses() {
      return this.targetClusterAddresses;
   }

   public String toString() {
      return "ReplicaInfoImpl[hostid=" + (this.ref != null ? this.ref.getHostID() : null) + " , oid=" + (this.ref != null ? this.ref.getObjectID() : null) + ", targetClusterAddresses=" + Arrays.toString(this.targetClusterAddresses) + "]";
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.ref);
      out.writeBoolean(this.targetClusterAddresses != null);
      if (this.targetClusterAddresses != null) {
         out.writeInt(this.targetClusterAddresses.length);
         String[] var2 = this.targetClusterAddresses;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String address = var2[var4];
            out.writeUTF(address);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.ref = (RemoteReference)in.readObject();
      boolean hasTargetClusterAddresses = in.readBoolean();
      if (hasTargetClusterAddresses) {
         int length = in.readInt();
         this.targetClusterAddresses = new String[length];

         for(int i = 0; i < length; ++i) {
            this.targetClusterAddresses[i] = in.readUTF();
         }
      }

   }
}
