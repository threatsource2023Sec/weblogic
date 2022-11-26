package weblogic.management.configuration;

public class NetworkChannelValidator {
   public static void validateName(String name) throws IllegalArgumentException {
      if (name != null && name.trim().length() != 0) {
         if (name.startsWith(".WL")) {
            throw new IllegalArgumentException("Name may not start with .WL");
         } else if (name.equals("Default") || name.equals("Administrator")) {
            throw new IllegalArgumentException("Name may not be 'Default' or 'Administrator'");
         }
      } else {
         throw new IllegalArgumentException("Name may not be null or empty string");
      }
   }
}
