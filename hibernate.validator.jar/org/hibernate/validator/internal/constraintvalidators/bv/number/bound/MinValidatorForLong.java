package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

public class MinValidatorForLong extends AbstractMinValidator {
   protected int compare(Long number) {
      return NumberComparatorHelper.compare(number, this.minValue);
   }
}
