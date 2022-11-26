package org.hibernate.validator.internal.constraintvalidators.bv;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.DecimalMax;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class DecimalMaxValidatorForCharSequence implements ConstraintValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private BigDecimal maxValue;
   private boolean inclusive;

   public void initialize(DecimalMax maxValue) {
      try {
         this.maxValue = new BigDecimal(maxValue.value());
      } catch (NumberFormatException var3) {
         throw LOG.getInvalidBigDecimalFormatException(maxValue.value(), var3);
      }

      this.inclusive = maxValue.inclusive();
   }

   public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
      if (value == null) {
         return true;
      } else {
         try {
            int comparisonResult = (new BigDecimal(value.toString())).compareTo(this.maxValue);
            return this.inclusive ? comparisonResult <= 0 : comparisonResult < 0;
         } catch (NumberFormatException var4) {
            return false;
         }
      }
   }
}
