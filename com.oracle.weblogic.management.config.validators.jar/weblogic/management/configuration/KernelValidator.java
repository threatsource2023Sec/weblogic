package weblogic.management.configuration;

public class KernelValidator {
   public static void validateMaxCommMessageSize(int value) {
      if (value != -1 && (value < 4096 || value > 2000000000)) {
         throw new IllegalArgumentException("Illegal value for MaxCOMMMessageSize: " + value);
      }
   }

   public static void validateMaxHTTPMessageSize(int value) {
      if (value != -1 && (value < 4096 || value > 2000000000)) {
         throw new IllegalArgumentException("Illegal value for MaxHTTPMessageSize: " + value);
      }
   }

   public static void validateMaxIIOPMessageSize(int value) {
      if (value != -1 && (value < 4096 || value > 2000000000)) {
         throw new IllegalArgumentException("Illegal value for MaxIIOPMessageSize: " + value);
      }
   }

   public static void validateMaxT3MessageSize(int value) {
      if (value != -1 && (value < 4096 || value > 2000000000)) {
         throw new IllegalArgumentException("Illegal value for MaxT3MessageSize: " + value);
      }
   }
}
