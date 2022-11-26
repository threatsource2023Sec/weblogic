package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BERAny extends BERElement {
   private BERElement m_value = null;

   public BERAny(BERElement var1) {
      this.m_value = var1;
   }

   public BERAny(BERTagDecoder var1, InputStream var2, int[] var3) throws IOException {
      this.m_value = getElement(var1, var2, var3);
   }

   public void write(OutputStream var1) throws IOException {
      this.m_value.write(var1);
   }

   public int getType() {
      return -3;
   }

   public String toString() {
      return "ANY {" + this.m_value + "}";
   }
}
