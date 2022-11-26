package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;

public class DecimalMaxValidatorForFloat extends AbstractDecimalMaxValidator {
   protected int compare(Float number) {
      return DecimalNumberComparatorHelper.compare(number, this.maxValue, InfinityNumberComparatorHelper.GREATER_THAN);
   }
}
