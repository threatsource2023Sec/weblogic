package org.hibernate.validator.internal.constraintvalidators.hv.pl;

import java.util.List;
import org.hibernate.validator.constraints.pl.REGON;

public class REGONValidator extends PolishNumberValidator {
   private static final int[] WEIGHTS_REGON_14 = new int[]{2, 4, 8, 5, 0, 9, 7, 3, 6, 1, 2, 4, 8};
   private static final int[] WEIGHTS_REGON_9 = new int[]{8, 9, 2, 3, 4, 5, 6, 7};

   public void initialize(REGON constraintAnnotation) {
      super.initialize(0, Integer.MAX_VALUE, -1, false);
   }

   protected int[] getWeights(List digits) {
      if (digits.size() == 8) {
         return WEIGHTS_REGON_9;
      } else {
         return digits.size() == 13 ? WEIGHTS_REGON_14 : new int[0];
      }
   }
}
