package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;

public class BERInteger extends BERIntegral {
   public BERInteger(int var1) {
      super(var1);
   }

   public BERInteger(InputStream var1, int[] var2) throws IOException {
      super(var1, var2);
   }

   public int getType() {
      return 2;
   }

   public String toString() {
      return "Integer {" + this.getValue() + "}";
   }
}
