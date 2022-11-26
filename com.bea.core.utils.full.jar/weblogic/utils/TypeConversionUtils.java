package weblogic.utils;

import java.util.Dictionary;
import java.util.Map;

public final class TypeConversionUtils {
   private TypeConversionUtils() {
   }

   public static Dictionary stringToDictionary(String s, Dictionary d) {
      return stringToDictionary(s, d, ",");
   }

   public static Map stringToMap(String s, Map map) {
      return stringToMap(s, map, ",");
   }

   public static Dictionary stringToDictionary(String s, Dictionary d, String sep) {
      boolean escaped = false;
      if (s == null) {
         return d;
      } else {
         String[] st;
         int tok;
         String[] nvp;
         if (s.indexOf("=\"") == -1) {
            st = StringUtils.splitCompletely(s, sep);

            for(tok = st.length - 1; tok > -1; --tok) {
               nvp = StringUtils.split(st[tok], '=');
               d.put(nvp[0], nvp[1]);
            }
         } else {
            st = StringUtils.splitCompletely(s, sep, true);

            for(tok = st.length - 1; tok > -1; tok -= 2) {
               String tk;
               if (!st[tok].endsWith("\"")) {
                  tk = st[tok];
                  escaped = false;
               } else {
                  String tkbuf;
                  for(tkbuf = st[tok]; st[tok].indexOf("=\"") == -1; tkbuf = st[tok] + st[tok + 1] + tkbuf) {
                     tok -= 2;
                  }

                  tk = tkbuf;
                  escaped = true;
               }

               nvp = StringUtils.split(tk, '=');
               if (escaped) {
                  d.put(nvp[0], nvp[1].substring(1, nvp[1].length() - 1));
               } else {
                  d.put(nvp[0], nvp[1]);
               }
            }
         }

         return d;
      }
   }

   public static Map stringToMap(String s, Map map, String sep) {
      boolean escaped = false;
      if (s == null) {
         return map;
      } else {
         String[] st;
         int tok;
         String[] nvp;
         if (s.indexOf("=\"") == -1) {
            st = StringUtils.splitCompletely(s, sep);

            for(tok = st.length - 1; tok > -1; --tok) {
               nvp = StringUtils.split(st[tok], '=');
               map.put(nvp[0], nvp[1]);
            }
         } else {
            st = StringUtils.splitCompletely(s, sep, true);

            for(tok = st.length - 1; tok > -1; tok -= 2) {
               String tk;
               if (!st[tok].endsWith("\"")) {
                  tk = st[tok];
                  escaped = false;
               } else {
                  String tkbuf;
                  for(tkbuf = st[tok]; st[tok].indexOf("=\"") == -1; tkbuf = st[tok] + st[tok + 1] + tkbuf) {
                     tok -= 2;
                  }

                  tk = tkbuf;
                  escaped = true;
               }

               nvp = StringUtils.split(tk, '=');
               if (escaped) {
                  map.put(nvp[0], nvp[1].substring(1, nvp[1].length() - 1));
               } else {
                  map.put(nvp[0], nvp[1]);
               }
            }
         }

         return map;
      }
   }
}
