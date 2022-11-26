package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import java.math.BigInteger;

public class DecimalMinValidatorForBigInteger extends AbstractDecimalMinValidator {
   protected int compare(BigInteger number) {
      return DecimalNumberComparatorHelper.compare(number, this.minValue);
   }
}
