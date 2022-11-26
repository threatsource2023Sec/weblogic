package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;

public class DecimalMinValidatorForFloat extends AbstractDecimalMinValidator {
   protected int compare(Float number) {
      return DecimalNumberComparatorHelper.compare(number, this.minValue, InfinityNumberComparatorHelper.LESS_THAN);
   }
}
