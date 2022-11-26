package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import java.math.BigDecimal;

public class DecimalMinValidatorForBigDecimal extends AbstractDecimalMinValidator {
   protected int compare(BigDecimal number) {
      return DecimalNumberComparatorHelper.compare(number, this.minValue);
   }
}
