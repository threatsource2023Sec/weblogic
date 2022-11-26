package org.hibernate.validator.internal.constraintvalidators.bv;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class DecimalMinValidatorForCharSequence implements ConstraintValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private BigDecimal minValue;
   private boolean inclusive;

   public void initialize(DecimalMin minValue) {
      try {
         this.minValue = new BigDecimal(minValue.value());
      } catch (NumberFormatException var3) {
         throw LOG.getInvalidBigDecimalFormatException(minValue.value(), var3);
      }

      this.inclusive = minValue.inclusive();
   }

   public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
      if (value == null) {
         return true;
      } else {
         try {
            int comparisonResult = (new BigDecimal(value.toString())).compareTo(this.minValue);
            return this.inclusive ? comparisonResult >= 0 : comparisonResult > 0;
         } catch (NumberFormatException var4) {
            return false;
         }
      }
   }
}
