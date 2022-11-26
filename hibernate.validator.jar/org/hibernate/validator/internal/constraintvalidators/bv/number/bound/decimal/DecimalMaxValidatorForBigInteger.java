package org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal;

import java.math.BigInteger;

public class DecimalMaxValidatorForBigInteger extends AbstractDecimalMaxValidator {
   protected int compare(BigInteger number) {
      return DecimalNumberComparatorHelper.compare(number, this.maxValue);
   }
}
