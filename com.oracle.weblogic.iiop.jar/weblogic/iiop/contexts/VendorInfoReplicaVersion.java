package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.rmi.cluster.ReplicaVersion;

public class VendorInfoReplicaVersion extends ServiceContext {
   private int version;

   VendorInfoReplicaVersion(CorbaInputStream in) {
      super(1111834895);
      this.readEncapsulatedContext(in);
   }

   public static VendorInfoReplicaVersion createResponse(ReplicaVersion replicaVersion) {
      return new VendorInfoReplicaVersion(replicaVersion.getVersion());
   }

   private VendorInfoReplicaVersion(int version) {
      super(1111834895);
      this.version = version;
   }

   public ReplicaVersion getReplicaVersion() {
      return new ReplicaVersion(this.version);
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_long(this.version);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      this.version = in.read_long();
   }
}
