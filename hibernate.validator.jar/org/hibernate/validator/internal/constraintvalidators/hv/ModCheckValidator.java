package org.hibernate.validator.internal.constraintvalidators.hv;

import java.util.List;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.constraints.ModCheck;
import org.hibernate.validator.internal.util.ModUtil;

/** @deprecated */
@Deprecated
public class ModCheckValidator extends ModCheckBase implements ConstraintValidator {
   private int multiplier;
   private ModCheck.ModType modType;

   public void initialize(ModCheck constraintAnnotation) {
      super.initialize(constraintAnnotation.startIndex(), constraintAnnotation.endIndex(), constraintAnnotation.checkDigitPosition(), constraintAnnotation.ignoreNonDigitCharacters());
      this.modType = constraintAnnotation.modType();
      this.multiplier = constraintAnnotation.multiplier();
   }

   public boolean isCheckDigitValid(List digits, char checkDigit) {
      int modResult = true;
      int checkValue = this.extractDigit(checkDigit);
      int modResult;
      if (this.modType.equals(ModCheck.ModType.MOD11)) {
         modResult = ModUtil.calculateMod11Check(digits, this.multiplier);
         if (modResult == 10 || modResult == 11) {
            modResult = 0;
         }
      } else {
         modResult = ModUtil.calculateLuhnMod10Check(digits);
      }

      return checkValue == modResult;
   }
}
