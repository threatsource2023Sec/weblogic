package org.glassfish.grizzly.http.server;

import java.util.Locale;

class LegacyLocaleParser implements LocaleParser {
   public Locale parseLocale(String source) {
      int dash = source.indexOf(45);
      String language;
      String country;
      String variant;
      if (dash < 0) {
         language = source;
         country = "";
         variant = "";
      } else {
         language = source.substring(0, dash);
         country = source.substring(dash + 1);
         int vDash = country.indexOf(45);
         if (vDash > 0) {
            String cTemp = country.substring(0, vDash);
            variant = country.substring(vDash + 1);
            country = cTemp;
         } else {
            variant = "";
         }
      }

      return isAlpha(language) && isAlpha(country) && isAlpha(variant) ? new Locale(language, country, variant) : null;
   }

   private static boolean isAlpha(String value) {
      if (value == null) {
         return false;
      } else {
         for(int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
               return false;
            }
         }

         return true;
      }
   }
}
