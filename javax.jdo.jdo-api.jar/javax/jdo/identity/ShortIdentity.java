package javax.jdo.identity;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ShortIdentity extends SingleFieldIdentity {
   private short key;

   private void construct(short key) {
      this.key = key;
      this.hashCode = this.hashClassName() ^ key;
   }

   public ShortIdentity(Class pcClass, short key) {
      super(pcClass);
      this.construct(key);
   }

   public ShortIdentity(Class pcClass, Short key) {
      super(pcClass);
      this.setKeyAsObject(key);
      this.construct(key);
   }

   public ShortIdentity(Class pcClass, String str) {
      super(pcClass);
      this.assertKeyNotNull(str);
      this.construct(Short.parseShort(str));
   }

   public ShortIdentity() {
   }

   public short getKey() {
      return this.key;
   }

   public String toString() {
      return Short.toString(this.key);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         ShortIdentity other = (ShortIdentity)obj;
         return this.key == other.key;
      }
   }

   public int compareTo(Object o) {
      if (o instanceof ShortIdentity) {
         ShortIdentity other = (ShortIdentity)o;
         int result = super.compare(other);
         return result == 0 ? this.key - other.key : result;
      } else if (o == null) {
         throw new ClassCastException("object is null");
      } else {
         throw new ClassCastException(this.getClass().getName() + " != " + o.getClass().getName());
      }
   }

   protected Object createKeyAsObject() {
      return new Short(this.key);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeShort(this.key);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.key = in.readShort();
   }
}
