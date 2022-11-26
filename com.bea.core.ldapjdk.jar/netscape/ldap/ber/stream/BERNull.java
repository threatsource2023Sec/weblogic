package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BERNull extends BERElement {
   public BERNull() {
   }

   public BERNull(InputStream var1, int[] var2) throws IOException {
      readLengthOctets(var1, var2);
   }

   public void write(OutputStream var1) throws IOException {
      byte[] var2 = new byte[]{5, 0};
      var1.write(var2);
   }

   public int getType() {
      return 5;
   }

   public String toString() {
      return "Null {}";
   }
}
