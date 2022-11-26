package weblogic.management.configuration;

public class IIOPValidator {
   public static void validateMaxMessageSize(int value) {
      if (value != -1 && (value < 4096 || value > 2000000000)) {
         throw new IllegalArgumentException("Illegal value for MaxMessageSize: " + value);
      }
   }
}
