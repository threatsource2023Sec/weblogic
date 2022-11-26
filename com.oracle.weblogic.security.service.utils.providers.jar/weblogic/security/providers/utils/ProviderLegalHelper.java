package weblogic.security.providers.utils;

public class ProviderLegalHelper {
   public static String checkNull(String value) {
      if (value != null && value.trim().length() != 0) {
         return value;
      } else {
         throw new IllegalArgumentException("The value can not be null or empty.");
      }
   }
}
