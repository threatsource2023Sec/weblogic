package weblogic.management.configuration;

import com.bea.logging.LoggingConfigValidator;

public class LoggingLegalHelper {
   /** @deprecated */
   @Deprecated
   public static boolean isLogStartTimeValid(String format, String time) {
      return LoggingConfigValidator.isLogStartTimeValid(format, time);
   }

   public static void validateLogTimeString(String value) throws IllegalArgumentException {
      if (value != null && !value.equals("")) {
         if (!isLogStartTimeValid("H:mm", value)) {
            throw new IllegalArgumentException("Illegal time string: " + value);
         }
      } else {
         String msg = "LogTimeString can't be null or empty string";
         throw new IllegalArgumentException(msg);
      }
   }

   public static void validateWebServerLogRotationTimeBegin(String value) throws IllegalArgumentException {
      if (value != null && !value.equals("")) {
         if (!isLogStartTimeValid("MM-dd-yyyy-k:mm:ss", value) && !isLogStartTimeValid("H:mm", value)) {
            throw new IllegalArgumentException("Illegal time string: " + value);
         }
      }
   }
}
