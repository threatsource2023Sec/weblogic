package javax.jdo.identity;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class IntIdentity extends SingleFieldIdentity {
   private int key;

   private void construct(int key) {
      this.key = key;
      this.hashCode = this.hashClassName() ^ key;
   }

   public IntIdentity(Class pcClass, int key) {
      super(pcClass);
      this.construct(key);
   }

   public IntIdentity(Class pcClass, Integer key) {
      super(pcClass);
      this.setKeyAsObject(key);
      this.construct(key);
   }

   public IntIdentity(Class pcClass, String str) {
      super(pcClass);
      this.assertKeyNotNull(str);
      this.construct(Integer.parseInt(str));
   }

   public IntIdentity() {
   }

   public int getKey() {
      return this.key;
   }

   public String toString() {
      return Integer.toString(this.key);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         IntIdentity other = (IntIdentity)obj;
         return this.key == other.key;
      }
   }

   public int compareTo(Object o) {
      if (o instanceof IntIdentity) {
         IntIdentity other = (IntIdentity)o;
         int result = super.compare(other);
         return result == 0 ? this.key - other.key : result;
      } else if (o == null) {
         throw new ClassCastException("object is null");
      } else {
         throw new ClassCastException(this.getClass().getName() + " != " + o.getClass().getName());
      }
   }

   protected Object createKeyAsObject() {
      return new Integer(this.key);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeInt(this.key);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.key = in.readInt();
   }
}
