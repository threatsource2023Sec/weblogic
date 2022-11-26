package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import java.math.BigDecimal;

public class MaxValidatorForBigDecimal extends AbstractMaxValidator {
   protected int compare(BigDecimal number) {
      return NumberComparatorHelper.compare(number, this.maxValue);
   }
}
