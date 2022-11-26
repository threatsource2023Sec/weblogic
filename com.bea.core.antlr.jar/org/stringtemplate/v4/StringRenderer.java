package org.stringtemplate.v4;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

public class StringRenderer implements AttributeRenderer {
   public String toString(Object o, String formatString, Locale locale) {
      String s = (String)o;
      if (formatString == null) {
         return s;
      } else if (formatString.equals("upper")) {
         return s.toUpperCase(locale);
      } else if (formatString.equals("lower")) {
         return s.toLowerCase(locale);
      } else if (formatString.equals("cap")) {
         return s.length() > 0 ? Character.toUpperCase(s.charAt(0)) + s.substring(1) : s;
      } else {
         if (formatString.equals("url-encode")) {
            try {
               return URLEncoder.encode(s, "UTF-8");
            } catch (UnsupportedEncodingException var6) {
            }
         }

         return formatString.equals("xml-encode") ? escapeHTML(s) : String.format(locale, formatString, s);
      }
   }

   public static String escapeHTML(String s) {
      if (s == null) {
         return null;
      } else {
         StringBuilder buf = new StringBuilder(s.length());
         int len = s.length();

         for(int i = 0; i < len; ++i) {
            char c = s.charAt(i);
            switch (c) {
               case '\t':
               case '\n':
               case '\r':
                  buf.append(c);
                  break;
               case '&':
                  buf.append("&amp;");
                  break;
               case '<':
                  buf.append("&lt;");
                  break;
               case '>':
                  buf.append("&gt;");
                  break;
               default:
                  boolean control = c < ' ';
                  boolean aboveASCII = c > '~';
                  if (!control && !aboveASCII) {
                     buf.append(c);
                  } else {
                     buf.append("&#");
                     buf.append(c);
                     buf.append(";");
                  }
            }
         }

         return buf.toString();
      }
   }
}
