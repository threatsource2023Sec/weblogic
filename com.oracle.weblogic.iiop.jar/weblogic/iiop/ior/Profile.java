package weblogic.iiop.ior;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public class Profile {
   public static final int TAG_INTERNET_IOP = 0;
   static final int TAG_MULTIPLE_COMPONENTS = 1;
   private final int tag;
   private byte[] buf;

   public Profile(int tag) {
      this.tag = tag;
   }

   public void read(CorbaInputStream in) {
      this.buf = in.read_octet_sequence();
   }

   public void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      out.write_long(this.buf.length);
      out.write_octet_array(this.buf, 0, this.buf.length);
   }

   public String toString() {
      return "Unknown profile tag=" + this.tag;
   }
}
