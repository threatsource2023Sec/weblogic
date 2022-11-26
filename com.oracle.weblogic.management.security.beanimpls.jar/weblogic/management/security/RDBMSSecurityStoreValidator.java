package weblogic.management.security;

import javax.management.InvalidAttributeValueException;

public class RDBMSSecurityStoreValidator {
   private static final String STRING_DELIMITER = ",";
   private static final String PROPERTY_DELIMITER = "=";

   public static void validateProperties(String value) throws InvalidAttributeValueException {
      if (value != null) {
         boolean isValid = true;
         String[] subStrings;
         if (value.indexOf(",") < 0) {
            subStrings = new String[]{value};
         } else {
            subStrings = value.split(",");
         }

         for(int i = 0; i < subStrings.length; ++i) {
            String aString = subStrings[i].trim();
            if (aString.length() > 0) {
               int index = aString.indexOf("=");
               if (index < 0) {
                  isValid = false;
                  break;
               }

               String propKey = aString.substring(0, index).trim();
               String propValue = aString.substring(index + 1, aString.length()).trim();
               if (propKey.length() < 1 || propValue.length() < 1) {
                  isValid = false;
                  break;
               }

               if (!propKey.matches("[a-zA-Z0-9._]+")) {
                  isValid = false;
                  break;
               }
            }
         }

         if (!isValid) {
            throw new InvalidAttributeValueException("Illegal value for Properties: " + value);
         }
      }
   }
}
