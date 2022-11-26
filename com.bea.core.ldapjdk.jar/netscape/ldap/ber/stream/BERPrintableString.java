package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;

public class BERPrintableString extends BERCharacterString {
   public BERPrintableString(String var1) {
      this.m_value = var1;
   }

   public BERPrintableString(byte[] var1) {
      super(var1);
   }

   public BERPrintableString(BERTagDecoder var1, InputStream var2, int[] var3) throws IOException {
      super(var1, var2, var3);
   }

   public BERPrintableString(InputStream var1, int[] var2) throws IOException {
      super(var1, var2);
   }

   public int getType() {
      return 19;
   }

   public String toString() {
      return this.m_value == null ? "PrintableString (null)" : "PrintableString {" + this.m_value + "}";
   }
}
