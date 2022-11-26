package javax.jdo.identity;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ByteIdentity extends SingleFieldIdentity {
   private byte key;

   private void construct(byte key) {
      this.key = key;
      this.hashCode = super.hashClassName() ^ key;
   }

   public ByteIdentity(Class pcClass, byte key) {
      super(pcClass);
      this.construct(key);
   }

   public ByteIdentity(Class pcClass, Byte key) {
      super(pcClass);
      this.setKeyAsObject(key);
      this.construct(key);
   }

   public ByteIdentity(Class pcClass, String str) {
      super(pcClass);
      this.assertKeyNotNull(str);
      this.construct(Byte.parseByte(str));
   }

   public ByteIdentity() {
   }

   public byte getKey() {
      return this.key;
   }

   public String toString() {
      return Byte.toString(this.key);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         ByteIdentity other = (ByteIdentity)obj;
         return this.key == other.key;
      }
   }

   public int compareTo(Object o) {
      if (o instanceof ByteIdentity) {
         ByteIdentity other = (ByteIdentity)o;
         int result = super.compare(other);
         return result == 0 ? this.key - other.key : result;
      } else if (o == null) {
         throw new ClassCastException("object is null");
      } else {
         throw new ClassCastException(this.getClass().getName() + " != " + o.getClass().getName());
      }
   }

   protected Object createKeyAsObject() {
      return new Byte(this.key);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeByte(this.key);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.key = in.readByte();
   }
}
