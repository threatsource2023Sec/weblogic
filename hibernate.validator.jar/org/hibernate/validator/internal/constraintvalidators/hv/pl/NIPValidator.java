package org.hibernate.validator.internal.constraintvalidators.hv.pl;

import java.util.List;
import org.hibernate.validator.constraints.pl.NIP;

public class NIPValidator extends PolishNumberValidator {
   private static final int[] WEIGHTS_NIP = new int[]{6, 5, 7, 2, 3, 4, 5, 6, 7};

   public void initialize(NIP constraintAnnotation) {
      super.initialize(0, Integer.MAX_VALUE, -1, true);
   }

   protected int[] getWeights(List digits) {
      return WEIGHTS_NIP;
   }
}
