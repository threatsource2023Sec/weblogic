package weblogic.cache.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.concurrent.atomic.AtomicLong;

public class Version implements Externalizable {
   private AtomicLong version = new AtomicLong();

   public long get() {
      return this.version.get();
   }

   public void set(long version) {
      this.version.set(version);
   }

   public long increment() {
      return this.version.incrementAndGet();
   }

   public int hashCode() {
      return (int)this.get();
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof Version) {
         Version other = (Version)o;
         return other.version.get() == this.version.get();
      } else {
         return false;
      }
   }

   public String toString() {
      return super.toString() + "|" + this.version.get();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeLong(this.version.get());
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.version.set(in.readLong());
   }
}
