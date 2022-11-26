package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BERSet extends BERConstruct {
   public BERSet() throws IOException {
   }

   public BERSet(BERTagDecoder var1, InputStream var2, int[] var3) throws IOException {
      super(var1, var2, var3);
   }

   public void write(OutputStream var1) throws IOException {
      super.write(var1);
   }

   public int getType() {
      return 49;
   }

   public String toString() {
      String var1 = "";

      for(int var2 = 0; var2 < super.size(); ++var2) {
         if (var2 != 0) {
            var1 = var1 + ", ";
         }

         var1 = var1 + super.elementAt(var2).toString();
      }

      return "Set {" + var1 + "}";
   }
}
