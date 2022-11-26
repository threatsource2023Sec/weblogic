package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;

public class BERNumericString extends BERCharacterString {
   public BERNumericString(String var1) {
      this.m_value = var1;
   }

   public BERNumericString(byte[] var1) {
      super(var1);
   }

   public BERNumericString(BERTagDecoder var1, InputStream var2, int[] var3) throws IOException {
      super(var1, var2, var3);
   }

   public BERNumericString(InputStream var1, int[] var2) throws IOException {
      super(var1, var2);
   }

   public int getType() {
      return 18;
   }

   public String toString() {
      return this.m_value == null ? "NumericString (null)" : "NumericString {" + this.m_value + "}";
   }
}
