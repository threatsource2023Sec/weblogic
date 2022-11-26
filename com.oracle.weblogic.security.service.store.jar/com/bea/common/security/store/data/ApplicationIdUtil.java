package com.bea.common.security.store.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public abstract class ApplicationIdUtil {
   private ApplicationIdUtil() {
   }

   static String encode(String value) {
      if (value != null && !value.equals("")) {
         StringTokenizer st = new StringTokenizer(value, ",=", true);

         StringBuilder result;
         String token;
         for(result = new StringBuilder(); st.hasMoreTokens(); result.append(token)) {
            token = st.nextToken();
            if (token.charAt(0) == ',' || token.charAt(0) == '=') {
               result.append('\\');
            }
         }

         return result.toString();
      } else {
         return "";
      }
   }

   static Map parse(String binding) {
      Map result = new LinkedHashMap();
      StringTokenizer st = new StringTokenizer(binding, ",");
      StringBuilder component = new StringBuilder();
      boolean isAppend = false;

      while(true) {
         while(st.hasMoreTokens()) {
            String token = st.nextToken();
            if (isAppend) {
               component.append(token);
               isAppend = false;
            } else if (component.length() > 0) {
               component.replace(0, component.length() - 1, token);
            } else {
               component.append(token);
            }

            if (token.charAt(token.length() - 1) == '\\' && st.hasMoreTokens()) {
               isAppend = true;
            } else {
               parseNameValue(result, component.toString());
            }
         }

         return result;
      }
   }

   private static void parseNameValue(Map result, String component) {
      StringBuilder name = null;
      StringBuilder value = null;
      int idx = 0;

      int ci;
      while(true) {
         ci = component.indexOf(61, idx);
         if (ci > 0) {
            if (component.charAt(ci - 1) == '\\') {
               idx = ci + 1;
               continue;
            }

            name = new StringBuilder(component.substring(0, ci));
            if (ci + 1 < component.length()) {
               value = new StringBuilder(component.substring(ci + 1));
            }
            break;
         }

         if (ci == 0) {
            if (component.length() > 1) {
               value = new StringBuilder(component.substring(1));
            }
         } else {
            name = new StringBuilder(component);
         }
         break;
      }

      int ei;
      if (name != null) {
         for(ci = 0; (ci = name.indexOf("\\,", ci)) >= 0; ++ci) {
            name.replace(ci, ci + 1, ",");
         }

         for(ei = 0; (ei = name.indexOf("\\=", ei)) >= 0; ++ei) {
            name.replace(ei, ei + 1, ",");
         }
      }

      if (value != null) {
         for(ci = 0; (ci = value.indexOf("\\,", ci)) >= 0; ++ci) {
            value.replace(ci, ci + 1, ",");
         }

         for(ei = 0; (ei = value.indexOf("\\=", ei)) >= 0; ++ei) {
            value.replace(ei, ei + 1, ",");
         }
      }

      result.put(name != null ? name.toString() : null, value != null ? value.toString() : null);
   }
}
