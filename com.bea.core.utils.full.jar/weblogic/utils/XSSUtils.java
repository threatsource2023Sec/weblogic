package weblogic.utils;

public class XSSUtils {
   private static final String[] xssStrings = new String[]{"&quot;", null, null, "&#37;", "&amp;", "&#39;", "&#40;", "&#41;", null, "&#43;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&#59;", "&lt;", null, "&gt;"};
   private static final int BEGIN = 34;

   public static String encodeXSS(String text) {
      if (text == null) {
         return null;
      } else {
         int len = text.length();
         if (len < 1) {
            return text;
         } else {
            boolean foundXSSChar = false;
            StringBuilder buf = null;

            for(int i = 0; i < len; ++i) {
               char ch = text.charAt(i);
               int pos = ch - 34;
               if (pos > -1 && pos < xssStrings.length && xssStrings[pos] != null) {
                  if (!foundXSSChar) {
                     buf = new StringBuilder(len);
                     buf.append(text.substring(0, i));
                  }

                  buf.append(xssStrings[pos]);
                  foundXSSChar = true;
               } else if (foundXSSChar) {
                  buf.append(ch);
               }
            }

            if (!foundXSSChar) {
               return text;
            } else {
               return buf.toString();
            }
         }
      }
   }
}
