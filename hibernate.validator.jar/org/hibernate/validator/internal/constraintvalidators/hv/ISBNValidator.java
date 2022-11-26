package org.hibernate.validator.internal.constraintvalidators.hv;

import java.util.function.Function;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.ISBN;

public class ISBNValidator implements ConstraintValidator {
   private static Pattern NOT_DIGITS_OR_NOT_X = Pattern.compile("[^\\dX]");
   private int length;
   private Function checkChecksumFunction;

   public void initialize(ISBN constraintAnnotation) {
      switch (constraintAnnotation.type()) {
         case ISBN_10:
            this.length = 10;
            this.checkChecksumFunction = this::checkChecksumISBN10;
            break;
         case ISBN_13:
            this.length = 13;
            this.checkChecksumFunction = this::checkChecksumISBN13;
      }

   }

   public boolean isValid(CharSequence isbn, ConstraintValidatorContext context) {
      if (isbn == null) {
         return true;
      } else {
         String digits = NOT_DIGITS_OR_NOT_X.matcher(isbn).replaceAll("");
         return digits.length() != this.length ? false : (Boolean)this.checkChecksumFunction.apply(digits);
      }
   }

   private boolean checkChecksumISBN10(String isbn) {
      int sum = 0;

      for(int i = 0; i < isbn.length() - 1; ++i) {
         sum += (isbn.charAt(i) - 48) * (i + 1);
      }

      char checkSum = isbn.charAt(9);
      return sum % 11 == (checkSum == 'X' ? 10 : checkSum - 48);
   }

   private boolean checkChecksumISBN13(String isbn) {
      int sum = 0;

      for(int i = 0; i < isbn.length() - 1; ++i) {
         sum += (isbn.charAt(i) - 48) * (i % 2 == 0 ? 1 : 3);
      }

      char checkSum = isbn.charAt(12);
      return 10 - sum % 10 == checkSum - 48;
   }
}
