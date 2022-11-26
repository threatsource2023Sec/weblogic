package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;

public class BERVisibleString extends BERCharacterString {
   public BERVisibleString(String var1) {
      this.m_value = var1;
   }

   public BERVisibleString(byte[] var1) {
      super(var1);
   }

   public BERVisibleString(BERTagDecoder var1, InputStream var2, int[] var3) throws IOException {
      super(var1, var2, var3);
   }

   public BERVisibleString(InputStream var1, int[] var2) throws IOException {
      super(var1, var2);
   }

   public int getType() {
      return 26;
   }

   public String toString() {
      return this.m_value == null ? "VisibleString (null)" : "VisibleString {" + this.m_value + "}";
   }
}
