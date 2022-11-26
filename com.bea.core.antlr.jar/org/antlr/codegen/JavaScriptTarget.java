package org.antlr.codegen;

public class JavaScriptTarget extends Target {
   public String encodeIntAsCharEscape(int v) {
      String hex = Integer.toHexString(v | 65536).substring(1, 5);
      return "\\u" + hex;
   }

   public String getTarget64BitStringFromValue(long word) {
      StringBuffer buf = new StringBuffer(22);
      buf.append("0x");
      this.writeHexWithPadding(buf, Integer.toHexString((int)(word & 4294967295L)));
      buf.append(", 0x");
      this.writeHexWithPadding(buf, Integer.toHexString((int)(word >> 32)));
      return buf.toString();
   }

   private void writeHexWithPadding(StringBuffer buf, String digits) {
      digits = digits.toUpperCase();
      int padding = 8 - digits.length();

      for(int i = 1; i <= padding; ++i) {
         buf.append('0');
      }

      buf.append(digits);
   }
}
