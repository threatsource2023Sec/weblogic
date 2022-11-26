package org.hibernate.validator.internal.constraintvalidators.hv;

import java.util.List;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.constraints.LuhnCheck;
import org.hibernate.validator.internal.util.ModUtil;

public class LuhnCheckValidator extends ModCheckBase implements ConstraintValidator {
   public void initialize(LuhnCheck constraintAnnotation) {
      super.initialize(constraintAnnotation.startIndex(), constraintAnnotation.endIndex(), constraintAnnotation.checkDigitIndex(), constraintAnnotation.ignoreNonDigitCharacters());
   }

   public boolean isCheckDigitValid(List digits, char checkDigit) {
      int modResult = ModUtil.calculateLuhnMod10Check(digits);
      if (!Character.isDigit(checkDigit)) {
         return false;
      } else {
         int checkValue = this.extractDigit(checkDigit);
         return checkValue == modResult;
      }
   }
}
