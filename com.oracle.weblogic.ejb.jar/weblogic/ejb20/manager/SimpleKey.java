package weblogic.ejb20.manager;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.utils.io.Immutable;

public class SimpleKey implements Externalizable, Immutable {
   private static final long serialVersionUID = 2652002217566939126L;
   private long val;
   private transient int hashValue;

   public SimpleKey(long val) {
      this.val = val;
      this.hashValue = (int)(val ^ val >> 32);
   }

   public SimpleKey() {
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof SimpleKey) {
         SimpleKey other = (SimpleKey)obj;
         return this.val == other.val;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.hashValue;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeLong(this.val);
   }

   public void readExternal(ObjectInput in) throws IOException {
      this.val = in.readLong();
      this.hashValue = (int)(this.val ^ this.val >> 32);
   }

   public String toString() {
      return Long.toString(this.val);
   }
}
