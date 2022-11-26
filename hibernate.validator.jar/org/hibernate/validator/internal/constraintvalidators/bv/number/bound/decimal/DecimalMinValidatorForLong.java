package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

public class DecimalMinValidatorForLong extends AbstractDecimalMinValidator {
   protected int compare(Long number) {
      return DecimalNumberComparatorHelper.compare(number, this.minValue);
   }
}
