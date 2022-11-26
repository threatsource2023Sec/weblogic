package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BERBoolean extends BERElement {
   private boolean m_value = true;

   public BERBoolean(boolean var1) {
      this.m_value = var1;
   }

   public BERBoolean(InputStream var1, int[] var2) throws IOException {
      int var3 = var1.read();
      int var10002 = var2[0]++;
      var3 = var1.read();
      var10002 = var2[0]++;
      if (var3 > 0) {
         this.m_value = true;
      } else {
         this.m_value = false;
      }

   }

   public void write(OutputStream var1) throws IOException {
      var1.write(1);
      var1.write(1);
      if (this.m_value) {
         var1.write(255);
      } else {
         var1.write(0);
      }

   }

   public boolean getValue() {
      return this.m_value;
   }

   public int getType() {
      return 1;
   }

   public String toString() {
      return "Boolean {" + this.m_value + "}";
   }
}
