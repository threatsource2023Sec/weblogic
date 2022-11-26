package weblogic.management.configuration;

import java.util.regex.Pattern;

public class NMPasswordValidator {
   public static void validatePassword(String s) throws IllegalArgumentException {
      boolean containsDigitOrSpecialChar = Pattern.compile("[^a-zA-Z]").matcher(s).find();
      if (s.length() < 8 || !containsDigitOrSpecialChar) {
         throw new IllegalArgumentException("The password must be at least 8 alphanumeric characters with at least one number or special character.");
      }
   }
}
