package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;

public class DecimalMinValidatorForDouble extends AbstractDecimalMinValidator {
   protected int compare(Double number) {
      return DecimalNumberComparatorHelper.compare(number, this.minValue, InfinityNumberComparatorHelper.LESS_THAN);
   }
}
