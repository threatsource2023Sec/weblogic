package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;

public class MinValidatorForDouble extends AbstractMinValidator {
   protected int compare(Double number) {
      return NumberComparatorHelper.compare(number, this.minValue, InfinityNumberComparatorHelper.LESS_THAN);
   }
}
