package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class ResourceGroupKeyImpl implements ResourceGroupKey, Externalizable {
   private String partitionName;
   private String resourceGroup;
   private int hashcode;
   private static final byte KEY_CODE_MASK = 7;
   private static final byte NULLKEY_CODE = 1;
   private static final byte GLOBALKEY_CODE = 7;
   private static final ResourceGroupKeyImpl NULL_KEY = new ResourceGroupKeyImpl("", "");
   private static final ResourceGroupKeyImpl GLOBAL_KEY = new ResourceGroupKeyImpl("DOMAIN", "GLOBAL_RESOURCE_GROUP");

   public ResourceGroupKeyImpl() {
   }

   ResourceGroupKeyImpl(String partitionName, String resourceGroup) {
      this.partitionName = partitionName;
      this.resourceGroup = resourceGroup;
      this.hashcode = partitionName.hashCode() ^ resourceGroup.hashCode();
   }

   static ResourceGroupKey createKey(String partitionName, String resourceGroup) {
      if (partitionName != null && partitionName.equals("DOMAIN") && resourceGroup != null && resourceGroup.equals("GLOBAL_RESOURCE_GROUP")) {
         return GLOBAL_KEY;
      } else {
         return isEmpty(partitionName) && isEmpty(resourceGroup) ? NULL_KEY : new ResourceGroupKeyImpl(partitionName, resourceGroup);
      }
   }

   public String getResourceGroupName() {
      return this.resourceGroup;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public int hashCode() {
      return this.hashcode;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof ResourceGroupKeyImpl)) {
         return false;
      } else {
         ResourceGroupKeyImpl key = (ResourceGroupKeyImpl)other;
         return this.partitionName != null && this.partitionName.equals(key.partitionName) && this.resourceGroup != null && this.resourceGroup.equals(key.resourceGroup);
      }
   }

   public String toString() {
      return " PartitionId: " + this.partitionName + ", ResourceGroup: " + this.resourceGroup;
   }

   static void writeKeyToStream(ResourceGroupKey key, ObjectOutput out) throws IOException {
      byte keyCode = 0;
      if (key == NULL_KEY) {
         keyCode = 1;
      } else if (key == GLOBAL_KEY) {
         keyCode = 7;
      }

      out.writeByte(keyCode);
      if ((keyCode & 7) == 0) {
         key.writeExternal(out);
      }

   }

   static ResourceGroupKey readKeyFromStream(ObjectInput in) throws IOException, ClassNotFoundException {
      byte keyCode = in.readByte();
      if ((keyCode & 7) == 7) {
         return GLOBAL_KEY;
      } else if ((keyCode & 7) == 1) {
         return NULL_KEY;
      } else {
         ResourceGroupKeyImpl key = new ResourceGroupKeyImpl();
         key.readExternal(in);
         return key;
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.partitionName);
      out.writeUTF(this.resourceGroup);
      out.writeInt(this.hashcode);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.partitionName = in.readUTF();
      this.resourceGroup = in.readUTF();
      this.hashcode = in.readInt();
   }

   private static boolean isEmpty(String s) {
      return s == null || s.length() == 0;
   }
}
