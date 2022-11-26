package weblogic.j2ee.descriptor.wl;

import com.bea.logging.LoggingConfigValidator;

public class LoggingBeanValidator {
   public static void validateLogTimeString(String value) throws IllegalArgumentException {
      if (value != null && !value.equals("")) {
         if (!LoggingConfigValidator.isLogStartTimeValid("H:mm", value)) {
            throw new IllegalArgumentException("Illegal time string: " + value);
         }
      } else {
         String msg = "LogTimeString can't be null or empty string";
         throw new IllegalArgumentException(msg);
      }
   }
}
