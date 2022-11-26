package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

public class DecimalMaxValidatorForNumber extends AbstractDecimalMaxValidator {
   protected int compare(Number number) {
      return DecimalNumberComparatorHelper.compare(number, this.maxValue);
   }
}
