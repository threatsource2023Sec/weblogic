package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;

public class BEREnumerated extends BERIntegral {
   public BEREnumerated(int var1) {
      super(var1);
   }

   public BEREnumerated(InputStream var1, int[] var2) throws IOException {
      super(var1, var2);
   }

   public int getType() {
      return 10;
   }

   public String toString() {
      return "Enumerated {" + this.getValue() + "}";
   }
}
