package org.hibernate.validator.internal.constraintvalidators.hv;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class ModCheckBase {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final Pattern NUMBERS_ONLY_REGEXP = Pattern.compile("[^0-9]");
   private static final int DEC_RADIX = 10;
   private int startIndex;
   private int endIndex;
   private int checkDigitIndex;
   private boolean ignoreNonDigitCharacters;

   public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         String valueAsString = value.toString();

         String digitsAsString;
         char checkDigit;
         try {
            digitsAsString = this.extractVerificationString(valueAsString);
            checkDigit = this.extractCheckDigit(valueAsString);
         } catch (IndexOutOfBoundsException var9) {
            return false;
         }

         digitsAsString = this.stripNonDigitsIfRequired(digitsAsString);

         List digits;
         try {
            digits = this.extractDigits(digitsAsString);
         } catch (NumberFormatException var8) {
            return false;
         }

         return this.isCheckDigitValid(digits, checkDigit);
      }
   }

   public abstract boolean isCheckDigitValid(List var1, char var2);

   protected void initialize(int startIndex, int endIndex, int checkDigitIndex, boolean ignoreNonDigitCharacters) {
      this.startIndex = startIndex;
      this.endIndex = endIndex;
      this.checkDigitIndex = checkDigitIndex;
      this.ignoreNonDigitCharacters = ignoreNonDigitCharacters;
      this.validateOptions();
   }

   protected int extractDigit(char value) throws NumberFormatException {
      if (Character.isDigit(value)) {
         return Character.digit(value, 10);
      } else {
         throw LOG.getCharacterIsNotADigitException(value);
      }
   }

   private List extractDigits(String value) throws NumberFormatException {
      List digits = new ArrayList(value.length());
      char[] chars = value.toCharArray();
      char[] var4 = chars;
      int var5 = chars.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         char c = var4[var6];
         digits.add(this.extractDigit(c));
      }

      return digits;
   }

   private boolean validateOptions() {
      if (this.startIndex < 0) {
         throw LOG.getStartIndexCannotBeNegativeException(this.startIndex);
      } else if (this.endIndex < 0) {
         throw LOG.getEndIndexCannotBeNegativeException(this.endIndex);
      } else if (this.startIndex > this.endIndex) {
         throw LOG.getInvalidRangeException(this.startIndex, this.endIndex);
      } else if (this.checkDigitIndex > 0 && this.startIndex <= this.checkDigitIndex && this.endIndex > this.checkDigitIndex) {
         throw LOG.getInvalidCheckDigitException(this.startIndex, this.endIndex);
      } else {
         return true;
      }
   }

   private String stripNonDigitsIfRequired(String value) {
      return this.ignoreNonDigitCharacters ? NUMBERS_ONLY_REGEXP.matcher(value).replaceAll("") : value;
   }

   private String extractVerificationString(String value) throws IndexOutOfBoundsException {
      if (this.endIndex == Integer.MAX_VALUE) {
         return value.substring(0, value.length() - 1);
      } else {
         return this.checkDigitIndex == -1 ? value.substring(this.startIndex, this.endIndex) : value.substring(this.startIndex, this.endIndex + 1);
      }
   }

   private char extractCheckDigit(String value) throws IndexOutOfBoundsException {
      if (this.checkDigitIndex == -1) {
         return this.endIndex == Integer.MAX_VALUE ? value.charAt(value.length() - 1) : value.charAt(this.endIndex);
      } else {
         return value.charAt(this.checkDigitIndex);
      }
   }
}
