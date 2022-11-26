package weblogic.protocol;

import java.io.Serializable;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public class Identity implements Serializable {
   private long identity;

   public Identity(long identity) {
      this.identity = identity;
   }

   public long getIdentityAsLong() {
      return this.identity;
   }

   public boolean isTransient() {
      return this.identity < 0L;
   }

   public static Identity read(InputStream is) {
      return new Identity(is.read_longlong());
   }

   public void write(OutputStream os) {
      os.write_longlong(this.identity);
   }

   public int hashCode() {
      return (int)(-1L & this.identity ^ this.identity >>> 32);
   }

   public boolean equals(Object other) {
      return other instanceof Identity && ((Identity)other).identity == this.identity;
   }

   public String toString() {
      return "Identity<" + Long.toHexString(this.identity) + ">";
   }
}
