package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

public class WrappedSerializable implements Externalizable {
   Serializable serializable;
   ResourceGroupKey resourceGroupKey;

   public WrappedSerializable() {
   }

   WrappedSerializable(Serializable serializable, ResourceGroupKey key) {
      this.serializable = serializable;
      this.resourceGroupKey = key;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.serializable);
      ResourceGroupKeyImpl.writeKeyToStream(this.resourceGroupKey, out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.serializable = (Serializable)in.readObject();
      this.resourceGroupKey = ResourceGroupKeyImpl.readKeyFromStream(in);
   }

   public String toString() {
      return " serializable: " + this.serializable + ", resourceGroupKey: " + this.resourceGroupKey;
   }
}
