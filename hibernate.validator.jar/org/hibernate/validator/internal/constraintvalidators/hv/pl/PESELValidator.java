package org.hibernate.validator.internal.constraintvalidators.hv.pl;

import java.util.Collections;
import java.util.List;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.constraints.pl.PESEL;
import org.hibernate.validator.internal.constraintvalidators.hv.ModCheckBase;
import org.hibernate.validator.internal.util.ModUtil;

public class PESELValidator extends ModCheckBase implements ConstraintValidator {
   private static final int[] WEIGHTS_PESEL = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

   public void initialize(PESEL constraintAnnotation) {
      super.initialize(0, Integer.MAX_VALUE, -1, true);
   }

   public boolean isCheckDigitValid(List digits, char checkDigit) {
      Collections.reverse(digits);
      int modResult = ModUtil.calculateModXCheckWithWeights(digits, 10, Integer.MAX_VALUE, WEIGHTS_PESEL);
      switch (modResult) {
         case 10:
            return checkDigit == '0';
         default:
            return Character.isDigit(checkDigit) && modResult == this.extractDigit(checkDigit);
      }
   }
}
