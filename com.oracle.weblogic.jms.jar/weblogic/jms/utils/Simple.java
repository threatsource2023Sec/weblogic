package weblogic.jms.utils;

import java.util.Locale;

public class Simple {
   public static String getenv(String var) {
      String result = System.getProperty(var);
      if (result != null) {
         return result;
      } else {
         try {
            result = System.getenv(var.replace('.', '_').toUpperCase(Locale.ENGLISH));
         } catch (SecurityException var3) {
         }

         return result;
      }
   }
}
