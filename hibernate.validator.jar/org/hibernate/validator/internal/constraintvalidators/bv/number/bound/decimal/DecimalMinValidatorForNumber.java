package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

public class DecimalMinValidatorForNumber extends AbstractDecimalMinValidator {
   protected int compare(Number number) {
      return DecimalNumberComparatorHelper.compare(number, this.minValue);
   }
}
