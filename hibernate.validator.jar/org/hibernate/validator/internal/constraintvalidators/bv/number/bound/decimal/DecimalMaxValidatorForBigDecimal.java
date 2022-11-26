package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import java.math.BigDecimal;

public class DecimalMaxValidatorForBigDecimal extends AbstractDecimalMaxValidator {
   protected int compare(BigDecimal number) {
      return DecimalNumberComparatorHelper.compare(number, this.maxValue);
   }
}
