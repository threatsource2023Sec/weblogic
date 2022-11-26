package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

public class DecimalMaxValidatorForLong extends AbstractDecimalMaxValidator {
   protected int compare(Long number) {
      return DecimalNumberComparatorHelper.compare(number, this.maxValue);
   }
}
