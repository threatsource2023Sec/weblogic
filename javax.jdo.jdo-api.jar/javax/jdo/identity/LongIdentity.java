package javax.jdo.identity;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class LongIdentity extends SingleFieldIdentity {
   private long key;

   private void construct(long key) {
      this.key = key;
      this.hashCode = this.hashClassName() ^ (int)key;
   }

   public LongIdentity(Class pcClass, long key) {
      super(pcClass);
      this.construct(key);
   }

   public LongIdentity(Class pcClass, Long key) {
      super(pcClass);
      this.setKeyAsObject(key);
      this.construct(key);
   }

   public LongIdentity(Class pcClass, String str) {
      super(pcClass);
      this.assertKeyNotNull(str);
      this.construct(Long.parseLong(str));
   }

   public LongIdentity() {
   }

   public long getKey() {
      return this.key;
   }

   public String toString() {
      return Long.toString(this.key);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         LongIdentity other = (LongIdentity)obj;
         return this.key == other.key;
      }
   }

   public int compareTo(Object o) {
      if (o instanceof LongIdentity) {
         LongIdentity other = (LongIdentity)o;
         int result = super.compare(other);
         if (result == 0) {
            long diff = this.key - other.key;
            if (diff == 0L) {
               return 0;
            } else {
               return diff < 0L ? -1 : 1;
            }
         } else {
            return result;
         }
      } else if (o == null) {
         throw new ClassCastException("object is null");
      } else {
         throw new ClassCastException(this.getClass().getName() + " != " + o.getClass().getName());
      }
   }

   protected Object createKeyAsObject() {
      return new Long(this.key);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeLong(this.key);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.key = in.readLong();
   }
}
