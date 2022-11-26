package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;

public class BERSequence extends BERConstruct {
   public BERSequence() {
   }

   public BERSequence(BERTagDecoder var1, InputStream var2, int[] var3) throws IOException {
      super(var1, var2, var3);
   }

   public int getType() {
      return 48;
   }

   public String toString() {
      String var1 = "";

      for(int var2 = 0; var2 < super.size(); ++var2) {
         if (var2 != 0) {
            var1 = var1 + ", ";
         }

         var1 = var1 + super.elementAt(var2).toString();
      }

      return "Sequence {" + var1 + "}";
   }
}
