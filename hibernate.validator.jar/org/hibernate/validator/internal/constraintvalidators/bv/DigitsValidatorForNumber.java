package org.hibernate.validator.internal.constraintvalidators.bv;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Digits;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class DigitsValidatorForNumber implements ConstraintValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private int maxIntegerLength;
   private int maxFractionLength;

   public void initialize(Digits constraintAnnotation) {
      this.maxIntegerLength = constraintAnnotation.integer();
      this.maxFractionLength = constraintAnnotation.fraction();
      this.validateParameters();
   }

   public boolean isValid(Number num, ConstraintValidatorContext constraintValidatorContext) {
      if (num == null) {
         return true;
      } else {
         BigDecimal bigNum;
         if (num instanceof BigDecimal) {
            bigNum = (BigDecimal)num;
         } else {
            bigNum = (new BigDecimal(num.toString())).stripTrailingZeros();
         }

         int integerPartLength = bigNum.precision() - bigNum.scale();
         int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();
         return this.maxIntegerLength >= integerPartLength && this.maxFractionLength >= fractionPartLength;
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
