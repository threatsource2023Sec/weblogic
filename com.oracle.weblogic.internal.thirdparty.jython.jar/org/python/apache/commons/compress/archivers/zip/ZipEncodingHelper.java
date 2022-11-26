package org.python.apache.commons.compress.archivers.zip;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.python.apache.commons.compress.utils.Charsets;

public abstract class ZipEncodingHelper {
   private static final Map simpleEncodings;
   private static final byte[] HEX_DIGITS;
   static final String UTF8 = "UTF8";
   static final ZipEncoding UTF8_ZIP_ENCODING;

   static ByteBuffer growBuffer(ByteBuffer b, int newCapacity) {
      b.limit(b.position());
      b.rewind();
      int c2 = b.capacity() * 2;
      ByteBuffer on = ByteBuffer.allocate(c2 < newCapacity ? newCapacity : c2);
      on.put(b);
      return on;
   }

   static void appendSurrogate(ByteBuffer bb, char c) {
      bb.put((byte)37);
      bb.put((byte)85);
      bb.put(HEX_DIGITS[c >> 12 & 15]);
      bb.put(HEX_DIGITS[c >> 8 & 15]);
      bb.put(HEX_DIGITS[c >> 4 & 15]);
      bb.put(HEX_DIGITS[c & 15]);
   }

   public static ZipEncoding getZipEncoding(String name) {
      if (isUTF8(name)) {
         return UTF8_ZIP_ENCODING;
      } else if (name == null) {
         return new FallbackZipEncoding();
      } else {
         SimpleEncodingHolder h = (SimpleEncodingHolder)simpleEncodings.get(name);
         if (h != null) {
            return h.getEncoding();
         } else {
            try {
               Charset cs = Charset.forName(name);
               return new NioZipEncoding(cs);
            } catch (UnsupportedCharsetException var3) {
               return new FallbackZipEncoding(name);
            }
         }
      }
   }

   static boolean isUTF8(String charsetName) {
      if (charsetName == null) {
         charsetName = Charset.defaultCharset().name();
      }

      if (Charsets.UTF_8.name().equalsIgnoreCase(charsetName)) {
         return true;
      } else {
         Iterator var1 = Charsets.UTF_8.aliases().iterator();

         String alias;
         do {
            if (!var1.hasNext()) {
               return false;
            }

            alias = (String)var1.next();
         } while(!alias.equalsIgnoreCase(charsetName));

         return true;
      }
   }

   static {
      Map se = new HashMap();
      char[] cp437_high_chars = new char[]{'Ç', 'ü', 'é', 'â', 'ä', 'à', 'å', 'ç', 'ê', 'ë', 'è', 'ï', 'î', 'ì', 'Ä', 'Å', 'É', 'æ', 'Æ', 'ô', 'ö', 'ò', 'û', 'ù', 'ÿ', 'Ö', 'Ü', '¢', '£', '¥', '₧', 'ƒ', 'á', 'í', 'ó', 'ú', 'ñ', 'Ñ', 'ª', 'º', '¿', '⌐', '¬', '½', '¼', '¡', '«', '»', '░', '▒', '▓', '│', '┤', '╡', '╢', '╖', '╕', '╣', '║', '╗', '╝', '╜', '╛', '┐', '└', '┴', '┬', '├', '─', '┼', '╞', '╟', '╚', '╔', '╩', '╦', '╠', '═', '╬', '╧', '╨', '╤', '╥', '╙', '╘', '╒', '╓', '╫', '╪', '┘', '┌', '█', '▄', '▌', '▐', '▀', 'α', 'ß', 'Γ', 'π', 'Σ', 'σ', 'µ', 'τ', 'Φ', 'Θ', 'Ω', 'δ', '∞', 'φ', 'ε', '∩', '≡', '±', '≥', '≤', '⌠', '⌡', '÷', '≈', '°', '∙', '·', '√', 'ⁿ', '²', '■', ' '};
      SimpleEncodingHolder cp437 = new SimpleEncodingHolder(cp437_high_chars);
      se.put("CP437", cp437);
      se.put("Cp437", cp437);
      se.put("cp437", cp437);
      se.put("IBM437", cp437);
      se.put("ibm437", cp437);
      char[] cp850_high_chars = new char[]{'Ç', 'ü', 'é', 'â', 'ä', 'à', 'å', 'ç', 'ê', 'ë', 'è', 'ï', 'î', 'ì', 'Ä', 'Å', 'É', 'æ', 'Æ', 'ô', 'ö', 'ò', 'û', 'ù', 'ÿ', 'Ö', 'Ü', 'ø', '£', 'Ø', '×', 'ƒ', 'á', 'í', 'ó', 'ú', 'ñ', 'Ñ', 'ª', 'º', '¿', '®', '¬', '½', '¼', '¡', '«', '»', '░', '▒', '▓', '│', '┤', 'Á', 'Â', 'À', '©', '╣', '║', '╗', '╝', '¢', '¥', '┐', '└', '┴', '┬', '├', '─', '┼', 'ã', 'Ã', '╚', '╔', '╩', '╦', '╠', '═', '╬', '¤', 'ð', 'Ð', 'Ê', 'Ë', 'È', 'ı', 'Í', 'Î', 'Ï', '┘', '┌', '█', '▄', '¦', 'Ì', '▀', 'Ó', 'ß', 'Ô', 'Ò', 'õ', 'Õ', 'µ', 'þ', 'Þ', 'Ú', 'Û', 'Ù', 'ý', 'Ý', '¯', '´', '\u00ad', '±', '‗', '¾', '¶', '§', '÷', '¸', '°', '¨', '·', '¹', '³', '²', '■', ' '};
      SimpleEncodingHolder cp850 = new SimpleEncodingHolder(cp850_high_chars);
      se.put("CP850", cp850);
      se.put("Cp850", cp850);
      se.put("cp850", cp850);
      se.put("IBM850", cp850);
      se.put("ibm850", cp850);
      simpleEncodings = Collections.unmodifiableMap(se);
      HEX_DIGITS = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
      UTF8_ZIP_ENCODING = new FallbackZipEncoding("UTF8");
   }

   private static class SimpleEncodingHolder {
      private final char[] highChars;
      private Simple8BitZipEncoding encoding;

      SimpleEncodingHolder(char[] highChars) {
         this.highChars = highChars;
      }

      public synchronized Simple8BitZipEncoding getEncoding() {
         if (this.encoding == null) {
            this.encoding = new Simple8BitZipEncoding(this.highChars);
         }

         return this.encoding;
      }
   }
}
