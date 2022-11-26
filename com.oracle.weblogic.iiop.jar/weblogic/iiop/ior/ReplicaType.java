package weblogic.iiop.ior;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.cluster.replication.ROID;
import weblogic.rmi.cluster.ReplicaID;
import weblogic.rmi.cluster.ejb.ReplicaIDImpl;

public enum ReplicaType {
   none {
      void write_value(ObjectOutput out, ReplicaID replicaID) throws IOException {
      }

      ReplicaID read_value(ObjectInput in) {
         return null;
      }
   },
   ejb_roid {
      void write_value(ObjectOutput out, ReplicaID replicaID) throws IOException {
         ((ROID)replicaID.getID()).writeExternal(out);
      }

      ReplicaID read_value(ObjectInput in) throws IOException, ClassNotFoundException {
         ROID roid = new ROID();
         roid.readExternal(in);
         return new ReplicaIDImpl(roid);
      }
   },
   byte_array {
      void write_value(ObjectOutput out, ReplicaID replicaID) throws IOException {
         this.writeBytes(out, (byte[])((byte[])replicaID.getID()));
      }

      private void writeBytes(ObjectOutput out, byte[] id) throws IOException {
         out.writeInt(id.length);
         out.write(id);
      }

      ReplicaID read_value(ObjectInput in) throws IOException, ClassNotFoundException {
         int numBytes = in.readInt();
         byte[] bytes = new byte[numBytes];
         in.read(bytes);
         return new ReplicaIDImpl(bytes);
      }
   };

   private ReplicaType() {
   }

   abstract void write_value(ObjectOutput var1, ReplicaID var2) throws IOException;

   abstract ReplicaID read_value(ObjectInput var1) throws IOException, ClassNotFoundException;

   public static ReplicaType forId(ReplicaID id) {
      return id == null ? none : getEnumForValue(id.getID());
   }

   private static ReplicaType getEnumForValue(Object value) {
      if (value instanceof byte[]) {
         return byte_array;
      } else if (value instanceof ROID) {
         return ejb_roid;
      } else {
         throw new IllegalArgumentException("Unsupported replica ID implementation: " + value.getClass());
      }
   }

   // $FF: synthetic method
   ReplicaType(Object x2) {
      this();
   }
}
