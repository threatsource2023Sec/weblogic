package javax.jdo.identity;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class StringIdentity extends SingleFieldIdentity {
   public StringIdentity(Class pcClass, String key) {
      super(pcClass);
      this.setKeyAsObject(key);
      this.hashCode = this.hashClassName() ^ key.hashCode();
   }

   public StringIdentity() {
   }

   public String getKey() {
      return (String)this.keyAsObject;
   }

   public String toString() {
      return (String)this.keyAsObject;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         StringIdentity other = (StringIdentity)obj;
         return this.keyAsObject.equals(other.keyAsObject);
      }
   }

   public int compareTo(Object o) {
      if (o instanceof StringIdentity) {
         StringIdentity other = (StringIdentity)o;
         int result = super.compare(other);
         return result == 0 ? ((String)this.keyAsObject).compareTo((String)other.keyAsObject) : result;
      } else if (o == null) {
         throw new ClassCastException("object is null");
      } else {
         throw new ClassCastException(this.getClass().getName() + " != " + o.getClass().getName());
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeObject(this.keyAsObject);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.keyAsObject = (String)in.readObject();
   }
}
