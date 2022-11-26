package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

public class MaxValidatorForLong extends AbstractMaxValidator {
   protected int compare(Long number) {
      return NumberComparatorHelper.compare(number, this.maxValue);
   }
}
