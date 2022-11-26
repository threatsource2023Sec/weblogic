package org.hibernate.validator.internal.constraintvalidators.hv.pl;

import java.util.Collections;
import java.util.List;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.ModCheckBase;
import org.hibernate.validator.internal.util.ModUtil;

public abstract class PolishNumberValidator extends ModCheckBase implements ConstraintValidator {
   public boolean isCheckDigitValid(List digits, char checkDigit) {
      Collections.reverse(digits);
      int modResult = 11 - ModUtil.calculateModXCheckWithWeights(digits, 11, Integer.MAX_VALUE, this.getWeights(digits));
      switch (modResult) {
         case 10:
         case 11:
            return checkDigit == '0';
         default:
            return Character.isDigit(checkDigit) && modResult == this.extractDigit(checkDigit);
      }
   }

   protected abstract int[] getWeights(List var1);
}
