package org.hibernate.validator.internal.constraintvalidators.bv;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Digits;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class DigitsValidatorForCharSequence implements ConstraintValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private int maxIntegerLength;
   private int maxFractionLength;

   public void initialize(Digits constraintAnnotation) {
      this.maxIntegerLength = constraintAnnotation.integer();
      this.maxFractionLength = constraintAnnotation.fraction();
      this.validateParameters();
   }

   public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
      if (charSequence == null) {
         return true;
      } else {
         BigDecimal bigNum = this.getBigDecimalValue(charSequence);
         if (bigNum == null) {
            return false;
         } else {
            int integerPartLength = bigNum.precision() - bigNum.scale();
            int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();
            return this.maxIntegerLength >= integerPartLength && this.maxFractionLength >= fractionPartLength;
         }
      }
   }

   private BigDecimal getBigDecimalValue(CharSequence charSequence) {
      try {
         BigDecimal bd = new BigDecimal(charSequence.toString());
         return bd;
      } catch (NumberFormatException var4) {
         return null;
      }
   }

   private void validateParameters() {
      if (this.maxIntegerLength < 0) {
         throw LOG.getInvalidLengthForIntegerPartException();
      } else if (this.maxFractionLength < 0) {
         throw LOG.getInvalidLengthForFractionPartException();
      }
   }
}
