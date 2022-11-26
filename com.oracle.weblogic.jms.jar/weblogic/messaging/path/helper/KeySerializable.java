package weblogic.messaging.path.helper;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import weblogic.messaging.path.Key;
import weblogic.messaging.saf.utils.SAFClientUtil;

public class KeySerializable implements Key, Externalizable {
   static final long serialVersionUID = 7635946436418512920L;
   private Serializable id;
   private String assembly;
   private byte subsystem;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int FLAG_CLUSTER = 256;

   public KeySerializable(byte subsystemIdx, String assemblyId, Serializable keyId) {
      assert assemblyId != null && subsystemIdx > -1 && subsystemIdx < Key.RESERVED_SUBSYSTEMS.size();

      this.subsystem = subsystemIdx;
      this.assembly = assemblyId;
      this.id = keyId;
      Comparable test = (Comparable)this.id;
   }

   public KeySerializable() {
   }

   public byte getSubsystem() {
      return this.subsystem;
   }

   public String getAssemblyId() {
      return this.assembly;
   }

   public Serializable getKeyId() {
      return this.id;
   }

   public int hashCode() {
      return this.assembly.hashCode() ^ this.id.hashCode() ^ this.subsystem;
   }

   public String toString() {
      return this.id + "^" + this.subsystem + "^" + this.assembly;
   }

   public boolean equals(Object o) {
      if (!(o instanceof KeySerializable)) {
         return false;
      } else {
         KeySerializable other = (KeySerializable)o;
         return other == this || this.subsystem == other.subsystem && (this.assembly == other.assembly || this.assembly.equals(other.assembly)) && (this.id == other.id || this.id.equals(other.id));
      }
   }

   public int compareTo(Object arg) {
      Key other = (Key)arg;
      int c = ((Comparable)this.id).compareTo(other.getKeyId());
      if (c != 0) {
         return c;
      } else {
         c = this.assembly.compareTo(other.getAssemblyId());
         return c != 0 ? c : this.subsystem - other.getSubsystem();
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = 1;
      if (this.assembly == "CLUSTER") {
         flags |= 256;
      }

      out.writeInt(flags);
      out.writeObject(this.id);
      out.writeByte(this.subsystem);
      if (this.assembly != "CLUSTER") {
         out.writeUTF(this.assembly);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1) {
         throw SAFClientUtil.versionIOException(version, 1, 1);
      } else {
         this.id = (Serializable)in.readObject();
         this.subsystem = in.readByte();
         if ((flags & 256) == 0) {
            this.assembly = in.readUTF();
         } else {
            this.assembly = "CLUSTER";
         }

      }
   }
}
