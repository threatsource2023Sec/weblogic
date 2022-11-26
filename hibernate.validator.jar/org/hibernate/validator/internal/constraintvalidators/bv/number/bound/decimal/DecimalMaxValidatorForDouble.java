package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;

public class DecimalMaxValidatorForDouble extends AbstractDecimalMaxValidator {
   protected int compare(Double number) {
      return DecimalNumberComparatorHelper.compare(number, this.maxValue, InfinityNumberComparatorHelper.GREATER_THAN);
   }
}
