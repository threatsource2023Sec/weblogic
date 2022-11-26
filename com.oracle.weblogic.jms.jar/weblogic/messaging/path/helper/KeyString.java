package weblogic.messaging.path.helper;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import weblogic.messaging.path.Key;
import weblogic.messaging.saf.utils.SAFClientUtil;

public class KeyString implements Key, Externalizable {
   static final long serialVersionUID = -6246813907189434496L;
   private String id;
   private String assembly;
   private byte subsystem;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int FLAG_CLUSTER = 256;

   public KeyString(byte subsystemIdx, String assemblyId, String keyId) {
      assert subsystemIdx > -1 && subsystemIdx < Key.RESERVED_SUBSYSTEMS.size();

      this.subsystem = subsystemIdx;
      this.assembly = assemblyId.intern();
      this.id = keyId.intern();
   }

   public KeyString() {
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

   public String getStringId() {
      return this.id;
   }

   public int hashCode() {
      return this.assembly.hashCode() ^ this.id.hashCode() ^ this.subsystem;
   }

   public String toString() {
      return this.id + "^" + this.subsystem + "^" + this.assembly;
   }

   public boolean equals(Object o) {
      if (!(o instanceof Key)) {
         return false;
      } else {
         Key other = (Key)o;
         return other == this || this.subsystem == other.getSubsystem() && (this.assembly == other.getAssemblyId() || this.assembly.equals(other.getAssemblyId())) && (this.id == other.getKeyId() || this.id.equals(other.getKeyId()));
      }
   }

   public int compareTo(Object arg) {
      if (arg == this) {
         return 0;
      } else {
         Key other = (Key)arg;
         int c = this.id.compareTo(other.getKeyId());
         if (c != 0) {
            return c;
         } else {
            c = this.assembly.compareTo(other.getAssemblyId());
            return c != 0 ? c : this.subsystem - other.getSubsystem();
         }
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = 1;
      if (this.assembly == "CLUSTER") {
         flags |= 256;
      }

      out.writeInt(flags);
      out.writeUTF(this.id);
      if (this.assembly != "CLUSTER") {
         out.writeUTF(this.assembly);
      }

      out.writeByte(this.subsystem);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1) {
         throw SAFClientUtil.versionIOException(version, 1, 1);
      } else {
         this.id = in.readUTF();
         if ((flags & 256) == 0) {
            this.assembly = in.readUTF().intern();
         } else {
            this.assembly = "CLUSTER";
         }

         this.subsystem = in.readByte();
      }
   }
}
