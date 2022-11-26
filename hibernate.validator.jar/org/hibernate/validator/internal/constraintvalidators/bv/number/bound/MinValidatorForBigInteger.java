package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import java.math.BigInteger;

public class MinValidatorForBigInteger extends AbstractMinValidator {
   protected int compare(BigInteger number) {
      return NumberComparatorHelper.compare(number, this.minValue);
   }
}
