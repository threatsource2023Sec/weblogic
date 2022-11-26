package com.bea.security.providers.xacml;

public class XMLEscaper {
   private static final char[] LT = "&lt;".toCharArray();
   private static final char[] GT = "&gt;".toCharArray();
   private static final char[] AMP = "&amp;".toCharArray();
   private static final char[] APOS = "&apos;".toCharArray();
   private static final char[] QUOTE = "&quot;".toCharArray();

   public static String escapeXMLChars(String data) {
      String result = data;
      if (data != null) {
         int k = 0;
         char[] name = data.toCharArray();
         char[] out = new char[name.length * QUOTE.length];

         for(int i = 0; i < name.length; ++i) {
            switch (name[i]) {
               case '"':
                  System.arraycopy(QUOTE, 0, out, k, QUOTE.length);
                  k += QUOTE.length;
                  break;
               case '&':
                  System.arraycopy(AMP, 0, out, k, AMP.length);
                  k += AMP.length;
                  break;
               case '\'':
                  System.arraycopy(APOS, 0, out, k, APOS.length);
                  k += APOS.length;
                  break;
               case '<':
                  System.arraycopy(LT, 0, out, k, LT.length);
                  k += LT.length;
                  break;
               case '>':
                  System.arraycopy(GT, 0, out, k, GT.length);
                  k += GT.length;
                  break;
               default:
                  out[k++] = name[i];
            }
         }

         if (k != name.length) {
            result = new String(out, 0, k);
         }
      }

      return result;
   }

   public static String unescapeXMLChars(String data) {
      String result = data;
      if (data != null) {
         int i = 0;
         int k = 0;
         char[] name = data.toCharArray();
         char[] out = new char[name.length];

         while(i < name.length) {
            if (name[i] == '&') {
               switch (name[i + 1]) {
                  case 'a':
                     if (name[i + 2] == 'm') {
                        out[k++] = '&';
                        i += AMP.length;
                     } else {
                        out[k++] = '\'';
                        i += APOS.length;
                     }
                     break;
                  case 'g':
                     out[k++] = '>';
                     i += GT.length;
                     break;
                  case 'l':
                     out[k++] = '<';
                     i += LT.length;
                     break;
                  case 'q':
                     out[k++] = '"';
                     i += QUOTE.length;
                     break;
                  default:
                     out[k++] = name[i++];
               }
            } else {
               out[k++] = name[i++];
            }
         }

         if (k != name.length) {
            result = new String(out, 0, k);
         }
      }

      return result;
   }
}
