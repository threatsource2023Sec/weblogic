package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;

public class MaxValidatorForNumber extends AbstractMaxValidator {
   protected int compare(Number number) {
      return NumberComparatorHelper.compare(number, this.maxValue, InfinityNumberComparatorHelper.GREATER_THAN);
   }
}
